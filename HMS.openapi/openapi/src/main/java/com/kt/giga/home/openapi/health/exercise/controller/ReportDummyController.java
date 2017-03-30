package com.kt.giga.home.openapi.health.exercise.controller;

import java.util.HashMap;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * EC report dummy 컨트롤러
 * 사용되지 않음. dummy로 처리 
 * @author crazywings@paran.com
 *
 */
@Controller
public class ReportDummyController {

	/**
	 * API 리턴 미디어 타입
	 */
	private static final String PRODUCES_JSON = "application/json; charset=UTF-8";

	/**
	 * 내부 인터널 API - retv (조회 요청에 대한 결과 Report) 매핑 메쏘드
	 *
	 * @param resultJson			조회 요청 결과 JSON
	 * @return						처리 결과 맵
	 * 								code	: 처리 결과 코드
	 * 								msg		: 처리 결과 메시지
	 * @throws Exception
	 */
	@RequestMapping(value={"/dummy/intn/initarprt/spotdev/retv"}, method = {RequestMethod.POST}, produces=PRODUCES_JSON)
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public HashMap<String, String> setDeviceReadResultDummy(@RequestBody String resultJson) throws Exception {
		HashMap<String, String> dummyMap = new HashMap<>();
		dummyMap.put("code", "204");
		dummyMap.put("msg", "dummy");		
		return dummyMap;
	}

	/**
	 * 내부 인터널 API - rtimeContl (실시간 제어에 대한 결과 Report) 매핑 메쏘드
	 *
	 * @param resultJson			실시간 제어 결과 JSON
	 * @return						처리 결과 맵
	 * 									code	: 처리 결과 코드
	 * 									msg		: 처리 결과 메시지
	 * @throws Exception
	 */
	@RequestMapping(value={"/dummy/intn/contlrprt/spotdev/rtimeContl"}, method = {RequestMethod.POST}, produces=PRODUCES_JSON)
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public HashMap<String, String> setRTimeControlResultDummy(@RequestBody String resultJson) throws Exception {
		HashMap<String, String> dummyMap = new HashMap<>();
		dummyMap.put("code", "204");
		dummyMap.put("msg", "dummy");
		return dummyMap;
	}

	/**
	 * 내부 인터널 API - setupChg (설정 제어에 대한 결과 Report) 매핑 메쏘드
	 *
	 * @param resultJson			설정 제어 결과 JSON
	 * @return						처리 결과 맵
	 * 									code	: 처리 결과 코드
	 * 									msg		: 처리 결과 메시지
	 * @throws Exception
	 */
	@RequestMapping(value={"/dummy/intn/contlrprt/spotdev/setupChg"}, method = {RequestMethod.POST}, produces=PRODUCES_JSON)
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public HashMap<String, String> setSetupControlResultDummy(@RequestBody String resultJson) throws Exception {
		HashMap<String, String> dummyMap = new HashMap<>();
		dummyMap.put("code", "204");
		dummyMap.put("msg", "dummy");
		return dummyMap;
	}

	/**
	 * 내부 인터널 API - udateRprt (장치 및 상태 변경) 매핑 메쏘드
	 * @param updateJson			장치 상태 변경 Report
	 * @return						처리 결과 맵
	 * 									code	: 처리 결과 코드
	 * 									msg		: 처리 결과 메시지
	 * @throws Exception
	 */
	@RequestMapping(value={"/dummy/intn/inita/spotdev/udateRprt"}, method = {RequestMethod.POST}, produces=PRODUCES_JSON)
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public HashMap<String, String> setSpotDevUpdateDummy(@RequestBody String updateJson) throws Exception {
		HashMap<String, String> dummyMap = new HashMap<>();
		dummyMap.put("code", "204");
		dummyMap.put("msg", "dummy");
		return dummyMap;
	}

}