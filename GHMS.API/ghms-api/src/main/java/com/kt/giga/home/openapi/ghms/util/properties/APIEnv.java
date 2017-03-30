package com.kt.giga.home.openapi.ghms.util.properties;

import java.util.HashMap;

import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

import com.kt.giga.home.openapi.ghms.common.GHmsConstants.CommonConstants;

@Configuration
@PropertySource({
//	"classpath:/properties/app.${hiot.run.env:kth}.properties"	// 공통
	"file:${ghms.hiot.properties.path}/app.${hiot.run.env:kth}.properties"
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
		return CommonConstants.DSTR_CD;
	}
	
	public String getSvcThemeCd() {
		return CommonConstants.SVC_THEME_CD;
	}
	
	public String getServiceCode(String unitSvcCd) {
		return getDstrCd() + getSvcThemeCd() + unitSvcCd;
	}
	
	public String getLast3ByteUnitSvcCd(String unitSvcCd) {
		return StringUtils.substring(unitSvcCd, StringUtils.length(unitSvcCd) - 3);
	}
}
