package com.kt.giga.home.openapi.hcam.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * 음성 세션 클래스
 * 
 * @author 
 *
 */
@JsonInclude(Include.NON_EMPTY)
public class VoiceSession {
	
	/**
	 * 음성 세션 아이디
	 */
	private String sessionId;
	
	/**
	 * 서버 주소. 앱에서 접속하게 될 EC 서버 주소
	 */
	private String ecServer;
	
	/**
	 * 서버 리스닝 포트. 앱에서 접속하게 될 EC 서버 포트
	 */
	private Integer ecPort;
	
	/**
	 * 인증 요청 번호. EC 접속 인증에 사용할 인증 요청 번호
	 */
	private String athnRqtNo;
	
	/**
	 * 카메라의 서비스 대상 일련번호
	 */
	private Long svcTgtSeq;
	
	/**
	 * 카메라의 현장장치 일련번호
	 */
	private Long spotDevSeq;
	
	/**
	 * 음성 전송 센싱태그 코드
	 */
	private String snsnTagCd;
	
	/**
	 * 음성 세션 만료시간 (분)
	 */
	private Integer expireTime;
	
	/**
	 * 세션 권한을 소유한 회원 일련번호
	 */
	private Long mbrSeq;

	/**
	 * 세션 권한을 소유한 앱 단말 아이디
	 */
	private String termlId;
	
	/**
	 * 카메라의 디바이스 UUID
	 */
	private String devUUID;
	
	public String getSessionId() {
		return sessionId;
	}
	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}
	public String getEcServer() {
		return ecServer;
	}
	public void setEcServer(String ecServer) {
		this.ecServer = ecServer;
	}
	public Integer getEcPort() {
		return ecPort;
	}
	public void setEcPort(Integer ecPort) {
		this.ecPort = ecPort;
	}
	public String getAthnRqtNo() {
		return athnRqtNo;
	}
	public void setAthnRqtNo(String athnRqtNo) {
		this.athnRqtNo = athnRqtNo;
	}
	public Long getSvcTgtSeq() {
		return svcTgtSeq;
	}
	public void setSvcTgtSeq(Long svcTgtSeq) {
		this.svcTgtSeq = svcTgtSeq;
	}
	public Long getSpotDevSeq() {
		return spotDevSeq;
	}
	public void setSpotDevSeq(Long spotDevSeq) {
		this.spotDevSeq = spotDevSeq;
	}
	public String getSnsnTagCd() {
		return snsnTagCd;
	}
	public void setSnsnTagCd(String snsnTagCd) {
		this.snsnTagCd = snsnTagCd;
	}
	public Integer getExpireTime() {
		return expireTime;
	}
	public void setExpireTime(Integer expireTime) {
		this.expireTime = expireTime;
	}
	public Long getMbrSeq() {
		return mbrSeq;
	}
	public void setMbrSeq(Long mbrSeq) {
		this.mbrSeq = mbrSeq;
	}
	public String getTermlId() {
		return termlId;
	}
	public void setTermlId(String termlId) {
		this.termlId = termlId;
	}
	public String getDevUUID() {
		return devUUID;
	}
	public void setDevUUID(String devUUID) {
		this.devUUID = devUUID;
	}
	
}
