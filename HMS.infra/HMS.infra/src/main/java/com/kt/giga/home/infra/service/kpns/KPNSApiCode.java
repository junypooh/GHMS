package com.kt.giga.home.infra.service.kpns;

import com.kt.giga.home.infra.service.ApiCode;

public enum KPNSApiCode implements ApiCode {
	req_message_send (
		"req_message_send",
		"post",
		"/req_message_sending"
	);
	
	private String name;
	private String method;
	private String url;

	private KPNSApiCode(String name, String method, String url) {
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
