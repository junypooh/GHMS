package com.kt.giga.home.openapi.hcam.service;

import java.util.ArrayList;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.kt.giga.home.openapi.common.APIEnv;
import com.kt.giga.home.openapi.common.AuthToken;
import com.kt.giga.home.openapi.hcam.HCamConstants;
import com.kt.giga.home.openapi.hcam.HCamConstants.SnsnTagCd;
import com.kt.giga.home.openapi.hcam.dao.VoiceDao;
import com.kt.giga.home.openapi.hcam.domain.VoiceSession;
import com.kt.giga.home.openapi.service.APIException;
import com.kt.giga.home.openapi.service.ECService;
import com.kt.giga.home.openapi.vo.spotdev.query.SpotDevBasVO;

/**
 * 음성 전송 서비스
 *
 * @author
 *
 */
@Service("HCam.VoiceService")
public class VoiceService {

	/**
	 * 로거
	 */
	private Logger log = LoggerFactory.getLogger(getClass());

	/**
	 * OpenAPI 환경 프라퍼티
	 */
	@Autowired
	private APIEnv env;

	/**
	 * 음성 전송 매퍼 인터페이스
	 */
	@Autowired
	private VoiceDao voiceDao;

	/**
	 * 사용자 서비스
	 */
	@Autowired
	private UserService userService;

	/**
	 * 디바이스 서비스
	 */
	@Autowired
	private DeviceService deviceService;

	/**
	 * EC 연동 서비스
	 */
	@Autowired
	private ECService ecService;

	/**
	 * 음성 세션 만료 시간. (분)
	 */
	private static final int EXPIRE_TIME_MIN = 1;		// 1분으로 해달라는 요구사항이 있음

	/**
	 * 음성 세션 조회 메쏘드
	 *
	 * @param authToken		로그인 인증 토큰
	 * @param devUUID		홈 카메라 식별자
	 * @return				음성 세션 정보 객체
	 * @throws Exception
	 */
	public VoiceSession getVoiceSession(String authToken, String devUUID) throws Exception {

		AuthToken loginAuthToken	= userService.checkLoginAuthToken(authToken);
		String svcCode				= loginAuthToken.getSvcCode();
		long mbrSeq					= loginAuthToken.getUserNoLong();
		String termlId				= loginAuthToken.getDeviceId();
		String sessionId			= null;

		// 1. 기존 음성세션 존재여부 확인, 이미 사용하고 있으면 권한없음 에러 처리
		VoiceSession prevSession = voiceDao.getVoiceSession(devUUID);

		if(prevSession == null) {
			// 1.1 기존에 만들어진 세션이 없는 경우 신규 생성함
			sessionId = String.valueOf(voiceDao.createVoiceSessionId());
			voiceDao.insertVoiceSession(sessionId, devUUID, mbrSeq, termlId);
		} else {
			// 1.2 기존에 만들어진 세션이 있는 경우 동일한 접속이 아니면 예외처리함
			sessionId = prevSession.getSessionId();
			long sessionMbrSeq = prevSession.getMbrSeq();
			String sessionTermlId = prevSession.getTermlId();

			if(sessionMbrSeq == mbrSeq && StringUtils.equalsIgnoreCase(sessionTermlId, termlId)) {
				// 1.2.1 동일한 접속인 경우 시간 연장
				voiceDao.updateVoiceSession(sessionId, devUUID, mbrSeq, termlId);
			} else {
				// 1.2.2 동일한 접속이 아닌 경우 예외처리
				throw new APIException(String.format("Voice session is full. Prev[%d:%s], Req[%d:%s]",
						sessionMbrSeq, sessionTermlId, mbrSeq, termlId), HttpStatus.FORBIDDEN, HCamConstants.ErrorCode.VOICE_SESSION_OVERFLOW);
			}
		}

		SpotDevBasVO spotDevBas = deviceService.checkDeviceConnectedEC(svcCode, devUUID);
		String ecServer = ecService.getRealIp(spotDevBas.getCommnAgntBasVO().getIpadr());

		int ecPort = env.getIntProperty("ec.voice.port");

		// TODO 인증처리는 다시 논의해야 함, 현재는 ec 에서 그대로 통과
//		String ecAuthReqNo = commnAgnt.getAthnRqtNo();
		String ecAuthReqNo = "0";

		VoiceSession voiceSession = new VoiceSession();
		voiceSession.setSessionId(sessionId);
		voiceSession.setEcServer(ecServer);
		voiceSession.setEcPort(ecPort);
		voiceSession.setAthnRqtNo(ecAuthReqNo);
		voiceSession.setSvcTgtSeq(spotDevBas.getSvcTgtSeq());
		voiceSession.setSpotDevSeq(spotDevBas.getSpotDevSeq());
		voiceSession.setSnsnTagCd(SnsnTagCd.VOICE_CONTROL);	// 음성
		voiceSession.setExpireTime(EXPIRE_TIME_MIN);

		return voiceSession;
	}

	/**
	 * 음성 세션 연장 메쏘드
	 *
	 * @param authToken		로그인 인증토큰
	 * @param devUUID		디바이스 UUID
	 * @param sessionId		음성 세션 아이디
	 * @return				음성 세션 객체
	 * @throws Exception
	 */
	public VoiceSession refreshVoiceSession(String authToken, String devUUID, String sessionId) throws Exception {

		AuthToken loginAuthToken = userService.checkLoginAuthToken(authToken);
		long mbrSeq = loginAuthToken.getUserNoLong();
		String termlId = loginAuthToken.getDeviceId();
		voiceDao.updateVoiceSession(sessionId, devUUID, mbrSeq, termlId);

		VoiceSession voiceSession = new VoiceSession();
		voiceSession.setExpireTime(EXPIRE_TIME_MIN);

		return voiceSession;
	}

	/**
	 * 음성 세션 삭제 메쏘드
	 *
	 * @param authToken		로그인 인증토큰
	 * @param devUUID		디바이스 UUID
	 * @param sessionId		음성 세션 아이디
	 * @throws Exception
	 */
	public void deleteVoiceSession(String authToken, String devUUID, String sessionId) throws Exception {

		AuthToken loginAuthToken = userService.checkLoginAuthToken(authToken);
		long mbrSeq = loginAuthToken.getUserNoLong();
		String termlId = loginAuthToken.getDeviceId();
		voiceDao.deleteVoiceSession(sessionId, devUUID, mbrSeq, termlId);
	}

	/**
	 * 만료된 음성 세션을 삭제하는 비동기 메쏘드
	 * 스케줄러에 의해 호출됨
	 */
	@Async
	public void deleteExpiredVoiceSession() {

		try {
			log.debug("## deleteExpiredVoiceSession BEGIN ##");

			ArrayList<VoiceSession> expiredVoiceSessionList = voiceDao.getExpiredVoiceSession(EXPIRE_TIME_MIN);
			int expiredSessionCount = CollectionUtils.size(expiredVoiceSessionList);

			if(expiredSessionCount > 0) {

				for(VoiceSession voiceSession : expiredVoiceSessionList) {
					String sessionId	= voiceSession.getSessionId();
					String devUUID		= voiceSession.getDevUUID();
					Long mbrSeq			= voiceSession.getMbrSeq();
					String termlId		= voiceSession.getTermlId();

					voiceDao.deleteVoiceSession(sessionId, devUUID, mbrSeq, termlId);
				}
			}

			log.debug("## deleteExpiredVoiceSession END. Delete Count : {} ##", expiredSessionCount);

		} catch(Exception e) {
			log.warn(e.getMessage(), e);
		}
	}

}
