/*******************************************************************************
 * Copyright(c) 2015 KT. All rights reserved.
 * This software is the proprietary information of KT.
 *******************************************************************************/
package com.kt.giga.home.openapi.ghms.user.domain.vo;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * 
 * @author junypooh ( kyungjun.park@ceinside.co.kr )
 * @since 2015. 2. 3.
 */
@JsonInclude(Include.NON_EMPTY)
public class DeviceAuthVo {
	
	/** 사용자 일련 번호 */
	private long mbrSeq;

	/** 현장장치 리스트 */
	private List<DeviceVo> deviceList;

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
	public List<DeviceVo> getDeviceList() {
		return deviceList;
	}

	/**
	 * @param deviceList TODO
	 */
	public void setDeviceList(List<DeviceVo> deviceList) {
		this.deviceList = deviceList;
	}

}
