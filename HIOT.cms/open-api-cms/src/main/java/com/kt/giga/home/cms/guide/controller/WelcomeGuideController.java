package com.kt.giga.home.cms.guide.controller;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.servlet.http.*;

import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.beans.factory.annotation.*;

import com.kt.giga.home.cms.util.*;
import com.kt.giga.home.cms.common.domain.Code;
import com.kt.giga.home.cms.common.domain.LoginInfo;
import com.kt.giga.home.cms.common.service.APIEnv;
import com.kt.giga.home.cms.common.service.CodeService;
import com.kt.giga.home.cms.common.service.OpenAPICallService;
import com.kt.giga.home.cms.guide.service.*;

@Controller
@RequestMapping("/guide")
public class WelcomeGuideController {
	
	@Autowired
	private HttpServletRequest request;
	
	@Autowired
	private WelcomeGuideService welcomeGuideService;
	
	@Autowired
	private CodeService codeService;
	
	@Autowired
	private APIEnv apiEnv;
	
	@Autowired
	private OpenAPICallService openAPICallService;
	
	@RequestMapping("/guideWelcomeList")
	public String getGuideWelcomeList(Model model, @RequestParam Map<String, Object> searchInfo) {
		
		int page, pageSize = 10;
		
		page = searchInfo.get("page") == null || searchInfo.get("page").toString().trim().equals("") ? 1 : Integer.parseInt(searchInfo.get("page").toString().trim());
		String cpCode	= searchInfo.get("cpCode") == null || searchInfo.get("cpCode").toString().trim().equals("") ? "" : searchInfo.get("cpCode").toString().trim();
		String searchColumn	= searchInfo.get("searchColumn") == null || searchInfo.get("searchColumn").toString().trim().equals("") ? "" : searchInfo.get("searchColumn").toString().trim();
		String searchString	= searchInfo.get("searchString") == null || searchInfo.get("searchString").toString().trim().equals("") ? "" : searchInfo.get("searchString").toString().trim();
		
		LoginInfo loginInfo = (LoginInfo)request.getAttribute("loginInfo");
		
		searchInfo.put("id"				, loginInfo.getId());
		searchInfo.put("page"			, page);
		searchInfo.put("pageSize"		, pageSize);
		
		int welcomeCount = welcomeGuideService.getCount(searchInfo);
		List<Map<String, Object>> welcomeList = welcomeGuideService.getList(searchInfo);
		List<Code> serviceList = codeService.getList("1200");
		
		Map<String, String> serviceMap = new HashMap<>();
		for(Code service : serviceList) {
			serviceMap.put(service.getName(), service.getMemo());
		}
		
		for(Map<String, Object> welcome : welcomeList) {
			welcome.put("cpName", serviceMap.get(welcome.get("cpCode")).toString());
		}
		
		PageNavi pageNavi = new PageNavi();
		
		pageNavi.setAction("/guide/guideWelcomeList");
		pageNavi.setTotalCount(welcomeCount);
		pageNavi.setPageSize(pageSize);
		pageNavi.setNowPage(page);
		pageNavi.setParameters("cpCode", cpCode);
		pageNavi.setParameters("searchColumn", searchColumn);
		pageNavi.setParameters("searchString", searchString);
		pageNavi.make();
		
		model.addAttribute("welcomeList"	, welcomeList);
		model.addAttribute("pageNavi"	, pageNavi);
		
		return "/guide/guideWelcomeList";
	}
	
	@ResponseBody
	@RequestMapping("/updateWelcomeOpenYN")
	public Map<String, Object> updateWelcomeOpenYN(@RequestParam Map<String, Object> guide) {
		System.out.println("guide = " + guide);
		Map<String, Object> result = new HashMap<>();
		
		try {
			welcomeGuideService.updateOpenYN(guide);
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
	@RequestMapping("/getWelcomeVersionList")
	public Map<String, Object> getCameraTipVersionList(@RequestParam Map<String, Object> guide) {
		Map<String, Object> result = new HashMap<>();
		
		try {
			List<Map<String, Object>> list = welcomeGuideService.getVersionList(guide);
			result.put("list", list);
			result.put("code", 200);
			result.put("msg", "success");
		} catch(Exception e) {
			result.put("code", 500);
			result.put("msg", "server error : " + e.getMessage());
		}
		
		return result;
	}
	
	@ResponseBody
	@RequestMapping("/checkWelcomeFormData")
	public Map<String, Object> checkWelcomeFormData(@RequestParam Map<String, Object> guide) {
		Map<String, Object> result = new HashMap<>();
		String rtnStr = welcomeGuideService.checkWelcomeFormData(guide);
		
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
	
	@RequestMapping("/guideWelcomeRegister")
	public String guideWelcomeRegisterForm(Model model) {
		Map<String, Object> verMap = new HashMap<String, Object>();
		verMap.put("cpCode", "001");
		
		List<Map<String, Object>> verList = welcomeGuideService.getVersionList(verMap);
		model.addAttribute("verList", verList);
		
		return "/guide/guideWelcomeRegister";
	}
	
	@RequestMapping(value="/guideWelcomeRegister", params="method=register")
	public String guideWelcomeRegister(Model model, MultipartHttpServletRequest multipartRequest, @RequestParam Map<String, Object> guide) {

		String script = "alert('%s');";
		String writerIp = request.getRemoteAddr();	
		String writerId = ((LoginInfo)request.getAttribute("loginInfo")).getId();
		String uploadPath = request.getServletContext().getInitParameter("welcomePath");
		String realPath = new File(uploadPath).getAbsolutePath();
		String rootPathType = request.getServletContext().getInitParameter("rootPathType");
		
		if(rootPathType.equals("virtual")) {
			//realPath = request.getServletContext().getRealPath(uploadPath);
			realPath = request.getServletContext().getInitParameter("uploadPath") + request.getServletContext().getInitParameter("welcomePath");
		}
		
		/*//한자리 정수 앞에 0 이 붙을경우 제거
		String verNum = "";
		String[] verArryStr = guide.get("verNum").toString().split("\\.");
		if(verArryStr.length == 4){
			verNum = 	Integer.toString(Integer.parseInt(verArryStr[0])) + "." + 
							Integer.toString(Integer.parseInt(verArryStr[1])) + "." + 
							Integer.toString(Integer.parseInt(verArryStr[2])) + "." + 
							Integer.toString(Integer.parseInt(verArryStr[3]));
		}
		guide.put("verNum", verNum);*/
		
		guide.put("writerIp"		, writerIp);
		guide.put("writerId"		, writerId);
		guide.put("virtualPath"	, uploadPath);
		guide.put("realPath"		, realPath);
		guide.put("regDate"		, new Date());
		guide.put("images", multipartRequest.getFileMap());				
		
		try {
			welcomeGuideService.register(guide);
			openAPICallService.updateOpenAPIInit();
			script  = String.format(script, "정상적으로 등록 처리 되었습니다.");
			script += String.format("location.href = '/guide/guideWelcomeList';"); 			
			
		} catch(Exception e) {
			model.addAttribute("guide", guide);
			script  = String.format(script, "등록 과정중 오류가 발생하였습니다.\\n다시 시도하여 주십시오.");
			e.printStackTrace();
		}
		
		model.addAttribute("script", script);
		
		return "/guide/guideWelcomeRegister";
	}
	
	@RequestMapping("/guideWelcomeModify")
	public String guideWelcomeModifyForm(Model model, @RequestParam Map<String, Object> guide) {
		
		LoginInfo loginInfo = (LoginInfo)request.getAttribute("loginInfo");
		
		guide.put("id", loginInfo.getId());
		
		Map<String, Object> welcome = welcomeGuideService.get(guide);
		String cpName = codeService.getServiceName(welcome.get("cpCode").toString());
		welcome.put("cpName", cpName);
		
		String domain = apiEnv.getProperty("www.domain");
		String port = apiEnv.getProperty("www.port");
		welcome.put("domain", domain);
		welcome.put("port", port);
		
		model.addAttribute("welcome", welcome);
		
		return "/guide/guideWelcomeModify";
	}
	
	@RequestMapping(value="/guideWelcomeModify", params="method=modify")
	public String guideWelcomeModify(Model model, MultipartHttpServletRequest multipartRequest, @RequestParam Map<String, Object> guide) {
		
		String script = "alert('%s');";
		String modifierIp = request.getRemoteAddr();		
		String modifierId = ((LoginInfo)request.getAttribute("loginInfo")).getId();
		String uploadPath = request.getServletContext().getInitParameter("welcomePath");
		String realPath = new File(uploadPath).getAbsolutePath();
		String rootPathType = request.getServletContext().getInitParameter("rootPathType");		
		
		if(rootPathType.equals("virtual")) {
			//realPath = request.getServletContext().getRealPath(uploadPath);
			realPath = request.getServletContext().getInitParameter("uploadPath") + request.getServletContext().getInitParameter("welcomePath");
		}		
		
		String cpCode	= guide.get("cpCode")	!= null	? guide.get("cpCode").toString().trim() : "";
		String searchColumn		= guide.get("searchColumn") 	!= null	? guide.get("searchColumn").toString().trim() : "";
		String searchString		= guide.get("searchString")	!= null	? guide.get("searchString").toString().trim() : "";
		int page = guide.get("page") == null || guide.get("page").toString().trim().equals("") ? 1 : Integer.parseInt(guide.get("page").toString().trim());
			
		guide.put("modifierId"	, modifierId);
		guide.put("modifierIp"	, modifierIp);
		guide.put("virtualPath"	, uploadPath);
		guide.put("realPath"		, realPath);
		guide.put("updateDate"	, new Date());
		guide.put("images", multipartRequest.getFileMap());	
		
		try {
			welcomeGuideService.modify(guide);
			openAPICallService.updateOpenAPIInit();
			script  = String.format(script, "정상적으로 수정 처리 되었습니다.");		
			script += String.format("location.href = '/guide/guideWelcomeList?&page=" + page  
					+ "&searchColumn=" + searchColumn
					+ "&searchString=" + searchString 
					+ "&cpCode=" + cpCode 
					+ "';");
		} catch(Exception e) {
			model.addAttribute("searchGuide", guide);
			script  = String.format(script, "수정 과정중 오류가 발생하였습니다.\\n다시 시도하여 주십시오.");
			e.printStackTrace();
		}
		
		model.addAttribute("script", script);
		
		return "/guide/guideWelcomeModify";
		
	}	
	
	@ResponseBody 
	@RequestMapping("/removeWelcomeGuide")
	public Map<String, Object> removeWelcomeGuide(@RequestParam Map<String, Object> welcomeList) {
		
		Map<String, Object> result = new HashMap<>(); 
		Map<String, Object> welcome = new HashMap<>(); 
		
		try {
			String[] pkList = welcomeList.get("pkList").toString().split(",");
			
			for(String pkStr : pkList) {
				welcome.put("pk", Integer.valueOf(pkStr));
				welcomeGuideService.remove(welcome);
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
}
