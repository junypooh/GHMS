package com.kt.giga.home.infra.domain.ucems;

import java.util.Map;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.kt.giga.home.infra.domain.commons.HttpObjectRequest;
import com.kt.giga.home.infra.domain.commons.HttpSoapObjectRequest;

@XmlRootElement(name="GetNewFirm", namespace="http://kt.cems.com/WebService/CemsLinkage/HomeCam")
public class GetNewFirmInfoRequest extends HttpSoapObjectRequest {
	private String secret;	
	private String modelCode;

	@XmlElement(name="Secret", namespace="http://kt.cems.com/WebService/CemsLinkage/HomeCam")
	public String getSecret() {
		return secret;
	}

	public void setSecret(String secret) {
		this.secret = secret;
	}

	@XmlElement(name="ModelCode", namespace="http://kt.cems.com/WebService/CemsLinkage/HomeCam")
	public String getModelCode() {
		return modelCode;
	}

	public void setModelCode(String modelCode) {
		this.modelCode = modelCode;
	}
}
