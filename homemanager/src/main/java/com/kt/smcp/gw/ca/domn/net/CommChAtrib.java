package com.kt.smcp.gw.ca.domn.net;

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
public class CommChAtrib implements Serializable, Cloneable
{
	/** 직렬화아이디 */
	private static final long serialVersionUID = -4055262810389540546L;

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

	public static enum CommChAtribNm
	{
		/** SSL 여부 */
		SSL_YN("SSL_YN"),
		/** 키저장파일경로 */
		KEY_STORE_FILE_PATH("KEY_STORE_FILE_PATH"),
		/** 트러스트저장파일경로 */
		TRUST_STORE_FILE_PATH("TRUST_STORE_FILE_PATH"),
		/** 키저장비밀번호 */
		KEY_STORE_PASSWORD("KEY_STORE_PASSWORD"),
		/** 트러스트저장비밀번호 */
		TRUST_STORE_PASSWORD("TRUST_STORE_PASSWORD"),
		/** SSL컨택스트비밀번호 */
		SSL_CONTEXT_PASSWORD("SSL_CONTEXT_PASSWORD"),
		/** ASYNC 여부 */
		ASYNC_YN("ASYNC_YN"),
		/** 세션중복처리유형 */
		SESSN_DUPL_TRT_TYPE("SESSN_DUPL_TRT_TYPE"),
		/** 토픽 */
		TOPIC("TOPIC"),
		/** 토픽접두사 */
		TOPIC_PREFIX("TOPIC_PREFIX"),
		/** 토픽접미사 */
		TOPIC_SUFFIX("TOPIC_SUFFIX"),
		/** 토픽그룹아이디 */
		TOPIC_GROUP_ID("TOPIC_GROUP_ID"),
		;

		private final String value;

        private CommChAtribNm(String value) {
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
		private static final HashMap<String, CommChAtribNm> map = new HashMap<String, CommChAtribNm>();
		static {
			for(CommChAtribNm it : values()) {
				map.put(it.toString(), it);
			}
		}

		// value에 해당되는 enum을 반환
		public static CommChAtribNm fromString(String value) {
			return map.get(value);
		}
	}

	public static enum CommChAtribVal
	{
		/** 이전세션종료 */
		ALL_SESSN_CLO("ALL_SESSN_CLO"),
		/** 신규세션종료 */
		NEW_SESSN_CLO("NEW_SESSN_CLO"),
		/** 이전세션종료 */
		PREV_SESSN_CLO("PREV_SESSN_CLO"),
		;

		private final String value;

        private CommChAtribVal(String value) {
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
		private static final HashMap<String, CommChAtribVal> map = new HashMap<String, CommChAtribVal>();
		static {
			for(CommChAtribVal it : values()) {
				map.put(it.toString(), it);
			}
		}

		// value에 해당되는 enum을 반환
		public static CommChAtribVal fromString(String value) {
			return map.get(value);
		}

	}
}