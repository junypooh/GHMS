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
 * @since 2015. 2. 10.
 */
@JsonInclude(Include.NON_EMPTY)
public class CodeVo {
    
    private String  pk_code;                // 기초코드
    private String  fd_up_code;             // 상위코드
    private String  fd_name;                // 코드이름
    private String  fd_use_yn;              // 코드 사용여부 YN
    private String  fd_memo;                // 코드 설명
    
    /**
     * @return TODO
     */
    public String getPk_code() {
        return pk_code;
    }
    /**
     * @return TODO
     */
    public String getFd_up_code() {
        return fd_up_code;
    }
    /**
     * @return TODO
     */
    public String getFd_name() {
        return fd_name;
    }
    /**
     * @return TODO
     */
    public String getFd_use_yn() {
        return fd_use_yn;
    }
    /**
     * @return TODO
     */
    public String getFd_memo() {
        return fd_memo;
    }
    /**
     * @param pk_code TODO
     */
    public void setPk_code(String pk_code) {
        this.pk_code = pk_code;
    }
    /**
     * @param fd_up_code TODO
     */
    public void setFd_up_code(String fd_up_code) {
        this.fd_up_code = fd_up_code;
    }
    /**
     * @param fd_name TODO
     */
    public void setFd_name(String fd_name) {
        this.fd_name = fd_name;
    }
    /**
     * @param fd_use_yn TODO
     */
    public void setFd_use_yn(String fd_use_yn) {
        this.fd_use_yn = fd_use_yn;
    }
    /**
     * @param fd_memo TODO
     */
    public void setFd_memo(String fd_memo) {
        this.fd_memo = fd_memo;
    }
    
    

}
