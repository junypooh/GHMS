package com.kt.giga.home.cms.customer.service;

import java.util.*;

/**
 * 일반공지 서비스
 * @author 김용성
 *
 */
public interface GeneralNoticeService {
	
	/**
	 * 일반공지 레코드 카운트를 가져온다.
	 * @param searchInfo 검색조건
	 * @return 레코드 카운트
	 */
	int getCount(Map<String, Object> searchInfo);
	
	/**
	 * 우선노출(중요) 레코드 카운트 가져온다.
	 * @param cpCode 서비스 코드
	 * @return 우선노출(중요) 레코드 카운트
	 */
	int getPreorderCount(String cpCode);
	
	/**
	 * 일반공지를 가져온다.
	 * @param searchInfo 검색조건
	 * @return 일반공지
	 */
	Map<String, Object> get(Map<String, Object> searchInfo);
	
	/**
	 * 일반공지 리스트를 가져온다.
	 * @param searchInfo 검색조건
	 * @return 일반공지 리스트
	 */
	List<Map<String, Object>> getList(Map<String, Object> searchInfo);
	
	/**
	 * 일반공지 노출여부 업데이트
	 * @param notice 대상 일반공지 정보
	 */
	void updateOpenYN(Map<String, Object> notice);
	
	/**
	 * 일반공지 우선노출(중요) 업데이트
	 * @param notice 대상 일반공지 정보
	 */
	void updatePreorderYN(Map<String, Object> notice);
	
	/**
	 * 일반공지 삭제
	 * @param notice 대상 일반공지 정보
	 */
	void remove(Map<String, Object> notice);
	
	/**
	 * 일반공지 등록
	 * @param notice 일반공지 정보
	 */
	void register(Map<String, Object> notice);
	
	/**
	 * 일반공지 수정
	 * @param notice 대상 일반공지 정보
	 */
	void modify(Map<String, Object> notice);
}
