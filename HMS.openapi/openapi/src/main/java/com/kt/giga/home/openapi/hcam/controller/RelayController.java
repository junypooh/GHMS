package com.kt.giga.home.openapi.hcam.controller;

import com.kt.giga.home.openapi.hcam.domain.RelayEvent;
import com.kt.giga.home.openapi.hcam.domain.RelaySession;
import com.kt.giga.home.openapi.hcam.service.RelayControlService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import java.io.StringReader;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;

import static org.springframework.web.bind.annotation.RequestMethod.*;

/**
 * 릴레이 서버 연동처리 컨트롤러
 *
 * @author
 */
@Controller("HCam.RelayController")
@RequestMapping("/relay")
public class RelayController {

    private final Logger log = LoggerFactory.getLogger(getClass());

    /**
     * 실시간 모니터링 컨트롤 서비스
     */
    @Autowired
    private RelayControlService relayControlService;

    /**
     * 실시간 모니터링 접속 정보 요청 API 매핑 메쏘드
     *
     * @param authToken 로그인 인증 토큰
     * @param devUUID   디바이스 UUID
     * @return 실시간 모니터링 세션 객체
     * @throws Exception
     */
    @RequestMapping(
            value = "/session",
            method = GET,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseBody
    public Callable<Map<String, String>> getRelaySession(
            @RequestHeader(value = "authToken") String authToken,
            @RequestParam(value = "devUUID", required = true) String devUUID) throws Exception {

        return relayControlService.getRelaySession(authToken, devUUID);
    }

    /**
     * 실시간 모니터링 접속 정보 요청 API 매핑 메쏘드 (통합 조회용 개발 단계)
     *
     * @param authToken 로그인 인증 토큰
     * @param devUUID   디바이스 UUID
     * @return 실시간 모니터링 세션 객체
     * @throws Exception
     */
    @RequestMapping(
            value = "/newsession",
            method = GET,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseBody
    public Callable<Map<String, String>> getIntegratedRelaySession(
            @RequestHeader(value = "authToken") String authToken,
            @RequestParam(value = "devUUID", required = true) String devUUID) throws Exception {

        return relayControlService.getRelaySession(authToken, devUUID);
    }

    /**
     * 실시간 모니터링 세션 연장 요청 API 매핑 메쏘드
     *
     * @param authToken 로그인 인증 토큰
     * @param params    요청 파라미터 맵. devUUID(디바이스 UUID), sessionId(실시간 모니터링 세션 아이디)
     * @return {@link com.kt.giga.home.openapi.hcam.domain.RelaySession}
     * @throws Exception
     */
    @RequestMapping(
            value = "/session",
            method = PUT,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseBody
    public RelaySession refreshRelaySessionExpireTime(
            @RequestHeader(value = "authToken") String authToken,
            @RequestBody HashMap<String, String> params) throws Exception {

        String devUUID = params.get("devUUID");
        String sessionId = params.get("sessionId");
        return relayControlService.refreshRelaySessionExpireTime(authToken, devUUID, sessionId);
    }

    /**
     * Relay Event Receiver
     *
     * @param xmlString Relay Event
     * @throws Exception
     */
    @RequestMapping(
            value = "/relay/event",
            method = POST,
            consumes = MediaType.APPLICATION_ATOM_XML_VALUE)
    @ResponseBody
    public void processRelayEvent(

            @RequestBody String xmlString

    ) throws Exception {

        String decodedString = URLDecoder.decode(xmlString, "UTF-8");
        log.debug("### RelayEvent : {}", decodedString);
        JAXBContext jaxbContext = JAXBContext.newInstance(RelayEvent.class);

        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
        RelayEvent relayEvent = (RelayEvent) jaxbUnmarshaller.unmarshal(new StringReader(decodedString));


        log.debug("### RelayEvent : \n{}", relayEvent.getEventType());
    }

}
