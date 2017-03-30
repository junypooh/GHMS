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
 * KT 서비스단말 상태조회 Response
 * @author junypooh ( kyungjun.park@ceinside.co.kr )
 * @since 2015. 6. 8.
 */
@XmlRootElement(name="GetServiceEquipInfoResult", namespace="http://tempuri.org/")
public class GetServiceEquipInfoResult {
	
	/** 처리결과 코드 */
	private String resultCode;
	
	/** 처리결과 메시지 */
	private String resultMessage;
	
	/** 네트워크 상태정보 */
	private List<NetworkStatusInfo> networkStatusInfo;

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

	public List<NetworkStatusInfo> getNetworkStatusInfo() {
		return networkStatusInfo;
	}

	@XmlElementWrapper(name="NetworkStatusInfoList", namespace="http://tempuri.org/")
	@XmlElementRefs({
		@XmlElementRef(name="NetworkStatusInfo", namespace="http://tempuri.org/", type=NetworkStatusInfo.class)
	})
	public void setNetworkStatusInfo(List<NetworkStatusInfo> networkStatusInfo) {
		this.networkStatusInfo = networkStatusInfo;
	}

}
