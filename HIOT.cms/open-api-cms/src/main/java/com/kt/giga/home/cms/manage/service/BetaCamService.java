package com.kt.giga.home.cms.manage.service;

import java.util.*;

/**
 * 
 * @author 한란민
 * 테스터 단말 관리
 *
 */

public interface BetaCamService {
	
	List<Map<String, Object>> getCamList();
	
	List<Map<String, Object>> getCamInfoList(Map<String, Object> searchInfo);
	
	void register(Map<String, Object> verFrmwr);
	
	void remove(Map<String, Object> verFrmwr);
}
