package com.kt.giga.home.cms.guide.service;

import java.io.IOException;
import java.util.*;

/**
 * 웰컴 가이드 관리 서비스
 * @author 한란민
 *
 */
public interface WelcomeGuideService {
	
	/**
	 * 웰컴가이드 레코드 카운트를 가져온다.
	 * @param searchInfo 검색조건
	 * @return 레코드 카운트
	 */
	int getCount(Map<String, Object> searchInfo);
	
	/**
	 * 웰컴가이드 정보를 갖고온다.
	 * @param searchInfo 검색조건
	 * @return 웰컴가이드
	 */
	Map<String, Object> get(Map<String, Object> searchInfo);
	
	/**
	 * 웰컴가이드 리스트를 가져온다.
	 * @param searchInfo 검색조건
	 * @return 웰컴가이드 리스트
	 */
	List<Map<String, Object>> getList(Map<String, Object> searchInfo);
	
	/**
	 * 웰컴가이드 노출여부 업데이트
	 * @param guide 대상 웰컴가이드 정보
	 */
	void updateOpenYN(Map<String, Object> guide);
	
	/**
	 * 해당 서비스 버전 리스트를 가지고 온다.
	 * @param searchInfo 검색조건
	 * @return guide 대상 버전 리스트
	 */
	List<Map<String, Object>> getVersionList(Map<String, Object> searchInfo);
	
	/**
	 * 웰컴가이드 등록시 같은 상품의 상위버전, 중복버전 체크
	 * @param searchInfo = cpCode, verNum
	 * @return 등록이 가능한지 유무(등록가능:true, 등록불가:false)
	 */
	String checkWelcomeFormData(Map<String, Object> searchInfo);
	
	/**
	 * 웰컴가이드 삭제
	 * @param guide 대상 웰컴가이드 정보
	 */
	void remove(Map<String, Object> guide);
	
	/**
	 * 웰컴가이드 등록
	 * @param guide 웰컴가이드 정보
	 */
	void register(Map<String, Object> guide) throws IllegalStateException, IOException ;
	
	/**
	 * 웰컴가이드 수정
	 * @param guide 대상 웰컴가이드 정보
	 */
	void modify(Map<String, Object> guide) throws IllegalStateException, IOException ;
}
