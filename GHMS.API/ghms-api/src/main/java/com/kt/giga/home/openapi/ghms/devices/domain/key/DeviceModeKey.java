/*******************************************************************************
 * Copyright(c) 2015 KT. All rights reserved.
 * This software is the proprietary information of KT.
 *******************************************************************************/
package com.kt.giga.home.openapi.ghms.devices.domain.key;

/**
 * 홈모드 갱신 Key
 * @author junypooh ( kyungjun.park@ceinside.co.kr )
 * @since 2015. 2. 16.
 */
public class DeviceModeKey {
	
	/** 서비스대상 일련번호 */
	private long serviceNo;
	
	/** 모드 코드 */
	private String modeCode;
	
	/** 사용자 일련번호 */
	private long userNo;
	
	/** 지역 코드 */
	private String dstrCd;
	
	/** 서비스 테마 코드 */
	private String svcThemeCd;
	
	/** 단위 서비스 코드 */
	private String unitSvcCd; 
	
	/** 현장장치 일련번호 */
	private long spotDevSeq;
	
	/** 설정 상태 코드 */
	private String setupSttusCd;

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
	public long getUserNo() {
		return userNo;
	}

	/**
	 * @param userNo TODO
	 */
	public void setUserNo(long userNo) {
		this.userNo = userNo;
	}

	/**
	 * @return TODO
	 */
	public String getDstrCd() {
		return dstrCd;
	}

	/**
	 * @param dstrCd TODO
	 */
	public void setDstrCd(String dstrCd) {
		this.dstrCd = dstrCd;
	}

	/**
	 * @return TODO
	 */
	public String getSvcThemeCd() {
		return svcThemeCd;
	}

	/**
	 * @param svcThemeCd TODO
	 */
	public void setSvcThemeCd(String svcThemeCd) {
		this.svcThemeCd = svcThemeCd;
	}

	/**
	 * @return TODO
	 */
	public String getUnitSvcCd() {
		return unitSvcCd;
	}

	/**
	 * @param unitSvcCd TODO
	 */
	public void setUnitSvcCd(String unitSvcCd) {
		this.unitSvcCd = unitSvcCd;
	}

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

}
