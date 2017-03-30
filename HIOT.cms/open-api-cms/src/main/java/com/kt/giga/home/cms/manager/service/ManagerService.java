package com.kt.giga.home.cms.manager.service;

import java.util.*;

import com.kt.giga.home.cms.manager.domain.*;

public interface ManagerService {

	int getCount(Map<String, Object> searchInfo);
	
	Manager get(String id);
	
	Manager get(Manager manager);
	
	List<Manager> getList(Map<String, Object> searchInfo);
	
	void register(Manager manager);
	
	void register(Manager manager, Map<String, Object> managerRole, List<Map<String, Object>> managerSvcList);
	
	void modify(Manager manager);
	
	void modify(Manager manager, Map<String, Object> managerRole, List<Map<String, Object>> managerSvcList);
	
	void remove(String id);
	
	void remove(Manager manager);
}
