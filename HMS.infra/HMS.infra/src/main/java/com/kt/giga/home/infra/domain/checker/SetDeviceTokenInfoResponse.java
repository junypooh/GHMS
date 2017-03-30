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
@XmlRootElement(name="SetDeviceTokenInfoResponse", namespace="http://tempuri.org/")
public class SetDeviceTokenInfoResponse extends HttpSoapObjectResponse {
	
	private SetDeviceTokenInfoResult setDeviceTokenInfoResult;

	public SetDeviceTokenInfoResult getSetDeviceTokenInfoResult() {
		return setDeviceTokenInfoResult;
	}

	@XmlElement(name="SetDeviceTokenInfoResult", namespace="http://tempuri.org/", type=SetDeviceTokenInfoResult.class)
	public void setSetDeviceTokenInfoResult(
			SetDeviceTokenInfoResult setDeviceTokenInfoResult) {
		this.setDeviceTokenInfoResult = setDeviceTokenInfoResult;
	}

}
