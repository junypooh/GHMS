package com.kt.giga.home.openapi.hcam.controller;

import com.kt.giga.home.openapi.hcam.domain.VoiceSession;
import com.kt.giga.home.openapi.hcam.service.VoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

import static org.springframework.web.bind.annotation.RequestMethod.*;


/**
 * 음성 전송 처리 컨트롤러
 *
 * @author
 */
@Controller("HCam.VoiceController")
@RequestMapping("/voice")
public class VoiceController {

    @Autowired
    private VoiceService voiceService;

    /**
     * 음성 전송 세션 조회 API 매핑 메쏘드
     *
     * @param authToken 로그인 인증 토큰
     * @param devUUID   디바이스 UUID
     * @return {@link com.kt.giga.home.openapi.hcam.domain.VoiceSession}
     * @throws Exception
     */
    @RequestMapping(
            value = "/session",
            method = GET,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseBody
    public VoiceSession getVoiceSession(

            @RequestHeader("authToken") String authToken,
            @RequestParam(value = "devUUID", required = true) String devUUID

    ) throws Exception {

        return voiceService.getVoiceSession(authToken, devUUID);
    }

    /**
     * 음성 전송 세션 연장 API 매핑 메쏘드
     *
     * @param authToken 로그인 인증 토큰
     * @param params    요청 파라미터 맵. devUUID(디바이스UUID), sessionId(음성 세션 아이디)
     * @return {@link com.kt.giga.home.openapi.hcam.domain.VoiceSession}
     * @throws Exception
     */
    @RequestMapping(
            value = "/session",
            method = PUT,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseBody
    public VoiceSession refreshVoiceSession(

            @RequestHeader("authToken") String authToken,
            @RequestBody Map<String, String> params

    ) throws Exception {

        String devUUID = params.get("devUUID");
        String sessionId = params.get("sessionId");

        return voiceService.refreshVoiceSession(authToken, devUUID, sessionId);
    }

    /**
     * 음성 전송 세션 삭제 API 매핑 메쏘드
     *
     * @param authToken 로그인 인증 토큰
     * @param devUUID   디바이스 UUID
     * @param sessionId 음성 세션 아이디
     * @return JSON 스트링. 고정값 "{}" 리턴
     * @throws Exception
     */
    @RequestMapping(
            value = "/session",
            method = DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseBody
    public String deleteVoiceSession(

            @RequestHeader("authToken") String authToken,
            @RequestParam(value = "devUUID", required = true) String devUUID,
            @RequestParam(value = "sessionId", required = true) String sessionId

    ) throws Exception {

        voiceService.deleteVoiceSession(authToken, devUUID, sessionId);
        return "{}";
    }

    /**
     * 음성 전송 세션 만료 체크 API 매핑 메쏘드.
     * cron 등 외부 스케줄러 또는 관리자에 의해 필요한 경우 사용.
     *
     * @return JSON 스트링. 고정값 "{}" 리턴
     * @throws Exception
     */
    @RequestMapping(
            value = "/session/check",
            method = GET,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseBody
    public String checkVoiceSession() throws Exception {

        voiceService.deleteExpiredVoiceSession();
        return "{}";
    }
}