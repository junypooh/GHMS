/*******************************************************************************
 * Copyright(c) 2015 KT. All rights reserved.
 * This software is the proprietary information of KT.
 *******************************************************************************/
package com.kt.giga.home.openapi.ghms.ktInfra.service;

import java.sql.SQLException;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import org.apache.commons.httpclient.HostConfiguration;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.params.HttpClientParams;
import org.apache.commons.httpclient.params.HttpConnectionManagerParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.CommonsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.kt.giga.home.openapi.ghms.util.exception.APIException;
import com.kt.giga.home.openapi.ghms.util.json.JsonUtils;
import com.kt.giga.home.openapi.ghms.util.properties.APIEnv;

/**
 * 테스트 용
 * @author junypooh ( kyungjun.park@ceinside.co.kr )
 * @since 2015. 6. 30.
 */
@Service
@Transactional(
        propagation = Propagation.REQUIRED,
        rollbackFor = {
                Exception.class,
                RuntimeException.class,
                SQLException.class
        }
)
public class CheckerService {

    /**
     * 로거
     */ 
    private Logger log = LoggerFactory.getLogger(getClass());

	private RestTemplate restTemplate;

    /**
     * OpenAPI 환경 프라퍼티
     */     
    @Autowired
    private APIEnv env;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly=true)
    public Map<String, Object> test(Map<String, Object> params) throws APIException {
        
        String response = sendPostRequestToKTInfra(params.get("url").toString(), params);
        HashMap<String, Object> responseMap = JsonUtils.fromJson(response, new TypeToken<HashMap<String, Object>>(){}.getType());
        
        return responseMap;
	}


    /**
     * KT 인프라 서버로 HTTP Post 요청 메쏘드
     * 
     * @param url               URL
     * @param o                 요청 오브젝트
     * @return                  처리 결과 스트링
     */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly=true)
    private String sendPostRequestToKTInfra(String url, Object o) {

        String result = null;
        try {
        	Map<String, Object> params = (Map<String, Object>)o;
            params.remove("url");
//            String json = toJson(o);
            String json = toJson(params);
            
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<String> entity = new HttpEntity<String>(json, headers);

			LinkedList<String> list = getRandomKtInfraServer();
			while(!list.isEmpty()) {
				String fullUrl = env.getProperty("ktinfra.protocol") + "://" + list.get(0) + ":" + env.getProperty("ktinfra.port") + url;
				list.remove(0);

				try	{
					log.info("# Send Request to KTInfra : {}\n{}", fullUrl, json);
					result = getKTInfraRestTemplate().postForObject(fullUrl, entity, String.class);
					log.info("# Recv Response from KTInfra : {}\n{}", fullUrl, JsonUtils.toPrettyJson(result));
					break;
				} catch(ResourceAccessException e) {
					log.warn(e.getMessage(), e.fillInStackTrace());
				} catch(HttpClientErrorException e) {
					log.warn(e.getMessage(), e.fillInStackTrace());
				}
			}
        } catch(Exception e) {
            
            log.warn(e.getMessage(), e);
            throw e;
        }
        
        return result;
    } 
    
    /**
     * JSON Serialize 메쏘드
     * 
     * @param o                 오브젝트
     * @return                  JSON 스트링
     */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly=true)
    private String toJson(Object o) {
        return new GsonBuilder().setPrettyPrinting().create().toJson(o);
    } 

	// 인프라 서버 개수만큼의 서버IP목록을 순서 섞어서 리턴
	@Transactional(propagation = Propagation.SUPPORTS, readOnly=true)
	public LinkedList<String> getRandomKtInfraServer() {
		LinkedList<String> list = new LinkedList<String>();
		String[] ktinfraServerList = env.splitProperty("ktinfra.serverList");

		for(int i = 0; i < ktinfraServerList.length; i++) {
			list.add(ktinfraServerList[i]);
		}

		Collections.shuffle(list);

		return list;
	}
    
    /**
     * KT 인프라 서버 HTTP 연동 위한 RestTemplate 생성 메쏘드
     * 
     * @return                  RestTemplate
     */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly=true)
    private RestTemplate getKTInfraRestTemplate() {
		
		if(restTemplate == null) {
			
			try {

				int readTimeout = env.getIntProperty("ktinfra.timeout.read");
				int connTimeout = env.getIntProperty("ktinfra.timeout.conn");
				
				HttpClientParams httpClientParams = new HttpClientParams();
				httpClientParams.setConnectionManagerTimeout(connTimeout);
				httpClientParams.setSoTimeout(readTimeout);
				
				HttpConnectionManagerParams connectionManagerParams = new HttpConnectionManagerParams();
				connectionManagerParams.setMaxConnectionsPerHost(HostConfiguration.ANY_HOST_CONFIGURATION, env.getIntProperty("ktinfra.conn.host"));
				connectionManagerParams.setMaxTotalConnections(env.getIntProperty("ktinfra.conn.max"));
				
				MultiThreadedHttpConnectionManager connectionManager = new MultiThreadedHttpConnectionManager();
				connectionManager.setParams(connectionManagerParams);
				
				CommonsClientHttpRequestFactory factory = new CommonsClientHttpRequestFactory(new HttpClient(httpClientParams, connectionManager));
				restTemplate = new RestTemplate(factory);
				
			} catch(Exception e) {
				
				log.warn(e.getMessage(), e);
			}			
		}
		
		return restTemplate;
    } 
}
