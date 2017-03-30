package com.kt.giga.home.openapi.health.exercise.controller;

import java.util.*;

import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.*;

import com.kt.giga.home.openapi.health.exercise.service.*;

/**
 * EC 장치관리 컨트롤러
 * @author 김용성
 *
 */
@Controller
public class ExerciseController {

	private static final String PRODUCES_JSON = "application/json; charset=UTF-8";
	
	@Autowired
	private ExerciseService exerciseService;
	
	// EC 장치 조회
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value="/ec/auth", method=RequestMethod.GET, produces=PRODUCES_JSON)		
	public Map<String, Object> get(@RequestHeader(value="authToken", required=true) String authToken) throws Exception {
		
		Map<String, Object> exercise = new HashMap<>();
		exercise.put("crud"	, "select");
		exerciseService.update(authToken, exercise);
		
		exercise = (Map)exercise.get("result");
		
		return exercise;
	}	
	
	// EC 장치 등록
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value="/ec/auth", method=RequestMethod.POST, produces=PRODUCES_JSON)
	public Map<String, Object> register(@RequestHeader(value="authToken", required=true) String authToken, @RequestBody Map<String, Object> exercise) throws Exception {
		
		exercise.put("crud", "insert");
		exerciseService.update(authToken, exercise);
		
		return exercise;
	}
	
	// EC 장치 수정 (인증번호 수정)
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value="/ec/auth", method=RequestMethod.PUT, produces=PRODUCES_JSON)	
	public Map<String, Object> modify(@RequestHeader(value="authToken", required=true) String authToken, @RequestBody Map<String, Object> exercise) throws Exception {

		exercise.put("crud", "update");
		exerciseService.update(authToken, exercise);

		return exercise;
	}
	
	// EC 장치 삭제
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value="/ec/auth", method=RequestMethod.DELETE, produces=PRODUCES_JSON)	
	public Map<String, Object> remove(@RequestHeader(value="authToken", required=true) String authToken) throws Exception {
		
		Map<String, Object> exercise = new HashMap<>();
		exercise.put("crud", "delete");
		
		exerciseService.update(authToken, exercise);
		return exercise;
	}	
	
}
