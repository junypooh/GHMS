/*******************************************************************************
 * Copyright(c) 2015 KT. All rights reserved.
 * This software is the proprietary information of KT.
 *******************************************************************************/
package com.kt.giga.home.openapi.ghms.checker.domain.vo;

import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * 
 * @author junypooh ( kyungjun.park@ceinside.co.kr )
 * @since 2015. 7. 24.
 */
@JsonInclude(Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
public class GetIoTEquipConnInfoResponse {

	private String resultCode;

	private String resultMessage;

	private List<IoTDeviceInfo> ioTDeviceInfos;

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

	public List<IoTDeviceInfo> getIoTDeviceInfos() {
		return ioTDeviceInfos;
	}

	public void setIoTDeviceInfos(List<IoTDeviceInfo> ioTDeviceInfos) {
		this.ioTDeviceInfos = ioTDeviceInfos;
	}

}
