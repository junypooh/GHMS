package com.kt.giga.home.cms.monitor.service;

import java.util.List;
import java.util.Map;

public interface SignalCheckService {
	
	/**
	 * 카메라 신호세기 대상장치 리스트를 가져온다.
	 * @param searchInfo 검색조건
	 * @return 대상장치 리스트
	 */
	List<Map<String, Object>> getList(Map<String, Object> searchInfo);
	List<Map<String, Object>> getListExcel(List<String> macAddrList);
    
    /**
     * 
     * 홈매니저 게이트웨이 신호세기 대상장치 리스트를 가져온다.
     * @param searchInfo 검색조건
     * @return 대상장치 리스트
     */
    List<Map<String, Object>> getManagerList(Map<String, Object> searchInfo);
    
    /**
     * 홈매니저 단말정보 조회 리스트를 가져온다.
     * @param searchInfo
     * @return
     */
    List<Map<String, Object>> getDeviceList(Map<String, Object> searchInfo);

}
