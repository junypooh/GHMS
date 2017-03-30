package com.kt.giga.home.cms.customer.service;

import java.util.*;

/**
 * 팝업공지 서비스
 * @author 김영훈
 *
 */
public interface PopupNoticeService {
	
	/**
	 * 팝업공지 레코드 카운트를 가져온다.
	 * @param searchInfo 검색조건
	 * @return 레코드 카운트
	 */
	int getCount(Map<String, Object> searchInfo);
	
	/**
	 * 날짜 비교하여 레코드 카운트를 가져온다  
	 * @param notice
	 * @return 중복되는 날짜 수 의 카운트(0이 아니면 중복)
	 */
	int checkDateCount(Map<String, Object> notice);
	
	/**
	 * 팝업공지를 가져온다.
	 * @param searchInfo 검색조건
	 * @return 팝업공지
	 */
	Map<String, Object> get(Map<String, Object> searchInfo);
	
	/**
	 * 팝업공지 리스트를 가져온다.
	 * @param searchInfo 검색조건
	 * @return 팝업공지 리스트
	 */
	List<Map<String, Object>> getList(Map<String, Object> searchInfo);
	

	/**
	 * 팝업공지 노출여부 업데이트
	 * @param notice 대상 팝업공지 정보
	 */
	void updateOpenYN(Map<String, Object> notice);
	
	/**
	 * 팝업공지 삭제
	 * @param notice 대상 팝업공지 정보
	 */
	void remove(Map<String, Object> notice);
	
	/**
	 * 팝업공지 등록
	 * @param notice 팝업공지 정보
	 */
	void register(Map<String, Object> notice);
	
	/**
	 * 팝업공지 수정
	 * @param notice 대상 팝업공지 정보
	 */
	void modify(Map<String, Object> notice);
}
