package com.kt.giga.home.infra.domain.sdp;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlElementRefs;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import com.kt.giga.home.infra.domain.commons.HttpSoapObjectResponse;

@XmlRootElement(name="getPartyAndSubInfoBySubTypeCDResponse", namespace="http://kt.com/sdp")
public class PartyAndSubInfoResponse extends HttpSoapObjectResponse {
	
	private String resultCode;
	private String resultMsg;
	private List<PartyInfo> party;
	private ErrorDetailInfo errorDetailInfo; 
	
	public String getResultCode() {
		return resultCode;
	}

	@XmlElement(name="returnCode", namespace="http://kt.com/sdp")
	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}

	public String getResultMsg() {
		return resultMsg;
	}

	@XmlElement(name="returnDesc", namespace="http://kt.com/sdp")
	public void setResultMsg(String resultMsg) {
		this.resultMsg = resultMsg;
	}
	
	public List<PartyInfo> getPartyMap() {
		return party;
	}
	
	@XmlElementWrapper(name="ListofParty", namespace="http://kt.com/sdp")
	@XmlElementRefs({
		@XmlElementRef(name="arrayofparty", namespace="http://kt.com/sdp_myolleh2", type=PartyInfo.class)
	})
	public void setPartyMap(List<PartyInfo> party) {
		this.party = party;
	}

	public ErrorDetailInfo getErrorDetailInfo() {
		return errorDetailInfo;
	}

	@XmlElement(name="ERRORDETAIL", namespace="http://kt.com/sdp")
	public void setErrorDetailInfo(ErrorDetailInfo errorDetailInfo) {
		this.errorDetailInfo = errorDetailInfo;
	}
}
