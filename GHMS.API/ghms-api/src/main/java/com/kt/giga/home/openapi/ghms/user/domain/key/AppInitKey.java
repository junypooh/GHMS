/*******************************************************************************
 * Copyright(c) 2015 KT. All rights reserved.
 * This software is the proprietary information of KT.
 *******************************************************************************/
package com.kt.giga.home.openapi.ghms.user.domain.key;

/**
 * 앱 초기 실행
 * @author junypooh ( kyungjun.park@ceinside.co.kr )
 * @since 2015. 4. 3.
 */
public class AppInitKey {
	
	
	/** 마스터구분(M,S,T)+지역코드+서비스테마코드+단위서비스코드 */
	private String unionSvcCode;
	
	/** 앱 단말 디바이스 아이디 */
	private String deviceId;
	
	/** 전화 번호 */
	private String telNo;
	
	/** 앱 버전 */
	private String appVer;
	
	/** PNS 등록 registration_id */
	private String pnsRegId;
	
	/** 앱 단말 스크린 사이즈. 'h', 'xh', 'xxh' */
	private String screenSize;

	/**
	 * @return TODO
	 */
	public String getUnionSvcCode() {
		return unionSvcCode;
	}

	/**
	 * @param unionSvcCode TODO
	 */
	public void setUnionSvcCode(String unionSvcCode) {
		this.unionSvcCode = unionSvcCode;
	}

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
	public String getTelNo() {
		return telNo;
	}

	/**
	 * @param telNo TODO
	 */
	public void setTelNo(String telNo) {
		this.telNo = telNo;
	}

	/**
	 * @return TODO
	 */
	public String getAppVer() {
		return appVer;
	}

	/**
	 * @param appVer TODO
	 */
	public void setAppVer(String appVer) {
		this.appVer = appVer;
	}

	/**
	 * @return TODO
	 */
	public String getPnsRegId() {
		return pnsRegId;
	}

	/**
	 * @param pnsRegId TODO
	 */
	public void setPnsRegId(String pnsRegId) {
		this.pnsRegId = pnsRegId;
	}

	/**
	 * @return TODO
	 */
	public String getScreenSize() {
		return screenSize;
	}

	/**
	 * @param screenSize TODO
	 */
	public void setScreenSize(String screenSize) {
		this.screenSize = screenSize;
	}
	
	

}
