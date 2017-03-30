package com.kt.giga.home.openapi.ghms.ktInfra.domain.key;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PushInfoRequest {
	private String registrationId;	
	private Map data;
	private String reportUrl;

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

	@JsonProperty("report_url")
	public String getReportUrl() {
		return reportUrl;
	}

	public void setReportUrl(String reportUrl) {
		this.reportUrl = reportUrl;
	}
}
