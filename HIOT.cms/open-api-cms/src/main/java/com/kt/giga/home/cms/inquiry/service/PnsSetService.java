package com.kt.giga.home.cms.inquiry.service;

import java.util.List;
import java.util.Map;

public interface PnsSetService {
	/**
	 * PNS 결과조회 레코드 카운트를 가져온다.
	 * @param searchInfo 검색조건
	 * @return 레코드 카운트
	 */
	int getCount(Map<String, Object> searchInfo);
	
	/**
	 * PNS 결과조회 리스트를 가져온다.
	 * @param searchInfo 검색조건
	 * @return 모니터링시간 리스트
	 */
	List<Map<String, Object>> getList(Map<String, Object> searchInfo);
	
	List<Object> getListEx(List<Map<String, Object>> pnsSetList);
	
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
	
	/**
	 * PNS 결과조회 상세페이지의 카운트를 가져온다.
	 * @param searchInfo 검색조건
	 * @return 레코드 카운트
	 */
	int getDetailCount(Map<String, Object> searchInfo);
	
	/**
	 * PNS 결과조회 상세페이지의 고객 정보를 가져온다.
	 * @param searchInfo 검색조건
	 * @return 고객정보
	 */
	List<Map<String, Object>> getDetailList(Map<String, Object> searchInfo);
	
	/**
	 * PNS 결과조회 Push 이력 카운트를 가져온다.
	 * @param searchInfo 검색조건
	 * @return Push 이력 카운트
	 */
	int getPushHistoryCount(Map<String, Object> searchInfo);
	
	/**
	 * PNS 결과조회 Push 이력 리스트를 가져온다.
	 * @param searchInfo 검색조건
	 * @return Push 이력 리스트
	 */
	List<Map<String, Object>> getPushHistory(Map<String, Object> searchInfo);
	
	List<Map<String, Object>> getListSubInfo(Map<String, Object> searchInfo);
}
