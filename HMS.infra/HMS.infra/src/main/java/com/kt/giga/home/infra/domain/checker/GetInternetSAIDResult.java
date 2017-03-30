/*******************************************************************************
 * Copyright(c) 2015 KT. All rights reserved.
 * This software is the proprietary information of KT.
 *******************************************************************************/
package com.kt.giga.home.infra.domain.checker;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 
 * @author junypooh ( kyungjun.park@ceinside.co.kr )
 * @since 2015. 8. 4.
 */
@XmlRootElement(name="GetInternetSAIDResult", namespace="http://tempuri.org/")
public class GetInternetSAIDResult {

    /** 처리결과 코드 */
	private String resultCode;

    /** 처리결과 메시지 */
	private String resultMessage;
	
	private String internetSAID;

	public String getResultCode() {
		return resultCode;
	}

	@XmlElement(name="ResultCode", namespace="http://tempuri.org/")
	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}

	public String getResultMessage() {
		return resultMessage;
	}

	@XmlElement(name="ResultMessage", namespace="http://tempuri.org/")
	public void setResultMessage(String resultMessage) {
		this.resultMessage = resultMessage;
	}

	public String getInternetSAID() {
		return internetSAID;
	}

	@XmlElement(name="InternetSAID", namespace="http://tempuri.org/")
	public void setInternetSAID(String internetSAID) {
		this.internetSAID = internetSAID;
	}

}
