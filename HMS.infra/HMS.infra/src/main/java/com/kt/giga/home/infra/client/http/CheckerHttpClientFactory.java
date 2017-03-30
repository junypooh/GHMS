/*******************************************************************************
 * Copyright(c) 2015 KT. All rights reserved.
 * This software is the proprietary information of KT.
 *******************************************************************************/
package com.kt.giga.home.infra.client.http;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.kt.giga.home.infra.client.host.CheckerServerInfo;
import com.kt.giga.home.infra.client.host.ServerInfo;

/**
 * 
 * @author junypooh ( kyungjun.park@ceinside.co.kr )
 * @since 2015. 6. 10.
 */
@Component
public class CheckerHttpClientFactory {
	
	@Autowired
	private CheckerServerInfo checkerServerInfo;
	
	private static final Map<String, CheckerHttpClient> servers = new HashMap<String, CheckerHttpClient>();
	
	public CheckerHttpClient getCheckerHttpClient(ServerInfo serverInfo) {
		return servers.get(serverInfo.getName());
	}
	
	@PostConstruct 
	public void init() {
		// 1. proxy 사용하지 않는 서버에 대한 설정
		checkerServerInfo.setProxyHost("");
		
		// 2. 외부시스템 연결 정보 프리로드
		servers.put(checkerServerInfo.getName(), new CheckerWebserviceHttpClient(checkerServerInfo));
	}

}
