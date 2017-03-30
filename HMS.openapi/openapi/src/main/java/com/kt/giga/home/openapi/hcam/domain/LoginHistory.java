package com.kt.giga.home.openapi.hcam.domain;

import org.joda.time.DateTime;

/**
 * Created by cecil on 2015-07-16.
 */
public class LoginHistory {

    private long mbrSeq;
    private String telNo;
    private DateTime loginDateTime;

    public DateTime getLoginDateTime() {

        return loginDateTime;
    }

    public void setLoginDateTime(DateTime loginDateTime) {

        this.loginDateTime = loginDateTime;
    }

    public long getMbrSeq() {

        return mbrSeq;
    }

    public void setMbrSeq(long mbrSeq) {

        this.mbrSeq = mbrSeq;
    }

    public String getTelNo() {

        return telNo;
    }

    public void setTelNo(String telNo) {

        this.telNo = telNo;
    }
}
