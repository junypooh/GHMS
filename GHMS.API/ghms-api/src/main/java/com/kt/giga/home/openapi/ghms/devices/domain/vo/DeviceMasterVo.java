/*******************************************************************************
 * Copyright(c) 2015 KT. All rights reserved.
 * This software is the proprietary information of KT.
 *******************************************************************************/
package com.kt.giga.home.openapi.ghms.devices.domain.vo;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * GHMS 고도화 용
 * 마스터 VO
 * @author junypooh ( kyungjun.park@ceinside.co.kr )
 * @since 2015. 7. 15.
 */
@JsonInclude(Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
public class DeviceMasterVo {
	
	/** 모드코드 */
	private String modeCode;
	/** 트랜잭션아이디리스트 */
	private List<String> transacIdList;
	/** 트랜잭션아이디(상위) 상태 */
	private String transacStatus;
	/** 장치 조회 여부('Y','N') */
	private String getCmdYn;
	/** 홈체커 조회 여부('Y','N') */
	private String getCheckerYn;
	/** "INCLUSION" / "EXCLUSION" 제어 성공 여부 */
	private boolean isControllRst;
	
	/** 게이트웨이 리스트 */
	private List<DeviceGw> spotDevGwList;
	
	/** AP 리스트 */
	private List<DeviceGw> spotDevAPList;

	public String getModeCode() {
		return modeCode;
	}

	public void setModeCode(String modeCode) {
		this.modeCode = modeCode;
	}

	public List<String> getTransacIdList() {
		return transacIdList;
	}

	public void setTransacIdList(List<String> transacIdList) {
		this.transacIdList = transacIdList;
	}

	public String getTransacStatus() {
		return transacStatus;
	}

	public void setTransacStatus(String transacStatus) {
		this.transacStatus = transacStatus;
	}

	public String getGetCmdYn() {
		return getCmdYn;
	}

	public void setGetCmdYn(String getCmdYn) {
		this.getCmdYn = getCmdYn;
	}

	public String getGetCheckerYn() {
		return getCheckerYn;
	}

	public void setGetCheckerYn(String getCheckerYn) {
		this.getCheckerYn = getCheckerYn;
	}

	public boolean isControllRst() {
		return isControllRst;
	}

	public void setControllRst(boolean isControllRst) {
		this.isControllRst = isControllRst;
	}

	public List<DeviceGw> getSpotDevGwList() {
		return spotDevGwList;
	}

	public void setSpotDevGwList(List<DeviceGw> spotDevGwList) {
		this.spotDevGwList = spotDevGwList;
	}

	public List<DeviceGw> getSpotDevAPList() {
		return spotDevAPList;
	}

	public void setSpotDevAPList(List<DeviceGw> spotDevAPList) {
		this.spotDevAPList = spotDevAPList;
	}

}
