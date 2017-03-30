/*******************************************************************************
 * Copyright(c) 2015 KT. All rights reserved.
 * This software is the proprietary information of KT.
 *******************************************************************************/
package com.kt.giga.home.openapi.ghms.common.domain.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * 리턴 VO 최상위 Class
 * @author junypooh ( kyungjun.park@ceinside.co.kr )
 * @since 2015. 2. 2.
 */
@JsonInclude(Include.NON_EMPTY)
public class BaseVo {

	private String resultCode;
	
	private String resultMsg;
	
	private String stateCode;

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
	public String getResultMsg() {
		return resultMsg;
	}

	/**
	 * @param resultMsg TODO
	 */
	public void setResultMsg(String resultMsg) {
		this.resultMsg = resultMsg;
	}

	/**
	 * @return TODO
	 */
	public String getStateCode() {
		return stateCode;
	}

	/**
	 * @param stateCode TODO
	 */
	public void setStateCode(String stateCode) {
		this.stateCode = stateCode;
	}
}
