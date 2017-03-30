package com.kt.giga.home.infra.service.ucems;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ws.soap.SoapFault;
import org.springframework.ws.soap.SoapFaultDetailElement;
import org.springframework.ws.soap.client.SoapFaultClientException;

import com.kt.giga.home.infra.client.host.UCEMSServerInfo;
import com.kt.giga.home.infra.client.http.InfraHttpClient;
import com.kt.giga.home.infra.client.http.InfraHttpClientFactory;
import com.kt.giga.home.infra.client.http.WebserviceHttpClient;
import com.kt.giga.home.infra.domain.commons.HttpObjectResponse;
import com.kt.giga.home.infra.domain.constants.ErrorCode;
import com.kt.giga.home.infra.domain.sdp.FaultInfo;
import com.kt.giga.home.infra.domain.ucems.ApplyNewFirmInfoRequest;
import com.kt.giga.home.infra.domain.ucems.ApplyNewFirmInfoResponse;
import com.kt.giga.home.infra.domain.ucems.GetNewFirmInfoRequest;
import com.kt.giga.home.infra.domain.ucems.GetNewFirmInfoResponse;
import com.kt.giga.home.infra.domain.ucems.StatusNewFirmInfoRequest;
import com.kt.giga.home.infra.domain.ucems.StatusNewFirmInfoResponse;
import com.kt.giga.home.infra.service.sdp.SDPApiCode;

@Service
public class UCEMSService {
	private Logger log = Logger.getLogger(this.getClass());
		
	@Autowired
	private InfraHttpClientFactory factory;
	
	@Autowired
	private UCEMSServerInfo serverInfo;
	
	public HttpObjectResponse get(GetNewFirmInfoRequest req) {
		// 01. 클라이언트 생성 
		InfraHttpClient infraHttpClient = factory.getInfraHttpClient(serverInfo);
		
		// 02. 올레 사용자 로그인 도메인 코드 고정값 설정
				
		// 03. 최신 펌웨어 정보 조회 요청 
		GetNewFirmInfoResponse res = null;
		try {
			String url = serverInfo.getProtocol() + "://" 
					+ serverInfo.getHost() + ":" 
					+ serverInfo.getPort() 
					+ UCEMSApiCode.GetNewFirm.getUrl(); 
			
			res = (GetNewFirmInfoResponse)infraHttpClient.send(url, req, new GetNewFirmInfoResponse());
		} catch (SoapFaultClientException e) {
			log.error(e.toString(), e.fillInStackTrace());
						
			if(res == null) res = new GetNewFirmInfoResponse();
			Map<String, String> map = parseFalutException(e.getSoapFault());
			
			res.getResultInfo().setResultCode(map.get("faultcode"));
			res.getResultInfo().setResultMsg(map.get("faultstring"));
		} catch (Exception e) {
			log.error(e.toString(), e.fillInStackTrace());
			
			if(res == null) res = new GetNewFirmInfoResponse();
			res.getResultInfo().setResultCode(ErrorCode.RESULT_ERROR);
			res.getResultInfo().setResultMsg(e.toString());
		}
		
		return res;
	}
	
	public HttpObjectResponse apply(ApplyNewFirmInfoRequest req) {
		// 01. 클라이언트 생성 
		InfraHttpClient infraHttpClient = factory.getInfraHttpClient(serverInfo);
		
		// 02. 올레 사용자 로그인 도메인 코드 고정값 설정
				
		// 03. 최신 펌웨어 정보 조회 요청 
		ApplyNewFirmInfoResponse res = null;
		try {
			String url = serverInfo.getProtocol() + "://" 
					+ serverInfo.getHost() + ":" 
					+ serverInfo.getPort() 
					+ UCEMSApiCode.ApplyNewFirm.getUrl(); 
			
			res = (ApplyNewFirmInfoResponse)infraHttpClient.send(url, req, new ApplyNewFirmInfoResponse());
		} catch (SoapFaultClientException e) {
			log.error(e.toString(), e.fillInStackTrace());
			
			if(res == null) res = new ApplyNewFirmInfoResponse();			
			Map<String, String> map = parseFalutException(e.getSoapFault());
			res.getResultInfo().setResultCode(map.get("faultcode"));
			res.getResultInfo().setResultMsg(map.get("faultstring"));
		} catch (Exception e) {
			log.error(e.toString(), e.fillInStackTrace());
			
			if(res == null) res = new ApplyNewFirmInfoResponse();
			res.getResultInfo().setResultCode(ErrorCode.RESULT_ERROR);
			res.getResultInfo().setResultMsg(e.toString());
		}
		
		return res;
	}
	
	public HttpObjectResponse status(StatusNewFirmInfoRequest req) {
		// 01. 클라이언트 생성 
		InfraHttpClient infraHttpClient = factory.getInfraHttpClient(serverInfo);
		
		// 02. 올레 사용자 로그인 도메인 코드 고정값 설정
				
		// 03. 최신 펌웨어 정보 조회 요청 
		StatusNewFirmInfoResponse res = null;
		try {
			String url = serverInfo.getProtocol() + "://" 
					+ serverInfo.getHost() + ":" 
					+ serverInfo.getPort() 
					+ UCEMSApiCode.StatusNewFirm.getUrl(); 
			
			res = (StatusNewFirmInfoResponse)infraHttpClient.send(url, req, new StatusNewFirmInfoResponse());
		} catch (SoapFaultClientException e) {
			log.error(e.toString(), e.fillInStackTrace());
			
			if(res == null) res = new StatusNewFirmInfoResponse();
			Map<String, String> map = parseFalutException(e.getSoapFault());
			res.getResultInfo().setResultCode(map.get("faultcode"));
			res.getResultInfo().setResultMsg(map.get("faultstring"));
		} catch (Exception e) {
			log.error(e.toString(), e.fillInStackTrace());
			
			if(res == null) res = new StatusNewFirmInfoResponse();
			res.getResultInfo().setResultCode(ErrorCode.RESULT_ERROR);
			res.getResultInfo().setResultMsg(e.toString());
		}
		
		return res;
	}
	
	private Map parseFalutException(SoapFault fault) {
		Map<String, String> map = new HashMap<String, String>();
		try {
			if(fault.getFaultDetail() != null && fault.getFaultDetail().getDetailEntries() != null) {
				Iterator<SoapFaultDetailElement> entries = fault.getFaultDetail().getDetailEntries();
				
				if(entries != null && entries.hasNext()) {
					WebserviceHttpClient infraHttpClient = (WebserviceHttpClient)factory.getInfraHttpClient(serverInfo);
					
					SoapFaultDetailElement faultDetailElement = (SoapFaultDetailElement)entries.next();				
					FaultInfo faultResponse = (FaultInfo)infraHttpClient.getWebServiceTemplate().getUnmarshaller().unmarshal(faultDetailElement.getSource());
					
					map.put("faultcode", faultResponse.getErrorCode());
					map.put("faultstring", faultResponse.getErrorDescription());
				}
			}
		} catch (Exception e) {
			log.error(e.toString(), e.fillInStackTrace());
		}
		
		return map;
	}
}
