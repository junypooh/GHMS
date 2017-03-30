package com.kt.giga.home.openapi.health.profile.dao;

import java.util.*;

public interface ProfileDao {
	
	int getMbrSeq(String mbrId);

	Map<String, Object> get(int mbrSeq);
	
	void register(Map<String, Object> profile);
	
	void modify(Map<String, Object> profile);
}
