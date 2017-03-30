package com.kt.giga.home.openapi.kpns.domain;

import java.util.Map;
import com.fasterxml.jackson.annotation.JsonProperty;

public class PushInfoRequest {
	private String registrationId;
	private String messageId;
	private Map data;
	private String reportUrl;

	@JsonProperty("registration_id")
	public String getRegistrationId() {
		return registrationId;
	}

	public void setRegistrationId(String registrationId) {
		this.registrationId = registrationId;
	}

	@JsonProperty("message_id")
	public String getMessageId() {
		return messageId;
	}

	public void setMessageId(String messageId) {
		this.messageId = messageId;
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
