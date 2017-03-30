package com.kt.giga.home.openapi.cms.dao;

import java.util.Map;

import com.kt.giga.home.openapi.cms.domain.VersionFrmwr;

public interface VersionFrmwrDao {
	public VersionFrmwr 	getVerFrmwrLast(Map<String, Object> map);
	public VersionFrmwr 	getVerBetaFrmwrLast(Map<String, Object> map);
}
