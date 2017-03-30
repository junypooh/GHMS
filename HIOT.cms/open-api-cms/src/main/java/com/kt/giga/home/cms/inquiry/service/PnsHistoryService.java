package com.kt.giga.home.cms.inquiry.service;

import java.util.List;
import java.util.Map;

public interface PnsHistoryService {
	/**
	 * PNS 결과조회 레코드 카운트를 가져온다.
	 * @param searchInfo 검색조건
	 * @return 레코드 카운트
	 */
	int getCount(Map<String, Object> searchInfo);
	
	/**
	 * PNS 결과조회를 가져온다.
	 * @param searchInfo 검색조건
	 * @return 모니터링시간
	 */
	Map<String, Object> get(Map<String, Object> searchInfo);
	
	/**
	 * PNS 결과조회 리스트를 가져온다.
	 * @param searchInfo 검색조건
	 * @return 모니터링시간 리스트
	 */
	List<Map<String, Object>> getList(Map<String, Object> searchInfo);
	
	List<Map<String, Object>> getListDetail(Map<String, Object> searchInfo);

	List<Map<String, Object>> getEntList(Map<String, Object> searchInfo);
	
	List<Map<String, Object>> getSvcList();
}
