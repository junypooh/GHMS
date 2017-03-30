package com.kt.giga.home.openapi.ghms.user.dao;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.ibatis.annotations.Param;

/**
 * 앱 초기 실행 매퍼 인터페이스
 * 
 * @author 조홍진
 *
 */
public interface InitDao {

	/**
	 * 앱 초기 실행 프라퍼티 조회
	 * 
	 * @param svcCode	서비스 코드
	 * @return			초기 실행 프라퍼티 리스트
	 */
	public ArrayList<HashMap<String, String>> getInitProps(
			@Param("svcCode") String svcCode
			);
	
}
