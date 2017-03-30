package com.kt.giga.home.cms.customer.controller;


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
public class GeneralNoticeController {
	
	@Autowired
	private HttpServletRequest request;
	
	@Autowired
	private GeneralNoticeService generalNoticeService;
	
	@Autowired
	private CodeService codeService;
	
	@ResponseBody 
	@RequestMapping("/removeGeneralNotice")
	public Map<String, Object> removeGeneralNotice(@RequestParam Map<String, Object> noticeList) {

		Map<String, Object> result = new HashMap<>(); 
		Map<String, Object> notice = new HashMap<>(); 
		
		try {
			String[] pkList = noticeList.get("pkList").toString().split(",");
			
			for(String pkStr : pkList) {
				
				notice.put("pk", Integer.valueOf(pkStr));
				
				generalNoticeService.remove(notice);
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
	@RequestMapping("/updateGeneralNoticeOpenYN")
	public Map<String, Object> updateGeneralNoticeOpenYN(@RequestParam Map<String, Object> notice) {
		
		Map<String, Object> result = new HashMap<>();
		
		try {
			generalNoticeService.updateOpenYN(notice);
			result.put("code", 200);
			result.put("msg", "success");
		} catch(Exception e) {
			result.put("code", 500);
			result.put("msg", "server error : " + e.getMessage());
		}
		
		return result;
	}
	
	@ResponseBody
	@RequestMapping("/updateGeneralNoticePreorderYN")
	public Map<String, Object> updateGeneralNoticePreorderYN(@RequestParam Map<String, Object> notice) {
		
		Map<String, Object> result = new HashMap<>();
		
		try {
			generalNoticeService.updatePreorderYN(notice);
			
			if(!(boolean)notice.get("over")) {
				result.put("code", 200);
				result.put("msg", "success");
			} else {
				result.put("code", 400);
				result.put("msg", "over");				
			}
		} catch(Exception e) {
			result.put("code", 500);
			result.put("msg", "server error : " + e.getMessage());
			e.printStackTrace();
		}
		
		return result;
	}
	
	@RequestMapping("/generalNoticeList")
	public String getGeneralNoticeList(Model model, @RequestParam Map<String, Object> searchInfo) {		
		
		int page, pageSize = 10;
		
		page = searchInfo.get("page") == null || searchInfo.get("page").toString().trim().equals("") ? 1 : Integer.parseInt(searchInfo.get("page").toString().trim());
		String searchString	= searchInfo.get("searchString") != null ? searchInfo.get("searchString").toString().trim() : "";
		String searchColumn	= searchInfo.get("searchColumn") != null ? searchInfo.get("searchColumn").toString().trim() : "";
		String openYnSelect	= searchInfo.get("openYnSelect") != null ? searchInfo.get("openYnSelect").toString().trim() : "";
		String cpCode	= searchInfo.get("cpCode") == null || searchInfo.get("cpCode").toString().trim().equals("") ? "" : searchInfo.get("cpCode").toString().trim();
		
		LoginInfo loginInfo = (LoginInfo)request.getAttribute("loginInfo");
		searchInfo.put("id"				, loginInfo.getId());
		searchInfo.put("cpCode"			, cpCode);
		searchInfo.put("page"			, page);
		searchInfo.put("pageSize"		, pageSize);
		searchInfo.put("searchString"	, searchString);
		
		List<Code> serviceList = codeService.getList("1200");		
		Map<String, String> serviceMap = new HashMap<>();
		for(Code service : serviceList) {
			serviceMap.put(service.getName(), service.getMemo());
		}
		
		List<Map<String, Object>> noticeList = generalNoticeService.getList(searchInfo);
		for(Map<String, Object> notice : noticeList) {
			notice.put("cpName", serviceMap.get(notice.get("cpCode")).toString());
		}
		int noticeCount = generalNoticeService.getCount(searchInfo);
		
		PageNavi pageNavi = new PageNavi();
		
		pageNavi.setAction("/customer/generalNoticeList");
		pageNavi.setTotalCount(noticeCount);
		pageNavi.setPageSize(pageSize);
		pageNavi.setNowPage(page);
		pageNavi.setParameters("cpCode", cpCode);
		pageNavi.setParameters("searchColumn", searchColumn);
		pageNavi.setParameters("searchString", searchString);
		pageNavi.setParameters("openYnSelect", openYnSelect);
		pageNavi.make();
		
		model.addAttribute("noticeList"	, noticeList);
		model.addAttribute("pageNavi"	, pageNavi);
		
		return "/customer/generalNoticeList";
	}
	
	@RequestMapping("/generalNoticeRegister")
	public String generalNoticeRegisterForm(Model model) {
		
		return "/customer/generalNoticeRegister";
	}
	
	@RequestMapping(value="/generalNoticeRegister", params="method=register")
	public String generalNoticeRegister(Model model, @RequestParam Map<String, Object> notice) {

		String script = "alert('%s');";
		String writerIp = request.getRemoteAddr();
		
		notice.put("writerIp", writerIp);
		notice.put("regDate", new Date());
		
		try {
			generalNoticeService.register(notice);
			script  = String.format(script, "정상적으로 등록 처리 되었습니다.");
			script += "location.href = '/customer/generalNoticeList';"; 			
		} catch(Exception e) {
			model.addAttribute("notice", notice);
			script  = String.format(script, "등록 과정중 오류가 발생하였습니다.\\n다시 시도하여 주십시오.");
		}
		
		model.addAttribute("script", script);
		
		return "/customer/generalNoticeRegister";
	}
	
	@RequestMapping("/generalNoticeModify")
	public String generalNoticeModifyForm(Model model, @RequestParam Map<String, Object> searchInfo) {
		
		String cpCode	= searchInfo.get("cpCode") == null || searchInfo.get("cpCode").toString().trim().equals("") ? "" : searchInfo.get("cpCode").toString().trim();
		
		searchInfo.put("cpCode", cpCode);
		LoginInfo loginInfo = (LoginInfo)request.getAttribute("loginInfo");
		searchInfo.put("id", loginInfo.getId());
		Map<String, Object> notice = generalNoticeService.get(searchInfo);
		
		String cpName = codeService.getServiceName(notice.get("cpCode").toString());
		notice.put("cpName", cpName);
		model.addAttribute("notice", notice);
		
		return "/customer/generalNoticeModify";
	}
	
	@RequestMapping(value="/generalNoticeModify" , params="method=modify")
	public String generalNoticeModify(Model model, @RequestParam Map<String, Object> notice) {
		

		String script = "alert('%s');";
		String modifierIp = request.getRemoteAddr();
		
		notice.put("modifierIp", modifierIp);
		notice.put("updateDate", new Date());
		
		try {
			generalNoticeService.modify(notice);
			script  = String.format(script, "정상적으로 수정 처리 되었습니다.");
			script += String.format("location.href = '/customer/generalNoticeList';", notice.get("page"), notice.get("searchColumn"), notice.get("searchString") ); 			
		} catch(Exception e) {
			
			model.addAttribute("notice", notice);
			script  = String.format(script, "수정 과정중 오류가 발생하였습니다.\\n다시 시도하여 주십시오.");
		}
		
		model.addAttribute("script", script);
		
		return "/customer/generalNoticeModify";		
	}
}
