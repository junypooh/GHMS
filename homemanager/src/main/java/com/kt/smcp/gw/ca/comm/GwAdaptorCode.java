/**
 * <PRE>
 *  Project : GWCommAgent
 *  Package : com.kt.smcp.gw.ca.comm
 * </PRE>
 * @file   : AdaptorCode.java
 * @date   : 2014. 7. 23. 오후 3:54:15
 * @author : CBJ
 * @brief  :
 *  변경이력    :
 *        이름     : 일자          : 근거자료   : 변경내용
 *       ------------------------------------
 *        CBJ  : 2014. 7. 23.       :            : 신규 개발.
 */
package com.kt.smcp.gw.ca.comm;

import java.util.HashMap;

/**
 * <PRE>
 *  ClassName : AdaptorCode
 * </PRE>
 * @version : 1.0
 * @date    : 2014. 7. 23. 오후 3:54:15
 * @author  : CBJ
 * @brief   :
 */

public class GwAdaptorCode
{
	/**
	 * <PRE>
	 *  ClassName : GwCode
	 * </PRE>
	 * @version : 1.0
	 * @date    : 2014. 1. 28. 오후 8:51:17
	 * @author  : byw
	 * @brief   : 게이트웨이유형: 1101
	 */
	public static enum GwType
	{
		/** energyFM */
		ENERGYFM("0001"),
		/** KMAP */
		KMAP("0002"),
		/** UbiCahn */
		UBICAHN("0003"),
		/** 표준시스템 */
		STD_SYS("0004"),
		/** 홈카메라 */
		HOM_CAM("1001"),
		/** MPU10 */
		MPU10("2001"),
		/** BOTEM */
		BOTEM("2002"),
		/** CONPO_CTE01D */
		CONPO_CTE01D("2003"),
		/** COWAY */
		COWAY("2004"),
		/** GW SAMPLE_ADAPTOR */
		SAMPLE_ADAPTOR("9999");

		private final String value;

        private GwType(String value) {
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
		private static final HashMap<String, GwType> map = new HashMap<String, GwType>();
		static {
			for(GwType it : values()) {
				map.put(it.toString(), it);
			}
		}

		// value에 해당되는 enum을 반환
		public static GwType fromString(String value) {
			return map.get(value);
		}

	}
}
