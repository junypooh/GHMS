/*******************************************************************************
 * Copyright(c) 2015 KT. All rights reserved.
 * This software is the proprietary information of KT.
 *******************************************************************************/
package com.kt.giga.home.openapi.ghms.user.domain.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * 
 * @author junypooh ( kyungjun.park@ceinside.co.kr )
 * @since 2015. 2. 4.
 */
@JsonInclude(Include.NON_EMPTY)
public class MasterUserVo {
	
	
	private String telNo;
	
	/** 회원 일련 번호 */
	private long userNo;
	
	/** 회원 닉네임 */
	private String userNm;
	
	/** 서비스 대상 일련번호 */
	private long serviceNo;
	
	/** 현장 장치 일련번호 */
	private String devUUID;

	/** 비밀 번호 */
	private String passwd;
	
	/** 프로필 url */
	private String userImgUrl;
	
	/** 비상연락망 시퀀스 */
	private int seq;
	
	/** 설정 코드 값 */
	private String cntrCode;                
	
	/** 설정 코드 value */
	private String cntrValue;
	
	/** SMS 발송 설정 여부 (Y:발송, N:미발송) */
	private String pushYn;
	
	/** count */
	private int cnt;
	
	/** 등록되어있는 도어락 최대 메모리 */
	private int maxMemory;
	
	/** 사용자 패스워드 일련번호 */
	private int passwdSeq;

    /**
     * @return TODO
     */
    public String getTelNo() {
        return telNo;
    }

    /**
     * @return TODO
     */
    public long getUserNo() {
        return userNo;
    }

    /**
     * @return TODO
     */
    public String getUserNm() {
        return userNm;
    }

    /**
     * @return TODO
     */
    public long getServiceNo() {
        return serviceNo;
    }

    /**
     * @return TODO
     */
    public String getDevUUID() {
        return devUUID;
    }

    /**
     * @return TODO
     */
    public String getPasswd() {
        return passwd;
    }

    /**
     * @return TODO
     */
    public String getUserImgUrl() {
        return userImgUrl;
    }

    /**
     * @return TODO
     */
    public int getSeq() {
        return seq;
    }

    /**
     * @return TODO
     */
    public String getCntrCode() {
        return cntrCode;
    }

    /**
     * @return TODO
     */
    public String getCntrValue() {
        return cntrValue;
    }

    /**
     * @param telNo TODO
     */
    public void setTelNo(String telNo) {
        this.telNo = telNo;
    }

    /**
     * @param userNo TODO
     */
    public void setUserNo(long userNo) {
        this.userNo = userNo;
    }

    /**
     * @param userNm TODO
     */
    public void setUserNm(String userNm) {
        this.userNm = userNm;
    }

    /**
     * @param serviceNo TODO
     */
    public void setServiceNo(long serviceNo) {
        this.serviceNo = serviceNo;
    }

    /**
     * @param devUUID TODO
     */
    public void setDevUUID(String devUUID) {
        this.devUUID = devUUID;
    }

    /**
     * @param passwd TODO
     */
    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    /**
     * @param userImgUrl TODO
     */
    public void setUserImgUrl(String userImgUrl) {
        this.userImgUrl = userImgUrl;
    }

    /**
     * @param seq TODO
     */
    public void setSeq(int seq) {
        this.seq = seq;
    }

    /**
     * @param cntrCode TODO
     */
    public void setCntrCode(String cntrCode) {
        this.cntrCode = cntrCode;
    }

    /**
     * @param cntrValue TODO
     */
    public void setCntrValue(String cntrValue) {
        this.cntrValue = cntrValue;
    }

    /**
     * @return TODO
     */
    public String getPushYn() {
        return pushYn;
    }

    /**
     * @param pushYn TODO
     */
    public void setPushYn(String pushYn) {
        this.pushYn = pushYn;
    }

    /**
     * @return TODO
     */
    public int getCnt() {
        return cnt;
    }

    /**
     * @return TODO
     */
    public int getMaxMemory() {
        return maxMemory;
    }

    /**
     * @param cnt TODO
     */
    public void setCnt(int cnt) {
        this.cnt = cnt;
    }
    
    /**
     * @return TODO
     */
    public int getPasswdSeq() {
        return passwdSeq;
    }

    /**
     * @param passwdSeq TODO
     */
    public void setPasswdSeq(int passwdSeq) {
        this.passwdSeq = passwdSeq;
    }

    /**
     * @param maxMemory TODO
     */
    public void setMaxMemory(int maxMemory) {
        this.maxMemory = maxMemory;
    }
    
    

}
