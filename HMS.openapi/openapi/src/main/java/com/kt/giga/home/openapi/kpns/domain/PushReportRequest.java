package com.kt.giga.home.openapi.kpns.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PushReportRequest {
	private String error;
	private String sentTime;
	private String messageId;
	private String sentResult;

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	@JsonProperty("sent_time")
	public String getSentTime() {
		return sentTime;
	}

	public void setSentTime(String sentTime) {
		this.sentTime = sentTime;
	}

	@JsonProperty("message_id")
	public String getMessageId() {
		return messageId;
	}

	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}

	@JsonProperty("sent_result")
	public String getSentResult() {
		return sentResult;
	}

	public void setSentResult(String sentResult) {
		this.sentResult = sentResult;
	}	
}
