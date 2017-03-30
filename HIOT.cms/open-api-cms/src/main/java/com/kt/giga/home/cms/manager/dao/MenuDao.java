package com.kt.giga.home.cms.manager.dao;

import java.util.*;

import com.kt.giga.home.cms.manager.domain.*;

/**
 * 메뉴 Dao
 * @author 김용성
 *
 */
public interface MenuDao {
	
	Menu get(Menu menu);
	
	int getCount(Menu menu);
	
	List<Menu> getList(Menu menu);
	
	List<Menu> getSortList();
	
	List<Menu> getListByID(String id);
	
	void register(Menu menu);
	
	void modify(Menu menu);
	
	void remove(Menu menu);
	
}
