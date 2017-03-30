package com.kt.giga.home.cms.customer.controller;


import java.util.*;

import javax.servlet.http.*;

import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.*;

import com.kt.giga.home.cms.common.domain.Code;
import com.kt.giga.home.cms.common.domain.LoginInfo;
import com.kt.giga.home.cms.common.service.CodeService;
import com.kt.giga.home.cms.customer.service.ManageGoodsService;
import com.kt.giga.home.cms.util.*;


@Controller
@RequestMapping("/customer")
public class ManageGoodsController {
	
	@Autowired
	private HttpServletRequest request;
	
	@Autowired
	private ManageGoodsService manageGoodsService; 
	
	@Autowired
	private CodeService codeService;
	
	@ResponseBody
	@RequestMapping("/getManageGoodsList")
	public Map<String, Object> getManageGoodsList(@RequestParam String cpCode) {
		Map<String, Object> result = new HashMap<>();
		
		try {
			LoginInfo loginInfo = (LoginInfo)request.getAttribute("loginInfo");
			
			Map<String, Object> searchFn = new HashMap<>();
			searchFn.put("id"		, loginInfo.getId());
			searchFn.put("cpCode"	, cpCode);
			searchFn.put("page"		, 1);
			searchFn.put("pageSize"	, 1000);		
			
			List<Map<String, Object>> manageFnList = manageGoodsService.getList(searchFn);
			
			result.put("list", manageFnList);			
			result.put("code", 200);
			result.put("msg", "success");
		} catch(Exception e) {
			result.put("code", 500);
			result.put("msg", "server error : " + e.getMessage());
		}
		
		return result;		
	}	
	
	@ResponseBody
	@RequestMapping("/removeManageGoods")
	public Map<String, Object> removeManageGoods(@RequestParam Map<String, Object> manageGoodsList) {
		Map<String, Object> result = new HashMap<>(); 
		Map<String, Object> manageGoods = new HashMap<>(); 
		
		try {
			String[] pkList = manageGoodsList.get("pkList").toString().split(",");
			
			for(String pkStr : pkList) {
				
				manageGoods.put("pk", Integer.valueOf(pkStr));
				
				manageGoodsService.remove(manageGoods);
			}	
			result.put("code", 200);
			result.put("msg", "success");
		} catch(Exception e) {
			result.put("code", 500);
			result.put("msg", "server error : " + e.getMessage());
		}
		
		return result;
	}		
	
	@RequestMapping("/manageGoodsList")
	public String manageGoodsList(Model model,@RequestParam Map<String, Object> searchInfo) {
		String searchColumn	= searchInfo.get("searchColumn") == null || searchInfo.get("searchColumn").toString().trim().equals("") ? "" : searchInfo.get("searchColumn").toString().trim();
		String searchString	= searchInfo.get("searchString") == null || searchInfo.get("searchString").toString().trim().equals("") ? "" : searchInfo.get("searchString").toString().trim();
		String cpCode	= searchInfo.get("cpCode") == null || searchInfo.get("cpCode").toString().trim().equals("") ? "" : searchInfo.get("cpCode").toString().trim();
		
		int page, pageSize = 10;		
		page = searchInfo.get("page") == null || searchInfo.get("page").toString().trim().equals("") ? 1 : Integer.parseInt(searchInfo.get("page").toString().trim());
		
		LoginInfo loginInfo = (LoginInfo)request.getAttribute("loginInfo");
		searchInfo.put("id"				, loginInfo.getId());
		searchInfo.put("cpCode"			, cpCode);
		searchInfo.put("page"			, page);
		searchInfo.put("pageSize"		, pageSize);
		searchInfo.put("searchString"	, searchString);
		
		List<Code> serviceList = codeService.getList("1200");
		
		int manageGoodsCount = manageGoodsService.getCount(searchInfo);
		List<Map<String, Object>> manageGoodsList = manageGoodsService.getList(searchInfo);

		Map<String, String> serviceMap = new HashMap<>();
		for(Code service : serviceList) {
			serviceMap.put(service.getName(), service.getMemo());
		}
		
		/*for(Map<String, Object> manageGoods : manageGoodsList) {
			manageGoods.put("cpName", serviceMap.get(manageGoods.get("cpCode")).toString());
		}*/
		
		PageNavi pageNavi = new PageNavi();
		
		pageNavi.setAction("/customer/manageGoodsList");
		pageNavi.setTotalCount(manageGoodsCount);
		pageNavi.setPageSize(pageSize);
		pageNavi.setNowPage(page);
		pageNavi.setParameters("cpCode", cpCode);
		
		pageNavi.setParameters("searchColumn", searchColumn);
		pageNavi.setParameters("searchString", searchString);
		pageNavi.make();		

		model.addAttribute("manageGoodsList"	, manageGoodsList);
		model.addAttribute("pageNavi"	, pageNavi);
		
		return "/customer/manageGoodsList";
	}
	
	@RequestMapping("/manageGoodsRegister")
	public String manageGoodsRegisterForm(Model model) {
		LoginInfo loginInfo = (LoginInfo)request.getAttribute("loginInfo");
		
		HashMap<String, String> manageGoods = new HashMap<String, String>();
				
		model.addAttribute("manageGoods", manageGoods);
		model.addAttribute("writerId", loginInfo.getId());	
		
		return "/customer/manageGoodsRegister";
	}
	
	@RequestMapping(value="/manageGoodsRegister", params="method=register")
	public String manageGoodsRegister(Model model, @RequestParam Map<String, Object> manageGoods) {
		String page	= manageGoods.get("page") == null || manageGoods.get("page").toString().trim().equals("") ? "1" : manageGoods.get("page").toString().trim();	
		String script = "alert('%s');";
		String writerIp = request.getRemoteAddr();	
		
		manageGoods.put("writerIp", writerIp);
		manageGoods.put("regDate", new Date());
		
		try {
			
		manageGoodsService.register(manageGoods);
			script  = String.format(script, "등록 되었습니다.");
			script += "location.href = '/customer/manageGoodsList?page= "+page + "';"; 			
		} catch(Exception e) {
			model.addAttribute("manageGoods", manageGoods);
			script  = String.format(script, "등록 과정중 오류가 발생하였습니다.\\n다시 시도하여 주십시오.");
		}
		
		model.addAttribute("script", script);
		
		return "/customer/manageGoodsRegister";
	}
	
	@RequestMapping("/manageGoodsModify")
	public String manageGoodsModifyForm(Model model, @RequestParam Map<String, Object> searchInfo) throws java.text.ParseException {
	
		LoginInfo loginInfo = (LoginInfo)request.getAttribute("loginInfo");
		searchInfo.put("id", loginInfo.getId());
		
		Map<String, Object> manageGoods = manageGoodsService.get(searchInfo);
		
		String cpName = codeService.getServiceName(manageGoods.get("cpCode").toString());
		
		manageGoods.put("modifyId", loginInfo.getId());
		manageGoods.put("cpName", cpName);
		
		model.addAttribute("manageGoods", manageGoods);
		return "/customer/manageGoodsModify";
	}
	
	@RequestMapping(value="/manageGoodsModify" , params="method=modify")
	public String manageGoodsModify(Model model, @RequestParam Map<String, Object> manageGoods) {
		String page	= manageGoods.get("page") == null || manageGoods.get("page").toString().trim().equals("") ? "1" : manageGoods.get("page").toString().trim();
		String searchColumn	= manageGoods.get("searchColumn") == null || manageGoods.get("searchColumn").toString().trim().equals("") ? "" : manageGoods.get("searchColumn").toString().trim();
		String searchString	= manageGoods.get("searchString") == null || manageGoods.get("searchString").toString().trim().equals("") ? "" : manageGoods.get("searchString").toString().trim();
		String cpCode	= manageGoods.get("cpCode") == null || manageGoods.get("cpCode").toString().trim().equals("") ? "" : manageGoods.get("cpCode").toString().trim();
		
		String script = "alert('%s');";
		
		try {

			manageGoodsService.modify(manageGoods);
			
			script  = String.format(script, "정상적으로 수정 처리 되었습니다.");
			script += "location.href = '/customer/manageGoodsList?page=" + page  
					+ "&searchColumn=" + searchColumn 
					+ "&searchString=" + searchString 
					+ "&cpCode=" + cpCode + "';"; 
	
		} catch(Exception e) {
			
			model.addAttribute("manageGoods", manageGoods);
			script  = String.format(script, "수정 과정중 오류가 발생하였습니다.\\n다시 시도하여 주십시오.");
		}
		
		model.addAttribute("script", script);
		
		return "/customer/manageGoodsModify";		
	}
	
	@ResponseBody
	@RequestMapping("/checkManageGoodsFormData")
	public Map<String, Object> checkManageGoodsFormData(@RequestParam Map<String, Object> searchInfo) {
		Map<String, Object> result = new HashMap<>();
		
		try {
			int cnt = manageGoodsService.checkManageGoodsFormData(searchInfo);
			if(cnt > 0){
				result.put("code", 201);
				result.put("msg", "equal");
			}else{
				result.put("code", 200);
				result.put("msg", "success");
			}
		} catch(Exception e) {
			result.put("code", 500);
			result.put("msg", "server error : " + e.getMessage());
		}
		
		return result;
	}
}
