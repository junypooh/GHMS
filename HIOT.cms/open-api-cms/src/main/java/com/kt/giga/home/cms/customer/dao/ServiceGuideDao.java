package com.kt.giga.home.cms.customer.dao;

import java.util.*;

public interface ServiceGuideDao {
	
	int getCount(Map<String, Object> searchInfo);
	
	Map<String, Object> get(Map<String, Object> serviceGuide);
	
	List<Map<String, Object>> getList(Map<String, Object> searchInfo);
	
	void updateOpenYN(Map<String, Object> serviceGuide);
	
	void remove(Map<String, Object> serviceGuide);
	
	void register(Map<String, Object> serviceGuide);
	
	void modify(Map<String, Object> serviceGuide);
}
