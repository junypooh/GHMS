package com.kt.giga.home.cms.inquiry.service;

import java.util.*;

import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

import com.kt.giga.home.cms.inquiry.dao.*;

@Service
public class PnsSetServiceImpl implements PnsSetService{
	@Autowired
	private PnsSetDao pnsSetDao;
	
	@Override
	public int getCount(Map<String, Object> searchInfo) {
		return pnsSetDao.getCount(searchInfo);
	}	
	
	@Override
	public List<Map<String, Object>> getList(Map<String, Object> searchInfo) {
		return pnsSetDao.getList(searchInfo);
	}
	
	@Override
	public int getDetailCount(Map<String, Object> searchInfo) {
		return pnsSetDao.getDetailCount(searchInfo);
	}	
	
	@Override
	public List<Map<String, Object>> getDetailList(Map<String, Object> searchInfo) {
		return pnsSetDao.getDetailList(searchInfo);
	}
	
	@Override
	public int getPushHistoryCount(Map<String, Object> searchInfo) {
		return pnsSetDao.getPushHistoryCount(searchInfo);
	}
	
	@Override
	public List<Map<String, Object>> getPushHistory(Map<String, Object> searchInfo) {
		return pnsSetDao.getPushHistory(searchInfo);
	}

	@Override
	public List<Map<String, Object>> getEntList() {
		return pnsSetDao.getEntList();
	}

	@Override
	public List<Map<String, Object>> getSvcList() {
		return pnsSetDao.getSvcList();
	}

	
	@Override
	public List<Map<String, Object>> getListSubInfo(Map<String, Object> searchInfo) {
		return pnsSetDao.getListSubInfo(searchInfo);
	}
	
	@Override
	public List<Object> getListEx(List<Map<String, Object>> pnsSetList) {
		
		List<Object> pnsSetListEx = new ArrayList<Object>();
		
		String[] arrStr1; 
		String maskStr, regDate, tempStr = "";
		
		if(pnsSetList != null){
			
			for(Map<String, Object> list : pnsSetList){

				List<Map<String, Object>> setupVal = pnsSetDao.getListSubInfo(list);
				list.put("setupVal"	, setupVal);
				
				pnsSetListEx.add(list);
			}
			
			for(Map<String, Object> nMap : pnsSetList){
				
				if(nMap.get("mbrId") != null){

					tempStr = nMap.get("mbrId").toString();
					if(tempStr.length() > 3) {
						maskStr = tempStr.substring(0, tempStr.length() - 3) + "***";
						nMap.put("mbrId", maskStr);
						nMap.put("beforMbrId", tempStr);						
					}else{nMap.put("mbrId", "");}
					
				}else{ nMap.put("mbrId", ""); }
				
				if(nMap.get("telNo") != null){

					tempStr = nMap.get("telNo").toString();
					if(tempStr.length() > 4) {
						maskStr = tempStr.substring(0, tempStr.length() - 4) + "****";
						nMap.put("telNo", maskStr);
						nMap.put("beforTelNo", tempStr);
					}else{nMap.put("telNo", "");}
					
				}else{ nMap.put("telNo", ""); }

				if(nMap.get("cretDt") != null && nMap.get("cretDt").toString().length() >= 20){
					
					arrStr1 = nMap.get("cretDt").toString().split(" ");
					regDate = arrStr1[0];
					nMap.put("cretDt", regDate);
					
				}else{ nMap.put("cretDt", ""); }
				
				// 푸시설정
				if(nMap.get("statEvetNm") != null){
					tempStr = nMap.get("statEvetNm").toString();
					nMap.put("statEvetNm", tempStr);
				}else{ nMap.put("statEvetNm", ""); }

			}
		}

		return pnsSetListEx;
		
	}
}
