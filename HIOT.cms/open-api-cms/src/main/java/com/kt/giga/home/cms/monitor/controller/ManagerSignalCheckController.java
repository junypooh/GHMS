package com.kt.giga.home.cms.monitor.controller;

import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONArray;
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

import com.google.gson.Gson;
import com.kt.giga.home.cms.common.service.APIEnv;
import com.kt.giga.home.cms.common.service.AuthToken;
import com.kt.giga.home.cms.monitor.service.SignalCheckService;


@Controller
@RequestMapping("/monitor")
public class ManagerSignalCheckController {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private APIEnv apiEnv; 
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private SignalCheckService signalCheckService; 
	
	//올레 홈매니저 GW WIFI 품질정보 요청 전송
	@ResponseBody
	@RequestMapping("/managerSignalCheckRequest")
	public Map<String, Object> signalCheckRequest(@RequestBody Map<String, Object> requestInfo) {
        Map<String, Object> result = new HashMap<>();
        
        Long svcTgtSeq = 0L, spotDevSeq = 0L;
        String devUUID = "", authTokenStr = "";
        
        int collectCount    = Integer.parseInt(requestInfo.get("collectCount").toString());
        int collectPeriod   = Integer.parseInt(requestInfo.get("collectPeriod").toString());
        
        try{
            String jsonStr = "";
            String[] serverList = apiEnv.getProperty("openapi.ghms.serverList").split("\\|");
            String url = apiEnv.getProperty("openapi.ghms.devices.cms.wifi");
            List<Map<String, Object>> signalCheckList = (List<Map<String, Object>>)requestInfo.get("items");
            
            result.put("code"   , 200);
            result.put("msg"    , "success");
            
            JSONArray totalArr      = new JSONArray();
            JSONArray successArr    = new JSONArray();
            JSONArray failArr       = new JSONArray();
            int totalCnt            = signalCheckList.size();
            int successCnt          = 0;
            int failCnt             = 0;

            for(Map<String, Object> signalCheck : signalCheckList) {
                devUUID     = signalCheck.get("devUUID").toString();
                svcTgtSeq   = Long.parseLong(signalCheck.get("svcTgtSeq").toString());
                spotDevSeq  = Long.parseLong(signalCheck.get("spotDevSeq").toString());
                
                HttpHeaders requestHeaders = new HttpHeaders();
                requestHeaders.add("Content-Type"   , "application/json; charset=utf-8");
                
                Map<String, Object> data = new HashMap<String, Object>();
                data.put("SVC_TGT_SEQ", svcTgtSeq);
                data.put("STOP_DEV_SEQ", spotDevSeq);
                data.put("collectCount" , collectCount);                
                data.put("collectPeriod", collectPeriod * 60 ); //단위 : 초            
                data.put("logMode", 1); //로그 on : 1 / off : 0
                
                Gson gson = new Gson();
                             
                HttpEntity<String> requestEntity = new HttpEntity<String>(gson.toJson(data), requestHeaders);
                ResponseEntity<Map> responseEntity = restTemplate.exchange(serverList[0] + url, HttpMethod.POST, requestEntity, Map.class);
                
                Map<String, Object> reponseBody =  responseEntity.getBody();
                String resultVal = (String) reponseBody.get("CMS");
                
                System.out.println("resultVal ::::::: " + resultVal);
                
                if(!"O".equals(resultVal)) {
                    result.put("code"   , 201);
                    result.put("msg"    , "fail");
                }
                
            }
            
        } catch(Exception e) {
            result.put("code"   , 500);
            result.put("msg"    , "server error");      
            
            logger.debug("================================= Exception Log ==============================================");
            System.out.println(  e  );
            logger.debug("================================= Exception Log ==============================================");
        }
        
        return result;
	}
    
    //올레 홈매니저 단말 연결상태 조회
    @ResponseBody
    @RequestMapping("/managerDeviceSignalCheckRequest")
    public Map<String, Object> signalDeviceCheckRequest(@RequestBody Map<String, Object> requestInfo) {
        Map<String, Object> result = new HashMap<>();
        
        Long svcTgtSeq = 0L, spotDevSeq = 0L;
        String devUUID = "", authTokenStr = "";
        
        int collectCount    = Integer.parseInt(requestInfo.get("collectCount").toString());
        int collectPeriod   = Integer.parseInt(requestInfo.get("collectPeriod").toString());
        
        try{
            String jsonStr = "";
            String[] serverList = apiEnv.getProperty("openapi.ghms.serverList").split("\\|");
            String url = apiEnv.getProperty("openapi.ghms.devices.cms.status");
            List<Map<String, Object>> signalCheckList = (List<Map<String, Object>>)requestInfo.get("items");
            
            result.put("code"   , 200);
            result.put("msg"    , "success");
            
            JSONArray totalArr      = new JSONArray();
            JSONArray successArr    = new JSONArray();
            JSONArray failArr       = new JSONArray();
            int totalCnt            = signalCheckList.size();
            int successCnt          = 0;
            int failCnt             = 0;

            for(Map<String, Object> signalCheck : signalCheckList) {
                devUUID     = signalCheck.get("devUUID").toString();
                svcTgtSeq   = Long.parseLong(signalCheck.get("svcTgtSeq").toString());
                spotDevSeq  = Long.parseLong(signalCheck.get("spotDevSeq").toString());
                
                HttpHeaders requestHeaders = new HttpHeaders();
                requestHeaders.add("Content-Type"   , "application/json; charset=utf-8"); 
                
                Map<String, Object> data = new HashMap<String, Object>();
                data.put("SVC_TGT_SEQ", svcTgtSeq);
                data.put("STOP_DEV_SEQ", spotDevSeq);
                data.put("collectCount" , collectCount);                
                data.put("collectPeriod", collectPeriod * 60 ); //단위 : 초            
                data.put("logMode", 1); //로그 on : 1 / off : 0
                
                Gson gson = new Gson();
                             
                HttpEntity<String> requestEntity = new HttpEntity<String>(gson.toJson(data), requestHeaders);
                ResponseEntity<Map> responseEntity = restTemplate.exchange(serverList[0] + url, HttpMethod.POST, requestEntity, Map.class);
                
                Map<String, Object> reponseBody =  responseEntity.getBody();
                String resultVal = (String) reponseBody.get("CMS");
                
                System.out.println("resultVal ::::::: " + resultVal);
                
                if(!"O".equals(resultVal)) {
                    result.put("code"   , 201);
                    result.put("msg"    , "fail");
                }
                
            }
            
        } catch(Exception e) {
            result.put("code"   , 500);
            result.put("msg"    , "server error");      
            
            logger.debug("================================= Exception Log ==============================================");
            System.out.println(  e  );
            logger.debug("================================= Exception Log ==============================================");
        }
        
        return result;
    }
	
	//올레 홈매니저 GW 로그 확인 요청
	@RequestMapping("/managerSignalCheckList")
	public String signalCheckList(Model model,@RequestParam Map<String, Object> searchInfo) throws ParseException{
		
		Long telCnt = 0L;
		String mbrId = "", telNo = "", spotDevId = "", devConStat = "", devGwMac="";
		List<Map<String, Object>> signalCheckList = signalCheckService.getManagerList(searchInfo);
		
		for(Map<String, Object> signalCheck : signalCheckList) {
			mbrId 		= signalCheck.get("mbrId") != null ? signalCheck.get("mbrId").toString() : "";
			telCnt 		= signalCheck.get("telCnt") != null ? (long)signalCheck.get("telCnt") : 0;
			telNo 		= signalCheck.get("telNo") != null ? signalCheck.get("telNo").toString() : "";
			spotDevId 	= signalCheck.get("spotDevId") != null ? signalCheck.get("spotDevId").toString() : "";			
			devConStat 	= signalCheck.get("devConStat") != null ? signalCheck.get("devConStat").toString() : "";		
			devGwMac	= signalCheck.get("devGwMac") != null ? signalCheck.get("devGwMac").toString() : "";	
			
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
		
		return "/monitor/managerSignalCheckList";
	}
}
