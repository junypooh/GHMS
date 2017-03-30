package com.kt.giga.home.cms.customer.controller;


import java.util.*;

import javax.servlet.http.*;

import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.*;

import com.kt.giga.home.cms.common.domain.LoginInfo;
import com.kt.giga.home.cms.common.service.CodeService;
import com.kt.giga.home.cms.customer.service.*;

@Controller
@RequestMapping("/customer")
public class ServiceUrlController {
	
	@Autowired
	private HttpServletRequest request;
	
	@Autowired
	private ServiceUrlService serviceUrlService;
	
	@Autowired
	private CodeService codeService;
	
	@RequestMapping("/serviceUrl")
	public String getServiceUrlLisat(Model model, @RequestParam Map<String, Object> searchInfo) {			
		
		String serviceUrl = "";
		LoginInfo loginInfo = (LoginInfo)request.getAttribute("loginInfo");
		String defaultCpCode = loginInfo.getSvcList().get(0).get("cpCode").toString();
		String cpCode	= searchInfo.get("cpCode") == null || searchInfo.get("cpCode").toString().trim().equals("") ? defaultCpCode : searchInfo.get("cpCode").toString().trim();
		searchInfo.put("cpCode", cpCode);
		
		try {
			serviceUrl = serviceUrlService.getUrl(searchInfo);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		model.addAttribute("cpCode"	, cpCode);
		model.addAttribute("serviceUrl"	, serviceUrl);
		
		return "/customer/serviceUrl";
	}
		
	
	@RequestMapping("/updateServiceUrl")
	public String serviceUrlModify(Model model, @RequestParam Map<String, Object> serviceUrl) {
		
		String script = "alert('%s');";		
		String targetCpCode = serviceUrl.get("targetCpCode").toString();
		try {
			serviceUrlService.modify(serviceUrl);
			script  = String.format(script, "정상적으로 수정 처리 되었습니다.");
			script += String.format("location.href = '/customer/serviceUrl?&cpCode=" + targetCpCode + "';");
		} catch(Exception e) {
			model.addAttribute("ServiceUrl", serviceUrl);
			script  = String.format(script, "수정 과정중 오류가 발생하였습니다.\\n다시 시도하여 주십시오.");
		}
		
		model.addAttribute("script", script);
		
		return "/customer/serviceUrl";		
	}
}
