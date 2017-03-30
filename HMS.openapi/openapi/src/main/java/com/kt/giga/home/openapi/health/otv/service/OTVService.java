package com.kt.giga.home.openapi.health.otv.service;

import java.util.*;

import org.slf4j.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonInclude.*;

import com.kt.giga.home.openapi.util.*;
import com.kt.giga.home.openapi.service.ECService;
import com.kt.giga.home.openapi.service.APIException;
import com.kt.giga.home.openapi.vo.row.*;
import com.kt.giga.home.openapi.vo.cnvy.*;
import com.kt.giga.home.openapi.common.APIEnv;
import com.kt.giga.home.openapi.health.otv.dao.*;
import com.kt.giga.home.openapi.health.user.service.*;
import com.kt.giga.home.openapi.health.exercise.service.*;

/**
 * 올레TV 관리 서비스
 * @author 김용성
 *
 */
@Service
public class OTVService {
	
	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private APIEnv apiEnv;
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private OTVDao otvDao;
	
	@Autowired
	private ECService ecService;
	
	@Autowired
	private ExerciseService exerciseService;
	
	@Autowired
	private HealthUserService healthUserService;
	
	@SuppressWarnings("unchecked")
	private Map<String, Object> sendMessage(String requestURI, Map<String, Object> msg) throws Exception {
		String resultStr = restTemplate.getForObject(requestURI, String.class, msg);
		Map<String, Object> result = ObjectConverter.toJSON(resultStr);
		return result;		
	}
	
	/**
	 * TV App 제어
	 * @param authToken 인증토큰
	 * @param otv TV App 제어정보
	 * @return 제어 처리결과 Map
	 * @throws APIException
	 * @throws Exception
	 */
	public Map<String, Object> control(String authToken, Map<String, Object> otv) throws APIException, Exception {
		
		AuthToken token = healthUserService.checkLoginAuthToken(authToken);
		// log용 데이터
		String logTelNo = token.getTelNo();
		String logUserNo = token.getUserNo();
		
		Validation.checkParameter(otv, "said", "appId", "cmd");
		
		String svcCode	= token.getSvcCode();		
		
		String said 	= otv.get("said").toString(); // 테스트용  TT121030009, TT140519003
		String appId 	= otv.get("appId").toString();
		String cmd		= otv.get("cmd").toString();
		
		String requestURI = null;
		Map<String, Object> result = new HashMap<>();
		
		try {
			
			Map<String, Object> msg = new HashMap<>();
			
			msg.put("SVC_CD"	, svcCode);
			msg.put("SAID"		, said);
			//msg.put("IS_DEV"	, "Y"); // 개발용 STB IP 조회시 사용				
			
			// STB IP 조회
			requestURI = apiEnv.getProperty("cc.url.stbip") + "?SVC_CD={SVC_CD}&SAID={SAID}"; 
			Map<String, Object> stb = this.sendMessage(requestURI, msg); 
			
			String jsonStr = ObjectConverter.toJSONString(stb, Include.ALWAYS);
			
			log.info("======================= STB IP 조회 =======================");
			log.info("telNo : "+logTelNo);
			log.info("mbrSeq : "+logUserNo);
			log.info("\n" + jsonStr);
			log.info("=========================================================");						
			
			if(stb.get("RESULT_CD").toString().equals("000")) {
				
				String stbIp = stb.get("STB_IP").toString();
				msg.put("STB_IP", stbIp);
					
				// 현재 시청 상태 조회
				requestURI = apiEnv.getProperty("cc.url.state") + "?SVC_CD={SVC_CD}&SAID={SAID}&STB_IP={STB_IP}";
				Map<String, Object> state = this.sendMessage(requestURI, msg);
				
				jsonStr = ObjectConverter.toJSONString(state, Include.ALWAYS);
				
				log.info("=======================  현재 시청 상태 조회 =======================");
				log.info("telNo : "+logTelNo);
				log.info("mbrSeq : "+logUserNo);
				log.info("\n" + jsonStr);
				log.info("============================================================");						
				
				if(!state.get("RESULT_CD").toString().equals("000")) {
					throw new Exception("공용제어 현재 시청 상태 조회 실패");
				}
				
				if(state.get("STB_STATE").toString().equals("2")) {
				
					// 매쉬업 명령 보내기
					requestURI  = apiEnv.getProperty("cc.url.exec") + "?SVC_CD={SVC_CD}&SAID={SAID}&STB_IP={STB_IP}";
					requestURI += "&FROM_APP={FROM_APP}&TO_APP={TO_APP}&UMT_MSG={UMT_MSG}";
					
					String fromApp	= "3013"; // 공용제어
					String toApp	= "3020"; // 매쉬업매니저 : 3020
					String umtStartMsg	= "mashup_startApplication|4e30." + toApp + "|" + appId + "|id=3";
					String umtStopMsg	= "mashup_stopApplication|4e30." + toApp + "|" + appId + "|";
					
					msg.put("FROM_APP"	, fromApp); 
					msg.put("TO_APP"	, toApp);
					msg.put("UMT_MSG"	, umtStartMsg);
					
					if(cmd.equals("1")) { // start
						msg.put("UMT_MSG", umtStartMsg);
						stb = this.sendMessage(requestURI, msg);
					} else if(cmd.equals("2")) { // stop
						msg.put("UMT_MSG", umtStopMsg);
						stb = this.sendMessage(requestURI, msg);
						
					} else if(cmd.equals("3")) { // restart
						msg.put("UMT_MSG", umtStopMsg);
						stb = this.sendMessage(requestURI, msg);
						msg.put("UMT_MSG", umtStartMsg);
						stb = this.sendMessage(requestURI, msg);
					}			
					
					if(!stb.get("RESULT_CD").toString().equals("000"))
						throw new Exception(stb.toString());
					
					jsonStr = ObjectConverter.toJSONString(stb, Include.ALWAYS);
					
					log.info("======================= 매쉬업 명령 보내기 =======================");
					log.info("telNo : "+logTelNo);
					log.info("mbrSeq : "+logUserNo);
					log.info("\n" + jsonStr);
					log.info("===========================================================");						
					
					result.put("ctlResult", 1); // 성공
				} else {
					result.put("ctlResult", 4); // STB 동작모드 상태아님(Off 혹은 대기)
				}
			} else {
				result.put("ctlResult", 3); // STB IP 획득실패
			}				
			
		} catch(Exception ex) {
			result.put("ctlResult", 2); // 실패
			log.error(ex.getMessage());
		}
		
		return result;
	}
	
	/**
	 * TV App 상태 전달
	 * @param otv TV App 상태정보
	 * @throws APIException
	 * @throws Exception
	 */
	public void sendStatus(Map<String, Object> otv) throws APIException, Exception {
		
		Validation.checkParameter(otv, "svcCode", "telNo", "endSttus");
		
		String jsonStr = "";
		Double tvStatus = 0d; 
		
		if(otv.get("endSttus").toString().equals("1")) {
			tvStatus = 7d;
		} else if (otv.get("endSttus").toString().equals("2")) {
			tvStatus = 8d;
		} else {
			throw new APIException("Invalid endSttus", HttpStatus.BAD_REQUEST);
		}	
		
		String unitSvcCd = otv.get("svcCode").toString();
		
		if(unitSvcCd != null && unitSvcCd.length() == 9) {
			unitSvcCd = unitSvcCd.substring(6);
		}
		
		otv.put("unitSvcCd"	, unitSvcCd);
		otv.put("svcTgtId"	, otv.get("telNo").toString());
		
		Map<String, Object> service = otvDao.getService(otv);
		
		Long svcTgtSeq = Long.valueOf(service.get("svc_tgt_seq").toString());
		
		otv.put("svcTgtSeq", svcTgtSeq);
		otv.put("spotDevId", otv.get("telNo").toString());
		
		Map<String, Object> device = otvDao.getDevice(otv);
		
		Long spotDevSeq = Long.valueOf(device.get("spot_dev_seq").toString());	// Long spotDevSeq = 1L;  
		
		ItgCnvyData itgCnvyData = new ItgCnvyData();
		
		SpotDevCnvyData spotDevCnvyData = new SpotDevCnvyData();
		spotDevCnvyData.setSvcTgtSeq(svcTgtSeq);
		spotDevCnvyData.setSpotDevSeq(spotDevSeq);		
		
		CnvyRow cnvyRow = new CnvyRow();
		
		SttusData sttusData = new SttusData();
		sttusData.setSnsnTagCd("20000004");
		sttusData.setRlNumVal(tvStatus);
		
		List<SttusData> sttusDatas = new ArrayList<>();
		sttusDatas.add(sttusData);
		
		cnvyRow.setSttusDatas(sttusDatas);
		
		spotDevCnvyData.getCnvyRows().add(cnvyRow);
		itgCnvyData.getSpotDevCnvyDatas().add(spotDevCnvyData);		
		
		jsonStr = ObjectConverter.toJSONString(itgCnvyData, Include.ALWAYS);
		
		log.info("==================================== itgCnvyData =======================================");
		log.info("\n" + jsonStr);
		log.info("========================================================================================");	
		
		ecService.sendRTimeControlToEC(itgCnvyData);
	}
	
	/**
	 * STB에 연결된 전화번호 조회
	 * @param otv TV App 계약정보
	 * @return TV App 상태정보
	 * @throws APIException
	 * @throws Exception
	 */
	public Map<String, Object> getStatus(Map<String, Object> otv) throws APIException, Exception {
		
		Validation.checkParameter(otv, "svcCode", "said");
		
		// 푸쉬푸쉬 조회 			
		String requestURI = apiEnv.getProperty("push.url.user") + "?said={said}";
		Map<String, Object> push = this.sendMessage(requestURI, otv); 
		
		if(push.get("result").toString().toLowerCase().equals("true")) {
			push.put("resultCode", "1");
		} else if(push.get("result").toString().toLowerCase().equals("false")) {
			push.put("resultCode", "2");
		} else {
			throw new APIException("Push Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		push.remove("result");
		
		return push;
	}

}
