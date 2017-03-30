package com.kt.giga.home.cms.manage.service;

import java.util.*;


public interface MonitorTimeService {
	
	/**
	 * 모니터링시간 레코드 카운트를 가져온다.
	 * @param searchInfo 검색조건
	 * @return 레코드 카운트
	 */
	int getCount(Map<String, Object> searchInfo);
	
	/**
	 * 모니터링시간를 가져온다.
	 * @param searchInfo 검색조건
	 * @return 모니터링시간
	 */
	Map<String, Object> get(Map<String, Object> searchInfo);
	
	/**
	 * 모니터링시간 리스트를 가져온다.
	 * @param searchInfo 검색조건
	 * @return 모니터링시간 리스트
	 */
	List<Map<String, Object>> getList(Map<String, Object> searchInfo);
	
	/**
	 * 모니터링시간 등록
	 * @param notice 모니터링시간 정보
	 */
	void register(Map<String, Object> monitor);
	

}
