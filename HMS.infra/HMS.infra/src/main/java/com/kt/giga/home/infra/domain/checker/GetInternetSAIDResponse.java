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
 * @since 2015. 8. 4.
 */
@XmlRootElement(name="GetInternetSAIDResponse", namespace="http://tempuri.org/")
public class GetInternetSAIDResponse extends HttpSoapObjectResponse {
	
	private GetInternetSAIDResult getInternetSAIDResult;

	public GetInternetSAIDResult getGetInternetSAIDResult() {
		return getInternetSAIDResult;
	}

	@XmlElement(name="GetInternetSAIDResult", namespace="http://tempuri.org/", type=GetInternetSAIDResult.class)
	public void setGetInternetSAIDResult(GetInternetSAIDResult getInternetSAIDResult) {
		this.getInternetSAIDResult = getInternetSAIDResult;
	}

}
