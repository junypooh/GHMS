/*******************************************************************************
 * Copyright(c) 2015 KT. All rights reserved.
 * This software is the proprietary information of KT.
 *******************************************************************************/
package com.kt.giga.home.infra.domain.checker;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlElementRefs;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 
 * @author junypooh ( kyungjun.park@ceinside.co.kr )
 * @since 2015. 8. 6.
 */
@XmlRootElement(name="GetWakeOnLanListResult", namespace="http://tempuri.org/")
public class GetWakeOnLanListResult {
	
	/** 처리결과 코드 */
	private String resultCode;
	
	/** 처리결과 메시지 */
	private String resultMessage;
	
	private List<MyPCInfo> pcInfoList;

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

	public List<MyPCInfo> getPcInfoList() {
		return pcInfoList;
	}

	@XmlElementWrapper(name="PCInfoList", namespace="http://tempuri.org/")
	@XmlElementRefs({
		@XmlElementRef(name="MyPCInfo", namespace="http://tempuri.org/", type=MyPCInfo.class)
	})
	public void setPcInfoList(List<MyPCInfo> pcInfoList) {
		this.pcInfoList = pcInfoList;
	}


}
