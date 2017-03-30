/*******************************************************************************
 * Copyright(c) 2015 KT. All rights reserved.
 * This software is the proprietary information of KT.
 *******************************************************************************/
package com.kt.giga.home.openapi.ghms.user.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.kt.giga.home.openapi.ghms.user.domain.key.MasterUserKey;
import com.kt.giga.home.openapi.ghms.user.domain.key.SvcEtsRelKey;
import com.kt.giga.home.openapi.ghms.user.domain.vo.DeviceVo;
import com.kt.giga.home.openapi.ghms.user.domain.vo.MasterUserVo;
import com.kt.giga.home.openapi.ghms.user.domain.vo.UserInfo;
import com.kt.giga.home.openapi.ghms.user.domain.vo.UserVo;

/**
 * 
 * @author junypooh ( kyungjun.park@ceinside.co.kr )
 * @since 2015. 2. 2.
 */
public interface MasterDao {
	
	/**
	 * 사용자 정보 조회.
	 * 
	 * @param telNo			전화번호
	 * @param mbrSeq		회원일련번호
	 * @param dstrCd		지역 코드
	 * @param svcThemeCd	서비스 테마 코드
	 * @param unitSvcCd		단위 서비스 코드
	 * @return UserVo		사용자 Class
	 */
	public UserVo selectMemberInfo(
            @Param("telNo")            	String telNo,
            @Param("mbrSeq")            Long mbrSeq,
            @Param("dstrCd")        	String dstrCd,
            @Param("svcThemeCd")        String svcThemeCd,
            @Param("unitSvcCd")         String unitSvcCd,
            @Param("masterYn")         	String masterYn);

	/**
	 * 기 승인 여부 확인
	 * @param key
	 * @return
	 */
	public int selectSvcEtsRel(SvcEtsRelKey key);
	
	/**
	 * SLAVE 사용자 서비스위임관계 테이블 등록(PUSH 승인 by 마스터)
	 * @param key		서비스 위임 관계 Key
	 */
	public void insertSlaveByPushIntoSvcEtsRel(SvcEtsRelKey key);
	
	/**
	 * SLAVE 사용자 서비스대상접속설정내역 테이블 등록(PUSH 승인 by 마스터)
	 * @param key		서비스 위임 관계 Key
	 */
	public void insertSlaveByPushIntoSvcTgtConnSetupTxn(SvcEtsRelKey key);
	
	/**
	 * SLAVE 사용자 서비스대상접속설정내역 테이블 등록(PUSH 승인 by 마스터) AP 서비스
	 * @param key		서비스 위임 관계 Key
	 */
	public void insertSlaveByPushIntoSvcTgtConnSetupTxnAp(SvcEtsRelKey key);
	
	/**
	 * 사용자 리스트 조회
	 * 
	 * @param key
	 * @return     사용자 리스트
	 */
	public List<MasterUserVo> selectSlaveUserList(MasterUserKey key);
	
	/**
	 * 서비스대상접속설정내역 테이블 조회
	 * 
	 * @param key
	 */
	public int selectUserMbrSpotDevSetupTxnCount(SvcEtsRelKey key);
	
	/**
	 * 사용자 삭제
	 * 서비스대상접속설정내역 테이블 DELETE
	 * 
	 * @param key
	 */
	public void deleteUserMbrSpotDevSetupTxn(SvcEtsRelKey key);
	
	
	/**
	 * 사용자 삭제
	 * 서비스위임관계 테이블 DELETE
	 * 
	 * @param key
	 */
	public void deleteUserSvcEtsRel(SvcEtsRelKey key);
	
	/**
	 * 사용자 디바이스(도어락) 회원비밀번호내역 비밀번호 조회
	 * 
	 * @param key
	 * @return
	 */
	public List<MasterUserVo> selectMbrPwdTxn(MasterUserKey key);
	
	/**
	 * 사용자 디바이스(도어락) 회원비밀번호내역 비밀번호 삭제
	 * 
	 * @param key
	 * @return
	 */
	public int deleteMbrPwdTxn(MasterUserKey key);
	
	/**
	 * 사용자 디바이스 사용권한 조회
	 * 
	 * @param key
	 * @return
	 */
	public List<DeviceVo> selectDeviceUseAuth(MasterUserKey key);
	
	/**
	 * 사용자 디바이스 사용권한 수정
	 * 
	 * @param key
	 * @return
	 */
	public int updateDeviceUserAuth(MasterUserKey key);
	
	/**
	 * 사용자 닉네임 조회
	 * @param key
	 */
	public String selectUserNickName(MasterUserKey key);
	
	/**
	 * 사용자 닉네임 등록
	 * @param key
	 */
	public void insertUserNickName(MasterUserKey key);
	
	/**
	 * 사용자 닉네임 수정
	 * @param key
	 */
	public void updateUserNickName(MasterUserKey key);
	
	/**
	 * IDMS 에서 넘어온 custNm 조회
	 * @param key
	 */
	public String selectSvcTgtNm(MasterUserKey key);
	
	/**
	 * 사용자 프로필 이미지 등록
	 * @param key
	 */
	public int insertUserProfileImg(MasterUserKey key);
	
	/**
	 * 사용자 디바이스(도어락) 비밀번호 및 닉네임 수정
	 * @param key
	 * @return
	 */
	public int updateMbrPwdTxn(MasterUserKey key);
	
	/**
	 * 사용자 프로필 이미지 정보 조회
	 * @param mbrSeq
	 * @return
	 */
	public Map<String, Object> selectUserProfileImg(
            @Param("mbrSeq")            Long mbrSeq
            );
	
	/**
	 * 사용자 디바이스(도어락) 존재 여부 CNT
	 * @param key
	 * @return
	 */
	public int selectMbrPwdTxnCnt(MasterUserKey key);
	
	/**
	 * 도어락 회원비밀번호내역 테이블 INSERT
	 * @param key
	 * @return
	 */
	public int insertMbrPwdTxn(MasterUserKey key);

	/**
	 * @param serviceNo
	 * @return
	 */
	public List<UserInfo> selectUserInfoList(
            @Param("serviceNo")            Long serviceNo
            );

	/**
	 * @param serviceNo
	 * @return
	 */
	public String selectSvcTgtTypeCd(
            @Param("svcTgtSeq")            Long svcTgtSeq
            );
	
}
