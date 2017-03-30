/*******************************************************************************
 * Copyright(c) 2015 KT. All rights reserved.
 * This software is the proprietary information of KT.
 *******************************************************************************/
package com.kt.giga.home.openapi.ghms.checker.domain.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * AP 인터넷 이용제한 목록 조회 Response
 * @author junypooh ( kyungjun.park@ceinside.co.kr )
 * @since 2015. 6. 4.
 */
@JsonInclude(Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
public class InternetAccessRule {

	/** C: 등록, U : 수정, D : 삭제 */
	private String crudFlag;
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
    
    /** 현재시간 포함여부 */
    private boolean currentEnable;

	public String getCrudFlag() {
		return crudFlag;
	}

	public void setCrudFlag(String crudFlag) {
		this.crudFlag = crudFlag;
	}

	public String getIndex() {
		return index;
	}

	public void setIndex(String index) {
		this.index = index;
	}

	public String getInternetAccessRuleEnable() {
		return internetAccessRuleEnable;
	}

	public void setInternetAccessRuleEnable(String internetAccessRuleEnable) {
		this.internetAccessRuleEnable = internetAccessRuleEnable;
	}

	public String getInternetAccessRuleName() {
		return internetAccessRuleName;
	}

	public void setInternetAccessRuleName(String internetAccessRuleName) {
		this.internetAccessRuleName = internetAccessRuleName;
	}

	public String getInternetAccessControlMac() {
		return internetAccessControlMac;
	}

	public void setInternetAccessControlMac(String internetAccessControlMac) {
		this.internetAccessControlMac = internetAccessControlMac;
	}

	public String getInternetAccessDate() {
		return internetAccessDate;
	}

	public void setInternetAccessDate(String internetAccessDate) {
		this.internetAccessDate = internetAccessDate;
	}

	public String getInternetAccessStartTime() {
		return internetAccessStartTime;
	}

	public void setInternetAccessStartTime(String internetAccessStartTime) {
		this.internetAccessStartTime = internetAccessStartTime;
	}

	public String getInternetAccessEndTime() {
		return internetAccessEndTime;
	}

	public void setInternetAccessEndTime(String internetAccessEndTime) {
		this.internetAccessEndTime = internetAccessEndTime;
	}

	public boolean isCurrentEnable() {
		return currentEnable;
	}

	public void setCurrentEnable(boolean currentEnable) {
		this.currentEnable = currentEnable;
	}
}
