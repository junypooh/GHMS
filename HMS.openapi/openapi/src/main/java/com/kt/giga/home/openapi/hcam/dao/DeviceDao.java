package com.kt.giga.home.openapi.hcam.dao;

import com.kt.giga.home.openapi.hcam.domain.ControlTransaction;
import com.kt.giga.home.openapi.hcam.domain.EnrollmentStatus;
import com.kt.giga.home.openapi.hcam.domain.ScheduleData;
import com.kt.giga.home.openapi.vo.row.GenlSetupData;
import com.kt.giga.home.openapi.vo.spotdev.SpotDev;
import com.kt.giga.home.openapi.vo.spotdev.SpotDevDtl;
import org.apache.ibatis.annotations.Param;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 디바이스 매퍼 인터페이스
 *
 * @author
 *
 */
public interface DeviceDao {

	/**
	 * 현장 장치 키 조회
	 *
	 * @param devUUID			디바이스 UUID
	 * @return					현장 장치 내부 키 맵
	 * 								svcTgtSeq(Long)		: 서비스 대상 일련번호
	 * 								spotDevSeq(Long)	: 현장 장치 일련번호
	 * 								spotDevId(String)	: 현장 장치 아이디
	 */
	public HashMap<String, Object> getSpotDevKey(
			@Param("devUUID")		String devUUID
			);

	/**
	 * 현장 장치 UUID 조회
	 *
	 * @param svcTgtSeq			서비스 대상 일련번호
	 * @param spotDevSeq		현장 장치 일련번호
	 * @return					디바이스 UUID
	 */
	public String getSpotDevUUID(
			@Param("svcTgtSeq")		long svcTgtSeq,
			@Param("spotDevSeq")	long spotDevSeq
			);

	/**
	 * 현장 장치 조회
	 *
	 * @param svcCode			서비스 코드
	 * @param devUUID			디바이스 UUID
	 * @param svcTgtSeq			서비스 대상 일련번호
	 * @param spotDevSeq		현장 장치 일련번호
	 * @return					현장 장치 SpotDev 리스트
	 */
	public ArrayList<SpotDev> getSpotDev(
			@Param("svcCode")		String svcCode,
			@Param("devUUID")		String devUUID,
			@Param("svcTgtSeq")		Long svcTgtSeq,
			@Param("spotDevSeq")	Long spotDevSeq
			);

	/**
	 * 회원에 연결된 현장 장치 조회
	 *
	 * @param svcCode			서비스 코드
	 * @param mbrSeq			회원 일련번호
	 * @param devUUID			디바이스 UUID
	 * @param svcTgtSeq			서비스 대상 일련번호
	 * @param spotDevSeq		현장 장치 일련번호
	 * @return					현장 장치 SpotDev 리스트
	 */
	public ArrayList<SpotDev> getMemberSpotDev(
			@Param("svcCode")		String svcCode,
			@Param("mbrSeq")		Long mbrSeq,
			@Param("devUUID")		String devUUID,
			@Param("svcTgtSeq")		Long svcTgtSeq,
			@Param("spotDevSeq")	Long spotDevSeq
			);

	/**
	 * 회원에 연결된 서비스 대상 키 조회
	 *
	 * @param svcCode			서비스 코드
	 * @param mbrSeq			회원 일련번호
	 * @return					서비스 대상 키 svc_tgt_seq
	 */
	public ArrayList<Long> getMemberSvcTgtSeq(
			@Param("svcCode")		String svcCode,
			@Param("mbrSeq")		long mbrSeq
			);

	/**
	 * 현장 장치 펌웨어 버전 업데이트
	 *
	 * @param svcTgtSeq			서비스 대상 일련번호
	 * @param spotDevSeq		현장 장치 일련번호
	 * @param frmwrVerNo		펌웨어 버전
	 */
	public void updateSpotDevFirmwareVersion(
			@Param("svcTgtSeq")			long svcTgtSeq,
			@Param("spotDevSeq")		long spotDevSeq,
			@Param("frmwrVerNo")		String frmwrVerNo
			);

	/**
	 * 펌웨업 업그레이드 진행중 리스트 조회
	 *
	 * @return				현장 장치 SpotDev 리스트
	 */
	public ArrayList<SpotDev> getFirmwareUpgradeDeviceList();
	
	/**
	 * 펌웨업 업그레이드 완료 리스트 조회
	 *
	 * @return				현장 장치 SpotDev 리스트
	 */
	public ArrayList<SpotDev> getFirmwareUpgradeCompleteDeviceList();

	/**
	 * 현장 장치 상세 정보 삽입
	 *
	 * @param svcTgtSeq			서비스 대상 일련번호
	 * @param spotDevSeq		현장 장치 일련번호
	 * @param spotDevItemNm		항목 이름
	 * @param spotDevItemVal	항목 값
	 */
	public void insertSpotDevDtl(
			@Param("svcTgtSeq")			long svcTgtSeq,
			@Param("spotDevSeq")		long spotDevSeq,
			@Param("spotDevItemNm")		String spotDevItemNm,
			@Param("spotDevItemVal")	String spotDevItemVal
			);

	/**
	 * 현장 장치 상세 정보 조회
	 *
	 * @param svcTgtSeq			서비스 대상 일련번호
	 * @param spotDevSeq		현장 장치 일련번호
	 * @param spotDevItemNm		항목 이름
	 * @return					현장 장치 상세 정보 SpotDevDtl 리스트
	 */
	public ArrayList<SpotDevDtl> getSpotDevDtl(
			@Param("svcTgtSeq")		long svcTgtSeq,
			@Param("spotDevSeq")	long spotDevSeq,
			@Param("spotDevItemNm")	String spotDevItemNm
			);
	
	/**
	 * 사용자별 현장 장치 상세 정보 조회
	 *
	 * @param mbrSeq
     * @param spotDevItemNm		항목 이름
	 * @return					현장 장치 상세 정보 SpotDevDtl 리스트
	 */
	public ArrayList<Map<String, String>> getSpotDevDtlByMbrId(
			@Param("mbrSeq")	long mbrSeq,
			@Param("spotDevItemNm")	String spotDevItemNm
			);

	/**
	 * 현장 장치 상세 정보 복수 조회
	 *
	 * @param svcTgtSeq			서비스 대상 일련번호
	 * @param spotDevSeq		현장 장치 일련번호
	 * @param spotDevItemNms	항목 이름 목록
	 * @return					현장 장치 상세 정보 SpotDevDtl 리스트
	 */
	public List<SpotDevDtl> getSpotDevDtls(
			@Param("svcTgtSeq")			long svcTgtSeq,
			@Param("spotDevSeq")		long spotDevSeq,
			@Param("spotDevItemNms")	List<String> spotDevItemNms
			);

	/**
	 * 현장 장치 상태 업데이트
	 *
	 * @param svcTgtSeq			서비스 대상 일련번호
	 * @param spotDevSeq		현장 장치 일련번호
	 * @param devSttusCd		현장 장치 상태
	 */
	public void updteSpotDevStatus(
			@Param("svcTgtSeq")			Long svcTgtSeq,
			@Param("spotDevSeq")		Long spotDevSeq,
			@Param("devSttusCd")		String devSttusCd
			);

	/**
	 * 현장 장치 상세 정보 업데이트
	 *
	 * @param svcTgtSeq			서비스 대상 일련번호
	 * @param spotDevSeq		현장 장치 일련번호
	 * @param spotDevItemNm		항목 이름
	 * @param spotDevItemVal	항목 값
	 */
	public int updateSpotDevDtl(
			@Param("svcTgtSeq")			long svcTgtSeq,
			@Param("spotDevSeq")		long spotDevSeq,
			@Param("spotDevItemNm")		String spotDevItemNm,
			@Param("spotDevItemVal")	String spotDevItemVal
			);

	/**
	 * 현장 장치 상세 정보 업데이트 - value + 1로 업데이트
	 *
	 * @param svcTgtSeq			서비스 대상 일련번호
	 * @param spotDevSeq		현장 장치 일련번호
	 * @param spotDevItemNm		항목 이름
     * @return
     */
    public int updateSpotDevDtlInc(
			@Param("svcTgtSeq")			long svcTgtSeq,
			@Param("spotDevSeq")		long spotDevSeq,
			@Param("spotDevItemNm")		String spotDevItemNm
			);

	/**
	 * 설정 기본 정보 삽입
	 *
	 * @param svcTgtSeq			서비스 대상 일련번호
	 * @param spotDevSeq		현장 장치 일련번호
	 * @param snsnTagCd			센싱 태그 코드
	 */
	public void insertSpotDevSetupBase(
			@Param("svcTgtSeq")		long svcTgtSeq,
			@Param("spotDevSeq")	long spotDevSeq,
			@Param("snsnTagCd")		String snsnTagCd
			);

	/**
	 * 설정 기본 정보 삭제
	 *
	 * @param svcTgtSeq			서비스 대상 일련번호
	 * @param spotDevSeq		현장 장치 일련번호
	 */
	public void deleteSpotDevSetupBaseByDevice(
			@Param("svcTgtSeq")		long svcTgtSeq,
			@Param("spotDevSeq")	long spotDevSeq
			);

	/**
	 * 일반 설정 조회
	 *
	 * @param svcTgtSeq			서비스 대상 일련번호
	 * @param spotDevSeq		현장 장치 일련번호
	 * @return					일반 설정 정보 GenlSetupData 리스트
	 */
	public ArrayList<GenlSetupData> getSpotDevGenSetup(
			@Param("svcTgtSeq")		long svcTgtSeq,
			@Param("spotDevSeq")	long spotDevSeq
			);

	/**
	 * 일반 설정 값 조회
	 *
	 * @param svcTgtSeq			서비스 대상 일련번호
	 * @param spotDevSeq		현장 장치 일련번호
	 * @param snsnTagCd			센싱 태그 코드
	 * @return					일반 설정 값
	 */
	public String getSpotDevGenSetupVal(
			@Param("svcTgtSeq")		long svcTgtSeq,
			@Param("spotDevSeq")	long spotDevSeq,
			@Param("snsnTagCd")		String snsnTagCd
			);

	/**
	 * 일반 설정 값 Upsert
	 *
	 * @param svcTgtSeq			서비스 대상 일련번호
	 * @param spotDevSeq		현장 장치 일련번호
	 * @param genlSetupDatas			센싱 태그 코드
	 */
	public void updateSpotDevGenSetups(
			@Param("svcTgtSeq")			long svcTgtSeq,
			@Param("spotDevSeq")		long spotDevSeq,
			@Param("genlSetupDatas")	List<GenlSetupData> genlSetupDatas
			);

	/**
	 * 일반 설정 값 변경
	 *
	 * @param svcTgtSeq			서비스 대상 일련번호
	 * @param spotDevSeq		현장 장치 일련번호
	 * @param snsnTagCd			센싱 태그 코드
	 * @param setupVal			일반 설정 값
	 */
	public void updateSpotDevGenSetup(
			@Param("svcTgtSeq")		long svcTgtSeq,
			@Param("spotDevSeq")	long spotDevSeq,
			@Param("snsnTagCd")		String snsnTagCd,
			@Param("setupVal")		String setupVal
			);

	/**
	 * 일반 설정 삽입
	 *
	 * @param svcTgtSeq			서비스 대상 일련번호
	 * @param spotDevSeq		현장 장치 일련번호
	 * @param snsnTagCd			센싱 태그 코드
	 * @param setupVal			일반 설정 값
	 */
	public void insertSpotDevGenSetup(
			@Param("svcTgtSeq")		long svcTgtSeq,
			@Param("spotDevSeq")	long spotDevSeq,
			@Param("snsnTagCd")		String snsnTagCd,
			@Param("setupVal")		String setupVal
			);

	/**
	 * 일반 설정 삭제
	 *
	 * @param svcTgtSeq			서비스 대상 일련번호
	 * @param spotDevSeq		현장 장치 일련번호
	 */
	public void deleteSpotDevGenSetupByDevice(
			@Param("svcTgtSeq")		long svcTgtSeq,
			@Param("spotDevSeq")	long spotDevSeq
			);

	/**
	 * 스케줄 설정 조회
	 *
	 * @param svcTgtSeq			서비스 대상 일련번호
	 * @param spotDevSeq		현장 장치 일련번호
	 * @return					스케줄 설정 정보 맵 리스트
	 * 								snsnTagCd(String)	: 센싱 태그 코드
	 * 								dowCd(String)		: 요일 코드
	 * 								dowSeq(int)			: 요일내 스케줄 일련번호
	 * 								stTime(String)		: 스케줄 시작시간
	 * 								fnsTime(String)		: 스케줄 종료시간
	 */
	public ArrayList<HashMap<String, Object>> getSpotDevSchSetup(
			@Param("svcTgtSeq")		long svcTgtSeq,
			@Param("spotDevSeq")	long spotDevSeq
			);

	/**
	 * 스케줄 설정 삭제
	 *
	 * @param svcTgtSeq			서비스 대상 일련번호
	 * @param spotDevSeq		현장 장치 일련번호
	 * @param snsnTagCd			센싱 태그 코드
	 */
	public void deleteSpotDevSchSetup(
			@Param("svcTgtSeq")		long svcTgtSeq,
			@Param("spotDevSeq")	long spotDevSeq,
			@Param("snsnTagCd")		String snsnTagCd
			);

	/**
	 * 스케줄 설정 삽입
	 *
	 * @param svcTgtSeq			서비스 대상 일련번호
	 * @param spotDevSeq		현장 장치 일련번호
	 * @param snsnTagCd			센싱 태그 코드
	 * @param dowCd				요일 코드
	 * @param stTime			스케줄 시작시간
	 * @param fnsTime			스케줄 종료시간
	 */
	public void insertSpotDevSchSetup(
			@Param("svcTgtSeq")		Long svcTgtSeq,
			@Param("spotDevSeq")	Long spotDevSeq,
			@Param("snsnTagCd")		String snsnTagCd,
			@Param("dowCd")			String dowCd,
			@Param("stTime")		String stTime,
			@Param("fnsTime")		String fnsTime,
			@Param("perdTime")		Integer perdTime,
			@Param("duratTime")		Integer duratTime
			);
	
	/**
	 * 스케줄 설정 변경
	 *
	 * @param svcTgtSeq			서비스 대상 일련번호
	 * @param spotDevSeq		현장 장치 일련번호
	 * @param snsnTagCd			센싱 태그 코드
	 * @param dowCd				요일 코드
	 * @param stTime			스케줄 시작시간
	 * @param fnsTime			스케줄 종료시간
	 */
	public int updateSpotDevSchSetup(
			@Param("svcTgtSeq")		Long svcTgtSeq,
			@Param("spotDevSeq")	Long spotDevSeq,
			@Param("snsnTagCd")		String snsnTagCd,
			@Param("dowCd")			String dowCd,
			@Param("stTime")		String stTime,
			@Param("fnsTime")		String fnsTime,
			@Param("perdTime")		Integer perdTime,
			@Param("duratTime")		Integer duratTime
			);

    /**
     * 스케줄 설정 Upsert
     *
     * @param scheduleDatas
     * @return
     */

    public int upsertSpotDevSchSetup(List<ScheduleData> scheduleDatas);

    /**
	 * 스케줄 설정  삭제
	 *
	 * @param svcTgtSeq			서비스 대상 일련번호
	 * @param spotDevSeq		현장 장치 일련번호
	 */
	public void deleteSpotDevSchSetupByDevice(
			@Param("svcTgtSeq")		Long svcTgtSeq,
			@Param("spotDevSeq")	Long spotDevSeq
			);

	/**
	 * 트랜잭션 아이디 생성
	 *
	 * @return					신규 생성한 트랜잭션 아이디
	 */
	public String createTransactionId();

	/**
	 * 트랜잭션 정보 조회
	 *
	 * @param transactionId		트랜잭션 아이디
	 * @return					트랜잭션 정보 객체
	 */
	public ControlTransaction getTransaction(String transactionId);

	/**
	 * 트랜잭션 상태 조회
	 *
	 * @param transactionId		트랜잭션 아이디
	 * @return					트랜잭션 상태
	 */
	public String getTransactionStatus(String transactionId);

	/**
	 * 트랜잭션 삽입
	 *
	 * @param transaction		신규 트랜잭션
	 */
	public void insertTransaction(ControlTransaction transaction);

	/**
	 * 트랜잭션 결과 삽입
	 *
	 * @param transactionId		트랜잭션 아이디
	 * @param type				트랜잭션 유형 r:retrieve(조회), c:control(실시간제어), s:setup(설정제어)
	 * @param result			트랜재션 결과
	 */
	public void insertTransactionResult(
			@Param("transactionId")		String transactionId,
			@Param("type")				String type,
			@Param("result")			String result
			);

	/**
	 * 트랜잭션 결과 조회
	 *
	 * @param transactionId		트랜잭션 아이디
	 * @param type				트랜잭션 유형
	 * @return					트랜잭션 결과 맵
	 * 								result(String)		: 트랜잭션 결과
	 * 								type(String)		: 트랜잭션 유형
	 */
	public HashMap<String, String> getTransactionResult(
			@Param("transactionId")		String transactionId,
			@Param("type")				String type
			);


	/**
	 * 트랜잭션 완료 처리
	 *
	 * @param transactionId		트랜잭션 아이디
	 * @param statusCode		트랜잭션 최종 상태 코드
	 */
	public void finalizeTransaction(
			@Param("transactionId")		String transactionId,
			@Param("statusCode")		String statusCode
			);

	/**
	 * 디바이스 이름 업데이트
	 *
	 * @param devUUID			디바이스 UUID
	 * @param devNm				디바이스 이름
	 */
	public void updateDeviceName(
			@Param("devUUID")			String devUUID,
			@Param("devNm")				String devNm
			);

	/**
	 * expireTimeMinute 이내의 신규 접속 종료 장치 리스트 조회
	 *
	 * @param expireTimeMinute	접속 종료 인지 expireTime
	 * @return					신규 접속 종료 장치의 키 맵 리스트
	 * 								svcTgtSeq(long)		: 서비스 대상 일련번호
	 * 								spotDevSeq(long)	: 현장 장치 일련번호
	 */
	public ArrayList<HashMap<String, Long>> getNotCheckClosedSpotDevConnBas(
			@Param("expireTimeMinute")	Integer expireTimeMinute
			);

	/**
	 * 장치 접속 상태 레코드 존재 체크
	 *
	 * @param svcTgtSeq			서비스 대상 일련번호
	 * @param spotDevSeq		현장 장치 일련번호
	 * @return					svcTgtSeq, spotDevSeq 조건에 맞는 레코드 수
	 */
	public int getSpotDevConnBasCount(
			@Param("svcTgtSeq")		long svcTgtSeq,
			@Param("spotDevSeq")	long spotDevSeq
			);

	/**
	 * 장치 접속 상태 삽입
	 *
	 * @param svcTgtSeq			서비스 대상 일련번호
	 * @param spotDevSeq		현장 장치 일련번호
	 * @param connYn			접속 여부 Y/N
	 */
	public void insertSpotDevConnBas(
			@Param("svcTgtSeq")		long svcTgtSeq,
			@Param("spotDevSeq")	long spotDevSeq,
			@Param("connYn")		String connYn
			);

	/**
	 * 장치 접속 상태 업데이트
	 *
	 * @param svcTgtSeq			서비스 대상 일련번호
	 * @param spotDevSeq		현장 장치 일련번호
	 * @param connYn			접속 여부 Y/N
	 * @param checkYn			체크 여부 Y/N
	 */
	public int updateSpotDevConnBas(
			@Param("svcTgtSeq")		long svcTgtSeq,
			@Param("spotDevSeq")	long spotDevSeq,
			@Param("connYn")		String connYn,
			@Param("checkYn")		String checkYn
			);

    /**
     * 장치 등록 상태 체크
     *
     * @param mbrSeq 회원 일련번호
     * @param mac    장치 MAC
     * @return {@link com.kt.giga.home.openapi.hcam.domain.EnrollmentStatus}
     */
    public EnrollmentStatus getEnrollmentStatus(
            @Param("mbrSeq") long mbrSeq,
            @Param("mac") String mac
    );
}
