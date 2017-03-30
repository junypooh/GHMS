/*******************************************************************************
 * Copyright(c) 2015 KT. All rights reserved.
 * This software is the proprietary information of KT.
 *******************************************************************************/
package com.kt.giga.home.openapi.ghms.ktInfra.domain.vo;

import java.util.List;

/**
 * KT 서비스단말 상태조회 Response
 * @author junypooh ( kyungjun.park@ceinside.co.kr )
 * @since 2015. 6. 8.
 */
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

	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}

	public String getResultMessage() {
		return resultMessage;
	}

	public void setResultMessage(String resultMessage) {
		this.resultMessage = resultMessage;
	}

	public List<NetworkStatusInfo> getNetworkStatusInfo() {
		return networkStatusInfo;
	}

	public void setNetworkStatusInfo(List<NetworkStatusInfo> networkStatusInfo) {
		this.networkStatusInfo = networkStatusInfo;
	}

}
