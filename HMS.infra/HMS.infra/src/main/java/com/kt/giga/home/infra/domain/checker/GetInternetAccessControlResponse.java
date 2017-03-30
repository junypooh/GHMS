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
 * @since 2015. 6. 10.
 */
@XmlRootElement(name="GetInternetAccessControlResponse", namespace="http://tempuri.org/")
public class GetInternetAccessControlResponse extends HttpSoapObjectResponse {
	
	private GetInternetAccessControlResult getInternetAccessControlResult;

	public GetInternetAccessControlResult getGetInternetAccessControlResult() {
		return getInternetAccessControlResult;
	}

	@XmlElement(name="GetInternetAccessControlResult", namespace="http://tempuri.org/", type=GetInternetAccessControlResult.class)
	public void setGetInternetAccessControlResult(
			GetInternetAccessControlResult getInternetAccessControlResult) {
		this.getInternetAccessControlResult = getInternetAccessControlResult;
	}

}
