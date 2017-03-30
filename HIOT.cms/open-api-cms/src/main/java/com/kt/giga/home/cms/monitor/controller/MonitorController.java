package com.kt.giga.home.cms.monitor.controller;
//************************* 미사용 컨트롤러(삭제예정) *************************  // 

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.servlet.http.*;

import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.*;

import com.kt.giga.home.cms.util.*;
import com.kt.giga.home.cms.monitor.service.*;

@Controller
@RequestMapping("/monitor")
public class MonitorController {
	
	@Autowired
	private HttpServletRequest request;

	@Autowired
	private MonitorService monitorService; 
		
	@RequestMapping("/monitorList")
	public String getSignalStrengthList(Model model,@RequestParam Map<String, Object> searchInfo) throws ParseException{
		int page, pageSize = 10;
		String format_date= ""; // am, pm으로 바꿀 것 
		String amPm_date= "";
		page = searchInfo.get("page") == null || searchInfo.get("page").toString().trim().equals("") ? 1 : Integer.parseInt(searchInfo.get("page").toString().trim());
		String searchString	= searchInfo.get("searchString") != null ? searchInfo.get("searchString").toString().trim() : "";
		String searchColumn	= searchInfo.get("searchColumn") != null ? searchInfo.get("searchColumn").toString().trim() : "";
		String cpCode	= searchInfo.get("cpCode") == null || searchInfo.get("cpCode").toString().trim().equals("") ? "" : searchInfo.get("cpCode").toString().trim();
		
		searchInfo.put("cpCode"			, cpCode);
		searchInfo.put("page"			, page);
		searchInfo.put("pageSize"		, pageSize);
		searchInfo.put("searchString"	, searchString);
		
		int noticeCount = monitorService.getCount(searchInfo);
		List<Map<String, Object>> noticeList = monitorService.getList(searchInfo);
		
		for(Map<String, Object> notice : noticeList){		
			//am, pm으로 
			format_date= notice.get("regDate").toString().trim();
			format_date= format_date.substring(0, 17);
			SimpleDateFormat format= new SimpleDateFormat("yyyy-MM-dd HH:mm");
			Date getDate= format.parse(format_date);
			SimpleDateFormat new_format= new SimpleDateFormat("yyyy.MM.dd ahh:mm", Locale.US);
			amPm_date= new_format.format(getDate);
			notice.put("amPmDate", amPm_date);
		}
		PageNavi pageNavi = new PageNavi();
		
		pageNavi.setAction("/monitor/monitorList");
		pageNavi.setTotalCount(noticeCount);
		pageNavi.setPageSize(pageSize);
		pageNavi.setNowPage(page);
		pageNavi.setParameters("cpCode", cpCode);
		pageNavi.setParameters("searchColumn", searchColumn);
		pageNavi.setParameters("searchString", searchString);
		pageNavi.make();
		
		model.addAttribute("noticeList"	, noticeList);
		model.addAttribute("pageNavi"	, pageNavi);
		
		return "/monitor/monitorList";
	}
	
	@RequestMapping("/monitorDetail")
	public String monitorDetail(Model model,@RequestParam Map<String, Object> searchInfo) {
		//model.addAttribute("monitorList"	, monitorList);
		
		return "/monitor/monitorDetail";
	}
}
