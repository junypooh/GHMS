/*******************************************************************************
 * Copyright(c) 2015 KT. All rights reserved.
 * This software is the proprietary information of KT.
 *******************************************************************************/
package com.kt.giga.home.openapi.ghms.user.domain.key;

/**
 * 현장장치 설정 Vo
 * @author junypooh ( kyungjun.park@ceinside.co.kr )
 * @since 2015. 2. 3.
 */
public class DeviceCntrKey {

	/** 설정 코드 값 */
	private String cntrCode;
	
	/** 설정 코드 Value */
	private String cntrValue;
	
	/**
	 * @return TODO
	 */
	public String getCntrCode() {
		return cntrCode;
	}

	/**
	 * @param cntrCode TODO
	 */
	public void setCntrCode(String cntrCode) {
		this.cntrCode = cntrCode;
	}

	/**
	 * @return TODO
	 */
	public String getCntrValue() {
		return cntrValue;
	}

	/**
	 * @param cntrValue TODO
	 */
	public void setCntrValue(String cntrValue) {
		this.cntrValue = cntrValue;
	}

}
