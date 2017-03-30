/*******************************************************************************
 * Copyright(c) 2015 KT. All rights reserved.
 * This software is the proprietary information of KT.
 *******************************************************************************/
package com.kt.giga.home.openapi.ghms.user.domain.key;

import java.util.List;

/**
 * APP <-> OpenAPI 간 사용하는 DeviceMasterVo
 * @author junypooh ( kyungjun.park@ceinside.co.kr )
 * @since 2015. 2. 3.
 */
public class DeviceMasterKey {
	
	/** 서비스대상일련번호 */
	private long svcTgtSeq;
	
	/** 홈모드 코드 */
	private String modeCode;

	/** 현장장치 설정 리스트 */
	private List<DeviceKey> deviceList;
	
	/** 현장장치 사용자 권한 Key */
	private DeviceAuthKey deviceAuthVo;

	/**
	 * @return TODO
	 */
	public long getSvcTgtSeq() {
		return svcTgtSeq;
	}

	/**
	 * @param svcTgtSeq TODO
	 */
	public void setSvcTgtSeq(long svcTgtSeq) {
		this.svcTgtSeq = svcTgtSeq;
	}

	/**
	 * @return TODO
	 */
	public String getModeCode() {
		return modeCode;
	}

	/**
	 * @param modeCode TODO
	 */
	public void setModeCode(String modeCode) {
		this.modeCode = modeCode;
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

	/**
	 * @return TODO
	 */
	public DeviceAuthKey getDeviceAuthVo() {
		return deviceAuthVo;
	}

	/**
	 * @param deviceAuthKey TODO
	 */
	public void setDeviceAuthVo(DeviceAuthKey deviceAuthVo) {
		this.deviceAuthVo = deviceAuthVo;
	}

}
