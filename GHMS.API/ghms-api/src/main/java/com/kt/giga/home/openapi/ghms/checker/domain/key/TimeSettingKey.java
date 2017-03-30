/*******************************************************************************
 * Copyright(c) 2015 KT. All rights reserved.
 * This software is the proprietary information of KT.
 *******************************************************************************/
package com.kt.giga.home.openapi.ghms.checker.domain.key;

import java.util.List;

import com.kt.giga.home.openapi.ghms.checker.domain.vo.InternetAccessRule;

/**
 * 
 * @author junypooh ( kyungjun.park@ceinside.co.kr )
 * @since 2015. 7. 20.
 */
public class TimeSettingKey {
	
	private String userId;
	
	private Long svcTgtSeq;
	
	private Long spotDevSeq;
	
	private String apMacAddress;
	
	private String apPassWord;
	
	private String pcMacAddress;
	
	private String hostMacAddress;
	
	private String devNm;
	
	private String type;
	
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

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Long getSvcTgtSeq() {
		return svcTgtSeq;
	}

	public void setSvcTgtSeq(Long svcTgtSeq) {
		this.svcTgtSeq = svcTgtSeq;
	}

	public Long getSpotDevSeq() {
		return spotDevSeq;
	}

	public void setSpotDevSeq(Long spotDevSeq) {
		this.spotDevSeq = spotDevSeq;
	}

	public String getApMacAddress() {
		return apMacAddress;
	}

	public void setApMacAddress(String apMacAddress) {
		this.apMacAddress = apMacAddress;
	}

	public String getApPassWord() {
		return apPassWord;
	}

	public void setApPassWord(String apPassWord) {
		this.apPassWord = apPassWord;
	}

	public String getPcMacAddress() {
		return pcMacAddress;
	}

	public void setPcMacAddress(String pcMacAddress) {
		this.pcMacAddress = pcMacAddress;
	}

	public String getHostMacAddress() {
		return hostMacAddress;
	}

	public void setHostMacAddress(String hostMacAddress) {
		this.hostMacAddress = hostMacAddress;
	}

	public String getDevNm() {
		return devNm;
	}

	public void setDevNm(String devNm) {
		this.devNm = devNm;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

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
