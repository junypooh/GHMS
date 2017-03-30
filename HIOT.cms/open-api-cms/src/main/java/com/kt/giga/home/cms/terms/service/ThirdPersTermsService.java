package com.kt.giga.home.cms.terms.service;

import java.util.*;

/**
 * 제3자취급위탁 약관 관리 서비스
 * @author 한란민
 *
 */
public interface ThirdPersTermsService {
	
	/**
	 * 제3자취급위탁 레코드 카운트를 가져온다.
	 * @param searchInfo 검색조건
	 * @return 레코드 카운트
	 */
	int getCount(Map<String, Object> searchInfo);
	
	/**
	 * 제3자취급위탁 정보를 갖고온다.
	 * @param searchInfo 검색조건
	 * @return 제3자취급위탁
	 */
	Map<String, Object> get(Map<String, Object> searchInfo);
	
	/**
	 * 제3자취급위탁 리스트를 가져온다.
	 * @param searchInfo 검색조건
	 * @return 제3자취급위탁 리스트
	 */
	List<Map<String, Object>> getList(Map<String, Object> searchInfo);
	
	/**
	 * 제3자취급위탁 등록시 같은 상품의 상위버전, 중복버전 체크
	 * @param searchInfo = cpCode, termsVer, termsCode
	 * @return 등록이 가능한지 유무
	 */
	String checkThirdTermsFormData(Map<String, Object> searchInfo);
	
	/**
	 * 제3자취급위탁 삭제
	 * @param terms 대상 제3자취급위탁 정보
	 */
	void remove(Map<String, Object> terms);
	
	/**
	 * 제3자취급위탁 등록
	 * @param terms 제3자취급위탁 정보
	 */
	void register(Map<String, Object> terms);
	
	/**
	 * 제3자취급위탁 수정
	 * @param terms 대상 제3자취급위탁 정보
	 */
	void modify(Map<String, Object> terms);
}
