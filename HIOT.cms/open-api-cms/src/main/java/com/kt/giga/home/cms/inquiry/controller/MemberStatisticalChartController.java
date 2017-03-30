package com.kt.giga.home.cms.inquiry.controller;

import java.text.*;
import java.util.*;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;
import org.springframework.beans.factory.annotation.*;

import com.kt.giga.home.cms.inquiry.service.*;

@Controller
@RequestMapping("/inquiry")
public class MemberStatisticalChartController {

	@Autowired
	private MemberStatisticalChartService memberStatisticalChartService;
	
	@ResponseBody
	@RequestMapping("/memberStatisticalChart")
	public Map<String, Object> memberStatisticalChart(Model model, @RequestParam Map<String, Object> searchInfo) throws Exception {
		
		Map<String, Object> result = new HashMap<>();
		
		result.put("accrueOperate"	, new ArrayList<>());
		result.put("operate"		, new ArrayList<>());
		result.put("stop"			, new ArrayList<>());
		result.put("cancel"			, new ArrayList<>());
		
		Map<String, String> pointShape = new HashMap<>();
		pointShape.put("symbol", "diamond");
		
		try{
			List<List<Map<String, Object>>> serviceStatisticalList = getMemberStatisticalTable(searchInfo);
			
			for(List<Map<String, Object>> statisticalList : serviceStatisticalList) {
				
				String unitSvcNm = statisticalList.get(0).get("unitSvcNm").toString();

				Map<String, Object> accrueOperate = new HashMap<>();
				accrueOperate.put("label" , unitSvcNm);
				accrueOperate.put("points", pointShape);				
				
				Map<String, Object> operate = new HashMap<>();
				operate.put("label" , unitSvcNm);
				operate.put("points", pointShape);
				
				Map<String, Object> stop = new HashMap<>();
				stop.put("label" , unitSvcNm);
				stop.put("points", pointShape);
				
				Map<String, Object> cancel = new HashMap<>();
				cancel.put("label" , unitSvcNm);
				cancel.put("points", pointShape);		
				
				List<Long[]> accrueOperateData = new ArrayList<>();
				List<Long[]> operateData = new ArrayList<>();
				List<Long[]> stopData = new ArrayList<>();
				List<Long[]> cancelData = new ArrayList<>();
				
				for(Map<String, Object> statistical : statisticalList) {
					if(statistical.get("dateStrMS") != null) {
						Long[] accrueOperateXY = new Long[2];
						Long[] operateXY = new Long[2];
						Long[] stopXY = new Long[2];
						Long[] cancelXY = new Long[2];

						accrueOperateXY[0] = Long.parseLong(statistical.get("dateStrMS").toString());
						accrueOperateXY[1] = (Long)statistical.get("accrueOperate");						
						
						operateXY[0] = Long.parseLong(statistical.get("dateStrMS").toString());
						operateXY[1] = (Long)statistical.get("operate");
						
						stopXY[0] = Long.parseLong(statistical.get("dateStrMS").toString());
						stopXY[1] = (Long)statistical.get("stop");
						
						cancelXY[0] = Long.parseLong(statistical.get("dateStrMS").toString());
						cancelXY[1] = (Long)statistical.get("cancel");			
						
						accrueOperateData.add(accrueOperateXY);
						operateData.add(operateXY);
						stopData.add(stopXY);
						cancelData.add(cancelXY);
					}
				}
				
				accrueOperate.put("data", accrueOperateData);
				operate.put("data", operateData);
				stop.put("data", stopData);
				cancel.put("data", cancelData);
				
				((List)result.get("accrueOperate")).add(accrueOperate);
				((List)result.get("operate")).add(operate);
				((List)result.get("stop")).add(stop);
				((List)result.get("cancel")).add(cancel);
			}
			
			result.put("code", 200);
			result.put("msg", "success");
		} catch(Exception e) {
			result.put("code", 500);
			result.put("msg", "server error");			
		}
		
		return result;
	}
	
	@RequestMapping("/memberStatisticalTable")
	public String memberStatisticalTable(Model model, @RequestParam Map<String, Object> searchInfo) throws Exception {
		
		List<List<Map<String, Object>>> serviceStatisticalList = getMemberStatisticalTable(searchInfo);
		model.addAttribute("serviceStatisticalList", serviceStatisticalList);
		
		return "/inquiry/memberStatisticalTable";
	}
	
	private List<List<Map<String, Object>>> getMemberStatisticalTable(Map<String, Object> searchInfo) throws Exception {
		
		String searchStartDate 	= searchInfo.get("searchStartDate") != null ? searchInfo.get("searchStartDate").toString().trim() : "";
		String searchEndDate 	= searchInfo.get("searchEndDate") != null ? searchInfo.get("searchEndDate").toString().trim() : "";
		String unitSvcCds 		= searchInfo.get("unitSvcCds") != null ? searchInfo.get("unitSvcCds").toString().trim() : "";
		
		Date startDate = null, endDate = null;
		String startDateStr = null, endDateStr = null;
		SimpleDateFormat fmt = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		
		if(searchStartDate.equals("") && searchEndDate.equals("")) {
			Calendar cal = Calendar.getInstance();
			cal.add(cal.DATE, -10); 
			searchStartDate	= fmt.format(cal.getTime()).substring(0, 8);
			searchEndDate	= fmt.format(new Date()).substring(0, 8);
		}
		
		startDateStr 	= searchStartDate.replace("-", "");
		endDateStr		= searchEndDate.replace("-", "");			
		startDate 		= fmt.parse(startDateStr + "000000" + "000");
		endDate			= fmt.parse(endDateStr + "235959" + "999");
		
		searchInfo.put("searchStartDateStr", startDateStr);
		searchInfo.put("searchEndDateStr", endDateStr);
		searchInfo.put("searchStartDate", startDate);
		searchInfo.put("searchEndDate", endDate);
		searchInfo.put("unitSvcCds", unitSvcCds);
		
		return memberStatisticalChartService.getList(searchInfo);		
	}
}
