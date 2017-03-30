package com.kt.giga.home.cms.manager.dao;

import java.util.*;

public interface RoleMenuDao {
	
	List<Map<String, Object>> getList(Map<String, Object> roleMenu);
	
	List<Map<String, Object>> getListByID(String id);
	
	void register(Map<String, Object> roleMenu);
	
	void remove(Map<String, Object> roleMenu);
}
