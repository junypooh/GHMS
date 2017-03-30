package com.kt.giga.home.cms.manager.service;

import java.util.*;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.*;
import org.springframework.beans.factory.annotation.*;

import com.kt.giga.home.cms.manager.dao.*;
import com.kt.giga.home.cms.manager.domain.*;

@Service
public class RoleServiceImpl implements RoleService {
	
	@Autowired
	private RoleDao roleDao; 
	
	@Autowired
	private RoleMenuService roleMenuService;
	
	@Autowired
	private ManagerRoleService managerRoleService;

	@Override
	public Role get(Role role) {
		return roleDao.get(role);
	}

	@Override
	public List<Role> getTotalList() {
		return roleDao.getTotalList();
	}

	@Override
	public List<Role> getListByID(String id) {
		return roleDao.getListByID(id);
	}

	@Override
	@Transactional
	public void register(Role role, List<String> permissions) {
		
		roleDao.register(role);
		
		for(String permission : permissions) {
			Map<String, Object> roleMenu = new HashMap<String, Object>();
			roleMenu.put("role", role.getRole());
			roleMenu.put("menu", permission);
			roleMenuService.register(roleMenu);
		}
	}

	@Override
	@Transactional
	public void modify(Role role, List<String> permissions) {
		
		boolean targetPermission;
		List<String> orgPermissions = new ArrayList<String>();
		List<String> registerPermissions = new ArrayList<String>();
		List<String> removePermissions = new ArrayList<String>();
		
		Map<String, Object> roleMap = new HashMap<>();
		roleMap.put("role", role.getRole());
		
		List<Map<String, Object>> roleMenuList = roleMenuService.getList(roleMap);
		
		for(Map<String, Object> roleMenu : roleMenuList) {
			orgPermissions.add(roleMenu.get("menu").toString());
		}
		
		for(String orgPermission : orgPermissions) {
			targetPermission = true;
			for(String permission : permissions) {
				if(orgPermission.equals(permission)) {
					targetPermission = false;
				}
			}
			
			if(targetPermission) {
				removePermissions.add(orgPermission);
			}
		}
		
		for(String permission : permissions) {
			targetPermission = true;
			for(String orgPermission : orgPermissions) {
				if(orgPermission.equals(permission)) {
					targetPermission = false;
				}
			}
			
			if(targetPermission) {
				registerPermissions.add(permission);
			}
		}		
		
		roleDao.modify(role);
		
		for(String permission : removePermissions) {
			Map<String, Object> roleMenu = new HashMap<>();
			roleMenu.put("role", role.getRole());
			roleMenu.put("menu", permission);
			roleMenuService.remove(roleMenu);
		}			
		
		for(String permission : registerPermissions) {
			Map<String, Object> roleMenu = new HashMap<>();
			roleMenu.put("role", role.getRole());
			roleMenu.put("menu", permission);
			roleMenuService.register(roleMenu);
		}
	}

	@Override
	@Transactional
	public void remove(Role role) {
		
		Map<String, Object> roleMenu = new HashMap<String, Object>();
		Map<String, Object> managerRole = new HashMap<String, Object>();
		
		roleMenu.put("role", role.getRole());
		managerRole.put("role", role.getRole());
		
		roleDao.remove(role);
		
		managerRoleService.remove(managerRole);
		roleMenuService.remove(roleMenu);
	}

}
