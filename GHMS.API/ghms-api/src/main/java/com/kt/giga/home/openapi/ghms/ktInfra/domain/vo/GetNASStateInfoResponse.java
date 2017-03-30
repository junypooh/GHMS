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
public class GetNASStateInfoResponse {
	
	private GetNASStateInfoResult getNASStateInfoResult;

	public GetNASStateInfoResult getGetNASStateInfoResult() {
		return getNASStateInfoResult;
	}

	public void setGetNASStateInfoResult(GetNASStateInfoResult getNASStateInfoResult) {
		this.getNASStateInfoResult = getNASStateInfoResult;
	}

}
