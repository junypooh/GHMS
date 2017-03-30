/*******************************************************************************
 * Copyright(c) 2015 KT. All rights reserved.
 * This software is the proprietary information of KT.
 *******************************************************************************/
package com.kt.giga.home.openapi.ghms.devices.dao;

import java.util.List;
import java.util.Map;

import com.kt.giga.home.openapi.ghms.devices.domain.key.SpotDevBasKey;
import com.kt.giga.home.openapi.ghms.ktInfra.domain.vo.SpotDevBas;
import com.kt.giga.home.openapi.ghms.ktInfra.domain.vo.SvcTgtBas;

/**
 * 장치 추가 시 초기 데이터 생성 DAO
 * @author junypooh ( kyungjun.park@ceinside.co.kr )
 * @since 2015. 3. 24.
 */
public interface InitDeviceAddDao {
	
	public SpotDevBas selectSpotDevBas(SpotDevBasKey key);
	
	/**
	 * 회원현장장치설정내역 삭제
	 * @param map
	 */
	public void deleteMbrSpotDevSetupTxn(Map<String, Object> map);
	
	/**
	 * 회원현장장치설정내역
	 * @param map
	 */
	public void insertMbrSpotDevSetupTxn(Map<String, Object> map);
	
	/**
	 * 홈모드별상태설정내역 delete
	 * @param map
	 */
	public void deleteHomModeBySttusSetupTxn(Map<String, Object> map);
	
	/**
	 * 홈모드별상태설정내역 INSERT
	 * @param map
	 */
	public void insertHomModeBySttusSetupTxn(Map<String, Object> map);
	
	/**
	 * 센싱태그코드 조회
	 * @param snsTagNm
	 * @return
	 */
	public List<Map<String, Object>> selectSnsnTagCd(Map<String, Object> map);

	/**
	 * 장치모델별센싱태그관리 INSERT
	 * @param map
	 */
	public void insertDevModelBySnsnTagRel(Map<String, Object> map);
	
	/**
	 * 현장장치설정기본 INSERT
	 * @param map
	 */
	public void insertSpotDevSetupBas(Map<String, Object> map);
	
	/**
	 * 현장장치기본 등록
	 * @param key
	 */
	public void insertSpotDevBas(SpotDevBasKey key);
	
	/**
	 * 현장장치 타입별 갯수 조회
	 * @param key
	 * @return
	 */
	public int selectSoptDevBasByType(SpotDevBasKey key);
	
	/**
	 * 현장장치 타입별 갯수 조회
	 * @param key
	 * @return
	 */
	public int selectSoptDevBasGateWayCount(SvcTgtBas key);
	
	/**
	 * 현장장치기본 수정
	 * @param key
	 */
	public void updateSpotDevBas(SpotDevBasKey key);
	
	/**
	 * 현장장치 ID로 현장장치 SEQ 조회
	 * @param key
	 * @return
	 */
	public long selectSpotDevSeqBySpotDevId(SpotDevBasKey key);
	
	/**
	 * 현장장치기본 삭제
	 * @param key
	 */
	public void deleteSpotDevBas(SpotDevBasKey key);
	
	/**
	 * 현장장치기본 펌웨어버전 데이터 업데이트
	 * @param key
	 * @return
	 */
	public int updateFrmwrVerNoFromSpotDevBas(SpotDevBasKey key);
	
	/**
	 * 게이트웨이 연결시 정보 update
	 * @param key
	 * @return
	 */
	public int updateGateWayTmpDev(SpotDevBasKey key);
	
	/**
	 * 게이트웨이에 연결된 하위 장치 정보 조회
	 * @param key
	 * @return
	 */
	public List<Long> selectSpotDevsConnectedByGw(SpotDevBasKey key);
	
	/**
	 * 게이트웨이에 연결된 하위 장치 삭제
	 * @param key
	 * @return
	 */
	public int deleteSpotDevsConnectedByGw(SpotDevBasKey key);
	
	/**
	 * 하위 장치 공장 초기화 시간 정보 조회
	 * @param key
	 * @return
	 */
	public List<Map<String, Object>> selectSpotDevsLastMtnDt(SpotDevBasKey key);
}
