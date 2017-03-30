package com.kt.giga.home.openapi.hcam.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * 사용자 init 클래스
 * 
 * @author 
 *
 */
@JsonInclude(Include.NON_EMPTY)
public class AppInitRequest {
	
	private String deviceId;
	private String telNo;
	private String appVer;
	private String pnsRegId;
	private String screenSize;	
	private String svcCode;

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public String getTelNo() {
		return telNo;
	}

	public void setTelNo(String telNo) {
		this.telNo = telNo;
	}

	public String getAppVer() {
		return appVer;
	}

	public void setAppVer(String appVer) {
		this.appVer = appVer;
	}

	public String getPnsRegId() {
		return pnsRegId;
	}

	public void setPnsRegId(String pnsRegId) {
		this.pnsRegId = pnsRegId;
	}

	public String getScreenSize() {
		return screenSize;
	}

	public void setScreenSize(String screenSize) {
		this.screenSize = screenSize;
	}

	public String getSvcCode() {
		return svcCode;
	}

	public void setSvcCode(String svcCode) {
		this.svcCode = svcCode;
	}
}
