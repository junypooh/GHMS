package com.kt.giga.home.openapi.ghms.kafka.dao;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.client.HBaseAdmin;
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
import org.springframework.stereotype.Repository;

import com.kt.giga.home.openapi.ghms.kafka.entity.HbaseControlHistory;
import com.kt.giga.home.openapi.ghms.kafka.type.MicroTimestampType;
import com.kt.iot.reader.StoreUtil.MicroTimestamp;
import com.kt.iot.reader.action.contl_occ_hist.ActionContlOccHist;
import com.kt.iot.reader.action.event_out.ActionEventOut;

@Repository
public class HbaseEventHistoryDao {

	private Log logger = LogFactory.getLog(this.getClass());
	
	@Autowired private HbaseTemplate hbaseTemplate;
	private HConnection hConnectionEventOut;
	
	
	@Value("${hbase.host}") public String hbaseHost;
	@Value("${hbase.port}") public String hbasePort;

	private String tableName = "event_out";
	
	
	public static byte[] CF_EVENT_OUT = Bytes.toBytes("cf");
       
	private byte[] qEventSeq               = Bytes.toBytes("eventSeq");       
	private byte[] qSvcCode               = Bytes.toBytes("svcCode");          
	private byte[] qSvcTgtSeq              = Bytes.toBytes("svcTgtSeq");      
	private byte[] qDevTypeCd              = Bytes.toBytes("devTypeCd");  
	private byte[] qDevNm                  = Bytes.toBytes("devNm");          
	private byte[] qSnsnCd                = Bytes.toBytes("snsnTagCd");

	@PostConstruct
	public void init() throws Exception {
		hConnectionEventOut = ActionEventOut.getConnection(hbaseHost, hbasePort);
		hConnectionEventOut.isMasterRunning();
	}
	
	@PreDestroy
	public void destroy() throws Exception{
		hConnectionEventOut.close();
	}
	
	
	public List<HbaseControlHistory> findEventOutList(Long svcTgtSeq, Long spotDevSeq, Long minTimestamp, Long maxTimestamp) {
		List<Map<String, String>> list = null;
		HConnection conn = null;
		try {
			conn = ActionEventOut.getConnection(hbaseHost, hbasePort);
			
			/** event를 저장하는 timestamp값이 잘못되어 시간 gap을 크게(1년으로) 늘리도록 하였음.(2015,08,19 최재완 요청) */
			list = ActionEventOut.scanEventOut(conn, "001HIT003", svcTgtSeq, spotDevSeq, (maxTimestamp-31536000000L), maxTimestamp, 9999);
		} catch (Exception e) {
			logger.warn("Exception in findEventOutList : "+e.getMessage());
		} finally{
			try {
				if(conn != null){
					conn.close();
				}
			} catch (IOException e) {
				logger.warn("IOException in conn.close() : "+e.getMessage());
			}
		}
		
		if(list == null){
			return null;
		}
		
		List<HbaseControlHistory> resList = new ArrayList<>();
		for(Map<String, String> map : list){
			logger.debug("map : " + map);
			
			String eventSeq = map.get("eventSeq") != null ? map.get("eventSeq").substring(3)+"000" : null;
			
			/** 공초이전의 값들은 보이지 않도록 필터링 로직 추가, timestamp값을 보정(2015,08,19 Jnam 작성) */
			if(eventSeq == null){
				continue;
			}else{
				Long timestamp = MicroTimestampType.TRANSAC_ID.getTimestamp(eventSeq);
				if(timestamp == null){
					continue;
				}else{
					if(timestamp < minTimestamp){
						continue;
					}
				}
			}
			
			HbaseControlHistory history = new HbaseControlHistory(map.get("rowId")
					, eventSeq
					, String.valueOf(svcTgtSeq)
					, map.get("devTypeCd")
					, String.valueOf(spotDevSeq)
					, ""
					, map.get("devNm")
					, map.get("outbDtm")
					, map.get("snsnTagCd")
					, ""
					, "01"
					, ""
					, ""
					, "");
			history.setEventId(map.get("evetId"));
			history.setAttributes(map.get("attributes"));
			history.setSvcCode(map.get("svcCode"));
			resList.add(history);
		}
		
		return resList;
	}	
	
	public List<HbaseControlHistory> findEventOutList(Long minStamp, Long maxStamp, Filter... filters) {
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
					    Bytes.toString(result.getValue(CF_EVENT_OUT, qEventSeq)).substring(3)+"000",	/** 3 as "E0_".length() */
					    Bytes.toString(result.getValue(CF_EVENT_OUT, qSvcTgtSeq)),
					    Bytes.toString(result.getValue(CF_EVENT_OUT, qDevTypeCd)),
					    Bytes.toString(result.getValue(CF_EVENT_OUT, Bytes.toBytes("spotDevSeq"))),
					    "",
					    Bytes.toString(result.getValue(CF_EVENT_OUT, qDevNm)),
					    Bytes.toString(result.getValue(CF_EVENT_OUT, Bytes.toBytes("outbDtm"))),
					    Bytes.toString(result.getValue(CF_EVENT_OUT, qSnsnCd)),
					    "",
					    "01",
					    "",
					    "",
					    "");
				hist.setEventId(Bytes.toString(result.getValue(CF_EVENT_OUT, Bytes.toBytes("evetId"))));
				hist.setAttributes(Bytes.toString(result.getValue(CF_EVENT_OUT, Bytes.toBytes("attributes"))));
				hist.setSvcCode(Bytes.toString(result.getValue(CF_EVENT_OUT, qSvcCode)));
				return hist;
			}
		};
				
		return hbaseTemplate.find(tableName, scan, mapper);
	}
		
}
