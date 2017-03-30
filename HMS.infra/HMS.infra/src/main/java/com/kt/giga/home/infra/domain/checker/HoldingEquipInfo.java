/*******************************************************************************
 * Copyright(c) 2015 KT. All rights reserved.
 * This software is the proprietary information of KT.
 *******************************************************************************/
package com.kt.giga.home.infra.domain.checker;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlElementRefs;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 
 * @author junypooh ( kyungjun.park@ceinside.co.kr )
 * @since 2015. 6. 8.
 */
@XmlRootElement(name="HoldingEquipInfo", namespace="http://tempuri.org/")
public class HoldingEquipInfo {

	/** OnOff상태 */
	private String onOff;
	
	/** KT단말Mac주소 */
	private String macAddress;
	
	/** KT단말 IP 주소 */
	private String ipAddress;
	
	/** KT 단말 유형 */
	private String deviceType;
	
	/** 제조사 */
	private String manufacturer;
	
	/** 비고 */
	private String remark;
	
	/** STB상태 */
	private String stbState;
	
	/** 시청채널 */
	private String channelName;
	
	/** 인터넷전화번호 */
	private String telNum;
	
	/** 단말 상태 정보 */
	private List<HostDeviceInfo> hostDeviceInfoList;

	public String getOnOff() {
		return onOff;
	}

	@XmlElement(name="OnOff", namespace="http://tempuri.org/")
	public void setOnOff(String onOff) {
		this.onOff = onOff;
	}

	public String getMacAddress() {
		return macAddress;
	}

	@XmlElement(name="MacAddress", namespace="http://tempuri.org/")
	public void setMacAddress(String macAddress) {
		this.macAddress = macAddress;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	@XmlElement(name="IPAddress", namespace="http://tempuri.org/")
	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public String getDeviceType() {
		return deviceType;
	}

	@XmlElement(name="DeviceType", namespace="http://tempuri.org/")
	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}

	public String getManufacturer() {
		return manufacturer;
	}

	@XmlElement(name="Manufacturer", namespace="http://tempuri.org/")
	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}

	public String getRemark() {
		return remark;
	}

	@XmlElement(name="Remark", namespace="http://tempuri.org/")
	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getStbState() {
		return stbState;
	}

	@XmlElement(name="STBState", namespace="http://tempuri.org/")
	public void setStbState(String stbState) {
		this.stbState = stbState;
	}

	public String getChannelName() {
		return channelName;
	}

	@XmlElement(name="ChannelName", namespace="http://tempuri.org/")
	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}

	public String getTelNum() {
		return telNum;
	}

	@XmlElement(name="TelNum", namespace="http://tempuri.org/")
	public void setTelNum(String telNum) {
		this.telNum = telNum;
	}

	public List<HostDeviceInfo> getHostDeviceInfoList() {
		return hostDeviceInfoList;
	}

	@XmlElementWrapper(name="HostDeviceInfoList", namespace="http://tempuri.org/")
	@XmlElementRefs({
		@XmlElementRef(name="HostDeviceInfo", namespace="http://tempuri.org/", type=HostDeviceInfo.class)
	})
	public void setHostDeviceInfoList(List<HostDeviceInfo> hostDeviceInfoList) {
		this.hostDeviceInfoList = hostDeviceInfoList;
	}

}
