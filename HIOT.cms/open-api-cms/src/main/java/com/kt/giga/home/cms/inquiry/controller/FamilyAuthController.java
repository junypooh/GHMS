/*******************************************************************************
 * Copyright(c) 2015 KT. All rights reserved.
 * This software is the proprietary information of KT.
 *******************************************************************************/
package com.kt.giga.home.cms.inquiry.controller;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.kt.giga.home.cms.inquiry.service.FamilyAuthService;
import com.kt.giga.home.cms.util.PageNavi;

/**
 * 
 * @author junypooh ( kyungjun.park@ceinside.co.kr )
 * @since 2015. 8. 4.
 */
@Controller
@RequestMapping("/inquiry")
public class FamilyAuthController {
	
	@Autowired
	private FamilyAuthService familyAuthService;

	
	@RequestMapping("/familyAuthList")
	public String getFamilyAuthList(Model model, @RequestParam Map<String, Object> searchInfo) {
		
		int page, pageSize = 5;
		
		page = searchInfo.get("page") == null || searchInfo.get("page").toString().trim().equals("") ? 1 : Integer.parseInt(searchInfo.get("page").toString().trim());
		String searchString	= searchInfo.get("searchString") != null ? searchInfo.get("searchString").toString().trim() : "***---***";	// 첫번째 화면에서 아무것도 보이지 않도록 하기 위해
		String searchColumn	= searchInfo.get("searchColumn") != null ? searchInfo.get("searchColumn").toString().trim() : "mbrId";		// 첫번째 화면에서 아무것도 보이지 않도록 함
		String searchDateType	= searchInfo.get("searchDateType") != null ? searchInfo.get("searchDateType").toString().trim() : "";
		String searchMonth 	= searchInfo.get("searchMonth") != null ? searchInfo.get("searchMonth").toString().trim() : "";
		String searchStartDate 	= searchInfo.get("searchStartDate") != null ? searchInfo.get("searchStartDate").toString().trim() : "";
		String searchEndDate 	= searchInfo.get("searchEndDate") != null ? searchInfo.get("searchEndDate").toString().trim() : "";
		String cpCode	= searchInfo.get("cpCode") == null || searchInfo.get("cpCode").toString().trim().equals("") ? "" : searchInfo.get("cpCode").toString().trim();
		
		if( searchInfo.isEmpty() )
		{
			 Calendar cal = Calendar.getInstance();
		     cal.add(Calendar.DATE, -1);
			 Date startDate = cal.getTime();
			 Date endDate = new Date();
			 SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			 searchStartDate = sdf.format(startDate);
			 searchEndDate = sdf.format(endDate);
			 searchDateType = "range";
		}
		
		searchInfo.put("page"			, page);
		searchInfo.put("pageSize"		, pageSize);
		
		// 첫번째페이지에서 검색결과 없이 나오기 위해서
		searchInfo.put("searchString"	, searchString);
		searchInfo.put("searchColumn"	, searchColumn);
		
		try {
			if(!searchDateType.equals("")) {
				Date startDate = null, endDate = null;
				SimpleDateFormat ft = new SimpleDateFormat("yyyyMMddHHmmssSSS");
				
				if(searchDateType.equals("range")) {
					if(!searchStartDate.equals("") && !searchEndDate.equals("")) {
						startDate 	= ft.parse(searchStartDate.replace("-", "") + "000000" + "000");
						endDate		= ft.parse(searchEndDate.replace("-", "") + "235959" + "999");
					} 
					//System.out.println("1 startDate = " + startDate);
					//System.out.println("1 endDate = " + endDate);
				} else {
					int year = Integer.parseInt(searchMonth.substring(0, 4));
					int month = Integer.parseInt(searchMonth.substring(4));
					
					Calendar calendar = Calendar.getInstance();
					calendar.set(year, month - 1, 1);
					
					int lastDate = calendar.getActualMaximum(Calendar.DATE);
					
					startDate 	= ft.parse(searchMonth + "01000000" + "000");
					endDate		= ft.parse(searchMonth + String.valueOf(lastDate) + "235959" + "999");
					
				}
				
				searchInfo.put("searchStartDate", startDate);
				searchInfo.put("searchEndDate", endDate);	
			}			
		} catch(Exception e) {
			searchInfo.put("searchStartDate", null);
			searchInfo.put("searchEndDate", null);			
		}
		
		int familyAuthCount = familyAuthService.getCount(searchInfo);
		List<Map<String, Object>> familyAuthList = familyAuthService.getList(searchInfo);
		
		PageNavi pageNavi = new PageNavi();
		
		pageNavi.setAction("/inquiry/familyAuthList");
		pageNavi.setTotalCount(familyAuthCount);
		pageNavi.setPageSize(pageSize);
		pageNavi.setNowPage(page);
		pageNavi.setParameters("cpCode", cpCode);
		pageNavi.setParameters("searchColumn", searchColumn);
		pageNavi.setParameters("searchString", searchString);
		pageNavi.setParameters("searchDateType", searchDateType);
		pageNavi.setParameters("searchStartDate", searchStartDate);
		pageNavi.setParameters("searchEndDate", searchEndDate);
		pageNavi.setParameters("searchMonth", searchMonth);
		pageNavi.make();
		
		model.addAttribute("familyAuthList"		, familyAuthList);
		model.addAttribute("pageNavi"		, pageNavi);
		
		return "/inquiry/familyAuthList";
	}

	
	@RequestMapping("/familyAuthDetail")
	public String getFamilyAuthDetail(Model model, @RequestParam Map<String, Object> searchInfo) {
		
		Map<String, Object> detail = familyAuthService.getDetail(searchInfo);
		
		model.addAttribute("mbrInfo"	, detail.get("mbrInfo"));
		model.addAttribute("doorInfo"	, detail.get("doorInfo"));
		model.addAttribute("gasInfo"	, detail.get("gasInfo"));
		model.addAttribute("trespsInfo"	, detail.get("trespsInfo"));
		model.addAttribute("apInfo"		, detail.get("apInfo"));
		
		return "/inquiry/familyAuthDetail";
	}

}
