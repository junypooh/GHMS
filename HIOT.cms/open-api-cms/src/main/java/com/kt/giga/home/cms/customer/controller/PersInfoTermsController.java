package com.kt.giga.home.cms.customer.controller;

import java.text.SimpleDateFormat;
import java.util.*;

import javax.servlet.http.*;

import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.*;

import com.kt.giga.home.cms.common.domain.Code;
import com.kt.giga.home.cms.common.domain.LoginInfo;
import com.kt.giga.home.cms.common.service.CodeService;
import com.kt.giga.home.cms.customer.service.PersInfoTermsService;
import com.kt.giga.home.cms.util.*;

@Controller
@RequestMapping("/customer")
public class PersInfoTermsController {
	
	@Autowired
	private HttpServletRequest request;
	
	@Autowired
	private PersInfoTermsService persInfoTermsService;
	
	@Autowired
	private CodeService codeService;
	
	@RequestMapping("/persInfoTermsList")
	public String getPersInfoTermssList(Model model, @RequestParam Map<String, Object> searchInfo) {
		
		int page, pageSize = 10;
		
		page = searchInfo.get("page") == null || searchInfo.get("page").toString().trim().equals("") ? 1 : Integer.parseInt(searchInfo.get("page").toString().trim());
		String cpCode	= searchInfo.get("cpCode") == null || searchInfo.get("cpCode").toString().trim().equals("") ? "" : searchInfo.get("cpCode").toString().trim();
		
		LoginInfo loginInfo = (LoginInfo)request.getAttribute("loginInfo");
		searchInfo.put("id"				, loginInfo.getId());
		searchInfo.put("page"			, page);
		searchInfo.put("pageSize"		, pageSize);
		
		int privacyCount = persInfoTermsService.getCount(searchInfo);
		List<Map<String, Object>> privacyList = persInfoTermsService.getList(searchInfo);
		
		
		List<Code> serviceList = codeService.getList("1200");
		
		Map<String, String> serviceMap = new HashMap<>();
		for(Code service : serviceList) {
			serviceMap.put(service.getName(), service.getMemo());
		}
		
		for(Map<String, Object> privacy : privacyList) {
			privacy.put("cpName", serviceMap.get(privacy.get("cpCode")).toString());
		}
		
		PageNavi pageNavi = new PageNavi();
		
		pageNavi.setAction("/customer/persInfoTermsList");
		pageNavi.setTotalCount(privacyCount);
		pageNavi.setPageSize(pageSize);
		pageNavi.setNowPage(page);
		pageNavi.setParameters("cpCode", cpCode);
		pageNavi.make();
		
		model.addAttribute("privacyList"	, privacyList);
		model.addAttribute("pageNavi"	, pageNavi);
		
		return "/customer/persInfoTermsList";
	}
	
	@ResponseBody
	@RequestMapping("/checkVerInfoFormData")
	public Map<String, Object> checkPersInfoFormData(@RequestParam Map<String, Object> privacy) {
		Map<String, Object> result = new HashMap<>();
		String rtnStr = persInfoTermsService.checkPersInfoFormData(privacy);
		try {
			if(rtnStr.equals("success")){
				result.put("code", 200);
				result.put("msg", "success");
			}else if(rtnStr.equals("fail")){
				result.put("code", 300);
				result.put("msg", "fail");
			}else{
				result.put("code", 500);
				result.put("msg", "error");
			}
		} catch(Exception e) {
			result.put("code", 500);
			result.put("msg", "server error : " + e.getMessage());
		}
		
		return result;
	}
	
	@RequestMapping("/persInfoTermsRegister")
	public String persInfoTermsRegisterForm(Model model) {		
		
		SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd");
		LoginInfo loginInfo = (LoginInfo)request.getAttribute("loginInfo");
		model.addAttribute("privacyAnnouncement", sdf.format(new Date()));
		model.addAttribute("privacyStart", sdf.format(new Date()));
		model.addAttribute("writerId", loginInfo.getId());
		
		return "/customer/persInfoTermsRegister";
	}
	
	@RequestMapping(value="/persInfoTermsRegister", params="method=register")
	public String persInfoTermsRegister(Model model, @RequestParam Map<String, Object> privacy) {

		String script = "alert('%s');";
		String writerIp = request.getRemoteAddr();
		privacy.put("writerIp", writerIp);
		privacy.put("regDate", new Date());
		
		//한자리 정수 앞에 0 이 붙을경우 제거
		/*String verNum = "";
		String[] verArryStr = privacy.get("privacyVer").toString().split("\\.");
		if(verArryStr.length == 3){
			verNum = 	Integer.toString(Integer.parseInt(verArryStr[0])) + "." + 
							Integer.toString(Integer.parseInt(verArryStr[1])) + "." + 
							Integer.toString(Integer.parseInt(verArryStr[2]));
		}
		privacy.put("privacyVer", verNum);*/
		
		try {
			privacy.put("privacyAnnouncement", privacy.get("privacyAnnouncement").toString().trim().replace("-", "") + "000000");
			privacy.put("privacyStart", privacy.get("privacyStart").toString().trim().replace("-", "") + "000000");
			persInfoTermsService.register(privacy);
			script  = String.format(script, "정상적으로 등록 처리 되었습니다.");
			script += "location.href = '/customer/persInfoTermsList';"; 			
		} catch(Exception e) {
			model.addAttribute("privacy", privacy);
			script  = String.format(script, "등록 과정중 오류가 발생하였습니다.\\n다시 시도하여 주십시오.");
		}
		
		model.addAttribute("script", script);
		
		return "/customer/persInfoTermsRegister";
	}
	
	@RequestMapping("/persInfoTermsModify")
	public String persInfoTermsModifyForm(Model model, @RequestParam Map<String, Object> privacy) {
		
		LoginInfo loginInfo = (LoginInfo)request.getAttribute("loginInfo");
		privacy.put("id", loginInfo.getId());		
		
		Map<String, Object> privacyMap = persInfoTermsService.get(privacy);

		String cpName = codeService.getServiceName(privacyMap.get("cpCode").toString());
		privacyMap.put("cpName", cpName);
		
		String subStr = "";
		
		if(privacyMap.get("privacyAnnouncement") != null){
			subStr = 	privacyMap.get("privacyAnnouncement").toString().substring(0, 4) + "-" + 
							privacyMap.get("privacyAnnouncement").toString().substring(4, 6) + "-" +
							privacyMap.get("privacyAnnouncement").toString().substring(6, 8);
			privacyMap.put("privacyAnnouncement", subStr);
		}
		if(privacyMap.get("privacyStart") != null){
			subStr = 	privacyMap.get("privacyStart").toString().substring(0, 4) + "-" + 
							privacyMap.get("privacyStart").toString().substring(4, 6) + "-" +
							privacyMap.get("privacyStart").toString().substring(6, 8);
			privacyMap.put("privacyStart", subStr);
		}
		
		model.addAttribute("privacyMap", privacyMap);
		
		return "/customer/persInfoTermsModify";
	}
	
	@RequestMapping(value="/persInfoTermsModify", params="method=modify")
	public String persInfoTermsModify(Model model, @RequestParam Map<String, Object> privacy) {

		String page	= privacy.get("page") == null || privacy.get("page").toString().trim().equals("") ? "1" : privacy.get("page").toString().trim();
		String cpCode	= privacy.get("cpCode") == null || privacy.get("cpCode").toString().trim().equals("") ? "" : privacy.get("cpCode").toString().trim();
		
		String script = "alert('%s');";
		String modifierIp = request.getRemoteAddr();
		LoginInfo loginInfo = (LoginInfo)request.getAttribute("loginInfo");
		privacy.put("modifierIp", modifierIp);
		privacy.put("modifierId", loginInfo.getId());
		privacy.put("updateDate", new Date());
		
		try {
			privacy.put("privacyAnnouncement", privacy.get("privacyAnnouncement").toString().trim().replace("-", "") + "000000");
			privacy.put("privacyStart", privacy.get("privacyStart").toString().trim().replace("-", "") + "000000");
			persInfoTermsService.modify(privacy);
			script  = String.format(script, "정상적으로 수정 처리 되었습니다.");
			script += String.format("location.href = '/customer/persInfoTermsList?&page=" + page  
					+ "&cpCode=" + cpCode 
					+ "';");
		} catch(Exception e) {
			model.addAttribute("privacyMap", privacy);
			script  = String.format(script, "수정 과정중 오류가 발생하였습니다.\\n다시 시도하여 주십시오.");
			e.printStackTrace();
		}
		
		model.addAttribute("script", script);
		
		return "/customer/persInfoTermsModify";
	}

	@ResponseBody 
	@RequestMapping("/removePersInfoTerms")
	public Map<String, Object> removePersInfoTerms(@RequestParam Map<String, Object> privacy) {
		
		Map<String, Object> result = new HashMap<>(); 
		Map<String, Object> privacyMap = new HashMap<>(); 
		
		try {
			String[] pkList = privacy.get("pkList").toString().split(",");
			
			for(String pkStr : pkList) {
				
				privacyMap.put("pk", Integer.valueOf(pkStr));
				
				persInfoTermsService.remove(privacyMap);
			}	
			
			result.put("code", 200);
			result.put("msg", "success");
		} catch(Exception e) {
			result.put("code", 500);
			result.put("msg", "server error : " + e.getMessage());
		}
		
		return result;
	}	
	
	
	
}
