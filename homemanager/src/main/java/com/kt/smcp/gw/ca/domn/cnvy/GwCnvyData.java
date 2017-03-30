package com.kt.smcp.gw.ca.domn.cnvy;

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
public class GwCnvyData extends CorePrcssData implements Serializable, Cloneable
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
	private List<CnvyRow> cnvyRows = new ArrayList<CnvyRow>();

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
		if(cnvyRows == null || cnvyRows.size() == 0)
		{
			return true;
		}
		for(CnvyRow cnvyRow : cnvyRows)
		{
			//빈객체가 아니면
			if(!cnvyRow.isEmptyData())
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

	public List<CnvyRow> getCnvyRows() {
		return cnvyRows;
	}

	public void setCnvyRows(List<CnvyRow> cnvyRows) {
		this.cnvyRows = cnvyRows;
	}

	public String getUnitSvcCd() {
		return unitSvcCd;
	}

	public void setUnitSvcCd(String unitSvcCd) {
		this.unitSvcCd = unitSvcCd;
	}


}
