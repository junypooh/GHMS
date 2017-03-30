package com.kt.giga.home.cms.manager.controller;

import java.util.*;

import javax.servlet.http.*;

import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.*;

import com.kt.giga.home.cms.manager.service.*;
import com.kt.giga.home.cms.manager.domain.*;


@Controller
@RequestMapping("/manager")
public class RoleController {
	
	@Autowired
	private HttpServletRequest request;
	
	@Autowired
	private RoleService roleService;

	@Autowired
	private RoleMenuService roleMenuService;	
	
	@Autowired
	private MenuService menuService;	
	
	
	@RequestMapping("/roleList")
	public String roleList(Model model) {
		
		List<Role> roleList = roleService.getTotalList();
		model.addAttribute("roleList", roleList);
		model.addAttribute("roleTotalCount", roleList.size());
		
		return "/manager/roleList";
	}
	
	@RequestMapping("/roleRegister")
	public String roleRegisterForm(Model model) {
		
		List<Menu> menuList = menuService.getSortList();
		model.addAttribute("menuList", menuList);
		
		return "/manager/roleRegister";
	}
	
	@RequestMapping(value="/roleRegister", params="method=register")
	public String roleRegister(Model model, @RequestParam Map<String, Object> roleInfo, @RequestParam(value="permissions", required=false) String[] permissions) {

		Role role = new Role();
		
		role.setName(roleInfo.get("name").toString().trim());
		role.setDescription(roleInfo.get("description").toString().trim());
		role.setUseYN(roleInfo.get("useYN").toString().trim());
		
		List<String> targetPermissions = new ArrayList<String>();
		
		if(permissions != null) {
			targetPermissions = Arrays.asList(permissions);
		}
		
		String script = "";
		
		try {
			roleService.register(role, targetPermissions);
			script += "alert('정상적으로 등록 처리되었습니다.');";
			script += "location.href = '/manager/roleList';";
			
		} catch(Exception ex) {
			script = "alert('등록 과정중 오류가 발생하였습니다.\\n다시 시도하여 주십시오.')";
			
			List<Menu> menuList = menuService.getSortList();
			
			model.addAttribute("menuList", menuList);
			model.addAttribute("roleInfo", roleInfo);
		}
		
		model.addAttribute("script", script);
		
		return "/manager/roleRegister";
	}
	
	@RequestMapping("/roleModify")
	public String roleModifyForm(Model model, @RequestParam(value="role") int roleNum) {
		Role roleObj = new Role();
		roleObj.setRole(roleNum);
		
		Map<String, Object> roleMap = new HashMap<>();
		roleMap.put("role", roleNum);
		
		Role role = roleService.get(roleObj);
		
		List<Menu> menuList = menuService.getSortList();
		List<Map<String, Object>> aclList = roleMenuService.getList(roleMap);
		
		String aclListStr = "";
		for(Map<String, Object> acl : aclList) {
			aclListStr += acl.get("menu").toString() + ",";
		}
		
		if(!aclListStr.equals("")){
			aclListStr = aclListStr.substring(0, aclListStr.length() - 1);
		}
		
		model.addAttribute("role", role);
		model.addAttribute("menuList", menuList);
		model.addAttribute("aclListStr", aclListStr);
		
		return "/manager/roleModify";
	}
	
	@RequestMapping(value="/roleModify", params="method=modify")
	public String roleModify(Model model, @RequestParam Map<String, Object> role, @RequestParam(value="permissions", required=false) String[] permissions) {
		
		Role roleObj = new Role();
		
		roleObj.setRole(Integer.parseInt(role.get("role").toString()));
		roleObj.setName(role.get("name").toString().trim());
		roleObj.setDescription(role.get("description").toString().trim());
		
		Map<String, Object> roleMap = new HashMap<>();
		roleMap.put("role", roleObj.getRole());		
		
		List<String> targetPermissions = new ArrayList<String>();
		
		if(permissions != null) {
			targetPermissions = Arrays.asList(permissions);
		}
		
		String script = "";
		
		try {
			roleService.modify(roleObj, targetPermissions);
			script += "alert('정상적으로 수정 처리 되었습니다.');";
			script += "location.href = '/manager/roleList';";
		} catch(Exception ex) {
			script = "alert('수정 과정중 오류가 발생 하였습니다.\\n다시 시도하여 주십시오.');";
			
			List<Menu> menuList = menuService.getSortList();
			
			List<Map<String, Object>> aclList = roleMenuService.getList(roleMap);
			
			String aclListStr = "";
			for(Map<String, Object> acl : aclList) {
				aclListStr += acl.get("menu").toString() + ",";
			}
			
			if(!aclListStr.equals("")) {
				aclListStr = aclListStr.substring(0, aclListStr.length() - 1);
			}			
			
			model.addAttribute("role", role);
			model.addAttribute("menuList", menuList);
			model.addAttribute("aclListStr", aclListStr);
		}
		
		model.addAttribute("script", script);
		
		return "/manager/roleModify";
	}	
	
	@RequestMapping(value="/roleModify", params="method=remove")
	public String roleRemove(Model model, @RequestParam Map<String, Object> role) {
		Role roleObj = new Role();
		roleObj.setRole(Integer.parseInt(role.get("role").toString()));
		
		String script = "";
		
		try {
			roleService.remove(roleObj);
			
			script += "alert('정상적으로 삭제 처리 되었습니다.');";
			script += "location.href = '/manager/roleList';";
		} catch(Exception ex) {
			script = "alert('삭제 과정중 오류가 발생하였습니다.\\n다시 시도하여 주십시오.');";
			model.addAttribute("role", role);
		}
		
		model.addAttribute("script", script);
		
		return "/manager/roleModify";
	}	
}
