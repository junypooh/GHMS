/*******************************************************************************
 * Copyright(c) 2015 KT. All rights reserved.
 * This software is the proprietary information of KT.
 *******************************************************************************/
package com.kt.giga.home.openapi.ghms.user.service;

import java.sql.SQLException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.kt.giga.home.openapi.ghms.common.domain.vo.BaseVo;
import com.kt.giga.home.openapi.ghms.user.dao.MyPageDao;
import com.kt.giga.home.openapi.ghms.user.domain.key.MasterUserKey;
import com.kt.giga.home.openapi.ghms.user.domain.vo.MasterUserVo;
import com.kt.giga.home.openapi.ghms.util.exception.APIException;

/**
 * 비상연락망 처리 서비스
 * @author junypooh ( kyungjun.park@ceinside.co.kr )
 * @since 2015. 2. 11.
 */
@Service
@Transactional(
        propagation = Propagation.REQUIRED,
        rollbackFor = {
                Exception.class,
                RuntimeException.class,
                SQLException.class
        }
)
public class MyPageService {

    /**
     * 로거
     */
    private Logger log = LoggerFactory.getLogger(this.getClass());
    
    @Autowired
    private MyPageDao myPageDao;

	/**
	 * 비상연락망 조회
	 * @param key		마스터 사용자 Key
	 * @return List<MasterUserVo>
	 * @throws APIException
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly=true)
    public List<MasterUserVo> selectEmcontacts(MasterUserKey key) throws APIException {
    	return myPageDao.selectEmcontacts(key);
    }
	
	/**
	 * 비상연락망 등록
	 * @param key		마스터 사용자 Key
	 * @throws APIException
	 */
	public BaseVo insertEmcontacts(MasterUserKey key) throws APIException {
		
		BaseVo vo = new BaseVo();
		
		try {
			myPageDao.insertEmcontacts(key);
			vo.setResultCode("0");
		} catch (Exception ex) {
			log.error("비상연락망 등록 에러");
			ex.printStackTrace();
			vo.setResultCode("-1");
		}
		
		return vo;
	}
	
	/**
	 * 비상연락망 삭제
	 * @param key		마스터 사용자 Key
	 * @throws APIException
	 */
	public BaseVo deleteEmcontacts(MasterUserKey key) throws APIException {
		
		BaseVo vo = new BaseVo();
		int delRows = 0;
		
		try {
			delRows = myPageDao.deleteEmcontacts(key);
			
			if(delRows > 0) {
				vo.setResultCode("0");	
			} else {
				log.warn("비상연락망 삭제 에러 삭제 대상 건 없음");
				vo.setResultCode("-1");
			}
		} catch (Exception ex) {
			log.error("비상연락망 삭제 에러");
			ex.printStackTrace();
			vo.setResultCode("-1");
		}
		
		return vo;
	}
	
	/**
	 * 비상연락망 수정 (SMS 발송여부 UPDATE)
	 * 
	 * @param key      마스터 사용자 Key
	 * @return
	 * @throws APIException
	 */
	public BaseVo updateEmcontacts(MasterUserKey key) throws APIException{
	    
	    BaseVo vo = new BaseVo();
	    int updRows = 0;
	    try {
	        updRows = myPageDao.updateEmcontacts(key);
	        
	        if(updRows > 0){
	            vo.setResultCode("0");
	        } else {
	            log.warn("비상연락망 수정 에러. 수정 대상 건 없음");
	            vo.setResultCode("-1");
	        }
	    } catch(Exception ex) {
	        log.error("비상연락망 수정 에러");
	        ex.printStackTrace();
	        vo.setResultCode("-1");
	    }
	    
	    return vo;
	}

}
