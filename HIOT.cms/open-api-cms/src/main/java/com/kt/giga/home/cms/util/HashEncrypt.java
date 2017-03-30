package com.kt.giga.home.cms.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HashEncrypt 
{
    MessageDigest md;
    String strENCData = "";

    public HashEncrypt(String EncMthd, String strData) 
    {
        this.encrypt(EncMthd, strData);
    }
    
    // 해쉬타입이 지정되지 않을경우 SHA-256 으로..
    public HashEncrypt(String strData) 
    {
        this.encrypt("SHA-256", strData);
    }
    
    public void encrypt(String EncMthd, String strData) 
    {
    	try  
    	{
    		MessageDigest md = MessageDigest.getInstance(EncMthd);
    		byte[] bytData = strData.getBytes();
    		md.update(bytData);
    		byte[] digest = md.digest();
    		for(int i =0;i<digest.length;i++) 
    		{
    			strENCData = strENCData + Integer.toHexString(digest[i] & 0xFF).toUpperCase();
    		}
       }
       catch(NoSuchAlgorithmException e)
       {
         System.out.print(e);
       }
    }

    public String getEncryptData(){return strENCData;}
}
