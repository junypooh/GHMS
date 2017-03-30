package com.kt.giga.home.infra.client.http;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import com.google.gson.Gson;
import com.kt.giga.home.infra.client.host.ServerInfo;
import com.kt.giga.home.infra.domain.commons.HttpObjectRequest;
import com.kt.giga.home.infra.domain.commons.HttpObjectResponse;
import com.kt.giga.home.infra.domain.commons.HttpRestObjectResponse;
import com.kt.giga.home.infra.util.JsonUtils;

import org.apache.commons.httpclient.HostConfiguration;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.params.HttpClientParams;
import org.apache.commons.httpclient.params.HttpConnectionManagerParams;
import org.apache.commons.lang.StringUtils;
import org.apache.tomcat.util.net.URL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.CommonsClientHttpRequestFactory;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

public class RestHttpClient extends InfraHttpClient {
	
	private final Logger log = LoggerFactory.getLogger(getClass());
	
	private RestTemplate restTemplate;
	
	private String hostlist;
	
	public RestHttpClient(ServerInfo serverInfo) {
		try {			
			HttpConnectionManagerParams connectionManagerParams = new HttpConnectionManagerParams();
			connectionManagerParams.setConnectionTimeout(5 * 1000);
			connectionManagerParams.setSoTimeout(5 * 1000);
			
			MultiThreadedHttpConnectionManager connectionManager = new MultiThreadedHttpConnectionManager();			
			connectionManager.setParams(connectionManagerParams);
			
			HttpClientParams httpClientParams = new HttpClientParams();
			httpClientParams.setAuthenticationPreemptive(true);
			
			List<HttpMessageConverter<?>> converters = new ArrayList<>();
			converters.add(new StringHttpMessageConverter());
			converters.add(new MappingJackson2HttpMessageConverter());
			converters.add(new FormHttpMessageConverter());
			
			HttpClient client = new HttpClient(httpClientParams, connectionManager);
			
			if(serverInfo.getProxyHost() != null && !"".equals(serverInfo.getProxyHost())) {
				HostConfiguration config = client.getHostConfiguration();
				config.setProxy(serverInfo.getProxyHost(), serverInfo.getProxyPort());
			}
			
			connectionManagerParams.setMaxConnectionsPerHost(HostConfiguration.ANY_HOST_CONFIGURATION, 300);
			connectionManagerParams.setMaxTotalConnections(1000);
			
			CommonsClientHttpRequestFactory factory = new CommonsClientHttpRequestFactory(client);
			
			this.restTemplate = new RestTemplate(factory);
			this.restTemplate.setMessageConverters(converters);
			
			this.hostlist = serverInfo.getHostlist();
			
		} catch(Exception e) {
			log.error(e.getMessage(), e.fillInStackTrace());
		}
	}
	
	public HttpObjectResponse send(String url, HttpObjectRequest req, HttpObjectResponse res) 
			throws Exception {
		return this.send(url, HttpMethod.POST, new HttpHeaders(), req, res);
	}
	
	@Override	
	public HttpObjectResponse send(String url, HttpMethod method, HttpHeaders headers, HttpObjectRequest req, HttpObjectResponse res) 
			throws Exception {
		try {
			headers.setContentType(MediaType.APPLICATION_JSON);
			headers.set("Connection", "Close");
			
			HttpEntity reqEntity = new HttpEntity(req, headers);
						
			ResponseEntity<?> httpResponse = null;	
			LinkedList<String> urlList = getRandomUrlList(url);
			while(!urlList.isEmpty()) {
				String fullUrl = urlList.get(0);
				urlList.remove(0);
				try {
					log.info("# Send Request to : {}\n{}", fullUrl, JsonUtils.toPrettyJson(req));
					httpResponse = restTemplate.exchange(fullUrl, method, reqEntity, res.getClass());
					log.info("# Recv Response from : {}\n{}", fullUrl, JsonUtils.toPrettyJson(httpResponse));
					break;
				} catch(ResourceAccessException e) {
					log.warn(e.getMessage(), e.fillInStackTrace());
				} catch(HttpClientErrorException e) {
					log.warn(e.getMessage(), e.fillInStackTrace());
				}				
			}
			
			if((HttpObjectResponse)httpResponse.getBody() != null) {
				res = (HttpObjectResponse)httpResponse.getBody();
			}
			
			if(res instanceof HttpRestObjectResponse) {
				((HttpRestObjectResponse) res).setStatusCode(httpResponse.getStatusCode().value());
				((HttpRestObjectResponse) res).setStatusText(httpResponse.getStatusCode().toString());
			}

		} catch (Exception e) {
			log.warn(e.getMessage(), e);
			throw e;
		}
		
		return res;
	}
	
	@Override	
	public String sendForm(String url, HttpMethod method, MultiValueMap<String, String> map) throws Exception {
		String res = "";

		try {
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
			headers.set("Connection", "Close");
			
			HttpEntity<MultiValueMap<String, String>> req = new HttpEntity<MultiValueMap<String, String>>(map, headers);
			
			ResponseEntity<?> httpResponse = null;	
			LinkedList<String> urlList = getRandomUrlList(url);
			while(!urlList.isEmpty()) {
				String fullUrl = urlList.get(0);
				urlList.remove(0);
				try {
					log.info("# Send Request to : {}\n{}", fullUrl, map);
					httpResponse = restTemplate.exchange(fullUrl, method, req, res.getClass());
					
					Map<String, String> resMap = null;
					
					if(httpResponse.getBody() != null) {
						res = (String)httpResponse.getBody();
						
						if(res.charAt(0) != '[' && res.charAt(0) != '{') {
							res += "&statusCode=" + httpResponse.getStatusCode().value();
							res += "&statusText=" + httpResponse.getStatusCode().toString();
							
							resMap = this.toMap(res);
						} else {
							resMap = new Gson().fromJson(res, Map.class);
						}
					}
					
					log.info("# Recv Response from : {}\n{}", fullUrl, JsonUtils.toPrettyJson(resMap));
					break;
				} catch(ResourceAccessException e) {
					log.warn(e.getMessage(), e.fillInStackTrace());
				} catch(HttpClientErrorException e) {
					log.warn(e.getMessage(), e.fillInStackTrace());
				}				
			}
			
		} catch (Exception e) {
			log.warn(e.getMessage(), e);
			throw e;
		}
		
		return res;
	}
	
	// 서버 개수만큼의 서버 목록을 순서 섞어서 리턴
	private LinkedList<String> getRandomUrlList(String urlStr) {
		URL url = null;
		int port = 80;
		String query = "";
		try {
			url = new URL(urlStr);
			if(url.getPort() > 0) port = url.getPort();
			if(url.getQuery() != null) query = "?" + url.getQuery();
		} catch (MalformedURLException e) {
			log.error(e.getMessage());
		}
		
		LinkedList<String> list = new LinkedList<String>();
		String[] serverList = StringUtils.split(this.hostlist, "|");

		for(int i = 0; i < serverList.length; i++) {
			list.add(url.getProtocol() + "://" + serverList[i] + ":" + port + url.getPath() + query);
		}

		Collections.shuffle(list);

		return list;
	}
	
	public Map toMap(String body) {
		Map map = new HashMap();
		
		StringTokenizer st = new StringTokenizer(body, "&");
		while(st.hasMoreTokens()) {
			String param = st.nextToken();			
			map.put(param.substring(0, param.indexOf("=")), param.substring(param.indexOf("=") + 1, param.length()));
		}		
		
		return map;
	}
}

