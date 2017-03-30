package com.kt.giga.home.openapi.util;

import java.util.*;

import org.springframework.http.HttpStatus;

import com.kt.giga.home.openapi.service.*;

/**
 * 유효성 검증 클래스
 * @author 김용성
 */
public class Validation {
	
	/**
	 * 요청 파라메터(Map) 검증
	 * @param target Map으로 구성된 파라메터
	 * @param parameters 검증 파라메터들
	 * @throws APIException
	 * @throws Exception
	 */
	public static void checkParameter(Map<String, Object> target, String ... parameters) throws APIException, Exception {
		
		for(String param : parameters) {
			if(!target.containsKey(param))
				throw new APIException("필수 파라메터 부족(" + param + ")", HttpStatus.BAD_REQUEST);
		}
	}
	
}
