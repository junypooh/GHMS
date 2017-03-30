/*******************************************************************************
 * Copyright(c) 2015 KT. All rights reserved.
 * This software is the proprietary information of KT.
 *******************************************************************************/
package com.kt.giga.home.openapi.ghms.checker.domain.key;

import java.util.List;


/**
 * 
 * @author junypooh ( kyungjun.park@ceinside.co.kr )
 * @since 2015. 7. 23.
 */
public class GetIoTEquipConnInfo {

	private String inType;

	private String inValue;

	private String deviceID;

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
