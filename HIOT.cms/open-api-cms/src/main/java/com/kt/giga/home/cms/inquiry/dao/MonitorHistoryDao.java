package com.kt.giga.home.cms.inquiry.dao;

import java.util.List;
import java.util.Map;

public interface MonitorHistoryDao {
	
	int getCount(Map<String, Object> searchInfo);
	
	Map<String, Object> get(Map<String, Object> searchInfo);
	
	List<Map<String, Object>> getList(Map<String, Object> searchInfo);
	
	List<Map<String, Object>> getMenuCodeList();	
	
	List<Map<String, Object>> getSelectMenuCodeList(Map<String, Object> searchInfo);
}
