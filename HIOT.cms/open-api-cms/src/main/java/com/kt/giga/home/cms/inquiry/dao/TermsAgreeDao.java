package com.kt.giga.home.cms.inquiry.dao;

import java.util.*;

public interface TermsAgreeDao {
	
	int getCount(Map<String, Object> searchInfo);
	
	List<Map<String, Object>> getList(Map<String, Object> searchInfo);

}
