package com.kt.giga.home.cms.manager.service;

import java.util.*;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.*;

import com.kt.giga.home.cms.manager.dao.*;

@Service
public class RoleMenuServiceImpl implements RoleMenuService {
	
	@Autowired
	private RoleMenuDao roleMenuDao;

	@Override
	public List<Map<String, Object>> getList(Map<String, Object> roleMenu) {
		return roleMenuDao.getList(roleMenu);
	}

	@Override
	public List<Map<String, Object>> getListByID(String id) {
		return roleMenuDao.getListByID(id);
	}

	@Override
	public void register(Map<String, Object> roleMenu) {
		roleMenuDao.register(roleMenu);
	}

	@Override
	public void remove(Map<String, Object> roleMenu) {
		roleMenuDao.remove(roleMenu);
	}

}
