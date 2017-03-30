package com.kt.giga.home.cms.inquiry.service;

import java.util.*;

/**
 * 상품별 고객정보 조회 서비스
 * @author 한란민
 *
 */
public interface ProductCusInfoService {
	
	/**
	 * 상품별 고객정보 레코드 카운트를 가져온다.
	 * @param searchInfo 검색조건
	 * @return 레코드 카운트
	 */
	int getCount(Map<String, Object> searchInfo);
	
	/**
	 * 상품별 고객정보 리스트를 가져온다.
	 * @param searchInfo 검색조건
	 * @return 상품별 고객정보 리스트
	 */
	List<Map<String, Object>> getList(Map<String, Object> searchInfo);
	
	/**
	 * 카메라 정보를 가져온다.
	 * @param mbrSeq
	 * @return 카메라 정보 리스트
	 */
	List<Map<String, Object>> getSpotDevInfoList(long mbrSeq);
	
	/**
	 * 휴대폰 정보를 가져온다.
	 * @param mbrSeq
	 * @return 휴대폰 정보 리스트
	 */
	List<Map<String, Object>> getTermlInfoList(long mbrSeq);
	
	
	
}
