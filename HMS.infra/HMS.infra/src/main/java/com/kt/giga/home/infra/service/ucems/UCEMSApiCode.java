package com.kt.giga.home.infra.service.ucems;

import com.kt.giga.home.infra.service.ApiCode;

public enum UCEMSApiCode implements ApiCode {
	GetNewFirm (
		"GetNewFirm",
		"post",
		"/CemsLinkage/HomeCam/HomeCam.asmx?wsdl"
	), ApplyNewFirm (
		"ApplyNewFirm",
		"post",
		"/CemsLinkage/HomeCam/HomeCam.asmx?wsdl"
	), StatusNewFirm (
		"StatusNewFirm",
		"post",
		"/CemsLinkage/HomeCam/HomeCam.asmx?wsdl"
	);
	
	private String name;
	private String method;
	private String url;

	private UCEMSApiCode(String name, String method, String url) {
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
