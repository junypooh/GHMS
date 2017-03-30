package com.kt.giga.home.openapi.kpns.service;

import com.kt.giga.home.openapi.common.APIEnv;
import com.kt.giga.home.openapi.domain.SpotDevBas;
import com.kt.giga.home.openapi.domain.SpotDevExpnsnBas;
import com.kt.giga.home.openapi.kpns.client.InfraHttpClientFactory;
import com.kt.giga.home.openapi.kpns.client.RestHttpClient;
import com.kt.giga.home.openapi.kpns.dao.EventDao;
import com.kt.giga.home.openapi.kpns.domain.Event;
import com.kt.giga.home.openapi.kpns.domain.PushInfoRequest;
import com.kt.giga.home.openapi.kpns.domain.PushReportRequest;
import com.kt.giga.home.openapi.kpns.host.KPNSServerInfo;
import com.kt.giga.home.openapi.service.APIException;
import com.kt.giga.home.openapi.util.JsonUtils;
import org.apache.commons.httpclient.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResourceAccessException;

import java.util.HashMap;
import java.util.Map;

@Service
public class KPNSService {
	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private InfraHttpClientFactory factory;
	
	@Autowired
	private KPNSServerInfo serverInfo;
	
	@Autowired
	private APIEnv env;
	
	@Autowired
	private EventDao eventDao;
	
	public void updateDetectTime(PushInfoRequest req) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("dev_uu_id", req.getData().get("devUUId"));
		map.put("spot_dev_item_nm", "lastDetectionTime");
		
		SpotDevBas spotDevBas = eventDao.getSpotDevBas(map);
		SpotDevExpnsnBas spotDevExpnsnBas = eventDao.getSpotDevExpnsnBas(map);
		
		if(spotDevBas != null) {
			if(spotDevExpnsnBas != null && spotDevExpnsnBas.getSvc_tgt_seq() != 0) {
				spotDevExpnsnBas.setSvc_tgt_seq(spotDevBas.getSvc_tgt_seq());
				spotDevExpnsnBas.setSpot_dev_seq(spotDevBas.getSpot_dev_seq());
				spotDevExpnsnBas.setSpot_dev_item_nm("lastDetectionTime");
				spotDevExpnsnBas.setSpot_dev_item_val(req.getData().get("detectTime").toString());		
				
				eventDao.updateSpotDevExpnsnBas(spotDevExpnsnBas);
			} else {
				spotDevExpnsnBas = new SpotDevExpnsnBas();
				spotDevExpnsnBas.setSvc_tgt_seq(spotDevBas.getSvc_tgt_seq());
				spotDevExpnsnBas.setSpot_dev_seq(spotDevBas.getSpot_dev_seq());
				spotDevExpnsnBas.setSpot_dev_item_nm("lastDetectionTime");
				spotDevExpnsnBas.setSpot_dev_item_val(req.getData().get("detectTime").toString());		
				
				eventDao.insertSpotDevExpnsnBas(spotDevExpnsnBas);
			}
		}
	}
	
	public long selectMsgTrmForwardTxnSeq() throws Exception {
		return eventDao.selectMsgTrmForwardTxnSeq();
	}
	
	public void insertMsgTrmForwardTxn(Event event) throws Exception {
		eventDao.insertMsgTrmForwardTxn(event);
	}
	
	public void report(PushReportRequest req) throws Exception {
		String rcvYn = "N";
		if("error_success".equals(req.getError()) && "result_sent_ok".equals(req.getSentResult())) {
			rcvYn = "Y";
		}
		
		Event event = new Event();
		event.setMsg_id(req.getMessageId());
		event.setMsg_rcv_yn(rcvYn);
		event.setMsg_rcv_fail_txn(req.getSentResult());
		event.setMsg_rcv_dt(req.getSentTime());
		
		eventDao.updateMsgTrmForwardTxn(event);
		
		if("N".equals(rcvYn) && "result_sending_failed".equals(req.getSentResult())) {
			// long msg_seq = service.selectMsgTrmForwardTxnSeq();
			long msg_seq = System.nanoTime();
			
			event = eventDao.selectMsgTrmForwardTxnByMsgId(event);
			
			if(event == null) {
				throw new APIException("Not Found msgid.", org.springframework.http.HttpStatus.NOT_FOUND);
			}

            Map<String, Object> map = JsonUtils.fromJson(event.getMsg_trm_sbst(), Map.class);
            map.put("seq", Long.toString(msg_seq));
			
			PushInfoRequest pushInfo = new PushInfoRequest();
			pushInfo.setRegistrationId(event.getPns_reg_id());
			pushInfo.setReportUrl(env.getProperty("kpns.report.url"));
			pushInfo.setData(map);
			
			// 5회 재전송 시도하였으면 재전송 없음
			if(event.getMsg_retry_seq() >= env.getIntProperty("kpns.retry.count")) return;
			
			// 1. 푸시 재전송
			Map<String, Object> res = this.push(pushInfo);
			
			// 2. 푸시 재전송 이력 등록
			String msg_id = "";
			String result = "";
			String forwardYn = "N";
			if(res != null) {
				if((int)res.get("statusCode") == HttpStatus.SC_OK || (int)res.get("statusCode") == HttpStatus.SC_CREATED) {
					if(1 == (int)res.get("success")) {
						msg_id = (String)res.get("multicast_id");
						result = "success";
						forwardYn = "Y";									
					} else {
						result = ((Map<String, String>)res.get("results")).get("error");
					}
				} else {
					result = (String)res.get("statusText");
				}
			} else {
				result = "unknown error";
			}
			
			event.setMsg_seq(msg_seq);
			event.setMsg_id(msg_id);
			event.setUp_msg_id(event.getMsg_id());
			event.setMsg_trm_fail_txn(result);
			event.setMsg_trm_forward_yn(forwardYn);
			event.setMsg_retry_seq(event.getMsg_retry_seq() + 1);
			this.insertMsgTrmForwardTxn(event);
		}
	}
	
	public Map<String, Object> push(PushInfoRequest req) throws Exception {
		RestHttpClient httpClient = factory.getInfraHttpClient(serverInfo);
		Map<String, Object> res = null;
		
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", "PNSLogin auth=" + env.getProperty("kpns.auth"));
		
		String url = serverInfo.getProtocol() + "://" 
				+ serverInfo.getHost() + ":" 
				+ serverInfo.getPort() 
				+ KPNSApiCode.req_message_send.getUrl(); 
		
		try {
			log.info("# Send Request to KPNS : {}\n{}", url, JsonUtils.toPrettyJson(req));
            res = httpClient.send(url, HttpMethod.POST, headers, req, new HashMap<String, Object>());
            log.info("# Recv Response from KPNS : {}\n{}", url, JsonUtils.toPrettyJson(res));
		} catch (ResourceAccessException e) {
			res = new HashMap<String, Object>();
			res.put("statusCode", HttpStatus.SC_SERVICE_UNAVAILABLE);
			res.put("statusText", e.getMessage());
			log.error(e.getMessage(), e.fillInStackTrace());
		} catch (HttpClientErrorException e) {
			res = new HashMap<String, Object>();
			res.put("statusCode", HttpStatus.SC_SERVICE_UNAVAILABLE);
			res.put("statusText", e.getMessage());
			log.error(e.getMessage(), e.fillInStackTrace());
		} catch (HttpServerErrorException e) {
			res = new HashMap<String, Object>();
			res.put("statusCode", HttpStatus.SC_INTERNAL_SERVER_ERROR);
			res.put("statusText", e.getMessage());
			log.error(e.getMessage(), e.fillInStackTrace());
		} catch(Exception e) {
			res = new HashMap<String, Object>();
			res.put("statusCode", HttpStatus.SC_INTERNAL_SERVER_ERROR); 
			res.put("statusText", e.getMessage());
			log.error(e.getMessage(), e.fillInStackTrace());
		}
		
		return res;
	}
}
