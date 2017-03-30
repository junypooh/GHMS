package com.kt.smcp.gw.ca.domn.snsntagcd;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.kt.smcp.gw.ca.comm.GwCode.M3SnsnTagType;
import com.kt.smcp.gw.ca.comm.GwCode.SnsnTagUnit;
import com.kt.smcp.gw.ca.comm.GwCode.UseYn;

public class SnsnTagCdBas implements Serializable {
	/** 센싱태그유형코드 */
	private M3SnsnTagType snsnTagTypeCd;
	/** 센싱태그코드 */
	private String snsnTagCd;
	/** 센싱태그명 */
	private String snsnTagNm;
	/** 센싱태그단위코드 */
	private SnsnTagUnit snsnTagUnitCd;
	/** 센싱태그설명 */
	private String snsnTagDesc;
	/** 표시순서 */
	private Integer indcOdrg;
	/** 사용여부 */
	private UseYn useYn;
	/** 생성자아이디 */
	private String cretrId;
	/** 생성일시 */
	private Date cretDt;
	
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
		if(snsnTagCd == null)
		{
			return 0;
		}			
		return snsnTagCd.hashCode();
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj)
	{	
		if( !(obj instanceof SnsnTagCdBas) )
		{
			return false;
		}
		
		SnsnTagCdBas snsnTagCdBas = (SnsnTagCdBas)obj;
		
		if(snsnTagCdBas.snsnTagTypeCd != null &&
				snsnTagCdBas.snsnTagCd != null &&
				this.snsnTagTypeCd  != null &&
				this.snsnTagCd != null) {
			
			if(this.snsnTagTypeCd.equals(snsnTagCdBas.snsnTagTypeCd) &&
					this.snsnTagCd.equals(snsnTagCdBas.snsnTagCd)) {
				
				return true;
			}
		} 
		
		return false;
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
	 * @return the snsnTagNm
	 */
	public String getSnsnTagNm() {
		return snsnTagNm;
	}

	/**
	 * @param snsnTagNm the snsnTagNm to set
	 */
	public void setSnsnTagNm(String snsnTagNm) {
		this.snsnTagNm = snsnTagNm;
	}

	/**
	 * @return the snsnTagUnitCd
	 */
	public SnsnTagUnit getSnsnTagUnitCd() {
		return snsnTagUnitCd;
	}

	/**
	 * @param snsnTagUnitCd the snsnTagUnitCd to set
	 */
	public void setSnsnTagUnitCd(String snsnTagUnitCd) {
		this.snsnTagUnitCd = SnsnTagUnit.fromString(snsnTagUnitCd);
	}

	/**
	 * @return the snsnTagDesc
	 */
	public String getSnsnTagDesc() {
		return snsnTagDesc;
	}

	/**
	 * @param snsnTagDesc the snsnTagDesc to set
	 */
	public void setSnsnTagDesc(String snsnTagDesc) {
		this.snsnTagDesc = snsnTagDesc;
	}

	/**
	 * @return the indcOdrg
	 */
	public Integer getIndcOdrg() {
		return indcOdrg;
	}

	/**
	 * @param indcOdrg the indcOdrg to set
	 */
	public void setIndcOdrg(Integer indcOdrg) {
		this.indcOdrg = indcOdrg;
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
	
	
}
