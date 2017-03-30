/*******************************************************************************
 * Copyright(c) 2015 KT. All rights reserved.
 * This software is the proprietary information of KT.
 *******************************************************************************/
package com.kt.giga.home.openapi.ghms.ktInfra.domain.vo;


/**
 * 
 * @author junypooh ( kyungjun.park@ceinside.co.kr )
 * @since 2015. 6. 8.
 */
public class HostDeviceInfo {

	/** OnOff상태 */
	private String onOff;
	
	/** 단말Mac주소 */
	private String macAddress;
	
	/** 단말 IP 주소 */
	private String ipAddress;
	
	/** 제조사 */
	private String manufacturer;
	
	/** 장치종류 */
	private String deviceType;
	
	/** DHCP옵션 */
	private String dhcpOption60;
	
	/** 접속유형 */
	private String wireless;
	
	/** 호스트명 */
	private String networkName;

	public String getOnOff() {
		return onOff;
	}

	public void setOnOff(String onOff) {
		this.onOff = onOff;
	}

	public String getMacAddress() {
		return macAddress;
	}

	public void setMacAddress(String macAddress) {
		this.macAddress = macAddress;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public String getManufacturer() {
		return manufacturer;
	}

	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}

	public String getDeviceType() {
		return deviceType;
	}

	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}

	public String getDhcpOption60() {
		return dhcpOption60;
	}

	public void setDhcpOption60(String dhcpOption60) {
		this.dhcpOption60 = dhcpOption60;
	}

	public String getWireless() {
		return wireless;
	}

	public void setWireless(String wireless) {
		this.wireless = wireless;
	}

	public String getNetworkName() {
		return networkName;
	}

	public void setNetworkName(String networkName) {
		this.networkName = networkName;
	}

}
