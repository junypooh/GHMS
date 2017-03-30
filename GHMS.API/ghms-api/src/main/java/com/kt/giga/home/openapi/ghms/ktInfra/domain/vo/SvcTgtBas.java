/*******************************************************************************
 * Copyright(c) 2015 KT. All rights reserved.
 * This software is the proprietary information of KT.
 *******************************************************************************/
package com.kt.giga.home.openapi.ghms.ktInfra.domain.vo;

/**
 * 서비스대상기본 VO
 * 
 * @author dahye.kim (dahye.kim@ceinside.co.kr)
 * @since 2015. 3. 10.
 */
public class SvcTgtBas {
    
    /** 서비스대상일련번호 */
    private long     svc_tgt_seq; 
    /** 회원 일련번호 */
    private String  mbr_seq; 
    /** 회원 일련번호 변경전 */
    private Integer mbrs_seq; 
    /** 지역코드 */
    private String  dstr_cd; 
    /** 서비스테마코드 */
    private String  svc_theme_cd; 
    /** 단위서비스코드 */
    private String  unit_svc_cd; 
    /** 서비스계약아이디(청약said) */
    private String  svc_cont_id; 
    /** 서비스대상아이디 */
    private String  svc_tgt_id; 
    /** 서비스대상유형코드 */
    private String  svc_tgt_type_cd; 
    /** 서비스대상명 */
    private String  svc_tgt_nm; 
    /** 운영상태코드 */
    private String  oprt_sttus_cd; 
    /** 상세정보여부 */
    private String  dtl_info_yn; 
    /** 구우편번호 */
    private String  old_zipcd; 
    /** 구주소1 */
    private String  old_adr1; 
    /** 구주소2 */
    private String  old_adr2; 
    /** 신규건물관리번호 */
    private String  new_bldg_mgt_no; 
    /** 신규우편번호 */
    private String  new_zipcd; 
    /** 신규주소1 */
    private String  new_adr1; 
    /** 신규주소2 */
    private String  new_adr2; 
    /** 삭제여부 */
    private String  del_yn; 
    /** 생성자아이디 */
    private String  cretr_id; 
    /** 생성일시 */
    private String  cret_dt; 
    /** 수정자아이디 */
    private String  amdr_id; 
    /** 수정일시 */
    private String  amd_dt; 
    /** 조직코드 */
    private String  org_cd;
    
    
    /**
     * @return TODO
     */
    public long getSvc_tgt_seq() {
        return svc_tgt_seq;
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
    public Integer getMbrs_seq() {
        return mbrs_seq;
    }
    /**
     * @return TODO
     */
    public String getDstr_cd() {
        return dstr_cd;
    }
    /**
     * @return TODO
     */
    public String getSvc_theme_cd() {
        return svc_theme_cd;
    }
    /**
     * @return TODO
     */
    public String getUnit_svc_cd() {
        return unit_svc_cd;
    }
    /**
     * @return TODO
     */
    public String getSvc_cont_id() {
        return svc_cont_id;
    }
    /**
     * @return TODO
     */
    public String getSvc_tgt_id() {
        return svc_tgt_id;
    }
    /**
     * @return TODO
     */
    public String getSvc_tgt_type_cd() {
        return svc_tgt_type_cd;
    }
    /**
     * @return TODO
     */
    public String getSvc_tgt_nm() {
        return svc_tgt_nm;
    }
    /**
     * @return TODO
     */
    public String getOprt_sttus_cd() {
        return oprt_sttus_cd;
    }
    /**
     * @return TODO
     */
    public String getDtl_info_yn() {
        return dtl_info_yn;
    }
    /**
     * @return TODO
     */
    public String getOld_zipcd() {
        return old_zipcd;
    }
    /**
     * @return TODO
     */
    public String getOld_adr1() {
        return old_adr1;
    }
    /**
     * @return TODO
     */
    public String getOld_adr2() {
        return old_adr2;
    }
    /**
     * @return TODO
     */
    public String getNew_bldg_mgt_no() {
        return new_bldg_mgt_no;
    }
    /**
     * @return TODO
     */
    public String getNew_zipcd() {
        return new_zipcd;
    }
    /**
     * @return TODO
     */
    public String getNew_adr1() {
        return new_adr1;
    }
    /**
     * @return TODO
     */
    public String getNew_adr2() {
        return new_adr2;
    }
    /**
     * @return TODO
     */
    public String getDel_yn() {
        return del_yn;
    }
    /**
     * @return TODO
     */
    public String getCretr_id() {
        return cretr_id;
    }
    /**
     * @return TODO
     */
    public String getCret_dt() {
        return cret_dt;
    }
    /**
     * @return TODO
     */
    public String getAmdr_id() {
        return amdr_id;
    }
    /**
     * @return TODO
     */
    public String getAmd_dt() {
        return amd_dt;
    }
    /**
     * @return TODO
     */
    public String getOrg_cd() {
        return org_cd;
    }
    /**
     * @param svc_tgt_seq TODO
     */
    public void setSvc_tgt_seq(long svc_tgt_seq) {
        this.svc_tgt_seq = svc_tgt_seq;
    }
    /**
     * @param mbr_seq TODO
     */
    public void setMbr_seq(String mbr_seq) {
        this.mbr_seq = mbr_seq;
    }
    /**
     * @param mbrs_seq TODO
     */
    public void setMbrs_seq(Integer mbrs_seq) {
        this.mbrs_seq = mbrs_seq;
    }
    /**
     * @param dstr_cd TODO
     */
    public void setDstr_cd(String dstr_cd) {
        this.dstr_cd = dstr_cd;
    }
    /**
     * @param svc_theme_cd TODO
     */
    public void setSvc_theme_cd(String svc_theme_cd) {
        this.svc_theme_cd = svc_theme_cd;
    }
    /**
     * @param unit_svc_cd TODO
     */
    public void setUnit_svc_cd(String unit_svc_cd) {
        this.unit_svc_cd = unit_svc_cd;
    }
    /**
     * @param svc_cont_id TODO
     */
    public void setSvc_cont_id(String svc_cont_id) {
        this.svc_cont_id = svc_cont_id;
    }
    /**
     * @param svc_tgt_id TODO
     */
    public void setSvc_tgt_id(String svc_tgt_id) {
        this.svc_tgt_id = svc_tgt_id;
    }
    /**
     * @param svc_tgt_type_cd TODO
     */
    public void setSvc_tgt_type_cd(String svc_tgt_type_cd) {
        this.svc_tgt_type_cd = svc_tgt_type_cd;
    }
    /**
     * @param svc_tgt_nm TODO
     */
    public void setSvc_tgt_nm(String svc_tgt_nm) {
        this.svc_tgt_nm = svc_tgt_nm;
    }
    /**
     * @param oprt_sttus_cd TODO
     */
    public void setOprt_sttus_cd(String oprt_sttus_cd) {
        this.oprt_sttus_cd = oprt_sttus_cd;
    }
    /**
     * @param dtl_info_yn TODO
     */
    public void setDtl_info_yn(String dtl_info_yn) {
        this.dtl_info_yn = dtl_info_yn;
    }
    /**
     * @param old_zipcd TODO
     */
    public void setOld_zipcd(String old_zipcd) {
        this.old_zipcd = old_zipcd;
    }
    /**
     * @param old_adr1 TODO
     */
    public void setOld_adr1(String old_adr1) {
        this.old_adr1 = old_adr1;
    }
    /**
     * @param old_adr2 TODO
     */
    public void setOld_adr2(String old_adr2) {
        this.old_adr2 = old_adr2;
    }
    /**
     * @param new_bldg_mgt_no TODO
     */
    public void setNew_bldg_mgt_no(String new_bldg_mgt_no) {
        this.new_bldg_mgt_no = new_bldg_mgt_no;
    }
    /**
     * @param new_zipcd TODO
     */
    public void setNew_zipcd(String new_zipcd) {
        this.new_zipcd = new_zipcd;
    }
    /**
     * @param new_adr1 TODO
     */
    public void setNew_adr1(String new_adr1) {
        this.new_adr1 = new_adr1;
    }
    /**
     * @param new_adr2 TODO
     */
    public void setNew_adr2(String new_adr2) {
        this.new_adr2 = new_adr2;
    }
    /**
     * @param del_yn TODO
     */
    public void setDel_yn(String del_yn) {
        this.del_yn = del_yn;
    }
    /**
     * @param cretr_id TODO
     */
    public void setCretr_id(String cretr_id) {
        this.cretr_id = cretr_id;
    }
    /**
     * @param cret_dt TODO
     */
    public void setCret_dt(String cret_dt) {
        this.cret_dt = cret_dt;
    }
    /**
     * @param amdr_id TODO
     */
    public void setAmdr_id(String amdr_id) {
        this.amdr_id = amdr_id;
    }
    /**
     * @param amd_dt TODO
     */
    public void setAmd_dt(String amd_dt) {
        this.amd_dt = amd_dt;
    }
    /**
     * @param org_cd TODO
     */
    public void setOrg_cd(String org_cd) {
        this.org_cd = org_cd;
    }
    
}
