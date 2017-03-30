package com.kt.giga.home.openapi.hcam.controller;

import com.kt.giga.home.openapi.hcam.domain.AppInit;
import com.kt.giga.home.openapi.hcam.domain.AppInitRequest;
import com.kt.giga.home.openapi.hcam.service.InitService;
import com.kt.giga.home.openapi.service.APIException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

/**
 * 앱 초기 실행 컨트롤러
 *
 * @author
 */
@Controller("HCam.InitController")
public class InitController {

    @Autowired
    private InitService initService;

    /**
     * 앱 초기 실행 API 매핑 메쏘드
     *
     * @return              {@link com.kt.giga.home.openapi.hcam.domain.AppInit}
     * @throws Exception
     * @param
     */
    @RequestMapping(

            value = "/user/init",
            method = POST,
            produces = APPLICATION_JSON_VALUE
    )
    @ResponseBody
    public AppInit init(

            @RequestBody AppInitRequest appInitRequest

    ) throws Exception {

        if (StringUtils.isBlank(appInitRequest.getDeviceId()) ||
                StringUtils.isBlank(appInitRequest.getAppVer())) {
            throw new APIException(HttpStatus.BAD_REQUEST.name(), HttpStatus.BAD_REQUEST);
        }

        String svcCode = StringUtils.defaultIfBlank(appInitRequest.getSvcCode(), "001");
        return initService.init(svcCode, appInitRequest.getDeviceId(), appInitRequest.getTelNo(), appInitRequest.getAppVer(), appInitRequest.getPnsRegId(), appInitRequest.getScreenSize());
    }

    /**
     * 앱 초기 실행 프라퍼티 리로딩 API 매핑 메쏘드.
     * 프라퍼티 메모리 갱신이 필요한 시점에 CMS 또는 외부 시스템에 의해 호출됨
     *
     * @param svcCode 서비스 코드
     * @return 리로딩된 프라퍼티 맵
     * @throws Exception
     */
    @RequestMapping(
            value = "/user/init/reload",
            method = GET,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseBody
    public Map<String, String> reloadProperties(

            @RequestParam(value = "svcCode", required = false) String svcCode

    ) throws Exception {

        svcCode = StringUtils.defaultIfBlank(svcCode, "001");
        return initService.reloadProperties(svcCode);
    }

}
