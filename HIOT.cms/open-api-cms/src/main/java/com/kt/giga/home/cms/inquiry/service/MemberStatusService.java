package com.kt.giga.home.cms.inquiry.service;

import java.util.List;
import java.util.Map;

public interface MemberStatusService {
	/**
	 * 상품별 가입자 현황 레코드 카운트를 가져온다.
	 * @param searchInfo 검색조건
	 * @return 레코드 카운트
	 */
	int getCount(Map<String, Object> searchInfo);
	
	/**
	 * 상품별 가입자 현황을 가져온다.
	 * @param searchInfo 검색조건
	 * @return 상품별 가입자 현황
	 */
	Map<String, Object> get(Map<String, Object> searchInfo);
	
	/**
	 * 상품별 가입자 현황을 가져온다.
	 * @param searchInfo 검색조건
	 * @return 상품별 가입자 현황 리스트
	 */
	List<Map<String, Object>> getList(Map<String, Object> searchInfo);
}
