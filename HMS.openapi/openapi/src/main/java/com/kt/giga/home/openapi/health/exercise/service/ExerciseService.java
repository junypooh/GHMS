package com.kt.giga.home.openapi.health.exercise.service;

/*import java.util.*;

import org.slf4j.*;
import org.json.simple.*;

import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.beans.factory.annotation.*;

import com.fasterxml.jackson.annotation.JsonInclude.*;

import com.kt.giga.home.openapi.util.*;
import com.kt.giga.home.openapi.common.APIEnv;
import com.kt.giga.home.openapi.health.exercise.dao.*;
import com.kt.giga.home.openapi.health.user.service.*;

import com.kt.health.smcp.gw.ca.domn.spotdev.SpotDevBasVO;*/

import java.util.*;

import org.slf4j.*;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.*;

import com.fasterxml.jackson.annotation.JsonInclude.*;

import com.kt.giga.home.openapi.util.*;
import com.kt.giga.home.openapi.service.*;
import com.kt.giga.home.openapi.health.exercise.dao.*;
import com.kt.giga.home.openapi.health.user.service.HealthUserService;
import com.kt.giga.home.openapi.vo.spotdev.query.SpotDevBasVO;
import com.kt.giga.home.openapi.vo.spotdev.query.SpotDevBasQueryVO;

/**
 * EC 장치관리 서비스
 * @author 김용성
 *
 */
@Service
public class ExerciseService {
	
	private Logger log = LoggerFactory.getLogger(this.getClass());	
	
	@Autowired
	private ECService ecService;
	
	@Autowired
	private ExerciseDao exerciseDao;	
	
	@Autowired
	private HealthUserService healthUserService;		
	
	public void update(String authToken, Map<String, Object> exercise) throws APIException, Exception {
		
		AuthToken token = healthUserService.checkLoginAuthToken(authToken);
		
		String unitSvcCd 	= token.getSvcCode();
		String svcTgtId		= token.getTelNo();
		
		if(unitSvcCd != null && unitSvcCd.length() == 9) {
			unitSvcCd = unitSvcCd.substring(6);
		}			
		
		exercise.put("unitSvcCd"	, unitSvcCd);
		exercise.put("svcTgtId"		, svcTgtId);
		
		this.update(exercise);
	}	
	
	public void update(Map<String, Object> exercise) throws APIException, Exception {
		
		try {
			
			String crud = exercise.get("crud").toString();
			
			Map<String, Object> service = exerciseDao.getService(exercise);
			service.put("svc_tgt_seq", Long.valueOf(service.get("svc_tgt_seq").toString()));
			
			exercise.put("svcTgtSeq", service.get("svc_tgt_seq"));
			exercise.put("spotDevId", exercise.get("svcTgtId"));
			
			Map<String, Object> device = exerciseDao.getDevice(exercise);
			device.put("spot_dev_seq", Long.valueOf(device.get("spot_dev_seq").toString()));
			device.put("dev_model_seq", Integer.valueOf(device.get("dev_model_seq").toString()));
			
			Map<String, Object> deviceModel = exerciseDao.getDeviceModel((int)device.get("dev_model_seq"));			
			
			SpotDevBasVO spotDevBas = new SpotDevBasVO();
			
			spotDevBas.setSvcTgtSeq((Long)service.get("svc_tgt_seq"));
			spotDevBas.setSpotDevSeq((Long)device.get("spot_dev_seq"));
			spotDevBas.setSpotDevId(device.get("spot_dev_id").toString());
			spotDevBas.setGwCnctId("HOME_FITNESS");
			
			spotDevBas.setSpotDevNm(device.get("dev_nm").toString());
			spotDevBas.setSttusCd("0001");
			spotDevBas.setColecCyclTime(30);
			spotDevBas.setDevConnTypeCd("02"); //장치연결유형 고정 : 02
			spotDevBas.setRowRstrtnYn("N");
			spotDevBas.setMakrNm(deviceModel.get("terml_makr_nm").toString());
			spotDevBas.setModelNm(deviceModel.get("dev_model_nm").toString());
			spotDevBas.setFrmwrVerNo("01.00");
			spotDevBas.setAthnFormlCd("0004");
			
			if(exercise.get("pinNo") != null) {
				spotDevBas.setAthnRqtNo(exercise.get("pinNo").toString());
			}
			
			spotDevBas.setAthnNo(device.get("dev_uu_id").toString());
			spotDevBas.setUseYn("Y");	
			
			exercise.clear();
			
			String jsonStr = ObjectConverter.toJSONString(spotDevBas, Include.ALWAYS);
			
			log.info("======================= SpotDevBasVO Request =======================");
			log.info("\n" + jsonStr);
			log.info("====================================================================");	
			
			try {
				
				SpotDevBasQueryVO result = null;
				
				switch(crud) {
					case "insert":
						result = ecService.sendInsertQueryToEC(spotDevBas);
						break;
					case "update":
						result = ecService.sendUpdateQueryToEC(spotDevBas);
						break;
					case "delete":
						result = ecService.sendDeleteQueryToEC(spotDevBas);
						break;
					case "select":
						result = ecService.sendSelectQueryToEC(spotDevBas);
						break;					
				}
				/*
				 * ec api response code
				 * 성공인 경우 - 캐쉬를 보지 않기 때문에 추가함(110,120,121)
				 * 100 						: select,update,delete,insert
				 * 120(정상처리 캐쉬전파오류) 		: update,delete,insert
				 * 121(정상처리 캐쉬전파일부오류) 	: update,delete,insert
				 * 110(정상처리,중복데이터 삭제처리) 	: insert
				 */				
				if(result != null) {
					if("100".equals(result.getRpyCode()) ) {
						if(crud.equals("select")) {
							Map<String, Object> resultMap  = ObjectConverter.toJSON(result.getSpotDevBasVO());
							resultMap.put("resultCode", "1");
							exercise.put("result", result);							
						} else {
							exercise.put("resultCode", "1");
						}
					} else if("120".equals(result.getRpyCode()) || "121".equals(result.getRpyCode())) {
						if(crud.equals("update") || crud.equals("delete") || crud.equals("insert") ) {
							exercise.put("resultCode", "1");
						}else{
							exercise.put("resultCode", "3");
						}
					} else if("110".equals(result.getRpyCode()) ) {
						if(crud.equals("insert")) {
							exercise.put("resultCode", "1");
						}else{
							exercise.put("resultCode", "3");
						}
					}else{
						
						exercise.put("resultCode", "3");
					}
					
				} else {
					exercise.put("resultCode", "3");
				}
				
			} catch(Exception e) {
				log.error("======================= Error =======================");
				log.error(e.getMessage());
				log.error("=====================================================");				
				exercise.put("resultCode", "3");				
			}
			
		} catch(Exception e) {
			log.error("======================= Error =======================");
			log.error(e.getMessage());
			log.error("=====================================================");
			exercise.put("resultCode", "2");			
		}
	}

/*	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private RestTemplate restTemplate;	
	
	@Autowired
	private APIEnv apiEnv;
	
	@Autowired
	private ExerciseDao exerciseDao;
	
	@Autowired
	private HealthUserService healthUserService;		
	
	private static <T> HttpEntity<T> getRequestEntity(T body) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		return new HttpEntity<T>(body, headers);
	}	
	
	public void update(Map<String, Object> exercise) throws APIException, Exception {
		
		String resultCode = ""; 
		
		try {
			
			Map<String, Object> service = exerciseDao.getService(exercise);
			service.put("svc_tgt_seq", Long.valueOf(service.get("svc_tgt_seq").toString()));
			
			exercise.put("svcTgtSeq", service.get("svc_tgt_seq"));
			exercise.put("spotDevId", exercise.get("svcTgtId"));
			
			Map<String, Object> device = exerciseDao.getDevice(exercise);
			device.put("spot_dev_seq", Long.valueOf(device.get("spot_dev_seq").toString()));
			device.put("dev_model_seq", Integer.valueOf(device.get("dev_model_seq").toString()));
			
			Map<String, Object> deviceModel = exerciseDao.getDeviceModel((int)device.get("dev_model_seq"));
			
			String requestURI = apiEnv.getProperty("ec.url.query." + exercise.get("crud").toString()); 
			
			SpotDevBasVO vo = new SpotDevBasVO();
			
			// 복합키 
			vo.setSvcTgtSeq((Long)service.get("svc_tgt_seq"));
			//vo.setSpotDevSeq(1L); // 1로 고정
			vo.setSpotDevSeq((Long)device.get("spot_dev_seq"));
			vo.setSpotDevId(device.get("spot_dev_id").toString());
			vo.setGwCnctId("HOME_FITNESS");
			
//			vo.setSpotDevNm("홈헬스케어");
			vo.setSpotDevNm(device.get("dev_nm").toString());
			vo.setSttusCd("0001");
			vo.setColecCyclTime(30);
			vo.setDevConnTypeCd("02"); //장치연결유형 고정 : 02
			vo.setRowRstrtnYn("N");
			vo.setMakrNm(deviceModel.get("terml_makr_nm").toString());
//			vo.setModelNm("Dalli-go001");
			vo.setModelNm(deviceModel.get("dev_model_nm").toString());
			vo.setFrmwrVerNo("01.00");
			vo.setAthnFormlCd("0004");
			
			if(exercise.get("pinNo") != null) {
				vo.setAthnRqtNo(exercise.get("pinNo").toString());
			}
			
			vo.setAthnNo(device.get("dev_uu_id").toString());
			vo.setUseYn("Y");	
			
			String voJSON = ObjectConverter.toJSONString(vo, Include.ALWAYS);
			
			log.info("==================================== VO ============================================");
			log.info("\n" + voJSON);
			log.info("========================================================================================");				
			
			boolean isSelect = exercise.get("crud").toString().equals("select") ? true : false;
			
			Map<String, Object> response = null;
			
			try {
				
				exercise.clear();
				response = restTemplate.postForObject(requestURI, this.getRequestEntity(vo), Map.class);
				
				if(response != null) {
					if(response.get("rpyCode") != null && response.get("rpyCode").toString().equals("100")) {
						
						if(isSelect) {
							
							Map<String, Object> result = (Map)response.get("spotDevBasVO");
							
//							result.remove("spotDevDtlVOs");
//							result.remove("spotDevCommChDtlVOs");
							result.remove("emptyData");
							result.put("resultCode", "1");
							
							exercise.put("result", result);
							
						} else {
							exercise.put("resultCode", "1");
						}
						
					} else {
						exercise.put("resultCode", "3");
					}
					
					String jsonStr = ObjectConverter.toJSONString(response, Include.ALWAYS);
					
					log.info("==================================== Result ============================================");
					log.info("\n" + jsonStr);
					log.info("========================================================================================");						
					
				} else {
					exercise.put("resultCode", "3");
				}
				
			} catch(Exception e) {
				log.error("==================================== Error ============================================");
				log.error(e.getMessage());
				log.error("=======================================================================================");				
				exercise.put("resultCode", "3");
			}	
			
		} catch(Exception e) {
			log.error("==================================== Error ============================================");
			log.error(e.getMessage());
			log.error("=======================================================================================");
			exercise.put("resultCode", "2");
		}
	}
	
	public void update(String authToken, Map<String, Object> exercise) throws APIException, Exception {
		
		AuthToken token = healthUserService.checkLoginAuthToken(authToken);
		
		String unitSvcCd 	= token.getSvcCode();
		String svcTgtId		= token.getTelNo();
		
		if(unitSvcCd != null && unitSvcCd.length() == 9) {
			unitSvcCd = unitSvcCd.substring(6);
		}			
		
		exercise.put("unitSvcCd"	, unitSvcCd);
		exercise.put("svcTgtId"		, svcTgtId);
		
		this.update(exercise);
	}*/
}
