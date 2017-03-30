package com.kt.giga.home.openapi.ghms.kafka.smcp.gw.ca.domn.gwintncnct;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.kt.giga.home.openapi.ghms.kafka.smcp.gw.ca.comm.GwAdaptorCode.GwType;
import com.kt.giga.home.openapi.ghms.kafka.smcp.gw.ca.comm.GwCode.DataLayrType;
import com.kt.giga.home.openapi.ghms.kafka.smcp.gw.ca.comm.GwCode.UseYn;

/**
 *
 * @since	: 2014. 11. 2.
 * @author	: CBJ
 * <PRE>
 * Revision History
 * ----------------------------------------------------
 * 2014. 11. 2. CBJ: 최초작성
 * ----------------------------------------------------
 * </PRE>
 */
@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class GwIntnCnctBas implements Serializable, Cloneable
{
	/** 게이트웨이연결아이디 */
	private String gwCnctId;
	/** 서비스대상일련번호 */
	private Long svcTgtSeq;
	/** 게이트웨이유형코드 */
	private GwType gwTypeCd;
	/** 단위서비스코드 */
	private String unitSvcCd;
	/** 레벨번호 */
	private Integer lvlNo;
	/** 소속서버아이디 */
	private String posSrvrId;
	/** 상위연결아이디 */
	private String upCnctId;
	/** 게이트웨이인바운드프로세서아이디 */
	private String gwInbndPrcsrId;
	/** 게이트웨이아웃바운드프로세서아이디 */
	private String gwOtbndPrcsrId;
	/** 상태코드 */
	private String sttusCd;
	/** 수집주기시간 */
	private Integer colecCyclTime;
	/** 유휴판단기준시간 */
	private Integer idleJdgmBaseTime;
	/** 재접속주기시간 */
	private Integer recnCyclTime;
	/** 재접속시도횟수 */
	private Integer recnTryTmscnt;
	/** 행제한여부 */
	private UseYn rowRstrtnYn;
	/** 행초최대수 */
	private Long rowSecMaxNum;
	/** 행10초최대수 */
	private Long row10secMaxNum;
	/** 행분최대수 */
	private Long rowMnutMaxNum;
	/** 행10분최대수 */
	private Long row10mnutMaxNum;
	/** 데이터레이어유형코드 */
	private DataLayrType dataLayrTypeCd;
	/** 수신데이터저장여부 */
	private UseYn rcvDataStoreYn;
	/** 수신데이터갱신여부 */
	private UseYn rcvDataUpdYn;
	/** 오류데이터저장여부 */
	private UseYn errDataStoreYn;
	/** 인증방식코드 */
	private String athnFormlCd;
	/** 인증요청번호 */
	private String athnRqtNo;
	/** 인증번호 */
	private String athnNo;
	/** 설치위치내용 */
	private String eqpLoSbst;
	/** 생성자아이디 */
	private String cretrId;
	/** 생성일시 */
	private String cretDt;
	/** 수정자아이디 */
	private String amdrId;
	/** 수정일시 */
	private String amdDt;
	/** 최종동작일시 */
	private String lastMtnDt;
	/** 비고 */
	private String rmark;
	/** 추가속성여부 */
	private UseYn apdAtribYn;
	/** 사용여부 */
	private UseYn useYn;

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.MULTI_LINE_STYLE);
	}

	@Override
	public Object clone() throws CloneNotSupportedException
	{
		return super.clone();
	}


	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode()
	{
		if(gwCnctId == null)
		{
			return 0;
		}
		return gwCnctId.hashCode();
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj)
	{
		if( !(obj instanceof GwIntnCnctBas) )
		{
			return false;
		}

		GwIntnCnctBas gwIntnCnctBas = (GwIntnCnctBas)obj;

		if(gwIntnCnctBas.gwCnctId != null &&
				this.gwCnctId  != null)
		{
			if(gwIntnCnctBas.gwCnctId.equals(this.gwCnctId))
			{
				return true;
			}
		}
		return false;
	}


	/**
	 * @return the gwCnctId
	 */
	public String getGwCnctId() {
		return gwCnctId;
	}

	/**
	 * @param gwCnctId the gwCnctId to set
	 */
	public void setGwCnctId(String gwCnctId) {
		this.gwCnctId = gwCnctId;
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



//	/**
//	 * @return the gwCnctDivCd
//	 */
//	public GwCnctDiv getGwCnctDivCd() {
//		return gwCnctDivCd;
//	}
//
//	/**
//	 * @param gwCnctDivCd the gwCnctDivCd to set
//	 */
//	public void setGwCnctDivCd(String gwCnctDivCd) {
//		this.gwCnctDivCd = GwCnctDiv.fromString(gwCnctDivCd);
//	}

	public String getUnitSvcCd() {
		return unitSvcCd;
	}

	public void setUnitSvcCd(String unitSvcCd) {
		this.unitSvcCd = unitSvcCd;
	}

	/**
	 * @return the lvlNo
	 */
	public Integer getLvlNo() {
		return lvlNo;
	}

	/**
	 * @param lvlNo the lvlNo to set
	 */
	public void setLvlNo(Integer lvlNo) {
		this.lvlNo = lvlNo;
	}

//	/**
//	 * @return the upCnctDivCd
//	 */
//	public String getUpCnctDivCd() {
//		return upCnctDivCd;
//	}
//
//	/**
//	 * @param upCnctDivCd the upCnctDivCd to set
//	 */
//	public void setUpCnctDivCd(String upCnctDivCd) {
//		this.upCnctDivCd = upCnctDivCd;
//	}

	/**
	 * @return the posSrvrId
	 */
	public String getPosSrvrId() {
		return posSrvrId;
	}

	/**
	 * @param posSrvrId the posSrvrId to set
	 */
	public void setPosSrvrId(String posSrvrId) {
		this.posSrvrId = posSrvrId;
	}

	/**
	 * @return the upCnctId
	 */
	public String getUpCnctId() {
		return upCnctId;
	}

	/**
	 * @param upCnctId the upCnctId to set
	 */
	public void setUpCnctId(String upCnctId) {
		this.upCnctId = upCnctId;
	}



//	/**
//	 * @return the ipadr
//	 */
//	public String getIpadr() {
//		return ipadr;
//	}
//
//	/**
//	 * @param ipadr the ipadr to set
//	 */
//	public void setIpadr(String ipadr) {
//		this.ipadr = ipadr;
//	}
//
//	/**
//	 * @return the msrUpIfTypeCd
//	 */
//	public String getMsrUpIfTypeCd() {
//		return msrUpIfTypeCd;
//	}
//
//	/**
//	 * @param msrUpIfTypeCd the msrUpIfTypeCd to set
//	 */
//	public void setMsrUpIfTypeCd(String msrUpIfTypeCd) {
//		this.msrUpIfTypeCd = msrUpIfTypeCd;
//	}
//
//	/**
//	 * @return the msrRpyCnctTypeCd
//	 */
//	public String getMsrRpyCnctTypeCd() {
//		return msrRpyCnctTypeCd;
//	}
//
//	/**
//	 * @param msrRpyCnctTypeCd the msrRpyCnctTypeCd to set
//	 */
//	public void setMsrRpyCnctTypeCd(String msrRpyCnctTypeCd) {
//		this.msrRpyCnctTypeCd = msrRpyCnctTypeCd;
//	}
//
//	/**
//	 * @return the msrRpyCnctAdr
//	 */
//	public String getMsrRpyCnctAdr() {
//		return msrRpyCnctAdr;
//	}
//
//	/**
//	 * @param msrRpyCnctAdr the msrRpyCnctAdr to set
//	 */
//	public void setMsrRpyCnctAdr(String msrRpyCnctAdr) {
//		this.msrRpyCnctAdr = msrRpyCnctAdr;
//	}
//
//	/**
//	 * @return the msrRpyProtCd
//	 */
//	public String getMsrRpyProtCd() {
//		return msrRpyProtCd;
//	}
//
//	/**
//	 * @param msrRpyProtCd the msrRpyProtCd to set
//	 */
//	public void setMsrRpyProtCd(String msrRpyProtCd) {
//		this.msrRpyProtCd = msrRpyProtCd;
//	}
//
//	/**
//	 * @return the msrRcvCnctTypeCd
//	 */
//	public String getMsrRcvCnctTypeCd() {
//		return msrRcvCnctTypeCd;
//	}
//
//	/**
//	 * @param msrRcvCnctTypeCd the msrRcvCnctTypeCd to set
//	 */
//	public void setMsrRcvCnctTypeCd(String msrRcvCnctTypeCd) {
//		this.msrRcvCnctTypeCd = msrRcvCnctTypeCd;
//	}
//
//	/**
//	 * @return the msrRcvCnctAdr
//	 */
//	public String getMsrRcvCnctAdr() {
//		return msrRcvCnctAdr;
//	}
//
//	/**
//	 * @param msrRcvCnctAdr the msrRcvCnctAdr to set
//	 */
//	public void setMsrRcvCnctAdr(String msrRcvCnctAdr) {
//		this.msrRcvCnctAdr = msrRcvCnctAdr;
//	}
//
//	/**
//	 * @return the msrRcvProtCd
//	 */
//	public String getMsrRcvProtCd() {
//		return msrRcvProtCd;
//	}
//
//	/**
//	 * @param msrRcvProtCd the msrRcvProtCd to set
//	 */
//	public void setMsrRcvProtCd(String msrRcvProtCd) {
//		this.msrRcvProtCd = msrRcvProtCd;
//	}
//
//	/**
//	 * @return the sttusUpIfTypeCd
//	 */
//	public String getSttusUpIfTypeCd() {
//		return sttusUpIfTypeCd;
//	}
//
//	/**
//	 * @param sttusUpIfTypeCd the sttusUpIfTypeCd to set
//	 */
//	public void setSttusUpIfTypeCd(String sttusUpIfTypeCd) {
//		this.sttusUpIfTypeCd = sttusUpIfTypeCd;
//	}
//
//	/**
//	 * @return the sttusRpyCnctTypeCd
//	 */
//	public String getSttusRpyCnctTypeCd() {
//		return sttusRpyCnctTypeCd;
//	}
//
//	/**
//	 * @param sttusRpyCnctTypeCd the sttusRpyCnctTypeCd to set
//	 */
//	public void setSttusRpyCnctTypeCd(String sttusRpyCnctTypeCd) {
//		this.sttusRpyCnctTypeCd = sttusRpyCnctTypeCd;
//	}
//
//	/**
//	 * @return the sttusRpyCnctAdr
//	 */
//	public String getSttusRpyCnctAdr() {
//		return sttusRpyCnctAdr;
//	}
//
//	/**
//	 * @param sttusRpyCnctAdr the sttusRpyCnctAdr to set
//	 */
//	public void setSttusRpyCnctAdr(String sttusRpyCnctAdr) {
//		this.sttusRpyCnctAdr = sttusRpyCnctAdr;
//	}
//
//	/**
//	 * @return the sttusRpyProtCd
//	 */
//	public String getSttusRpyProtCd() {
//		return sttusRpyProtCd;
//	}
//
//	/**
//	 * @param sttusRpyProtCd the sttusRpyProtCd to set
//	 */
//	public void setSttusRpyProtCd(String sttusRpyProtCd) {
//		this.sttusRpyProtCd = sttusRpyProtCd;
//	}
//
//	/**
//	 * @return the sttusRcvCnctTypeCd
//	 */
//	public String getSttusRcvCnctTypeCd() {
//		return sttusRcvCnctTypeCd;
//	}
//
//	/**
//	 * @param sttusRcvCnctTypeCd the sttusRcvCnctTypeCd to set
//	 */
//	public void setSttusRcvCnctTypeCd(String sttusRcvCnctTypeCd) {
//		this.sttusRcvCnctTypeCd = sttusRcvCnctTypeCd;
//	}
//
//	/**
//	 * @return the sttusRcvCnctAdr
//	 */
//	public String getSttusRcvCnctAdr() {
//		return sttusRcvCnctAdr;
//	}
//
//	/**
//	 * @param sttusRcvCnctAdr the sttusRcvCnctAdr to set
//	 */
//	public void setSttusRcvCnctAdr(String sttusRcvCnctAdr) {
//		this.sttusRcvCnctAdr = sttusRcvCnctAdr;
//	}
//
//	/**
//	 * @return the sttusRcvProtCd
//	 */
//	public String getSttusRcvProtCd() {
//		return sttusRcvProtCd;
//	}
//
//	/**
//	 * @param sttusRcvProtCd the sttusRcvProtCd to set
//	 */
//	public void setSttusRcvProtCd(String sttusRcvProtCd) {
//		this.sttusRcvProtCd = sttusRcvProtCd;
//	}
//
//	/**
//	 * @return the contlUpIfTypeCd
//	 */
//	public String getContlUpIfTypeCd() {
//		return contlUpIfTypeCd;
//	}
//
//	/**
//	 * @param contlUpIfTypeCd the contlUpIfTypeCd to set
//	 */
//	public void setContlUpIfTypeCd(String contlUpIfTypeCd) {
//		this.contlUpIfTypeCd = contlUpIfTypeCd;
//	}
//
//	/**
//	 * @return the contlRpyCnctTypeCd
//	 */
//	public String getContlRpyCnctTypeCd() {
//		return contlRpyCnctTypeCd;
//	}
//
//	/**
//	 * @param contlRpyCnctTypeCd the contlRpyCnctTypeCd to set
//	 */
//	public void setContlRpyCnctTypeCd(String contlRpyCnctTypeCd) {
//		this.contlRpyCnctTypeCd = contlRpyCnctTypeCd;
//	}
//
//	/**
//	 * @return the contlRpyCnctAdr
//	 */
//	public String getContlRpyCnctAdr() {
//		return contlRpyCnctAdr;
//	}
//
//	/**
//	 * @param contlRpyCnctAdr the contlRpyCnctAdr to set
//	 */
//	public void setContlRpyCnctAdr(String contlRpyCnctAdr) {
//		this.contlRpyCnctAdr = contlRpyCnctAdr;
//	}
//
//	/**
//	 * @return the contlRpyProtCd
//	 */
//	public String getContlRpyProtCd() {
//		return contlRpyProtCd;
//	}
//
//	/**
//	 * @param contlRpyProtCd the contlRpyProtCd to set
//	 */
//	public void setContlRpyProtCd(String contlRpyProtCd) {
//		this.contlRpyProtCd = contlRpyProtCd;
//	}
//
//	/**
//	 * @return the contlRcvCnctTypeCd
//	 */
//	public String getContlRcvCnctTypeCd() {
//		return contlRcvCnctTypeCd;
//	}
//
//	/**
//	 * @param contlRcvCnctTypeCd the contlRcvCnctTypeCd to set
//	 */
//	public void setContlRcvCnctTypeCd(String contlRcvCnctTypeCd) {
//		this.contlRcvCnctTypeCd = contlRcvCnctTypeCd;
//	}
//
//	/**
//	 * @return the contlRcvCnctAdr
//	 */
//	public String getContlRcvCnctAdr() {
//		return contlRcvCnctAdr;
//	}
//
//	/**
//	 * @param contlRcvCnctAdr the contlRcvCnctAdr to set
//	 */
//	public void setContlRcvCnctAdr(String contlRcvCnctAdr) {
//		this.contlRcvCnctAdr = contlRcvCnctAdr;
//	}
//
//	/**
//	 * @return the contlRcvProtCd
//	 */
//	public String getContlRcvProtCd() {
//		return contlRcvProtCd;
//	}
//
//	/**
//	 * @param contlRcvProtCd the contlRcvProtCd to set
//	 */
//	public void setContlRcvProtCd(String contlRcvProtCd) {
//		this.contlRcvProtCd = contlRcvProtCd;
//	}

	/**
	 * @return the gwInbndPrcsrId
	 */
	public String getGwInbndPrcsrId() {
		return gwInbndPrcsrId;
	}

	/**
	 * @param gwInbndPrcsrId the gwInbndPrcsrId to set
	 */
	public void setGwInbndPrcsrId(String gwInbndPrcsrId) {
		this.gwInbndPrcsrId = gwInbndPrcsrId;
	}

	/**
	 * @return the gwOtbndPrcsrId
	 */
	public String getGwOtbndPrcsrId() {
		return gwOtbndPrcsrId;
	}

	/**
	 * @param gwOtbndPrcsrId the gwOtbndPrcsrId to set
	 */
	public void setGwOtbndPrcsrId(String gwOtbndPrcsrId) {
		this.gwOtbndPrcsrId = gwOtbndPrcsrId;
	}

	/**
	 * @return the sttusCd
	 */
	public String getSttusCd() {
		return sttusCd;
	}

	/**
	 * @param sttusCd the sttusCd to set
	 */
	public void setSttusCd(String sttusCd) {
		this.sttusCd = sttusCd;
	}

	/**
	 * @return the colecCyclTime
	 */
	public Integer getColecCyclTime() {
		return colecCyclTime;
	}

	/**
	 * @param colecCyclTime the colecCyclTime to set
	 */
	public void setColecCyclTime(Integer colecCyclTime) {
		this.colecCyclTime = colecCyclTime;
	}

	/**
	 * @return the idleJdgmBaseTime
	 */
	public Integer getIdleJdgmBaseTime() {
		return idleJdgmBaseTime;
	}

	/**
	 * @param idleJdgmBaseTime the idleJdgmBaseTime to set
	 */
	public void setIdleJdgmBaseTime(Integer idleJdgmBaseTime) {
		this.idleJdgmBaseTime = idleJdgmBaseTime;
	}

	/**
	 * @return the recnCyclTime
	 */
	public Integer getRecnCyclTime() {
		return recnCyclTime;
	}

	/**
	 * @param recnCyclTime the recnCyclTime to set
	 */
	public void setRecnCyclTime(Integer recnCyclTime) {
		this.recnCyclTime = recnCyclTime;
	}

	/**
	 * @return the recnTryTmscnt
	 */
	public Integer getRecnTryTmscnt() {
		return recnTryTmscnt;
	}

	/**
	 * @param recnTryTmscnt the recnTryTmscnt to set
	 */
	public void setRecnTryTmscnt(Integer recnTryTmscnt) {
		this.recnTryTmscnt = recnTryTmscnt;
	}




	/**
	 * @return the dataLayrTypeCd
	 */
	public DataLayrType getDataLayrTypeCd() {
		return dataLayrTypeCd;
	}

	/**
	 * @param dataLayrTypeCd the dataLayrTypeCd to set
	 */
	public void setDataLayrTypeCd(String dataLayrTypeCd) {
		this.dataLayrTypeCd = DataLayrType.fromString(dataLayrTypeCd);
	}

	/**
	 * @return the rcvDataStoreYn
	 */
	public UseYn getRcvDataStoreYn() {
		return rcvDataStoreYn;
	}

	/**
	 * @param rcvDataStoreYn the rcvDataStoreYn to set
	 */
	public void setRcvDataStoreYn(String rcvDataStoreYn) {
		this.rcvDataStoreYn = UseYn.fromString(rcvDataStoreYn);
	}

	/**
	 * @return the rcvDataUpdYn
	 */
	public UseYn getRcvDataUpdYn() {
		return rcvDataUpdYn;
	}

	/**
	 * @param rcvDataUpdYn the rcvDataUpdYn to set
	 */
	public void setRcvDataUpdYn(String rcvDataUpdYn) {
		this.rcvDataUpdYn = UseYn.fromString(rcvDataUpdYn);
	}

	/**
	 * @return the errDataStoreYn
	 */
	public UseYn getErrDataStoreYn() {
		return errDataStoreYn;
	}

	/**
	 * @param errDataStoreYn the errDataStoreYn to set
	 */
	public void setErrDataStoreYn(String errDataStoreYn) {
		this.errDataStoreYn = UseYn.fromString(errDataStoreYn);
	}

	/**
	 * @return the athnFormlCd
	 */
	public String getAthnFormlCd() {
		return athnFormlCd;
	}

	/**
	 * @param athnFormlCd the athnFormlCd to set
	 */
	public void setAthnFormlCd(String athnFormlCd) {
		this.athnFormlCd = athnFormlCd;
	}

	/**
	 * @return the athnRqtNo
	 */
	public String getAthnRqtNo() {
		return athnRqtNo;
	}

	/**
	 * @param athnRqtNo the athnRqtNo to set
	 */
	public void setAthnRqtNo(String athnRqtNo) {
		this.athnRqtNo = athnRqtNo;
	}

	/**
	 * @return the athnNo
	 */
	public String getAthnNo() {
		return athnNo;
	}

	/**
	 * @param athnNo the athnNo to set
	 */
	public void setAthnNo(String athnNo) {
		this.athnNo = athnNo;
	}

	/**
	 * @return the eqpLoSbst
	 */
	public String getEqpLoSbst() {
		return eqpLoSbst;
	}

	/**
	 * @param eqpLoSbst the eqpLoSbst to set
	 */
	public void setEqpLoSbst(String eqpLoSbst) {
		this.eqpLoSbst = eqpLoSbst;
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

	/**
	 * @return the lastMtnDt
	 */
	public String getLastMtnDt() {
		return lastMtnDt;
	}

	/**
	 * @param lastMtnDt the lastMtnDt to set
	 */
	public void setLastMtnDt(String lastMtnDt) {
		this.lastMtnDt = lastMtnDt;
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
	 * @return the apdAtribYn
	 */
	public UseYn getApdAtribYn() {
		return apdAtribYn;
	}

	/**
	 * @param apdAtribYn the apdAtribYn to set
	 */
	public void setApdAtribYn(String apdAtribYn) {
		this.apdAtribYn = UseYn.fromString(apdAtribYn);
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

//	/**
//	 * @return the prcsChkYn
//	 */
//	public String getPrcsChkYn() {
//		return prcsChkYn;
//	}
//
//	/**
//	 * @param prcsChkYn the prcsChkYn to set
//	 */
//	public void setPrcsChkYn(String prcsChkYn) {
//		this.prcsChkYn = prcsChkYn;
//	}
//
//	/**
//	 * @return the prcsChkCyclTime
//	 */
//	public Integer getPrcsChkCyclTime() {
//		return prcsChkCyclTime;
//	}
//
//	/**
//	 * @param prcsChkCyclTime the prcsChkCyclTime to set
//	 */
//	public void setPrcsChkCyclTime(Integer prcsChkCyclTime) {
//		this.prcsChkCyclTime = prcsChkCyclTime;
//	}
//
//	/**
//	 * @return the prcsNm
//	 */
//	public String getPrcsNm() {
//		return prcsNm;
//	}
//
//	/**
//	 * @param prcsNm the prcsNm to set
//	 */
//	public void setPrcsNm(String prcsNm) {
//		this.prcsNm = prcsNm;
//	}
//
//	/**
//	 * @return the prcsRepPortNo
//	 */
//	public Integer getPrcsRepPortNo() {
//		return prcsRepPortNo;
//	}
//
//	/**
//	 * @param prcsRepPortNo the prcsRepPortNo to set
//	 */
//	public void setPrcsRepPortNo(Integer prcsRepPortNo) {
//		this.prcsRepPortNo = prcsRepPortNo;
//	}
//
//	/**
//	 * @return the sttusChkYn
//	 */
//	public String getSttusChkYn() {
//		return sttusChkYn;
//	}
//
//	/**
//	 * @param sttusChkYn the sttusChkYn to set
//	 */
//	public void setSttusChkYn(String sttusChkYn) {
//		this.sttusChkYn = sttusChkYn;
//	}
//
//	/**
//	 * @return the sttusChkCyclTime
//	 */
//	public Integer getSttusChkCyclTime() {
//		return sttusChkCyclTime;
//	}
//
//	/**
//	 * @param sttusChkCyclTime the sttusChkCyclTime to set
//	 */
//	public void setSttusChkCyclTime(Integer sttusChkCyclTime) {
//		this.sttusChkCyclTime = sttusChkCyclTime;
//	}

	/**
	 * @return the rowRstrtnYn
	 */
	public UseYn getRowRstrtnYn() {
		return rowRstrtnYn;
	}

	/**
	 * @param rowRstrtnYn the rowRstrtnYn to set
	 */
	public void setRowRstrtnYn(String rowRstrtnYn) {
		this.rowRstrtnYn = UseYn.fromString(rowRstrtnYn);
	}

	/**
	 * @return the rowSecMaxNum
	 */
	public Long getRowSecMaxNum() {
		return rowSecMaxNum;
	}

	/**
	 * @param rowSecMaxNum the rowSecMaxNum to set
	 */
	public void setRowSecMaxNum(Long rowSecMaxNum) {
		this.rowSecMaxNum = rowSecMaxNum;
	}

	/**
	 * @return the row10secMaxNum
	 */
	public Long getRow10secMaxNum() {
		return row10secMaxNum;
	}

	/**
	 * @param row10secMaxNum the row10secMaxNum to set
	 */
	public void setRow10secMaxNum(Long row10secMaxNum) {
		this.row10secMaxNum = row10secMaxNum;
	}

	/**
	 * @return the rowMnutMaxNum
	 */
	public Long getRowMnutMaxNum() {
		return rowMnutMaxNum;
	}

	/**
	 * @param rowMnutMaxNum the rowMnutMaxNum to set
	 */
	public void setRowMnutMaxNum(Long rowMnutMaxNum) {
		this.rowMnutMaxNum = rowMnutMaxNum;
	}

	/**
	 * @return the row10mnutMaxNum
	 */
	public Long getRow10mnutMaxNum() {
		return row10mnutMaxNum;
	}

	/**
	 * @param row10mnutMaxNum the row10mnutMaxNum to set
	 */
	public void setRow10mnutMaxNum(Long row10mnutMaxNum) {
		this.row10mnutMaxNum = row10mnutMaxNum;
	}

}
