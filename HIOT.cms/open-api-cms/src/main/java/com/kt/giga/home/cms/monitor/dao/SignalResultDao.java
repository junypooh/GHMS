package com.kt.giga.home.cms.monitor.dao;

import java.util.List;
import java.util.Map;

public interface SignalResultDao {
	
	List<Map<String, Object>> getList(Map<String, Object> searchInfo);
    
    List<Map<String, Object>> getManagerList(Map<String, Object> searchInfo);
	
}
