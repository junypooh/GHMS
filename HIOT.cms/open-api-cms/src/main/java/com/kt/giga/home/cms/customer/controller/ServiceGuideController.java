package com.kt.giga.home.cms.customer.controller;

import java.io.*;
import java.util.*;

import javax.servlet.http.*;

import org.springframework.ui.*;
import org.springframework.web.multipart.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;
import org.springframework.beans.factory.annotation.*;

import com.kt.giga.home.cms.util.*;
import com.kt.giga.home.cms.common.domain.*;
import com.kt.giga.home.cms.common.service.*;
import com.kt.giga.home.cms.customer.service.*;

@Controller
@RequestMapping("/customer")
public class ServiceGuideController {
	
	@Autowired
	private HttpServletRequest request;
	
	@Autowired
	private CodeService codeService;	
	
	@Autowired
	private ServiceGuideService serviceGuideService;
	
	@Autowired
	private ManageGoodsService manageGoodsService;
	
	@Autowired
	private OpenAPICallService openAPICallService;
	
	@Autowired
	private APIEnv apiEnv;
	
	@ResponseBody
	@RequestMapping("/updateServiceOpenYN")
	public Map<String, Object> updateServiceOpenYN(@RequestParam Map<String, Object> serviceGuide) {
		Map<String, Object> result = new HashMap<>();
		
		try {
			serviceGuideService.updateOpenYN(serviceGuide);
			openAPICallService.updateOpenAPIInit();
			result.put("code", 200);
			result.put("msg", "success");
		} catch(Exception e) {
			result.put("code", 500);
			result.put("msg", "server error : " + e.getMessage());
		}
		
		return result;
	}
	
	@ResponseBody 
	@RequestMapping("/removeServiceGuide")
	public Map<String, Object> removeServiceGuide(@RequestParam Map<String, Object> serviceGuideList) {

		Map<String, Object> result = new HashMap<>(); 
		Map<String, Object> seviceGuide = new HashMap<>(); 
		
		try {
			String[] pkList = serviceGuideList.get("pkList").toString().split(",");
			
			for(String pkStr : pkList) {
				seviceGuide.put("pk", Integer.valueOf(pkStr));
				serviceGuideService.remove(seviceGuide);
			}	
			openAPICallService.updateOpenAPIInit();
			
			result.put("code", 200);
			result.put("msg", "success");
		} catch(Exception e) {
			result.put("code", 500);
			result.put("msg", "server error : " + e.getMessage());
		}
		
		return result;
	}		
	
	@RequestMapping("/serviceGuideList")
	public String getServiceGuideList(Model model, @RequestParam Map<String, Object> searchInfo) {
		
		int searchSvcFn = 0;
		int page, pageSize = 10;
		LoginInfo loginInfo = (LoginInfo)request.getAttribute("loginInfo");
		
		page = searchInfo.get("page") == null || searchInfo.get("page").toString().trim().equals("") ? 1 : Integer.parseInt(searchInfo.get("page").toString().trim());
		searchSvcFn = searchInfo.get("searchSvcFn") == null || searchInfo.get("searchSvcFn").toString().trim().equals("") ? 0 : Integer.parseInt(searchInfo.get("searchSvcFn").toString().trim());
		
		String searchCpCode		= searchInfo.get("searchCpCode")	== null	? "" : searchInfo.get("searchCpCode").toString().trim();
		String searchColumn		= searchInfo.get("searchColumn") 	== null	? "" : searchInfo.get("searchColumn").toString().trim();
		String searchString		= searchInfo.get("searchString")	== null	? "" : searchInfo.get("searchString").toString().trim();
		
		searchInfo.put("id"				, loginInfo.getId());
		searchInfo.put("page"			, page);
		searchInfo.put("pageSize"		, pageSize);
		searchInfo.put("searchSvcFn"	, searchSvcFn);
		searchInfo.put("searchString"	, searchString);
		
		Map<String, Object> searchSvcFnMap = new HashMap<>();
		searchSvcFnMap.put("id"			, loginInfo.getId());
		searchSvcFnMap.put("cpCode"		, searchCpCode);
		searchSvcFnMap.put("page"		, 1);
		searchSvcFnMap.put("pageSize"	, 1000);		
		
		List<Map<String, Object>> svcFnList = manageGoodsService.getList(searchSvcFnMap);
		
		List<Code> serviceList = codeService.getList("1200"); // 관리 상품 리스트
		int serviceGuideCount = serviceGuideService.getCount(searchInfo);
		List<Map<String, Object>> serviceGuideList = serviceGuideService.getList(searchInfo);
		
		Map<String, String> serviceMap = new HashMap<>();
		for(Code service : serviceList) {
			serviceMap.put(service.getName(), service.getMemo());
		}
		
		for(Map<String, Object> serviceGuide : serviceGuideList) {
			serviceGuide.put("cpName", serviceMap.get(serviceGuide.get("cpCode")).toString());
		}		
		
		PageNavi pageNavi = new PageNavi();
		
		pageNavi.setAction("/customer/serviceGuideList");
		pageNavi.setTotalCount(serviceGuideCount);
		pageNavi.setPageSize(pageSize);
		pageNavi.setNowPage(page);
		pageNavi.setParameters("searchColumn"	, searchColumn);
		pageNavi.setParameters("searchString"	, searchString);
		pageNavi.setParameters("searchCpCode"	, searchCpCode);
		pageNavi.setParameters("searchSvcFn"	, String.valueOf(searchSvcFn));
		pageNavi.make();
		
		model.addAttribute("serviceGuideList"	, serviceGuideList);
		model.addAttribute("svcFnList"			, svcFnList);
		model.addAttribute("pageNavi"			, pageNavi);
		
		return "/customer/serviceGuideList";
	}
	
	@RequestMapping("/serviceGuideRegister")
	public String serviceGuideRegisterForm() {
		return "/customer/serviceGuideRegister";
	}
	
	@RequestMapping(value="/serviceGuideRegister", params="method=register")
	public String serviceGuideRegister(Model model, MultipartHttpServletRequest multipartRequest, @RequestParam Map<String, Object> serviceGuide) {

		String script = "alert('%s');";
		String writerIp = request.getRemoteAddr();	
		String writerId = ((LoginInfo)request.getAttribute("loginInfo")).getId();
		String uploadPath = request.getServletContext().getInitParameter("faqPath");
		String realPath = new File(uploadPath).getAbsolutePath();
		String rootPathType = request.getServletContext().getInitParameter("rootPathType");
		
		if(rootPathType.equals("virtual")) {
			//realPath = request.getServletContext().getRealPath(uploadPath);
			realPath = request.getServletContext().getInitParameter("uploadPath") + request.getServletContext().getInitParameter("faqPath");
		}
		
		String contentsType = serviceGuide.get("contentsType") != null ? serviceGuide.get("contentsType").toString() : "";
		String searchCpCode = serviceGuide.get("searchCpCode") != null ? serviceGuide.get("searchCpCode").toString() : "";
		int svcFn = serviceGuide.get("svcFn").toString() != null ? Integer.parseInt(serviceGuide.get("svcFn").toString()) : 0; 
		
		serviceGuide.put("svcFn"		, svcFn);
		serviceGuide.put("writerIp"		, writerIp);
		serviceGuide.put("writerId"		, writerId);
		serviceGuide.put("virtualPath"	, uploadPath);
		serviceGuide.put("realPath"		, realPath);
		serviceGuide.put("regDate"		, new Date());
		serviceGuide.put("images"		, null);
		
		if(contentsType.equals("image")) {
			serviceGuide.put("images", multipartRequest.getFileMap());
		}				
		
		try {
			serviceGuideService.register(serviceGuide);
			openAPICallService.updateOpenAPIInit();
			script  = String.format(script, "정상적으로 등록 처리 되었습니다.");
			script += "location.href = '/customer/serviceGuideList?searchCpCode=" + searchCpCode + "';"; 			
			
		} catch(Exception e) {
			model.addAttribute("serviceGuide", serviceGuide);
			script  = String.format(script, "등록 과정중 오류가 발생하였습니다.\\n다시 시도하여 주십시오.");
		}
		
		model.addAttribute("script", script);
		
		return "/customer/serviceGuideRegister";
	}
	
	@RequestMapping("/serviceGuideModify")
	public String serviceGuideModifyForm(Model model, @RequestParam Map<String, Object> serviceGuideInfo) {
		
		String id = ((LoginInfo)request.getAttribute("loginInfo")).getId();
		
		serviceGuideInfo.put("id", id);
		
		Map<String, Object> serviceGuide = serviceGuideService.get(serviceGuideInfo);
		//List<Map<String, Object>> manageFnList = manageGoodsService.getList(serviceGuide);
		String cpName = codeService.getServiceName(serviceGuide.get("cpCode").toString());
		serviceGuide.put("cpName", cpName);
		
		String domain = apiEnv.getProperty("www.domain");
		String port = apiEnv.getProperty("www.port");
		serviceGuide.put("domain", domain);
		serviceGuide.put("port", port);
		//serviceGuide.put("manageFnList", manageFnList);
		
		model.addAttribute("serviceGuide", serviceGuide);
		
		return "/customer/serviceGuideModify";
	}
	
	@RequestMapping(value="/serviceGuideModify", params="method=modify")
	public String serviceGuideModify(Model model, MultipartHttpServletRequest multipartRequest, @RequestParam Map<String, Object> searchGuideInfo) {
		
		String script = "alert('%s');";
		String modifierIp = request.getRemoteAddr();		
		String modifierId = ((LoginInfo)request.getAttribute("loginInfo")).getId();
		String uploadPath = request.getServletContext().getInitParameter("faqPath");
		String realPath = new File(uploadPath).getAbsolutePath();
		String rootPathType = request.getServletContext().getInitParameter("rootPathType");		
		
		if(rootPathType.equals("virtual")) {
			//realPath = request.getServletContext().getRealPath(uploadPath);
			realPath = request.getServletContext().getInitParameter("uploadPath") + request.getServletContext().getInitParameter("faqPath");
		}		
		
		String contentsType 	= searchGuideInfo.get("contentsType")   != null ? searchGuideInfo.get("contentsType").toString() : "";
		String searchCpCode		= searchGuideInfo.get("searchCpCode")	!= null	? searchGuideInfo.get("searchCpCode").toString().trim() : "";
		String searchFaqCode	= searchGuideInfo.get("searchFaqCode")	!= null	? searchGuideInfo.get("searchFaqCode").toString().trim() : "";
		String searchColumn		= searchGuideInfo.get("searchColumn") 	!= null	? searchGuideInfo.get("searchColumn").toString().trim() : "";
		String searchString		= searchGuideInfo.get("searchString")	!= null	? searchGuideInfo.get("searchString").toString().trim() : "";
		int page = searchGuideInfo.get("page") == null || searchGuideInfo.get("page").toString().trim().equals("") ? 1 : Integer.parseInt(searchGuideInfo.get("page").toString().trim());
		int svcFn = searchGuideInfo.get("svcFn").toString() != null ? Integer.parseInt(searchGuideInfo.get("svcFn").toString()) : 0; 	
		
		searchGuideInfo.put("svcFn"	, svcFn);
		searchGuideInfo.put("modifierId"	, modifierId);
		searchGuideInfo.put("modifierIp"	, modifierIp);
		searchGuideInfo.put("virtualPath"	, uploadPath);
		searchGuideInfo.put("realPath"		, realPath);
		searchGuideInfo.put("updateDate"	, new Date());
		searchGuideInfo.put("images"		, null);		
		
		if(contentsType.equals("image")) {
			searchGuideInfo.put("images", multipartRequest.getFileMap());
		}		
		
		try {
			serviceGuideService.modify(searchGuideInfo);
			openAPICallService.updateOpenAPIInit();
			script  = String.format(script, "정상적으로 수정 처리 되었습니다.");		
			script += String.format("location.href = '/customer/serviceGuideList?&page=" + page  
					+ "&searchColumn=" + searchColumn
					+ "&searchString=" + searchString 
					+ "&searchCpCode=" + searchCpCode 
					+ "&searchFaqCode=" + searchFaqCode 
					+ "';");
		} catch(Exception e) {
			model.addAttribute("searchGuide", searchGuideInfo);
			script  = String.format(script, "수정 과정중 오류가 발생하였습니다.\\n다시 시도하여 주십시오.");
		}
		
		model.addAttribute("script", script);
		
		return "/customer/serviceGuideModify";
	}
}
