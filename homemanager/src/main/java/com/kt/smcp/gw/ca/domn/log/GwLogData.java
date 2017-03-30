package com.kt.smcp.gw.ca.domn.log;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.kt.smcp.gw.ca.domn.CorePrcssData;

/**
 * 게이트웨이전달데이터 클래스
 * @since	: 2014. 11. 2.
 * @author	: CBJ
 * <PRE>
 * Revision History
 * ----------------------------------------------------
 * 2014. 11. 2. CBJ: 최초작성
 * ----------------------------------------------------
 * </PRE>
 */
public class GwLogData extends CorePrcssData implements Serializable, Cloneable
{
	/** 직렬화아이디 */
	private static final long serialVersionUID = 5511330085980288738L;

	/** 서비스대상일련번호 */
	private Long svcTgtSeq;
	/** 게이트웨이연결아이디 */
	private String gwCnctId;
	/** 단위서비스코드 */
	private String unitSvcCd;

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

	public String getGwCnctId() {
		return gwCnctId;
	}

	public void setGwCnctId(String gwCnctId) {
		this.gwCnctId = gwCnctId;
	}

	public List<LogRow> getLogRows() {
		return logRows;
	}

	public void setLogRows(List<LogRow> logRows) {
		this.logRows = logRows;
	}

	public String getUnitSvcCd() {
		return unitSvcCd;
	}

	public void setUnitSvcCd(String unitSvcCd) {
		this.unitSvcCd = unitSvcCd;
	}


}
