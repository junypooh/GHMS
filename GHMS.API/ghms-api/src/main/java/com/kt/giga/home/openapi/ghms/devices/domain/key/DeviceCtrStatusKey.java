/*******************************************************************************
 * Copyright(c) 2015 KT. All rights reserved.
 * This software is the proprietary information of KT.
 *******************************************************************************/
package com.kt.giga.home.openapi.ghms.devices.domain.key;

import java.util.List;

/**
 * 디바이스 동작 제어 Key
 * @author junypooh ( kyungjun.park@ceinside.co.kr )
 * @since 2015. 4. 16.
 */
public class DeviceCtrStatusKey {
	
	private String modeCode;
	
	private long serviceNo = 0;
	
	private long gwUUID = 0;
	/** APP 에서 사용하는 필드(API 서버는 무시) */
	private String spotDevSeq;
	
	private String getCmdYn;
	
	private String getCheckerYn;
	
	private List<String> transacIdList;

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
	public long getGwUUID() {
		return gwUUID;
	}

	/**
	 * @param gwUUID TODO
	 */
	public void setGwUUID(long gwUUID) {
		this.gwUUID = gwUUID;
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
	public String getGetCmdYn() {
		return getCmdYn;
	}

	/**
	 * @param getCmdYn TODO
	 */
	public void setGetCmdYn(String getCmdYn) {
		this.getCmdYn = getCmdYn;
	}

	public String getGetCheckerYn() {
		return getCheckerYn;
	}

	public void setGetCheckerYn(String getCheckerYn) {
		this.getCheckerYn = getCheckerYn;
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

}
