package com.kt.giga.home.cms.inquiry.dao;

import java.util.*;

public interface ProductCusInfoDao {
	
	int getCount(Map<String, Object> searchInfo);
	
	List<Map<String, Object>> getList(Map<String, Object> searchInfo);
	
	List<Map<String, Object>> getSpotDevInfoList(long mbrSeq);
	
	List<Map<String, Object>> getTermlInfoList(long mbrSeq);
}
