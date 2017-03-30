package com.kt.giga.home.infra.service.silk;

import com.kt.giga.home.infra.service.ApiCode;
import com.kt.giga.home.infra.service.sdp.SDPApiCode;

public enum OllehRtrmmbCode implements ApiCode {
	deleteUCloudAccount (
		"deleteUCloudAccount",
		"delete",
		"/hcam/ucloud/account"
	);
	
	private String name;
	private String method;
	private String url; 

	private OllehRtrmmbCode(String name, String method, String url) {
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
