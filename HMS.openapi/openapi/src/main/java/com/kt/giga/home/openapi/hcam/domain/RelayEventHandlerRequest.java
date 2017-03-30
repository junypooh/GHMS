package com.kt.giga.home.openapi.hcam.domain;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "SetEventURLRequest")
public class RelayEventHandlerRequest {
	private String url;

	@XmlElement
	public void setURL(String url) {
		this.url = url;
	}

	public String getURL() {
		return this.url;
	}
}
