package com.kt.giga.home.openapi.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import javax.xml.ws.http.HTTPException;

import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.kt.giga.home.openapi.common.APIEnv;
import com.kt.giga.home.openapi.dao.HomeProvisionDao;
import com.kt.giga.home.openapi.domain.HomeProvision;
import com.kt.giga.home.openapi.domain.SpotDevBas;
import com.kt.giga.home.openapi.domain.SpotDevExpnsnBas;
import com.kt.giga.home.openapi.domain.SvcTgtBas;
import com.kt.giga.home.openapi.hcam.HCamConstants.SnsnTagCd;
import com.kt.giga.home.openapi.hcam.HCamConstants.SpotDevItemNm;
import com.kt.giga.home.openapi.hcam.service.DeviceService;
import com.kt.giga.home.openapi.hcam.service.KTInfraService;
import com.kt.giga.home.openapi.vo.spotdev.query.SpotDevBasVO;
import com.kt.giga.home.openapi.vo.spotdev.query.SpotDevBasVO.SpotDevDtlVO;
import com.kt.giga.home.openapi.vo.spotdev.query.SpotDevBasQueryVO;
import com.kt.giga.home.openapi.vo.row.GenlSetupData;
import com.kt.giga.home.openapi.vo.row.SclgSetupData;
import com.kt.giga.home.openapi.vo.row.SclgData;

@Service
public class HomeProvisionService {
	
	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private HomeProvisionDao homeProvisionDao;

	@Autowired
	private APIEnv apiEnv;

	@Autowired
	private ECService ecService;
	
	@Autowired
	private DeviceService deviceService;
	
	@Autowired
	private KTInfraService ktService;
	
	public static final String EC_RSLT_SUCCESS = "100";							// 정상처리
	public static final String EC_RSLT_REDUNDANT_PROCESSING_SUCCESS	= "110";	// 정상처리 되었으나 처리시 중복데이터가 존재하여 삭제처리함.
	public static final String EC_RSLT_CACHE_NOTI_FAIL = "120";					// 정상 처리후 Cache전파 전체 실패
	public static final String EC_RSLT_CACHE_NOTI_PART_FAIL	= "121";			// 정상 처리후 Cache전파 일부 실패
	public static final String EC_RSLT_FAIL	= "200";							// 실패
	public static final String EC_RSLT_NO_REQUIRED_VALUES = "210";				// 필수 요청 조건 누락
	public static final String EC_RSLT_NO_DATA = "220";							// 해당 조건에 맞는 현장장치가 없음.
	
	public static final String RESULT_SUCCESS = "1";							// 프로비저닝 연동 성공
	public static final String RESULT_FAIL = "2";								// 프로비저닝 연동 실패
	
	public static final String UCLOUD_OP_INACTIVE = "1";						// 유클라우드 계정 일시 중지
	public static final String UCLOUD_OP_ACTIVE = "2";							// 유클라우드 계정 사용
	public static final String UCLOUD_OP_DELETE = "3";							// 유클라우드 계정 삭제(30일 후 완전 삭제)
	
	public static final String UCLOUD_RESULT_SUCCESS = "0";						// 유클라우드 연동 성공
	
	public static final String ROLLBACK_NONE = "0000";							// 롤백이 아닌 경우
	public static final String ROLLBACK_USE = "0002";							// 정상이용
	public static final String ROLLBACK_STOP = "0003";							// 이용정지
	public static final String ROLLBACK_EXPIRE = "0004";						// 해지
	public static final String ROLLBACK_DELETE = "0005";						// 삭제(신규 명령에 대한 롤백)

	@Transactional
	public Map<String, Object> setProvision(HomeProvision home) throws Exception {
		String cctvCode = home.getCctvCode();
		
		Map<String, Object> result = new HashMap<String, Object>();
		
		try {
			// 서비스 청약 정보 조회
			SvcTgtBas svcTgtBas = homeProvisionDao.getSvcTgtBasBySvcContId(home.getSaId());
			
			// 장치 정보 조회
			SpotDevBas reqSpotDevBas = new SpotDevBas();
			SpotDevBas spotDevBas = null;
			if(svcTgtBas != null) {
				reqSpotDevBas.setSvc_tgt_seq(svcTgtBas.getSvc_tgt_seq());
				reqSpotDevBas.setSpot_dev_id(home.getCctvSaid());
				spotDevBas = homeProvisionDao.getSpotDevBasBySpotDevId(reqSpotDevBas);
			}
			
			if(cctvCode.equals("M011")) { // 신규 등록				
				int svcTgtSeq = 0;
						
				if(svcTgtBas == null) { // 신규 청약 데이터가 존재 하지 않을시 
					svcTgtSeq = homeProvisionDao.getSvcTgtSeq();
					
					// OpenAPI 서비스 데이터 등록
					this.insertSvcTgtBas(home, svcTgtSeq);
				} else {
					svcTgtSeq = svcTgtBas.getSvc_tgt_seq();
					
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("svc_tgt_seq", svcTgtSeq);
					map.put("svc_tgt_id", home.getCustid());
					map.put("svc_tgt_nm", home.getCustName());
					map.put("mbr_seq", "");
					map.put("oprt_sttus_cd", "0001");
					map.put("use_yn", "Y");
					map.put("del_yn", "N");
						
					// OpenAPI 서비스 데이터 변경
					this.updateSvcTgtBas(map);
				}
				
				// 삭제되지 않은 목록 조회
				List<SpotDevBas> list = homeProvisionDao.getSpotDevBasListByUse(svcTgtSeq);
				
				String rpyCode = EC_RSLT_SUCCESS;
				if(spotDevBas == null) { // 기존 장치가 없는 경우
					int spotDevSeq = homeProvisionDao.getSpotDevSeq(svcTgtSeq);
					String devUUID = UUID.randomUUID().toString();
					
					// OpenAPI 장치 데이터 등록
					this.insertSpotDevBas(home, svcTgtSeq, spotDevSeq, devUUID);
					
					// OpenAPI 장치 확장 데이터 등록
					this.insertOpenAPIExpnsnBas(home, svcTgtSeq, spotDevSeq);
					
					// OpenAPI 장치 설정 기본값 등록
					this.insertOpenAPIDevSetup(svcTgtSeq, spotDevSeq);

					// 삭제할 장치가 있다면 기존 장치 삭제
					this.deleteSpotDevBas(list, spotDevSeq);
										
					// EC 장치 등록
					rpyCode = this.insertECDev(home, svcTgtSeq, spotDevSeq, devUUID);
					
				} else { // 기존 장치가 존재하는 경우
					int spotDevSeq = spotDevBas.getSpot_dev_seq();
					
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("svc_tgt_seq", svcTgtSeq);
					map.put("spot_dev_seq", spotDevSeq);
					map.put("athn_no", home.getCctvSecret());
					map.put("eqp_lo_sbst", home.getCctvLoc());
					map.put("dev_sttus_cd", "00");
					map.put("use_yn", "Y");
					map.put("del_yn", "N");
					
					map.put("cctv_mac", home.getCctvMac());
					map.put("cctv_serial", home.getCctvSerial());
					map.put("cctv_model_cd", home.getCctvModelCd());
					map.put("cctv_model_name", home.getCctvModelName());
						
					// OpenAPI 장치 데이터 변경
					this.updateSpotDevBas(map);
						
					// OpenAPI 장치 확장 데이터 변경
					this.updateOpenAPIExpnsnBas(map);
					
					// 삭제할 장치가 있다면 기존 장치 삭제
					this.deleteSpotDevBas(list, spotDevSeq);
											
					// EC 장치 변경
					if("Y".equals(spotDevBas.getDel_yn())) {
						this.insertECDev(home, svcTgtSeq, spotDevSeq, spotDevBas.getDev_uu_id());
					}
					rpyCode = this.updateECDev(spotDevBas, map);
				}				
				
				// EC 등록 결과에 따른 롤백 처리
				if(rpyCode.equals(EC_RSLT_SUCCESS) || rpyCode.equals(EC_RSLT_REDUNDANT_PROCESSING_SUCCESS)
						|| rpyCode.equals(EC_RSLT_CACHE_NOTI_FAIL) || rpyCode.equals(EC_RSLT_CACHE_NOTI_PART_FAIL)) {
					result.put("resultCode", RESULT_SUCCESS);
				} else {
					TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
					result.put("resultCode", RESULT_FAIL);
				}
				
			} else if(cctvCode.equals("M020")) { // 이용중지
				if(spotDevBas == null) {
					throw new HTTPException(HttpStatus.SC_NOT_FOUND);
				}
				
				int svcTgtSeq = svcTgtBas.getSvc_tgt_seq();
				int spotDevSeq = spotDevBas.getSpot_dev_seq();
				
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("svc_tgt_seq", svcTgtSeq);
				map.put("spot_dev_seq", spotDevSeq);
				
				map.put("oprt_sttus_cd", "0002");
				map.put("dev_sttus_cd", "02");
				map.put("use_yn", "N");
				map.put("del_yn", "N");
				
				// 삭제되지 않은 목록 조회
				List<SpotDevBas> list = homeProvisionDao.getSpotDevBasListByUse(svcTgtSeq);
								
				// OpenAPI 서비스 데이터 수정 
				this.updateSvcTgtBas(map);
				
				// OpenAPI 장치 데이터 수정 
				this.updateSpotDevBas(map);
				
				// 삭제할 장치가 있다면 기존 장치 삭제
				this.deleteSpotDevBas(list, spotDevSeq);
				
				// EC 장치 변경
				if("Y".equals(spotDevBas.getDel_yn())) {
					this.insertECDev(home, svcTgtSeq, spotDevSeq, spotDevBas.getDev_uu_id());
				}
				String rpyCode = this.updateECDev(spotDevBas, map);

				// EC 등록 결과에 따른 롤백 처리
				if(rpyCode.equals(EC_RSLT_SUCCESS) || rpyCode.equals(EC_RSLT_REDUNDANT_PROCESSING_SUCCESS)
						|| rpyCode.equals(EC_RSLT_CACHE_NOTI_FAIL) || rpyCode.equals(EC_RSLT_CACHE_NOTI_PART_FAIL)) {
					result.put("resultCode", RESULT_SUCCESS);
				} else {
					TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
					result.put("resultCode", RESULT_FAIL);
				}
				
				// 유클라우드 계정 상태 변경 (해지에서 해지취소되는 경우 대비)
				if(RESULT_SUCCESS.equals(result.get("resultCode"))) {
					int mbrSeq = NumberUtils.toInt(svcTgtBas.getMbr_seq());
					setUcloudAccount(svcTgtSeq, svcTgtBas.getSvc_cont_id(), mbrSeq, UCLOUD_OP_ACTIVE);
				}
				
			} else if(cctvCode.equals("M030")) { // 이용부활
				if(spotDevBas == null) {
					throw new HTTPException(HttpStatus.SC_NOT_FOUND);
				}
				
				int svcTgtSeq = svcTgtBas.getSvc_tgt_seq();
				int spotDevSeq = spotDevBas.getSpot_dev_seq();
				
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("svc_tgt_seq", svcTgtSeq);
				map.put("spot_dev_seq", spotDevSeq);
				
				map.put("oprt_sttus_cd", "0001");
				map.put("dev_sttus_cd", "01");
				map.put("use_yn", "Y");
				map.put("del_yn", "N");
				
				// 삭제되지 않은 목록 조회
				List<SpotDevBas> list = homeProvisionDao.getSpotDevBasListByUse(svcTgtSeq);
				
				// OpenAPI 서비스 데이터 수정 
				this.updateSvcTgtBas(map);
				
				// OpenAPI 장치 데이터 수정 
				this.updateSpotDevBas(map);
				
				// 삭제할 장치가 있다면 기존 장치 삭제
				this.deleteSpotDevBas(list, spotDevSeq);
				
				// EC 장치 변경
				if("Y".equals(spotDevBas.getDel_yn())) {
					this.insertECDev(home, svcTgtSeq, spotDevSeq, spotDevBas.getDev_uu_id());
				}
				String rpyCode = this.updateECDev(spotDevBas, map);

				// EC 등록 결과에 따른 롤백 처리
				if(rpyCode.equals(EC_RSLT_SUCCESS) || rpyCode.equals(EC_RSLT_REDUNDANT_PROCESSING_SUCCESS)
						|| rpyCode.equals(EC_RSLT_CACHE_NOTI_FAIL) || rpyCode.equals(EC_RSLT_CACHE_NOTI_PART_FAIL)) {
					result.put("resultCode", RESULT_SUCCESS);
				} else {
					TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
					result.put("resultCode", RESULT_FAIL);
				}
				
				// 유클라우드 계정 상태 변경 (해지에서 해지취소되는 경우 대비)
				if(RESULT_SUCCESS.equals(result.get("resultCode"))) {
					int mbrSeq = NumberUtils.toInt(svcTgtBas.getMbr_seq());
					setUcloudAccount(svcTgtSeq, svcTgtBas.getSvc_cont_id(), mbrSeq, UCLOUD_OP_ACTIVE);
				}
				
			} else if(cctvCode.equals("M050")) { // 해지
				if(spotDevBas == null) {
					throw new HTTPException(HttpStatus.SC_NOT_FOUND);
				}
				
				int svcTgtSeq = svcTgtBas.getSvc_tgt_seq();
				int spotDevSeq = spotDevBas.getSpot_dev_seq();
				
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("svc_tgt_seq", svcTgtSeq);
				map.put("spot_dev_seq", spotDevSeq);
				
				map.put("oprt_sttus_cd", "0003");
				map.put("dev_sttus_cd", "02");
				map.put("use_yn", "N");
				map.put("del_yn", "Y");
				
				// 삭제되지 않은 목록 조회
				List<SpotDevBas> list = homeProvisionDao.getSpotDevBasListByUse(svcTgtSeq);
								
				// OpenAPI 서비스 데이터 수정 
				this.updateSvcTgtBas(map);
				
				// OpenAPI 장치 데이터 수정 
				this.updateSpotDevBas(map);
				
				// 삭제할 장치가 있다면 기존 장치 삭제
				this.deleteSpotDevBas(list, spotDevSeq);
				
				// EC 장치 삭제
				String rpyCode = this.deleteECDev(svcTgtSeq, spotDevSeq);

				// EC 등록 결과에 따른 롤백 처리
				if(rpyCode.equals(EC_RSLT_SUCCESS) || rpyCode.equals(EC_RSLT_REDUNDANT_PROCESSING_SUCCESS)
						|| rpyCode.equals(EC_RSLT_CACHE_NOTI_FAIL) || rpyCode.equals(EC_RSLT_CACHE_NOTI_PART_FAIL)) {
					result.put("resultCode", RESULT_SUCCESS);
				} else {
					TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
					result.put("resultCode", RESULT_FAIL);
				}
				
				// 유클라우드 계정 상태 변경
				if(RESULT_SUCCESS.equals(result.get("resultCode"))) {
					int mbrSeq = NumberUtils.toInt(svcTgtBas.getMbr_seq());
					setUcloudAccount(svcTgtSeq, svcTgtBas.getSvc_cont_id(), mbrSeq, UCLOUD_OP_DELETE);
				}
				
			} else if(cctvCode.equals("M021") || cctvCode.equals("M031") || cctvCode.equals("M040")) { // 정보 변경
				int svcTgtSeq = svcTgtBas.getSvc_tgt_seq();
				int spotDevSeq = spotDevBas == null ? 0 : spotDevBas.getSpot_dev_seq();
				
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("svc_tgt_seq", svcTgtSeq);
				map.put("spot_dev_seq", spotDevSeq);
				map.put("svc_tgt_id", home.getCustid());
				map.put("svc_tgt_nm", home.getCustName());
				if(!svcTgtBas.getSvc_tgt_id().equals(home.getCustid())) { // 명의 변경 처리
					map.put("mbr_seq", "");
				}
				map.put("spot_dev_id", home.getCctvSaid());
				map.put("athn_no", home.getCctvSecret());
				map.put("eqp_lo_sbst", home.getCctvLoc());
				map.put("cctv_mac", home.getCctvMac());
				map.put("cctv_serial", home.getCctvSerial());
				map.put("cctv_model_cd", home.getCctvModelCd());
				map.put("cctv_model_name", home.getCctvModelName());
				
				if(cctvCode.equals("M021")) { // 이용중지
					map.put("oprt_sttus_cd", "0002");
					map.put("dev_sttus_cd", "02");
					map.put("use_yn", "N");
					map.put("del_yn", "N");
				} else if(cctvCode.equals("M031")) { // 이용부활
					map.put("oprt_sttus_cd", "0001");
					map.put("dev_sttus_cd", "01");
					map.put("use_yn", "Y");
					map.put("del_yn", "N");
				}
				
				// 삭제되지 않은 목록 조회
				List<SpotDevBas> list = homeProvisionDao.getSpotDevBasListByUse(svcTgtSeq);
				
				String rpyCode = EC_RSLT_SUCCESS;
				if(spotDevBas == null) { // 기존 장치가 없는 경우(기기변경)
					spotDevSeq = homeProvisionDao.getSpotDevSeq(svcTgtSeq);
					String devUUID = UUID.randomUUID().toString();
					
					// OpenAPI 장치 데이터 등록
					this.insertSpotDevBas(home, svcTgtSeq, spotDevSeq, devUUID);
					
					// OpenAPI 장치 확장 데이터 등록
					this.insertOpenAPIExpnsnBas(home, svcTgtSeq, spotDevSeq);
					
					// OpenAPI 장치 설정 기본값 등록
					this.insertOpenAPIDevSetup(svcTgtSeq, spotDevSeq);
					
					// 삭제할 장치가 있다면 기존 장치 삭제
					this.deleteSpotDevBas(list, spotDevSeq);
					
					// EC 장치 등록
					rpyCode = this.insertECDev(home, svcTgtSeq, spotDevSeq, devUUID);
					
				} else {
					// OpenAPI 서비스 데이터 변경 
					this.updateSvcTgtBas(map);
					
					// OpenAPI 장치 데이터 변경
					this.updateSpotDevBas(map);
						
					// OpenAPI 장치 확장 데이터 변경
					this.updateOpenAPIExpnsnBas(map);
					
					// 삭제할 장치가 있다면 기존 장치 삭제
					this.deleteSpotDevBas(list, spotDevSeq);
					
					// EC 장치 변경
					if("Y".equals(spotDevBas.getDel_yn())) {
						this.insertECDev(home, svcTgtSeq, spotDevSeq, spotDevBas.getDev_uu_id());
					}
					rpyCode = this.updateECDev(spotDevBas, map);
				}

				// EC 등록 결과에 따른 롤백 처리
				if(rpyCode.equals(EC_RSLT_SUCCESS) || rpyCode.equals(EC_RSLT_REDUNDANT_PROCESSING_SUCCESS)
						|| rpyCode.equals(EC_RSLT_CACHE_NOTI_FAIL) || rpyCode.equals(EC_RSLT_CACHE_NOTI_PART_FAIL)) {
					result.put("resultCode", RESULT_SUCCESS);
				} else {
					TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
					result.put("resultCode", RESULT_FAIL);
				}
				
				// 유클라우드 계정 상태 변경 (해지에서 해지취소되는 경우 대비)
				if(RESULT_SUCCESS.equals(result.get("resultCode"))) {
					int mbrSeq = NumberUtils.toInt(svcTgtBas.getMbr_seq());
					setUcloudAccount(svcTgtSeq, svcTgtBas.getSvc_cont_id(), mbrSeq, UCLOUD_OP_ACTIVE);
				}
				
			} else if(cctvCode.equals("M070")) { // 원부연동, 롤백
				int svcTgtSeq = 0;
				String rpyCode = EC_RSLT_SUCCESS;
				
				if(svcTgtBas == null) { // 신규 청약 데이터가 존재 하지 않을시 
					svcTgtSeq = homeProvisionDao.getSvcTgtSeq();
					
					// OpenAPI 서비스 데이터 등록
					this.insertSvcTgtBas(home, svcTgtSeq);
				} else {
					svcTgtSeq = svcTgtBas.getSvc_tgt_seq();
				}
				
				// 삭제되지 않은 목록 조회
				List<SpotDevBas> list = homeProvisionDao.getSpotDevBasListByUse(svcTgtSeq);
				
				if(spotDevBas == null) { // 기존 장치가 없는 경우
					int spotDevSeq = homeProvisionDao.getSpotDevSeq(svcTgtSeq);
					String devUUID = UUID.randomUUID().toString();
					
					// OpenAPI 장치 데이터 등록
					this.insertSpotDevBas(home, svcTgtSeq, spotDevSeq, devUUID);
					
					// OpenAPI 장치 확장 데이터 등록
					this.insertOpenAPIExpnsnBas(home, svcTgtSeq, spotDevSeq);
					
					// OpenAPI 장치 설정 기본값 등록
					this.insertOpenAPIDevSetup(svcTgtSeq, spotDevSeq);

					// 삭제할 장치가 있다면 기존 장치 삭제
					this.deleteSpotDevBas(list, spotDevSeq);
										
					// EC 장치 등록
					rpyCode = this.insertECDev(home, svcTgtSeq, spotDevSeq, devUUID);
					
					// OpenAPI 서비스 조회
					svcTgtBas = homeProvisionDao.getSvcTgtBasBySvcContId(home.getSaId());
					
				} else { // 기존 장치가 존재하는 경우
					int spotDevSeq = spotDevBas.getSpot_dev_seq();
					
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("svc_tgt_seq", svcTgtSeq);
					map.put("spot_dev_seq", spotDevSeq);
					map.put("svc_tgt_id", home.getCustid());
					map.put("svc_tgt_nm", home.getCustName());
					if(!svcTgtBas.getSvc_tgt_id().equals(home.getCustid())) { // 명의 변경 처리
						map.put("mbr_seq", "");
					}
					map.put("spot_dev_id", home.getCctvSaid());
					map.put("athn_no", home.getCctvSecret());
					map.put("eqp_lo_sbst", home.getCctvLoc());
					map.put("cctv_mac", home.getCctvMac());
					map.put("cctv_serial", home.getCctvSerial());
					map.put("cctv_model_cd", home.getCctvModelCd());
					map.put("cctv_model_name", home.getCctvModelName());
					
					if(home.getRollbackState().equals(ROLLBACK_USE)) {
						map.put("oprt_sttus_cd", "0001");
						map.put("del_yn", "N");
						map.put("dev_sttus_cd", "01");
						map.put("use_yn", "Y");
						
					} else if(home.getRollbackState().equals(ROLLBACK_STOP)) {
						map.put("oprt_sttus_cd", "0002");
						map.put("del_yn", "N");
						map.put("dev_sttus_cd", "02");
						map.put("use_yn", "N");
						
					} else if(home.getRollbackState().equals(ROLLBACK_EXPIRE)) {
						map.put("oprt_sttus_cd", "0003");
						map.put("del_yn", "Y");
						map.put("dev_sttus_cd", "02");
						map.put("use_yn", "N");
						
					} else if(home.getRollbackState().equals(ROLLBACK_DELETE)) {
						map.put("oprt_sttus_cd", "0003");
						map.put("del_yn", "Y");
						map.put("dev_sttus_cd", "02");
						map.put("use_yn", "N");
					}
					
					// OpenAPI 서비스 데이터 변경 
					this.updateSvcTgtBas(map);
					
					// OpenAPI 장치 데이터 변경
					this.updateSpotDevBas(map);
						
					// OpenAPI 장치 확장 데이터 변경
					this.updateOpenAPIExpnsnBas(map);
					
					// 삭제할 장치가 있다면 기존 장치 삭제
					this.deleteSpotDevBas(list, spotDevSeq);
					
					// EC 장치 변경
					if(home.getRollbackState().equals(ROLLBACK_DELETE)) {
						rpyCode = this.deleteECDev(svcTgtSeq, spotDevSeq);
						
					} else {
						if("Y".equals(svcTgtBas.getDel_yn())) {
							this.insertECDev(home, svcTgtSeq, spotDevSeq, spotDevBas.getDev_uu_id());
						}
						rpyCode = this.updateECDev(spotDevBas, map);
					}				
				}				
				
				// EC 등록 결과에 따른 롤백 처리
				if(rpyCode.equals(EC_RSLT_SUCCESS) || rpyCode.equals(EC_RSLT_REDUNDANT_PROCESSING_SUCCESS)
						|| rpyCode.equals(EC_RSLT_CACHE_NOTI_FAIL) || rpyCode.equals(EC_RSLT_CACHE_NOTI_PART_FAIL)) {
					result.put("resultCode", RESULT_SUCCESS);
				} else {
					TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
					result.put("resultCode", RESULT_FAIL);
				}
				
				// 유클라우드 계정 상태 변경
				if(RESULT_SUCCESS.equals(result.get("resultCode"))) {					
					String resultCode = UCLOUD_RESULT_SUCCESS;
					int mbrSeq = NumberUtils.toInt(svcTgtBas.getMbr_seq());
					if(home.getRollbackState().equals(ROLLBACK_EXPIRE)) {
						setUcloudAccount(svcTgtSeq, svcTgtBas.getSvc_cont_id(), mbrSeq, UCLOUD_OP_DELETE);
					} else {
						setUcloudAccount(svcTgtSeq, svcTgtBas.getSvc_cont_id(), mbrSeq, UCLOUD_OP_ACTIVE);
					}
				}
			}
			
		} catch (HTTPException e) {
			throw e;
			
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			throw e;
		}
		
		return result;
	}
	
	/*
	 * OpenAPI 서비스 생성
	 */
	public void insertSvcTgtBas(HomeProvision home, int svcTgtSeq) {
		SvcTgtBas stb = new SvcTgtBas();
		
		stb.setDstr_cd("001");
		stb.setSvc_theme_cd("HIT");
		stb.setUnit_svc_cd("001");
		stb.setSvc_tgt_seq(svcTgtSeq);
		stb.setSvc_tgt_id(home.getCustid());   		// cust아이디
		stb.setSvc_tgt_nm(home.getCustName()); 		// cust이름
		stb.setSvc_cont_id(home.getSaId());			// 청약아이디
		stb.setOprt_sttus_cd("0001");          		// 운영상태코드 설정
		stb.setDel_yn("N");                    		// 삭제여부 설정de
		
		homeProvisionDao.insertSvcTgtBas(stb);
	}
	
	/*
	 * OpenAPI 서비스 변경
	 */
	public void updateSvcTgtBas(Map<String, Object> map) {
		homeProvisionDao.updateSvcTgtBas(map);
	}
	
	/*
	 * OpenAPI 장치 생성
	 */
	public void insertSpotDevBas(HomeProvision home, int svcTgtSeq, int spotDevSeq, String devUUID) {
		SpotDevBas spotBas = new SpotDevBas();
		
		String devNm = "";
		int devModelSeq =  homeProvisionDao.getDevModelSeq(home.getCctvModelCd());
		
		// 카메라 변경 인 경우는 기존 카메라 이름 부여
		if(spotDevSeq > 1) {
			SpotDevBas oldSpotBas = new SpotDevBas();
			oldSpotBas.setSvc_tgt_seq(svcTgtSeq);
			oldSpotBas.setSpot_dev_seq(spotDevSeq - 1);
			
			SpotDevBas oldSpotDevBas = homeProvisionDao.getSpotDevBas(oldSpotBas);
			devNm = StringUtils.defaultString(oldSpotDevBas.getDev_nm());
		}
				
		spotBas.setSvc_tgt_seq(svcTgtSeq);                
		spotBas.setSpot_dev_seq(spotDevSeq);
		spotBas.setDev_model_seq(devModelSeq);             
		spotBas.setDev_uu_id(devUUID); 				// 장치 UUID
		spotBas.setSpot_dev_id(home.getCctvSaid());	// 현장장치아이디
		spotBas.setAthn_no(home.getCctvSecret());	// 인증번호
		spotBas.setEqp_lo_sbst(home.getCctvLoc());	// 설치위치내용
		spotBas.setDev_nm(devNm);					// 장치명
		spotBas.setUse_yn("Y");						// 사용여부
		spotBas.setDev_sttus_cd("00");				// 장치 상태 - 인증 대기
				
		homeProvisionDao.insertSpotDevBas(spotBas);
	}
	
	/*
	 * OpenAPI 장치 변경
	 */
	public void updateSpotDevBas(Map<String, Object> map) {
		homeProvisionDao.updateSpotDevBas(map);
	}
	
	/*
	 * OpenAPI 장치 삭제
	 */
	public void deleteSpotDevBas(List<SpotDevBas> list, int spotDevSeq) {
		for(SpotDevBas spotDevBas : list) {
			if(spotDevBas.getSpot_dev_seq() != spotDevSeq) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("svc_tgt_seq", spotDevBas.getSvc_tgt_seq());
				map.put("spot_dev_seq", spotDevBas.getSpot_dev_seq());
				map.put("oprt_sttus_cd", "0003");
				map.put("dev_sttus_cd", "02");
				map.put("use_yn", "N");
				map.put("del_yn", "Y");
				
				// OpenAPI 장치 데이터 수정 
				this.updateSpotDevBas(map);
				
				// EC 장치 삭제
				this.deleteECDev(spotDevBas.getSvc_tgt_seq(), spotDevBas.getSpot_dev_seq());
			}
		}
	}
		
	/*
	 * OpenAPI 장치 확장 정보 생성
	 */
	public void insertOpenAPIExpnsnBas(HomeProvision home, int svcTgtSeq, int spotDevSeq) {
		SpotDevExpnsnBas sdeb = new SpotDevExpnsnBas();
		
		Map<String, String> map = new HashMap<String, String>();
		map.put(SpotDevItemNm.CCTV_MAC, home.getCctvMac());
		map.put(SpotDevItemNm.CCTV_SEREIAL, home.getCctvSerial()); 
		map.put(SpotDevItemNm.CCTV_SECRET, home.getCctvSecret());
		map.put(SpotDevItemNm.CCTV_MODEL_CD, home.getCctvModelCd());
		map.put(SpotDevItemNm.CCTV_MODEL_NM, home.getCctvModelName());
		map.put(SpotDevItemNm.AP_NAME, "");
		map.put(SpotDevItemNm.AP_POWER, "");
		map.put(SpotDevItemNm.PRIVACY, "1");
		map.put(SpotDevItemNm.STORAGE_PNS, "");
		map.put(SpotDevItemNm.MOVING_PNS, "");
		map.put(SpotDevItemNm.SAVE_MODE, "");
		map.put(SpotDevItemNm.REVERTED, "");
		map.put(SpotDevItemNm.RESOLUTION, "");
		map.put(SpotDevItemNm.V_SENSITIVITY, "");
		map.put(SpotDevItemNm.M_SENSITIVITY, "");
		map.put(SpotDevItemNm.DETECTION, "");
		map.put(SpotDevItemNm.DETECTION_MODE, "");
		map.put(SpotDevItemNm.LAST_DETECTION_TIME, "");
		map.put(SpotDevItemNm.CON_STAT, "");
		map.put(SpotDevItemNm.CON_COUNT, "0");
		map.put(SpotDevItemNm.UPGRADE_STATUS, "0");
		map.put(SpotDevItemNm.UPGRADE_START_TIME, "");
		map.put(SpotDevItemNm.UCLOUD_STATUS, "0");
		
		Set key = map.keySet();
		for(Iterator<String> it = key.iterator(); it.hasNext();) { // 현장장치 상세 등록
			String keyNm = it.next();
			String val = map.get(keyNm);
			
			sdeb.setSvc_tgt_seq(svcTgtSeq);   // 현장장치항목값 
			sdeb.setSpot_dev_seq(spotDevSeq); // 현장장치값
			sdeb.setSpot_dev_item_nm(keyNm);
			sdeb.setSpot_dev_item_val(val);
			
			homeProvisionDao.insertSpotDevExpnsnBas(sdeb);
		}
	}
	
	/*
	 * OpenAPI 장치 확장 정보 수정
	 */
	public void updateOpenAPIExpnsnBas(Map<String, Object> map) {
		SpotDevExpnsnBas spotDevExpnsnBas = new SpotDevExpnsnBas();
		
		Map<String, Object> expnsnMap = new HashMap<String, Object>();
		expnsnMap.put(SpotDevItemNm.CCTV_MAC, map.get("cctv_mac"));
		expnsnMap.put(SpotDevItemNm.CCTV_SEREIAL, map.get("cctv_serial")); 
		expnsnMap.put(SpotDevItemNm.CCTV_SECRET, map.get("athn_no"));
		expnsnMap.put(SpotDevItemNm.CCTV_MODEL_CD, map.get("cctv_model_cd"));
		expnsnMap.put(SpotDevItemNm.CCTV_MODEL_NM, map.get("cctv_model_name"));
		
		Set key = expnsnMap.keySet();
		for(Iterator<String> it = key.iterator(); it.hasNext();) { // 현장장치 상세 등록
			String keyNm = it.next();
			String val = expnsnMap.get(keyNm) == null ? null : expnsnMap.get(keyNm).toString();
			
			spotDevExpnsnBas.setSvc_tgt_seq(NumberUtils.toInt(map.get("svc_tgt_seq").toString())); 
			spotDevExpnsnBas.setSpot_dev_seq(NumberUtils.toInt(map.get("spot_dev_seq").toString()));
			spotDevExpnsnBas.setSpot_dev_item_nm(keyNm);
			spotDevExpnsnBas.setSpot_dev_item_val(val);
			
			homeProvisionDao.updateSpotDevExpnsnBas(spotDevExpnsnBas);
		}
	}
	
	/*
	 * OpenAPI 장치 설정 정보 등록
	 */
	public void insertOpenAPIDevSetup(int svcTgtSeq, int spotDevSeq) {
		// 1. 설정기본, 일반 설정정보 Default 셋팅
		List<GenlSetupData> genlSetupDatas = new ArrayList<>();
		
		genlSetupDatas.add(new GenlSetupData("80000001", "0")); // 영상 송출 활성화 여부. 0:비활성화, 1:활성화
		genlSetupDatas.add(new GenlSetupData("80000002", "1")); // 해상도 옵션. 1,2,3
		genlSetupDatas.add(new GenlSetupData("80000003", "0")); // 침입 감지 활성화 여부. 0:비활성화, 1:활성화
		genlSetupDatas.add(new GenlSetupData("80000004", "1")); // 침입 감지 모드. 1:영상, 2:음향, 3:영상+음향
		genlSetupDatas.add(new GenlSetupData("80000005", "2")); // 움직임 감지 민감도 옵션. 1,2,3
		genlSetupDatas.add(new GenlSetupData("80000006", "2")); // 음향 감지 민감도 옵션. 1,2,3
		genlSetupDatas.add(new GenlSetupData("80000007", "0")); // 영상 송출 역전 여부. 0:정상, 1:역전
		genlSetupDatas.add(new GenlSetupData("80000010", "0")); // 저장 매체 모드. 0:저장하지않음, 1:SD카드, 2:Ucloud, 9:설정없음(센싱값은아님)
		genlSetupDatas.add(new GenlSetupData("80000012", "0")); // 예약 녹화 스케줄 활성화 여부. 0:비활성화, 1:활성화
		genlSetupDatas.add(new GenlSetupData("80000013", "0")); // 감지 스케줄 활성화 여부. 0:비활성화, 1:활성화
		genlSetupDatas.add(new GenlSetupData("80000014", "1")); // 예약 녹화 모드. 1:고정, 2:회전
		genlSetupDatas.add(new GenlSetupData("80000042", "0")); // 로그 모드. 0:로그전송Off, 1:로그전송On, 2:로그이전상태유지
		
		for(GenlSetupData genlSetupData : genlSetupDatas) {
			deviceService.createDeviceSetupBas(svcTgtSeq, spotDevSeq, genlSetupData);
			deviceService.createDeviceGeneralSetup(svcTgtSeq , spotDevSeq , genlSetupData);
		}
		
		// 2. 스케쥴 설정정보 Default 셋팅 
		List<SclgData> watchSclgDatas = new ArrayList<>();
		List<SclgData> recordSclgDatas = new ArrayList<>();
		
		// 월(1), 화(2), 수(3), 목(4), 금(5)
		for(int i = 1; i <=5; i++) {
			watchSclgDatas.add(new SclgData(String.valueOf(i), "0000", "0000"));
			recordSclgDatas.add(new SclgData(String.valueOf(i), "0000", "0000"));
		}		
		
		SclgSetupData watchSclgSetupData = new SclgSetupData();
		watchSclgSetupData.setSnsnTagCd(SnsnTagCd.SCH_SETUP_DETECTION); // 감시 스케쥴 설정
		watchSclgSetupData.setSclgDatas(watchSclgDatas);

		
		SclgSetupData recordSclgSetupData = new SclgSetupData();
		recordSclgSetupData.setSnsnTagCd(SnsnTagCd.SCH_SETUP_RECORD); // 녹화 스케쥴 설정		
		recordSclgSetupData.setSclgDatas(recordSclgDatas);
		
		deviceService.createDeviceScheduleSetup(svcTgtSeq, spotDevSeq, SnsnTagCd.SCH_SETUP_DETECTION, watchSclgSetupData);					
		deviceService.createDeviceScheduleSetup(svcTgtSeq, spotDevSeq, SnsnTagCd.SCH_SETUP_RECORD, recordSclgSetupData);	
	}
	
	/*
	 * EC 장치 생성
	 */
	public String insertECDev(HomeProvision home, int svcTgtBasSeq, int nextCntSpotDevSeq, String devUUID) {
		SpotDevBasVO vo = new SpotDevBasVO();
		
		Map<String, Object> modelMap = homeProvisionDao.getDevModelBas(home.getCctvModelCd());
		
		// 1. 현장 장치 기본
		vo.setSvcTgtSeq(Long.parseLong(Integer.toString(svcTgtBasSeq)));
		vo.setSpotDevSeq(Long.parseLong(Integer.toString(nextCntSpotDevSeq)));
		vo.setSpotDevId(home.getCctvSaid());
		vo.setGwCnctId("HOME_CAM");
		vo.setUnitSvcCd("001HIT001");
		vo.setSpotDevNm("");
		vo.setSttusCd("0001");
		vo.setColecCyclTime(30);
		vo.setRowRstrtnYn("N");
		vo.setMakrNm(modelMap.get("terml_makr_nm") == null ? null : modelMap.get("terml_makr_nm").toString());
		vo.setModelNm(modelMap.get("dev_model_cd") == null ? null : modelMap.get("dev_model_cd").toString());
		vo.setFrmwrVerNo("01.00");
		vo.setAthnFormlCd("0004");
		vo.setAthnRqtNo("");
		vo.setAthnNo(home.getCctvSecret());
		vo.setUseYn("Y");
		vo.setDevConnTypeCd("02");

		// 2. 현장 장치 상세
		List<SpotDevDtlVO> spotDevDtlVOs = new ArrayList<SpotDevDtlVO> ();
		
		SpotDevDtlVO voDtl = new SpotDevDtlVO();
		voDtl.setAtribNm("MAC_ADR");
		voDtl.setAtribVal(home.getCctvMac());
		spotDevDtlVOs.add(voDtl);
			
		voDtl = new SpotDevDtlVO();
		voDtl.setAtribNm("CEMS_SCRT_KEY");
		voDtl.setAtribVal(home.getCctvSecret());
		spotDevDtlVOs.add(voDtl);

		voDtl = new SpotDevDtlVO();
		voDtl.setAtribNm("DeviceType");
		voDtl.setAtribVal("IP_CCTV");
		spotDevDtlVOs.add(voDtl);
		
		voDtl = new SpotDevDtlVO();
		voDtl.setAtribNm("CCTV_LOC");
		voDtl.setAtribVal(home.getCctvLoc());
		spotDevDtlVOs.add(voDtl);
		
		voDtl = new SpotDevDtlVO();
		voDtl.setAtribNm("DEV_UU_ID");
		voDtl.setAtribVal(devUUID);
		spotDevDtlVOs.add(voDtl);

		vo.setSpotDevDtlVOs(spotDevDtlVOs);
		
		SpotDevBasQueryVO queryVO = ecService.sendInsertQueryToEC(vo);

		return queryVO.getRpyCode();
	}
	
	/*
	 * EC 장치 수정
	 */
	public String updateECDev(SpotDevBas spotDevBas, Map<String, Object> map) {
		SpotDevBasVO vo = new SpotDevBasVO();
		
		Map<String, Object> modelMap = homeProvisionDao.getDevModelBas(map.get("cctv_model_cd") == null ? null : map.get("cctv_model_cd").toString());
		
		// 현장장치 기본
		vo.setSvcTgtSeq(NumberUtils.toLong(map.get("svc_tgt_seq").toString()));
		vo.setSpotDevSeq(NumberUtils.toLong(map.get("spot_dev_seq").toString()));
		vo.setSpotDevId(map.get("spot_dev_id") == null ? null : map.get("spot_dev_id").toString());
		vo.setGwCnctId("HOME_CAM");
		vo.setUnitSvcCd("001HIT001");
		vo.setSttusCd(map.get("oprt_sttus_cd") == null ? null : map.get("oprt_sttus_cd").toString());
		vo.setColecCyclTime(30);
		vo.setRowRstrtnYn("N");
		if(modelMap != null) {
			vo.setMakrNm(modelMap.get("terml_makr_nm") == null ? null : modelMap.get("terml_makr_nm").toString());
			vo.setModelNm(modelMap.get("dev_model_cd") == null ? null : modelMap.get("dev_model_cd").toString());
		}
		vo.setAthnNo(map.get("athn_no") == null ? null : map.get("athn_no").toString());
		vo.setUseYn(map.get("use_yn") == null ? null : map.get("use_yn").toString());
		vo.setDevConnTypeCd("02"); //장치연결유형 고정 : 02

		// 현장 장치 상세 등록
		List<SpotDevDtlVO> spotDevDtlVOs = new ArrayList<SpotDevDtlVO> ();
		
		SpotDevDtlVO voDtl = new SpotDevDtlVO();
		voDtl.setAtribNm("MAC_ADR");
		voDtl.setAtribVal(map.get("cctv_mac") == null ? null : map.get("cctv_mac").toString());
		spotDevDtlVOs.add(voDtl);
			
		voDtl = new SpotDevDtlVO();
		voDtl.setAtribNm("CEMS_SCRT_KEY");
		voDtl.setAtribVal(map.get("athn_no") == null ? null : map.get("athn_no").toString());
		spotDevDtlVOs.add(voDtl);

		voDtl = new SpotDevDtlVO();
		voDtl.setAtribNm("DeviceType");
		voDtl.setAtribVal("IP_CCTV");
		spotDevDtlVOs.add(voDtl);
		
		voDtl = new SpotDevDtlVO();
		voDtl.setAtribNm("CCTV_LOC");
		voDtl.setAtribVal(map.get("eqp_lo_sbst") == null ? null : map.get("eqp_lo_sbst").toString());
		spotDevDtlVOs.add(voDtl);
		
		voDtl = new SpotDevDtlVO();
		voDtl.setAtribNm("DEV_UU_ID");
		voDtl.setAtribVal(spotDevBas.getDev_uu_id());
		spotDevDtlVOs.add(voDtl);

		vo.setSpotDevDtlVOs(spotDevDtlVOs);
		
		SpotDevBasQueryVO queryVO = ecService.sendUpdateQueryToEC(vo);

		return queryVO.getRpyCode();
	}
	
	/*
	 * EC 장치 삭제
	 */
	public String deleteECDev(int svcTgtSeq, int spotDevSeq) {
		SpotDevBasVO vo = new SpotDevBasVO();
		
		vo.setSvcTgtSeq(NumberUtils.toLong(Integer.toString(svcTgtSeq)));
		vo.setSpotDevSeq(NumberUtils.toLong(Integer.toString(spotDevSeq)));
		
		SpotDevBasQueryVO queryVO = ecService.sendDeleteQueryToEC(vo);

		return queryVO.getRpyCode();
	}
	
	/*
	 * Ucloud 계정 등록, 삭제
	 */
	public String setUcloudAccount(int svcTgtSeq, String svcContId, int mbrSeq, String opType) {
		String result = UCLOUD_RESULT_SUCCESS;
		
		if(mbrSeq != 0) { // 사용자와 카메라 청약정보가 연동 된 경우
			HashMap<String, Object> getMbrBas = homeProvisionDao.getMbrBas(mbrSeq);
			
			String loginId = getMbrBas.get("mbr_id").toString();
			String cid = getMbrBas.get("credential_id").toString();
			
			HashMap<String, String> map = ktService.sendUCloudAccountPutReq(loginId, cid, svcContId, opType);
			result = map.get("resultCode") == null ? UCLOUD_RESULT_SUCCESS : map.get("resultCode");
		}
		
		return result;
	}
}
