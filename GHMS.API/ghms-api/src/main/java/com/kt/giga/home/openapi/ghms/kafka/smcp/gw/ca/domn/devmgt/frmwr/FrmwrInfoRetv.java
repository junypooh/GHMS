/**
 * <PRE>
 *  Project : GWCommAgent
 *  Package : com.kt.smcp.gw.ca.gwadaptor.sif.contl.vo
 * </PRE>
 * @file   : SifFrmwrUdateRqtVO.java
 * @date   : 2013. 12. 11. 오후 3:26:07
 * @author : byw
 * @brief  :
 *  변경이력    :
 *        이름     : 일자          : 근거자료   : 변경내용
 *       ------------------------------------
 *        byw  : 2013. 12. 11.       :            : 신규 개발.
 */
package com.kt.giga.home.openapi.ghms.kafka.smcp.gw.ca.domn.devmgt.frmwr;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.kt.smcp.gw.ca.domn.CorePrcssData;

/**
 * 펌웨어정보요청
 * @since	: 2015. 1. 17.
 * @author	: CBJ
 * <PRE>
 * Revision History
 * ----------------------------------------------------
 * 2015. 1. 17. CBJ: 최초작성
 * ----------------------------------------------------
 * </PRE>
 */
public class FrmwrInfoRetv extends CorePrcssData implements Serializable, Cloneable
{
	/** 직렬화아이디 */
	private static final long serialVersionUID = 9001611598977653774L;

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
	/** 펌웨어버전 */
	private String frmwrVer;
	/** 펌웨어버전번호 */
	private Integer frmwrVerNo;
	/** 패키지정보 */
	private List<PkgInfo> pkgInfos = new ArrayList<PkgInfo>();

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.DEFAULT_STYLE);
	}

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

	public List<PkgInfo> getPkgInfos() {
		return pkgInfos;
	}

	public void setPkgInfos(List<PkgInfo> pkgInfos) {
		this.pkgInfos = pkgInfos;
	}


}
