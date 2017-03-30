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
@XmlRootElement(name="SetInternetAccessControlRecoveryResponse", namespace="http://tempuri.org/")
public class SetInternetAccessControlRecoveryResponse extends HttpSoapObjectResponse {
	
	private SetInternetAccessControlRecoveryResult setInternetAccessControlRecoveryResult;

	public SetInternetAccessControlRecoveryResult getSetInternetAccessControlRecoveryResult() {
		return setInternetAccessControlRecoveryResult;
	}

	@XmlElement(name="SetInternetAccessControlRecoveryResult", namespace="http://tempuri.org/", type=SetInternetAccessControlRecoveryResult.class)
	public void setSetInternetAccessControlRecoveryResult(
			SetInternetAccessControlRecoveryResult setInternetAccessControlRecoveryResult) {
		this.setInternetAccessControlRecoveryResult = setInternetAccessControlRecoveryResult;
	}

}
