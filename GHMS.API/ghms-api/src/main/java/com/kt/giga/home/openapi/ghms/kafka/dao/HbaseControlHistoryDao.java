package com.kt.giga.home.openapi.ghms.kafka.dao;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.client.HConnection;
import org.apache.hadoop.hbase.client.HTableInterface;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.filter.Filter;
import org.apache.hadoop.hbase.filter.FilterList;
import org.apache.hadoop.hbase.util.Bytes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.hadoop.hbase.HbaseTemplate;
import org.springframework.data.hadoop.hbase.RowMapper;
import org.springframework.data.hadoop.hbase.TableCallback;
import org.springframework.stereotype.Repository;

import com.kt.giga.home.openapi.ghms.kafka.entity.HbaseControlHistory;
import com.kt.iot.reader.action.contl_occ_hist.ActionContlOccHist;


@Repository
public class HbaseControlHistoryDao {

	private Log logger = LogFactory.getLog(this.getClass());
	
	@Value("${hbase.host}") public String hbaseHost;
	@Value("${hbase.port}") public String hbasePort;
	
	
	@Resource(name = "hbaseConfiguration")
	private Configuration config;
	
	@Autowired
	private HbaseTemplate hbaseTemplate;
	
	
	
	private String tableName = "contl_occ_hist";
	
	public static byte[] CF_CONTROL_HISTORY = Bytes.toBytes("cf");
	
     
	private byte[] qSvcCode               = Bytes.toBytes("svcCode");       
	private byte[] qContlSeq               = Bytes.toBytes("contlSeq");       
	private byte[] qSvcTgtSeq              = Bytes.toBytes("svcTgtSeq");      
	private byte[] qDevTypeCd              = Bytes.toBytes("devTypeCd");      
	private byte[] qSpotDevSeq             = Bytes.toBytes("spotDevSeq");        
	private byte[] qDevUuid                = Bytes.toBytes("devUuid");        
	private byte[] qDevNm                  = Bytes.toBytes("devNm");          
	private byte[] qContDt                 = Bytes.toBytes("contlDt");         
	private byte[] qContlCd                = Bytes.toBytes("snsnTagCd");        
	private byte[] qContlVal               = Bytes.toBytes("contlVal");       
	private byte[] qContlTrtSttusCd        = Bytes.toBytes("contlTrtSttusCd");
	private byte[] qMbrSeq                 = Bytes.toBytes("mbrSeq");         
	private byte[] qCustNm                 = Bytes.toBytes("custNm");         
	private byte[] qAcesUrlAdr             = Bytes.toBytes("acesUrlAdr");     
	
	public List<HbaseControlHistory> findControlHistoryList(Long svcTgtSeq, Long spotDevSeq, Long minTimestamp, Long maxTimestamp) {
		List<Map<String, String>> list = null;
		logger.debug("svcTgtSeq : " + svcTgtSeq);
		logger.debug("spotDevSeq : " + spotDevSeq);
		
		HConnection conn = null;
		try {
			conn = ActionContlOccHist.getConnection(hbaseHost, hbasePort);
			list = ActionContlOccHist.scanContlOccHist(conn, "001HIT003", svcTgtSeq, spotDevSeq, minTimestamp, maxTimestamp, 9999);
		} catch (Exception e) {
			logger.debug("Exception in findControlHistoryList : "+e.getMessage());
		} finally{
			try {
				if(conn != null){
					conn.close();
				}
			} catch (IOException e) {
				logger.debug("IOException in conn.close() : "+e.getMessage());
			}
		}
		
		if(list == null){
			return null;
		}
		
		List<HbaseControlHistory> resList = new ArrayList<>();
		for(Map<String, String> map : list){
			logger.debug("map : " + map);
			HbaseControlHistory history = new HbaseControlHistory(map.get("rowId")
					, map.get("contlSeq")
					, String.valueOf(svcTgtSeq)
					, map.get("devTypeCd")
					, String.valueOf(spotDevSeq)
					, map.get("devUuid")
					, map.get("devNm")
					, map.get("contlDt")
					, map.get("snsnTagCd")
					, map.get("contlVal")
					, map.get("contlTrtSttusCd")
					, map.get("mbrSeq")
					, map.get("custNm")
					, map.get("acesUrlAdr"));
			resList.add(history);
		}
		
		
		
		return resList;
	}	
	
	
	public List<HbaseControlHistory> findControlHistoryList(Long minStamp, Long maxStamp, Filter... filters) {		
				
		FilterList filterList = new FilterList(FilterList.Operator.MUST_PASS_ALL);
		for(Filter filter : filters){
			filterList.addFilter(filter);			
		}
		
		Scan scan = new Scan();
		scan.setReversed(true);
		scan.setFilter(filterList);
		
		try {
			scan.setTimeRange(minStamp, maxStamp);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		RowMapper<HbaseControlHistory> mapper = new RowMapper<HbaseControlHistory>() {
			@Override
			public HbaseControlHistory mapRow(Result result, int rowNum) throws Exception {
				HbaseControlHistory hist = new HbaseControlHistory(Bytes.toString(result.getRow()), 
					    Bytes.toString(result.getValue(CF_CONTROL_HISTORY, qContlSeq)),
					    Bytes.toString(result.getValue(CF_CONTROL_HISTORY, qSvcTgtSeq)),
					    Bytes.toString(result.getValue(CF_CONTROL_HISTORY, qDevTypeCd)),
					    Bytes.toString(result.getValue(CF_CONTROL_HISTORY, qSpotDevSeq)),
					    Bytes.toString(result.getValue(CF_CONTROL_HISTORY, qDevUuid)),
					    Bytes.toString(result.getValue(CF_CONTROL_HISTORY, qDevNm)),
					    Bytes.toString(result.getValue(CF_CONTROL_HISTORY, qContDt)),
					    Bytes.toString(result.getValue(CF_CONTROL_HISTORY, qContlCd)),
					    Bytes.toString(result.getValue(CF_CONTROL_HISTORY, qContlVal)),
					    Bytes.toString(result.getValue(CF_CONTROL_HISTORY, qContlTrtSttusCd)),
					    Bytes.toString(result.getValue(CF_CONTROL_HISTORY, qMbrSeq)),
					    Bytes.toString(result.getValue(CF_CONTROL_HISTORY, qCustNm)),
					    Bytes.toString(result.getValue(CF_CONTROL_HISTORY, qAcesUrlAdr)));
				hist.setSvcCode(Bytes.toString(result.getValue(CF_CONTROL_HISTORY, qSvcCode)));
				return hist;
			}
		};
				
		return hbaseTemplate.find(tableName, scan, mapper);
	}
	
	public List<HbaseControlHistory> findAll() {
		
		return hbaseTemplate.find(tableName, "cf", new RowMapper<HbaseControlHistory>() {
			@Override
			public HbaseControlHistory mapRow(Result result, int rowNum) throws Exception {
				return new HbaseControlHistory(Bytes.toString(result.getRow()), 
					    Bytes.toString(result.getValue(CF_CONTROL_HISTORY, qContlSeq)),
					    Bytes.toString(result.getValue(CF_CONTROL_HISTORY, qSvcTgtSeq)),
					    Bytes.toString(result.getValue(CF_CONTROL_HISTORY, qDevTypeCd)),
					    Bytes.toString(result.getValue(CF_CONTROL_HISTORY, qSpotDevSeq)),
					    Bytes.toString(result.getValue(CF_CONTROL_HISTORY, qDevUuid)),
					    Bytes.toString(result.getValue(CF_CONTROL_HISTORY, qDevNm)),
					    Bytes.toString(result.getValue(CF_CONTROL_HISTORY, qContDt)),
					    Bytes.toString(result.getValue(CF_CONTROL_HISTORY, qContlCd)),
					    Bytes.toString(result.getValue(CF_CONTROL_HISTORY, qContlVal)),
					    Bytes.toString(result.getValue(CF_CONTROL_HISTORY, qContlTrtSttusCd)),
					    Bytes.toString(result.getValue(CF_CONTROL_HISTORY, qMbrSeq)),
					    Bytes.toString(result.getValue(CF_CONTROL_HISTORY, qCustNm)),
					    Bytes.toString(result.getValue(CF_CONTROL_HISTORY, qAcesUrlAdr)));
			}
		});
	}
	
	
	public HbaseControlHistory insert(final HbaseControlHistory hist) {
		return hbaseTemplate.execute(tableName, new TableCallback<HbaseControlHistory>() {
			public HbaseControlHistory doInTable(HTableInterface table) throws Throwable {
				Put p = new Put(Bytes.toBytes(hist.getRowKey()));
				p.add(CF_CONTROL_HISTORY, qContlSeq       , Bytes.toBytes(hist.getEventSeq()));
				p.add(CF_CONTROL_HISTORY, qSvcTgtSeq      , Bytes.toBytes(hist.getSvcTgtSeq()));
				p.add(CF_CONTROL_HISTORY, qDevTypeCd      , Bytes.toBytes(hist.getDevTypeCd()));
				p.add(CF_CONTROL_HISTORY, qSpotDevSeq     , Bytes.toBytes(hist.getSpotDevSeq()));
				p.add(CF_CONTROL_HISTORY, qDevUuid        , Bytes.toBytes(hist.getDevUuid()));
				p.add(CF_CONTROL_HISTORY, qDevNm          , Bytes.toBytes(hist.getDevNm()));
				p.add(CF_CONTROL_HISTORY, qContDt         , Bytes.toBytes(hist.getContDt()));
				p.add(CF_CONTROL_HISTORY, qContlCd        , Bytes.toBytes(hist.getContlCd()));
				p.add(CF_CONTROL_HISTORY, qContlVal       , Bytes.toBytes(hist.getContlVal()));
				p.add(CF_CONTROL_HISTORY, qContlTrtSttusCd, Bytes.toBytes(hist.getContlTrtSttusCd()));
				p.add(CF_CONTROL_HISTORY, qMbrSeq         , Bytes.toBytes(hist.getMbrSeq()));
				p.add(CF_CONTROL_HISTORY, qCustNm         , Bytes.toBytes(hist.getCustNm()));
				p.add(CF_CONTROL_HISTORY, qAcesUrlAdr     , Bytes.toBytes(hist.getAcesUrlAdr()));
				p.add(CF_CONTROL_HISTORY, qSvcCode        , Bytes.toBytes(hist.getSvcCode()));
				table.put(p);
				return hist;
				
			}
		});
	}
	
	public void update(final String rowKey, final String colNm, final String colVal) {
		hbaseTemplate.execute(tableName, new TableCallback<HbaseControlHistory>() {
			public HbaseControlHistory doInTable(HTableInterface table) throws Throwable {
				Put p = new Put(Bytes.toBytes(rowKey));
				p.add(CF_CONTROL_HISTORY, Bytes.toBytes(colNm), Bytes.toBytes(colVal));
				table.put(p);
				return null;
			}
		});
	}
	
}
