package com.kt.giga.home.cms.inquiry.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kt.giga.home.cms.inquiry.dao.PnsHistoryDao;

@Service
public class PnsHistoryServiceImpl implements PnsHistoryService{
	@Autowired
	private PnsHistoryDao pnsHistoryDao;
	
	@Override
	public int getCount(Map<String, Object> searchInfo) {
		return pnsHistoryDao.getCount(searchInfo);
	}	
	
	@Override
	public List<Map<String, Object>> getList(Map<String, Object> searchInfo) {
		String maskStr, tempStr = "";
		List<Map<String, Object>> pnsHistoryList = pnsHistoryDao.getList(searchInfo);
		
		if(pnsHistoryList != null){
			for(Map<String, Object> nMap : pnsHistoryList){
				
				if(nMap.get("mbrId") != null){

					tempStr = nMap.get("mbrId").toString();
					if(tempStr.length() > 3) {
						maskStr = tempStr.substring(0, tempStr.length() - 3) + "***";
						nMap.put("mbrId", maskStr);
					}else{nMap.put("mbrId", "");}
					
				}else{ nMap.put("mbrId", ""); }
				
				if(nMap.get("telNo") != null){

					tempStr = nMap.get("telNo").toString().trim();
					if(tempStr.length() > 4) {
						maskStr = tempStr.substring(0, tempStr.length() - 4) + "****";
						nMap.put("telNo", maskStr);
					}else{nMap.put("telNo", "");}
					
				}else{ nMap.put("telNo", ""); }
				
				
				if(nMap.get("msgTrmDt") != null){

					nMap.put("msgTrmDt1", getDetailmsgTrmDtInfo((nMap.get("msgTrmDt")).toString(), 0));
					nMap.put("msgTrmDt2", getDetailmsgTrmDtInfo((nMap.get("msgTrmDt")).toString(), 1));
					
				}else{
					nMap.put("msgTrmDt", "-");
					nMap.put("msgTrmDt1", "-"); 
					nMap.put("msgTrmDt2", "-");
				}
				
				if(nMap.get("msgRcvDt") != null){

					nMap.put("msgRcvDt1", getDetailmsgTrmDtInfo((nMap.get("msgRcvDt")).toString(), 0));
					nMap.put("msgRcvDt2", getDetailmsgTrmDtInfo((nMap.get("msgRcvDt")).toString(), 1));
					
				}else{
					nMap.put("msgRcvDt", "-");
					nMap.put("msgRcvDt1", "-"); 
					nMap.put("msgRcvDt2", "-");
				}
				
			}
		}
		
		return pnsHistoryList;
	}

	@Override
	public Map<String, Object> get(Map<String, Object> searchInfo) {
		// TODO Auto-generated method stub
		return null;
	}
	
	/**
	 * 시간정보 변환 1999-2-4 21:09:xxx => 1999.2.4 pm09:09
	 * @param trmDt	단위서비스 메시지전송일시
	 * @param type	날짜?시간? 구분
	 * @return
	 */
	private String getDetailmsgTrmDtInfo(String trmDt, int type)
	{
		String str="";
		try {
			String info[] = trmDt.split(" ");
			if( type == 0 )
			{
				return info[0].replace("-", ".");
			}
			else
			{
				String timeStr[] = info[1].split(":");
				int timeNumber = Integer.parseInt(timeStr[0]);
				return 	(timeNumber < 12 ? "am" : "pm") + 
						((timeNumber%12)/10 == 0 ? "0":"") + 
						(timeNumber%12) + ":" + 
						timeStr[1];
			}
		} catch (Exception e) {}

		return str;
	}

	@Override
	public List<Map<String, Object>> getEntList(Map<String, Object> searchInfo) {
		return pnsHistoryDao.getEntList(searchInfo);
	}

	@Override
	public List<Map<String, Object>> getSvcList() {
		return pnsHistoryDao.getSvcList();
	}

	@Override
	public List<Map<String, Object>> getListDetail(
			Map<String, Object> searchInfo) {
		String maskStr, tempStr = "";
		
		List<Map<String, Object>> pnsHistoryDetail = pnsHistoryDao.getListDetail(searchInfo);
		
		if(pnsHistoryDetail != null){
			for(Map<String, Object> nMap : pnsHistoryDetail){
				if(nMap.get("mbrId") != null){

					tempStr = nMap.get("mbrId").toString();
					if(tempStr.length() > 3) {
						maskStr = tempStr.substring(0, tempStr.length() - 3) + "***";
						nMap.put("mbrId", maskStr);
					}else{nMap.put("mbrId", "");}
					
				}else{ nMap.put("mbrId", ""); }
				
				if(nMap.get("telNo") != null){

					tempStr = nMap.get("telNo").toString().trim();
					if(tempStr.length() > 4) {
						maskStr = tempStr.substring(0, tempStr.length() - 4) + "****";
						nMap.put("telNo", maskStr);
					}else{nMap.put("telNo", "");}
					
				}else{ nMap.put("telNo", ""); }
				
				if(nMap.get("msgTrmDt") != null){
		
					nMap.put("msgTrmDt1", getDetailmsgTrmDtInfo((nMap.get("msgTrmDt")).toString(), 0));
					nMap.put("msgTrmDt2", getDetailmsgTrmDtInfo((nMap.get("msgTrmDt")).toString(), 1));
					
				}else{ nMap.put("msgTrmDt", ""); }
				
				if(nMap.get("msgRcvDt") != null){

					nMap.put("msgRcvDt1", getDetailmsgTrmDtInfo((nMap.get("msgTrmDt")).toString(), 0));
					nMap.put("msgRcvDt2", getDetailmsgTrmDtInfo((nMap.get("msgTrmDt")).toString(), 1));
					
				}else{ 
					nMap.put("msgRcvDt1", "--"); 
					nMap.put("msgRcvDt2", "--");
				}
			}
			
		}
		
		return pnsHistoryDetail;
	}
}




