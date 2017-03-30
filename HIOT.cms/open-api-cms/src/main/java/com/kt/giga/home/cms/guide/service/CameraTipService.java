package com.kt.giga.home.cms.guide.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * 코치 가이드 관리 서비스
 * @author
 *
 */
public interface CameraTipService {
	/**
	 * 카메라 팁 레코드 카운트를 가져온다.
	 * @param searchInfo 검색조건
	 * @return 레코드 카운트
	 */
	int getCount(Map<String, Object> searchInfo);
	
	/**
	 * 카메라 팁 정보를 갖고온다.
	 * @param searchInfo 검색조건
	 * @return 카메라 팁 
	 */
	Map<String, Object> get(Map<String, Object> searchInfo);
	
	/**
	 * 카메라 팁 리스트를 가져온다.
	 * @param searchInfo 검색조건
	 * @return 코치가이드 리스트
	 */
	List<Map<String, Object>> getList(Map<String, Object> searchInfo);
	
	/**
	 * 카메라 팁 노출여부 업데이트
	 * @param guide 대상 코치가이드 정보
	 */
	void updateOpenYN(Map<String, Object> guide);
	
	/**
	 * 해당 서비스 버전 리스트를 가지고 온다.
	 * @param searchInfo 검색조건
	 * @return guide 대상 버전 리스트
	 */
	List<Map<String, Object>> getVersionList(Map<String, Object> searchInfo);
	
	/**
	 * 카메라 팁 등록시 같은 상품, 가이드위치의 상위버전, 중복버전 체크
	 * @param searchInfo = cpCode, verNum, positionCode
	 * @return 등록이 가능한지 유무(등록가능:true, 등록불가:false)
	 */
	String checkCameraTipFormData(Map<String, Object> searchInfo);
	
	/**
	 * 카메라 팁 삭제
	 * @param guide 대상 코치가이드 정보
	 */
	void remove(Map<String, Object> guide);
	
	/**
	 * 카메라 팁 등록
	 * @param guide 코치가이드 정보
	 */
	void register(Map<String, Object> guide) throws IllegalStateException, IOException ;
	
	/**
	 * 카메라 팁 수정
	 * @param guide 대상 코치가이드 정보
	 */
	void modify(Map<String, Object> guide) throws IllegalStateException, IOException ;
}
