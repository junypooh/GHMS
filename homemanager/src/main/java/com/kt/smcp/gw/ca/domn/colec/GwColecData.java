package com.kt.smcp.gw.ca.domn.colec;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.kt.smcp.gw.ca.domn.CorePrcssData;
import com.kt.smcp.gw.ca.domn.row.Row;

/**
 * 게이트웨이수집데이터 클래스
 * @since	: 2014. 11. 2.
 * @author	: CBJ
 * <PRE>
 * Revision History
 * ----------------------------------------------------
 * 2014. 11. 2. CBJ: 최초작성
 * ----------------------------------------------------
 * </PRE>
 */
public class GwColecData extends CorePrcssData implements Serializable, Cloneable
{
	/** 직렬화아이디 */
	private static final long serialVersionUID = 7466177720460448732L;

	/** 서비스대상일련번호 */
	private Long svcTgtSeq;
	/** 게이트웨이연결아이디 */
	private String gwCnctId;
	/** 단위서비스코드 */
	private String unitSvcCd;

	/** 수집행 리스트 */
	private List<Row> rows = new ArrayList<Row>();

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

	public String getUnitSvcCd() {
		return unitSvcCd;
	}

	public void setUnitSvcCd(String unitSvcCd) {
		this.unitSvcCd = unitSvcCd;
	}

	public List<Row> getRows() {
		return rows;
	}

	public void setRows(List<Row> rows) {
		this.rows = rows;
	}

	@Override
	public boolean isEmptyData()
	{
		if(rows.size() == 0)
		{
			return true;
		}
		return false;
	}

}
