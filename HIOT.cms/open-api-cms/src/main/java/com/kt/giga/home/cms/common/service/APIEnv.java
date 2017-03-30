package com.kt.giga.home.cms.common.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

@Configuration
@PropertySource({"classpath:app.${spring.profiles.active:kth}.properties"})
public class APIEnv {
	
	@Autowired
	public Environment env;
	
	public String getProperty(String key) {
		return env.getProperty(key);
	}
	
	public String getProperty(String key, String defaultValue) {
		return env.getProperty(key, defaultValue);
	}
	
	public int getIntProperty(String key) {
		return env.getProperty(key, Integer.class);
	}
	
	public String getDstrCd() {
		return "001";
	}
	
	public String getSvcThemeCd() {
		return "HIT";
	}
}
