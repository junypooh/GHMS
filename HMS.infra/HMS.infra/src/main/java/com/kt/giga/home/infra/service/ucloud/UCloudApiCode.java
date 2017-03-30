package com.kt.giga.home.infra.service.ucloud;

import com.kt.giga.home.infra.service.ApiCode;

public enum UCloudApiCode implements ApiCode {
	create_access_token (
		"create_access_token",
		"post",
		"/ucloud/api/1.0/internal/create_access_token"
	), delete_access_token (
		"delete_access_token",
		"post",
		"/ucloud/api/1.0/internal/delete_access_token"
	), create_user (
		"create_user",
		"post",
		"/ucloud/api/1.0/internal/create_user"
	), delete_user (
		"delete_user",
		"post",
		"/ucloud/api/1.0/internal/delete_user"
	), modify_user (
		"modify_user",
		"post",
		"/ucloud/api/1.0/internal/modify_user"
	);
	
	private String name;
	private String method;
	private String url;

	private UCloudApiCode(String name, String method, String url) {
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
