package com.kt.giga.home.infra.domain.sdp;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.kt.giga.home.infra.domain.commons.HttpObjectResponse;

@XmlRootElement(name="WamException", namespace="http://kt.com/sdp")
public class FaultInfo implements HttpObjectResponse {
	private String errorCode;
	private String errorDescription;
	
	@XmlElement(name="errorCode", namespace="http://kt.com/sdp")
	public String getErrorCode() {
		return errorCode;
	}
	
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}
	
	@XmlElement(name="errorDescription", namespace="http://kt.com/sdp")
	public String getErrorDescription() {
		return errorDescription;
	}
	
	public void setErrorDescription(String errorDescription) {
		this.errorDescription = errorDescription;
	}	
}
