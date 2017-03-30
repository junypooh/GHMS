package com.kt.giga.home.cms.monitor.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface SignalResultService {	
	
	/**
	 * 카메라 신호세기 결과조회 리스트를 가져온다.
	 * @param searchInfo 검색조건
	 * @return 모니터링시간 리스트
	 */
	List<Map<String, Object>> getList(Map<String, Object> searchInfo) throws IOException, Exception;

	List<Map<String, Object>> getListPage(Map<String, Object> searchInfo) throws IOException, Exception;

}
