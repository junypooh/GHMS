package com.kt.giga.home.openapi.dao;

import java.util.List;
import java.util.Map;

import org.json.simple.JSONObject;

import com.kt.giga.home.openapi.domain.SpotDevBas;

public interface SpotDevBasDao {
	public SpotDevBas getSpotDevBas(String spotDevId);
	public List<SpotDevBas> getSpotDevBasList(Map<String, Object> map);
	public SpotDevBas setSpotDevBas(Map<String, Object> map);
}
