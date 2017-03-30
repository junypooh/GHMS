package com.kt.giga.home.cms.manage.service;

import java.util.*;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.*;

import com.kt.giga.home.cms.manage.dao.*;

@Service
public class BetaCamServiceImpl implements BetaCamService {

	@Autowired
	private BetaCamDao betaCamDao;
	
	public List<Map<String, Object>> getCamList() {
		return betaCamDao.getCamList();
	}
	
	public List<Map<String, Object>> getCamInfoList(Map<String, Object> searchInfo) {
		
		String[] svcTgtSeqListStr = searchInfo.get("svcTgtSeqList").toString().split(",");
		String[] spotDevSeqListStr = searchInfo.get("spotDevSeqList").toString().split(",");
		int[] svcTgtSeqList = new int[svcTgtSeqListStr.length];
		int[] spotDevSeqList = new int[spotDevSeqListStr.length];
		int i;
		for(i=0 ; i<svcTgtSeqListStr.length ; i++){
			//System.out.println("svcTgtSeqListStr[i] = " + svcTgtSeqListStr[i]);
			svcTgtSeqList[i] = Integer.parseInt(svcTgtSeqListStr[i]);
		}
		for(i=0 ; i<spotDevSeqListStr.length ; i++){
			//System.out.println("spotDevSeqListStr[i] = " + spotDevSeqListStr[i]);
			spotDevSeqList[i] = Integer.parseInt(spotDevSeqListStr[i]);
		}
		
		searchInfo.put("svcTgtSeqList", svcTgtSeqList);
		searchInfo.put("spotDevSeqList", spotDevSeqList);
		
		return betaCamDao.getCamInfoList(searchInfo);
	}
	
	public void register(Map<String, Object> verFrmwr){
		
		String[] svcTgtSeqList = verFrmwr.get("svcTgtSeqList").toString().split(",");
		String[] spotDevSeqList = verFrmwr.get("spotDevSeqList").toString().split(",");
		
		for(int i=0 ; i<svcTgtSeqList.length ; i++){
			verFrmwr.put("svcTgtSeq", Integer.parseInt(svcTgtSeqList[i]));
			verFrmwr.put("spotDevSeq", Integer.parseInt(spotDevSeqList[i]));
			betaCamDao.register(verFrmwr);
		}
	}
	
	public void remove(Map<String, Object> verFrmwr){
		
		int svcTgtSeq = Integer.parseInt(verFrmwr.get("svcTgtSeq").toString());
		int spotDevSeq = Integer.parseInt(verFrmwr.get("spotDevSeq").toString());
		verFrmwr.put("svcTgtSeq", svcTgtSeq);
		verFrmwr.put("spotDevSeq", spotDevSeq);
		betaCamDao.remove(verFrmwr);
	}

}
