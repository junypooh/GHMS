package com.kt.giga.home.cms.monitor.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kt.giga.home.cms.monitor.dao.MonitorDao;


@Service
public class MonitorServiceImpl implements MonitorService{
	@Autowired
	private MonitorDao monitorDao;
	
	@Override
	public int getCount(Map<String, Object> searchInfo) {
		return monitorDao.getCount(searchInfo);
	}	
	
	@Override
	public Map<String, Object> get(Map<String, Object> searchInfo) {
		searchInfo.put("pk", Integer.valueOf(searchInfo.get("pk").toString()));
		return monitorDao.get(searchInfo);
	}	

	@Override
	public List<Map<String, Object>> getList(Map<String, Object> searchInfo) {
		return monitorDao.getList(searchInfo);
	}
}
