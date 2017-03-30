package com.kt.giga.home.infra.domain.sdp;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.kt.giga.home.infra.domain.commons.HttpObjectResponse;

@XmlRootElement(name="party_map", namespace="http://kt.com/sdp")
public class SubscriptionInfo {

	private String sourceSystemCd;	
	private String SourceSystemBindId;
	
	public String getSourceSystemCd() {
		return sourceSystemCd;
	}
	
	@XmlElement(name="Source_System_Cd", namespace="http://kt.com/sdp")
	public void setSourceSystemCd(String sourceSystemCd) {
		this.sourceSystemCd = sourceSystemCd;
	}
	
	public String getSourceSystemBindId() {
		return SourceSystemBindId;
	}
	
	@XmlElement(name="Source_System_Bind_Id", namespace="http://kt.com/sdp")
	public void setSourceSystemBindId(String sourceSystemBindId) {
		SourceSystemBindId = sourceSystemBindId;
	}
}
