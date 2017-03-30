package com.kt.giga.home.infra.domain.idms;

import com.kt.giga.home.infra.domain.commons.HttpRestObjectResponse;

public class SubscriptionInfoResponse extends HttpRestObjectResponse {
	public String resultCode;

	public String getResultCode() {
		return resultCode;
	}

	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}
}
