package com.kt.giga.home.openapi.ghms.kafka.smcp.gw.ca.domn.upifsys;

import java.io.Serializable;

import com.kt.giga.home.openapi.ghms.kafka.smcp.gw.ca.comm.GwCode.RoutType;
import com.kt.giga.home.openapi.ghms.kafka.smcp.gw.ca.comm.GwCode.UseYn;

public class UpIfSysLnkSvcTgt implements Serializable 
{
	/** 상위인터페이스시스템아이디 */
	private String upIfSysId;
	/** 연계조건아이디 */
	private String lnkCondId;
	/** 라우팅유형코드 */
	private RoutType routTypeCd;
	/** 단위서비스전체여부 */
	private UseYn unitSvcWholeYn;
	/** M2M서비스전체여부 */
	private UseYn m2mSvcWholeYn;
	/** 서비스대상전체여부 */
	private UseYn svcTgtWholeYn;
	/** 단위서비스코드 */
	private String unitSvcCd;
	/** M2M서비스번호 */
	private Integer m2mSvcNo;
	/** 서비스대상일련번호 */
	private Long svcTgtSeq;
	/**
	 * @return the upIfSysId
	 */
	public String getUpIfSysId() {
		return upIfSysId;
	}
	/**
	 * @param upIfSysId the upIfSysId to set
	 */
	public void setUpIfSysId(String upIfSysId) {
		this.upIfSysId = upIfSysId;
	}
	/**
	 * @return the lnkCondId
	 */
	public String getLnkCondId() {
		return lnkCondId;
	}
	/**
	 * @param lnkCondId the lnkCondId to set
	 */
	public void setLnkCondId(String lnkCondId) {
		this.lnkCondId = lnkCondId;
	}
	/**
	 * @return the routTypeCd
	 */
	public RoutType getRoutTypeCd() {
		return routTypeCd;
	}
	/**
	 * @param routTypeCd the routTypeCd to set
	 */
	public void setRoutTypeCd(String routTypeCd) {
		this.routTypeCd = RoutType.fromString(routTypeCd);
	}
	/**
	 * @return the unitSvcWholeYn
	 */
	public UseYn getUnitSvcWholeYn() {
		return unitSvcWholeYn;
	}
	/**
	 * @param unitSvcWholeYn the unitSvcWholeYn to set
	 */
	public void setUnitSvcWholeYn(String unitSvcWholeYn) {
		this.unitSvcWholeYn = UseYn.fromString(unitSvcWholeYn);;;
	}
	/**
	 * @return the m2mSvcWholeYn
	 */
	public UseYn getM2mSvcWholeYn() {
		return m2mSvcWholeYn;
	}
	/**
	 * @param m2mSvcWholeYn the m2mSvcWholeYn to set
	 */
	public void setM2mSvcWholeYn(String m2mSvcWholeYn) {
		this.m2mSvcWholeYn = UseYn.fromString(m2mSvcWholeYn);;
	}
	/**
	 * @return the svcTgtWholeYn
	 */
	public UseYn getSvcTgtWholeYn() {
		return svcTgtWholeYn;
	}
	/**
	 * @param svcTgtWholeYn the svcTgtWholeYn to set
	 */
	public void setSvcTgtWholeYn(String svcTgtWholeYn) {
		this.svcTgtWholeYn = UseYn.fromString(svcTgtWholeYn);
	}
	/**
	 * @return the unitSvcCd
	 */
	public String getUnitSvcCd() {
		return unitSvcCd;
	}
	/**
	 * @param unitSvcCd the unitSvcCd to set
	 */
	public void setUnitSvcCd(String unitSvcCd) {
		this.unitSvcCd = unitSvcCd;
	}
	/**
	 * @return the m2mSvcNo
	 */
	public Integer getM2mSvcNo() {
		return m2mSvcNo;
	}
	/**
	 * @param m2mSvcNo the m2mSvcNo to set
	 */
	public void setM2mSvcNo(Integer m2mSvcNo) {
		this.m2mSvcNo = m2mSvcNo;
	}
	/**
	 * @return the svcTgtSeq
	 */
	public Long getSvcTgtSeq() {
		return svcTgtSeq;
	}
	/**
	 * @param svcTgtSeq the svcTgtSeq to set
	 */
	public void setSvcTgtSeq(Long svcTgtSeq) {
		this.svcTgtSeq = svcTgtSeq;
	}
}


