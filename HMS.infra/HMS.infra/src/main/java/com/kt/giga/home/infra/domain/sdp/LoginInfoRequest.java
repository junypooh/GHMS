package com.kt.giga.home.infra.domain.sdp;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.kt.giga.home.infra.domain.commons.HttpObjectRequest;
import com.kt.giga.home.infra.domain.commons.HttpSoapObjectRequest;

@XmlRootElement(name="authenticateUserByIdPwdForOlleh", namespace="http://kt.com/sdp")
public class LoginInfoRequest extends HttpSoapObjectRequest {

	private String userId;
	private String userIdType;
	private String pw;
	private String userDeviceId;
	private String userDeviceIdType;
	private String domainNameCd;

	@XmlElement(name="user_name", namespace="http://kt.com/sdp")
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserIdType() {
		return userIdType;
	}

	public void setUserIdType(String userIdType) {
		this.userIdType = userIdType;
	}

	@XmlElement(name="password", namespace="http://kt.com/sdp")
	public String getPw() {
		return pw;
	}

	public void setPw(String pw) {
		this.pw = pw;
	}

	public String getUserDeviceId() {
		return userDeviceId;
	}

	public void setUserDeviceId(String userDeviceId) {
		this.userDeviceId = userDeviceId;
	}

	public String getUserDeviceIdType() {
		return userDeviceIdType;
	}

	public void setUserDeviceIdType(String userDeviceIdType) {
		this.userDeviceIdType = userDeviceIdType;
	}

	@XmlElement(name="domain_name_cd", namespace="http://kt.com/sdp")
	public String getDomainNameCd() {
		return domainNameCd;
	}

	public void setDomainNameCd(String domainNameCd) {
		this.domainNameCd = domainNameCd;
	}
}
