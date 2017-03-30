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
 * @since 2015. 8. 6.
 */
@XmlRootElement(name="GetWakeOnLanListResponse", namespace="http://tempuri.org/")
public class GetWakeOnLanListResponse extends HttpSoapObjectResponse {
	
	private GetWakeOnLanListResult getWakeOnLanListResult;

	public GetWakeOnLanListResult getGetWakeOnLanListResult() {
		return getWakeOnLanListResult;
	}

	@XmlElement(name="GetWakeOnLanListResult", namespace="http://tempuri.org/", type=GetWakeOnLanListResult.class)
	public void setGetWakeOnLanListResult(
			GetWakeOnLanListResult getWakeOnLanListResult) {
		this.getWakeOnLanListResult = getWakeOnLanListResult;
	}

}
