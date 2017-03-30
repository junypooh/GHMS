package com.kt.giga.home.cms.monitor.dao;

import java.util.List;
import java.util.Map;

public interface SignalCheckDao {
	
	List<Map<String, Object>> getList(Map<String, Object> searchInfo);
	List<Map<String, Object>> getListExcel(List<String> macAddrList);
    
    List<Map<String, Object>> getManagerList(Map<String, Object> searchInfo);
    List<Map<String, Object>> getDeviceList(Map<String, Object> searchInfo);
	
}
