/*******************************************************************************
 * Copyright(c) 2015 KT. All rights reserved.
 * This software is the proprietary information of KT.
 *******************************************************************************/
package com.kt.giga.home.cms.inquiry.service;

import java.util.List;
import java.util.Map;

/**
 * 
 * @author junypooh ( kyungjun.park@ceinside.co.kr )
 * @since 2015. 8. 10.
 */
public interface ManagerDeviceService {
	
	/**
	 * 홈매니저 사용 이력조회 리스트를 가져온다.
	 * @param searchInfo 검색조건
	 * @return 홈매니저 사용 이력조회 리스트
	 */
	List<Map<String, String>> getList(Map<String, Object> searchInfo);
	
	/**
	 * 홈매니저 사용 이력조회 상세를 가져온다.
	 * @param searchInfo 검색조건
	 * @return 홈매니저 사용 이력조회 상세
	 */
	Map<String, Object> getDetail(Map<String, Object> searchInfo);

}
