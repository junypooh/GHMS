/*******************************************************************************
 * Copyright(c) 2015 KT. All rights reserved.
 * This software is the proprietary information of KT.
 *******************************************************************************/
package com.kt.giga.home.openapi.ghms.ktInfra.domain.vo;


/**
 * 
 * @author junypooh ( kyungjun.park@ceinside.co.kr )
 * @since 2015. 6. 23.
 */
public class GetPCOnOffStateResponse {
	
	private GetPCOnOffStateResult getPCOnOffStateResult;

	public GetPCOnOffStateResult getGetPCOnOffStateResult() {
		return getPCOnOffStateResult;
	}

	public void setGetPCOnOffStateResult(GetPCOnOffStateResult getPCOnOffStateResult) {
		this.getPCOnOffStateResult = getPCOnOffStateResult;
	}

}
