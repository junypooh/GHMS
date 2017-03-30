package com.kt.giga.home.infra.client.host;

public interface ServerInfo {
	public String getName();
	public void setName(String name);
	
	public String getType();
	public void setType(String type);
	
	public String getHost();
	public void setHost(String host);
	
	public String getHostlist();
	public void setHostlist(String hostlist);
	
	public String getPort();
	public void setPort(String port);
	
	public String getProtocol();
	public void setProtocol(String protocol);
	
	public String getAction();
	public void setAction(String action);
	
	public String getUsername();
	public void setUsername(String username);
	
	public String getPassword();
	public void setPassword(String password);
	
	public String getProxyHost();
	public void setProxyHost(String proxyHost);
	
	public int getProxyPort();
	public void setProxyPort(int port);
}
