package com.kt.giga.home.openapi.ghms.kafka.service;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kt.giga.home.openapi.ghms.kafka.dao.KafkaMbrPwdDao;

/**
 * @author jnam
 */
@Service
public class KafkaMbrPwdService{

	public static Log logger = LogFactory.getLog(KafkaMbrPwdService.class);
	
	@Autowired public KafkaMbrPwdDao kafkaMbrPwdDao;
	
	/**
	 * @param input MbrPwdTxn 데이터를 저장한다.
	 */
	@Transactional
	public void saveMbrPwdTxn(Map<String, Object> input){
		int count = kafkaMbrPwdDao.selectMbrPwdTxnCnt(input);
		if(count > 0){
			kafkaMbrPwdDao.updateMbrPwdTxn(input);
		}else{
			kafkaMbrPwdDao.insertMbrPwdTxn(input);
		}
	}
	
	/**
	 * @param input MbrPwdTxn 데이터를 삭제한다.
	 */
	public void deleteMbrPwdTxn(Map<String, Object> input){
		kafkaMbrPwdDao.deleteMbrPwdTxn(input);
		
	}
	
	
	public String selectMbrPwdTxnNm(Long svcTgtSeq, Long spotDevSeq, Long userSeq){
		Map<String, Object> input = new HashMap<String, Object>();
		
		input.put("svcTgtSeq", svcTgtSeq);
		input.put("spotDevSeq", spotDevSeq);
		input.put("userSeq", userSeq);
		
		return kafkaMbrPwdDao.selectMbrPwdTxnNm(input);
	}
	
}





















