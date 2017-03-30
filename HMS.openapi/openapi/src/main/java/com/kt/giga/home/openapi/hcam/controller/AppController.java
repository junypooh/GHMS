package com.kt.giga.home.openapi.hcam.controller;

import com.kt.giga.home.openapi.hcam.domain.AppInit;
import com.kt.giga.home.openapi.hcam.service.InitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

/**
 * Created by cecil on 2015-07-13.
 */
@Controller
@RequestMapping("/app")
public class AppController {

    @Autowired
    private InitService initService;

    /**
     * 앱 최신 버전 조회 API 매핑 메쏘드
     *
     * @param authToken 로그인 인증 토큰
     * @throws Exception
     * @return 앱 초기 실행 객체
     */
    @RequestMapping(
            value = "/version",
            method = GET,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseBody
    public AppInit getAppVersion(
            @RequestHeader("authToken") String authToken
    ) throws Exception {

        return initService.getNewAppVersion(authToken);

    }

}
