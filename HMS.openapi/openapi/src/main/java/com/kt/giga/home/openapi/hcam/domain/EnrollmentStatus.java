package com.kt.giga.home.openapi.hcam.domain;

/**
 * Created by cecil on 2015-07-08.
 *
 * 카메라 등록 상태
 *
 */
public class EnrollmentStatus {

    private boolean connected;
    private boolean owned;

    public boolean isConnected() {
        return connected;
    }

    public void setConnected(boolean isConnected) {
        this.connected = isConnected;
    }

    public boolean isOwned() {
        return owned;
    }

    public void setOwned(boolean owned) {
        this.owned = owned;
    }
}