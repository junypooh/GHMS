/*******************************************************************************
 * Copyright(c) 2015 KT. All rights reserved.
 * This software is the proprietary information of KT.
 *******************************************************************************/
package com.kt.giga.home.infra.domain.checker;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.kt.giga.home.infra.domain.commons.HttpSoapObjectRequest;

/**
 * KT 서비스단말 상태조회 Request
 * @author junypooh ( kyungjun.park@ceinside.co.kr )
 * @since 2015. 6. 8.
 */
@XmlRootElement(name="GetServiceEquipInfo", namespace="http://tempuri.org/")
public class GetServiceEquipInfo extends HttpSoapObjectRequest {

	/** 사용자ID */
	private String userID;

	/** 단말식별자 */
	private String uuID;

	/** 조회 유형 */
	private String inType;
	
	/** 조회 값 */
	private String inValue;

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

	@XmlElement(name="InType", namespace="http://tempuri.org/")
	public String getInType() {
		return inType;
	}

	public void setInType(String inType) {
		this.inType = inType;
	}

	@XmlElement(name="InValue", namespace="http://tempuri.org/")
	public String getInValue() {
		return inValue;
	}

	public void setInValue(String inValue) {
		this.inValue = inValue;
	}

}
