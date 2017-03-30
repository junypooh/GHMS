/*******************************************************************************
 * Copyright(c) 2015 KT. All rights reserved.
 * This software is the proprietary information of KT.
 *******************************************************************************/
package com.kt.giga.home.infra.domain.checker;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * PC On/Off 상태 체크 response
 * @author dahye.kim (dahye.kim@ceinside.co.kr)
 * @since 2015. 6. 8.
 */
@XmlRootElement(name="GetPCOnOffStateResult", namespace="http://tempuri.org/")
public class GetPCOnOffStateResult {

    /** 처리결과 코드 */
    private String resultCode;
    
    /** 처리결과 메시지 */
    private String resultMessage;
    
    /** OnOff 상태 */
    private String onOff;

    public String getResultCode() {
        return resultCode;
    }
    
    @XmlElement(name="ResultCode", namespace="http://tempuri.org/")
    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

    public String getResultMessage() {
        return resultMessage;
    }

    @XmlElement(name="ResultMessage", namespace="http://tempuri.org/")
    public void setResultMessage(String resultMessage) {
        this.resultMessage = resultMessage;
    }

    public String getOnOff() {
        return onOff;
    }

    @XmlElement(name="OnOff", namespace="http://tempuri.org/")
    public void setOnOff(String onOff) {
        this.onOff = onOff;
    }
    
    
}
