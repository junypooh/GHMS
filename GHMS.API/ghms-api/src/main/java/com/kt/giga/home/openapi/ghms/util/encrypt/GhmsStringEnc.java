/*******************************************************************************
 * Copyright(c) 2015 KT. All rights reserved.
 * This software is the proprietary information of KT.
 *******************************************************************************/
package com.kt.giga.home.openapi.ghms.util.encrypt;

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;

/**
 * 프로퍼티 암호화 String 유틸
 * @author junypooh ( kyungjun.park@ceinside.co.kr )
 * @since 2015. 5. 7.
 */
public class GhmsStringEnc {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		StandardPBEStringEncryptor standardPBEStringEncryptor = new StandardPBEStringEncryptor();
		standardPBEStringEncryptor.setAlgorithm("PBEWithMD5AndDES"); // application-context.xml > encryption:string-encryptor > algorithm 값
		standardPBEStringEncryptor.setPassword("hiot-ghms!@#$"); // application-context.xml > encryption:string-encryptor > password 값
		String encodedPass = standardPBEStringEncryptor.encrypt("fe8025947de7cd71"); // 암호화 하고자 하는 plan Text
		System.out.println("Encrypted Password for admin is : "+encodedPass); // 생성할때 마다 틀린 값이 나옴.
	}

}
