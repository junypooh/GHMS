package com.kt.giga.home.cms.inquiry.controller;

import java.util.*;

import javax.servlet.http.HttpServletRequest;

import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.*;

import com.kt.giga.home.cms.util.*;
import com.kt.giga.home.cms.common.domain.Code;
import com.kt.giga.home.cms.common.service.CodeService;
import com.kt.giga.home.cms.inquiry.service.*;

@Controller
@RequestMapping("/inquiry")
public class AppVerHistoryController {

	@Autowired
	private HttpServletRequest request;
	
	@Autowired
	private AppVerHistoryService appVerHistoryService;
	
	@Autowired
	private CodeService codeService;
	
	@RequestMapping("/appVerHistoryList")
	public String appVerHistoryList(Model model, @RequestParam Map<String, Object> searchInfo) {
		
		int page, pageSize = 10;
		
		page = searchInfo.get("page") == null || searchInfo.get("page").toString().trim().equals("") ? 1 : Integer.parseInt(searchInfo.get("page").toString().trim());
		String cpCode	= searchInfo.get("cpCode") == null || searchInfo.get("cpCode").toString().trim().equals("") ? "" : searchInfo.get("cpCode").toString().trim();
		String searchColumn	= searchInfo.get("searchColumn") == null || searchInfo.get("searchColumn").toString().trim().equals("") ? "" : searchInfo.get("searchColumn").toString().trim();
		String searchString	= searchInfo.get("searchString") == null || searchInfo.get("searchString").toString().trim().equals("") ? "" : searchInfo.get("searchString").toString().trim();
		
		searchInfo.put("page"			, page);
		searchInfo.put("pageSize"		, pageSize);
		searchInfo.put("searchString"	, searchString);
		
		int appVerHistoryCount = appVerHistoryService.getCount(searchInfo);
		List<Map<String, Object>> appVerHistoryList = appVerHistoryService.getList(searchInfo);
		List<Code> serviceList = codeService.getList("1200");
		
		Map<String, String> serviceMap = new HashMap<>();
		for(Code service : serviceList) {
			serviceMap.put(service.getName(), service.getMemo());
		}
		
		String maskStr;
		String[] splitStr;
		String contentsStr;
		
		if(appVerHistoryList != null){
			for(Map<String, Object> appVerHistory : appVerHistoryList) {
				appVerHistory.put("cpName", serviceMap.get(appVerHistory.get("unitSvcCd")).toString());
				
				if(appVerHistory.get("contents") != null) {
					
					//고객ID 마스킹
					maskStr 	= appVerHistory.get("mbrId").toString().trim();
					maskStr			= maskStr.substring(0, maskStr.length()-3) + "***";
					appVerHistory.put("mbrId", maskStr);
					
					//전화번호 마스킹
					maskStr 	= appVerHistory.get("telNo").toString().trim();
					maskStr			= maskStr.substring(0, maskStr.length()-4) + "****";
					appVerHistory.put("telNo", maskStr);
					
					contentsStr = appVerHistory.get("contents").toString();
					splitStr = contentsStr.split("\\|");
					
					//단말기 명
					if(splitStr.length > 0){
						appVerHistory.put("devName", splitStr[0]);
					}
					
					//OS버전
					if(splitStr.length > 1){
						appVerHistory.put("osVer", splitStr[1]);
					}
				}
			}
		}
		
		PageNavi pageNavi = new PageNavi();
		
		pageNavi.setAction("/inquiry/appVerHistoryList");
		pageNavi.setTotalCount(appVerHistoryCount);
		pageNavi.setPageSize(pageSize);
		pageNavi.setNowPage(page);
		pageNavi.setParameters("cpCode", cpCode);
		pageNavi.setParameters("searchColumn", searchColumn);
		pageNavi.setParameters("searchString", searchString);
		pageNavi.make();
		
		model.addAttribute("appVerHistoryList"	, appVerHistoryList);
		model.addAttribute("pageNavi"		, pageNavi);
		
		return "/inquiry/appVerHistoryList";
	}
}
