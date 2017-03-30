package com.kt.giga.home.infra.client.http;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.kt.giga.home.infra.client.host.GhmsOpenApiServerInfo;
import com.kt.giga.home.infra.client.host.GhmsSDPServerInfo;
import com.kt.giga.home.infra.client.host.KPNSServerInfo;
import com.kt.giga.home.infra.client.host.OpenApiServerInfo;
import com.kt.giga.home.infra.client.host.SDPServerInfo;
import com.kt.giga.home.infra.client.host.ServerInfo;
import com.kt.giga.home.infra.client.host.UCEMSServerInfo;
import com.kt.giga.home.infra.client.host.UCloudServerInfo;

@Component
public class InfraHttpClientFactory {
	
	@Autowired
	private OpenApiServerInfo openApiServerInfo;
	
	@Autowired
	private GhmsOpenApiServerInfo ghmsOpenApiServerInfo;
	
	@Autowired
	private KPNSServerInfo kpnsServerInfo;
	
	@Autowired
	private SDPServerInfo sdpServerInfo;
	
	@Autowired
	private GhmsSDPServerInfo ghmsSdpServerInfo;
	
	@Autowired
	private UCEMSServerInfo ucemsServerInfo;
	
	@Autowired
	private UCloudServerInfo ucloudServerInfo;
	
	private static final Map<String, InfraHttpClient> servers = new HashMap<String, InfraHttpClient>();
	
	public InfraHttpClient getInfraHttpClient(ServerInfo serverInfo) {
		return servers.get(serverInfo.getName());
	}
	
	@PostConstruct 
	public void init() {
		// 1. proxy 사용하지 않는 서버에 대한 설정
		openApiServerInfo.setProxyHost("");
		ghmsOpenApiServerInfo.setProxyHost("");
		
		// 2. 외부시스템 연결 정보 프리로드
		servers.put(openApiServerInfo.getName(), new RestHttpClient(openApiServerInfo));
		servers.put(ghmsOpenApiServerInfo.getName(), new RestHttpClient(ghmsOpenApiServerInfo));
		servers.put(sdpServerInfo.getName(), new WebserviceHttpClient(sdpServerInfo));
		servers.put(ghmsSdpServerInfo.getName(), new WebserviceHttpClient(ghmsSdpServerInfo));
		servers.put(ucemsServerInfo.getName(), new WebserviceHttpClient(ucemsServerInfo));
		servers.put(ucloudServerInfo.getName(), new RestHttpClient(ucloudServerInfo));
		servers.put(kpnsServerInfo.getName(), new RestHttpClient(kpnsServerInfo));
	}
}

