package com.kt.giga.home.cms.inquiry.dao;

import java.util.List;
import java.util.Map;

public interface PnsHistoryDao {
	
	int getCount(Map<String, Object> searchInfo);
	
	Map<String, Object> get(Map<String, Object> searchInfo);
	
	List<Map<String, Object>> getList(Map<String, Object> searchInfo);
	
	List<Map<String, Object>> getListDetail(Map<String, Object> searchInfo);
	
	List<Map<String, Object>> getEntList(Map<String, Object> searchInfo);
	
	List<Map<String, Object>> getSvcList();
	
}
