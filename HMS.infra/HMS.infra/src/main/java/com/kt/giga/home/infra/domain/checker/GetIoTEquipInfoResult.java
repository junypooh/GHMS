/*******************************************************************************
 * Copyright(c) 2015 KT. All rights reserved.
 * This software is the proprietary information of KT.
 *******************************************************************************/
package com.kt.giga.home.infra.domain.checker;

import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;


/**
 * 
 * @author junypooh ( kyungjun.park@ceinside.co.kr )
 * @since 2015. 6. 29.
 */
@JsonInclude(Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
public class GetIoTEquipInfoResult {

	@JsonProperty("ResultCode")
	private String resultCode;

	@JsonProperty("ResultMessage")
	private String resultMessage;

	@JsonProperty("IoTGWInfo")
	private List<IoTGWInfo> ioTGWInfos;

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
	public List<IoTGWInfo> getIoTGWInfos() {
		return ioTGWInfos;
	}

	/**
	 * @param ioTGWInfos TODO
	 */
	public void setIoTGWInfos(List<IoTGWInfo> ioTGWInfos) {
		this.ioTGWInfos = ioTGWInfos;
	}

}
