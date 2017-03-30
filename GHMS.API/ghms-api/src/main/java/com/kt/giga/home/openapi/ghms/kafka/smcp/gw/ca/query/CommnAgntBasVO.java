package com.kt.giga.home.openapi.ghms.kafka.smcp.gw.ca.query;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.kt.giga.home.openapi.ghms.kafka.smcp.gw.ca.comm.GwCode.AthnForml;
import com.kt.giga.home.openapi.ghms.kafka.smcp.gw.ca.comm.GwCode.CnctType;
import com.kt.giga.home.openapi.ghms.kafka.smcp.gw.ca.comm.GwCode.CommChType;
import com.kt.giga.home.openapi.ghms.kafka.smcp.gw.ca.comm.GwCode.CommDataTrtModeType;
import com.kt.giga.home.openapi.ghms.kafka.smcp.gw.ca.comm.GwCode.CommnAgntSttus;
import com.kt.giga.home.openapi.ghms.kafka.smcp.gw.ca.comm.GwCode.DataProtType;
import com.kt.giga.home.openapi.ghms.kafka.smcp.gw.ca.comm.GwCode.EcodType;
import com.kt.giga.home.openapi.ghms.kafka.smcp.gw.ca.comm.GwCode.IfType;
import com.kt.giga.home.openapi.ghms.kafka.smcp.gw.ca.comm.GwCode.UseYn;


@JsonInclude(Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
public class CommnAgntBasVO {
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
	private long cretDt;
	/** 생성자아이디 */
	private String cretrId;
	/** 수정자아이디 */
	private String amdrId;
	/** 수정일시 */
	private long amdDt;
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
	public long getCretDt() {
		return cretDt;
	}

	/**
	 * @param cretDt the cretDt to set
	 */
	public void setCretDt(long cretDt) {
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
	public long getAmdDt() {
		return amdDt;
	}

	/**
	 * @param amdDt the amdDt to set
	 */
	public void setAmdDt(long amdDt) {
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





	public static class CommnAgntSetupDtlVO {

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

	public static class CommnAgntCommChDtlVO {
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
