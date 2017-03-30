/*******************************************************************************
 * Copyright(c) 2015 KT. All rights reserved.
 * This software is the proprietary information of KT.
 *******************************************************************************/
package com.kt.giga.home.openapi.ghms.ktInfra.domain.vo;

import java.util.List;

import com.kt.giga.home.openapi.ghms.checker.domain.vo.InternetAccessRule;

/**
 * AP 인터넷 이용제한 목록 조회 Response
 * @author junypooh ( kyungjun.park@ceinside.co.kr )
 * @since 2015. 6. 4.
 */
public class GetInternetAccessControlResult {
	
    /** 처리결과 코드 */
    private String resultCode;

    /** 처리결과 메시지 */
    private String resultMessage;
    
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
