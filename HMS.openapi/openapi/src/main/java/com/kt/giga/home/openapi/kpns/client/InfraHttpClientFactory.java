package com.kt.giga.home.openapi.kpns.client;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.kt.giga.home.openapi.kpns.host.KPNSServerInfo;

@Component
public class InfraHttpClientFactory {
	
	@Autowired
	private KPNSServerInfo kpnsServerInfo;
	
	private final Map<String, RestHttpClient> servers = new HashMap<String, RestHttpClient>();
	
	public RestHttpClient getInfraHttpClient(KPNSServerInfo serverInfo) {
		return servers.get(serverInfo.getName());
	}
	
	@PostConstruct 
	public void init() {
		servers.put(kpnsServerInfo.getName(), new RestHttpClient(kpnsServerInfo));
	}
}

