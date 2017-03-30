package com.kt.giga.home.openapi.hcam.internal;

import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kt.giga.home.openapi.hcam.HCamConstants.SnsnTagCd;
import com.kt.giga.home.openapi.hcam.dao.DeviceDao;
import com.kt.giga.home.openapi.service.ECService;
import com.kt.giga.home.openapi.vo.cnvy.CnvyRow;
import com.kt.giga.home.openapi.vo.cnvy.ItgCnvyData;
import com.kt.giga.home.openapi.vo.cnvy.SpotDevCnvyData;
import com.kt.giga.home.openapi.vo.row.ContlData;

public class RelayCamConnRunnable implements Runnable {

	private final Logger log = LoggerFactory.getLogger(getClass());

	private long svcTgtSeq;

	private long spotDevSeq;

	private String ip;

	private int port;

	private int relayDeviceId;

	private int relayDevicePw;

	private String key;

	private String iv;

	private ECService ecService;

	private DeviceDao deviceDao;

	public void setSvcTgtSeq(long svcTgtSeq) {
		this.svcTgtSeq = svcTgtSeq;
	}

	public void setSpotDevSeq(long spotDevSeq) {
		this.spotDevSeq = spotDevSeq;
	}

	public void setIP(String ip) {
		this.ip = ip;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public void setRelayDeviceId(int relayDeviceId) {
		this.relayDeviceId = relayDeviceId;
	}

	public void setRelayDevicePw(int relayDevicePw) {
		this.relayDevicePw = relayDevicePw;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public void setIV(String iv) {
		this.iv = iv;
	}

	public void setECService(ECService ecService) {
		this.ecService = ecService;
	}

	public void setDeviceDao(DeviceDao deviceDao) {
		this.deviceDao = deviceDao;
	}

	@Override
	public void run() {

		log.debug("### Requesting Camera to Connect to Relay Server.");
		ItgCnvyData itgCnvyData = new ItgCnvyData();

		SpotDevCnvyData spotDevCnvyData = new SpotDevCnvyData();
		spotDevCnvyData.setSvcTgtSeq(svcTgtSeq);
		spotDevCnvyData.setSpotDevSeq(spotDevSeq);

		CnvyRow cnvyRow = new CnvyRow();

		cnvyRow.setTransacId(deviceDao.createTransactionId());

		ContlData contlData = new ContlData();
		contlData.setSnsnTagCd(SnsnTagCd.RTIME_RELAY_SESSION_CONN);
		cnvyRow.addContlData(contlData);

		HashMap<String, Object> rowExtension = cnvyRow.getRowExtension();
		rowExtension.put("relayIp", ip);
		rowExtension.put("relayPort", port);
		rowExtension.put("relayDeviceId", relayDeviceId);
		rowExtension.put("relayDevicePw", relayDevicePw);
		rowExtension.put("secretKey", key);
		rowExtension.put("iV", iv);
		spotDevCnvyData.getCnvyRows().add(cnvyRow);
		itgCnvyData.getSpotDevCnvyDatas().add(spotDevCnvyData);

		ecService.sendRTimeControlToEC(itgCnvyData);

	}

}
