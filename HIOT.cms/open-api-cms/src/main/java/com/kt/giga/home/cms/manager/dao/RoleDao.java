package com.kt.giga.home.cms.manager.dao;

import java.util.*;

import com.kt.giga.home.cms.manager.domain.*;

public interface RoleDao {

	Role get(Role role);
	
	List<Role> getTotalList();
	
	List<Role> getListByID(String id);
	
	void register(Role role);
	
	void modify(Role role);
	
	void remove(Role role);
}
