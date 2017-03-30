package com.kt.giga.home.openapi.hcam.dao;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Param;

import com.kt.giga.home.openapi.hcam.domain.VoiceSession;

/**
 * 음성 전송 관련 매퍼 인터페이스
 * 
 * @author 
 *
 */
public interface VoiceDao {

	/**
	 * 음성 세션 아이디 생성
	 * 
	 * @return					음성 세션 아이디 (시퀀스)
	 */
	public int createVoiceSessionId();
	
	/**
	 * 음성 세션 조회
	 * 
	 * @param devUUID			디바이스 UUID
	 * @return					음성 세션 객체
	 */
	public VoiceSession getVoiceSession(
			@Param("devUUID")			String devUUID
			);
	
	/**
	 * 만료된 음성 세션 조회
	 * 
	 * @param expireTimeMinute	만료 시간 분
	 * @return					음성 세션 객체 리스트
	 */
	public ArrayList<VoiceSession> getExpiredVoiceSession(
			@Param("expireTimeMinute")	int expireTimeMinute
			);
	
	/**
	 * 음성 세션 삽입
	 * 
	 * @param sessionId			음성 세션 아이디			
	 * @param devUUID			디바이스 UUID
	 * @param mbrSeq			멤버 일련번호
	 * @param termlId			단말 아이디
	 */
	public void insertVoiceSession(
			@Param("sessionId")			String sessionId,
			@Param("devUUID")			String devUUID,
			@Param("mbrSeq")			long mbrSeq,
			@Param("termlId")			String termlId
			);
	
	/**
	 * 음성 세션 연장
	 * 
	 * @param sessionId			음성 세션 아이디			
	 * @param devUUID			디바이스 UUID
	 * @param mbrSeq			멤버 일련번호
	 * @param termlId			단말 아이디
	 */
	public void updateVoiceSession(
			@Param("sessionId")			String sessionId,
			@Param("devUUID")			String devUUID,
			@Param("mbrSeq")			long mbrSeq,
			@Param("termlId")			String termlId			
			);
	
	/**
	 * 음성 세션 삭제
	 * 
	 * @param sessionId			음성 세션 아이디			
	 * @param devUUID			디바이스 UUID
	 * @param mbrSeq			멤버 일련번호
	 * @param termlId			단말 아이디
	 */
	public void deleteVoiceSession(
			@Param("sessionId")			String sessionId,
			@Param("devUUID")			String devUUID,
			@Param("mbrSeq")			long mbrSeq,
			@Param("termlId")			String termlId
			);

}
