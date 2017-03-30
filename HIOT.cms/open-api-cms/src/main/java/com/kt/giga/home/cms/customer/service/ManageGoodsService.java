package com.kt.giga.home.cms.customer.service;

import java.util.*;

/**
 * 상품 별 기능 관리 서비스
 * @author 김영훈
 *
 */
public interface ManageGoodsService {
	
	/**
	 * 상품 별 기능 관리 레코드 카운트를 가져온다.
	 * @param searchInfo 검색조건
	 * @return 레코드 카운트
	 */
	int getCount(Map<String, Object> searchInfo);
	
	/**
	 * 상품 별 기능 관리를 가져온다.
	 * @param searchInfo 검색조건
	 * @return 상품 별 기능 관리
	 */
	Map<String, Object> get(Map<String, Object> searchInfo);
	
	/**
	 * 상품 별 기능 관리 리스트를 가져온다.
	 * @param searchInfo 검색조건
	 * @return 상품 별 기능 관리 리스트
	 */
	List<Map<String, Object>> getList(Map<String, Object> searchInfo);
	
	/**
	 * 상품 별 기능 관리 삭제
	 * @param manageGoods 대상 상품 별 기능 관리 정보
	 */
	void remove(Map<String, Object> manageGoods);
	
	/**
	 * 상품 별 기능 관리 등록
	 * @param manageGoods 상품 별 기능 관리 정보
	 */
	void register(Map<String, Object> manageGoods);
	
	/**
	 * 상품 별 기능 관리 수정
	 * @param manageGoods 대상 상품 별 기능 관리 정보
	 */
	void modify(Map<String, Object> manageGoods);
	
	/**
	 * 상품 별 기능 중복 버전 업데이트 여부 체크 
	 * @param searchInfo
	 * @return 레코드 카운트
	 */
	int checkManageGoodsFormData(Map<String, Object> searchInfo);
}
