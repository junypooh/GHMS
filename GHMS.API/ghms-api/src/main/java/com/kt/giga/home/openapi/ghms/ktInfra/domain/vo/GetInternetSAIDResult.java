/*******************************************************************************
 * Copyright(c) 2015 KT. All rights reserved.
 * This software is the proprietary information of KT.
 *******************************************************************************/
package com.kt.giga.home.openapi.ghms.ktInfra.domain.vo;


/**
 * 
 * @author junypooh ( kyungjun.park@ceinside.co.kr )
 * @since 2015. 8. 4.
 */
public class GetInternetSAIDResult {

    /** 처리결과 코드 */
	private String resultCode;

    /** 처리결과 메시지 */
	private String resultMessage;
	
	private String internetSAID;

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

	public String getInternetSAID() {
		return internetSAID;
	}

	public void setInternetSAID(String internetSAID) {
		this.internetSAID = internetSAID;
	}

}
