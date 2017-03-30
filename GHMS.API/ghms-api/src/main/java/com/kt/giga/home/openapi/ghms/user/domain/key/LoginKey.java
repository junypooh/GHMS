/*******************************************************************************
 * Copyright(c) 2015 KT. All rights reserved.
 * This software is the proprietary information of KT.
 *******************************************************************************/
package com.kt.giga.home.openapi.ghms.user.domain.key;

/**
 * 로그인 Key
 * @author junypooh ( kyungjun.park@ceinside.co.kr )
 * @since 2015. 4. 3.
 */
public class LoginKey {
	
	/** 올레 아이디 */
	private String userId;

	/** 비밀번호 */
	private String passwd;
	
	/** 강제 로그인 */
	private String forceYn;
	
	/** 서비스일련번호 */
	private String serviceNo;
	
	/** 생년월일 (예:19910506) */
	private String birthDay;
	
	/** 양력/음력 구분 (01 : 양력, 02: 음력) */
	private String lunarSolarCd;
	
	/** 성별정보 (01:여자, 02:남자) */
	private String gender;
	
	/** 휴대폰 WIFI MAC Address */
	private String wifiMacAddr;
	
	/**
	 * @return TODO
	 */
	public String getUserId() {
		return userId;
	}

	/**
	 * @param userId TODO
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}

	/**
	 * @return TODO
	 */
	public String getPasswd() {
		return passwd;
	}

	/**
	 * @param passwd TODO
	 */
	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}

	/**
	 * @return TODO
	 */
	public String getForceYn() {
		return forceYn;
	}

	/**
	 * @param forceYn TODO
	 */
	public void setForceYn(String forceYn) {
		this.forceYn = forceYn;
	}

	/**
	 * @return TODO
	 */
	public String getServiceNo() {
		return serviceNo;
	}

	/**
	 * @param serviceNo TODO
	 */
	public void setServiceNo(String serviceNo) {
		this.serviceNo = serviceNo;
	}

	/**
	 * @return TODO
	 */
	public String getBirthDay() {
		return birthDay;
	}

	/**
	 * @param birthDay TODO
	 */
	public void setBirthDay(String birthDay) {
		this.birthDay = birthDay;
	}

	/**
	 * @return TODO
	 */
	public String getLunarSolarCd() {
		return lunarSolarCd;
	}

	/**
	 * @param lunarSolarCd TODO
	 */
	public void setLunarSolarCd(String lunarSolarCd) {
		this.lunarSolarCd = lunarSolarCd;
	}

	/**
	 * @return TODO
	 */
	public String getGender() {
		return gender;
	}

	/**
	 * @param gender TODO
	 */
	public void setGender(String gender) {
		this.gender = gender;
	}

	/**
	 * @return TODO
	 */
	public String getWifiMacAddr() {
		return wifiMacAddr;
	}

	/**
	 * @param wifiMacAddr TODO
	 */
	public void setWifiMacAddr(String wifiMacAddr) {
		this.wifiMacAddr = wifiMacAddr;
	}

}
