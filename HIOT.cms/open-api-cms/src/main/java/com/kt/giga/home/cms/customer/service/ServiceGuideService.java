package com.kt.giga.home.cms.customer.service;

import java.io.IOException;
import java.util.*;

/**
 * 서비스 안내 관리 서비스
 * @author 한란민
 *
 */
public interface ServiceGuideService {
	
	/**
	 * 서비스안내 레코드 카운트를 가져온다.
	 * @param searchInfo 검색조건
	 * @return 레코드 카운트
	 */
	int getCount(Map<String, Object> searchInfo);
	
	/**
	 * 서비스안내 정보를 갖고온다.
	 * @param searchInfo 검색조건
	 * @return 서비스안내
	 */
	Map<String, Object> get(Map<String, Object> serviceGuide);
	
	/**
	 * 서비스안내 리스트를 가져온다.
	 * @param searchInfo 검색조건
	 * @return 서비스안내 리스트
	 */
	List<Map<String, Object>> getList(Map<String, Object> searchInfo);
	
	/**
	 * 서비스안내 노출여부 업데이트
	 * @param guide 대상 서비스안내 정보
	 */
	void updateOpenYN(Map<String, Object> serviceGuide);
	
	/**
	 * 서비스안내 삭제
	 * @param guide 대상 서비스안내 정보
	 */
	void remove(Map<String, Object> serviceGuide);
	
	/**
	 * 서비스안내 등록
	 * @param guide 서비스안내 정보
	 */
	void register(Map<String, Object> serviceGuide) throws IllegalStateException, IOException ;
	
	/**
	 * 서비스안내 수정
	 * @param guide 대상 서비스안내 정보
	 */
	void modify(Map<String, Object> serviceGuide) throws IllegalStateException, IOException ;
}
