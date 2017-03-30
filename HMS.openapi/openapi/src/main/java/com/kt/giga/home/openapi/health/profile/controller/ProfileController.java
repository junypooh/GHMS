package com.kt.giga.home.openapi.health.profile.controller;

import java.util.*;

import org.json.simple.*;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.*;

import com.kt.giga.home.openapi.health.profile.service.*;

/**
 * 프로필 관리 컨트롤러
 * @author 김용성
 *
 */
@Controller
@RequestMapping("/user")
public class ProfileController {
	
	private static final String PRODUCES_JSON = "application/json; charset=UTF-8";
	
	@Autowired
	private ProfileService profileService;
	
	// 프로필 조회 (서비스별,단말별)
	@ResponseBody
	@ResponseStatus(HttpStatus.OK) 	
	@RequestMapping(value="/profile", method=RequestMethod.GET, produces=PRODUCES_JSON)
	public Map<String, Object> getProfile(@RequestHeader(value="authToken", required=true) String authToken) throws Exception {
		
		Map<String, Object> profile = profileService.get(authToken);
		return profile;
	}
	
	// 프로필 등록 
	@ResponseBody
	@ResponseStatus(HttpStatus.OK) 	
	@RequestMapping(value="/profile", method=RequestMethod.POST, produces=PRODUCES_JSON)
	public JSONObject registerProfile(@RequestHeader(value="authToken", required=true) String authToken, @RequestBody Map<String, Object> profile) throws Exception {
		
		profileService.register(authToken, profile); 
		
		JSONObject json = new JSONObject();
		json.put("profRegResult", profile.get("profRegResult"));
		return json;
	}

	// 프로필 수정 
	@ResponseBody
	@ResponseStatus(HttpStatus.OK) 	
	@RequestMapping(value="/profile", method=RequestMethod.PUT, produces=PRODUCES_JSON)
	public JSONObject modifyProfile(@RequestHeader(value="authToken", required=true) String authToken, @RequestBody Map<String, Object> profile) throws Exception {
		
		profileService.modify(authToken, profile);
		
		JSONObject json = new JSONObject();
		json.put("profMdfResult", profile.get("profMdfResult"));
		return json;		
	}	
}
