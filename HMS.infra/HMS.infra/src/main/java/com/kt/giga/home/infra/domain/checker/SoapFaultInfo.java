package com.kt.giga.home.infra.domain.checker;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.kt.giga.home.infra.domain.commons.HttpObjectResponse;

@XmlRootElement(name="WamException", namespace="http://tempuri.org/")
public class SoapFaultInfo implements HttpObjectResponse {
	
	private String faultcode;
	
	private String faultString;
	
	public String getFaultcode() {
		return faultcode;
	}

	@XmlElement(name="faultcode", namespace="http://tempuri.org/")
	public void setFaultcode(String faultcode) {
		this.faultcode = faultcode;
	}

	public String getFaultString() {
		return faultString;
	}

	@XmlElement(name="faultstring", namespace="http://tempuri.org/")
	public void setFaultString(String faultString) {
		this.faultString = faultString;
	}
}
