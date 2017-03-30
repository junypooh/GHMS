package com.kt.giga.home.openapi.hcam;

/**
 * 홈캠 Constants 클래스
 * TODO 센싱 태그 코드 및 rowExtension 키 정리 필요
 *
 * @author
 *
 */
public class HCamConstants {

	/**
	 *
	 * 센싱 태그 코드 정의
	 *
	 */
	public static class SnsnTagCd {

		public static final String RTIME_RELAY_SESSION_CONN		= "30000010";

		public static final String RETV_SETUP						= "31000009";
		public static final String RETV_GEN_SETUP					= "31000002";
		public static final String RETV_SCH_SETUP					= "31000003";
        // 감지 스케줄 조회
		public static final String RETV_SCH_SETUP_DETECTION		    = "31000004";
        // 녹화 스케줄 조회
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

        // 감지 스케줄 설정
		public static final String SCH_SETUP_DETECTION				= "81000001";

        // 녹화 스케줄 설정
		public static final String SCH_SETUP_RECORD					= "81000002";
	}

	/**
	 * 현장 장치 확장 테이블의 항목 이름
	 *
	 *
	 */
	public static class SpotDevItemNm {

		public static final String CCTV_MAC							= "cctvMac";
		public static final String CCTV_SEREIAL						= "cctvSerial";
		public static final String CCTV_SECRET						= "cctvSecret";
		public static final String CCTV_MODEL_CD					= "cctvModelCd";
		public static final String CCTV_MODEL_NM					= "cctvModelName";

		public static final String AP_NAME							= "apName";
		public static final String AP_POWER							= "apPower";
		public static final String PRIVACY							= "privacy";
		public static final String STORAGE_PNS						= "storagePNS";
		public static final String MOVING_PNS						= "movingPNS";

		public static final String SAVE_MODE						= "saveMode";
		public static final String REVERTED							= "reverted";
		public static final String RESOLUTION						= "resolution";
		public static final String V_SENSITIVITY					= "vSensitivity";
		public static final String M_SENSITIVITY					= "mSensitivity";

		public static final String DETECTION						= "detection";
		public static final String DETECTION_MODE					= "detectionMode";
		public static final String LAST_DETECTION_TIME				= "lastDetectionTime";
		public static final String CON_STAT							= "conStat";
		public static final String CON_COUNT						= "conCount";

		public static final String UPGRADE_STATUS					= "upgradeStatus";
		public static final String UPGRADE_START_TIME				= "upgradeStartTime";
		public static final String UPGRADE_COMPLETE_TIME			= "upgradeCompleteTime";
		public static final String UCLOUD_STATUS					= "ucloudStatus";
	}

	/**
	 *
	 * 현장 장치 확장 테이블의 항목 값
	 *
	 */
	public static class SpotDevItemVal {

            public static final String CON_STAT_OFFLINE					=         "0";
            public static final String CON_STAT_ONLINE					=          "1";
            public static final String CON_STAT_INSECURE                =      "2";

            public static final String UPGRADE_STATUS_INIT				=       "0";
            public static final String UPGRADE_STATUS_IDLE				=       "10";
            public static final String UPGRADE_STATUS_DOWNLOAD			=    "30";
            public static final String UPGRADE_STATUS_DOWNLOAD_OK		=  "40";
            public static final String UPGRADE_STATUS_INSTALL			=     "50";
            public static final String UPGRADE_STATUS_SUCCESS			=     "90";
            public static final String UPGRADE_STATUS_CONN_TIMEOUT		= "-91";
            public static final String UPGRADE_STATUS_TIMEOUT			=     "-92";
            public static final String UPGRADE_STATUS_FAILURE			=     "-99";

            public static final String UCLOUD_STATUS_OFF				=         "0";
            public static final String UCLOUD_STATUS_ON					=         "1";
	}

	/**
	 *
	 * 트랜잭션 결과 테이블의 제어 유형
	 *
	 */
	public static class TransacResultType {

		public static final String RETRIEVE							= "r";
		public static final String CONTROL							= "c";
		public static final String SETUP							= "s";
	}

	/**
	 *
	 * 에러 코드 정의
	 *
	 */
	public static class ErrorCode {

        public static final String EXPIRED_AUTH_TOKEN         = "-11";
        public static final String INVALID_INIT_AUTH_TOKEN    = "-12";
        public static final String INVALID_LOGIN_AUTH_TOKEN   = "-13";

        public static final String INVALID_DEVICE             = "-21";
        public static final String NOT_CONTROLLABLE_DEVICE    = "-22";
        public static final String CONFLICT_FIRMWAREUPGRADE   = "-26";
        public static final String CONFLICT_MONITORING        = "-27";

        public static final String EC_ERROR                   = "-31";
        public static final String CEMS_ERROR                 = "-32";

        public static final String RELAY_SERVER_ERROR         = "-41";

        public static final String EXPIRED_VOICE_SESSION      = "-51";
        public static final String VOICE_SESSION_OVERFLOW     = "-52";

        public static final String UCLOUD_ERROR               = "-61";
        public static final String UCLOUD_ID_NOT_FOUND        = "-62";

        public static final String SDP_INVALID_IDNPASSWD      = "-71";
        public static final String SDP_RETIRE_USER            = "-72";
        public static final String SDP_CORP_USER              = "-73";
        public static final String SDP_STOP_USER              = "-74";
        public static final String SDP_QOOKNSHOW              = "-75";
        public static final String SDP_SYSTEM_ERROR           = "-76";
        public static final String SDP_NETWWORK_ERROR         = "-77";
        public static final String SDP_INTERNET_ONLY_ID_ERROR = "-78";
        public static final String SDP_UNKNOWN_ERROR          = "-79";
        public static final String SDP_INVALID_PASSWD_05      = "-81";
        public static final String SDP_INVALID_PASSWD_10      = "-82";
        public static final String SDP_REQ_TERM               = "-83";
        public static final String SDP_REQ_AGE                = "-84";

        public static final String UNKNOWN_EXCEPTION          = "-99";
    }

}
