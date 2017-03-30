package com.kt.giga.home.cms.manager.service;

import java.util.*;

public interface ManagerSvcService {

	List<Map<String, Object>> getListByID(String id);
	
	void register(Map<String, Object> managerSvc);
	
	void remove(String id);
}
