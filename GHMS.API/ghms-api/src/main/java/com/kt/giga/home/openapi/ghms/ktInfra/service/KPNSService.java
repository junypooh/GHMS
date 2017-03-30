/*******************************************************************************
 * Copyright(c) 2015 KT. All rights reserved.
 * This software is the proprietary information of KT.
 *******************************************************************************/
package com.kt.giga.home.openapi.ghms.ktInfra.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResourceAccessException;

import com.google.gson.GsonBuilder;
import com.kt.giga.home.openapi.ghms.common.GHmsConstants.CommonConstants;
import com.kt.giga.home.openapi.ghms.common.token.AuthToken;
import com.kt.giga.home.openapi.ghms.ktInfra.client.InfraHttpClientFactory;
import com.kt.giga.home.openapi.ghms.ktInfra.client.RestHttpClient;
import com.kt.giga.home.openapi.ghms.ktInfra.dao.KPNSDao;
import com.kt.giga.home.openapi.ghms.ktInfra.domain.key.KPNSEvent;
import com.kt.giga.home.openapi.ghms.ktInfra.domain.key.PushInfoRequest;
import com.kt.giga.home.openapi.ghms.ktInfra.domain.key.PushReportRequest;
import com.kt.giga.home.openapi.ghms.ktInfra.host.KPNSServerInfo;
import com.kt.giga.home.openapi.ghms.user.dao.UserDao;
import com.kt.giga.home.openapi.ghms.util.exception.APIException;
import com.kt.giga.home.openapi.ghms.util.json.JsonUtils;
import com.kt.giga.home.openapi.ghms.util.properties.APIEnv;

/**
 * 
 * @author junypooh ( kyungjun.park@ceinside.co.kr )
 * @since 2015. 3. 10.
 */
@Service
public class KPNSService {

	/** Logger */
	private Logger log = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private InfraHttpClientFactory factory;
	
	@Autowired
	private KPNSServerInfo serverInfo;
	
	@Autowired
	private APIEnv env;
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private KPNSDao kPNSDao;
	
	public Map<String, Object> push(PushInfoRequest req) throws APIException {
		return push(req, false);
	}
	
	public Map<String, Object> push(PushInfoRequest req, boolean insertHistory) throws APIException {
		
		RestHttpClient httpClient = factory.getInfraHttpClient(serverInfo);
		Map<String, Object> res = null;
		
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", "PNSLogin auth=" + env.getProperty("kpns.auth"));
		
		// call back URL 셋팅
		req.setReportUrl(env.getProperty("kpns.report.url"));
		
		String url = serverInfo.getProtocol() + "://" 
				+ serverInfo.getHost() + ":" 
				+ serverInfo.getPort() 
				+ KPNSApiCode.req_message_send.getUrl();
		
		if(req.getRegistrationId() == null || "".equals(req.getRegistrationId())) {
			return res;
		}
		
		try {
			log.info("## Send Request to KPNS : {}\n{}", url, JsonUtils.toPrettyJson(req));
			res = httpClient.send(url, HttpMethod.POST, headers, req, new HashMap<String, Object>());
			log.info("## Recv Response from KPNS : {}\n{}", url, JsonUtils.toPrettyJson(res));
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
		
		// 푸시 이력 등록
		String eventId = req.getData().get("eventId").toString();
		// API 에서 보내는 푸시는 이력 생성 하지 않는다.
		if(insertHistory && eventId.indexOf("A") == -1) {

			String msg_id = "";
			String result = "";
			String forwardYn = "N";

			if((int)res.get("statusCode") == HttpStatus.SC_OK || (int)res.get("statusCode") == HttpStatus.SC_CREATED) {
				if(1 == (int)res.get("success")) {
					msg_id = (String)res.get("multicast_id");
					result = "success";
					forwardYn = "Y";										
					log.debug("Push Success : " + req.getRegistrationId());										
				} else {
					result = ((Map<String, String>)res.get("results")).get("error");
				}
			} else {
				result = (String)res.get("statusText");
			}
			
			if(StringUtils.isEmpty(msg_id)) {
				msg_id = StringUtils.replaceChars(UUID.randomUUID().toString(), "-", "");
			}
			
			long svcTgtSeq = 0;
			long spotDevSeq = 0;
			try {
				
				Map data = req.getData();
				if(data.get("svcTgtSeq") != null && !"null".equals(data.get("svcTgtSeq"))) {
					svcTgtSeq = Long.valueOf(data.get("svcTgtSeq").toString());
				}
				if(data.get("deviceSeq") != null && !"null".equals(data.get("deviceSeq"))) {
					spotDevSeq = Long.valueOf(data.get("deviceSeq").toString());
				}
				
			} catch (Exception ex) {
				// ignore
			}
			
			long msg_seq = System.nanoTime();
			
			Map<String, Object> mbrInfoByPnsId = kPNSDao.selectMbrInfoByPnsId(req.getRegistrationId());
			long mbrSeq = Long.valueOf(mbrInfoByPnsId.get("mbr_seq").toString());
			String telNo = mbrInfoByPnsId.get("tel_no").toString();
			
			KPNSEvent event = new KPNSEvent();
			event.setMbr_seq(mbrSeq);
			event.setSvc_tgt_seq(svcTgtSeq);
			event.setSpot_dev_seq(spotDevSeq);
			event.setPns_reg_id(req.getRegistrationId());
			event.setTel_no(telNo);
			event.setDstr_cd(CommonConstants.DSTR_CD);
			event.setSvc_theme_cd(CommonConstants.SVC_THEME_CD);
			event.setUnit_svc_cd(CommonConstants.UNIT_SVC_CD);
			event.setSetup_cd(req.getData().get("eventId").toString().substring(10, 14));
			event.setMsg_seq(msg_seq);
			event.setMsg_id(msg_id);
			event.setMsg_trm_sbst(new GsonBuilder().create().toJson(req.getData()));
			event.setMsg_trm_fail_txn(result);
			event.setMsg_trm_forward_yn(forwardYn);
			
			kPNSDao.insertMsgTrmForwardTxn(event);
		}
		
		return res;
	}
	
	public void report(PushReportRequest req) throws Exception {
		String rcvYn = "N";
		if("error_success".equals(req.getError()) && "result_sent_ok".equals(req.getSentResult())) {
			rcvYn = "Y";
		}
		
		KPNSEvent event = new KPNSEvent();
		event.setMsg_id(req.getMessageId());
		event.setMsg_rcv_yn(rcvYn);
		event.setMsg_rcv_fail_txn(req.getSentResult());
		event.setMsg_rcv_dt(req.getSentTime());
		
		kPNSDao.updateMsgTrmForwardTxn(event);
		
		if("N".equals(rcvYn) && "result_sending_failed".equals(req.getSentResult())) {
			// long msg_seq = service.selectMsgTrmForwardTxnSeq();
			long msg_seq = System.nanoTime();
			
			event = kPNSDao.selectMsgTrmForwardTxnByMsgId(event);
			
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
			kPNSDao.insertMsgTrmForwardTxn(event);
		}
	}
	
	/**
	 * 마스터 PNS등록아이디 조회 (단건)
	 * @param svcTgtSeq    서비스대상일련번호
	 * @return
	 */
	public String selectMasterPnsRegId(long svcTgtSeq) {
	    
	    String pnsRegId = selectMasterPnsRegID(svcTgtSeq, null, null, null);
	    
	    return pnsRegId;
	}
	
	/**
	 * 마스터 PNS등록아이디 조회 (단건)
	 * @param svcTgtSeq    서비스대상일련번호
	 * @param dstrCd       지역코드
	 * @param svcThemeCd   서비스테마코드
	 * @param unitSvcCd    단위서비스코드
	 * @return
	 */
	public String selectMasterPnsRegID(long svcTgtSeq, String dstrCd, String svcThemeCd, String unitSvcCd) {
	    
	    dstrCd     = dstrCd != null ? dstrCd : CommonConstants.DSTR_CD;
	    svcThemeCd = svcThemeCd != null ? svcThemeCd : CommonConstants.SVC_THEME_CD;
	    unitSvcCd  = unitSvcCd != null ? unitSvcCd : CommonConstants.UNIT_SVC_CD;
	    
	    String pnsRegId = userDao.selectMasterPnsRegId(svcTgtSeq, dstrCd, svcThemeCd, unitSvcCd);
	    
	    return pnsRegId;
	}
	
	/**
	 * 슬레이브 PNS등록아이디 조회 (LIST)
	 * @param svcTgtSeq    서비스대상일련번호
	 * @return
	 */
	public List<String> selectSlavePnsRegId(long svcTgtSeq) {
	    
	    List<String> pnsRegId = selectSlavePnsRegId(svcTgtSeq, null, null, null);
	    
	    return pnsRegId;
	}
	
    /**
     * 슬레이브 PNS등록아이디 조회 (LIST)
     * @param svcTgtSeq     서비스대상일련번호
     * @param dstrCd        지역코드
     * @param svcThemeCd    서비스테마코드
     * @param unitSvcCd     단위서비스코드
     * @return
     */
    public List<String> selectSlavePnsRegId(long svcTgtSeq, String dstrCd, String svcThemeCd, String unitSvcCd) {
        
        dstrCd     = dstrCd != null ? dstrCd : CommonConstants.DSTR_CD;
        svcThemeCd = svcThemeCd != null ? svcThemeCd : CommonConstants.SVC_THEME_CD;
        unitSvcCd  = unitSvcCd != null ? unitSvcCd : CommonConstants.UNIT_SVC_CD;
        
        List<String> pnsRegId = userDao.selectSlavePnsRegId(svcTgtSeq, dstrCd, svcThemeCd, unitSvcCd);
        return pnsRegId;
    }
	
    /**
     * 마스터, 슬레이브 PNS등록아이디 조회
     * @param svcTgtSeq     서비스대상일련번호
     * @return
     */
    public List<String> selectMasterAndSlavePnsRegId(long svcTgtSeq) {
        List<String> pnsRegIdList = selectMasterAndSlavePnsRegId(svcTgtSeq, null, null, null);
        return pnsRegIdList;
    }
    
    /**
     * 마스터, 슬레이브 PNS등록아이디 조회
     * @param svcTgtSeq     서비스대상일련번호
     * @param dstrCd        지역코드
     * @param svcThemeCd    서비스테마코드
     * @param unitSvcCd     단위서비스코드
     * @return
     */
    public List<String> selectMasterAndSlavePnsRegId(long svcTgtSeq, String dstrCd, String svcThemeCd, String unitSvcCd) {
        
        dstrCd     = dstrCd != null ? dstrCd : CommonConstants.DSTR_CD;
        svcThemeCd = svcThemeCd != null ? svcThemeCd : CommonConstants.SVC_THEME_CD;
        unitSvcCd  = unitSvcCd != null ? unitSvcCd : CommonConstants.UNIT_SVC_CD;
        
        List<String> pnsRegIdList  = userDao.selectMasterSlavePnsRegId(svcTgtSeq, dstrCd, svcThemeCd, unitSvcCd, null);
        
        return pnsRegIdList;
    }
	
    /**
     * 로그인한 사용자를 제외한 마스터/슬레이브 PNS등록아이디 조회
     * @param svcTgtSeq     서비스대상일련번호
     * @return
     */
    public List<String> selectExcludeMyInfoPnsRegId(AuthToken token, long svcTgtSeq) {
        List<String> pnsRegIdList = selectExcludeMyInfoPnsRegId(token, svcTgtSeq, null, null, null);
        return pnsRegIdList;
    }
    
    /**
     * 로그인한 사용자를 제외한 마스터/슬레이브 PNS등록아이디 조회
     * @param svcTgtSeq     서비스대상일련번호
     * @param dstrCd        지역코드
     * @param svcThemeCd    서비스테마코드
     * @param unitSvcCd     단위서비스코드
     * @return
     */
    public List<String> selectExcludeMyInfoPnsRegId(AuthToken token, long svcTgtSeq, String dstrCd, String svcThemeCd, String unitSvcCd) {
        
        dstrCd     = dstrCd != null ? dstrCd : CommonConstants.DSTR_CD;
        svcThemeCd = svcThemeCd != null ? svcThemeCd : CommonConstants.SVC_THEME_CD;
        unitSvcCd  = unitSvcCd != null ? unitSvcCd : CommonConstants.UNIT_SVC_CD;
        
        List<String> pnsRegIdList  = userDao.selectMasterSlavePnsRegId(svcTgtSeq, dstrCd, svcThemeCd, unitSvcCd, token.getUserNoLong());
        
        return pnsRegIdList;
    }

}
