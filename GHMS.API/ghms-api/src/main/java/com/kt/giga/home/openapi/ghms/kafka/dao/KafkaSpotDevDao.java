/*******************************************************************************
 * Copyright(c) 2015 KT. All rights reserved.
 * This software is the proprietary information of KT.
 *******************************************************************************/
package com.kt.giga.home.openapi.ghms.kafka.dao;

import java.util.List;
import java.util.Map;

import com.kt.giga.home.openapi.ghms.kafka.entity.HbaseControlHistory;
import com.kt.giga.home.openapi.ghms.kafka.smcp.gw.ca.avro.ComplexEvent;

/**
 * @author jnam
 */
public interface KafkaSpotDevDao {
        
    /**
     * 장치 일반설정 데이터 존재여부를 체크
     * @param map
     * @return int
     */
    public int              selectSpotDevGenlSetupTxnCnt(Map<String, Object> input);
    
    /**
     * 장치 일반설정 데이터 입력
     * @param void
     * @return
     */
    public void              insertSpotDevGenlSetupTxn(Map<String, Object> input);
    
    /**
     * 장치 일반설정 데이터 수정
     * @param void
     * @return
     */
    public void              updateSpotDevGenlSetupTxn(Map<String, Object> input);
    
    /**
     * 장치 제어 이력을 쌓기위한 데이터를 가져오는 쿼리
     * @param HbaseControlHistory
     * @return
     */
    public HbaseControlHistory              selectHbaseControlHistory(Map<String, Object> input);
    public List<Map<String, Object>>              selectSensorList(Map<String, Object> input);
    public String              selectSpotDevNm(Map<String, Object> input);
    public Long              selectSpotDevSeq(Map<String, Object> input);
}
