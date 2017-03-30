package com.kt.giga.home.openapi.hcam.internal;

import java.util.List;

import com.kt.giga.home.openapi.vo.spotdev.SpotDev;
import com.kt.giga.home.openapi.vo.spotdev.SpotDevRetvReslt;

public interface RetvCallback<V> {

	V retv(List<SpotDev> spotDevs, List<SpotDevRetvReslt> spotDevRetvReslts);
	String[] getSnsnTagCds();

}
