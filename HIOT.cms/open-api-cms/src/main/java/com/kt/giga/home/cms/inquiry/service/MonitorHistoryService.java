package com.kt.giga.home.cms.inquiry.service;

import java.util.List;
import java.util.Map;

public interface MonitorHistoryService {
	/**
	 * 모니터링 사용 조회 레코드 카운트를 가져온다.
	 * @param searchInfo 검색조건
	 * @return 레코드 카운트
	 */
	int getCount(Map<String, Object> searchInfo);
	
	/**
	 * 모니터링 사용 조회를 가져온다.
	 * @param searchInfo 검색조건
	 * @return 모니터링시간
	 */
	Map<String, Object> get(Map<String, Object> searchInfo);
	
	/**
	 * 모니터링 사용 조회 리스트를 가져온다.
	 * @param searchInfo 검색조건
	 * @return 모니터링시간 리스트
	 */
	List<Map<String, Object>> getList(Map<String, Object> searchInfo);
	
	List<Map<String, Object>> getMenuCodeList();	
	
	List<Map<String, Object>> getSelectMenuCodeList(Map<String, Object> searchInfo);
}
