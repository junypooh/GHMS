package com.kt.giga.home.cms.manager.service;

import java.util.*;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.*;
import org.springframework.beans.factory.annotation.*;

import com.kt.giga.home.cms.manager.dao.*;
import com.kt.giga.home.cms.manager.domain.*;

@Service
public class ManagerServiceImpl implements ManagerService {
	
	@Autowired
	private ManagerDao managerDao;
	
	@Autowired
	private ManagerSvcService managerSvcService;
	
	@Autowired
	private ManagerRoleService managerRoleService;	

	@Override
	public int getCount(Map<String, Object> searchInfo) {
		return managerDao.getCount(searchInfo);
	}
	
	@Override
	public Manager get(String id) {
		Manager manager = new Manager();
		manager.setId(id);
		return this.get(manager);
	}	

	@Override
	public Manager get(Manager manager) {
		return managerDao.get(manager);
	}

	@Override
	public List<Manager> getList(Map<String, Object> searchInfo) {
		return managerDao.getList(searchInfo);
	}
	
	@Override
	public void register(Manager manager) {
		managerDao.register(manager);
	}
	
	@Override
	@Transactional
	public void register(Manager manager, Map<String, Object> managerRole, List<Map<String, Object>> managerSvcList) {
		
		this.register(manager);
		managerRoleService.register(managerRole);
		
		for(Map<String, Object> managerSvc : managerSvcList) {
			managerSvcService.register(managerSvc);
		}
	}	

	@Override
	public void modify(Manager manager) {
		managerDao.modify(manager);
	}
	
	@Override
	@Transactional
	public void modify(Manager manager, Map<String, Object> managerRole, List<Map<String, Object>> managerSvcList) {
		
		Map<String, Object> mr = new HashMap<>();
		mr.put("id", manager.getId());
		
		this.modify(manager);
		managerRoleService.remove(mr);
		managerRoleService.register(managerRole);
		managerSvcService.remove(manager.getId());
		
		for(Map<String, Object> managerSvc : managerSvcList) {
			managerSvcService.register(managerSvc);
		}		
	}	

	@Override
	public void remove(String id) {
		Manager manager = new Manager();
		manager.setId(id);
		this.remove(manager);
	}	
	
	@Override
	@Transactional
	public void remove(Manager manager) {
		
		Map<String, Object> managerRole = new HashMap<>();
		managerRole.put("id", manager.getId());
		
		managerDao.remove(manager);
		managerRoleService.remove(managerRole);
		managerSvcService.remove(manager.getId());
	}
}
