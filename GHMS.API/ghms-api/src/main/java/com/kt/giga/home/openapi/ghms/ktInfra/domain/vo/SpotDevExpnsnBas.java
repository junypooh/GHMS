/*******************************************************************************
 * Copyright(c) 2015 KT. All rights reserved.
 * This software is the proprietary information of KT.
 *******************************************************************************/
package com.kt.giga.home.openapi.ghms.ktInfra.domain.vo;

/**
 * 현장장치확장기본 VO
 * 
 * @author dahye.kim (dahye.kim@ceinside.co.kr)
 * @since 2015. 3. 10.
 */
public class SpotDevExpnsnBas {
    
    /** 서비스 대상 일련 번호 */
    private long svc_tgt_seq;
    /** 현장 장치 일련 번호 */
    private long spot_dev_seq;
    /** 현장 장치 항목 명 */
    private String spot_dev_item_nm;
    /** 현장 장치 항목 값 */
    private String spot_dev_item_val;
    
    /**
     * @return TODO
     */
    public long getSvc_tgt_seq() {
        return svc_tgt_seq;
    }
    /**
     * @return TODO
     */
    public long getSpot_dev_seq() {
        return spot_dev_seq;
    }
    /**
     * @return TODO
     */
    public String getSpot_dev_item_nm() {
        return spot_dev_item_nm;
    }
    /**
     * @return TODO
     */
    public String getSpot_dev_item_val() {
        return spot_dev_item_val;
    }
    /**
     * @param svc_tgt_seq TODO
     */
    public void setSvc_tgt_seq(long svc_tgt_seq) {
        this.svc_tgt_seq = svc_tgt_seq;
    }
    /**
     * @param spot_dev_seq TODO
     */
    public void setSpot_dev_seq(long spot_dev_seq) {
        this.spot_dev_seq = spot_dev_seq;
    }
    /**
     * @param spot_dev_item_nm TODO
     */
    public void setSpot_dev_item_nm(String spot_dev_item_nm) {
        this.spot_dev_item_nm = spot_dev_item_nm;
    }
    /**
     * @param spot_dev_item_val TODO
     */
    public void setSpot_dev_item_val(String spot_dev_item_val) {
        this.spot_dev_item_val = spot_dev_item_val;
    }
    

}
