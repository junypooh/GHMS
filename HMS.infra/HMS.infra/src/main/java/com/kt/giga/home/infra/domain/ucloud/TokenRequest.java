package com.kt.giga.home.infra.domain.ucloud;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.kt.giga.home.infra.domain.commons.HttpObjectRequest;

public class TokenRequest implements HttpObjectRequest {
	private String apiToken;
	private String loginId;
	private String cid;
	private String serviceType;
	private String saId;
	private String caSaid;
	private String authToken;
	
	@JsonProperty("api_token")
	public String getApiToken() {
		return apiToken;
	}
	
	public void setApiToken(String apiToken) {
		this.apiToken = apiToken;
	}
	
	@JsonProperty("login_id")
	public String getLoginId() {
		return loginId;
	}
	
	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}
	
	@JsonProperty("cid")
	public String getCid() {
		return cid;
	}
	
	public void setCid(String cid) {
		this.cid = cid;
	}
	
	@JsonProperty("service_type")
	public String getServiceType() {
		return serviceType;
	}
	
	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}
	
	@JsonProperty("sa_id")
	public String getSaId() {
		return saId;
	}
	
	public void setSaId(String saId) {
		this.saId = saId;
	}
	
	@JsonProperty("ca_saId")
	public String getCaSaid() {
		return caSaid;
	}
	
	
	public void setCaSaid(String caSaid) {
		this.caSaid = caSaid;
	}

	public String getAuthToken() {
		return authToken;
	}

	public void setAuthToken(String authToken) {
		this.authToken = authToken;
	}
}
