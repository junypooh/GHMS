/*******************************************************************************
 * Copyright(c) 2015 KT. All rights reserved.
 * This software is the proprietary information of KT.
 *******************************************************************************/
package com.kt.giga.home.openapi.ghms.ktInfra.domain.vo;


/**
 * NAS 상태조회 response
 * @author dahye.kim (dahye.kim@ceinside.co.kr)
 * @since 2015. 6. 8.
 */
public class GetNASStateInfoResult {

    /** 처리결과 코드 */
    private String resultCode;
    
    /** 처리결과 메시지 */
    private String resultMessage;
    
    /** NAS 상태 */
    private String enable;

    public String getResultCode() {
        return resultCode;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

    public String getResultMessage() {
        return resultMessage;
    }

    public void setResultMessage(String resultMessage) {
        this.resultMessage = resultMessage;
    }

    public String getEnable() {
        return enable;
    }

    public void setEnable(String enable) {
        this.enable = enable;
    }
    
    
}
