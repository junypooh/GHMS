package com.kt.giga.home.cms.monitor.controller;
// ************************* 미사용 컨트롤러(삭제예정) *************************  // 


import java.text.*;
import java.util.*;

import org.slf4j.*;
import org.springframework.http.*;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.beans.factory.annotation.*;

import com.fasterxml.jackson.annotation.JsonInclude.*;

import com.kt.giga.home.cms.util.*;
import com.kt.giga.home.cms.vo.row.*;
import com.kt.giga.home.cms.vo.cnvy.*;
import com.kt.giga.home.cms.common.service.*;
import com.kt.giga.home.cms.monitor.service.*;

@Controller
@RequestMapping("/monitor")
public class LogOnOffController {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private APIEnv apiEnv; 
	
	@Autowired
	private RestTemplate restTemplate;	
	
	@Autowired
	private LogOnOffService logOnOffService; 
	
	@ResponseBody
	@RequestMapping("/logOnOffRequest")
	public Map<String, Object> signalCheckRequest(@RequestBody Map<String, Object> requestInfo) {
		Map<String, Object> result = new HashMap<>();
		
		String authTokenStr = "", jsonStr = "";
		String devUUID 	= requestInfo.get("devUUID").toString();
		String[] serverList = apiEnv.getProperty("openapi.serverList").split("\\|");
		String url = apiEnv.getProperty("openapi.hcam.devices.control.config");		
		
		int logMode 	= Integer.parseInt(requestInfo.get("logMode").toString());
		Long svcTgtSeq	= Long.parseLong(requestInfo.get("svcTgtSeq").toString());
		Long spotDevSeq	= Long.parseLong(requestInfo.get("spotDevSeq").toString());		
		
		try {

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
			
			GenlSetupData genlSetupData = new GenlSetupData();
			genlSetupData.setSnsnTagCd("80000042");
			genlSetupData.setSetupVal("1");
			
			List<GenlSetupData> genlSetupDatas = new ArrayList<>();
			genlSetupDatas.add(genlSetupData);
			
			HashMap<String, Object> rowExtension = new HashMap<>();
			rowExtension.put("logMode", logMode);				
			
			cnvyRow.setGenlSetupDatas(genlSetupDatas);
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
			
			result.put("code"	, 200);
			result.put("msg"	, "success");			
		} catch(Exception e) {
			
			logger.debug("================================= Server Error Start ==============================================");
			logger.debug(e.getMessage());
			logger.debug("================================= Server Error End   =============================================");				
			
			result.put("code"	, 500);
			result.put("msg"	, "server error");			
		}
		
		return result;
	}
		
	@RequestMapping("/logOnOffList")
	public String logOnOffList(Model model,@RequestParam Map<String, Object> searchInfo) throws ParseException{
		
		int page, pageSize = 10;
		
		page = searchInfo.get("page") == null || searchInfo.get("page").toString().trim().equals("") ? 1 : Integer.parseInt(searchInfo.get("page").toString().trim());
		String searchString	= searchInfo.get("searchString") != null ? searchInfo.get("searchString").toString().trim() : "";
		String searchColumn	= searchInfo.get("searchColumn") != null ? searchInfo.get("searchColumn").toString().trim() : "";
		
		searchInfo.put("page"			, page);
		searchInfo.put("pageSize"		, pageSize);
		searchInfo.put("searchString"	, searchString);
		
		int logOnOffCount = logOnOffService.getCount(searchInfo);
		List<Map<String, Object>> logOnOffList = logOnOffService.getList(searchInfo);
		
		Long telCnt = 0L;
		String mbrId = "", telNo = "", spotDevId = "", devNm = "";		
		
		for(Map<String, Object> logOnOff : logOnOffList) {
			mbrId 		= logOnOff.get("mbrId") != null && !logOnOff.get("mbrId").equals("") ? logOnOff.get("mbrId").toString() : "--";
			telCnt 		= logOnOff.get("telCnt") != null ? (Long)logOnOff.get("telCnt") : 0L;
			telNo 		= logOnOff.get("telNo") != null && !logOnOff.get("telNo").equals("") ? logOnOff.get("telNo").toString() : "--";
			spotDevId 	= logOnOff.get("spotDevId") != null && !logOnOff.get("spotDevId").equals("") ? logOnOff.get("spotDevId").toString() : "--";
			devNm		= logOnOff.get("devNm") != null && !logOnOff.get("devNm").equals("") ? logOnOff.get("devNm").toString() : "--";
			
			if(mbrId.length() > 3) {
				mbrId = mbrId.substring(0, mbrId.length() - 3) + "***";
			}
			
			if(spotDevId.length() > 4) {
				spotDevId = spotDevId.substring(0, spotDevId.length() - 4) + "****";
			}
			
			if(telNo.length() > 4){
				telNo = telNo.substring(0, telNo.length() - 4) + "****";
			}			
			
			if(telCnt > 1) {
				telNo = telNo + "(외 " + String.valueOf(telCnt - 1) + ")";
			}			
			
			logOnOff.put("spotDevId", spotDevId);				
			logOnOff.put("mbrId", mbrId);
			logOnOff.put("telNo", telNo);
			logOnOff.put("devNm", devNm);
		}		
		
		PageNavi pageNavi = new PageNavi();
		
		pageNavi.setAction("/monitor/logOnOffList");
		pageNavi.setTotalCount(logOnOffCount);
		pageNavi.setPageSize(pageSize);
		pageNavi.setNowPage(page);
		pageNavi.setParameters("searchColumn", searchColumn);
		pageNavi.setParameters("searchString", searchString);
		pageNavi.make();
		
		model.addAttribute("logOnOffList"	, logOnOffList);
		model.addAttribute("logOnOffCount"	, logOnOffCount);
		model.addAttribute("pageNavi"		, pageNavi);
		
		return "/monitor/logOnOffList";
	}
}
