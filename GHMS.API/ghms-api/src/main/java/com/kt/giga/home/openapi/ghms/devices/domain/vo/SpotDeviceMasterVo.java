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
 * GHMS 1차용
 * 마스터 VO
 * @author junypooh ( kyungjun.park@ceinside.co.kr )
 * @since 2015. 3. 11.
 */
@JsonInclude(Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
public class SpotDeviceMasterVo {
	
	/** 모드코드 */
	private String modeCode;
	/** 트랜잭션아이디리스트 */
	private List<String> transacIdList;
	/** 트랜잭션아이디(상위) 상태 */
	private String transacStatus;
	/** 장치 조회 여부('Y','N') */
	private String getCmdYn;
	/** "INCLUSION" / "EXCLUSION" 제어 성공 여부 */
	private boolean isControllRst;
	/** APP 에서 사용하는 필드(API 서버는 무시) */
	private String spotDevSeq;
	
	/** 게이트웨이 리스트 */
	private List<SpotDeviceGw> spotDevGwList;

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
	public List<String> getTransacIdList() {
		return transacIdList;
	}

	/**
	 * @param transacIdList TODO
	 */
	public void setTransacIdList(List<String> transacIdList) {
		this.transacIdList = transacIdList;
	}

	/**
	 * @return TODO
	 */
	public String getTransacStatus() {
		return transacStatus;
	}

	/**
	 * @param transacStatus TODO
	 */
	public void setTransacStatus(String transacStatus) {
		this.transacStatus = transacStatus;
	}

	/**
	 * @return TODO
	 */
	public String getGetCmdYn() {
		return getCmdYn;
	}

	/**
	 * @param getCmdYn TODO
	 */
	public void setGetCmdYn(String getCmdYn) {
		this.getCmdYn = getCmdYn;
	}

	/**
	 * @return TODO
	 */
	public boolean isControllRst() {
		return isControllRst;
	}

	/**
	 * @param isControllRst TODO
	 */
	public void setControllRst(boolean isControllRst) {
		this.isControllRst = isControllRst;
	}

	/**
	 * @return TODO
	 */
	public String getSpotDevSeq() {
		return spotDevSeq;
	}

	/**
	 * @param spotDevSeq TODO
	 */
	public void setSpotDevSeq(String spotDevSeq) {
		this.spotDevSeq = spotDevSeq;
	}

	/**
	 * @return TODO
	 */
	public List<SpotDeviceGw> getSpotDevGwList() {
		return spotDevGwList;
	}

	/**
	 * @param spotDevGwList TODO
	 */
	public void setSpotDevGwList(List<SpotDeviceGw> spotDevGwList) {
		this.spotDevGwList = spotDevGwList;
	}
}
