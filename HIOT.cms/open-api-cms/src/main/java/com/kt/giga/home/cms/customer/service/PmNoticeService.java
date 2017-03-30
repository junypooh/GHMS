package com.kt.giga.home.cms.customer.service;

import java.util.*;

/**
 * PM공지 서비스
 * @author 김영훈
 *
 */
public interface PmNoticeService {
	
	/**
	 * PM공지 레코드 카운트를 가져온다.
	 * @param searchInfo 검색조건
	 * @return 레코드 카운트
	 */
	int getCount(Map<String, Object> searchInfo);

	/**
	 * 날짜 비교하여 레코드 카운트를 가져온다  
	 * @param notice
	 * @return 중복되는 예약 설정 안함이면서 노출여부가 Y인 레코드의 카운트 
	 */
	int checkDateCount(Map<String, Object> notice);
	//
	/**
	 * PM공지를 가져온다.
	 * @param searchInfo 검색조건
	 * @return PM공지
	 */
	Map<String, Object> get(Map<String, Object> searchInfo);
	
	/**
	 * PM공지 리스트를 가져온다.
	 * @param searchInfo 검색조건
	 * @return PM공지 리스트
	 */
	List<Map<String, Object>> getList(Map<String, Object> searchInfo);
	
	/**
	 * PM공지 노출여부 업데이트
	 * @param notice 대상 PM공지 정보
	 */
	void updateOpenYN(Map<String, Object> notice);
	
	/**
	 * PM공지 삭제
	 * @param notice 대상 PM공지 정보
	 */
	void remove(Map<String, Object> notice);
	
	/**
	 * PM공지 등록
	 * @param notice PM공지 정보
	 */
	void register(Map<String, Object> notice);
	
	/**
	 * PM공지 수정
	 * @param notice 대상 PM공지 정보
	 */
	void modify(Map<String, Object> notice);
}
