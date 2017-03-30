package com.kt.giga.home.openapi.ghms.ktInfra.client;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.httpclient.HostConfiguration;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethodRetryHandler;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.params.HttpClientParams;
import org.apache.commons.httpclient.params.HttpConnectionManagerParams;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.CommonsClientHttpRequestFactory;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import com.kt.giga.home.openapi.ghms.ktInfra.domain.key.PushInfoRequest;
import com.kt.giga.home.openapi.ghms.ktInfra.host.KPNSServerInfo;

public class RestHttpClient {
	
	private Logger log = LoggerFactory.getLogger(getClass());
	
	private RestTemplate restTemplate;
	
	public RestHttpClient(KPNSServerInfo serverInfo) {
		try {
			HttpConnectionManagerParams connectionManagerParams = new HttpConnectionManagerParams();
			connectionManagerParams.setConnectionTimeout(5000);
			connectionManagerParams.setSoTimeout(5000);
			
			MultiThreadedHttpConnectionManager connectionManager = new MultiThreadedHttpConnectionManager();			
			connectionManager.setParams(connectionManagerParams);
			
			HttpClientParams httpClientParams = new HttpClientParams();
			httpClientParams.setAuthenticationPreemptive(true);
			
			List<HttpMessageConverter<?>> converters = new ArrayList<>();
			converters.add(new StringHttpMessageConverter());
			converters.add(new MappingJackson2HttpMessageConverter());
			
			HttpClient client = new HttpClient(httpClientParams, connectionManager);

			connectionManagerParams.setMaxConnectionsPerHost(HostConfiguration.ANY_HOST_CONFIGURATION, 20);
			connectionManagerParams.setMaxTotalConnections(100);
			connectionManagerParams.setLinger(0);

			CommonsClientHttpRequestFactory factory = new CommonsClientHttpRequestFactory(client);
			
			this.restTemplate = new RestTemplate(factory);
			this.restTemplate.setMessageConverters(converters);
			
		} catch(Exception e) {
			log.error(e.getMessage(), e);
		}
	}
	
	public Map<String, Object> send(String url, HttpMethod method, HttpHeaders headers, PushInfoRequest req, Map<String, Object> res) 
			throws Exception {
		try {
			headers.setContentType(MediaType.APPLICATION_JSON);
			headers.set("Connection", "Close");
			
			HttpEntity reqEntity = new HttpEntity(req, headers);
						
			ResponseEntity<?> httpResponse = restTemplate.exchange(url, method, reqEntity, res.getClass());
			res = (Map<String, Object>)httpResponse.getBody();
			
			res.put("statusCode", httpResponse.getStatusCode().value());
			res.put("statusText", httpResponse.getStatusCode().toString());
			
		} catch (ResourceAccessException e) {
			throw e;
		} catch (HttpClientErrorException e) {
			throw e;
		} catch (Exception e) {
			throw e;
		}
		
		return res;
	}
}

