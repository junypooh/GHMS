package com.kt.giga.home.cms.inquiry.service;

import java.util.*;

import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.Service;

import com.kt.giga.home.cms.inquiry.dao.*;

@Service
public class DeviceControlRecordServiceImpl implements DeviceControlRecordService {
	
	@Autowired
	private DeviceControlRecordDao deviceControlRecordDao;
	
	private Map<String, Map<String, String>> multiValueSnsnTagDic = null;
	
	public String getControlInfo(String snsnTagDtlCd, String contlVal) {
		
		String result = "";
		
		if(multiValueSnsnTagDic == null) {
			
			multiValueSnsnTagDic = new HashMap<>();
			
			// 80000002 : 해상도 옵션
			Map<String, String> snsnTag80000002 = new HashMap<>();
			snsnTag80000002.put("1", "고화질");
			snsnTag80000002.put("2", "중화질");
			snsnTag80000002.put("3", "저화질");
			multiValueSnsnTagDic.put("80000002", snsnTag80000002);
			
			// 80000003 : 침입 감지 활성화 여부
			Map<String, String> snsnTag80000003 = new HashMap<>();
			snsnTag80000003.put("0", "비활성화");
			snsnTag80000003.put("1", "활성화");
			multiValueSnsnTagDic.put("80000003", snsnTag80000003);				
			
			// 80000004 : 침입 감지 모드
			Map<String, String> snsnTag80000004 = new HashMap<>();
			snsnTag80000004.put("1", "영상");
			snsnTag80000004.put("2", "음향");
			snsnTag80000004.put("3", "영상 + 음향");
			multiValueSnsnTagDic.put("80000004", snsnTag80000004);		
			
			// 80000005 : 움직임 감지 민감도 옵션
			Map<String, String> snsnTag80000005 = new HashMap<>();
			snsnTag80000005.put("1", "낮음");
			snsnTag80000005.put("2", "보통");
			snsnTag80000005.put("3", "높음");
			multiValueSnsnTagDic.put("80000005", snsnTag80000005);		
			
			// 80000006 : 음향 감지 민감도 옵션
			Map<String, String> snsnTag80000006 = new HashMap<>();
			snsnTag80000006.put("1", "낮음");
			snsnTag80000006.put("2", "보통");
			snsnTag80000006.put("3", "높음");
			multiValueSnsnTagDic.put("80000006", snsnTag80000006);		
			
			// 80000010 : 저장 매체 모드
			Map<String, String> snsnTag80000010 = new HashMap<>();
			snsnTag80000010.put("0", "저장하지않음");
			snsnTag80000010.put("1", "SD카드");
			snsnTag80000010.put("2", "Ucloud");
			snsnTag80000010.put("9", "설정없음");
			multiValueSnsnTagDic.put("80000010", snsnTag80000010);			
			
			// 80000012 : 예약 녹화 스케줄 활성화 여부
			Map<String, String> snsnTag80000012 = new HashMap<>();
			snsnTag80000012.put("0", "비활성화");
			snsnTag80000012.put("1", "활성화");
			multiValueSnsnTagDic.put("80000012", snsnTag80000012);		
			
			// 80000013 : 감지 스케줄 활성화 여부
			Map<String, String> snsnTag80000013 = new HashMap<>();
			snsnTag80000013.put("0", "비활성화");
			snsnTag80000013.put("1", "활성화");
			multiValueSnsnTagDic.put("80000013", snsnTag80000013);				
		}
		
		if(multiValueSnsnTagDic.containsKey(snsnTagDtlCd)) {
			if(multiValueSnsnTagDic.get(snsnTagDtlCd).get(contlVal) != null) {
				result = multiValueSnsnTagDic.get(snsnTagDtlCd).get(contlVal);
			}
		}
		
		return result;
	}

	@Override
	public int getCount(Map<String, Object> searchInfo) {
		return deviceControlRecordDao.getCount(searchInfo);
	}

	@Override
	public List<Map<String, Object>> getList(Map<String, Object> searchInfo) {
		
		String snsnTagDtlCd = "", contlVal = "", contlInfo = "";
		List<Map<String, Object>> controlRecordList = deviceControlRecordDao.getList(searchInfo);
		
		for(Map<String, Object> controlRecord : controlRecordList) {
			snsnTagDtlCd = controlRecord.get("snsnTagDtlCd").toString();
			contlVal = controlRecord.get("contlVal").toString();
			contlInfo = this.getControlInfo(snsnTagDtlCd, contlVal);
			controlRecord.put("contlInfo", contlInfo);
		}
		
		return controlRecordList;
	}
	
	
	@Override
	public List<Map<String, Object>> getSnsnTag() {
		return deviceControlRecordDao.getSnsnTag();
	}
	
	public List<Object> getControlInfo(String searchSnsnTag) {
		
		if(multiValueSnsnTagDic == null) {
			
			multiValueSnsnTagDic = new HashMap<>();
			
			// 80000002 : 해상도 옵션
			Map<String, String> snsnTag80000002 = new HashMap<>();
			snsnTag80000002.put("1", "고화질");
			snsnTag80000002.put("2", "중화질");
			snsnTag80000002.put("3", "저화질");
			multiValueSnsnTagDic.put("80000002", snsnTag80000002);
			
			// 80000003 : 침입 감지 활성화 여부
			Map<String, String> snsnTag80000003 = new HashMap<>();
			snsnTag80000003.put("0", "비활성화");
			snsnTag80000003.put("1", "활성화");
			multiValueSnsnTagDic.put("80000003", snsnTag80000003);				
			
			// 80000004 : 침입 감지 모드
			Map<String, String> snsnTag80000004 = new HashMap<>();
			snsnTag80000004.put("1", "영상");
			snsnTag80000004.put("2", "음향");
			snsnTag80000004.put("3", "영상 + 음향");
			multiValueSnsnTagDic.put("80000004", snsnTag80000004);		
			
			// 80000005 : 움직임 감지 민감도 옵션
			Map<String, String> snsnTag80000005 = new HashMap<>();
			snsnTag80000005.put("1", "낮음");
			snsnTag80000005.put("2", "보통");
			snsnTag80000005.put("3", "높음");
			multiValueSnsnTagDic.put("80000005", snsnTag80000005);		
			
			// 80000006 : 음향 감지 민감도 옵션
			Map<String, String> snsnTag80000006 = new HashMap<>();
			snsnTag80000006.put("1", "낮음");
			snsnTag80000006.put("2", "보통");
			snsnTag80000006.put("3", "높음");
			multiValueSnsnTagDic.put("80000006", snsnTag80000006);		
			
			// 80000010 : 저장 매체 모드
			Map<String, String> snsnTag80000010 = new HashMap<>();
			snsnTag80000010.put("0", "저장하지않음");
			snsnTag80000010.put("1", "SD카드");
			snsnTag80000010.put("2", "Ucloud");
			snsnTag80000010.put("9", "설정없음");
			multiValueSnsnTagDic.put("80000010", snsnTag80000010);			
			
			// 80000012 : 예약 녹화 스케줄 활성화 여부
			Map<String, String> snsnTag80000012 = new HashMap<>();
			snsnTag80000012.put("0", "비활성화");
			snsnTag80000012.put("1", "활성화");
			multiValueSnsnTagDic.put("80000012", snsnTag80000012);		
			
			// 80000013 : 감지 스케줄 활성화 여부
			Map<String, String> snsnTag80000013 = new HashMap<>();
			snsnTag80000013.put("0", "비활성화");
			snsnTag80000013.put("1", "활성화");
			multiValueSnsnTagDic.put("80000013", snsnTag80000013);				
		}
		
		Map<String, String> map = multiValueSnsnTagDic.get(searchSnsnTag);
		
		List<Object> CncnTagContlVal = new ArrayList<>();
		
		if( map != null  )
		{
			CncnTagContlVal.clear();
			
			for( String key : map.keySet() ){
				Map<String, String> newMap = new HashMap<>();
				newMap.put("code", key);
				newMap.put("name", map.get(key));
				CncnTagContlVal.add(newMap);
	        }
		}

		return CncnTagContlVal;
	}	

}
