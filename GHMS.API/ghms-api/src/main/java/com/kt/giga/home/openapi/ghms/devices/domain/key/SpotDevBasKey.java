/*******************************************************************************
 * Copyright(c) 2015 KT. All rights reserved.
 * This software is the proprietary information of KT.
 *******************************************************************************/
package com.kt.giga.home.openapi.ghms.devices.domain.key;

import java.util.ArrayList;
import java.util.List;

import com.kt.giga.home.openapi.ghms.kafka.smcp.gw.ca.domn.spotdev.SpotDevDtl;

/**
 * 현장 장치 기본 KEY
 * @author dahye.kim (dahye.kim@ceinside.co.kr)
 * @since 2015. 3. 30.
 */
public class SpotDevBasKey {

    /** 서비스대상일련번호  */
    private Long svcTgtSeq;
    /** 현장장치일련번호 */
    private Long spotDevSeq;
    /** 상위서비스대상일련번호 */
    private Long upSvcTgtSeq;
    /** 상위장치일련번호 */
    private Long upSpotDevSeq;
    /** 장치UU아이디 */
    private String devUUID;
    /** 장치그룹아이디 */
    private String devGroupId;
    /** 장치모델일련번호 */
    private String devModelSeq;
    /** 상위현장장치아이디 */
    private String upSpotDevId;
	/** 상위게이트웨이연결아이디 */
	private String upGwCnctId;
    /** 현장장치아이디 */
    private String spotDevId;
    /** 장치명 */
    private String devNm;
    /** 물리장치여부 */
    private String physDevYn;
    /** 사용여부 */
    private String useYn;
    /** 임시장치여부 */
    private String tmpDevYn;
    /** IP주소 */
    private String ipadr;
    /** 인증번호 */
    private String athnNo;
    /** 인증방식코드 */
    private String athnFormlCd;
    /** 수집주기시간 */
    private long colecCyclTime;
    /** 유휴판단기준시간 */
    private long idleJdgmBaseTime;
    /** 재접속주기기간 */
    private long recnCyclTime;
    /** 재접속시도횟수 */
    private long recnTryTmscnt;
    /** 상품일련번호 */
    private String prodSeq;
    /** 등록일련번호 */
    private String regSeq;
    /** 펌웨어버전번호 */
    private String frmwrVerNo;
    /** 최종동작일시 */
    private String lastMtnDt;
    /** 설치위치내용 */
    private String eqpLoSbst;
    /** 장애관리자아이디 */
    private String troblAdmrId;
    /** 장치상태코드 */
    private String devSttusCd;
    /** 위도값 */
    private String latitVal;
    /** 위도구분코드 */
    private String latitDivCd;
    /** 경도구분코드 */
    private String lngitDivCd;
    /** 경도값 */
    private String lngitVal;
    /** 고도 */
    private String altt;
    /** 비고 */
    private String rmark;
    /** 삭제여부 */
    private String delYn;
    /** 생성자아이디 */
    private String cretrId;
    /** 생성일시 */
    private String cretDt;
    /** 수정자아이디 */
    private String amdrId;
    /** 수정일시 */
    private String amdDt;
    /** 장치유형코드 */
    private String devTypeCd;
    /** 장치모델코드 */
    private String devModelCd;
    /** 서비스대상유형코드 */
    private String svcTgtTypeCd;
	/** 현장장치상세 */
	private List<SpotDevDtl> spotDevDtls = new ArrayList<SpotDevDtl>();
    /**
     * @return TODO
     */
    public Long getSvcTgtSeq() {
        return svcTgtSeq;
    }
    /**
     * @return TODO
     */
    public Long getSpotDevSeq() {
        return spotDevSeq;
    }
    /**
     * @return TODO
     */
    public Long getUpSvcTgtSeq() {
        return upSvcTgtSeq;
    }
    /**
     * @return TODO
     */
    public Long getUpSpotDevSeq() {
        return upSpotDevSeq;
    }
    /**
     * @return TODO
     */
    public String getDevUUID() {
        return devUUID;
    }
    /**
     * @return TODO
     */
    public String getDevGroupId() {
        return devGroupId;
    }
    /**
     * @return TODO
     */
    public String getDevModelSeq() {
        return devModelSeq;
    }
    /**
     * @return TODO
     */
    public String getSpotDevId() {
        return spotDevId;
    }
    /**
     * @return TODO
     */
    public String getDevNm() {
        return devNm;
    }
    /**
     * @return TODO
     */
    public String getPhysDevYn() {
        return physDevYn;
    }
    /**
     * @return TODO
     */
    public String getUseYn() {
        return useYn;
    }
    /**
     * @return TODO
     */
    public String getTmpDevYn() {
        return tmpDevYn;
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
    public String getAthnNo() {
        return athnNo;
    }
    /**
     * @return TODO
     */
    public String getAthnFormlCd() {
        return athnFormlCd;
    }
    /**
     * @return TODO
     */
    public long getColecCyclTime() {
        return colecCyclTime;
    }
    /**
     * @return TODO
     */
    public long getIdleJdgmBaseTime() {
        return idleJdgmBaseTime;
    }
    /**
     * @return TODO
     */
    public long getRecnCyclTime() {
        return recnCyclTime;
    }
    /**
     * @return TODO
     */
    public long getRecnTryTmscnt() {
        return recnTryTmscnt;
    }
    /**
     * @return TODO
     */
    public String getProdSeq() {
        return prodSeq;
    }
    /**
     * @return TODO
     */
    public String getRegSeq() {
        return regSeq;
    }
    /**
     * @return TODO
     */
    public String getFrmwrVerNo() {
        return frmwrVerNo;
    }
    /**
     * @return TODO
     */
    public String getLastMtnDt() {
        return lastMtnDt;
    }
    /**
     * @return TODO
     */
    public String getEqpLoSbst() {
        return eqpLoSbst;
    }
    /**
     * @return TODO
     */
    public String getTroblAdmrId() {
        return troblAdmrId;
    }
    /**
     * @return TODO
     */
    public String getDevSttusCd() {
        return devSttusCd;
    }
    /**
     * @return TODO
     */
    public String getLatitVal() {
        return latitVal;
    }
    /**
     * @return TODO
     */
    public String getLatitDivCd() {
        return latitDivCd;
    }
    /**
     * @return TODO
     */
    public String getLngitDivCd() {
        return lngitDivCd;
    }
    /**
     * @return TODO
     */
    public String getLngitVal() {
        return lngitVal;
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
    public String getDelYn() {
        return delYn;
    }
    /**
     * @return TODO
     */
    public String getCretrId() {
        return cretrId;
    }
    /**
     * @return TODO
     */
    public String getCretDt() {
        return cretDt;
    }
    /**
     * @return TODO
     */
    public String getAmdrId() {
        return amdrId;
    }
    /**
     * @return TODO
     */
    public String getAmdDt() {
        return amdDt;
    }
    /**
     * @return TODO
     */
    public String getDevTypeCd() {
        return devTypeCd;
    }
    /**
     * @param svcTgtSeq TODO
     */
    public void setSvcTgtSeq(Long svcTgtSeq) {
        this.svcTgtSeq = svcTgtSeq;
    }
    /**
     * @param spotDevSeq TODO
     */
    public void setSpotDevSeq(Long spotDevSeq) {
        this.spotDevSeq = spotDevSeq;
    }
    /**
     * @param upSvcTgtSeq TODO
     */
    public void setUpSvcTgtSeq(Long upSvcTgtSeq) {
        this.upSvcTgtSeq = upSvcTgtSeq;
    }
    /**
     * @param upSpotDevSeq TODO
     */
    public void setUpSpotDevSeq(Long upSpotDevSeq) {
        this.upSpotDevSeq = upSpotDevSeq;
    }
    /**
     * @param devUUID TODO
     */
    public void setDevUUID(String devUUID) {
        this.devUUID = devUUID;
    }
    /**
     * @param devGroupId TODO
     */
    public void setDevGroupId(String devGroupId) {
        this.devGroupId = devGroupId;
    }
    /**
     * @param devModelSeq TODO
     */
    public void setDevModelSeq(String devModelSeq) {
        this.devModelSeq = devModelSeq;
    }
    /**
     * @param spotDevId TODO
     */
    public void setSpotDevId(String spotDevId) {
        this.spotDevId = spotDevId;
    }
    /**
     * @param devNm TODO
     */
    public void setDevNm(String devNm) {
        this.devNm = devNm;
    }
    /**
     * @param physDevYn TODO
     */
    public void setPhysDevYn(String physDevYn) {
        this.physDevYn = physDevYn;
    }
    /**
     * @param useYn TODO
     */
    public void setUseYn(String useYn) {
        this.useYn = useYn;
    }
    /**
     * @param tmpDevYn TODO
     */
    public void setTmpDevYn(String tmpDevYn) {
        this.tmpDevYn = tmpDevYn;
    }
    /**
     * @param ipadr TODO
     */
    public void setIpadr(String ipadr) {
        this.ipadr = ipadr;
    }
    /**
     * @param athnNo TODO
     */
    public void setAthnNo(String athnNo) {
        this.athnNo = athnNo;
    }
    /**
     * @param athnFormlCd TODO
     */
    public void setAthnFormlCd(String athnFormlCd) {
        this.athnFormlCd = athnFormlCd;
    }
    /**
     * @param colecCyclTime TODO
     */
    public void setColecCyclTime(long colecCyclTime) {
        this.colecCyclTime = colecCyclTime;
    }
    /**
     * @param idleJdgmBaseTime TODO
     */
    public void setIdleJdgmBaseTime(long idleJdgmBaseTime) {
        this.idleJdgmBaseTime = idleJdgmBaseTime;
    }
    /**
     * @param recnCyclTime TODO
     */
    public void setRecnCyclTime(long recnCyclTime) {
        this.recnCyclTime = recnCyclTime;
    }
    /**
     * @param recnTryTmscnt TODO
     */
    public void setRecnTryTmscnt(long recnTryTmscnt) {
        this.recnTryTmscnt = recnTryTmscnt;
    }
    /**
     * @param prodSeq TODO
     */
    public void setProdSeq(String prodSeq) {
        this.prodSeq = prodSeq;
    }
    /**
     * @param regSeq TODO
     */
    public void setRegSeq(String regSeq) {
        this.regSeq = regSeq;
    }
    /**
     * @param frmwrVerNo TODO
     */
    public void setFrmwrVerNo(String frmwrVerNo) {
        this.frmwrVerNo = frmwrVerNo;
    }
    /**
     * @param lastMtnDt TODO
     */
    public void setLastMtnDt(String lastMtnDt) {
        this.lastMtnDt = lastMtnDt;
    }
    /**
     * @param eqpLoSbst TODO
     */
    public void setEqpLoSbst(String eqpLoSbst) {
        this.eqpLoSbst = eqpLoSbst;
    }
    /**
     * @param troblAdmrId TODO
     */
    public void setTroblAdmrId(String troblAdmrId) {
        this.troblAdmrId = troblAdmrId;
    }
    /**
     * @param devSttusCd TODO
     */
    public void setDevSttusCd(String devSttusCd) {
        this.devSttusCd = devSttusCd;
    }
    /**
     * @param latitVal TODO
     */
    public void setLatitVal(String latitVal) {
        this.latitVal = latitVal;
    }
    /**
     * @param latitDivCd TODO
     */
    public void setLatitDivCd(String latitDivCd) {
        this.latitDivCd = latitDivCd;
    }
    /**
     * @param lngitDivCd TODO
     */
    public void setLngitDivCd(String lngitDivCd) {
        this.lngitDivCd = lngitDivCd;
    }
    /**
     * @param lngitVal TODO
     */
    public void setLngitVal(String lngitVal) {
        this.lngitVal = lngitVal;
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
     * @param delYn TODO
     */
    public void setDelYn(String delYn) {
        this.delYn = delYn;
    }
    /**
     * @param cretrId TODO
     */
    public void setCretrId(String cretrId) {
        this.cretrId = cretrId;
    }
    /**
     * @param cretDt TODO
     */
    public void setCretDt(String cretDt) {
        this.cretDt = cretDt;
    }
    /**
     * @param amdrId TODO
     */
    public void setAmdrId(String amdrId) {
        this.amdrId = amdrId;
    }
    /**
     * @param amdDt TODO
     */
    public void setAmdDt(String amdDt) {
        this.amdDt = amdDt;
    }
    /**
     * @param devTypeCd TODO
     */
    public void setDevTypeCd(String devTypeCd) {
        this.devTypeCd = devTypeCd;
    }
	/**
	 * @return TODO
	 */
	public String getDevModelCd() {
		return devModelCd;
	}
	/**
	 * @param devModelCd TODO
	 */
	public void setDevModelCd(String devModelCd) {
		this.devModelCd = devModelCd;
	}
	/**
	 * @return TODO
	 */
	public String getUpSpotDevId() {
		return upSpotDevId;
	}
	/**
	 * @param upSpotDevId TODO
	 */
	public void setUpSpotDevId(String upSpotDevId) {
		this.upSpotDevId = upSpotDevId;
	}
	/**
	 * @return the upGwCnctId
	 */
	public String getUpGwCnctId() {
		return upGwCnctId;
	}
	/**
	 * @param upGwCnctId the upGwCnctId to set
	 */
	public void setUpGwCnctId(String upGwCnctId) {
		this.upGwCnctId = upGwCnctId;
	}

	/**
	 * @return TODO
	 */
	public String getSvcTgtTypeCd() {
		return svcTgtTypeCd;
	}
	/**
	 * @param svcTgtTypeCd TODO
	 */
	public void setSvcTgtTypeCd(String svcTgtTypeCd) {
		this.svcTgtTypeCd = svcTgtTypeCd;
	}
	public List<SpotDevDtl> getSpotDevDtls() {
		return spotDevDtls;
	}
	public void setSpotDevDtls(List<SpotDevDtl> spotDevDtls) {
		this.spotDevDtls = spotDevDtls;
	}
}
