/*******************************************************************************
 * Copyright(c) 2015 KT. All rights reserved.
 * This software is the proprietary information of KT.
 *******************************************************************************/
package com.kt.giga.home.infra.controller.checker;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kt.giga.home.infra.domain.checker.CheckerKey;
import com.kt.giga.home.infra.domain.checker.GetInternetAccessControl;
import com.kt.giga.home.infra.domain.checker.GetInternetAccessControlResponse;
import com.kt.giga.home.infra.domain.checker.GetInternetSAID;
import com.kt.giga.home.infra.domain.checker.GetInternetSAIDResponse;
import com.kt.giga.home.infra.domain.checker.GetIoTEquipConnInfo;
import com.kt.giga.home.infra.domain.checker.GetIoTEquipConnInfoResponse;
import com.kt.giga.home.infra.domain.checker.GetIoTEquipConnInfoResult;
import com.kt.giga.home.infra.domain.checker.GetIoTEquipInfoResult;
import com.kt.giga.home.infra.domain.checker.GetNASStateInfo;
import com.kt.giga.home.infra.domain.checker.GetNASStateInfoResponse;
import com.kt.giga.home.infra.domain.checker.GetOllehIDResult;
import com.kt.giga.home.infra.domain.checker.GetPCOnOffState;
import com.kt.giga.home.infra.domain.checker.GetPCOnOffStateResponse;
import com.kt.giga.home.infra.domain.checker.GetServiceEquipInfo;
import com.kt.giga.home.infra.domain.checker.GetServiceEquipInfoResponse;
import com.kt.giga.home.infra.domain.checker.GetWakeOnLanList;
import com.kt.giga.home.infra.domain.checker.GetWakeOnLanListResponse;
import com.kt.giga.home.infra.domain.checker.SetDeviceTokenInfo;
import com.kt.giga.home.infra.domain.checker.SetDeviceTokenInfoResponse;
import com.kt.giga.home.infra.domain.checker.SetInternetAccessControl;
import com.kt.giga.home.infra.domain.checker.SetInternetAccessControlRecovery;
import com.kt.giga.home.infra.domain.checker.SetInternetAccessControlRecoveryResponse;
import com.kt.giga.home.infra.domain.checker.SetInternetAccessControlResponse;
import com.kt.giga.home.infra.domain.checker.SetWakeOnLan;
import com.kt.giga.home.infra.domain.checker.SetWakeOnLanResponse;
import com.kt.giga.home.infra.service.checker.CheckerService;

/**
 * 스마트홈 체커 연동 Controller
 * @author junypooh ( kyungjun.park@ceinside.co.kr )
 * @since 2015. 6. 4.
 */
@Controller
@RequestMapping("/checker")
public class CheckerController {
	
	@Autowired
	private CheckerService checkerService;
	
	/**
	 * Infra -> Checker 연동 API
	 */
	/**
	 * AP 인터넷 이용제한 목록 조회
	 * @param req
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value={"/getInternetAccessControl"}, method=RequestMethod.POST)
	public @ResponseBody GetInternetAccessControlResponse getInternetAccessControl(@RequestBody GetInternetAccessControl req) throws Exception {		
		GetInternetAccessControlResponse res = (GetInternetAccessControlResponse)checkerService.getInternetAccessControl(req);
		
		return res;
	}
	
	/**
	 * AP 인터넷 이용제한 룰관리
	 * @param req
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value={"/setInternetAccessControl"}, method=RequestMethod.POST)
	public @ResponseBody SetInternetAccessControlResponse setInternetAccessControl(@RequestBody SetInternetAccessControl req) throws Exception {		
		SetInternetAccessControlResponse res = (SetInternetAccessControlResponse)checkerService.setInternetAccessControl(req);
		
		return res;
	}
	
	/**
	 * AP 인터넷 이용제한 룰 복구
	 * @param req
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value={"/setInternetAccessControlRecovery"}, method=RequestMethod.POST)
	public @ResponseBody SetInternetAccessControlRecoveryResponse setInternetAccessControlRecovery(@RequestBody SetInternetAccessControlRecovery req) throws Exception {		
		SetInternetAccessControlRecoveryResponse res = (SetInternetAccessControlRecoveryResponse)checkerService.setInternetAccessControlRecovery(req);
		
		return res;
	}
	
	/**
	 * KT 서비스단말 상태조회
	 * @param req
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value={"/getServiceEquipInfo"}, method=RequestMethod.POST)
	public @ResponseBody GetServiceEquipInfoResponse getServiceEquipInfo(@RequestBody GetServiceEquipInfo req) throws Exception {		
		GetServiceEquipInfoResponse res = (GetServiceEquipInfoResponse)checkerService.getServiceEquipInfo(req);
		
		return res;
	}
	
	/**
	 * NAS 상태조회
	 * @param req
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value={"/getNASStateInfo"}, method=RequestMethod.POST)
	public @ResponseBody GetNASStateInfoResponse getNASStateInfo(@RequestBody GetNASStateInfo req) throws Exception {		
		GetNASStateInfoResponse res = (GetNASStateInfoResponse)checkerService.getNASStateInfo(req);
		
		return res;
	}
	
	/**
	 * PC 원격 켜기 목록조회
	 * @param req
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value={"/getWakeOnLanList"}, method=RequestMethod.POST)
	public @ResponseBody GetWakeOnLanListResponse getWakeOnLanList(@RequestBody GetWakeOnLanList req) throws Exception {		
		GetWakeOnLanListResponse res = (GetWakeOnLanListResponse)checkerService.getWakeOnLanList(req);
		
		return res;
	}
	
	/**
	 * PC 원격 켜기
	 * @param req
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value={"/setWakeOnLan"}, method=RequestMethod.POST)
	public @ResponseBody SetWakeOnLanResponse setWakeOnLan(@RequestBody SetWakeOnLan req) throws Exception {		
		SetWakeOnLanResponse res = (SetWakeOnLanResponse)checkerService.setWakeOnLan(req);
		
		return res;
	}
	
	/**
	 * PC On/Off 상태 체크
	 * @param req
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value={"/getPCOnOffState"}, method=RequestMethod.POST)
	public @ResponseBody GetPCOnOffStateResponse getPCOnOffState(@RequestBody GetPCOnOffState req) throws Exception {		
		GetPCOnOffStateResponse res = (GetPCOnOffStateResponse)checkerService.getPCOnOffState(req);
		
		return res;
	}
	
	/**
	 * Push ID 전송
	 * @param req
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value={"/setDeviceTokenInfo"}, method=RequestMethod.POST)
	public @ResponseBody SetDeviceTokenInfoResponse setDeviceTokenInfo(@RequestBody SetDeviceTokenInfo req) throws Exception {		
		SetDeviceTokenInfoResponse res = (SetDeviceTokenInfoResponse)checkerService.setDeviceTokenInfo(req);
		
		return res;
	}

	/**
	 * 서비스계약ID 조회
	 * @param req
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value={"/getInternetSAID"}, method=RequestMethod.POST)
	public @ResponseBody GetInternetSAIDResponse getInternetSAID(@RequestBody GetInternetSAID req) throws Exception {		
		GetInternetSAIDResponse res = (GetInternetSAIDResponse)checkerService.getInternetSAID(req);
		
		return res;
	}
	
	/**
	 * Checker -> Infra 연동 API
	 */
	/**
	 * 홈 IoT 서비스단말 상태조회
	 * @param inType
	 * @param inValue
	 * @return GetIoTEquipInfoResult
	 * @throws Exception
	 */
	@RequestMapping(value={"/getIoTEquipInfo"}, method=RequestMethod.GET)
	public @ResponseBody GetIoTEquipInfoResult getIoTEquipInfo(
			@RequestParam(value="InType") String inType,
			@RequestParam(value="InValue") String inValue
			) throws Exception {	
		
		CheckerKey key = new CheckerKey(inType, inValue);
		
		return checkerService.getIoTEquipInfo(key);
	}
	
	/**
	 * Olleh통합ID 조회
	 * @param inType
	 * @param inValue
	 * @return GetOllehIDResult
	 * @throws Exception
	 */
	@RequestMapping(value={"/getOllehID"}, method=RequestMethod.GET)
	public @ResponseBody GetOllehIDResult getOllehID(
			@RequestParam(value="InType") String inType,
			@RequestParam(value="InValue") String inValue
			) throws Exception {	
		
		CheckerKey key = new CheckerKey(inType, inValue);
		
		return checkerService.getOllehID(key);
	}
	
	/**
	 * IoT 접속단말 연결상태 요청
	 * @param req
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value={"/getIoTEquipConnInfo"}, method=RequestMethod.POST)
	public @ResponseBody GetIoTEquipConnInfoResult getIoTEquipConnInfo(@RequestBody GetIoTEquipConnInfo req) throws Exception {		
		
		return checkerService.getIoTEquipConnInfo(req);
	}
	
	/**
	 * IoT 접속단말 연결상태 조회
	 * @param req
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value={"/getIoTEquipConnInfoResponse"}, method=RequestMethod.POST)
	public @ResponseBody GetIoTEquipConnInfoResponse getIoTEquipConnInfoResponse(@RequestBody GetIoTEquipConnInfo req) throws Exception {		
		
		return checkerService.getIoTEquipConnInfoResponse(req);
	}

}
