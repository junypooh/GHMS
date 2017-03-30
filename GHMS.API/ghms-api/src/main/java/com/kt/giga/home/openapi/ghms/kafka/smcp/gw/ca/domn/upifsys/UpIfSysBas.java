package com.kt.giga.home.openapi.ghms.kafka.smcp.gw.ca.domn.upifsys;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.kt.giga.home.openapi.ghms.kafka.smcp.gw.ca.comm.GwCode.AthnForml;
import com.kt.giga.home.openapi.ghms.kafka.smcp.gw.ca.comm.GwCode.DataLayrType;
import com.kt.giga.home.openapi.ghms.kafka.smcp.gw.ca.comm.GwCode.M3sSttus;
import com.kt.giga.home.openapi.ghms.kafka.smcp.gw.ca.comm.GwCode.UseYn;
import com.kt.giga.home.openapi.ghms.kafka.smcp.gw.ca.comm.UpSysAdaptorCode.UpSysType;

@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class UpIfSysBas implements Serializable {
	/** 상위인터페이스시스템아이디 */
	private String upIfSysId;
	/** 상위시스템타입 */
	private UpSysType upSysTypeCd;
//	/** 단위서비스코드 */
//	private String unitSvcCd;
//	/** 서비스대상일련번호 */
//	private Long svcTgtSeq;
	/** 인터페이스시스템명 */
	private String ifSysNm;
	/** 인터페이스모듈명 */
	private String ifModulNm;
//	/** IP주소 */
//	private String ipadr;
	/** 상위시스템인바운드프로세서아이디 */
	private String upSysInbndPrcsrId;
	/** 상위시스템아웃바운드프로세서아이디 */
	private String upSysOtbndPrcsrId;
//	/** 측정수신연결유형코드 */
//	private CnctType msrRcvCnctTypeCd;
//	/** 측정수신연결주소 */
//	private String msrRcvCnctAdr;
//	/** 측정수신프로토콜코드 */
//	private ProtocolType msrRcvProtCd;
//	/** 측정수신인증방식코드 */
//	private AthnForml msrRcvAthnFormlCd;
//	/** 측정수신인증번호 */
//	private String msrRcvAthnNo;
//	/** 측정수신암호화여부 */
//	private UseYn msrRcvEcodYn;
//	/** 측정수신암호화유형코드 */
//	private EcodType msrRcvEcodTypeCd;
//	/** 측정수신암호화키값 */
//	private String msrRcvEcodKeyVal;
//	/** 상태수신연결유형코드 */
//	private CnctType sttusRcvCnctTypeCd;
//	/** 상태수신연결주소 */
//	private String sttusRcvCnctAdr;
//	/** 상태수신프로토콜코드 */
//	private ProtocolType sttusRcvProtCd;
//	/** 상태수신인증방식코드 */
//	private AthnForml sttusRcvAthnFormlCd;
//	/** 상태수신인증번호 */
//	private String sttusRcvAthnNo;
//	/** 상태수신암호화여부 */
//	private UseYn sttusRcvEcodYn;
//	/** 상태수신암호화유형코드 */
//	private EcodType sttusRcvEcodTypeCd;
//	/** 상태수신암호화키값 */
//	private String sttusRcvEcodKeyVal;
//	/** 제어수신연결유형코드 */
//	private CnctType contlRcvCnctTypeCd;
//	/** 제어수신연결주소 */
//	private String contlRcvCnctAdr;
//	/** 제어수신프로토콜코드 */
//	private ProtocolType contlRcvProtCd;
//	/** 제어수신인증방식코드 */
//	private AthnForml contlRcvAthnFormlCd;
//	/** 제어수신인증번호 */
//	private String contlRcvAthnNo;
//	/** 제어수신암호화여부 */
//	private UseYn contlRcvEcodYn;
//	/** 제어수신암호화유형코드 */
//	private EcodType contlRcvEcodTypeCd;
//	/** 제어수신암호화키값 */
//	private String contlRcvEcodKeyVal;
//	/** 질의수신연결유형코드 */
//	private CnctType qryRqtCnctTypeCd;
//	/** 질의수신연결주소 */
//	private String qryRqtCnctAdr;
//	/** 질의수신프로토콜코드 */
//	private ProtocolType qryRqtProtCd;
//	/** 질의수신인증방식코드 */
//	private AthnForml qryRqtAthnFormlCd;
//	/** 질의수신인증번호 */
//	private String qryRqtAthnNo;
//	/** 질의수신암호화여부 */
//	private UseYn qryRqtEcodYn;
//	/** 질의수신암호화유형코드 */
//	private EcodType qryRqtEcodTypeCd;
//	/** 질의수신암호화키값 */
//	private String qryRqtEcodKeyVal;
	/** 상태코드 */
	private M3sSttus sttusCd;
	/** 수집주기시간 */
	private Integer colecCyclTime;
	/** 유휴판단기준시간 */
	private Integer idleJdgmBaseTime;
	/** 재접속주기시간 */
	private Integer recnCyclTime;
	/** 재접속시도횟수 */
	private Long recnTryTmscnt;
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
	/** 초기재전송시간 */
	private Long earlyRsndTime;
	/** 인증방식코드 */
	private AthnForml athnFormlCd;
	/** 인증요청번호 */
	private String athnRqtNo;
	/** 인증번호 */
	private String athnNo;
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
	/** 사용여부 */
	private UseYn useYn;

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.MULTI_LINE_STYLE);
	}

	@Override
	public int hashCode()
	{
		if(upIfSysId == null)
		{
			return 0;
		}
		return upIfSysId.hashCode();
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj)
	{
		if( !(obj instanceof UpIfSysBas) )
		{
			return false;
		}

		UpIfSysBas upIfSysBasVO = (UpIfSysBas)obj;

		if(upIfSysBasVO.upIfSysId != null &&
				this.upIfSysId  != null)
		{
			if(upIfSysBasVO.upIfSysId.equals(this.upIfSysId))
			{
				return true;
			}
		}
		return false;
	}

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
	 * @return the upSysTypeCd
	 */
	public UpSysType getUpSysTypeCd() {
		return upSysTypeCd;
	}

//	/**
//	 * @param upSysTypeCd the upSysTypeCd to set
//	 */
//	public void setUpSysTypeCd(UpSysType upSysTypeCd) {
//		this.upSysTypeCd = upSysTypeCd;
//	}

	/**
	 * @param upSysTypeCd the upSysTypeCd to set
	 */
	public void setUpSysTypeCd(String upSysTypeCd) {
		this.upSysTypeCd = UpSysType.fromString(upSysTypeCd);
	}

//	/**
//	 * @return the unitSvcCd
//	 */
//	public String getUnitSvcCd() {
//		return unitSvcCd;
//	}
//	/**
//	 * @param unitSvcCd the unitSvcCd to set
//	 */
//	public void setUnitSvcCd(String unitSvcCd) {
//		this.unitSvcCd = unitSvcCd;
//	}
//	/**
//	 * @return the svcTgtSeq
//	 */
//	public Long getSvcTgtSeq() {
//		return svcTgtSeq;
//	}
//	/**
//	 * @param svcTgtSeq the svcTgtSeq to set
//	 */
//	public void setSvcTgtSeq(Long svcTgtSeq) {
//		this.svcTgtSeq = svcTgtSeq;
//	}
	/**
	 * @return the ifSysNm
	 */
	public String getIfSysNm() {
		return ifSysNm;
	}
	/**
	 * @param ifSysNm the ifSysNm to set
	 */
	public void setIfSysNm(String ifSysNm) {
		this.ifSysNm = ifSysNm;
	}
	/**
	 * @return the ifModulNm
	 */
	public String getIfModulNm() {
		return ifModulNm;
	}
	/**
	 * @param ifModulNm the ifModulNm to set
	 */
	public void setIfModulNm(String ifModulNm) {
		this.ifModulNm = ifModulNm;
	}
//	/**
//	 * @return the ipadr
//	 */
//	public String getIpadr() {
//		return ipadr;
//	}
//	/**
//	 * @param ipadr the ipadr to set
//	 */
//	public void setIpadr(String ipadr) {
//		this.ipadr = ipadr;
//	}

	/**
	 * @return the upSysInbndPrcsrId
	 */
	public String getUpSysInbndPrcsrId() {
		return upSysInbndPrcsrId;
	}

	/**
	 * @param upSysInbndPrcsrId the upSysInbndPrcsrId to set
	 */
	public void setUpSysInbndPrcsrId(String upSysInbndPrcsrId) {
		this.upSysInbndPrcsrId = upSysInbndPrcsrId;
	}

	/**
	 * @return the upSysOtbndPrcsrId
	 */
	public String getUpSysOtbndPrcsrId() {
		return upSysOtbndPrcsrId;
	}

	/**
	 * @param upSysOtbndPrcsrId the upSysOtbndPrcsrId to set
	 */
	public void setUpSysOtbndPrcsrId(String upSysOtbndPrcsrId) {
		this.upSysOtbndPrcsrId = upSysOtbndPrcsrId;
	}

//	/**
//	 * @return the msrRcvCnctTypeCd
//	 */
//	public CnctType getMsrRcvCnctTypeCd() {
//		return msrRcvCnctTypeCd;
//	}
//	/**
//	 * @param msrRcvCnctTypeCd the msrRcvCnctTypeCd to set
//	 */
//	public void setMsrRcvCnctTypeCd(String msrRcvCnctTypeCd) {
//		this.msrRcvCnctTypeCd = CnctType.fromString(msrRcvCnctTypeCd);
//	}
//	/**
//	 * @return the msrRcvCnctAdr
//	 */
//	public String getMsrRcvCnctAdr() {
//		return msrRcvCnctAdr;
//	}
//	/**
//	 * @param msrRcvCnctAdr the msrRcvCnctAdr to set
//	 */
//	public void setMsrRcvCnctAdr(String msrRcvCnctAdr) {
//		this.msrRcvCnctAdr = msrRcvCnctAdr;
//	}
//	/**
//	 * @return the msrRcvProtCd
//	 */
//	public ProtocolType getMsrRcvProtCd() {
//		return msrRcvProtCd;
//	}
//	/**
//	 * @param msrRcvProtCd the msrRcvProtCd to set
//	 */
//	public void setMsrRcvProtCd(String msrRcvProtCd) {
//		this.msrRcvProtCd = ProtocolType.fromString(msrRcvProtCd);
//	}
//	/**
//	 * @return the msrRcvAthnFormlCd
//	 */
//	public AthnForml getMsrRcvAthnFormlCd() {
//		return msrRcvAthnFormlCd;
//	}
//	/**
//	 * @param msrRcvAthnFormlCd the msrRcvAthnFormlCd to set
//	 */
//	public void setMsrRcvAthnFormlCd(String msrRcvAthnFormlCd) {
//		this.msrRcvAthnFormlCd = AthnForml.fromString(msrRcvAthnFormlCd);
//	}
//	/**
//	 * @return the msrRcvAthnNo
//	 */
//	public String getMsrRcvAthnNo() {
//		return msrRcvAthnNo;
//	}
//	/**
//	 * @param msrRcvAthnNo the msrRcvAthnNo to set
//	 */
//	public void setMsrRcvAthnNo(String msrRcvAthnNo) {
//		this.msrRcvAthnNo = msrRcvAthnNo;
//	}
//	/**
//	 * @return the msrRcvEcodYn
//	 */
//	public UseYn getMsrRcvEcodYn() {
//		return msrRcvEcodYn;
//	}
//	/**
//	 * @param msrRcvEcodYn the msrRcvEcodYn to set
//	 */
//	public void setMsrRcvEcodYn(String msrRcvEcodYn) {
//		this.msrRcvEcodYn = UseYn.fromString(msrRcvEcodYn);
//	}
//	/**
//	 * @return the msrRcvEcodTypeCd
//	 */
//	public EcodType getMsrRcvEcodTypeCd() {
//		return msrRcvEcodTypeCd;
//	}
//	/**
//	 * @param msrRcvEcodTypeCd the msrRcvEcodTypeCd to set
//	 */
//	public void setMsrRcvEcodTypeCd(String msrRcvEcodTypeCd) {
//		this.msrRcvEcodTypeCd = EcodType.fromString(msrRcvEcodTypeCd);
//	}
//	/**
//	 * @return the msrRcvEcodKeyVal
//	 */
//	public String getMsrRcvEcodKeyVal() {
//		return msrRcvEcodKeyVal;
//	}
//	/**
//	 * @param msrRcvEcodKeyVal the msrRcvEcodKeyVal to set
//	 */
//	public void setMsrRcvEcodKeyVal(String msrRcvEcodKeyVal) {
//		this.msrRcvEcodKeyVal = msrRcvEcodKeyVal;
//	}
//	/**
//	 * @return the sttusRcvCnctTypeCd
//	 */
//	public CnctType getSttusRcvCnctTypeCd() {
//		return sttusRcvCnctTypeCd;
//	}
//	/**
//	 * @param sttusRcvCnctTypeCd the sttusRcvCnctTypeCd to set
//	 */
//	public void setSttusRcvCnctTypeCd(String sttusRcvCnctTypeCd) {
//		this.sttusRcvCnctTypeCd = CnctType.fromString(sttusRcvCnctTypeCd);
//	}
//	/**
//	 * @return the sttusRcvCnctAdr
//	 */
//	public String getSttusRcvCnctAdr() {
//		return sttusRcvCnctAdr;
//	}
//	/**
//	 * @param sttusRcvCnctAdr the sttusRcvCnctAdr to set
//	 */
//	public void setSttusRcvCnctAdr(String sttusRcvCnctAdr) {
//		this.sttusRcvCnctAdr = sttusRcvCnctAdr;
//	}
//	/**
//	 * @return the sttusRcvProtCd
//	 */
//	public ProtocolType getSttusRcvProtCd() {
//		return sttusRcvProtCd;
//	}
//	/**
//	 * @param sttusRcvProtCd the sttusRcvProtCd to set
//	 */
//	public void setSttusRcvProtCd(String sttusRcvProtCd) {
//		this.sttusRcvProtCd = ProtocolType.fromString(sttusRcvProtCd);
//	}
//	/**
//	 * @return the sttusRcvAthnFormlCd
//	 */
//	public AthnForml getSttusRcvAthnFormlCd() {
//		return sttusRcvAthnFormlCd;
//	}
//	/**
//	 * @param sttusRcvAthnFormlCd the sttusRcvAthnFormlCd to set
//	 */
//	public void setSttusRcvAthnFormlCd(String sttusRcvAthnFormlCd) {
//		this.sttusRcvAthnFormlCd = AthnForml.fromString(sttusRcvAthnFormlCd);
//	}
//	/**
//	 * @return the sttusRcvAthnNo
//	 */
//	public String getSttusRcvAthnNo() {
//		return sttusRcvAthnNo;
//	}
//	/**
//	 * @param sttusRcvAthnNo the sttusRcvAthnNo to set
//	 */
//	public void setSttusRcvAthnNo(String sttusRcvAthnNo) {
//		this.sttusRcvAthnNo = sttusRcvAthnNo;
//	}
//	/**
//	 * @return the sttusRcvEcodYn
//	 */
//	public UseYn getSttusRcvEcodYn() {
//		return sttusRcvEcodYn;
//	}
//	/**
//	 * @param sttusRcvEcodYn the sttusRcvEcodYn to set
//	 */
//	public void setSttusRcvEcodYn(String sttusRcvEcodYn) {
//		this.sttusRcvEcodYn = UseYn.fromString(sttusRcvEcodYn);
//	}
//	/**
//	 * @return the sttusRcvEcodTypeCd
//	 */
//	public EcodType getSttusRcvEcodTypeCd() {
//		return sttusRcvEcodTypeCd;
//	}
//	/**
//	 * @param sttusRcvEcodTypeCd the sttusRcvEcodTypeCd to set
//	 */
//	public void setSttusRcvEcodTypeCd(String sttusRcvEcodTypeCd) {
//		this.sttusRcvEcodTypeCd = EcodType.fromString(sttusRcvEcodTypeCd);
//	}
//	/**
//	 * @return the sttusRcvEcodKeyVal
//	 */
//	public String getSttusRcvEcodKeyVal() {
//		return sttusRcvEcodKeyVal;
//	}
//	/**
//	 * @param sttusRcvEcodKeyVal the sttusRcvEcodKeyVal to set
//	 */
//	public void setSttusRcvEcodKeyVal(String sttusRcvEcodKeyVal) {
//		this.sttusRcvEcodKeyVal = sttusRcvEcodKeyVal;
//	}
//	/**
//	 * @return the contlRcvCnctTypeCd
//	 */
//	public CnctType getContlRcvCnctTypeCd() {
//		return contlRcvCnctTypeCd;
//	}
//	/**
//	 * @param contlRcvCnctTypeCd the contlRcvCnctTypeCd to set
//	 */
//	public void setContlRcvCnctTypeCd(String contlRcvCnctTypeCd) {
//		this.contlRcvCnctTypeCd = CnctType.fromString(contlRcvCnctTypeCd);
//	}
//	/**
//	 * @return the contlRcvCnctAdr
//	 */
//	public String getContlRcvCnctAdr() {
//		return contlRcvCnctAdr;
//	}
//	/**
//	 * @param contlRcvCnctAdr the contlRcvCnctAdr to set
//	 */
//	public void setContlRcvCnctAdr(String contlRcvCnctAdr) {
//		this.contlRcvCnctAdr = contlRcvCnctAdr;
//	}
//	/**
//	 * @return the contlRcvProtCd
//	 */
//	public ProtocolType getContlRcvProtCd() {
//		return contlRcvProtCd;
//	}
//	/**
//	 * @param contlRcvProtCd the contlRcvProtCd to set
//	 */
//	public void setContlRcvProtCd(String contlRcvProtCd) {
//		this.contlRcvProtCd = ProtocolType.fromString(contlRcvProtCd);
//	}
//	/**
//	 * @return the contlRcvAthnFormlCd
//	 */
//	public AthnForml getContlRcvAthnFormlCd() {
//		return contlRcvAthnFormlCd;
//	}
//	/**
//	 * @param contlRcvAthnFormlCd the contlRcvAthnFormlCd to set
//	 */
//	public void setContlRcvAthnFormlCd(String contlRcvAthnFormlCd) {
//		this.contlRcvAthnFormlCd = AthnForml.fromString(contlRcvAthnFormlCd);
//	}
//	/**
//	 * @return the contlRcvAthnNo
//	 */
//	public String getContlRcvAthnNo() {
//		return contlRcvAthnNo;
//	}
//	/**
//	 * @param contlRcvAthnNo the contlRcvAthnNo to set
//	 */
//	public void setContlRcvAthnNo(String contlRcvAthnNo) {
//		this.contlRcvAthnNo = contlRcvAthnNo;
//	}
//	/**
//	 * @return the contlRcvEcodYn
//	 */
//	public UseYn getContlRcvEcodYn() {
//		return contlRcvEcodYn;
//	}
//	/**
//	 * @param contlRcvEcodYn the contlRcvEcodYn to set
//	 */
//	public void setContlRcvEcodYn(String contlRcvEcodYn) {
//		this.contlRcvEcodYn = UseYn.fromString(contlRcvEcodYn);
//	}
//	/**
//	 * @return the contlRcvEcodTypeCd
//	 */
//	public EcodType getContlRcvEcodTypeCd() {
//		return contlRcvEcodTypeCd;
//	}
//	/**
//	 * @param contlRcvEcodTypeCd the contlRcvEcodTypeCd to set
//	 */
//	public void setContlRcvEcodTypeCd(String contlRcvEcodTypeCd) {
//		this.contlRcvEcodTypeCd = EcodType.fromString(contlRcvEcodTypeCd);
//	}
//	/**
//	 * @return the contlRcvEcodKeyVal
//	 */
//	public String getContlRcvEcodKeyVal() {
//		return contlRcvEcodKeyVal;
//	}
//	/**
//	 * @param contlRcvEcodKeyVal the contlRcvEcodKeyVal to set
//	 */
//	public void setContlRcvEcodKeyVal(String contlRcvEcodKeyVal) {
//		this.contlRcvEcodKeyVal = contlRcvEcodKeyVal;
//	}
//	/**
//	 * @return the qryRqtCnctTypeCd
//	 */
//	public CnctType getQryRqtCnctTypeCd() {
//		return qryRqtCnctTypeCd;
//	}
//	/**
//	 * @param qryRqtCnctTypeCd the qryRqtCnctTypeCd to set
//	 */
//	public void setQryRqtCnctTypeCd(String qryRqtCnctTypeCd) {
//		this.qryRqtCnctTypeCd = CnctType.fromString(qryRqtCnctTypeCd);
//	}
//	/**
//	 * @return the qryRqtCnctAdr
//	 */
//	public String getQryRqtCnctAdr() {
//		return qryRqtCnctAdr;
//	}
//	/**
//	 * @param qryRqtCnctAdr the qryRqtCnctAdr to set
//	 */
//	public void setQryRqtCnctAdr(String qryRqtCnctAdr) {
//		this.qryRqtCnctAdr = qryRqtCnctAdr;
//	}
//	/**
//	 * @return the qryRqtProtCd
//	 */
//	public ProtocolType getQryRqtProtCd() {
//		return qryRqtProtCd;
//	}
//	/**
//	 * @param qryRqtProtCd the qryRqtProtCd to set
//	 */
//	public void setQryRqtProtCd(String qryRqtProtCd) {
//		this.qryRqtProtCd = ProtocolType.fromString(qryRqtProtCd);
//	}
//	/**
//	 * @return the qryRqtAthnFormlCd
//	 */
//	public AthnForml getQryRqtAthnFormlCd() {
//		return qryRqtAthnFormlCd;
//	}
//	/**
//	 * @param qryRqtAthnFormlCd the qryRqtAthnFormlCd to set
//	 */
//	public void setQryRqtAthnFormlCd(String qryRqtAthnFormlCd) {
//		this.qryRqtAthnFormlCd = AthnForml.fromString(qryRqtAthnFormlCd);
//	}
//	/**
//	 * @return the qryRqtAthnNo
//	 */
//	public String getQryRqtAthnNo() {
//		return qryRqtAthnNo;
//	}
//	/**
//	 * @param qryRqtAthnNo the qryRqtAthnNo to set
//	 */
//	public void setQryRqtAthnNo(String qryRqtAthnNo) {
//		this.qryRqtAthnNo = qryRqtAthnNo;
//	}
//	/**
//	 * @return the qryRqtEcodYn
//	 */
//	public UseYn getQryRqtEcodYn() {
//		return qryRqtEcodYn;
//	}
//	/**
//	 * @param qryRqtEcodYn the qryRqtEcodYn to set
//	 */
//	public void setQryRqtEcodYn(String qryRqtEcodYn) {
//		this.qryRqtEcodYn = UseYn.fromString(qryRqtEcodYn);
//	}
//	/**
//	 * @return the qryRqtEcodTypeCd
//	 */
//	public EcodType getQryRqtEcodTypeCd() {
//		return qryRqtEcodTypeCd;
//	}
//	/**
//	 * @param qryRqtEcodTypeCd the qryRqtEcodTypeCd to set
//	 */
//	public void setQryRqtEcodTypeCd(String qryRqtEcodTypeCd) {
//		this.qryRqtEcodTypeCd = EcodType.fromString(qryRqtEcodTypeCd);
//	}
//	/**
//	 * @return the qryRqtEcodKeyVal
//	 */
//	public String getQryRqtEcodKeyVal() {
//		return qryRqtEcodKeyVal;
//	}
//	/**
//	 * @param qryRqtEcodKeyVal the qryRqtEcodKeyVal to set
//	 */
//	public void setQryRqtEcodKeyVal(String qryRqtEcodKeyVal) {
//		this.qryRqtEcodKeyVal = qryRqtEcodKeyVal;
//	}
	/**
	 * @return the sttusCd
	 */
	public M3sSttus getSttusCd() {
		return sttusCd;
	}
	/**
	 * @param sttusCd the sttusCd to set
	 */
	public void setSttusCd(String sttusCd) {
		this.sttusCd = M3sSttus.fromString(sttusCd);
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
	public Long getRecnTryTmscnt() {
		return recnTryTmscnt;
	}
	/**
	 * @param recnTryTmscnt the recnTryTmscnt to set
	 */
	public void setRecnTryTmscnt(Long recnTryTmscnt) {
		this.recnTryTmscnt = recnTryTmscnt;
	}
	/**
	 * @return the earlyRsndTime
	 */
	public Long getEarlyRsndTime() {
		return earlyRsndTime;
	}
	/**
	 * @param earlyRsndTime the earlyRsndTime to set
	 */
	public void setEarlyRsndTime(Long earlyRsndTime) {
		this.earlyRsndTime = earlyRsndTime;
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
