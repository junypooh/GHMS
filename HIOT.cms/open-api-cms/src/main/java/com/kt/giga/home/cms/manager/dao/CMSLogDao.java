package com.kt.giga.home.cms.manager.dao;

import java.util.*;

public interface CMSLogDao
{
	/**
	 * @param pk_cms_log CMS 로그 PK
	 * @return CMS 로그 정보
	 */
	Map<String, Object> get(int pk_cms_log);
	
	/**
	 * @param searchInfo 검색정보
	 * @return 검색된 로그 카운트
	 */
	int getCount(Map<String, Object> searchInfo);
	
	/**
	 * @param searchInfo 검색정보
	 * @return 검색된 로그 리스트
	 */
	List<Map<String, Object>> getList(Map<String, Object> searchInfo);
	
	/**
	 * @param cmsLog 로그정보
	 * @return 등록된 로그 pk_cms_log 값
	 */
	void register(Map<String, Object> cmsLog);
}
