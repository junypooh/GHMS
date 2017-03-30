package com.kt.giga.home.cms.customer.service;

import java.util.*;

/**
 * 개인정보취급방침 약관 관리 서비스
 * @author 한란민
 *
 */
public interface PersInfoTermsService {
	
	/**
	 * 개인정보취급방침 레코드 카운트를 가져온다.
	 * @param searchInfo 검색조건
	 * @return 레코드 카운트
	 */
	int getCount(Map<String, Object> searchInfo);
	
	/**
	 * 개인정보취급방침 정보를 갖고온다.
	 * @param searchInfo 검색조건
	 * @return 개인정보취급방침
	 */
	Map<String, Object> get(Map<String, Object> searchInfo);
	
	/**
	 * 개인정보취급방침 리스트를 가져온다.
	 * @param searchInfo 검색조건
	 * @return 개인정보취급방침 리스트
	 */
	List<Map<String, Object>> getList(Map<String, Object> searchInfo);
	
	/**
	 * @modifier 김영훈
	 * 개인정보취급방침 등록시 중복버전 체크
	 * @param searchInfo = cpCode, verNum, positionCode
	 * @return 등록이 가능한지 유무(등록가능:true, 등록불가:false)
	 */	
	String checkPersInfoFormData(Map<String, Object> searchInfo);
	
	/**
	 * 개인정보취급방침 삭제
	 * @param terms 대상 개인정보취급방침 정보
	 */
	void remove(Map<String, Object> terms);
	
	/**
	 * 개인정보취급방침 등록
	 * @param terms 개인정보취급방침 정보
	 */
	void register(Map<String, Object> terms);
	
	/**
	 * 개인정보취급방침 수정
	 * @param terms 대상 개인정보취급방침 정보
	 */
	void modify(Map<String, Object> terms);
}
