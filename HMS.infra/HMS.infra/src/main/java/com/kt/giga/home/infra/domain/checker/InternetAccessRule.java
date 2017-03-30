/*******************************************************************************
 * Copyright(c) 2015 KT. All rights reserved.
 * This software is the proprietary information of KT.
 *******************************************************************************/
package com.kt.giga.home.infra.domain.checker;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * AP 인터넷 이용제한 목록 조회 Response
 * @author junypooh ( kyungjun.park@ceinside.co.kr )
 * @since 2015. 6. 4.
 */
@XmlRootElement(name="InternetAccessRule", namespace="http://tempuri.org/")
public class InternetAccessRule {

    /** 룰 인덱스 */
    private String index;

    /** 인터넷 접속 차단여부 */
    private String internetAccessRuleEnable;

    /** 룰 이름 */
    private String internetAccessRuleName;

    /** 이용제한 단말 맥 */
    private String internetAccessControlMac;

    /** 룰 적용 요일 */
    private String internetAccessDate;

    /** 시작 시간 */
    private String internetAccessStartTime;

    /** 종료 시간 */
    private String internetAccessEndTime;

	public String getIndex() {
		return index;
	}

	@XmlElement(name="Index", namespace="http://tempuri.org/")
	public void setIndex(String index) {
		this.index = index;
	}

	public String getInternetAccessRuleEnable() {
		return internetAccessRuleEnable;
	}

	@XmlElement(name="InternetAccessRuleEnable", namespace="http://tempuri.org/")
	public void setInternetAccessRuleEnable(String internetAccessRuleEnable) {
		this.internetAccessRuleEnable = internetAccessRuleEnable;
	}

	public String getInternetAccessRuleName() {
		return internetAccessRuleName;
	}

	@XmlElement(name="InternetAccessRuleName", namespace="http://tempuri.org/")
	public void setInternetAccessRuleName(String internetAccessRuleName) {
		this.internetAccessRuleName = internetAccessRuleName;
	}

	public String getInternetAccessControlMac() {
		return internetAccessControlMac;
	}

	@XmlElement(name="InternetAccessControlMac", namespace="http://tempuri.org/")
	public void setInternetAccessControlMac(String internetAccessControlMac) {
		this.internetAccessControlMac = internetAccessControlMac;
	}

	public String getInternetAccessDate() {
		return internetAccessDate;
	}

	@XmlElement(name="InternetAccessDate", namespace="http://tempuri.org/")
	public void setInternetAccessDate(String internetAccessDate) {
		this.internetAccessDate = internetAccessDate;
	}

	public String getInternetAccessStartTime() {
		return internetAccessStartTime;
	}

	@XmlElement(name="InternetAccessStartTime", namespace="http://tempuri.org/")
	public void setInternetAccessStartTime(String internetAccessStartTime) {
		this.internetAccessStartTime = internetAccessStartTime;
	}

	public String getInternetAccessEndTime() {
		return internetAccessEndTime;
	}

	@XmlElement(name="InternetAccessEndTime", namespace="http://tempuri.org/")
	public void setInternetAccessEndTime(String internetAccessEndTime) {
		this.internetAccessEndTime = internetAccessEndTime;
	}
}
