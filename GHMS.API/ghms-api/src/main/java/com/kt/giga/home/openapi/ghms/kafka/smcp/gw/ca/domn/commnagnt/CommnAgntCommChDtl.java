package com.kt.giga.home.openapi.ghms.kafka.smcp.gw.ca.domn.commnagnt;

import java.io.Serializable;

import com.kt.giga.home.openapi.ghms.kafka.smcp.gw.ca.comm.GwCode.AthnForml;
import com.kt.giga.home.openapi.ghms.kafka.smcp.gw.ca.comm.GwCode.CnctType;
import com.kt.giga.home.openapi.ghms.kafka.smcp.gw.ca.comm.GwCode.CommChType;
import com.kt.giga.home.openapi.ghms.kafka.smcp.gw.ca.comm.GwCode.CommDataTrtModeType;
import com.kt.giga.home.openapi.ghms.kafka.smcp.gw.ca.comm.GwCode.DataProtType;
import com.kt.giga.home.openapi.ghms.kafka.smcp.gw.ca.comm.GwCode.EcodType;
import com.kt.giga.home.openapi.ghms.kafka.smcp.gw.ca.comm.GwCode.IfType;
import com.kt.giga.home.openapi.ghms.kafka.smcp.gw.ca.comm.GwCode.UseYn;

/**
 * <PRE>
 *  ClassName GwCommChDtlVO
 * </PRE>
 * @brief
 * @version 1.0
 * @date 2014. 1. 22. 오후 3:08:24
 * @author byw
 */
public class CommnAgntCommChDtl  implements Serializable {
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
//	/** 헤더프로토콜코드 */
//	private ProtocolType headProtCd;
//	/** 이진변환유형코드 */
//	private EncodingType binChangeTypeCd;
//	/** 헤더길이 */
//	private Integer headLen;
//	/** 본문최대길이 */
//	private Long bdyMaxLen;
//	/** 바디길이필드위치값 */
//	private Integer bdyLenFieldLoVal;
//	/** 바디길이필드길이 */
//	private Integer bdyLenFieldLen;
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
//	public ProtocolType getHeadProtCd() {
//		return headProtCd;
//	}
//	/**
//	 * @param headProtCd the headProtCd to set
//	 */
//	public void setHeadProtCd(String headProtCd) {
//		this.headProtCd = ProtocolType.fromString(headProtCd);
//	}
//	/**
//	 * @return the binChangeTypeCd
//	 */
//	public EncodingType getBinChangeTypeCd() {
//		return binChangeTypeCd;
//	}
//	/**
//	 * @param binChangeTypeCd the binChangeTypeCd to set
//	 */
//	public void setBinChangeTypeCd(String binChangeTypeCd) {
//		this.binChangeTypeCd = EncodingType.fromString(binChangeTypeCd);
//	}
//	/**
//	 * @return the headLen
//	 */
//	public Integer getHeadLen() {
//		return headLen;
//	}
//	/**
//	 * @param headLen the headLen to set
//	 */
//	public void setHeadLen(Integer headLen) {
//		this.headLen = headLen;
//	}
//	/**
//	 * @return the bdyMaxLen
//	 */
//	public Long getBdyMaxLen() {
//		return bdyMaxLen;
//	}
//	/**
//	 * @param bdyMaxLen the bdyMaxLen to set
//	 */
//	public void setBdyMaxLen(Long bdyMaxLen) {
//		this.bdyMaxLen = bdyMaxLen;
//	}
//	/**
//	 * @return the bdyLenFieldLoVal
//	 */
//	public Integer getBdyLenFieldLoVal() {
//		return bdyLenFieldLoVal;
//	}
//	/**
//	 * @param bdyLenFieldLoVal the bdyLenFieldLoVal to set
//	 */
//	public void setBdyLenFieldLoVal(Integer bdyLenFieldLoVal) {
//		this.bdyLenFieldLoVal = bdyLenFieldLoVal;
//	}
//	/**
//	 * @return the bdyLenFieldLen
//	 */
//	public Integer getBdyLenFieldLen() {
//		return bdyLenFieldLen;
//	}
//	/**
//	 * @param bdyLenFieldLen the bdyLenFieldLen to set
//	 */
//	public void setBdyLenFieldLen(Integer bdyLenFieldLen) {
//		this.bdyLenFieldLen = bdyLenFieldLen;
//	}
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
