package com.kt.giga.home.cms.monitor.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import com.kt.iot.reader.vo.LogEvent2;

public interface ManagerHbaseRltService {	
	
	/**
	 * hbase 로그 리스트를 가져온다.
	 * @param searchInfo 검색조건
	 * @return 모니터링시간 리스트
	 */
	List<LogEvent2> getHbase(Map<String, Object> searchInfo) throws IOException, Exception;
}
