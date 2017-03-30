/*******************************************************************************
 * Copyright(c) 2015 KT. All rights reserved.
 * This software is the proprietary information of KT.
 *******************************************************************************/
package com.kt.giga.home.infra.client.http;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.util.MultiValueMap;

import com.kt.giga.home.infra.domain.commons.HttpObjectRequest;
import com.kt.giga.home.infra.domain.commons.HttpObjectResponse;

/**
 * 
 * @author junypooh ( kyungjun.park@ceinside.co.kr )
 * @since 2015. 6. 10.
 */
public abstract class CheckerHttpClient {
	
	public abstract HttpObjectResponse send(String url, HttpObjectRequest req, HttpObjectResponse res, String action) throws Exception;
	
	public abstract HttpObjectResponse send(String url, HttpMethod method, HttpHeaders headers, HttpObjectRequest req, HttpObjectResponse res, String action) throws Exception;
	
	public abstract Object sendForm(String url, HttpMethod method, MultiValueMap<String, String> map) throws Exception;

}
