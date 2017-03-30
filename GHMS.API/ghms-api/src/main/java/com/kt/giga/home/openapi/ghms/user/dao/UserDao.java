/*******************************************************************************
 * Copyright(c) 2015 KT. All rights reserved.
 * This software is the proprietary information of KT.
 *******************************************************************************/
package com.kt.giga.home.openapi.ghms.user.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.kt.giga.home.openapi.ghms.kafka.smcp.gw.ca.domn.spotdev.SpotDev;
import com.kt.giga.home.openapi.ghms.user.domain.vo.SnsnTagBasVo;
import com.kt.giga.home.openapi.ghms.user.domain.vo.UserVo;
import com.kt.giga.home.openapi.ghms.util.exception.APIException;


/**
 * 사용자 매퍼 인터페이스
 * User 와 Mbr 용어를 같이 사용하고 있음
 * TODO 테이블에 토큰정보 삽입시 컬럼 사이즈에 맞게 200 바이트 잘라서 넣고 있음. 정리 필요.
 * 
 * @author dahye.kim (dahye.kim@ceinside.co.kr)
 * @since 2015. 2. 2.
 */
public interface UserDao {
    
    /**
     * 회원 시퀀스 오브젝트 생성, 없는 경우 런타임 생성함
     */
    public void createMbrBasTableSequence();
    
    /**
     * 회원 일련번호 생성
     * 
     * @return              신규 생성된 회원 일련번호
     * @throws APIException
     */
    public Long createMbrSeq();
    
    /**
     * 기존에 존재하는 사용자 정보 조회
     * 
     * @param userId        사용자 아이디
     * @return              사용자 객체
     */
    public UserVo getUserBase(String userId);
    
    /**
     * 서비스회원관계, 서비스대상접속기본 존재유무 COUNT
     * 
     * @param mbrSeq        회원일련번호
     * @param dstrCd        지역 코드
     * @param svcThemeCd    서비스 테마 코드
     * @param unitSvcCd     단위 서비스 코드
     * @param termlId       접속 단말 아이디
     * @return  서비스회원관계 CNT, 서비스대상접속기본 CNT, 서비스접속대상기본단말변경 확인 CNT
     */
    public HashMap<String, Object> getUserSvcExistCnt(
            @Param("mbrSeq")            Long mbrSeq,
            @Param("dstrCd")            String dstrCd,
            @Param("svcThemeCd")        String svcThemeCd,
            @Param("unitSvcCd")         String unitSvcCd,
            @Param("termlId")           String termlId);

    /**
     * 서비스 사용자 삽입
     * 
     * @param userBase      사용자 객체
     */
    public void insertServiceUserRelation(UserVo userBase);
    
    /**
     * 서비스 대상 접속 기본 등록
     * 
     * @param userBase  사용자(앱 접속자) 객체
     */
    public void insertSvcTgtConnBas(UserVo userBase);
    
    /**
     * 회원의 디바이스 정보가 변경 되었다면 서비스대상접속기본 데이터에 새로운 디바이스 ID로 변경
     * 
     * @param userBase  사용자(앱 접속자) 객체  
     */
    public void updateOldDeviceSvcTgtConnBas(UserVo userBase);
    public void updateNewDeviceSvcTgtConnBas(UserVo userBase);

    /**
     * 사용자 정보 삽입
     * 
     * @param userBase      사용자 객체
     */
    public void insertUserBase(UserVo userBase);

    /**
     * Credential ID 업데이트
     * 
     * @param mbrSeq        회원 일련번호
     * @param credentialId  Credential ID
     */
    public void updateCredentialId(
            @Param("mbrSeq")            Long mbrSeq,
            @Param("credentialId")      String credentialId
            );
    
    /**
     * 법정대리인 동의여부 조회 
     * 
     * @param unitSvcCd         단위 서비스 코드
     * @param mbrSeq            회원 일련번호
     * @return                  법정대리인 동의 수
     */
    public int getParentsAgreeCount(
            @Param("unitSvcCd")           String unitSvcCd,
            @Param("mbrSeq")            Long mbrSeq         
            );
    
    /**
     * 회원에 매핑된 서비스 대상 기본 수 조회 (회원 매핑된 청약 수)
     * 
     * @param mbrSeq            회원일련번호
     * @param dstrCd            지역 코드
     * @param svcThemeCd        서비스 테마 코드
     * @param unitSvcCd         단위 서비스 코드
     * @return
     */
    public int getMbrSvcTgtBasCount(
            @Param("mbrSeq")        Long mbrSeq,
            @Param("dstrCd")        String dstrCd,
            @Param("svcThemeCd")    String svcThemeCd,
            @Param("unitSvcCd")     String unitSvcCd,
            @Param("svcTgtTypeCd")	String svcTgtTypeCd
            );
	
	/**
	 * 회원 일련번호가 널인 현장 장치 조회 (청약 후 미개통 상태)
	 * 
	 * @param custIdList		custId 리스트
	 * @return					현장 장치 리스트
	 */
	public List<SpotDev> getNullMbrSpotDevBas(
			@Param("custIdList")		String[] custIdList
			);
	
	/**
	 * 서비스 대상 기본의 회원 일련번호 매핑 (청약 후 개통 처리)
	 * 
	 * @param sdpUser			사용자(SDP연동) 객체
	 */
	public void updateSvcTgtBasMbrSeq(UserVo sdpUser);
    
    /**
     * 동의 필요한 약관 수 조회
     * 
     * @param unitSvcCd         단위 서비스 코드
     * @param mbrSeq            회원 일련번호
     * @return                  동의 필요한 약관 수
     */
    public int getAgreeRequiredTermsCount(
            @Param("unitSvcCd")         String unitSvcCd,
            @Param("mbrSeq")            Long mbrSeq     
            );
    
    /**
     * 서비스대상기본테이블에 운영상태코드를 서비스회원관계 테이블 서비스상태코드에 업데이트
     * 
     * @param userVo
     */
    public void updateMbrRelSttusCdFromTgtBas(UserVo userBase);
    
    /**
     * 샌싱코드 리스트 조회
     * @return
     */
    public List<SnsnTagBasVo> selectSnsnTagBasList(
            @Param("unitSvcCd")         String unitSvcCd
            );
    
    /**
     * 마스터 PNS등록아이디 조회
     * @param svcTgtSeq     서비스대상일련번호
     * @param spotDevSeq    현장장치일련번호
     * @param dstrCd        지역코드
     * @param svcThemeCd    서비스테마코드
     * @param unitSvcCd     단위서비스코드
     * @return
     */
    public String selectMasterPnsRegId(
            @Param("svcTgtSeq")         long   svcTgtSeq, 
            @Param("dstrCd")            String dstrCd, 
            @Param("svcThemeCd")        String svcThemeCd, 
            @Param("unitSvcCd")         String unitSvcCd
            );
    
    /**
     * 슬레이브 PNS등록아이디 조회
     * @param svcTgtSeq     서비스대상일련번호
     * @param spotDevSeq    현장장치일련번호
     * @param dstrCd        지역코드
     * @param svcThemeCd    서비스테마코드
     * @param unitSvcCd     단위서비스코드
     * @return
     */
    public List<String> selectSlavePnsRegId(
            @Param("svcTgtSeq")         long   svcTgtSeq, 
            @Param("dstrCd")            String dstrCd, 
            @Param("svcThemeCd")        String svcThemeCd, 
            @Param("unitSvcCd")         String unitSvcCd
            );
    
    /**
     * 마스터, 슬레이브 PNS등록아이디 조회
     * @param svcTgtSeq     서비스대상일련번호
     * @param spotDevSeq    현장장치일련번호
     * @param dstrCd        지역코드
     * @param svcThemeCd    서비스테마코드
     * @param unitSvcCd     단위서비스코드
     * @return
     */
    public List<String> selectMasterSlavePnsRegId(
            @Param("svcTgtSeq")         long   svcTgtSeq, 
            @Param("dstrCd")            String dstrCd, 
            @Param("svcThemeCd")        String svcThemeCd, 
            @Param("unitSvcCd")         String unitSvcCd,
            @Param("mbrSeq")         	Long mbrSeq
            );
    
    /**
     * 매니저프로파일 내역에 기본 등록
     * @param mbrSeq    회원일련번호
     * @param userId    사용자ID
     */
    public void insertManagerProfTxn(
            @Param("mbrSeq")            long   mbrSeq,
            @Param("userId")            String userId
            );
    
    /**
     * 매니저프로파일 내역 존재유무 확인
     * @param mbrSeq    회원일련번호
     * @return
     */
    public Map<String, Object> selectChkFromManagerProfTxn(long mbrSeq);
    
    /**
     * 매니저프로파일에 내역이 존재하지만 FileSeq가 null일때 UPDATE
     * @param mbrSeq    회원일련번호
     */
    public void updateFileSeqFromManagerProfTxn(long mbrSeq);
    
    
    /**
     * 모든 허브 서비스 중지 여부
     * @param mbrSeq
     * @param dstrCd
     * @param svcThemeCd
     * @param unitSvcCd
     * @return
     */
    public Map<String, Long> selectCheckStopService(
		    @Param("mbrSeq")        	long mbrSeq,
		    @Param("dstrCd")        	String dstrCd,
		    @Param("svcThemeCd")        String svcThemeCd,
		    @Param("unitSvcCd")			String unitSvcCd
		    );

	/**
	 * 서비스 대상 기본 정보 수정
	 * @param map
	 */
	public void updateSvcTgtBas(Map<String, Object> map);
    
    /**
     * 임의고객청약내역 데이터 존재 여부
     * @param svcTgtSeq
     * @param serviceNo
     * @return
     */
    public Long selectRanCustSubsTxn(
			@Param("serviceNo") String serviceNo);
}
