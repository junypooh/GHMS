/**
 * <PRE>
 *  Project : GWCommAgent
 *  Package : com.kt.smcp.gw.ca.core
 * </PRE>
 * @file   : GwCode.java
 * @date   : 2014. 1. 27. 오후 9:45:21
 * @author : 추병조
 * @brief  :
 *  변경이력    :
 *        이름     : 일자          : 근거자료   : 변경내용
 *       ------------------------------------
 *        추병조  : 2014. 1. 27.       :            : 신규 개발.
 */
package com.kt.giga.home.openapi.vo;

import java.util.HashMap;

/**
 * <PRE>
 *  ClassName : GwCode
 * </PRE>
 * @version : 1.0
 * @date    : 2014. 1. 27. 오후 9:45:21
 * @author  : 추병조
 * @brief   :
 */

public class GwCode
{
	/**
	 * <PRE>
	 *  ClassName : GwRcvRspnCmd GwCode
	 * </PRE>
	 * @version : 1.0
	 * @date    : 2014. 2. 19. 오전 10:25:07
	 * @author  : 추병조
	 * @brief   : GW 수신응대타입(TODO 내부코드명 표준명에 맞출것인지를 고려)
	 */
	public static enum RcvRspnType
	{
		/** ack */
		ACK("01"),
		/** non ack */
		NON_ACK("02")
		;

		private final String value;

        private RcvRspnType(String value) {
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

		@Override
		public String toString() {
			return value;
		}
	}

	/**
	 * <PRE>
	 *  ClassName : GwRcvRspnCmd GwCode
	 * </PRE>
	 * @version : 1.0
	 * @date    : 2014. 2. 19. 오전 10:25:07
	 * @author  : 추병조
	 * @brief   : GW 수신응대명령어(TODO 내부코드명 표준명에 맞출것인지를 고려)
	 */
	public static enum RcvRspnCmd
	{
		/** non action */
		NON_ACTION("01"),
		/** close */
		CLOSE("02")
		;

		private final String value;

        private RcvRspnCmd(String value) {
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

		@Override
		public String toString() {
			return value;
		}
	}

	/**
	 * <PRE>
	 *  ClassName SessnActonType GwCode
	 * </PRE>
	 * @brief: 세션액션타입
	 * @version 1.0
	 * @date 2014. 10. 22. 오전 9:12:07
	 * @author CBJ
	 */
	public static enum SessnActonType
	{
		/** non ack */
		NON_ACK("01"),
		/** insert */
		INSERT("02"),
		/** update */
		UPDATE("03"),
		/** remove */
		REMOVE("04"),
		;
		private final String value;

        private SessnActonType(String value) {
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

		@Override
		public String toString() {
			return value;
		}
	}

	/**
	 * <PRE>
	 *  ClassName : GwCode
	 * </PRE>
	 * @version : 1.0
	 * @date    : 2014. 2. 05. 오전 10:49:56
	 * @author  : byw
	 * @brief   : GwCacheType
	 */
	public static enum GwCacheType {

		/** 현장장치기본, 현장장치상세 */
		SPOT_DEV_BAS("0001"),
		/** 현장장치연계데이터관계 */
		SPOT_DEV_LNK_DATA_REL("0002"),
		/** 게이트웨이센싱태그매핑관계 */
		GW_SNSN_TAG_MAPPG_REL("0003"),
		/** 게이트웨이내부연결기본, 게이트웨이내부연결상세, 게이트웨이통신채널상세 */
		GW_INTN_CNCT_BAS("0004"),
		/** 센싱태그코드기본 */
		SNSN_TAG_CD_BAS("0005"),
		/** 상위인터페이스시스템기본 */
		UP_IF_SYS_BAS("0006"),
		/** 커뮤니케이션대리인기본, 커뮤니케이션대리인설정상세 */
		COMMN_AGNT_BAS("0007");

		private final String value;

        private GwCacheType(String value) {
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

		@Override
		public String toString() {
			return value;
		}
	}

	/**
	 * <PRE>
	 *  ClassName : GwCode
	 * </PRE>
	 * @version : 1.0
	 * @date    : 2014. 1. 16. 오후 8:49:56
	 * @author  : 추병조
	 * @brief   : 정렬유형
	 */
	public static enum OrderType
	{
		/** 내림차순 정렬 */
		ORDER_DESC("DESC");

		private final String value;

        private OrderType(String value) {
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

		@Override
		public String toString() {
			return value;
		}

		// value에 해당되는 enum을 반환하기 위한 Map 생성 및 설정
		private static final HashMap<String, OrderType> map = new HashMap<String, OrderType>();
		static {
			for(OrderType it : values()) {
				map.put(it.toString(), it);
			}
		}
		// value에 해당되는 enum을 반환
		public static OrderType fromString(String value) {
			return map.get(value);
		}
	}


	/**
	 * <PRE>
	 *  ClassName : GwCode
	 * </PRE>
	 * @version : 1.0
	 * @date    : 2014. 1. 16. 오후 8:49:56
	 * @author  : 추병조
	 * @brief   : 인터페이스 유형 : 1001
	 */
	public static enum IfType
	{
		/** 클라이언튼 */
		CLIENT("0001"),
		/** 서버 */
		SERVER("0002"),
		/** 모두 */
		ALL("0003"),
		/** 지원안함 */
		NO_SUPPORT("0004");
		private final String value;

        private IfType(String value) {
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

		@Override
		public String toString() {
			return value;
		}
		// value에 해당되는 enum을 반환하기 위한 Map 생성 및 설정
		private static final HashMap<String, IfType> map = new HashMap<String, IfType>();
		static {
			for(IfType it : values()) {
				map.put(it.toString(), it);
			}
		}
		// value에 해당되는 enum을 반환
		public static IfType fromString(String value) {
			return map.get(value);
		}
	}



	/**
	 * <PRE>
	 *  ClassName : GwCode
	 * </PRE>
	 * @version : 1.0
	 * @date    : 2014. 1. 28. 오후 8:51:17
	 * @author  : byw
	 * @brief   : 사용여부 enum
	 */
	public static enum UseYn
	{

		/** 사용 */
		YES("Y"),
		/** 미사용 */
		NO("N");

		private final String value;

        private UseYn(String value) {
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
		private static final HashMap<String, UseYn> map = new HashMap<String, UseYn>();
		static {
			for(UseYn it : values()) {
				map.put(it.toString(), it);
			}
		}

		// value에 해당되는 enum을 반환
		public static UseYn fromString(String value) {
			return map.get(value);
		}

	}


	/**
	 * <PRE>
	 *  ClassName : GwCode
	 * </PRE>
	 * @version : 1.0
	 * @date    : 2014. 1. 28. 오후 8:51:17
	 * @author  : byw
	 * @brief   : 게이트웨이HB패킷타입 enum
	 */
	public static enum GwHbPacktType
	{

		/** 게이트웨이 HB 요청 패킷 타입 */
		GW_HB_REQUEST("00005001"),
		/** 게이트웨이 HB 응답 패킷 타입 */
		GW_HB_RESPONSE("00005002");

		private final String value;

        private GwHbPacktType(String value) {
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
		private static final HashMap<String, GwHbPacktType> map = new HashMap<String, GwHbPacktType>();
		static {
			for(GwHbPacktType it : values()) {
				map.put(it.toString(), it);
			}
		}

		// value에 해당되는 enum을 반환
		public static GwHbPacktType fromString(String value) {
			return map.get(value);
		}

	}



	/**
	 * <PRE>
	 *  ClassName : GwCode
	 * </PRE>
	 * @version : 1.0
	 * @date    : 2014. 1. 28. 오후 8:51:17
	 * @author  : byw
	 * @brief   : EFM 제어 GW 등록 enum
	 */
	public static enum EfmGwReqPacktType
	{

		/** EFM 제어 GW 등록 요청 패킷 타입 */
		EFM_GW_REG_REQUEST("00005003"),
		/** EFM 제어 GW 등록 응답 패킷 타입 */
		EFM_GW_REG_RESPONSE("00005004");

		private final String value;

        private EfmGwReqPacktType(String value) {
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
		private static final HashMap<String, EfmGwReqPacktType> map = new HashMap<String, EfmGwReqPacktType>();
		static {
			for(EfmGwReqPacktType it : values()) {
				map.put(it.toString(), it);
			}
		}

		// value에 해당되는 enum을 반환
		public static EfmGwReqPacktType fromString(String value) {
			return map.get(value);
		}

	}




	/**
	 * <PRE>
	 *  ClassName : GwCode
	 * </PRE>
	 * @version : 1.0
	 * @date    : 2014. 1. 28. 오후 8:51:17
	 * @author  : byw
	 * @brief   : 현장장치HB패킷타입 enum
	 */
	public static enum SpotDevHbPacktType
	{

		/** 현장장치 HB 요청 패킷 타입 */
		SPOT_HB_REQUEST("00005011"),
		/** 현장장치 HB 응답 패킷 타입 */
		SPOT_HB_RESPONSE("00005012");

		private final String value;

        private SpotDevHbPacktType(String value) {
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
		private static final HashMap<String, SpotDevHbPacktType> map = new HashMap<String, SpotDevHbPacktType>();
		static {
			for(SpotDevHbPacktType it : values()) {
				map.put(it.toString(), it);
			}
		}

		// value에 해당되는 enum을 반환
		public static SpotDevHbPacktType fromString(String value) {
			return map.get(value);
		}

	}





	/**
	 * <PRE>
	 *  ClassName : GwCode
	 * </PRE>
	 * @version : 1.0
	 * @date    : 2014. 1. 28. 오후 8:51:17
	 * @author  : byw
	 * @brief   : 하위시스템전송데이터내역패킷타입 enum
	 */
	public static enum LowSysTrmTxnPacktType
	{

		/** 요청 패킷 타입 */
		LOW_SYS_TRM_MGT_REQUEST("00003101"),
		/** 응답 패킷 타입 */
		LOW_SYS_TRM_MGT_RESPONSE("00003102");

		private final String value;

        private LowSysTrmTxnPacktType(String value) {
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
		private static final HashMap<String, LowSysTrmTxnPacktType> map = new HashMap<String, LowSysTrmTxnPacktType>();
		static {
			for(LowSysTrmTxnPacktType it : values()) {
				map.put(it.toString(), it);
			}
		}

		// value에 해당되는 enum을 반환
		public static LowSysTrmTxnPacktType fromString(String value) {
			return map.get(value);
		}

	}











	/**
	 * <PRE>
	 *  ClassName : GwCode
	 * </PRE>
	 * @version : 1.0
	 * @date    : 2014. 1. 28. 오후 8:51:17
	 * @author  : byw
	 * @brief   : 제어인터페이스유형: 1002
	 */
	public static enum ContlIfType
	{
		/** 동기 */
		SYNC("0001"),
		/** 비동기 */
		ASYNC("0002"),
		/** 모두 */
		ALL("0003"),
		/** 지원안함 */
		NO_SUPPORT("0004");

		private final String value;

        private ContlIfType(String value) {
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
		private static final HashMap<String, ContlIfType> map = new HashMap<String, ContlIfType>();
		static {
			for(ContlIfType it : values()) {
				map.put(it.toString(), it);
			}
		}

		// value에 해당되는 enum을 반환
		public static ContlIfType fromString(String value) {
			return map.get(value);
		}

	}



	/**
	 * <PRE>
	 *  ClassName : GwCode
	 * </PRE>
	 * @version : 1.0
	 * @date    : 2014. 1. 28. 오후 8:51:17
	 * @author  : byw
	 * @brief   : 데이터프로토콜: 1003
	 */
	public static enum DataProtType
	{
		/** BEMS_MODBUS_ACCURA2300 */
		BEMS_MODBUS_ACCURA2300("00001401"),
		/** BEMS_MODBUS_ACCURA2350_3P */
		BEMS_MODBUS_ACCURA2350_3P("00001402"),
		/** BEMS_MODBUS_ACCURA2350_1P */
		BEMS_MODBUS_ACCURA2350_1P("00001403"),
		/** BEMS_MODBUS_ACCURA3500 */
		BEMS_MODBUS_ACCURA3500("00001404"),
		/** BEMS_MODBUS_JC2300A */
		BEMS_MODBUS_JC2300A("00001405"),
		/** BEMS_MODBUS_DIOM16 */
		BEMS_MODBUS_DIOM16("00001406"),
		/** BEMS_ZIGBEE_THMOTE */
		BEMS_ZIGBEE_THMOTE("00001407"),
		/** BEMS_MODBUS_WCU3000 */
		BEMS_MODBUS_WCU3000("00001408"),
		/** BEMS_BACNET_DEVICE */
		BEMS_BACNET_DEVICE("00001409"),
		/** BEMS_ZIGBEE_ECMS */
		BEMS_ZIGBEE_ECMS("00001410"),
		/** KMI */
		KMI("00010001"),
		/** GCA 센싱 */
		GCA_SENSING("00020001"),
		/** 3M 위치 */
		M3S_LOCATION("00030001"),
		/** 3M 센싱 */
		M3S_SENSING("00030002");

		private final String value;

        private DataProtType(String value) {
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
		private static final HashMap<String, DataProtType> map = new HashMap<String, DataProtType>();
		static {
			for(DataProtType it : values()) {
				map.put(it.toString(), it);
			}
		}

		// value에 해당되는 enum을 반환
		public static DataProtType fromString(String value) {
			return map.get(value);
		}

	}





	/**
	 * <PRE>
	 *  ClassName : GwCode
	 * </PRE>
	 * @version : 1.0
	 * @date    : 2014. 1. 28. 오후 8:51:17
	 * @author  : byw
	 * @brief   : 연결유형: 1004
	 */
	public static enum CnctType
	{
		/** TCP(Stream) */
		TCP_STREAM("0001"),
		/** TCP(Dgram) */
		TCP_CONNECTLESS("0002"),
		/** HTTP */
		HTTP("0003"),
		/** HTTPS */
		HTTPS("0004"),
		/** RS232 */
		RS232("0005"),
		/** DB_POSTGRE_SQL */
		DB_POSTGRE_SQL("0011"),
		/** DB_ORACLE */
		DB_ORACLE("0012"),
		/** DB_MS_SQL */
		DB_MS_SQL("0013"),
		/** DB_MY_SQL */
		DB_MY_SQL("0014"),
		/** DB_ALTIBASE */
		DB_ALTIBASE("0015"),
		;

		private final String value;

        private CnctType(String value) {
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
		private static final HashMap<String, CnctType> map = new HashMap<String, CnctType>();
		static {
			for(CnctType it : values()) {
				map.put(it.toString(), it);
			}
		}

		// value에 해당되는 enum을 반환
		public static CnctType fromString(String value) {
			return map.get(value);
		}

	}





	/**
	 * <PRE>
	 *  ClassName : GwCode
	 * </PRE>
	 * @version : 1.0
	 * @date    : 2014. 1. 28. 오후 8:51:17
	 * @author  : byw
	 * @brief   : 인증방식: 1005
	 */
	public static enum AthnForml
	{
		/** API Key */
		API_KEY("0001"),
		/** OAuth */
		OAUTH("0002"),
		/** IP white list */
		IP_WHITE_LIST("0003"),
		/** ID/Password */
		ID_PASSWORD("0004");

		private final String value;

        private AthnForml(String value) {
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
		private static final HashMap<String, AthnForml> map = new HashMap<String, AthnForml>();
		static {
			for(AthnForml it : values()) {
				map.put(it.toString(), it);
			}
		}

		// value에 해당되는 enum을 반환
		public static AthnForml fromString(String value) {
			return map.get(value);
		}

	}




	/**
	 * <PRE>
	 *  ClassName : GwCode
	 * </PRE>
	 * @version : 1.0
	 * @date    : 2014. 1. 28. 오후 8:51:17
	 * @author  : byw
	 * @brief   : 암호화유형: 1006
	 */
	public static enum EcodType
	{
		/** AES 128 */
		AES_128("0011"),
		/** AES 256 */
		AES_256("0012"),
		/** DES 128 */
		DES_64("0021"),
		/** DES 256 */
		DES_EDE_192("0022"),
		/** SEED 128 */
		SEED_128("0031"),
		/** ARIA 128 */
		ARIA_128("0041"),
		/** ARIA 256 */
		ARIA_192("0042"),
		/** ARIA 256 */
		ARIA_256("0043"),
		;

		private final String value;

        private EcodType(String value) {
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
		private static final HashMap<String, EcodType> map = new HashMap<String, EcodType>();
		static {
			for(EcodType it : values()) {
				map.put(it.toString(), it);
			}
		}

		// value에 해당되는 enum을 반환
		public static EcodType fromString(String value) {
			return map.get(value);
		}

	}




	/**
	 * <PRE>
	 *  ClassName : GwCode
	 * </PRE>
	 * @version : 1.0
	 * @date    : 2014. 1. 28. 오후 8:51:17
	 * @author  : byw
	 * @brief   : 언어유형: 1007
	 */
	public static enum LangType
	{
		/** Korean */
		KORL("KOR"),
		/** English */
		ENGL("ENG");

		private final String value;

        private LangType(String value) {
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
		private static final HashMap<String, LangType> map = new HashMap<String, LangType>();
		static {
			for(LangType it : values()) {
				map.put(it.toString(), it);
			}
		}

		// value에 해당되는 enum을 반환
		public static LangType fromString(String value) {
			return map.get(value);
		}

	}


	/**
	 * <PRE>
	 *  ClassName : GwCode
	 * </PRE>
	 * @version : 1.0
	 * @date    : 2014. 1. 28. 오후 8:51:17
	 * @author  : byw
	 * @brief   : 통신채널유형: 1009
	 */
	public static enum CommChType
	{
		/** 통합 */
		INTEGRATION("0010"),
		/** GW 쿼리 */
		GW_QUERY("0020"),
		/** GW 쿼리 리포트 */
		GW_QUERY_REPORT("0021"),
		/** 로그인 */
		LOGIN("0030"),
		/** 초기화 */
		INIT("0040"),
		/** 수집 통합*/
		COLLECTION_ITG("0050"),
		/** 수집 센싱 */
		COLLECTION_SNSN("0051"),
		/** 수집 위치 */
		COLLECTION_LO("0052"),
		/** 수집 상태 */
		COLLECTION_STTUS("0053"),
		/** 수집 이진 */
		COLLECTION_BIN("0054"),
		/** 수집 이벤트 */
		COLLECTION_EV("0055"),
		/** 제어 */
		CONTROL("0060"),
		/** 제어 */
		CONTROL_REPORT("0061"),
		/** 체크 */
		CHECK("0070"),
		/** 외부 시스템 쿼리 */
		EXTR_SYS_QUERY("0080");

		private final String value;

        private CommChType(String value) {
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
		private static final HashMap<String, CommChType> map = new HashMap<String, CommChType>();
		static {
			for(CommChType it : values()) {
				map.put(it.toString(), it);
			}
		}

		// value에 해당되는 enum을 반환
		public static CommChType fromString(String value) {
			return map.get(value);
		}

	}

	/**
	 * <PRE>
	 *  ClassName : GwCode
	 * </PRE>
	 * @version : 1.0
	 * @date    : 2014. 1. 16. 오후 8:49:56
	 * @author  : 추병조
	 * @brief   : 프로토콜유형 1010
	 */
	public static enum ProtocolType
	{
		//변경될 경우 MinaMessageDecoder 꼭 수정 해줄 것
		/** Type Length Value: length type byte */
		TLV_BYTE("0001"),
		/** Type Length Value: length type short */
		TLV_SHORT("0002"),
		/** Type Length Value: length type int */
		TLV_INT("0003"),
		/** Type Length Value: length type long */
		TLV_LONG("0004"),
		/** Type Length Value: length type string */
		TLV_STRING("0005"),

		/** ETX: etx type byte */
		ETX_BYTE("0011"),
		/** ETX: etx type short */
		ETX_SHORT("0012"),
		/** ETX: etx type int */
		ETX_INT("0013"),
		/** ETX: etx type long */
		ETX_LONG("0014"),
		/** ETX: etx type string */
		ETX_STRING("0015"),

		/** Content Length */
		CONTENT_LENGTH("0021"),
		/** Adaptor Custom */
		ADAPTOR_CUSTOM("0031");

		private final String value;

        private ProtocolType(String value) {
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
		private static final HashMap<String, ProtocolType> map = new HashMap<String, ProtocolType>();
		static {
			for(ProtocolType it : values()) {
				map.put(it.toString(), it);
			}
		}

		// value에 해당되는 enum을 반환
		public static ProtocolType fromString(String value) {
			return map.get(value);
		}
	}


	/**
	 * <PRE>
	 *  ClassName : GwCode
	 * </PRE>
	 * @version : 1.0
	 * @date    : 2014. 1. 28. 오후 8:51:17
	 * @author  : byw
	 * @brief   : 벌크처리 코드 1011
	 */
	public static enum CommDataTrtModeType
	{
		/** 동기처리 모드 */
		SYSC("0001"),
		/** 비동기처리 메모리큐 벌크 모드 */
		ASYSC_MEMORY_QUEUE_BULK("0002"),
		/** 비동기처리 메모리큐 단건 모드 */
		ASYSC_MEMORY_QUEUE_ONE("0003"),
		/** 비동기처리 ActiveMQ 벌크 모드 */
		ASYSC_ACTIVE_MQ_BULK("0011"),
		/** 비동기처리 ActiveMQ 단건 모드 */
		ASYSC_ACTIVE_MQ_ONE("0012");

		private final String value;

        private CommDataTrtModeType(String value) {
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
		private static final HashMap<String, CommDataTrtModeType> map = new HashMap<String, CommDataTrtModeType>();
		static {
			for(CommDataTrtModeType it : values()) {
				map.put(it.toString(), it);
			}
		}

		// value에 해당되는 enum을 반환
		public static CommDataTrtModeType fromString(String value) {
			return map.get(value);
		}
	}

	/**
	 * <PRE>
	 *  ClassName : GwCode
	 * </PRE>
	 * @version : 1.0
	 * @date    : 2014. 1. 16. 오후 8:51:17
	 * @author  : 추병조
	 * @brief   : 인코딩타입 enum: 1012
	 */
	public static enum EncodingType
	{
		/** encoding type: Big endian */
		BIG_ENDIAN("01"),
		/** encoding type: little endian */
		LITTLE_ENDIAN("02");

		private final String value;

        private EncodingType(String value) {
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
		private static final HashMap<String, EncodingType> map = new HashMap<String, EncodingType>();
		static {
			for(EncodingType it : values()) {
				map.put(it.toString(), it);
			}
		}

		// value에 해당되는 enum을 반환
		public static EncodingType fromString(String value) {
			return map.get(value);
		}

	}

	/**
	 * <PRE>
	 *  ClassName : DataLayrType GwCode
	 * </PRE>
	 * @version : 1.0
	 * @date    : 2014. 2. 19. 오후 3:03:04
	 * @author  : 추병조
	 * @brief   : 데이터 레이어 코드 1013
	 */
	public static enum DataLayrType
	{
		/** 관계 데이터베이스 */
		REL_DB("01"),
		/** 파일 시스템 */
		FILE_SYS("02");

		private final String value;

        private DataLayrType(String value) {
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
		private static final HashMap<String, DataLayrType> map = new HashMap<String, DataLayrType>();
		static {
			for(DataLayrType it : values()) {
				map.put(it.toString(), it);
			}
		}

		// value에 해당되는 enum을 반환
		public static DataLayrType fromString(String value) {
			return map.get(value);
		}
	}

	/**
	 * <PRE>
	 *  ClassName : CommChType GwCode
	 * </PRE>
	 * @version : 1.0
	 * @date    : 2014. 3. 11. 오후 4:58:28
	 * @author  : CBJ
	 * @brief   : 통신채널처리유형 enum: 1014
	 */
	public static enum CommChTrtType
	{
		/** encoding type: Big endian */
		IN_BOUND("10"),
		/** encoding type: little endian */
		OUT_BOUND("20"),
		/** encoding type: little endian */
		IN_OUT_BOUND("30");

		private final String value;

        private CommChTrtType(String value) {
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
		private static final HashMap<String, CommChTrtType> map = new HashMap<String, CommChTrtType>();
		static {
			for(CommChTrtType it : values()) {
				map.put(it.toString(), it);
			}
		}

		// value에 해당되는 enum을 반환
		public static CommChTrtType fromString(String value) {
			return map.get(value);
		}
	}

	/**
	 * <PRE>
	 *  ClassName : EcodPdngType GwCode
	 * </PRE>
	 * @version : 1.0
	 * @date    : 2014. 6. 30. 오후 3:01:26
	 * @author  : CBJ
	 * @brief   : 암호화패딩유형코드(1019)
	 */
	public static enum EcodPdngType
	{
		/** PKCS5 */
		PKCS5("1"),
		/** ZERO */
		ZERO("2"),
		/** ANSIX */
		ANSIX923("3");

		private final String value;

        private EcodPdngType(String value) {
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
		private static final HashMap<String, EcodPdngType> map = new HashMap<String, EcodPdngType>();
		static {
			for(EcodPdngType it : values()) {
				map.put(it.toString(), it);
			}
		}

		// value에 해당되는 enum을 반환
		public static EcodPdngType fromString(String value) {
			return map.get(value);
		}

	}






	/**
	 * <PRE>
	 *  ClassName : GwCode
	 * </PRE>
	 * @version : 1.0
	 * @date    : 2014. 1. 28. 오후 8:51:17
	 * @author  : byw
	 * @brief   : 게이트웨이연결구분: 1102
	 */
	public static enum GwCnctDiv
	{
		/** EFM GW Server */
		EFM_GW_SERVER("1001"),
		/** GW Manager */
		GW_MANAGER("1002"),
		/** Connector */
		CONNECTOR("1003"),
		/** Connection */
		CONNECTION("1004"),
		//TODO 임시
		/** MPU10 */
		MPU10("2001");

		private final String value;

        private GwCnctDiv(String value) {
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
		private static final HashMap<String, GwCnctDiv> map = new HashMap<String, GwCnctDiv>();
		static {
			for(GwCnctDiv it : values()) {
				map.put(it.toString(), it);
			}
		}

		// value에 해당되는 enum을 반환
		public static GwCnctDiv fromString(String value) {
			return map.get(value);
		}

	}




	/**
	 * <PRE>
	 *  ClassName : GwCode
	 * </PRE>
	 * @version : 1.0
	 * @date    : 2014. 1. 28. 오후 8:51:17
	 * @author  : byw
	 * @brief   : 커뮤니케이션에이전트설정: 1103
	 */
	public static enum CommnAgntSetup
	{
		/** 패킷 큐 크기 */
		PACKET_QUEUE_SIZE("0001"),
		/** 계측 큐 크기 */
		MSR_QUEUE_SIZE("0002"),
		/** 상태 큐 크기 */
		STATUS_QUEUE_SIZE("0003"),
		/** 패킷 처리 스레드 개수 */
		PACKET_PROCESS_THREAD_COUNT("0011"),
		/** 측정 처리 스레드 개수 */
		MSR_PROCESS_THREAD_COUNT("0012"),
		/** 상태 처리 스레드 개수 */
		STATUS_PROCESS_THREAD_COUNT("0013"),
		/** 패킷 처리 모드 */
		PACKET_PROCESS_MODE("0021"),
		/** 측정 처리 모드 */
		MSR_PROCESS_MODE("0022"),
		/** 상태 모드 */
		STATUS_PROCESS_MODE("0023"),
		/** 패킷 큐 Push 타임아웃 */
		PACKET_QUEUE_PUSH_TIMEOUT("0031"),
		/** 측정 큐 Push 타임아웃 */
		MSR_QUEUE_PUSH_TIMEOUT("0032"),
		/** 상태 큐 Push 타임아웃 */
		STATUS_QUEUE_PUSH_TIMEOUT("0033"),
		/** 패킷 큐 벌크 주기 */
		PACKET_QUEUE_BULK_PERIOD("0041"),
		/** GW HB 요청 IP */
		GW_HB_REQUEST_IP("0101"),
		/** GW HB 요청 Port */
		GW_HB_REQUEST_PORT("0102"),
		/** GW HB 에러 기준 */
		GW_HB_ERROR_THRESHOLD("0103"),
		/** 장치 HB 요청 IP */
		DEVICE_HB_REQUEST_IP("0111"),
		/** 현장장치 HB 요청 Port */
		DEVICE_HB_REQUEST_PORT("0112"),
		/** 현장장치 HB 에러 리포트 기준 */
		DEVICE_HB_ERROR_THRESHOLD("0113"),
		/** 현장장치 제어 요청 IP */
		DEVICE_CONTROL_REQUEST_IP("0121"),
		/** 현장장치 제어 요청 Port */
		DEVICE_CONTROL_REQUEST_PORT("0122");

		private final String value;

        private CommnAgntSetup(String value) {
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
		private static final HashMap<String, CommnAgntSetup> map = new HashMap<String, CommnAgntSetup>();
		static {
			for(CommnAgntSetup it : values()) {
				map.put(it.toString(), it);
			}
		}

		// value에 해당되는 enum을 반환
		public static CommnAgntSetup fromString(String value) {
			return map.get(value);
		}

	}




	/**
	 * <PRE>
	 *  ClassName : GwCode
	 * </PRE>
	 * @version : 1.0
	 * @date    : 2014. 1. 28. 오후 8:51:17
	 * @author  : byw
	 * @brief   : 게이트웨이센싱태그유형: 1104
	 */
	public static enum GwSnsnTagType
	{
		/** 측정센싱 */
		MSR_VAL("10"),
		/** 상태 */
		STTUS("20"),
		/** 제어 */
		CONTROL("30"),
		/** 측정위치 */
		MSR_LOCATION("40"),
		/** 이진데이터 */
		BIN_DATA("50"),
		/** 문자열데이터 */
		STR_DATA("60"),
		/** 이벤트데이터 */
		EVENT_DATA("70"),
		/**  일반설정 */
		GENL_SETUP("80"),
		/** 스케줄링설정 */
		SCLG_SETUP("81"),
		;

		private final String value;

        private GwSnsnTagType(String value) {
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
		private static final HashMap<String, GwSnsnTagType> map = new HashMap<String, GwSnsnTagType>();
		static {
			for(GwSnsnTagType it : values()) {
				map.put(it.toString(), it);
			}
		}

		// value에 해당되는 enum을 반환
		public static GwSnsnTagType fromString(String value) {
			return map.get(value);
		}

	}






	/**
	 * <PRE>
	 *  ClassName : GwCode
	 * </PRE>
	 * @version : 1.0
	 * @date    : 2014. 1. 28. 오후 8:51:17
	 * @author  : byw
	 * @brief   : 커뮤니케이션에이전트설정분류: 1105
	 */
	public static enum CommnAgntSetupDiv
	{
		/** EFM GW 어댑터 */
		EFM_GW_ADAPTOR("0001"),
		/** KMAP GW 어댑터 */
		KMAP_GW_ADAPTOR("0002");

		private final String value;

        private CommnAgntSetupDiv(String value) {
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
		private static final HashMap<String, CommnAgntSetupDiv> map = new HashMap<String, CommnAgntSetupDiv>();
		static {
			for(CommnAgntSetupDiv it : values()) {
				map.put(it.toString(), it);
			}
		}

		// value에 해당되는 enum을 반환
		public static CommnAgntSetupDiv fromString(String value) {
			return map.get(value);
		}

	}





	/**
	 * <PRE>
	 *  ClassName : GwCode
	 * </PRE>
	 * @version : 1.0
	 * @date    : 2014. 1. 28. 오후 8:51:17
	 * @author  : byw
	 * @brief   : 패킷오류구분: 1111
	 */
	public static enum PacktErrTxnDiv
	{
		/** LOW_SYS_RCV_ERR_TXN */
		LOW_SYS_RCV_ERR_TXN("0001"),
		/** UP_SYS_RCV_ERR_TXN */
		UP_SYS_RCV_ERR_TXN("0002"),
		/** LOW_SYS_TRM_ERR_TXN */
		LOW_SYS_TRM_ERR_TXN("0003"),
		/** UP_SYS_TRM_ERR_TXN */
		UP_SYS_TRM_ERR_TXN("0004");

		private final String value;

        private PacktErrTxnDiv(String value) {
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
		private static final HashMap<String, PacktErrTxnDiv> map = new HashMap<String, PacktErrTxnDiv>();
		static {
			for(PacktErrTxnDiv it : values()) {
				map.put(it.toString(), it);
			}
		}

		// value에 해당되는 enum을 반환
		public static PacktErrTxnDiv fromString(String value) {
			return map.get(value);
		}

	}

	/**
	 * <PRE>
	 *  ClassName : EvPushType GwCode
	 * </PRE>
	 * @version : 1.0
	 * @date    : 2014. 5. 12. 오전 11:03:04
	 * @author  : CBJ
	 * @brief   : 이벤트푸시유형코드(1113)
	 */
	public static enum EvPushType
	{
		/** 펌웨어업데이트 */
		FIRMWARE_UPDATE("01"),
		/** 초기단말설정 */
		INITIAL_DEVICE_SETUP("02"),
		/** 단말기환경설정 */
		DEVICE_SETUP("03"),
		/** 원격제어 */
		REMOTE_CONTROL("04"),
		/** 메세지전달 */
		MESSAGE_FORWARDING("05");

		private final String value;

        private EvPushType(String value) {
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
		private static final HashMap<String, EvPushType> map = new HashMap<String, EvPushType>();
		static {
			for(EvPushType it : values()) {
				map.put(it.toString(), it);
			}
		}

		// value에 해당되는 enum을 반환
		public static EvPushType fromString(String value) {
			return map.get(value);
		}
	}

	/**
	 * <PRE>
	 *  ClassName : RoutingType GwCode
	 * </PRE>
	 * @version : 1.0
	 * @date    : 2014. 5. 31. 오후 3:47:33
	 * @author  : CBJ
	 * @brief   : 라우팅유형코드(1114)
	 */
	public static enum RoutType
	{
		/** 포함 */
		INCLUSION("01"),
		/** 배타 */
		EXCLUSION("02");

		private final String value;

        private RoutType(String value) {
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
		private static final HashMap<String, RoutType> map = new HashMap<String, RoutType>();
		static {
			for(RoutType it : values()) {
				map.put(it.toString(), it);
			}
		}

		// value에 해당되는 enum을 반환
		public static RoutType fromString(String value) {
			return map.get(value);
		}
	}

	/**
	 * <PRE>
	 *  ClassName : PushType GwCode
	 * </PRE>
	 * @version : 1.0
	 * @date    : 2014. 6. 8. 오후 8:14:32
	 * @author  : CBJ
	 * @brief   : 푸시유형코드(1115)
	 */
	public static enum PushType
	{
		SMS("01"),
		USSD("02");
		private final String value;

        private PushType(String value) {
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
		private static final HashMap<String, PushType> map = new HashMap<String, PushType>();
		static {
			for(PushType it : values()) {
				map.put(it.toString(), it);
			}
		}

		// value에 해당되는 enum을 반환
		public static PushType fromString(String value) {
			return map.get(value);
		}
	}

	/**
	 * <PRE>
	 *  ClassName : GwCode
	 * </PRE>
	 * @version : 1.0
	 * @date    : 2014. 1. 28. 오후 8:51:17
	 * @author  : byw
	 * @brief   : 현장장치상태: 1201
	 */
	public static enum SpotDevSttus
	{
		/** 미수집 */
		NO_COLLECTION("0000"),
		/** 수집 */
		COLLECTION("0001"),
		/** 고장 */
		MALFUNCTION("0002");

		private final String value;

        private SpotDevSttus(String value) {
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
		private static final HashMap<String, SpotDevSttus> map = new HashMap<String, SpotDevSttus>();
		static {
			for(SpotDevSttus it : values()) {
				map.put(it.toString(), it);
			}
		}

		// value에 해당되는 enum을 반환
		public static SpotDevSttus fromString(String value) {
			return map.get(value);
		}

	}






	/**
	 * <PRE>
	 *  ClassName : GwCode
	 * </PRE>
	 * @version : 1.0
	 * @date    : 2014. 1. 28. 오후 8:51:17
	 * @author  : byw
	 * @brief   : 게이트웨이내부연결상태: 1202
	 */
	public static enum GwIntnCnctSttus
	{
		/** 정상 */
		NORMALITY("0001"),
		/** 비정상 */
		ABNORMALITY("0002");

		private final String value;

        private GwIntnCnctSttus(String value) {
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
		private static final HashMap<String, GwIntnCnctSttus> map = new HashMap<String, GwIntnCnctSttus>();
		static {
			for(GwIntnCnctSttus it : values()) {
				map.put(it.toString(), it);
			}
		}

		// value에 해당되는 enum을 반환
		public static GwIntnCnctSttus fromString(String value) {
			return map.get(value);
		}

	}




	/**
	 * <PRE>
	 *  ClassName : GwCode
	 * </PRE>
	 * @version : 1.0
	 * @date    : 2014. 1. 28. 오후 8:51:17
	 * @author  : byw
	 * @brief   : 커뮤니케이션에이전트상태: 1203
	 */
	public static enum CommnAgntSttus
	{
		/** 정상 */
		NORMALITY("0001"),
		/** 비정상 */
		ABNORMALITY("0002");

		private final String value;

        private CommnAgntSttus(String value) {
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
		private static final HashMap<String, CommnAgntSttus> map = new HashMap<String, CommnAgntSttus>();
		static {
			for(CommnAgntSttus it : values()) {
				map.put(it.toString(), it);
			}
		}

		// value에 해당되는 enum을 반환
		public static CommnAgntSttus fromString(String value) {
			return map.get(value);
		}

	}





	/**
	 * <PRE>
	 *  ClassName : GwCode
	 * </PRE>
	 * @version : 1.0
	 * @date    : 2014. 1. 28. 오후 8:51:17
	 * @author  : byw
	 * @brief   : 3M상태: 1204
	 */
	public static enum M3sSttus
	{
		//
		/** 정상 */
		NORMALITY("0001"),
		/** 비정상 */
		ABNORMALITY("0002");

		private final String value;

        private M3sSttus(String value) {
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
		private static final HashMap<String, M3sSttus> map = new HashMap<String, M3sSttus>();
		static {
			for(M3sSttus it : values()) {
				map.put(it.toString(), it);
			}
		}

		// value에 해당되는 enum을 반환
		public static M3sSttus fromString(String value) {
			return map.get(value);
		}

	}





	/**
	 * <PRE>
	 *  ClassName : GwCode
	 * </PRE>
	 * @version : 1.0
	 * @date    : 2014. 1. 28. 오후 8:51:17
	 * @author  : byw
	 * @brief   : 연결상태: 1205
	 */
	public static enum CnctSttus
	{
		/** 연결 */
		CONNECTION("0001"),
		/** 비연결 */
		DISCONNECTION("0002");

		private final String value;

        private CnctSttus(String value) {
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
		private static final HashMap<String, CnctSttus> map = new HashMap<String, CnctSttus>();
		static {
			for(CnctSttus it : values()) {
				map.put(it.toString(), it);
			}
		}

		// value에 해당되는 enum을 반환
		public static CnctSttus fromString(String value) {
			return map.get(value);
		}

	}








	/**
	 * <PRE>
	 *  ClassName : GwCode
	 * </PRE>
	 * @version : 1.0
	 * @date    : 2014. 1. 28. 오후 8:51:17
	 * @author  : byw
	 * @brief   : 점검패킷처리상태: 1206
	 */
	public static enum ChkPacktTrtSttus
	{
		/** 정상 */
		NORMAIITY("0001"),
		/** 타임아웃 */
		TIMEOUT("0002"),
		/** 오작동 */
		MALFUNCTION("0003"),
		/** 시스템과부하 */
		SYSTEM_OVERLOAD("0004");

		private final String value;

        private ChkPacktTrtSttus(String value) {
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
		private static final HashMap<String, ChkPacktTrtSttus> map = new HashMap<String, ChkPacktTrtSttus>();
		static {
			for(ChkPacktTrtSttus it : values()) {
				map.put(it.toString(), it);
			}
		}

		// value에 해당되는 enum을 반환
		public static ChkPacktTrtSttus fromString(String value) {
			return map.get(value);
		}

	}



	/**
	 * <PRE>
	 *  ClassName : GwCode
	 * </PRE>
	 * @version : 1.0
	 * @date    : 2014. 1. 28. 오후 8:51:17
	 * @author  : byw
	 * @brief   : 수신패킷처리상태: 1207
	 */
	public static enum RcvPacktTrtSttus
	{
		/** 정상 */
		NORMALITY("0001"),
		/** 구현오류 */
		IMPLEMENTATION_ERROR("0002"),
		/** 패킷수신 */
		PACKET_RECEPTION("0003"),
		/** 패킷 푸시 오류 */
		PACKET_PUSH_ERROR("0004"),
		/** 복호화 오류 */
		DECRYPTION_ERROR("0005"),
		/** 패킷파싱 오류 */
		PASSING_ERROR("0006"),
		/** 인증 오류 */
		CERTIFICATION_ERROR("0007"),
		/** 응답 오류 */
		ACK_ERROR("0008"),
		/** 통신채널인증오류 */
		COMM_CH_ATHN_ERROR("0009"),
		/** 일반오류 */
		GENERAL_ERROR("0010"),
		/** 요청정보오류 */
		REQUEST_INFO_ERROR("0011"),
		/** 패킷수신오류 */
		PACKET_RECEPTION_ERROR("0012")
		;

		private final String value;

        private RcvPacktTrtSttus(String value) {
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
		private static final HashMap<String, RcvPacktTrtSttus> map = new HashMap<String, RcvPacktTrtSttus>();
		static {
			for(RcvPacktTrtSttus it : values()) {
				map.put(it.toString(), it);
			}
		}

		// value에 해당되는 enum을 반환
		public static RcvPacktTrtSttus fromString(String value) {
			return map.get(value);
		}

	}




	/**
	 * <PRE>
	 *  ClassName : GwCode
	 * </PRE>
	 * @version : 1.0
	 * @date    : 2014. 1. 28. 오후 8:51:17
	 * @author  : byw
	 * @brief   : 에러조치상태: 1208
	 */
	public static enum ErrActnSttus
	{
		/** 미확인 */
		UNCONFIRMED("0001"),
		/** 확인 */
		CONFIRMED("0002"),
		/** 분석 */
		ANALYSIS("0003"),
		/** 조치완료 */
		ACTION_COMPLETION("0004");

		private final String value;

        private ErrActnSttus(String value) {
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
		private static final HashMap<String, ErrActnSttus> map = new HashMap<String, ErrActnSttus>();
		static {
			for(ErrActnSttus it : values()) {
				map.put(it.toString(), it);
			}
		}

		// value에 해당되는 enum을 반환
		public static ErrActnSttus fromString(String value) {
			return map.get(value);
		}

	}




	/**
	 * <PRE>
	 *  ClassName : GwCode
	 * </PRE>
	 * @version : 1.0
	 * @date    : 2014. 1. 28. 오후 8:51:17
	 * @author  : byw
	 * @brief   : 메뉴유형: 1210
	 */
	public static enum MenuType
	{
		/** 메뉴 */
		MENU("0001"),
		/** 페이지 */
		PAGE("0002");

		private final String value;

        private MenuType(String value) {
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
		private static final HashMap<String, MenuType> map = new HashMap<String, MenuType>();
		static {
			for(MenuType it : values()) {
				map.put(it.toString(), it);
			}
		}

		// value에 해당되는 enum을 반환
		public static MenuType fromString(String value) {
			return map.get(value);
		}

	}



	/**
	 * <PRE>
	 *  ClassName : GwCode
	 * </PRE>
	 * @version : 1.0
	 * @date    : 2014. 1. 28. 오후 8:51:17
	 * @author  : byw
	 * @brief   : 하위수신데이터처리상태: 1211
	 */
	public static enum LowRcvDataTrtSttus
	{
		/** 요청 수신 */
		REQ_RECV("0001"),
		/** 요청 로직 처리중 */
		REQ_LOGIC_PROCESSING("0002"),
		/** 상위 시스템 전송 */
		UP_SYSTEM_SEND("0003"),
		/** 응답 수신 */
		RES_RECV("0004"),
		/** 응답 로직 처리중*/
		RES_LOGIC_PROCESSING("0005"),
		/** 하위 시스템 전송 */
		LOW_SYSTEM_SEND("0006"),
		/** 구현 오류 */
		IMPLEMENTATION_ERROR("0011"),
		/** 인증 오류 */
		CERTIFICATION_ERROR("0012"),
		/** 값 유효성 오류 */
		VALUE_VALIDATION_ERROR("0013"),
		/** 요청 로직 오류 */
		REQ_LOGIC_ERROR("0014"),
		/** 상위 시스템 전송 오류 */
		UP_SYSTEM_SEND_ERROR("0015"),
		/** 응답 타임아웃 */
		RES_TIME_OUT("0016"),
		/** 응답 로직 오류 */
		RES_LOGIC_ERROR("0017"),
		/** 하위 시스템 전송 오류 */
		LOW_SYSTEM_SEND_ERROR("0018");

		private final String value;

        private LowRcvDataTrtSttus(String value) {
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
		private static final HashMap<String, LowRcvDataTrtSttus> map = new HashMap<String, LowRcvDataTrtSttus>();
		static {
			for(LowRcvDataTrtSttus it : values()) {
				map.put(it.toString(), it);
			}
		}

		// value에 해당되는 enum을 반환
		public static LowRcvDataTrtSttus fromString(String value) {
			return map.get(value);
		}

	}





	/**
	 * <PRE>
	 *  ClassName : GwCode
	 * </PRE>
	 * @version : 1.0
	 * @date    : 2014. 1. 28. 오후 8:51:17
	 * @author  : byw
	 * @brief   : 상위수신데이터처리상태코드: 1212
	 */
	public static enum UpRcvDataTrtSttus
	{
		/** 요청 수신 */
		REQ_RECV("0001"),
		/** 요청 로직 처리중 */
		REQ_LOGIC_PROCESSING("0002"),
		/** 하위시스템 전송 */
		LOW_SYSTEM_SEND("0003"),
		/** 응답 수신 */
		RES_RECV("0004"),
		/** 응답 로직 처리중*/
		RES_LOGIC_PROCESSING("0005"),
		/** 상위시스템 전송 */
		UP_SYSTEM_SEND("0006"),
		/** 구현 오류 */
		IMPLEMENTATION_ERROR("0011"),
		/** 인증 오류 */
		CERTIFICATION_ERROR("0012"),
		/** 값 유효성 오류 */
		VALUE_VALIDATION_ERROR("0013"),
		/** 요청 로직 오류 */
		REQ_LOGIC_ERROR("0014"),
		/** 하위시스템 전송 오류 */
		LOW_SYSTEM_SEND_ERROR("0015"),
		/** 응답 타임아웃 */
		RES_TIME_OUT("0016"),
		/** 응답 로직 오류 */
		RES_LOGIC_ERROR("0017"),
		/** 상위시스템 전송 오류 */
		UP_SYSTEM_SEND_ERROR("0018");

		private final String value;

        private UpRcvDataTrtSttus(String value) {
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
		private static final HashMap<String, UpRcvDataTrtSttus> map = new HashMap<String, UpRcvDataTrtSttus>();
		static {
			for(UpRcvDataTrtSttus it : values()) {
				map.put(it.toString(), it);
			}
		}

		// value에 해당되는 enum을 반환
		public static UpRcvDataTrtSttus fromString(String value) {
			return map.get(value);
		}

	}

	/**
	 * <PRE>
	 *  ClassName : GwCode
	 * </PRE>
	 * @version : 1.0
	 * @date    : 2014. 1. 28. 오후 8:51:17
	 * @author  : byw
	 * @brief   : 전송패킷처리상태: 1213
	 */
	public static enum TrmPacktTrtSttus
	{
		/** 정상 */
		NORMALITY("0001"),
		/** 구현오류 */
		IMPLEMENTATION_ERROR("0002"),
		/** 객체 수신 */
		OBJECT_RECEPTION("0003"),
		/** 패킷생성 오류 */
		PACKET_CREATION_ERROR("0004"),
		/** 암호화 오류 */
		ENCRYPTION_ERROR("0005"),
		/** 패킷 푸시 오류 */
		PACKET_PUSH_ERROR("0006"),
		/** 인증 오류 */
		CERTIFICATION_ERROR("0007"),
		/** 패킷 전송 오류*/
		PACKET_TRANSMISSION_ERROR("0008"),
		/** 응답 오류 */
		ACK_ERROR("0009");

		private final String value;

        private TrmPacktTrtSttus(String value) {
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
		private static final HashMap<String, TrmPacktTrtSttus> map = new HashMap<String, TrmPacktTrtSttus>();
		static {
			for(TrmPacktTrtSttus it : values()) {
				map.put(it.toString(), it);
			}
		}

		// value에 해당되는 enum을 반환
		public static TrmPacktTrtSttus fromString(String value) {
			return map.get(value);
		}

	}


	/**
	 * <PRE>
	 *  ClassName : EvPushTrtSttus GwCode
	 * </PRE>
	 * @version : 1.0
	 * @date    : 2014. 5. 12. 오전 11:08:36
	 * @author  : CBJ
	 * @brief   : 이벤트푸시처리상태: 1214
	 */
	public static enum EvPushTrtSttus
	{
		/** 푸시요청수신 */
		PUSH_REQUEST_RECV("01"),
		/** Wake-UP전송 */
		WAKE_UP_SEND("02"),
		/** 이벤트요청수신 */
		EVENT_REQUEST_RECV("03"),
		/** 이벤트푸시완료 */
		EVENT_PUSH_COMPLETION("04"),
		/** 푸시오류 */
		PUSH_ERROR("05"),
		/** 요청타임아웃 */
		REQUEST_TIMEOUT("06"),
		/** 이벤트유효성에러 */
		EVENT_VALIDATION_ERROR("07");



		private final String value;

        private EvPushTrtSttus(String value) {
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
		private static final HashMap<String, EvPushTrtSttus> map = new HashMap<String, EvPushTrtSttus>();
		static {
			for(EvPushTrtSttus it : values()) {
				map.put(it.toString(), it);
			}
		}

		// value에 해당되는 enum을 반환
		public static EvPushTrtSttus fromString(String value) {
			return map.get(value);
		}

	}

	/**
	 * <PRE>
	 *  ClassName SpotDevSubsSttus GwCode
	 * </PRE>
	 * @brief: 장치청약상태: 1216
	 * @version 1.0
	 * @date 2014. 10. 15. 오후 6:51:37
	 * @author CBJ
	 */
	public static enum SpotDevSubsSttus
	{
		/** 미가입 */
		NOSUBS("01"),
		/** 개통대기 */
		OPN_WAIT("02"),
		/** 개통완료 */
		OPN_CMPLT("03"),
		/** 일시정지 */
		TSTOP("04"),
		/** 이용정지 */
		USST("05"),
		/** 미납 */
		NPAY("06"),
		/** 해지 */
		TRMN("07"),
		/** 장치구매 */
		DEV_BUY("08"),
		;
		private final String value;

        private SpotDevSubsSttus(String value) {
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
		private static final HashMap<String, SpotDevSubsSttus> map = new HashMap<String, SpotDevSubsSttus>();
		static {
			for(SpotDevSubsSttus it : values()) {
				map.put(it.toString(), it);
			}
		}

		// value에 해당되는 enum을 반환
		public static SpotDevSubsSttus fromString(String value) {
			return map.get(value);
		}

	}

	/**
	 * <PRE>
	 *  ClassName : GwCode
	 * </PRE>
	 * @version : 1.0
	 * @date    : 2014. 1. 28. 오후 8:51:17
	 * @author  : byw
	 * @brief   : 오류그룹: 1301
	 */
	public static enum ErrGroup
	{
		/** 구현 오류 */
		IMPLEMENTATION_ERROR("0001"),
		/** DB 오류 */
		DB_ERROR("0002"),
		/** 사용자 오류 */
		USER_ERROR("0003"),
		/** 로직 오류 */
		LOGIC_ERROR("0004");

		private final String value;

        private ErrGroup(String value) {
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
		private static final HashMap<String, ErrGroup> map = new HashMap<String, ErrGroup>();
		static {
			for(ErrGroup it : values()) {
				map.put(it.toString(), it);
			}
		}

		// value에 해당되는 enum을 반환
		public static ErrGroup fromString(String value) {
			return map.get(value);
		}

	}





	/**
	 * <PRE>
	 *  ClassName : GwCode
	 * </PRE>
	 * @version : 1.0
	 * @date    : 2014. 1. 28. 오후 8:51:17
	 * @author  : byw
	 * @brief   : 오류상세: 1302
	 */
	public static enum ErrDtl
	{
		/** 파싱오류 */
		PARSING_ERROR("00000001"),
		/** 세션 오류 */
		SESSION_ERROR("00000002"),
		/** 암호화 키 오류 */
		ENCRYPTION_KEY_ERROR("00000003");

		private final String value;

        private ErrDtl(String value) {
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
		private static final HashMap<String, ErrDtl> map = new HashMap<String, ErrDtl>();
		static {
			for(ErrDtl it : values()) {
				map.put(it.toString(), it);
			}
		}

		// value에 해당되는 enum을 반환
		public static ErrDtl fromString(String value) {
			return map.get(value);
		}

	}






	/**
	 * <PRE>
	 *  ClassName : GwCode
	 * </PRE>
	 * @version : 1.0
	 * @date    : 2014. 1. 28. 오후 8:51:17
	 * @author  : byw
	 * @brief   : 오류등급: 1303
	 */
	public static enum ErrClas
	{
		/** 치명적인 */
		FATAL("0001"),
		/** 오류 */
		ERROR("0002"),
		/** 경고 */
		WARN("0003");

		private final String value;

        private ErrClas(String value) {
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
		private static final HashMap<String, ErrClas> map = new HashMap<String, ErrClas>();
		static {
			for(ErrClas it : values()) {
				map.put(it.toString(), it);
			}
		}

		// value에 해당되는 enum을 반환
		public static ErrClas fromString(String value) {
			return map.get(value);
		}

	}





	/**
	 * <PRE>
	 *  ClassName : GwCode
	 * </PRE>
	 * @version : 1.0
	 * @date    : 2014. 1. 28. 오후 8:51:17
	 * @author  : byw
	 * @brief   : 패킷오류유형: 1304
	 */
	public static enum PacktErrType
	{
		/** 수신 일반 오류 */
		RCV_GENL_ERR("0001"),
		/** 수신 오류 */
		RCV_ERR("0002"),
		/** 수신 객체 변환 오류 */
		RCV_OBJ_CHANGE_ERR("0003"),
		/** 수신 푸시 오류 */
		RCV_PUSH_ERR("0004"),
		/** 수신 복호화 오류 */
		RCV_DECOD_ERR("0005"),
		/** 수신 인증 오류 */
		RCV_ATHN_ERR("0006"),
		/** 수신 응답 생성 오류 */
		RCV_RPY_CRET_ERR("0007"),
		/** 수신 응답 전송 오류 */
		RCV_RPY_TRM_ERR("0008"),

		/** 전송 일반 오류 */
		TRM_GENL_ERR("0011"),
		/** 전송 객체 변환 오류 */
		TRM_OBJ_CHANGE_ERR("0012"),
		/** 전송 암호화 오류 */
		TRM_ECOD_ERR("0013"),
		/** 전송 푸시 오류 */
		TRM_PUSH_ERR("0014"),
		/** 전송 인증 오류 */
		TRM_ATHN_ERR("0015"),
		/** 전송 오류 */
		TRM_ERR("0016"),
		/** 전송 응답 생성 오류 */
		TRM_RPY_CRET_ERR("0017"),
		/** 전송 응답 전송 오류 */
		TRM_RPY_TRM_ERR("0018");

		private final String value;

        private PacktErrType(String value) {
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
		private static final HashMap<String, PacktErrType> map = new HashMap<String, PacktErrType>();
		static {
			for(PacktErrType it : values()) {
				map.put(it.toString(), it);
			}
		}

		// value에 해당되는 enum을 반환
		public static PacktErrType fromString(String value) {
			return map.get(value);
		}

	}





	/**
	 * <PRE>
	 *  ClassName : GwCode
	 * </PRE>
	 * @version : 1.0
	 * @date    : 2014. 1. 28. 오후 8:51:17
	 * @author  : byw
	 * @brief   : 3M 센싱태그유형: 2001
	 */
	public static enum M3SnsnTagType
	{
		/** 측정센싱 */
		MSR_VAL("10"),
		/** 상태 */
		STTUS("20"),
		/** 제어 */
		CONTROL("30"),
		/** 측정위치 */
		MSR_LOCATION("40"),
		/** 이진데이터 */
		BIN_DATA("50"),
		/** 문자열데이터 */
		STR_DATA("60"),
		/** 이벤트데이터 */
		EVENT_DATA("70")
		;

		private final String value;

        private M3SnsnTagType(String value) {
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
		private static final HashMap<String, M3SnsnTagType> map = new HashMap<String, M3SnsnTagType>();
		static {
			for(M3SnsnTagType it : values()) {
				map.put(it.toString(), it);
			}
		}

		// value에 해당되는 enum을 반환
		public static M3SnsnTagType fromString(String value) {
			return map.get(value);
		}

	}



	/**
	 * <PRE>
	 *  ClassName : GwCode
	 * </PRE>
	 * @version : 1.0
	 * @date    : 2014. 1. 28. 오후 8:51:17
	 * @author  : byw
	 * @brief   : 센싱태그단위: 2002
	 */
	public static enum SnsnTagUnit
	{
		/** 상태 */
		STATUS("00000001"),
		/** % */
		PERCENT("00000002"),
		/** A */
		A("00000003"),
		/** Iavg */
		IAVG("00000004"),
		/** KW */
		KW("00000005"),
		/** kWh */
		KWH("00000006"),
		/** ㎥ */
		M3("00000007"),
		/** N㎥/Hr */
		NM3_HR("00000008"),
		/** ON/OFF */
		ON_OFF("00000009"),
		/** Ptot */
		PTOT("00000010"),
		/** VlnAvg */
		VLNAVG("00000011"),
		/** H */
		H("00000012"),
		/** ppm */
		PPM("00000013"),
		/** ON */
		ON("00000014"),
		/** OFF */
		OFF("00000015"),
		/** ℃ */
		C("00000016"),
		/** V */
		V("00000017");


		private final String value;

        private SnsnTagUnit(String value) {
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
		private static final HashMap<String, SnsnTagUnit> map = new HashMap<String, SnsnTagUnit>();
		static {
			for(SnsnTagUnit it : values()) {
				map.put(it.toString(), it);
			}
		}

		// value에 해당되는 enum을 반환
		public static SnsnTagUnit fromString(String value) {
			return map.get(value);
		}

	}



	/**
	 * <PRE>
	 *  ClassName : GwCode
	 * </PRE>
	 * @version : 1.0
	 * @date    : 2014. 1. 28. 오후 8:51:17
	 * @author  : byw
	 * @brief   : 게이트웨이센싱태그단위: 1106
	 */
	public static enum GwSnsnTagUnit
	{
		//
		/** 상태 */
		STATUS("00000001"),
		/** % */
		PERCENT("00000002"),
		/** A */
		A("00000003"),
		/** Iavg */
		IAVG("00000004"),
		/** KW */
		KW("00000005"),
		/** kWh */
		KWH("00000006"),
		/** ㎥ */
		M3("00000007"),
		/** N㎥/Hr */
		NM3_HR("00000008"),
		/** ON/OFF */
		ON_OFF("00000009"),
		/** Ptot */
		PTOT("00000010"),
		/** VlnAvg */
		VLNAVG("00000011"),
		/** H */
		H("00000012"),
		/** ppm */
		PPM("00000013"),
		/** ON */
		ON("00000014"),
		/** OFF */
		OFF("00000015"),
		/** ℃ */
		C("00000016"),
		/** V */
		V("00000017");


		private final String value;

        private GwSnsnTagUnit(String value) {
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
		private static final HashMap<String, GwSnsnTagUnit> map = new HashMap<String, GwSnsnTagUnit>();
		static {
			for(GwSnsnTagUnit it : values()) {
				map.put(it.toString(), it);
			}
		}

		// value에 해당되는 enum을 반환
		public static GwSnsnTagUnit fromString(String value) {
			return map.get(value);
		}

	}

	/**
	 * <PRE>
	 *  ClassName : GwRcvTrtDiv GwCode
	 * </PRE>
	 * @version : 1.0
	 * @date    : 2014. 1. 28. 오후 8:51:17
	 * @author  : byw
	 * @brief   : 게이트웨이수신처리구분: 로직코드
	 */
	public static enum GwRcvTrtDiv
	{
		/** GW 내부 응답 */
		GW_INNER_RES("01"),
		/** 수집 */
		COLLECTION("02"),
		/** 제어응답 */
		CONTROL_RES("03"),
		/** 마스터 데이터 갱신 */
		MASTER_DATA_UPDATE("04");


		private final String value;

        private GwRcvTrtDiv(String value) {
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
		private static final HashMap<String, GwRcvTrtDiv> map = new HashMap<String, GwRcvTrtDiv>();
		static {
			for(GwRcvTrtDiv it : values()) {
				map.put(it.toString(), it);
			}
		}

		// value에 해당되는 enum을 반환
		public static GwRcvTrtDiv fromString(String value) {
			return map.get(value);
		}

	}




	/**
	 * <PRE>
	 *  ClassName : GwSpotDevApiRpy GwCode
	 * </PRE>
	 * @version : 1.0
	 * @date    : 2014. 1. 28. 오후 8:51:17
	 * @author  : byw
	 * @brief   : 게이트웨이현장장치관리 API 응대코드
	 */
	public static enum GwSpotDevApiRpy
	{
		/** GW 내부 응답 */
		SUCCESS("100"),
		/** 수집 */
		FAIL("200");
		/** 제어응답 */


		private final String value;

        private GwSpotDevApiRpy(String value) {
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

		// value에 해당되는 enum을 반환하기 위한 Map 생성 및 설정
		private static final HashMap<String, GwSpotDevApiRpy> map = new HashMap<String, GwSpotDevApiRpy>();
		static {
			for(GwSpotDevApiRpy it : values()) {
				map.put(it.toString(), it);
			}
		}

		// value에 해당되는 enum을 반환
		public static GwSpotDevApiRpy fromString(String value) {
			return map.get(value);
		}

	}



	/**
	 * <PRE>
	 *  ClassName : GwCode
	 * </PRE>
	 * @version : 1.0
	 * @date    : 2014. 1. 28. 오후 8:51:17
	 * @author  : byw
	 * @brief   : 게이트웨이 캐쉬 이벤트 유형
	 */
	public static enum GwCacheEventType
	{
		/** 캐쉬 값 추가 */
		PUT("01"),
		/** 캐쉬 값 삭제 */
		REMOVE("02"),
		/** 캐쉬 리로딩 */
		RELOAD("03");


		private final String value;

        private GwCacheEventType(String value) {
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
		private static final HashMap<String, GwCacheEventType> map = new HashMap<String, GwCacheEventType>();
		static {
			for(GwCacheEventType it : values()) {
				map.put(it.toString(), it);
			}
		}

		// value에 해당되는 enum을 반환
		public static GwCacheEventType fromString(String value) {
			return map.get(value);
		}
	}

}
