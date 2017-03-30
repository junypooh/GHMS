/*******************************************************************************
 * Copyright(c) 2015 KT. All rights reserved.
 * This software is the proprietary information of KT.
 *******************************************************************************/
package com.kt.giga.home.openapi.ghms.util.data;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.swing.ImageIcon;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.kt.giga.home.openapi.ghms.util.date.DateTimeUtil;
import com.kt.giga.home.openapi.ghms.util.properties.APIEnv;

/**
 * 
 * @author junypooh ( kyungjun.park@ceinside.co.kr )
 * @since 2015. 2. 16.
 */
@Component
public class DataUtil {

	/** Logger */
	private Logger log = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private APIEnv apiEnv;

    /**
     * 이미지 파일 업로드
     * @param request
     * @param filePath
     * @param uploadFile
     * @return Map<String, Object>
     * @throws Exception
     */
    public Map<String, Object> uploadImgFile(String filePath, CommonsMultipartFile uploadFile) throws Exception{
        return uploadImgFile(filePath, uploadFile, null);
    }

    /**
     * 이미지 파일 업로드
     * @param request
     * @param filePath
     * @param uploadFile
     * @param prefix
     * @return Map<String, Object>
     * @throws Exception
     */
    public Map<String, Object> uploadImgFile(String filePath, CommonsMultipartFile uploadFile, String prefix) throws Exception{
        return uploadImgFile(filePath, uploadFile, prefix, null);
    }

    /**
     * 이미지 파일 업로드
     * @param request
     * @param filePath
     * @param uploadFile
     * @param prefix
     * @param makeFileName
     * @return Map<String, Object>
     * @throws Exception
     */
    public Map<String, Object> uploadImgFile(String filePath, CommonsMultipartFile uploadFile, String prefix, String makeFileName) throws Exception{

    	/*
    	 * [key]
    	 * ORIGIN_FILE_NM	: 원본파일명
    	 * STORE_FILE_NM	: 저장파일명
    	 * FILE_SIZE_VAL	: 파일사이즈값
    	 * CONTS_TYPE_VAL	: 컨텐츠타입값
    	 * FILE_PATH_VAL	: 파일 저장 위치
    	 */
    	Map<String, Object> map = null;
    	
        String uploadFimeName = "";
        String dirDate = DateTimeUtil.getDateByPattern("yyyyMMdd");
//        String uploadFilePath = apiEnv.getProperty("system.context.path") + filePath + "/" + dirDate;
        String uploadFilePath = filePath + "/" + dirDate;


        File dir = new File(uploadFilePath);

        //디렉토리 경로가 존재하지 않는다면 생성
        if ( !dir.exists() ) {
            dir.mkdirs();
        }
/*
        String localYn = AbitsProperty.getLocalYn();
        if(!"Y".equals(localYn)){
            // 폴더 권한 설정
            String cmd = "chmod 777 " + uploadFilePath;

            Runtime rt = Runtime.getRuntime();
            Process prc = rt.exec(cmd);
            prc.waitFor();
            // 폴더 권한 설정
        }
*/
        if(uploadFile != null && !uploadFile.isEmpty()){
            String saveFilePath;
            RandomGUID randomGUI = new RandomGUID();
            
            // 생성하고자하는 파일명이 없을 때만 랜덤파일명 적용
            if(makeFileName == null || "".equals(makeFileName)) {
                makeFileName = randomGUI.toString();
            }

            uploadFimeName = uploadFile.getOriginalFilename();
            
            if(prefix == null || "".equals(prefix)) {
                saveFilePath = uploadFilePath + "/" + makeFileName + uploadFimeName.substring(uploadFimeName.lastIndexOf("."), uploadFimeName.length());   
            } else {
                saveFilePath = uploadFilePath + "/" + prefix + makeFileName + uploadFimeName.substring(uploadFimeName.lastIndexOf("."), uploadFimeName.length());   
            }   

            File saveFile = new File(saveFilePath);

            uploadFile.transferTo(saveFile);//파일을 생성한다.
            
            log.debug("uploadFile.getContentType() {} " + uploadFile.getContentType());

            //파일 타입이 이미지가 아닌 경우 Exception 발생
            if(!uploadFile.getContentType().startsWith("image") && !uploadFile.getContentType().startsWith("jpg")){
                if(!isImageFile(saveFilePath)){ //모바일기기에서 이미지인데도 오류나는 부분이 있어서 여기서 한번 이미지파일 포맷이 정말 맞는지 체크하자.
                    deleteFile(saveFilePath);
                    throw new Exception("WRONG Image Content Type");
                }
            }

            //2013.09.23 확장자가 아래의 4개지 경우를 벗어나는 경우 Exception 발생
            String ext = uploadFimeName.substring(uploadFimeName.lastIndexOf(".") + 1, uploadFimeName.length());
            ext = ext.toLowerCase();
            
            log.debug("uploadFile Extention {} " + ext);

            if(!ext.equals("jpg") && !ext.equals("jpeg") && !ext.equals("gif")  && !ext.equals("png")){
                deleteFile(saveFilePath);
                throw new Exception("WRONG Image Extention Type");
            }

            saveFile = null;
            
            map = new HashMap<String, Object>();
        	// 원본파일명
        	map.put("ORIGIN_FILE_NM", uploadFimeName);
        	// 파일 저장 위치 + 저장파일명
        	map.put("STORE_FILE_NM", saveFilePath);
        	// 파일사이즈값
        	map.put("FILE_SIZE_VAL", uploadFile.getSize());
        	// 컨텐츠타입값
        	map.put("CONTS_TYPE_VAL", uploadFile.getContentType());
        }

//        return fileName;
        return map;
    }

    /**
     * 파일 삭제
     * @param request
     * @param filePath
     * @param originFime
     * @return
     * @throws Exception
     */
    public void deleteFile(String originFimeName) throws Exception{

        if(originFimeName != null && !"".equals(originFimeName)){
            File deleteFile = new File(originFimeName);

            if(deleteFile.exists()){
                deleteFile.delete();
            }
        }
    }

    /**
     * 해당 파일이 이미지 인지 체크한다.
     * @param filePath
     * @return
     * @throws Exception
     */
    public boolean isImageFile(String filePath) throws Exception{
        File file = new File(filePath);

        ImageIcon ii = new ImageIcon(file.getPath());

        if( ii.getIconWidth() == -1 && ii.getIconHeight() == -1 ) {
            return false;
        }
        return true;
    }

}
