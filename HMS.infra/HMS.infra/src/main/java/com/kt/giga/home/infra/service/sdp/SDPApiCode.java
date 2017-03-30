package com.kt.giga.home.infra.service.sdp;

import com.kt.giga.home.infra.service.ApiCode;
import com.kt.giga.home.infra.service.sdp.SDPApiCode;

public enum SDPApiCode implements ApiCode {
	authenticateUserByIdPwdForOlleh (
		"authenticateUserByIdPwdForOlleh",
		"post",
		"/authentication/AuthenticationManagerSSL?wsdl"
	), getPartyAndSubInfoBySubTypeCD (
		"getPartyAndSubInfoBySubTypeCD",
		"post",
		"/SubscriptionInfoRetrieval/SubscriptionInfoRetrievalManagerSSL?wsdl"
	), getSpecificSubscpnInfo (
		"getSpecificSubscpnInfo",
		"post",
		"/SubscriptionInfoRetrieval/SubscriptionInfoRetrievalManager?wsdl"
	), getSpecificSubsAndUserInfo (
		"getSpecificSubsAndUserInfo",
		"post",
		"/SubscriptionInfoRetrieval/SubscriptionInfoRetrievalManagerSSL?wsdl"
	);
	
	private String name;
	private String method;
	private String url;

	private SDPApiCode(String name, String method, String url) {
		this.name = name;
		this.method = method;
		this.url = url;		
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
}
