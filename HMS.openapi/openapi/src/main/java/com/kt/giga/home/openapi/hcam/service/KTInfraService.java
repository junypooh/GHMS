package com.kt.giga.home.openapi.hcam.service;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.kt.giga.home.openapi.common.APIEnv;
import com.kt.giga.home.openapi.hcam.HCamConstants.ErrorCode;
import com.kt.giga.home.openapi.hcam.HCamConstants.SpotDevItemVal;
import com.kt.giga.home.openapi.hcam.domain.User;
import com.kt.giga.home.openapi.service.APIException;
import com.kt.giga.home.openapi.util.JsonUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.httpclient.HostConfiguration;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.params.HttpClientParams;
import org.apache.commons.httpclient.params.HttpConnectionManagerParams;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.http.client.CommonsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * KT 인프라 서버 연동 서비스
 * TODO 예외처리 일관성있게 정리 필요
 *
 * @author
 *
 */
@Service("HCam.KTInfraService")
public class KTInfraService {

    private final String SOURCE_SYSTEM_CD_ICIS = "01";
    /**
	 * 로거
	 */
	private Logger log = LoggerFactory.getLogger(getClass());
	private RestTemplate restTemplate;
	/**
	 * OpenAPI 환경 프라퍼티
	 */
	@Autowired
	private APIEnv env;

	/**
	 * SDP 연동 인증 요청 메쏘드
	 *
	 * @param userId			사용자 아이디
	 * @param passwd			사용자 패스워드
	 * @return					SDP 로그인 처리 결과 맵
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public HashMap<String, String> sendLoginRequest(String userId, String passwd) throws Exception {

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
	 *
	 * @param userId			사용자 아이디
	 * @param credentialId		Credential ID
	 * @return					SDP 연동 사용자 객체
	 * @throws Exception
	 */
	public User sendSubsRequest(String userId, String credentialId) throws Exception {

		Map<String, String> params = new HashMap<String, String>();
		params.put("userName", userId);
		params.put("credtId", credentialId);

		String response = sendPostRequestToKTInfra(env.getProperty("ktinfra.url.sdp.subs"), params);
		JsonObject responseJsonObject = JsonUtils.fromJson(response);

		User user = new User();
		user.setCredentialId(credentialId);

		// custId 찾기
		JsonArray partyMapList = responseJsonObject.getAsJsonArray("partyMap");

        HashSet<String> custIdSet = new HashSet<String>();
        String birthDate = "";
        int age = 0;

        if(partyMapList != null && partyMapList.size() > 0) {

            for (JsonElement partyElement : partyMapList) {
                JsonObject partyMap = partyElement.getAsJsonObject();
                String partyDetailTypeCd = partyMap.get("partyDetailTypeCd").getAsString();

                if (User.PARTY_DETAIL_CD_01.equals(partyDetailTypeCd)) {
                    if (partyMap.has("birthDate") && StringUtils.isEmpty(birthDate)) {
                        birthDate = partyMap.get("birthDate").getAsString();
                        age = getAgeFromBirth(birthDate);

                        user.setBirth(birthDate);
                        user.setAge(age);
                    }
                } else if (User.PARTY_DETAIL_CD_02.equals(partyDetailTypeCd)) {
                    if (partyMap.has("birthDate") && StringUtils.isEmpty(birthDate)) {
                        birthDate = partyMap.get("birthDate").getAsString();
                        age = getAgeFromBirth(birthDate);

                        user.setBirth(birthDate);
                        user.setAge(age);
                    }
                } else if (User.PARTY_DETAIL_CD_07.equals(partyDetailTypeCd)) {
                    if (StringUtils.isEmpty(birthDate)) {
                        birthDate = "1970101";
                        age = getAgeFromBirth(birthDate);

                        user.setBirth(birthDate);
                        user.setAge(age);
                    }
                }

                if (User.PARTY_DETAIL_CD_01.equals(partyDetailTypeCd)
                        || User.PARTY_DETAIL_CD_02.equals(partyDetailTypeCd)
                        || User.PARTY_DETAIL_CD_07.equals(partyDetailTypeCd)) {
                    if (partyMap.get("partyMap") != null && partyMap.get("partyMap").isJsonArray()) {
                        JsonArray innerPartyMapList = partyMap.getAsJsonArray("partyMap");
                        if (innerPartyMapList != null && innerPartyMapList.size() > 0) {
                            for (JsonElement innerParty : innerPartyMapList) {
                                if (SOURCE_SYSTEM_CD_ICIS.equals(innerParty.getAsJsonObject().get("sourceSystemCd").getAsString())) { // ICIS 오더만
                                    custIdSet.add(innerParty.getAsJsonObject().get("sourceSystemBindId").getAsString());
                                }
                            }
                        }
                    }
                }
            }

            user.setCustIdList(custIdSet.toArray(new String[0]));
        }

        log.info("## custid " + userId + " : " + custIdSet);

		return user;
	}

	/**
	 * 펌웨어 업그레이드 요청 메쏘드
	 *
	 * @param secret			시크릿
	 * @param mac				맥 주소
	 * @param cameraSaid		카메라 SAID
	 * @throws Exception
	 */
	public String sendCameraFirmUpRequest(String secret, String mac , String cameraSaid) throws Exception {

		Map<String, String> params = new HashMap<String, String>();
		params.put("secret", secret);
		params.put("mac", mac);
		params.put("cameraSaid", cameraSaid);

		String response = sendPostRequestToKTInfra(env.getProperty("ktinfra.url.ucems.apply"), params);
		JsonObject responseJsonObject = JsonUtils.fromJson(response);

		JsonObject resultInfo = responseJsonObject.getAsJsonObject("resultInfo");

		if(resultInfo == null) {
			throw new APIException("## sendCameraFirmUpRequest resultInfo is null.", HttpStatus.INTERNAL_SERVER_ERROR);
		}

		String resultCode = resultInfo.get("resultCode").getAsString();
		String resultMsg = resultInfo.get("resultMsg").getAsString();
		String requestDT = resultInfo.get("requestDT").getAsString();
		if(!StringUtils.equalsIgnoreCase(resultCode, "OK")) {
			throw new APIException(String.format("## sendCameraFirmUpRequest resultCode is NOT OK :", resultMsg), HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return requestDT;
	}

	/**
	 * 펌웨어 업그레이드 상태 조회 요청 메쏘드
	 *
	 * @param secret			시크릿
	 * @param mac				맥 주소
	 * @param cameraSaid		카메라 SAID
	 * @return
	 */
	public HashMap<String, String> sendCameraFirmUpCheckRequest(String secret, String mac , String cameraSaid, String dateTime) {

		String upgradeStatus = "0", startDate = "";

		try {

			Map<String, String> params = new HashMap<String, String>();
			params.put("secret", secret);
			params.put("mac", mac);
			params.put("cameraSaid", cameraSaid);
			params.put("requestDT", dateTime);

			String response = sendPostRequestToKTInfra(env.getProperty("ktinfra.url.ucems.status"), params);
			JsonObject responseJsonObject = JsonUtils.fromJson(response);
			JsonObject resultInfo = responseJsonObject.getAsJsonObject("resultInfo");

			if(resultInfo == null) {
				throw new Exception("## sendCameraFirmUpCheckRequest resultInfo is null.");
			}

			String resultCode = resultInfo.get("resultCode").getAsString();
			String resultMsg = resultInfo.get("resultMsg").getAsString().trim();
			
			if(StringUtils.equalsIgnoreCase(resultCode, "OK")) {
				// ignore case
			} else if(StringUtils.equalsIgnoreCase(resultCode, "NOK") 
					&& "업그레이드 상태 정보 없음".equals(resultMsg)) {
				resultInfo.addProperty("stateCode", SpotDevItemVal.UPGRADE_STATUS_IDLE);
			} else {
				throw new Exception("## sendCameraFirmUpCheckRequest resultCode is NOT OK.");
			}

			String stateCode = resultInfo.get("stateCode").getAsString();
            int stateCodeNum = NumberUtils.toInt(stateCode);

			if(stateCodeNum == 0) { // 업그레이드 정보 없음
				upgradeStatus = SpotDevItemVal.UPGRADE_STATUS_INIT;
			} else if(stateCodeNum == 10) { // 다운로드 idle 상태
				upgradeStatus = SpotDevItemVal.UPGRADE_STATUS_IDLE;
			} else if(stateCodeNum >= 11 && stateCodeNum <= 29) { // 다운로드 실패
				upgradeStatus = SpotDevItemVal.UPGRADE_STATUS_FAILURE;
			} else if(stateCodeNum == 30) { // 다운로드 진행 중
				upgradeStatus = SpotDevItemVal.UPGRADE_STATUS_DOWNLOAD;
			} else if(stateCodeNum == 40) { // 다운로드 진행 성공
				upgradeStatus = SpotDevItemVal.UPGRADE_STATUS_DOWNLOAD_OK;
			} else if(stateCodeNum == 50) { // 설치 대기 중
				upgradeStatus = SpotDevItemVal.UPGRADE_STATUS_INSTALL;
			} else if(stateCodeNum == 60) { // 업그레이드 진행 중
				upgradeStatus = SpotDevItemVal.UPGRADE_STATUS_INSTALL;
			} else if(stateCodeNum >= 70 && stateCodeNum <= 89) { // 설치 실패
				upgradeStatus = SpotDevItemVal.UPGRADE_STATUS_FAILURE;
			} else if(stateCodeNum >= 90) { // 설치 성공
				upgradeStatus = SpotDevItemVal.UPGRADE_STATUS_SUCCESS;
			}

		} catch(Exception e) {
			log.warn(e.getMessage(), e);
			upgradeStatus = SpotDevItemVal.UPGRADE_STATUS_FAILURE;
		}

		HashMap<String, String> resultMap = new HashMap<String, String>();
		resultMap.put("result", "0");
		resultMap.put("upgradeStatus", upgradeStatus);
		
		log.debug("## sendCameraFirmUpCheckRequest : {}", resultMap);

		return resultMap;
	}

	/**
	 * 펌웨어 최신 버전 조회 요청 메쏘드
	 *
	 * @param secret			시크릿
	 * @param modelCode			모델 코드
	 * @return					펌웨어 최신 버전 스트링
	 * @throws Exception
	 */
	public String sendCameraFirmVersionCheckRequest(String secret, String modelCode) throws Exception {

		Map<String, String> params = new HashMap<String, String>();
		params.put("secret", secret);
		params.put("modelCode", modelCode);

		String response = sendPostRequestToKTInfra(env.getProperty("ktinfra.url.ucems.get"), params);
		JsonObject responseJsonObject = JsonUtils.fromJson(response);

		JsonObject resultInfo = responseJsonObject.getAsJsonObject("resultInfo");

		if(resultInfo == null) {
			throw new APIException("## sendCameraFirmUpCheckRequest resultInfo is null.", HttpStatus.INTERNAL_SERVER_ERROR);
		}

		String resultCode = resultInfo.get("resultCode").getAsString();
		if(!StringUtils.equalsIgnoreCase(resultCode, "OK")) {
			throw new APIException("## sendCameraFirmUpCheckRequest resultCode is NOT OK.", HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return resultInfo.get("newVersion").getAsString();
	}

	/**
	 * UCloud 토큰 발급 요청 메쏘드. 익셉션을 던지지 않는다.
	 *
	 * @param loginId			로그인 아이디
	 * @param credentialId		Credential ID
	 * @param saId				SAID
	 * @param cameraSaid		카메라 SAID
	 * @return					발급 요청 결과 맵
	 */
	public HashMap<String, Object> sendUCloudAccessTokenReq(String loginId, String credentialId, String saId, String cameraSaid) {

		HashMap<String, Object> resultMap = new HashMap<String, Object>();

		try {

			Map<String, String> params = new HashMap<String, String>();
			params.put("loginId", loginId);
			params.put("cid", credentialId);
			params.put("saId", saId);
			params.put("caSaid", cameraSaid);

			String response = sendPostRequestToKTInfra(env.getProperty("ktinfra.url.ucloud.token"), params);
			resultMap = JsonUtils.fromJson(response, new TypeToken<HashMap<String, Object>>(){}.getType());

		} catch(Exception e) {

			log.warn(e.getMessage(), e);
			resultMap.put("resultCode", ErrorCode.UCLOUD_ERROR);

		} finally {

			if(!resultMap.containsKey("resultCode")) {
				resultMap.put("resultCode", ErrorCode.UCLOUD_ERROR);
			}
		}

		return resultMap;
	}

	/**
	 * UCloud 계정 생성 요청 메쏘드
	 *
	 * @param loginId			로그인 아이디
	 * @param credentialId		Credential ID
	 * @param saId				SAID
	 * @return
	 */
	public HashMap<String, Object> sendUCloudAccountCreateReq(String loginId, String credentialId, String saId) {

		HashMap<String, Object> resultMap = new HashMap<String, Object>();

		try {

			Map<String, String> params = new HashMap<String, String>();
			params.put("loginId", loginId);
			params.put("cid", credentialId);
			params.put("saId", saId);

			String response = sendPostRequestToKTInfra(env.getProperty("ktinfra.url.ucloud.account"), params);
			resultMap = JsonUtils.fromJson(response, new TypeToken<HashMap<String, Object>>(){}.getType());

		} catch(Exception e) {

			log.warn(e.getMessage(), e);
			resultMap.put("resultCode", ErrorCode.UCLOUD_ERROR);

		} finally {

			if(!resultMap.containsKey("resultCode")) {
				resultMap.put("resultCode", ErrorCode.UCLOUD_ERROR);
			}
		}

		return resultMap;
	}

	/**
	 * UCloud 계정 변경 요청 메쏘드
	 *
	 * @param loginId			로그인 아이디
	 * @param credentialId		Credential ID
	 * @param saId				SAID
	 * @param opType			opType
	 * @return
	 */
	public HashMap<String, String> sendUCloudAccountPutReq(String loginId, String credentialId, String saId, String opType) {

		HashMap<String, String> resultMap = new HashMap<String, String>();

		try {

			HashMap<String, String> params = new HashMap<String, String>();
			params.put("loginId", loginId);
			params.put("cid", credentialId);
			params.put("saId", saId);
			params.put("opType", opType);

			String response = sendPutRequestToKTInfra(env.getProperty("ktinfra.url.ucloud.account"), params);
			resultMap = JsonUtils.fromJson(response, new TypeToken<HashMap<String, String>>(){}.getType());

		} catch(Exception e) {

			log.warn(e.getMessage(), e);
			resultMap.put("resultCode", ErrorCode.UCLOUD_ERROR);

		} finally {

			if(!resultMap.containsKey("resultCode")) {
				resultMap.put("resultCode", ErrorCode.UCLOUD_ERROR);
			}
		}

		return resultMap;
	}

	/**
	 * UCloud 계정 삭제 요청 메쏘드
	 *
	 * @param loginId			로그인 아이디
	 * @param credentialId		Credential ID
	 * @param saId				SAID
	 * @return
	 */
	public HashMap<String, Object> sendUCloudAccountDeleteReq(String loginId, String credentialId, String saId) {

		HashMap<String, Object> resultMap = new HashMap<String, Object>();

		try {

			HashMap<String, String> params = new HashMap<String, String>();
			params.put("loginId", loginId);
			params.put("cid", credentialId);
			params.put("saId", saId);

			String response = sendDeleteRequestToKTInfra(env.getProperty("ktinfra.url.ucloud.account"), params);
			resultMap = JsonUtils.fromJson(response, new TypeToken<HashMap<String, Object>>(){}.getType());

		} catch(Exception e) {

			log.warn(e.getMessage(), e);
			resultMap.put("resultCode", ErrorCode.UCLOUD_ERROR);

		} finally {

			if(!resultMap.containsKey("resultCode")) {
				resultMap.put("resultCode", ErrorCode.UCLOUD_ERROR);
			}
		}

		return resultMap;
	}

	/**
	 * 로그인 이벤트 Push 발송 메쏘드. PlanC. 더 이상 사용하지 않음
	 *
	 * @param registrationId	PNS Registration ID
	 * @param telNo				전화번호
	 */
	public void sendPnsRequest(String registrationId, String telNo) {

		HashMap<String, Object> params = new HashMap<String, Object>();

		HashMap<String, String> data = new HashMap<String, String>();
		data.put("telNo", telNo);
		data.put("msg", "${telNo}님이 올레 홈캠을 로그인 하였습니다.");

		params.put("registrationId", registrationId);
		params.put("data", data);

		sendPostRequestToKTInfra(env.getProperty("ktinfra.url.kpns.push"), params);
	}

	/**
	 * SDP 생년월일로 만 나이를 계산하는 메쏘드
	 *
	 * @param birthYYYYMMDD		생년월일
	 * @return					만 나이
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
	 * KT 인프라 서버 HTTP 연동 위한 RestTemplate 생성 메쏘드
	 *
	 * @return					RestTemplate
	 */
	@SuppressWarnings("deprecation")
	private RestTemplate getKTInfraRestTemplate() {

		if(restTemplate == null) {

			try {

				int readTimeout = env.getIntProperty("ktinfra.timeout.read");
				int connTimeout = env.getIntProperty("ktinfra.timeout.conn");

				HttpClientParams httpClientParams = new HttpClientParams();
				httpClientParams.setConnectionManagerTimeout(connTimeout);
				httpClientParams.setSoTimeout(readTimeout);

				HttpConnectionManagerParams connectionManagerParams = new HttpConnectionManagerParams();
				connectionManagerParams.setMaxConnectionsPerHost(HostConfiguration.ANY_HOST_CONFIGURATION, env.getIntProperty("ktinfra.conn.host"));
				connectionManagerParams.setMaxTotalConnections(env.getIntProperty("ktinfra.conn.max"));
				connectionManagerParams.setLinger(0);

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
	 * KT 인프라 서버로 HTTP Post 요청 메쏘드
	 *
	 * @param url				URL
	 * @param o					요청 오브젝트
	 * @return					처리 결과 스트링
	 */
	private String sendPostRequestToKTInfra(String url, Object o) {
		String result = null;

		try	{

			String json = JsonUtils.toJson(o);

			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);

			HttpEntity<String> entity = new HttpEntity<String>(json, headers);

            if (o instanceof HashMap && ((HashMap)o).containsKey("pw"))
                json = JsonUtils.fromJson(json).remove("pw").getAsString();

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
	 * KT 인프라 서버로 HTTP Put 요청 메쏘드
	 *
	 * @param url				URL
	 * @param params			파라미터 맵
	 * @return					처리 결과 스트링
	 */
	private String sendPutRequestToKTInfra(String url, HashMap<String, String> params) {
		String result = null;

		try	{
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<String> entity = new HttpEntity<String>(headers);

			LinkedList<String> list = getRandomKtInfraServer();
			while(!list.isEmpty()) {
				String fullUrl = env.getProperty("ktinfra.protocol") + "://" + list.get(0) + ":" + env.getProperty("ktinfra.port") + url;
				list.remove(0);
				
				UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.fromUriString(fullUrl);
				if(params != null) {

					Iterator<String> it = params.keySet().iterator();
					while(it.hasNext()) {

						String name = it.next();
						String value = params.get(name);
						uriComponentsBuilder.queryParam(name, value);
					}
				}

				try	{
					log.info("# Send Request to KTInfra : {}", fullUrl);
					result = getKTInfraRestTemplate().exchange(uriComponentsBuilder.build().toUri(), HttpMethod.PUT, entity, String.class).getBody();
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
	 * KT 인프라 서버로 HTTP Delete 요청 메쏘드
	 *
	 * @param url				URL
	 * @param params			파라미터 맵
	 * @return					처리 결과 스트링
	 */
	private String sendDeleteRequestToKTInfra(String url, HashMap<String, String> params) {
		String result = null;

		try	{
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<String> entity = new HttpEntity<String>(headers);

			LinkedList<String> list = getRandomKtInfraServer();
			while(!list.isEmpty()) {
				String fullUrl = env.getProperty("ktinfra.protocol") + "://" + list.get(0) + ":" + env.getProperty("ktinfra.port") + url;
				list.remove(0);
				
				UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.fromUriString(fullUrl);
				if(params != null) {
					Iterator<String> it = params.keySet().iterator();
					while(it.hasNext()) {

						String name = it.next();
						String value = params.get(name);
						uriComponentsBuilder.queryParam(name, value);
					}
				}

				try	{
					log.info("# Send Request to KTInfra : {}", fullUrl);
					result = getKTInfraRestTemplate().exchange(uriComponentsBuilder.build().toUri(), HttpMethod.DELETE, entity, String.class).getBody();
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
