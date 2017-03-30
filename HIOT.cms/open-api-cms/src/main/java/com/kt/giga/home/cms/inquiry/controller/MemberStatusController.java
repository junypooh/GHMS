package com.kt.giga.home.cms.inquiry.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.kt.giga.home.cms.inquiry.service.MemberStatusService;
import com.kt.giga.home.cms.util.PageNavi;


@Controller
@RequestMapping("/inquiry")
public class MemberStatusController {
	
	@Autowired
	private HttpServletRequest request;

	@Autowired
	private MemberStatusService memberStatusService; 
		
	@RequestMapping("/memberStatusList")
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
		
		int memberStatusCount = memberStatusService.getCount(searchInfo);
		List<Map<String, Object>> memberStatusList = memberStatusService.getList(searchInfo);
		
		for(Map<String, Object> memberStatus : memberStatusList){		
			//am, pm으로 
			format_date= memberStatus.get("regDate").toString().trim();
			format_date= format_date.substring(0, 17);
			SimpleDateFormat format= new SimpleDateFormat("yyyy-MM-dd HH:mm");
			Date getDate= format.parse(format_date);
			SimpleDateFormat new_format= new SimpleDateFormat("yyyy.MM.dd ahh:mm", Locale.US);
			amPm_date= new_format.format(getDate);
			memberStatus.put("amPmDate", amPm_date);
		}
		PageNavi pageNavi = new PageNavi();
		
		pageNavi.setAction("/inquiry/memberStatusService");
		pageNavi.setTotalCount(memberStatusCount);
		pageNavi.setPageSize(pageSize);
		pageNavi.setNowPage(page);
		pageNavi.setParameters("cpCode", cpCode);
		pageNavi.setParameters("searchColumn", searchColumn);
		pageNavi.setParameters("searchString", searchString);
		pageNavi.make();
		
		model.addAttribute("memberStatusList"	, memberStatusList);
		model.addAttribute("pageNavi"	, pageNavi);
		
		return "/inquiry/memberStatusList";
	}
}
