/*******************************************************************************
 * Copyright(c) 2015 KT. All rights reserved.
 * This software is the proprietary information of KT.
 *******************************************************************************/
package com.kt.giga.home.openapi.ghms.devices.domain.vo;


/**
 * 홈모드 Vo
 * @author junypooh ( kyungjun.park@ceinside.co.kr )
 * @since 2015. 2. 16.
 */
public class DeviceModeVo {
	
	/** 현장장치 일련번호 */
	private long spotDevSeq;
	
	/** 설정 상태 코드 */
	private String setupSttusCd;
	
	/** 현장장치 기본 장치명 */
	private String devNm;
	
	/** 장치모델 기본 장치모델명 */
	private String devModelNm;

	/**
	 * @return TODO
	 */
	public long getSpotDevSeq() {
		return spotDevSeq;
	}

	/**
	 * @param spotDevSeq TODO
	 */
	public void setSpotDevSeq(long spotDevSeq) {
		this.spotDevSeq = spotDevSeq;
	}

	/**
	 * @return TODO
	 */
	public String getSetupSttusCd() {
		return setupSttusCd;
	}

	/**
	 * @param setupSttusCd TODO
	 */
	public void setSetupSttusCd(String setupSttusCd) {
		this.setupSttusCd = setupSttusCd;
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

	/**
	 * @return TODO
	 */
	public String getDevModelNm() {
		return devModelNm;
	}

	/**
	 * @param devModelNm TODO
	 */
	public void setDevModelNm(String devModelNm) {
		this.devModelNm = devModelNm;
	}

}
