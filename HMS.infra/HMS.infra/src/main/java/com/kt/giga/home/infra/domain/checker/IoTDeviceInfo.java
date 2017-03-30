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
public class IoTDeviceInfo {

	@JsonProperty("DeviceID")
	private String deviceId;

	@JsonProperty("Manufacturer")
	private String manufacturer;

	@JsonProperty("DeviceType")
	private String deviceType;

	@JsonProperty("DeviceStatus")
	private String deviceStatus;

	@JsonProperty("LastRecvDate")
	private String lastRecvDate;

	@JsonProperty("DeviceBatteryStatus")
	private String deviceBatteryStatus;

	/**
	 * @return TODO
	 */
	public String getDeviceId() {
		return deviceId;
	}

	/**
	 * @param deviceId TODO
	 */
	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	/**
	 * @return TODO
	 */
	public String getManufacturer() {
		return manufacturer;
	}

	/**
	 * @param manufacturer TODO
	 */
	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}

	/**
	 * @return TODO
	 */
	public String getDeviceType() {
		return deviceType;
	}

	/**
	 * @param deviceType TODO
	 */
	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}

	/**
	 * @return TODO
	 */
	public String getDeviceStatus() {
		return deviceStatus;
	}

	/**
	 * @param deviceStatus TODO
	 */
	public void setDeviceStatus(String deviceStatus) {
		this.deviceStatus = deviceStatus;
	}

	/**
	 * @return TODO
	 */
	public String getLastRecvDate() {
		return lastRecvDate;
	}

	/**
	 * @param lastRecvDate TODO
	 */
	public void setLastRecvDate(String lastRecvDate) {
		this.lastRecvDate = lastRecvDate;
	}

	/**
	 * @return TODO
	 */
	public String getDeviceBatteryStatus() {
		return deviceBatteryStatus;
	}

	/**
	 * @param deviceBatteryStatus TODO
	 */
	public void setDeviceBatteryStatus(String deviceBatteryStatus) {
		this.deviceBatteryStatus = deviceBatteryStatus;
	}

}
