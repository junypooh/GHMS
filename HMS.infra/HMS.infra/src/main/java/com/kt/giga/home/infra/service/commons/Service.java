package com.kt.giga.home.infra.service.commons;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.kt.giga.home.infra.client.host.ServerInfo;

public class Service {
	private Logger log = Logger.getLogger(this.getClass());
	
	@Autowired
	private ServerInfo serverInfo;
	
	public ServerInfo getServerInfo() {
		return serverInfo;
	}

	public void setServerInfo(ServerInfo serverInfo) {
		this.serverInfo = serverInfo;
	}

	public String getHost() {
		String host = serverInfo.getProtocol() + "://" 
				+ serverInfo.getHost() + ":" 
				+ serverInfo.getPort(); 
		
		return host;
	}
}
