package com.kt.giga.home.cms.inquiry.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kt.giga.home.cms.inquiry.dao.MonitorHistoryDao;

@Service
public class MonitorHistoryServiceImpl implements MonitorHistoryService{
	@Autowired
	private MonitorHistoryDao monitorHistoryDao;
	
	@Override
	public int getCount(Map<String, Object> searchInfo) {
		return monitorHistoryDao.getCount(searchInfo);
	}	
	
	@Override
	public Map<String, Object> get(Map<String, Object> searchInfo) {
		searchInfo.put("pk", Integer.valueOf(searchInfo.get("pk").toString()));
		return monitorHistoryDao.get(searchInfo);
	}	

	@Override
	public List<Map<String, Object>> getList(Map<String, Object> searchInfo) {
		//return monitorHistoryDao.getList(searchInfo);
		List<Map<String, Object>> monitorHistoryList = monitorHistoryDao.getList(searchInfo);
		if(monitorHistoryList != null){
			String tempStr="", maskStr="";
			
			for(Map<String, Object> nMap : monitorHistoryList){
				if(nMap.get("mbr_id") != null){

					tempStr = nMap.get("mbr_id").toString();
					if(tempStr.length() > 3) {
						maskStr = tempStr.substring(0, tempStr.length() - 3) + "***";
						nMap.put("mbr_id", maskStr);
						nMap.put("beforMbrId", tempStr);						
					}else{nMap.put("mbr_id", "");}
					
				}else{ nMap.put("mbrId", ""); }
				
				if(nMap.get("tel_no") != null){

					tempStr = nMap.get("tel_no").toString();
					if(tempStr.length() > 4) {
						maskStr = tempStr.substring(0, tempStr.length() - 4) + "****";
						nMap.put("tel_no", maskStr);
					}else{nMap.put("tel_no", "");}
					
				}else{ nMap.put("tel_no", ""); }
				
				if(nMap.get("pk_code") != null){
					switch(nMap.get("pk_code").toString()){
						case "CJ11" : 	nMap.put("description", "클릭 > 메인 > 카메라리스트 > 모니터링 시작"); break;
						case "CL02" : 	nMap.put("description", "클릭 > 실시간모니터링 > - > 카메라 전환"); break;
						case "CL03" : 	nMap.put("description", "클릭 > 실시간모니터링 > - > 모니터링 종료"); break;
						case "CL14" : 	nMap.put("description", "클릭 > 실시간모니터링 > 모니터링영역 > 음소거"); break;
						case "CL15" : 	nMap.put("description", "클릭 > 실시간모니터링 > 모니터링영역 > 음소거 해제"); break;
						case "CL21" : 	nMap.put("description", "클릭 > 실시간모니터링 > 모니터링기능 > 화면캡쳐"); break;
						case "CL22" : 	nMap.put("description", "클릭 > 실시간모니터링 > 모니터링기능 > 영상녹화"); break;
						case "CL23" : 	nMap.put("description", "클릭 > 실시간모니터링 > 모니터링기능 > 영상녹화중지"); break;
						case "CL24" : 	nMap.put("description", "클릭 > 실시간모니터링 > 모니터링기능 > 경보음"); break;
						case "CL25" : 	nMap.put("description", "클릭 > 실시간모니터링 > 모니터링기능 > 음성전송"); break;
						case "CL26" : 	nMap.put("description", "클릭 > 실시간모니터링 > 모니터링기능 > 긴급통화"); break;
						case "CL61" : 	nMap.put("description", "클릭 > 실시간모니터링 > 네트워크 오류 > 확인"); break;
						case "CL71" : 	nMap.put("description", "클릭 > 실시간모니터링 > 모니터링 제한시간 초과 > 확인"); break;
						case "CL81" : 	nMap.put("description", "클릭 > 실시간모니터링 > 모니터링 도중 해상도 변경 > 확인"); break;
						case "CL91" : 	nMap.put("description", "클릭 > 실시간모니터링 > 연결타임아웃 > 확인"); break;
						default :		nMap.put("description", "-");
					}
				}else{ nMap.put("pk_code", ""); nMap.put("description", "-"); }
				
			}
		}
		
		return monitorHistoryList;
	}
	
	@Override
	public List<Map<String, Object>> getMenuCodeList() {
		return monitorHistoryDao.getMenuCodeList();
	}	
	
	@Override
	public List<Map<String, Object>> getSelectMenuCodeList(Map <String, Object> searchInfo) {
		return monitorHistoryDao.getSelectMenuCodeList(searchInfo);
	}
}
