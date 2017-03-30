package com.kt.smcp.gw.ca.domn;

import com.kt.smcp.gw.ca.comm.GwCode.PltfrmVer;

public abstract class CorePrcssData
{
	/** 플랫폼버전 */
	protected PltfrmVer pltfrmVer = PltfrmVer.VER_01_50;

	abstract public boolean isEmptyData();

	public PltfrmVer getPltfrmVer() {
		return pltfrmVer;
	}

	public void setPltfrmVer(PltfrmVer pltfrmVer) {
		this.pltfrmVer = pltfrmVer;
	}


}
