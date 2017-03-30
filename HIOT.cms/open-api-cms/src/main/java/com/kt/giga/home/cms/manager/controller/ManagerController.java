package com.kt.giga.home.cms.manager.controller;

import java.util.*;

import javax.servlet.http.*;

import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.*;

import com.kt.giga.home.cms.common.domain.*;
import com.kt.giga.home.cms.common.service.*;
import com.kt.giga.home.cms.manager.domain.*;
import com.kt.giga.home.cms.manager.service.*;
import com.kt.giga.home.cms.util.*;


@Controller
@RequestMapping("/manager")
public class ManagerController {
	
	@Autowired
	private HttpServletRequest request;
	
	@Autowired
	private CodeService codeService;
	
	@Autowired
	private RoleService roleService;

	@Autowired
	private ManagerService managerService;	
	
	@Autowired
	private ManagerRoleService managerRoleService;
	
	@Autowired
	private ManagerSvcService managerSvcService;

	@ResponseBody
	@RequestMapping("/isOverlapID")
	public Map<String, Object> isOverlapID(@RequestParam String id) {
		
		Map<String, Object> result = new HashMap<>();
		
		try {
			
			Manager manager = managerService.get(id);
			
			result.put("code", 200);
			
			if(manager != null) {
				result.put("data", "true");
			} else {
				result.put("data", "false");
			}
			
			result.put("msg", "success");			
		} catch(Exception e) {
			result.put("code", 500);
			result.put("msg", "server error : " + e.getMessage());			
		}
		
		return result;
	}
	
	@ResponseBody 
	@RequestMapping("/removeManager")
	public Map<String, Object> removeManager(@RequestParam Map<String, Object> managerList) {

		Map<String, Object> result = new HashMap<>(); 
		
		try {
			String[] idList = managerList.get("managerList").toString().split(",");
			
			for(String id : idList) {
				managerService.remove(id);
			}	
			
			result.put("code", 200);
			result.put("msg", "success");
		} catch(Exception e) {
			result.put("code", 500);
			result.put("msg", "server error : " + e.getMessage());
		}
		
		return result;
	}		
	
	@RequestMapping("/managerList")
	public String managerList(Model model, @RequestParam Map<String, Object> searchInfo) {
		
		int searchRole, page, pageSize = 10;
		
		page = searchInfo.get("page") == null || searchInfo.get("page").toString().trim().equals("") ? 1 : Integer.parseInt(searchInfo.get("page").toString().trim());
		searchRole	= searchInfo.get("searchRole") != null ? Integer.parseInt(searchInfo.get("searchRole").toString().trim()) : 0;
		
		String searchColumn	= searchInfo.get("searchColumn") != null ? searchInfo.get("searchColumn").toString().trim() : "";		
		String searchString	= searchInfo.get("searchString") != null ? searchInfo.get("searchString").toString().trim() : "";
		
		searchInfo.put("page"			, page);
		searchInfo.put("pageSize"		, pageSize);
		searchInfo.put("searchRole"		, searchRole);
		searchInfo.put("searchString"	, searchString);
		
		int managerCount = managerService.getCount(searchInfo);
		List<Manager> managerList = managerService.getList(searchInfo);
		List<Role> roleList = roleService.getTotalList();
		
		for(Manager manager : managerList) {
			manager.setSvcList(managerSvcService.getListByID(manager.getId()));
		}
		
		PageNavi pageNavi = new PageNavi();
		pageNavi.setAction("/manager/managerList");
		pageNavi.setNowPage(page);
		pageNavi.setTotalCount(managerCount);
		pageNavi.setMethod("get");
		pageNavi.setPageSize(pageSize);
		pageNavi.setParameters("searchColumn", searchColumn);
		pageNavi.setParameters("searchString", searchString);
		pageNavi.make();	
		
		model.addAttribute("managerList", managerList);
		model.addAttribute("roleList", roleList);
		model.addAttribute("pageNavi", pageNavi);
		
		return "/manager/managerList";
	}
	
	@RequestMapping("/managerRegister")
	public String managerRegisterForm(Model model) {
		List<Role> roleList = roleService.getTotalList();
		List<Code> serviceList = codeService.getList("1200");
		
		model.addAttribute("roleList", roleList);
		model.addAttribute("serviceList", serviceList);
		
		return "/manager/managerRegister";
	}
	
	@RequestMapping(value="/managerRegister", params="method=register")
	public String managerRegister(Model model, @RequestParam Map<String, String> managerInfo, @RequestParam String[] cpCodes) {

		String script = "";
		String id = managerInfo.get("id").trim();
		String pw = managerInfo.get("pw").trim();
		
		List<Code> serviceList = codeService.getList("1200");
		
		try {
			
			Manager manager = managerService.get(id);
			
			if(manager != null)
				throw new Exception("OverlapID");
			
			HashEncrypt sha256 = new HashEncrypt(pw);
			pw = sha256.getEncryptData();			
			
			manager = new Manager();
			
			manager.setId(id);
			manager.setPw(pw);
			manager.setName(managerInfo.get("name").trim());
			manager.setTeam(managerInfo.get("team").trim());
			manager.setStatusCd(managerInfo.get("statusCd").trim());
			manager.setMobile(managerInfo.get("mobile").trim());
			manager.setEmail(managerInfo.get("email").trim());
			manager.setMemo(managerInfo.get("memo").trim());
			
			Map<String, Object> managerRole = new HashMap<>();
			managerRole.put("id", id);
			managerRole.put("role", Integer.parseInt(managerInfo.get("role")));
			
			Map<String, String> serviceMap = new HashMap<>(); 
			
			/* 
			 * 서비스 단위 코드는 name 에 코드값이 있으며,  
			 * memo 에 이름이 들어가 있다.
			 */
			for(Code service : serviceList) {
				serviceMap.put(service.getName(), service.getMemo());  
			}
			
			List<Map<String, Object>> managerSvcList = new ArrayList<>();
			
			for(String cpCode : cpCodes) {
				Map<String, Object> managerSvc = new HashMap<String, Object>();
				managerSvc.put("id", id);
				managerSvc.put("cpCode", cpCode);
				managerSvc.put("cpName", serviceMap.get(cpCode));
				managerSvcList.add(managerSvc);
			}
			
			managerService.register(manager, managerRole, managerSvcList);
			
			script += "alert('정상적으로 등록 처리되었습니다.');";
			script += "location.href = '/manager/managerList';";
			
		} catch(Exception e) {
			
			String msg = e.getMessage().equals("OverlapID") ? "중복된 아이디 입니다." : "등록 과정중 오류가 발생했습니다.\n다시 시도하여 주십시오.";
			script = String.format("alert('%s');", msg);
			
			List<Role> roleList = roleService.getTotalList();
			
			model.addAttribute("manager", managerInfo);
			model.addAttribute("roleList", roleList);
			model.addAttribute("serviceList", serviceList);
		}
		
		model.addAttribute("script", script);
		
		return "/manager/managerRegister";
	}
	
	@RequestMapping("/managerModify")
	public String managerModifyForm(Model model, String id) {

		String svcListStr = "";
		LoginInfo loginInfo = (LoginInfo)request.getAttribute("loginInfo");
		
		Manager manager = managerService.get(id);
		List<Role> roleList = roleService.getTotalList();
		List<Code> serviceList = codeService.getList("1200");
		
		for(Map<String, Object> svc : loginInfo.getSvcList()) {
			svcListStr += svc.get("cpCode").toString() + ",";
		}
		
		if(!svcListStr.equals("")) {
			svcListStr = svcListStr.substring(0, svcListStr.length() - 1);
		}			
		
		model.addAttribute("manager", manager);
		model.addAttribute("roleList", roleList);
		model.addAttribute("serviceList", serviceList);
		model.addAttribute("svcListStr", svcListStr);
		
		return "/manager/managerModify";
	}
	
	@RequestMapping(value="/managerModify", params="method=modify")
	public String managerModify(Model model, @RequestParam Map<String, Object> managerInfo, @RequestParam String[] cpCodes) {
		
		String script = "";
		List<Code> serviceList = codeService.getList("1200");
		String pw = managerInfo.get("pw") != null ? managerInfo.get("pw").toString() : "";
		
		try {
			Manager manager = new Manager();
			
			manager.setId(managerInfo.get("id").toString().trim());
			manager.setName(managerInfo.get("name").toString().trim());
			manager.setTeam(managerInfo.get("team").toString().trim());
			manager.setStatusCd(managerInfo.get("statusCd").toString().trim());
			manager.setMobile(managerInfo.get("mobile").toString().trim());
			manager.setEmail(managerInfo.get("email").toString().trim());
			manager.setMemo(managerInfo.get("memo").toString().trim());
			
			if(!pw.equals("")) {
				HashEncrypt sha256 = new HashEncrypt(pw);
				pw = sha256.getEncryptData();
				
				manager.setPw(pw);
				manager.setChangePwDate(new Date());
			}
			
			Map<String, Object> managerRole = new HashMap<>();
			managerRole.put("id", manager.getId());
			managerRole.put("role", Integer.parseInt(managerInfo.get("role").toString()));
			
			Map<String, String> serviceMap = new HashMap<>();
			
			/* 
			 * 서비스 단위 코드는 name 에 코드값이 있으며,  
			 * memo 에 이름이 들어가 있다.
			 */
			for(Code service : serviceList) {
				serviceMap.put(service.getName(), service.getMemo());  
			}
			
			List<Map<String, Object>> managerSvcList = new ArrayList<>();
			
			for(String cpCode : cpCodes) {
				Map<String, Object> managerSvc = new HashMap<String, Object>();
				managerSvc.put("id", managerInfo.get("id").toString().trim());
				managerSvc.put("cpCode", cpCode);
				managerSvc.put("cpName", serviceMap.get(cpCode));				
				managerSvcList.add(managerSvc);
			}			
			
			managerService.modify(manager, managerRole, managerSvcList);
			
			String queryString = "?page=" + managerInfo.get("page").toString();
			queryString += "&searchRole=" + managerInfo.get("searchRole").toString();
			queryString += "&searchColumn=" + managerInfo.get("searchColumn").toString();
			queryString += "&searchString=" + managerInfo.get("searchString").toString();
			
			script += "alert('정상적으로 수정 처리되었습니다.');";
			script += "location.href = '/manager/managerList" + queryString + "';";			
			
		} catch(Exception e) {
			script = "alert(수정 과정중 오류가 발생하였습니다.\n다시 시도하여 주십시오.');";
			List<Role> roleList = roleService.getTotalList();
			
			model.addAttribute("manager", managerInfo);			
			model.addAttribute("roleList", roleList);		
			model.addAttribute("serviceList", serviceList);
		}
		
		model.addAttribute("script", script);
		
		return "/manager/managerModify";
	}
	
	@RequestMapping("/managerRemove")
	public String managerRemove(Model model, @RequestParam Map<String, Object> manager) {
		
		String script = "";
		
		try {
			managerService.remove(manager.get("id").toString());
			
			String queryString = "?page=" + manager.get("page").toString();
			queryString += "&searchRole=" + manager.get("searchRole").toString();
			queryString += "&searchColumn=" + manager.get("searchColumn").toString();
			queryString += "&searchString=" + manager.get("searchString").toString();
			
			script += "alert('정상적으로 삭제 처리되었습니다.');";
			script += "location.href = '/manager/managerList" + queryString + "';";
			
		} catch(Exception e) {
			script = "alert('삭제 과정중 오류가 발생하였습니다.\n다시 시도하여 주십시오.');";
			model.addAttribute("manager", manager);
		}
		
		model.addAttribute("script", script);
		
		return "/manager/managerModify";
	}
}
