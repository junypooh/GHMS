package com.kt.giga.home.openapi.vo.spotdev;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.kt.giga.home.openapi.vo.GwCode.AthnForml;
import com.kt.giga.home.openapi.vo.GwCode.SpotDevSubsSttus;
import com.kt.giga.home.openapi.vo.GwCode.UseYn;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(Include.NON_EMPTY)
public class SpotDevBas
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
	@JsonIgnore
	private String spotDevId;
	/** 상위서비스대상일련번호 */
	private Long upSvcTgtSeq;
	/** 상위게이트웨이연결아이디 */
	private String upGwCnctId;
	/** 상위현장장치아이디 */
	private String upSpotDevId;
	/** 단위서비스코드 */
	private String unitSvcCd;
	/**현장장치명  */
	@JsonInclude(Include.ALWAYS)
	private String spotDevNm;
	/**연결IP주소  */
	private String cnctIpadr;
	/**가상현장장치아이디  */
	private String virtlSpotDevId;
	/**청약상태코드  */
	private SpotDevSubsSttus subsSttusCd;
	/** 접속여부 */
	private UseYn connYn;
	/**세션아이디 : empty 체크해서 true 이면 오프라인, false 이면 온라인 */
	private String sessnId;
	/**소속서버아이디  */
	private String posSrvrId;
	/**접속서버아이디  */
	private String connSrvrId;
	/**장치연결유형코드  */
	private String devConnTypeCd;
	/** 상태코드 */
	private String sttusCd;
	/** 현장장치개통상태 */
	private SpotDevSubsSttus spotDevSubsSttusCd;
	/** 수집주기시간 */
	private Integer colecCyclTime;
	/** 유휴판단기준시간 */
	private Integer idleJdgmBaseTime;
	/** 재접속주기시간 */
	private Integer recnCyclTime;
	/** 재접속시도횟수 */
	private Integer recnTryTmscnt;
	/** 행제한여부 */
	private String rowRstrtnYn;
	/** 제조사명 */
	private String makrNm;
	/** 모델명 */
	private String modelNm;
	/** 상품일련번호 */
	private String prodSeq;
	/** 등록일련번호 */
	private String regSeq;
	/** 펌웨어버전번호 */
	private String frmwrVerNo;
	/** 인증방식코드 */
	private AthnForml athnFormlCd;
	/** 인증요청번호 */
	private String athnRqtNo;
	/** 인증번호 */
	@JsonIgnore
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
	private String useYn;
	/** 삭제여부 */
	private UseYn delYn;

	public SpotDevBas() {

	}

	public SpotDevBas(Long svcTgtSeq, Long spotDevSeq) {
		this.svcTgtSeq = svcTgtSeq;
		this.spotDevSeq = spotDevSeq;
	}

	public SpotDevBas(Long svcTgtSeq, Long spotDevSeq, String spotDevId) {
		this.svcTgtSeq = svcTgtSeq;
		this.spotDevSeq = spotDevSeq;
		this.spotDevId = spotDevId;
	}

	/**
	 * @return the svcTgtSeq
	 */
	@JsonIgnore
	public Long getSvcTgtSeq() {
		return svcTgtSeq;
	}

	/**
	 * @param svcTgtSeq the svcTgtSeq to set
	 */
	@JsonProperty
	public void setSvcTgtSeq(Long svcTgtSeq) {
		this.svcTgtSeq = svcTgtSeq;
	}

	/**
	 * @return the spotDevSeq
	 */
	@JsonIgnore
	public Long getSpotDevSeq() {
		return spotDevSeq;
	}



	/**
	 * @param spotDevSeq the spotDevSeq to set
	 */
	@JsonProperty
	public void setSpotDevSeq(Long spotDevSeq) {
		this.spotDevSeq = spotDevSeq;
	}



	/**
	 * @return the spotDevId
	 */
	public String getSpotDevId() {
		return spotDevId;
	}



	/**
	 * @param spotDevId the spotDevId to set
	 */
	public void setSpotDevId(String spotDevId) {
		this.spotDevId = spotDevId;
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
	 * @return the upSvcTgtSeq
	 */
	public Long getUpSvcTgtSeq() {
		return upSvcTgtSeq;
	}



	/**
	 * @param upSvcTgtSeq the upSvcTgtSeq to set
	 */
	public void setUpSvcTgtSeq(Long upSvcTgtSeq) {
		this.upSvcTgtSeq = upSvcTgtSeq;
	}



	/**
	 * @return the upGwCnctId
	 */
	public String getUpGwCnctId() {
		return upGwCnctId;
	}



	/**
	 * @param upGwCnctId the upGwCnctId to set
	 */
	public void setUpGwCnctId(String upGwCnctId) {
		this.upGwCnctId = upGwCnctId;
	}



	/**
	 * @return the upSpotDevId
	 */
	public String getUpSpotDevId() {
		return upSpotDevId;
	}



	/**
	 * @param upSpotDevId the upSpotDevId to set
	 */
	public void setUpSpotDevId(String upSpotDevId) {
		this.upSpotDevId = upSpotDevId;
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
	 * @return the spotDevNm
	 */
	public String getSpotDevNm() {
		return spotDevNm;
	}



	/**
	 * @param spotDevNm the spotDevNm to set
	 */
	public void setSpotDevNm(String spotDevNm) {
		this.spotDevNm = spotDevNm;
	}



	/**
	 * @return the cnctIpadr
	 */
	public String getCnctIpadr() {
		return cnctIpadr;
	}



	/**
	 * @param cnctIpadr the cnctIpadr to set
	 */
	public void setCnctIpadr(String cnctIpadr) {
		this.cnctIpadr = cnctIpadr;
	}



	/**
	 * @return the virtlSpotDevId
	 */
	public String getVirtlSpotDevId() {
		return virtlSpotDevId;
	}



	/**
	 * @param virtlSpotDevId the virtlSpotDevId to set
	 */
	public void setVirtlSpotDevId(String virtlSpotDevId) {
		this.virtlSpotDevId = virtlSpotDevId;
	}



	/**
	 * @return the subsSttusCd
	 */
	public SpotDevSubsSttus getSubsSttusCd() {
		return subsSttusCd;
	}



	/**
	 * @param subsSttusCd the subsSttusCd to set
	 */
	public void setSubsSttusCd(String subsSttusCd) {
		this.subsSttusCd = SpotDevSubsSttus.fromString(subsSttusCd);
	}



	/**
	 * @return the sessnId
	 */
	public String getSessnId() {
		return sessnId;
	}



	/**
	 * @param sessnId the sessnId to set
	 */
	public void setSessnId(String sessnId) {
		this.sessnId = sessnId;
	}



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
	 * @return the connSrvrId
	 */
	public String getConnSrvrId() {
		return connSrvrId;
	}



	/**
	 * @param connSrvrId the connSrvrId to set
	 */
	public void setConnSrvrId(String connSrvrId) {
		this.connSrvrId = connSrvrId;
	}



	/**
	 * @return the devConnTypeCd
	 */
	public String getDevConnTypeCd() {
		return devConnTypeCd;
	}



	/**
	 * @param devConnTypeCd the devConnTypeCd to set
	 */
	public void setDevConnTypeCd(String devConnTypeCd) {
		this.devConnTypeCd = devConnTypeCd;
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

	public SpotDevSubsSttus getSpotDevSubsSttusCd() {
		return spotDevSubsSttusCd;
	}

	public void setSpotDevSubsSttusCd(String spotDevSubsSttusCd) {
		this.spotDevSubsSttusCd = SpotDevSubsSttus.fromString(spotDevSubsSttusCd);
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
	 * @return the makrNm
	 */
	public String getMakrNm() {
		return makrNm;
	}



	/**
	 * @param makrNm the makrNm to set
	 */
	public void setMakrNm(String makrNm) {
		this.makrNm = makrNm;
	}



	/**
	 * @return the modelNm
	 */
	public String getModelNm() {
		return modelNm;
	}



	/**
	 * @param modelNm the modelNm to set
	 */
	public void setModelNm(String modelNm) {
		this.modelNm = modelNm;
	}



	/**
	 * @return the prodSeq
	 */
	public String getProdSeq() {
		return prodSeq;
	}



	/**
	 * @param prodSeq the prodSeq to set
	 */
	public void setProdSeq(String prodSeq) {
		this.prodSeq = prodSeq;
	}



	/**
	 * @return the regSeq
	 */
	public String getRegSeq() {
		return regSeq;
	}



	/**
	 * @param regSeq the regSeq to set
	 */
	public void setRegSeq(String regSeq) {
		this.regSeq = regSeq;
	}



	/**
	 * @return the frmwrVerNo
	 */
	public String getFrmwrVerNo() {
		return frmwrVerNo;
	}



	/**
	 * @param frmwrVerNo the frmwrVerNo to set
	 */
	public void setFrmwrVerNo(String frmwrVerNo) {
		this.frmwrVerNo = frmwrVerNo;
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
	public String getUseYn() {
		return useYn;
	}



	/**
	 * @param useYn the useYn to set
	 */
	public void setUseYn(String useYn) {
		this.useYn = useYn;
	}



	/**
	 * @return the delYn
	 */
	public UseYn getDelYn() {
		return delYn;
	}



	/**
	 * @param delYn the delYn to set
	 */
	public void setDelYn(String delYn) {
		this.delYn = UseYn.fromString(delYn);
	}



	public UseYn getConnYn() {
		return connYn;
	}

	public void setConnYn(UseYn connYn) {
		this.connYn = connYn;
	}

	public String getRowRstrtnYn() {
		return rowRstrtnYn;
	}

	public void setRowRstrtnYn(String rowRstrtnYn) {
		this.rowRstrtnYn = rowRstrtnYn;
	}

    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}

}
