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
import com.kt.giga.home.cms.monitor.service.SignalCheckService;
@Controller
@RequestMapping("/manage")
public class BetaVerFrmwrController {
	
	@Autowired
	private HttpServletRequest request;
	
	@Autowired
	private BetaVerFrmwrService betaVerFrmwrService;
	
	@Autowired
	private SignalCheckService signalCheckService; 
	
	@Autowired
	private CodeService codeService;
	
	@Autowired
	private OpenAPICallService openAPICallService;
	
	@RequestMapping("/betaVerFrmwrList")
	public String getBetaVerFrmwrList(Model model,@RequestParam Map<String, Object> searchInfo) {	
		
		int page, pageSize = 10;			
		page = searchInfo.get("page") == null || searchInfo.get("page").toString().trim().equals("") ? 1 : Integer.parseInt(searchInfo.get("page").toString().trim());
		
		//업데이트 여부 
		String update_require= "필수"; 
		String update_select= "선택";
		
		searchInfo.put("page"			, page);
		searchInfo.put("pageSize"		, pageSize);
		
		int verFrmwrCount = betaVerFrmwrService.getCount(searchInfo);
		List<Map<String, Object>> verFrmwrList = betaVerFrmwrService.getList(searchInfo);
		
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
		
		pageNavi.setAction("/manage/betaVerFrmwrList");
		pageNavi.setTotalCount(verFrmwrCount);
		pageNavi.setPageSize(pageSize);
		pageNavi.setNowPage(page);
		pageNavi.make();
		
		model.addAttribute("verFrmwrList"	, verFrmwrList);
		model.addAttribute("pageNavi"	, pageNavi);

		return "/manage/betaVerFrmwrList";
	}
	
	@RequestMapping("/betaVerFrmwrRegister")
	public String betaVerFrmwrRegisterForm(Model model, @RequestParam Map<String, Object> searchInfo) {
		
		SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd");
		searchInfo.put("start_date", sdf.format(new Date()));
		model.addAttribute("verFrmwr", searchInfo);
		
		return "/manage/betaVerFrmwrRegister";
	}
	
	// @RequestMapping 의 params 옵션은 동일한 URI를 호출하여도 선택적으로 어떠한 Controller 의 메서드를 
	// 실행할지를 결정한다. 위의 generalverFrmwrRegisterForm 메서드와 아래의 generalverFrmwrRegister 메서드는 
	// 동일한 URI를 가졌으나 method 라는 변수에 register 값이 담겨있으면 아래의 메서드가 실행된다ㅏ.
	@RequestMapping(value="/betaVerFrmwrRegister", params="method=register")
	public String betaVerFrmwrRegister(Model model, @RequestParam Map<String, Object> verFrmwr) {

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
			betaVerFrmwrService.register(verFrmwr);
			openAPICallService.updateOpenAPIInit();
			script  = String.format(script, "정상적으로 등록 처리 되었습니다.");
			script += "location.href = '/manage/betaVerFrmwrList';"; 			
		} catch(Exception e) {
			model.addAttribute("verFrmwr", verFrmwr);
			script  = String.format(script, "등록 과정중 오류가 발생하였습니다.\\n다시 시도하여 주십시오.");
		}
		
		model.addAttribute("script", script);		
		
		return "/manage/betaVerFrmwrRegister";
	}
	
	@RequestMapping("/betaVerFrmwrModify")
	public String betaVerFrmwrModifyForm(Model model, @RequestParam Map<String, Object> searchInfo) {
		
		String startTime= "";
		String getTime= ""; 	
		
		LoginInfo loginInfo = (LoginInfo)request.getAttribute("loginInfo");
		Map<String, Object> verFrmwr = new HashMap<String, Object>();
		
		try {
			//테스트 펌웨어 정보 가져온다.
			searchInfo.put("id", loginInfo.getId());
			verFrmwr = betaVerFrmwrService.get(searchInfo);
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		
		try {
			getTime= verFrmwr.get("startTime").toString().trim().substring(0, 12);
			SimpleDateFormat format= new SimpleDateFormat("yyyyMMddHHmm");		
			Date getdate= format.parse(getTime);
			SimpleDateFormat new_format= new SimpleDateFormat("yyyy년 MM월 dd일 hh:mm a ", Locale.US);
			startTime= new_format.format(getdate);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		verFrmwr.put("startTime", startTime); // put 	
		model.addAttribute("verFrmwr", verFrmwr);
		
		return "/manage/betaVerFrmwrModify";
	}
	
	@RequestMapping(value="/betaVerFrmwrModify" , params="method=modify")
	public String betaVerFrmwrModify(Model model, @RequestParam Map<String, Object> verFrmwr) {
		
		LoginInfo loginInfo = (LoginInfo)request.getAttribute("loginInfo");
		String script = "alert('%s');";
		String modifierIp = request.getRemoteAddr();
		
		verFrmwr.put("modifierId", loginInfo.getId());
		verFrmwr.put("modifierIp", modifierIp);
		verFrmwr.put("updateDate", new Date());
		
		try {			
			betaVerFrmwrService.modify(verFrmwr);
			openAPICallService.updateOpenAPIInit();
			script  = String.format(script, "정상적으로 수정 처리 되었습니다.");
			script += "location.href = '/manage/betaVerFrmwrList';"; 			
		} catch(Exception e) {
			model.addAttribute("verFrmwr", verFrmwr);
			script  = String.format(script, "수정 과정중 오류가 발생하였습니다.\\n다시 시도하여 주십시오.");
			e.printStackTrace();
		}
		
		model.addAttribute("script", script);		
		
		return "/manage/betaVerFrmwrModify";		
	}
	
	@ResponseBody
	@RequestMapping("/checkBetaVerFrmwrFormData")
	public Map<String, Object> checkFormData(@RequestParam Map<String, Object> searchInfo) {
		Map<String, Object> result = new HashMap<>();
		String rtnStr = betaVerFrmwrService.checkVerFrmwrFormData(searchInfo);
		
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
	@RequestMapping("/removeBetaVerFrmwr")
	public Map<String, Object> removeVerFrmwr(@RequestParam Map<String, Object> verAppList) {
		//
		Map<String, Object> result = new HashMap<>(); 
		Map<String, Object> welcome = new HashMap<>(); 
		
		try {
			String[] pkList = verAppList.get("pkList").toString().split(",");
			
			for(String pkStr : pkList) {
				welcome.put("pk", Integer.valueOf(pkStr));
				betaVerFrmwrService.remove(welcome);
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
