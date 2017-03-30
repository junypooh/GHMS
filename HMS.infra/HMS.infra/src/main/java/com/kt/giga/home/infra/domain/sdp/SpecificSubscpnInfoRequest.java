/*******************************************************************************
 * Copyright(c) 2015 KT. All rights reserved.
 * This software is the proprietary information of KT.
 *******************************************************************************/
package com.kt.giga.home.infra.domain.sdp;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.kt.giga.home.infra.domain.commons.HttpSoapObjectRequest;

/**
 * OIF_114 Request
 * @author junypooh ( kyungjun.park@ceinside.co.kr )
 * @since 2015. 7. 3.
 */
@XmlRootElement(name="getSpecificSubscpnInfoRequest", namespace="http://kt.com/sdp")
public class SpecificSubscpnInfoRequest extends HttpSoapObjectRequest {
	
	private String credentialId;
	private String userName;
	private String credentialTypeCd;
	private String subscpnSourceTypeCd;
	private String subscpnSourceValue;

	@XmlElement(name="Credt_Id", namespace="http://kt.com/sdp")
	public String getCredentialId() {
		return credentialId;
	}
	public void setCredentialId(String credentialId) {
		this.credentialId = credentialId;
	}
	@XmlElement(name="User_Name", namespace="http://kt.com/sdp")
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	@XmlElement(name="Credt_Type_Cd", namespace="http://kt.com/sdp")
	public String getCredentialTypeCd() {
		return credentialTypeCd;
	}
	public void setCredentialTypeCd(String credentialTypeCd) {
		this.credentialTypeCd = credentialTypeCd;
	}
	@XmlElement(name="Subscpn_Source_Type_Cd", namespace="http://kt.com/sdp")
	public String getSubscpnSourceTypeCd() {
		return subscpnSourceTypeCd;
	}
	public void setSubscpnSourceTypeCd(String subscpnSourceTypeCd) {
		this.subscpnSourceTypeCd = subscpnSourceTypeCd;
	}
	@XmlElement(name="Subscpn_Source_Value", namespace="http://kt.com/sdp")
	public String getSubscpnSourceValue() {
		return subscpnSourceValue;
	}
	public void setSubscpnSourceValue(String subscpnSourceValue) {
		this.subscpnSourceValue = subscpnSourceValue;
	}

}
