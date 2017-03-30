package com.kt.giga.home.cms.manager.service;

import java.util.*;

import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

import com.kt.giga.home.cms.manager.dao.*;

@Service
public class ManagerSvcServiceImpl implements ManagerSvcService {
	
	@Autowired
	private ManagerSvcDao managerSvcDao;

	@Override
	public List<Map<String, Object>> getListByID(String id) {
		return managerSvcDao.getListByID(id);
	}

	@Override
	public void register(Map<String, Object> managerSvc) {
		managerSvcDao.register(managerSvc);
	}

	@Override
	public void remove(String id) {
		managerSvcDao.remove(id);
	}

}
