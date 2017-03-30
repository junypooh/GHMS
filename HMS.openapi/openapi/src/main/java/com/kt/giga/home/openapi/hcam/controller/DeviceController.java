package com.kt.giga.home.openapi.hcam.controller;

import com.kt.giga.home.openapi.common.AuthToken;
import com.kt.giga.home.openapi.hcam.domain.EnrollmentStatus;
import com.kt.giga.home.openapi.hcam.service.DeviceService;
import com.kt.giga.home.openapi.service.APIException;
import com.kt.giga.home.openapi.vo.cnvy.ItgCnvyData;
import com.kt.giga.home.openapi.vo.spotdev.SpotDevRetvReslt;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;

/**
 * 디바이스 처리 컨트롤러
 */
@Controller("HCam.DeviceController")
@RequestMapping("/devices")
public class DeviceController {

    @Autowired
    private DeviceService deviceService;

    /**
     * 디바이스 마스터 정보 조회 API 매핑 메쏘드
     *
     * @param authToken 로그인 인증 토큰
     * @param devUUID   디바이스 UUID
     * @return {@link com.kt.giga.home.openapi.vo.spotdev.SpotDevRetvReslt}
     * @throws Exception
     */
    @RequestMapping(
            value = "/spot",
            method = GET,
            produces = APPLICATION_JSON_VALUE
    )
    @ResponseBody
    public Callable<SpotDevRetvReslt> getDeviceMaster(

            @RequestHeader(value = "authToken") String authToken,
            @RequestParam(value = "devUUID", required = false) String devUUID

    ) throws Exception {

        return deviceService.getMasterConfig(authToken, devUUID);

    }

    /**
     * 디바이스 마스터 정보 조회 API 매핑 메쏘드
     *
     * @param authToken 로그인 인증 토큰
     * @param devUUID   디바이스 UUID
     * @return 디바이스 마스터 정보. SpotDevRetvReslt 객체
     * @throws Exception
     */
    @RequestMapping(
            value = "/spotdb",
            method = GET,
            produces = APPLICATION_JSON_VALUE
    )
    @ResponseBody
    public SpotDevRetvReslt getDeviceMasterOnlyDB(

            @RequestHeader(value = "authToken") String authToken,
            @RequestParam(value = "devUUID", required = false) String devUUID) throws Exception {

        return deviceService.getMasterConfigOnlyDB(authToken, devUUID);

    }

    /**
     * 디바이스 설정 제어 API 매핑 메쏘드
     *
     * @param authToken   로그인 인증 토큰
     * @param itgCnvyData {@link com.kt.giga.home.openapi.vo.cnvy.ItgCnvyData}
     * @param httpRequest {@link javax.servlet.http.HttpServletRequest}
     * @return {@link com.kt.giga.home.openapi.vo.spotdev.SpotDevRetvReslt}
     * @throws Exception
     */
    @RequestMapping(
            value = "/control/config",
            method = PUT,
            consumes = APPLICATION_JSON_VALUE,
            produces = APPLICATION_JSON_VALUE
    )
    @ResponseBody
    public SpotDevRetvReslt setDeviceSetupControl(

            @RequestHeader(value = "authToken") String authToken,
            @RequestBody ItgCnvyData itgCnvyData,
            HttpServletRequest httpRequest

    ) throws Exception {

        AuthToken authTokenAddRequestIp = AuthToken.encodeAuthTokenAddRequestIp(authToken, httpRequest.getRemoteAddr());
        return deviceService.setDeviceSetupControl(authTokenAddRequestIp.getToken(), itgCnvyData);

    }

    /**
     * 디바이스 실시간 동작 제어 API 매핑 메쏘드
     *
     * @param authToken   로그인 인증 토큰
     * @param itgCnvyData {@link com.kt.giga.home.openapi.vo.cnvy.ItgCnvyData}
     * @param httpRequest {@link javax.servlet.http.HttpServletRequest}
     * @return {@link com.kt.giga.home.openapi.vo.spotdev.SpotDevRetvReslt}
     * @throws Exception
     */
    @RequestMapping(
            value = "/control/rtime",
            method = PUT,
            consumes = APPLICATION_JSON_VALUE,
            produces = APPLICATION_JSON_VALUE
    )
    @ResponseBody
    public SpotDevRetvReslt setDeviceRTimeControl(

            @RequestHeader(value = "authToken") String authToken,
            @RequestBody ItgCnvyData itgCnvyData,
            HttpServletRequest httpRequest

    ) throws Exception {

        AuthToken authTokenAddRequestIp = AuthToken.encodeAuthTokenAddRequestIp(authToken, httpRequest.getRemoteAddr());
        return deviceService.setDeviceRTimeControl(authTokenAddRequestIp.getToken(), itgCnvyData);

    }

    /**
     * 카메라 정보 조회 API 매핑 메쏘드
     *
     * @param authToken 로그인 인증 토큰
     * @param devUUID   디바이스 UUID
     * @param snsnTagCd 센싱 태그
     * @return JSON 스트링. 고정값 "{}" 리턴
     * @throws Exception
     */
    @RequestMapping(
            value = "/control/retv",
            method = GET,
            produces = APPLICATION_JSON_VALUE
    )
    @ResponseBody
    public SpotDevRetvReslt getDeviceControlRetv(

            @RequestHeader(value = "authToken") String authToken,
            @RequestParam(value = "devUUID", required = true) String devUUID,
            @RequestParam(value = "snsnTagCd", required = true) String snsnTagCd,
            HttpServletRequest httpRequest

    ) throws Exception {

        AuthToken authTokenAddRequestIp = AuthToken.encodeAuthTokenAddRequestIp(authToken, httpRequest.getRemoteAddr());
        return deviceService.setDeviceSetupReadControl(authTokenAddRequestIp.getToken(), devUUID, snsnTagCd);
    }

    /**
     * 제어 트랜잭션 상태 조회 API 매핑 메쏘드
     *
     * @param authToken     로그인 인증 토큰
     * @param transactionId 트랜잭션 아이디
     * @return 디바이스 마스터 정보. SpotDevRetvReslt 객체
     * @throws Exception
     */
    @RequestMapping(
            value = "/control/status",
            method = GET,
            produces = APPLICATION_JSON_VALUE
    )
    @ResponseBody
    public SpotDevRetvReslt getDeviceControlStatus(

            @RequestHeader(value = "authToken") String authToken,
            @RequestParam(value = "transacId", required = true) String transactionId
    ) throws Exception {

        return deviceService.getControlTransactionStatus(authToken, transactionId);
    }

    /**
     * 홈 카메라 닉네임 설정 API 매핑 메쏘드
     *
     * @param authToken 로그인 인증 토큰
     * @param params    요청 파라미터 맵. devUUID(디바이스 UUID), devNm(디바이스 닉네임)
     * @return JSON 스트링. 고정값 "{}" 리턴
     * @throws Exception
     */
    @RequestMapping(
            value = "/spot",
            method = PUT,
            produces = APPLICATION_JSON_VALUE)
    @ResponseBody
    public String setDeviceName(

            @RequestHeader(value = "authToken") String authToken,
            @RequestBody Map<String, String> params

    ) throws Exception {

        String devUUID = params.get("devUUID");
        String devNm = params.get("devNm");
        deviceService.setDeviceName(authToken, devUUID, devNm);

        return "{}";
    }

    /**
     * 홈 카메라 저장매체 설정 API 매핑 메쏘드
     *
     * @param authToken   로그인 인증 토큰
     * @param itgCnvyData {@link com.kt.giga.home.openapi.vo.cnvy.ItgCnvyData}
     * @return {@link com.kt.giga.home.openapi.vo.spotdev.SpotDevRetvReslt}
     * @throws Exception
     */
    @RequestMapping(
            value = "/control/storage",
            method = PUT,
            consumes = APPLICATION_JSON_VALUE,
            produces = APPLICATION_JSON_VALUE
    )
    @ResponseBody
    public SpotDevRetvReslt setDeviceStorageControl(

            @RequestHeader(value = "authToken") String authToken,
            @RequestBody ItgCnvyData itgCnvyData

    ) throws Exception {

        // 일반설정 그대로 호출
        return deviceService.setDeviceSetupControl(authToken, itgCnvyData);

    }

    /**
     * 펌웨어 최신버전 조회 (실시간) API 매핑 메쏘드
     *
     * @param authToken 로그인 인증 토큰
     * @param devUUID   디바이스 UUID
     * @return 펌웨어 최신버전 정보 맵
     * newFrmwrVerNo	: 펌웨어 최신버전 정보
     * @throws Exception
     */
    @RequestMapping(
            value = "/ucems/firmver",
            method = GET,
            produces = APPLICATION_JSON_VALUE
    )
    @ResponseBody
    public Map<String, String> getUCEMSFirmwareVersion(

            @RequestHeader(value = "authToken") String authToken,
            @RequestParam(value = "devUUID", required = false) String devUUID

    ) throws Exception {

        return deviceService.getCurrentReleasedFirmwareVersionFromUCEMS(authToken, devUUID);
    }

    /**
     * 홈 카메라 펌웨어 업그레이드 요청 API 매핑 메쏘드
     *
     * @param authToken 로그인 인증 토큰
     * @param params
     * @return JSON 스트링. 고정값 "{}" 리턴
     * @throws Exception
     */
    @RequestMapping(
            value = "/ucems/firmup",
            method = PUT,
            produces = APPLICATION_JSON_VALUE)
    @ResponseBody
    public Map<String, String> setUCEMSFirmwareUpgradeControl(

            @RequestHeader(value = "authToken") String authToken,
            @RequestBody HashMap<String, String> params

    ) throws Exception {

        String devUUID = params.get("devUUID");
        return deviceService.setFirmwareUpgradeControlToUCEMS(authToken, devUUID);
    }

    /**
     * 펌웨어 업그레이드 상태 조회 (실시간) API 매핑 메쏘드
     *
     * @param authToken 로그인 인증 토큰
     * @param devUUID   디바이스 UUID
     * @return 펌웨어 업그레이 상태 정보 맵
     * result		: 결과코드 -1:실패, 0:성공
     * stateCode	: 상태코드
     * @throws Exception
     */
    @RequestMapping(
            value = "/ucems/firmup/status",
            method = GET,
            produces = APPLICATION_JSON_VALUE
    )
    @ResponseBody
    public Map<String, String> getUCEMSFirmwareUpgradeStatus(

            @RequestHeader(value = "authToken") String authToken,
            @RequestParam(value = "devUUID", required = false) String devUUID

    ) throws Exception {

        return deviceService.getFirmwareUpgradeStatusFromUCEMS(authToken, devUUID);
    }

    /**
     * 펌웨어 업그레이드 확인 완료 API 매핑 메쏘드
     *
     * @param authToken 로그인 인증 토큰
     * @param params
     * @return 펌웨어 업그레이 상태 정보 맵
     * result		: 결과코드 -1:실패, 0:성공
     * stateCode	: 상태코드
     * @throws Exception
     */
    @RequestMapping(
            value = "/ucems/firmup/complete",
            method = PUT,
            produces = APPLICATION_JSON_VALUE
    )
    @ResponseBody
    public Map<String, String> getUCEMSFirmwareUpgradeStatus(

            @RequestHeader(value = "authToken") String authToken,
            @RequestBody HashMap<String, String> params

    ) throws Exception {

        String devUUID = params.get("devUUID");
        return deviceService.setFirmwareUpgradeComplete(authToken, devUUID);
    }

    /**
     * 디바이스 연결 상태 체크 API 매핑 메쏘드
     * Cron 등 외부 스케줄러에 의해 호출 필요한 경우 사용. 내부 스케줄러로 대체되어 현재는 사용하지 않음
     *
     * @param unitSvcCd 단위 서비스 코드
     * @return JSON 스트링. 고정값 "{}" 리턴
     */
    @RequestMapping(
            value = "/spot/conn/check",
            method = GET,
            produces = APPLICATION_JSON_VALUE
    )
    @ResponseBody
    public String checkDeviceConnStatus(

            @RequestParam(value = "unitSvcCd", required = false) String unitSvcCd

    ) {

        deviceService.checkDeviceConnStatus(StringUtils.defaultIfBlank(unitSvcCd, "001"));
        return "{}";
    }

    //

    /**
     * 디바이스 업그레이드 상태 체크 API 매핑 메쏘드
     * Cron 등 외부 스케줄러에 의해 호출 필요한 경우 사용. 내부 스케줄러로 대체되어 현재는 사용하지 않음
     *
     * @param unitSvcCd 단위 서비스 코드
     * @return JSON 스트링. 고정값 "{}" 리턴
     */
    @RequestMapping(
            value = "/ucems/firmup/status/check",
            method = GET,
            produces = APPLICATION_JSON_VALUE
    )
    public String checkUCEMSFirmwareUpgradeStatus(

            @RequestParam(value = "unitSvcCd", required = false) String unitSvcCd

    ) {

        deviceService.checkFirmwareUpgradeStatusFromUCEMS();
        return "{}";
    }

    /**
     *
     * @param authToken             {@link com.kt.giga.home.openapi.common.AuthToken}
     * @param mac                   {@link java.lang.String}
     * @return                      {@link com.kt.giga.home.openapi.hcam.domain.EnrollmentStatus}
     * @throws Exception
     */
    @RequestMapping(
            value = "/verifyenroll/{mac}",
            method = GET,
            produces = APPLICATION_JSON_VALUE)

    @ResponseBody
    public EnrollmentStatus verifyEnrollment(

            @RequestHeader(value = "authToken") String authToken,
            @PathVariable("mac") String mac

    ) throws Exception {

        final EnrollmentStatus enrollmentStatus = deviceService.getEnrollmentStatus(authToken, mac.toUpperCase());

        if (null == enrollmentStatus)
            throw new APIException("Device does not exist.", HttpStatus.NOT_FOUND);

        return enrollmentStatus;

    }

}
