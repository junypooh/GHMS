package com.kt.giga.home.cms.manage.service;

import java.io.IOException;
import java.util.*;

/**
 * App 버전 관리 서비스
 * @author 김영훈
 *
 *///
public interface VerAppService {
	
	/**
	 * App 버전 관리 레코드 카운트를 가져온다.
	 * @param searchInfo 검색조건
	 * @return 레코드 카운트
	 */
	int getCount(Map<String, Object> searchInfo);
	
	/**
	 * App 버전 관리를 가져온다.
	 * @param searchInfo 검색조건
	 * @return App 버전 관리
	 */
	Map<String, Object> get(Map<String, Object> searchInfo);
	
	/**
	 * App 버전 관리 리스트를 가져온다.
	 * @param searchInfo 검색조건
	 * @return App 버전 관리 리스트
	 */
	List<Map<String, Object>> getList(Map<String, Object> searchInfo);

	/**
	 * App 버전 관리 등록
	 * @param manage App 버전 관리 정보
	 */
	void register(Map<String, Object> verApp) throws IllegalStateException, IOException ;
	
	/**
	 * App 버전 관리 수정
	 * @param manage 대상 App 버전 관리 정보
	 */
	void modify(Map<String, Object> verApp) throws IllegalStateException, IOException ;
	
	/**
	 * APP 버전 중복 버전에 관한 카운트를 가져온다. 
	 * @param searchInfo
	 * @return 레코드 카운트
	 */
	String checkVerAppFormData(Map<String, Object> searchInfo);
	
	/***
	 * App 버전 업데이트 여부 체크한다. 
	 * @param searchInfo
	 * @return
	 */
	String verAppUpdateCheck(Map<String, Object> searchInfo);
	
	/***
	 * App 버전 관리 삭제 
	 * @param verApp
	 * @throws IllegalStateException
	 * @throws IOException
	 */
	void remove(Map<String, Object> verApp) throws IllegalStateException, IOException;
}
