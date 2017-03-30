package com.kt.giga.home.infra.domain.ucloud;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.kt.giga.home.infra.domain.commons.HttpRestObjectResponse;

public class AccountResponse extends HttpRestObjectResponse {
	private String resultCode;
	private String resultMsg;

	public String getResultCode() {
		return resultCode;
	}

	@JsonProperty("result_code")
	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}

	public String getResultMsg() {
		return resultMsg;
	}

	@JsonProperty("result_msg")
	public void setResultMsg(String resultMsg) {
		this.resultMsg = resultMsg;
	}
}
