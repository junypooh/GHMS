package com.kt.giga.home.infra.domain.ucloud;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.kt.giga.home.infra.domain.commons.HttpObjectRequest;

public class AccountRequest implements HttpObjectRequest {
	private String apiToken;
	private String loginId;
	private String cid;
	private String serviceType;
	private String saId;
	private String opType;
	
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

	@JsonProperty("op_type")
	public String getOpType() {
		return opType;
	}

	public void setOpType(String opType) {
		this.opType = opType;
	}
}
