/*******************************************************************************
 * Copyright(c) 2015 KT. All rights reserved.
 * This software is the proprietary information of KT.
 *******************************************************************************/
package com.kt.giga.home.openapi.ghms.devices.domain.key;

/**
 * 
 * @author junypooh ( kyungjun.park@ceinside.co.kr )
 * @since 2015. 3. 17.
 */
public class DeviceSpotKey {

	/** 서비스대상 일련번호 */
	private long serviceNo;

	/** 현장장치 일련번호 */
	private long devUUID;

	/** 현장장치 닉네임 */
	private String devNm;

	/**
	 * @return TODO
	 */
	public long getServiceNo() {
		return serviceNo;
	}

	/**
	 * @param serviceNo TODO
	 */
	public void setServiceNo(long serviceNo) {
		this.serviceNo = serviceNo;
	}

	/**
	 * @return TODO
	 */
	public long getDevUUID() {
		return devUUID;
	}

	/**
	 * @param devUUID TODO
	 */
	public void setDevUUID(long devUUID) {
		this.devUUID = devUUID;
	}

	/**
	 * @return TODO
	 */
	public String getDevNm() {
		return devNm;
	}

	/**
	 * @param devNm TODO
	 */
	public void setDevNm(String devNm) {
		this.devNm = devNm;
	}

}
