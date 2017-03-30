/*******************************************************************************
 * Copyright(c) 2015 KT. All rights reserved.
 * This software is the proprietary information of KT.
 *******************************************************************************/
package com.kt.giga.home.openapi.ghms.devices.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.kt.giga.home.openapi.ghms.devices.domain.key.DeviceModeKey;
import com.kt.giga.home.openapi.ghms.devices.domain.vo.DeviceModeVo;
import com.kt.giga.home.openapi.ghms.devices.domain.vo.NewSpotDevice;
import com.kt.giga.home.openapi.ghms.devices.domain.vo.NewSpotDeviceGw;
import com.kt.giga.home.openapi.ghms.devices.domain.vo.SpotDevice;
import com.kt.giga.home.openapi.ghms.devices.domain.vo.SpotDeviceGw;
import com.kt.giga.home.openapi.ghms.devices.domain.vo.SpotDeviceSnsnData;
import com.kt.giga.home.openapi.ghms.kafka.smcp.gw.ca.domn.spotdev.SpotDev;
import com.kt.giga.home.openapi.ghms.kafka.smcp.gw.ca.domn.spotdev.SpotDevDtl;
import com.kt.giga.home.openapi.ghms.ktInfra.domain.vo.SpotDevBas;

/**
 * 제어 Dao
 * @author junypooh ( kyungjun.park@ceinside.co.kr )
 * @since 2015. 2. 16.
 */
public interface DevicesDao {
	
	/**
	 * 현장 장비별 제어 설정 값 조회
	 * @param key
	 * @return
	 */
	public List<DeviceModeVo> selectModeStatusSetupTxn(DeviceModeKey key);
	
	/**
	 * 모드 설정 상태 존재 유무
	 * @param key
	 * @return
	 */
	public int selectModeStatus(DeviceModeKey key);
	
	/**
	 * 모드 상태 갱신
	 * @param key
	 */
	public void insertModeStatus(DeviceModeKey key);
	
	/**
	 * 모드 상태 갱신
	 * @param key
	 */
	public void updateModeStatus(DeviceModeKey key);
	
	/**
	 * 모드 상태 코드 조회
	 * @param key
	 * @return String 모드 상태 코드
	 */
	public String selectModeStatusCode(DeviceModeKey key);
	
	/**
	 * 모드 별 장비 상태 코드 조회
	 * @param key
	 * @return List<DeviceModeVo>
	 */
	public List<DeviceModeVo> selectDeviceModeStatusCode(DeviceModeKey key);

	/**
	 * 모드 별 장비 상태 코드 수정
	 * @param deviceKey
	 */
	public void updateModeStatusSetupTxn(DeviceModeKey deviceKey);

	/**
	 * 장치 닉네임 설정
	 * @param serviceNo
	 * @param gwUUID
	 * @param devNm
	 */
	public void updateSpotDeviceNickName(
			@Param("serviceNo")		long serviceNo,
			@Param("spotDevSeq")		long spotDevSeq,
			@Param("devNm")			String devNm);
	
	/**
	 * 현장 장치 조회
	 * 
	 * @param svcCode      단위서비스코드
	 * @param devUUID      디바이스 UUID
	 * @param svcTgtSeq    서비스 대상 일련번호
	 * @param spotDevSeq   현장 장치 일련번호
	 * @return             현장 장치 SpotDev 리스트
	 */
	public ArrayList<SpotDev> selectSpotDev(
	        @Param("unitSvcCd")    String unitSvcCd,
	        @Param("devUUID")      String devUUID,
	        @Param("svcTgtSeq")    Long svcTgtSeq,
	        @Param("spotDevSeq")   Long spotDevSeq
	        );
	
	/**
	 * 현장 장치 상세 정보 조회
	 * 
	 * @param svcTgtSeq
	 * @param spotDevSeq
	 * @param spotDevItemNm
	 * @return
	 */
	public ArrayList<SpotDevDtl> selectSpotDevDtl(
	        @Param("svcTgtSeq")        long svcTgtSeq,
	        @Param("spotDevSeq")       long spotDevSeq,
	        @Param("spotDevItemNm")    String spotDevItemNm
	        );
	
	/**
	 * 게이트웨이 정보 조회
	 * 
	 * @param mbrSeq
	 * @param dstrCd
	 * @param svcThemeCd
	 * @param unitSvcCd
	 * @param gwUUID
	 * @return
	 */
	public List<SpotDeviceGw> selectSpotGateWayList(
	        @Param("mbrSeq")        	long mbrSeq,
	        @Param("dstrCd")        	String dstrCd,
	        @Param("svcThemeCd")        String svcThemeCd,
	        @Param("unitSvcCd")			String unitSvcCd,
	        @Param("svcTgtSeq")        	long svcTgtSeq,
	        @Param("spotDevSeq")			long spotDevSeq,
	        @Param("devTypeCd")			String devTypeCd
			);
	
	/**
	 * 게이트웨이 정보 조회(IFTTT 용)
	 * 
	 * @param mbrSeq
	 * @param dstrCd
	 * @param svcThemeCd
	 * @param unitSvcCd
	 * @param gwUUID
	 * @return
	 */
	public List<NewSpotDeviceGw> selectSpotGateWayList2(
	        @Param("mbrSeq")        	long mbrSeq,
	        @Param("dstrCd")        	String dstrCd,
	        @Param("svcThemeCd")        String svcThemeCd,
	        @Param("unitSvcCd")			String unitSvcCd,
	        @Param("svcTgtSeq")        	long svcTgtSeq,
	        @Param("spotDevSeq")			long spotDevSeq,
	        @Param("devTypeCd")			String devTypeCd
			);

	/**
	 * 현장장치 정보 조회
	 * 
	 * @param spotGroupCd
	 * @param gateWaySpotDevSeq
	 * @return
	 */
	public List<SpotDevice> selectSpotDevList(
	        @Param("spotGroupCd")       String spotGroupCd,
	        @Param("svcTgtSeq")        	long svcTgtSeq,
	        @Param("gateWaySpotDevSeq")	Long gateWaySpotDevSeq,
	        @Param("spotDevSeq")		Long spotDevSeq,
	        @Param("mbrSeq")        	long mbrSeq
			);

	/**
	 * 현장장치 정보 조회(고도화 테스트 임시 사용)
	 * 
	 * @param spotGroupCd
	 * @param gateWaySpotDevSeq
	 * @return
	 */
	public List<NewSpotDevice> selectSpotDevList2(
	        @Param("svcTgtSeq")        	long svcTgtSeq,
	        @Param("gateWaySpotDevSeq")	Long gateWaySpotDevSeq,
	        @Param("spotDevSeq")		Long spotDevSeq,
	        @Param("mbrSeq")        	long mbrSeq
			);
	
	/**
	 * 현장장치일반설정내역 조회
	 * 
	 * @param svcTgtSeq
	 * @param spotDevSeq
	 * @return
	 */
	public List<SpotDeviceSnsnData> selectSpotDevSetupTxn(
	        @Param("svcTgtSeq")        long svcTgtSeq,
	        @Param("spotDevSeq")       long spotDevSeq
	        );

    /**
     * 회원에 연결된 현장 장치 조회
     *
     * @param unitSvcCd         단위서비스 코드
     * @param mbrSeq            회원 일련번호
     * @param devUUID           디바이스 UUID
     * @param svcTgtSeq         서비스 대상 일련번호
     * @param spotDevSeq        현장 장치 일련번호
     * @return                  현장 장치 SpotDev 리스트
     */
    public ArrayList<SpotDev> selectMemberSpotDev(
            @Param("unitSvcCd")     String unitSvcCd,
            @Param("mbrSeq")        Long mbrSeq,
            @Param("devUUID")       String devUUID,
            @Param("svcTgtSeq")     Long svcTgtSeq,
            @Param("spotDevSeq")    Long spotDevSeq
            );

    /**
     * 현장 장치 상세 정보 업데이트
     *
     * @param svcTgtSeq         서비스 대상 일련번호
     * @param spotDevSeq        현장 장치 일련번호
     * @param spotDevItemNm     항목 이름
     * @param spotDevItemVal    항목 값
     */
    public int updateSpotDevDtl(
            @Param("svcTgtSeq")         long svcTgtSeq,
            @Param("spotDevSeq")        long spotDevSeq,
            @Param("spotDevItemNm")     String spotDevItemNm,
            @Param("spotDevItemVal")    String spotDevItemVal
            );

    /**
     * 현장 장치 상세 정보 삽입
     *
     * @param svcTgtSeq         서비스 대상 일련번호
     * @param spotDevSeq        현장 장치 일련번호
     * @param spotDevItemNm     항목 이름
     * @param spotDevItemVal    항목 값
     */
    public void insertSpotDevDtl(
            @Param("svcTgtSeq")         long svcTgtSeq,
            @Param("spotDevSeq")        long spotDevSeq,
            @Param("spotDevItemNm")     String spotDevItemNm,
            @Param("spotDevItemVal")    String spotDevItemVal
            );
    
    /**
     * 현장장치기본 장치명 조회(현장장치용)
     * @param svcTgtSeq     서비스대상일련번호
     * @param spotDevSeq    현장장치일련번호
     * @return
     */
    public String selectDevNmFromSpotDevBas(
            @Param("svcTgtSeq")         long svcTgtSeq,
            @Param("spotDevSeq")        long spotDevSeq
            );
    
    /**
     * 현장장치기본 장치명 조회(GW용)
     * @param svcTgtSeq 서비스대상일련번호
     * @return
     */
    public String selectGWDevNmFromSpotDevBas(
            @Param("svcTgtSeq")         long svcTgtSeq
            );

	/**
	 * 장치 일반설정 데이터 존재여부를 체크
	 * 
	 * @param map
	 * @return int
	 */
	public int selectSpotDevGenlSetupTxnCnt(Map<String, Object> input);

	/**
	 * 장치 일반설정 데이터 삭제
	 * 
	 * @param void
	 * @return
	 */
	public void deleteSpotDevGenlSetupTxn(Map<String, Object> input);

	/**
	 * 장치 일반설정 데이터 입력
	 * 
	 * @param void
	 * @return
	 */
	public void insertSpotDevGenlSetupTxn(Map<String, Object> input);

	/**
	 * 장치 일반설정 데이터 수정
	 * 
	 * @param void
	 * @return
	 */
	public void updateSpotDevGenlSetupTxn(Map<String, Object> input);
	
	/**
	 * 회원 비밀번호 count 조회
	 * @param svcTgtSeq
	 * @param spotDevSeq
	 * @param pwd
	 * @param userCdSeq
	 * @return
	 */
	public int selectCountMbrPwdTnx(
            @Param("svcTgtSeq")		long svcTgtSeq,
            @Param("spotDevSeq")	long spotDevSeq,
            @Param("userCdSeq")		int userCdSeq
            );

	/**
	 * 회원 비밀번호 데이터 수정
	 * @param svcTgtSeq
	 * @param spotDevSeq
	 * @param pwd
	 * @param userCdSeq
	 * @return
	 */
	public void updateMbrPwdTnx(
            @Param("svcTgtSeq")		long svcTgtSeq,
            @Param("spotDevSeq")	long spotDevSeq,
            @Param("pwd")     		String pwd,
            @Param("userCdSeq")		int userCdSeq
            );

	/**
	 * 회원 비밀번호 데이터 등록
	 * @param svcTgtSeq
	 * @param spotDevSeq
	 * @param pwd
	 * @param userCdSeq
	 * @return
	 */
	public void insertMbrPwdTnx(
            @Param("svcTgtSeq")		long svcTgtSeq,
            @Param("spotDevSeq")	long spotDevSeq,
            @Param("pwd")     		String pwd,
            @Param("userCdSeq")		int userCdSeq
            );
	
	/**
	 * 서비스일련번호로 GW 정보 조회
	 * @param svcTgtSeq
	 */
	public SpotDevBas selectGwSpotDevSeqBySvcTgtSeq(
            @Param("svcTgtSeq")		long svcTgtSeq
			);
	
	/**
	 * 사용되고 있는 장치 이름 조회
	 * @param mbrSeq
	 * @param devNm
	 * @return
	 */
	public int selectSpotDevNmCount(
            @Param("mbrSeq")	Long mbrSeq,
			@Param("devNm")			String devNm,
            @Param("svcTgtSeq")		long svcTgtSeq,
            @Param("spotDevSeq")		long spotDevSeq
			);

}
