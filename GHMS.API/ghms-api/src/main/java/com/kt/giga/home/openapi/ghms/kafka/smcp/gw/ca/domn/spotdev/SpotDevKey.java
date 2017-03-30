package com.kt.giga.home.openapi.ghms.kafka.smcp.gw.ca.domn.spotdev;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.kt.giga.home.openapi.ghms.kafka.smcp.gw.ca.comm.GwCode.DevCnctType;


/**
 * 현장장치Key
 * @since	: 2014. 11. 6.
 * @author	: CBJ
 * <PRE>
 * Revision History
 * ----------------------------------------------------
 * 2014. 11. 6. CBJ: 최초작성
 * ----------------------------------------------------
 * </PRE>
 */
public class SpotDevKey implements Serializable, Cloneable
{
	/** 직렬화아이디 */
	private static final long serialVersionUID = -4050831110058895636L;

	/** 서비스대상일련번호 */
	private Long svcTgtSeq;
	/** 현장장치일련번호 */
	private Long spotDevSeq;
	/** 게이트웨이연결아이디 */
	private String gwCnctId;
	/** M2M서비스번호 */
	private Integer m2mSvcNo;
	/** 현장장치아이디 */
	private String spotDevId;
	/** 상위서비스대상일련번호(3M에서는 필요한가?) */
	private Long upSvcTgtSeq;
	/** 상위게이트웨이연결아이디(3M에서는 필요한가?) */
	private String upGwCnctId;
	/** 상위현장장치아이디(3M에서는 필요한가?) */
	private String upSpotDevId;
	/**장치연결유형코드  */
	private DevCnctType devConnTypeCd;


	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.SIMPLE_STYLE);
	}

	@Override
	public Object clone() throws CloneNotSupportedException
	{
		return super.clone();
	}

	public Long getSvcTgtSeq() {
		return svcTgtSeq;
	}

	public void setSvcTgtSeq(Long svcTgtSeq) {
		this.svcTgtSeq = svcTgtSeq;
	}

	public Long getSpotDevSeq() {
		return spotDevSeq;
	}

	public void setSpotDevSeq(Long spotDevSeq) {
		this.spotDevSeq = spotDevSeq;
	}

	public String getGwCnctId() {
		return gwCnctId;
	}

	public void setGwCnctId(String gwCnctId) {
		this.gwCnctId = gwCnctId;
	}

	public Integer getM2mSvcNo() {
		return m2mSvcNo;
	}

	public void setM2mSvcNo(Integer m2mSvcNo) {
		this.m2mSvcNo = m2mSvcNo;
	}

	public String getSpotDevId() {
		return spotDevId;
	}

	public void setSpotDevId(String spotDevId) {
		this.spotDevId = spotDevId;
	}



	public Long getUpSvcTgtSeq() {
		return upSvcTgtSeq;
	}

	public void setUpSvcTgtSeq(Long upSvcTgtSeq) {
		this.upSvcTgtSeq = upSvcTgtSeq;
	}

	public String getUpGwCnctId() {
		return upGwCnctId;
	}

	public void setUpGwCnctId(String upGwCnctId) {
		this.upGwCnctId = upGwCnctId;
	}

	public String getUpSpotDevId() {
		return upSpotDevId;
	}

	public void setUpSpotDevId(String upSpotDevId) {
		this.upSpotDevId = upSpotDevId;
	}

	public DevCnctType getDevConnTypeCd() {
		return devConnTypeCd;
	}

	public void setDevConnTypeCd(DevCnctType devConnTypeCd) {
		this.devConnTypeCd = devConnTypeCd;
	}




}
