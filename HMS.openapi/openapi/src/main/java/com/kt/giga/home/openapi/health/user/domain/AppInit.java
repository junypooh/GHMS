package com.kt.giga.home.openapi.health.user.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_EMPTY)
public class AppInit {
	
	private String authToken;
	private String deviceId;
	private String newAppVer;
	private String appUpOption;
	private String noticeOption;
	private String noticeUrl;
	private String joinGuideUrl;
	private String ucloudJoinGuideUrl;
	private String welcomeGuideUrl;
	private String coachGuideUrl;
	
	
	public String getNoticeUrl() {
		return noticeUrl;
	}

	public void setNoticeUrl(String noticeUrl) {
		this.noticeUrl = noticeUrl;
	}

	public String getJoinGuideUrl() {
		return joinGuideUrl;
	}

	public void setJoinGuideUrl(String joinGuideUrl) {
		this.joinGuideUrl = joinGuideUrl;
	}

	public String getUcloudJoinGuideUrl() {
		return ucloudJoinGuideUrl;
	}

	public void setUcloudJoinGuideUrl(String ucloudJoinGuideUrl) {
		this.ucloudJoinGuideUrl = ucloudJoinGuideUrl;
	}

	public String getWelcomeGuideUrl() {
		return welcomeGuideUrl;
	}

	public void setWelcomeGuideUrl(String welcomeGuideUrl) {
		this.welcomeGuideUrl = welcomeGuideUrl;
	}

	public String getCoachGuideUrl() {
		return coachGuideUrl;
	}

	public void setCoachGuideUrl(String coachGuideUrl) {
		this.coachGuideUrl = coachGuideUrl;
	}

	public String getAuthToken() {
		return authToken;
	}
	
	public void setAuthToken(String authToken) {
		this.authToken = authToken;
	}
	
	public String getDeviceId() {
		return deviceId;
	}
	
	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}
	
	public String getNewAppVer() {
		return newAppVer;
	}
	
	public void setNewAppVer(String newAppVer) {
		this.newAppVer = newAppVer;
	}
	
	public String getAppUpOption() {
		return appUpOption;
	}
	
	public void setAppUpOption(String appUpOption) {
		this.appUpOption = appUpOption;
	}
	
	public String getNoticeOption() {
		return noticeOption;
	}
	
	public void setNoticeOption(String noticeOption) {
		this.noticeOption = noticeOption;
	}
	
	
}
