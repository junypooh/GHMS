package com.kt.giga.home.cms.inquiry.dao;

import java.util.List;
import java.util.Map;

public interface PnsSetDao {
	int getCount(Map<String, Object> searchInfo);
	
	List<Map<String, Object>> getList(Map<String, Object> searchInfo);
	
	List<Map<String, Object>> getPush(Map<String, Object> pushMap);
	
	int getDetailCount(Map<String, Object> searchInfo);
	
	List<Map<String, Object>> getDetailList(Map<String, Object> searchInfo);
	
	int getPushHistoryCount(Map<String, Object> searchInfo);
	
	List<Map<String, Object>> getPushHistory(Map<String, Object> pushMap);
	
	/**
	 * PNS 결과조회에서 이벤트 종류 검색
	 * @return
	 */
	List<Map<String, Object>> getEntList();
	
	/**
	 * PNS 결과조회에서 서비스 종류 검색
	 * @return
	 */
	List<Map<String, Object>> getSvcList();	
	
	List<Map<String, Object>> getListSubInfo(Map<String, Object> searchInfo);
}
