/*******************************************************************************
 * Copyright(c) 2015 KT. All rights reserved.
 * This software is the proprietary information of KT.
 *******************************************************************************/
package com.kt.giga.home.infra.domain.checker;

import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

import com.kt.giga.home.infra.domain.commons.HttpObjectResponse;

/**
 * 
 * @author junypooh ( kyungjun.park@ceinside.co.kr )
 * @since 2015. 7. 23.
 */
public class GetIoTEquipConnInfoResult implements HttpObjectResponse {

	@JsonProperty("ResultCode")
	private String resultCode;

	@JsonProperty("ResultMessage")
	private String resultMessage;

	@JsonProperty("TransacIdList")
	private List<String> transacIdList;

	public String getResultCode() {
		return resultCode;
	}

	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}

	public String getResultMessage() {
		return resultMessage;
	}

	public void setResultMessage(String resultMessage) {
		this.resultMessage = resultMessage;
	}

	public List<String> getTransacIdList() {
		return transacIdList;
	}

	public void setTransacIdList(List<String> transacIdList) {
		this.transacIdList = transacIdList;
	}

}
