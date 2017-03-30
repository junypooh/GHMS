package com.kt.giga.home.openapi.vo.spotdev;


/**
 * 현장장치Key
 * @since	: 2014. 11. 6.
 * @author	: CBJ
 * <PRE>
 * Revision History
 * ----------------------------------------------------
 * 2014. 11. 6. CBJ: 최초작성
 * 2014. 11.16. 조홍진: OpenAPI 커스토마이징 
 * ----------------------------------------------------
 * </PRE>
 */
public class SpotDevKey
{
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
	
	public SpotDevKey() {
		
	}
	
	public SpotDevKey(Long svcTgtSeq, Long spotDevSeq) {
		this.svcTgtSeq = svcTgtSeq;
		this.spotDevSeq = spotDevSeq;
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
	
}
