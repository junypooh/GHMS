package com.kt.giga.home.cms.inquiry.service;

import java.util.*;

public interface TermsAgreeService {

	int getCount(Map<String, Object> searchInfo);
	
	List<Map<String, Object>> getList(Map<String, Object> searchInfo);
}
