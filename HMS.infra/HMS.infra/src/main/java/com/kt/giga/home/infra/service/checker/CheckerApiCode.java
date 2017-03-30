/*******************************************************************************
 * Copyright(c) 2015 KT. All rights reserved.
 * This software is the proprietary information of KT.
 *******************************************************************************/
package com.kt.giga.home.infra.service.checker;

import com.kt.giga.home.infra.service.ApiCode;

/**
 * 스마트홈 체커 연동 API 정보
 * @author junypooh ( kyungjun.park@ceinside.co.kr )
 * @since 2015. 6. 4.
 */
public enum CheckerApiCode implements ApiCode {

	/** AP 인터넷 이용제한 목록 조회 */
	getInternetAccessControl (
		"getInternetAccessControl",
		"post",
		"/GHMToDAAS.asmx",
		"http://tempuri.org/GetInternetAccessControl"
	),
	/** AP 인터넷 이용제한 룰관리 */
	setInternetAccessControl (
		"setInternetAccessControl",
		"post",
		"/GHMToDAAS.asmx",
		"http://tempuri.org/SetInternetAccessControl"
	),
	/** AP 인터넷 이용제한 룰 복구 */
	setInternetAccessControlRecovery (
		"setInternetAccessControlRecovery",
		"post",
		"/GHMToDAAS.asmx",
		"http://tempuri.org/SetInternetAccessControlRecovery"
	),
	/** KT 서비스단말 상태조회 */
	getServiceEquipInfo (
		"getServiceEquipInfo",
		"post",
		"/GHMToDAAS.asmx",
		"http://tempuri.org/GetServiceEquipInfo"
	),
	/** NAS 상태조회 */
	getNASStateInfo (
		"getNASStateInfo",
		"post",
		"/GHMToDAAS.asmx",
		"http://tempuri.org/GetNASStateInfo"
	),
	/** PC 원격 켜기 목록조회 */
	getWakeOnLanList (
		"getWakeOnLanList",
		"post",
		"/GHMToDAAS.asmx",
		"http://tempuri.org/GetWakeOnLanList"
	),
	/** PC 원격 켜기 */
	setWakeOnLan (
		"setWakeOnLan",
		"post",
		"/GHMToDAAS.asmx",
		"http://tempuri.org/SetWakeOnLan"
	),
	/** PC On/Off 상태 체크 */
	getPCOnOffState (
		"getPCOnOffState",
		"post",
		"/GHMToDAAS.asmx",
		"http://tempuri.org/GetPCOnOffState"
	),
	/** Push ID 전송 */
	setDeviceTokenInfo (
		"setDeviceTokenInfo",
		"post",
		"/GHMToDAAS.asmx",
		"http://tempuri.org/SetDeviceTokenInfo"
	),
	/** 서비스계약ID 조회 */
	getInternetSAID (
		"getInternetSAID",
		"post",
		"/GHMToDAAS.asmx",
		"http://tempuri.org/GetInternetSAID"
	),
	/** IoT 접속단말 연결상태 요청 */
	getIoTEquipConnInfo (
		"getIoTEquipConnInfo",
		"post",
		"/ghms/smartChecker/getIoTEquipConnInfo",
		""
	),
	/** IoT 접속단말 연결상태 조회 */
	getIoTEquipConnInfoResponse (
		"getIoTEquipConnInfo",
		"post",
		"/ghms/smartChecker/getIoTEquipConnInfoResponse",
		""
	);
	
	private String name;
	private String method;
	private String url;
	private String action;

	private CheckerApiCode(String name, String method, String url, String action) {
		this.name = name;
		this.method = method;
		this.url = url;		
		this.action = action;		
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

}
