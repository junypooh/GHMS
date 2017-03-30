/*******************************************************************************
 * Copyright(c) 2015 KT. All rights reserved.
 * This software is the proprietary information of KT.
 *******************************************************************************/
package com.kt.giga.home.openapi.ghms.devices.domain.vo;


/**
 * 
 * @author junypooh ( kyungjun.park@ceinside.co.kr )
 * @since 2015. 4. 2.
 */
public class SpotDeviceSnsnData {

	/** 센싱태그코드 */
	private String snsnTagCd;
	/** 설정값 */
	private String setupVal;
	/** 업데이트 시간 */
	private String updDtm;
	/**
	 * @return TODO
	 */
	public String getSnsnTagCd() {
		return snsnTagCd;
	}
	/**
	 * @param snsnTagCd TODO
	 */
	public void setSnsnTagCd(String snsnTagCd) {
		this.snsnTagCd = snsnTagCd;
	}
	/**
	 * @return TODO
	 */
	public String getSetupVal() {
		return setupVal;
	}
	/**
	 * @param setupVal TODO
	 */
	public void setSetupVal(String setupVal) {
		this.setupVal = setupVal;
	}
	/**
	 * @return TODO
	 */
	public String getUpdDtm() {
		return updDtm;
	}
	/**
	 * @param updDtm TODO
	 */
	public void setUpdDtm(String updDtm) {
		this.updDtm = updDtm;
	}
	

}
