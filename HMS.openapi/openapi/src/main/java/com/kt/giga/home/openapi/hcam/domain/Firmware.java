package com.kt.giga.home.openapi.hcam.domain;

import org.apache.ibatis.type.Alias;

/**
 * Created by cecil on 2015-06-15.
 */

@Alias("FirmwareAlias")
public class Firmware {
    private String version;
    private String modelName;
    private boolean mandatory;
    private boolean beta;

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public boolean isMandatory() {
        return mandatory;
    }

    public void setMandatory(boolean mandatory) {
        this.mandatory = mandatory;
    }

    public boolean isBeta() {
        return beta;
    }

    public void setBeta(boolean beta) {
        this.beta = beta;
    }
}
