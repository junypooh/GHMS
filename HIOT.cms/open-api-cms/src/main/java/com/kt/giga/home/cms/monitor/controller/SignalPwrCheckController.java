package com.kt.giga.home.cms.monitor.controller;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
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
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.kt.giga.home.cms.common.service.APIEnv;
import com.kt.giga.home.cms.common.service.AuthToken;
import com.kt.giga.home.cms.monitor.service.SignalCheckService;
import com.kt.giga.home.cms.util.ObjectConverter;

@Controller
@RequestMapping("/monitor")
public class SignalPwrCheckController {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private APIEnv apiEnv; 
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private SignalCheckService signalCheckService; 
	
	//올레 홈캠 카메라 신호세기 확인 요청 전송
	@ResponseBody
	@RequestMapping("/signalPwrCheckRequest")
	public Map<String, Object> signalPwrCheckRequest(@RequestBody Map<String, Object> requestInfo) {
		Map<String, Object> result = new HashMap<>();
		
		Long svcTgtSeq = 0L, spotDevSeq = 0L;
		String devUUID = "", authTokenStr = "";
		
		int collectCount	= Integer.parseInt(requestInfo.get("collectCount").toString());
		int collectPeriod 	= Integer.parseInt(requestInfo.get("collectPeriod").toString());
		
		try{
			logger.info("## signalCheckList size");
			
			String jsonStr = "";
			String[] serverList = apiEnv.getProperty("openapi.serverList").split("\\|");
			String url = apiEnv.getProperty("openapi.hcam.devices.control.retv");
			List<Map<String, Object>> signalCheckList = (List<Map<String, Object>>)requestInfo.get("items");
			
			logger.info("## signalCheckList size" + signalCheckList.size());
			
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
			    
//				ItgCnvyData itgCnvyData = new ItgCnvyData();
//				
//				SpotDevCnvyData spotDevCnvyData = new SpotDevCnvyData();
//				spotDevCnvyData.setDevUUID(devUUID);
//				spotDevCnvyData.setSvcTgtSeq(svcTgtSeq);
//				spotDevCnvyData.setSpotDevSeq(spotDevSeq);		
//				
//				CnvyRow cnvyRow = new CnvyRow();
//				
//				ContlData contlData = new ContlData();
////				contlData.setSnsnTagCd("30000011");	//wifi 로그전송 센싱태그 30000011 (api 11번 참고)
//				contlData.setSnsnTagCd("31000010");	//wifi 품질로그 센싱태그 31000010 (api 12번 참고)
//				contlData.setContlVal(0.0);
//				
//				List<ContlData> contlDatas = new ArrayList<>();
//				contlDatas.add(contlData);
//				
//				HashMap<String, Object> rowExtension = new HashMap<>();
//				rowExtension.put("collectCount"	, collectCount);				
//				rowExtension.put("collectPeriod", collectPeriod);				
//				
//				cnvyRow.setContlDatas(contlDatas);
//				cnvyRow.setRowExtension(rowExtension);
//				
//				spotDevCnvyData.getCnvyRows().add(cnvyRow);
//				itgCnvyData.getSpotDevCnvyDatas().add(spotDevCnvyData);		
//				
//				String itgCnvyDataStr = ObjectConverter.toJSONString(itgCnvyData, Include.ALWAYS);//결과 : 배열 스트링
			    
			    String itgCnvyDataStr = "?devUUID="+devUUID+"&snsnTagCd=31000010";
				
				logger.debug("================================= Request Body Start  ==============================================");
				logger.debug(itgCnvyDataStr);
				logger.debug("================================= Request Body End   =============================================");				
				
				HttpEntity<String> requestEntity = new HttpEntity<String>(requestHeaders);
				ResponseEntity<Map> responseEntity = restTemplate.exchange(serverList[0] + url + itgCnvyDataStr, HttpMethod.GET, requestEntity, Map.class);
				
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
			e.printStackTrace();
			
			result.put("code"	, 500);
			result.put("msg"	, "server error");			
		}
		
		return result;
	}
	
	//올레 홈캠 카메라 신호세기 확인 요청
	@RequestMapping("/signalPwrCheckList")
	public String signalPwrCheckList(Model model,@RequestParam Map<String, Object> searchInfo) throws ParseException{
		
		Long telCnt = 0L;
		String mbrId = "", telNo = "", spotDevId = "", devConStat = "", devCctvMac = "";
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
		
		return "/monitor/signalPwrCheckList";
	}
	
	public String macAddrFormatter( String str){
		
		String rtnStr = "";
		for( int i=0; i<str.toString().length(); i++ ){
			rtnStr += str.charAt(i);
			if(i%2 == 1 && i<str.toString().length()-1){
				rtnStr += ":";
			}
		}
		return rtnStr;
	}
	//올레 홈캠 카메라 신호세기 확인 요청(파일 업로드 다중요청)
	@RequestMapping("/signalPwrCheckUpload")
	public String signalPwrCheckUpload(Model model,@RequestParam MultipartFile file) throws ParseException{
		
		int num 			= 0;
		Long svcTgtSeq 		= 0L;
		Long spotDevSeq	 	= 0L;
		String devUUID 		= "";
		String devCctvMac	= "";
		String line 		= "";
		String macAddr 		= "";
		String rtnMessage	= "";
		String authTokenStr	= "";
		//String script 		= "alert('%s'); location.replace='/monitor/signalPwrCheckList'";
		String script 		= "alert('%s'); location.href='/monitor/signalPwrCheckList';";
		
		//script += "location.href = '/guide/guideCoachList';"; 	

		List<String> macAddrList = new ArrayList<String>();
		
		String jsonStr = "";
		String[] serverList = apiEnv.getProperty("openapi.serverList").split("\\|");
		String url = apiEnv.getProperty("openapi.hcam.devices.control.retv");
		
		if (!file.isEmpty()) {
			logger.debug("================================= upload file Info ==============================================");
			try{
				System.out.println("fileSize : " 			 + file.getSize());
				System.out.println("flleContentType : "		 + file.getContentType());
				System.out.println("flleOriginalFilename : " + file.getOriginalFilename());

				InputStream inputStream 		= file.getInputStream();
				BufferedReader bufferedReader 	= new BufferedReader(new InputStreamReader(inputStream));
				
				while ((line = bufferedReader.readLine()) != null) {
					line = line.trim().replaceAll( ":" ,"" );
					line = this.macAddrFormatter(line);

					macAddrList.add(line);

					num++;
					System.out.println("fileLine "+ num + " : " + line );
				}

			}catch(Exception e){
				e.printStackTrace();
			}			
			logger.debug("================================= upload file Info ==============================================");
			
			try{
				int successCnt			= 0;
				int failCnt				= 0;
				String  successLog		= "";
				String  failLog			= "";
				String  rtnStuts		= "";
				
				List<Map<String, Object>> signalCheckList = signalCheckService.getListExcel(macAddrList);
				
				
				for(Map<String, Object> signalCheck : signalCheckList) {
					devCctvMac 		= signalCheck.get("devCctvMac") != null	? signalCheck.get("devCctvMac").toString()  : "";
					devUUID 		= signalCheck.get("devUUID") != null	? signalCheck.get("devUUID").toString()  : "";
					svcTgtSeq 		= signalCheck.get("svcTgtSeq") != null	? Long.parseLong( signalCheck.get("svcTgtSeq").toString() ) : 0;
					spotDevSeq		= signalCheck.get("spotDevSeq") != null ? Long.parseLong( signalCheck.get("spotDevSeq").toString() ) : 0;
					
					System.out.println("get devUUID / svcTgtSeq / spotDevSeq : " + devUUID +" / " + svcTgtSeq +" / " + spotDevSeq);
					
					// =================================== 신호세기 요청
					AuthToken authToken = AuthToken.encodeAuthToken(devUUID, "0000", null, null, "cmsuserrequest", "001");
					authTokenStr = authToken.getToken();
					
					HttpHeaders requestHeaders = new HttpHeaders();
				    requestHeaders.add("Content-Type"	, "application/json; charset=utf-8");
				    requestHeaders.add("authToken"		, authTokenStr);
				    
				    //Request Body
				    String itgCnvyDataStr = "?devUUID="+devUUID+"&snsnTagCd=31000010";
					
					HttpEntity<String> requestEntity = new HttpEntity<String>(requestHeaders);
					ResponseEntity<Map> responseEntity = restTemplate.exchange(serverList[0] + url + itgCnvyDataStr, HttpMethod.GET, requestEntity, Map.class);
					
					HttpHeaders reponseHeaders = responseEntity.getHeaders();
					Map<String, Object> reponseBody =  responseEntity.getBody();
					
					//Reponse Headers
					jsonStr = ObjectConverter.toJSONString(reponseHeaders, Include.ALWAYS);
					
					//Reponse Body
					jsonStr = ObjectConverter.toJSONString(reponseBody, Include.ALWAYS);
					
					JSONObject 	reponseBodyJson 			= ObjectConverter.toJSON(reponseBody);
					JSONArray 	spotDevsJsonArray			= (JSONArray) reponseBodyJson.get("spotDevs");
					JSONObject 	spotDevsJson 				= (JSONObject) spotDevsJsonArray.get(0);
					JSONArray 	spotDevDtlsJsonArray		= (JSONArray) spotDevsJson.get("spotDevDtls");
					JSONObject 	spotDevDtlsJson				= (JSONObject) spotDevDtlsJsonArray.get(0);
					String 		atribVal					= (String)spotDevDtlsJson.get("atribVal");
					
					//camera status
					rtnStuts = (atribVal.equals("0") ? "실패" : "성공") +" : "+ devCctvMac +" / "+ svcTgtSeq +" / "+ spotDevSeq;
					System.out.println( rtnStuts );
					
					// =================================== 신호세기 요청
					
					//요청결과
					if(atribVal.equals("0")){

						failCnt++;
						failLog += rtnStuts + "\\n";
					}else{
						
						successCnt++;
						successLog 	+= rtnStuts + "\\n";
					}
				}
				rtnMessage = rtnMessage.format( "총 [%d]건 중 [%d]건 성공, [%d]건 실패\\n" , signalCheckList.size(), successCnt, failCnt);
				rtnMessage += "(mac 주소는 svcTgtSeq 별로 중복 가능)\\n\\n";
				rtnMessage += successLog + "\\n";				
				rtnMessage += failLog;					
				
				script  = String.format(script, rtnMessage);
			}catch(Exception e){
				e.printStackTrace();
				script  = String.format(script, "엑셀 파일 업로드중 문제가 발생하였습니다.\\n다시 시도해주세요.");
			}
		}else{
			
		}
		
		model.addAttribute("script", script);
		return "/monitor/signalPwrCheckList";
	}
	
}
