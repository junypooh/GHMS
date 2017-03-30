package com.kt.giga.home.openapi.cms.dao;

import java.util.List;
import java.util.Map;

import com.kt.giga.home.openapi.cms.domain.FileInfo;
import com.kt.giga.home.openapi.cms.domain.GuideCamera;

public interface GuideCameraDao {
	public GuideCamera 			getGuideCamera(Map<String, Object> map);
	public List<GuideCamera> 	getGuideCameraList(Map<String, Object> map);
	public List<FileInfo>			getGuideCameraImg(Map<String, Object> map);
}
