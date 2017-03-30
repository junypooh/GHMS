package com.kt.giga.home.cms.monitor.dao;

import java.util.List;
import java.util.Map;

public interface MonitorDao {
	int getCount(Map<String, Object> searchInfo);
	
	Map<String, Object> get(Map<String, Object> searchInfo);
	
	List<Map<String, Object>> getList(Map<String, Object> searchInfo);
	
}
