package com.kt.giga.home.cms.customer.dao;

import java.util.*;

public interface ManageGoodsDao {
	
	int getCount(Map<String, Object> searchInfo);
	
	Map<String, Object> get(Map<String, Object> searchInfo);
	
	List<Map<String, Object>> getList(Map<String, Object> searchInfo);
	
	void remove(Map<String, Object> notice);
	
	void register(Map<String, Object> notice);
	
	void modify(Map<String, Object> notice);
	
	int checkManageGoodsFormData(Map<String, Object> searchInfo);
}
