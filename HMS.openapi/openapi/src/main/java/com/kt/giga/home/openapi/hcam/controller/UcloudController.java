package com.kt.giga.home.openapi.hcam.controller;

import com.kt.giga.home.openapi.hcam.service.DeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.DELETE;

/**
 * Created by cecil on 2015-07-13.
 */
@Controller
@RequestMapping(value = "/ucloud")
public class UcloudController {

    @Autowired
    private DeviceService deviceService;

    /**
     * UCloud 해지 API 매핑 메쏘드
     *
     * @param unitSvcCd 단위 서비스 코드
     * @param loginId   로그인 아이디
     * @return 처리 결과 맵
     */
    @RequestMapping(
            value = "/account",
            method = DELETE,
            produces = APPLICATION_JSON_VALUE
    )
    @ResponseBody
    public HashMap<String, String> deleteUCloudAccount(
            
            @RequestParam(value = "unitSvcCd", required = false) String unitSvcCd,
            @RequestParam(value = "loginId", required = false) String loginId) {

        return deviceService.deleteUCloudAccount(unitSvcCd, loginId);
    }


}
