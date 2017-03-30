package com.kt.giga.home.openapi.hcam.dao;

import com.kt.giga.home.openapi.hcam.domain.Firmware;
import com.kt.giga.home.openapi.vo.spotdev.SpotDev;
import org.apache.ibatis.annotations.Param;

/**
 * 디바이스 매퍼 인터페이스
 *
 * @author
 */
public interface FirmwareDao {

    /**
     * Get a latest beta firmware
     *
     * @return ${FirmwareBeta}
     */
    public Firmware getLatestBetaFirmware(
            @Param("spotDev") SpotDev spotDev
    );

}
