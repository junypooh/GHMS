package com.kt.giga.home.cms.inquiry.service;

import java.util.*;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.*;

import com.kt.giga.home.cms.inquiry.dao.*;

@Service
public class MemberStatisticalChartServiceImpl implements MemberStatisticalChartService {
	
	@Autowired
	private MemberStatisticalChartDao memberStatisticalChartDao;

	@Override
	public List<List<Map<String, Object>>> getList(Map<String, Object> searchInfo) {
		
		Long millisecond = 0L;  
		Calendar cal = Calendar.getInstance();
		
		List<Map<String, Object>> statisticalList = memberStatisticalChartDao.getList(searchInfo);
		Map<String, List<Map<String, Object>>> statisticalMap = new HashMap<>();
		
		for(Map<String, Object> statistical : statisticalList) {
			if(!statisticalMap.containsKey(statistical.get("unitSvcCd"))) {
				statisticalMap.put(statistical.get("unitSvcCd").toString(), new ArrayList<Map<String, Object>>());
			}
			
			statisticalMap.get(statistical.get("unitSvcCd")).add(statistical);			
		}
		
		List<List<Map<String, Object>>> serviceStatisticalList = new ArrayList<>();
		for(Map.Entry<String, List<Map<String, Object>>> entry : statisticalMap.entrySet()) {
			
			long accrueOperateTotal = 0, operateTotal = 0, stopTotal = 0, cancelTotal = 0;
			for(Map<String, Object> statistical : entry.getValue()) {
				operateTotal	+= (long)statistical.get("operate");
				stopTotal 		+= (long)statistical.get("stop");
				cancelTotal 	+= (long)statistical.get("cancel");
			}
			
			accrueOperateTotal = (long)entry.getValue().get(entry.getValue().size() - 1).get("accrueOperate");
			
			Map<String, Object> statisticalTotal = new HashMap<>();
			statisticalTotal.put("dateStr"			, "");
			statisticalTotal.put("unitSvcCd"		, entry.getValue().get(0).get("unitSvcCd").toString());
			statisticalTotal.put("unitSvcNm"		, entry.getValue().get(0).get("unitSvcNm").toString());
			statisticalTotal.put("accrueOperate"	, accrueOperateTotal);
			statisticalTotal.put("operate"			, operateTotal);
			statisticalTotal.put("stop"				, stopTotal);
			statisticalTotal.put("cancel"			, cancelTotal);
			entry.getValue().add(statisticalTotal);
			
			Collections.sort(entry.getValue(), new DateCompare());
			
			String year = "", month = "", day = "", dateStr = "";
			
			for(Map<String, Object> statistical : entry.getValue()) {
				dateStr = statistical.get("dateStr").toString();
				if(dateStr.equals("")) {
					statistical.put("dateStrFmt", "총합");
				} else {
					year = dateStr.substring(0, 4);
					month = dateStr.substring(4, 6);
					day = dateStr.substring(6, 8);
					dateStr = year + "-" + month + "-" + day;
					
					cal.set(Integer.parseInt(year), Integer.parseInt(month) - 1, Integer.parseInt(day));
					millisecond = cal.getTimeInMillis();
										
					statistical.put("dateStrFmt", dateStr);
					statistical.put("dateStrMS", millisecond);
				}
			}
			
			serviceStatisticalList.add(entry.getValue());
		}
		
		return serviceStatisticalList;
	}
	
	public class DateCompare implements Comparator<Map<String, Object>>	{
		@Override
		public int compare(Map<String, Object> obj1, Map<String, Object> obj2) {
			return obj1.get("dateStr").toString().compareTo(obj2.get("dateStr").toString());
		}
	}	

}
