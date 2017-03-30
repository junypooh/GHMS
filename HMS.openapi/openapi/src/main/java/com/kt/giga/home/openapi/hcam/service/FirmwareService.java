package com.kt.giga.home.openapi.hcam.service;

import com.kt.giga.home.openapi.hcam.dao.FirmwareDao;
import com.kt.giga.home.openapi.hcam.domain.Firmware;
import com.kt.giga.home.openapi.vo.spotdev.SpotDev;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Created by cecil on 2015-06-16.
 */
@Component
public class FirmwareService implements IFirmwareService {

    private final Logger log = LoggerFactory.getLogger(getClass());

    @Value("${service.code}")
    private String svcCode;

    @Autowired
    private InitService initService;

    @Autowired
    private FirmwareDao firmwareDao;

    @Override
    public Firmware getLatestBetaFirmware(SpotDev spotDev) {
        String betaVersion = initService.getProperty(svcCode, "beta.firm.version");
        String betaOption = initService.getProperty(svcCode, "beta.firm.upOption");

        Firmware betaFirmware;

        try {
            if (betaVersion != null && betaOption != null) {
                betaFirmware = new Firmware();
                betaFirmware.setVersion(betaVersion);
                betaFirmware.setBeta(true);
                betaFirmware.setMandatory(betaOption.equals("1") ? Boolean.TRUE : Boolean.FALSE);
            } else {
                betaFirmware = firmwareDao.getLatestBetaFirmware(spotDev);
            }
        } catch (Exception e) {
            betaFirmware = new Firmware();
            betaFirmware.setVersion("0.00.00");
            betaFirmware.setBeta(Boolean.TRUE);
            betaFirmware.setMandatory(Boolean.FALSE);
        }

        return betaFirmware;
    }

}
