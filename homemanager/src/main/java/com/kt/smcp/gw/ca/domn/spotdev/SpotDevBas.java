package com.kt.smcp.gw.ca.domn.spotdev;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.kt.smcp.gw.ca.comm.GwCode.AthnForml;
import com.kt.smcp.gw.ca.comm.GwCode.DevCnctType;
import com.kt.smcp.gw.ca.comm.GwCode.SpotDevSttus;
import com.kt.smcp.gw.ca.comm.GwCode.SpotDevSubsSttus;
import com.kt.smcp.gw.ca.comm.GwCode.UseYn;
import com.kt.smcp.gw.ca.domn.CorePrcssData;

@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class SpotDevBas extends CorePrcssData implements Serializable, Cloneable
{
	/** 직렬화아이디 */
	private static final long serialVersionUID = -5620071222896151382L;

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
	/** 상위서비스대상일련번호 */
	private Long upSvcTgtSeq;
	/** 상위게이트웨이연결아이디 */
	private String upGwCnctId;
	/** 상위현장장치아이디 */
	private String upSpotDevId;
	/** 단위서비스코드 */
	private String unitSvcCd;
	/**현장장치명  */
	private String spotDevNm;
	/**연결IP주소  */
	private String cnctIpadr;
	/**가상현장장치아이디  */
	private String virtlSpotDevId;
	/**청약상태코드  */
	private SpotDevSubsSttus subsSttusCd;
	//TODO DB스키마추가
	/** 접속여부 */
	private UseYn connYn;
	/**세션아이디  */
	private String sessnId;
	/**소속서버아이디  */
	private String posSrvrId;
	/**접속서버아이디  */
	private String connSrvrId;
	/**장치연결유형코드  */
	private DevCnctType devConnTypeCd;
	/** 상태코드 */
	private SpotDevSttus sttusCd;
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
	/** 행초최대수 */
	private Long rowSecMaxNum;
	/** 행10초최대수 */
	private Long row10secMaxNum;
	/** 행분최대수 */
	private Long rowMnutMaxNum;
	/** 행10분최대수 */
	private Long row10mnutMaxNum;
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
	/** 삭제여부 */
	private UseYn delYn;

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

	@Override
	public boolean isEmptyData()
	{
		if(this.existPkValue())
		{
			return false;
		}
		return true;
	}

	public SpotDevBas()
	{

	}

	public SpotDevBas(SpotDevBas spotDevBas)
	{
		/** 서비스대상일련번호 */
		this.setSvcTgtSeq(spotDevBas.getSvcTgtSeq());
		/** 현장장치일련번호 */
		this.setSpotDevSeq(spotDevBas.getSpotDevSeq());
		/** 게이트웨이연결아이디 */
		this.setGwCnctId(spotDevBas.getGwCnctId());
		/** M2M서비스번호 */
		this.setM2mSvcNo(spotDevBas.getM2mSvcNo());
		/** 현장장치아이디 */
		this.setSpotDevId(spotDevBas.getSpotDevId());
		/** 상위게이트웨이연결아이디 */
		this.setUpGwCnctId(spotDevBas.getUpGwCnctId());
		/** 상위현장장치아이디 */
		this.setUpSpotDevId(spotDevBas.getUpSpotDevId());
		/**현장장치명  */
		this.setSpotDevNm(spotDevBas.getSpotDevNm());
		/**연결IP주소  */
		this.setCnctIpadr(spotDevBas.getCnctIpadr());
		/**가상현장장치아이디  */
		this.setVirtlSpotDevId(spotDevBas.getVirtlSpotDevId());
		/**세션아이디  */
		this.setSessnId(spotDevBas.getSessnId());
		/**소속서버아이디  */
		this.setPosSrvrId(spotDevBas.getPosSrvrId());
		/**접속서버아이디  */
		this.setConnSrvrId(spotDevBas.getConnSrvrId());
		/**장치연결유형코드  */
		if(spotDevBas.getDevConnTypeCd() != null)
		{
			this.setDevConnTypeCd(spotDevBas.getDevConnTypeCd().getValue());
		}
		/** 상태코드 */
		this.setSttusCd(spotDevBas.getSttusCd().getValue());
		/** 현장장치개통상태 */
		if(spotDevBas.getSpotDevSubsSttusCd() != null)
		{
			this.setSpotDevSubsSttusCd(spotDevBas.getSpotDevSubsSttusCd().getValue());
		}
		/** 수집주기시간 */
		this.setColecCyclTime(spotDevBas.getColecCyclTime());
		/** 유휴판단기준시간 */
		this.setIdleJdgmBaseTime(spotDevBas.getIdleJdgmBaseTime());
		/** 재접속주기시간 */
		this.setRecnCyclTime(spotDevBas.getRecnCyclTime());
		/** 재접속시도횟수 */
		this.setRecnTryTmscnt(spotDevBas.getRecnTryTmscnt());
		/** 제조사명 */
		this.setMakrNm(spotDevBas.getMakrNm());
		/** 모델명 */
		this.setModelNm(spotDevBas.getModelNm());
		/** 상품일련번호 */
		this.setProdSeq(spotDevBas.getProdSeq());
		/** 등록일련번호 */
		this.setRegSeq(spotDevBas.getRegSeq());
		/** 펌웨어버전번호 */
		this.setFrmwrVerNo(spotDevBas.getFrmwrVerNo());
		/** 인증방식코드 */
		if(spotDevBas.getAthnFormlCd() != null)
		{
			this.setAthnFormlCd(spotDevBas.getAthnFormlCd().getValue());
		}
		/** 인증요청번호 */
		this.setAthnRqtNo(spotDevBas.getAthnRqtNo());
		/** 인증번호 */
		this.setAthnNo(spotDevBas.getAthnNo());
		/** 설치위치내용 */
		this.setEqpLoSbst(spotDevBas.getEqpLoSbst());
		/** 생성일시 */
		this.setCretDt(spotDevBas.getCretDt());
		/** 수정일시 */
		this.setAmdDt(spotDevBas.getAmdDt());
		/** 최종동작일시 */
		this.setLastMtnDt(spotDevBas.getLastMtnDt());
		/** 비고 */
		this.setRmark(spotDevBas.getRmark() );
		/** 사용여부 */
		if(spotDevBas.getUseYn() != null)
		{
			this.setUseYn(spotDevBas.getUseYn().getValue());
		}
	}

	public void update(SpotDevBas spotDev)
	{
		/** 서비스대상일련번호 */
		if(spotDev.getSvcTgtSeq() != null)
		{
			this.setSvcTgtSeq(spotDev.getSvcTgtSeq());
		}
		/** 현장장치일련번호 */
		if(spotDev.getSpotDevSeq() != null)
		{
			this.setSpotDevSeq(spotDev.getSpotDevSeq());
		}
		/** 게이트웨이연결아이디 */
		if(spotDev.getGwCnctId() != null)
		{
			this.setGwCnctId(spotDev.getGwCnctId());
		}
		/** M2M서비스번호 */
		if(spotDev.getM2mSvcNo() != null)
		{
			this.setM2mSvcNo(spotDev.getM2mSvcNo());
		}
		/** 현장장치아이디 */
		if(spotDev.getSpotDevId() != null)
		{
			this.setSpotDevId(spotDev.getSpotDevId());
		}
		/** 상위게이트웨이연결아이디 */
		if(spotDev.getUpGwCnctId() != null)
		{
			this.setUpGwCnctId(spotDev.getUpGwCnctId());
		}

		/** 상위현장장치아이디 */
		if(spotDev.getUpSpotDevId() != null)
		{
			this.setUpSpotDevId(spotDev.getUpSpotDevId());
		}
		/**현장장치명  */
		if(spotDev.getSpotDevNm() != null)
		{
			this.setSpotDevNm(spotDev.getSpotDevNm());
		}
		/**연결IP주소  */
		if(spotDev.getCnctIpadr() != null)
		{
			this.setCnctIpadr(spotDev.getCnctIpadr());
		}
		/**가상현장장치아이디  */
		if(spotDev.getVirtlSpotDevId() != null)
		{
			this.setVirtlSpotDevId(spotDev.getVirtlSpotDevId());
		}
		/**세션아이디  */
		if(spotDev.getSessnId() != null)
		{
			this.setSessnId(spotDev.getSessnId());
		}
		/**소속서버아이디  */
		if(spotDev.getPosSrvrId() != null)
		{
			this.setPosSrvrId(spotDev.getPosSrvrId());
		}
		/**접속서버아이디  */
		if(spotDev.getConnSrvrId() != null)
		{
			this.setConnSrvrId(spotDev.getConnSrvrId());
		}
		/**장치연결유형코드  */
		if(spotDev.getDevConnTypeCd() != null)
		{
			this.setDevConnTypeCd(spotDev.getDevConnTypeCd().getValue());
		}
		/** 상태코드 */
		if(spotDev.getSttusCd() != null)
		{
			this.setSttusCd(spotDev.getSttusCd().getValue());
		}
		/** 현장장치개통상태 */
		if(spotDev.getSpotDevSubsSttusCd() != null)
		{
			this.setSpotDevSubsSttusCd(spotDev.getSpotDevSubsSttusCd().getValue());
		}
		/** 수집주기시간 */
		if(spotDev.getColecCyclTime() != null)
		{
			this.setColecCyclTime(spotDev.getColecCyclTime());
		}

		/** 유휴판단기준시간 */
		if(spotDev.getIdleJdgmBaseTime() != null)
		{
			this.setIdleJdgmBaseTime(spotDev.getIdleJdgmBaseTime());
		}
		/** 재접속주기시간 */
		if(spotDev.getRecnCyclTime() != null)
		{
			this.setRecnCyclTime(spotDev.getRecnCyclTime());
		}

		/** 재접속시도횟수 */
		if(spotDev.getRecnTryTmscnt() != null)
		{
			this.setRecnTryTmscnt(spotDev.getRecnTryTmscnt());
		}
		/** 제조사명 */
		if(spotDev.getMakrNm() != null)
		{
			this.setMakrNm(spotDev.getMakrNm());
		}

		/** 모델명 */
		if(spotDev.getModelNm() != null)
		{
			this.setModelNm(spotDev.getModelNm());
		}
		/** 상품일련번호 */
		if(spotDev.getProdSeq() != null)
		{
			this.setProdSeq(spotDev.getProdSeq());
		}
		/** 등록일련번호 */
		if(spotDev.getRegSeq() != null)
		{
			this.setRegSeq(spotDev.getRegSeq());
		}
		/** 펌웨어버전번호 */
		if(spotDev.getFrmwrVerNo() != null)
		{
			this.setFrmwrVerNo(spotDev.getFrmwrVerNo());
		}
		/** 인증방식코드 */
		if(spotDev.getAthnFormlCd() != null)
		{
			this.setAthnFormlCd(spotDev.getAthnFormlCd().getValue());
		}
		if(spotDev.getAthnFormlCd() != null)
		{
			this.setAthnFormlCd(spotDev.getAthnFormlCd().getValue());
		}
		/** 인증요청번호 */
		if(spotDev.getAthnRqtNo() != null)
		{
			this.setAthnRqtNo(spotDev.getAthnRqtNo());
		}
		/** 인증번호 */
		if(spotDev.getAthnNo() != null)
		{
			this.setAthnNo(spotDev.getAthnNo());
		}
		/** 설치위치내용 */
		if(spotDev.getEqpLoSbst() != null)
		{
			this.setEqpLoSbst(spotDev.getEqpLoSbst());
		}
		/** 생성일시 */
		if(spotDev.getCretDt() != null)
		{
			this.setCretDt(spotDev.getCretDt());
		}
		/** 수정일시 */
		if(spotDev.getAmdDt() != null)
		{
			this.setAmdDt(spotDev.getAmdDt());
		}
		/** 최종동작일시 */
		if(spotDev.getLastMtnDt() != null)
		{
			this.setLastMtnDt(spotDev.getLastMtnDt());
		}
		/** 비고 */
		if(spotDev.getRmark() != null)
		{
			this.setRmark(spotDev.getRmark() );
		}

		/** 사용여부 */
		if(spotDev.getUseYn() != null)
		{
			this.setUseYn(spotDev.getUseYn().getValue());
		}
	}


	public boolean existPkValue()
	{
		if(this.svcTgtSeq  != null && this.spotDevSeq != null)
		{
			return true;
		}

		if(this.svcTgtSeq != null && this.gwCnctId != null && this.spotDevId != null)
		{
			return true;
		}

		if(this.m2mSvcNo != null && this.spotDevId != null)
		{
			return true;
		}

		return false;

	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode()
	{
		if(spotDevId == null)
		{
			return 0;
		}
		return spotDevId.hashCode();
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj)
	{
		if( !(obj instanceof SpotDevBas) )
		{
			return false;
		}

		SpotDevBas spotDevBas = (SpotDevBas)obj;

		if(spotDevBas.svcTgtSeq != null && spotDevBas.spotDevSeq != null
				&& this.svcTgtSeq  != null && this.spotDevSeq != null)
		{
			if(this.svcTgtSeq.longValue() == spotDevBas.svcTgtSeq.longValue() &&
					this.spotDevSeq.longValue() == spotDevBas.spotDevSeq.longValue())
			{
				return true;
			}
			else
			{
				return false;
			}
		}

		if(spotDevBas.m2mSvcNo != null && spotDevBas.spotDevId != null && this.m2mSvcNo != null && this.spotDevId != null)
		{
			//if(spotDevBas.m2mSvcNo.equals(this.m2mSvcNo) && spotDevBas.spotDevId.equals(this.spotDevId))
			if(spotDevBas.m2mSvcNo.intValue() == this.m2mSvcNo.intValue() && spotDevBas.spotDevId.equals(this.spotDevId))
			{
				return true;
			}
			else
			{
				return false;
			}
		}

		//TODO 검증해볼 것. 서비스대상일련번호 없이도 유니크해야함. 청약에 기준에서 체크 할 것
		if(spotDevBas.gwCnctId != null && spotDevBas.spotDevId != null && this.gwCnctId != null && this.spotDevId != null )
		{
			if(spotDevBas.gwCnctId.equals(this.gwCnctId) && spotDevBas.spotDevId.equals(this.spotDevId))
			{
				return true;
			}
			else
			{
				return false;
			}
		}

//		if(spotDevBas.svcTgtSeq != null && spotDevBas.gwCnctId != null && spotDevBas.spotDevId != null
//				&& this.svcTgtSeq != null && this.gwCnctId != null && this.spotDevId != null )
//		{
//				if(spotDevBas.svcTgtSeq.longValue() == this.svcTgtSeq.longValue() &&
//					spotDevBas.gwCnctId.equals(this.gwCnctId) &&
//					spotDevBas.spotDevId.equals(this.spotDevId))
//				{
//					return true;
//				}
//				else
//				{
//					return false;
//				}
//		}


		return false;
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



//	/**
//	 * @return the gwCnctDivCd
//	 */
//	public GwCnctDiv getGwCnctDivCd() {
//		return gwCnctDivCd;
//	}
//
//
//
//	/**
//	 * @param gwCnctDivCd the gwCnctDivCd to set
//	 */
//	public void setGwCnctDivCd(String gwCnctDivCd) {
//		this.gwCnctDivCd = GwCnctDiv.fromString(gwCnctDivCd);
//	}



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



	public UseYn getConnYn() {
		return connYn;
	}

	public void setConnYn(String connYn) {
		this.connYn = UseYn.fromString(connYn);
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
	public DevCnctType getDevConnTypeCd() {
		return devConnTypeCd;
	}



	/**
	 * @param devConnTypeCd the devConnTypeCd to set
	 */
	public void setDevConnTypeCd(String devConnTypeCd) {
		this.devConnTypeCd = DevCnctType.fromString(devConnTypeCd);
	}



	/**
	 * @return the sttusCd
	 */
	public SpotDevSttus getSttusCd() {
		return sttusCd;
	}



	/**
	 * @param sttusCd the sttusCd to set
	 */
	public void setSttusCd(String sttusCd) {
		this.sttusCd = SpotDevSttus.fromString(sttusCd);
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




	public String getRowRstrtnYn() {
		return rowRstrtnYn;
	}

	public void setRowRstrtnYn(String rowRstrtnYn) {
		this.rowRstrtnYn = rowRstrtnYn;
	}

	public Long getRowSecMaxNum() {
		return rowSecMaxNum;
	}

	public void setRowSecMaxNum(Long rowSecMaxNum) {
		this.rowSecMaxNum = rowSecMaxNum;
	}

	public Long getRow10secMaxNum() {
		return row10secMaxNum;
	}

	public void setRow10secMaxNum(Long row10secMaxNum) {
		this.row10secMaxNum = row10secMaxNum;
	}

	public Long getRowMnutMaxNum() {
		return rowMnutMaxNum;
	}

	public void setRowMnutMaxNum(Long rowMnutMaxNum) {
		this.rowMnutMaxNum = rowMnutMaxNum;
	}

	public Long getRow10mnutMaxNum() {
		return row10mnutMaxNum;
	}

	public void setRow10mnutMaxNum(Long row10mnutMaxNum) {
		this.row10mnutMaxNum = row10mnutMaxNum;
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
}
