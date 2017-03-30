package com.kt.giga.home.openapi.ghms.kafka.smcp.gw.ca.domn.devmgt.frmwr;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.kt.smcp.gw.ca.domn.CorePrcssData;

public class FrmwrUdateSttus extends CorePrcssData implements Serializable, Cloneable
{
	/** 직렬화아이디 */
	private static final long serialVersionUID = 6756657251556008564L;

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
	/** 장치응답 상태코드 */
	private String devTrtSttusCd = "";	
	/** 업데이트트랜잭션아이디 */
	private String udateTransacId;
	/** 펌웨어일련번호 */
	private Long frmwrSeq;
	/** 펌웨어파일경로명 */
	private String frmwrFilePathNm;
	/** 펌웨어버전 */
	private String frmwrVer;
	/** 펌웨어버전번호 */
	private Integer frmwrVerNo;
	/** 펌웨어크기 */
	private Integer frmwrSize;
	/** 패키지정보 */
	private List<PkgInfo> pkgInfos = new ArrayList<PkgInfo>();
	/** 발생일시 */
	private Date occDt;
	/** 상태코드 */
	private String sttusCd;
	/** 상태값 */
	private String sttusVal;

	@Override
	public boolean isEmptyData()
	{
		return false;
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
	public String getUdateTransacId() {
		return udateTransacId;
	}
	public void setUdateTransacId(String udateTransacId) {
		this.udateTransacId = udateTransacId;
	}
	public Long getFrmwrSeq() {
		return frmwrSeq;
	}
	public void setFrmwrSeq(Long frmwrSeq) {
		this.frmwrSeq = frmwrSeq;
	}
	public String getFrmwrFilePathNm() {
		return frmwrFilePathNm;
	}
	public void setFrmwrFilePathNm(String frmwrFilePathNm) {
		this.frmwrFilePathNm = frmwrFilePathNm;
	}
	public String getFrmwrVer() {
		return frmwrVer;
	}
	public void setFrmwrVer(String frmwrVer) {
		this.frmwrVer = frmwrVer;
	}
	public Integer getFrmwrVerNo() {
		return frmwrVerNo;
	}
	public void setFrmwrVerNo(Integer frmwrVerNo) {
		this.frmwrVerNo = frmwrVerNo;
	}
	public Integer getFrmwrSize() {
		return frmwrSize;
	}
	public void setFrmwrSize(Integer frmwrSize) {
		this.frmwrSize = frmwrSize;
	}
	public List<PkgInfo> getPkgInfos() {
		return pkgInfos;
	}
	public void setPkgInfos(List<PkgInfo> pkgInfos) {
		this.pkgInfos = pkgInfos;
	}
	public Date getOccDt() {
		return occDt;
	}
	public void setOccDt(Date occDt) {
		this.occDt = occDt;
	}
	public String getSttusCd() {
		return sttusCd;
	}
	public void setSttusCd(String sttusCd) {
		this.sttusCd = sttusCd;
	}
	public String getSttusVal() {
		return sttusVal;
	}
	public void setSttusVal(String sttusVal) {
		this.sttusVal = sttusVal;
	}
	public String getDevTrtSttusCd() {
		return devTrtSttusCd;
	}
	public void setDevTrtSttusCd(String devTrtSttusCd) {
		this.devTrtSttusCd = devTrtSttusCd;
	}
	
}
