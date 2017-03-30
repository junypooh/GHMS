package com.kt.health.smcp.gw.ca.query.commnagent.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.kt.health.smcp.gw.ca.comm.GwCode.AthnForml;
import com.kt.health.smcp.gw.ca.comm.GwCode.CnctType;
import com.kt.health.smcp.gw.ca.comm.GwCode.CommChType;
import com.kt.health.smcp.gw.ca.comm.GwCode.CommDataTrtModeType;
import com.kt.health.smcp.gw.ca.comm.GwCode.CommnAgntSttus;
import com.kt.health.smcp.gw.ca.comm.GwCode.DataProtType;
import com.kt.health.smcp.gw.ca.comm.GwCode.EcodType;
import com.kt.health.smcp.gw.ca.comm.GwCode.EncodingType;
import com.kt.health.smcp.gw.ca.comm.GwCode.IfType;
import com.kt.health.smcp.gw.ca.comm.GwCode.ProtocolType;
import com.kt.health.smcp.gw.ca.comm.GwCode.UseYn;
/*import com.kt.smcp.gw.ca.query.commnagent.vo.CommnAgntBasVO;
import com.kt.smcp.gw.ca.query.commnagent.vo.CommnAgntCommChDtlVO;
import com.kt.smcp.gw.ca.query.commnagent.vo.CommnAgntSetupDtlVO;*/

@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class CommnAgntBasVO implements Serializable {
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

	/** 커뮤니케이션대리인설정상세정보목록 */
	private List<CommnAgntSetupDtlVO> commnAgntSetupDtlVOs = new ArrayList<CommnAgntSetupDtlVO>();
	/** 커뮤니케이션대리인통신채널상세목록*/
	private List<CommnAgntCommChDtlVO> commnAgntCommChDtlVOs = new ArrayList<CommnAgntCommChDtlVO>();


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
		if( !(obj instanceof CommnAgntBasVO) )
		{
			return false;
		}

		CommnAgntBasVO commnAgntBasVO = (CommnAgntBasVO)obj;

		if(commnAgntBasVO.commnAgntId != null &&
				this.commnAgntId  != null)
		{
			if(commnAgntBasVO.commnAgntId.equals(this.commnAgntId))
			{
				return true;
			}
		}
		return false;
	}

	public CommnAgntCommChDtlVO getCommnAgntCommChDtlVO(CommChType commChType)
	{
		for(CommnAgntCommChDtlVO commnAgntCommChDtlVO : commnAgntCommChDtlVOs)
		{
			if(commnAgntCommChDtlVO.getCommChCd() == commChType)
			{
				return commnAgntCommChDtlVO;
			}
		}
		return null;
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

	/**
	 * @return the commnAgntSetupDtlVOList
	 */
	public List<CommnAgntSetupDtlVO> getCommnAgntSetupDtlVOs() {
		return commnAgntSetupDtlVOs;
	}

	/**
	 * @param commnAgntSetupDtlVOList the commnAgntSetupDtlVOList to set
	 */
	public void setCommnAgntSetupDtlVOs(List<CommnAgntSetupDtlVO> commnAgntSetupDtlVOList) {
		commnAgntSetupDtlVOs = commnAgntSetupDtlVOList;
	}


	/**
	 * @return the commnAgntCommChDtlVOs
	 */
	public List<CommnAgntCommChDtlVO> getCommnAgntCommChDtlVOs() {
		return commnAgntCommChDtlVOs;
	}


	/**
	 * @param commnAgntCommChDtlVOs the commnAgntCommChDtlVOs to set
	 */
	public void setCommnAgntCommChDtlVOs(
			List<CommnAgntCommChDtlVO> commnAgntCommChDtlVOs) {
		this.commnAgntCommChDtlVOs = commnAgntCommChDtlVOs;
	}





	public static class CommnAgntSetupDtlVO implements Serializable {

		private String commnAgntId;
		private String setupDivCd;
		private String setupCtgCd;
		private Integer intVal;
		private Double rlNumVal;
		private String binYn;
		private String strValTxn;
		private byte[] binValTxn;
		private String cretrId;
		private Date cretDt;
		private String amdrId;
		private Date amdDt;


		@Override
		public String toString() {
			return ToStringBuilder.reflectionToString(this,
					ToStringStyle.MULTI_LINE_STYLE);
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
		 * @return the setupDivCd
		 */
		public String getSetupDivCd() {
			return setupDivCd;
		}
		/**
		 * @param setupDivCd the setupDivCd to set
		 */
		public void setSetupDivCd(String setupDivCd) {
			this.setupDivCd = setupDivCd;
		}
		/**
		 * @return the setupCtgCd
		 */
		public String getSetupCtgCd() {
			return setupCtgCd;
		}
		/**
		 * @param setupCtgCd the setupCtgCd to set
		 */
		public void setSetupCtgCd(String setupCtgCd) {
			this.setupCtgCd = setupCtgCd;
		}
		/**
		 * @return the intVal
		 */
		public Integer getIntVal() {
			return intVal;
		}
		/**
		 * @param intVal the intVal to set
		 */
		public void setIntVal(Integer intVal) {
			this.intVal = intVal;
		}
		/**
		 * @return the rlNumVal
		 */
		public Double getRlNumVal() {
			return rlNumVal;
		}
		/**
		 * @param rlNumVal the rlNumVal to set
		 */
		public void setRlNumVal(Double rlNumVal) {
			this.rlNumVal = rlNumVal;
		}
		/**
		 * @return the binYn
		 */
		public String getBinYn() {
			return binYn;
		}
		/**
		 * @param binYn the binYn to set
		 */
		public void setBinYn(String binYn) {
			this.binYn = binYn;
		}
		/**
		 * @return the strValTxn
		 */
		public String getStrValTxn() {
			return strValTxn;
		}
		/**
		 * @param strValTxn the strValTxn to set
		 */
		public void setStrValTxn(String strValTxn) {
			this.strValTxn = strValTxn;
		}
		/**
		 * @return the binValTxn
		 */
		public byte[] getBinValTxn() {
			return binValTxn;
		}
		/**
		 * @param binValTxn the binValTxn to set
		 */
		public void setBinValTxn(byte[] binValTxn) {
			this.binValTxn = binValTxn;
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
	}


	/**
	 * <PRE>
	 *  ClassName GwCommChDtlVO
	 * </PRE>
	 * @brief
	 * @version 1.0
	 * @date 2014. 1. 22. 오후 3:08:24
	 * @author byw
	 */

	public static class CommnAgntCommChDtlVO implements Serializable {
		/** 커뮤니케이션대리인아이디 */
		private String commnAgntId;
		/** 통신채널아이디 */
		private String commChId;
		/** 통신채널코드 */
		private CommChType commChCd;
		/** IP주소 */
		private String ipadr;
		/** 인터페이스유형코드 */
		private IfType ifTypeCd;
		/** 연결유형코드 */
		private CnctType cnctTypeCd;
		/** 포트번호 */
		private Integer portNo;
		/** 부URL주소 */
		private String subUrlAdr;
		/** 연결타임아웃시간 */
		private Long cnctToutTime;
		/** 프로토콜코드 */
		private DataProtType protCd;
//		/** 헤더프로토콜코드 */
//		private ProtocolType headProtCd;
//		/** 이진변환유형코드 */
//		private EncodingType binChangeTypeCd;
//		/** 헤더길이 */
//		private Integer headLen;
//		/** 본문최대길이 */
//		private Long bdyMaxLen;
//		/** 바디길이필드위치값 */
//		private Integer bdyLenFieldLoVal;
//		/** 바디길이필드길이 */
//		private Integer bdyLenFieldLen;
		/** 수집주기시간 */
		private Long colecCyclTime;
		/** 폴링주기시간 */
		private Long polngCyclTime;
		/** 폴링지연시간 */
		private Long polngDelayTime;
		/** 유휴판단기준시간 */
		private Long idleJdgmBaseTime;
		/** 재접속주기시간 */
		private Long recnCyclTime;
		/** 재접속시도횟수 */
		private Integer recnTryTmscnt;
		/** 작업처리타임아웃시간 */
		private Long wrkTrtToutTime;
		/** 인증방식코드 */
		private AthnForml athnFormlCd;
		/** 인증요청번호 */
		private String athnRqtNo;
		/** 인증번호 */
		private String athnNo;
		/** 수신암호화여부 */
		private UseYn rcvEcodYn;
		/** 수신암호화유형코드 */
		private EcodType rcvEcodTypeCd;
		/** 수신암호화키값 */
		private String rcvEcodKeyVal;
		/** 송신암호화여부 */
		private UseYn sndEcodYn;
		/** 송신암호화유형코드 */
		private EcodType sndEcodTypeCd;
		/** 송신암호화키값 */
		private String sndEcodKeyVal;
		/** 통신데이터처리모드코드 */
		private CommDataTrtModeType commDataTrtModeCd;
		/** 큐크기값 */
		private Integer queueSizeVal;
		/** 작업처리스레드수 */
		private Integer wrkTrtThrNum;
		/** 큐푸시최대시간 */
		private Long queuePushMaxTime;
		/** 묶음처리주기시간 */
		private Long bundTrtCyclTime;



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
		 * @return the commChCd
		 */
		public CommChType getCommChCd() {
			return commChCd;
		}
		/**
		 * @param commChCd the commChCd to set
		 */
		public void setCommChCd(String commChCd) {
			this.commChCd = CommChType.fromString(commChCd);
		}

		/**
		 * @return the commChId
		 */
		public String getCommChId() {
			return commChId;
		}
		/**
		 * @param commChId the commChId to set
		 */
		public void setCommChId(String commChId) {
			this.commChId = commChId;
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
		 * @return the ifTypeCd
		 */
		public IfType getIfTypeCd() {
			return ifTypeCd;
		}
		/**
		 * @param ifTypeCd the ifTypeCd to set
		 */
		public void setIfTypeCd(String ifTypeCd) {
			this.ifTypeCd = IfType.fromString(ifTypeCd);
		}
		/**
		 * @return the cnctTypeCd
		 */
		public CnctType getCnctTypeCd() {
			return cnctTypeCd;
		}
		/**
		 * @param cnctTypeCd the cnctTypeCd to set
		 */
		public void setCnctTypeCd(String cnctTypeCd) {
			this.cnctTypeCd = CnctType.fromString(cnctTypeCd);
		}
		/**
		 * @return the portNo
		 */
		public Integer getPortNo() {
			return portNo;
		}
		/**
		 * @param portNo the portNo to set
		 */
		public void setPortNo(Integer portNo) {
			this.portNo = portNo;
		}
		/**
		 * @return the subUrlAdr
		 */
		public String getSubUrlAdr() {
			return subUrlAdr;
		}
		/**
		 * @param subUrlAdr the subUrlAdr to set
		 */
		public void setSubUrlAdr(String subUrlAdr) {
			this.subUrlAdr = subUrlAdr;
		}
		/**
		 * @return the cnctToutTime
		 */
		public Long getCnctToutTime() {
			return cnctToutTime;
		}
		/**
		 * @param cnctToutTime the cnctToutTime to set
		 */
		public void setCnctToutTime(Long cnctToutTime) {
			this.cnctToutTime = cnctToutTime;
		}
		/**
		 * @return the protCd
		 */
		public DataProtType getProtCd() {
			return protCd;
		}
		/**
		 * @param protCd the protCd to set
		 */
		public void setProtCd(String protCd) {
			this.protCd = DataProtType.fromString(protCd);
		}
		/**
		 * @return the headProtCd
		 */
//		public ProtocolType getHeadProtCd() {
//			return headProtCd;
//		}
//		/**
//		 * @param headProtCd the headProtCd to set
//		 */
//		public void setHeadProtCd(String headProtCd) {
//			this.headProtCd = ProtocolType.fromString(headProtCd);
//		}
//		/**
//		 * @return the binChangeTypeCd
//		 */
//		public EncodingType getBinChangeTypeCd() {
//			return binChangeTypeCd;
//		}
//		/**
//		 * @param binChangeTypeCd the binChangeTypeCd to set
//		 */
//		public void setBinChangeTypeCd(String binChangeTypeCd) {
//			this.binChangeTypeCd = EncodingType.fromString(binChangeTypeCd);
//		}
//		/**
//		 * @return the headLen
//		 */
//		public Integer getHeadLen() {
//			return headLen;
//		}
//		/**
//		 * @param headLen the headLen to set
//		 */
//		public void setHeadLen(Integer headLen) {
//			this.headLen = headLen;
//		}
//		/**
//		 * @return the bdyMaxLen
//		 */
//		public Long getBdyMaxLen() {
//			return bdyMaxLen;
//		}
//		/**
//		 * @param bdyMaxLen the bdyMaxLen to set
//		 */
//		public void setBdyMaxLen(Long bdyMaxLen) {
//			this.bdyMaxLen = bdyMaxLen;
//		}
//		/**
//		 * @return the bdyLenFieldLoVal
//		 */
//		public Integer getBdyLenFieldLoVal() {
//			return bdyLenFieldLoVal;
//		}
//		/**
//		 * @param bdyLenFieldLoVal the bdyLenFieldLoVal to set
//		 */
//		public void setBdyLenFieldLoVal(Integer bdyLenFieldLoVal) {
//			this.bdyLenFieldLoVal = bdyLenFieldLoVal;
//		}
//		/**
//		 * @return the bdyLenFieldLen
//		 */
//		public Integer getBdyLenFieldLen() {
//			return bdyLenFieldLen;
//		}
//		/**
//		 * @param bdyLenFieldLen the bdyLenFieldLen to set
//		 */
//		public void setBdyLenFieldLen(Integer bdyLenFieldLen) {
//			this.bdyLenFieldLen = bdyLenFieldLen;
//		}
		/**
		 * @return the colecCyclTime
		 */
		public Long getColecCyclTime() {
			return colecCyclTime;
		}
		/**
		 * @param colecCyclTime the colecCyclTime to set
		 */
		public void setColecCyclTime(Long colecCyclTime) {
			this.colecCyclTime = colecCyclTime;
		}
		/**
		 * @return the polngCyclTime
		 */
		public Long getPolngCyclTime() {
			return polngCyclTime;
		}
		/**
		 * @param polngCyclTime the polngCyclTime to set
		 */
		public void setPolngCyclTime(Long polngCyclTime) {
			this.polngCyclTime = polngCyclTime;
		}
		/**
		 * @return the polngDelayTime
		 */
		public Long getPolngDelayTime() {
			return polngDelayTime;
		}
		/**
		 * @param polngDelayTime the polngDelayTime to set
		 */
		public void setPolngDelayTime(Long polngDelayTime) {
			this.polngDelayTime = polngDelayTime;
		}
		/**
		 * @return the idleJdgmBaseTime
		 */
		public Long getIdleJdgmBaseTime() {
			return idleJdgmBaseTime;
		}
		/**
		 * @param idleJdgmBaseTime the idleJdgmBaseTime to set
		 */
		public void setIdleJdgmBaseTime(Long idleJdgmBaseTime) {
			this.idleJdgmBaseTime = idleJdgmBaseTime;
		}
		/**
		 * @return the recnCyclTime
		 */
		public Long getRecnCyclTime() {
			return recnCyclTime;
		}
		/**
		 * @param recnCyclTime the recnCyclTime to set
		 */
		public void setRecnCyclTime(Long recnCyclTime) {
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
		 * @return the wrkTrtToutTime
		 */
		public Long getWrkTrtToutTime() {
			return wrkTrtToutTime;
		}
		/**
		 * @param wrkTrtToutTime the wrkTrtToutTime to set
		 */
		public void setWrkTrtToutTime(Long wrkTrtToutTime) {
			this.wrkTrtToutTime = wrkTrtToutTime;
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
		 * @return the rcvEcodYn
		 */
		public UseYn getRcvEcodYn() {
			return rcvEcodYn;
		}
		/**
		 * @param rcvEcodYn the rcvEcodYn to set
		 */
		public void setRcvEcodYn(String rcvEcodYn) {
			this.rcvEcodYn = UseYn.fromString(rcvEcodYn);
		}
		/**
		 * @return the rcvEcodTypeCd
		 */
		public EcodType getRcvEcodTypeCd() {
			return rcvEcodTypeCd;
		}
		/**
		 * @param rcvEcodTypeCd the rcvEcodTypeCd to set
		 */
		public void setRcvEcodTypeCd(String rcvEcodTypeCd) {
			this.rcvEcodTypeCd = EcodType.fromString(rcvEcodTypeCd);
		}
		/**
		 * @return the rcvEcodKeyVal
		 */
		public String getRcvEcodKeyVal() {
			return rcvEcodKeyVal;
		}
		/**
		 * @param rcvEcodKeyVal the rcvEcodKeyVal to set
		 */
		public void setRcvEcodKeyVal(String rcvEcodKeyVal) {
			this.rcvEcodKeyVal = rcvEcodKeyVal;
		}
		/**
		 * @return the sndEcodYn
		 */
		public UseYn getSndEcodYn() {
			return sndEcodYn;
		}
		/**
		 * @param sndEcodYn the sndEcodYn to set
		 */
		public void setSndEcodYn(String sndEcodYn) {
			this.sndEcodYn = UseYn.fromString(sndEcodYn);
		}
		/**
		 * @return the sndEcodTypeCd
		 */
		public EcodType getSndEcodTypeCd() {
			return sndEcodTypeCd;
		}
		/**
		 * @param sndEcodTypeCd the sndEcodTypeCd to set
		 */
		public void setSndEcodTypeCd(String sndEcodTypeCd) {
			this.sndEcodTypeCd = EcodType.fromString(sndEcodTypeCd);
		}
		/**
		 * @return the sndEcodKeyVal
		 */
		public String getSndEcodKeyVal() {
			return sndEcodKeyVal;
		}
		/**
		 * @param sndEcodKeyVal the sndEcodKeyVal to set
		 */
		public void setSndEcodKeyVal(String sndEcodKeyVal) {
			this.sndEcodKeyVal = sndEcodKeyVal;
		}
		/**
		 * @return the commDataTrtModeCd
		 */
		public CommDataTrtModeType getCommDataTrtModeCd() {
			return commDataTrtModeCd;
		}
		/**
		 * @param commDataTrtModeCd the commDataTrtModeCd to set
		 */
		public void setCommDataTrtModeCd(String commDataTrtModeCd) {
			this.commDataTrtModeCd = CommDataTrtModeType.fromString(commDataTrtModeCd);
		}
		/**
		 * @return the queueSizeVal
		 */
		public Integer getQueueSizeVal() {
			return queueSizeVal;
		}
		/**
		 * @param queueSizeVal the queueSizeVal to set
		 */
		public void setQueueSizeVal(Integer queueSizeVal) {
			this.queueSizeVal = queueSizeVal;
		}
		/**
		 * @return the wrkTrtThrNum
		 */
		public Integer getWrkTrtThrNum() {
			return wrkTrtThrNum;
		}
		/**
		 * @param wrkTrtThrNum the wrkTrtThrNum to set
		 */
		public void setWrkTrtThrNum(Integer wrkTrtThrNum) {
			this.wrkTrtThrNum = wrkTrtThrNum;
		}
		/**
		 * @return the queuePushMaxTime
		 */
		public Long getQueuePushMaxTime() {
			return queuePushMaxTime;
		}
		/**
		 * @param queuePushMaxTime the queuePushMaxTime to set
		 */
		public void setQueuePushMaxTime(Long queuePushMaxTime) {
			this.queuePushMaxTime = queuePushMaxTime;
		}
		/**
		 * @return the bundTrtCyclTime
		 */
		public Long getBundTrtCyclTime() {
			return bundTrtCyclTime;
		}
		/**
		 * @param bundTrtCyclTime the bundTrtCyclTime to set
		 */
		public void setBundTrtCyclTime(Long bundTrtCyclTime) {
			this.bundTrtCyclTime = bundTrtCyclTime;
		}
	}
}
