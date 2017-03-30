package com.kt.giga.home.openapi.health.device.dao;

import java.util.*;

public interface DeviceSensorDao {
	
	Map<String, Object> get(Map<String, Object> searchInfo);

	void register(Map<String, Object> device);
}
