package com.kt.giga.home.cms.guide.dao;

import java.util.*;

public interface CoachGuideDao {
	
	int getCount(Map<String, Object> searchInfo);
	
	Map<String, Object> get(Map<String, Object> searchInfo);
	
	List<Map<String, Object>> getList(Map<String, Object> searchInfo);
	
	void updateOpenYN(Map<String, Object> Coach);
	
	void updateAllOpenYN(Map<String, Object> Coach);
	
	//String getLastVer(Map<String, Object> searchInfo);
	
	int getCheckDataCount(Map<String, Object> searchInfo);
	
	List<Map<String, Object>> getVersionList (Map<String, Object> searchInfo);
	
	void remove(Map<String, Object> Coach);
	
	void register(Map<String, Object> Coach);
	
	void modify(Map<String, Object> Coach);
}
