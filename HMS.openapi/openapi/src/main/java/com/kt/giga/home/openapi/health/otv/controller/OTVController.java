package com.kt.giga.home.openapi.health.otv.controller;

import java.util.*;

import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.*;

import com.kt.giga.home.openapi.health.otv.service.*;

/**
 * 올레TV 관리 컨트롤러
 * @author 김용성
 *
 */
@Controller
@RequestMapping("/app/otv") 
public class OTVController {
	
	private static final String PRODUCES_JSON = "application/json; charset=UTF-8";
	
	@Autowired
	private OTVService otvService;
	
	// TV App 제어 요청(start, stop, restart)
	@ResponseBody
	@ResponseStatus(HttpStatus.OK) 	
	@RequestMapping(value="/control", method=RequestMethod.PUT, produces=PRODUCES_JSON)
	public Map<String, Object> control(@RequestHeader(value="authToken", required=true) String authToken, @RequestBody Map<String, Object> otv) throws Exception {
		return otvService.control(authToken, otv);
	}	
	
	// TV App 상태 전달 요청(TV App 죽었을때)
	@ResponseBody
	@ResponseStatus(HttpStatus.OK) 	
	@RequestMapping(value="/status", method=RequestMethod.PUT, produces=PRODUCES_JSON)
	public void sendStatus(@RequestBody Map<String, Object> otv) throws Exception {
		otvService.sendStatus(otv);
	}
	
	// STB에 연결된 전화번호 조회
	@ResponseBody
	@ResponseStatus(HttpStatus.OK) 	
	@RequestMapping(value="/status", method=RequestMethod.GET, produces=PRODUCES_JSON)	
	public Map<String, Object> getStatus(@RequestParam Map<String, Object> otv) throws Exception {
		return otvService.getStatus(otv);
	}

}
