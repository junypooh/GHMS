package com.kt.giga.home.cms.customer.controller;

import java.text.SimpleDateFormat;
import java.util.*;

import javax.servlet.http.*;

import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.*;

import com.kt.giga.home.cms.util.*;
import com.kt.giga.home.cms.common.domain.Code;
import com.kt.giga.home.cms.common.domain.LoginInfo;
import com.kt.giga.home.cms.common.service.CodeService;
import com.kt.giga.home.cms.customer.service.*;

@Controller
@RequestMapping("/customer")
public class PmNoticeController {
	
	@Autowired
	private HttpServletRequest request;
	
	@Autowired
	private PmNoticeService pmNoticeService;
	
	@Autowired
	private CodeService codeService;
	
	@ResponseBody 
	@RequestMapping("/removePmNotice")
	public Map<String, Object> removePmNotice(@RequestParam Map<String, Object> noticeList) {

		Map<String, Object> result = new HashMap<>(); 
		Map<String, Object> notice = new HashMap<>(); 		
		
		try {
			String[] pkList = noticeList.get("pkList").toString().split(",");
			for(String pkStr : pkList) {
				notice.put("pk", Integer.valueOf(pkStr));
				pmNoticeService.remove(notice);
			}	
			result.put("code", 200);
			result.put("msg", "success");
		} catch(Exception e) {
			result.put("code", 500);
			result.put("msg", "server error : " + e.getMessage());
		}
		
		return result;
	}	
	
	@ResponseBody
	@RequestMapping("/updatePmNoticeOpenYN")
	public Map<String, Object> updatePmNoticeOpenYN(@RequestParam Map<String, Object> notice) {
		
		Map<String, Object> result = new HashMap<>();
		// 리스트 페이지에서 Y -> N 을 할 때는 openYN을 업데이트 하면서 아무런 메시지가 필요 없다. 
		// but N -> Y 일때는 중복인 시간을 확인하고 해주어야 한다. 
		// notice 안에는 targetCpCode, pk, openYN, 시간 이 존재한다.
		
		// System.out.println("updatePmNoticeOpenYN의 notice : " + notice );
		// System.out.println("pmNoticeService.checkDateCount(notice) :" + pmNoticeService.checkDateCount(notice));
		try{
			if(   ( notice.get("openYN").toString().trim().equals("N") ) // OPENYN이 Y이면 N으로 변하는데 중복체크가 필요없다.  
		       || ( notice.get("openYN").toString().trim().equals("Y") && pmNoticeService.checkDateCount(notice) == 200)){
				
				// checkDateCount가 0이면 중복 되는 날짜가 없는 것 
				pmNoticeService.updateOpenYN(notice);
				result.put("code", 200); 
				result.put("msg", "success");
				
				//openYN이 N -> Y 이고 중복 되는 날짜가 있을 때 
			}else if(notice.get("openYN").toString().trim().equals("Y") && pmNoticeService.checkDateCount(notice) == 201){
				result.put("code", 201);
				result.put("msg", "overlap");
			}
		}catch(Exception ex){
			ex.printStackTrace();
			result.put("code", 500); 
			result.put("msg", "server error"); 
		}
		
		return result; 
	}
	
	@RequestMapping("/pmNoticeList")
	public String getPmNoticeList(Model model, @RequestParam Map<String, Object> searchInfo) {
		String searchColumn	= searchInfo.get("searchColumn") == null || searchInfo.get("searchColumn").toString().trim().equals("") ? "" : searchInfo.get("searchColumn").toString().trim();
		String searchString	= searchInfo.get("searchString") == null || searchInfo.get("searchString").toString().trim().equals("") ? "" : searchInfo.get("searchString").toString().trim();
		String cpCode	= searchInfo.get("cpCode") == null || searchInfo.get("cpCode").toString().trim().equals("") ? "" : searchInfo.get("cpCode").toString().trim();
		String openYnSelect	= searchInfo.get("openYnSelect") != null ? searchInfo.get("openYnSelect").toString().trim() : "";
		
		
		int page, pageSize = 10;		
		page = searchInfo.get("page") == null || searchInfo.get("page").toString().trim().equals("") ? 1 : Integer.parseInt(searchInfo.get("page").toString().trim());
		
		LoginInfo loginInfo = (LoginInfo)request.getAttribute("loginInfo");

		searchInfo.put("id"				, loginInfo.getId());
		searchInfo.put("cpCode"			, cpCode);
		searchInfo.put("page"			, page);
		searchInfo.put("pageSize"		, pageSize);
		searchInfo.put("searchString"	, searchString);
		
		int noticeCount = pmNoticeService.getCount(searchInfo);
		List<Map<String, Object>> noticeList = pmNoticeService.getList(searchInfo);
		
		SimpleDateFormat sdf= new SimpleDateFormat("yyyyMMddHHmmss");
		
		for(Map<String, Object> notice : noticeList){

			if(notice.get("endTime") != null){
				long toDay = Long.parseLong(sdf.format(new Date()));
				long getDay = Long.parseLong(notice.get("endTime").toString());
				
				if(getDay != 0 && toDay > getDay){
					notice.put("noticeYN", "N"); 
				}else{
					notice.put("noticeYN", "Y");
				}
			}
		}
		
		List<Code> serviceList = codeService.getList("1200");
		
		Map<String, String> serviceMap = new HashMap<>();
		for(Code service : serviceList) {
			serviceMap.put(service.getName(), service.getMemo());
		}	
		for(Map<String, Object> notice : noticeList) {
			notice.put("cpName", serviceMap.get(notice.get("cpCode")).toString());
		}
		

		PageNavi pageNavi = new PageNavi();
		
		pageNavi.setAction("/customer/pmNoticeList");
		pageNavi.setTotalCount(noticeCount);
		pageNavi.setPageSize(pageSize);
		pageNavi.setNowPage(page);
		pageNavi.setParameters("openYnSelect", openYnSelect);
		pageNavi.setParameters("searchColumn", searchColumn);
		pageNavi.setParameters("searchString", searchString);
		pageNavi.setParameters("cpCode", cpCode);
		pageNavi.make();
		
		model.addAttribute("noticeList"	, noticeList);
		model.addAttribute("pageNavi"	, pageNavi);
		
		return "/customer/pmNoticeList";
	}
	
	@RequestMapping("/pmNoticeRegister")
	public String pmNoticeRegisterForm(Model model) {
		
		return "/customer/pmNoticeRegister";
	}
	
	@RequestMapping(value="/pmNoticeRegister", params="method=register")
	public String pmNoticeRegister(Model model, @RequestParam Map<String, Object> notice) {
		LoginInfo loginInfo = (LoginInfo)request.getAttribute("loginInfo");
		String script = "alert('%s');";
		String writerIp = request.getRemoteAddr();
		notice.put("writerId", loginInfo.getId());
		notice.put("writerIp", writerIp);
		notice.put("regDate", new Date());
		
		//예약 설정이 체크 안되어있을때
		if(notice.get("reserveChk") != null){
			notice.put("reserveYN", "N"); // 예약 설정이 체크 안되어있음
			if(notice.get("start_date") != null && notice.get("s_hour") != null && notice.get("s_minute") != null){
	
				String start = notice.get("start_date").toString();
				String s_hour = notice.get("s_hour").toString();
				String s_minute = notice.get("s_minute").toString();
				
				if(Integer.parseInt(s_hour) < 10){ 
					s_hour = "0" + s_hour; 
				}
				if(Integer.parseInt(s_minute) < 10){ 
					s_minute = "0" + s_minute; 
				}				
				String start_date = start.replace("-", "") + s_hour + s_minute + "00";
				notice.put("start_date", start_date);
				
			}else{ 
				notice.put("reserveYN", "Y"); // 예약 설정이 체크 되어있음
				// 시작일은 0001년 01월 01일 01시 01분 01초 
				notice.put("start_date", "00010101010101");
			}
			
			//종료일 지정안함이 체크가 안되어있을때
			if(notice.get("endYN") == null){ 
				if(notice.get("end_date") != null && notice.get("e_hour") != null && notice.get("e_minute") != null){
					
					String end = notice.get("end_date").toString();
					String e_hour = notice.get("e_hour").toString();
					String e_minute = notice.get("e_minute").toString();
					
					if(Integer.parseInt(e_hour) < 10){ 
						e_hour = "0" + e_hour;
					}
					if(Integer.parseInt(e_minute) < 10){ 
						e_minute = "0" + e_minute;
					}
					
					String end_date = end.replace("-", "") + e_hour + e_minute + "00";
					notice.put("end_date", end_date);
				}else{ 
					notice.put("end_date", "99991230115959");
				}
				
			}else{
				notice.put("end_date", "99991230115959"); // 종료일 지정 안함이 체크 되었을 때 
			}
		}else{
			notice.put("start_date", "00010101010101");
			notice.put("end_date", "99991230115959"); 
		}
		// 날짜 중복 체크  script타고 메인으로 넘어가면서 중복일경우 checkDateCount가 0이 아닐경우 alert띄워주면서 노출여부를 Y에서 N으로 변경
		int checkDate = pmNoticeService.checkDateCount(notice);
		
		if(checkDate == 201 && notice.get("openYN").toString().trim().equals("Y")){
			notice.put("openYN", "N");
			script = String.format(script, "중복 되는 날짜가 존재 합니다. 비 노출 상태로 등록 처리 됩니다.");
			script += "location.href = '/customer/pmNoticeList';"; 	
			
			pmNoticeService.register(notice);
			model.addAttribute("script", script);
			
			return "/customer/pmNoticeRegister";
		}
		
		try {
			pmNoticeService.register(notice);
			script  = String.format(script, "정상적으로 등록 처리 되었습니다.");
			script += "location.href = '/customer/pmNoticeList';"; 			
		} catch(Exception e) {
			model.addAttribute("notice", notice);
			script  = String.format(script, "등록 과정중 오류가 발생하였습니다.\\n다시 시도하여 주십시오.");
			e.printStackTrace();
		}
		
		model.addAttribute("script", script);
		
		return "/customer/pmNoticeRegister";
	}
	
	@RequestMapping("/pmNoticeModify")
	public String pmNoticeModifyForm(Model model, @RequestParam Map<String, Object> searchInfo) {
		
		LoginInfo loginInfo = (LoginInfo)request.getAttribute("loginInfo");
		searchInfo.put("id", loginInfo.getId());
		Map<String, Object> notice = pmNoticeService.get(searchInfo);
		String cpName = codeService.getServiceName(notice.get("cpCode").toString());
		notice.put("cpName", cpName);

		String reserveChk = "Y";
		String endYN = "N";
		
		//시작일
		if( !notice.get("startTime").toString().trim().equals("00010101010101") ){
			String startTime = notice.get("startTime").toString().trim();
			String s_year = startTime.substring(0, 4); 
			String s_month = startTime.substring(4, 6); 
			String s_day = startTime.substring(6, 8);
			String s_hour = startTime.substring(8,10);
			String s_minute = startTime.substring(10, 12);
			
			notice.put("startTime", s_year + "-" + s_month + "-" + s_day);
			notice.put("s_hour", Integer.parseInt(s_hour));
			notice.put("s_minute", Integer.parseInt(s_minute));
			
			//종료일
			if( !notice.get("endTime").toString().trim().equals("99991212121212") ){
				String endTime= notice.get("endTime").toString();
				String e_year= endTime.substring(0, 4); 
				String e_month= endTime.substring(4, 6); 
				String e_day= endTime.substring(6, 8);
				String e_hour= endTime.substring(8,10);
				String e_minute= endTime.substring(10, 12);

				notice.put("endTime", e_year + "-" + e_month + "-" + e_day);
				notice.put("e_hour", Integer.parseInt(e_hour));
				notice.put("e_minute", Integer.parseInt(e_minute));
			}else{
				endYN = "Y";
			}
			
		}else{ 
			reserveChk = "N";
			endYN = "Y";
		}
		
		//예약 설정 YN
		notice.put("reserveChk", reserveChk);
		
		if(notice.get("startTime").equals("0001-01-01")){
			notice.put("startTime", null);
		}
		if(notice.get("endTime").equals("9999-12-12")){
			notice.put("endTime", null);
		}
		//종료일 지정안함 YN
		notice.put("endYN", endYN);
		model.addAttribute("notice", notice);
		
		return "/customer/pmNoticeModify";
	}
	
	@RequestMapping(value="/pmNoticeModify" , params="method=modify")
	public String pmNoticeModify(Model model, @RequestParam Map<String, Object> notice) {
		LoginInfo loginInfo = (LoginInfo)request.getAttribute("loginInfo");	
		System.out.println("notice :" + notice);
		String page	= notice.get("page") == null || notice.get("page").toString().trim().equals("") ? "1" : notice.get("page").toString().trim();
		String searchColumn	= 	notice.get("searchColumn") == null || notice.get("searchColumn").toString().trim().equals("") ? "" : notice.get("searchColumn").toString().trim();
		String searchString	= 	notice.get("searchString") == null || notice.get("searchString").toString().trim().equals("") ? "" : notice.get("searchString").toString().trim();
		String cpCode	= 		notice.get("cpCode") == null || notice.get("cpCode").toString().trim().equals("") ? "" : notice.get("cpCode").toString().trim();
		String openYnSelect	= 	notice.get("openYnSelect") == null || notice.get("openYnSelect").toString().trim().equals("") ? "" : notice.get("openYnSelect").toString().trim();
		
		String script = "alert('%s');";
		String modifierIp = request.getRemoteAddr();
		
		notice.put("modifyId", loginInfo.getId());
		notice.put("modifierIp", modifierIp);
		notice.put("updateDate", new Date());
		
		// 날짜 가공을 위한 변수 선언
		String start_date ="";
		String end_date ="";
		
		if( notice.get("start_date") == null && notice.get("s_hour") == null && notice.get("s_minute") == null ){	
			notice.put("start_date", "00010101010101");
			notice.put("end_date", "99991212121212"); 
			
		}else{
			if(notice.get("end_date") == null){			
				String start = notice.get("start_date").toString();
				String s_hour = notice.get("s_hour").toString();
				String s_minute = notice.get("s_minute").toString();
				
				if(Integer.parseInt(s_hour) < 10){						
					s_hour = "0" + s_hour; 
				}					
				if(Integer.parseInt(s_minute) < 10){						
					s_minute = "0" + s_minute;
				}	
				start_date = start.replace("-", "") + s_hour + s_minute + "00";
					
				notice.put("start_date", start_date);
				notice.put("end_date", "99991212121212");
					
				}else{
					
					String start = notice.get("start_date").toString();
					String s_hour = notice.get("s_hour").toString();
					String s_minute = notice.get("s_minute").toString();
					
					String end = notice.get("end_date").toString();
					String e_hour = notice.get("e_hour").toString();
					String e_minute = notice.get("e_minute").toString();
					
					if(Integer.parseInt(s_hour) < 10){						
						s_hour = "0" + s_hour; 
					}
					if(Integer.parseInt(s_minute) < 10){		
						s_minute = "0" + s_minute;
					}
					if(Integer.parseInt(e_hour) < 10){ 	
						e_hour = "0" + e_hour; 
					}
					if(Integer.parseInt(e_minute) < 10){ 						
						e_minute = "0" + e_minute; 
					}
					
					start_date = start.replace("-", "") + s_hour + s_minute + "00";
					end_date = end.replace("-", "") + e_hour + e_minute + "00";
					
					notice.put("start_date", start_date);
					notice.put("end_date", end_date);
				}
		}
		// 날짜 중복 체크  script타고 메인으로 넘어가면서 중복일경우 checkDateCount가 0이 아닐경우 alert띄워주면서 노출여부를 Y에서 N으로 변경 
		int checkDate = pmNoticeService.checkDateCount(notice);
		if(checkDate == 201 && notice.get("openYN").toString().trim().equals("Y")){
			notice.put("openYN", "N");
			script = String.format(script, "중복 되는 날짜가 존재 합니다. 비 노출 상태로 수정 처리 됩니다.");
			script += "location.href = '/customer/pmNoticeList?&page="
															+ page  
															+ "&searchColumn=" + searchColumn 
															+ "&searchString=" + searchString 
															+ "&cpCode=" + cpCode 
															+ "&openYnSelect=" + openYnSelect
															+ "';";
			pmNoticeService.modify(notice);
			model.addAttribute("script", script);	
			return "/customer/pmNoticeModify";
		}

		try {
			pmNoticeService.modify(notice);
			script  = String.format(script, "정상적으로 수정 처리 되었습니다.");
			script += String.format("location.href = '/customer/pmNoticeList?&page=" + page  
											+ "&searchColumn=" + searchColumn 
											+ "&searchString=" + searchString 
											+ "&cpCode=" + cpCode 
											+ "&openYnSelect=" + openYnSelect
											+ "';");
		} catch(Exception e) {
			e.printStackTrace();
			model.addAttribute("notice", notice);
			script  = String.format(script, "수정 과정중 오류가 발생하였습니다.\\n다시 시도하여 주십시오.");
		}
		
		model.addAttribute("script", script);
		
		return "/customer/pmNoticeModify";		
	}
}
