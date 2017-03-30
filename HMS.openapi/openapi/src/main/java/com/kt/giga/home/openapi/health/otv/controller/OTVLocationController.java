package com.kt.giga.home.openapi.health.otv.controller;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.kt.giga.home.openapi.health.otv.service.OTVLocationService;

/**
 * OTV App. 위치 컨트롤러
 * @author jnam
 *
 */
@Controller
public class OTVLocationController {
	
	private static final String PRODUCES_JSON = "application/json; charset=UTF-8";
	
	@Autowired
	private OTVLocationService otvLocationService;
	
	/**
	 * TV App. 위치 조회
	 * @param jsonObject				TV App 정보 (svcCode, said)
	 * @return						JsonObject(type, x, y)
	 * @throws Exception
	 */
	@RequestMapping(value={"/app/otv/config"}, method=RequestMethod.GET, produces=PRODUCES_JSON)
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public Map<String, Object> getOtvConf(
			@RequestParam Map<String, Object> input) throws Exception {
		return otvLocationService.selectOtvConf(input);
	}

	/**
	 * TV App. 위치 설정
	 * @param jsonObject				TV App 정보 (said, svcCode, type, x, y)
	 * @return						void
	 * @throws Exception
	 */
	@RequestMapping(value={"/app/otv/config"}, method={RequestMethod.POST}, produces=PRODUCES_JSON)
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public void postOtvConf( 
			  @RequestBody Map<String, Object> input) throws Exception {
		otvLocationService.insertOtvConf(input);
	}
	
	/**
	 * TV App. 위치 수정
	 * @param authToken				로그인 인증 토큰(svcCode)
	 * @param jsonObject				TV App 정보 (said, type, x, y)
	 * @return						void
	 * @throws Exception
	 */
	@RequestMapping(value={"/app/otv/config"}, method={RequestMethod.PUT}, produces=PRODUCES_JSON)
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public void putOtvConf(
			  @RequestHeader(value="authToken") String token, 
			  @RequestBody Map<String, Object> input) throws Exception {
		otvLocationService.updateOtvConf(token, input);
	}

	/**
	 * TV App. 위치 삭제
	 * @param authToken				로그인 인증 토큰(svcCode)
	 * @param queryString				TV App 정보 (said)
	 * @return						void
	 * @throws Exception
	 */
	@RequestMapping(value={"/app/otv/config"}, method={RequestMethod.DELETE}, produces=PRODUCES_JSON)
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public void deleteOtvConf(
			  @RequestHeader(value="authToken") String token, 
			  @RequestParam Map<String, Object> input) throws Exception {
		otvLocationService.deleteOtvConf(token, input);
	}

}
