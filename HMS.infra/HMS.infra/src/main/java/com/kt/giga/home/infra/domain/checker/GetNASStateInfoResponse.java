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
@XmlRootElement(name="GetNASStateInfoResponse", namespace="http://tempuri.org/")
public class GetNASStateInfoResponse extends HttpSoapObjectResponse {
	
	private GetNASStateInfoResult getNASStateInfoResult;

	public GetNASStateInfoResult getGetNASStateInfoResult() {
		return getNASStateInfoResult;
	}

	@XmlElement(name="GetNASStateInfoResult", namespace="http://tempuri.org/", type=GetNASStateInfoResult.class)
	public void setGetNASStateInfoResult(GetNASStateInfoResult getNASStateInfoResult) {
		this.getNASStateInfoResult = getNASStateInfoResult;
	}

}
