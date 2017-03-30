/*******************************************************************************
 * Copyright(c) 2015 KT. All rights reserved.
 * This software is the proprietary information of KT.
 *******************************************************************************/
package com.kt.giga.home.openapi.ghms.cms.domain.key;

/**
 * 
 * 약관 동의 Key
 * 
 * @author dahye.kim (dahye.kim@ceinside.co.kr)
 * @since 2015. 2. 10.
 */
public class TermsKey {
    
    /** 약관 목록 pk */
    private int    fk_terms; 
    /** 동의 여부 YN */
    private String fd_agree_yn; 
    /** 고객 번호 */
    private String mbr_seq; 
    /** 서비스 구분 코드 */
    private String fk_cp_code; 
    
    /** 법정대리인 동의 pk */
    private int    pk_parents_agree; 
    /** 동의 날짜 */
    private String fd_agree_date; 
    /** 법정대리인 이름 */
    private String fd_parents_name; 
    /** 법정대리인 휴대폰 번호 */
    private String fd_parents_mobile; 
    
    /**
     * @return TODO
     */
    public int getFk_terms() {
        return fk_terms;
    }
    /**
     * @return TODO
     */
    public String getFd_agree_yn() {
        return fd_agree_yn;
    }
    /**
     * @return TODO
     */
    public String getMbr_seq() {
        return mbr_seq;
    }
    /**
     * @return TODO
     */
    public String getFk_cp_code() {
        return fk_cp_code;
    }
    /**
     * @param fk_terms TODO
     */
    public void setFk_terms(int fk_terms) {
        this.fk_terms = fk_terms;
    }
    /**
     * @param fd_agree_yn TODO
     */
    public void setFd_agree_yn(String fd_agree_yn) {
        this.fd_agree_yn = fd_agree_yn;
    }
    /**
     * @param mbr_seq TODO
     */
    public void setMbr_seq(String mbr_seq) {
        this.mbr_seq = mbr_seq;
    }
    /**
     * @param fk_cp_code TODO
     */
    public void setFk_cp_code(String fk_cp_code) {
        this.fk_cp_code = fk_cp_code;
    }
    /**
     * @return TODO
     */
    public int getPk_parents_agree() {
        return pk_parents_agree;
    }
    /**
     * @return TODO
     */
    public String getFd_agree_date() {
        return fd_agree_date;
    }
    /**
     * @return TODO
     */
    public String getFd_parents_name() {
        return fd_parents_name;
    }
    /**
     * @return TODO
     */
    public String getFd_parents_mobile() {
        return fd_parents_mobile;
    }
    /**
     * @param pk_parents_agree TODO
     */
    public void setPk_parents_agree(int pk_parents_agree) {
        this.pk_parents_agree = pk_parents_agree;
    }
    /**
     * @param fd_agree_date TODO
     */
    public void setFd_agree_date(String fd_agree_date) {
        this.fd_agree_date = fd_agree_date;
    }
    /**
     * @param fd_parents_name TODO
     */
    public void setFd_parents_name(String fd_parents_name) {
        this.fd_parents_name = fd_parents_name;
    }
    /**
     * @param fd_parents_mobile TODO
     */
    public void setFd_parents_mobile(String fd_parents_mobile) {
        this.fd_parents_mobile = fd_parents_mobile;
    }
    
   
    
    
    

}
