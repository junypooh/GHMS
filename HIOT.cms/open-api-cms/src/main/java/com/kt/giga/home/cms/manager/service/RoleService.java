package com.kt.giga.home.cms.manager.service;

import java.util.*;

import com.kt.giga.home.cms.manager.domain.*;

public interface RoleService {
	
	Role get(Role role);
	
	List<Role> getTotalList();
	
	List<Role> getListByID(String id);	
	
	void register(Role role, List<String> permissions);
	
	void modify(Role role, List<String> permissions);	
	
	void remove(Role role);	
}
