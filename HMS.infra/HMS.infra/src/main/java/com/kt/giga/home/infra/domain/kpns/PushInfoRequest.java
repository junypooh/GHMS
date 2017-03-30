package com.kt.giga.home.infra.domain.kpns;

import java.util.Map;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.kt.giga.home.infra.domain.commons.HttpObjectRequest;

public class PushInfoRequest implements HttpObjectRequest {
	private String registrationId;	
	private Map data;
	private int retryCount;

	@JsonProperty("registration_id")
	public String getRegistrationId() {
		return registrationId;
	}

	public void setRegistrationId(String registrationId) {
		this.registrationId = registrationId;
	}

	@JsonProperty("data")
	public Map getData() {
		return data;
	}

	public void setData(Map data) {
		this.data = data;
	}

	public int getRetryCount() {
		return retryCount;
	}

	public void setRetryCount(int retryCount) {
		this.retryCount = retryCount;
	}
}
