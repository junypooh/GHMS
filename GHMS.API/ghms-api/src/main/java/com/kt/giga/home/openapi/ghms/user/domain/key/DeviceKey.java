/*******************************************************************************
 * Copyright(c) 2015 KT. All rights reserved.
 * This software is the proprietary information of KT.
 *******************************************************************************/
package com.kt.giga.home.openapi.ghms.user.domain.key;


/**
 * APP <-> OpenAPI 간 사용하는 DeviceVo
 * @author junypooh ( kyungjun.park@ceinside.co.kr )
 * @since 2015. 2. 3.
 */
public class DeviceKey {

	/** 현장장치 일련번호 */
	private long devUUID;
	
	/** 현장장치 기본 장치명 */
	private String devNm;
	
	/** 장치모델 기본 장치모델명 */
	private String devModelNm;
	
	/** 설정 코드 값 */
	private String cntrCode;
	
	/** 설정 코드 value */
	private String cntrValue;

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

	/**
	 * @return TODO
	 */
	public String getDevNm() {
		return devNm;
	}

	/**
	 * @param devNm TODO
	 */
	public void setDevNm(String devNm) {
		this.devNm = devNm;
	}

	/**
	 * @return TODO
	 */
	public String getDevModelNm() {
		return devModelNm;
	}

	/**
	 * @param devModelNm TODO
	 */
	public void setDevModelNm(String devModelNm) {
		this.devModelNm = devModelNm;
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
	
	
}
