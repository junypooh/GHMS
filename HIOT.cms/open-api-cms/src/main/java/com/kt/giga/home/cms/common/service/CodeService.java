package com.kt.giga.home.cms.common.service;

import java.util.*;

import com.kt.giga.home.cms.common.domain.*;

/**
 * 기초코드 서비스
 * @author 김용성
 *
 */
public interface CodeService {

	/**
	 * 서비스 이름 조회
	 * @param svcCode 서비스 코드
	 * @return 서비스 이름
	 */
	String getServiceName(String svcCode);
	
	/**
	 * 코드 조회
	 * @param code 코드
	 * @return 코드
	 */
	Code get(String code);
	
	/**
	 * 코드 리스트 조회
	 * @param upCode 상위코드
	 * @return 코드리스트
	 */
	List<Code> getList(String upCode);
	
	String getUrl(String pkCode);
	
}
