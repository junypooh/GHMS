/*******************************************************************************
 * Copyright(c) 2015 KT. All rights reserved.
 * This software is the proprietary information of KT.
 *******************************************************************************/
package com.kt.giga.home.openapi.ghms.user.domain.key;

/**
 * 서비스 위임 관계 Key
 * @author junypooh ( kyungjun.park@ceinside.co.kr )
 * @since 2015. 2. 4.
 */
public class SvcEtsRelKey {
	
	/**
	 * 회원 일련 번호
	 */
	private long mbrSeq;
	
	/**
	 * 위임 회원 일련 번호
	 */
	private long etsMbrSeq;
	
	/**
	 * 지역 코드
	 */
	private String dstrCd;
	
	/**
	 * 서비스 테마 코드
	 */
	private String svcThemeCd;
	
	/**
	 * 단위 서비스 코드
	 */
	private String unitSvcCd; 
	
	/**
	 * 서비스대상일련번호
	 */
	private long svcTgtSeq;
	
	/**
	 * 현장장치일련번호
	 */
	private long spotDevSeq;

	/**
	 * 위임 회원 DeviceId
	 */
	private String etsMbrDeviceId;
	
	/** 
	 * 그룹 설정 코드 
	 */
	private String groupSetUpCd;
	
	/** 
	 * 설정 코드 
	 */
	private String setUpCd;
	
	/** 
	 * 설정 값 
	 */
	private String setUpValue;
	
	/** 
	 * 서비스대상유형코드
	 */
	private String svcTgtTypeCd;

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
	public long getEtsMbrSeq() {
		return etsMbrSeq;
	}

	/**
	 * @param etsMbrSeq TODO
	 */
	public void setEtsMbrSeq(long etsMbrSeq) {
		this.etsMbrSeq = etsMbrSeq;
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
	public String getEtsMbrDeviceId() {
		return etsMbrDeviceId;
	}

	/**
	 * @param etsMbrDeviceId TODO
	 */
	public void setEtsMbrDeviceId(String etsMbrDeviceId) {
		this.etsMbrDeviceId = etsMbrDeviceId;
	}

	/**
	 * @return TODO
	 */
	public String getGroupSetUpCd() {
		return groupSetUpCd;
	}

	/**
	 * @param groupSetUpCd TODO
	 */
	public void setGroupSetUpCd(String groupSetUpCd) {
		this.groupSetUpCd = groupSetUpCd;
	}

	/**
	 * @return TODO
	 */
	public String getSetUpCd() {
		return setUpCd;
	}

	/**
	 * @param setUpCd TODO
	 */
	public void setSetUpCd(String setUpCd) {
		this.setUpCd = setUpCd;
	}

	/**
	 * @return TODO
	 */
	public String getSetUpValue() {
		return setUpValue;
	}

	/**
	 * @param setUpValue TODO
	 */
	public void setSetUpValue(String setUpValue) {
		this.setUpValue = setUpValue;
	}

	/**
	 * @return TODO
	 */
	public String getSvcTgtTypeCd() {
		return svcTgtTypeCd;
	}

	/**
	 * @param svcTgtTypeCd TODO
	 */
	public void setSvcTgtTypeCd(String svcTgtTypeCd) {
		this.svcTgtTypeCd = svcTgtTypeCd;
	}

}
