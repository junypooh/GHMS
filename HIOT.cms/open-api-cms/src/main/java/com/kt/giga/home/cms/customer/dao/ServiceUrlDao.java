package com.kt.giga.home.cms.customer.dao;

import java.util.*;

public interface ServiceUrlDao {
	
	String getUrl(Map<String, Object> service); 
	
	void modify(Map<String, Object> service);
}
