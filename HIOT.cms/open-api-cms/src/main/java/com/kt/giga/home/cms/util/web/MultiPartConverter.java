package com.kt.giga.home.cms.util.web;

import java.io.*;

import org.springframework.web.multipart.*;

/**
 * <p>Spring MultipartFile 변환클래스</p>
 */
public class MultiPartConverter 
{
	/**
	 * MultipartFile 을 java.io.File 로 변환한다. 
	 * @param multipart Spring MultipartFile
	 * @return java.io.File
	 * @throws IllegalStateException
	 * @throws IOException
	 */
	public static File toFile(MultipartFile multipart) throws IllegalStateException, IOException 
	{
        File tmpFile = new File(System.getProperty("java.io.tmpdir") + System.getProperty("file.separator") + multipart.getOriginalFilename());
	    multipart.transferTo(tmpFile);
	    return tmpFile;
	}	
}
