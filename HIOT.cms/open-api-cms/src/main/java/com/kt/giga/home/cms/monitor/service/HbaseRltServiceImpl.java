package com.kt.giga.home.cms.monitor.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.hadoop.hbase.client.HConnection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kt.giga.home.cms.common.service.APIEnv;
import com.kt.giga.home.cms.monitor.dao.SignalResultDao;
import com.kt.iot.reader.action.log_event2.ActionLogEventOccdt;
import com.kt.iot.reader.action.log_event2.ActionLogEventOccdtLogType;
import com.kt.iot.reader.action.log_event2.ActionLogEventRcvdt;//ec 도착한시간
import com.kt.iot.reader.action.log_event2.ActionLogEventRcvdtLogType;
import com.kt.iot.reader.vo.LogEvent;
import com.kt.iot.reader.vo.LogEvent2;
//서버에서 받은시간

@Service
public class HbaseRltServiceImpl implements HbaseRltService {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());	
	
	@Autowired
	private APIEnv apiEnv; 
	
	// private HConnection conn = null;
	
	@Autowired
	private SignalResultDao signalResultDao;
	
	//------ hbase 연결 후 해당조건의 hbase 로그 조회  
	@Override
	public List<LogEvent2> getHbase(Map<String, Object> searchInfo) throws IOException, Exception {
		logger.debug("*************** org.springframework.stereotype.Service.getListPage ***************");

		HConnection conn 	= null;
		String remoteHost 	= apiEnv.getProperty("hbase.ip");
		String remotePort 	= apiEnv.getProperty("hbase.port");		 

		List<LogEvent2> tempResultList 	= new ArrayList<>(); 
		List<LogEvent2> signalResultList 	= new ArrayList<>(); 

		try{
			try {
				conn = ActionLogEventRcvdt.getConnection(remoteHost, remotePort);
				
				logger.debug("======================= HBase scan start ========================");
				List<LogEvent2> tempList = this.scanHbaseLog(conn, searchInfo);
				
				if(tempList != null) {
					tempResultList.addAll(tempList);
				}
				signalResultList = tempResultList;
				logger.debug("======================= HBase scan end ========================");

			} catch(Exception e) {
				logger.error("======================= HBase Connection Error ========================");
				logger.error(e.getMessage());
				e.printStackTrace();
				logger.error("======================= HBase Connection Error ========================");
				
			} finally {
				if(conn != null) {
					try {
						conn.close();
					} catch (IOException e) {
						// ignore case
					}
				}
			}
			
		}catch(Exception e){
			searchInfo.put("signalResultListCount", 0);
		}

		logger.debug("*************** get hbase ***************");
		if(searchInfo.get("submitType").equals("download")){
			System.out.println("this log is download...");
		}else{
			System.out.println(signalResultList);
		}
		logger.debug("*************** get hbase ***************");

		return signalResultList;
	}
	
	
/*
  		svcTgtSeq=1000001141 
		spotDevSeq=1 
		devUUID=df70fbbe-6020-44c4-8225-920a87ac644c
		submitType=page
		searchLogType=ALL
		searchSortType=rcvDt
		searchStartDate=2015-06-05
		searchStartHour=15
		searchEndDate=2015-06-12
		searchEndHour=23
		page=0
		pageSize=10
		searchString=
		startLong=1433484000000
		endLong=1434121199999
*/
	private List<LogEvent2> scanHbaseLog(HConnection conn, Map<String, Object> inquiryTarget) {
		
		int maxCnt	 		= 99;
		int downloadCnt	 	= 99999;
		String svcCode 		= "001HIT001";

		String logType 		= inquiryTarget.get("searchLogType").toString();
		String sortType 	= inquiryTarget.get("searchSortType").toString();
		String submitType 	= inquiryTarget.get("submitType").toString();

		long svcTgtSeq 		= Long.parseLong(inquiryTarget.get("svcTgtSeq").toString());
		long spotDevSeq 	= Long.parseLong(inquiryTarget.get("spotDevSeq").toString());
		long startDt 		= Long.parseLong(inquiryTarget.get("startLong").toString());
		long endDt 			= Long.parseLong(inquiryTarget.get("endLong").toString());
		
		List<LogEvent> rows		= new ArrayList<LogEvent>();
		List<LogEvent2> rows2	= new ArrayList<LogEvent2>();

		
		logger.debug("*************** logType = "		+ logType);
		logger.debug("*************** sortType = "	+ sortType);
		logger.debug("*************** submitType = "	+ submitType);
		logger.debug("*************** svcCode = "		+ svcCode);
		logger.debug("*************** svcTgtSeq = "	+ svcTgtSeq);
		logger.debug("*************** spotDevSeq = "	+ spotDevSeq);
		logger.debug("*************** startDt = "		+ startDt);
		logger.debug("*************** endDt = "		+ endDt);
		logger.debug("*************** maxCnt = "		+ maxCnt);
		
		try {			
			switch(logType){
				case "ALL":	//타입별 구분 없이 전체요청
					if (sortType.equals("rcvDt") ){					
						if( submitType.equals("page") ){			//전체 + 수신정렬 + 결과조회
							logger.debug("전체 + 수신정렬 + 결과조회 ==> ActionLogEventRcvdt.scanLogEventRcvdt(conn, svcCode, svcTgtSeq, spotDevSeq, startDt, endDt, maxCnt);");
							rows2 = ActionLogEventRcvdt.scanLogEventRcvdt(conn, svcCode, svcTgtSeq, spotDevSeq, startDt, endDt, maxCnt);
//							rows = ActionLogEvent.scanLogEvent(conn, svcCode, svcTgtSeq, spotDevSeq, startDt, endDt, maxCnt);
						}else if( submitType.equals("download") ){//전체 + 수신정렬 + 다운로드
							logger.debug("전체 + 수신정렬 + 다운로드 ==> ActionLogEventRcvdt.scanLogEventRcvdtForDownload(conn, svcCode, svcTgtSeq, spotDevSeq, startDt, endDt, downloadCnt);");
							rows2 = ActionLogEventRcvdt.scanLogEventRcvdtForDownload(conn, svcCode, svcTgtSeq, spotDevSeq, startDt, endDt, downloadCnt);
//							rows = ActionLogEvent.scanLogEvent(conn, svcCode, svcTgtSeq, spotDevSeq, startDt, endDt, downloadCnt);
						}	
						
					}else if( sortType.equals("occDt") ){			
						if( submitType.equals("page") ){			//전체 + 발생정렬 + 결과조회
							logger.debug("전체 + 발생정렬 + 결과조회 ==> ActionLogEventOccdt.scanLogEventOccdt(conn, svcCode, svcTgtSeq, spotDevSeq, startDt, endDt, maxCnt);");
							rows2 = ActionLogEventOccdt.scanLogEventOccdt(conn, svcCode, svcTgtSeq, spotDevSeq, startDt, endDt, maxCnt);
						}else if( submitType.equals("download") ){	//전체 + 발생정렬 + 다운로드
							logger.debug("전체 + 발생정렬 + 다운로드 ==> ActionLogEventOccdt.scanLogEventOccdtForDownload(conn, svcCode, svcTgtSeq, spotDevSeq, startDt, endDt, downloadCnt);");
							rows2 = ActionLogEventOccdt.scanLogEventOccdtForDownload(conn, svcCode, svcTgtSeq, spotDevSeq, startDt, endDt, downloadCnt);
						}	
					}
					break;
					
				default:	//로그타입 : SYSTEM , NETWORK, SERVER , SETTING , SERVICE , ETC, WIFI
					if (sortType.equals("rcvDt") ){					
						if( submitType.equals("page") ){			//타입별 + 수신정렬 + 결과조회
							logger.debug("타입별 + 수신정렬 + 결과조회 ==> ActionLogEventRcvdtLogType.scanLogEventRcvdtLogTypeRange(conn, svcCode, svcTgtSeq, spotDevSeq, logType, startDt, endDt, maxCnt);");
							rows2 = ActionLogEventRcvdtLogType.scanLogEventRcvdtLogTypeRange(conn, svcCode, svcTgtSeq, spotDevSeq, logType, startDt, endDt, maxCnt);
						}else if( submitType.equals("download") ){	//타입별 + 수신정렬 + 다운로드
							logger.debug("타입별 + 수신정렬 + 다운로드 ==> ActionLogEventRcvdtLogType.scanLogEventRcvdtLogTypeRangeForDownload(conn, svcCode, svcTgtSeq, spotDevSeq, logType, startDt, endDt, downloadCnt);");
							rows2 = ActionLogEventRcvdtLogType.scanLogEventRcvdtLogTypeRangeForDownload(conn, svcCode, svcTgtSeq, spotDevSeq, logType, startDt, endDt, downloadCnt);
						}	
						
					}else if( sortType.equals("occDt") ){			
						if( submitType.equals("page") ){			//타입별 + 발생정렬 + 결과조회
							logger.debug("타입별 + 발생정렬 + 결과조회 ==> ActionLogEventOccdtLogType.scanLogEventOccdtLogTypeRange(conn, svcCode, svcTgtSeq, spotDevSeq, logType, startDt, endDt, maxCnt);");
							rows2 = ActionLogEventOccdtLogType.scanLogEventOccdtLogTypeRange(conn, svcCode, svcTgtSeq, spotDevSeq, logType, startDt, endDt, maxCnt);
						}else if( submitType.equals("download") ){	//타입별 + 발생정렬 + 다운로드
							logger.debug("타입별 + 발생정렬 + 다운로드 ==> ActionLogEventOccdtLogType.scanLogEventOccdtLogTypeRangeForDownload(conn, svcCode, svcTgtSeq, spotDevSeq, logType, startDt, endDt, downloadCnt);");
							rows2 = ActionLogEventOccdtLogType.scanLogEventOccdtLogTypeRangeForDownload(conn, svcCode, svcTgtSeq, spotDevSeq, logType, startDt, endDt, downloadCnt);
						}	
					}
					break;
					
			}
			
			logger.debug("*************** result rows count = "	+ rows2.size());			
			
		} catch (Exception e) {
			logger.error("=========================== HBase Scan Error ============================");
			logger.error(e.getMessage());
			logger.error("=========================== HBase Scan Error ============================");
		}
		
		return rows2;
		
	}
	
}
