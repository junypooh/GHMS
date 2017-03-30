package com.kt.giga.home.openapi.ghms.kafka.smcp.gw.ca.domn.commnagnt;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.kt.giga.home.openapi.ghms.kafka.smcp.gw.ca.comm.GwCode.AthnForml;
import com.kt.giga.home.openapi.ghms.kafka.smcp.gw.ca.comm.GwCode.CommnAgntSttus;
import com.kt.giga.home.openapi.ghms.kafka.smcp.gw.ca.comm.GwCode.UseYn;

@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class CommnAgntBas implements Serializable {
	/** 커뮤니케이션대리인아이디 */
	private String commnAgntId;
	/** 커뮤니케이션대리인명 */
	private String commnAgntNm;
	/** IP주소 */
	private String ipadr;
	/** 관리UI여부 */
	private UseYn mgtUiYn;
	/** 연계모듈여부 */
	private UseYn lnkModulYn;
	/** 캐쉬정상여부 */
	private UseYn cacheNrmlYn;

//	/** 측정상위인터페이스유형코드 */
//	private IfType msrUpIfTypeCd;
//	/** 측정응답연결유형코드 */
//	private CnctType msrRpyCnctTypeCd;
//	/** 측정응답연결주소 */
//	private String msrRpyCnctAdr;
//	/** 측정응답프로토콜코드 */
//	private DataProtType msrRpyProtCd;
//	/** 측정응답암호화여부 */
//	private UseYn msrRpyEcodYn;
//	/** 측정응답암호화유형코드 */
//	private EcodType msrRpyEcodTypeCd;
//	/** 측정응답암호화키값 */
//	private String msrRpyEcodKeyVal;
//	/** 측정수신연결유형코드 */
//	private CnctType msrRcvCnctTypeCd;
//	/** 측정수신연결주소 */
//	private String msrRcvCnctAdr;
//	/** 측정수신프로토콜코드 */
//	private DataProtType msrRcvProtCd;
//	/** 상태상위인터페이스유형코드 */
//	private IfType sttusUpIfTypeCd;
//	/** 상태응답연결유형코드 */
//	private CnctType sttusRpyCnctTypeCd;
//	/** 상태응답연결주소 */
//	private String sttusRpyCnctAdr;
//	/** 상태응답프로토콜코드 */
//	private DataProtType sttusRpyProtCd;
//	/** 상태응답암호화여부 */
//	private UseYn sttusRpyEcodYn;
//	/** 상태응답암호화유형코드 */
//	private EcodType sttusRpyEcodTypeCd;
//	/** 상태응답암호화키값 */
//	private String sttusRpyEcodKeyVal;
//	/** 상태수신연결유형코드 */
//	private CnctType sttusRcvCnctTypeCd;
//	/** 상태수신연결주소 */
//	private String sttusRcvCnctAdr;
//	/** 상태수신프로토콜코드 */
//	private DataProtType sttusRcvProtCd;
//	/** 제어상위인터페이스유형코드 */
//	private IfType contlUpIfTypeCd;
//	/** 제어응답연결유형코드 */
//	private CnctType contlRpyCnctTypeCd;
//	/** 제어응답연결주소 */
//	private String contlRpyCnctAdr;
//	/** 제어응답프로토콜코드 */
//	private DataProtType contlRpyProtCd;
//	/** 제어응답암호화여부 */
//	private UseYn contlRpyEcodYn;
//	/** 제어응답암호화유형코드 */
//	private EcodType contlRpyEcodTypeCd;
//	/** 제어응답암호화키값 */
//	private String contlRpyEcodKeyVal;
//	/** 제어수신연결유형코드 */
//	private CnctType contlRcvCnctTypeCd;
//	/** 제어수신연결주소 */
//	private String contlRcvCnctAdr;
//	/** 제어수신프로토콜코드 */
//	private DataProtType contlRcvProtCd;
//	/** 질의요청연결유형코드 */
//	private CnctType qryRqtCnctTypeCd;
//	/** 질의요청연결주소 */
//	private String qryRqtCnctAdr;
//	/** 질의요청프로토콜코드 */
//	private DataProtType qryRqtProtCd;
//	/** 질의요청인증방식코드 */
//	private AthnForml qryRqtAthnFormlCd;
//	/** 질의요청인증번호 */
//	private String qryRqtAthnNo;
//	/** 질의요청암호화여부 */
//	private UseYn qryRqtEcodYn;
//	/** 질의요청암호화유형코드 */
//	private EcodType qryRqtEcodTypeCd;
//	/** 질의요청암호화키값 */
//	private String qryRqtEcodKeyVal;
	/** 상태코드 */
	private CommnAgntSttus sttusCd;
	/** 수집주기시간 */
	private Integer colecCyclTime;
	/** 유휴판단기준시간 */
	private Integer idleJdgmBaseTime;
	/** 재접속주기시간 */
	private Integer recnCyclTime;
	/** 재접속시도횟수 */
	private Integer recnTryTmscnt;
	/** 인증방식코드 */
	private AthnForml athnFormlCd;
	/** 인증요청번호 */
	private String athnRqtNo;
	/** 인증번호 */
	private String athnNo;
	/** 설치위치내용 */
	private String eqpLoSbst;
	/** 생성일시 */
	private Date cretDt;
	/** 생성자아이디 */
	private String cretrId;
	/** 수정자아이디 */
	private String amdrId;
	/** 수정일시 */
	private Date amdDt;
	/** 최종동작일시 */
	private String lastMtnDt;
	/** 비고 */
	private String rmark;
	/** 사용여부 */
	private UseYn useYn;
	/** 프로세스점검여부 */
	private UseYn prcsChkYn;
	/** 프로세스점검주기시간 */
	private Long prcsChkCyclTime;
	/** 프로세스명 */
	private String prcsNm;
	/** 프로세스대표포트번호 */
	private Long prcsRepPortNo;
	/** 내부자원점검여부 */
	private UseYn intnRiChkYn;
	/** 내부자원점검주기시간 */
	private Long intnRiChkCyclTime;

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.MULTI_LINE_STYLE);
	}

	@Override
	public int hashCode()
	{
		if(commnAgntId == null)
		{
			return 0;
		}
		return commnAgntId.hashCode();
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj)
	{
		if( !(obj instanceof CommnAgntBas) )
		{
			return false;
		}

		CommnAgntBas commnAgntBas = (CommnAgntBas)obj;

		if(commnAgntBas.commnAgntId != null &&
				this.commnAgntId  != null)
		{
			if(commnAgntBas.commnAgntId.equals(this.commnAgntId))
			{
				return true;
			}
		}
		return false;
	}

	/**
	 * @return the commnAgntId
	 */
	public String getCommnAgntId() {
		return commnAgntId;
	}

	/**
	 * @param commnAgntId the commnAgntId to set
	 */
	public void setCommnAgntId(String commnAgntId) {
		this.commnAgntId = commnAgntId;
	}

	/**
	 * @return the commnAgntNm
	 */
	public String getCommnAgntNm() {
		return commnAgntNm;
	}

	/**
	 * @param commnAgntNm the commnAgntNm to set
	 */
	public void setCommnAgntNm(String commnAgntNm) {
		this.commnAgntNm = commnAgntNm;
	}

	/**
	 * @return the ipadr
	 */
	public String getIpadr() {
		return ipadr;
	}

	/**
	 * @param ipadr the ipadr to set
	 */
	public void setIpadr(String ipadr) {
		this.ipadr = ipadr;
	}

	/**
	 * @return the mgtUiYn
	 */
	public UseYn getMgtUiYn() {
		return mgtUiYn;
	}


	/**
	 * @param mgtUiYn the mgtUiYn to set
	 */
	public void setMgtUiYn(String mgtUiYn) {
		this.mgtUiYn = UseYn.fromString(mgtUiYn);
	}


	/**
	 * @return the lnkModulYn
	 */
	public UseYn getLnkModulYn() {
		return lnkModulYn;
	}


	/**
	 * @param lnkModulYn the lnkModulYn to set
	 */
	public void setLnkModulYn(String lnkModulYn) {
		this.lnkModulYn = UseYn.fromString(lnkModulYn);
	}



//	/**
//	 * @return the msrUpIfTypeCd
//	 */
//	public IfType getMsrUpIfTypeCd() {
//		return msrUpIfTypeCd;
//	}
//
//
//	/**
//	 * @param msrUpIfTypeCd the msrUpIfTypeCd to set
//	 */
//	public void setMsrUpIfTypeCd(String msrUpIfTypeCd) {
//		this.msrUpIfTypeCd = IfType.fromString(msrUpIfTypeCd);
//	}
//
//
//	/**
//	 * @return the msrRpyCnctTypeCd
//	 */
//	public CnctType getMsrRpyCnctTypeCd() {
//		return msrRpyCnctTypeCd;
//	}
//
//	/**
//	 * @param msrRpyCnctTypeCd the msrRpyCnctTypeCd to set
//	 */
//	public void setMsrRpyCnctTypeCd(String msrRpyCnctTypeCd) {
//		this.msrRpyCnctTypeCd = CnctType.fromString(msrRpyCnctTypeCd);
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
//	public DataProtType getMsrRpyProtCd() {
//		return msrRpyProtCd;
//	}
//
//	/**
//	 * @param msrRpyProtCd the msrRpyProtCd to set
//	 */
//	public void setMsrRpyProtCd(String msrRpyProtCd) {
//		this.msrRpyProtCd = DataProtType.fromString(msrRpyProtCd);
//	}
//
//	/**
//	 * @return the msrRpyEcodYn
//	 */
//	public UseYn getMsrRpyEcodYn() {
//		return msrRpyEcodYn;
//	}
//
//	/**
//	 * @param msrRpyEcodYn the msrRpyEcodYn to set
//	 */
//	public void setMsrRpyEcodYn(String msrRpyEcodYn) {
//		this.msrRpyEcodYn = UseYn.fromString(msrRpyEcodYn);
//	}
//
//	/**
//	 * @return the msrRpyEcodTypeCd
//	 */
//	public EcodType getMsrRpyEcodTypeCd() {
//		return msrRpyEcodTypeCd;
//	}
//
//	/**
//	 * @param msrRpyEcodTypeCd the msrRpyEcodTypeCd to set
//	 */
//	public void setMsrRpyEcodTypeCd(String msrRpyEcodTypeCd) {
//		this.msrRpyEcodTypeCd = EcodType.fromString(msrRpyEcodTypeCd);
//	}
//
//	/**
//	 * @return the msrRpyEcodKeyVal
//	 */
//	public String getMsrRpyEcodKeyVal() {
//		return msrRpyEcodKeyVal;
//	}
//
//	/**
//	 * @param msrRpyEcodKeyVal the msrRpyEcodKeyVal to set
//	 */
//	public void setMsrRpyEcodKeyVal(String msrRpyEcodKeyVal) {
//		this.msrRpyEcodKeyVal = msrRpyEcodKeyVal;
//	}
//
//	/**
//	 * @return the msrRcvCnctTypeCd
//	 */
//	public CnctType getMsrRcvCnctTypeCd() {
//		return msrRcvCnctTypeCd;
//	}
//
//	/**
//	 * @param msrRcvCnctTypeCd the msrRcvCnctTypeCd to set
//	 */
//	public void setMsrRcvCnctTypeCd(String msrRcvCnctTypeCd) {
//		this.msrRcvCnctTypeCd = CnctType.fromString(msrRcvCnctTypeCd);
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
//	public DataProtType getMsrRcvProtCd() {
//		return msrRcvProtCd;
//	}
//
//	/**
//	 * @param msrRcvProtCd the msrRcvProtCd to set
//	 */
//	public void setMsrRcvProtCd(String msrRcvProtCd) {
//		this.msrRcvProtCd = DataProtType.fromString(msrRcvProtCd);
//	}
//
//	/**
//	 * @return the sttusUpIfTypeCd
//	 */
//	public IfType getSttusUpIfTypeCd() {
//		return sttusUpIfTypeCd;
//	}
//
//	/**
//	 * @param sttusUpIfTypeCd the sttusUpIfTypeCd to set
//	 */
//	public void setSttusUpIfTypeCd(String sttusUpIfTypeCd) {
//		this.sttusUpIfTypeCd = IfType.fromString(sttusUpIfTypeCd);
//	}
//
//	/**
//	 * @return the sttusRpyCnctTypeCd
//	 */
//	public CnctType getSttusRpyCnctTypeCd() {
//		return sttusRpyCnctTypeCd;
//	}
//
//	/**
//	 * @param sttusRpyCnctTypeCd the sttusRpyCnctTypeCd to set
//	 */
//	public void setSttusRpyCnctTypeCd(String sttusRpyCnctTypeCd) {
//		this.sttusRpyCnctTypeCd = CnctType.fromString(sttusRpyCnctTypeCd);
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
//	public DataProtType getSttusRpyProtCd() {
//		return sttusRpyProtCd;
//	}
//
//	/**
//	 * @param sttusRpyProtCd the sttusRpyProtCd to set
//	 */
//	public void setSttusRpyProtCd(String sttusRpyProtCd) {
//		this.sttusRpyProtCd = DataProtType.fromString(sttusRpyProtCd);
//	}
//
//	/**
//	 * @return the sttusRpyEcodYn
//	 */
//	public UseYn getSttusRpyEcodYn() {
//		return sttusRpyEcodYn;
//	}
//
//	/**
//	 * @param sttusRpyEcodYn the sttusRpyEcodYn to set
//	 */
//	public void setSttusRpyEcodYn(String sttusRpyEcodYn) {
//		this.sttusRpyEcodYn = UseYn.fromString(sttusRpyEcodYn);
//	}
//
//	/**
//	 * @return the sttusRpyEcodTypeCd
//	 */
//	public EcodType getSttusRpyEcodTypeCd() {
//		return sttusRpyEcodTypeCd;
//	}
//
//	/**
//	 * @param sttusRpyEcodTypeCd the sttusRpyEcodTypeCd to set
//	 */
//	public void setSttusRpyEcodTypeCd(String sttusRpyEcodTypeCd) {
//		this.sttusRpyEcodTypeCd = EcodType.fromString(sttusRpyEcodTypeCd);
//	}
//
//	/**
//	 * @return the sttusRpyEcodKeyVal
//	 */
//	public String getSttusRpyEcodKeyVal() {
//		return sttusRpyEcodKeyVal;
//	}
//
//	/**
//	 * @param sttusRpyEcodKeyVal the sttusRpyEcodKeyVal to set
//	 */
//	public void setSttusRpyEcodKeyVal(String sttusRpyEcodKeyVal) {
//		this.sttusRpyEcodKeyVal = sttusRpyEcodKeyVal;
//	}
//
//	/**
//	 * @return the sttusRcvCnctTypeCd
//	 */
//	public CnctType getSttusRcvCnctTypeCd() {
//		return sttusRcvCnctTypeCd;
//	}
//
//	/**
//	 * @param sttusRcvCnctTypeCd the sttusRcvCnctTypeCd to set
//	 */
//	public void setSttusRcvCnctTypeCd(String sttusRcvCnctTypeCd) {
//		this.sttusRcvCnctTypeCd = CnctType.fromString(sttusRcvCnctTypeCd);
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
//	public DataProtType getSttusRcvProtCd() {
//		return sttusRcvProtCd;
//	}
//
//	/**
//	 * @param sttusRcvProtCd the sttusRcvProtCd to set
//	 */
//	public void setSttusRcvProtCd(String sttusRcvProtCd) {
//		this.sttusRcvProtCd = DataProtType.fromString(sttusRcvProtCd);
//	}
//
//	/**
//	 * @return the contlUpIfTypeCd
//	 */
//	public IfType getContlUpIfTypeCd() {
//		return contlUpIfTypeCd;
//	}
//
//	/**
//	 * @param contlUpIfTypeCd the contlUpIfTypeCd to set
//	 */
//	public void setContlUpIfTypeCd(String contlUpIfTypeCd) {
//		this.contlUpIfTypeCd = IfType.fromString(contlUpIfTypeCd);
//	}
//
//	/**
//	 * @return the contlRpyCnctTypeCd
//	 */
//	public CnctType getContlRpyCnctTypeCd() {
//		return contlRpyCnctTypeCd;
//	}
//
//	/**
//	 * @param contlRpyCnctTypeCd the contlRpyCnctTypeCd to set
//	 */
//	public void setContlRpyCnctTypeCd(String contlRpyCnctTypeCd) {
//		this.contlRpyCnctTypeCd = CnctType.fromString(contlRpyCnctTypeCd);
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
//	public DataProtType getContlRpyProtCd() {
//		return contlRpyProtCd;
//	}
//
//	/**
//	 * @param contlRpyProtCd the contlRpyProtCd to set
//	 */
//	public void setContlRpyProtCd(String contlRpyProtCd) {
//		this.contlRpyProtCd = DataProtType.fromString(contlRpyProtCd);
//	}
//
//	/**
//	 * @return the contlRpyEcodYn
//	 */
//	public UseYn getContlRpyEcodYn() {
//		return contlRpyEcodYn;
//	}
//
//	/**
//	 * @param contlRpyEcodYn the contlRpyEcodYn to set
//	 */
//	public void setContlRpyEcodYn(String contlRpyEcodYn) {
//		this.contlRpyEcodYn = UseYn.fromString(contlRpyEcodYn);
//	}
//
//	/**
//	 * @return the contlRpyEcodTypeCd
//	 */
//	public EcodType getContlRpyEcodTypeCd() {
//		return contlRpyEcodTypeCd;
//	}
//
//	/**
//	 * @param contlRpyEcodTypeCd the contlRpyEcodTypeCd to set
//	 */
//	public void setContlRpyEcodTypeCd(String contlRpyEcodTypeCd) {
//		this.contlRpyEcodTypeCd = EcodType.fromString(contlRpyEcodTypeCd);
//	}
//
//	/**
//	 * @return the contlRpyEcodKeyVal
//	 */
//	public String getContlRpyEcodKeyVal() {
//		return contlRpyEcodKeyVal;
//	}
//
//	/**
//	 * @param contlRpyEcodKeyVal the contlRpyEcodKeyVal to set
//	 */
//	public void setContlRpyEcodKeyVal(String contlRpyEcodKeyVal) {
//		this.contlRpyEcodKeyVal = contlRpyEcodKeyVal;
//	}
//
//	/**
//	 * @return the contlRcvCnctTypeCd
//	 */
//	public CnctType getContlRcvCnctTypeCd() {
//		return contlRcvCnctTypeCd;
//	}
//
//	/**
//	 * @param contlRcvCnctTypeCd the contlRcvCnctTypeCd to set
//	 */
//	public void setContlRcvCnctTypeCd(String contlRcvCnctTypeCd) {
//		this.contlRcvCnctTypeCd = CnctType.fromString(contlRcvCnctTypeCd);
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
//	public DataProtType getContlRcvProtCd() {
//		return contlRcvProtCd;
//	}
//
//	/**
//	 * @param contlRcvProtCd the contlRcvProtCd to set
//	 */
//	public void setContlRcvProtCd(String contlRcvProtCd) {
//		this.contlRcvProtCd = DataProtType.fromString(contlRcvProtCd);
//	}
//
//	/**
//	 * @return the qryRqtCnctTypeCd
//	 */
//	public CnctType getQryRqtCnctTypeCd() {
//		return qryRqtCnctTypeCd;
//	}
//
//	/**
//	 * @param qryRqtCnctTypeCd the qryRqtCnctTypeCd to set
//	 */
//	public void setQryRqtCnctTypeCd(String qryRqtCnctTypeCd) {
//		this.qryRqtCnctTypeCd = CnctType.fromString(qryRqtCnctTypeCd);
//	}
//
//	/**
//	 * @return the qryRqtCnctAdr
//	 */
//	public String getQryRqtCnctAdr() {
//		return qryRqtCnctAdr;
//	}
//
//	/**
//	 * @param qryRqtCnctAdr the qryRqtCnctAdr to set
//	 */
//	public void setQryRqtCnctAdr(String qryRqtCnctAdr) {
//		this.qryRqtCnctAdr = qryRqtCnctAdr;
//	}
//
//	/**
//	 * @return the qryRqtProtCd
//	 */
//	public DataProtType getQryRqtProtCd() {
//		return qryRqtProtCd;
//	}
//
//	/**
//	 * @param qryRqtProtCd the qryRqtProtCd to set
//	 */
//	public void setQryRqtProtCd(String qryRqtProtCd) {
//		this.qryRqtProtCd = DataProtType.fromString(qryRqtProtCd);
//	}
//
//	/**
//	 * @return the qryRqtAthnFormlCd
//	 */
//	public AthnForml getQryRqtAthnFormlCd() {
//		return qryRqtAthnFormlCd;
//	}
//
//	/**
//	 * @param qryRqtAthnFormlCd the qryRqtAthnFormlCd to set
//	 */
//	public void setQryRqtAthnFormlCd(String qryRqtAthnFormlCd) {
//		this.qryRqtAthnFormlCd = AthnForml.fromString(qryRqtAthnFormlCd);
//	}
//
//	/**
//	 * @return the qryRqtAthnNo
//	 */
//	public String getQryRqtAthnNo() {
//		return qryRqtAthnNo;
//	}
//
//	/**
//	 * @param qryRqtAthnNo the qryRqtAthnNo to set
//	 */
//	public void setQryRqtAthnNo(String qryRqtAthnNo) {
//		this.qryRqtAthnNo = qryRqtAthnNo;
//	}
//
//	/**
//	 * @return the qryRqtEcodYn
//	 */
//	public UseYn getQryRqtEcodYn() {
//		return qryRqtEcodYn;
//	}
//
//	/**
//	 * @param qryRqtEcodYn the qryRqtEcodYn to set
//	 */
//	public void setQryRqtEcodYn(String qryRqtEcodYn) {
//		this.qryRqtEcodYn = UseYn.fromString(qryRqtEcodYn);
//	}
//
//	/**
//	 * @return the qryRqtEcodTypeCd
//	 */
//	public EcodType getQryRqtEcodTypeCd() {
//		return qryRqtEcodTypeCd;
//	}
//
//	/**
//	 * @param qryRqtEcodTypeCd the qryRqtEcodTypeCd to set
//	 */
//	public void setQryRqtEcodTypeCd(String qryRqtEcodTypeCd) {
//		this.qryRqtEcodTypeCd = EcodType.fromString(qryRqtEcodTypeCd);
//	}
//
//	/**
//	 * @return the qryRqtEcodKeyVal
//	 */
//	public String getQryRqtEcodKeyVal() {
//		return qryRqtEcodKeyVal;
//	}
//
//	/**
//	 * @param qryRqtEcodKeyVal the qryRqtEcodKeyVal to set
//	 */
//	public void setQryRqtEcodKeyVal(String qryRqtEcodKeyVal) {
//		this.qryRqtEcodKeyVal = qryRqtEcodKeyVal;
//	}

	/**
	 * @return the cacheNrmlYn
	 */
	public UseYn getCacheNrmlYn() {
		return cacheNrmlYn;
	}


	/**
	 * @param cacheNrmlYn the cacheNrmlYn to set
	 */
	public void setCacheNrmlYn(String cacheNrmlYn) {
		this.cacheNrmlYn = UseYn.fromString(cacheNrmlYn);
	}


	/**
	 * @return the sttusCd
	 */
	public CommnAgntSttus getSttusCd() {
		return sttusCd;
	}

	/**
	 * @param sttusCd the sttusCd to set
	 */
	public void setSttusCd(String sttusCd) {
		this.sttusCd = CommnAgntSttus.fromString(sttusCd);
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
	 * @return the athnFormlCd
	 */
	public AthnForml getAthnFormlCd() {
		return athnFormlCd;
	}

	/**
	 * @param athnFormlCd the athnFormlCd to set
	 */
	public void setAthnFormlCd(String athnFormlCd) {
		this.athnFormlCd = AthnForml.fromString(athnFormlCd);
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
	 * @return the prcsChkYn
	 */
	public UseYn getPrcsChkYn() {
		return prcsChkYn;
	}

	/**
	 * @param prcsChkYn the prcsChkYn to set
	 */
	public void setPrcsChkYn(String prcsChkYn) {
		this.prcsChkYn = UseYn.fromString(prcsChkYn);
	}

	/**
	 * @return the prcsChkCyclTime
	 */
	public Long getPrcsChkCyclTime() {
		return prcsChkCyclTime;
	}

	/**
	 * @param prcsChkCyclTime the prcsChkCyclTime to set
	 */
	public void setPrcsChkCyclTime(Long prcsChkCyclTime) {
		this.prcsChkCyclTime = prcsChkCyclTime;
	}

	/**
	 * @return the prcsNm
	 */
	public String getPrcsNm() {
		return prcsNm;
	}

	/**
	 * @param prcsNm the prcsNm to set
	 */
	public void setPrcsNm(String prcsNm) {
		this.prcsNm = prcsNm;
	}

	/**
	 * @return the prcsRepPortNo
	 */
	public Long getPrcsRepPortNo() {
		return prcsRepPortNo;
	}

	/**
	 * @param prcsRepPortNo the prcsRepPortNo to set
	 */
	public void setPrcsRepPortNo(Long prcsRepPortNo) {
		this.prcsRepPortNo = prcsRepPortNo;
	}

	/**
	 * @return the intnRiChkYn
	 */
	public UseYn getIntnRiChkYn() {
		return intnRiChkYn;
	}

	/**
	 * @param intnRiChkYn the intnRiChkYn to set
	 */
	public void setIntnRiChkYn(String intnRiChkYn) {
		this.intnRiChkYn = UseYn.fromString(intnRiChkYn);
	}

	/**
	 * @return the intnRiChkCyclTime
	 */
	public Long getIntnRiChkCyclTime() {
		return intnRiChkCyclTime;
	}

	/**
	 * @param intnRiChkCyclTime the intnRiChkCyclTime to set
	 */
	public void setIntnRiChkCyclTime(Long intnRiChkCyclTime) {
		this.intnRiChkCyclTime = intnRiChkCyclTime;
	}

}