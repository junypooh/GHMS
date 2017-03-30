package com.kt.giga.home.infra.domain.kpns;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.kt.giga.home.infra.domain.commons.HttpRestObjectResponse;

public class PushInfoResponse extends HttpRestObjectResponse {
	private String multicastId;
	private String success;
	private String failure;	
	private List<Map<String, String>> results;

	public String getMulticastId() {
		return multicastId;
	}

	@JsonProperty("multicast_id")
	public void setMulticastId(String multicastId) {
		this.multicastId = multicastId;
	}

	public String getSuccess() {
		return success;
	}

	public void setSuccess(String success) {
		this.success = success;
	}

	public String getFailure() {
		return failure;
	}

	public void setFailure(String failure) {
		this.failure = failure;
	}

	public List<Map<String, String>> getResults() {
		return results;
	}

	public void setResults(List<Map<String, String>> results) {
		this.results = results;
	}
}
