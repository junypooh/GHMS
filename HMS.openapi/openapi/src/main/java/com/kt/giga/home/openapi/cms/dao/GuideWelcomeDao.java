package com.kt.giga.home.openapi.cms.dao;

import java.util.List;
import java.util.Map;

import com.kt.giga.home.openapi.cms.domain.FileInfo;
import com.kt.giga.home.openapi.cms.domain.GuideWelcome;

public interface GuideWelcomeDao {
	public GuideWelcome 		getGuideWelcome(Map<String, Object> map);
	public List<GuideWelcome> 	getGuideWelcomeList(Map<String, Object> map);
	public List<FileInfo>			getGuideWelcomeImg(Map<String, Object> map);
}
