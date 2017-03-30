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
public class TermsAgreeController {

	@Autowired
	private HttpServletRequest request;
	
	@Autowired
	private TermsAgreeService termsAgreeService;
	
	@Autowired
	private CodeService codeService;
	
	@RequestMapping("/termsAgreeList")
	public String termsAgreeList(Model model, @RequestParam Map<String, Object> searchInfo) {
		
		int page, pageSize = 10;
		
		page = searchInfo.get("page") == null || searchInfo.get("page").toString().trim().equals("") ? 1 : Integer.parseInt(searchInfo.get("page").toString().trim());
		String cpCode	= searchInfo.get("cpCode") == null || searchInfo.get("cpCode").toString().trim().equals("") ? "" : searchInfo.get("cpCode").toString().trim();
		String termsCode	= searchInfo.get("termsCode") == null || searchInfo.get("termsCode").toString().trim().equals("") ? "" : searchInfo.get("termsCode").toString().trim();
		String searchColumn	= searchInfo.get("searchColumn") == null || searchInfo.get("searchColumn").toString().trim().equals("") ? "" : searchInfo.get("searchColumn").toString().trim();
		String searchString	= searchInfo.get("searchString") == null || searchInfo.get("searchString").toString().trim().equals("") ? "" : searchInfo.get("searchString").toString().trim();
		
		LoginInfo loginInfo = (LoginInfo)request.getAttribute("loginInfo");
		
		searchInfo.put("id"				, loginInfo.getId());
		searchInfo.put("page"			, page);
		searchInfo.put("pageSize"		, pageSize);
		searchInfo.put("searchString"	, searchString);
		
		int termsAgreeCount = termsAgreeService.getCount(searchInfo);
		List<Map<String, Object>> termsAgreeList = termsAgreeService.getList(searchInfo);
		List<Code> codeList = codeService.getList("1300");
		List<Code> serviceList = codeService.getList("1200");
		
		Map<String, String> serviceMap = new HashMap<>();
		for(Code service : serviceList) {
			serviceMap.put(service.getName(), service.getMemo());
		}
		
		String mbrId = "", maskMbrId = "";
		String telNo = "", maskTelNo = "";
		
		for(Map<String, Object> termsAgree : termsAgreeList) {
			
			//상품명
			termsAgree.put("cpName", serviceMap.get(termsAgree.get("cpCode")).toString());
			
			//마스킹처리
			if(termsAgree.get("mbrId") != null && termsAgree.get("mbrId").toString().length() > 3) {
				mbrId = termsAgree.get("mbrId").toString();
				maskMbrId = mbrId.substring(0, mbrId.length() - 3) + "***";
			}
			
			termsAgree.put("mbrId", maskMbrId);
			
			if(termsAgree.get("telNo") != null && termsAgree.get("telNo").toString().length() > 4) {
				telNo = termsAgree.get("telNo").toString();
				maskTelNo = telNo.substring(0, telNo.length() - 4) + "****";
			}			
			
			termsAgree.put("telNo", maskTelNo);
			
		}
		
		PageNavi pageNavi = new PageNavi();
		
		pageNavi.setAction("/inquiry/termsAgreeList");
		pageNavi.setTotalCount(termsAgreeCount);
		pageNavi.setPageSize(pageSize);
		pageNavi.setNowPage(page);
		pageNavi.setParameters("cpCode", cpCode);
		pageNavi.setParameters("termsCode", termsCode);
		pageNavi.setParameters("searchColumn", searchColumn);
		pageNavi.setParameters("searchString", searchString);
		pageNavi.make();
		
		model.addAttribute("termsAgreeList"	, termsAgreeList);
		model.addAttribute("codeList"	, codeList);
		model.addAttribute("pageNavi"		, pageNavi);
		
		return "/inquiry/termsAgreeList";
	}
}
