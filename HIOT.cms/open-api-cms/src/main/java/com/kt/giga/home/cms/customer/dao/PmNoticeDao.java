package com.kt.giga.home.cms.customer.dao;

import java.util.*;

public interface PmNoticeDao {
	
	int getCount(Map<String, Object> searchInfo);
	
	// 날짜 중복 체크 카운트 추가 
	int checkDateCount(Map<String, Object> notice);

	Map<String, Object> get(Map<String, Object> searchInfo);
	
	List<Map<String, Object>> getList(Map<String, Object> searchInfo);

	void updateOpenYN(Map<String, Object> notice);
	
	void updateReserveYN(Map<String, Object> notice);
	
	void remove(Map<String, Object> notice);
	
	void register(Map<String, Object> notice);
	
	void modify(Map<String, Object> notice);
	
}
