package com.kt.giga.home.openapi.domain;


/**
 *  카메라 설정 정보 클래스
 * 
 * @author 조상현
 *
 */
public class CameraConfig {
	private String 	videSendYn;		// 영상 송출 여부 [0:off , 1:on]
	private String 	resolOptn;		// 해상도 옵션 [1 , 2 , 3]
	private String 	prcvYn;			// 감지 활성화 여부 [Y:on, N:off]
	private String 	prcvMode;		// 감지모드 [1:영상, 2:음향, 3:영상+음향]
	private String 	sdCardSttus;	// SD 카드 장착 상태 [0:미장착 , 1:장착 , 2:오류발생]
	private String 	videRvrtSendYn;	// 영상 역전 송출 여부 [Y:역전 송출(천장설치), N:일반 송출]
	private String 	movingPns;;		// 움직임 감지 PNS 간격 제어
	private String 	ucloudPns;		// ucloud PNS 간격 제어
	private String 	saveMode;		// 저장방식 제어 [0:저장않음, 1:sd, 2:ucloud]
	private String 	mSensitivity;	// 영상 감지 민감도 옵션 [1 , 2 , 3]
	private String 	vSensitivity;	// 음향 변화 감지 민감도 옵션 [1 , 2 , 3]
	private String 	apName;			// 홈 카메라가 현재 연결된 Wi-Fi SSID
	private String 	apPower;		// 홈 카메라가 현재 연결된 Wi-Fi 신호 세기
	
	/**
	 * @return 영상 송출 여부 [0:off , 1:on]
	 */
	public String getVideSendYn() {
		return videSendYn;
	}
	/**
	 * @param 영상 송출 여부 [0:off , 1:on]
	 */
	public void setVideSendYn(String videSendYn) {
		this.videSendYn = videSendYn;
	}
	/**
	 * @return 해상도 옵션 [1 , 2 , 3]
	 */
	public String getResolOptn() {
		return resolOptn;
	}
	/**
	 * @param 해상도 옵션 [1 , 2 , 3]
	 */
	public void setResolOptn(String resolOptn) {
		this.resolOptn = resolOptn;
	}
	/**
	 * @return 감지 활성화 여부 [Y:on, N:off]
	 */
	public String getPrcvYn() {
		return prcvYn;
	}
	/**
	 * @param 감지 활성화 여부 [Y:on, N:off]
	 */
	public void setPrcvYn(String prcvYn) {
		this.prcvYn = prcvYn;
	}
	/**
	 * @return 감지모드 [1:영상, 2:음향, 3:영상+음향]
	 */
	public String getPrcvMode() {
		return prcvMode;
	}
	/**
	 * @param 감지모드 [1:영상, 2:음향, 3:영상+음향]
	 */
	public void setPrcvMode(String prcvMode) {
		this.prcvMode = prcvMode;
	}
	/**
	 * @return SD 카드 장착 상태 [0:미장착 , 1:장착 , 2:오류발생]
	 */
	public String getSdCardSttus() {
		return sdCardSttus;
	}
	/**
	 * @param SD 카드 장착 상태 [0:미장착 , 1:장착 , 2:오류발생]
	 */
	public void setSdCardSttus(String sdCardSttus) {
		this.sdCardSttus = sdCardSttus;
	}
	/**
	 * @return 영상 역전 송출 여부 [Y:역전 송출(천장설치), N:일반 송출]
	 */
	public String getVideRvrtSendYn() {
		return videRvrtSendYn;
	}
	/**
	 * @param 영상 역전 송출 여부 [Y:역전 송출(천장설치), N:일반 송출]
	 */
	public void setVideRvrtSendYn(String videRvrtSendYn) {
		this.videRvrtSendYn = videRvrtSendYn;
	}
	/**
	 * @return 움직임 감지 PNS 간격 제어
	 */
	public String getMovingPns() {
		return movingPns;
	}
	/**
	 * @param 움직임 감지 PNS 간격 제어
	 */
	public void setMovingPns(String movingPns) {
		this.movingPns = movingPns;
	}
	/**
	 * @return ucloud PNS 간격 제어
	 */
	public String getUcloudPns() {
		return ucloudPns;
	}
	/**
	 * @param ucloud PNS 간격 제어
	 */
	public void setUcloudPns(String ucloudPns) {
		this.ucloudPns = ucloudPns;
	}
	/**
	 * @return 저장방식 제어 [0:저장않음, 1:sd, 2:ucloud]
	 */
	public String getSaveMode() {
		return saveMode;
	}
	/**
	 * @param 저장방식 제어 [0:저장않음, 1:sd, 2:ucloud]
	 */
	public void setSaveMode(String saveMode) {
		this.saveMode = saveMode;
	}
	/**
	 * @return 영상 감지 민감도 옵션 [1 , 2 , 3]
	 */
	public String getmSensitivity() {
		return mSensitivity;
	}
	/**
	 * @param 영상 감지 민감도 옵션 [1 , 2 , 3]
	 */
	public void setmSensitivity(String mSensitivity) {
		this.mSensitivity = mSensitivity;
	}
	/**
	 * @return 음향 변화 감지 민감도 옵션 [1 , 2 , 3]
	 */
	public String getvSensitivity() {
		return vSensitivity;
	}
	/**
	 * @param 음향 변화 감지 민감도 옵션 [1 , 2 , 3]
	 */
	public void setvSensitivity(String vSensitivity) {
		this.vSensitivity = vSensitivity;
	}
	/**
	 * @return 홈 카메라가 현재 연결된 Wi-Fi SSID
	 */
	public String getApName() {
		return apName;
	}
	/**
	 * @param 홈 카메라가 현재 연결된 Wi-Fi SSID
	 */
	public void setApName(String apName) {
		this.apName = apName;
	}
	/**
	 * @return 홈 카메라가 현재 연결된 Wi-Fi 신호 세기
	 */
	public String getApPower() {
		return apPower;
	}
	/**
	 * @param 홈 카메라가 현재 연결된 Wi-Fi 신호 세기
	 */
	public void setApPower(String apPower) {
		this.apPower = apPower;
	}
	
	
	
}