package com.kt.giga.home.openapi.ghms.kafka.dao;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.hbase.client.HTableInterface;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.filter.Filter;
import org.apache.hadoop.hbase.filter.FilterList;
import org.apache.hadoop.hbase.filter.PageFilter;
import org.apache.hadoop.hbase.util.Bytes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.hadoop.hbase.HbaseTemplate;
import org.springframework.data.hadoop.hbase.RowMapper;
import org.springframework.data.hadoop.hbase.TableCallback;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.kt.giga.home.openapi.ghms.kafka.entity.HbaseControlHistory;
import com.kt.giga.home.openapi.ghms.kafka.type.MicroTimestampType;

@Repository
public class HbaseModeHistoryDao {

	private Log logger = LogFactory.getLog(this.getClass());
	
	@Autowired
	private HbaseTemplate hbaseTemplate;

	private String tableName = "mode_occ_hist";
	
	public static byte[] CF_MODE = Bytes.toBytes("m");
       
	private byte[] qTransactId               = Bytes.toBytes("transacId");       
	private byte[] qSvcTgtSeq              = Bytes.toBytes("svcTgtSeq");      
	private byte[] qOccDt                 = Bytes.toBytes("occDt");         
	private byte[] qModeVal                 = Bytes.toBytes("modeVal");         

	public List<HbaseControlHistory> findModeList(Filter... filters) {
		FilterList filterList = new FilterList(FilterList.Operator.MUST_PASS_ALL);
		for(Filter filter : filters){
			filterList.addFilter(filter);			
		}
		
		Scan scan = new Scan();
		scan.setReversed(true);
		scan.setFilter(filterList);
		
		long maxStamp = System.currentTimeMillis();	/** 현재 */
		long minStamp = maxStamp - 2592000000L;	/** 1개월전 */
		
		try {
			scan.setTimeRange(minStamp, maxStamp);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		RowMapper<HbaseControlHistory> mapper = new RowMapper<HbaseControlHistory>() {
			@Override
			public HbaseControlHistory mapRow(Result result, int rowNum) throws Exception {
				
				HbaseControlHistory hist = new HbaseControlHistory(Bytes.toString(result.getRow()), 
					    Bytes.toString(result.getValue(CF_MODE, qTransactId)),
					    Bytes.toString(result.getValue(CF_MODE, qSvcTgtSeq)),
					    "",
					    "",
					    "",
					    "",
					    Bytes.toString(result.getValue(CF_MODE, qOccDt)),
					    "99990001",
					    Bytes.toString(result.getValue(CF_MODE, qModeVal)),
					    "01",
					    "",
					    "",
					    "");
				return hist;
			}
		};
				
		return hbaseTemplate.find(tableName, scan, mapper);
	}

	public void insert(final String rowKey, final String transacId, final String svcTgtSeq, final String modeVal) {
		hbaseTemplate.execute(tableName, new TableCallback<HbaseControlHistory>() {
			public HbaseControlHistory doInTable(HTableInterface table) throws Throwable {
				Put p = new Put(Bytes.toBytes(rowKey));
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
				Date contlDt = new Date();
				contlDt.setTime(MicroTimestampType.TRANSAC_ID.getTimestamp(transacId));

				p.add(CF_MODE, qTransactId       , Bytes.toBytes(transacId));
				p.add(CF_MODE, qSvcTgtSeq      , Bytes.toBytes(svcTgtSeq));
				p.add(CF_MODE, qOccDt         , Bytes.toBytes(format.format(contlDt)));
				p.add(CF_MODE, qModeVal       , Bytes.toBytes(modeVal));
				table.put(p);
				return null;
			}
		});
		
	}
	
}
