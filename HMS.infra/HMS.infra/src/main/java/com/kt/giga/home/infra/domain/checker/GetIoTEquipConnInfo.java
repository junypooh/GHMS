/*******************************************************************************
 * Copyright(c) 2015 KT. All rights reserved.
 * This software is the proprietary information of KT.
 *******************************************************************************/
package com.kt.giga.home.infra.domain.checker;

import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

import com.kt.giga.home.infra.domain.commons.HttpObjectRequest;

/**
 * 
 * @author junypooh ( kyungjun.park@ceinside.co.kr )
 * @since 2015. 7. 23.
 */
public class GetIoTEquipConnInfo implements HttpObjectRequest {

	@JsonProperty("InType")
	private String inType;

	@JsonProperty("InValue")
	private String inValue;

	@JsonProperty("DeviceID")
	private String deviceID;

	@JsonProperty("TransacIdList")
	private List<String> transacIdList;

	public String getInType() {
		return inType;
	}

	public void setInType(String inType) {
		this.inType = inType;
	}

	public String getInValue() {
		return inValue;
	}

	public void setInValue(String inValue) {
		this.inValue = inValue;
	}

	public String getDeviceID() {
		return deviceID;
	}

	public void setDeviceID(String deviceID) {
		this.deviceID = deviceID;
	}

	public List<String> getTransacIdList() {
		return transacIdList;
	}

	public void setTransacIdList(List<String> transacIdList) {
		this.transacIdList = transacIdList;
	}	
	
}
