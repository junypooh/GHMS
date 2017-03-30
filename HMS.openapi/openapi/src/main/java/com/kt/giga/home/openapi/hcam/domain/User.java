package com.kt.giga.home.openapi.hcam.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.kt.giga.home.openapi.common.AuthToken;
import org.apache.commons.lang.math.NumberUtils;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * 사용자 클래스
 * 
 * @author 
 *
 */
@JsonInclude(Include.NON_EMPTY)
public class User {

    public static final String PARTY_DETAIL_CD_01 = "01";    // 내국인
    public static final String PARTY_DETAIL_CD_02 = "02";    // 외국인
    public static final String PARTY_DETAIL_CD_07 = "07";    // 개인사업자

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

    private boolean isInternetOnlyID;
	
	public User() {
		
	}
	
	public User(Long mbrSeq, String mbrId) {
		this.mbrSeq			= mbrSeq;
		this.mbrId			= mbrId;		
	}
	
	public User(String dstrCd, String svcThemeCd, String unitSvcCd, Long mbrSeq, String mbrId) {
		
		this.dstrCd			= dstrCd;
		this.svcThemeCd 	= svcThemeCd;
		this.unitSvcCd		= unitSvcCd;
		
		this.mbrSeq			= mbrSeq;
		this.mbrId			= mbrId;
	}
	
	public User(String dstrCd, String svcThemeCd, AuthToken loginAuthToken) {
		
		this.dstrCd			= dstrCd;
		this.svcThemeCd 	= svcThemeCd;
		this.unitSvcCd		= loginAuthToken.getSvcCode();
		
		this.mbrSeq			= NumberUtils.toLong(loginAuthToken.getUserNo());
		this.mbrId			= loginAuthToken.getUserId();
		
		this.pnsRegId		= loginAuthToken.getPnsRegId();
		this.telNo			= loginAuthToken.getTelNo();
	}
	
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

	public void setMbrSeq(String mbrSeq) {
		this.mbrSeq = NumberUtils.toLong(mbrSeq);
    }

    public void setMbrSeq(Long mbrSeq) {
        this.mbrSeq = mbrSeq;
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

    public boolean isInternetOnlyID() {

        return isInternetOnlyID;
    }

    public void setInternetOnlyID(boolean isInternetOnlyID) {

        this.isInternetOnlyID = isInternetOnlyID;
    }

    @Override
    public String toString() {

        return MoreObjects.toStringHelper(this)
                .omitNullValues()
                .add("age", age)
                .add("applVer", applVer)
                .add("authToken", authToken)
                .add("birth", birth)
                .add("credentialId", credentialId)
                .add("custIdList", custIdList)
                .add("dstrCd", dstrCd)
                .add("mbrId", mbrId)
                .add("mbrSeq", mbrSeq)
                .add("pnsRegId", pnsRegId)
                .add("status", status)
                .add("svcThemeCd", svcThemeCd)
                .add("telNo", telNo)
                .add("termlId", termlId)
                .add("unitSvcCd", unitSvcCd).toString();

    }
}
