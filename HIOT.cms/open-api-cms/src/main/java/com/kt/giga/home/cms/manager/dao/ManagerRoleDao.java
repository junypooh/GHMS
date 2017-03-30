package com.kt.giga.home.cms.manager.dao;

import java.util.*;

public interface ManagerRoleDao {

	List<Map<String, Object>> getListByID(String id);
	
	void register(Map<String, Object> managerRole);
	
	void remove(Map<String, Object> managerRole);
	
}
