package com.kt.giga.home.cms.manager.dao;

import java.util.*;

public interface CMSLogParamDao
{
	/**
	 * @param fk_cms_log FK CMS 로그
	 * @return 로그 파라메터 리스트
	 */
	List<Map<String, Object>> getList(int fk_cms_log);
	
	/**
	 * @param cmsLogParam 로그 파라메터
	 */
	void register(Map<String, Object> cmsLogParam);
}
