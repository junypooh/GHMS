package com.kt.giga.home.openapi.cms.dao;

import java.util.List;
import java.util.Map;

import com.kt.giga.home.openapi.cms.domain.VersionApp;

public interface VersionAppDao {
	public VersionApp 			getVerAppLast(Map<String, Object> map);
	public List<VersionApp> 	getVerAppList(Map<String, Object> map);
}
