package com.kt.giga.home.cms.terms.dao;

import java.util.*;

public interface ServiceTermsDao {
	
	int getCount(Map<String, Object> searchInfo);
	
	Map<String, Object> get(Map<String, Object> searchInfo);
	
	List<Map<String, Object>> getList(Map<String, Object> searchInfo);
	
	String checkServiceTermsFormData(Map<String, Object> searchInfo);
	
	String getLastVer(Map<String, Object> searchInfo);
	
	void remove(Map<String, Object> terms);
	
	void register(Map<String, Object> terms);
	
	void modify(Map<String, Object> terms);
}
