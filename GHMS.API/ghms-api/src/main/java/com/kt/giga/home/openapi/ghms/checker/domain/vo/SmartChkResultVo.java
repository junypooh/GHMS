/*******************************************************************************
 * Copyright(c) 2015 KT. All rights reserved.
 * This software is the proprietary information of KT.
 *******************************************************************************/
package com.kt.giga.home.openapi.ghms.checker.domain.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * 
 * @author junypooh ( kyungjun.park@ceinside.co.kr )
 * @since 2015. 7. 20.
 */
@JsonInclude(Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
public class SmartChkResultVo {
	
    /** 처리결과 코드 */
    private String resultCode;

    /** 처리결과 메시지 */
    private String resultMessage;
    
    private String onOff;
    
    private String enable;

	/**
	 * @return TODO
	 */
	public String getResultCode() {
		return resultCode;
	}

	/**
	 * @param resultCode TODO
	 */
	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}

	/**
	 * @return TODO
	 */
	public String getResultMessage() {
		return resultMessage;
	}

	/**
	 * @param resultMessage TODO
	 */
	public void setResultMessage(String resultMessage) {
		this.resultMessage = resultMessage;
	}

	/**
	 * @return TODO
	 */
	public String getOnOff() {
		return onOff;
	}

	/**
	 * @param onOff TODO
	 */
	public void setOnOff(String onOff) {
		this.onOff = onOff;
	}

	public String getEnable() {
		return enable;
	}

	public void setEnable(String enable) {
		this.enable = enable;
	}

}
