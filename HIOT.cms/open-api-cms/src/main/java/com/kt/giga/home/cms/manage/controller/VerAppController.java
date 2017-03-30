package com.kt.giga.home.cms.manage.controller;


import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.*;

import javax.servlet.http.*;

import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.beans.factory.annotation.*;

import com.kt.giga.home.cms.util.*;
import com.kt.giga.home.cms.common.domain.Code;
import com.kt.giga.home.cms.common.domain.LoginInfo;
import com.kt.giga.home.cms.common.service.APIEnv;
import com.kt.giga.home.cms.common.service.CodeService;
import com.kt.giga.home.cms.common.service.OpenAPICallService;
import com.kt.giga.home.cms.manage.service.*;
import com.kt.giga.home.cms.manage.view.JxlsExcelViewVerAppList;

@Controller
@RequestMapping("/manage")
public class VerAppController {
	
	@Autowired
	private HttpServletRequest request;
	
	@Autowired
	private VerAppService verAppService;
	
	@Autowired
	private CodeService codeService;
	
	@Autowired
	private APIEnv apiEnv;
	
	@Autowired
	private OpenAPICallService openAPICallService;
	
	@RequestMapping("/verAppList")
	public String getVerAppList(Model model, @RequestParam Map<String, Object> searchInfo) {		
		String cpCode	= searchInfo.get("cpCode") == null || searchInfo.get("cpCode").toString().trim().equals("") ? "" : searchInfo.get("cpCode").toString().trim();
		String osCode	= searchInfo.get("osCode") == null || searchInfo.get("osCode").toString().trim().equals("") ? "" : searchInfo.get("osCode").toString().trim();
		int page, pageSize = 10;		
		page = searchInfo.get("page") == null || searchInfo.get("page").toString().trim().equals("") ? 1 : Integer.parseInt(searchInfo.get("page").toString().trim());
			
/*		// 업데이트 여부 
		String update_require= "필수"; 
		String update_select= "선택";
		*/
		LoginInfo loginInfo = (LoginInfo)request.getAttribute("loginInfo");
		searchInfo.put("id"				, loginInfo.getId());
		searchInfo.put("cpCode"			, cpCode);
		searchInfo.put("osCode"			, osCode);
		searchInfo.put("page"			, page);
		searchInfo.put("pageSize"		, pageSize);
		
		int verAppCount = verAppService.getCount(searchInfo);
		List<Map<String, Object>> verAppList = verAppService.getList(searchInfo);
		List<Code> osList = codeService.getList("1700");
		List<Code> phList = codeService.getList("1800");

/*		for(Map<String, Object> verApp : verAppList){

			if(verApp.get("mandatoryYN").toString().compareTo("Y") == 0){
				verApp.put("mandatoryYN", update_require);
			}else if(verApp.get("mandatoryYN").toString().compareTo("N") == 0){
				verApp.put("mandatoryYN", update_select); 
			}else{
				verApp.put("mandatoryYN", "알 수 없음");
			}
		}*/
		
		List<Code> serviceList = codeService.getList("1200");
		
		Map<String, String> serviceMap = new HashMap<>();
		for(Code service : serviceList) {
			serviceMap.put(service.getName(), service.getMemo());
		}
		
		for(Map<String, Object> verApp : verAppList) {
			verApp.put("cpName", serviceMap.get(verApp.get("cpCode")).toString());
		}

		PageNavi pageNavi = new PageNavi();
		
		pageNavi.setAction("/manage/verAppList");
		pageNavi.setTotalCount(verAppCount);
		pageNavi.setPageSize(pageSize);
		pageNavi.setNowPage(page);
		pageNavi.setParameters("cpCode", cpCode);
		pageNavi.setParameters("osCode", osCode);
		pageNavi.make();
		
		model.addAttribute("cpCode"	, cpCode);	
		model.addAttribute("verAppList"	, verAppList);
		model.addAttribute("osList"	, osList);
		model.addAttribute("phList"	, phList);	
		model.addAttribute("pageNavi"	, pageNavi);
		
		return "/manage/verAppList";
	}
	
	@RequestMapping(value="/verAppList", params="proc=excel") //proc 가 excel 일때 excel 다운 
	public ModelAndView excelVerAppList(Model model, @RequestParam Map<String, Object> searchInfo) {

		// 업데이트 여부 
		String update_require= "필수"; 
		String update_select= "선택";
		
		LoginInfo loginInfo = (LoginInfo)request.getAttribute("loginInfo");
		searchInfo.put("id"				, loginInfo.getId());
		
		List<Map<String, Object>> verAppList = verAppService.getList(searchInfo);
		List<Code> serviceList = codeService.getList("1200");
		List<Code> osList = codeService.getList("1700");
		List<Code> phList = codeService.getList("1800");
		
		Map<String, String> serviceMap = new HashMap<>();
		for(Code service : serviceList) {
			serviceMap.put(service.getName(), service.getMemo());
		}

		for(Map<String, Object> verApp : verAppList){
			
			verApp.put("cpName", serviceMap.get(verApp.get("cpCode")).toString());
			
			for(Code code : osList){
				if(verApp.get("osCode").toString().equals(code.getCode().toString())){
					verApp.put("osCode", code.getName().toString());
				}
			}
			
			for(Code code : phList){
				if(verApp.get("phCode").toString().equals(code.getCode().toString())){
					verApp.put("phCode", code.getName().toString());
				}
			}
			
			// 업데이트 여부
			// excel에 필수, 선택 여부가 출력 되야함 원래는 YN 임 
			if(verApp.get("mandatoryYN").toString().compareTo("Y") == 0){
				verApp.put("mandatoryYN", update_require);
			}else if(verApp.get("mandatoryYN").toString().compareTo("N") == 0){
				verApp.put("mandatoryYN", update_select); 
			}else{
				verApp.put("mandatoryYN", "알 수 없음");
			}
		}

		ModelAndView mav = new ModelAndView(new JxlsExcelViewVerAppList());
		mav.addObject("verAppList", verAppList);
		
		return mav;
	}
	
	@RequestMapping("/verAppRegister")
	public String verAppRegisterForm(Model model) {
		
		List<Code> osList = codeService.getList("1700");
		List<Code> phList = codeService.getList("1800");
		
		model.addAttribute("osList", osList);
		model.addAttribute("phList", phList);
		
		return "/manage/verAppRegister";
	}
	
	@RequestMapping(value="/verAppRegister", params="method=register")
	public String verAppRegister(Model model, MultipartHttpServletRequest multipartRequest, @RequestParam Map<String, Object> manage) {
		
		// 입력 받은 버전 
		String inputVer= manage.get("verNum") == null || manage.get("verNum").toString().trim().equals("") ? "" : manage.get("verNum").toString().trim();
		// 입력 받은 버전 	inputVer : n1.n2n3.n4n5.n6n7 
		String inputVerFirstNum = inputVer.substring(0, 1);	// n1
		String inputVerSecondNum = inputVer.substring(2, 4); // n2n3 				
		// SELECT verNum FROM verAPP테이블 WHERE cpCode, osCode, phCode 의 가장 최신에 입력 된 버전
		String preVerNum= verAppService.verAppUpdateCheck(manage); 
		
		// 등록 되어 있는 비교한 버전이 있는 경우 input버전과 비교 
		if(preVerNum != null && !preVerNum.equals("")){
				String preVerFirstNum= preVerNum.trim().substring(0, 1); // 저장 된 버전 n1.n2n3.n4n5.n6n7의 n1
				String preVerSecondNum= preVerNum.trim().substring(2, 4); // n2n3
				// 첫번쨰와 두번째 자리의 자리수가 다른경우 업데이트 필수  
				if( !preVerFirstNum.equals(inputVerFirstNum) || !preVerSecondNum.equals(inputVerSecondNum) ){
					manage.put("mandatoryYN", "Y");
				}else{// 첫번쨰와 두번째 자리의 자리수가 같은경우 업데이트 선택 			
					manage.put("mandatoryYN", "N");				
				}
				
		}else{// 등록 되어있는 버전이 없을때 업데이트 필수 
			manage.put("mandatoryYN", "Y");
		}
		
		String script = "alert('%s');";
		String writerIp = request.getRemoteAddr();	
		String writerId = ((LoginInfo)request.getAttribute("loginInfo")).getId();
		String uploadPath = request.getServletContext().getInitParameter("appPath");
		String realPath = new File(uploadPath).getAbsolutePath();
		String rootPathType = request.getServletContext().getInitParameter("rootPathType");
		
		if(rootPathType.equals("virtual")) {
			//realPath = request.getServletContext().getRealPath(uploadPath);
			realPath = request.getServletContext().getInitParameter("uploadPath") + request.getServletContext().getInitParameter("appPath");
		}
		
		//한자리 정수 앞에 0 이 붙을경우 제거
		/*String verNum = "";
		String[] verArryStr = manage.get("verNum").toString().split("\\.");
		if(verArryStr.length == 4){
			verNum = 	Integer.toString(Integer.parseInt(verArryStr[0])) + "." + 
							Integer.toString(Integer.parseInt(verArryStr[1])) + "." + 
							Integer.toString(Integer.parseInt(verArryStr[2])) + "." + 
							Integer.toString(Integer.parseInt(verArryStr[3]));
		}
		manage.put("verNum", verNum);*/
		
		manage.put("writerIp"		, writerIp);
		manage.put("writerId"		, writerId);
		manage.put("virtualPath"	, uploadPath);
		manage.put("realPath"		, realPath);
		manage.put("regDate"		, new Date());
		manage.put("files", multipartRequest.getFileMap());				
		
		try {
			verAppService.register(manage);
			openAPICallService.updateOpenAPIInit();
			script  = String.format(script, "정상적으로 등록 처리 되었습니다.");
			script += String.format("location.href = '/manage/verAppList';"); 			
			
		} catch(Exception e) {
			model.addAttribute("manage", manage);
			script  = String.format(script, "등록 과정중 오류가 발생하였습니다.\\n다시 시도하여 주십시오.");
			e.printStackTrace();
		}
		
		model.addAttribute("script", script);
		
		return "/manage/verAppRegister";
	}
	
	@RequestMapping("/verAppModify")
	public String verAppModifyForm(Model model, @RequestParam Map<String, Object> searchInfo) {
		System.out.println("modify의 mandatoryYN :" + searchInfo.get("mandatoryYN".toString()));
		
		List<Code> osList = codeService.getList("1700");
		List<Code> phList = codeService.getList("1800");
		
		LoginInfo loginInfo = (LoginInfo)request.getAttribute("loginInfo");
		searchInfo.put("id", loginInfo.getId());
		
		Map<String, Object> verApp = verAppService.get(searchInfo);
		String mandatoryYN = verApp.get("mandatoryYN").toString().trim();

		String cpName = codeService.getServiceName(verApp.get("cpCode").toString());
		verApp.put("cpName", cpName);
		
		String domain = apiEnv.getProperty("www.domain");
		String port = apiEnv.getProperty("www.port");
		verApp.put("domain", domain);
		verApp.put("port", port);
		
		model.addAttribute("osList", osList);
		model.addAttribute("phList", phList);
		model.addAttribute("verApp", verApp);
		model.addAttribute("mandatoryYN", mandatoryYN);

		return "/manage/verAppModify";
	}
	
	@RequestMapping(value="/verAppModify" , params="method=modify")
	public String verAppModify(Model model, MultipartHttpServletRequest multipartRequest, @RequestParam Map<String, Object> verApp) {

		String script = "alert('%s');";
		String modifierIp = request.getRemoteAddr();		
		String modifierId = ((LoginInfo)request.getAttribute("loginInfo")).getId();
		String uploadPath = request.getServletContext().getInitParameter("appPath");
		String realPath = new File(uploadPath).getAbsolutePath();
		String rootPathType = request.getServletContext().getInitParameter("rootPathType");		
		
		if(rootPathType.equals("virtual")) {
			//realPath = request.getServletContext().getRealPath(uploadPath);
			realPath = request.getServletContext().getInitParameter("uploadPath") + request.getServletContext().getInitParameter("appPath");
		}		
		
		String osCode	= verApp.get("osCode")	!= null	? verApp.get("osCode").toString().trim() : "";
		String cpCode	= verApp.get("cpCode")	!= null	? verApp.get("cpCode").toString().trim() : "";
		int page = verApp.get("page") == null || verApp.get("page").toString().trim().equals("") ? 1 : Integer.parseInt(verApp.get("page").toString().trim());
			
		verApp.put("modifierId"	, modifierId);
		verApp.put("modifierIp"	, modifierIp);
		verApp.put("virtualPath"	, uploadPath);
		verApp.put("realPath"		, realPath);
		verApp.put("updateDate"	, new Date());
		verApp.put("files", multipartRequest.getFileMap());	
		
		try {
			verAppService.modify(verApp);
			openAPICallService.updateOpenAPIInit();
			script  = String.format(script, "정상적으로 수정 처리 되었습니다.");
			script += String.format("location.href = '/manage/verAppList?" 
											+ "&page=" + page  
											+ "&osCode=" + osCode
											+ "&cpCode=" + cpCode 
											+ "';");
		} catch(Exception e) {
			model.addAttribute("verApp", verApp);
			script  = String.format(script, "수정 과정중 오류가 발생하였습니다.\\n다시 시도하여 주십시오.");
			e.printStackTrace();
		}
		
		model.addAttribute("script", script);
		
		return "/manage/verAppModify";
	}
	
	@ResponseBody
	@RequestMapping("/checkVerAppFormData")
	public Map<String, Object> checkFormData(@RequestParam Map<String, Object> searchInfo) {
		Map<String, Object> result = new HashMap<>();
		String rtnStr = verAppService.checkVerAppFormData(searchInfo);
		
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
	
	@ResponseBody 
	@RequestMapping("/removeVerApp")
	public Map<String, Object> removeVerApp(@RequestParam Map<String, Object> verAppList) {
		//
		Map<String, Object> result = new HashMap<>(); 
		Map<String, Object> welcome = new HashMap<>(); 
		
		try {
			String[] pkList = verAppList.get("pkList").toString().split(",");
			
			for(String pkStr : pkList) {
				welcome.put("pk", Integer.valueOf(pkStr));
				verAppService.remove(welcome);
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
