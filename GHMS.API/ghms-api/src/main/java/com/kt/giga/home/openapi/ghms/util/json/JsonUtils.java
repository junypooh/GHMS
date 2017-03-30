/*******************************************************************************
 * Copyright(c) 2015 KT. All rights reserved.
 * This software is the proprietary information of KT.
 *******************************************************************************/
package com.kt.giga.home.openapi.ghms.util.json;

import java.lang.reflect.Type;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/**
 * 
 * @author junypooh ( kyungjun.park@ceinside.co.kr )
 * @since 2015. 4. 3.
 */
public class JsonUtils {
	
	private Logger log = LoggerFactory.getLogger(getClass());
	
	private static Gson gson = null;

	static
	{
		GsonBuilder gsonBuilder = new GsonBuilder().setPrettyPrinting();
		gson = gsonBuilder.create();
	}
	
	/**
	 * JSON Serialize 메쏘드
	 *
	 * @param o					오브젝트
	 * @return					JSON 스트링
	 */
	public static String toJson(Object o) {
		return gson.toJson(o);
	}

	/**
	 * JSON Deserialize 메쏘드
	 *
	 * @param json				JSON 스트링
	 * @param typeOfT			오브젝트 타입
	 * @return					오브젝트
	 */
	public static <T> T fromJson(String json, Type typeOfT) {

		T obj = null;
		try {
			obj = gson.fromJson(json, typeOfT);
		} catch(Exception e) {
			e.printStackTrace();
		}

		return obj;
	}

	/**
	 * JSON 스트링의 JsonObject 변환 메쏘드
	 *
	 * @param json				JSON 스트링
	 * @return					JsonObject
	 */
	public static JsonObject fromJson(String json) {

		JsonObject obj = null;
		try {
			obj = new JsonParser().parse(json).getAsJsonObject();
		} catch(Exception e) {
			// ignore case
		}

		return obj;
	}
	
	/**
	 * json 데이터를 pretty 포맷의 문자열을 반환한다.
	 *
	 * @param json				JSON 스트링
	 * @return					String
	 */
	public static String toPrettyJson(Object o) {
		if(o instanceof String) {
			return gson.toJson(fromJson((String)o));
		} else {
			return gson.toJson(o);
		}
	}
}
