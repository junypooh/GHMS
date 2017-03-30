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
@XmlRootElement(name="GetServiceEquipInfoResponse", namespace="http://tempuri.org/")
public class GetServiceEquipInfoResponse extends HttpSoapObjectResponse {

	private GetServiceEquipInfoResult getServiceEquipInfoResult;

	public GetServiceEquipInfoResult getGetServiceEquipInfoResult() {
		return getServiceEquipInfoResult;
	}

	@XmlElement(name="GetServiceEquipInfoResult", namespace="http://tempuri.org/", type=GetServiceEquipInfoResult.class)
	public void setGetServiceEquipInfoResult(
			GetServiceEquipInfoResult getServiceEquipInfoResult) {
		this.getServiceEquipInfoResult = getServiceEquipInfoResult;
	}
}
