package com.kt.giga.home.cms.guide.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.servlet.http.*;

import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.beans.factory.annotation.*;

import com.kt.giga.home.cms.util.*;
import com.kt.giga.home.cms.common.domain.Code;
import com.kt.giga.home.cms.common.domain.LoginInfo;
import com.kt.giga.home.cms.common.service.APIEnv;
import com.kt.giga.home.cms.common.service.CodeService;
import com.kt.giga.home.cms.common.service.OpenAPICallService;
import com.kt.giga.home.cms.guide.service.CoachGuideService;

@Controller
@RequestMapping("/guide")
public class CoachGuideController {
	
	@Autowired
	private HttpServletRequest request;
	
	@Autowired
	private CoachGuideService coachGuideService;
	
	@Autowired
	private CodeService codeService;
	
	@Autowired
	private APIEnv apiEnv;
	
	@Autowired
	private OpenAPICallService openAPICallService;
	
	@RequestMapping("/guideCoachList")
	public String getGuideCoachList(Model model, @RequestParam Map<String, Object> searchInfo) {
		
		int page, pageSize = 10;
		
		page = searchInfo.get("page") == null || searchInfo.get("page").toString().trim().equals("") ? 1 : Integer.parseInt(searchInfo.get("page").toString().trim());
		String cpCode	= searchInfo.get("cpCode") == null || searchInfo.get("cpCode").toString().trim().equals("") ? "" : searchInfo.get("cpCode").toString().trim();
		String positionCode	= searchInfo.get("positionCode") == null || searchInfo.get("positionCode").toString().trim().equals("") ? "" : searchInfo.get("positionCode").toString().trim();
		String searchColumn	= searchInfo.get("searchColumn") == null || searchInfo.get("searchColumn").toString().trim().equals("") ? "" : searchInfo.get("searchColumn").toString().trim();
		String searchString	= searchInfo.get("searchString") == null || searchInfo.get("searchString").toString().trim().equals("") ? "" : searchInfo.get("searchString").toString().trim();
		
		LoginInfo loginInfo = (LoginInfo)request.getAttribute("loginInfo");
		
		searchInfo.put("id"				, loginInfo.getId());
		searchInfo.put("page"			, page);
		searchInfo.put("pageSize"		, pageSize);
		searchInfo.put("searchString"	, searchString);
		
		int coachCount = coachGuideService.getCount(searchInfo);
		List<Map<String, Object>> coachList = coachGuideService.getList(searchInfo);
		List<Code> codeList = codeService.getList("1600");
		List<Code> serviceList = codeService.getList("1200");
		
		Map<String, String> serviceMap = new HashMap<>();
		for(Code service : serviceList) {
			serviceMap.put(service.getName(), service.getMemo());
		}
		
		for(Map<String, Object> coach : coachList) {
			coach.put("cpName", serviceMap.get(coach.get("cpCode")).toString());
		}
		
		PageNavi pageNavi = new PageNavi();
		
		pageNavi.setAction("/guide/guideCoachList");
		pageNavi.setTotalCount(coachCount);
		pageNavi.setPageSize(pageSize);
		pageNavi.setNowPage(page);
		pageNavi.setParameters("cpCode", cpCode);
		pageNavi.setParameters("positionCode", positionCode);
		pageNavi.setParameters("searchColumn", searchColumn);
		pageNavi.setParameters("searchString", searchString);
		pageNavi.make();
		
		model.addAttribute("coachList"	, coachList);
		model.addAttribute("codeList"	, codeList);
		model.addAttribute("pageNavi"	, pageNavi);
		
		return "/guide/guideCoachList";
	}
	
	@ResponseBody
	@RequestMapping("/updateCoachOpenYN")
	public Map<String, Object> updateCoachOpenYN(@RequestParam Map<String, Object> guide) {
		Map<String, Object> result = new HashMap<>();
		
		try {
			coachGuideService.updateOpenYN(guide);
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
	@RequestMapping("/getCoachVersionList")
	public Map<String, Object> getCoachVersionList(@RequestParam Map<String, Object> guide) {
		Map<String, Object> result = new HashMap<>();
		
		try {
			List<Map<String, Object>> list = coachGuideService.getVersionList(guide);
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
	@RequestMapping("/checkCoachFormData")
	public Map<String, Object> checkCoachFormData(@RequestParam Map<String, Object> guide) {
		Map<String, Object> result = new HashMap<>();
		String rtnStr = coachGuideService.checkCoachFormData(guide);
		
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
	
	@RequestMapping("/guideCoachRegister")
	public String guideCoachRegisterForm(Model model) {
		Map<String, Object> verMap = new HashMap<String, Object>();
		verMap.put("cpCode", "001");
		
		List<Code> codeList = codeService.getList("1600");
		List<Map<String, Object>> verList = coachGuideService.getVersionList(verMap);
		
		model.addAttribute("codeList", codeList);
		model.addAttribute("verList", verList);
		
		return "/guide/guideCoachRegister";
	}
	
	@RequestMapping(value="/guideCoachRegister", params="method=register")
	public String guideCoachRegister(Model model, MultipartHttpServletRequest multipartRequest, @RequestParam Map<String, Object> guide) {
		
		String script = "alert('%s');";
		String writerIp = request.getRemoteAddr();	
		String writerId = ((LoginInfo)request.getAttribute("loginInfo")).getId();
		String uploadPath = request.getServletContext().getInitParameter("coachPath");
		String realPath = new File(uploadPath).getAbsolutePath();
		String rootPathType = request.getServletContext().getInitParameter("rootPathType");
		
		if(rootPathType.equals("virtual")) {
			//realPath = request.getServletContext().getRealPath(uploadPath);
			realPath = request.getServletContext().getInitParameter("uploadPath") + request.getServletContext().getInitParameter("coachPath");;
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
			coachGuideService.register(guide);
			openAPICallService.updateOpenAPIInit();
			script  = String.format(script, "정상적으로 등록 처리 되었습니다.");
			script += "location.href = '/guide/guideCoachList';"; 		
			
		} catch(Exception e) {
			model.addAttribute("guide", guide);
			script  = String.format(script, "등록 과정중 오류가 발생하였습니다.\\n다시 시도하여 주십시오.");
			e.printStackTrace();
		}
		
		model.addAttribute("script", script);
		
		return "/guide/guideCoachRegister";
	}
	
	@RequestMapping("/guideCoachModify")
	public String guideCoachModifyForm(Model model, @RequestParam Map<String, Object> guide) {
		
		LoginInfo loginInfo = (LoginInfo)request.getAttribute("loginInfo");
		
		guide.put("id", loginInfo.getId());
		
		Map<String, Object> coach = coachGuideService.get(guide);
		List<Code> codeList = codeService.getList("1600");
		String cpName = codeService.getServiceName(coach.get("cpCode").toString());
		coach.put("cpName", cpName);
		
		String domain = apiEnv.getProperty("www.domain");
		String port = apiEnv.getProperty("www.port");
		coach.put("domain", domain);
		coach.put("port", port);
		
		model.addAttribute("coach", coach);
		model.addAttribute("codeList", codeList);
		
		return "/guide/guideCoachModify";
	}
	
	@RequestMapping(value="/guideCoachModify", params="method=modify")
	public String guideCoachModify(Model model, MultipartHttpServletRequest multipartRequest, @RequestParam Map<String, Object> guide) {
		
		/*String page	= guide.get("page") == null || guide.get("page").toString().trim().equals("") ? "1" : guide.get("page").toString().trim();
		String searchColumn	= guide.get("searchColumn") == null || guide.get("searchColumn").toString().trim().equals("") ? "" : guide.get("searchColumn").toString().trim();
		String searchString	= guide.get("searchString") == null || guide.get("searchString").toString().trim().equals("") ? "" : guide.get("searchString").toString().trim();
		String cpCode	= guide.get("cpCode") == null || guide.get("cpCode").toString().trim().equals("") ? "" : guide.get("cpCode").toString().trim();
		String positionCode	= guide.get("positionCode") == null || guide.get("positionCode").toString().trim().equals("") ? "" : guide.get("positionCode").toString().trim();
			
		Map<String, MultipartFile> fileMap = ((MultipartHttpServletRequest)request).getFileMap();
		String script = "alert('%s');";
		LoginInfo loginInfo = (LoginInfo)request.getAttribute("loginInfo");
		String modifierIp = request.getRemoteAddr();
		String path = "D:\\img\\";
        String size = "";
        String originalFileName = "";
		String saveFileName = "";
		InputStream is;
		FileOutputStream fos;
		
		guide.put("modifierId", loginInfo.getId());
		guide.put("modifierIp", modifierIp);
		guide.put("updateDate", new Date());
		
		System.out.println("========= Upload File Start =========");
		for(Map.Entry<String, MultipartFile> entry : fileMap.entrySet()){
			if(!fileMap.get(entry.getKey()).isEmpty()){
				
				MultipartFile mFile = fileMap.get(entry.getKey());
				
				originalFileName = mFile.getOriginalFilename();
	        	size = Long.toString(mFile.getSize());
	        	
	        	//확장자 구하기
	        	String ext = originalFileName.substring(originalFileName.lastIndexOf(".")+1,originalFileName.length());
	        	
	        	//중복되지 않는 파일명 생성하기
	     		String randomUUID = ""+UUID.randomUUID();
	     		saveFileName = randomUUID+"."+ext;
	        	
	     		try {
	     			is = mFile.getInputStream();
		        	fos = new FileOutputStream(path + saveFileName);
		            FileCopyUtils.copy(is, fos);
		            fos.close();
		            is.close();
				} catch (Exception e) {
					e.printStackTrace();
					// TODO: handle exception
				}
	     		
	     		System.out.println(" ");
	     		System.out.println("path : " + path + saveFileName);
	     		System.out.println("size : " + size);
	     		System.out.println("originalFileName : " + originalFileName);
	     		System.out.println("saveFileName : " + saveFileName);
	     		
	     		if(entry.getKey().equals("hdpi_imgFile")){
	     			guide.put("hdpiPath", path);
	     			guide.put("hdpiSize", size);
	     			guide.put("hdpiOrgName", originalFileName);
	     			guide.put("hdpiSaveName", saveFileName);
	     		}else if(entry.getKey().equals("xhdpi_imgFile")){
	     			guide.put("xhdpiPath", path);
	     			guide.put("xhdpiSize", size);
	     			guide.put("xhdpiOrgName", originalFileName);
	     			guide.put("xhdpiSaveName", saveFileName);
	     		}else if(entry.getKey().equals("xxhdpi_imgFile")){
	     			guide.put("xxhdpiPath", path);
	     			guide.put("xxhdpiSize", size);
	     			guide.put("xxhdpiOrgName", originalFileName);
	     			guide.put("xxhdpiSaveName", saveFileName);
	     		}
			}
		}
		System.out.println(" ");
		System.out.println("========= Upload File End =========");
		
		try {
			coachGuideService.modify(guide);
			script  = String.format(script, "정상적으로 수정 처리 되었습니다.");	
			script += String.format("location.href = '/guide/guideCoachList?&page=" + page  
					+ "&searchColumn=" + searchColumn
					+ "&searchString=" + searchString 
					+ "&cpCode=" + cpCode 
					+ "&positionCode=" + positionCode 
					+ "';");
		} catch(Exception e) {
			model.addAttribute("guide", guide);
			script  = String.format(script, "수정 과정중 오류가 발생하였습니다.\\n다시 시도하여 주십시오.");
			e.printStackTrace();
		}
		
		model.addAttribute("script", script);
		
		return "/guide/guideCoachModify";*/
		
		String script = "alert('%s');";
		String modifierIp = request.getRemoteAddr();		
		String modifierId = ((LoginInfo)request.getAttribute("loginInfo")).getId();
		String uploadPath = request.getServletContext().getInitParameter("coachPath");
		String realPath = new File(uploadPath).getAbsolutePath();
		String rootPathType = request.getServletContext().getInitParameter("rootPathType");		
		
		if(rootPathType.equals("virtual")) {
			//realPath = request.getServletContext().getRealPath(uploadPath);
			realPath = request.getServletContext().getInitParameter("uploadPath") + request.getServletContext().getInitParameter("coachPath");;
		}		
		
		String page	= guide.get("page") == null || guide.get("page").toString().trim().equals("") ? "1" : guide.get("page").toString().trim();
		String searchColumn	= guide.get("searchColumn") == null || guide.get("searchColumn").toString().trim().equals("") ? "" : guide.get("searchColumn").toString().trim();
		String searchString	= guide.get("searchString") == null || guide.get("searchString").toString().trim().equals("") ? "" : guide.get("searchString").toString().trim();
		String cpCode	= guide.get("cpCode") == null || guide.get("cpCode").toString().trim().equals("") ? "" : guide.get("cpCode").toString().trim();
		String positionCode	= guide.get("positionCode") == null || guide.get("positionCode").toString().trim().equals("") ? "" : guide.get("positionCode").toString().trim();
			
		guide.put("modifierId"	, modifierId);
		guide.put("modifierIp"	, modifierIp);
		guide.put("virtualPath"	, uploadPath);
		guide.put("realPath"		, realPath);
		guide.put("updateDate"	, new Date());
		guide.put("images", multipartRequest.getFileMap());	
		
		try {
			coachGuideService.modify(guide);
			openAPICallService.updateOpenAPIInit();
			script  = String.format(script, "정상적으로 수정 처리 되었습니다.");		
			script += String.format("location.href = '/guide/guideCoachList?&page=" + page  
					+ "&searchColumn=" + searchColumn
					+ "&searchString=" + searchString 
					+ "&cpCode=" + cpCode
					+ "&positionCode=" + positionCode 
					+ "';");
		} catch(Exception e) {
			model.addAttribute("guide", guide);
			script  = String.format(script, "수정 과정중 오류가 발생하였습니다.\\n다시 시도하여 주십시오.");
			e.printStackTrace();
		}
		
		model.addAttribute("script", script);
		
		return "/guide/guideCoachModify";
	}

	@ResponseBody 
	@RequestMapping("/removeCoachGuide")
	public Map<String, Object> removeCoachGuide(@RequestParam Map<String, Object> welcomeList) {
		
		Map<String, Object> result = new HashMap<>(); 
		Map<String, Object> welcome = new HashMap<>(); 
		
		try {
			String[] pkList = welcomeList.get("pkList").toString().split(",");
			
			for(String pkStr : pkList) {
				welcome.put("pk", Integer.valueOf(pkStr));
				coachGuideService.remove(welcome);
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
