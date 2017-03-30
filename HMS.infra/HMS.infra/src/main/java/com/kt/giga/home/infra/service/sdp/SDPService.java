package com.kt.giga.home.infra.service.sdp;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ws.soap.SoapFault;
import org.springframework.ws.soap.SoapFaultDetailElement;
import org.springframework.ws.soap.client.SoapFaultClientException;

import com.kt.giga.home.infra.client.host.ServerInfo;
import com.kt.giga.home.infra.client.http.InfraHttpClient;
import com.kt.giga.home.infra.client.http.InfraHttpClientFactory;
import com.kt.giga.home.infra.client.http.WebserviceHttpClient;
import com.kt.giga.home.infra.domain.commons.HttpObjectResponse;
import com.kt.giga.home.infra.domain.constants.ErrorCode;
import com.kt.giga.home.infra.domain.sdp.FaultInfo;
import com.kt.giga.home.infra.domain.sdp.LoginInfoRequest;
import com.kt.giga.home.infra.domain.sdp.LoginInfoResponse;
import com.kt.giga.home.infra.domain.sdp.PartyAndSubInfoRequest;
import com.kt.giga.home.infra.domain.sdp.PartyAndSubInfoResponse;
import com.kt.giga.home.infra.domain.sdp.SpecificSubsAndUserInfoRequest;
import com.kt.giga.home.infra.domain.sdp.SpecificSubsAndUserInfoResponse;
import com.kt.giga.home.infra.domain.sdp.SpecificSubscpnInfoRequest;
import com.kt.giga.home.infra.domain.sdp.SpecificSubscpnInfoResponse;

@Service
public class SDPService {
	private Logger log = Logger.getLogger(this.getClass());
	
	public static final String SDP_DOMAIN_NAME_CD_OLLEH = "2";
	public static final String SDP_CREDENTIAL_TYPE_CD_OLLEH = "01";
	public static final String SDP_SUBSCPN_TYPE_CD_OLLEH = "09";
	public static final String SDP_RESULT_ERROR = "0";
	public static final String SDP_RESULT_SUCCESS = "1";
	
	@Autowired
	private InfraHttpClientFactory factory;
	
//	@Autowired
//	private SDPServerInfo serverInfo;

	public HttpObjectResponse login(LoginInfoRequest req, ServerInfo serverInfo) {
		// 01. 클라이언트 생성 
		InfraHttpClient infraHttpClient = factory.getInfraHttpClient(serverInfo);
		
		// 02. 로그인 요청 기본값 설정
		req.setDomainNameCd(SDP_DOMAIN_NAME_CD_OLLEH);
		
		// 03. OIF_127(올레 사용자 로그인) 요청 
		LoginInfoResponse res = null;
		try {
			String url = serverInfo.getProtocol() + "://" 
					+ serverInfo.getHost() + ":" 
					+ serverInfo.getPort() 
					+ SDPApiCode.authenticateUserByIdPwdForOlleh.getUrl(); 
			
			res = (LoginInfoResponse)infraHttpClient.send(url, req, new LoginInfoResponse());
		} catch (SoapFaultClientException e) {
			if(res == null) res = new LoginInfoResponse();
			Map<String, String> map = parseFaultException(e.getSoapFault(), serverInfo);
			res.setResultCode(map.get("code"));
			res.setResultMsg(map.get("msg"));
		} catch (Exception e) {
			if(res == null) res = new LoginInfoResponse();
			res.setResultCode(ErrorCode.RESULT_ERROR);
			res.setResultMsg(e.toString());
			
			log.error(e.toString(), e.fillInStackTrace());
		}
		
		// 정상적인 응답이나 ERRORDETAIL 로 에러결과가 리턴되는 경우
		if(SDP_RESULT_ERROR.equals(res.getResultCode())) {
			res.setResultCode(res.getErrorDetailInfo().getErrorCode());
			res.setResultMsg(res.getErrorDetailInfo().getErrorDescription());
		}
		
		return res;
	}
	
	public HttpObjectResponse subscription(PartyAndSubInfoRequest req, ServerInfo serverInfo) {
		// 01. 클라이언트 생성 
		InfraHttpClient infraHttpClient = factory.getInfraHttpClient(serverInfo);
		
		// 02. 청약 서비스 코드 기본값 설정
		req.setSubscpnTypeCd("99"); // 계약이 아닌 고객 cust_id 조회 인 경우
				
		// 03. OIF_138 호출
		PartyAndSubInfoResponse res = null;		
		try {
			String url = serverInfo.getProtocol() + "://" 
					+ serverInfo.getHost() + ":" 
					+ serverInfo.getPort() 
					+ SDPApiCode.getPartyAndSubInfoBySubTypeCD.getUrl(); 
			
			res = (PartyAndSubInfoResponse)infraHttpClient.send(url, req, new PartyAndSubInfoResponse());
		} catch (SoapFaultClientException e) {
			if(res == null) res = new PartyAndSubInfoResponse();
			Map<String, String> map = parseFaultException(e.getSoapFault(), serverInfo);
			res.setResultCode(map.get("code"));
			res.setResultMsg(map.get("msg"));
		} catch (Exception e) {
			if(res == null) res = new PartyAndSubInfoResponse();
			res.setResultCode(ErrorCode.RESULT_ERROR);
			res.setResultMsg(e.toString());
			
			log.error(e.toString(), e.fillInStackTrace());
		}
			
		// 정상적인 응답이나 ERRORDETAIL 로 에러결과가 리턴되는 경우
		if(SDP_RESULT_ERROR.equals(res.getResultCode())) {
			res.setResultCode(res.getErrorDetailInfo().getErrorCode());
			res.setResultMsg(res.getErrorDetailInfo().getErrorDescription());
		}
		
		return res;
	}
	
	public HttpObjectResponse getSpecificSubscpnInfo(SpecificSubscpnInfoRequest req, ServerInfo serverInfo) {
		// 01. 클라이언트 생성 
		InfraHttpClient infraHttpClient = factory.getInfraHttpClient(serverInfo);
		
		// 02. OIF_114 호출
		SpecificSubscpnInfoResponse res = null;		
		try {
			String url = serverInfo.getProtocol() + "://" 
					+ serverInfo.getHost() + ":" 
					+ serverInfo.getPort() 
					+ SDPApiCode.getSpecificSubscpnInfo.getUrl(); 
			
			res = (SpecificSubscpnInfoResponse)infraHttpClient.send(url, req, new SpecificSubscpnInfoResponse());
		} catch (SoapFaultClientException e) {
			if(res == null) res = new SpecificSubscpnInfoResponse();
			Map<String, String> map = parseFaultException(e.getSoapFault(), serverInfo);
			res.setResultCode(map.get("code"));
			res.setResultMsg(map.get("msg"));
		} catch (Exception e) {
			if(res == null) res = new SpecificSubscpnInfoResponse();
			res.setResultCode(ErrorCode.RESULT_ERROR);
			res.setResultMsg(e.toString());
			
			log.error(e.toString(), e.fillInStackTrace());
		}
		return res;
	}
	
	public HttpObjectResponse getSpecificSubsAndUserInfo(SpecificSubsAndUserInfoRequest req, ServerInfo serverInfo) {
		// 01. 클라이언트 생성 
		InfraHttpClient infraHttpClient = factory.getInfraHttpClient(serverInfo);
		
		// 02. OIF_115 호출
		SpecificSubsAndUserInfoResponse res = null;		
		try {
			String url = serverInfo.getProtocol() + "://" 
					+ serverInfo.getHost() + ":" 
					+ serverInfo.getPort() 
					+ SDPApiCode.getSpecificSubsAndUserInfo.getUrl(); 
			
			res = (SpecificSubsAndUserInfoResponse)infraHttpClient.send(url, req, new SpecificSubsAndUserInfoResponse());
		} catch (SoapFaultClientException e) {
			if(res == null) res = new SpecificSubsAndUserInfoResponse();
			Map<String, String> map = parseFaultException(e.getSoapFault(), serverInfo);
			res.setResultCode(map.get("code"));
			res.setResultMsg(map.get("msg"));
		} catch (Exception e) {
			if(res == null) res = new SpecificSubsAndUserInfoResponse();
			res.setResultCode(ErrorCode.RESULT_ERROR);
			res.setResultMsg(e.toString());
			
			log.error(e.toString(), e.fillInStackTrace());
		}
		return res;
	}
	
	private Map parseFaultException(SoapFault fault, ServerInfo serverInfo) {
		Map<String, String> map = new HashMap<String, String>();
		try {
			if(fault.getFaultDetail() != null && fault.getFaultDetail().getDetailEntries() != null) {
				Iterator<SoapFaultDetailElement> entries = fault.getFaultDetail().getDetailEntries();
				
				if(entries != null && entries.hasNext()) {
					WebserviceHttpClient infraHttpClient = (WebserviceHttpClient)factory.getInfraHttpClient(serverInfo);
					
					SoapFaultDetailElement faultDetailElement = (SoapFaultDetailElement)entries.next();				
					FaultInfo faultResponse = (FaultInfo)infraHttpClient.getWebServiceTemplate().getUnmarshaller().unmarshal(faultDetailElement.getSource());
					
					map.put("code", faultResponse.getErrorCode());
					map.put("msg", faultResponse.getErrorDescription());
				}
			}
		} catch (Exception e) {
			log.error(e.toString(), e.fillInStackTrace());
		}
		
		return map;
	}
}
