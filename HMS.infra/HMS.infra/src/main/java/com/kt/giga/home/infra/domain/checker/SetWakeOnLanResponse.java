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
@XmlRootElement(name="SetWakeOnLanResponse", namespace="http://tempuri.org/")
public class SetWakeOnLanResponse extends HttpSoapObjectResponse {
	
	private SetWakeOnLanResult setWakeOnLanResult;

	public SetWakeOnLanResult getSetWakeOnLanResult() {
		return setWakeOnLanResult;
	}

	@XmlElement(name="SetWakeOnLanResult", namespace="http://tempuri.org/", type=SetWakeOnLanResult.class)
	public void setSetWakeOnLanResult(SetWakeOnLanResult setWakeOnLanResult) {
		this.setWakeOnLanResult = setWakeOnLanResult;
	}

}
