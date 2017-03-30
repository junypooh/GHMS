/*******************************************************************************
 * Copyright(c) 2015 KT. All rights reserved.
 * This software is the proprietary information of KT.
 *******************************************************************************/
package com.kt.giga.home.infra.domain.checker;

import org.codehaus.jackson.annotate.JsonProperty;


/**
 * 
 * @author junypooh ( kyungjun.park@ceinside.co.kr )
 * @since 2015. 6. 29.
 */
public class GetOllehIDResult {

	@JsonProperty("ResultCode")
	private String resultCode;

	@JsonProperty("ResultMessage")
	private String resultMessage;

	@JsonProperty("OllehID")
	private String ollehId;

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
	public String getOllehId() {
		return ollehId;
	}

	/**
	 * @param ollehId TODO
	 */
	public void setOllehId(String ollehId) {
		this.ollehId = ollehId;
	}

}
