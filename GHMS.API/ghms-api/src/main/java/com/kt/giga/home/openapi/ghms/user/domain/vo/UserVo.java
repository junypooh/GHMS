/*******************************************************************************
 * Copyright(c) 2015 KT. All rights reserved.
 * This software is the proprietary information of KT.
 *******************************************************************************/
package com.kt.giga.home.openapi.ghms.user.domain.vo;

import java.util.List;

import org.apache.commons.lang.math.NumberUtils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * 사용자 VO
 * @author junypooh ( kyungjun.park@ceinside.co.kr )
 * @since 2015. 2. 2.
 */
@JsonInclude(Include.NON_EMPTY)
public class UserVo {
	
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
	 * 회원 일련 번호
	 */
	private Long mbrSeq;
	
	/**
	 * 회원 아이디
	 */
	private String mbrId;
	
	/**
	 * Credential ID
	 */
	private String credentialId;
	
	/**
	 * 앱 단말 아이디
	 */
	private String termlId;
	
	/**
	 * PNS Registration ID
	 */
	private String pnsRegId;
	
	/**
	 * 앱 버전
	 */
	private String applVer;
	
	/**
	 * 전화 번호
	 */
	private String telNo;

	/**
	 * 로그인 인증 토큰
	 */
	private String authToken;
	
	/**
	 * 신규 발급 로그인 인증 토큰
	 */
	private String newAuthToken;
	
	/**
	 * 로그인 상태. 0:오프라인, 1:온라인
	 */
	private String status;
	
	/**
	 * 생일
	 */
	private String birth;
	
	/**
	 * 나이
	 */
	private Integer age;
	
	/**
	 * CustID 리스트
	 */
	private String[] custIdList;
	
	/**
	 * ICIS 원천계약ID 리스트
	 */
    private String[] icisSaIdList;

	/**
	 * AP 서비스 아이디 리스트
	 */
    private List<String> apSvcIdList;
	
	/** 사용자 이름(사용자 리스트 조회용) */
	private String userNm;
	
	/** 사용자 일련번호(사용자 리스트 조회용) */
	private String userNo;
	
	/** 로그인 실패(아이디 패스워드 불일치) 여부 */
	private boolean loginPassYn;
    
    /** 샌싱태그 리스트 */
    private List<SnsnTagBasVo> snsnTagBasVo;
	
	public UserVo() {
		
	}
	
	public UserVo(Long mbrSeq, String mbrId, String credentialId) {
		this.mbrSeq			= mbrSeq;
		this.mbrId			= mbrId;
		this.credentialId	= credentialId;
	}

	public UserVo(String dstrCd, String svcThemeCd, String unitSvcCd, Long mbrSeq, String mbrId) {
		
		this.dstrCd			= dstrCd;
		this.svcThemeCd 	= svcThemeCd;
		this.unitSvcCd		= unitSvcCd;
		
		this.mbrSeq			= mbrSeq;
		this.mbrId			= mbrId;
	}
/*
	public UserVo(String dstrCd, String svcThemeCd, AuthToken loginAuthToken) {
		
		this.dstrCd			= dstrCd;
		this.svcThemeCd 	= svcThemeCd;
		this.unitSvcCd		= loginAuthToken.getUnitSvcCode();
		
		this.mbrSeq			= NumberUtils.toLong(loginAuthToken.getUserNo());
		this.mbrId			= loginAuthToken.getUserId();
		
		this.pnsRegId		= loginAuthToken.getPnsRegId();
		this.telNo			= loginAuthToken.getTelNo();
	}
	*/
	public String getStatus() {
		return status;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}

	public String getDstrCd() {
		return dstrCd;
	}

	public void setDstrCd(String dstrCd) {
		this.dstrCd = dstrCd;
	}

	public String getSvcThemeCd() {
		return svcThemeCd;
	}

	public void setSvcThemeCd(String svcThemeCd) {
		this.svcThemeCd = svcThemeCd;
	}

	public String getUnitSvcCd() {
		return unitSvcCd;
	}

	public void setUnitSvcCd(String unitSvcCd) {
		this.unitSvcCd = unitSvcCd;
	}

	public Long getMbrSeq() {
		return mbrSeq;
	}

	public void setMbrSeq(Long mbrSeq) {
		this.mbrSeq = mbrSeq;
	}
	
	public void setMbrSeq(String mbrSeq) {
		this.mbrSeq = NumberUtils.toLong(mbrSeq);
	}

	public String getMbrId() {
		return mbrId;
	}

	public void setMbrId(String mbrId) {
		this.mbrId = mbrId;
	}

	public String getCredentialId() {
		return credentialId;
	}

	public void setCredentialId(String credentialId) {
		this.credentialId = credentialId;
	}

	public String getTermlId() {
		return termlId;
	}

	public void setTermlId(String termlId) {
		this.termlId = termlId;
	}

	public String getPnsRegId() {
		return pnsRegId;
	}

	public void setPnsRegId(String pnsRegId) {
		this.pnsRegId = pnsRegId;
	}

	public String getApplVer() {
		return applVer;
	}

	public void setApplVer(String applVer) {
		this.applVer = applVer;
	}

	public String getTelNo() {
		return telNo;
	}

	public void setTelNo(String telNo) {
		this.telNo = telNo;
	}

	public String getAuthToken() {
		return authToken;
	}

	public void setAuthToken(String authToken) {
		this.authToken = authToken;
	}

	public String getNewAuthToken() {
		return newAuthToken;
	}

	public void setNewAuthToken(String newAuthToken) {
		this.newAuthToken = newAuthToken;
	}

	public String getBirth() {
		return birth;
	}

	public void setBirth(String birth) {
		this.birth = birth;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public String[] getCustIdList() {
		return custIdList;
	}

	public void setCustIdList(String[] custIdList) {
		this.custIdList = custIdList;
	}

	/**
	 * @return TODO
	 */
	public String[] getIcisSaIdList() {
		return icisSaIdList;
	}

	/**
	 * @param icisSaIdList TODO
	 */
	public void setIcisSaIdList(String[] icisSaIdList) {
		this.icisSaIdList = icisSaIdList;
	}

	/**
	 * @return TODO
	 */
	public List<String> getApSvcIdList() {
		return apSvcIdList;
	}

	/**
	 * @param apSvcIdList TODO
	 */
	public void setApSvcIdList(List<String> apSvcIdList) {
		this.apSvcIdList = apSvcIdList;
	}

	/**
	 * @return TODO
	 */
	public String getUserNm() {
		return userNm;
	}

	/**
	 * @param userNm TODO
	 */
	public void setUserNm(String userNm) {
		this.userNm = userNm;
	}

	/**
	 * @return TODO
	 */
	public String getUserNo() {
		return userNo;
	}

	/**
	 * @param userNo TODO
	 */
	public void setUserNo(String userNo) {
		this.userNo = userNo;
	}

	/**
	 * @return TODO
	 */
	public boolean isLoginPassYn() {
		return loginPassYn;
	}

	/**
	 * @param loginPassYn TODO
	 */
	public void setLoginPassYn(boolean loginPassYn) {
		this.loginPassYn = loginPassYn;
	}

	/**
	 * @return TODO
	 */
	public List<SnsnTagBasVo> getSnsnTagBasVo() {
		return snsnTagBasVo;
	}

	/**
	 * @param snsnTagBasVo TODO
	 */
	public void setSnsnTagBasVo(List<SnsnTagBasVo> snsnTagBasVo) {
		this.snsnTagBasVo = snsnTagBasVo;
	}
}
