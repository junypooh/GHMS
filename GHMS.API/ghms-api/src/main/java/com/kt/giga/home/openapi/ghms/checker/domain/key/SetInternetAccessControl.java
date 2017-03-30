/*******************************************************************************
 * Copyright(c) 2015 KT. All rights reserved.
 * This software is the proprietary information of KT.
 *******************************************************************************/
package com.kt.giga.home.openapi.ghms.checker.domain.key;


/**
 * AP 인터넷 이용제한 룰 관리 Request
 * @author junypooh ( kyungjun.park@ceinside.co.kr )
 * @since 2015. 6. 8.
 */
public class SetInternetAccessControl {

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

	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}

	public String getUuID() {
		return uuID;
	}

	public void setUuID(String uuID) {
		this.uuID = uuID;
	}

	public String getApMacAddress() {
		return apMacAddress;
	}

	public void setApMacAddress(String apMacAddress) {
		this.apMacAddress = apMacAddress;
	}

	public String getHostMacAdderss() {
		return hostMacAdderss;
	}

	public void setHostMacAdderss(String hostMacAdderss) {
		this.hostMacAdderss = hostMacAdderss;
	}

	public String getCrudFlag() {
		return crudFlag;
	}

	public void setCrudFlag(String crudFlag) {
		this.crudFlag = crudFlag;
	}

	public String getIndex() {
		return index;
	}

	public void setIndex(String index) {
		this.index = index;
	}

	public String getEnable() {
		return enable;
	}

	public void setEnable(String enable) {
		this.enable = enable;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getWeekly() {
		return weekly;
	}

	public void setWeekly(String weekly) {
		this.weekly = weekly;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

}
