/*******************************************************************************
 * Copyright(c) 2015 KT. All rights reserved.
 * This software is the proprietary information of KT.
 *******************************************************************************/
package com.kt.giga.home.openapi.ghms.ktInfra.service;

import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.httpclient.HostConfiguration;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.params.HttpClientParams;
import org.apache.commons.httpclient.params.HttpConnectionManagerParams;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.client.CommonsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.kt.giga.home.openapi.ghms.checker.domain.key.TimeSettingKey;
import com.kt.giga.home.openapi.ghms.checker.domain.vo.InternetAccessRule;
import com.kt.giga.home.openapi.ghms.ktInfra.domain.vo.GetInternetAccessControlResponse;
import com.kt.giga.home.openapi.ghms.ktInfra.domain.vo.GetInternetAccessControlResult;
import com.kt.giga.home.openapi.ghms.ktInfra.domain.vo.GetInternetSAIDResponse;
import com.kt.giga.home.openapi.ghms.ktInfra.domain.vo.GetInternetSAIDResult;
import com.kt.giga.home.openapi.ghms.ktInfra.domain.vo.GetNASStateInfoResponse;
import com.kt.giga.home.openapi.ghms.ktInfra.domain.vo.GetNASStateInfoResult;
import com.kt.giga.home.openapi.ghms.ktInfra.domain.vo.GetPCOnOffStateResponse;
import com.kt.giga.home.openapi.ghms.ktInfra.domain.vo.GetPCOnOffStateResult;
import com.kt.giga.home.openapi.ghms.ktInfra.domain.vo.GetServiceEquipInfoResponse;
import com.kt.giga.home.openapi.ghms.ktInfra.domain.vo.GetServiceEquipInfoResult;
import com.kt.giga.home.openapi.ghms.ktInfra.domain.vo.GetWakeOnLanListResponse;
import com.kt.giga.home.openapi.ghms.ktInfra.domain.vo.GetWakeOnLanListResult;
import com.kt.giga.home.openapi.ghms.ktInfra.domain.vo.SetDeviceTokenInfoResponse;
import com.kt.giga.home.openapi.ghms.ktInfra.domain.vo.SetDeviceTokenInfoResult;
import com.kt.giga.home.openapi.ghms.ktInfra.domain.vo.SetInternetAccessControlRecoveryResponse;
import com.kt.giga.home.openapi.ghms.ktInfra.domain.vo.SetInternetAccessControlRecoveryResult;
import com.kt.giga.home.openapi.ghms.ktInfra.domain.vo.SetInternetAccessControlResponse;
import com.kt.giga.home.openapi.ghms.ktInfra.domain.vo.SetInternetAccessControlResult;
import com.kt.giga.home.openapi.ghms.ktInfra.domain.vo.SetWakeOnLanResponse;
import com.kt.giga.home.openapi.ghms.ktInfra.domain.vo.SetWakeOnLanResult;
import com.kt.giga.home.openapi.ghms.user.domain.vo.UserVo;
import com.kt.giga.home.openapi.ghms.util.exception.APIException;
import com.kt.giga.home.openapi.ghms.util.json.JsonUtils;
import com.kt.giga.home.openapi.ghms.util.properties.APIEnv;


/**
 * 
 * @author dahye.kim (dahye.kim@ceinside.co.kr)
 * @since 2015. 2. 2.
 */
@Service
public class KTInfraService {

    /**
     * 로거
     */ 
    private Logger log = LoggerFactory.getLogger(getClass());

	private RestTemplate restTemplate;
	
	private final String SOURCE_SYSTEM_CD_ICIS = "01";

    /**
     * OpenAPI 환경 프라퍼티
     */     
    @Autowired
    private APIEnv env;

    /**
     * SDP 연동 인증 요청 메쏘드
     * OIF_127
     * 
     * @param userId            사용자 아이디
     * @param passwd            사용자 패스워드
     * @return                  SDP 로그인 처리 결과 맵
     * @throws APIException
     */
    @SuppressWarnings("unchecked")
    public HashMap<String, String> sendLoginRequest(String userId, String passwd) throws APIException {
        
        Map<String, String> params = new HashMap<String, String>();
        params.put("userId", userId);
        params.put("pw", passwd);
        
        String response = sendPostRequestToKTInfra(env.getProperty("ktinfra.url.sdp.login"), params);
        HashMap<String, Object> responseMap = JsonUtils.fromJson(response, new TypeToken<HashMap<String, Object>>(){}.getType());
        
        HashMap<String, String> resultMap = new HashMap<String, String>();
        resultMap.put("resultCode", String.valueOf(responseMap.get("resultCode")));
        resultMap.put("resultMsg", String.valueOf(responseMap.get("resultMsg")));
		resultMap.put("loginFailCounter", String.valueOf(responseMap.get("loginFailCounter")));

        Object credentialObject = responseMap.get("credentials");
        if(credentialObject != null) {
            ArrayList<HashMap<String, String>> credentialList = (ArrayList<HashMap<String, String>>)credentialObject;
            if(CollectionUtils.isNotEmpty(credentialList)) {
                resultMap.putAll(credentialList.get(0));    
            }
        }
        
        return resultMap;
    }

    /**
     * SDP 연동 청약 정보 요청 메쏘드
     * OIF_138
     * 
     * @param userId            사용자 아이디
     * @param credentialId      Credential ID
     * @return                  SDP 연동 사용자 객체
     * @throws APIException
     */
    public UserVo sendSubsRequest(String userId, String credentialId) throws APIException {
        
        Map<String, String> params = new HashMap<String, String>();
        params.put("userName", userId);
        params.put("credtId", credentialId);
        
        String response = sendPostRequestToKTInfra(env.getProperty("ktinfra.url.sdp.subs"), params);
        JsonObject responseJsonObject = JsonUtils.fromJson(response);

        UserVo user = new UserVo();
        user.setCredentialId(credentialId);
        
        // custId 찾기
        JsonArray partyMapList = responseJsonObject.getAsJsonArray("partyMap");
        
        if(partyMapList != null && partyMapList.size() > 0) {
            
            JsonObject firstPartyMap = partyMapList.get(0).getAsJsonObject();
            String birthDate = firstPartyMap.get("birthDate").getAsString();
            int age = getAgeFromBirth(birthDate);
            
            user.setBirth(birthDate);
            user.setAge(age);
            
            JsonArray innerPartyMapList = firstPartyMap.getAsJsonArray("partyMap");
            if(innerPartyMapList != null) {
                HashSet<String> custIdSet = new HashSet<String>();
                for(JsonElement innerParty : innerPartyMapList) {
                    custIdSet.add(innerParty.getAsJsonObject().get("sourceSystemBindId").getAsString());
                }
                
                user.setCustIdList(custIdSet.toArray(new String[0]));
            }
        }           

        return user;
    }

    /**
     * SDP 연동 청약 정보 요청 메쏘드(고도화용)
     * OIF_138
     * 
     * @param userId            사용자 아이디
     * @param credentialId      Credential ID
     * @return                  SDP 연동 사용자 객체
     * @throws APIException
     */
    public UserVo sendSubsRequestUpgrade(String userId, String credentialId) throws APIException {
        
        Map<String, String> params = new HashMap<String, String>();
        params.put("userName", userId);
        params.put("credtId", credentialId);
        
        String response = sendPostRequestToKTInfra(env.getProperty("ktinfra.url.sdp.subs"), params);
        JsonObject responseJsonObject = JsonUtils.fromJson(response);

        UserVo user = new UserVo();
        user.setCredentialId(credentialId);
        
        // custId 찾기
        JsonArray partyMapList = responseJsonObject.getAsJsonArray("partyMap");

		HashSet<String> custIdSet = new HashSet<String>();
		HashSet<String> icisSaIdSet = new HashSet<String>();
		
		if(partyMapList != null && partyMapList.size() > 0) {

			for(JsonElement partyElement : partyMapList) {
				JsonObject partyMap = partyElement.getAsJsonObject();

				String birthDate = "";
				int age = 0;
				
				if(partyMap.has("birthDate") && !"".equals(partyMap.get("birthDate").getAsString())) {
					birthDate = partyMap.get("birthDate").getAsString();
					age = getAgeFromBirth(birthDate);
					
					user.setBirth(birthDate);
					user.setAge(age);
				} else {
					birthDate = "1970101";
					age = getAgeFromBirth(birthDate);
					
					user.setBirth(birthDate);
					user.setAge(age);
				}
				
				if(partyMap.get("partyMap") != null && partyMap.get("partyMap").isJsonArray()) {
					
					JsonArray innerPartyMapList = partyMap.getAsJsonArray("partyMap");
					if(innerPartyMapList != null && innerPartyMapList.size() > 0) {
						for(JsonElement innerParty : innerPartyMapList) {
							if(SOURCE_SYSTEM_CD_ICIS.equals(innerParty.getAsJsonObject().get("sourceSystemCd").getAsString())) { // ICIS 오더만
								custIdSet.add(innerParty.getAsJsonObject().get("sourceSystemBindId").getAsString());
							}
						}
					}
				}
				
				if(partyMap.get("subscriptionInfo") != null && partyMap.get("subscriptionInfo").isJsonArray()) {
					
					JsonArray subscriptionList = partyMap.getAsJsonArray("subscriptionInfo");
					if(subscriptionList != null && subscriptionList.size() > 0) {
						for(JsonElement subscription : subscriptionList) {
							icisSaIdSet.add(subscription.getAsJsonObject().get("icisSaId").getAsString());
						}
					}
				}
			}
			
			user.setCustIdList(custIdSet.toArray(new String[0]));
			user.setIcisSaIdList(icisSaIdSet.toArray(new String[0]));
		}          

        return user;
    }

    /**
     * SDP 특정 계약 정보 요청 메쏘드
     * OIF_114
     * 
     * @param credentialId      ICISSA_ID
     * @return                  SA_ID 리스트
     * @throws APIException
     */
    public List<String> sendSpecificSubscpnInfo(String icisSaId) throws APIException {
        
        Map<String, String> params = new HashMap<String, String>();
        params.put("subscpnSourceTypeCd", "02");
        params.put("subscpnSourceValue", icisSaId);
        
        String response = sendPostRequestToKTInfra(env.getProperty("ktinfra.url.sdp.spec.subs"), params);
        JsonObject responseJsonObject = JsonUtils.fromJson(response);

        List<String> list = new ArrayList<String>();
        
        // custId 찾기
        JsonArray subscpnInfos = responseJsonObject.getAsJsonArray("subscpnInfos");
        
        if(subscpnInfos != null && subscpnInfos.size() > 0) {
        	
        	for(JsonElement subscpnInfo : subscpnInfos) {
        		
        		JsonObject object = subscpnInfo.getAsJsonObject();
        		String rtnIcisSaId = object.get("icisSaId").getAsString();
        		
        		JsonArray array = object.getAsJsonArray("subscpnInfoSSVs");
        		
        		for(JsonElement subscpnInfoSSV : array) {
            		
        			String vasCd = subscpnInfoSSV.getAsJsonObject().get("vasCd").getAsString();
        			// GiGA WiFi home (4V19)
        			if("4V19".equals(vasCd)) {
        				list.add(rtnIcisSaId);
        			}
        			
        		}
        	}
        }           

        return list;
    }

    /**
     * SDP 특정 계약 정보 요청 메쏘드
     * OIF_115
     * 
     * @param credentialId      ICISSA_ID
     * @return                  SA_ID 리스트
     * @throws APIException
     */
    public Map<String, Object> sendSpecificSubsAndUserInfo(String serviceNo) throws APIException {
        
        Map<String, String> params = new HashMap<String, String>();
        params.put("credentialTypeCd", "11");
        params.put("userName", serviceNo);
        
        String response = sendPostRequestToKTInfra(env.getProperty("ktinfra.url.sdp.spec.subs.user.info"), params);
        Map<String, Object> responseJson = JsonUtils.fromJson(response, Map.class);
        
        log.debug(responseJson.toString());
        
        return responseJson;
    }

    /**
     * SDP 연동 스마트 체커 장치 조회
     * 
     * @param svcSaId            서비스 계약 아이디
     * @return
     * @throws APIException
     */
    public GetServiceEquipInfoResult sendGetServiceEquipInfo(String userId, String deviceId, String apMac, String svcSaId) {
        
    	GetServiceEquipInfoResult equipInfoResult = null;
    			
    	try {
            Map<String, String> params = new HashMap<String, String>();
            params.put("userID", userId);
            params.put("uuID", deviceId);
            params.put("inType", "1");
            params.put("inValue", svcSaId);
            
            String response = sendPostRequestToKTInfra(env.getProperty("ktinfra.url.sdp.getServiceEquipInfo"), params);
            GetServiceEquipInfoResponse equipResponse = JsonUtils.fromJson(response, new TypeToken<GetServiceEquipInfoResponse>(){}.getType());
            
            equipInfoResult = equipResponse.getGetServiceEquipInfoResult();	
    	} catch (Exception ex) {
    		equipInfoResult = new GetServiceEquipInfoResult();
    		equipInfoResult.setResultCode("F");
    		equipInfoResult.setResultMessage("SmartChecker Server Connection Error");
    		log.error("SmartChecker Server Connection Error");
    		ex.printStackTrace();
    	}
        
        return equipInfoResult;
    }

    /**
     * SDP 연동 스마트 체커 AP 인터넷 이용제한 목록 조회
     * 
     * @param svcSaId            서비스 계약 아이디
     * @return
     * @throws APIException
     */
    public GetInternetAccessControlResult getInternetAccessControl(String userId, String deviceId, String apMac, String hostMac) {
        
    	GetInternetAccessControlResult getInternetAccessControlResult = null;
    	
    	try {
            Map<String, String> params = new HashMap<String, String>();
            params.put("userID", userId);
            params.put("uuID", deviceId);
            params.put("apMacAddress", apMac.replaceAll(":", ""));
            params.put("hostMacAddress", hostMac);
            
            String response = sendPostRequestToKTInfra(env.getProperty("ktinfra.url.sdp.getInternetAccessControl"), params);
            GetInternetAccessControlResponse internetAccessControlResponse = JsonUtils.fromJson(response, new TypeToken<GetInternetAccessControlResponse>(){}.getType());
            
            getInternetAccessControlResult = internetAccessControlResponse.getGetInternetAccessControlResult();	
    	} catch (Exception ex) {
    		getInternetAccessControlResult = new GetInternetAccessControlResult();
    		getInternetAccessControlResult.setResultCode("F");
    		getInternetAccessControlResult.setResultMessage("SmartChecker Server Connection Error");
    		log.error("SmartChecker Server Connection Error");
    		ex.printStackTrace();
    	}
        
        return getInternetAccessControlResult;
    }

    /**
     * SDP 연동 스마트 체커 AP 인터넷 이용제한 룰관리
     * 
     * @param svcSaId            서비스 계약 아이디
     * @return
     * @throws APIException
     */
    public SetInternetAccessControlResult setInternetAccessControl(String userId, String deviceId, TimeSettingKey key) {
        
    	SetInternetAccessControlResult setInternetAccessControlResult = null;
    	
    	try {
            Map<String, String> params = new HashMap<String, String>();
            params.put("userID", userId);
            params.put("uuID", deviceId);
            params.put("apMacAddress", key.getGigaAPMac().replaceAll(":", ""));
            
            List<InternetAccessRule> internetAccessRules = key.getInternetAccessRules();
            
            for(InternetAccessRule rule : internetAccessRules) {
                params.put("hostMacAdderss", rule.getInternetAccessControlMac());
                params.put("crudFlag", rule.getCrudFlag());
                params.put("index", rule.getIndex());
                params.put("enable", rule.getInternetAccessRuleEnable());
                params.put("description", rule.getInternetAccessRuleName());
                params.put("weekly", rule.getInternetAccessDate());
                params.put("startTime", rule.getInternetAccessStartTime().replaceAll(":", ""));
                params.put("endTime", rule.getInternetAccessEndTime().replaceAll(":", ""));
                
                break; // 1건만 처리함.
            }
            
            String response = sendPostRequestToKTInfra(env.getProperty("ktinfra.url.sdp.setInternetAccessControl"), params);
            SetInternetAccessControlResponse internetAccessControlResponse = JsonUtils.fromJson(response, new TypeToken<SetInternetAccessControlResponse>(){}.getType());
            
            setInternetAccessControlResult = internetAccessControlResponse.getSetInternetAccessControlResult();
    	} catch (Exception ex) {
    		setInternetAccessControlResult = new SetInternetAccessControlResult();
    		setInternetAccessControlResult.setResultCode("F");
    		setInternetAccessControlResult.setResultMessage("SmartChecker Server Connection Error");
    		log.error("SmartChecker Server Connection Error");
    		ex.printStackTrace();
    	}
        
        return setInternetAccessControlResult;
    }

    /**
     * SDP 연동 스마트 체커 AP 인터넷 이용제한 룰 복구
     * 
     * @param svcSaId            서비스 계약 아이디
     * @return
     * @throws APIException
     */
    public SetInternetAccessControlRecoveryResult setInternetAccessControlRecovery(String userId, String deviceId, String apMacAddress) {
        
    	SetInternetAccessControlRecoveryResult setInternetAccessControlRecoveryResult = null;
    	
    	try {
        	
            Map<String, String> params = new HashMap<String, String>();
            params.put("userID", userId);
            params.put("uuID", deviceId);
            params.put("apMacAddress", apMacAddress.replaceAll(":", ""));
            
            String response = sendPostRequestToKTInfra(env.getProperty("ktinfra.url.sdp.setInternetAccessControlRecovery"), params);
            SetInternetAccessControlRecoveryResponse setInternetAccessControlRecoveryResponse = JsonUtils.fromJson(response, new TypeToken<SetInternetAccessControlRecoveryResponse>(){}.getType());
            
            setInternetAccessControlRecoveryResult = setInternetAccessControlRecoveryResponse.getSetInternetAccessControlRecoveryResult();
    	} catch (Exception ex) {
    		setInternetAccessControlRecoveryResult = new SetInternetAccessControlRecoveryResult();
    		setInternetAccessControlRecoveryResult.setResultCode("F");
    		setInternetAccessControlRecoveryResult.setResultMessage("SmartChecker Server Connection Error");
    		log.error("SmartChecker Server Connection Error");
    		ex.printStackTrace();
    	}
        
        return setInternetAccessControlRecoveryResult;
    }

    /**
     * SDP 연동 스마트 체커 NAS 상태조회
     * 
     * @param svcSaId            서비스 계약 아이디
     * @return
     * @throws APIException
     */
    public GetNASStateInfoResult getNASStateInfo(String userId, String deviceId, String apMacAddress) {
        
    	GetNASStateInfoResult getNASStateInfoResult = null;
    	
    	try {
        	
            Map<String, String> params = new HashMap<String, String>();
            params.put("userID", userId);
            params.put("uuID", deviceId);
            params.put("apMacAddress", apMacAddress.replaceAll(":", ""));
            
            String response = sendPostRequestToKTInfra(env.getProperty("ktinfra.url.sdp.getNASStateInfo"), params);
            GetNASStateInfoResponse getNASStateInfoResponse = JsonUtils.fromJson(response, new TypeToken<GetNASStateInfoResponse>(){}.getType());
            
            getNASStateInfoResult = getNASStateInfoResponse.getGetNASStateInfoResult();
    	} catch (Exception ex) {
    		getNASStateInfoResult = new GetNASStateInfoResult();
    		getNASStateInfoResult.setResultCode("F");
    		getNASStateInfoResult.setResultMessage("SmartChecker Server Connection Error");
    		log.error("SmartChecker Server Connection Error");
    		ex.printStackTrace();
    	}
        
        return getNASStateInfoResult;
    }

    /**
     * SDP 연동 스마트 체커 PC 상태 조회
     * 
     * @param svcSaId            서비스 계약 아이디
     * @return
     * @throws APIException
     */
    public GetPCOnOffStateResult getPCOnOffState(String userId, String deviceId, String apMacAddress, String pcMacAddress) {
        
    	GetPCOnOffStateResult getPCOnOffStateResult = null;
    	
    	try {
        	
            Map<String, String> params = new HashMap<String, String>();
            params.put("userID", userId);
            params.put("uuID", deviceId);
            params.put("apMacAddress", apMacAddress.replaceAll(":", ""));
            params.put("pcMacAddress", pcMacAddress);
            
            String response = sendPostRequestToKTInfra(env.getProperty("ktinfra.url.sdp.getPCOnOffState"), params);
            GetPCOnOffStateResponse getPCOnOffStateResponse = JsonUtils.fromJson(response, new TypeToken<GetPCOnOffStateResponse>(){}.getType());
            
            getPCOnOffStateResult = getPCOnOffStateResponse.getGetPCOnOffStateResult();
    	} catch (Exception ex) {
    		getPCOnOffStateResult = new GetPCOnOffStateResult();
    		getPCOnOffStateResult.setResultCode("F");
    		getPCOnOffStateResult.setResultMessage("SmartChecker Server Connection Error");
    		log.error("SmartChecker Server Connection Error");
    		ex.printStackTrace();
    	}
        
        return getPCOnOffStateResult;
    }

    /**
     * SDP 연동 스마트 체커 PC 원격 켜기
     * 
     * @param svcSaId            서비스 계약 아이디
     * @return
     * @throws APIException
     */
    public SetWakeOnLanResult setWakeOnLan(String userId, String deviceId, String apMacAddress, String pcMacAddress) {
        
    	SetWakeOnLanResult setWakeOnLanResult = null;
    	
    	try {
        	
            Map<String, String> params = new HashMap<String, String>();
            params.put("userID", userId);
            params.put("uuID", deviceId);
            params.put("apMacAddress", apMacAddress.replaceAll(":", ""));
            params.put("pcMacAddress", pcMacAddress);
            
            String response = sendPostRequestToKTInfra(env.getProperty("ktinfra.url.sdp.setWakeOnLan"), params);
            SetWakeOnLanResponse setWakeOnLanResponse = JsonUtils.fromJson(response, new TypeToken<SetWakeOnLanResponse>(){}.getType());
            
            setWakeOnLanResult = setWakeOnLanResponse.getSetWakeOnLanResult();
    	} catch (Exception ex) {
    		setWakeOnLanResult = new SetWakeOnLanResult();
    		setWakeOnLanResult.setResultCode("F");
    		setWakeOnLanResult.setResultMessage("SmartChecker Server Connection Error");
    		log.error("SmartChecker Server Connection Error");
    		ex.printStackTrace();
    	}
        
        return setWakeOnLanResult;
    }

    /**
     * SDP 연동 스마트 체커 PC 원격 켜기 목록조회
     * 
     * @param svcSaId            서비스 계약 아이디
     * @return
     * @throws APIException
     */
    public GetWakeOnLanListResult getWakeOnLanList(String userId, String deviceId, String apMacAddress) {
        
    	GetWakeOnLanListResult getWakeOnLanListResult = null;
    	
    	try {
        	
            Map<String, String> params = new HashMap<String, String>();
            params.put("userID", userId);
            params.put("uuID", deviceId);
            params.put("apMacAddress", apMacAddress.replaceAll(":", ""));
            
            String response = sendPostRequestToKTInfra(env.getProperty("ktinfra.url.sdp.getWakeOnLanList"), params);
            GetWakeOnLanListResponse getWakeOnLanListResponse = JsonUtils.fromJson(response, new TypeToken<GetWakeOnLanListResponse>(){}.getType());
            
            getWakeOnLanListResult = getWakeOnLanListResponse.getGetWakeOnLanListResult();
    	} catch (Exception ex) {
    		getWakeOnLanListResult = new GetWakeOnLanListResult();
    		getWakeOnLanListResult.setResultCode("F");
    		getWakeOnLanListResult.setResultMessage("SmartChecker Server Connection Error");
    		log.error("SmartChecker Server Connection Error");
    		ex.printStackTrace();
    	}
        
        return getWakeOnLanListResult;
    }

    /**
     * SDP 연동 스마트 체커 Push ID 전송
     * 
     * @param userId
     * @param deviceId
     * @param macAddress
     * @param deviceType
     * @param token
     * @param pushYn
     * @param optional
     * @return
     */
    public SetDeviceTokenInfoResult setDeviceTokenInfo(String userId, String deviceId, String macAddress, String deviceType, String token, String pushYn, String optional) {
        
    	SetDeviceTokenInfoResult setDeviceTokenInfoResult = null;
    	
    	try {
        	
            Map<String, String> params = new HashMap<String, String>();
            params.put("userID", userId);
            params.put("uuID", deviceId);
            params.put("macAddress", macAddress);
            params.put("deviceType", deviceType);
            params.put("token", token);
            params.put("pushYn", pushYn);
            params.put("optional", optional);
            
            String response = sendPostRequestToKTInfra(env.getProperty("ktinfra.url.sdp.setDeviceTokenInfo"), params);
            SetDeviceTokenInfoResponse setDeviceTokenInfoResponse = JsonUtils.fromJson(response, new TypeToken<SetDeviceTokenInfoResponse>(){}.getType());
            
            setDeviceTokenInfoResult = setDeviceTokenInfoResponse.getSetDeviceTokenInfoResult();
    	} catch (Exception ex) {
    		setDeviceTokenInfoResult = new SetDeviceTokenInfoResult();
    		setDeviceTokenInfoResult.setResultCode("F");
    		setDeviceTokenInfoResult.setResultMessage("SmartChecker Server Connection Error");
    		log.error("SmartChecker Server Connection Error");
    		ex.printStackTrace();
    	}
        
        return setDeviceTokenInfoResult;
    }

    /**
     * SDP 연동 스마트 체커 서비스계약ID 조회
     * 
     * @param apMac            AP 맥주소
     * @return
     * @throws APIException
     */
    public GetInternetSAIDResult getInternetSAID(String userId, String deviceId, String apMac) {
        
    	GetInternetSAIDResult getInternetSAIDResult = null;
    	
    	try {
            Map<String, String> params = new HashMap<String, String>();
            params.put("userID", userId);
            params.put("uuID", deviceId);
            params.put("apMacAddress", apMac.replaceAll(":", ""));
            
            String response = sendPostRequestToKTInfra(env.getProperty("ktinfra.url.sdp.getInternetSAID"), params);
            GetInternetSAIDResponse getInternetSAIDResponse = JsonUtils.fromJson(response, new TypeToken<GetInternetSAIDResponse>(){}.getType());
            
            getInternetSAIDResult = getInternetSAIDResponse.getGetInternetSAIDResult();
    	} catch (Exception ex) {
    		getInternetSAIDResult = new GetInternetSAIDResult();
    		getInternetSAIDResult.setResultCode("F");
    		getInternetSAIDResult.setResultMessage("SmartChecker Server Connection Error");
    		log.error("SmartChecker Server Connection Error");
    		ex.printStackTrace();
    	}
        
        return getInternetSAIDResult;
    }

    /**
     * KT 인프라 서버로 HTTP Post 요청 메쏘드
     * 
     * @param url               URL
     * @param o                 요청 오브젝트
     * @return                  처리 결과 스트링
     */
    private String sendPostRequestToKTInfra(String url, Object o) {

        String result = null;
        try {
            String json = toJson(o);
            
            HttpHeaders headers = new HttpHeaders();
//            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));

            HttpEntity<String> entity = new HttpEntity<String>(json, headers);

			LinkedList<String> list = getRandomKtInfraServer();
			while(!list.isEmpty()) {
				String fullUrl = env.getProperty("ktinfra.protocol") + "://" + list.get(0) + ":" + env.getProperty("ktinfra.port") + url;
				list.remove(0);

				try	{
					log.info("# Send Request to KTInfra : {}\n{}", fullUrl, json);
					result = getKTInfraRestTemplate().postForObject(fullUrl, entity, String.class);
					log.info("# Recv Response from KTInfra : {}\n{}", fullUrl, JsonUtils.toPrettyJson(result));
					break;
				} catch(ResourceAccessException e) {
					log.warn(e.getMessage(), e.fillInStackTrace());
				} catch(HttpClientErrorException e) {
					log.warn(e.getMessage(), e.fillInStackTrace());
				}
			}
        } catch(Exception e) {
            
            log.warn(e.getMessage(), e);
            throw e;
        }
        
        return result;
    }
    
    /**
     * KT 인프라 서버 HTTP 연동 위한 RestTemplate 생성 메쏘드
     * 
     * @return                  RestTemplate
     */
    private RestTemplate getKTInfraRestTemplate() {
		
		if(restTemplate == null) {
			
			try {

				int readTimeout = env.getIntProperty("ktinfra.timeout.read");
				int connTimeout = env.getIntProperty("ktinfra.timeout.conn");
				
				HttpClientParams httpClientParams = new HttpClientParams();
//				httpClientParams.setConnectionManagerTimeout(connTimeout);
//				httpClientParams.setSoTimeout(readTimeout);
				
				HttpConnectionManagerParams connectionManagerParams = new HttpConnectionManagerParams();
				connectionManagerParams.setMaxConnectionsPerHost(HostConfiguration.ANY_HOST_CONFIGURATION, env.getIntProperty("ktinfra.conn.host"));
				connectionManagerParams.setMaxTotalConnections(env.getIntProperty("ktinfra.conn.max"));
				
				MultiThreadedHttpConnectionManager connectionManager = new MultiThreadedHttpConnectionManager();
				connectionManager.setParams(connectionManagerParams);
				
				CommonsClientHttpRequestFactory factory = new CommonsClientHttpRequestFactory(new HttpClient(httpClientParams, connectionManager));
				restTemplate = new RestTemplate(factory);
				
			} catch(Exception e) {
				
				log.warn(e.getMessage(), e);
			}			
		}
		
		return restTemplate;
    }   
    
    /**
     * JSON Serialize 메쏘드
     * 
     * @param o                 오브젝트
     * @return                  JSON 스트링
     */
    private String toJson(Object o) {
        return new GsonBuilder().setPrettyPrinting().create().toJson(o);
    }   
    
    /**
     * SDP 생년월일로 만 나이를 계산하는 메쏘드
     * 
     * @param birthYYYYMMDD     생년월일
     * @return                  만 나이
     */
    private int getAgeFromBirth(String birthYYYYMMDD) {

        int age = 0;
        
        try {
            Calendar birth = new GregorianCalendar();
            birth.setTime(new SimpleDateFormat("yyyyMMdd").parse(birthYYYYMMDD));
            
            Calendar now = Calendar.getInstance();
            age = now.get(Calendar.YEAR) - birth.get(Calendar.YEAR);  

            // 생일이 안지났으면 -1을 한번더
            if(now.get(Calendar.DAY_OF_YEAR) < birth.get(Calendar.DAY_OF_YEAR)) {
                age--;
            }
            
        } catch(Exception e) {
            log.warn(e.getMessage(), e);
        }

        return age;
    }


    /**
     * 펌웨어 최신 버전 조회 요청 메쏘드
     *
     * @param secret            시크릿
     * @param modelCode         모델 코드
     * @return                  펌웨어 최신 버전 스트링
     * @throws APIException
     */
    public String sendFirmVersionCheckRequest(String secret, String modelCode) throws APIException {

        Map<String, String> params = new HashMap<String, String>();
        params.put("secret", secret);
        params.put("modelCode", modelCode);

        String response = sendPostRequestToKTInfra(env.getProperty("ktinfra.url.ucems.get"), params);
        JsonObject responseJsonObject = JsonUtils.fromJson(response);

        JsonObject resultInfo = responseJsonObject.getAsJsonObject("resultInfo");

        if(resultInfo == null) {
            throw new APIException("## sendGhmsHUBFirmUpCheckRequest resultInfo is null.", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        String resultCode = resultInfo.get("resultCode").getAsString();
        if(!StringUtils.equalsIgnoreCase(resultCode, "OK")) {
//            throw new APIException("## sendGhmsHUBFirmUpCheckRequest resultCode is NOT OK.", HttpStatus.INTERNAL_SERVER_ERROR);
            return "x.x.x";
        }

        return resultInfo.get("newVersion").getAsString();
    }

    /**
     * 펌웨어 업그레이드 요청 메쏘드
     *
     * @param secret            시크릿
     * @param mac               맥 주소
     * @param spotDevId         게이트웨이 SAID
     * @throws APIException
     */
    public Map<String, String> sendGwFirmUpRequest(String secret, String mac , String spotDevId) throws APIException {
        
        Map<String, String> params = new HashMap<String, String>();
        params.put("secret", secret);
        params.put("mac", mac);
        params.put("cameraSaid", spotDevId);
        
        String response = sendPostRequestToKTInfra(env.getProperty("ktinfra.url.ucems.apply"), params);
        JsonObject responseJsonObject = JsonUtils.fromJson(response);
        
        JsonObject resultInfo = responseJsonObject.getAsJsonObject("resultInfo");
        
        if(resultInfo == null) {
            throw new APIException("## sendCameraFirmUpRequest resultInfo is null.", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        String resultCode = resultInfo.get("resultCode").getAsString();
        String resultMsg = resultInfo.get("resultMsg").getAsString();
        String requestDT = resultInfo.get("requestDT").getAsString();
        String newVersion = "";
        if(!StringUtils.equalsIgnoreCase(resultCode, "OK")) {
            throw new APIException(String.format("## sendCameraFirmUpRequest resultCode is NOT OK :", resultMsg), HttpStatus.INTERNAL_SERVER_ERROR);
        } else {
            newVersion = resultInfo.get("newVersion").getAsString();
        }
        
        Map<String, String> map = new HashMap<String, String>();
        map.put("requestDT", requestDT);
        map.put("newVersion", newVersion);
        
        return map;
    }

	// 인프라 서버 개수만큼의 서버IP목록을 순서 섞어서 리턴
	public LinkedList<String> getRandomKtInfraServer() {
		LinkedList<String> list = new LinkedList<String>();
		String[] ktinfraServerList = env.splitProperty("ktinfra.serverList");

		for(int i = 0; i < ktinfraServerList.length; i++) {
			list.add(ktinfraServerList[i]);
		}

		Collections.shuffle(list);

		return list;
	}

}
