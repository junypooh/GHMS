package com.kt.giga.home.openapi.health.otv.dao;

import java.util.*;

public interface OTVDao {
	Map<String, Object> getService(Map<String, Object> searchInfo);
	
	Map<String, Object> getDevice(Map<String, Object> searchInfo);
}
