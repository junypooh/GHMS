package com.kt.giga.home.cms.manage.service;

import java.util.*;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.*;

import com.kt.giga.home.cms.common.service.OpenAPICallService;
import com.kt.giga.home.cms.manage.dao.*;

@Service
public class MonitorTimeServiceImpl implements MonitorTimeService {

	@Autowired
	private MonitorTimeDao monitorTimeDao;
	
	@Autowired
	private OpenAPICallService openAPICallService;
	
	@Override
	public int getCount(Map<String, Object> searchInfo) {
		return monitorTimeDao.getCount(searchInfo);
	}	
	
	@Override
	public Map<String, Object> get(Map<String, Object> searchInfo) {
		searchInfo.put("pk", Integer.valueOf(searchInfo.get("pk").toString()));
		return monitorTimeDao.get(searchInfo);
	}	

	@Override
	public List<Map<String, Object>> getList(Map<String, Object> searchInfo) {
		return monitorTimeDao.getList(searchInfo);
	}

	@Override
	public void register(Map<String, Object> monitor) {
		monitorTimeDao.register(monitor);
		
		try {
			openAPICallService.updateOpenAPIInit("001");
		} catch (Exception e) {
			e.printStackTrace();
		}	
	}
}
