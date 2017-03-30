/*******************************************************************************
 * Copyright(c) 2015 KT. All rights reserved.
 * This software is the proprietary information of KT.
 *******************************************************************************/
package com.kt.giga.home.openapi.ghms.kafka.dao;

import java.util.Map;

import com.kt.giga.home.openapi.ghms.kafka.entity.HbaseControlHistory;

/**
 * @author jnam
 */
public interface KafkaMbrPwdDao {
        
    /**
     * 회원 비빌번호 내역 데이터 존재여부를 체크
     * @param map
     * @return int
     */
    public int              selectMbrPwdTxnCnt(Map<String, Object> input);
    
    /**
     * 회원 비빌번호 내역 입력
     * @param void
     * @return
     */
    public void              insertMbrPwdTxn(Map<String, Object> input);
    
    /**
     * 회원 비빌번호 내역 수정
     * @param void
     * @return
     */
    public void              updateMbrPwdTxn(Map<String, Object> input);
    public void              deleteMbrPwdTxn(Map<String, Object> input);
    public String              selectMbrPwdTxnNm(Map<String, Object> input);
    
}
