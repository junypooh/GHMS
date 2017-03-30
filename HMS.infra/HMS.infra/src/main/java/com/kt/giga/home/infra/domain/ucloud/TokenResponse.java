package com.kt.giga.home.infra.domain.ucloud;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.kt.giga.home.infra.domain.commons.HttpRestObjectResponse;

public class TokenResponse extends HttpRestObjectResponse {
	private String resultCode;
	private String resultMsg;
	private String apiKey;
	private String apiSecret;
	private String authToken;	
	private String authTokenSecret;

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

	public String getApiKey() {
		return apiKey;
	}

	public void setApiKey(String apiKey) {
		this.apiKey = apiKey;
	}

	public String getApiSecret() {
		return apiSecret;
	}

	public void setApiSecret(String apiSecret) {
		this.apiSecret = apiSecret;
	}

	public String getAuthToken() {
		return authToken;
	}

	@JsonProperty("oauth_token")
	public void setAuthToken(String authToken) {
		this.authToken = authToken;
	}

	public String getAuthTokenSecret() {
		return authTokenSecret;
	}

	@JsonProperty("oauth_token_secret")
	public void setAuthTokenSecret(String authTokenSecret) {
		this.authTokenSecret = authTokenSecret;
	}
}
