/*******************************************************************************
 * Copyright(c) 2015 KT. All rights reserved.
 * This software is the proprietary information of KT.
 *******************************************************************************/
package com.kt.giga.home.openapi.ghms.common.domain.vo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * APP 암호화 키 전달 VO
 * @author junypooh ( kyungjun.park@ceinside.co.kr )
 * @since 2015. 5. 7.
 */
@Component
public class AppEncryptorVo {
	
	@Value("#{system['encryptor.key']}")
	private String key;

	/**
	 * @return TODO
	 */
	public String getKey() {
		return key;
	}

	/**
	 * @param key TODO
	 */
	public void setKey(String key) {
		this.key = key;
	}

}
