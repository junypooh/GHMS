package com.kt.giga.home.openapi.hcam.dao;

import java.util.HashMap;

import org.apache.ibatis.annotations.Param;

/**
 * 릴레이 서버 연동 실시간 모니터링 관련 매퍼 인터페이스
 * 
 * @author 
 *
 */
public interface RelayDao {
	
	/**
	 * 릴레이 디바이스 아이디 생성
	 * 
	 * @return				음성 세션 아이디 (시퀀스)
	 */
	public int createRelayDeviceId();
	
	/**
	 * 현장장치 키 맵 조회
	 * 
	 * @param devUUID		디바이스 UUID
	 * @return				현장장치 키 맵(svcTgtSeq, spotDevSeq)
	 */
	public HashMap<String, Long> getSpotDevKey(
			@Param("devUUID") String devUUID
			);
	
	/**
	 * 릴레이 디바이스 아이디/패스워드 맵 조회
	 * 
	 * @param devUUID		디바이스 UUID
	 * @return				릴레이 디바이스 아이디 맵 (relayDeviceId, relayDevicePw)
	 */
	public HashMap<String, Integer> getRelayDeviceIdPw(
			@Param("devUUID") String devUUID
			);	
	
	/**
	 *  릴레이 디바이스 아이디/패스워드 삽입
	 * 
	 * @param devUUID		디바이스 UUID
	 * @param relayDeviceId	릴레이 디바이스 아이디
	 * @param relayDevicePw	릴레이 디바이스 패스워드
	 */
	public void insertRelayDeviceIdPw(
			@Param("devUUID") String devUUID,
			@Param("relayDeviceId") int relayDeviceId,
			@Param("relayDevicePw") int relayDevicePw
			);
	
	/**
	 * 릴레이 디바이스 아이디/패스워드 변경. TODO 사용하지 않으면 삭제 필요함
	 * 
	 * @param devUUID		디바이스 UUID
	 * @param relayDevicePw	릴레이 디바이스 패스워드
	 */
	public void updateRelayDevicePw(
			@Param("devUUID") String devUUID,
			@Param("relayDevicePw") int relayDevicePw			
			);
}
