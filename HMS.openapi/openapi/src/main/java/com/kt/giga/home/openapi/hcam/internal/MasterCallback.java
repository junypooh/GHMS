package com.kt.giga.home.openapi.hcam.internal;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.kt.giga.home.openapi.common.APIEnv;
import com.kt.giga.home.openapi.hcam.HCamConstants.SpotDevItemNm;
import com.kt.giga.home.openapi.hcam.HCamConstants.SpotDevItemVal;
import com.kt.giga.home.openapi.hcam.domain.Firmware;
import com.kt.giga.home.openapi.hcam.service.DeviceService;
import com.kt.giga.home.openapi.hcam.service.IFirmwareService;
import com.kt.giga.home.openapi.hcam.service.InitService;
import com.kt.giga.home.openapi.util.JsonUtils;
import com.kt.giga.home.openapi.vo.row.GenlSetupData;
import com.kt.giga.home.openapi.vo.row.SclgSetupData;
import com.kt.giga.home.openapi.vo.spotdev.SpotDev;
import com.kt.giga.home.openapi.vo.spotdev.SpotDevDtl;
import com.kt.giga.home.openapi.vo.spotdev.SpotDevRetvReslt;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Iterator;
import java.util.List;

public abstract class MasterCallback<V> implements RetvCallback<V> {

    private static Gson gson = null;

    static {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gson = gsonBuilder.create();
    }

    private final Logger log = LoggerFactory.getLogger(getClass());
    protected boolean removeResult = true;
	protected boolean genlSetupDataRequired = true;
	protected boolean sclgSetupDataRequired = true;
	protected boolean devDtlRequired = true;
	protected boolean realtimeRequired = false;
	protected APIEnv env;
	protected InitService initService;
	protected String svcCode;
	protected DeviceService deviceService;
    protected IFirmwareService IFirmwareService;

	public void setAPIEnv(APIEnv env) {
		this.env = env;
	}

	public void setInitService(InitService initService) {
		this.initService = initService;
	}

	public void setSvcCode(String svcCode) {
		this.svcCode = svcCode;
	}

	public void setDeviceService(DeviceService deviceService) {
		this.deviceService = deviceService;
	}

	public void setRealtimeRequired(boolean realtimeRequired) {
		this.realtimeRequired = realtimeRequired;
	}

    public void setIFirmwareService(IFirmwareService IFirmwareService) {
        this.IFirmwareService = IFirmwareService;
    }

    @Override
    abstract public V retv(List<SpotDev> spotDevs, List<SpotDevRetvReslt> spotDevRetvReslts);

	protected SpotDevRetvReslt mergeAll(List<SpotDev> spotDevs, List<SpotDevRetvReslt> spotDevRetvReslts) {

		SpotDevRetvReslt mergedReslt = new SpotDevRetvReslt();
		mergedReslt.setSpotDevs(spotDevs);

		for (Iterator<SpotDevRetvReslt> iterator = spotDevRetvReslts.iterator(); iterator.hasNext();) {
			SpotDevRetvReslt spotDevRetvReslt = iterator.next();

			boolean exist = false;

			if (spotDevRetvReslt == null)
				return null;

            SpotDev sourceDev = spotDevRetvReslt.getFirstSpotDev();
            for (SpotDev spotDev : spotDevs) {

                if (spotDev.getSvcTgtSeq().longValue() == sourceDev.getSvcTgtSeq().longValue() && spotDev.getSpotDevSeq().longValue() == sourceDev.getSpotDevSeq().longValue()) {

                    exist = true;


                    if (genlSetupDataRequired)
                        setGenlSetupDatas(sourceDev, spotDev);

                    if (sclgSetupDataRequired)
                        setSclgSetupDatas(sourceDev, spotDev);

                    if (devDtlRequired)
                        setDevDtlDatas(sourceDev, spotDev);

                    break;
                }
            }

            if (!exist) {
                spotDevs.add(sourceDev);
            }

		}

		log.debug("### Merged Before Database Result : {}", gson.toJson(mergedReslt));

		if (!mergedReslt.getSpotDevs().isEmpty() && !this.realtimeRequired) {
			for (Iterator<SpotDev> devIterator = mergedReslt.getSpotDevs().iterator(); devIterator.hasNext();) {
				SpotDev finalSpotDev = devIterator.next();

				// If size of genlSetupDatas is 0 or size of sclgSetupDatas is 0 then device is offline.

				log.debug("### Size Of GenlSetupDatas of Device {} : {}", finalSpotDev.getSpotDevId(), finalSpotDev.getGenlSetupDatas().size());
				log.debug("### Size Of SclgSetupDatas of Device {} : {}", finalSpotDev.getSpotDevId(), finalSpotDev.getSclgSetupDatas().size());
				if (finalSpotDev.getGenlSetupDatas().size() == 0 || finalSpotDev.getSclgSetupDatas().size() == 0) {
					// Retrieving information from database.

					if (this.genlSetupDataRequired || this.sclgSetupDataRequired) {
						SpotDev spotDevFromDB = deviceService.getDeviceSetup(finalSpotDev.getDevUUID());

						if (this.genlSetupDataRequired && finalSpotDev.getGenlSetupDatas().size() == 0)
							finalSpotDev.setGenlSetupDatas(spotDevFromDB.getGenlSetupDatas());

						if (this.sclgSetupDataRequired && finalSpotDev.getSclgSetupDatas().size() == 0)
							finalSpotDev.setSclgSetupDatas(spotDevFromDB.getSclgSetupDatas());
					}

					if (this.devDtlRequired && finalSpotDev.getSpotDevDtls().size() == 0) {
						SpotDev spotDevDetailFromDB = deviceService.getDeviceDetail(null, finalSpotDev.getSvcTgtSeq(), finalSpotDev.getSpotDevSeq());
						finalSpotDev.setSpotDevDtls(spotDevDetailFromDB.getSpotDevDtls());
					}

					for (Iterator<SpotDevDtl> iterator = finalSpotDev.getSpotDevDtls().iterator(); iterator.hasNext();) {
						SpotDevDtl spotDevDtl = iterator.next();

                        if (spotDevDtl.getAtribNm().equals(SpotDevItemNm.CON_STAT) && spotDevDtl.getAtribVal().equals(SpotDevItemVal.CON_STAT_ONLINE)) {
                            spotDevDtl.setAtribVal(SpotDevItemVal.CON_STAT_INSECURE);
                            break;
                        }
					}
				}
			}
		}

		log.debug("### Merged After Database Result : {}", gson.toJson(mergedReslt));
		log.info("# Response GET : /hcam/devices/spot\n" + JsonUtils.toPrettyJson(mergedReslt));

		return mergedReslt;
	}

	protected void setGenlSetupDatas(SpotDev sourceDev, SpotDev spotDev) {
		if (sourceDev.getGenlSetupDatas().size() > 0) {

			for (GenlSetupData genlSetupData : sourceDev.getGenlSetupDatas()) {

				spotDev.getGenlSetupDatas().add(genlSetupData);
			}


		}
	}

	protected void setSclgSetupDatas(SpotDev sourceDev, SpotDev targetDev) {
		if (sourceDev.getSclgSetupDatas().size() > 0) {
			log.debug("### Setting SclgSetupDatas");
			for (Iterator<SclgSetupData> iterator = sourceDev.getSclgSetupDatas().iterator(); iterator.hasNext();) {
				SclgSetupData sclgSetupData = iterator.next();

				// 감시 스케줄 : 31000004 --> 80010001
				// 녹화 스케줄 : 31000005 --> 80010002
				if (sclgSetupData.getSnsnTagCd().equals("31000004"))
					sclgSetupData.setSnsnTagCd("81000001");
				else if (sclgSetupData.getSnsnTagCd().equals("31000005"))
					sclgSetupData.setSnsnTagCd("81000002");

			}
			targetDev.getSclgSetupDatas().addAll(sourceDev.getSclgSetupDatas());
		}
	}

	protected void setDevDtlDatas(SpotDev sourceDev, SpotDev spotDev) {
		if (sourceDev.getSpotDevDtls().size() > 0) {
			log.debug("### Setting SpotDevDtls");

			log.debug("### Remove result? : {}", this.removeResult);
			for (SpotDevDtl spotDevDtl : sourceDev.getSpotDevDtls()) {


				// Whether removes "result" parameter
				if (this.removeResult) {
					if (spotDevDtl.getAtribNm().equals("result")) {
						continue;
					}
				}

				if (spotDevDtl.getAtribNm().equals("privacy")) {
					// Changing the name of parameter "status" to "privacy"
//					spotDevDtl.setAtribNm("privacy");
					spotDevDtl.setAtribVal((spotDevDtl.getAtribVal().equals("0")) ? "1" : "0");

					// Adding GenlSetupData with key "80000001" and value of privacy
					spotDev.addGenlSetupData(new GenlSetupData("80000001", spotDevDtl.getAtribVal()));
				}
				else if (spotDevDtl.getAtribNm().equals("scheduled")) {
					if (spotDevDtl.getAtribVal().equals("0")) {
						spotDev.addGenlSetupData(new GenlSetupData("80000012", "0"));
						spotDev.addGenlSetupData(new GenlSetupData("80000013", "0"));
					} else if (spotDevDtl.getAtribVal().equals("1")) {
						spotDev.addGenlSetupData(new GenlSetupData("80000012", "0"));
						spotDev.addGenlSetupData(new GenlSetupData("80000013", "1"));
					} else if (spotDevDtl.getAtribVal().equals("2")) {
						spotDev.addGenlSetupData(new GenlSetupData("80000012", "1"));
						spotDev.addGenlSetupData(new GenlSetupData("80000013", "0"));
					} else if (spotDevDtl.getAtribVal().equals("3")) {
						spotDev.addGenlSetupData(new GenlSetupData("80000012", "1"));
						spotDev.addGenlSetupData(new GenlSetupData("80000013", "1"));
					}
				} else {
					if (spotDevDtl.getAtribNm().equals("firmwareVersion")) {
						spotDev.setFrmwrVerNo(spotDevDtl.getAtribVal());
						setFirmwareConfig(spotDev);
					}

					String snsnTagCd = env.getProperty("snsnTagCd." + spotDevDtl.getAtribNm());
					if(StringUtils.isNotBlank(snsnTagCd)) {
						spotDev.addGenlSetupData(new GenlSetupData(snsnTagCd, spotDevDtl.getAtribVal()));
					}
				}

				spotDev.getSpotDevDtls().add(spotDevDtl);
			}
		}
	}

	protected void setFirmwareConfig(SpotDev spotDev) {

		if (initService == null)
			return;

		String currentAvailableFirmwareVersion = initService.getProperty(svcCode, "dev.firm.version");
		String currentFirmwareUpgradeOption = initService.getProperty(svcCode, "dev.firm.upOption");
        log.debug("Firmware Production : {}", currentAvailableFirmwareVersion);

        if (spotDev.isBeta()) {
            final Firmware latestBetaFirmware = IFirmwareService.getLatestBetaFirmware(spotDev);
            if (latestBetaFirmware != null && StringUtils.defaultString(latestBetaFirmware.getVersion()).compareTo(currentAvailableFirmwareVersion) > 0) {
                currentAvailableFirmwareVersion = latestBetaFirmware.getVersion();
                currentFirmwareUpgradeOption = latestBetaFirmware.isMandatory() ? "1" : "2";
            }
        }

		if(StringUtils.defaultString(spotDev.getFrmwrVerNo()).compareTo(currentAvailableFirmwareVersion) >= 0) {
			currentFirmwareUpgradeOption = "0";
		}

		spotDev.addSpotDevDtl("newFrmwrVerNo", currentAvailableFirmwareVersion);
		spotDev.addSpotDevDtl("upgradeOption", currentFirmwareUpgradeOption);
	}

}
