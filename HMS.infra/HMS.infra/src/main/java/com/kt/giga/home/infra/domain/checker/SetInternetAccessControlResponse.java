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
@XmlRootElement(name="SetInternetAccessControlResponse", namespace="http://tempuri.org/")
public class SetInternetAccessControlResponse extends HttpSoapObjectResponse {
	
	private SetInternetAccessControlResult setInternetAccessControlResult;

	public SetInternetAccessControlResult getSetInternetAccessControlResult() {
		return setInternetAccessControlResult;
	}

	@XmlElement(name="SetInternetAccessControlResult", namespace="http://tempuri.org/", type=SetInternetAccessControlResult.class)
	public void setSetInternetAccessControlResult(
			SetInternetAccessControlResult setInternetAccessControlResult) {
		this.setInternetAccessControlResult = setInternetAccessControlResult;
	}

}
