package com.kt.giga.home.cms.terms.service;

import java.util.*;

/**
 * 개인정보 수집및 이용동의 관리 서비스
 * @author 한란민
 *
 */
public interface PersInfoCollectTermsService {
	
	/**
	 * 개인정보 수집및 이용동의 레코드 카운트를 가져온다.
	 * @param searchInfo 검색조건
	 * @return 레코드 카운트
	 */
	int getCount(Map<String, Object> searchInfo);
	
	/**
	 * 개인정보 수집및 이용동의 정보를 갖고온다.
	 * @param searchInfo 검색조건
	 * @return 개인정보 수집및 이용동의
	 */
	Map<String, Object> get(Map<String, Object> searchInfo);
	
	/**
	 * 개인정보 수집및 이용동의 리스트를 가져온다.
	 * @param searchInfo 검색조건
	 * @return 개인정보 수집및 이용동의 리스트
	 */
	List<Map<String, Object>> getList(Map<String, Object> searchInfo);
	
	/**
	 * 개인정보 수집및 이용동의 등록시 같은 상품의 상위버전, 중복버전 체크
	 * @param searchInfo = cpCode, termsVer, termsCode
	 * @return 등록이 가능한지 유무
	 */
	String checkInfoCollectFormData(Map<String, Object> searchInfo);
	
	/**
	 * 개인정보 수집및 이용동의 삭제
	 * @param terms 대상 개인정보 수집및 이용동의 정보
	 */
	void remove(Map<String, Object> terms);
	
	/**
	 * 개인정보 수집및 이용동의 등록
	 * @param terms 개인정보 수집및 이용동의 정보
	 */
	void register(Map<String, Object> terms);
	
	/**
	 * 개인정보 수집및 이용동의 수정
	 * @param terms 대상 개인정보 수집및 이용동의 정보
	 */
	void modify(Map<String, Object> terms);
}
