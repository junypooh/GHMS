package com.kt.giga.home.cms.customer.dao;

import java.util.*;

public interface GeneralNoticeDao {
	
	int getCount(Map<String, Object> searchInfo);
	
	int getPreorderCount(String cpCode);
	
	Map<String, Object> get(Map<String, Object> searchInfo);
	
	List<Map<String, Object>> getList(Map<String, Object> searchInfo);
	
	void updateOpenYN(Map<String, Object> notice);
	
	void updatePreorderYN(Map<String, Object> notice);
	
	void remove(Map<String, Object> notice);
	
	void register(Map<String, Object> notice);
	
	void modify(Map<String, Object> notice);
}
