package com.kt.giga.home.openapi.common;

import java.util.HashMap;

import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

@Configuration
@PropertySource({
	"file:${hiot.properties.path}/app.${hiot.run.env:svc}.properties"
})
public class APIEnv {
	
	@Autowired
	public Environment env;
	private HashMap<String, String> envMap = new HashMap<String, String>();
	
	public String getProperty(String key) {		
		if(!envMap.containsKey(key)) {
			envMap.put(key, env.getProperty(key));
		}
		
		return envMap.get(key); 
	}
	
	public String getProperty(String key, String defaultValue) {
		if(!envMap.containsKey(key)) {
			envMap.put(key, env.getProperty(key));
		}
		
		return MapUtils.getString(envMap, key, defaultValue);
	}
	
	public int getIntProperty(String key) {
		if(!envMap.containsKey(key)) {
			envMap.put(key, env.getProperty(key));
		}
		
		return MapUtils.getIntValue(envMap, key);
	}
	
	public boolean isPropertyTrue(String key) {
		if(!envMap.containsKey(key)) {
			envMap.put(key, env.getProperty(key));
		}
		
		return BooleanUtils.toBoolean(getProperty(key));
	}
	
	public String[] splitProperty(String name, String delimiter) {
		String property = getProperty(name);
		if(StringUtils.isEmpty(property)) {
			return null;
		} else {
			return StringUtils.split(property, delimiter);
		}
	}
	
	public String[] splitProperty(String name) {
		return splitProperty(name, "|");
	}	
	
	public String getDstrCd() {
		return "001";
	}
	
	public String getSvcThemeCd() {
		return "HIT";
	}
	
	public String getServiceCode(String unitSvcCd) {
		return getDstrCd() + getSvcThemeCd() + unitSvcCd;
	}
	
	public String getLast3ByteUnitSvcCd(String unitSvcCd) {
		return StringUtils.substring(unitSvcCd, StringUtils.length(unitSvcCd) - 3);
	}
}
