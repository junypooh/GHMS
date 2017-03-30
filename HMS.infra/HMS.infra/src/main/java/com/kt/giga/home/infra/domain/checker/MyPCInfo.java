/*******************************************************************************
 * Copyright(c) 2015 KT. All rights reserved.
 * This software is the proprietary information of KT.
 *******************************************************************************/
package com.kt.giga.home.infra.domain.checker;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 
 * @author junypooh ( kyungjun.park@ceinside.co.kr )
 * @since 2015. 8. 11.
 */
@XmlRootElement(name="MyPCInfo", namespace="http://tempuri.org/")
public class MyPCInfo {
	
	private String pcMacAddress;
	
	private String pcDescription;

	public String getPcMacAddress() {
		return pcMacAddress;
	}

	@XmlElement(name="PCMacAddress", namespace="http://tempuri.org/")
	public void setPcMacAddress(String pcMacAddress) {
		this.pcMacAddress = pcMacAddress;
	}

	public String getPcDescription() {
		return pcDescription;
	}

	@XmlElement(name="PCDescription", namespace="http://tempuri.org/")
	public void setPcDescription(String pcDescription) {
		this.pcDescription = pcDescription;
	}

}
