package com.kt.giga.home.cms.manage.controller;

import java.text.SimpleDateFormat;
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
import com.kt.giga.home.cms.common.service.OpenAPICallService;
import com.kt.giga.home.cms.manage.service.*;
@Controller
@RequestMapping("/manage")
public class VerFrmwrController {
	
	@Autowired
	private HttpServletRequest request;
	
	@Autowired
	private VerFrmwrService verFrmwrService;
	
	@Autowired
	private CodeService codeService;
	
	@Autowired
	private OpenAPICallService openAPICallService;
	
	@RequestMapping("/verFrmwrList")
	public String getVerFrmwrList(Model model,@RequestParam Map<String, Object> searchInfo) {	
		String proc	= searchInfo.get("proc") == null || searchInfo.get("proc").toString().trim().equals("") ? "" : searchInfo.get("proc").toString().trim();
		String cpCode	= searchInfo.get("cpCode") == null || searchInfo.get("cpCode").toString().trim().equals("") ? "" : searchInfo.get("cpCode").toString().trim();
		
		int page, pageSize = 10;			
		page = searchInfo.get("page") == null || searchInfo.get("page").toString().trim().equals("") ? 1 : Integer.parseInt(searchInfo.get("page").toString().trim());
		
		//업데이트 여부 
		String update_require= "필수"; 
		String update_select= "선택";
		
		LoginInfo loginInfo = (LoginInfo)request.getAttribute("loginInfo");
		searchInfo.put("id"				, loginInfo.getId());
		searchInfo.put("cpCode"			, cpCode);
		searchInfo.put("page"			, page);
		searchInfo.put("pageSize"		, pageSize);
		
		int verFrmwrCount = verFrmwrService.getCount(searchInfo);
		List<Map<String, Object>> verFrmwrList = verFrmwrService.getList(searchInfo);
		
		List<Code> serviceList = codeService.getList("1200");
		
		Map<String, String> serviceMap = new HashMap<>();
		for(Code service : serviceList) {
			serviceMap.put(service.getName(), service.getMemo());
		}
		
		for(Map<String, Object> verFrmwr : verFrmwrList){
			verFrmwr.put("cpName", serviceMap.get(verFrmwr.get("cpCode")).toString());
			//업데이트 여부 
			if(verFrmwr.get("mandatoryYN").toString().compareTo("Y") == 0){
				verFrmwr.put("mandatoryYN", update_require);
				
			}else if(verFrmwr.get("mandatoryYN").toString().compareTo("N") == 0){
					verFrmwr.put("mandatoryYN", update_select); 
					
			}else{
					verFrmwr.put("mandatoryYN", "알 수 없음");
			}
		}
		
		PageNavi pageNavi = new PageNavi();
		
		pageNavi.setAction("/manage/verFrmwrList");
		pageNavi.setTotalCount(verFrmwrCount);
		pageNavi.setPageSize(pageSize);
		pageNavi.setNowPage(page);
		pageNavi.setParameters("cpCode", cpCode);	
		pageNavi.make();
		
		model.addAttribute("verFrmwrList"	, verFrmwrList);
		model.addAttribute("pageNavi"	, pageNavi);
		model.addAttribute("proc"	, proc);
		model.addAttribute("cpCode"	, cpCode);	

		return "/manage/verFrmwrList";
	}
	
	@RequestMapping("/verFrmwrRegister")
	public String verFrmwrRegisterForm(Model model) {
		
		HashMap<String, String> verFrmwr = new HashMap<String, String>();
		
		SimpleDateFormat sdf= new SimpleDateFormat("yyyyMMddHHmmss");
	
		String startTime= sdf.format(new Date());					
		String s_year= startTime.substring(0, 4); 
		String s_month= startTime.substring(4, 6); 
		String s_day= startTime.substring(6, 8);
		String s_sub= s_year + "-" + s_month + "-" + s_day;
		
		verFrmwr.put("startTime", s_sub);	
			
		model.addAttribute("verFrmwr", verFrmwr);
		
		return "/manage/verFrmwrRegister";
	}
	
	// @RequestMapping 의 params 옵션은 동일한 URI를 호출하여도 선택적으로 어떠한 Controller 의 메서드를 
	// 실행할지를 결정한다. 위의 generalverFrmwrRegisterForm 메서드와 아래의 generalverFrmwrRegister 메서드는 
	// 동일한 URI를 가졌으나 method 라는 변수에 register 값이 담겨있으면 아래의 메서드가 실행된다ㅏ.
	@RequestMapping(value="/verFrmwrRegister", params="method=register")
	public String verFrmwrRegister(Model model, @RequestParam Map<String, Object> verFrmwr) {

		LoginInfo loginInfo = (LoginInfo)request.getAttribute("loginInfo");
		String script = "alert('%s');";
		String writerIp = request.getRemoteAddr();
		String start = "";
		String s_hour = "";
		String s_minute = "";
		
		verFrmwr.put("writerId", loginInfo.getId());
		verFrmwr.put("writerIp", writerIp);
		verFrmwr.put("regDate", new Date());
		
		if(verFrmwr.get("start_date") != null && verFrmwr.get("s_hour") != null){
			start = verFrmwr.get("start_date").toString();  
			
			if(Integer.parseInt(verFrmwr.get("s_hour").toString()) < 10){	// start hour  
				s_hour = "0" + verFrmwr.get("s_hour").toString();
			}else{ s_hour = verFrmwr.get("s_hour").toString(); }
			
			if(Integer.parseInt(verFrmwr.get("s_minute").toString()) < 10){ // end hour 
				s_minute = "0" + verFrmwr.get("s_minute").toString();
			}else{ s_minute = verFrmwr.get("s_minute").toString(); }
			
			String start_date = start.replace("-", "") + s_hour + s_minute + "00";
			verFrmwr.put("start_date", start_date);		// total 
		}
		
		try {			
			verFrmwrService.register(verFrmwr);
			openAPICallService.updateOpenAPIInit();
			script  = String.format(script, "정상적으로 등록 처리 되었습니다.");
			script += "location.href = '/manage/verFrmwrList';"; 			
		} catch(Exception e) {
			model.addAttribute("verFrmwr", verFrmwr);
			script  = String.format(script, "등록 과정중 오류가 발생하였습니다.\\n다시 시도하여 주십시오.");
		}
		
		model.addAttribute("script", script);		
		
		return "/manage/verFrmwrRegister";
	}
	
	@RequestMapping("/verFrmwrModify")
	public String verFrmwrModifyForm(Model model, @RequestParam Map<String, Object> searchInfo) throws java.text.ParseException {
		String startTime= "";
		String getTime= ""; 	
		LoginInfo loginInfo = (LoginInfo)request.getAttribute("loginInfo");
		
		searchInfo.put("id", loginInfo.getId());
		Map<String, Object> verFrmwr = verFrmwrService.get(searchInfo);
		String cpName = codeService.getServiceName(verFrmwr.get("cpCode").toString());

		try {
			//yyyyMMddHHmmss00 을 yyyy년 MM월 dd일 hh:mm: am/pm 형식으로 변환
			getTime= verFrmwr.get("startTime").toString().trim().substring(0, 12); // 년월일시분
			SimpleDateFormat format= new SimpleDateFormat("yyyyMMddHHmm");		
			Date getdate= format.parse(getTime);
			SimpleDateFormat new_format= new SimpleDateFormat("yyyy년 MM월 dd일 hh:mm a ", Locale.US); // am,pm 으로 변환
			startTime= new_format.format(getdate);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		verFrmwr.put("startTime", startTime); // put 
		verFrmwr.put("cpName", cpName);				
		model.addAttribute("verFrmwr", verFrmwr);
		
		
		return "/manage/verFrmwrModify";
	}
	
	@RequestMapping(value="/verFrmwrModify" , params="method=modify")
	public String verFrmwrModify(Model model, @RequestParam Map<String, Object> verFrmwr) {
		LoginInfo loginInfo = (LoginInfo)request.getAttribute("loginInfo");
		String page	= verFrmwr.get("page") == null || verFrmwr.get("page").toString().trim().equals("") ? "1" : verFrmwr.get("page").toString().trim();
		String cpCode	= verFrmwr.get("cpCode") == null || verFrmwr.get("cpCode").toString().trim().equals("") ? "" : verFrmwr.get("cpCode").toString().trim();
		String script = "alert('%s');";
		String modifierIp = request.getRemoteAddr();
		String modifyId= loginInfo.getId(); 
		String mandatoryYN = verFrmwr.get("mandatoryYN") == null || verFrmwr.get("mandatoryYN").toString().trim().equals("") ? "" : verFrmwr.get("mandatoryYN").toString().trim();
		
		verFrmwr.put("modifierId", modifyId);
		verFrmwr.put("modifierIp", modifierIp);
		verFrmwr.put("updateDate", new Date());
		verFrmwr.put("mandatoryYN", mandatoryYN);
		
		try {
			verFrmwrService.modify(verFrmwr);
			openAPICallService.updateOpenAPIInit();
			script  = String.format(script, "정상적으로 수정 처리 되었습니다.");
			script += String.format("location.href = '/manage/verFrmwrList?&page=" + page 
													+ "&cpCode=" 				   + cpCode 
													+ "';");			
		} catch(Exception e) {
			model.addAttribute("verFrmwr", verFrmwr);
			script  = String.format(script, "수정 과정중 오류가 발생하였습니다.\\n다시 시도하여 주십시오.");
		}
		model.addAttribute("script", script);	
		
		return "/manage/verFrmwrModify";		
	}
	
	@ResponseBody
	@RequestMapping("/checkVerFrmwrFormData")
	public Map<String, Object> checkFormData(@RequestParam Map<String, Object> searchInfo) {
		Map<String, Object> result = new HashMap<>();
		String rtnStr = verFrmwrService.checkVerFrmwrFormData(searchInfo);
		
		try {
			if(rtnStr.equals("success")){
				result.put("code", 200);
				result.put("msg", "success");
			}else if(rtnStr.equals("lowVer")){
				result.put("code", 201);
				result.put("msg", "lowVer");
			}else if(rtnStr.equals("equalVer")){
				result.put("code", 202);
				result.put("msg", "equalVer");
			}
		} catch(Exception e) {
			result.put("code", 500);
			result.put("msg", "server error : " + e.getMessage());
		}
		
		return result;
	}
	
	@ResponseBody 
	@RequestMapping("/removeVerFrmwr")
	public Map<String, Object> removeVerFrmwr(@RequestParam Map<String, Object> verAppList) {
		//
		Map<String, Object> result = new HashMap<>(); 
		Map<String, Object> welcome = new HashMap<>(); 
		
		try {
			String[] pkList = verAppList.get("pkList").toString().split(",");
			
			for(String pkStr : pkList) {
				welcome.put("pk", Integer.valueOf(pkStr));
				verFrmwrService.remove(welcome);
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
