package com.kt.giga.home.openapi.hcam.domain;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "SetEventURLResponse")
public class RelayEventHandlerResponse {

	private String result;
	private int errorCode;
	private String description;

	public String getResult() {
		return result;
	}

	@XmlElement
	public void setResult(String result) {
		this.result = result;
	}
	public int getErrorCode() {
		return errorCode;
	}

	@XmlElement
	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}
	public String getDescription() {
		return description;
	}

	@XmlElement
	public void setDescription(String description) {
		this.description = description;
	}

}
