package com.kt.giga.home.openapi.ghms.kafka.smcp.gw.ca.domn.net;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.kt.giga.home.openapi.ghms.kafka.smcp.gw.ca.domn.CorePrcssData;

@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class CommChSessnRetvReslt extends CorePrcssData implements Serializable, Cloneable
{
	/** 직렬화아이디 */
	private static final long serialVersionUID = -7618287047608379491L;
	/** 접속서버아이디  */
	protected List<CommChSessn> commChSessns = new ArrayList<CommChSessn>();
	/** 검색결과  */
	protected int totalCnt   = 0;


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
		if(commChSessns.size() > 0)
		{
			return false;
		}
		return true;
	}

	public CommChSessnRetvReslt()
	{
	}

	public List<CommChSessn> getCommChSessns() {
		return commChSessns;
	}

	public void setCommChSessns(List<CommChSessn> commChSessns) {
		this.commChSessns = commChSessns;
	}

	public int getTotalCnt() {
		return totalCnt;
	}

	public void setTotalCnt(int totalCnt) {
		this.totalCnt = totalCnt;
	}


}
