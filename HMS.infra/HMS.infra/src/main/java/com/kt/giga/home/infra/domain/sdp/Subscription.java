package com.kt.giga.home.infra.domain.sdp;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement(name="listofsubscription", namespace="http://kt.com/sdp_myolleh2")
public class Subscription {
	
	private String icisSaId;

	public String getIcisSaId() {
		return icisSaId;
	}

	@XmlElement(name="Icis_Sa_Id", namespace="http://kt.com/sdp_myolleh2")
	public void setIcisSaId(String icisSaId) {
		this.icisSaId = icisSaId;
	}

}
