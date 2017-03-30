package com.kt.giga.home.cms.customer.service;

import java.util.*;

/**
 * 
 * @author 김영훈
 *
 */
public interface ServiceUrlService {
	
	/**
	 * 서비스안내 URL을 가져온다.
	 * @return
	 */
	String getUrl(Map<String, Object> service);
	
	/**
	 * URL을 수정한다.
	 * @param notice
	 */
	void modify(Map<String, Object> service);
}
