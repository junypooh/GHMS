package com.kt.giga.home.cms.manager.dao;

import java.util.*;

import com.kt.giga.home.cms.manager.domain.*;

public interface ManagerDao {

	Manager get(Manager id);
	
	int getCount(Map<String, Object> searchInfo);
	
	List<Manager> getList(Map<String, Object> searchInfo);
	
	void register(Manager manager);
	
	void modify(Manager manager);
	
	void remove(Manager manager);
}
