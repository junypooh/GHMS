package com.kt.giga.home.cms.terms.service;

import java.util.*;

/**
 * 서비스 이용 약관 관리 서비스
 * @author 한란민
 *
 */
public interface ServiceTermsService {
	
	/**
	 * 서비스이용약관 레코드 카운트를 가져온다.
	 * @param searchInfo 검색조건
	 * @return 레코드 카운트
	 */
	int getCount(Map<String, Object> searchInfo);
	
	/**
	 * 서비스이용약관 정보를 갖고온다.
	 * @param searchInfo 검색조건
	 * @return 서비스이용약관
	 */
	Map<String, Object> get(Map<String, Object> searchInfo);
	
	/**
	 * 서비스이용약관 리스트를 가져온다.
	 * @param searchInfo 검색조건
	 * @return 서비스이용약관 리스트
	 */
	List<Map<String, Object>> getList(Map<String, Object> searchInfo);
	
	/**
	 * 서비스이용약관 등록시 같은 상품의 상위버전, 중복버전 체크
	 * @param searchInfo = cpCode, termsVer, termsCode
	 * @return 등록이 가능한지 유무
	 */
	String checkServiceTermsFormData(Map<String, Object> searchInfo);
	
	/**
	 * 서비스이용약관 삭제
	 * @param terms 대상 서비스이용약관 정보
	 */
	void remove(Map<String, Object> terms);
	
	/**
	 * 서비스이용약관 등록
	 * @param terms 서비스이용약관 정보
	 */
	void register(Map<String, Object> terms);
	
	/**
	 * 서비스이용약관 수정
	 * @param terms 대상 서비스이용약관 정보
	 */
	void modify(Map<String, Object> terms);
}
