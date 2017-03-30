/*******************************************************************************
 * Copyright(c) 2015 KT. All rights reserved.
 * This software is the proprietary information of KT.
 *******************************************************************************/
package com.kt.giga.home.openapi.ghms.ktInfra.domain.vo;

/**
 * 현장 장치 기본 클래스
 * 
 * @author dahye.kim (dahye.kim@ceinside.co.kr)
 * @since 2015. 3. 12.
 */
public class SpotDevBas {
    /** 현장 장치 일련번호 */
    private long   spot_dev_seq;
    /** 서비스 대상 일련번호 */
    private long   svc_tgt_seq;
    /** 상위 장치 일련번호 */
    private long   up_spot_dev_seq;
    /** 상위 서비스 대상 일련번호 */
    private String up_svc_tgt_seq;
    /** 장치 UUID */
    private String dev_uu_id;
    /** 장치 그룹 아이디 */
    private String dev_group_id;
    /** 장치 모델 일련번호 */
    private long   dev_model_seq;
    /** 현장 장치 아이디 */
    private String spot_dev_id;
    /** 장치명 */
    private String dev_nm;
    /** 물리 장치 여부 */
    private String phys_dev_yn;
    /** 사용 여부 */
    private String use_yn;
    /** 임치 장치 여부 */
    private String tmp_dev_yn;
    /** IP 주소 */
    private String ipadr;
    /** 인증번호 */
    private String athn_no;
    /** 인증 방식 코드 */
    private String athn_forml_cd;
    /** 수집 주기 시간 */
    private String colec_cycl_time;
    /** 유휴 판단 기준 시간 */
    private String idle_jdgm_base_time;
    /** 재접속 주기 시간 */
    private String recn_cycl_time;
    /** 재접속 시도 횟수 */
    private String recn_try_tmscnt;
    /** 상품 일련번호 */
    private String prod_seq;
    /** 등록 일련번호 */
    private String reg_seq;
    /** 펌웨어 버전 번호 */
    private String frmwr_ver_no;
    /** 최종 동작 일시 */
    private String last_mtn_dt;
    /** 설치 위치 내용 */
    private String eqp_lo_sbst;
    /** 장애 관리자 아이디 */
    private String trobl_admr_id;
    /** 장치 상태 코드 */
    private String dev_sttus_cd;
    /** 위도 값 */
    private String latit_val;
    /** 위도 구분 코드 */
    private String latit_div_cd;
    /** 경도 구분 코드 */
    private String lngit_div_cd;
    /** 경도 값 */
    private String lngit_val;
    /** 고도 */
    private String altt;
    /** 비고 */
    private String rmark;
    /** 삭제 여부 */
    private String del_yn;
    /** 생성자 아이디 */
    private String cretr_id;
    /** 수정일시 */
    private String cret_dt;
    /** 수정자아이디 */
    private String amdr_id;
    /** 수정일시 */
    private String  amd_dt;
    /**
     * @return TODO
     */
    public long getSpot_dev_seq() {
        return spot_dev_seq;
    }
    /**
     * @return TODO
     */
    public long getSvc_tgt_seq() {
        return svc_tgt_seq;
    }
    /**
     * @return TODO
     */
    public long getUp_spot_dev_seq() {
        return up_spot_dev_seq;
    }
    /**
     * @return TODO
     */
    public String getUp_svc_tgt_seq() {
        return up_svc_tgt_seq;
    }
    /**
     * @return TODO
     */
    public String getDev_uu_id() {
        return dev_uu_id;
    }
    /**
     * @return TODO
     */
    public String getDev_group_id() {
        return dev_group_id;
    }
    /**
     * @return TODO
     */
    public long getDev_model_seq() {
        return dev_model_seq;
    }
    /**
     * @return TODO
     */
    public String getSpot_dev_id() {
        return spot_dev_id;
    }
    /**
     * @return TODO
     */
    public String getDev_nm() {
        return dev_nm;
    }
    /**
     * @return TODO
     */
    public String getPhys_dev_yn() {
        return phys_dev_yn;
    }
    /**
     * @return TODO
     */
    public String getUse_yn() {
        return use_yn;
    }
    /**
     * @return TODO
     */
    public String getTmp_dev_yn() {
        return tmp_dev_yn;
    }
    /**
     * @return TODO
     */
    public String getIpadr() {
        return ipadr;
    }
    /**
     * @return TODO
     */
    public String getAthn_no() {
        return athn_no;
    }
    /**
     * @return TODO
     */
    public String getAthn_forml_cd() {
        return athn_forml_cd;
    }
    /**
     * @return TODO
     */
    public String getColec_cycl_time() {
        return colec_cycl_time;
    }
    /**
     * @return TODO
     */
    public String getIdle_jdgm_base_time() {
        return idle_jdgm_base_time;
    }
    /**
     * @return TODO
     */
    public String getRecn_cycl_time() {
        return recn_cycl_time;
    }
    /**
     * @return TODO
     */
    public String getRecn_try_tmscnt() {
        return recn_try_tmscnt;
    }
    /**
     * @return TODO
     */
    public String getProd_seq() {
        return prod_seq;
    }
    /**
     * @return TODO
     */
    public String getReg_seq() {
        return reg_seq;
    }
    /**
     * @return TODO
     */
    public String getFrmwr_ver_no() {
        return frmwr_ver_no;
    }
    /**
     * @return TODO
     */
    public String getLast_mtn_dt() {
        return last_mtn_dt;
    }
    /**
     * @return TODO
     */
    public String getEqp_lo_sbst() {
        return eqp_lo_sbst;
    }
    /**
     * @return TODO
     */
    public String getTrobl_admr_id() {
        return trobl_admr_id;
    }
    /**
     * @return TODO
     */
    public String getDev_sttus_cd() {
        return dev_sttus_cd;
    }
    /**
     * @return TODO
     */
    public String getLatit_val() {
        return latit_val;
    }
    /**
     * @return TODO
     */
    public String getLatit_div_cd() {
        return latit_div_cd;
    }
    /**
     * @return TODO
     */
    public String getLngit_div_cd() {
        return lngit_div_cd;
    }
    /**
     * @return TODO
     */
    public String getLngit_val() {
        return lngit_val;
    }
    /**
     * @return TODO
     */
    public String getAltt() {
        return altt;
    }
    /**
     * @return TODO
     */
    public String getRmark() {
        return rmark;
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
     * @param spot_dev_seq TODO
     */
    public void setSpot_dev_seq(long spot_dev_seq) {
        this.spot_dev_seq = spot_dev_seq;
    }
    /**
     * @param svc_tgt_seq TODO
     */
    public void setSvc_tgt_seq(long svc_tgt_seq) {
        this.svc_tgt_seq = svc_tgt_seq;
    }
    /**
     * @param up_spot_dev_seq TODO
     */
    public void setUp_spot_dev_seq(long up_spot_dev_seq) {
        this.up_spot_dev_seq = up_spot_dev_seq;
    }
    /**
     * @param up_svc_tgt_seq TODO
     */
    public void setUp_svc_tgt_seq(String up_svc_tgt_seq) {
        this.up_svc_tgt_seq = up_svc_tgt_seq;
    }
    /**
     * @param dev_uu_id TODO
     */
    public void setDev_uu_id(String dev_uu_id) {
        this.dev_uu_id = dev_uu_id;
    }
    /**
     * @param dev_group_id TODO
     */
    public void setDev_group_id(String dev_group_id) {
        this.dev_group_id = dev_group_id;
    }
    /**
     * @param dev_model_seq TODO
     */
    public void setDev_model_seq(long dev_model_seq) {
        this.dev_model_seq = dev_model_seq;
    }
    /**
     * @param spot_dev_id TODO
     */
    public void setSpot_dev_id(String spot_dev_id) {
        this.spot_dev_id = spot_dev_id;
    }
    /**
     * @param dev_nm TODO
     */
    public void setDev_nm(String dev_nm) {
        this.dev_nm = dev_nm;
    }
    /**
     * @param phys_dev_yn TODO
     */
    public void setPhys_dev_yn(String phys_dev_yn) {
        this.phys_dev_yn = phys_dev_yn;
    }
    /**
     * @param use_yn TODO
     */
    public void setUse_yn(String use_yn) {
        this.use_yn = use_yn;
    }
    /**
     * @param tmp_dev_yn TODO
     */
    public void setTmp_dev_yn(String tmp_dev_yn) {
        this.tmp_dev_yn = tmp_dev_yn;
    }
    /**
     * @param ipadr TODO
     */
    public void setIpadr(String ipadr) {
        this.ipadr = ipadr;
    }
    /**
     * @param athn_no TODO
     */
    public void setAthn_no(String athn_no) {
        this.athn_no = athn_no;
    }
    /**
     * @param athn_forml_cd TODO
     */
    public void setAthn_forml_cd(String athn_forml_cd) {
        this.athn_forml_cd = athn_forml_cd;
    }
    /**
     * @param colec_cycl_time TODO
     */
    public void setColec_cycl_time(String colec_cycl_time) {
        this.colec_cycl_time = colec_cycl_time;
    }
    /**
     * @param idle_jdgm_base_time TODO
     */
    public void setIdle_jdgm_base_time(String idle_jdgm_base_time) {
        this.idle_jdgm_base_time = idle_jdgm_base_time;
    }
    /**
     * @param recn_cycl_time TODO
     */
    public void setRecn_cycl_time(String recn_cycl_time) {
        this.recn_cycl_time = recn_cycl_time;
    }
    /**
     * @param recn_try_tmscnt TODO
     */
    public void setRecn_try_tmscnt(String recn_try_tmscnt) {
        this.recn_try_tmscnt = recn_try_tmscnt;
    }
    /**
     * @param prod_seq TODO
     */
    public void setProd_seq(String prod_seq) {
        this.prod_seq = prod_seq;
    }
    /**
     * @param reg_seq TODO
     */
    public void setReg_seq(String reg_seq) {
        this.reg_seq = reg_seq;
    }
    /**
     * @param frmwr_ver_no TODO
     */
    public void setFrmwr_ver_no(String frmwr_ver_no) {
        this.frmwr_ver_no = frmwr_ver_no;
    }
    /**
     * @param last_mtn_dt TODO
     */
    public void setLast_mtn_dt(String last_mtn_dt) {
        this.last_mtn_dt = last_mtn_dt;
    }
    /**
     * @param eqp_lo_sbst TODO
     */
    public void setEqp_lo_sbst(String eqp_lo_sbst) {
        this.eqp_lo_sbst = eqp_lo_sbst;
    }
    /**
     * @param trobl_admr_id TODO
     */
    public void setTrobl_admr_id(String trobl_admr_id) {
        this.trobl_admr_id = trobl_admr_id;
    }
    /**
     * @param dev_sttus_cd TODO
     */
    public void setDev_sttus_cd(String dev_sttus_cd) {
        this.dev_sttus_cd = dev_sttus_cd;
    }
    /**
     * @param latit_val TODO
     */
    public void setLatit_val(String latit_val) {
        this.latit_val = latit_val;
    }
    /**
     * @param latit_div_cd TODO
     */
    public void setLatit_div_cd(String latit_div_cd) {
        this.latit_div_cd = latit_div_cd;
    }
    /**
     * @param lngit_div_cd TODO
     */
    public void setLngit_div_cd(String lngit_div_cd) {
        this.lngit_div_cd = lngit_div_cd;
    }
    /**
     * @param lngit_val TODO
     */
    public void setLngit_val(String lngit_val) {
        this.lngit_val = lngit_val;
    }
    /**
     * @param altt TODO
     */
    public void setAltt(String altt) {
        this.altt = altt;
    }
    /**
     * @param rmark TODO
     */
    public void setRmark(String rmark) {
        this.rmark = rmark;
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
    
   
    
}
