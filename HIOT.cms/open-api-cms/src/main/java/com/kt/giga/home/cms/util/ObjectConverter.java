package com.kt.giga.home.cms.util;

import org.json.simple.*;
import org.json.simple.parser.*;

import com.fasterxml.jackson.core.*;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.annotation.JsonInclude.*;

public class ObjectConverter {
	
	public static String toJSONString(Object obj) throws JsonProcessingException {
		return ObjectConverter.toJSONString(obj, Include.NON_NULL);
	}
	
	public static String toJSONString(Object obj, Include include) throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		mapper.setSerializationInclusion(include);
		return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(obj);		
	}
	
	public static JSONObject toJSON(Object obj) throws JsonProcessingException, ParseException {
		return ObjectConverter.toJSON(obj, Include.NON_NULL);
	}
	
	public static JSONObject toJSON(Object obj, Include include) throws JsonProcessingException, ParseException {
		
		ObjectMapper mapper = new ObjectMapper();
		mapper.setSerializationInclusion(include);
		
		String jsonStr = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(obj);
		jsonStr = jsonStr.replaceAll("\\r", "").replaceAll("\\n", "").replaceAll("\\t", "");
		
		JSONObject json = (JSONObject)new JSONParser().parse(jsonStr);
		
		return json;
	}
	
	public static JSONObject toJSON(String jsonStr) throws JsonProcessingException, ParseException {
		return ObjectConverter.toJSON(jsonStr, Include.NON_NULL);
	}
	
	public static JSONObject toJSON(String jsonStr, Include include) throws JsonProcessingException, ParseException {
		
		JSONObject orgJson = (JSONObject)new JSONParser().parse(jsonStr);
		ObjectMapper mapper = new ObjectMapper();
		mapper.setSerializationInclusion(include);
		
		jsonStr = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(orgJson);		
		
		JSONObject newJson = (JSONObject)new JSONParser().parse(jsonStr);
		
		return newJson;		
	}

}
