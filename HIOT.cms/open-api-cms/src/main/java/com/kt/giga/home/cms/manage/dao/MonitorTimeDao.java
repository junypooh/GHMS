package com.kt.giga.home.cms.manage.dao;

import java.util.*;

public interface MonitorTimeDao {
	
	int getCount(Map<String, Object> searchInfo);
	
	Map<String, Object> get(Map<String, Object> searchInfo);
	
	List<Map<String, Object>> getList(Map<String, Object> searchInfo);
	
	void register(Map<String, Object> monitor);
	
}
