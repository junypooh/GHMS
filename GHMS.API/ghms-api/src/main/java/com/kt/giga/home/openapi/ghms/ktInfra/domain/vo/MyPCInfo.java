/*******************************************************************************
 * Copyright(c) 2015 KT. All rights reserved.
 * This software is the proprietary information of KT.
 *******************************************************************************/
package com.kt.giga.home.openapi.ghms.ktInfra.domain.vo;


/**
 * 
 * @author junypooh ( kyungjun.park@ceinside.co.kr )
 * @since 2015. 8. 11.
 */
public class MyPCInfo {
	
	private String pcMacAddress;
	
	private String pcDescription;

	public String getPcMacAddress() {
		return pcMacAddress;
	}

	public void setPcMacAddress(String pcMacAddress) {
		this.pcMacAddress = pcMacAddress;
	}

	public String getPcDescription() {
		return pcDescription;
	}

	public void setPcDescription(String pcDescription) {
		this.pcDescription = pcDescription;
	}

}
