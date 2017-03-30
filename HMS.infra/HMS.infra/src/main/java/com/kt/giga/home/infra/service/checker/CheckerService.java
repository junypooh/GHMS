/*******************************************************************************
 * Copyright(c) 2015 KT. All rights reserved.
 * This software is the proprietary information of KT.
 *******************************************************************************/
package com.kt.giga.home.infra.service.checker;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.ws.soap.SoapFault;
import org.springframework.ws.soap.SoapFaultDetailElement;
import org.springframework.ws.soap.client.SoapFaultClientException;

import com.kt.giga.home.infra.client.host.CheckerServerInfo;
import com.kt.giga.home.infra.client.host.GhmsOpenApiServerInfo;
import com.kt.giga.home.infra.client.http.CheckerHttpClient;
import com.kt.giga.home.infra.client.http.CheckerHttpClientFactory;
import com.kt.giga.home.infra.client.http.CheckerWebserviceHttpClient;
import com.kt.giga.home.infra.client.http.InfraHttpClient;
import com.kt.giga.home.infra.client.http.InfraHttpClientFactory;
import com.kt.giga.home.infra.dao.checker.CheckerDao;
import com.kt.giga.home.infra.domain.checker.CheckerKey;
import com.kt.giga.home.infra.domain.checker.GetInternetAccessControl;
import com.kt.giga.home.infra.domain.checker.GetInternetAccessControlResponse;
import com.kt.giga.home.infra.domain.checker.GetInternetAccessControlResult;
import com.kt.giga.home.infra.domain.checker.GetInternetSAID;
import com.kt.giga.home.infra.domain.checker.GetInternetSAIDResponse;
import com.kt.giga.home.infra.domain.checker.GetInternetSAIDResult;
import com.kt.giga.home.infra.domain.checker.GetIoTEquipConnInfo;
import com.kt.giga.home.infra.domain.checker.GetIoTEquipConnInfoResponse;
import com.kt.giga.home.infra.domain.checker.GetIoTEquipConnInfoResult;
import com.kt.giga.home.infra.domain.checker.GetIoTEquipInfoResult;
import com.kt.giga.home.infra.domain.checker.GetNASStateInfo;
import com.kt.giga.home.infra.domain.checker.GetNASStateInfoResponse;
import com.kt.giga.home.infra.domain.checker.GetNASStateInfoResult;
import com.kt.giga.home.infra.domain.checker.GetOllehIDResult;
import com.kt.giga.home.infra.domain.checker.GetPCOnOffState;
import com.kt.giga.home.infra.domain.checker.GetPCOnOffStateResponse;
import com.kt.giga.home.infra.domain.checker.GetPCOnOffStateResult;
import com.kt.giga.home.infra.domain.checker.GetServiceEquipInfo;
import com.kt.giga.home.infra.domain.checker.GetServiceEquipInfoResponse;
import com.kt.giga.home.infra.domain.checker.GetServiceEquipInfoResult;
import com.kt.giga.home.infra.domain.checker.GetWakeOnLanList;
import com.kt.giga.home.infra.domain.checker.GetWakeOnLanListResponse;
import com.kt.giga.home.infra.domain.checker.GetWakeOnLanListResult;
import com.kt.giga.home.infra.domain.checker.IoTDeviceInfo;
import com.kt.giga.home.infra.domain.checker.IoTGWInfo;
import com.kt.giga.home.infra.domain.checker.SetDeviceTokenInfo;
import com.kt.giga.home.infra.domain.checker.SetDeviceTokenInfoResponse;
import com.kt.giga.home.infra.domain.checker.SetDeviceTokenInfoResult;
import com.kt.giga.home.infra.domain.checker.SetInternetAccessControl;
import com.kt.giga.home.infra.domain.checker.SetInternetAccessControlRecovery;
import com.kt.giga.home.infra.domain.checker.SetInternetAccessControlRecoveryResponse;
import com.kt.giga.home.infra.domain.checker.SetInternetAccessControlRecoveryResult;
import com.kt.giga.home.infra.domain.checker.SetInternetAccessControlResponse;
import com.kt.giga.home.infra.domain.checker.SetInternetAccessControlResult;
import com.kt.giga.home.infra.domain.checker.SetWakeOnLan;
import com.kt.giga.home.infra.domain.checker.SetWakeOnLanResponse;
import com.kt.giga.home.infra.domain.checker.SetWakeOnLanResult;
import com.kt.giga.home.infra.domain.checker.SoapFaultInfo;
import com.kt.giga.home.infra.domain.commons.HttpObjectResponse;
import com.kt.giga.home.infra.domain.constants.ErrorCode;

/**
 * 스마트홈 체커 연동 Service
 * @author junypooh ( kyungjun.park@ceinside.co.kr )
 * @since 2015. 6. 4.
 */

@Service
public class CheckerService {

	private Logger log = Logger.getLogger(this.getClass());
	
	@Autowired
	private CheckerHttpClientFactory factory;
	
	@Autowired
	private InfraHttpClientFactory infrafactory;
	
	@Autowired
	private CheckerServerInfo serverInfo;
	
	@Autowired
	private GhmsOpenApiServerInfo ghmsServerInfo;
	
	@Autowired
	private CheckerDao checkerDao;
	
	/**
	 * Infra -> Checker 연동 API
	 */
	/**
	 * AP 인터넷 이용제한 목록 조회
	 * @param req
	 * @return
	 */
	public HttpObjectResponse getInternetAccessControl(GetInternetAccessControl req) {
		
		// 01. 클라이언트 생성 
		CheckerHttpClient checkerHttpClient = factory.getCheckerHttpClient(serverInfo);

		// 02. 2.4.1.1.	AP 인터넷 이용제한 목록 조회 요청
		GetInternetAccessControlResponse res = null;
		try {
			String url = serverInfo.getProtocol() + "://" 
					+ serverInfo.getHost() + ":" 
					+ serverInfo.getPort() 
					+ serverInfo.getUrl()
					+ CheckerApiCode.getInternetAccessControl.getUrl(); 
			
			res = (GetInternetAccessControlResponse)checkerHttpClient.send(url, req, new GetInternetAccessControlResponse(), CheckerApiCode.getInternetAccessControl.getAction());
		} catch (SoapFaultClientException e) {
			if(res == null) res = new GetInternetAccessControlResponse();
			GetInternetAccessControlResult getInternetAccessControlResult = new GetInternetAccessControlResult();
			res.setGetInternetAccessControlResult(getInternetAccessControlResult);
			Map<String, String> map = parseFalutException(e.getSoapFault());
			getInternetAccessControlResult.setResultCode(map.get("code"));
			getInternetAccessControlResult.setResultMessage(map.get("msg"));
			
			log.error(e.toString(), e.fillInStackTrace());
		} catch (Exception e) {
			if(res == null) res = new GetInternetAccessControlResponse();
			GetInternetAccessControlResult getInternetAccessControlResult = new GetInternetAccessControlResult();
			res.setGetInternetAccessControlResult(getInternetAccessControlResult);
			getInternetAccessControlResult.setResultCode(ErrorCode.RESULT_ERROR);
			getInternetAccessControlResult.setResultMessage(e.toString());
			
			log.error(e.toString(), e.fillInStackTrace());
		}
		
		return res;
	}
	
	/**
	 * AP 인터넷 이용제한 룰관리
	 * @param req
	 * @return
	 */
	public HttpObjectResponse setInternetAccessControl(SetInternetAccessControl req) {

		// 01. 클라이언트 생성 
		CheckerHttpClient checkerHttpClient = factory.getCheckerHttpClient(serverInfo);

		// 02. AP 인터넷 이용제한 룰관리
		SetInternetAccessControlResponse res = null;
		try {
			String url = serverInfo.getProtocol() + "://" 
					+ serverInfo.getHost() + ":" 
					+ serverInfo.getPort()  
					+ serverInfo.getUrl()
					+ CheckerApiCode.setInternetAccessControl.getUrl(); 
			
			res = (SetInternetAccessControlResponse)checkerHttpClient.send(url, req, new SetInternetAccessControlResponse(), CheckerApiCode.setInternetAccessControl.getAction());
		} catch (SoapFaultClientException e) {
			if(res == null) res = new SetInternetAccessControlResponse();
			SetInternetAccessControlResult result = new SetInternetAccessControlResult();
			Map<String, String> map = parseFalutException(e.getSoapFault());
			result.setResultCode(map.get("code"));
			result.setResultMessage(map.get("msg"));
			res.setSetInternetAccessControlResult(result);
			
			log.error(e.toString(), e.fillInStackTrace());
		} catch (Exception e) {
			if(res == null) res = new SetInternetAccessControlResponse();
			SetInternetAccessControlResult result = new SetInternetAccessControlResult();
			result.setResultCode(ErrorCode.RESULT_ERROR);
			result.setResultMessage(e.toString());
			res.setSetInternetAccessControlResult(result);
			
			log.error(e.toString(), e.fillInStackTrace());
		}
		
		return res;
	}
	
	/**
	 * AP 인터넷 이용제한 룰 복구
	 * @param req
	 * @return
	 */
	public HttpObjectResponse setInternetAccessControlRecovery(SetInternetAccessControlRecovery req) {

		// 01. 클라이언트 생성 
		CheckerHttpClient checkerHttpClient = factory.getCheckerHttpClient(serverInfo);

		// 02. AP 인터넷 이용제한 룰 복구
		SetInternetAccessControlRecoveryResponse res = null;
		try {
			String url = serverInfo.getProtocol() + "://" 
					+ serverInfo.getHost() + ":" 
					+ serverInfo.getPort() 
					+ serverInfo.getUrl()
					+ CheckerApiCode.setInternetAccessControlRecovery.getUrl(); 
			
			res = (SetInternetAccessControlRecoveryResponse)checkerHttpClient.send(url, req, new SetInternetAccessControlRecoveryResponse(), CheckerApiCode.setInternetAccessControlRecovery.getAction());
		} catch (SoapFaultClientException e) {
			if(res == null) res = new SetInternetAccessControlRecoveryResponse();
			SetInternetAccessControlRecoveryResult result = new SetInternetAccessControlRecoveryResult();
			Map<String, String> map = parseFalutException(e.getSoapFault());
			result.setResultCode(map.get("code"));
			result.setResultMessage(map.get("msg"));
			res.setSetInternetAccessControlRecoveryResult(result);
			
			log.error(e.toString(), e.fillInStackTrace());
		} catch (Exception e) {
			if(res == null) res = new SetInternetAccessControlRecoveryResponse();
			SetInternetAccessControlRecoveryResult result = new SetInternetAccessControlRecoveryResult();
			result.setResultCode(ErrorCode.RESULT_ERROR);
			result.setResultMessage(e.toString());
			res.setSetInternetAccessControlRecoveryResult(result);
			
			log.error(e.toString(), e.fillInStackTrace());
		}
		
		return res;
	}
	
	/**
	 * KT 서비스단말 상태조회
	 * @param req
	 * @return
	 */
	public HttpObjectResponse getServiceEquipInfo(GetServiceEquipInfo req) {

		// 01. 클라이언트 생성 
		CheckerHttpClient checkerHttpClient = factory.getCheckerHttpClient(serverInfo);

		// 02. KT 서비스단말 상태조회
		GetServiceEquipInfoResponse res = null;
		try {
			String url = serverInfo.getProtocol() + "://" 
					+ serverInfo.getHost() + ":" 
					+ serverInfo.getPort() 
					+ serverInfo.getUrl()
					+ CheckerApiCode.getServiceEquipInfo.getUrl(); 
			
			res = (GetServiceEquipInfoResponse)checkerHttpClient.send(url, req, new GetServiceEquipInfoResponse(), CheckerApiCode.getServiceEquipInfo.getAction());
		} catch (SoapFaultClientException e) {
			if(res == null) res = new GetServiceEquipInfoResponse();
			GetServiceEquipInfoResult result = new GetServiceEquipInfoResult();
			Map<String, String> map = parseFalutException(e.getSoapFault());
			result.setResultCode(map.get("code"));
			result.setResultMessage(map.get("msg"));
			res.setGetServiceEquipInfoResult(result);
			
			log.error(e.toString(), e.fillInStackTrace());
		} catch (Exception e) {
			if(res == null) res = new GetServiceEquipInfoResponse();
			GetServiceEquipInfoResult result = new GetServiceEquipInfoResult();
			result.setResultCode(ErrorCode.RESULT_ERROR);
			result.setResultMessage(e.toString());
			res.setGetServiceEquipInfoResult(result);
			
			log.error(e.toString(), e.fillInStackTrace());
		}
		
		return res;
	}
	
	/**
	 * NAS 상태조회
	 * @param req
	 * @return
	 */
	public HttpObjectResponse getNASStateInfo(GetNASStateInfo req) {

		// 01. 클라이언트 생성 
		CheckerHttpClient checkerHttpClient = factory.getCheckerHttpClient(serverInfo);

		// 02. NAS 상태조회
		GetNASStateInfoResponse res = null;
		try {
			String url = serverInfo.getProtocol() + "://" 
					+ serverInfo.getHost() + ":" 
					+ serverInfo.getPort() 
					+ serverInfo.getUrl()
					+ CheckerApiCode.getNASStateInfo.getUrl(); 
			
			res = (GetNASStateInfoResponse)checkerHttpClient.send(url, req, new GetNASStateInfoResponse(), CheckerApiCode.getNASStateInfo.getAction());
		} catch (SoapFaultClientException e) {
			if(res == null) res = new GetNASStateInfoResponse();
			GetNASStateInfoResult result = new GetNASStateInfoResult();
			Map<String, String> map = parseFalutException(e.getSoapFault());
			result.setResultCode(map.get("code"));
			result.setResultMessage(map.get("msg"));
			res.setGetNASStateInfoResult(result);
			
			log.error(e.toString(), e.fillInStackTrace());
		} catch (Exception e) {
			if(res == null) res = new GetNASStateInfoResponse();
			GetNASStateInfoResult result = new GetNASStateInfoResult();
			result.setResultCode(ErrorCode.RESULT_ERROR);
			result.setResultMessage(e.toString());
			res.setGetNASStateInfoResult(result);
			
			log.error(e.toString(), e.fillInStackTrace());
		}
		
		return res;
	}
	
	/**
	 * PC 원격 켜기 목록조회
	 * @param req
	 * @return
	 */
	public HttpObjectResponse getWakeOnLanList(GetWakeOnLanList req) {

		// 01. 클라이언트 생성 
		CheckerHttpClient checkerHttpClient = factory.getCheckerHttpClient(serverInfo);

		// 02. PC 원격 켜기 목록조회
		GetWakeOnLanListResponse res = null;
		try {
			String url = serverInfo.getProtocol() + "://" 
					+ serverInfo.getHost() + ":" 
					+ serverInfo.getPort() 
					+ serverInfo.getUrl()
					+ CheckerApiCode.setWakeOnLan.getUrl(); 
			
			res = (GetWakeOnLanListResponse)checkerHttpClient.send(url, req, new GetWakeOnLanListResponse(), CheckerApiCode.getWakeOnLanList.getAction());
		} catch (SoapFaultClientException e) {
			if(res == null) res = new GetWakeOnLanListResponse();
			GetWakeOnLanListResult result = new GetWakeOnLanListResult();
			Map<String, String> map = parseFalutException(e.getSoapFault());
			result.setResultCode(map.get("code"));
			result.setResultMessage(map.get("msg"));
			res.setGetWakeOnLanListResult(result);
			
			log.error(e.toString(), e.fillInStackTrace());
		} catch (Exception e) {
			if(res == null) res = new GetWakeOnLanListResponse();
			GetWakeOnLanListResult result = new GetWakeOnLanListResult();
			result.setResultCode(ErrorCode.RESULT_ERROR);
			result.setResultMessage(e.toString());
			res.setGetWakeOnLanListResult(result);
			
			log.error(e.toString(), e.fillInStackTrace());
		}
		
		return res;
	}
	
	/**
	 * PC 원격 켜기
	 * @param req
	 * @return
	 */
	public HttpObjectResponse setWakeOnLan(SetWakeOnLan req) {

		// 01. 클라이언트 생성 
		CheckerHttpClient checkerHttpClient = factory.getCheckerHttpClient(serverInfo);

		// 02. PC 원격 켜기
		SetWakeOnLanResponse res = null;
		try {
			String url = serverInfo.getProtocol() + "://" 
					+ serverInfo.getHost() + ":" 
					+ serverInfo.getPort() 
					+ serverInfo.getUrl()
					+ CheckerApiCode.setWakeOnLan.getUrl(); 
			
			res = (SetWakeOnLanResponse)checkerHttpClient.send(url, req, new SetWakeOnLanResponse(), CheckerApiCode.setWakeOnLan.getAction());
		} catch (SoapFaultClientException e) {
			if(res == null) res = new SetWakeOnLanResponse();
			SetWakeOnLanResult result = new SetWakeOnLanResult();
			Map<String, String> map = parseFalutException(e.getSoapFault());
			result.setResultCode(map.get("code"));
			result.setResultMessage(map.get("msg"));
			res.setSetWakeOnLanResult(result);
			
			log.error(e.toString(), e.fillInStackTrace());
		} catch (Exception e) {
			if(res == null) res = new SetWakeOnLanResponse();
			SetWakeOnLanResult result = new SetWakeOnLanResult();
			result.setResultCode(ErrorCode.RESULT_ERROR);
			result.setResultMessage(e.toString());
			res.setSetWakeOnLanResult(result);
			
			log.error(e.toString(), e.fillInStackTrace());
		}
		
		return res;
	}
	
	/**
	 * PC On/Off 상태 체크
	 * @param req
	 * @return
	 */
	public HttpObjectResponse getPCOnOffState(GetPCOnOffState req) {

		// 01. 클라이언트 생성 
		CheckerHttpClient checkerHttpClient = factory.getCheckerHttpClient(serverInfo);

		// 02. PC On/Off 상태 체크
		GetPCOnOffStateResponse res = null;
		try {
			String url = serverInfo.getProtocol() + "://" 
					+ serverInfo.getHost() + ":" 
					+ serverInfo.getPort() 
					+ serverInfo.getUrl()
					+ CheckerApiCode.getPCOnOffState.getUrl(); 
			
			res = (GetPCOnOffStateResponse)checkerHttpClient.send(url, req, new GetPCOnOffStateResponse(), CheckerApiCode.getPCOnOffState.getAction());
		} catch (SoapFaultClientException e) {
			if(res == null) res = new GetPCOnOffStateResponse();
			GetPCOnOffStateResult result = new GetPCOnOffStateResult();
			Map<String, String> map = parseFalutException(e.getSoapFault());
			result.setResultCode(map.get("code"));
			result.setResultMessage(map.get("msg"));
			res.setGetPCOnOffStateResult(result);
			
			log.error(e.toString(), e.fillInStackTrace());
		} catch (Exception e) {
			if(res == null) res = new GetPCOnOffStateResponse();
			GetPCOnOffStateResult result = new GetPCOnOffStateResult();
			result.setResultCode(ErrorCode.RESULT_ERROR);
			result.setResultMessage(e.toString());
			res.setGetPCOnOffStateResult(result);
			
			log.error(e.toString(), e.fillInStackTrace());
		}
		
		return res;
	}
	
	/**
	 * Push ID 전송
	 * @param req
	 * @return
	 */
	public HttpObjectResponse setDeviceTokenInfo(SetDeviceTokenInfo req) {

		// 01. 클라이언트 생성 
		CheckerHttpClient checkerHttpClient = factory.getCheckerHttpClient(serverInfo);

		// 02. PC On/Off 상태 체크
		SetDeviceTokenInfoResponse res = null;
		try {
			String url = serverInfo.getProtocol() + "://" 
					+ serverInfo.getHost() + ":" 
					+ serverInfo.getPort() 
					+ serverInfo.getUrl()
					+ CheckerApiCode.setDeviceTokenInfo.getUrl(); 
			
			res = (SetDeviceTokenInfoResponse)checkerHttpClient.send(url, req, new SetDeviceTokenInfoResponse(), CheckerApiCode.setDeviceTokenInfo.getAction());
		} catch (SoapFaultClientException e) {
			if(res == null) res = new SetDeviceTokenInfoResponse();
			SetDeviceTokenInfoResult result = new SetDeviceTokenInfoResult();
			Map<String, String> map = parseFalutException(e.getSoapFault());
			result.setResultCode(map.get("code"));
			result.setResultMessage(map.get("msg"));
			res.setSetDeviceTokenInfoResult(result);
			
			log.error(e.toString(), e.fillInStackTrace());
		} catch (Exception e) {
			if(res == null) res = new SetDeviceTokenInfoResponse();
			SetDeviceTokenInfoResult result = new SetDeviceTokenInfoResult();
			result.setResultCode(ErrorCode.RESULT_ERROR);
			result.setResultMessage(e.toString());
			res.setSetDeviceTokenInfoResult(result);
			
			log.error(e.toString(), e.fillInStackTrace());
		}
		
		return res;
	}
	
	/**
	 * 서비스계약ID 조회
	 * @param req
	 * @return
	 */
	public HttpObjectResponse getInternetSAID(GetInternetSAID req) {

		// 01. 클라이언트 생성 
		CheckerHttpClient checkerHttpClient = factory.getCheckerHttpClient(serverInfo);

		// 02. PC On/Off 상태 체크
		GetInternetSAIDResponse res = null;
		try {
			String url = serverInfo.getProtocol() + "://" 
					+ serverInfo.getHost() + ":" 
					+ serverInfo.getPort() 
					+ serverInfo.getUrl()
					+ CheckerApiCode.setDeviceTokenInfo.getUrl(); 
			
			res = (GetInternetSAIDResponse)checkerHttpClient.send(url, req, new GetInternetSAIDResponse(), CheckerApiCode.getInternetSAID.getAction());
		} catch (SoapFaultClientException e) {
			if(res == null) res = new GetInternetSAIDResponse();
			GetInternetSAIDResult result = new GetInternetSAIDResult();
			Map<String, String> map = parseFalutException(e.getSoapFault());
			result.setResultCode(map.get("code"));
			result.setResultMessage(map.get("msg"));
			res.setGetInternetSAIDResult(result);
			
			log.error(e.toString(), e.fillInStackTrace());
		} catch (Exception e) {
			if(res == null) res = new GetInternetSAIDResponse();
			GetInternetSAIDResult result = new GetInternetSAIDResult();
			result.setResultCode(ErrorCode.RESULT_ERROR);
			result.setResultMessage(e.toString());
			res.setGetInternetSAIDResult(result);
			
			log.error(e.toString(), e.fillInStackTrace());
		}
		
		return res;
	}
	
	private Map<String, String> parseFalutException(SoapFault fault) {
		Map<String, String> map = new HashMap<String, String>();
		try {
			if(fault.getFaultDetail() != null && fault.getFaultDetail().getDetailEntries() != null) {
				Iterator<SoapFaultDetailElement> entries = fault.getFaultDetail().getDetailEntries();
				
				if(entries != null && entries.hasNext()) {
					CheckerWebserviceHttpClient checkerHttpClient = (CheckerWebserviceHttpClient)factory.getCheckerHttpClient(serverInfo);
					
					SoapFaultDetailElement faultDetailElement = (SoapFaultDetailElement)entries.next();				
					SoapFaultInfo faultResponse = (SoapFaultInfo)checkerHttpClient.getWebServiceTemplate().getUnmarshaller().unmarshal(faultDetailElement.getSource());
					
					map.put("code", faultResponse.getFaultcode());
					map.put("msg", faultResponse.getFaultString());
				}
			}
		} catch (Exception e) {
			log.error(e.toString(), e.fillInStackTrace());
		}
		
		return map;
	}
	
	/**
	 * Checker -> Infra 연동 API
	 */
	/**
	 * 홈 IoT 서비스단말 상태조회
	 * @param key
	 * @return GetIoTEquipInfoResult
	 */
	public GetIoTEquipInfoResult getIoTEquipInfo(CheckerKey key) {
		
		GetIoTEquipInfoResult result = new GetIoTEquipInfoResult();
		
		List<IoTGWInfo> ioTGWInfos = checkerDao.getIotGwInfo(key);
		
		if(ioTGWInfos.size() < 1) {
			result.setResultCode("F");
			result.setResultMessage("홈허브 정보 없음.");
		} else {
			
			for(IoTGWInfo ioTGWInfo : ioTGWInfos) {
				
				key.setSvcTgtSeq(ioTGWInfo.getSvcTgtSeq());
				key.setSpotDevSeq(ioTGWInfo.getSpotDevSeq());
				
				List<IoTDeviceInfo> ioTDeviceInfos = checkerDao.getIotDeviceInfo(key);
				
				for(IoTDeviceInfo info : ioTDeviceInfos) {
					info.setDeviceBatteryStatus(Integer.toString(Integer.parseInt(info.getDeviceBatteryStatus(), 16)));
				}
				
				ioTGWInfo.setIoTDeviceInfos(ioTDeviceInfos);
				
			}
			
			result.setIoTGWInfos(ioTGWInfos);
			result.setResultMessage("성공");
			result.setResultCode("T");
		}
		
		return result;
	}
	
	/**
	 * Olleh통합ID 조회
	 * @param key
	 * @return GetOllehIDResult
	 */
	public GetOllehIDResult getOllehID(CheckerKey key) {
	    
	    List<GetOllehIDResult> ollehIDResultList = checkerDao.getOllehID(key);
	    
	    GetOllehIDResult ollehIDResult = new GetOllehIDResult();
	    ollehIDResult.setResultCode("F");
	    
	    if(ollehIDResultList.size() > 1){
	        ollehIDResult.setResultMessage("OllehID 여러개 존재");
	    }else if(ollehIDResultList.size() < 1){
	        ollehIDResult.setResultMessage("OllehID 존재하지 않음");
	    }else{
	    	if(StringUtils.isEmpty(ollehIDResultList.get(0).getOllehId())) {
		        ollehIDResult.setResultMessage("OllehID 존재하지 않음");
	    	} else {
		        ollehIDResult.setResultMessage("성공");
		        ollehIDResult.setResultCode("T");
		        ollehIDResult.setOllehId(ollehIDResultList.get(0).getOllehId());
	    	}
	    }
		return ollehIDResult;
	}

	/**
	 * IoT 접속단말 연결상태 요청
	 * @param req
	 * @return GetIoTEquipConnInfoResult
	 */
	public GetIoTEquipConnInfoResult getIoTEquipConnInfo(GetIoTEquipConnInfo req) {

		GetIoTEquipConnInfoResult result = new GetIoTEquipConnInfoResult();
		        
        try {
            // 2. openapi 서비스 연돋 요청
            InfraHttpClient infraHttpClient = infrafactory.getInfraHttpClient(ghmsServerInfo);
            
            try {
                
                String url = ghmsServerInfo.getProtocol() + "://" 
                        + ghmsServerInfo.getHost() + ":" 
                        + ghmsServerInfo.getPort() 
                        + CheckerApiCode.getIoTEquipConnInfo.getUrl(); 
                
                result = (GetIoTEquipConnInfoResult)infraHttpClient.send(url, req, new GetIoTEquipConnInfoResult());
            } catch (Exception e) {
                log.error(e.toString(), e.fillInStackTrace());
            }
            
        } catch(Exception e) {
            log.error(e.toString(), e.fillInStackTrace());
            e.printStackTrace();
            
            result.setResultCode("F");
            result.setResultMessage(e.getMessage());
            
            return result;
        }
		
		return result;
	}

	/**
	 * IoT 접속단말 연결상태 조회
	 * @param req
	 * @return GetIoTEquipConnInfoResponse
	 */
	public GetIoTEquipConnInfoResponse getIoTEquipConnInfoResponse(GetIoTEquipConnInfo req) {

		GetIoTEquipConnInfoResponse result = new GetIoTEquipConnInfoResponse();
		        
        try {
            // 2. openapi 서비스 연돋 요청
            InfraHttpClient infraHttpClient = infrafactory.getInfraHttpClient(ghmsServerInfo);
            
            try {
                
                String url = ghmsServerInfo.getProtocol() + "://" 
                        + ghmsServerInfo.getHost() + ":" 
                        + ghmsServerInfo.getPort() 
                        + CheckerApiCode.getIoTEquipConnInfoResponse.getUrl(); 
                
                result = (GetIoTEquipConnInfoResponse)infraHttpClient.send(url, req, new GetIoTEquipConnInfoResponse());
            } catch (Exception e) {
                log.error(e.toString(), e.fillInStackTrace());
            }
            
        } catch(Exception e) {
            log.error(e.toString(), e.fillInStackTrace());
            e.printStackTrace();
            
            result.setResultCode("F");
            result.setResultMessage(e.getMessage());
            
            return result;
        }
		
		return result;
	}
}
