package com.kt.giga.home.cms.monitor.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.kt.giga.home.cms.monitor.service.SignalCheckService;
import com.kt.giga.home.cms.monitor.service.SignalResultService;
import com.kt.giga.home.cms.util.PageNavi;

@Controller
@RequestMapping("/monitor")
public class SignalPwrResultController {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());	
	
	@Autowired
	private HttpServletRequest request;
	
	@Autowired
	private SignalCheckService signalCheckService; 

	@Autowired
	private SignalResultService signalResultService; 
		
	@RequestMapping("/signalPwrResultList")
	public String getSignalPwrResultList(Model model,@RequestParam Map<String, Object> searchInfo) throws Exception {
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
		searchInfo.put("onlyWifiYn"		, "Y");
		
		//--------------------------- 날짜관련 스크립트 ---------------------------
		try {
			/*if(!searchDateType.equals("")) {
				Date startDate = null, endDate = null;
				SimpleDateFormat ft = new SimpleDateFormat("yyyyMMddHHmmss");
				
				if(searchDateType.equals("range")) {
					if(!searchStartDate.equals("") && !searchEndDate.equals("")) {
						startDate 	= ft.parse(searchStartDate.replace("-", "") + "000000");
						endDate		= ft.parse(searchEndDate.replace("-", "") + "235959");
					} 
				} else {
					int year = Integer.parseInt(searchWeek.substring(0, 4));
					int month = Integer.parseInt(searchWeek.substring(4));
					
					Calendar calendar = Calendar.getInstance();
					calendar.set(year, month - 1, 1);
					
					int lastDate = calendar.getActualMaximum(Calendar.DATE);
					
					startDate 	= ft.parse(searchWeek + "01000000");
					endDate		= ft.parse(searchWeek + String.valueOf(lastDate) + "235959");
				}
				
				searchInfo.put("searchStartDate", startDate);
				searchInfo.put("searchEndDate", endDate);	
			}		*/	
			
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
		
		List<Map<String, Object>> signalResultList = signalResultService.getList(searchInfo);
		List<Map<String, Object>> bindList = new ArrayList<Map<String, Object>>();

		long telCnt = 0L;
		String mbrId = "", telNo = "", spotDevId = "", devNm="", devCctvMac="", attrs="";
		Map mapAttrs = null;
//		String CurrentSSID = "", CurrentRSSI = "", AdjacentNum = "", AdjacentRSSI = "", CoChannelNum = "", CoChannelRSSI = "";
//		Map<String, Object> attrs_map = new HashMap<String, Object>();
		
		for(Map<String, Object> signalResult : signalResultList) {
			mbrId 		= signalResult.get("mbrId") != null ? signalResult.get("mbrId").toString() : "";
			telCnt 		= signalResult.get("telCnt") != null ? (long)signalResult.get("telCnt") : 0;
			telNo 		= signalResult.get("telNo") != null ? signalResult.get("telNo").toString() : "";
//			spotDevId	= signalResult.get("spotDevId") != null ? signalResult.get("spotDevId").toString() : "";
			devNm	 	= signalResult.get("devNm") != null ? signalResult.get("devNm").toString() : "";
			devCctvMac 	= signalResult.get("devCctvMac") != null ? signalResult.get("devCctvMac").toString() : "";
			mapAttrs 	= (Map)signalResult.get("attrs");
			
			if( mapAttrs != null ){
				if(mapAttrs.get("60000008") != null){
					if( mapAttrs.containsKey("60000008")==true && mapAttrs.get("60000008").equals("WIFI") ){
						signalResult.put("CurrentSSID", mapAttrs.get("1536") );		//	- CurrentSSID(현재 연결 SSID)
						signalResult.put("CurrentRSSI", mapAttrs.get("1537") );		//	- CurrentRSSI(현재 연결 신호세기)
						signalResult.put("AdjacentNum", mapAttrs.get("1538") );		//	- AdjacentNum(인접채널 수)
						signalResult.put("AdjacentRSSI", mapAttrs.get("1539") );	//	- AdjacentRSSI(인접채널 신호세기)
						signalResult.put("CoChannelNum", mapAttrs.get("1540") );	//	- CoChannelNum(동일채널 수)
						signalResult.put("CoChannelRSSI", mapAttrs.get("1541") );	//	- CoChannelRSSI(동일채널 신호세기)
						
						signalResult.put("eventDt", mapAttrs.get("61000001") );	//	이벤트 발생일시
						signalResult.put("devNm", devNm);
//						signalResult.put("devCctvMac", devCctvMac);

						if(mbrId.length() > 3) {
							mbrId = mbrId.substring(0, mbrId.length() - 3) + "***";
							signalResult.put("mbrId", mbrId);
						}
						
						if(spotDevId.length() > 4) {
							spotDevId = spotDevId.substring(0, spotDevId.length() - 4) + "****";
							signalResult.put("spotDevId", spotDevId);				
						}
						
						if(telNo.length() > 4){
							telNo = telNo.substring(0, telNo.length() - 4) + "****";
						}			
						
						if(telCnt > 1) {
							telNo = telNo + "(외 " + String.valueOf(telCnt - 1) + ")";
						}			
						
						signalResult.put("telNo", telNo);
						bindList.add(signalResult);
					}
				}				
			}

		}		
		
		PageNavi pageNavi = new PageNavi();
		
		pageNavi.setAction("/monitor/signalPwrResultList");
		pageNavi.setTotalCount((Integer)searchInfo.get("signalResultListCount"));
		pageNavi.setPageSize(pageSize);
		pageNavi.setNowPage(page);
		pageNavi.setParameters("searchColumn", searchColumn);
		pageNavi.setParameters("searchString", searchString);
		pageNavi.make();
		
		model.addAttribute("hourList"			, hourList);
		model.addAttribute("signalResultList"	, bindList);
		model.addAttribute("pageNavi"			, pageNavi);
		
		System.out.println("========= bindList.size() ========>" + bindList.size());
		
		return "/monitor/signalPwrResultList";
	}
	
	@RequestMapping("/signalPwrResultDetail")
	public String monitorPwrDetail(Model model,@RequestParam Map<String, Object> searchInfo) {
		
		return "/monitor/signalPwrResultDetail";
	}
	
	@RequestMapping("/signalPwrResultListPage")
	public String getSignalResultListPage(Model model,@RequestParam Map<String, Object> searchInfo) throws Exception {
		//====================================================== 카메라 검색 ======================================================
		Long telCnt = 0L;
		String mbrId = "", telNo = "", spotDevId = "", devConStat = "", devCctvMac="";
		List<Map<String, Object>> signalCheckList = signalCheckService.getList(searchInfo);
		
		for(Map<String, Object> signalCheck : signalCheckList) {
			mbrId 		= signalCheck.get("mbrId") != null ? signalCheck.get("mbrId").toString() : "";
			telCnt 		= signalCheck.get("telCnt") != null ? (long)signalCheck.get("telCnt") : 0;
			telNo 		= signalCheck.get("telNo") != null ? signalCheck.get("telNo").toString() : "";
			spotDevId 	= signalCheck.get("spotDevId") != null ? signalCheck.get("spotDevId").toString() : "";			
			devConStat 	= signalCheck.get("devConStat") != null ? signalCheck.get("devConStat").toString() : "";		
			devCctvMac	= signalCheck.get("devCctvMac") != null ? signalCheck.get("devCctvMac").toString() : "";	
			
			if(mbrId.length() > 3) {
				mbrId = mbrId.substring(0, mbrId.length() - 3) + "***";
				signalCheck.put("mbrId", mbrId);
			}
			
			if(spotDevId.length() > 4) {
				spotDevId = spotDevId.substring(0, spotDevId.length() - 4) + "****";
				signalCheck.put("spotDevId", spotDevId);				
			}
			
			if(telNo.length() > 4){
				telNo = telNo.substring(0, telNo.length() - 4) + "****";
			}			
			
			if(telCnt > 1) {
				telNo = telNo + "(외 " + String.valueOf(telCnt - 1) + ")";
			}			
			
			if(devConStat.equals("1")) {
				signalCheck.put("devConStat", "접속");
			}else{
				signalCheck.put("devConStat", "미접속");
			}
			
			signalCheck.put("telNo", telNo);
		}

		model.addAttribute("signalCheckList", signalCheckList);
		model.addAttribute("signalCheckCount", signalCheckList.size());
		
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
		model.addAttribute("hourList"			, hourList);

		//====================================================== 카메라 검색 ======================================================

		return "/monitor/signalPwrResultListPage";
	}
	
	@RequestMapping("/signalPwrResultListPageLog")
	public String signalResultListPageLog(Model model,@RequestParam Map<String, Object> searchInfo) throws Exception {
		
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
		List<Map<String, Object>> bindList = new ArrayList<Map<String, Object>>();
		
		Map<String, Object> mapAttrs = new HashMap<>();
		
		for(Map<String, Object> signalResult : signalResultList) {
			mapAttrs 	= (Map<String, Object>)signalResult.get("attrs");
			
			if( mapAttrs != null ){
				if(mapAttrs.get("60000008") != null){
					if( mapAttrs.containsKey("60000008")==true && mapAttrs.get("60000008").equals("WIFI") ){
						signalResult.put("CurrentSSID", mapAttrs.get("1536") );		//	- CurrentSSID(현재 연결 SSID)
						signalResult.put("CurrentRSSI", mapAttrs.get("1537") );		//	- CurrentRSSI(현재 연결 신호세기)
						signalResult.put("AdjacentNum", mapAttrs.get("1538") );		//	- AdjacentNum(인접채널 수)
						signalResult.put("AdjacentRSSI", mapAttrs.get("1539") );	//	- AdjacentRSSI(인접채널 신호세기)
						signalResult.put("CoChannelNum", mapAttrs.get("1540") );	//	- CoChannelNum(동일채널 수)
						signalResult.put("CoChannelRSSI", mapAttrs.get("1541") );	//	- CoChannelRSSI(동일채널 신호세기)
						signalResult.put("eventDt", mapAttrs.get("61000001") );	//	이벤트 발생일시
						bindList.add(signalResult);
					}
				}				
			}
		}		

//		System.out.println( "totalCnt = "	+ signalResultList.get(0).get("totalCnt") );
		
		PageNavi pageNavi = new PageNavi();
		
		pageNavi.setAction("/monitor/signalPwrResultListPageLog");
//		pageNavi.setTotalCount((Integer)searchInfo.get("signalResultListCount"));
//		pageNavi.setTotalCount( Integer.parseInt(signalResultList.get(0).get("totalCnt").toString() ) );
		pageNavi.setTotalCount( bindList.size() );		
		pageNavi.setPageSize(pageSize);
		pageNavi.setNowPage(page);
		pageNavi.setParametersMap(searchInfo);
		pageNavi.make();
		
		model.addAttribute("signalResultList"	, bindList);
		model.addAttribute("pageNavi"	, pageNavi);
		//====================================================== 결과 조회 ======================================================

		return "/monitor/signalPwrResultListPageLog";
	}
}

