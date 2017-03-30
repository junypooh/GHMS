package com.kt.giga.home.openapi.health.exercise.dao;

import java.util.*;

public interface ExerciseDao {

	Map<String, Object> getService(Map<String, Object> searchInfo);
	
	Map<String, Object> getDevice(Map<String, Object> searchInfo);
	
	Map<String, Object> getDeviceModel(int devModelSeq);
}
