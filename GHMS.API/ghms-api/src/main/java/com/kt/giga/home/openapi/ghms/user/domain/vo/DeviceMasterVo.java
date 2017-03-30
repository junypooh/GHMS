/*******************************************************************************
 * Copyright(c) 2015 KT. All rights reserved.
 * This software is the proprietary information of KT.
 *******************************************************************************/
package com.kt.giga.home.openapi.ghms.user.domain.vo;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * APP <-> OpenAPI 간 사용하는 DeviceMasterVo
 * @author junypooh ( kyungjun.park@ceinside.co.kr )
 * @since 2015. 2. 3.
 */
@JsonInclude(Include.NON_EMPTY)
public class DeviceMasterVo {
	
	/** 서비스대상일련번호 */
	private long svcTgtSeq;
	
	/** 홈모드 코드 */
	private String modeCode;

	/** 현장장치 설정 리스트 */
	private List<DeviceVo> deviceList;
	
	/** 현장장치 사용자 권한 Vo */
	private DeviceAuthVo deviceAuthVo;

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
	public List<DeviceVo> getDeviceList() {
		return deviceList;
	}

	/**
	 * @param deviceList TODO
	 */
	public void setDeviceList(List<DeviceVo> deviceList) {
		this.deviceList = deviceList;
	}

	/**
	 * @return TODO
	 */
	public DeviceAuthVo getDeviceAuthVo() {
		return deviceAuthVo;
	}

	/**
	 * @param deviceAuthVo TODO
	 */
	public void setDeviceAuthVo(DeviceAuthVo deviceAuthVo) {
		this.deviceAuthVo = deviceAuthVo;
	}

}
