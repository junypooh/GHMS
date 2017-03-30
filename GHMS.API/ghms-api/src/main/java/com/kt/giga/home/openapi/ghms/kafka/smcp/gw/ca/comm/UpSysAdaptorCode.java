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
package com.kt.giga.home.openapi.ghms.kafka.smcp.gw.ca.comm;

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

public class UpSysAdaptorCode
{
	/**
	 * <PRE>
	 *  ClassName : UpSysType GwCode
	 * </PRE>
	 * @version : 1.0
	 * @date    : 2014. 3. 16. 오후 7:21:52
	 * @author  : CBJ
	 * @brief   : 상위시스템유형코드(1112)
	 */
	public static enum UpSysType
	{
		/** 3M */
		M3("0001"),
		/** KMI */
		KMI("0002"),
		/** UbiCahn */
		UBICAHN("0003"),
		/** Kepco(모자) */
		KEPCO("0004"),
		/** Kepco(고압) */
		KEPCO_HIGH("0005");

		private final String value;

        private UpSysType(String value) {
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
		private static final HashMap<String, UpSysType> map = new HashMap<String, UpSysType>();
		static {
			for(UpSysType it : values()) {
				map.put(it.toString(), it);
			}
		}

		// value에 해당되는 enum을 반환
		public static UpSysType fromString(String value) {
			return map.get(value);
		}
	}
}
