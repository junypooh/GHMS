package com.kt.giga.home.openapi.hcam.domain;

import com.google.common.base.MoreObjects;
import com.kt.giga.home.openapi.hcam.HCamConstants;

import static com.kt.giga.home.openapi.hcam.HCamConstants.SnsnTagCd.RETV_SCH_SETUP_DETECTION;
import static com.kt.giga.home.openapi.hcam.HCamConstants.SnsnTagCd.SCH_SETUP_DETECTION;
import static com.kt.giga.home.openapi.hcam.HCamConstants.SnsnTagCd.SCH_SETUP_RECORD;

/**
 * Created by cecil on 2015-07-25.
 */
public class ScheduleData {

    private long svcTgtSeq;
    private long spotDevSeq;
    private String snsnTagCd;
    private String dayOfWeekCd;
    private String startTime;
    private String finishTime;
    private Integer periodTime;
    private Integer durationTime;

    public long getSvcTgtSeq() {

        return svcTgtSeq;
    }

    public void setSvcTgtSeq(long svcTgtSeq) {

        this.svcTgtSeq = svcTgtSeq;
    }

    public long getSpotDevSeq() {

        return spotDevSeq;
    }

    public void setSpotDevSeq(long spotDevSeq) {

        this.spotDevSeq = spotDevSeq;
    }

    public String getSnsnTagCd() {

        return snsnTagCd;
    }

    public void setSnsnTagCd(String snsnTagCd) {

        this.snsnTagCd = snsnTagCd;
    }

    public String getDayOfWeekCd() {

        return dayOfWeekCd;
    }

    public void setDayOfWeekCd(String dayOfWeekCd) {

        this.dayOfWeekCd = dayOfWeekCd;
    }

    public String getStartTime() {

        return startTime;
    }

    public void setStartTime(String startTime) {

        this.startTime = startTime;
    }

    public String getFinishTime() {

        return finishTime;
    }

    public void setFinishTime(String finishTime) {

        this.finishTime = finishTime;
    }

    public Integer getPeriodTime() {

        return periodTime;
    }

    public void setPeriodTime(Integer periodTime) {

        this.periodTime = periodTime;
    }

    public Integer getDurationTime() {

        return durationTime;
    }

    public void setDurationTime(Integer durationTime) {

        this.durationTime = durationTime;
    }

    public ScheduleData(long svcTgtSeq, long spotDevSeq, String snsnTagCd, String dayOfWeekCd, String startTime, String finishTime, Integer periodTime, Integer durationTime) {

        this.svcTgtSeq = svcTgtSeq;
        this.spotDevSeq = spotDevSeq;
        this.snsnTagCd = snsnTagCd;
        if (this.snsnTagCd.equals(RETV_SCH_SETUP_DETECTION) || this.snsnTagCd.equals(SCH_SETUP_DETECTION))
            this.snsnTagCd = SCH_SETUP_DETECTION;
        else
            this.snsnTagCd = SCH_SETUP_RECORD;

        this.dayOfWeekCd = dayOfWeekCd;
        this.startTime = startTime;
        this.finishTime = finishTime;
        this.periodTime = periodTime;
        this.durationTime = durationTime;
    }

    @Override
    public String toString() {

        return MoreObjects.toStringHelper(this)
                .add("svcTgtSeq", svcTgtSeq)
                .add("spotDevSeq", spotDevSeq)
                .add("snsnTagCd", snsnTagCd)
                .add("dayOfWeekCd", dayOfWeekCd)
                .add("startTime", startTime)
                .add("finishTime", finishTime)
                .add("periodTime", periodTime)
                .add("durationTime", durationTime)
                .toString();

    }
}
