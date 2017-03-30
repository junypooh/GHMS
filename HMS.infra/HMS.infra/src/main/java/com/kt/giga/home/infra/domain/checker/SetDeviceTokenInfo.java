/*******************************************************************************
 * Copyright(c) 2015 KT. All rights reserved.
 * This software is the proprietary information of KT.
 *******************************************************************************/
package com.kt.giga.home.infra.domain.checker;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.kt.giga.home.infra.domain.commons.HttpSoapObjectRequest;

/**
 * Push ID 전송 request
 * @author dahye.kim (dahye.kim@ceinside.co.kr)
 * @since 2015. 6. 8.
 */
@XmlRootElement(name="SetDeviceTokenInfo", namespace="http://tempuri.org/")
public class SetDeviceTokenInfo extends HttpSoapObjectRequest {
    
    /** 사용자 ID */
    private String userID;
    
    /** 단말 식별자 */
    private String uuID;
    
    /** WiFi 맥주소 */
    private String macAddress;
    
    /** 스마트폰 유형 */
    private String deviceType;
    
    /** Push 아이디 */
    private String token;
    
    /** Push 사용여부 */
    private String pushYn;
    
    /** Push 사용 추가 옵션 */
    private String optional;

    @XmlElement(name="UserID", namespace="http://tempuri.org/")
    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    @XmlElement(name="UUID", namespace="http://tempuri.org/")
    public String getUuID() {
        return uuID;
    }

    public void setUuID(String uuID) {
        this.uuID = uuID;
    }

    @XmlElement(name="MacAddress", namespace="http://tempuri.org/")
    public String getMacAddress() {
        return macAddress;
    }

    public void setMacAddress(String macAddress) {
        this.macAddress = macAddress;
    }

    @XmlElement(name="DeviceType", namespace="http://tempuri.org/")
    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    @XmlElement(name="Token", namespace="http://tempuri.org/")
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @XmlElement(name="PushYN", namespace="http://tempuri.org/")
    public String getPushYn() {
        return pushYn;
    }

    public void setPushYn(String pushYn) {
        this.pushYn = pushYn;
    }

    @XmlElement(name="Optional", namespace="http://tempuri.org/")
    public String getOptional() {
        return optional;
    }

    public void setOptional(String optional) {
        this.optional = optional;
    }
    
    

}
