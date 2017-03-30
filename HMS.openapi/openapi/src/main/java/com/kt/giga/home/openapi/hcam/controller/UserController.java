package com.kt.giga.home.openapi.hcam.controller;

import com.kt.giga.home.openapi.common.AuthToken;
import com.kt.giga.home.openapi.hcam.domain.*;
import com.kt.giga.home.openapi.hcam.service.UserService;
import com.kt.giga.home.openapi.service.APIException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.*;

/**
 * 초기실행, 인증/로그인, 토큰갱신등 회원관련 처리 컨트롤러
 *
 * @author
 */
@Controller("HCam.UserController")
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 사용자 인증, 로그인 API 매핑 메쏘드
     *
     * @param authToken 초기 인증 토큰
     * @param login     {@link com.kt.giga.home.openapi.hcam.domain.Login}
     * @return {@link com.kt.giga.home.openapi.hcam.domain.UserAuth}
     * @throws Exception
     */
    @RequestMapping(
            value = "/authentication",
            method = POST,
            produces = APPLICATION_JSON_VALUE
    )
    @ResponseBody
    public UserAuth auth(

            @RequestHeader(value = "authToken") String authToken,
            @RequestBody Login login

    ) throws Exception {

        if (StringUtils.isBlank(login.getUserId()) || StringUtils.isBlank(login.getPasswd())) {
            throw new APIException(HttpStatus.BAD_REQUEST.name(), HttpStatus.BAD_REQUEST);
        }

        return userService.auth(authToken, login.getUserId(), login.getPasswd());
    }

    /**
     * 휴대폰 번호 매핑 메쏘드
     *
     * @param authToken 초기 인증 토큰
     * @param map
     * @return {@link com.kt.giga.home.openapi.hcam.domain.UserAuth}
     * @throws Exception
     */
    @RequestMapping(
            value = "/tel",
            method = PUT,
            produces = APPLICATION_JSON_VALUE
    )
    @ResponseBody
    public UserAuth regTel(

            @RequestHeader(value = "authToken") String authToken,
            @RequestBody Map<String, String> map

    ) throws Exception {

        if (map == null || !map.containsKey("telNo") || StringUtils.isBlank(map.get("telNo"))) {
            throw new APIException(HttpStatus.BAD_REQUEST.name(), HttpStatus.BAD_REQUEST);
        }

        AppInitRequest appInitRequest = new AppInitRequest();
        appInitRequest.setTelNo(map.get("telNo"));

        return userService.regTel(authToken, appInitRequest);
    }

    /**
     * PNS regid 매핑 메쏘드
     *
     * @param authToken 초기 인증 토큰
     * @param map
     * @return {@link com.kt.giga.home.openapi.hcam.domain.UserAuth}
     * @throws Exception
     */
    @RequestMapping(
            value = "/pns",
            method = PUT,
            produces = APPLICATION_JSON_VALUE
    )
    @ResponseBody
    public UserAuth regPns(

            @RequestHeader(value = "authToken") String authToken,
            @RequestBody Map<String, String> map

    ) throws Exception {

        if (map == null || !map.containsKey("pnsRegId") || StringUtils.isBlank(map.get("pnsRegId"))) {
            throw new APIException(HttpStatus.BAD_REQUEST.name(), HttpStatus.BAD_REQUEST);
        }

        AppInitRequest appInitRequest = new AppInitRequest();
        appInitRequest.setPnsRegId(map.get("pnsRegId"));

        return userService.regPns(authToken, appInitRequest);
    }

    /**
     * 인증토큰 재발급 API 매핑 메쏘드
     *
     * @param authToken 로그인 인증 토큰
     * @return {@link com.kt.giga.home.openapi.hcam.domain.User}
     * @throws Exception
     */
    @RequestMapping(
            value = "/authentication/manager",
            method = GET,
            produces = APPLICATION_JSON_VALUE
    )
    @ResponseBody
    public User refreshToken(

            @RequestHeader(value = "authToken") String authToken

    ) throws Exception {

        return userService.refreshLoginAuthToken(authToken);
    }

    /**
     * 로그아웃 API 매핑 메쏘드
     *
     * @param authToken 로그인 인증 토큰
     * @return JSON 스트링. 고정값 "{}" 리턴
     * @throws Exception
     */
    @RequestMapping(
            value = "/logout",
            method = GET,
            produces = APPLICATION_JSON_VALUE
    )
    @ResponseBody
    public String logout(

            @RequestHeader(value = "authToken") String authToken

    ) throws Exception {

        userService.logout(authToken);

        return "{}";
    }

    /**
     * PNS 알림 수신 설정 조회 API 매핑 메쏘드
     *
     * @param authToken 로그인 인증 토큰
     * @return 알림 수신 설정 맵.
     * prcvPns		: 침입감지알림 수신여부. 0:OFF, 1:ON
     * loginPns	    : 로그인알림 수신여부. 0:OFF, 1:ON
     * cctvPns		: 카메라 연결오류알림 수신여부. 0:OFF, 1:ON
     * ucloudPns	: UCloud 공간부족알림 수신여부. 0:OFF, 1:ON
     * sdCardPns	: SD카드 오류알림 수신여부. 0:OFF, 1:ON
     * @throws Exception
     */
    @RequestMapping(
            value = "/config",
            method = GET,
            produces = APPLICATION_JSON_VALUE
    )
    @ResponseBody
    public Map<String, String> getUserSetup(

            @RequestHeader(value = "authToken") String authToken

    ) throws Exception {

        return userService.getUserSetup(authToken);
    }

    /**
     * PNS 알림 수신 설정 API 매핑 메쏘드
     *
     * @param authToken 로그인 인증 토큰
     * @param params    요청 파라미터 맵
     *                  prcvPns		: 침입감지알림 수신여부. 0:OFF, 1:ON
     *                  loginPns	: 로그인알림 수신여부. 0:OFF, 1:ON
     *                  cctvPns		: 카메라 연결오류알림 수신여부. 0:OFF, 1:ON
     *                  ucloudPns	: UCloud 공간부족알림 수신여부. 0:OFF, 1:ON
     *                  sdCardPns	: SD카드 오류알림 수신여부. 0:OFF, 1:ON
     * @return JSON 스트링. 고정값 "{}" 리턴
     * @throws Exception
     */
    @RequestMapping(
            value = "/config",
            method = PUT,
            produces = APPLICATION_JSON_VALUE
    )
    @ResponseBody
    public String setUserSetup(

            @RequestHeader(value = "authToken") String authToken,
            @RequestBody HashMap<String, String> params

    ) throws Exception {

        userService.setUserSetup(authToken, params);
        return "{}";
    }

    /**
     * 앱 접속자 리스트 조회 API 매핑 메쏘드
     *
     * @param authToken 로그인 인증 토큰
     * @return {@link java.util.List} of {@link com.kt.giga.home.openapi.hcam.domain.User}
     * @throws Exception
     */
    @RequestMapping(
            value = "/access",
            method = GET,
            produces = APPLICATION_JSON_VALUE
    )
    @ResponseBody
    public List<User> getUserAccess(

            @RequestHeader(value = "authToken") String authToken

    ) throws Exception {

        return userService.getAppUserList(authToken);
    }

    /**
     *
     * Returns a List{@link java.util.List} of latest login history of specified count.
     * @param authToken
     * @param count
     * @return a List{@link java.util.List} of
     * @throws Exception
     */
    @RequestMapping(
            value    = "/loginhistory",
            method   = GET,
            produces = APPLICATION_JSON_VALUE
    )
    @ResponseBody
    public List<LoginHistory> getLoginHistory(

            @RequestHeader(value = "authToken") String authToken,
            @RequestParam(value = "count") int count

    ) throws Exception {

        return userService.getLoginHistory(authToken, count);

    }

    @RequestMapping(
            value = "/mapping",
            method = DELETE,
            produces = APPLICATION_JSON_VALUE
    )
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public String initializeMap(
            @RequestHeader(value = "authToken") String authTokenString
    ) throws Exception {

        final AuthToken authToken = userService.checkLoginAuthToken(authTokenString);
        userService.initializeMappedDevices(authToken.getUserNoLong());
        return "{}";
    }
}
