package com.kt.giga.home.openapi.health.device.controller;

import java.util.*;

import org.json.simple.*;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.*;

import com.kt.giga.home.openapi.health.device.service.*;

/**
 * 헬스케어 센서 등록 컨트롤러
 * @author 김용성
 *
 */
@Controller
public class DeviceSensorController {

	private static final String PRODUCES_JSON = "application/json; charset=UTF-8";

	@Autowired
	private DeviceSensorService deviceSensorService;

	// 센서 등록
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value="/devices/spot", method=RequestMethod.POST, produces=PRODUCES_JSON)
	public JSONObject registerSensor(@RequestHeader(value="authToken", required=true) String authToken, @RequestBody Map<String, Object> device) throws Exception {

		deviceSensorService.register(authToken, device);

		JSONObject result = new JSONObject();
		result.put("spotDevRegSttus", device.get("spotDevRegSttus"));

		return result;

	}
}
