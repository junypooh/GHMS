package com.kt.giga.home.cms.monitor.controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.kt.giga.home.cms.monitor.service.HbaseRltService;
import com.kt.giga.home.cms.monitor.service.SignalCheckService;
import com.kt.iot.reader.vo.LogEvent2;

@Controller
@RequestMapping("/monitor")
public class HbaseRltController {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());	
	
	@Autowired
	private HttpServletRequest request;
	
	@Autowired
	private SignalCheckService signalCheckService; 

	@Autowired
	private HbaseRltService hbaseRltService; 
		
	//------------- hbase 로그 검색하기전에 카메라대상을 선택하는 페이지
	@RequestMapping("/hbaseRlt")
	public String getHbaseRlt(Model model,@RequestParam Map<String, Object> searchInfo) throws Exception {

		Long telCnt = 0L;
		String mbrId = "", telNo = "", spotDevId = "", devConStat = "", devCctvMac="";
		
		//====================================================== 카메라 검색 ======================================================
		//[카메라 로그 확인 요청]의 메소드를 사용 
		List<Map<String, Object>> signalCheckList = signalCheckService.getList(searchInfo);
		
		for(Map<String, Object> signalCheck : signalCheckList) {
			mbrId 		= signalCheck.get("mbrId") 		!= null ? signalCheck.get("mbrId").toString() 		: "";
			telCnt 		= signalCheck.get("telCnt") 	!= null ? (long)signalCheck.get("telCnt") 			: 0;
			telNo 		= signalCheck.get("telNo") 		!= null ? signalCheck.get("telNo").toString() 		: "";
			spotDevId 	= signalCheck.get("spotDevId") 	!= null ? signalCheck.get("spotDevId").toString() 	: "";			
			devConStat 	= signalCheck.get("devConStat") != null ? signalCheck.get("devConStat").toString() 	: "";		
			devCctvMac	= signalCheck.get("devCctvMac") != null ? signalCheck.get("devCctvMac").toString() 	: "";	
			
			if(mbrId.length() > 3) {
				mbrId 	= mbrId.substring(0, mbrId.length() - 3) + "***";
				signalCheck.put("mbrId", mbrId);
			}
			
			if(spotDevId.length() > 4) {
				spotDevId = spotDevId.substring(0, spotDevId.length() - 4) + "****";
				signalCheck.put("spotDevId", spotDevId);				
			}
			
			if(telNo.length() > 4){
				telNo 	= telNo.substring(0, telNo.length() - 4) + "****";
			}			
			
			if(telCnt > 1) {
				telNo 	= telNo + "(외 " + String.valueOf(telCnt - 1) + ")";
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
		//====================================================== 카메라 검색 ======================================================

		//카메라 검색후에 hbase 조회 할 시간 셋팅
		List<Map<String, Object>> hourList 	= new ArrayList<Map<String, Object>>();
		for(int i=0; i<24; i++){
			Map<String, Object> hourInfo 	= new HashMap<String, Object>();

			if(i<10){
				hourInfo.put("hour", "0" + Integer.toString(i));
			}else{
				hourInfo.put("hour", Integer.toString(i));
			}
			
			hourList.add(hourInfo);
		}
		model.addAttribute("hourList"			, hourList);


		return "/monitor/hbaseRlt";
	}
	
	@RequestMapping("/hbaseRltLog")
	public String getHbaseRltLog(Model model,@RequestParam Map<String, Object> searchInfo, HttpServletRequest request, HttpServletResponse response) throws Exception {
		String returnUrl 		= "";
		int maxCnt	 			= 99;

		String submitType 		= searchInfo.get("submitType") 		!= null ? searchInfo.get("submitType").toString().trim() 		: "";
		String searchLogType 	= searchInfo.get("searchLogType") 	!= null ? searchInfo.get("searchLogType").toString().trim() 	: "";
		String searchSortType 	= searchInfo.get("searchSortType") 	!= null ? searchInfo.get("searchSortType").toString().trim() 	: "";
		String searchStartDate 	= searchInfo.get("searchStartDate") != null ? searchInfo.get("searchStartDate").toString().trim() 	: "";
		String searchEndDate 	= searchInfo.get("searchEndDate") 	!= null ? searchInfo.get("searchEndDate").toString().trim() 	: "";
		String searchStartHour 	= searchInfo.get("searchStartHour") != null ? searchInfo.get("searchStartHour").toString().trim() 	: "";
		String searchEndHour 	= searchInfo.get("searchEndHour") 	!= null ? searchInfo.get("searchEndHour").toString().trim() 	: "";
		
		String svcTgtSeq 		= searchInfo.get("svcTgtSeq") 		!= null ? searchInfo.get("svcTgtSeq").toString().trim() 		: "";
		String spotDevSeq 		= searchInfo.get("spotDevSeq") 		!= null ? searchInfo.get("spotDevSeq").toString().trim() 		: "";
		String devUUID 			= searchInfo.get("devUUID") 		!= null ? searchInfo.get("devUUID").toString().trim() 			: "";

		String searchRcvDtLast		= searchInfo.get("searchRcvDtLast") 	!= null ? searchInfo.get("searchRcvDtLast").toString().trim() 		: "";
		String searchEventDtLast	= searchInfo.get("searchEventDtLast") 	!= null ? searchInfo.get("searchEventDtLast").toString().trim() 	: "";
		String searchRcvDtSeqLast	= searchInfo.get("searchRcvDtSeqLast") 	!= null ? searchInfo.get("searchRcvDtSeqLast").toString().trim() 	: "";
		String searchOccDtSeqLast	= searchInfo.get("searchOccDtSeqLast") 	!= null ? searchInfo.get("searchOccDtSeqLast").toString().trim() 	: "";

		Boolean moreRcvDt 		= false;
		Boolean moreOccDt 		= false;
		Boolean bindStatus 		= true;
		int intRcvDtSeqLast		= searchRcvDtSeqLast == "" ? 0 : Integer.parseInt( searchRcvDtSeqLast.toString() );
		int intOccDtSeqLast		= searchOccDtSeqLast == "" ? 0 : Integer.parseInt( searchOccDtSeqLast.toString() );

		
		searchInfo.put("svcTgtSeq"		, svcTgtSeq);
		searchInfo.put("spotDevSeq"		, spotDevSeq);
		searchInfo.put("devUUID"		, devUUID);
		
		//--------------------------- 날짜관련 스크립트 ---------------------------
		try {
			Date startDate	= null, endDate = null;
			Long startLong 	= null, endLong = null;
			SimpleDateFormat ft = new SimpleDateFormat("yyyyMMddHHmmssSSS");
			if(!searchStartDate.equals("") && !searchEndDate.equals("")) {
				startDate 	= ft.parse(searchStartDate.replace("-", "") + searchStartHour + "0000000");
				endDate		= ft.parse(searchEndDate.replace("-", "") + searchEndHour + "5959999");

				//더보기 일경우 마지막로그 시간을 검색시작 시간으로 치환
				if( searchSortType.equals("rcvDt") && !searchRcvDtLast.equals("")){
					moreRcvDt	= true;
					endDate = ft.parse( searchRcvDtLast.replace("-", "").replace(":", "").replace(".", "").replace(" ", "") );

					System.out.println("==================================== timestamp searchRcvDtLast ==================================== ");
					System.out.println(searchRcvDtLast.replace("-", "").replace(":", "").replace(".", "").replace(" ", ""));
					System.out.println(endDate);
					System.out.println("searchRcvDtSeqLast = " + searchRcvDtSeqLast);
					System.out.println("==================================== timestamp searchRcvDtLast ==================================== ");
				}
				
				if( searchSortType.equals("occDt") && !searchEventDtLast.equals("")){
					moreOccDt	= true;
					endDate = ft.parse( searchEventDtLast.replace("-", "").replace(":", "").replace(".", "").replace(" ", "") );

					System.out.println("==================================== timestamp searchEventDtLast ==================================== ");
					System.out.println(searchEventDtLast.replace("-", "").replace(":", "").replace(".", "").replace(" ", ""));
					System.out.println(endDate);
					System.out.println("searchOccDtSeqLast = " + searchOccDtSeqLast);
					System.out.println("==================================== timestamp searchEventDtLast ==================================== ");
				}

				startLong 	= startDate.getTime();
				endLong 	= endDate.getTime();
				
				searchInfo.put("startLong", startLong);
				searchInfo.put("endLong", endLong);
			}			
		} catch(Exception e) {
			searchInfo.put("searchStartDate", null);
			searchInfo.put("searchEndDate", null);			
		}
		//--------------------------- 날짜관련 스크립트 ---------------------------

		System.out.println("==================================== send searchInfo ==================================== ");
		System.out.println(searchInfo);
		System.out.println("==================================== send searchInfo ==================================== ");

		List<LogEvent2> signalResultList = hbaseRltService.getHbase(searchInfo);
		List<Map<String, Object>> bindList 		= new ArrayList<Map<String, Object>>();

		System.out.println("==================================== return signalResultList ==================================== ");
//		System.out.println(signalResultList);
		if(submitType.equals("download")){
			System.out.println("this log is download...");
		}else{
			System.out.println("this log is page view...");
		}
		System.out.println("==================================== return signalResultList ==================================== ");

		String rcvDtLast = "";
		String eventDtLast = "";

		switch(submitType){
		case "page": //결과조회
			if ( searchLogType.equals("WIFI") ){ //카메라 로그 전체, 타입별					
				returnUrl = "/monitor/hbaseRltWifi";
				
				Map<String, Object> mapAttrs = new HashMap<>();
				for(LogEvent2 signalResult : signalResultList) {
//					System.out.println("==================================== signalResult =" + signalResult);
					Map<String, Object> tmpBind = new HashMap<>();

					tmpBind.put("rcvDt"		, "-" );
					tmpBind.put("eventDt"	, "-" );	
					tmpBind.put("logType"	, "-" );
					tmpBind.put("logCode"	, "-" );
					tmpBind.put("attrs"		, "-" );
					tmpBind.put("rcvDtSeq"	, "" );//롤링키
					tmpBind.put("occDtSeq"	, "" );//롤링키

					if( signalResult.getRcvDt() != null ){
						tmpBind.put("rcvDt"		, signalResult.getRcvDt());
					}
					if( signalResult.getAttrs() != null ){
						tmpBind.put("attrs"		, signalResult.getAttrs());
					}

					if( signalResult.getRcvDtSeq() != null ){
						tmpBind.put("rcvDtSeq"		, signalResult.getRcvDtSeq());
					}
					if( signalResult.getOccDtSeq() != null ){
						tmpBind.put("occDtSeq"		, signalResult.getOccDtSeq());
					}

					mapAttrs 	= signalResult.getAttrs();
//					System.out.println("==================================== mapAttrs =" + mapAttrs);

					if(mapAttrs != null){
						if(mapAttrs.get("60000008") != null){
							if( mapAttrs.containsKey("60000008")==true){
								tmpBind.put("CurrentSSID", mapAttrs.get("1536") );		//	- CurrentSSID(현재 연결 SSID)
								tmpBind.put("CurrentRSSI", mapAttrs.get("1537") );		//	- CurrentRSSI(현재 연결 신호세기)
								tmpBind.put("AdjacentNum", mapAttrs.get("1538") );		//	- AdjacentNum(인접채널 수)
								tmpBind.put("AdjacentRSSI", mapAttrs.get("1539") );		//	- AdjacentRSSI(인접채널 신호세기)
								tmpBind.put("CoChannelNum", mapAttrs.get("1540") );		//	- CoChannelNum(동일채널 수)
								tmpBind.put("CoChannelRSSI", mapAttrs.get("1541") );	//	- CoChannelRSSI(동일채널 신호세기)
							}
						}
					}
					
					//더보기의 경우 롤링키 이전값은 스킵하고 바인딩
					bindStatus = true;
					if( moreRcvDt == true){
						if(searchRcvDtLast.equals(	signalResult.getRcvDt() )) {
							if( intRcvDtSeqLast < Integer.parseInt( signalResult.getRcvDtSeq() )){
								bindStatus = true;
							}else{
								bindStatus = false;
								logger.debug("skip ■ "	+ tmpBind);
							}
						}
					}else if( moreOccDt == true){
						if(searchEventDtLast.equals(signalResult.getOccDt().toString() )) {
							if(	intOccDtSeqLast < Integer.parseInt( signalResult.getOccDtSeq() ) ){
								bindStatus = true;
							}else{
								bindStatus = false;
								logger.debug("skip ■ "	+ tmpBind);
							}
						}
					}
					
					if(	bindStatus == true ){
						bindList.add(tmpBind);
					}

				}	
			}else {	//카메라 로그 전체, 타입별			
				returnUrl = "/monitor/hbaseRltLog";
				
				Map<String, Object> mapAttrs = new HashMap<>();
				for(LogEvent2 signalResult : signalResultList) {
//					System.out.println("==================================== signalResult =" + signalResult);
					Map<String, Object> tmpBind = new HashMap<>();

					tmpBind.put("rcvDt"		, "-" );
					tmpBind.put("eventDt"	, "-" );	
					tmpBind.put("logType"	, "-" );
					tmpBind.put("logCode"	, "-" );
					tmpBind.put("attrs"		, "-" );
					tmpBind.put("rcvDtSeq"	, "" );//롤링키
					tmpBind.put("occDtSeq"	, "" );//롤링키
					
					if( signalResult.getRcvDt() != null ){
						tmpBind.put("rcvDt"		, signalResult.getRcvDt());
						rcvDtLast =  signalResult.getRcvDt().toString();
					}
					if( signalResult.getAttrs() != null ){
						tmpBind.put("attrs"		, signalResult.getAttrs());
					}

					if( signalResult.getRcvDtSeq() != null ){
						tmpBind.put("rcvDtSeq"		, signalResult.getRcvDtSeq());
					}
					if( signalResult.getOccDtSeq() != null ){
						tmpBind.put("occDtSeq"		, signalResult.getOccDtSeq());
					}
					
					mapAttrs 	= signalResult.getAttrs();
//					System.out.println("==================================== mapAttrs =" + mapAttrs);

					if(mapAttrs != null){
						if(mapAttrs.get("60000008") != null){
							if( mapAttrs.containsKey("60000008")==true){
								tmpBind.put("eventDt", mapAttrs.get("61000001") );	
								tmpBind.put("logType", mapAttrs.get("60000008") );
								
								rcvDtLast = mapAttrs.get("61000001").toString();

								try{
									if( mapAttrs.get("-1").toString().contains("[RCV]") == true ){
										String[] msgSplit = mapAttrs.get("-1").toString().split("\\[RCV\\] ");
										String[] msgList = msgSplit[1].toString().split(" ");
										tmpBind.put("logCode", msgList[2].toString() );
									}
									if( mapAttrs.get("-1").toString().contains("[SND]") ){
										String[] msgSplit = mapAttrs.get("-1").toString().split("\\[SND\\] ");
										String[] msgList = msgSplit[1].toString().split(" ");
										tmpBind.put("logCode", msgList[2].toString() );
									}
								}catch(Exception e){
									//ignore case
								}
							}
						}
					}
					
					//더보기의 경우 롤링키 이전값은 스킵하고 바인딩
					bindStatus = true;
					if( moreRcvDt == true){
						if(searchRcvDtLast.equals(	signalResult.getRcvDt() )) {
							if( intRcvDtSeqLast < Integer.parseInt( signalResult.getRcvDtSeq() )){
								bindStatus = true;
							}else{
								bindStatus = false;
								logger.debug("skip ■ "	+ tmpBind);
							}
						}
					}else if( moreOccDt == true){
						if(searchEventDtLast.equals(signalResult.getOccDt().toString() )) {
							if(	intOccDtSeqLast < Integer.parseInt( signalResult.getOccDtSeq() ) ){
								bindStatus = true;
							}else{
								bindStatus = false;
								logger.debug("skip ■ "	+ tmpBind);
							}
						}
					}
					
					if(	bindStatus == true ){
						bindList.add(tmpBind);
					}
					
				}	
			}
			model.addAttribute("signalResultList"	, bindList);
			model.addAttribute("searchInfo"			, searchInfo);
			break;
			
		default://다운로드
			returnUrl = null;

			int signalResultCnt 			= signalResultList.size() ;
			StringBuffer logData 			= new StringBuffer();
			Map<String, Object> mapAttrs 	= new HashMap<>();
			
			for(LogEvent2 signalResult : signalResultList) {
//				System.out.println("==================================== signalResult =" + signalResult);
				Map<String, Object> tmpBind = new HashMap<>();

				tmpBind.put("rcvDt"		, "-" );
				tmpBind.put("eventDt"	, "-" );	
				tmpBind.put("logType"	, "-" );
				tmpBind.put("logCode"	, "-" );
				tmpBind.put("attrs"		, "-" );
				
				if( signalResult.getRcvDt() != null ){
					tmpBind.put("rcvDt"		, signalResult.getRcvDt());
				}
				if( signalResult.getAttrs() != null ){
					tmpBind.put("attrs"		, signalResult.getAttrs());
				}

				mapAttrs 	= signalResult.getAttrs();
//				System.out.println("==================================== mapAttrs =" + mapAttrs);
				if(mapAttrs != null){
					if(mapAttrs.get("60000008") != null){
						if( mapAttrs.containsKey("60000008")==true ){
							tmpBind.put("eventDt", mapAttrs.get("61000001") );	
							tmpBind.put("logType", mapAttrs.get("60000008") );

							try{
								if( mapAttrs.get("-1").toString().contains("[RCV]") == true ){
									String[] msgSplit = mapAttrs.get("-1").toString().split("\\[RCV\\] ");
									String[] msgList = msgSplit[1].toString().split(" ");
									tmpBind.put("logCode", msgList[2].toString() );
								}
								if( mapAttrs.get("-1").toString().contains("[SND]") ){
									String[] msgSplit = mapAttrs.get("-1").toString().split("\\[SND\\] ");
									String[] msgList = msgSplit[1].toString().split(" ");
									tmpBind.put("logCode", msgList[2].toString() );
								}
							}catch(Exception e){
								//ignore case
							}
						}
					}
				}
				
				//streaming contents
				logData.append(
					Integer.toString(signalResultCnt) 	+ "\t" + 
					tmpBind.get("rcvDt").toString() 	+ "\t" + 
					tmpBind.get("logType").toString() 	+ "\t" + 
					tmpBind.get("logCode").toString() 	+ "\t" +
					tmpBind.get("attrs").toString() 	+ "\t" + 
					"\r\n"
				);
				signalResultCnt--;
			}	
			
			//---------------------------------------- streaming download ----------------------------------------
			response.setHeader("Cache-Control", "max-age=0");
			response.setContentType("application/download; charset=utf-8");
			response.setContentLength(logData.length());
			
			SimpleDateFormat df	= new SimpleDateFormat("yyyyMMdd-hhmmss.SSS");
			String nowDate 		= df.format(new Date());
			
			String userAgent 	= request.getHeader("User-Agent");
			boolean ie			= userAgent.indexOf("MSIE") > -1;
			String fileName 	= "log_" + nowDate + ".txt";
			
			if(ie) {
				fileName 		= URLEncoder.encode(fileName, "utf-8");
			} else {
				fileName 		= new String(fileName.getBytes("utf-8"), "iso-8859-1");
			}
			response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\";");
			response.setHeader("Content-Transfer-Encoding", "binary");
			
			OutputStream os 	= response.getOutputStream();
			InputStream is 		= IOUtils.toInputStream(logData, "UTF-8");
			
			try {
				FileCopyUtils.copy(is, os);
				
			} finally {
				if(is != null) {
					try {
						is.close();
					} catch(IOException ioe) {}
				}
			}
			
			os.flush();
			//---------------------------------------- streaming download ----------------------------------------
			
			break;
		}
		
		//검색요청갯수 보다 응답갯수가 작으면 더보기 버튼 숨김 
		if(maxCnt > signalResultList.size()){
			model.addAttribute("moreBtn"	, false);
		}else{
			model.addAttribute("moreBtn"	, true);
		}
		return returnUrl;
	}

}

