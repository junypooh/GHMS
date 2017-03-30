package com.kt.giga.home.cms.inquiry.controller;

import java.util.*;

import javax.servlet.http.HttpServletRequest;

import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.*;

import com.kt.giga.home.cms.util.*;
import com.kt.giga.home.cms.common.domain.Code;
import com.kt.giga.home.cms.common.domain.LoginInfo;
import com.kt.giga.home.cms.common.service.CodeService;
import com.kt.giga.home.cms.inquiry.service.*;

@Controller
@RequestMapping("/inquiry")
public class ParentsAgreeController {
	
	@Autowired
	private HttpServletRequest request;

	@Autowired
	private ParentsAgreeService parentsAgreeService;
	
	@Autowired
	private CodeService codeService;
	
	@RequestMapping("/parentsAgreeList")
	public String  parentsAgreeList(Model model, @RequestParam Map<String, Object> searchInfo) {
		
		int page, pageSize = 10;
		
		page = searchInfo.get("page") != null ? Integer.parseInt(searchInfo.get("page").toString().trim()) : 1 ;
		String searchColumn	= searchInfo.get("searchColumn") != null ? searchInfo.get("searchColumn").toString().trim() : "";
		String searchString	= searchInfo.get("searchString") != null ? searchInfo.get("searchString").toString().trim() : "";
		String cpCode	= searchInfo.get("cpCode") == null || searchInfo.get("cpCode").toString().trim().equals("") ? "" : searchInfo.get("cpCode").toString().trim();
		
		LoginInfo loginInfo = (LoginInfo)request.getAttribute("loginInfo");
		
		searchInfo.put("id"				, loginInfo.getId());
		searchInfo.put("page"			, page);
		searchInfo.put("pageSize"		, pageSize);
		searchInfo.put("searchString"	, searchString);
		
		int parentsAgreeCount = parentsAgreeService.getCount(searchInfo);
		List<Map<String, Object>> parentsAgreeList = parentsAgreeService.getList(searchInfo);	
		List<Code> serviceList = codeService.getList("1200");
		
		Map<String, String> serviceMap = new HashMap<>();
		for(Code service : serviceList) {
			serviceMap.put(service.getName(), service.getMemo());
		}
		
		for(Map<String, Object> terms : parentsAgreeList) {
			terms.put("cpName", serviceMap.get(terms.get("cpCode")).toString());
		}
		
		String memberId = "";
		String maskMemberId = "***";
		for(Map<String, Object> parentsAgree : parentsAgreeList) {
			
			
			if(parentsAgree.get("mbrId") != null && parentsAgree.get("mbrId").toString().length() > 3) {
				memberId = parentsAgree.get("mbrId").toString();
				maskMemberId = memberId.substring(0, memberId.length() - 3) + "***";
			}
			
			parentsAgree.put("mbrId", maskMemberId);
			
			if(parentsAgree.get("parentsMobile") != null && parentsAgree.get("parentsMobile").toString().length() > 4) {
				memberId = parentsAgree.get("parentsMobile").toString();
				maskMemberId = memberId.substring(0, memberId.length() - 4) + "****";
			}
			
			parentsAgree.put("parentsMobile", maskMemberId);
		}		
		
		PageNavi pageNavi = new PageNavi();
		
		pageNavi.setAction("/inquiry/parentsAgreeList");
		pageNavi.setTotalCount(parentsAgreeCount);
		pageNavi.setPageSize(pageSize);
		pageNavi.setNowPage(page);
		pageNavi.setParameters("searchColumn", searchColumn);
		pageNavi.setParameters("searchString", searchString);
		pageNavi.setParameters("cpCode", cpCode);
		pageNavi.make();
		
		model.addAttribute("parentsAgreeList"	, parentsAgreeList);
		model.addAttribute("pageNavi"			, pageNavi);	
		
		return "/inquiry/parentsAgreeList";
	}
}
