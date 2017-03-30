/*******************************************************************************
 * Copyright(c) 2015 KT. All rights reserved.
 * This software is the proprietary information of KT.
 *******************************************************************************/
package com.kt.giga.home.openapi.ghms.user.domain.key;

/**
 * 알림 설정 Key
 * @author junypooh ( kyungjun.park@ceinside.co.kr )
 * @since 2015. 2. 12.
 */
public class ConfigKey {
	
	/** 앱 단말 식별자 */
	private String deviceId;
	
	/** 사용자 일련번호 */
	private long userNo;
    
    /** 지역 코드 */
    private String dstrCd;
    
    /** 서비스 테마 코드 */
    private String svcThemeCd;
    
    /** 단위 서비스 코드 */
    private String unitSvcCd;
    
    /** 그룹 설정 코드 */
    private String groupSetupCd;
    
    /** 설정 코드 */
    private String setupCd; 
    
    /** 설정 값 */
    private String setupVal;
	
	/** 서비스 대상 일련 번호 */
	private long serviceNo;
    
    /** 회원 구분 (T: 구분없음, M: 마스터 , S: 슬레이브) */
    private String userType;
	
	/** 현장장치 아이디 */
	private long devUUID;

	/**
	 * @return TODO
	 */
	public String getDeviceId() {
		return deviceId;
	}

	/**
	 * @param deviceId TODO
	 */
	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
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
	public String getGroupSetupCd() {
		return groupSetupCd;
	}

	/**
	 * @param groupSetupCd TODO
	 */
	public void setGroupSetupCd(String groupSetupCd) {
		this.groupSetupCd = groupSetupCd;
	}

	/**
	 * @return TODO
	 */
	public String getSetupCd() {
		return setupCd;
	}

	/**
	 * @param setupCd TODO
	 */
	public void setSetupCd(String setupCd) {
		this.setupCd = setupCd;
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
	public String getUserType() {
		return userType;
	}

	/**
	 * @param userType TODO
	 */
	public void setUserType(String userType) {
		this.userType = userType;
	}

	/**
	 * @return TODO
	 */
	public long getDevUUID() {
		return devUUID;
	}

	/**
	 * @param devUUID TODO
	 */
	public void setDevUUID(long devUUID) {
		this.devUUID = devUUID;
	}

}
