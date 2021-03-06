package com.kt.giga.home.infra.domain.ucems;

import java.util.Map;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.kt.giga.home.infra.domain.commons.HttpObjectRequest;
import com.kt.giga.home.infra.domain.commons.HttpSoapObjectRequest;

@XmlRootElement(name="ApplyNewFirm", namespace="http://kt.cems.com/WebService/CemsLinkage/HomeCam")
public class ApplyNewFirmInfoRequest extends HttpSoapObjectRequest {
	private String mac;
	private String secret;	
	private String cameraSaid;

	@XmlElement(name="MAC", namespace="http://kt.cems.com/WebService/CemsLinkage/HomeCam")
	public String getMac() {
		return mac;
	}

	public void setMac(String mac) {
		this.mac = mac;
	}

	@XmlElement(name="Secret", namespace="http://kt.cems.com/WebService/CemsLinkage/HomeCam")
	public String getSecret() {
		return secret;
	}

	public void setSecret(String secret) {
		this.secret = secret;
	}

	@XmlElement(name="CameraSAID", namespace="http://kt.cems.com/WebService/CemsLinkage/HomeCam")
	public String getCameraSaid() {
		return cameraSaid;
	}

	public void setCameraSaid(String cameraSaid) {
		this.cameraSaid = cameraSaid;
	}
}
