package com.kt.giga.home.openapi.ghms.common.service;

import java.nio.charset.Charset;
import java.util.Collections;
import java.util.LinkedList;

import org.apache.commons.httpclient.HostConfiguration;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.params.HttpClientParams;
import org.apache.commons.httpclient.params.HttpConnectionManagerParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.CommonsClientHttpRequestFactory;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.google.gson.GsonBuilder;
import com.kt.giga.home.openapi.ghms.kafka.smcp.gw.ca.query.SpotDevBasQueryVO;
import com.kt.giga.home.openapi.ghms.kafka.smcp.gw.ca.query.SpotDevBasVO;
import com.kt.giga.home.openapi.ghms.util.json.JsonUtils;
import com.kt.giga.home.openapi.ghms.util.properties.APIEnv;

/**
 * EC 연동 서비스
 * 
 * @author 
 *
 */
@Service("Common.ECService")
public class ECService {

	private final Logger log = LoggerFactory.getLogger(getClass());

	@Autowired
	private APIEnv env;

	@Autowired
	private ThreadPoolTaskExecutor executor;

	@Autowired
	private RestTemplate restTemplate;
/*
	// 현장장치 정보 조회
	public void sendRetrieveToEC(SpotDevRetv spotDevRetv) {
		sendRequestToConnectedEC(env.getProperty("ec.url.retv"), spotDevRetv);
	}

	// 현장장치 정보 조회 Report
	public void sendRetrieveReportToEC(SpotDevRetvReslt spotDevRetvReslt) {
		sendRequestToConnectedEC(env.getProperty("ec.url.retv.report"), spotDevRetvReslt);
	}

	// 현장장치 제어
	public void sendRTimeControlToEC(ItgCnvyData itgCnvyData) {
		sendRequestToConnectedEC(env.getProperty("ec.url.rtime"), itgCnvyData);
	}

	// 현장장치 설정
	public void sendSetupChangeToEC(ItgCnvyData itgCnvyData) {
		sendRequestToConnectedEC(env.getProperty("ec.url.setup"), itgCnvyData);
	}
*/
	// 현장장치 조회
	public SpotDevBasQueryVO sendSelectQueryToEC(SpotDevBasVO spotDevBas) {
		String ecResult = sendRetryRequestToEC(env.getProperty("ec.url.query.select"), spotDevBas);
		return JsonUtils.fromJson(ecResult, SpotDevBasQueryVO.class);
	}

	// 현장장치 조회
	public SpotDevBasQueryVO sendSelectQueryToEC(long svcTgtSeq, long spotDevSeq, String spotDevId) {

		SpotDevBasVO spotDevBas = new SpotDevBasVO();

		spotDevBas.setSvcTgtSeq(svcTgtSeq);
		spotDevBas.setSpotDevSeq(spotDevSeq);
		spotDevBas.setSpotDevId(spotDevId);

		return sendSelectQueryToEC(spotDevBas);
	}

	// 현장장치 조회
	public SpotDevBasQueryVO sendSelectSpotQueryToEC(SpotDevBasVO spotDevBas) {
		String ecResult = sendRetryRequestToEC(env.getProperty("ec.url.query.select"), spotDevBas);
		return JsonUtils.fromJson(ecResult, SpotDevBasQueryVO.class);
	}
/*
	public SpotDev getOnlineSpotDev(SpotDev spotDev, boolean includeDetail) {

		boolean isOnline = false;
		List<SpotDevDtl> spotDevDtls = null;
		SpotDevBasQueryVO spotDevBasQuery = sendSelectQueryToEC(spotDev.getSvcTgtSeq(), spotDev.getSpotDevSeq(), spotDev.getSpotDevId());
		SpotDevBasVO spotDevBasVO = spotDevBasQuery.getSpotDevBasVO();

		if (spotDevBasVO != null) {

			if (includeDetail) {
				spotDevDtls = new ArrayList<SpotDevDtl>();
				for (SpotDevDtlVO spotDevDtlVO : spotDevBasVO.getSpotDevDtlVOs()) {

					String key = spotDevDtlVO.getAtribNm();
					String value = spotDevDtlVO.getAtribVal();
					key = env.getProperty("spotDevDtlVO.key." + key);
					if (key != null)
						spotDevDtls.add(new SpotDevDtl(key, value));
				}

				spotDev.getSpotDevDtls().addAll(spotDevDtls);
			}

			isOnline = StringUtils.isNotEmpty(spotDevBasVO.getSessnId());

			return (isOnline) ? spotDev : null;
		}

		return null;
	}
*/
	// 현장장치 삽입
	public SpotDevBasQueryVO sendInsertQueryToEC(SpotDevBasVO spotDevBas) {
		String ecResult = sendRetryRequestToEC(env.getProperty("ec.url.query.insert"), spotDevBas);
		return JsonUtils.fromJson(ecResult, SpotDevBasQueryVO.class);
	}

	// 현장장치 변경
	public SpotDevBasQueryVO sendUpdateQueryToEC(SpotDevBasVO spotDevBas) {
		String ecResult = sendRetryRequestToEC(env.getProperty("ec.url.query.update"), spotDevBas);
		return JsonUtils.fromJson(ecResult, SpotDevBasQueryVO.class);
	}

	// 현장장치 이름 변경
	public SpotDevBasQueryVO setDevNm(long svcTgtSeq, long spotDevSeq, String spotDevId, String devNm) {

		SpotDevBasQueryVO spotDevBasQuery = sendSelectQueryToEC(svcTgtSeq, spotDevSeq, spotDevId);

		if(spotDevBasQuery == null) {
			return null;
		}

		SpotDevBasVO spotDevBas = spotDevBasQuery.getSpotDevBasVO();

		spotDevBas.setSvcTgtSeq(svcTgtSeq);
		spotDevBas.setSpotDevSeq(spotDevSeq);
		spotDevBas.setSpotDevId(spotDevId);
		spotDevBas.setSpotDevNm(devNm);

		String ecResult = sendRetryRequestToEC(env.getProperty("ec.url.query.update"), spotDevBas);
		return JsonUtils.fromJson(ecResult, SpotDevBasQueryVO.class);
	}

	// 현장장치 삭제
	public SpotDevBasQueryVO sendDeleteQueryToEC(SpotDevBasVO spotDevBas) {
		String ecResult = sendRetryRequestToEC(env.getProperty("ec.url.query.delete"), spotDevBas);
		return JsonUtils.fromJson(ecResult, SpotDevBasQueryVO.class);
	}

	@SuppressWarnings("deprecation")
	private RestTemplate getECRestTemplate() {
		
		if(restTemplate == null) {
			
			try {

				int readTimeout = env.getIntProperty("ec.timeout.read");
				int connTimeout = env.getIntProperty("ec.timeout.conn");
				
				HttpClientParams httpClientParams = new HttpClientParams();
				httpClientParams.setConnectionManagerTimeout(connTimeout);
				httpClientParams.setSoTimeout(readTimeout);
				
				HttpConnectionManagerParams connectionManagerParams = new HttpConnectionManagerParams();
				connectionManagerParams.setMaxConnectionsPerHost(HostConfiguration.ANY_HOST_CONFIGURATION, env.getIntProperty("ec.conn.host"));
				connectionManagerParams.setMaxTotalConnections(env.getIntProperty("ec.conn.max"));
				
				MultiThreadedHttpConnectionManager connectionManager = new MultiThreadedHttpConnectionManager();
				connectionManager.setParams(connectionManagerParams);
				
				CommonsClientHttpRequestFactory factory = new CommonsClientHttpRequestFactory(new HttpClient(httpClientParams, connectionManager));
				restTemplate = new RestTemplate(factory);
				
			} catch(Exception e) {
				
				log.warn(e.getMessage(), e);
			}			
		}
		
//		HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
//		factory.setReadTimeout(env.getIntProperty("ec.timeout.read"));
//		factory.setConnectTimeout(env.getIntProperty("ec.timeout.conn"));
//		return new RestTemplate(factory);
		
		return restTemplate;
	}

	public String toJson(Object o, boolean isPrettyPrinting) {
		if(isPrettyPrinting) {
			return new GsonBuilder().setPrettyPrinting().create().toJson(o);
		}
		else {
			return new GsonBuilder().create().toJson(o);
		}
	}

	public String toJson(Object o) {
		return toJson(o, false);
	}

	public String sendRequestToEC(String url, Object o) {
		String ecResult = null;
		try	{
			String json = toJson(o);

			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
			HttpEntity<String> entity = new HttpEntity<String>(json, headers);

			log.info("## send Request to EC {} : {}", url, json);
//			ecResult = restTemplate.postForObject(url, entity, String.class);
			ecResult = getECRestTemplate().postForObject(url, entity, String.class);
			log.info("## recv Response from EC {} : {}", url, ecResult);
		} catch(RestClientException e) {
			throw e;
		} catch(Exception e) {
			log.warn(e.getMessage(), e);
		}

		return ecResult;
	}
/*
	// 장치가 연결된 제어서버 ip로 요청
	public String sendRequestToConnectedEC(String url, Object o) {
		long svcTgtSeq = 0L;
		long spotDevSeq = 0L;
		String spotDevId = null;

		log.debug("## sendRequestToConnectedEC VO : {}", toJson(o));

		if(o instanceof SpotDevRetv) {
			SpotDevKey spotDevKey = ((SpotDevRetv) o).getFirstInclSpotDevKey();
			svcTgtSeq = spotDevKey.getSvcTgtSeq();
			spotDevSeq = spotDevKey.getSpotDevSeq();
			spotDevId = spotDevKey.getSpotDevId();

		} else if(o instanceof SpotDevRetvReslt) {
			SpotDev spotDev = ((SpotDevRetvReslt) o).getFirstSpotDev();
			svcTgtSeq = spotDev.getSvcTgtSeq();
			spotDevSeq = spotDev.getSpotDevSeq();
			spotDevId = spotDev.getSpotDevId();

		} else if(o instanceof ItgCnvyData) {
			SpotDevCnvyData spotDevCnvyData = ((ItgCnvyData) o).getFirstSpotDevCnvyData();
			svcTgtSeq = spotDevCnvyData.getSvcTgtSeq();
			spotDevSeq = spotDevCnvyData.getSpotDevSeq();
			spotDevId = spotDevCnvyData.getSpotDevId();
		} else {
			log.warn("## sendRequestToConnectedEC VO Unknown!! : {}", toJson(o));
		}

		SpotDevBasQueryVO spotDevBasQueryVO = sendSelectQueryToEC(svcTgtSeq, spotDevSeq, spotDevId);
		SpotDevBasVO spotDevBasVO = spotDevBasQueryVO.getSpotDevBasVO();

		if(spotDevBasVO == null || spotDevBasVO.getCommnAgntBasVO() == null) {

			// TODO 익셉션을 던져야 하면 여기에서 익셉션
			log.warn("## sendRequestToConnectedEC VO : Not Connected. {}", toJson(o));
			return null;
		} else {
			String ip = spotDevBasVO.getCommnAgntBasVO().getIpadr();
			String fullUrl = env.getProperty("ec.intn.protocol") + "://" + ip + ":" + env.getProperty("ec.intn.port") + url;
			return sendRequestToEC(fullUrl, o);
		}
	}
*/
	// 연결 오류 발생할 경우 제어서버 개수만큼 재 요청
	public String sendRetryRequestToEC(String url, Object o) {
		String ecResult = null;
		LinkedList<String> list = getRandomECServer();

		while(!list.isEmpty()) {
			String fullUrl = env.getProperty("ec.ca.protocol") + "://" + list.get(0) + ":" + env.getProperty("ec.ca.port") + url;
			list.remove(0);

			try	{
				ecResult = sendRequestToEC(fullUrl, o);
				break;
			} catch(RestClientException e) {
				log.warn(e.getMessage(), e);
			}
		}

		return ecResult;
	}

	// 제어서버 개수만큼의 서버IP목록을 순서 섞어서 리턴
	public LinkedList<String> getRandomECServer() {
		LinkedList<String> list = new LinkedList<String>();
		String[] ecServerList = env.splitProperty("ec.serverList");

		for(int i = 0; i < ecServerList.length; i++) {
			list.add(ecServerList[i]);
		}

		Collections.shuffle(list);

		return list;
	}

	// 가상 IP에 맵핑되는 리얼IP를 리턴 (맵핑되는 IP가 없을 경우 가상 IP를 리턴)
	// 맵핑하려는 프로퍼티 값의(serverRealList, serverList) 순서를 동일하게 해야한다.
	public String getRealIp(String ip) {
		// real 서버와 맵핑
		String[] ecServerRealList = env.splitProperty("ec.serverRealList");
		String[] ecServerList = env.splitProperty("ec.serverList");
		String realIp = ip;
		for(int i = 0; i < ecServerList.length; i++) {
			if(ip.equals(ecServerList[i])) {
				realIp = ecServerRealList[i];
				break;
			}
		}

		return realIp;
	}
}
