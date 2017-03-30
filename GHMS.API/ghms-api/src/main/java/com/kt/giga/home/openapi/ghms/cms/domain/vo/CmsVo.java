/*******************************************************************************
 * Copyright(c) 2015 KT. All rights reserved.
 * This software is the proprietary information of KT.
 *******************************************************************************/
package com.kt.giga.home.openapi.ghms.cms.domain.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * 
 * @author dahye.kim (dahye.kim@ceinside.co.kr)
 * @since 2015. 2. 6.
 */
@JsonInclude(Include.NON_EMPTY)
public class CmsVo {
    
    /* APP , 펌웨어 버전 */
    /** 최신 버전 */
    private String verNum; 
    /** 버전 설명 */
    private String verMemo; 
    /** 업데이트 필수여부 YN */
    private String verMandatoryYn; 
    /** 모델명 */
    private String modelName; 
    
    /* 코치가이드, 웰컴가이드, 디바이스팁 */
    /** 제목 */
    private String title; 
    /** 가이드 위치 */
    private String position; 
    /** 가이드 이미지 url 1 */
    private String imgUrl_1; 
    /** 가이드 이미지 url 2 */
    private String imgUrl_2;
    /** 가이드 이미지 url 3 */
    private String imgUrl_3;
    
    
    /* 가이드 DB 칼럼 */
    /** 가이드 제목 */
    private String fd_title;
    /** 가이드 버전 정보 */
    private String fd_ver_num;
    /** 가이드 노출 위치 */
    private String fd_position_code;
    /** 가이드 이미지 url 1 */
    private String fd_file_path_1;
    /** 가이드 이미지 url 2 */
    private String fd_file_path_2;
    /** 가이드 이미지 url 3 */
    private String fd_file_path_3;
    
    /* App 버전 DB 칼럼 */
    /** App 버전 설명 */
    private String  fd_ver_memo; 
    /** 업데이트 필수여부YN */
    private String  fd_mandatory_yn; 
    
    /* 펌웨어 버전 DB 칼럼 */
    /** 펌웨어 버전 설명 */
    private String fd_ver_title;
    /** 모델명 */
    private String fd_model_name;
    
    
    
    /**
     * @return TODO
     */
    public String getVerNum() {
        return verNum;
    }
    /**
     * @return TODO
     */
    public String getVerMemo() {
        return verMemo;
    }
    /**
     * @return TODO
     */
    public String getVerMandatoryYn() {
        return verMandatoryYn;
    }
    /**
     * @return TODO
     */
    public String getModelName() {
        return modelName;
    }
    /**
     * @return TODO
     */
    public String getTitle() {
        return title;
    }
    /**
     * @return TODO
     */
    public String getPosition() {
        return position;
    }
    /**
     * @return TODO
     */
    public String getImgUrl_1() {
        return imgUrl_1;
    }
    /**
     * @return TODO
     */
    public String getImgUrl_2() {
        return imgUrl_2;
    }
    /**
     * @return TODO
     */
    public String getImgUrl_3() {
        return imgUrl_3;
    }
    /**
     * @return TODO
     */
    public String getFd_title() {
        return fd_title;
    }
    /**
     * @return TODO
     */
    public String getFd_ver_num() {
        return fd_ver_num;
    }
    /**
     * @return TODO
     */
    public String getFd_position_code() {
        return fd_position_code;
    }
    /**
     * @return TODO
     */
    public String getFd_file_path_1() {
        return fd_file_path_1;
    }
    /**
     * @return TODO
     */
    public String getFd_file_path_2() {
        return fd_file_path_2;
    }
    /**
     * @return TODO
     */
    public String getFd_file_path_3() {
        return fd_file_path_3;
    }
    /**
     * @param verNum TODO
     */
    public void setVerNum(String verNum) {
        this.verNum = verNum;
    }
    /**
     * @param verMemo TODO
     */
    public void setVerMemo(String verMemo) {
        this.verMemo = verMemo;
    }
    /**
     * @param verMandatoryYn TODO
     */
    public void setVerMandatoryYn(String verMandatoryYn) {
        this.verMandatoryYn = verMandatoryYn;
    }
    /**
     * @param modelName TODO
     */
    public void setModelName(String modelName) {
        this.modelName = modelName;
    }
    /**
     * @param title TODO
     */
    public void setTitle(String title) {
        this.title = title;
    }
    /**
     * @param position TODO
     */
    public void setPosition(String position) {
        this.position = position;
    }
    /**
     * @param imgUrl_1 TODO
     */
    public void setImgUrl_1(String imgUrl_1) {
        this.imgUrl_1 = imgUrl_1;
    }
    /**
     * @param imgUrl_2 TODO
     */
    public void setImgUrl_2(String imgUrl_2) {
        this.imgUrl_2 = imgUrl_2;
    }
    /**
     * @param imgUrl_3 TODO
     */
    public void setImgUrl_3(String imgUrl_3) {
        this.imgUrl_3 = imgUrl_3;
    }
    /**
     * @param fd_title TODO
     */
    public void setFd_title(String fd_title) {
        this.fd_title = fd_title;
    }
    /**
     * @param fd_ver_num TODO
     */
    public void setFd_ver_num(String fd_ver_num) {
        this.fd_ver_num = fd_ver_num;
    }
    /**
     * @param fd_position_code TODO
     */
    public void setFd_position_code(String fd_position_code) {
        this.fd_position_code = fd_position_code;
    }
    /**
     * @param fd_file_path_1 TODO
     */
    public void setFd_file_path_1(String fd_file_path_1) {
        this.fd_file_path_1 = fd_file_path_1;
    }
    /**
     * @param fd_file_path_2 TODO
     */
    public void setFd_file_path_2(String fd_file_path_2) {
        this.fd_file_path_2 = fd_file_path_2;
    }
    /**
     * @param fd_file_path_3 TODO
     */
    public void setFd_file_path_3(String fd_file_path_3) {
        this.fd_file_path_3 = fd_file_path_3;
    }
    /**
     * @return TODO
     */
    public String getFd_ver_memo() {
        return fd_ver_memo;
    }
    /**
     * @return TODO
     */
    public String getFd_mandatory_yn() {
        return fd_mandatory_yn;
    }
    /**
     * @param fd_ver_memo TODO
     */
    public void setFd_ver_memo(String fd_ver_memo) {
        this.fd_ver_memo = fd_ver_memo;
    }
    /**
     * @param fd_mandatory_yn TODO
     */
    public void setFd_mandatory_yn(String fd_mandatory_yn) {
        this.fd_mandatory_yn = fd_mandatory_yn;
    }
    /**
     * @return TODO
     */
    public String getFd_ver_title() {
        return fd_ver_title;
    }
    /**
     * @return TODO
     */
    public String getFd_model_name() {
        return fd_model_name;
    }
    /**
     * @param fd_ver_title TODO
     */
    public void setFd_ver_title(String fd_ver_title) {
        this.fd_ver_title = fd_ver_title;
    }
    /**
     * @param fd_model_name TODO
     */
    public void setFd_model_name(String fd_model_name) {
        this.fd_model_name = fd_model_name;
    }
    
    
}
