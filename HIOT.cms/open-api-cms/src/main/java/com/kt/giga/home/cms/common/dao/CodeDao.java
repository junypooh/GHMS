package com.kt.giga.home.cms.common.dao;

import java.util.*;

import com.kt.giga.home.cms.common.domain.*;

public interface CodeDao {
	
	/**
	 * 서비스 이름 조회
	 * @param svcCode 서비스 코드
	 * @return 서비스 이름
	 */
	String getServiceName(String svcCode);	
	
	Code get(String code);
	
	List<Code> getList(String upCode);
	
	String getUrl(String pkCode);
}
