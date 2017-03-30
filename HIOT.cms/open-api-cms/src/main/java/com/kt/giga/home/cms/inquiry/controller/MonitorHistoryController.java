package com.kt.giga.home.cms.inquiry.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.kt.giga.home.cms.common.service.APIEnv;
import com.kt.giga.home.cms.inquiry.service.MonitorHistoryService;
import com.kt.giga.home.cms.inquiry.view.JxlsExcelViewMonitorHistoryList;
import com.kt.giga.home.cms.util.PageNavi;


@Controller
@RequestMapping("/inquiry")
public class MonitorHistoryController {
	
	@Autowired
	private HttpServletRequest request;

	@Autowired
	private MonitorHistoryService monitorHistoryService; 
	
	@Autowired
	private APIEnv apiEnv;
		
	@RequestMapping("/monitorHistoryList")
	public String getSignalStrengthList(Model model,@RequestParam Map<String, Object> searchInfo) throws ParseException{
		String format_date	= ""; // am, pm으로 바꿀 것 
		String amPm_date	= "";
		String excelCount = apiEnv.getProperty("excelCount");
		int page, pageSize 	= 10;
		/*String[] arrStr1, arrStr2; 
		String maskStr, regDate, regTime = "";*/
		
		page = searchInfo.get("page") != null ? Integer.parseInt(searchInfo.get("page").toString().trim()) : 1 ;
		String cpCode			= searchInfo.get("cpCode") != null ? searchInfo.get("cpCode").toString().trim() : "";
		String searchColumn		= searchInfo.get("searchColumn") != null ? searchInfo.get("searchColumn").toString().trim() : "";
		String searchString		= searchInfo.get("searchString") != null ? searchInfo.get("searchString").toString().trim() : "";
		String searchDateType	= searchInfo.get("searchDateType") != null ? searchInfo.get("searchDateType").toString().trim() : "";
		String searchMonth 		= searchInfo.get("searchMonth") != null ? searchInfo.get("searchMonth").toString().trim() : "";
		String searchStartDate 	= searchInfo.get("searchStartDate") != null ? searchInfo.get("searchStartDate").toString().trim() : "";
		String searchEndDate 	= searchInfo.get("searchEndDate") != null ? searchInfo.get("searchEndDate").toString().trim() : "";
		String fd_type		= searchInfo.get("fd_type") == null || searchInfo.get("fd_type").toString().trim().equals("") ? "" : searchInfo.get("fd_type").toString().trim();
		String fd_depth_1		= searchInfo.get("fd_depth_1") == null || searchInfo.get("fd_depth_1").toString().trim().equals("") ? "" : searchInfo.get("fd_depth_1").toString().trim();
		String fd_depth_2		= searchInfo.get("fd_depth_2") == null || searchInfo.get("fd_depth_2").toString().trim().equals("") ? "" : searchInfo.get("fd_depth_2").toString().trim();
		String fd_depth_3		= searchInfo.get("fd_depth_3") == null || searchInfo.get("fd_depth_3").toString().trim().equals("") ? "" : searchInfo.get("fd_depth_3").toString().trim();
		
		searchInfo.put("page"			, page);
		searchInfo.put("pageSize"		, pageSize);
		searchInfo.put("searchString"	, searchString);
		
		SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");		
		if(searchDateType.equals("")) { 
			searchDateType ="range";
			Calendar cal = Calendar.getInstance();
			cal.add(cal.DATE, -7); 
			searchStartDate	= fmt.format(cal.getTime());
			searchEndDate	= fmt.format(new Date());
		}
		
		int monitorHistoryCount = 0;
		List<Map<String, Object>> monitorHistoryList = null;		

		Date sDate = null;
		Date eDate = null;
		SimpleDateFormat transFormat = new SimpleDateFormat("yyyyMMddhhmmssSSS");
		//SimpleDateFormat ft = new SimpleDateFormat("yyyyMMddHHmmss");
		try {
			if(!searchDateType.equals("")) {
				String startDate = null;
				String endDate = null;

				if(searchDateType.equals("range")) {
					if(!searchStartDate.equals("") && !searchEndDate.equals("")) {
						startDate 	= searchStartDate.replace("-", "") + "000000" + "000";
						endDate		= searchEndDate.replace("-", "") + "235959" + "999";
					} 
				} else {
					int year = Integer.parseInt(searchMonth.substring(0, 4));
					int month = Integer.parseInt(searchMonth.substring(4));
					
					Calendar calendar = Calendar.getInstance();
					calendar.set(year, month - 1, 1);
					
					int lastDate = calendar.getActualMaximum(Calendar.DATE);
					
					startDate 	= searchMonth + "01000000" + "000";
					endDate		= searchMonth + String.valueOf(lastDate) + "235959" + "999";
				}
				
				sDate = (Date) transFormat.parse(startDate);
				eDate = (Date) transFormat.parse(endDate);
				
				searchInfo.put("searchStartDate", sDate);
				searchInfo.put("searchEndDate", eDate);
				/*searchInfo.put("searchStartDate", startDate);
				searchInfo.put("searchEndDate", endDate);*/
				
				searchInfo.put("pk_code", fd_type + fd_depth_1 + fd_depth_2 + fd_depth_3);
				
				monitorHistoryCount = monitorHistoryService.getCount(searchInfo);
				monitorHistoryList = monitorHistoryService.getList(searchInfo);
			}else{
				if(searchStartDate.equals("") && searchEndDate.equals("")) {
					/*Date startDate = null, endDate = null;
					startDate 	= ft.parse(searchStartDate.replace("-", "") + "000000");
					endDate		= ft.parse(searchEndDate.replace("-", "") + "235959");*/
					
					sDate = (Date) transFormat.parse(searchStartDate);
					eDate = (Date) transFormat.parse(searchEndDate);
					
					searchInfo.put("searchStartDate", sDate);
					searchInfo.put("searchEndDate", eDate);				
					/*searchInfo.put("searchStartDate", searchStartDate);
					searchInfo.put("searchEndDate", searchEndDate);*/
				}
				

			}
		} catch(Exception e) {
			searchInfo.put("searchStartDate", null);
			searchInfo.put("searchEndDate", null);			
		}
		

		if(monitorHistoryList != null){
			String[] splitStr;
			String contentsStr;
			
			for(Map<String, Object> monitorHistory : monitorHistoryList){		
				//am, pm으로 
				format_date					= monitorHistory.get("cret_dt").toString().trim();
				format_date					= format_date.substring(0, 17);
				SimpleDateFormat format		= new SimpleDateFormat("yyyy-MM-dd HH:mm");
				Date getDate				= format.parse(format_date);
				SimpleDateFormat new_format	= new SimpleDateFormat("yyyy.MM.dd ahh:mm", Locale.US);
				amPm_date					= new_format.format(getDate);
				monitorHistory.put("amPmDate", amPm_date);
				
				//전화번호 마스킹
				String tel_no 	= monitorHistory.get("tel_no").toString().trim();
				tel_no			= tel_no.substring(0, tel_no.length()-4) + "****";
				monitorHistory.put("tel_no", tel_no);
	
				if(monitorHistory.get("dev_nm")==null || monitorHistory.get("dev_nm")==""){ 
					monitorHistory.put("dev_nm", "-"); 
				}
				if(monitorHistory.get("spot_dev_id")==null || monitorHistory.get("spot_dev_id")==""){ 
					monitorHistory.put("spot_dev_id", "-"); 
				}
				if(monitorHistory.get("fd_depth_3")==null || monitorHistory.get("fd_depth_3")==""){ 
					monitorHistory.put("fd_depth_3", "-"); 
				}	
				
				//초기화
				monitorHistory.put("bandwidth", "");
				monitorHistory.put("resolution", "");
				monitorHistory.put("monitorTime", "");
				monitorHistory.put("sessionId", "");
				monitorHistory.put("accNetwork", "");
				monitorHistory.put("deviceName", "");
				monitorHistory.put("osVer", "");
				
				if(monitorHistory.get("menu_code").equals("CL03")){ //모니터링 종료 -> CL03
					if(monitorHistory.get("contents") != null){
						
						contentsStr = monitorHistory.get("contents").toString();
						splitStr = contentsStr.split("\\|");
						
						//대역폭 
						if(splitStr.length > 1){
							if( splitStr[1].equals("0") ){
								monitorHistory.put("bandwidth", splitStr[1]);
							}else{
								double orgNum = Double.parseDouble(splitStr[1]) / 125000;
								double tmpNum = Math.round( orgNum*100d ) / 100d;
								monitorHistory.put("bandwidth", Double.toString(tmpNum) );								
							}
						}
						
						//해상도
						if(splitStr.length > 2){
							switch(splitStr[2]){
								case "720" : monitorHistory.put("resolution", "고화질"); break;
								case "480" : monitorHistory.put("resolution", "중화질"); break;
								case "240" : monitorHistory.put("resolution", "저화질"); break;
								default : 
							}
						}
						
						//모니터링 시간
						if(splitStr.length > 3){
							monitorHistory.put("monitorTime", splitStr[3]);
						}
						
						//세션아이디
						if(splitStr.length > 4){
							monitorHistory.put("sessionId", splitStr[4]);
						}
						
						//회선망
						if(splitStr.length > 5){
							if(splitStr[5].equals("100")){
								monitorHistory.put("accNetwork", "WIFI");
							}else if(splitStr[5].equals("200")){
								monitorHistory.put("accNetwork", "LTE");
							}
						}
						
						//단말기명
						if(splitStr.length > 6){
							monitorHistory.put("deviceName", splitStr[6]);
						}
						
						//OS 버전
						if(splitStr.length > 7){
							monitorHistory.put("osVer", splitStr[7]);
						}
					}
				}else if(monitorHistory.get("menu_code").equals("CJ11")){ //모니터링 시작 -> CJ11
					
					if(monitorHistory.get("contents") != null){
						
						contentsStr = monitorHistory.get("contents").toString();
						splitStr = contentsStr.split("\\|");
						monitorHistory.put("fd_memo", "모니터링 시작"); 
						
						//세션아이디
						if(splitStr.length > 0){
							monitorHistory.put("sessionId", splitStr[0]);
						}
						
						//회선망
						if(splitStr.length > 1){
							if(splitStr[1].equals("100")){
								monitorHistory.put("accNetwork", "WIFI");
							}else if(splitStr[1].equals("200")){
								monitorHistory.put("accNetwork", "LTE");
							}
						}
						
						//단말기명
						if(splitStr.length > 2){
							monitorHistory.put("deviceName", splitStr[2]);
						}
						
						//OS 버전
						if(splitStr.length > 3){
							monitorHistory.put("osVer", splitStr[3]);
						}
						
					}
				}else{
					//모니터링 시작코드(CJ11), 종료코드(CL03)가 아니면 원문을 출력한다.
					monitorHistory.put("fd_memo", monitorHistory.get("contents") ); 
				}
			}
		}
		
		Map<String, Object> nMap = new HashMap<String, Object>();
		nMap.put("selectColumn", "fd_type");
		List<Map<String, Object>> typeList = monitorHistoryService.getSelectMenuCodeList(nMap);
		model.addAttribute("typeList"	, typeList);
		
		if(!fd_type.equals("")){
			nMap.put("selectColumn", "fd_depth_1");
			nMap.put("pk_code", fd_type);
			List<Map<String, Object>> depth1_list = monitorHistoryService.getSelectMenuCodeList(nMap);
			model.addAttribute("depth1_list"	, depth1_list);
		}
		
		if(!fd_depth_1.equals("")){
			nMap.put("selectColumn", "fd_depth_2");
			nMap.put("pk_code", fd_type + fd_depth_1);
			List<Map<String, Object>> depth2_list = monitorHistoryService.getSelectMenuCodeList(nMap);
			model.addAttribute("depth2_list"	, depth2_list);
		}
		
		if(!fd_depth_2.equals("")){
			nMap.put("selectColumn", "fd_depth_3");
			nMap.put("pk_code", fd_type + fd_depth_1 + fd_depth_2);
			List<Map<String, Object>> depth3_list = monitorHistoryService.getSelectMenuCodeList(nMap);
			model.addAttribute("depth3_list"	, depth3_list);
		}
		
		PageNavi pageNavi = new PageNavi();	
		pageNavi.setAction("/inquiry/monitorHistoryList");
		pageNavi.setTotalCount(monitorHistoryCount);
		pageNavi.setPageSize(pageSize);
		pageNavi.setNowPage(page);
		pageNavi.setParameters("cpCode", cpCode);
		pageNavi.setParameters("searchColumn", searchColumn);
		pageNavi.setParameters("searchString", searchString);
		pageNavi.setParameters("searchDateType", searchDateType);
		pageNavi.setParameters("searchMonth", searchMonth);
		pageNavi.setParameters("searchStartDate", searchStartDate);
		pageNavi.setParameters("searchEndDate", searchEndDate);
		pageNavi.setParameters("fd_type", fd_type);
		pageNavi.setParameters("fd_depth_1", fd_depth_1);
		pageNavi.setParameters("fd_depth_2", fd_depth_2);
		pageNavi.setParameters("fd_depth_3", fd_depth_3);
		pageNavi.make();
		
		model.addAttribute("monitorHistoryList"	, monitorHistoryList);
		model.addAttribute("pageNavi"			, pageNavi);
		model.addAttribute("excelCount"			, excelCount);
		
		return "/inquiry/monitorHistoryList";
	}
	
	@RequestMapping(value="/monitorHistoryList", params="event=excel") //proc 가 excel 일때 excel 다운 
	public ModelAndView excelMonitorHistoryList(Model model, @RequestParam Map<String, Object> searchInfo) throws ParseException {
		
		String format_date	= ""; // am, pm으로 바꿀 것 
		String amPm_date	= "";
		
		String excelCount = apiEnv.getProperty("excelCount");
		
		String searchDateType	= searchInfo.get("searchDateType") != null ? searchInfo.get("searchDateType").toString().trim() : "";
		String searchMonth 		= searchInfo.get("searchMonth") != null ? searchInfo.get("searchMonth").toString().trim() : "";
		String searchStartDate 	= searchInfo.get("searchStartDate") != null ? searchInfo.get("searchStartDate").toString().trim() : "";
		String searchEndDate 	= searchInfo.get("searchEndDate") != null ? searchInfo.get("searchEndDate").toString().trim() : "";
		String fd_type		= searchInfo.get("fd_type") == null || searchInfo.get("fd_type").toString().trim().equals("") ? "" : searchInfo.get("fd_type").toString().trim();
		String fd_depth_1		= searchInfo.get("fd_depth_1") == null || searchInfo.get("fd_depth_1").toString().trim().equals("") ? "" : searchInfo.get("fd_depth_1").toString().trim();
		String fd_depth_2		= searchInfo.get("fd_depth_2") == null || searchInfo.get("fd_depth_2").toString().trim().equals("") ? "" : searchInfo.get("fd_depth_2").toString().trim();
		String fd_depth_3		= searchInfo.get("fd_depth_3") == null || searchInfo.get("fd_depth_3").toString().trim().equals("") ? "" : searchInfo.get("fd_depth_3").toString().trim();
		
		searchInfo.put("page"			, "");
		searchInfo.put("pageSize"		, "");
		searchInfo.put("limitCount"		, excelCount);
		
		SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");		
		if(searchDateType.equals("")) { 
			searchDateType ="range";
			Calendar cal = Calendar.getInstance();
			cal.add(cal.DATE, -7); 
			searchStartDate	= fmt.format(cal.getTime());
			searchEndDate	= fmt.format(new Date());
		}
		
		List<Map<String, Object>> monitorHistoryList = null;		

		Date sDate = null;
		Date eDate = null;
		SimpleDateFormat transFormat = new SimpleDateFormat("yyyyMMddhhmmssSSS");
		//SimpleDateFormat ft = new SimpleDateFormat("yyyyMMddHHmmss");
		try {
			if(!searchDateType.equals("")) {
				String startDate = null;
				String endDate = null;

				if(searchDateType.equals("range")) {
					if(!searchStartDate.equals("") && !searchEndDate.equals("")) {
						startDate 	= searchStartDate.replace("-", "") + "000000" + "000";
						endDate		= searchEndDate.replace("-", "") + "235959" + "999";
					} 
				} else {
					int year = Integer.parseInt(searchMonth.substring(0, 4));
					int month = Integer.parseInt(searchMonth.substring(4));
					
					Calendar calendar = Calendar.getInstance();
					calendar.set(year, month - 1, 1);
					
					int lastDate = calendar.getActualMaximum(Calendar.DATE);
					
					startDate 	= searchMonth + "01000000" + "000";
					endDate		= searchMonth + String.valueOf(lastDate) + "235959" + "999";
				}
				
				sDate = (Date) transFormat.parse(startDate);
				eDate = (Date) transFormat.parse(endDate);
				
				searchInfo.put("searchStartDate", sDate);
				searchInfo.put("searchEndDate", eDate);
				/*searchInfo.put("searchStartDate", startDate);
				searchInfo.put("searchEndDate", endDate);*/
				
				searchInfo.put("pk_code", fd_type + fd_depth_1 + fd_depth_2 + fd_depth_3);
				
				monitorHistoryList = monitorHistoryService.getList(searchInfo);
			}else{
				if(searchStartDate.equals("") && searchEndDate.equals("")) {
					/*Date startDate = null, endDate = null;
					startDate 	= ft.parse(searchStartDate.replace("-", "") + "000000");
					endDate		= ft.parse(searchEndDate.replace("-", "") + "235959");*/
					
					sDate = (Date) transFormat.parse(searchStartDate);
					eDate = (Date) transFormat.parse(searchEndDate);
					
					searchInfo.put("searchStartDate", sDate);
					searchInfo.put("searchEndDate", eDate);				
					/*searchInfo.put("searchStartDate", searchStartDate);
					searchInfo.put("searchEndDate", searchEndDate);*/
				}
				

			}
		} catch(Exception e) {
			searchInfo.put("searchStartDate", null);
			searchInfo.put("searchEndDate", null);			
		}
		

		if(monitorHistoryList != null){
			String[] splitStr;
			String contentsStr;
			
			for(Map<String, Object> monitorHistory : monitorHistoryList){		
				//am, pm으로 
				format_date					= monitorHistory.get("cret_dt").toString().trim();
				format_date					= format_date.substring(0, 17);
				SimpleDateFormat format		= new SimpleDateFormat("yyyy-MM-dd HH:mm");
				Date getDate				= format.parse(format_date);
				SimpleDateFormat new_format	= new SimpleDateFormat("yyyy.MM.dd ahh:mm", Locale.US);
				amPm_date					= new_format.format(getDate);
				monitorHistory.put("amPmDate", amPm_date);
				
				//전화번호 마스킹
				String tel_no 	= monitorHistory.get("tel_no").toString().trim();
				tel_no			= tel_no.substring(0, tel_no.length()-4) + "****";
				monitorHistory.put("tel_no", tel_no);
	
				if(monitorHistory.get("dev_nm")==null || monitorHistory.get("dev_nm")==""){ 
					monitorHistory.put("dev_nm", "-"); 
				}
				if(monitorHistory.get("spot_dev_id")==null || monitorHistory.get("spot_dev_id")==""){ 
					monitorHistory.put("spot_dev_id", "-"); 
				}
				if(monitorHistory.get("fd_depth_3")==null || monitorHistory.get("fd_depth_3")==""){ 
					monitorHistory.put("fd_depth_3", "-"); 
				}	
				
				//초기화
				monitorHistory.put("bandwidth", "");
				monitorHistory.put("resolution", "");
				monitorHistory.put("monitorTime", "");
				monitorHistory.put("sessionId", "");
				monitorHistory.put("accNetwork", "");
				monitorHistory.put("deviceName", "");
				monitorHistory.put("osVer", "");
				
				if(monitorHistory.get("menu_code").equals("CL03")){ //모니터링 종료 -> CL03
					if(monitorHistory.get("contents") != null){
						
						contentsStr = monitorHistory.get("contents").toString();
						splitStr = contentsStr.split("\\|");
						
						//대역폭 
						if(splitStr.length > 1){
							if( splitStr[1].equals("0") ){
								monitorHistory.put("bandwidth", splitStr[1]);
							}else{
								double orgNum = Double.parseDouble(splitStr[1]) / 125000;
								double tmpNum = Math.round( orgNum*100d ) / 100d;
								monitorHistory.put("bandwidth", Double.toString(tmpNum) );								
							}
						}
						
						//해상도
						if(splitStr.length > 2){
							switch(splitStr[2]){
								case "720" : monitorHistory.put("resolution", "고화질"); break;
								case "480" : monitorHistory.put("resolution", "중화질"); break;
								case "240" : monitorHistory.put("resolution", "저화질"); break;
								default : 
							}
						}
						
						//모니터링 시간
						if(splitStr.length > 3){
							monitorHistory.put("monitorTime", splitStr[3]);
						}
						
						//세션아이디
						if(splitStr.length > 4){
							monitorHistory.put("sessionId", splitStr[4]);
						}
						
						//회선망
						if(splitStr.length > 5){
							if(splitStr[5].equals("100")){
								monitorHistory.put("accNetwork", "WIFI");
							}else if(splitStr[5].equals("200")){
								monitorHistory.put("accNetwork", "LTE");
							}
						}
						
						//단말기명
						if(splitStr.length > 6){
							monitorHistory.put("deviceName", splitStr[6]);
						}
						
						//OS 버전
						if(splitStr.length > 7){
							monitorHistory.put("osVer", splitStr[7]);
						}
					}
				}else if(monitorHistory.get("menu_code").equals("CJ11")){ //모니터링 시작 -> CJ11
					
					if(monitorHistory.get("contents") != null){
						
						contentsStr = monitorHistory.get("contents").toString();
						splitStr = contentsStr.split("\\|");
						monitorHistory.put("fd_memo", "모니터링 시작"); 
						
						//세션아이디
						if(splitStr.length > 0){
							monitorHistory.put("sessionId", splitStr[0]);
						}
						
						//회선망
						if(splitStr.length > 1){
							if(splitStr[1].equals("100")){
								monitorHistory.put("accNetwork", "WIFI");
							}else if(splitStr[1].equals("200")){
								monitorHistory.put("accNetwork", "LTE");
							}
						}
						
						//단말기명
						if(splitStr.length > 2){
							monitorHistory.put("deviceName", splitStr[2]);
						}
						
						//OS 버전
						if(splitStr.length > 3){
							monitorHistory.put("osVer", splitStr[3]);
						}
						
					}
				}else{
					//모니터링 시작코드(CJ11), 종료코드(CL03)가 아니면 원문을 출력한다.
					monitorHistory.put("fd_memo", monitorHistory.get("contents") ); 
				}
			}
		}
		
		
		ModelAndView mav = new ModelAndView(new JxlsExcelViewMonitorHistoryList());
		mav.addObject("monitorHistoryList", monitorHistoryList);
		
		return mav;
	}
	
	@ResponseBody
	@RequestMapping("/getSelectMenuCodeList")
	public Map<String, Object> getSelectMenuCodeList(@RequestParam Map<String, Object> searchInfo) {
		Map<String, Object> result = new HashMap<>();
		
		try { 
			List<Map<String, Object>> list = monitorHistoryService.getSelectMenuCodeList(searchInfo);
			result.put("code", 200);
			result.put("msg", "success");
			result.put("list", list);
		} catch(Exception e) {
			result.put("code", 500);
			result.put("msg", "server error : " + e.getMessage());
		}
		return result;
	}
}
