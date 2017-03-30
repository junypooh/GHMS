package com.kt.giga.home.openapi.service;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.httpclient.HostConfiguration;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.params.HttpClientParams;
import org.apache.commons.httpclient.params.HttpConnectionManagerParams;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.CommonsClientHttpRequestFactory;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.google.gson.GsonBuilder;
import com.kt.giga.home.openapi.common.APIEnv;
import com.kt.giga.home.openapi.hcam.HCamConstants.SpotDevItemNm;
import com.kt.giga.home.openapi.hcam.HCamConstants.SpotDevItemVal;
import com.kt.giga.home.openapi.util.JsonUtils;
import com.kt.giga.home.openapi.vo.cnvy.ItgCnvyData;
import com.kt.giga.home.openapi.vo.cnvy.SpotDevCnvyData;
import com.kt.giga.home.openapi.vo.spotdev.SpotDev;
import com.kt.giga.home.openapi.vo.spotdev.SpotDevDtl;
import com.kt.giga.home.openapi.vo.spotdev.SpotDevKey;
import com.kt.giga.home.openapi.vo.spotdev.SpotDevRetv;
import com.kt.giga.home.openapi.vo.spotdev.SpotDevRetvReslt;
import com.kt.giga.home.openapi.vo.spotdev.SpotDevUdate;
import com.kt.giga.home.openapi.vo.spotdev.query.SpotDevBasQueryVO;
import com.kt.giga.home.openapi.vo.spotdev.query.SpotDevBasVO;
import com.kt.giga.home.openapi.vo.spotdev.query.SpotDevBasVO.SpotDevDtlVO;

/**
 * EC 연동 서비스
 *
 * @author
 *
 */
@SuppressWarnings("deprecation")
@Service("Common.ECService")
public class ECService {

	private final Logger log = LoggerFactory.getLogger(getClass());

	@Value("${service.hostname}")
	private String hostname;

	@Autowired
	private APIEnv env;

	@Autowired
	private ThreadPoolTaskExecutor executor;

	private RestTemplate restTemplate;

	// 현장장치 정보 조회
	public String sendRetrieveToEC(SpotDevRetv spotDevRetv) {
		return sendRequestToConnectedEC(env.getProperty("ec.url.retv"), spotDevRetv);
	}

	// 현장장치 정보 조회 Report
	public String sendRetrieveReportToEC(SpotDevRetvReslt spotDevRetvReslt) {
		return sendRequestToConnectedEC(env.getProperty("ec.url.retv.report"), spotDevRetvReslt);
	}

	// 현장장치 제어
	public String sendRTimeControlToEC(ItgCnvyData itgCnvyData) {
		return sendRequestToConnectedEC(env.getProperty("ec.url.rtime"), itgCnvyData);
	}

	// 현장장치 설정
	public String sendSetupChangeToEC(ItgCnvyData itgCnvyData) {
		return sendRequestToConnectedEC(env.getProperty("ec.url.setup"), itgCnvyData);
	}

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

	public String sendRequestToEC(String url, Object o) {
		String ecResult = null;
		try	{
			String json = JsonUtils.toJson(o);

			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
			HttpEntity<String> entity = new HttpEntity<String>(json, headers);

			log.info("# Send Request to EC : {}\n{}", url, json);
			ecResult = getECRestTemplate().postForObject(url, entity, String.class);
			log.info("# Recv Response from EC : {}\n{}", url, JsonUtils.toPrettyJson(ecResult));
		} catch(RestClientException e) {
			throw e;
		} catch(Exception e) {
			log.warn(e.getMessage(), e);
		}

		return ecResult;
	}

	// 장치가 연결된 제어서버 ip로 요청
	public String sendRequestToConnectedEC(String url, Object o) {
		long svcTgtSeq = 0L;
		long spotDevSeq = 0L;
		String spotDevId = null;
		String reqEcSrvrId = null;

		log.debug("## sendRequestToConnectedEC VO : {}", JsonUtils.toJson(o));

		if(o instanceof SpotDevRetv) {
			((SpotDevRetv)o).setReqApiSrvrId(hostname);
			reqEcSrvrId = ((SpotDevRetv)o).getReqEcSrvrId();

			SpotDevKey spotDevKey = ((SpotDevRetv) o).getFirstInclSpotDevKey();
			svcTgtSeq = spotDevKey.getSvcTgtSeq();
			spotDevSeq = spotDevKey.getSpotDevSeq();
			spotDevId = spotDevKey.getSpotDevId();

		} else if(o instanceof SpotDevRetvReslt) {
			((SpotDevRetvReslt)o).setReqApiSrvrId(hostname);
			reqEcSrvrId = ((SpotDevRetvReslt)o).getReqEcSrvrId();

			SpotDev spotDev = ((SpotDevRetvReslt) o).getFirstSpotDev();
			svcTgtSeq = spotDev.getSvcTgtSeq();
			spotDevSeq = spotDev.getSpotDevSeq();
			spotDevId = spotDev.getSpotDevId();

		} else if(o instanceof ItgCnvyData) {
			((ItgCnvyData)o).setReqApiSrvrId(hostname);
			reqEcSrvrId = ((ItgCnvyData)o).getReqEcSrvrId();

			SpotDevCnvyData spotDevCnvyData = ((ItgCnvyData) o).getFirstSpotDevCnvyData();
			svcTgtSeq = spotDevCnvyData.getSvcTgtSeq();
			spotDevSeq = spotDevCnvyData.getSpotDevSeq();
			spotDevId = spotDevCnvyData.getSpotDevId();

		} else if(o instanceof SpotDevUdate) {
			((SpotDevUdate)o).setReqApiSrvrId(hostname);
			reqEcSrvrId = ((SpotDevUdate)o).getReqEcSrvrId();

		} else if(o instanceof SpotDevRetvReslt) {
			((SpotDevRetvReslt)o).setReqApiSrvrId(hostname);
			reqEcSrvrId = ((SpotDevRetvReslt)o).getReqEcSrvrId();

		} else {
			log.warn("## sendRequestToConnectedEC VO Unknown!! : {}", JsonUtils.toJson(o));
		}

		// TODO : reqEcSrvrId 값이 있는 경우 EC에서 접속서버 정보를 올려 준 경우 이므로 EC로 장치 접속 서버 조회 없이 처리 가능하다.
		SpotDevBasQueryVO spotDevBasQueryVO = sendSelectQueryToEC(svcTgtSeq, spotDevSeq, spotDevId);
		SpotDevBasVO spotDevBasVO = spotDevBasQueryVO.getSpotDevBasVO();
		
		if(StringUtils.isEmpty(reqEcSrvrId)) {
			if(spotDevBasVO == null || spotDevBasVO.getCommnAgntBasVO() == null) {
				// TODO 익셉션을 던져야 하면 여기에서 익셉션
				log.warn("## sendRequestToConnectedEC VO : Not Connected. {}", JsonUtils.toJson(o));
				return SpotDevItemVal.CON_STAT_OFFLINE;

			} else {
				String ip = spotDevBasVO.getCommnAgntBasVO().getIpadr();
				String fullUrl = env.getProperty("ec.intn.protocol") + "://" + ip + ":" + env.getProperty("ec.intn.port") + url;
				sendRequestToEC(fullUrl, o);
				return SpotDevItemVal.CON_STAT_ONLINE;
			}
		} else {
			String ip = env.getProperty("ec." + reqEcSrvrId);
			String fullUrl = env.getProperty("ec.intn.protocol") + "://" + ip + ":" + env.getProperty("ec.intn.port") + url;
			sendRequestToEC(fullUrl, o);
			return SpotDevItemVal.CON_STAT_ONLINE;
		}
	}

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
			} catch(ResourceAccessException e) {
				log.warn(e.getMessage(), e.fillInStackTrace());
			} catch(HttpClientErrorException e) {
				log.warn(e.getMessage(), e.fillInStackTrace());
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
