package com.kt.giga.home.openapi.hcam.controller;

import com.kt.giga.home.openapi.hcam.service.DummyService;
import org.apache.commons.lang.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 초기실행, 인증/로그인, 토큰갱신등 회원관련 처리 컨트롤러
 * 
 * @author 
 *
 */
@Controller("HCam.DummyController")
public class DummyController {

	@Autowired
	private DummyService dummyService;

	@RequestMapping(value={"/dummy"}, method=RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public void dummy(@RequestParam(value="index") String index, @RequestParam(value="count") String count) throws Exception {
        dummyService.dummy(NumberUtils.toInt(index), NumberUtils.toInt(count));
    }
	
	@RequestMapping(value={"/dummyuser"}, method=RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public void dummyUser(@RequestParam(value="index") String index, @RequestParam(value="count") String count) throws Exception {
        dummyService.dummyUser(NumberUtils.toInt(index), NumberUtils.toInt(count));
    }
	
	@RequestMapping(value={"/kpns"}, method=RequestMethod.POST)
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public String kpns(@RequestBody Map<String, Object> map) throws Exception {
		dummyService.kpns(map);
		return "ok";
	}
	
	@RequestMapping(value={"/inituser"}, method=RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public String initUser(@RequestParam(value="id") String userId) throws Exception {
		dummyService.initUser(userId);
		return "ok";
	}
	
	@RequestMapping(value={"/dummy/relay"}, method=RequestMethod.POST)
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public String relay(@RequestBody Map<String, Object> map) throws Exception {
		return dummyService.relay(map);
	}
}
