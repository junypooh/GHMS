package com.kt.giga.home.openapi.ghms.kafka.smcp.gw.ca.domn.upifsys;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 *
 * @since	: 2014. 11. 2.
 * @author	: CBJ
 * <PRE>
 * Revision History
 * ----------------------------------------------------
 * 2014. 11. 2. CBJ: 최초작성
 * ----------------------------------------------------
 * </PRE>
 */
public class UpIfCommChAtribDtl implements Serializable, Cloneable
{
	/** 속성명 */
	private String atribNm;
	/** 속성값 */
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
	/**
	 * @return the atribNm
	 */
	public String getAtribNm() {
		return atribNm;
	}
	/**
	 * @param atribNm the atribNm to set
	 */
	public void setAtribNm(String atribNm) {
		this.atribNm = atribNm;
	}
	/**
	 * @return the atribVal
	 */
	public String getAtribVal() {
		return atribVal;
	}
	/**
	 * @param atribVal the atribVal to set
	 */
	public void setAtribVal(String atribVal) {
		this.atribVal = atribVal;
	}
}