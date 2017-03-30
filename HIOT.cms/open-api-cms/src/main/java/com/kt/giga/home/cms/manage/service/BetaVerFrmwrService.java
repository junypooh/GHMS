package com.kt.giga.home.cms.manage.service;

import java.io.IOException;
import java.util.*;

/**
 * 
 * @author 한란민
 * 테스터 단말 펌웨어 버전관리
 *
 */
public interface BetaVerFrmwrService {
	
	/**
	 * 테스터 단말 펌웨어 버전관리 레코드 카운트를 가져온다.
	 * @param searchInfo 검색조건
	 * @return 레코드 카운트
	 */
	int getCount(Map<String, Object> searchInfo);
	
	/**
	 * 테스터 단말 펌웨어 버전관리를 가져온다.
	 * @param searchInfo 검색조건
	 * @return 단말 펌웨어 버전 관리
	 */
	Map<String, Object> get(Map<String, Object> searchInfo);
	
	/**
	 * 테스터 단말 펌웨어 버전관리 리스트를 가져온다.
	 * @param searchInfo 검색조건
	 * @return 단말 펌웨어 버전 관리 리스트
	 */
	List<Map<String, Object>> getList(Map<String, Object> searchInfo);
	
	/**
	 * 테스터 단말 펌웨어 버전관리 등록
	 * @param notice 단말 펌웨어 버전 관리 정보
	 */
	void register(Map<String, Object> notice);
	
	/**
	 * 테스터 단말 펌웨어 버전관리 수정
	 * @param notice 대상 단말 펌웨어 버전 관리 정보
	 */
	void modify(Map<String, Object> notice);
	
	/**
	 * 테스터 단말 펌웨어 중복 버전 업데이트 여부 체크 
	 * @param searchInfo
	 * @return 레코드 카운트
	 */
	String checkVerFrmwrFormData(Map<String, Object> searchInfo);
	
	
	/***
	 * 테스터 펌웨어 버전 관리 삭제 
	 * @param verFrmwr
	 * @throws IllegalStateException
	 * @throws IOException
	 *///
	void remove(Map<String, Object> verFrmwr) throws IllegalStateException, IOException;
}
