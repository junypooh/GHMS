package com.kt.giga.home.openapi.ghms.kafka.smcp.gw.ca.domn.row;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * 컨트롤데이터 클래스
 * @since	: 2014. 11. 2.
 * @author	: CBJ
 * <PRE>
 * Revision History
 * ----------------------------------------------------
 * 2014. 11. 2. CBJ: 최초작성
 * ----------------------------------------------------
 * </PRE>
 */
public class ContlData implements Serializable, Cloneable
{
	/** 직렬화아이디 */
	private static final long serialVersionUID = -7728016829426168134L;

	/** 센싱태그코드 */
	private String snsnTagCd;
	/** 제어값 */
	private Double contlVal;

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

	public String getSnsnTagCd() {
		return snsnTagCd;
	}

	public void setSnsnTagCd(String snsnTagCd) {
		this.snsnTagCd = snsnTagCd;
	}

	public Double getContlVal() {
		return contlVal;
	}

	public void setContlVal(Double contlVal) {
		this.contlVal = contlVal;
	}


}
