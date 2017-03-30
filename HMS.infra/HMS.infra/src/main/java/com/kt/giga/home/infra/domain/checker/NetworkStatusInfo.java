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
 * @since 2015. 6. 8.
 */
@XmlRootElement(name="NetworkStatusInfo", namespace="http://tempuri.org/")
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

	@XmlElement(name="OnOff", namespace="http://tempuri.org/")
	public void setOnOff(String onOff) {
		this.onOff = onOff;
	}

	public String getInternetType() {
		return internetType;
	}

	@XmlElement(name="InternetType", namespace="http://tempuri.org/")
	public void setInternetType(String internetType) {
		this.internetType = internetType;
	}

	public List<HoldingEquipInfo> getHoldingEquipInfoList() {
		return holdingEquipInfoList;
	}

	@XmlElementWrapper(name="HoldingEquipInfoList", namespace="http://tempuri.org/")
	@XmlElementRefs({
		@XmlElementRef(name="HoldingEquipInfo", namespace="http://tempuri.org/", type=HoldingEquipInfo.class)
	})
	public void setHoldingEquipInfoList(List<HoldingEquipInfo> holdingEquipInfoList) {
		this.holdingEquipInfoList = holdingEquipInfoList;
	}

}
