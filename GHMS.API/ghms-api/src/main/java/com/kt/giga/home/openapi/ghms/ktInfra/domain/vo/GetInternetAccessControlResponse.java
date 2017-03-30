/*******************************************************************************
 * Copyright(c) 2015 KT. All rights reserved.
 * This software is the proprietary information of KT.
 *******************************************************************************/
package com.kt.giga.home.openapi.ghms.ktInfra.domain.vo;


/**
 * 
 * @author junypooh ( kyungjun.park@ceinside.co.kr )
 * @since 2015. 6. 10.
 */
public class GetInternetAccessControlResponse {
	
	private GetInternetAccessControlResult getInternetAccessControlResult;

	public GetInternetAccessControlResult getGetInternetAccessControlResult() {
		return getInternetAccessControlResult;
	}

	public void setGetInternetAccessControlResult(
			GetInternetAccessControlResult getInternetAccessControlResult) {
		this.getInternetAccessControlResult = getInternetAccessControlResult;
	}

}
