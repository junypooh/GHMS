package com.kt.giga.home.infra.domain.ucems;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.kt.giga.home.infra.domain.commons.HttpRestObjectResponse;
import com.kt.giga.home.infra.domain.commons.HttpSoapObjectResponse;

@XmlRootElement(name="GetNewFirmResponse", namespace="http://kt.cems.com/WebService/CemsLinkage/HomeCam")
public class GetNewFirmInfoResponse extends HttpSoapObjectResponse {
	private ResultInfo resultInfo;
	
	public GetNewFirmInfoResponse() {
		this.resultInfo = new ResultInfo();
	}

	public ResultInfo getResultInfo() {
		return resultInfo;
	}

	@XmlElement(name="GetNewFirmResult")
	public void setResultInfo(ResultInfo resultInfo) {
		this.resultInfo = resultInfo;
	}
}
