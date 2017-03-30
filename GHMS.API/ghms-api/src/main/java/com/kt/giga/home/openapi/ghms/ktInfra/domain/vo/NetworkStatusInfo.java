/*******************************************************************************
 * Copyright(c) 2015 KT. All rights reserved.
 * This software is the proprietary information of KT.
 *******************************************************************************/
package com.kt.giga.home.openapi.ghms.ktInfra.domain.vo;

import java.util.List;

/**
 * 
 * @author junypooh ( kyungjun.park@ceinside.co.kr )
 * @since 2015. 6. 8.
 */
public class NetworkStatusInfo {
	
	/** OnOff상태 */
	private String onOff;
	
	/** 망구분 */
	private String internetType;
	
	/** KT단말 상태 정보 */
	private List<HoldingEquipInfo> holdingEquipInfoList;

	public String getOnOff() {
		return onOff;
	}

	public void setOnOff(String onOff) {
		this.onOff = onOff;
	}

	public String getInternetType() {
		return internetType;
	}

	public void setInternetType(String internetType) {
		this.internetType = internetType;
	}

	public List<HoldingEquipInfo> getHoldingEquipInfoList() {
		return holdingEquipInfoList;
	}

	public void setHoldingEquipInfoList(List<HoldingEquipInfo> holdingEquipInfoList) {
		this.holdingEquipInfoList = holdingEquipInfoList;
	}

}
