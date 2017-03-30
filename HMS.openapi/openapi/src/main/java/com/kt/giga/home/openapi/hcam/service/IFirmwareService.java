package com.kt.giga.home.openapi.hcam.service;

import com.kt.giga.home.openapi.hcam.domain.Firmware;
import com.kt.giga.home.openapi.vo.spotdev.SpotDev;
import org.springframework.stereotype.Service;

/**
 * Created by cecil on 2015-06-16.
 */
@Service
public interface IFirmwareService {
    Firmware getLatestBetaFirmware(SpotDev spotDev);
}
