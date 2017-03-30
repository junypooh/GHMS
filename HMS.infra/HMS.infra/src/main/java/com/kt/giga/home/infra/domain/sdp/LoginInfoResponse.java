package com.kt.giga.home.infra.domain.sdp;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.kt.giga.home.infra.domain.commons.HttpSoapObjectResponse;

@XmlRootElement(name="authenticateUserByIdPwdForOllehResponse", namespace="http://kt.com/sdp")
public class LoginInfoResponse extends HttpSoapObjectResponse {

	private String resultCode;	
	private String resultMsg;
	private String loginFailCounter;
	private List<CredentialInfo> credentials;
	private ErrorDetailInfo errorDetailInfo;
	
	public String getResultCode() {
		return resultCode;
	}

	@XmlElement(name="return_code", namespace="http://kt.com/sdp")
	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}

	public String getResultMsg() {
		return resultMsg;
	}

	@XmlElement(name="return_desc", namespace="http://kt.com/sdp")
	public void setResultMsg(String resultMsg) {
		this.resultMsg = resultMsg;
	}
	
	public String getLoginFailCounter() {
		return loginFailCounter;
	}

	@XmlElement(name="login_fail_counter", namespace="http://kt.com/sdp")
	public void setLoginFailCounter(String loginFailCounter) {
		this.loginFailCounter = loginFailCounter;
	}

	public List<CredentialInfo> getCredentials() {
		return credentials;
	}

	@XmlElement(name="credential", namespace="http://kt.com/sdp")
	public void setCredentials(List<CredentialInfo> credentials) {
		this.credentials = credentials;
	}
	
	public ErrorDetailInfo getErrorDetailInfo() {
		return errorDetailInfo;
	}

	@XmlElement(name="ERRORDETAIL", namespace="http://kt.com/sdp")
	public void setErrorDetailInfo(ErrorDetailInfo errorDetailInfo) {
		this.errorDetailInfo = errorDetailInfo;
	}
}
