package com.kt.giga.home.openapi.hcam.internal;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.kt.giga.home.openapi.hcam.HCamConstants.SnsnTagCd;
import com.kt.giga.home.openapi.vo.spotdev.SpotDev;
import com.kt.giga.home.openapi.vo.spotdev.SpotDevDtl;
import com.kt.giga.home.openapi.vo.spotdev.SpotDevRetvReslt;

public class GenlSetupDataMapCallback extends MasterCallback<Map<String, String>> implements RetvCallback<Map<String, String>> {

	private static final String[] snsnTagCds = new String[] { SnsnTagCd.RETV_GEN_SETUP };

	public GenlSetupDataMapCallback() {
		super();
		this.removeResult = false;
		this.genlSetupDataRequired = false;
		this.sclgSetupDataRequired = false;
		this.devDtlRequired = true;
	}

	@Override
	public Map<String, String> retv(List<SpotDev> spotDevs, List<SpotDevRetvReslt> spotDevRetvReslts) {
		SpotDevRetvReslt spotDevRetvReslt = this.mergeAll(spotDevs, spotDevRetvReslts);

		Map<String, String> genlSetupDataMap = new HashMap<String, String>();
		for (SpotDevDtl spotDevDtl : spotDevRetvReslt.getFirstSpotDev().getSpotDevDtls()) {
			genlSetupDataMap.put(spotDevDtl.getAtribNm(), spotDevDtl.getAtribVal());
		}
		return genlSetupDataMap;
	}

	@Override
	public String[] getSnsnTagCds() {
		return GenlSetupDataMapCallback.snsnTagCds;
	}

}
