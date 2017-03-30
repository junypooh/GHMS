/*******************************************************************************
 * Copyright(c) 2015 KT. All rights reserved.
 * This software is the proprietary information of KT.
 *******************************************************************************/
package com.kt.giga.home.openapi.ghms.user.domain.key;

import java.util.List;

/**
 * 
 * @author junypooh ( kyungjun.park@ceinside.co.kr )
 * @since 2015. 2. 3.
 */
public class DeviceAuthKey {
	
	/** 사용자 일련 번호 */
	private long mbrSeq;

	/** 현장장치 리스트 */
	private List<DeviceKey> deviceList;

	/**
	 * @return TODO
	 */
	public long getMbrSeq() {
		return mbrSeq;
	}

	/**
	 * @param mbrSeq TODO
	 */
	public void setMbrSeq(long mbrSeq) {
		this.mbrSeq = mbrSeq;
	}

	/**
	 * @return TODO
	 */
	public List<DeviceKey> getDeviceList() {
		return deviceList;
	}

	/**
	 * @param deviceList TODO
	 */
	public void setDeviceList(List<DeviceKey> deviceList) {
		this.deviceList = deviceList;
	}

}
