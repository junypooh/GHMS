/*******************************************************************************
 * Copyright(c) 2015 KT. All rights reserved.
 * This software is the proprietary information of KT.
 *******************************************************************************/
package com.kt.giga.home.infra.domain.checker;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.kt.giga.home.infra.domain.commons.HttpSoapObjectRequest;

/**
 * 
 * @author junypooh ( kyungjun.park@ceinside.co.kr )
 * @since 2015. 8. 4.
 */
@XmlRootElement(name="GetInternetSAID", namespace="http://tempuri.org/")
public class GetInternetSAID extends HttpSoapObjectRequest {

	/** 사용자ID */
	private String userID;
	
	/** 단말식별자 */
	private String uuID;
	
	/** AP 맥주소 */
	private String apMacAddress;

	@XmlElement(name="UserID", namespace="http://tempuri.org/")
	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}

	@XmlElement(name="UUID", namespace="http://tempuri.org/")
	public String getUuID() {
		return uuID;
	}

	public void setUuID(String uuID) {
		this.uuID = uuID;
	}

	@XmlElement(name="APMacAddress", namespace="http://tempuri.org/")
	public String getApMacAddress() {
		return apMacAddress;
	}

	public void setApMacAddress(String apMacAddress) {
		this.apMacAddress = apMacAddress;
	}

}
