package com.kt.giga.home.infra.client.http;

import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.util.MultiValueMap;

import com.kt.giga.home.infra.client.host.ServerInfo;
import com.kt.giga.home.infra.domain.commons.HttpObjectRequest;
import com.kt.giga.home.infra.domain.commons.HttpObjectResponse;

public abstract class InfraHttpClient {
	
	public abstract HttpObjectResponse send(String url, HttpObjectRequest req, HttpObjectResponse res) throws Exception;
	
	public abstract HttpObjectResponse send(String url, HttpMethod method, HttpHeaders headers, HttpObjectRequest req, HttpObjectResponse res) throws Exception;
	
	public abstract Object sendForm(String url, HttpMethod method, MultiValueMap<String, String> map) throws Exception;
	
}