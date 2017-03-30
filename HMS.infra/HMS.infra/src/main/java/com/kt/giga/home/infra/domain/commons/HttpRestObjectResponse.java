package com.kt.giga.home.infra.domain.commons;

import com.kt.giga.home.infra.domain.commons.HttpObjectResponse;

public class HttpRestObjectResponse implements HttpObjectResponse {
	
	private int statusCode;	
	private String statusText;
	
	public int getStatusCode() {
		return statusCode;
	}
	
	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}
	
	public String getStatusText() {
		return statusText;
	}
	
	public void setStatusText(String statusText) {
		this.statusText = statusText;
	}
}
