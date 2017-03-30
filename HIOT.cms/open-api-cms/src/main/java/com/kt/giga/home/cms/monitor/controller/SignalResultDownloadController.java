package com.kt.giga.home.cms.monitor.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.kt.giga.home.cms.monitor.service.SignalResultService;
import com.kt.giga.home.cms.util.PageNavi;

@Controller
@RequestMapping("/monitor")
public class SignalResultDownloadController {
	
	private Logger logger = Logger.getLogger(this.getClass());
	
	@Autowired
	private SignalResultService signalResultService; 
	
	@RequestMapping("/signalResultList.download")
	public ModelAndView download(@RequestParam Map<String, Object> searchInfo) throws Exception {
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
				if(mapAttrs.containsKey("60000008") && !mapAttrs.get("60000008").equals("WIFI")){
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
	
	@RequestMapping("/signalResultListPageLog.download")
	public ModelAndView downloadPage(@RequestParam Map<String, Object> searchInfo) throws Exception {
		
		
		
		//====================================================== 결과 조회 ======================================================
		int page, pageSize = 10;
		
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

		searchInfo.put("searchLogType"	, searchLogType);

		searchInfo.put("svcTgtSeq"		, svcTgtSeq);
		searchInfo.put("spotDevSeq"		, spotDevSeq);
		searchInfo.put("devUUID"		, devUUID);

		searchInfo.put("page"			, 1);
		searchInfo.put("pageSize"		, 9999);
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
		
		//---------------- 검색 시간
		List<Map<String, Object>> hourList 	= new ArrayList<Map<String, Object>>();
		for(int i=0; i<24; i++){
			Map<String, Object> hourInfo 		= new HashMap<String, Object>();

			if(i<10){
				hourInfo.put("hour", "0" + Integer.toString(i));
			}else{
				hourInfo.put("hour", Integer.toString(i));
			}
			
			hourList.add(hourInfo);
		}
		//--------------------------- 날짜관련 스크립트 ---------------------------
		
		List<Map<String, Object>> signalResultList = signalResultService.getListPage(searchInfo);
		List<Map<String, Object>> bindList = new ArrayList<Map<String, Object>>();
		
		
		String devNm="", attrs="";
		Map mapAttrs = null;
		
		for(Map<String, Object> signalResult : signalResultList) {
			devNm	 	= signalResult.get("devNm") != null ? signalResult.get("devNm").toString() : "";

			mapAttrs 	= (Map)signalResult.get("attrs");
			
			if(mapAttrs != null){
				if(mapAttrs.get("60000008") != null){
					if( mapAttrs.containsKey("60000008")==true && !mapAttrs.get("60000008").equals("WIFI") ){
						signalResult.put("eventDt", mapAttrs.get("61000001") );	
						signalResult.put("logType", mapAttrs.get("60000008") );
						signalResult.put("logCode", "-" );
						
						try{
							if( mapAttrs.get("-1").toString().contains("[RCV]") ||  mapAttrs.get("-1").toString().contains("[SND]") ){
								String[] msgList = mapAttrs.get("-1").toString().split(" ");
								signalResult.put("logCode", msgList[4].toString() );
							}
						}catch(Exception e){
						}
						
						signalResult.put("devNm", devNm);
						bindList.add(signalResult);
					}
				}
			}
		}		

		StringBuffer logData = new StringBuffer();
//		int totalCount = (Integer)searchInfo.get("signalResultListCount");
		int totalCount = Integer.parseInt(signalResultList.get(0).get("totalCnt").toString() );
		
		System.out.println("======================= logData Start ========================");
		for(Map<String, Object> bindMap : bindList){
			
			System.out.println(
					Integer.toString(totalCount) + "\t" + 
					bindMap.get("rcvDt").toString() + "\t" + 
					bindMap.get("logType").toString() + "\t" + 
					bindMap.get("logCode").toString() + "\t" +
					bindMap.get("attrs").toString()
			);
			
			logData.append(
							Integer.toString(totalCount) + "\t" + 
							bindMap.get("rcvDt").toString() + "\t" + 
							bindMap.get("logType").toString() + "\t" + 
							bindMap.get("logCode").toString() + "\t" +
							bindMap.get("attrs").toString() + "\t" + 
							"\r\n"
			);
			totalCount--;
			
			
		}
		System.out.println("======================= logData End ========================");
		
		ModelAndView mv = new ModelAndView("downloadView");
		mv.addObject("logData", logData.toString());
		
		return mv;
	}
}