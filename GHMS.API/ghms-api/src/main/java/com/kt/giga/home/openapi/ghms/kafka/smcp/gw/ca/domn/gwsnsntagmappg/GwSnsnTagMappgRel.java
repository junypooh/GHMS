package com.kt.giga.home.openapi.ghms.kafka.smcp.gw.ca.domn.gwsnsntagmappg;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.kt.giga.home.openapi.ghms.kafka.smcp.gw.ca.comm.GwAdaptorCode.GwType;
import com.kt.giga.home.openapi.ghms.kafka.smcp.gw.ca.comm.GwCode.GwSnsnTagType;
import com.kt.giga.home.openapi.ghms.kafka.smcp.gw.ca.comm.GwCode.M3SnsnTagType;

public class GwSnsnTagMappgRel implements Serializable {
	/** 게이트웨이유형코드 */
	private GwType gwTypeCd;
	/** 게이트웨이센싱태그유형코드 */
	private GwSnsnTagType gwSnsnTagTypeCd;
	/** 게이트웨이센싱태그코드 */
	private String gwSnsnTagCd;
	/** 센싱태그유형코드 */
	private M3SnsnTagType snsnTagTypeCd;
	/** 센싱태그코드 */
	private String snsnTagCd;
	/** 비고 */
	private String rmark;
	/** 생성자아이디 */
	private String cretrId;
	/** 생성일시 */
	private String cretDt;
	/** 수정자아이디 */
	private String amdrId;
	/** 수정일시 */
	private String amdDt;

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.MULTI_LINE_STYLE);
	}

	@Override
	public int hashCode()
	{
		//TODO PK로 해쉬코드 만들 것. PK 3개로 수정 후
		if(gwSnsnTagCd == null)
		{
			return 0;
		}
		return gwSnsnTagCd.hashCode();
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj)
	{
		if( !(obj instanceof GwSnsnTagMappgRel) )
		{
			return false;
		}

		GwSnsnTagMappgRel gwSnsnTagMappgRel = (GwSnsnTagMappgRel)obj;

		if(gwSnsnTagMappgRel.gwTypeCd != null &&
				gwSnsnTagMappgRel.gwSnsnTagTypeCd != null &&
				gwSnsnTagMappgRel.gwSnsnTagCd != null &&
				gwSnsnTagMappgRel.snsnTagTypeCd != null &&
				gwSnsnTagMappgRel.snsnTagCd != null &&
				this.gwTypeCd  != null &&
				this.gwSnsnTagTypeCd != null &&
				this.gwSnsnTagCd != null &&
				this.snsnTagTypeCd != null &&
				this.snsnTagCd != null)	{

			if(this.gwTypeCd.equals(gwSnsnTagMappgRel.gwTypeCd) &&
					this.gwSnsnTagTypeCd.equals(gwSnsnTagMappgRel.gwSnsnTagTypeCd) &&
					this.gwSnsnTagCd.equals(gwSnsnTagMappgRel.gwSnsnTagCd) &&
					this.snsnTagTypeCd.equals(gwSnsnTagMappgRel.snsnTagTypeCd) &&
					this.snsnTagCd.equals(gwSnsnTagMappgRel.snsnTagCd)) {

				return true;
			}
		}

		if(gwSnsnTagMappgRel.gwTypeCd != null &&
				gwSnsnTagMappgRel.gwSnsnTagTypeCd != null &&
				gwSnsnTagMappgRel.gwSnsnTagCd != null &&
				this.gwTypeCd  != null &&
				this.gwSnsnTagTypeCd != null &&
				this.gwSnsnTagCd != null) {

			if(this.gwTypeCd.equals(gwSnsnTagMappgRel.gwTypeCd) &&
					this.gwSnsnTagTypeCd.equals(gwSnsnTagMappgRel.gwSnsnTagTypeCd) &&
					this.gwSnsnTagCd.equals(gwSnsnTagMappgRel.gwSnsnTagCd)) {

				return true;
			}
		}

		//TODO AK 여부 체크할 것. 존재가능성이 있음. 11온도, 13섭시온도 모두 온도로 설정 가능
		if(gwSnsnTagMappgRel.gwTypeCd != null &&
				gwSnsnTagMappgRel.snsnTagTypeCd != null &&
				gwSnsnTagMappgRel.snsnTagCd != null &&
				this.gwTypeCd  != null &&
				this.snsnTagTypeCd != null &&
				this.snsnTagCd != null) {

			if(this.gwTypeCd.equals(gwSnsnTagMappgRel.gwTypeCd) &&
					this.snsnTagTypeCd.equals(gwSnsnTagMappgRel.snsnTagTypeCd) &&
					this.snsnTagCd.equals(gwSnsnTagMappgRel.snsnTagCd)) {

				return true;
			}
		}

		return false;
	}


	/**
	 * @return the gwTypeCd
	 */
	public GwType getGwTypeCd() {
		return gwTypeCd;
	}
	/**
	 * @param gwTypeCd the gwTypeCd to set
	 */
	public void setGwTypeCd(String gwTypeCd) {
		this.gwTypeCd = GwType.fromString(gwTypeCd);
	}
	/**
	 * @return the gwSnsnTagTypeCd
	 */
	public GwSnsnTagType getGwSnsnTagTypeCd() {
		return gwSnsnTagTypeCd;
	}
	/**
	 * @param gwSnsnTagTypeCd the gwSnsnTagTypeCd to set
	 */
	public void setGwSnsnTagTypeCd(String gwSnsnTagTypeCd) {
		this.gwSnsnTagTypeCd = GwSnsnTagType.fromString(gwSnsnTagTypeCd);
	}
	/**
	 * @return the gwSnsnTagCd
	 */
	public String getGwSnsnTagCd() {
		return gwSnsnTagCd;
	}
	/**
	 * @param gwSnsnTagCd the gwSnsnTagCd to set
	 */
	public void setGwSnsnTagCd(String gwSnsnTagCd) {
		this.gwSnsnTagCd = gwSnsnTagCd;
	}
	/**
	 * @return the snsnTagTypeCd
	 */
	public M3SnsnTagType getSnsnTagTypeCd() {
		return snsnTagTypeCd;
	}
	/**
	 * @param snsnTagTypeCd the snsnTagTypeCd to set
	 */
	public void setSnsnTagTypeCd(String snsnTagTypeCd) {
		this.snsnTagTypeCd = M3SnsnTagType.fromString(snsnTagTypeCd);
	}
	/**
	 * @return the snsnTagCd
	 */
	public String getSnsnTagCd() {
		return snsnTagCd;
	}
	/**
	 * @param snsnTagCd the snsnTagCd to set
	 */
	public void setSnsnTagCd(String snsnTagCd) {
		this.snsnTagCd = snsnTagCd;
	}
	/**
	 * @return the rmark
	 */
	public String getRmark() {
		return rmark;
	}
	/**
	 * @param rmark the rmark to set
	 */
	public void setRmark(String rmark) {
		this.rmark = rmark;
	}
	/**
	 * @return the cretrId
	 */
	public String getCretrId() {
		return cretrId;
	}
	/**
	 * @param cretrId the cretrId to set
	 */
	public void setCretrId(String cretrId) {
		this.cretrId = cretrId;
	}
	/**
	 * @return the cretDt
	 */
	public String getCretDt() {
		return cretDt;
	}
	/**
	 * @param cretDt the cretDt to set
	 */
	public void setCretDt(String cretDt) {
		this.cretDt = cretDt;
	}
	/**
	 * @return the amdrId
	 */
	public String getAmdrId() {
		return amdrId;
	}
	/**
	 * @param amdrId the amdrId to set
	 */
	public void setAmdrId(String amdrId) {
		this.amdrId = amdrId;
	}
	/**
	 * @return the amdDt
	 */
	public String getAmdDt() {
		return amdDt;
	}
	/**
	 * @param amdDt the amdDt to set
	 */
	public void setAmdDt(String amdDt) {
		this.amdDt = amdDt;
	}

}
