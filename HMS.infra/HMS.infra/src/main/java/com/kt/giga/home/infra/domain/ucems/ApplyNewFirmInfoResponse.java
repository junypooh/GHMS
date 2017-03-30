package com.kt.giga.home.infra.domain.ucems;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.kt.giga.home.infra.domain.commons.HttpSoapObjectResponse;

@XmlRootElement(name="ApplyNewFirmResponse", namespace="http://kt.cems.com/WebService/CemsLinkage/HomeCam")
public class ApplyNewFirmInfoResponse extends HttpSoapObjectResponse {
	private ResultInfo resultInfo;
	
	public ApplyNewFirmInfoResponse() {
		this.resultInfo = new ResultInfo();
	}

	public ResultInfo getResultInfo() {
		return resultInfo;
	}

	@XmlElement(name="ApplyNewFirmResult")
	public void setResultInfo(ResultInfo resultInfo) {
		this.resultInfo = resultInfo;
	}
}
