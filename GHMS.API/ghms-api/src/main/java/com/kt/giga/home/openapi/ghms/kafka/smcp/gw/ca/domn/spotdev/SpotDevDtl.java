package com.kt.giga.home.openapi.ghms.kafka.smcp.gw.ca.domn.spotdev;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class SpotDevDtl implements Serializable, Cloneable
{
	/** 직렬화이이디 */
	private static final long serialVersionUID = -6653624078103050159L;

	/** 애트리뷰트명 */
	private String atribNm;
	/** 애트리뷰트값 */
	private String atribVal;

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

	public SpotDevDtl()
	{

	}

	public SpotDevDtl(SpotDevDtl spotDevDtl)
	{
		this.atribNm = spotDevDtl.getAtribNm();
		this.atribVal = spotDevDtl.getAtribVal();
	}

	public String getAtribNm() {
		return atribNm;
	}

	public void setAtribNm(String atribNm) {
		this.atribNm = atribNm;
	}

	public String getAtribVal() {
		return atribVal;
	}

	public void setAtribVal(String atribVal) {
		this.atribVal = atribVal;
	}


}
