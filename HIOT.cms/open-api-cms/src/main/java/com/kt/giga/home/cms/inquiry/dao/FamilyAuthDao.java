/*******************************************************************************
 * Copyright(c) 2015 KT. All rights reserved.
 * This software is the proprietary information of KT.
 *******************************************************************************/
package com.kt.giga.home.cms.inquiry.dao;

import java.util.List;
import java.util.Map;

/**
 * 
 * @author junypooh ( kyungjun.park@ceinside.co.kr )
 * @since 2015. 8. 4.
 */
public interface FamilyAuthDao {
	/**
	 * 가족사용자 정보 결과조회 레코드 카운트를 가져온다.
	 * @param searchInfo 검색조건
	 * @return 레코드 카운트
	 */
	int getCount(Map<String, Object> searchInfo);
	
	/**
	 * 가족사용자 정보 결과조회 리스트를 가져온다.
	 * @param searchInfo 검색조건
	 * @return 가족사용자 정보 결과조회 리스트
	 */
	List<Map<String, Object>> getList(Map<String, Object> searchInfo);
	
	/**
	 * 가족사용자 정보 결과를 가져온다.
	 * @param searchInfo 검색조건
	 * @return 가족사용자 정보 결과 상세 조회
	 */
	Map<String, Object> getDetail(Map<String, Object> searchInfo);
	
	/**
	 * Iot 허브/AP 권한 목록을 가져온다.
	 * @param searchInfo 검색조건
	 * @return Iot 허브 권한 목록 조회
	 */
	List<Map<String, Object>> getAuthDetail(Map<String, Object> searchInfo);

}
