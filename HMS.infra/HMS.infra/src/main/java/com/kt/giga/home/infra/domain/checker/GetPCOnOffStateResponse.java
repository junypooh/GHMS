/*******************************************************************************
 * Copyright(c) 2015 KT. All rights reserved.
 * This software is the proprietary information of KT.
 *******************************************************************************/
package com.kt.giga.home.infra.domain.checker;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.kt.giga.home.infra.domain.commons.HttpSoapObjectResponse;

/**
 * 
 * @author junypooh ( kyungjun.park@ceinside.co.kr )
 * @since 2015. 6. 23.
 */
@XmlRootElement(name="GetPCOnOffStateResponse", namespace="http://tempuri.org/")
public class GetPCOnOffStateResponse extends HttpSoapObjectResponse {
	
	private GetPCOnOffStateResult getPCOnOffStateResult;

	public GetPCOnOffStateResult getGetPCOnOffStateResult() {
		return getPCOnOffStateResult;
	}

	@XmlElement(name="GetPCOnOffStateResult", namespace="http://tempuri.org/", type=GetPCOnOffStateResult.class)
	public void setGetPCOnOffStateResult(GetPCOnOffStateResult getPCOnOffStateResult) {
		this.getPCOnOffStateResult = getPCOnOffStateResult;
	}

}
