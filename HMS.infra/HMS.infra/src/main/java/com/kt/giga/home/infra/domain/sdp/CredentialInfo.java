package com.kt.giga.home.infra.domain.sdp;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

public class CredentialInfo {
	private String username;	
	private String credentialId;	
	private String credentialTypeCode;	
	private String credentialDetailTypeCode;

	@XmlElement(name="username", namespace="http://kt.com/sdp")
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@XmlElement(name="credential_id", namespace="http://kt.com/sdp")
	public String getCredentialId() {
		return credentialId;
	}

	public void setCredentialId(String credentialId) {
		this.credentialId = credentialId;
	}

	@XmlElement(name="credential_type_code", namespace="http://kt.com/sdp")
	public String getCredentialTypeCode() {
		return credentialTypeCode;
	}

	public void setCredentialTypeCode(String credentialTypeCode) {
		this.credentialTypeCode = credentialTypeCode;
	}

	@XmlElement(name="credential_detail_type_code", namespace="http://kt.com/sdp")
	public String getCredentialDetailTypeCode() {
		return credentialDetailTypeCode;
	}

	public void setCredentialDetailTypeCode(String credentialDetailTypeCode) {
		this.credentialDetailTypeCode = credentialDetailTypeCode;
	}
}
