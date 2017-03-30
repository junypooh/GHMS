package com.kt.giga.home.cms.monitor.service;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.hadoop.hbase.client.HConnection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kt.giga.home.cms.common.service.APIEnv;
import com.kt.giga.home.cms.monitor.dao.SignalResultDao;
import com.kt.iot.reader.action.log_event.ActionLogEvent;
import com.kt.iot.reader.action.log_event.ActionLogEventLogType;
import com.kt.iot.reader.vo.LogEvent;

@Service
public class SignalResultServiceImpl implements SignalResultService {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());	
	
	@Autowired
	private APIEnv apiEnv; 
	
	// private HConnection conn = null;
	
	@Autowired
	private SignalResultDao signalResultDao;
	
	@Override
	public List<Map<String, Object>> getList(Map<String, Object> searchInfo) throws IOException, Exception {
		
		String remoteHost = apiEnv.getProperty("hbase.ip");
		String remotePort = apiEnv.getProperty("hbase.port");		 
				
		List<Map<String, Object>> tempResultList = new ArrayList<>(); 
		List<Map<String, Object>> signalResultList = new ArrayList<>(); 
		List<Map<String, Object>> inquiryTargetList = signalResultDao.getList(searchInfo);

//		logger.debug("================================= logger.debug  ==============================================");
//		System.out.println("================================= inquiryTargetList  ==============================================");
//		System.out.println(inquiryTargetList);
		
		if(inquiryTargetList != null && inquiryTargetList.size() > 0) {
			HConnection conn = null;
			
			long startDt;
			long endDt;
			
			SimpleDateFormat ft = new SimpleDateFormat("yyyyMMddHHmmss");
			
			if(searchInfo.get("startLong") == null && searchInfo.get("endLong") == null){
				startDt = 0L;   //System.currentTimeMillis()- (100*1000);
				endDt = System.currentTimeMillis();	
			}else{
				startDt = (long)searchInfo.get("startLong");
				endDt = (long)searchInfo.get("endLong");
			}
			
			logger.debug("======================= HBase search Date ========================");
			logger.debug("=======" + Long.toString(startDt) );
			logger.debug("=======" + Long.toString(endDt)	);
			logger.debug("======================= HBase search Date ========================");
						
			try {
				conn = ActionLogEvent.getConnection(remoteHost, remotePort);
				
				for(Map<String, Object> inquiryTarget : inquiryTargetList) {
					List<Map<String, Object>> tempList = this.searchSignalList(conn, inquiryTarget, startDt, endDt);
					
					if(tempList != null) {
						tempResultList.addAll(tempList);
					}
				}				
				
			} catch(Exception e) {
				logger.error("======================= HBase Connection Error ========================");
				logger.error(e.getMessage());
				logger.error("=======================================================================");
				
				tempResultList.addAll(inquiryTargetList);
				for(Map<String, Object> tempResult : tempResultList) {
					tempResult.put("rcvDtStr"	, "연결 오류");
				}				
			} finally {
				if(conn != null) {
					try {
						conn.close();
					} catch (IOException e) {
						// ignore case
					}
				}
			}
			
			
			if( tempResultList.size() != 0 ){
				Collections.sort(tempResultList, new DateCompare());
				
			}
			
			
			int page = (int)searchInfo.get("page");
			int pageSize = (int)searchInfo.get("pageSize");
			int totalCount = tempResultList.size();
			int startIndex = (page - 1) * pageSize;
			int endIndex = (pageSize * page) - 1 > totalCount ? totalCount : (pageSize * page) - 1;
			
			searchInfo.put("signalResultListCount", totalCount);		
			
			signalResultList = tempResultList.size() > pageSize ? tempResultList.subList(startIndex , endIndex) : tempResultList;
	
			String dateStr = "";
			Calendar cal = Calendar.getInstance();
			SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			
			for(Map<String, Object> signalResult : signalResultList) {
				
				if( signalResult.get("rcvDt") != null){
					cal.setTimeInMillis((long)signalResult.get("rcvDt"));
					dateStr = fmt.format(cal.getTime());
					if(!signalResult.containsKey("rcvDtStr")) {
						signalResult.put("rcvDtStr", dateStr);
					}
				}
			}
			
		} else {
			searchInfo.put("signalResultListCount", 0);
		}
		
		return signalResultList;
	}
	
	private List<Map<String, Object>> searchSignalList(HConnection conn, Map<String, Object> inquiryTarget, long startDt, long endDt) {
		
		long svcTgtSeq 		= Long.parseLong(inquiryTarget.get("svcTgtSeq").toString());
		long spotDevSeq 	= Long.parseLong(inquiryTarget.get("spotDevSeq").toString());
//		String spotDevId 	= inquiryTarget.get("spotDevId").toString();
		int maxCnt	 		= 9999;
		String svcCode 		= "001HIT001";

		List<Map<String, Object>> signalList = null;
		
		System.out.println("*************** svcTgtSeq = "	+ svcTgtSeq);
		System.out.println("*************** spotDevSeq = "	+ spotDevSeq);
		System.out.println("*************** startDt = "		+ startDt);
		System.out.println("*************** endDt = "		+ endDt);
		System.out.println("*************** svcCode = "		+ svcCode);

		try {			
			
			List<LogEvent> rows = ActionLogEvent.scanLogEvent(conn, svcCode, svcTgtSeq, spotDevSeq, startDt, endDt, maxCnt);
//			List<LogEvent> rows = ActionLogEventLogType.scanLogEvent_logType( conn, svcCode, "WIFI", startDt, endDt, 10000);
			
			signalList = new ArrayList<>();
			String eventDt	= ""; 
			for (Iterator it = rows.iterator(); it.hasNext();) {
				LogEvent event = (LogEvent) it.next();
				
				logger.debug("## event : " + event.toString());
				
				Map<String, Object> signal = new HashMap<>();
				signal.put("svcTgtSeq"	, svcTgtSeq);
				signal.put("spotDevSeq"	, spotDevSeq);
				signal.put("spotDevId"	, inquiryTarget.get("spotDevId"));
				signal.put("devUUID"	, inquiryTarget.get("devUUID"));
				signal.put("devNm"		, inquiryTarget.get("devNm"));
				signal.put("devCctvMac"	, inquiryTarget.get("devCctvMac"));
				signal.put("mbrId"		, inquiryTarget.get("mbrId"));
				signal.put("telNo"		, inquiryTarget.get("telNo"));
				signal.put("telCnt"		, inquiryTarget.get("telCnt"));
				signal.put("rcvDt"		, event.getRcvDt());	
				signal.put("attrs"		, event.getAttrs());	
				if( event.getAttrs().get("61000001") != null ){
					eventDt	= event.getAttrs().get("61000001").toString();
					signal.put("eventDt"	, Long.parseLong( eventDt.replaceAll("[^\\d]", "") ));	
				}else{
					signal.put("eventDt"	, Long.parseLong("19700101000000000") );	//1970-01-01 00:00:00.000
				}
				
				Map mapAttrs = event.getAttrs();
				
				signalList.add(signal);

//				if(inquiryTarget.get("onlyWifiYn").equals("Y")){
//					if( mapAttrs.containsKey("60000008")==true && mapAttrs.get("60000008").equals("WIFI") ){
//						signalList.add(signal);
//					}
//				}else{
//					if( mapAttrs.containsKey("60000008")==true && !mapAttrs.get("60000008").equals("WIFI") ){
//						signalList.add(signal);
//					}
//				}

			}
			
//			if(rows != null && rows.size() > 0) {
//				signalList = new ArrayList<>();
//				for(LogEvent row : rows) {
//					Map<String, Object> signal = new HashMap<>();
//					signal.put("svcTgtSeq"	, svcTgtSeq);
//					signal.put("spotDevSeq"	, spotDevSeq);
//					signal.put("devUUID"	, inquiryTarget.get("devUUID"));
//					signal.put("devNm"		, inquiryTarget.get("devNm"));
//					signal.put("mbrId"		, inquiryTarget.get("mbrId"));
//					signal.put("telNo"		, inquiryTarget.get("telNo"));
//					signal.put("telCnt"		, inquiryTarget.get("telCnt"));
//					signal.put("rcvDt"		, row.getRcvDt());	
//					signal.put("attrs"		, row.getAttrs());	
//					
//					signalList.add(signal);
//				}
//			} 
		} catch (Exception e) {
			logger.error("=========================== HBase Scan Error ============================");
			logger.error(e.getMessage());
		}
		
		return signalList;
		
	}
	
	// 내림차순 정렬
	public class DateCompare implements Comparator<Map<String, Object>>	{
		@Override
		public int compare(Map<String, Object> obj1, Map<String, Object> obj2) {
			int result = -1;
			
			try{
//				long rcvDt1 = (long)obj1.get("rcvDt"); 
//				long rcvDt2 = (long)obj2.get("rcvDt"); 
				long rcvDt1 = (long)obj1.get("eventDt"); 
				long rcvDt2 = (long)obj2.get("eventDt"); 
				
				if(rcvDt1 > rcvDt2) {
					result = -1;
				} else if(rcvDt1 < rcvDt2) {
					result = 1;
				} else if(rcvDt1 == rcvDt2) {
					result = 0;
				}
				
			}catch(Exception e){
				//todo
			}

			return result;
		}
	}		

	@Override
	public List<Map<String, Object>> getListPage(Map<String, Object> searchInfo) throws IOException, Exception {
		System.out.println("*************** org.springframework.stereotype.Service.getListPage ***************");

		String remoteHost = apiEnv.getProperty("hbase.ip");
		String remotePort = apiEnv.getProperty("hbase.port");		 
				
		List<Map<String, Object>> tempResultList = new ArrayList<>(); 
		List<Map<String, Object>> signalResultList = new ArrayList<>(); 

		try{
			HConnection conn = null;
			
			long startDt;
			long endDt;
			
			SimpleDateFormat ft = new SimpleDateFormat("yyyyMMddHHmmss");
			
			if(searchInfo.get("startLong") == null && searchInfo.get("endLong") == null){
				startDt = 0L;   //System.currentTimeMillis()- (100*1000);
				endDt = System.currentTimeMillis();	
			}else{
				startDt = (long)searchInfo.get("startLong");
				endDt = (long)searchInfo.get("endLong");
			}
			
			logger.debug("======================= HBase search Date ========================");
			logger.debug("=======" + Long.toString(startDt) );
			logger.debug("=======" + Long.toString(endDt)	);
			logger.debug("======================= HBase search Date ========================");
						
			try {
				conn = ActionLogEvent.getConnection(remoteHost, remotePort);
				
				List<Map<String, Object>> tempList = this.searchSignalListPage(conn, searchInfo, startDt, endDt);
				
				if(tempList != null) {
					tempResultList.addAll(tempList);
				}
			} catch(Exception e) {
				logger.error("======================= HBase Connection Error ========================");
				logger.error(e.getMessage());
				e.printStackTrace();
				logger.error("=======================================================================");
				
				Map<String, Object> tempResult = new HashMap<String, Object>(); 
				tempResult.put("rcvDtStr"	, "연결 오류");
			} finally {
				if(conn != null) {
					try {
						conn.close();
					} catch (IOException e) {
						// ignore case
					}
				}
			}
			
			//날짜비교 정렬
//			if( tempResultList.size() != 0 ){
//				Collections.sort(tempResultList, new DateCompare());
//			}
			
			int page = (int)searchInfo.get("page");
			int pageSize = (int)searchInfo.get("pageSize");
			int totalCount = Integer.parseInt( tempResultList.get(0).get("totalCnt").toString() );
			int startIndex = (page - 1) * pageSize;
			int endIndex = (pageSize * page) - 1 > totalCount ? totalCount : (pageSize * page) - 1;
			
			searchInfo.put("signalResultListCount", totalCount);		
			
			signalResultList = tempResultList.size() > pageSize ? tempResultList.subList(startIndex , endIndex) : tempResultList;
	
			String dateStr = "";
			Calendar cal = Calendar.getInstance();
			SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			
			for(Map<String, Object> signalResult : signalResultList) {
				
				if( signalResult.get("rcvDt") != null){
					cal.setTimeInMillis((long)signalResult.get("rcvDt"));
					dateStr = fmt.format(cal.getTime());
					if(!signalResult.containsKey("rcvDtStr")) {
						signalResult.put("rcvDtStr", dateStr);
					}
				}
			}
		}catch(Exception e){
			searchInfo.put("signalResultListCount", 0);
		}

		return signalResultList;
	}
	
	private List<Map<String, Object>> searchSignalListPage(HConnection conn, Map<String, Object> inquiryTarget, long startDt, long endDt) {
		
		String logType 	= inquiryTarget.get("searchLogType").toString();
		
		String submitType = "page";
		if(inquiryTarget.get("submitType") != null){
			submitType = inquiryTarget.get("submitType").toString();
		}
		
		long svcTgtSeq 	= Long.parseLong(inquiryTarget.get("svcTgtSeq").toString());
		long spotDevSeq = Long.parseLong(inquiryTarget.get("spotDevSeq").toString());
		int page 		= Integer.parseInt(inquiryTarget.get("page").toString());
		int pageSize 	= Integer.parseInt(inquiryTarget.get("pageSize").toString());
		int maxCnt	 	= 9999;
		String svcCode 	= "001HIT001";
		
		long totalCnt 		= 0L;
		List<LogEvent> rows	= new ArrayList<LogEvent>();

		List<Map<String, Object>> signalList = null;
		
		System.out.println("*************** logType = "		+ logType);
		System.out.println("*************** svcTgtSeq = "	+ svcTgtSeq);
		System.out.println("*************** spotDevSeq = "	+ spotDevSeq);
		System.out.println("*************** startDt = "		+ startDt);
		System.out.println("*************** endDt = "		+ endDt);
		System.out.println("*************** page = "		+ page);
		System.out.println("*************** pageSize = "	+ pageSize);
		System.out.println("*************** submitType = "	+ submitType);
		System.out.println("*************** svcCode = "		+ svcCode);
		
		try {			
			if(logType.equals("ALL")){
				//rows = ActionLogEvent.scanLogEventWithPaging(conn, svcTgtSeq, spotDevSeq, startDt, endDt, page, pageSize);
				
				if(submitType.equals("page")){
					rows = ActionLogEvent.scanLogEvent(conn, svcCode, svcTgtSeq, spotDevSeq, startDt, endDt, pageSize);
					totalCnt = ActionLogEvent.getCount(conn, svcCode, svcTgtSeq, spotDevSeq, startDt, endDt);
				}else if(submitType.equals("download")){
					rows = ActionLogEvent.scanLogEvent(conn, svcCode, svcTgtSeq, spotDevSeq, startDt ,endDt, pageSize);
					totalCnt = ActionLogEvent.getCount(conn, svcCode, svcTgtSeq, spotDevSeq, startDt, endDt);
				}else if(submitType.equals("wifi")){
					rows = ActionLogEvent.scanLogEvent(conn, svcCode, svcTgtSeq, spotDevSeq, startDt ,endDt, pageSize);
					totalCnt = rows.size();
				}else{
					System.out.println("submitType mismatch");	
				}
				
			}else{
//				로그타입 : SYSTEM , NETWORK, SERVER , SETTING , SERVICE , ETC
				totalCnt = ActionLogEventLogType.getCount(conn, svcCode, logType, startDt, endDt);
				//rows = ActionLogEventLogType.scanLogEventLogTypeWithPaging(conn, "001HIT001", logType, startDt, endDt, page, pageSize);
				
				if(submitType.equals("page")){
					rows = ActionLogEventLogType.scanLogEventLogTypeWithPaging(conn, svcCode, logType, startDt, endDt, page, pageSize);
				}else if(submitType.equals("download")){
					rows = ActionLogEventLogType.scanLogEventLogType(conn, svcCode, logType, startDt, endDt, pageSize);
				}else{
					System.out.println("submitType mismatch");		
				}
				
			}
			
			System.out.println("*************** result rows count = "	+ rows.size());			
			System.out.println("*************** totalCnt = "	+ totalCnt);			
			
			signalList = new ArrayList<>();
			String eventDt	= ""; 
			for (Iterator it = rows.iterator(); it.hasNext();) {
				LogEvent event = (LogEvent) it.next();
				
				logger.debug("## event : " + event.toString());
				
				Map<String, Object> signal = new HashMap<>();
				signal.put("totalCnt"	, totalCnt);
				signal.put("svcTgtSeq"	, svcTgtSeq);
				signal.put("spotDevSeq"	, spotDevSeq);
				signal.put("spotDevId"	, inquiryTarget.get("spotDevId"));
				signal.put("devUUID"	, inquiryTarget.get("devUUID"));
				signal.put("devNm"		, inquiryTarget.get("devNm"));
				signal.put("devCctvMac"	, inquiryTarget.get("devCctvMac"));
				signal.put("mbrId"		, inquiryTarget.get("mbrId"));
				signal.put("telNo"		, inquiryTarget.get("telNo"));
				signal.put("telCnt"		, inquiryTarget.get("telCnt"));
				signal.put("rcvDt"		, event.getRcvDt());	
				signal.put("attrs"		, event.getAttrs());	
				if( event.getAttrs().get("61000001") != null ){
					eventDt	= event.getAttrs().get("61000001").toString();
					signal.put("eventDt"	, Long.parseLong( eventDt.replaceAll("[^\\d]", "") ));	
				}else{
					signal.put("eventDt"	, Long.parseLong("19700101000000000") );	//1970-01-01 00:00:00.000
				}
				
				Map mapAttrs = event.getAttrs();
				
				signalList.add(signal);
			}
			
		} catch (Exception e) {
			logger.error("=========================== HBase Scan Error ============================");
			logger.error(e.getMessage());
		}
		
		return signalList;
		
	}
}
