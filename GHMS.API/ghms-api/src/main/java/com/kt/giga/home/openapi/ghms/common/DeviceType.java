/*******************************************************************************
 * Copyright(c) 2015 KT. All rights reserved.
 * This software is the proprietary information of KT.
 *******************************************************************************/
package com.kt.giga.home.openapi.ghms.common;

/**
 * 장치 유형 코드 enum Class
 * code 변경 시 DB(DEV_MODEL_BAS)도 변경해줘야함.
 * @author dahye.kim (dahye.kim@ceinside.co.kr)
 * @since 2015. 3. 27.
 */
public enum DeviceType {

    GATE_WAY("0207", "IoT허브", "홈매니저허브"),
    DOOR_LOCK("4003", "디지털 도어락", "도어락"),
    GAS_VALVE("1006", "가스 안전기", "가스 안전기"),
    TRESPS_SENSOR("0701", "열림 감지기", "열림 감지기"),
    UNKNOWN("0000", "UnKnown", "UnKnown"),
    KT_AP_SVC("G000", "GiGAWiFihome", "GiGA WiFi home"),
    KT_HOME_SVC("G001", "GiGAWiFiKTHOMESVC", "KT홈서비스"),
    KT_PC_SVC("G002", "GiGAWiFiPC", "PC"),
    KT_TIME_SVC("G003", "GiGAWiFiInternet", "인터넷");
    
    private String code;
    private String groupNm;
    private String modelNm;
    
    private DeviceType(String code, String groupNm, String modelNm) {
        this.code = code;
        this.groupNm = groupNm;
        this.modelNm = modelNm;
    }
    
    private DeviceType(String code, String groupNm) {
        this.code = code;
        this.groupNm = groupNm;
    }
    
    private DeviceType(String code) {
        this.code = code;
    }
    
    public String getCode() {
        return code;
    }
    
	public String getGroupNm() {
		return groupNm;
	}

	public String getModelNm() {
		return modelNm;
	}
}
