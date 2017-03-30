package com.kt.giga.home.infra.domain.ucems;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.kt.giga.home.infra.domain.commons.HttpRestObjectResponse;
import com.kt.giga.home.infra.domain.commons.HttpSoapObjectResponse;

@XmlRootElement(name="StatusNewFirmResponse", namespace="http://kt.cems.com/WebService/CemsLinkage/HomeCam")
public class StatusNewFirmInfoResponse extends HttpSoapObjectResponse {
	private ResultInfo resultInfo;
	
	public StatusNewFirmInfoResponse() {
		this.resultInfo = new ResultInfo();
	}

	public ResultInfo getResultInfo() {
		return resultInfo;
	}

	@XmlElement(name="StatusNewFirmResult")
	public void setResultInfo(ResultInfo resultInfo) {
		this.resultInfo = resultInfo;
	}
}
