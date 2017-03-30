package com.kt.giga.home.cms.monitor.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kt.giga.home.cms.monitor.dao.LogOnOffDao;

@Service
public class LogOnOffServiceImpl implements LogOnOffService {
	
	@Autowired
	private LogOnOffDao logOnOffDao;
	
	@Override
	public int getCount(Map<String, Object> searchInfo) {
		return logOnOffDao.getCount(searchInfo);
	}	
	
	@Override
	public List<Map<String, Object>> getList(Map<String, Object> searchInfo) {
		return logOnOffDao.getList(searchInfo);
	}
}
