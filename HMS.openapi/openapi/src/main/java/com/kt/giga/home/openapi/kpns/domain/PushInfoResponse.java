package com.kt.giga.home.openapi.kpns.domain;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PushInfoResponse {
	private int statusCode;
	private String statusText;
	private String multicastId;
	private String success;
	private String failure;	
	private List<Map<String, String>> results;

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
