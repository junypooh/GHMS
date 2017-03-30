/*******************************************************************************
 * Copyright(c) 2015 KT. All rights reserved.
 * This software is the proprietary information of KT.
 *******************************************************************************/
package com.kt.giga.home.cms.inquiry.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.hadoop.hbase.client.HConnection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kt.giga.home.cms.common.service.APIEnv;
import com.kt.giga.home.cms.inquiry.dao.ManagerDeviceDao;
import com.kt.iot.reader.action.contl_occ_hist.ActionContlOccHist;
import com.kt.iot.reader.action.event_out.ActionEventOut;

/**
 * 
 * @author junypooh ( kyungjun.park@ceinside.co.kr )
 * @since 2015. 8. 10.
 */
@Service
public class ManagerDeviceServiceImpl implements ManagerDeviceService {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());	
	
	@Autowired
	private APIEnv apiEnv; 
	
	@Autowired
	private ManagerDeviceDao managerDeviceDao;
	
	private static Map<String, String> baseDeviceTypeMap;
	
	static {
		// 현장장치 그룹별 setting.
		baseDeviceTypeMap = new HashMap<String, String>();
		baseDeviceTypeMap.put("0207", "홈매니저허브");
		baseDeviceTypeMap.put("4003", "도어락");
		baseDeviceTypeMap.put("1006", "가스 안전기");
		baseDeviceTypeMap.put("0701", "열림 감지기");
		baseDeviceTypeMap.put("0000", "UnKnown");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Map<String, String>> getList(Map<String, Object> searchInfo) {

		HConnection conn 	= null;
		HConnection conn1 	= null;
		String remoteHost 	= apiEnv.getProperty("hbase.ip");
		String remotePort 	= apiEnv.getProperty("hbase.port");
		
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();

		try{
			try {
				conn = ActionContlOccHist.getConnection(remoteHost, remotePort);
				conn1 = ActionEventOut.getConnection(remoteHost, remotePort);
				
            	String logTypeContlOcc = "N";
            	String logTypeEventOut = "N";
            	if(searchInfo.get("logTypeContlOcc") != null) {
            		logTypeContlOcc = searchInfo.get("logTypeContlOcc").toString();	
            	}
            	if(searchInfo.get("logTypeEventOut") != null) {
            		logTypeEventOut = searchInfo.get("logTypeEventOut").toString();	
            	}
			
				logger.debug("======================= HBase scan start ========================");
				if("Y".equals(logTypeContlOcc)) {
					List<Map<String, String>> scanHbaseContlOccHistLog = this.scanHbaseContlOccHistLog(conn, searchInfo);
					list.addAll(scanHbaseContlOccHistLog);	
				}
				if("Y".equals(logTypeEventOut)) {
					List<Map<String, String>> scanHbaseEventOutHistLog = this.scanHbaseEventOutHistLog(conn1, searchInfo);
					list.addAll(scanHbaseEventOutHistLog);						
				}
				logger.debug("======================= HBase scan end ========================");
				
				// 제어 시간 기준으로 내림차순 sorting
				Collections.sort(list, new Comparator<Map<String, String >>() {
		            @Override
		            public int compare(Map<String, String> first, Map<String, String> second) {
		            	
		            	String firstValue = first.get("occDt");
		            	if(firstValue == null) {
		            		firstValue = "0000-00-00 00:00:00.000";
		            	}
		            	String secondValue = second.get("occDt");
		            	if(secondValue == null) {
		            		secondValue = "0000-00-00 00:00:00.000";
		            	}
		                
		            	return secondValue.compareTo(firstValue);
		            }
		        });

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
				if(conn1 != null) {
					try {
						conn1.close();
					} catch (IOException e) {
						// ignore case
					}
				}
			}
			
		}catch(Exception e){
			searchInfo.put("signalResultListCount", 0);
		}
		
		return list;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Map<String, Object> getDetail(Map<String, Object> searchInfo) {
		// TODO Auto-generated method stub
		return null;
	}
	
	private List<Map<String, String>> scanHbaseContlOccHistLog(HConnection conn, Map<String, Object> inquiryTarget) throws Exception {
		
		int maxRows	 		= 9999;
		String svcCode 		= "001HIT003";
		List<Map<String,String>> scanContlOccHist = null;
		List<Map<String,String>> allScanContlOccHist = new ArrayList<Map<String,String>>();

		long svcTgtSeq 		= Long.parseLong(inquiryTarget.get("svcTgtSeq").toString());
		long startDt 		= Long.parseLong(inquiryTarget.get("searchStartDateLong").toString());
		long endDt 			= Long.parseLong(inquiryTarget.get("searchEndDateLong").toString());
/*
		Date startDate = null, endDate = null;
		SimpleDateFormat ft = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		startDate 	= ft.parse("201506260000000");
		endDate		= ft.parse("201506265959999");
		
//		long svcTgtSeq 		= (long)1000020626;
		long svcTgtSeq 		= Long.parseLong(inquiryTarget.get("svcTgtSeq").toString());
		long startDt 		= startDate.getTime();
		long endDt 			= endDate.getTime();

		logger.info("svcTgtSeq > " + svcTgtSeq);
		logger.info("startDt > " + startDt);
		logger.info("endDt > " + endDt);
*/		
		List<Map<String,Object>> spotDevSeqList = managerDeviceDao.selectSpotDevSeqBySvcTgtSeq(svcTgtSeq);
		List<Map<String,Object>> snsnTagCdList = managerDeviceDao.selectSnsnTagCdList();
		
		if(spotDevSeqList != null) {
			for(Map<String, Object> map : spotDevSeqList) {
				long spotDevSeq 	= Long.parseLong(map.get("spotDevSeq").toString());
				logger.info("spotDevSeq > " + spotDevSeq);
				
				try {
					logger.info("=========================== HBase scanHbaseContlOccHistLog Start ============================");
					scanContlOccHist = ActionContlOccHist.scanContlOccHist(conn, svcCode, svcTgtSeq, spotDevSeq, startDt, endDt, maxRows);
					if(scanContlOccHist != null) {
						for(Map<String, String> logMap : scanContlOccHist) {
							logMap.put("logType", "기기원격제어");
							logMap.put("spotDevGwNm", map.get("spotDevGwNm").toString());
//							logMap.put("spotDevNm", map.get("spotDevNm").toString());
							logMap.put("spotDevNm", logMap.get("devNm"));
							String mbrId = managerDeviceDao.selectMbrId(Long.parseLong(logMap.get("mbrSeq")));
							if(mbrId != null && mbrId.length() > 3) {
								mbrId = mbrId.substring(0, mbrId.length() - 3) + "***";
								logMap.put("mbrId", mbrId);
							}
							logMap.put("contlDesc", getListValueInfo(snsnTagCdList, logMap.get("snsnTagCd"), "snsnTagCd", "snsnTagDesc"));
//							logMap.put("deviceType", baseDeviceTypeMap.get(map.get("devTypeCd").toString()));
							logMap.put("deviceType", baseDeviceTypeMap.get(logMap.get("devTypeCd")));
							logMap.put("occDt", logMap.get("contlDt"));
						}
						allScanContlOccHist.addAll(scanContlOccHist);
					}
					logger.info("=========================== HBase scanHbaseContlOccHistLog End ============================");
					
				} catch (Exception e) {
					logger.error("=========================== HBase Scan Error ============================");
					logger.error(e.getMessage());
					logger.error("=========================== HBase Scan Error ============================");
					e.printStackTrace();
				}
			}

//			if(allScanContlOccHist != null) {
//				for(Map<String, String> hbaseMap : allScanContlOccHist) {
//					logger.info(hbaseMap.toString());	
//				}
//			}
		}
		
		return allScanContlOccHist;
	}
	
	private List<Map<String, String>> scanHbaseEventOutHistLog(HConnection conn, Map<String, Object> inquiryTarget) throws Exception {
		
		int maxRows	 		= 9999;
		String svcCode 		= "001HIT003";
		List<Map<String,String>> scanEventOutHist = null;
		List<Map<String,String>> allScanEventOutHist = new ArrayList<Map<String,String>>();
		
		long svcTgtSeq 		= Long.parseLong(inquiryTarget.get("svcTgtSeq").toString());
//		long startDt 		= Long.parseLong(inquiryTarget.get("searchStartDateLong").toString());
		String startDt 		= inquiryTarget.get("orgSearchStartDate").toString();
		long endDt 			= Long.parseLong(inquiryTarget.get("searchEndDateLong").toString());
/*
		Date startDate = null, endDate = null;
		SimpleDateFormat ft = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		startDate 	= ft.parse("201506260000000");
		endDate		= ft.parse("201506265959999");
		
//		long svcTgtSeq 		= (long)1000020626;
		long svcTgtSeq 		= Long.parseLong(inquiryTarget.get("svcTgtSeq").toString());
		long startDt 		= startDate.getTime();
		long endDt 			= endDate.getTime();

		logger.info("svcTgtSeq > " + svcTgtSeq);
		logger.info("startDt > " + startDt);
		logger.info("endDt > " + endDt);
*/		
		List<Map<String,Object>> spotDevSeqList = managerDeviceDao.selectSpotDevSeqBySvcTgtSeq(svcTgtSeq);
		List<Map<String,Object>> eventList = managerDeviceDao.selectEventList();
		
		if(spotDevSeqList != null) {
			for(Map<String, Object> map : spotDevSeqList) {
				long spotDevSeq 	= Long.parseLong(map.get("spotDevSeq").toString());
				String lastMtnDt 	= "000000000000";
				if(map.get("lastMtnDt") != null) {
					lastMtnDt 	= map.get("lastMtnDt").toString();
				}
				logger.info("spotDevSeq > " + spotDevSeq);
				
				try {
					logger.info("=========================== HBase scanHbaseEventOutHistLog Start ============================");
					/** event를 저장하는 timestamp값이 잘못되어 시간 gap을 크게(1년으로) 늘리도록 하였음.(2015,08,19 최재완 요청) */
					/** event경우 RDB에서 공장초기화 시간 조회 하여 그 이후 데이터만 노출하는 정책. */
//					scanEventOutHist = ActionEventOut.scanEventOut(conn, svcCode, svcTgtSeq, spotDevSeq, startDt, endDt, maxRows);
					scanEventOutHist = ActionEventOut.scanEventOut(conn, svcCode, svcTgtSeq, spotDevSeq, (endDt-31536000000L), endDt, maxRows);
					if(scanEventOutHist != null) {
						for(Map<String, String> logMap : scanEventOutHist) {
							
							String occDt = logMap.get("eventSeq") != null ? logMap.get("eventSeq").substring(3,11) : null; //YYYYMMDD
							String occDtm = logMap.get("eventSeq") != null ? logMap.get("eventSeq").substring(3,15) : null; //YYYYMMDDHH24MI
							if(occDt == null || Integer.parseInt(startDt) > Integer.parseInt(occDt) || Long.parseLong(lastMtnDt) >= Long.parseLong(occDtm)) {
								continue;
							} else {
								
								logMap.put("logType", "이상상태발생");
								logMap.put("spotDevGwNm", map.get("spotDevGwNm").toString());
								logMap.put("spotDevNm", map.get("spotDevNm").toString());
//								logMap.put("spotDevNm", logMap.get("devNm"));
//								String mbrId = managerDeviceDao.selectMbrId(Long.parseLong(logMap.get("mbrSeq")));
//								if(mbrId != null && mbrId.length() > 3) {
//									mbrId = mbrId.substring(0, mbrId.length() - 3) + "***";
//									logMap.put("mbrId", mbrId);
//								}
								logMap.put("contlDesc", getListValueInfo(eventList, logMap.get("evetId"), "eventId", "stateventNm"));
								logMap.put("deviceType", baseDeviceTypeMap.get(map.get("devTypeCd").toString()));
								logMap.put("occDt", logMap.get("outbDtm"));
								
								allScanEventOutHist.add(logMap);
							}
						}
//						allScanEventOutHist.addAll(scanEventOutHist);
/*
						logger.info("=========================== DEBUG HBase scanHbaseEventOutHistLog Start ============================");
						if(scanEventOutHist != null) {
							for(Map<String, String> hbaseMap : scanEventOutHist) {
								logger.info(hbaseMap.toString());	
							}
						}
						logger.info("=========================== DEBUG HBase scanHbaseEventOutHistLog End ============================");

						logger.info("=========================== DEBUG HBase scanHbaseEventOutHistLogParsing Start ============================");
						if(allScanEventOutHist != null) {
							for(Map<String, String> hbaseMap : allScanEventOutHist) {
								logger.info(hbaseMap.toString());	
							}
						}
						logger.info("=========================== DEBUG HBase scanHbaseEventOutHistLogParsing End ============================");
*/
					}
					logger.info("=========================== HBase scanHbaseEventOutHistLog End ============================");
					
				} catch (Exception e) {
					logger.error("=========================== HBase Scan Error ============================");
					logger.error(e.getMessage());
					logger.error("=========================== HBase Scan Error ============================");
					e.printStackTrace();
				}
			}
/*
			logger.info("=========================== DEBUG HBase scanHbaseEventOutHistLog Start ============================");
			if(allScanEventOutHist != null) {
				for(Map<String, String> hbaseMap : allScanEventOutHist) {
					logger.info(hbaseMap.toString());	
				}
			}
			logger.info("=========================== DEBUG HBase scanHbaseEventOutHistLog End ============================");
*/
		}
		
		return allScanEventOutHist;
	}
	
	private String getListValueInfo(List<Map<String, Object>> list, String value, String mappingColumnName, String viewColumnName) {
		
		String viewColumnValue = "";
		
		if(list != null) {
			for(Map<String, Object> map : list) {
				if(value.equals(map.get(mappingColumnName).toString())) {
					viewColumnValue =  map.get(viewColumnName).toString();
				}
			}
		}
		
		return viewColumnValue;
	}

}
