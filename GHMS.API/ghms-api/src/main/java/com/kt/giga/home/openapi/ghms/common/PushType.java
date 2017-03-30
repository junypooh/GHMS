/*******************************************************************************
 * Copyright(c) 2015 KT. All rights reserved.
 * This software is the proprietary information of KT.
 *******************************************************************************/
package com.kt.giga.home.openapi.ghms.common;

/**
 * PUSH 코드 enum Class
 * @author dahye.kim (dahye.kim@ceinside.co.kr)
 * @since 2015. 4. 2.
 */
public enum PushType {

    /** 현장장치기본 펌웨어 업데이트 종료 푸시 */
    SLAVE_REQUEST("001HIT003A0001"),
    /** 슬레이브 신청 승인 */
    SLAVE_REQUEST_OK("001HIT003A0002"),
    /** 현장장치기본 펌웨어 업데이트 종료 푸시 */
    FRMWR_VER_COMPLETE("001HIT003A0004"),
    /** GW 공장초기화 알림(샌싱태그 와 무관) */
    DEVICE_INITIAL("001HIT003A0005"),
    /** 모드 변경 시 통보 PUSH */
    HUB_MOD_CHG("001HIT003A0006"),
    /** 도어락 열림 */
    DOOR_LOCK_OPEN("001HIT003A0007","50996203"),
    /** 가스밸브 통보(refresh 용도) */
    GASVALVE_REFRESH("001HIT003A0008"),
    /** 침입감지 작동 */
    TRESPS_SENSOR_ON("001HIT003A0009","99990000"),
    /** 침입감지 해제 */
    TRESPS_SENSOR_OFF("001HIT003A0010","99990000"),
    /** 가스밸브 잠금 */
    GAS_VALVE_CLOSE("001HIT003A0011","50992503"),
    /** 도어락 방범모드 제어 (미사용) */
    DOOR_CRIME_PREVENT_MODE("001HIT003A0012",""),
    /** 슬레이브 신청 승인 거절 */
    SLAVE_REQUEST_NOK("001HIT003A0013"),
    /** 디바이스 추가 완료시 통보 PUSH */
    DEVICE_ADD("001HIT003A0014"),
    /** 디바이스 제거 완료시 통보 PUSH */
    DEVICE_DEL("001HIT003A0015"),
    /** Security 제품 비정상 등록 PUSH */
    SECURITY_PRODUCT_INCLUSION_FAULT("001HIT003A0016"),
    /** 가스밸브 남은시간 통보 */
    GASVALVE_REMAIN_TIME("001HIT003A0017"),
    /** 서비스 이용중지 통보 PUSH */
    USE_STOP_SERVICE("001HIT003A0018");
    
    
    private String eventId;
    private String snsnTagCd;
    
    private PushType(String eventId){
        this.eventId = eventId;
    }
    
    private PushType(String eventId, String snsnTagCd){
        this.eventId = eventId;
        this.snsnTagCd = snsnTagCd;
    }
    
    public String getEventId(){
        return eventId;
    }
    
    public String getSnsnTagCd(){
        return snsnTagCd;
    }

}
