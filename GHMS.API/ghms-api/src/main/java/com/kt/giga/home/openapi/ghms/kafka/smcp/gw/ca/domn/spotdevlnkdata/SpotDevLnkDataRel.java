package com.kt.giga.home.openapi.ghms.kafka.smcp.gw.ca.domn.spotdevlnkdata;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.kt.giga.home.openapi.ghms.kafka.smcp.gw.ca.comm.GwCode.M3SnsnTagType;
import com.kt.giga.home.openapi.ghms.kafka.smcp.gw.ca.comm.GwCode.UseYn;

public class SpotDevLnkDataRel implements Serializable {
	/** 서비스대상일련번호 */
	private Long svcTgtSeq;
	/** 현장장치일련번호 */
	private Long spotDevSeq;
	/** 센싱태그유형코드 */
	private M3SnsnTagType snsnTagTypeCd;
	/** 센싱태그코드 */
	private String snsnTagCd;
	/** 생성자아이디 */
	private String cretrId;
	/** 생성일시 */
	private Date cretDt;
	/** 수정자아이디 */
	private String amdrId;
	/** 수정일시 */
	private Date amdDt;
	/** 비고 */
	private String rmark;
	/** 사용여부 */
	private UseYn useYn;
	
	
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.MULTI_LINE_STYLE);
	}
	
	
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode()
	{
		if(spotDevSeq == null)
		{
			return 0;
		}			
		return spotDevSeq.hashCode();
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj)
	{	
		if( !(obj instanceof SpotDevLnkDataRel) )
		{
			return false;
		}
		
		SpotDevLnkDataRel spotDevLnkDataRel = (SpotDevLnkDataRel)obj;
		
		if(spotDevLnkDataRel.svcTgtSeq != null &&
			spotDevLnkDataRel.spotDevSeq != null &&
			spotDevLnkDataRel.snsnTagTypeCd != null &&
			spotDevLnkDataRel.snsnTagCd != null && 
				this.svcTgtSeq  != null &&
				this.spotDevSeq != null &&
				this.snsnTagTypeCd != null &&
				this.snsnTagCd != null)	{
			
			if(this.svcTgtSeq.longValue() == spotDevLnkDataRel.svcTgtSeq.longValue() && 
					this.spotDevSeq.longValue() == spotDevLnkDataRel.spotDevSeq.longValue() &&
					this.snsnTagTypeCd.equals(spotDevLnkDataRel.snsnTagTypeCd) &&
					this.snsnTagCd.equals(spotDevLnkDataRel.snsnTagCd)) {
				
				return true;
			}
		} 
		
		if(spotDevLnkDataRel.svcTgtSeq != null && 
				spotDevLnkDataRel.spotDevSeq != null && 
				this.svcTgtSeq != null && 
				this.spotDevSeq != null) {
			
			if(this.svcTgtSeq.longValue() == spotDevLnkDataRel.svcTgtSeq.longValue() && 
					this.spotDevSeq.longValue() == spotDevLnkDataRel.spotDevSeq.longValue()) {
				
				return true;
			}
		}
		
		if(spotDevLnkDataRel.snsnTagTypeCd != null && 
				spotDevLnkDataRel.snsnTagCd != null && 
				this.snsnTagTypeCd != null && 
				this.snsnTagCd != null) {
			
			if(this.snsnTagTypeCd.equals(spotDevLnkDataRel.snsnTagTypeCd) && 
					this.snsnTagCd.equals(spotDevLnkDataRel.snsnTagCd)) {
				
				return true;
			}
		}
		
		return false;
	}
	
	      
	/**
	 * @return the svcTgtSeq
	 */
	public Long getSvcTgtSeq() {
		return svcTgtSeq;
	}
	/**
	 * @param svcTgtSeq the svcTgtseq to set
	 */
	public void setSvcTgtSeq(Long svcTgtSeq) {
		this.svcTgtSeq = svcTgtSeq;
	}
	/**
	 * @return the spotDevSeq
	 */
	public Long getSpotDevSeq() {
		return spotDevSeq;
	}
	/**
	 * @param spotDevSeq the spotDevSeq to set
	 */
	public void setSpotDevSeq(Long spotDevSeq) {
		this.spotDevSeq = spotDevSeq;
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
	public Date getCretDt() {
		return cretDt;
	}
	/**
	 * @param cretDt the cretDt to set
	 */
	public void setCretDt(Date cretDt) {
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
	public Date getAmdDt() {
		return amdDt;
	}
	/**
	 * @param amdDt the amdDt to set
	 */
	public void setAmdDt(Date amdDt) {
		this.amdDt = amdDt;
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
	 * @return the useYn
	 */
	public UseYn getUseYn() {
		return useYn;
	}
	/**
	 * @param useYn the useYn to set
	 */
	public void setUseYn(String useYn) {
		this.useYn = UseYn.fromString(useYn);
	}
}
