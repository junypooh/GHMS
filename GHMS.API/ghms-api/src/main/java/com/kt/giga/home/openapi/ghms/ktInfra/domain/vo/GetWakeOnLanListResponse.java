/*******************************************************************************
 * Copyright(c) 2015 KT. All rights reserved.
 * This software is the proprietary information of KT.
 *******************************************************************************/
package com.kt.giga.home.openapi.ghms.ktInfra.domain.vo;


/**
 * 
 * @author junypooh ( kyungjun.park@ceinside.co.kr )
 * @since 2015. 8. 6.
 */
public class GetWakeOnLanListResponse {
	
	private GetWakeOnLanListResult getWakeOnLanListResult;

	public GetWakeOnLanListResult getGetWakeOnLanListResult() {
		return getWakeOnLanListResult;
	}

	public void setGetWakeOnLanListResult(
			GetWakeOnLanListResult getWakeOnLanListResult) {
		this.getWakeOnLanListResult = getWakeOnLanListResult;
	}

}
