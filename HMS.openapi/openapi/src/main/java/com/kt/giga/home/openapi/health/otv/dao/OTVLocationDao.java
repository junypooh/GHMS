package com.kt.giga.home.openapi.health.otv.dao;

import java.util.*;

public interface OTVLocationDao {
	Map<String, Object> selectOtvConf(Map<String, Object> input);
	void insertOtvConf(Map<String, Object> input);
	void updateOtvConf(Map<String, Object> input);
	void deleteOtvConf(Map<String, Object> input);
	int getOtvConfCnt(Map<String, Object> input);
}
