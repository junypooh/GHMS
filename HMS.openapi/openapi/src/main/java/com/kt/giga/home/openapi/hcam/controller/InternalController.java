package com.kt.giga.home.openapi.hcam.controller;

import com.kt.giga.home.openapi.hcam.service.DeviceService;
import com.kt.giga.home.openapi.vo.spotdev.SpotDevRetvReslt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

/**
 * Created by cecil on 2015-07-10.
 * <p/>
 * Controller for communicating with EC Servers.
 */

@Controller
@RequestMapping(value = "/intn")
public class InternalController {

    @Autowired
    private DeviceService deviceService;

    /**
     * 내부 인터널 API - retv (조회 요청에 대한 결과 Report) 매핑 메쏘드
     *
     * @param spotDevRetvReslt {@link com.kt.giga.home.openapi.vo.spotdev.SpotDevRetvReslt}
     * @return 처리 결과 맵
     * @throws Exception
     */
    @RequestMapping(
            value = "/initarprt/spotdev/retv",
            method = POST,
            consumes = APPLICATION_JSON_VALUE,
            produces = APPLICATION_JSON_VALUE
    )
    @ResponseBody
    public Callable<Map<String, String>> setDeviceReadResult(

            @RequestBody SpotDevRetvReslt spotDevRetvReslt

    ) throws Exception {

        return deviceService.setDeviceReadResult(spotDevRetvReslt);
    }

    /**
     * 내부 인터널 API - retv (조회 요청 수신) 매핑 메쏘드
     *
     * @param requestJson
     * @return 처리 결과 맵
     * @throws Exception
     */
    @RequestMapping(
            value = "/inita/spotdev/retv",
            method = POST,
            consumes = APPLICATION_JSON_VALUE,
            produces = APPLICATION_JSON_VALUE
    )
    @ResponseBody
    public HashMap<String, String> setDeviceReadControlRequest(

            @RequestBody String requestJson

    ) throws Exception {

        deviceService.setDeviceReadRequest(requestJson);
        return deviceService.getIntnResult("0", "success");
    }

    /**
     * 내부 인터널 API - rtimeContl (실시간 제어에 대한 결과 Report) 매핑 메쏘드
     *
     * @param resultJson 실시간 제어 결과 JSON
     * @return 처리 결과 맵
     * @throws Exception
     */
    @RequestMapping(
            value = "/contlrprt/spotdev/rtimeContl",
            method = POST,
            consumes = APPLICATION_JSON_VALUE,
            produces = APPLICATION_JSON_VALUE
    )
    @ResponseBody
    public HashMap<String, String> setRTimeControlResult(

            @RequestBody String resultJson

    ) throws Exception {

        return deviceService.setRTimeControlResult(resultJson);
    }

    /**
     * 내부 인터널 API - setupChg (설정 제어에 대한 결과 Report) 매핑 메쏘드
     *
     * @param resultJson 설정 제어 결과 JSON
     * @return 처리 결과 맵
     * @throws Exception
     */
    @RequestMapping(
            value = "/contlrprt/spotdev/setupChg",
            method = POST,
            consumes = APPLICATION_JSON_VALUE,
            produces = APPLICATION_JSON_VALUE
    )
    @ResponseBody
    public HashMap<String, String> setSetupControlResult(

            @RequestBody String resultJson

    ) throws Exception {

        return deviceService.setSetupControlResult(resultJson);

    }

    /**
     * 내부 인터널 API - udateRprt (장치 및 상태 변경) 매핑 메쏘드
     *
     * @param updateJson 장치 상태 변경 Report
     * @return 처리 결과 맵
     * @throws Exception
     */
    @RequestMapping(
            value = "/inita/spotdev/udateRprt",
            method = POST,
            consumes = APPLICATION_JSON_VALUE,
            produces = APPLICATION_JSON_VALUE
    )
    @ResponseBody
    public HashMap<String, String> setSpotDevUpdate(

            @RequestBody String updateJson

    ) throws Exception {

        return deviceService.setSpotDevUpdate(updateJson);
    }
}
