package com.kt.giga.home.infra.client.host;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class GhmsOpenApiServerInfo implements ServerInfo {
	@Value("#{system['ghms.openapi.name']}")
	private String name;
	
	@Value("#{system['ghms.openapi.type']}")
	private String type;
	
	@Value("#{system['ghms.openapi.host']}")
	private String host;
	
	@Value("#{system['ghms.openapi.hostlist']}")
	private String hostlist;
	
	@Value("#{system['ghms.openapi.port']}")
	private String port;
	
	@Value("#{system['ghms.openapi.protocol']}")
	private String protocol;
	
	@Value("#{system['ghms.openapi.action']}")
	private String action;
	
	@Value("#{system['ghms.openapi.username']}")
	public String username;
	
	@Value("#{system['ghms.openapi.password']}")
	private String password;
	
	@Value("#{system['proxy.host']}")
	private String proxyHost;
	
	@Value("#{system['proxy.port']}")
	private int proxyPort;

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String getType() {
		return type;
	}

	@Override
	public void setType(String type) {
		this.type = type;
	}

	@Override
	public String getHost() {
		return host;
	}

	@Override
	public void setHost(String host) {
		this.host = host;
	}
	
	@Override
	public String getHostlist() {
		return hostlist;
	}

	@Override
	public void setHostlist(String hostlist) {
		this.hostlist = hostlist;
	}
	
	@Override
	public String getPort() {
		return port;
	}

	@Override
	public void setPort(String port) {
		this.port = port;
	}

	@Override
	public String getProtocol() {
		return protocol;
	}

	@Override
	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	@Override
	public String getUsername() {
		return username;
	}

	@Override
	public void setUsername(String username) {
		this.username = username;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public void setPassword(String password) {
		this.password = password;
	}
	
	@Override
	public String getProxyHost() {
		return proxyHost;
	}

	@Override
	public void setProxyHost(String proxyHost) {
		this.proxyHost = proxyHost;
	}

	@Override
	public int getProxyPort() {
		return proxyPort;
	}

	@Override
	public void setProxyPort(int proxyPort) {
		this.proxyPort = proxyPort;
	}
}
