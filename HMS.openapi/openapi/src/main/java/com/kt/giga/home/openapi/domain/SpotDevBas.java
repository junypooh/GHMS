package com.kt.giga.home.openapi.domain;

/**
 * 현장장치기본 클래스
 * 
 * @author 조상현
 *
 */
public class SpotDevBas {
	private int 	svc_tgt_seq;			// 서비스대상일련번호	// int
	private int 	spot_dev_seq;			// 현장장치일련번호		// int
	private String 	dev_group_id;			// 장치그룹아이디
	private int 	dev_model_seq;			// 장치모델일련번호		// int
	private String 	spot_dev_id;			// 현장장치아이디
	private String 	dev_nm;					// 장치명
	private String 	use_yn;					// 사용여부
	private String 	tmp_dev_yn;				// 임시장치여부	
	private String 	ipadr;					// ip주소
	private String 	athn_no;				// 인증번호
	private String 	athn_forml_cd;			// 인증방식코드
	private String 	colec_cycl_time;		// 수집주기시간			// int
	private String 	idle_jdgm_base_time;	// 유휴판단기준시간		// int
	private String 	recn_cycl_time;			// 재접속주기시간		// int
	private String 	recn_try_tmscnt;		// 재접속시도횟수		// int
	private String 	prod_seq;				// 상품일련번호
	private String 	reg_seq;				// 등록일련번호
	private String 	frmwr_ver_no;			// 펌웨어버전번호
	private String 	last_mtn_dt;			// 최종동작일시
	private String 	eqp_lo_sbst;			// 설치위치내용
	private String 	trobl_admr_id;			// 장애관리자아이디
	private String 	dev_sttus_cd;			// 장치상태코드
	private String 	latit_val;				// 위도값				// int
	private String 	latit_div_cd;			// 위도구분코드
	private String 	lngit_div_cd;			// 경도구분코드
	private String 	lngit_val;				// 경도값				// int
	private String 	altt;					// 고도					// int
	private String 	rmark;					// 비고
	private String 	del_yn;					// 삭제여부
	private String 	cretr_id;				// 생성자아이디
	private String 	cret_dt;				// 생성일시
	private String 	amdr_id;				// 수정자아이디
	private String 	amd_dt;					// 수정일시
	private String  dev_uu_id;              // 장치uuId
	     
	
	
	//현장장치 확장기본
	private String 	mac;					// Mac주소
	private String 	controlStatus;			// 제어세션 상태 0:비정상, 1:정상, 2:펌웨어업그레이드
	private String 	apName;					// 홈 카메라가 현재 연결된 Wi-Fi SSID
	private String 	apPower;				// 홈 카메라가 현재 연결된 Wi-Fi 신호 세기
	
	//센싱태그
	private String 	status;					// 영상 송출 여부 [0:OFF , 1:ON]
	private String 	resolution;				// 해상도 옵션 [1 , 2 , 3]
	private String 	sdCard;					// SD 카드 장착 상태 [0:미장착 , 1:장착 , 2:오류발생]
	private String 	reverted;				// 영상 역전 송출 여부 [0:일반 송출 , 1:역전 송출(천장설치)]
	private String 	firmVer;				// 설치된 펌웨어 버전
	private String 	newFirmVer;				// 최신 펌웨어 버전
	private String 	firmUpOption;			// 최신 펌웨어 버전 업데이트 옵션 [0:업데이트 불필요 , 1:강제 , 2:선택]
	private String 	privacy;				// 사생활 보호모드 여부 [0:OFF , 1:ON]
	private String 	record;					// 예약녹화 활성화 여부 [0:OFF, 1:ON]
	private String 	detection;				// 감지 활성화 여부 [0:OFF, 1:ON]
	
	//현장장치 스케쥴 설정내역
	private String 	detectionDay;			// 감지 요일, 콤마구분 최대7개 1(일)~7(토)
	private String 	detectionTimeFrom;		// 감지 시작시간 HHmm
	private String 	detectionTimeTo;		// 감지 종료시간 HHmm

	private String 	recordDay;				// 예약녹화 요일, 콤마구분 최대7개 1(일)~7(토)
	private String 	recordTimeFrom;			// 예약녹화 시작시간 HHmm
	private String 	recordTimeTo;			// 예약녹화 종료시간 HHmm

	
	
	public String getDev_uu_id() {
		return dev_uu_id;
	}
	public void setDev_uu_id(String dev_uu_id) {
		this.dev_uu_id = dev_uu_id;
	}
	/**
	 * @return 서비스대상일련번호
	 */
	public int getSvc_tgt_seq() {
		return svc_tgt_seq;
	}
	public void setSvc_tgt_seq(int svc_tgt_seq) {
		this.svc_tgt_seq = svc_tgt_seq;
	}
	/**
	 * @return 현장장치일련번호
	 */
	public int getSpot_dev_seq() {
		return spot_dev_seq;
	}
	public void setSpot_dev_seq(int spot_dev_seq) {
		this.spot_dev_seq = spot_dev_seq;
	}
	/**
	 * @return 장치그룹아이디
	 */
	public String getDev_group_id() {
		return dev_group_id;
	}
	/**
	 * @param 장치그룹아이디
	 */
	public void setDev_group_id(String dev_group_id) {
		this.dev_group_id = dev_group_id;
	}
	
	/**
	 * @return 장치모델일련번호
	 */
	public int getDev_model_seq() {
		return dev_model_seq;
	}
	/**
	 * @param 장치모델일련번호
	 */
	public void setDev_model_seq(int dev_model_seq) {
		this.dev_model_seq = dev_model_seq;
	}
	/**
	 * @return 현장장치아이디
	 */
	public String getSpot_dev_id() {
		return spot_dev_id;
	}
	/**
	 * @param 현장장치아이디
	 */
	public void setSpot_dev_id(String spot_dev_id) {
		this.spot_dev_id = spot_dev_id;
	}
	/**
	 * @return 장치명
	 */
	public String getDev_nm() {
		return dev_nm;
	}
	/**
	 * @param 장치명
	 */
	public void setDev_nm(String dev_nm) {
		this.dev_nm = dev_nm;
	}
	/**
	 * @return 사용여부
	 */
	public String getUse_yn() {
		return use_yn;
	}
	/**
	 * @param 사용여부
	 */
	public void setUse_yn(String use_yn) {
		this.use_yn = use_yn;
	}
	/**
	 * @return 임시장치여부
	 */
	public String getTmp_dev_yn() {
		return tmp_dev_yn;
	}
	/**
	 * @param 임시장치여부
	 */
	public void setTmp_dev_yn(String tmp_dev_yn) {
		this.tmp_dev_yn = tmp_dev_yn;
	}
	/**
	 * @return ip주소
	 */
	public String getIpadr() {
		return ipadr;
	}
	/**
	 * @param ip주소
	 */
	public void setIpadr(String ipadr) {
		this.ipadr = ipadr;
	}
	/**
	 * @return 인증번호
	 */
	public String getAthn_no() {
		return athn_no;
	}
	/**
	 * @param 인증번호
	 */
	public void setAthn_no(String athn_no) {
		this.athn_no = athn_no;
	}
	/**
	 * @return 인증방식코드
	 */
	public String getAthn_forml_cd() {
		return athn_forml_cd;
	}
	/**
	 * @param 인증방식코드
	 */
	public void setAthn_forml_cd(String athn_forml_cd) {
		this.athn_forml_cd = athn_forml_cd;
	}
	/**
	 * @return 수집주기시간
	 */
	public String getColec_cycl_time() {
		return colec_cycl_time;
	}
	/**
	 * @param 수집주기시간
	 */
	public void setColec_cycl_time(String colec_cycl_time) {
		this.colec_cycl_time = colec_cycl_time;
	}
	/**
	 * @return 유휴판단기준시간
	 */
	public String getIdle_jdgm_base_time() {
		return idle_jdgm_base_time;
	}
	/**
	 * @param 유휴판단기준시간
	 */
	public void setIdle_jdgm_base_time(String idle_jdgm_base_time) {
		this.idle_jdgm_base_time = idle_jdgm_base_time;
	}
	/**
	 * @return 재접속주기시간
	 */
	public String getRecn_cycl_time() {
		return recn_cycl_time;
	}
	/**
	 * @param 재접속주기시간
	 */
	public void setRecn_cycl_time(String recn_cycl_time) {
		this.recn_cycl_time = recn_cycl_time;
	}
	/**
	 * @return 재접속시도횟수
	 */
	public String getRecn_try_tmscnt() {
		return recn_try_tmscnt;
	}
	/**
	 * @param 재접속시도횟수
	 */
	public void setRecn_try_tmscnt(String recn_try_tmscnt) {
		this.recn_try_tmscnt = recn_try_tmscnt;
	}
	/**
	 * @return 상품일련번호
	 */
	public String getProd_seq() {
		return prod_seq;
	}
	/**
	 * @param 상품일련번호
	 */
	public void setProd_seq(String prod_seq) {
		this.prod_seq = prod_seq;
	}
	/**
	 * @return 등록일련번호
	 */
	public String getReg_seq() {
		return reg_seq;
	}
	/**
	 * @param 등록일련번호
	 */
	public void setReg_seq(String reg_seq) {
		this.reg_seq = reg_seq;
	}
	/**
	 * @return 펌웨어버전번호
	 */
	public String getFrmwr_ver_no() {
		return frmwr_ver_no;
	}
	/**
	 * @param 펌웨어버전번호
	 */
	public void setFrmwr_ver_no(String frmwr_ver_no) {
		this.frmwr_ver_no = frmwr_ver_no;
	}
	/**
	 * @return 최종동작일시
	 */
	public String getLast_mtn_dt() {
		return last_mtn_dt;
	}
	/**
	 * @param 최종동작일시
	 */
	public void setLast_mtn_dt(String last_mtn_dt) {
		this.last_mtn_dt = last_mtn_dt;
	}
	/**
	 * @return 설치위치내용
	 */
	public String getEqp_lo_sbst() {
		return eqp_lo_sbst;
	}
	/**
	 * @param 설치위치내용
	 */
	public void setEqp_lo_sbst(String eqp_lo_sbst) {
		this.eqp_lo_sbst = eqp_lo_sbst;
	}
	/**
	 * @return 장애관리자아이디
	 */
	public String getTrobl_admr_id() {
		return trobl_admr_id;
	}
	/**
	 * @param 장애관리자아이디
	 */
	public void setTrobl_admr_id(String trobl_admr_id) {
		this.trobl_admr_id = trobl_admr_id;
	}
	/**
	 * @return 장치상태코드
	 */
	public String getDev_sttus_cd() {
		return dev_sttus_cd;
	}
	/**
	 * @param 장치상태코드
	 */
	public void setDev_sttus_cd(String dev_sttus_cd) {
		this.dev_sttus_cd = dev_sttus_cd;
	}
	/**
	 * @return 위도값
	 */
	public String getLatit_val() {
		return latit_val;
	}
	/**
	 * @param 위도값
	 */
	public void setLatit_val(String latit_val) {
		this.latit_val = latit_val;
	}
	/**
	 * @return 위도구분코드
	 */
	public String getLatit_div_cd() {
		return latit_div_cd;
	}
	/**
	 * @param 위도구분코드
	 */
	public void setLatit_div_cd(String latit_div_cd) {
		this.latit_div_cd = latit_div_cd;
	}
	/**
	 * @return 경도구분코드
	 */
	public String getLngit_div_cd() {
		return lngit_div_cd;
	}
	/**
	 * @param 경도구분코드
	 */
	public void setLngit_div_cd(String lngit_div_cd) {
		this.lngit_div_cd = lngit_div_cd;
	}
	/**
	 * @return 경도값
	 */
	public String getLngit_val() {
		return lngit_val;
	}
	/**
	 * @param 경도값
	 */
	public void setLngit_val(String lngit_val) {
		this.lngit_val = lngit_val;
	}
	/**
	 * @return 고도
	 */
	public String getAltt() {
		return altt;
	}
	/**
	 * @param 고도
	 */
	public void setAltt(String altt) {
		this.altt = altt;
	}
	/**
	 * @return 비고
	 */
	public String getRmark() {
		return rmark;
	}
	/**
	 * @param 비고
	 */
	public void setRmark(String rmark) {
		this.rmark = rmark;
	}
	/**
	 * @return 삭제여부
	 */
	public String getDel_yn() {
		return del_yn;
	}
	/**
	 * @param 삭제여부
	 */
	public void setDel_yn(String del_yn) {
		this.del_yn = del_yn;
	}
	/**
	 * @return 생성자아이디
	 */
	public String getCretr_id() {
		return cretr_id;
	}
	/**
	 * @param 생성자아이디
	 */
	public void setCretr_id(String cretr_id) {
		this.cretr_id = cretr_id;
	}
	/**
	 * @return 생성일시
	 */
	public String getCret_dt() {
		return cret_dt;
	}
	/**
	 * @param 생성일시
	 */
	public void setCret_dt(String cret_dt) {
		this.cret_dt = cret_dt;
	}
	/**
	 * @return 수정자아이디
	 */
	public String getAmdr_id() {
		return amdr_id;
	}
	/**
	 * @param 수정자아이디
	 */
	public void setAmdr_id(String amdr_id) {
		this.amdr_id = amdr_id;
	}
	/**
	 * @return 수정일시
	 */
	public String getAmd_dt() {
		return amd_dt;
	}
	/**
	 * @param 수정일시
	 */
	public void setAmd_dt(String amd_dt) {
		this.amd_dt = amd_dt;
	}
	
//------------------------ 현장장치 확장기본 ------------------------
	/**
	 * @return Mac주소
	 */
	public String getMac() {
		return mac;
	}
	/**
	 * @param Mac주소
	 */
	public void setMac(String mac) {
		this.mac = mac;
	}
	/**
	 * @return 제어세션 상태 0:비정상, 1:정상, 2:펌웨어업그레이드
	 */
	public String getControlStatus() {
		return controlStatus;
	}
	/**
	 * @param 제어세션 상태 0:비정상, 1:정상, 2:펌웨어업그레이드
	 */
	public void setControlStatus(String controlStatus) {
		this.controlStatus = controlStatus;
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

	
	//------------------------ 현장장치 확장기본 ------------------------

	
	/**
	 * @return 영상 송출 여부 [0:off , 1:on]
	 */
	public String getStatus() {
		return status;
	}
	/**
	 * @param 영상 송출 여부 [0:off , 1:on]
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	/**
	 * @return 해상도 옵션 [1 , 2 , 3]
	 */
	public String getResolution() {
		return resolution;
	}
	/**
	 * @param 해상도 옵션 [1 , 2 , 3]
	 */
	public void setResolution(String resolution) {
		this.resolution = resolution;
	}
	/**
	 * @return SD 카드 장착 상태 [0:미장착 , 1:장착 , 2:오류발생]
	 */
	public String getSdCard() {
		return sdCard;
	}
	/**
	 * @param SD 카드 장착 상태 [0:미장착 , 1:장착 , 2:오류발생]
	 */
	public void setSdCard(String sdCard) {
		this.sdCard = sdCard;
	}
	/**
	 * @return 영상 역전 송출 여부 [0:일반 송출 , 1:역전 송출(천장설치)]
	 */
	public String getReverted() {
		return reverted;
	}
	/**
	 * @param 영상 역전 송출 여부 [0:일반 송출 , 1:역전 송출(천장설치)]
	 */
	public void setReverted(String reverted) {
		this.reverted = reverted;
	}
	/**
	 * @return 설치된 펌웨어 버전
	 */
	public String getFirmVer() {
		return firmVer;
	}
	/**
	 * @param 설치된 펌웨어 버전
	 */
	public void setFirmVer(String firmVer) {
		this.firmVer = firmVer;
	}
	/**
	 * @return 최신 펌웨어 버전
	 */
	public String getNewFirmVer() {
		return newFirmVer;
	}
	/**
	 * @param 최신 펌웨어 버전
	 */
	public void setNewFirmVer(String newFirmVer) {
		this.newFirmVer = newFirmVer;
	}
	/**
	 * @return 최신 펌웨어 버전 업데이트 옵션 [0:업데이트 불필요 , 1:강제 , 2:선택]
	 */
	public String getFirmUpOption() {
		return firmUpOption;
	}
	/**
	 * @param 최신 펌웨어 버전 업데이트 옵션 [0:업데이트 불필요 , 1:강제 , 2:선택]
	 */
	public void setFirmUpOption(String firmUpOption) {
		this.firmUpOption = firmUpOption;
	}
	/**
	 * @return 사생활 보호모드 여부 [0:off , 1:on]
	 */
	public String getPrivacy() {
		return privacy;
	}
	/**
	 * @param 사생활 보호모드 여부 [0:off , 1:on]
	 */
	public void setPrivacy(String privacy) {
		this.privacy = privacy;
	}
	/**
	 * @return 예약녹화 활성화 여부 [0:OFF, 1:ON]
	 */
	public String getRecord() {
		return record;
	}
	/**
	 * @param 예약녹화 활성화 여부 [0:OFF, 1:ON]
	 */
	public void setRecord(String record) {
		this.record = record;
	}
	/**
	 * @return 감지 활성화 여부 [0:OFF, 1:ON]
	 */
	public String getDetection() {
		return detection;
	}
	/**
	 * @param 감지 활성화 여부 [0:OFF, 1:ON]
	 */
	public void setDetection(String detection) {
		this.detection = detection;
	}

}
