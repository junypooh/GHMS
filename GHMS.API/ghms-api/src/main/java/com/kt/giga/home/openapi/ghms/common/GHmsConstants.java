package com.kt.giga.home.openapi.ghms.common;

import java.util.EnumSet;

import com.google.gson.Gson;

/**
 * 홈캠 Constants 클래스
 * TODO 센싱 태그 코드 및 rowExtension 키 정리 필요
 *  
 * @author 조홍진
 *
 */
public class GHmsConstants {
	
	public static class CommonConstants {
		
		/** API 리턴 미디어 타입 */	
		public static final String PRODUCES_JSON = "application/json; charset=UTF-8";
		
		/** 지역코드 */
		public static final String DSTR_CD = "001";
		
		/** 서비스테마코드 */
		public static final String SVC_THEME_CD = "HIT";
		
		/** 단위서비스코드 */
		public static final String UNIT_SVC_CD = "003";
		
		/** 허브 서비스 대상 유형 코드 */
		public static final String GHMS_GW_SVC_TGT_TYPE_CD = "0000";
		
		/** AP 서비스 대상 유형 코드 */
		public static final String GHMS_AP_SVC_TGT_TYPE_CD = "0001";
	}

	/**
	 * 
	 * 센싱 태그 코드 정의
	 *
	public static class SnsnTagCd {
		
		public static final String RTIME_RELAY_SESSION_CONN			= "30000010";
		
		public static final String RETV_GEN_SETUP					= "31000002";
		public static final String RETV_SCH_SETUP					= "31000003";
		public static final String RETV_SCH_SETUP_DETECTION			= "31000004";
		public static final String RETV_SCH_SETUP_RECORD			= "31000005";
		public static final String RETV_UCLOUD_TOKEN				= "31000006";
		
		public static final String VOICE_CONTROL					= "50000001";
		
		public static final String GEN_SETUP_RESOLUTION				= "80000002";
		public static final String GEN_SETUP_DETECTION				= "80000003";
		public static final String GEN_SETUP_DETECTION_MODE			= "80000004";
		public static final String GEN_SETUP_SAVE_MODE				= "80000010";
		public static final String GEN_SETUP_RECORD_SCHEDULE		= "80000012";
		public static final String GEN_SETUP_DETECTION_SCHEDULE		= "80000013";
		public static final String GEN_SETUP_RECORD_MODE			= "80000014";
		
		public static final String UCLOUD_API_KEY					= "80000024";
		public static final String UCLOUD_API_SECRET				= "80000025";
		public static final String UCLOUD_AUTH_TOKEN				= "80000026";
		public static final String UCLOUD_AUTH_TOKEN_SECRET			= "80000027";
		public static final String UCLOUD_PATH						= "80000028";
		
		public static final String SCH_SETUP_DETECTION				= "80010001";
		public static final String SCH_SETUP_RECORD					= "80010002";
	}
	 */
	
	/**
	 * 
	 * 현장 장치 확장 테이블의 항목 이름 
	 *
	 */
	public static class SpotDevItemNm {

        public static final String GW_MAC                           = "gwMac";
        public static final String GW_SEREIAL                       = "gwSerial";
        public static final String GW_SECRET                        = "gwSecret";
        public static final String GW_MODEL_CD                      = "gwModelCd";
        public static final String GW_MODEL_NM                      = "gwModelName";
        
        public static final String AP_NAME                          = "apName";
        public static final String AP_POWER                         = "apPower";
        public static final String PRIVACY                          = "privacy";
        public static final String STORAGE_PNS                      = "storagePNS";
        public static final String MOVING_PNS                       = "movingPNS";
        
        public static final String SAVE_MODE                        = "saveMode";
        public static final String REVERTED                         = "reverted";
        public static final String RESOLUTION                       = "resolution";
        public static final String V_SENSITIVITY                    = "vSensitivity";
        public static final String M_SENSITIVITY                    = "mSensitivity";
        
        public static final String DETECTION                        = "detection";
        public static final String DETECTION_MODE                   = "detectionMode";
        public static final String LAST_DETECTION_TIME              = "lastDetectionTime";
        public static final String CON_STAT                         = "conStat";
        public static final String CON_COUNT                        = "conCount";
        
        public static final String UPGRADE_STATUS                   = "upgradeStatus";
        public static final String UPGRADE_START_TIME               = "upgradeStartTime";
        public static final String UCLOUD_STATUS                    = "ucloudStatus";
	}
	
	/**
	 * 
	 * 현장 장치 확장 테이블의 항목 값
	 *
	 */
	public static class SpotDevItemVal {
		
		public static final String CON_STAT_OFFLINE					= "0";
		public static final String CON_STAT_ONLINE					= "1";
		
		public static final String UPGRADE_STATUS_FAILURE			= "-1";
		public static final String UPGRADE_STATUS_INIT				= "0";
		public static final String UPGRADE_STATUS_START				= "1";
		public static final String UPGRADE_STATUS_DOWNLOAD			= "2";
		public static final String UPGRADE_STATUS_INSTALL			= "3";
		
		public static final String UCLOUD_STATUS_OFF				= "0";
		public static final String UCLOUD_STATUS_ON					= "1";
	}
	
	/**
	 * 
	 * 에러 코드 정의
	 *
	 */
	public static class ErrorCode {
		
		public static final String EXPIRED_AUTH_TOKEN				= "-11";
		public static final String INVALID_INIT_AUTH_TOKEN			= "-12";
		public static final String INVALID_LOGIN_AUTH_TOKEN			= "-13";
		public static final String DUPLICATE_LOGIN_AUTH_TOKEN		= "-14";

		public static final String SDP_INVALID_ID					= "-70";
		public static final String SDP_INVALID_PASSWD				= "-71";
		public static final String SDP_RETIRE_USER					= "-72";
		public static final String SDP_CORP_USER					= "-73";
		public static final String SDP_STOP_USER					= "-74";
		public static final String SDP_QOOKNSHOW					= "-75";
		public static final String SDP_SYSTEM_ERROR					= "-76";
		public static final String SDP_NETWWORK_ERROR				= "-77";
		public static final String SDP_UNKNOWN_ERROR				= "-79";
		public static final String SDP_INVALID_PASSWD_05			= "-81";
		public static final String SDP_INVALID_PASSWD_10			= "-82";
/*
		public static final String INVALID_ID_PASSWD				= "-14";
		public static final String SDP_CALL_FAILURE					= "-15";
		public static final String UNKNOWN_SDP_RESULT				= "-16";
		public static final String LOGIN_MAX_COUNT_OVERFLOW			= "-17";
*/
		public static final String INVALID_DEVICE					= "-21";
		public static final String NOT_CONTROLLABLE_DEVICE			= "-22";
		public static final String CONFLICT_FIRMWAREUPGRADE			= "-26";
		
		public static final String EC_ERROR							= "-31";
		
		public static final String RELAY_SERVER_ERROR				= "-41";
		
		public static final String EXPIRED_VOICE_SESSION			= "-51";
		public static final String VOICE_SESSION_OVERFLOW			= "-52";
		
		public static final String UCLOUD_ERROR						= "-61";
		public static final String UCLOUD_ID_NOT_FOUND				= "-62";
		
		public static final String REQUEST_NULL_EXCEPTION           = "-98";
		public static final String UNKNOWN_EXCEPTION				= "-99";
		
	}

	/**
	 * 현장장치 알람 정의
	 */
	public static class SpotDevAlram {
		
		enum Alram { 
			BATTERY,
			SECURITY,
			REMOTE,
			LONG_OPEN
		}
		
		public static String toDefaultString() {
			
			Gson gson = new Gson();
			EnumSet<Alram> alram = EnumSet.of(Alram.BATTERY,Alram.REMOTE,Alram.SECURITY,Alram.LONG_OPEN);
		    return gson.toJson(alram);
		}
	}
	
}
