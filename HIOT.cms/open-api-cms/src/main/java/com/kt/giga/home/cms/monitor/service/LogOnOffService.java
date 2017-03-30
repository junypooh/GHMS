package com.kt.giga.home.cms.monitor.service;

import java.util.List;
import java.util.Map;

public interface LogOnOffService{
	/**
	 * 카메라 로그전송 On/Off 레코드 카운트를 가져온다.
	 * @param searchInfo 검색조건
	 * @return 레코드 카운트
	 */
	int getCount(Map<String, Object> searchInfo);
	
	/**
	 * 카메라 로그전송 On/Off 리스트를 가져온다.
	 * @param searchInfo 검색조건
	 * @return 모니터링시간 리스트
	 */
	List<Map<String, Object>> getList(Map<String, Object> searchInfo);
	
}
