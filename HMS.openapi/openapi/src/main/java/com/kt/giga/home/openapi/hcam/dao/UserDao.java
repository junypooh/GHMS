package com.kt.giga.home.openapi.hcam.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.kt.giga.home.openapi.hcam.domain.LoginHistory;
import org.apache.ibatis.annotations.Param;

import com.kt.giga.home.openapi.hcam.domain.User;
import com.kt.giga.home.openapi.vo.spotdev.SpotDev;

/**
 * 사용자 매퍼 인터페이스
 * User 와 Mbr 용어를 같이 사용하고 있음
 * TODO 테이블에 토큰정보 삽입시 컬럼 사이즈에 맞게 200 바이트 잘라서 넣고 있음. 정리 필요.
 * 
 * @author 
 *
 */
public interface UserDao {
	
	/**
	 * 회원 시퀀스 오브젝트 생성, 없는 경우 런타임 생성함
	 */
	public void createMbrBasTableSequence();
	
	/**
	 * 회원 일련번호 생성
	 * 
	 * @return				신규 생성된 회원 일련번호
	 * @throws Exception
	 */
	public Long createMbrSeq() throws Exception;
	
	/**
	 * 서비스 대상 일련번호 조건으로 회원 일련번호 조회 
	 * 
	 * @param svcTgtSeq		서비스 대상 일련번호
	 * @return				회원 일련번호
	 */
	public Long getMbrSeqBySvcTgtSeq(Long svcTgtSeq);
	
	/**
	 * 기존에 존재하는 사용자 정보 조회
	 * 
	 * @param userId		사용자 아이디
	 * @return				사용자 객체
	 */
	public User getUserBase(String userId);

	/**
	 * 사용자 정보 삽입
	 * 
	 * @param userBase		사용자 객체
	 */
	public void insertUserBase(User userBase);

	/**
	 * 서비스 사용자 삽입
	 * 
	 * @param userBase		사용자 객체
	 */
	public void insertServiceUserRelation(User userBase);

	/**
	 * Credential ID 업데이트
	 * 
	 * @param mbrSeq		회원 일련번호
	 * @param credentialId	Credential ID
	 */
	public void updateCredentialId(
			@Param("mbrSeq")			Long mbrSeq,
			@Param("credentialId")		String credentialId
			);	
	
	/**
	 * 앱 접속자 정보 조회
	 * 
	 * @param dstrCd
     * @param svcThemeCd
     * @param unitSvcCd
     * @param mbrSeq
     * @param termlId
     * @return				사용자 객체
	 */
	public User selectAppUser(
			@Param("dstrCd")			String dstrCd,
			@Param("svcThemeCd")		String svcThemeCd,
			@Param("unitSvcCd")			String unitSvcCd,
			@Param("mbrSeq")			Long mbrSeq,
			@Param("termlId")			String termlId
			);	
	
	/**
	 * 앱 접속자 정보 삽입
	 * 
	 * @param appUser		사용자(앱 접속자) 객체
	 */
	public void insertAppUser(User appUser);
	
	/**
	 * 앱 접속자 정보 업데이트
	 * 
	 * @param appUser		사용자(앱 접속자) 객체
	 */
	public void updateAppUser(User appUser);

	/**
	 * 앱 접속자 정보 삭제, termlId 기준 삭제 
	 * 
	 * @param dstrCd			지역 코드
	 * @param svcThemeCd		서비스 테마 코드
	 * @param unitSvcCd			단위 서비스 코드
	 * @param termlId			접속 단말 아이디
	 */
	public void deleteAppUser(
			@Param("dstrCd")			String dstrCd,
			@Param("svcThemeCd")		String svcThemeCd,
			@Param("unitSvcCd")			String unitSvcCd,
			@Param("mbrSeq")			Long mbrSeq,
			@Param("termlId")			String termlId
			);

	/**
	 * 앱 접속자 토큰 정보 삽입, 200 바이트 잘라서 삽입, 사용은 하지 않음
	 * 
	 * @param appUser		사용자(앱 접속자 토큰) 객체
	 */
	public void insertAppUserToken(User appUser);
	
	/**
	 * 앱 접속자 토큰 정보 업데이트, 200 바이트 잘라서 업데이트, 사용은 하지 않음
	 * 
	 * @param appUser		사용자(앱 접속자 토큰) 객체
	 */
	public void updateAppUserToken(User appUser);
	
	/**
	 * 앱 접속자 토큰 정보를 만료 상태로 업데이트. 발급 날짜를 1970년으로 초기화
	 * 
	 * @param appUser		사용자(앱 접속자 토큰) 객체
	 */
	public void updateAppUserTokenExpired(User appUser);
	
	/**
	 * 앱 접속자 토큰 정보 삭제
	 * 
	 * @param dstrCd			지역 코드
	 * @param svcThemeCd		서비스 테마 코드
	 * @param unitSvcCd			단위 서비스 코드
	 * @param termlId			접속 단말 아이디
	 */
	public void deleteAppUserToken(
			@Param("dstrCd")			String dstrCd,
			@Param("svcThemeCd")		String svcThemeCd,
			@Param("unitSvcCd")			String unitSvcCd,
			@Param("mbrSeq")			Long mbrSeq,
			@Param("termlId")			String termlId			
			);
	
	/**
	 * 앱 접속자 리스트 조회. 
	 * 접속 단말 아이디 널인 경우 회원 일련번호를 공유하는 모든 앱 접속자 리스트 조회
	 * 
	 * @param dstrCd			지역 코드
	 * @param svcThemeCd		서비스 테마 코드
	 * @param unitSvcCd			단위 서비스 코드
	 * @param mbrSeq			회원 일련번호
	 * @param termlId			접속 단말 아이디 (옵션)
	 * @param expireTimeMinute	만료 조건을 위한 시간(분)
	 * @return					사용자(앱 접속자) 객체 리스트
	 */
	public ArrayList<User> getAppUserList(
			@Param("dstrCd")			String dstrCd,
			@Param("svcThemeCd")		String svcThemeCd,
			@Param("unitSvcCd")			String unitSvcCd,
			@Param("mbrSeq")			Long mbrSeq,
			@Param("termlId")			String termlId,
			@Param("expireTimeMinute")	Integer expireTimeMinute			
			);
	
	/**
	 * 접속 단말 아이디의 회원 일련번호 조회
	 * 
	 * @param dstrCd			지역 코드
	 * @param svcThemeCd		서비스 테마 코드
	 * @param unitSvcCd			단위 서비스 코드
	 * @param termlId			접속 단말 아이디
	 * @return					회원 일련번호
	 */
	public User[] getSvcTgtConnBasByTermlId(
			@Param("dstrCd")			String dstrCd,
			@Param("svcThemeCd")		String svcThemeCd,
			@Param("unitSvcCd")			String unitSvcCd,
			@Param("termlId")			String termlId			
			);
	
	/**
	 * 접속 단말 아이디의 회원 일련번호 조회
	 * 
	 * @param dstrCd			지역 코드
	 * @param svcThemeCd		서비스 테마 코드
	 * @param unitSvcCd			단위 서비스 코드
	 * @param pnsRegId
     * @return					회원 일련번호
	 */
	public User[] getSvcTgtConnBasByPnsRegId(
			@Param("dstrCd")			String dstrCd,
			@Param("svcThemeCd")		String svcThemeCd,
			@Param("unitSvcCd")			String unitSvcCd,
			@Param("pnsRegId")			String pnsRegId			
			);
	
	/**
	 * Push 수신 대상 조회
	 * 
	 * @param dstrCd			지역 코드
	 * @param svcThemeCd		서비스 테마 코드
	 * @param unitSvcCd			단위 서비스 코드
	 * @param mbrSeq			회원 일련번호
	 * @param expireTimeMinute	만료 조건을 위한 시간(분)
	 * @param setupCd			설정 코드
	 * @return					사용자(앱 접속자) 객체 리스트
	 */
	public ArrayList<User> getPnsTargetList(
			@Param("dstrCd")			String dstrCd,
			@Param("svcThemeCd")		String svcThemeCd,
			@Param("unitSvcCd")			String unitSvcCd,
			@Param("mbrSeq")			Long mbrSeq,
			@Param("expireTimeMinute")	Integer expireTimeMinute,
			@Param("setupCd")			String setupCd
			);
	
	/**
	 * 회원 일련번호가 널인 현장 장치 조회 (청약 후 미개통 상태)
	 * 
	 * @param custIdList		custId 리스트
	 * @return					현장 장치 리스트
	 */
	public ArrayList<SpotDev> getNullMbrSpotDevBas(
			@Param("custIdList")		String[] custIdList
			);
	
	/**
	 * 서비스 대상 기본의 회원 일련번호 매핑 (청약 후 개통 처리)
	 * 
	 * @param sdpUser			사용자(SDP연동) 객체
	 */
	public void updateSvcTgtBasMbrSeq(User sdpUser);
	
	/**
	 * 회원에 매핑된 서비스 대상 기본 수 조회 (회원 매핑된 청약 수)
	 * 
	 * @param mbrSeq			회원 일련번호
	 * @return					회원 매핑된 청약 수
	 */
	public int getMbrSvcTgtBasCount(Long mbrSeq);
	
	/**
	 * 동의 필요한 약관 수 조회
	 * 
	 * @param svcCode			서비스 코드
	 * @param mbrSeq			회원 일련번호
	 * @return					동의 필요한 약관 수
	 */
	public int getAgreeRequiredTermsCount(
			@Param("svcCode")			String svcCode,
			@Param("mbrSeq")			Long mbrSeq		
			);
	
	/**
	 * 법정대리인 동의여부 조회 
	 * 
	 * @param svcCode			서비스 코드
	 * @param mbrSeq			회원 일련번호
	 * @return					법정대리인 동의 수
	 */
	public int getParentsAgreeCount(
			@Param("svcCode")			String svcCode,
			@Param("mbrSeq")			Long mbrSeq			
			);
	
	/**
	 * 사용자 설정 조회
	 * 
	 * @param dstrCd			지역 코드
	 * @param svcThemeCd		서비스 테마 코드
	 * @param unitSvcCd			단위 서비스 코드
	 * @param mbrSeq			회원 일련번호
	 * @param connTermlId		접속 단말 아이디
	 * @param groupSetupCd		그룹 설정 코드
	 * @return					사용자 설정 리스트
	 */
	public ArrayList<HashMap<String, String>> getUserSetup(
			@Param("dstrCd")			String dstrCd,
			@Param("svcThemeCd")		String svcThemeCd,
			@Param("unitSvcCd")			String unitSvcCd,
			@Param("mbrSeq")			Long mbrSeq,
			@Param("connTermlId")		String connTermlId,
			@Param("groupSetupCd")		String groupSetupCd
			);
	
	/**
	 * 사용자 설정 삭제
	 * 
	 * @param dstrCd			지역 코드
	 * @param svcThemeCd		서비스 테마 코드
	 * @param unitSvcCd			단위 서비스 코드
	 * @param mbrSeq			회원 일련번호
	 * @param connTermlId		접속 단말 아이디
	 * @param groupSetupCd		그룹 설정 코드
	 */
	public void deleteUserSetup(
			@Param("dstrCd")			String dstrCd,
			@Param("svcThemeCd")		String svcThemeCd,
			@Param("unitSvcCd")			String unitSvcCd,
			@Param("mbrSeq")			Long mbrSeq,
			@Param("connTermlId")		String connTermlId,
			@Param("groupSetupCd")		String groupSetupCd			
			);
	
	/**
	 * 사용자 설정 삽입
	 * 
	 * @param dstrCd			지역 코드
	 * @param svcThemeCd		서비스 테마 코드
	 * @param unitSvcCd			단위 서비스 코드
	 * @param mbrSeq			회원 일련번호
	 * @param connTermlId		접속 단말 아이디
	 * @param groupSetupCd		그룹 설정 코드
	 * @param setupCd			설정 코드
	 * @param setupVal			설정 값
	 */
	public void insertUserSetup(
			@Param("dstrCd")			String dstrCd,
			@Param("svcThemeCd")		String svcThemeCd,
			@Param("unitSvcCd")			String unitSvcCd,
			@Param("mbrSeq")			Long mbrSeq,
			@Param("connTermlId")		String connTermlId,
			@Param("groupSetupCd")		String groupSetupCd,
			@Param("setupCd")			String setupCd,
			@Param("setupVal")			String setupVal
			);
	
	/**
	 * 사용자 설정 업데이트 
	 * 
	 * @param dstrCd			지역 코드
	 * @param svcThemeCd		서비스 테마 코드
	 * @param unitSvcCd			단위 서비스 코드
	 * @param mbrSeq			회원 일련번호
	 * @param connTermlId		접속 단말 아이디
	 * @param groupSetupCd		그룹 설정 코드
	 * @param setupCd			설정 코드
	 * @param setupVal			설정 값
	 */
	public int updateUserSetup(
			@Param("dstrCd")			String dstrCd,
			@Param("svcThemeCd")		String svcThemeCd,
			@Param("unitSvcCd")			String unitSvcCd,
			@Param("mbrSeq")			Long mbrSeq,
			@Param("connTermlId")		String connTermlId,
			@Param("groupSetupCd")		String groupSetupCd,
			@Param("setupCd")			String setupCd,
			@Param("setupVal")			String setupVal			
			);

    void insertLoginHistory(User appUser);

    public List<LoginHistory> getLoginHistory(

            @Param("mbrSeq")            Long mbrSeq,
            @Param("count")             Integer count

    );

    void initializeMappedDevices(
            @Param("mbrSeq")            Long mbrSeq
    );
}
