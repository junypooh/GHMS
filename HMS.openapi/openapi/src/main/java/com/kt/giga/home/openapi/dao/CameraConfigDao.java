package com.kt.giga.home.openapi.dao;

import java.util.Map;

import com.kt.giga.home.openapi.domain.CameraConfig;

public interface CameraConfigDao {
	public CameraConfig getCameraConfig(Map<String, Object> map);
}
