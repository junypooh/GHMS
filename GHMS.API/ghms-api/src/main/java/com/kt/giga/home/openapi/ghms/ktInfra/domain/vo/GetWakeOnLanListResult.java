/*******************************************************************************
 * Copyright(c) 2015 KT. All rights reserved.
 * This software is the proprietary information of KT.
 *******************************************************************************/
package com.kt.giga.home.openapi.ghms.ktInfra.domain.vo;

import java.util.List;

/**
 * 
 * @author junypooh ( kyungjun.park@ceinside.co.kr )
 * @since 2015. 8. 6.
 */
public class GetWakeOnLanListResult {
	
	/** 처리결과 코드 */
	private String resultCode;
	
	/** 처리결과 메시지 */
	private String resultMessage;
	
	private List<MyPCInfo> pcInfoList;

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

	public List<MyPCInfo> getPcInfoList() {
		return pcInfoList;
	}

	public void setPcInfoList(List<MyPCInfo> pcInfoList) {
		this.pcInfoList = pcInfoList;
	}

}
