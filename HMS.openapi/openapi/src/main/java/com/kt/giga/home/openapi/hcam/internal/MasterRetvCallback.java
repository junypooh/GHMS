package com.kt.giga.home.openapi.hcam.internal;

import java.util.List;

import com.kt.giga.home.openapi.hcam.HCamConstants.SnsnTagCd;
import com.kt.giga.home.openapi.vo.spotdev.SpotDev;
import com.kt.giga.home.openapi.vo.spotdev.SpotDevRetvReslt;

public class MasterRetvCallback extends MasterCallback<SpotDevRetvReslt> implements RetvCallback<SpotDevRetvReslt> {

	private static final String[] snsnTagCds = new String[] { SnsnTagCd.RETV_GEN_SETUP, SnsnTagCd.RETV_SCH_SETUP };

	@Override
	public SpotDevRetvReslt retv(List<SpotDev> spotDevs, List<SpotDevRetvReslt> spotDevRetvReslts) {
		return (SpotDevRetvReslt) mergeAll(spotDevs, spotDevRetvReslts);
	}

	@Override
	public String[] getSnsnTagCds() {
		return MasterRetvCallback.snsnTagCds;
	}

}
