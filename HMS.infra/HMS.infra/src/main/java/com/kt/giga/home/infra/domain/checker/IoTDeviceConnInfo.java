/*******************************************************************************
 * Copyright(c) 2015 KT. All rights reserved.
 * This software is the proprietary information of KT.
 *******************************************************************************/
package com.kt.giga.home.infra.domain.checker;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;



/**
 * 
 * @author junypooh ( kyungjun.park@ceinside.co.kr )
 * @since 2015. 6. 30.
 */
@JsonInclude(Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
public class IoTDeviceConnInfo {

	@JsonProperty("DeviceID")
	private String deviceId;

	@JsonProperty("DeviceType")
	private String deviceType;

	@JsonProperty("ConnStatus")
	private String connStatus;

	@JsonProperty("LastRecvDate")
	private String lastRecvDate;

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public String getDeviceType() {
		return deviceType;
	}

	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}

	public String getConnStatus() {
		return connStatus;
	}

	public void setConnStatus(String connStatus) {
		this.connStatus = connStatus;
	}

	public String getLastRecvDate() {
		return lastRecvDate;
	}

	public void setLastRecvDate(String lastRecvDate) {
		this.lastRecvDate = lastRecvDate;
	}

}
