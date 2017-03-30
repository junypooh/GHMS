package com.kt.giga.home.cms.manager.service;

import java.util.*;

public interface ManagerRoleService {

	List<Map<String, Object>> getListByID(String id);
	
	void register(Map<String, Object> managerRole);
	
	void remove(Map<String, Object> managerRole);
}
