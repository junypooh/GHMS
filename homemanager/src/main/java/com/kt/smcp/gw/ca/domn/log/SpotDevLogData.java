package com.kt.smcp.gw.ca.domn.log;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.kt.smcp.gw.ca.comm.GwCode.DevCnctType;
import com.kt.smcp.gw.ca.domn.CorePrcssData;

/**
 * 현장장치전달데이터 클래스
 * @since	: 2014. 11. 2.
 * @author	: CBJ
 * <PRE>
 * Revision History
 * ----------------------------------------------------
 * 2014. 11. 2. CBJ: 최초작성
 * ----------------------------------------------------
 * </PRE>
 */
public class SpotDevLogData extends CorePrcssData implements Serializable, Cloneable
{
	/** 직렬화아이디 */
	private static final long serialVersionUID = 3318501925906623051L;

	/** 서비스대상일련번호 */
	private Long svcTgtSeq;
	/** 현장장치일련번호 */
	private Long spotDevSeq;
	/** M2M 서비스 번호 */
	private Integer m2mSvcNo;
	/** 현장장치아이디 */
	private String spotDevId;
	/** 게이트웨이연결아이디 */
	private String gwCnctId;
	/** 모델명 */
	private String modelNm;
	/** 단위서비스코드 */
	private String unitSvcCd;
	/**장치연결유형코드  */
	private DevCnctType devConnTypeCd;

	/** 전달행 리스트 */
	private List<LogRow> logRows = new ArrayList<LogRow>();

	@Override
	public String toString()
	{
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SIMPLE_STYLE);
	}

	@Override
	public Object clone() throws CloneNotSupportedException
	{
		return super.clone();
	}

	@Override
	public boolean isEmptyData()
	{
		if(logRows == null || logRows.size() == 0)
		{
			return true;
		}
		for(LogRow logRow : logRows)
		{
			//빈객체가 아니면
			if(!logRow.isEmptyData())
			{
				return false;
			}
		}
		return true;
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

	public String getGwCnctId() {
		return gwCnctId;
	}

	public void setGwCnctId(String gwCnctId) {
		this.gwCnctId = gwCnctId;
	}



	public String getModelNm() {
		return modelNm;
	}

	public void setModelNm(String modelNm) {
		this.modelNm = modelNm;
	}

	public String getUnitSvcCd() {
		return unitSvcCd;
	}

	public void setUnitSvcCd(String unitSvcCd) {
		this.unitSvcCd = unitSvcCd;
	}


	public DevCnctType getDevConnTypeCd() {
		return devConnTypeCd;
	}

	public void setDevConnTypeCd(DevCnctType devConnTypeCd) {
		this.devConnTypeCd = devConnTypeCd;
	}

	public List<LogRow> getLogRows() {
		return logRows;
	}

	public void setLogRows(List<LogRow> logRows) {
		this.logRows = logRows;
	}

}
