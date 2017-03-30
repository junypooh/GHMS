/*******************************************************************************
 * Copyright(c) 2015 KT. All rights reserved.
 * This software is the proprietary information of KT.
 *******************************************************************************/
package com.kt.giga.home.infra.domain.sdp;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.kt.giga.home.infra.domain.commons.HttpSoapObjectResponse;

/**
 * 
 * @author junypooh ( kyungjun.park@ceinside.co.kr )
 * @since 2015. 8. 20.
 */
@XmlRootElement(name="getSpecificSubsAndUserInfoResponse", namespace="http://kt.com/sdp")
public class SpecificSubsAndUserInfoResponse extends HttpSoapObjectResponse {

	/*
	Birth_Date : yyyyMMdd형태
	Lunar_Solar_Cal_Birth_Date_Cd : 01:양력, 02:음력
	Gender : 01 = Woman, 02 = Man
	Foreigner : 01 = Local, 02 = Foreigner
	*/
	private String resultCode;	
	private String resultMsg;
	private String birthDay;
	private String lunarSolarCd;
	private String gender;
	private String foreigner;
	
	public String getResultCode() {
		return resultCode;
	}
	@XmlElement(name="returnCode", namespace="http://kt.com/sdp")
	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}
	public String getResultMsg() {
		return resultMsg;
	}
	@XmlElement(name="returnDesc", namespace="http://kt.com/sdp")
	public void setResultMsg(String resultMsg) {
		this.resultMsg = resultMsg;
	}
	public String getBirthDay() {
		return birthDay;
	}
	@XmlElement(name="Birth_Date", namespace="http://kt.com/sdp")
	public void setBirthDay(String birthDay) {
		this.birthDay = birthDay;
	}
	public String getLunarSolarCd() {
		return lunarSolarCd;
	}
	@XmlElement(name="Lunar_Solar_Cal_Birth_Date_Cd", namespace="http://kt.com/sdp")
	public void setLunarSolarCd(String lunarSolarCd) {
		this.lunarSolarCd = lunarSolarCd;
	}
	public String getGender() {
		return gender;
	}
	@XmlElement(name="Gender", namespace="http://kt.com/sdp")
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getForeigner() {
		return foreigner;
	}
	@XmlElement(name="Foreigner", namespace="http://kt.com/sdp")
	public void setForeigner(String foreigner) {
		this.foreigner = foreigner;
	}

}
