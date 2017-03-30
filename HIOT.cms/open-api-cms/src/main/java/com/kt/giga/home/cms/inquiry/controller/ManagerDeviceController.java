/*******************************************************************************
 * Copyright(c) 2015 KT. All rights reserved.
 * This software is the proprietary information of KT.
 *******************************************************************************/
package com.kt.giga.home.cms.inquiry.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.kt.giga.home.cms.inquiry.service.ManagerDeviceService;
import com.kt.giga.home.cms.monitor.service.SignalCheckService;

/**
 * 
 * @author junypooh ( kyungjun.park@ceinside.co.kr )
 * @since 2015. 8. 10.
 */
@Controller
@RequestMapping("/inquiry")
public class ManagerDeviceController {

	@Autowired
	private ManagerDeviceService managerDeviceService;
	
	@Autowired
	private SignalCheckService signalCheckService;
	
	//올레 홈매니저 GW 로그 확인 요청
	@RequestMapping("/managerDeviceList")
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
			
			signalCheck.put("telNo", telNo);
		}

		model.addAttribute("signalCheckList", signalCheckList);
		model.addAttribute("signalCheckCount", signalCheckList.size());
		
		return "/inquiry/managerDevice";
	}

	@RequestMapping("/managerDeviceLog")
	public String getManagerDeviceList(Model model, @RequestParam Map<String, Object> searchInfo) {
		
		String searchDateType	= searchInfo.get("searchDateType") != null ? searchInfo.get("searchDateType").toString().trim() : "";
		String searchMonth 	= searchInfo.get("searchMonth") != null ? searchInfo.get("searchMonth").toString().trim() : "";
		String searchStartDate 	= searchInfo.get("searchStartDate") != null ? searchInfo.get("searchStartDate").toString().trim() : "";
		String searchEndDate 	= searchInfo.get("searchEndDate") != null ? searchInfo.get("searchEndDate").toString().trim() : "";
		
		if( searchInfo.isEmpty() )
		{
			 Calendar cal = Calendar.getInstance();
		     cal.add(Calendar.DATE, -1);
			 Date startDate = cal.getTime();
			 Date endDate = new Date();
			 SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			 searchStartDate = sdf.format(startDate);
			 searchEndDate = sdf.format(endDate);
			 searchDateType = "range";
		}
		
		try {
			if(!searchDateType.equals("")) {
				Date startDate = null, endDate = null;
				SimpleDateFormat ft = new SimpleDateFormat("yyyyMMddHHmmssSSS");
				
				if(searchDateType.equals("range")) {
					if(!searchStartDate.equals("") && !searchEndDate.equals("")) {
						startDate 	= ft.parse(searchStartDate.replace("-", "") + "000000" + "000");
						endDate		= ft.parse(searchEndDate.replace("-", "") + "235959" + "999");
					} 
					//System.out.println("1 startDate = " + startDate);
					//System.out.println("1 endDate = " + endDate);
				} else {
					int year = Integer.parseInt(searchMonth.substring(0, 4));
					int month = Integer.parseInt(searchMonth.substring(4));
					
					Calendar calendar = Calendar.getInstance();
					calendar.set(year, month - 1, 1);
					
					int lastDate = calendar.getActualMaximum(Calendar.DATE);
					
					startDate 	= ft.parse(searchMonth + "01000000" + "000");
					endDate		= ft.parse(searchMonth + String.valueOf(lastDate) + "235959" + "999");
				}
				
				String orgSearchStartDate = "".equals(searchStartDate) ? searchMonth+"01" : searchStartDate.replace("-", "");
				
				searchInfo.put("orgSearchStartDate", orgSearchStartDate);
				searchInfo.put("searchStartDate", startDate);
				searchInfo.put("searchEndDate", endDate);	
				searchInfo.put("searchStartDateLong", startDate.getTime());
				searchInfo.put("searchEndDateLong", endDate.getTime());
			}			
		} catch(Exception e) {
			searchInfo.put("searchStartDate", null);
			searchInfo.put("searchEndDate", null);			
		}
		
		List<Map<String, String>> list = managerDeviceService.getList(searchInfo);

		model.addAttribute("list"		, list);

		return "/inquiry/managerDeviceLog";
	}
}
