package com.kt.giga.home.cms.monitor.controller;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.kt.giga.home.cms.common.service.APIEnv;
import com.kt.giga.home.cms.common.service.AuthToken;
import com.kt.giga.home.cms.monitor.service.SignalCheckService;
import com.kt.giga.home.cms.util.ObjectConverter;
import com.kt.giga.home.cms.vo.cnvy.CnvyRow;
import com.kt.giga.home.cms.vo.cnvy.ItgCnvyData;
import com.kt.giga.home.cms.vo.cnvy.SpotDevCnvyData;
import com.kt.giga.home.cms.vo.row.ContlData;

@Controller
@RequestMapping("/monitor")
public class SignalCheckController {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private APIEnv apiEnv; 
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private SignalCheckService signalCheckService; 
	
	//올레 홈캠 카메라 로그 확인 요청 전송
	@ResponseBody
	@RequestMapping("/signalCheckRequest")
	public Map<String, Object> signalCheckRequest(@RequestBody Map<String, Object> requestInfo) {
		Map<String, Object> result = new HashMap<>();
		
		Long svcTgtSeq = 0L, spotDevSeq = 0L;
		String devUUID = "", authTokenStr = "";
		
		int collectCount	= Integer.parseInt(requestInfo.get("collectCount").toString());
		int collectPeriod 	= Integer.parseInt(requestInfo.get("collectPeriod").toString());
		
		try{
			String jsonStr = "";
			String[] serverList = apiEnv.getProperty("openapi.serverList").split("\\|");
			String url = apiEnv.getProperty("openapi.hcam.devices.control.rtime");
			List<Map<String, Object>> signalCheckList = (List<Map<String, Object>>)requestInfo.get("items");
			
			result.put("code"	, 200);
			result.put("msg"	, "success");
			
			JSONArray totalArr 		= new JSONArray();
			JSONArray successArr 	= new JSONArray();
			JSONArray failArr 		= new JSONArray();
			int totalCnt			= signalCheckList.size();
			int successCnt			= 0;
			int failCnt				= 0;

			for(Map<String, Object> signalCheck : signalCheckList) {
				devUUID 	= signalCheck.get("devUUID").toString();
				svcTgtSeq	= Long.parseLong(signalCheck.get("svcTgtSeq").toString());
				spotDevSeq	= Long.parseLong(signalCheck.get("spotDevSeq").toString());
				
				AuthToken authToken = AuthToken.encodeAuthToken(devUUID, "0000", null, null, "cmsuserrequest", "001");
				authTokenStr = authToken.getToken();
				
			    HttpHeaders requestHeaders = new HttpHeaders();
			    requestHeaders.add("Content-Type"	, "application/json; charset=utf-8");
			    requestHeaders.add("authToken"		, authTokenStr);				
			    
				ItgCnvyData itgCnvyData = new ItgCnvyData();
				
				SpotDevCnvyData spotDevCnvyData = new SpotDevCnvyData();
				spotDevCnvyData.setDevUUID(devUUID);
				spotDevCnvyData.setSvcTgtSeq(svcTgtSeq);
				spotDevCnvyData.setSpotDevSeq(spotDevSeq);		
				
				CnvyRow cnvyRow = new CnvyRow();
				
				ContlData contlData = new ContlData();
				contlData.setSnsnTagCd("30000011");	//wifi 로그전송 센싱태그 31000011 (api 11번 참고)
//				contlData.setSnsnTagCd("30000010");	//wifi 품질로그 센싱태그 31000010 (api 12번 참고)
				contlData.setContlVal(0.0);
				
				List<ContlData> contlDatas = new ArrayList<>();
				contlDatas.add(contlData);
				
				HashMap<String, Object> rowExtension = new HashMap<>();
				rowExtension.put("collectCount"	, collectCount);				
				rowExtension.put("collectPeriod", collectPeriod * 60 );	//단위 : 초			
				rowExtension.put("logMode", 1);	//로그 on : 1 / off : 0		
				
				cnvyRow.setContlDatas(contlDatas);
				cnvyRow.setRowExtension(rowExtension);
				
				spotDevCnvyData.getCnvyRows().add(cnvyRow);
				itgCnvyData.getSpotDevCnvyDatas().add(spotDevCnvyData);		
				
				String itgCnvyDataStr = ObjectConverter.toJSONString(itgCnvyData, Include.ALWAYS);
				
				logger.debug("================================= Request Body Start  ==============================================");
				logger.debug(itgCnvyDataStr);
				logger.debug("================================= Request Body End   =============================================");				
				
				HttpEntity<String> requestEntity = new HttpEntity<String>(itgCnvyDataStr, requestHeaders);
				ResponseEntity<Map> responseEntity = restTemplate.exchange(serverList[0] + url, HttpMethod.PUT, requestEntity, Map.class);
				
				HttpHeaders reponseHeaders = responseEntity.getHeaders();
				Map<String, Object> reponseBody =  responseEntity.getBody();
				
				jsonStr = ObjectConverter.toJSONString(reponseHeaders, Include.ALWAYS);
				logger.debug("================================= Reponse Headers Start ==============================================");
				logger.debug(jsonStr);
				logger.debug("================================= Reponse Headers End   =============================================");
				
				jsonStr = ObjectConverter.toJSONString(reponseBody, Include.ALWAYS);
				logger.debug("================================= Reponse Body Start ==============================================");
				logger.debug(jsonStr);
				logger.debug("================================= Reponse Body End   =============================================");				

				JSONObject 	reponseBodyJson 			= ObjectConverter.toJSON(reponseBody);
				JSONArray 	spotDevsJsonArray			= (JSONArray) reponseBodyJson.get("spotDevs");
				JSONObject 	spotDevsJson 				= (JSONObject) spotDevsJsonArray.get(0);
				JSONArray 	spotDevDtlsJsonArray		= (JSONArray) spotDevsJson.get("spotDevDtls");
				JSONObject 	spotDevDtlsJson				= (JSONObject) spotDevDtlsJsonArray.get(0);
				String 		atribVal					= (String)spotDevDtlsJson.get("atribVal");
				
				logger.debug("================================= camera status ==============================================");
				System.out.println(  atribVal  );
				logger.debug("================================= camera status ==============================================");
				
				JSONObject jsonObj 	= new JSONObject();
				jsonObj.put(devUUID, atribVal);

				totalArr.add(jsonObj);

				if(atribVal.equals("0")){

					failCnt++;
					result.put("code"	, 201);
					failArr.add(devUUID);
				}else{
					
					successCnt++;
					successArr.add(devUUID);
				}
			}
			result.put("totalCnt"	, totalCnt);
			result.put("successCnt"	, successCnt);
			result.put("failCnt"	, failCnt);
			result.put("totalArr"	, totalArr);
			result.put("successArr"	, successArr);
			result.put("failArr"	, failArr);
			
		} catch(Exception e) {
			result.put("code"	, 500);
			result.put("msg"	, "server error");		
			
			logger.debug("================================= Exception Log ==============================================");
			System.out.println(  e  );
			logger.debug("================================= Exception Log ==============================================");
		}
		
		return result;
	}
	
	//올레 홈캠 카메라 로그 확인 요청
	@RequestMapping("/signalCheckList")
	public String signalCheckList(Model model,@RequestParam Map<String, Object> searchInfo) throws ParseException{
		
		Long telCnt = 0L;
		String mbrId = "", telNo = "", spotDevId = "", devConStat = "", devCctvMac="";
		List<Map<String, Object>> signalCheckList = signalCheckService.getList(searchInfo);
		
		for(Map<String, Object> signalCheck : signalCheckList) {
			mbrId 		= signalCheck.get("mbrId") != null ? signalCheck.get("mbrId").toString() : "";
			telCnt 		= signalCheck.get("telCnt") != null ? (long)signalCheck.get("telCnt") : 0;
			telNo 		= signalCheck.get("telNo") != null ? signalCheck.get("telNo").toString() : "";
			spotDevId 	= signalCheck.get("spotDevId") != null ? signalCheck.get("spotDevId").toString() : "";			
			devConStat 	= signalCheck.get("devConStat") != null ? signalCheck.get("devConStat").toString() : "";		
			devCctvMac	= signalCheck.get("devCctvMac") != null ? signalCheck.get("devCctvMac").toString() : "";	
			
			if(mbrId.length() > 3) {
				mbrId = mbrId.substring(0, mbrId.length() - 3) + "***";
				signalCheck.put("mbrId", mbrId);
			}
			
			if(spotDevId.length() > 4) {
				spotDevId = spotDevId.substring(0, spotDevId.length() - 4) + "****";
				signalCheck.put("spotDevId", spotDevId);				
			}
			
			if(telNo.length() > 4){
				telNo = telNo.substring(0, telNo.length() - 4) + "****";
			}			
			
			if(telCnt > 1) {
				telNo = telNo + "(외 " + String.valueOf(telCnt - 1) + ")";
			}			
			
			if(devConStat.equals("1")) {
				signalCheck.put("devConStat", "접속");
			}else{
				signalCheck.put("devConStat", "미접속");
			}
			
			signalCheck.put("telNo", telNo);
		}

		model.addAttribute("signalCheckList", signalCheckList);
		model.addAttribute("signalCheckCount", signalCheckList.size());
		
		return "/monitor/signalCheckList";
	}
}
