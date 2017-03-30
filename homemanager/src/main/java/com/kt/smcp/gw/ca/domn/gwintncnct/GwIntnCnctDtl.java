package com.kt.smcp.gw.ca.domn.gwintncnct;

import java.io.Serializable;
import java.util.HashMap;

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
public class GwIntnCnctDtl implements Serializable, Cloneable
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

	public static enum GwIntnCnctDtlAtribNm
	{
		/** 플랫폼버전 */
		PLTFRM_VER("PLTFRM_VER"),
		/** 어댑터메인버전 */
		ADAP_MAIN_VER("ADAP_MAIN_VER"),
		/** 어댑터서브버전 */
		ADAP_SUB_VER("ADAP_SUB_VER"),
		;

		private final String value;

        private GwIntnCnctDtlAtribNm(String value) {
                this.value = value;
        }

        public boolean equals(String obj)
    	{
        	return value.equals(obj);
    	}

		/**
		 * @return the value
		 */
		public String getValue() {
			return value;
		}

		/* (non-Javadoc)
		 * @see java.lang.Enum#toString()
		 */
		@Override
		public String toString() {
			return value;
		}


		// value에 해당되는 enum을 반환하기 위한 Map 생성 및 설정
		private static final HashMap<String, GwIntnCnctDtlAtribNm> map = new HashMap<String, GwIntnCnctDtlAtribNm>();
		static {
			for(GwIntnCnctDtlAtribNm it : values()) {
				map.put(it.toString(), it);
			}
		}

		// value에 해당되는 enum을 반환
		public static GwIntnCnctDtlAtribNm fromString(String value) {
			return map.get(value);
		}

	}

}