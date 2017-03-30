/*******************************************************************************
 * Copyright(c) 2015 KT. All rights reserved.
 * This software is the proprietary information of KT.
 *******************************************************************************/
package com.kt.giga.home.openapi.ghms.ktInfra.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.kt.giga.home.openapi.ghms.ktInfra.domain.vo.SpotDevBas;
import com.kt.giga.home.openapi.ghms.ktInfra.domain.vo.SpotDevExpnsnBas;
import com.kt.giga.home.openapi.ghms.ktInfra.domain.vo.SvcTgtBas;


/**
 * 
 * @author dahye.kim (dahye.kim@ceinside.co.kr)
 * @since 2015. 3. 12.
 */
public interface GhmsProvisionDao {

    /**
     * 서비스 청약 정보 조회
     * 
     * @param svcContId     서비스 계약 아이디
     * @return
     */
    public SvcTgtBas selectSvcTgtBasBySvcContId(String svcContId);

    /**
     * 서비스 청약 정보 조회
     * 
     * @param svcContId     서비스 계약 아이디
     * @return
     */
    public SvcTgtBas selectSvcTgtBasBySvcContIdByMac(
			@Param("svcContId") String svcContId,
			@Param("svcTgtSeq") Long svcTgtSeq,
			@Param("macAddr") String macAddr);

    
    /**
     * 장치 정보 조회
     * 
     * @param spotDevBas
     * @return
     */
    public SpotDevBas selectSpotDevBasBySpotDevId(SpotDevBas spotDevBas);
    
    /**
     * 현장 장치 일련번호 생성
     * 
     * @return
     */
    public long selectSvcTgtSeq();
    
    /**
     * 서비스 대상 기본 등록
     * 
     * @param svcTgtBas
     */
    public void insertSvcTgtBas(SvcTgtBas svcTgtBas);
    
    /**
     * 서비스 대상 기본 변경
     * 
     * @param map
     */
    public void updateSvcTgtBas(Map<String, Object> map);
    
    /**
     * 삭제되지 않은 현장장치 목록 조회
     * 
     * @param svcTgtSeq     서비스대상일련번호
     * @return
     */
    public List<SpotDevBas> selectSpotDevBasListByUse(long svcTgtSeq);
    
    /**
     * 현장 장치 일련번호 조회
     * 
     * @param svcTgtSeq     서비스대상일련번호
     * @return
     */
    public long selectSpotDevSeq(long svcTgtSeq);
    
    /**
     * 장치 모델 일련번호 조회
     * 
     * @param gwModelCd
     * @return
     */
    public long selectDevModelSeq(String gwModelCd);
    
    /**
     * 현장 장치 기본 등록
     * 
     * @param spotDevBas
     */
    public void insertSpotDevBas(SpotDevBas spotDevBas);
    
    /**
     * 현장 장치 확장 기본 등록
     * 
     * @param sdeb
     */
    public void insertSpotDevExpnsnBas(SpotDevExpnsnBas sdeb);
    
    /**
     * 현장 장치 기본 데이터 수정
     * 
     * @param map
     */
    public void updateSpotDevBas(Map<String, Object> map);
    
    /**
     * 장치 모델 기본 데이터 조회
     * 
     * @param gwModelCd
     * @return
     */
    public Map<String, Object> selectDevModelBas(String gwModelCd);
    
    /**
     * 현장 장치 확장 기본 변경
     * 
     * @param sdeb
     */
    public void updateSpotDevExpnsnBas(SpotDevExpnsnBas sdeb);
    
    /**
     * 임의고객청약내역 데이터 존재 여부
     * @param svcTgtSeq
     * @param serviceNo
     * @return
     */
    public int selectRanCustSubsTxnCount(
			@Param("svcTgtSeq") Long svcTgtSeq,
			@Param("serviceNo") String serviceNo);
    
    /**
     * 임의고객청약내역 데이터 존재 등록
     * @param svcTgtSeq
     * @param serviceNo
     * @return
     */
    public void insertRanCustSubsTxnCount(
			@Param("svcTgtSeq") Long svcTgtSeq,
			@Param("serviceNo") String serviceNo);
    
    /**
     * 임의고객청약내역 데이터 존재 갱신
     * @param svcTgtSeq
     * @param serviceNo
     * @return
     */
    public void updateRanCustSubsTxnCount(
			@Param("svcTgtSeq") Long svcTgtSeq,
			@Param("serviceNo") String serviceNo);
    
    
}
