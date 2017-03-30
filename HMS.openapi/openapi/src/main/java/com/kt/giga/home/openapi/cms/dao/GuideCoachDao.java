package com.kt.giga.home.openapi.cms.dao;

import java.util.List;
import java.util.Map;

import com.kt.giga.home.openapi.cms.domain.FileInfo;
import com.kt.giga.home.openapi.cms.domain.GuideCoach;

public interface GuideCoachDao {
	public List<GuideCoach>		getGuideCoach(Map<String, Object> map);
	public List<GuideCoach> 	getGuideCoachList(Map<String, Object> map);
	public List<FileInfo>			getGuideCoachImg(Map<String, Object> map);
}
