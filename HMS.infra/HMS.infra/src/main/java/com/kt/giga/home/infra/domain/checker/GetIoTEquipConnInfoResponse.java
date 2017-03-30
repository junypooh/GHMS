/*******************************************************************************
 * Copyright(c) 2015 KT. All rights reserved.
 * This software is the proprietary information of KT.
 *******************************************************************************/
package com.kt.giga.home.infra.domain.checker;

import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

import com.kt.giga.home.infra.domain.commons.HttpObjectResponse;

/**
 * 
 * @author junypooh ( kyungjun.park@ceinside.co.kr )
 * @since 2015. 7. 24.
 */
public class GetIoTEquipConnInfoResponse implements HttpObjectResponse {

	@JsonProperty("ResultCode")
	private String resultCode;

	@JsonProperty("ResultMessage")
	private String resultMessage;

	@JsonProperty("IoTDeviceInfo")
	private List<IoTDeviceConnInfo> ioTDeviceInfos;

	public String getResultCode() {
		return resultCode;
	}

	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}

	public String getResultMessage() {
		return resultMessage;
	}

	public void setResultMessage(String resultMessage) {
		this.resultMessage = resultMessage;
	}

	public List<IoTDeviceConnInfo> getIoTDeviceInfos() {
		return ioTDeviceInfos;
	}

	public void setIoTDeviceInfos(List<IoTDeviceConnInfo> ioTDeviceInfos) {
		this.ioTDeviceInfos = ioTDeviceInfos;
	}

}
