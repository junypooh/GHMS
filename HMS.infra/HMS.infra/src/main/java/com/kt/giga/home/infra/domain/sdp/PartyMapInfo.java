package com.kt.giga.home.infra.domain.sdp;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="listofpartymap", namespace="http://kt.com/sdp_myolleh2")
public class PartyMapInfo {

	private String sourceSystemCd;
	private String sourceSystemBindId;
	
	public String getSourceSystemCd() {
		return sourceSystemCd;
	}

	@XmlElement(name="Source_System_Cd", namespace="http://kt.com/sdp_myolleh2")
	public void setSourceSystemCd(String sourceSystemCd) {
		this.sourceSystemCd = sourceSystemCd;
	}

	public String getSourceSystemBindId() {
		return sourceSystemBindId;
	}

	@XmlElement(name="Source_System_Bind_Id", namespace="http://kt.com/sdp_myolleh2")
	public void setSourceSystemBindId(String sourceSystemBindId) {
		this.sourceSystemBindId = sourceSystemBindId;
	}
}
