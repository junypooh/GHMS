package com.kt.giga.home.cms.manage.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.servlet.http.*;

import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.beans.factory.annotation.*;

import com.kt.giga.home.cms.util.*;
import com.kt.giga.home.cms.common.domain.*;
import com.kt.giga.home.cms.common.service.*;
import com.kt.giga.home.cms.manage.service.*;

@Controller
@RequestMapping("/manage")
public class MonitorTimeController {
	
	@Autowired
	private HttpServletRequest request;
	
	@Autowired
	private APIEnv apiEnv; 
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private MonitorTimeService monitorTimeService;
	
	@Autowired
	private OpenAPICallService openAPICallService;
	
	@RequestMapping("/monitorTimeList")
	public String getMonitorTimeList(Model model,@RequestParam Map<String, Object> searchInfo) throws ParseException {
		String format_date = ""; // am, pm으로 바꿀 것 
		String amPm_date = "";

		LoginInfo loginInfo = (LoginInfo)request.getAttribute("loginInfo");
		
		int page, pageSize = 10;		
		page = searchInfo.get("page") == null || searchInfo.get("page").toString().trim().equals("") ? 1 : Integer.parseInt(searchInfo.get("page").toString().trim());

		searchInfo.put("id"			, loginInfo.getId());
		searchInfo.put("page"		, page);
		searchInfo.put("pageSize"	, pageSize);
		
		int monitorCount = monitorTimeService.getCount(searchInfo);
		List<Map<String, Object>> monitorList = monitorTimeService.getList(searchInfo);		
		
		for(Map<String, Object> monitor : monitorList){		
			format_date = monitor.get("updateDate").toString().trim();
			format_date = format_date.substring(0, 17);
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			Date getDate = format.parse(format_date);
			SimpleDateFormat new_format = new SimpleDateFormat("yyyy.MM.dd ahh:mm", Locale.US);
			amPm_date= new_format.format(getDate);
			monitor.put("amPmDate", amPm_date);
		}
		
		PageNavi pageNavi = new PageNavi();
		
		pageNavi.setAction("/manage/monitorTimeList");
		pageNavi.setTotalCount(monitorCount);
		pageNavi.setPageSize(pageSize);
		pageNavi.setNowPage(page);
		pageNavi.make();
		
		model.addAttribute("noticeList"	, monitorList);
		model.addAttribute("pageNavi"	, pageNavi);
		model.addAttribute("loginInfo", loginInfo);
		
		return "/manage/monitorTimeList";
	}
	
	@RequestMapping("/monitorTimeRegister")
	public String verFrmwrRegisterForm(Model model) {
		LoginInfo loginInfo = (LoginInfo)request.getAttribute("loginInfo");
		
		model.addAttribute("loginInfo", loginInfo);
		return "/manage/monitorTimeRegister";
	}
	
	@RequestMapping(value="/monitorTimeRegister", params="method=register")
	public String verFrmwrRegister(Model model, @RequestParam Map<String, Object> monitor) {
		
		String script = "alert('%s');";
		LoginInfo loginInfo = (LoginInfo)request.getAttribute("loginInfo");
		
		//input: char, db : int 
		monitor.put("regDate", new Date());
		monitor.put("modifyDate", new Date()); 
		monitor.put("interval", Integer.parseInt(monitor.get("interval").toString()));
		monitor.put("writerId", loginInfo.getId());

		// 제한없음 null 처리 
		if(monitor.get("unlimitedYN") == null) {
			monitor.put("unlimitedYN", "N");
		}

		try {
			monitorTimeService.register(monitor);
			script  = String.format(script, "저장이 완료되었습니다.");
			script += "location.href = '/manage/monitorTimeList';";  			
		} catch(Exception e) {
			model.addAttribute("monitor", monitor);
			script  = String.format(script, "등록 과정중 오류가 발생하였습니다.\\n다시 시도하여 주십시오.");
		}
		
		model.addAttribute("script", script);
		
		return "/manage/monitorTimeRegister";
	}

}
