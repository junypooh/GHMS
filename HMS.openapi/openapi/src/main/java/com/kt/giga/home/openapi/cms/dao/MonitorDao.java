package com.kt.giga.home.openapi.cms.dao;

import java.util.List;
import java.util.Map;

public interface MonitorDao {
	public int getCount(Map<String, Object> searchInfo);
	
	public Map<String, Object> get(Map<String, Object> searchInfo);
	
	public List<Map<String, Object>> getList(Map<String, Object> searchInfo);
	
	public int getLast();
	
}
