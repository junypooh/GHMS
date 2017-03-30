package com.kt.giga.home.infra.domain.sdp;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.kt.giga.home.infra.domain.commons.HttpObjectResponse;

public class ErrorDetailInfo {
	private String errorCode;
	private String errorDescription;
	
	@XmlElement(name="errorcode", namespace="http://kt.com/sdp")
	public String getErrorCode() {
		return errorCode;
	}
	
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}
	
	@XmlElement(name="errordescription", namespace="http://kt.com/sdp")
	public String getErrorDescription() {
		return errorDescription;
	}
	
	public void setErrorDescription(String errorDescription) {
		this.errorDescription = errorDescription;
	}	
}
