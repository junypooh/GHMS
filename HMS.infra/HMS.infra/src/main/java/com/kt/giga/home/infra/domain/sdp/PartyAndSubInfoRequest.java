package com.kt.giga.home.infra.domain.sdp;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.kt.giga.home.infra.domain.commons.HttpSoapObjectRequest;

@XmlRootElement(name="getPartyAndSubInfoBySubTypeCDRequest", namespace="http://kt.com/sdp")
public class PartyAndSubInfoRequest extends HttpSoapObjectRequest {

	private String userName;
	private String credtId;
	private String subscpnTypeCd;
	
	@XmlElement(name="User_Name", namespace="http://kt.com/sdp")
	public String getUserName() {
		return userName;
	}
	
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	@XmlElement(name="Credt_Id", namespace="http://kt.com/sdp")
	public String getCredtId() {
		return credtId;
	}
	
	public void setCredtId(String credtId) {
		this.credtId = credtId;
	}

	@XmlElement(name="Subscpn_Type_Cd", namespace="http://kt.com/sdp")
	public String getSubscpnTypeCd() {
		return subscpnTypeCd;
	}

	public void setSubscpnTypeCd(String subscpnTypeCd) {
		this.subscpnTypeCd = subscpnTypeCd;
	}
}
