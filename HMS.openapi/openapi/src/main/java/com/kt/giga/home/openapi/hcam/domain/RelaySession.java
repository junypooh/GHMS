package com.kt.giga.home.openapi.hcam.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * 실시간 모니터링 세션 클래스
 *
 * @author
 *
 */
@JsonInclude(Include.NON_EMPTY)
public class RelaySession {

	/**
	 * 실시간 모니터링 세션 아이디
	 */
	private String sessionId;

	/**
	 * RTSP URL
	 */
	private String rtspUrl;

	/**
	 * AES 인증 키
	 */
	private String key;

	/**
	 * AES 인증 Initial Vector
	 */
	private String iv;

	/**
	 * 세션 만료 시간 (분)
	 */
	private int expireTime;

	/**
	 * 최대 세션 만료 시간 (분)
	 */
	private int maxExpireTime;

	/**
	 * 채널 생성/조인 상태. CREATE or JOIN
	 */
	private String status;

	/**
	 * 카메라 연결 상태 조회 결과
	 */
	private String result;

	/**
	 * 사생활보호 모드
	 */
	private String videoactive;

	public String getSessionId() {
		return sessionId;
	}
	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}
	public String getRtspUrl() {
		return rtspUrl;
	}
	public void setRtspUrl(String rtspUrl) {
		this.rtspUrl = rtspUrl;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getIv() {
		return iv;
	}
	public void setIv(String iv) {
		this.iv = iv;
	}
	public int getExpireTime() {
		return expireTime;
	}
	public void setExpireTime(int expireTime) {
		this.expireTime = expireTime;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}

	public int getMaxExpireTime() {
		return this.maxExpireTime;
	}

	public void setMaxExpireTime(int maxExtensionMinute) {
		this.maxExpireTime = maxExtensionMinute;
	}

	public String getResult() {
		return this.result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getVideoActive() {
		return this.videoactive;
	}

	public void setVideoActive(String videoActive) {
		this.videoactive = videoActive;
	}
}
