package com.kt.giga.home.cms.inquiry.controller;

import java.text.*;
import java.util.*;

import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.beans.factory.annotation.*;

import com.kt.giga.home.cms.util.*;
import com.kt.giga.home.cms.common.service.APIEnv;
import com.kt.giga.home.cms.inquiry.service.*;
import com.kt.giga.home.cms.inquiry.view.JxlsExcelViewDeviceControlRecordList;

@Controller
@RequestMapping("/inquiry")
public class DeviceControlRecordController {
	
	@Autowired
	private DeviceControlRecordService deviceControlRecordService;
	
	@Autowired
	private APIEnv apiEnv;
	
	@RequestMapping("/deviceControlRecord")
	public String deviceControlRecord(Model model, @RequestParam Map<String, Object> searchInfo) {
		
		int page, pageSize = 10;
		int controlRecordCount = 0;
		String[] arrStr1, arrStr2; 
		String maskStr, regDate, regTime = "";
		String excelCount = apiEnv.getProperty("excelCount");
		
		page = searchInfo.get("page") != null ? Integer.parseInt(searchInfo.get("page").toString().trim()) : 1 ;
		String cpCode			= searchInfo.get("cpCode") != null ? searchInfo.get("cpCode").toString().trim() : "";
		String searchColumn		= searchInfo.get("searchColumn") != null ? searchInfo.get("searchColumn").toString().trim() : "";
		String searchString		= searchInfo.get("searchString") != null ? searchInfo.get("searchString").toString().trim() : "";
		String searchDateType	= searchInfo.get("searchDateType") != null ? searchInfo.get("searchDateType").toString().trim() : "";
		String searchMonth 		= searchInfo.get("searchMonth") != null ? searchInfo.get("searchMonth").toString().trim() : "";
		String searchStartDate 	= searchInfo.get("searchStartDate") != null ? searchInfo.get("searchStartDate").toString().trim() : "";
		String searchEndDate 	= searchInfo.get("searchEndDate") != null ? searchInfo.get("searchEndDate").toString().trim() : "";
		String searchSnsnTag 	= searchInfo.get("searchSnsnTag") != null ? searchInfo.get("searchSnsnTag").toString().trim() : "";
		String searchContlVal	= searchInfo.get("searchContlVal") != null ? searchInfo.get("searchContlVal").toString().trim() : "";
		
		
		searchInfo.put("page"			, page);
		searchInfo.put("pageSize"		, pageSize);
		searchInfo.put("searchString"	, searchString);	
		
		List<Map<String, Object>> controlRecordList = null;
		

		SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");		
		//System.out.println("11 searchDateType => " + searchDateType);
		if(searchDateType.equals("")) { 
			searchDateType ="range";
			Calendar cal = Calendar.getInstance();
			cal.add(cal.DATE, -7); 
			searchStartDate	= fmt.format(cal.getTime());
			searchEndDate	= fmt.format(new Date());
		}
		//System.out.println("11 searchStartDate => " + searchStartDate);
		//System.out.println("11 searchEndDate => " + searchEndDate);		
		
		SimpleDateFormat ft = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		try {
			if(!searchDateType.equals("")) {
				Date startDate = null, endDate = null;

				if(searchDateType.equals("range")) {
					if(!searchStartDate.equals("") && !searchEndDate.equals("")) {
						startDate 	= ft.parse(searchStartDate.replace("-", "") + "000000" + "000");
						endDate		= ft.parse(searchEndDate.replace("-", "") + "235959" + "999");
					} 
				} else {
					int year = Integer.parseInt(searchMonth.substring(0, 4));
					int month = Integer.parseInt(searchMonth.substring(4));
					
					Calendar calendar = Calendar.getInstance();
					calendar.set(year, month - 1, 1);
					
					int lastDate = calendar.getActualMaximum(Calendar.DATE);
					
					startDate 	= ft.parse(searchMonth + "01000000" + "000");
					endDate		= ft.parse(searchMonth + String.valueOf(lastDate) + "235959" + "999");
				}
				
				searchInfo.put("searchStartDate", startDate);
				searchInfo.put("searchEndDate", endDate);	
				
				controlRecordCount = deviceControlRecordService.getCount(searchInfo);
				controlRecordList = deviceControlRecordService.getList(searchInfo);
			}
		} catch(Exception e) {
			searchInfo.put("searchStartDate", null);
			searchInfo.put("searchEndDate", null);			
		}
		

				
		if(controlRecordList != null){
			for(Map<String, Object> nMap : controlRecordList){
				if(nMap.get("contlOccrId").toString() != null){

					arrStr1 = nMap.get("contlOccrId").toString().split(";");

					if(arrStr1[0].length() > 3) {
						maskStr = arrStr1[0].substring(0, arrStr1[0].length() - 3) + "***";
						nMap.put("customerId", maskStr);
					}else{nMap.put("customerId", "");}
					
					if(arrStr1.length > 1 && arrStr1[1].length() > 4) {
						maskStr = arrStr1[1].substring(0, arrStr1[1].length() - 4) + "****";
						nMap.put("phoneNum", maskStr);
					}else{nMap.put("phoneNum", "");}
					
				}else{
					nMap.put("customerId", "");
					nMap.put("phoneNum", "");
				}
				
				if(nMap.get("spotDevId") != null && nMap.get("spotDevId").toString().length() > 3) {
					maskStr = nMap.get("spotDevId").toString().substring(0, nMap.get("spotDevId").toString().length() - 3) + "***";
					nMap.put("spotDevId", maskStr);
				}else{nMap.put("spotDevId", "");}
				
				if(nMap.get("occDt").toString() != null && nMap.get("occDt").toString().length() >= 20){
					arrStr1 = nMap.get("occDt").toString().split(" ");
					arrStr2 = arrStr1[1].split("\\.");
					
					regDate = arrStr1[0];
					regTime = arrStr2[0];
					
					nMap.put("regDate", regDate);
					nMap.put("regTime", regTime);
				}else{
					nMap.put("regDate", "");
					nMap.put("regTime", "");
				}
			}
		}
		
		List<Map<String, Object>> snsnTagList = null;
		snsnTagList = deviceControlRecordService.getSnsnTag();
		
		List<Object> ContlValList = null;
		ContlValList = deviceControlRecordService.getControlInfo(searchSnsnTag);
		
		
		PageNavi pageNavi = new PageNavi();
		
		System.out.println(searchStartDate);
		pageNavi.setAction("/inquiry/deviceControlRecord");
		pageNavi.setTotalCount(controlRecordCount);
		pageNavi.setPageSize(pageSize);
		pageNavi.setNowPage(page);
		pageNavi.setParameters("cpCode", cpCode);
		pageNavi.setParameters("searchColumn", searchColumn);
		pageNavi.setParameters("searchString", searchString);
		pageNavi.setParameters("searchDateType", searchDateType);
		pageNavi.setParameters("searchMonth", searchMonth);
		pageNavi.setParameters("searchStartDate", searchStartDate);
		pageNavi.setParameters("searchEndDate", searchEndDate);
		pageNavi.setParameters("searchSnsnTag", searchSnsnTag);
		pageNavi.setParameters("searchContlVal", searchContlVal);
		
		
		pageNavi.make();		
		
		model.addAttribute("controlRecordList", controlRecordList);
		model.addAttribute("pageNavi"	, pageNavi);		
		model.addAttribute("snsnTagList", snsnTagList);
		model.addAttribute("ContlValList", ContlValList);
		model.addAttribute("excelCount", excelCount);
		
		return "/inquiry/deviceControlRecord";
	}
	
	@RequestMapping(value="/deviceControlRecord", params="event=excel") //proc 가 excel 일때 excel 다운 
	public ModelAndView excelDeviceControlRecord(Model model, @RequestParam Map<String, Object> searchInfo) {
		
		String[] arrStr1, arrStr2; 
		String maskStr, regDate, regTime = "";
		
		String searchDateType	= searchInfo.get("searchDateType") != null ? searchInfo.get("searchDateType").toString().trim() : "";
		String searchMonth 		= searchInfo.get("searchMonth") != null ? searchInfo.get("searchMonth").toString().trim() : "";
		String searchStartDate 	= searchInfo.get("searchStartDate") != null ? searchInfo.get("searchStartDate").toString().trim() : "";
		String searchEndDate 	= searchInfo.get("searchEndDate") != null ? searchInfo.get("searchEndDate").toString().trim() : "";
		
		String excelCount = apiEnv.getProperty("excelCount");
		
		List<Map<String, Object>> controlRecordList = null;
		

		SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");		
		if(searchDateType.equals("")) { 
			searchDateType ="range";
			Calendar cal = Calendar.getInstance();
			cal.add(cal.DATE, -7); 
			searchStartDate	= fmt.format(cal.getTime());
			searchEndDate	= fmt.format(new Date());
		}
		
		SimpleDateFormat ft = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		try {
			if(!searchDateType.equals("")) {
				Date startDate = null, endDate = null;

				if(searchDateType.equals("range")) {
					if(!searchStartDate.equals("") && !searchEndDate.equals("")) {
						startDate 	= ft.parse(searchStartDate.replace("-", "") + "000000" + "000");
						endDate		= ft.parse(searchEndDate.replace("-", "") + "235959" + "999");
					} 
				} else {
					int year = Integer.parseInt(searchMonth.substring(0, 4));
					int month = Integer.parseInt(searchMonth.substring(4));
					
					Calendar calendar = Calendar.getInstance();
					calendar.set(year, month - 1, 1);
					
					int lastDate = calendar.getActualMaximum(Calendar.DATE);
					
					startDate 	= ft.parse(searchMonth + "01000000" + "000");
					endDate		= ft.parse(searchMonth + String.valueOf(lastDate) + "235959" + "999");
				}
				searchInfo.remove("page");
				searchInfo.remove("pageSize");
				searchInfo.put("searchStartDate", startDate);
				searchInfo.put("searchStartDate", startDate);
				searchInfo.put("searchEndDate", endDate);	
				searchInfo.put("limitCount", excelCount);
				controlRecordList = deviceControlRecordService.getList(searchInfo);
				
				for(Map<String, Object> nMap : controlRecordList){
					if(nMap.get("contlOccrId").toString() != null){

						arrStr1 = nMap.get("contlOccrId").toString().split(";");

						if(arrStr1[0].length() > 3) {
							maskStr = arrStr1[0].substring(0, arrStr1[0].length() - 3) + "***";
							nMap.put("customerId", maskStr);
						}else{nMap.put("customerId", "");}
						
						if(arrStr1.length > 1 && arrStr1[1].length() > 4) {
							maskStr = arrStr1[1].substring(0, arrStr1[1].length() - 4) + "****";
							nMap.put("phoneNum", maskStr);
						}else{nMap.put("phoneNum", "");}
						
					}else{
						nMap.put("customerId", "");
						nMap.put("phoneNum", "");
					}
					
					if(nMap.get("spotDevId") != null && nMap.get("spotDevId").toString().length() > 3) {
						maskStr = nMap.get("spotDevId").toString().substring(0, nMap.get("spotDevId").toString().length() - 3) + "***";
						nMap.put("spotDevId", maskStr);
					}else{nMap.put("spotDevId", "");}
					
					if(nMap.get("occDt").toString() != null && nMap.get("occDt").toString().length() >= 20){
						arrStr1 = nMap.get("occDt").toString().split(" ");
						arrStr2 = arrStr1[1].split("\\.");
						
						regDate = arrStr1[0];
						regTime = arrStr2[0];
						
						nMap.put("regDate", regDate);
						nMap.put("regTime", regTime);
					}else{
						nMap.put("regDate", "");
						nMap.put("regTime", "");
					}
				}
			}
		} catch(Exception e) {
			searchInfo.put("searchStartDate", null);
			searchInfo.put("searchEndDate", null);			
		}
		
		
		ModelAndView mav = new ModelAndView(new JxlsExcelViewDeviceControlRecordList());
		mav.addObject("controlRecordList", controlRecordList);
		
		return mav;
	}
	
	@ResponseBody
	@RequestMapping("/getSnsnTagContlValList")
	public Map<String, Object> getSnsnTagContlValList(@RequestParam Map<String, Object> searchInfo) {
		String searchSnsnTag	= searchInfo.get("searchSnsnTag") != null ? searchInfo.get("searchSnsnTag").toString().trim() : "";
		Map<String, Object> result = new HashMap<>();
		try { 
			List<Object> item = deviceControlRecordService.getControlInfo(searchSnsnTag);
			result.put("code", 200);
			result.put("msg", "success");
			result.put("list", item);
		} catch(Exception e) {
			result.put("code", 500);
			result.put("msg", "server error : " + e.getMessage());
		}
		return result;
	}	
}
