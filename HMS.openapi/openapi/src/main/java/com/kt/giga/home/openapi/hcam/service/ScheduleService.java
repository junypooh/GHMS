package com.kt.giga.home.openapi.hcam.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.kt.giga.home.openapi.common.APIEnv;
import com.kt.giga.home.openapi.kpns.service.KPNSService;

/**
 * 스케줄 처리 서비스
 *
 * @author
 *
 */
@Service("HCam.ScheduleService")
public class ScheduleService {

	@Autowired
	private APIEnv env;

	@Autowired
	private DeviceService deviceService;

	@Autowired
	private VoiceService voiceService;
	
	@Autowired
	private KPNSService kPNSService;

	/**
	 * 디바이스 연결 상태 체크 스케줄
	 */
	@Scheduled(fixedDelay=60 * 1000, initialDelay = 20 * 1000)
	public void checkDeviceConnStatus() {

		if(env.isPropertyTrue("schedule.check.hcam")) {
			deviceService.checkDeviceConnStatus(null);
		}
	}

	/**
	 * 펌웨어 업그레이드 상태 체크 스케줄
	 */
	@Scheduled(fixedDelay=60 * 1000, initialDelay = 30 * 1000)
	public void checkUCEMSFirmwareUpgradeStatus() {

		if(env.isPropertyTrue("schedule.check.hcam")) {
			deviceService.checkFirmwareUpgradeStatusFromUCEMS();
		}
	}
	
	/**
	 * 펌웨어 업그레이드 완료 후 연결 상태 체크 스케줄
	 */
	@Scheduled(fixedDelay=30 * 1000, initialDelay = 40 * 1000)
	public void checkUCEMSFirmwareUpgradeComplete() {

		if(env.isPropertyTrue("schedule.check.hcam")) {
			deviceService.checkFirmwareUpgradeComplete();
		}
	}

	/**
	 * 만료된 음성 세션 삭제 스케줄
	 */
	@Scheduled(fixedDelay=60 * 1000, initialDelay = 60 * 1000)
	public void deleteExpiredVoiceSession() {

		if(env.isPropertyTrue("schedule.check.hcam")) {
			voiceService.deleteExpiredVoiceSession();
		}
	}

}
