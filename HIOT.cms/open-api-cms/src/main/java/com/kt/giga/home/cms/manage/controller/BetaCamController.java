package com.kt.giga.home.cms.manage.controller;

import java.util.*;

import javax.servlet.http.*;

import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.*;

import com.kt.giga.home.cms.manage.service.*;
import com.kt.giga.home.cms.monitor.service.SignalCheckService;
@Controller
@RequestMapping("/manage")
public class BetaCamController {
	
	@Autowired
	private HttpServletRequest request;
	
	@Autowired
	private BetaCamService betaCamService;
	
	@Autowired
	private SignalCheckService signalCheckService;
	
	@RequestMapping("/betaCamList")
	public String getBetaCamList(Model model,@RequestParam Map<String, Object> searchInfo) {	
		
		int i;
		String mbrId, telNo, spotDevId, devConStat;
		String svcTgtSeqList_cam = "";
		String spotDevSeqList_cam = "";
		Long telCnt;
		
		String searchColumn	= searchInfo.get("searchColumn") == null || searchInfo.get("searchColumn").toString().trim().equals("") ? "" : searchInfo.get("searchColumn").toString().trim();
		String searchString		= searchInfo.get("searchString") == null || searchInfo.get("searchString").toString().trim().equals("") ? "" : searchInfo.get("searchString").toString().trim();
		
		List<Map<String, Object>> seqList = new ArrayList<>();
		List<Map<String, Object>> targetCamList = new ArrayList<>();
		List<Map<String, Object>> searchCamList = new ArrayList<>();
		try {
			
			//tb_version_frmwr_cam 테이블에서 seq 리스트를 갖고온다.
			seqList = betaCamService.getCamList();
			
			if(seqList.size() > 0){
				for(i=0 ; i<seqList.size() ; i++){
					svcTgtSeqList_cam += seqList.get(i).get("svcTgtSeq").toString() + ",";
					spotDevSeqList_cam += seqList.get(i).get("spotDevSeq").toString() + ",";
				}
				svcTgtSeqList_cam = svcTgtSeqList_cam.substring(0, svcTgtSeqList_cam.length() -1); //마지막 "," 제거
				spotDevSeqList_cam = spotDevSeqList_cam.substring(0, spotDevSeqList_cam.length() -1); //마지막 "," 제거
				searchInfo.put("svcTgtSeqList", svcTgtSeqList_cam);
				searchInfo.put("spotDevSeqList", spotDevSeqList_cam);
				
				//가지고온 seq 리스트로 사용자정보, 단말정보를 갖고온다.
				targetCamList = betaCamService.getCamInfoList(searchInfo);
				
				for(Map<String, Object> cam : targetCamList){
					mbrId 			= cam.get("mbrId") != null ? cam.get("mbrId").toString() : "";
					telCnt 			= cam.get("telCnt") != null ? (long)cam.get("telCnt") : 0;
					telNo 				= cam.get("telNo") != null ? cam.get("telNo").toString() : "";
					spotDevId 		= cam.get("spotDevId") != null ? cam.get("spotDevId").toString() : "";			
					devConStat 	= cam.get("devConStat") != null ? cam.get("devConStat").toString() : "";		
					
					if(mbrId.length() > 3) {
						mbrId = mbrId.substring(0, mbrId.length() - 3) + "***";
						cam.put("mbrId", mbrId);
					}
					if(spotDevId.length() > 4) {
						spotDevId = spotDevId.substring(0, spotDevId.length() - 4) + "****";
						cam.put("spotDevId", spotDevId);				
					}
					if(telNo.length() > 4){
						telNo = telNo.substring(0, telNo.length() - 4) + "****";
					}			
					if(telCnt > 1) {
						telNo = telNo + "(외 " + String.valueOf(telCnt - 1) + ")";
					}			
					if(devConStat.equals("1")) {
						cam.put("devConStat", "접속");
					}else{
						cam.put("devConStat", "미접속");
					}
					cam.put("telNo", telNo);
				}
			}
			
			if(!searchColumn.equals("") || !searchString.equals("")){
				Map<String, Object> nMap = new HashMap<>();
				nMap.put("searchColumn", searchColumn);
				nMap.put("searchString", searchString);
				
				//검색값으로 사용자정보, 단말정보를 갖고온다.
				searchCamList = signalCheckService.getList(nMap);
				for(Map<String, Object> cam : searchCamList){
					mbrId 			= cam.get("mbrId") != null ? cam.get("mbrId").toString() : "";
					telCnt 			= cam.get("telCnt") != null ? (long)cam.get("telCnt") : 0;
					telNo 				= cam.get("telNo") != null ? cam.get("telNo").toString() : "";
					spotDevId 		= cam.get("spotDevId") != null ? cam.get("spotDevId").toString() : "";			
					devConStat 	= cam.get("devConStat") != null ? cam.get("devConStat").toString() : "";		
					
					if(mbrId.length() > 3) {
						mbrId = mbrId.substring(0, mbrId.length() - 3) + "***";
						cam.put("mbrId", mbrId);
					}
					if(spotDevId.length() > 4) {
						spotDevId = spotDevId.substring(0, spotDevId.length() - 4) + "****";
						cam.put("spotDevId", spotDevId);				
					}
					if(telNo.length() > 4){
						telNo = telNo.substring(0, telNo.length() - 4) + "****";
					}			
					if(telCnt > 1) {
						telNo = telNo + "(외 " + String.valueOf(telCnt - 1) + ")";
					}			
					if(devConStat.equals("1")) {
						cam.put("devConStat", "접속");
					}else{
						cam.put("devConStat", "미접속");
					}
					cam.put("telNo", telNo);
				}
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		model.addAttribute("targetCamCount", targetCamList.size());
		model.addAttribute("targetCamList", targetCamList);
		model.addAttribute("searchCamCount", searchCamList.size());
		model.addAttribute("searchCamList", searchCamList);
		
		return "/manage/betaCamList";
	}
	
	@RequestMapping(value="/betaCamList", params="method=register")
	public String betaCamRegister(Model model,@RequestParam Map<String, Object> searchInfo) {	
		
		String searchColumn	= searchInfo.get("searchColumn") == null || searchInfo.get("searchColumn").toString().trim().equals("") ? "" : searchInfo.get("searchColumn").toString().trim();
		String searchString		= searchInfo.get("searchString") == null || searchInfo.get("searchString").toString().trim().equals("") ? "" : searchInfo.get("searchString").toString().trim();
		
		String script = "alert('%s');";
		
		try {			
			betaCamService.register(searchInfo);
			script  = String.format(script, "정상적으로 등록 처리 되었습니다.");
		} catch(Exception e) {
			script  = String.format(script, "등록 과정중 오류가 발생하였습니다.\\n다시 시도하여 주십시오.");
			e.printStackTrace();
		}
		script += "location.href = '/manage/betaCamList" + "?searchColumn=" + searchColumn + "&searchString=" + searchString + "';"; 
		
		model.addAttribute("script", script);		
		
		return "/manage/betaCamList";
	}
	
	@RequestMapping(value="/betaCamList", params="method=delete")
	public String betaCamDelete(Model model,@RequestParam Map<String, Object> searchInfo) {	
		
		String searchColumn	= searchInfo.get("searchColumn") == null || searchInfo.get("searchColumn").toString().trim().equals("") ? "" : searchInfo.get("searchColumn").toString().trim();
		String searchString		= searchInfo.get("searchString") == null || searchInfo.get("searchString").toString().trim().equals("") ? "" : searchInfo.get("searchString").toString().trim();
		
		String script = "alert('%s');";
		
		try {			
			betaCamService.remove(searchInfo);
			script  = String.format(script, "정상적으로 삭제 처리 되었습니다.");
		} catch(Exception e) {
			script  = String.format(script, "삭제 과정중 오류가 발생하였습니다.\\n다시 시도하여 주십시오.");
			e.printStackTrace();
		}
		script += "location.href = '/manage/betaCamList" + "?searchColumn=" + searchColumn + "&searchString=" + searchString + "';"; 
		
		model.addAttribute("script", script);		
		
		return "/manage/betaCamList";
	}
	
}
