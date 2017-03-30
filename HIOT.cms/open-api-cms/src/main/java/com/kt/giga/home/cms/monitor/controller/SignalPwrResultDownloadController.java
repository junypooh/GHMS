package com.kt.giga.home.cms.monitor.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.kt.giga.home.cms.monitor.service.SignalResultService;

@Controller
@RequestMapping("/monitor")
public class SignalPwrResultDownloadController {
	
	private Logger log = Logger.getLogger(this.getClass());
	
	@Autowired
	private SignalResultService signalResultService; 
	
	@RequestMapping("/signalPwrResultList.download")
	public ModelAndView pwrDownload(@RequestParam Map<String, Object> searchInfo) throws Exception {
		int page, pageSize = 10000;
		
		page = searchInfo.get("page") == null || searchInfo.get("page").toString().trim().equals("") ? 1 : Integer.parseInt(searchInfo.get("page").toString().trim());
		String searchString		= searchInfo.get("searchString") != null ? searchInfo.get("searchString").toString().trim() : "";
		String searchColumn		= searchInfo.get("searchColumn") != null ? searchInfo.get("searchColumn").toString().trim() : "";
		String searchDateType	= searchInfo.get("searchDateType") != null ? searchInfo.get("searchDateType").toString().trim() : "";
		String searchWeek 		= searchInfo.get("searchWeek") != null ? searchInfo.get("searchWeek").toString().trim() : "";
		String searchStartDate 	= searchInfo.get("searchStartDate") != null ? searchInfo.get("searchStartDate").toString().trim() : "";
		String searchEndDate 	= searchInfo.get("searchEndDate") != null ? searchInfo.get("searchEndDate").toString().trim() : "";
		String searchStartHour 	= searchInfo.get("searchStartHour") != null ? searchInfo.get("searchStartHour").toString().trim() : "";
		String searchEndHour 	= searchInfo.get("searchEndHour") != null ? searchInfo.get("searchEndHour").toString().trim() : "";

		searchInfo.put("page"			, page);
		searchInfo.put("pageSize"		, pageSize);
		searchInfo.put("searchString"	, searchString);
		
		//--------------------------- 날짜관련 스크립트 ---------------------------
		try {
			Date startDate = null, endDate = null;
			Long startLong = null, endLong = null;
			SimpleDateFormat ft = new SimpleDateFormat("yyyyMMddHHmmss");
			if(!searchStartDate.equals("") && !searchEndDate.equals("")) {
				startDate 	= ft.parse(searchStartDate.replace("-", "") + searchStartHour + "0000");
				endDate		= ft.parse(searchEndDate.replace("-", "") + searchEndHour + "5959");
				
				startLong = startDate.getTime();
				endLong = endDate.getTime();
				
				searchInfo.put("startLong", startLong);
				searchInfo.put("endLong", endLong);
			}
			
			
		} catch(Exception e) {
			searchInfo.put("searchStartDate", null);
			searchInfo.put("searchEndDate", null);			
		}
		//--------------------------- 날짜관련 스크립트 ---------------------------

		List<Map<String, Object>> signalResultList = signalResultService.getList(searchInfo);
		List<Map<String, Object>> bindList = new ArrayList<Map<String, Object>>();
		
		String type = null;
		String mac = null;
		String date = null;
		Map mapAttrs = null;
		StringBuffer logData = new StringBuffer();
		
		for(Map<String, Object> signalResult : signalResultList) {
			mapAttrs = (Map)signalResult.get("attrs");
			
			if(mapAttrs.get("60000008") != null){
				if( mapAttrs.containsKey("60000008")==true && mapAttrs.get("60000008").equals("WIFI") ){
					type = (String)mapAttrs.get("60000008");
					mac = (String)mapAttrs.get("60000007");
					date = (String)mapAttrs.get("61000001");
					
					mapAttrs.remove("60000008");
					mapAttrs.remove("60000007");
					mapAttrs.remove("61000001");
					
					logData.append(date + " " + type + " [" + mac + "] " + mapAttrs.toString() + "\r\n");
				}
			}
		}
		
		ModelAndView mv = new ModelAndView("downloadView");
		mv.addObject("logData", logData.toString());
		
		return mv;
	}
	
	@RequestMapping("/signalPwrResultListPageLog.download")
	public ModelAndView pwrPageLogDownload(@RequestParam Map<String, Object> searchInfo) throws Exception {
		//====================================================== 결과 조회 ======================================================
		int page, pageSize = 9999;
		
		page = searchInfo.get("page") == null || searchInfo.get("page").toString().trim().equals("") ? 1 : Integer.parseInt(searchInfo.get("page").toString().trim());
		String searchString		= searchInfo.get("searchString") != null ? searchInfo.get("searchString").toString().trim() : "";
		String searchColumn		= searchInfo.get("searchColumn") != null ? searchInfo.get("searchColumn").toString().trim() : "";

		String searchLogType 	= searchInfo.get("searchLogType") != null ? searchInfo.get("searchLogType").toString().trim() : "";
		String searchStartDate 	= searchInfo.get("searchStartDate") != null ? searchInfo.get("searchStartDate").toString().trim() : "";
		String searchEndDate 	= searchInfo.get("searchEndDate") != null ? searchInfo.get("searchEndDate").toString().trim() : "";
		String searchStartHour 	= searchInfo.get("searchStartHour") != null ? searchInfo.get("searchStartHour").toString().trim() : "";
		String searchEndHour 	= searchInfo.get("searchEndHour") != null ? searchInfo.get("searchEndHour").toString().trim() : "";
		
		String svcTgtSeq 		= searchInfo.get("svcTgtSeq") != null ? searchInfo.get("svcTgtSeq").toString().trim() : "";
		String spotDevSeq 		= searchInfo.get("spotDevSeq") != null ? searchInfo.get("spotDevSeq").toString().trim() : "";
		String devUUID 			= searchInfo.get("devUUID") != null ? searchInfo.get("devUUID").toString().trim() : "";
		String devCctvMac 			= searchInfo.get("devCctvMac") != null ? searchInfo.get("devCctvMac").toString().trim() : "";

		searchInfo.put("searchLogType"	, "ALL");

		searchInfo.put("svcTgtSeq"		, svcTgtSeq);
		searchInfo.put("spotDevSeq"		, spotDevSeq);
		searchInfo.put("devUUID"		, devUUID);

		searchInfo.put("page"			, page);
		searchInfo.put("pageSize"		, pageSize);
		searchInfo.put("searchString"	, searchString);
		
		//--------------------------- 날짜관련 스크립트 ---------------------------
		try {
			Date startDate = null, endDate = null;
			Long startLong = null, endLong = null;
			SimpleDateFormat ft = new SimpleDateFormat("yyyyMMddHHmmss");
			if(!searchStartDate.equals("") && !searchEndDate.equals("")) {
				startDate 	= ft.parse(searchStartDate.replace("-", "") + searchStartHour + "0000");
				endDate		= ft.parse(searchEndDate.replace("-", "") + searchEndHour + "5959");
				
				startLong = startDate.getTime();
				endLong = endDate.getTime();
				
				searchInfo.put("startLong", startLong);
				searchInfo.put("endLong", endLong);
			}
			
			
		} catch(Exception e) {
			searchInfo.put("searchStartDate", null);
			searchInfo.put("searchEndDate", null);			
		}
		//--------------------------- 날짜관련 스크립트 ---------------------------
		
		List<Map<String, Object>> signalResultList = signalResultService.getListPage(searchInfo);
		//List<Map<String, Object>> bindList = new ArrayList<Map<String, Object>>();
		
		Map<String, Object> mapAttrs = new HashMap<>();
		StringBuffer logData = new StringBuffer();
		String type = null;
		String mac = null;
		String date = null;
		for(Map<String, Object> signalResult : signalResultList) {
			mapAttrs 	= (Map<String, Object>)signalResult.get("attrs");
			
			if( mapAttrs != null ){
				if(mapAttrs.get("60000008") != null){
					if( mapAttrs.containsKey("60000008")==true && mapAttrs.get("60000008").equals("WIFI") ){
						/*
						signalResult.put("CurrentSSID", mapAttrs.get("1536") );		//	- CurrentSSID(현재 연결 SSID)
						signalResult.put("CurrentRSSI", mapAttrs.get("1537") );		//	- CurrentRSSI(현재 연결 신호세기)
						signalResult.put("AdjacentNum", mapAttrs.get("1538") );		//	- AdjacentNum(인접채널 수)
						signalResult.put("AdjacentRSSI", mapAttrs.get("1539") );	//	- AdjacentRSSI(인접채널 신호세기)
						signalResult.put("CoChannelNum", mapAttrs.get("1540") );	//	- CoChannelNum(동일채널 수)
						signalResult.put("CoChannelRSSI", mapAttrs.get("1541") );	//	- CoChannelRSSI(동일채널 신호세기)
						
						signalResult.put("eventDt", mapAttrs.get("61000001") );	//	이벤트 발생일시
						*/
						type = (String)mapAttrs.get("60000008");
						//mac = (String)mapAttrs.get("60000007");
						mac = devCctvMac;
						date = (String)mapAttrs.get("61000001");
						
						mapAttrs.remove("60000008");
						mapAttrs.remove("60000007");
						mapAttrs.remove("61000001");
						
						logData.append(date + " " + type + " [" + mac + "] " + mapAttrs.toString() + "\r\n");
						//bindList.add(signalResult);
					}
				}				
			}
		}		
		
		
		/*int totalCount = bindList.size();
		
		System.out.println("======================= logData Start ========================");
		for(Map<String, Object> bindMap : bindList){
			
			System.out.println(
					Integer.toString(totalCount) + "\t" + 
					bindMap.get("rcvDt").toString() + "\t" + 
					bindMap.get("CurrentSSID").toString() + "\t" + 
					bindMap.get("CurrentRSSI").toString() + "\t" +
					bindMap.get("AdjacentNum").toString() + "\t" +
					bindMap.get("AdjacentRSSI").toString() + "\t" +
					bindMap.get("CoChannelNum").toString() + "\t" +
					bindMap.get("CoChannelRSSI").toString() + "\t" +
					bindMap.get("eventDt").toString()
			);
			
			logData.append(
					Integer.toString(totalCount) + "\t" + 
					bindMap.get("rcvDt").toString() + "\t" + 
					bindMap.get("CurrentSSID").toString() + "\t" + 
					bindMap.get("CurrentRSSI").toString() + "\t" +
					bindMap.get("AdjacentNum").toString() + "\t" +
					bindMap.get("AdjacentRSSI").toString() + "\t" +
					bindMap.get("CoChannelNum").toString() + "\t" +
					bindMap.get("CoChannelRSSI").toString() + "\t" +
					bindMap.get("eventDt").toString() + 
					"\r\n"
			);
			totalCount--;
			
			
		}
		System.out.println("======================= logData End ========================");*/
		//====================================================== 결과 조회 ======================================================
		
		ModelAndView mv = new ModelAndView("downloadView");
		mv.addObject("logData", logData.toString());
		
		return mv;
	}
}