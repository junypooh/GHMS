/*******************************************************************************
 * Copyright(c) 2015 KT. All rights reserved.
 * This software is the proprietary information of KT.
 *******************************************************************************/
package com.kt.giga.home.infra.domain.checker;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.kt.giga.home.infra.domain.commons.HttpSoapObjectRequest;

/**
 * AP 인터넷 이용제한 룰 관리 Request
 * @author junypooh ( kyungjun.park@ceinside.co.kr )
 * @since 2015. 6. 8.
 */
@XmlRootElement(name="SetInternetAccessControl", namespace="http://tempuri.org/")
public class SetInternetAccessControl extends HttpSoapObjectRequest {

	/** 사용자ID */
	private String userID;
	
	/** 단말식별자 */
	private String uuID;
	
	/** AP 맥주소 */
	private String apMacAddress;
	
	/** 단말 맥주소 */
	private String hostMacAdderss;
	
	/** CRUD플래그 */
	private String crudFlag;
	
	/** 룰 인덱스 */
	private String index;
	
	/** 인터넷 접속 차단여부 */
	private String enable;
	
	/** 룰 이름 */
	private String description;
	
	/** 룰 적용 요일 */
	private String weekly;
	
	/** 시작 시간 */
	private String startTime;
	
	/** 종료 시간 */
	private String endTime;

	@XmlElement(name="UserID", namespace="http://tempuri.org/")
	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}

	@XmlElement(name="UUID", namespace="http://tempuri.org/")
	public String getUuID() {
		return uuID;
	}

	public void setUuID(String uuID) {
		this.uuID = uuID;
	}

	@XmlElement(name="APMacAddress", namespace="http://tempuri.org/")
	public String getApMacAddress() {
		return apMacAddress;
	}

	public void setApMacAddress(String apMacAddress) {
		this.apMacAddress = apMacAddress;
	}

	@XmlElement(name="HostMacAddress", namespace="http://tempuri.org/")
	public String getHostMacAdderss() {
		return hostMacAdderss;
	}

	public void setHostMacAdderss(String hostMacAdderss) {
		this.hostMacAdderss = hostMacAdderss;
	}

	@XmlElement(name="CRUDFlag", namespace="http://tempuri.org/")
	public String getCrudFlag() {
		return crudFlag;
	}

	public void setCrudFlag(String crudFlag) {
		this.crudFlag = crudFlag;
	}

	@XmlElement(name="Index", namespace="http://tempuri.org/")
	public String getIndex() {
		return index;
	}

	public void setIndex(String index) {
		this.index = index;
	}

	@XmlElement(name="Enable", namespace="http://tempuri.org/")
	public String getEnable() {
		return enable;
	}

	public void setEnable(String enable) {
		this.enable = enable;
	}

	@XmlElement(name="Description", namespace="http://tempuri.org/")
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@XmlElement(name="Weekly", namespace="http://tempuri.org/")
	public String getWeekly() {
		return weekly;
	}

	public void setWeekly(String weekly) {
		this.weekly = weekly;
	}

	@XmlElement(name="StartTime", namespace="http://tempuri.org/")
	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	@XmlElement(name="EndTime", namespace="http://tempuri.org/")
	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

}
