package com.kt.giga.home.openapi.ghms.kafka.smcp.gw.ca.domn.row;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * 스케줄데이터 클래스
 * @since	: 2014. 11. 2.
 * @author	: CBJ
 * <PRE>
 * Revision History
 * ----------------------------------------------------
 * 2014. 11. 2. CBJ: 최초작성
 * ----------------------------------------------------
 * </PRE>
 */
public class SclgData implements Serializable, Cloneable
{
	/** 직렬화아이디 */
	private static final long serialVersionUID = -2706104727227233106L;

	/** 스케줄설정요일코드 */
	private String dowCd;
	/** 스케줄설정요일코드 */
	private List<SclgTimeData> sclgTimeDatas = new ArrayList<SclgTimeData>();

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

	public String getDowCd() {
		return dowCd;
	}

	public void setDowCd(String dowCd) {
		this.dowCd = dowCd;
	}

	public List<SclgTimeData> getSclgTimeDatas() {
		return sclgTimeDatas;
	}

	public void setSclgTimeDatas(List<SclgTimeData> sclgTimeDatas) {
		this.sclgTimeDatas = sclgTimeDatas;
	}


}
