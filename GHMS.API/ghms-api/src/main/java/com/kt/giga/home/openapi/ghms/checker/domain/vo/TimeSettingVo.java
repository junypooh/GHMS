/*******************************************************************************
 * Copyright(c) 2015 KT. All rights reserved.
 * This software is the proprietary information of KT.
 *******************************************************************************/
package com.kt.giga.home.openapi.ghms.checker.domain.vo;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * 
 * @author junypooh ( kyungjun.park@ceinside.co.kr )
 * @since 2015. 7. 20.
 */
@JsonInclude(Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
public class TimeSettingVo {
	
    /** 처리결과 코드 */
    private String resultCode;

    /** 처리결과 메시지 */
    private String resultMessage;
    
    /** GiGA-AP 맥주소 */
    private String gigaAPMac;
    
    /** 인터넷 이용제한 사용 여부 */
    private String internetAccessControlEnable;
    
    /** 인터넷 이용제한 룰 목록 */
    private List<InternetAccessRule> internetAccessRules;

	public String getResultCode() {
		return resultCode;
	}

	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}

	public String getResultMessage() {
		return resultMessage;
	}

	public void setResultMessage(String resultMessage) {
		this.resultMessage = resultMessage;
	}

	public String getGigaAPMac() {
		return gigaAPMac;
	}

	public void setGigaAPMac(String gigaAPMac) {
		this.gigaAPMac = gigaAPMac;
	}

	public String getInternetAccessControlEnable() {
		return internetAccessControlEnable;
	}

	public void setInternetAccessControlEnable(String internetAccessControlEnable) {
		this.internetAccessControlEnable = internetAccessControlEnable;
	}

	public List<InternetAccessRule> getInternetAccessRules() {
		return internetAccessRules;
	}

	public void setInternetAccessRules(List<InternetAccessRule> internetAccessRules) {
		this.internetAccessRules = internetAccessRules;
	}
}
