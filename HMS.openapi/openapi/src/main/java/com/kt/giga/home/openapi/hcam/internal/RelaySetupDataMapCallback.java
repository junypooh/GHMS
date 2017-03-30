package com.kt.giga.home.openapi.hcam.internal;

import java.util.Map;

public class RelaySetupDataMapCallback extends GenlSetupDataMapCallback implements RetvCallback<Map<String, String>> {

	public RelaySetupDataMapCallback() {
		this.removeResult = false;
		this.devDtlRequired = true;
	}

}
