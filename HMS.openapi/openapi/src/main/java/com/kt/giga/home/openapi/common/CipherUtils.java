package com.kt.giga.home.openapi.common;

import java.security.MessageDigest;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang3.StringUtils;

/**
 * 암호화 처리 유틸 클래스
 * 
 * @author 
 *
 */
public class CipherUtils {

	private final static String CHARSET = "UTF-8";
	
	private static SecureRandom secureRandom = new SecureRandom();
	
	public static byte[] generateSecureRandom(int size) {
		byte[] bytes = new byte[size];
		secureRandom.nextBytes(bytes);
		return bytes;
	}
	
	public static String toBase64(byte[] bytes) throws Exception {
		return new String(Base64.encodeBase64(bytes), CHARSET);
	}
	
	public static String toHex(byte[] bytes) {
		return Hex.encodeHexString(bytes);
	}
	
	public static byte[] fromBase64(String base64) throws Exception {
		return Base64.decodeBase64(base64.getBytes(CHARSET));
	}
	
	public static byte[] fromHex(String hex) throws Exception {
		return Hex.decodeHex(hex.toCharArray());
	}
	
	public static byte[] encrypt(String alg, String trf, byte[] keyBytes, byte[] ivBytes, String plainText) throws Exception {

		byte[] plainTextBytes = plainText.getBytes(CHARSET); 
		
		SecretKeySpec skeySpec = new SecretKeySpec(keyBytes, alg);
		IvParameterSpec param = new IvParameterSpec(ivBytes);
		
		Cipher cipher = Cipher.getInstance(trf);
		cipher.init(Cipher.ENCRYPT_MODE, skeySpec, param);			

		return cipher.doFinal(plainTextBytes);
	}
	
	public static byte[] encryptAESCTRNoPadding(byte[] keyBytes, byte[] ivBytes, String plainText) throws Exception {
		return encrypt("AES", "AES/CTR/NoPadding", keyBytes, ivBytes, plainText);
	}
	
	public static String encryptAESCTRNoPaddingBase64(byte[] keyBytes, byte[] ivBytes, String plainText) throws Exception {
		return toBase64(encryptAESCTRNoPadding(keyBytes, ivBytes, plainText));
	}
	
	public static String encryptAESCTRNoPaddingHex(byte[] keyBytes, byte[] ivBytes, String plainText) throws Exception {
		return toHex(encryptAESCTRNoPadding(keyBytes, ivBytes, plainText));
	}	
	
	public static String decrypt(String alg, String trf, byte[] keyBytes, byte[] ivBytes, byte[] cipherTextBytes) throws Exception	{
		
		SecretKeySpec skeySpec = new SecretKeySpec(keyBytes, alg);
		IvParameterSpec param = new IvParameterSpec(ivBytes);
		
		Cipher cipher = Cipher.getInstance(trf);
		cipher.init(Cipher.DECRYPT_MODE, skeySpec, param);			
		
		return new String(cipher.doFinal(cipherTextBytes), CHARSET);
	}	
	
	public static String decryptAESCTRNoPadding(byte[] keyBytes, byte[] ivBytes, byte[] cipherTextBytes) throws Exception {
		return decrypt("AES", "AES/CTR/NoPadding", keyBytes, ivBytes, cipherTextBytes);
	}
	
	public static String decryptAESCTRNoPaddingBase64(byte[] keyBytes, byte[] ivBytes, String cipherTextBase64) throws Exception {
		return decryptAESCTRNoPadding(keyBytes, ivBytes, fromBase64(cipherTextBase64));
	}
	
	public static String decryptAESCTRNoPaddingHex(byte[] keyBytes, byte[] ivBytes, String cipherTextHex) throws Exception {
		return decryptAESCTRNoPadding(keyBytes, ivBytes, fromHex(cipherTextHex));
	}	

	public static String md5(String plainText)throws Exception {
		if(plainText == null)
			return null;
		
		MessageDigest md5 = MessageDigest.getInstance("MD5");
		byte[] hashValue = md5.digest(plainText.getBytes(CHARSET));
		
		if(hashValue == null)
			return null;
		
		return toHex(hashValue);
	}
	
	public static String getMaskingTelNo(String telNo) {
		int len = StringUtils.length(telNo); 
		return StringUtils.overlay(telNo, StringUtils.repeat("*", len - 7), 3, len - 4);
	}
}
