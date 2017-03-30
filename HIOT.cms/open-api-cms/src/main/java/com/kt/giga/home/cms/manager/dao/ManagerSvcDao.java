package com.kt.giga.home.cms.manager.dao;

import java.util.*;

public interface ManagerSvcDao {
	
	List<Map<String, Object>> getListByID(String id);
	
	void register(Map<String, Object> managerSvc);
	
	void remove(String id);
}
