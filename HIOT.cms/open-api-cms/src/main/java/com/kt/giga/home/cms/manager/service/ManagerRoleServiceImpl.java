package com.kt.giga.home.cms.manager.service;

import java.util.*;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.*;

import com.kt.giga.home.cms.manager.dao.*;

@Service
public class ManagerRoleServiceImpl implements ManagerRoleService {
	
	@Autowired
	private ManagerRoleDao managerRoleDao;

	@Override
	public List<Map<String, Object>> getListByID(String id) {
		return managerRoleDao.getListByID(id);
	}

	@Override
	public void register(Map<String, Object> managerRole) {
		managerRoleDao.register(managerRole);
	}

	@Override
	public void remove(Map<String, Object> managerRole) {
		managerRoleDao.remove(managerRole);
	}

}
