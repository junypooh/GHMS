package com.kt.giga.home.cms.terms.controller;

import java.util.*;

import javax.servlet.http.*;

import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.*;

import com.kt.giga.home.cms.common.domain.Code;
import com.kt.giga.home.cms.common.domain.LoginInfo;
import com.kt.giga.home.cms.common.service.CodeService;
import com.kt.giga.home.cms.terms.service.ThirdPersTermsService;
import com.kt.giga.home.cms.util.*;

@Controller
@RequestMapping("/terms")
public class ThirdPersTermsController {
	
	// HttpServletRequest 는 Web 및 spring mvc 의존성을 지니기 때문에 Service 가 아닌
	// Controller 에서만 되도록 사용하며, 될수있는한 Service 레이어에서 사용하지 않도록 한다.
	@Autowired
	private HttpServletRequest request;
	
	@Autowired
	private ThirdPersTermsService thirdPersTermsService;
	
	@Autowired
	private CodeService codeService;
	
	@RequestMapping("/thirdPersTermsList")
	public String getThirdPersTermssList(Model model, @RequestParam Map<String, Object> searchInfo) {
		
		int page, pageSize = 10;
		
		String cpCode	= searchInfo.get("cpCode") == null || searchInfo.get("cpCode").toString().trim().equals("") ? "" : searchInfo.get("cpCode").toString().trim();
		page = searchInfo.get("page") == null || searchInfo.get("page").toString().trim().equals("") ? 1 : Integer.parseInt(searchInfo.get("page").toString().trim());
		
		LoginInfo loginInfo = (LoginInfo)request.getAttribute("loginInfo");
		
		searchInfo.put("id"				, loginInfo.getId());
		searchInfo.put("page"			, page);
		searchInfo.put("pageSize"		, pageSize);
		
		int termsCount = thirdPersTermsService.getCount(searchInfo);
		List<Map<String, Object>> termsList = thirdPersTermsService.getList(searchInfo);
		List<Code> serviceList = codeService.getList("1200");
		
		Map<String, String> serviceMap = new HashMap<>();
		for(Code service : serviceList) {
			serviceMap.put(service.getName(), service.getMemo());
		}
		
		for(Map<String, Object> terms : termsList) {
			terms.put("cpName", serviceMap.get(terms.get("cpCode")).toString());
		}
		
		PageNavi pageNavi = new PageNavi();
		
		pageNavi.setAction("/terms/thirdPersTermsList");
		pageNavi.setTotalCount(termsCount);
		pageNavi.setPageSize(pageSize);
		pageNavi.setNowPage(page);
		pageNavi.setParameters("cpCode", cpCode);
		pageNavi.make();
		
		model.addAttribute("termsList"	, termsList);
		model.addAttribute("pageNavi"	, pageNavi);
		
		return "/terms/thirdPersTermsList";
	}
	
	@ResponseBody
	@RequestMapping("/checkThirdTermsFormData")
	public Map<String, Object> checkInfoCollectFormData(@RequestParam Map<String, Object> terms) {
		Map<String, Object> result = new HashMap<>();
		
		terms.put("termsCode", "1303"); //제3자 취급위탁 약관
		
		String rtnStr= thirdPersTermsService.checkThirdTermsFormData(terms);
		
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
	
	@RequestMapping("/thirdPersTermsRegister")
	public String thirdPersTermsRegisterForm(Model model) {		
		return "/terms/thirdPersTermsRegister";
	}
	
	@RequestMapping(value="/thirdPersTermsRegister", params="method=register")
	public String thirdPersTermsRegister(Model model, @RequestParam Map<String, Object> terms) {

		String script = "alert('%s');";
		String writerIp = request.getRemoteAddr();
		LoginInfo loginInfo = (LoginInfo)request.getAttribute("loginInfo");
		
		//한자리 정수 앞에 0 이 붙을경우 제거
		/*String verNum = "";
		String[] verArryStr = terms.get("termsVer").toString().split("\\.");
		if(verArryStr.length == 3){
			verNum = 	Integer.toString(Integer.parseInt(verArryStr[0])) + "." + 
							Integer.toString(Integer.parseInt(verArryStr[1])) + "." + 
							Integer.toString(Integer.parseInt(verArryStr[2]));
		}
		terms.put("termsVer", verNum);*/
		
		terms.put("writerId", loginInfo.getId());
		terms.put("writerIp", writerIp);
		terms.put("regDate", new Date());
		terms.put("termsCode", "1303"); //제3자 취급위탁 약관
		
		try {
			thirdPersTermsService.register(terms);
			script  = String.format(script, "정상적으로 등록 처리 되었습니다.");
			script += "location.href = '/terms/thirdPersTermsList';"; 			
		} catch(Exception e) {
			model.addAttribute("terms", terms);
			script  = String.format(script, "등록 과정중 오류가 발생하였습니다.\\n다시 시도하여 주십시오.");
			e.printStackTrace();
		}
		
		model.addAttribute("script", script);
		
		return "/terms/thirdPersTermsRegister";
	}
	
	@RequestMapping("/thirdPersTermsModify")
	public String thirdPersTermsModifyForm(Model model, @RequestParam Map<String, Object> terms) {
		
		LoginInfo loginInfo = (LoginInfo)request.getAttribute("loginInfo");
		
		terms.put("id", loginInfo.getId());
		
		Map<String, Object> termsMap = thirdPersTermsService.get(terms);
		String cpName = codeService.getServiceName(termsMap.get("cpCode").toString());
		
		termsMap.put("cpName", cpName);
		
		model.addAttribute("termsMap", termsMap);
		
		return "/terms/thirdPersTermsModify";
	}
	
	@RequestMapping(value="/thirdPersTermsModify", params="method=modify")
	public String thirdPersTermsModify(Model model, @RequestParam Map<String, Object> terms) {
		
		String page	= terms.get("page") == null || terms.get("page").toString().trim().equals("") ? "1" : terms.get("page").toString().trim();
		String cpCode	= terms.get("cpCode") == null || terms.get("cpCode").toString().trim().equals("") ? "" : terms.get("cpCode").toString().trim();

		String script = "alert('%s');";
		LoginInfo loginInfo = (LoginInfo)request.getAttribute("loginInfo");
		String modifierIp = request.getRemoteAddr();
		
		terms.put("modifierId", loginInfo.getId());
		terms.put("modifierIp", modifierIp);
		terms.put("updateDate", new Date());
		
		try {
			thirdPersTermsService.modify(terms);
			script  = String.format(script, "정상적으로 수정 처리 되었습니다.");	
			script += String.format("location.href = '/terms/thirdPersTermsList?&page=" + page  
					+ "&cpCode=" + cpCode 
					+ "';");
		} catch(Exception e) {
			model.addAttribute("termsMap", terms);
			script  = String.format(script, "수정 과정중 오류가 발생하였습니다.\\n다시 시도하여 주십시오.");
			e.printStackTrace();
		}
		
		model.addAttribute("script", script);
		
		return "/terms/thirdPersTermsModify";
	}

	// @ResponseBody 어노테이션은 View Resolver(화면을 구성하는 페이지 xxx.jsp 등)가 아닌 
	// Controller 에서의 직접적인 응답 스트림을 구성할 경우 사용한다.
	// 현재는 Ajax 호출에 직접 응답을 위하여 사용
	@ResponseBody 
	@RequestMapping("/removeThirdPersTerms")
	public Map<String, Object> removeThirdPersTerms(@RequestParam Map<String, Object> termsList) {
		
		Map<String, Object> result = new HashMap<>(); 
		Map<String, Object> termsMap = new HashMap<>(); 
		
		try {
			String[] pkList = termsList.get("pkList").toString().split(",");
			
			for(String pkStr : pkList) {
				
				termsMap.put("pk", Integer.valueOf(pkStr));
				
				thirdPersTermsService.remove(termsMap);
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
