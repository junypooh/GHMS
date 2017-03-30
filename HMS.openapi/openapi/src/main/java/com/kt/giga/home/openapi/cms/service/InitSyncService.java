package com.kt.giga.home.openapi.cms.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.kt.giga.home.openapi.cms.dao.CodeDao;
import com.kt.giga.home.openapi.cms.dao.GuideCameraDao;
import com.kt.giga.home.openapi.cms.dao.GuideCoachDao;
import com.kt.giga.home.openapi.cms.dao.GuideWelcomeDao;
import com.kt.giga.home.openapi.cms.dao.HomesvcPropsDao;
import com.kt.giga.home.openapi.cms.dao.MonitorDao;
import com.kt.giga.home.openapi.cms.dao.NoticePmDao;
import com.kt.giga.home.openapi.cms.dao.NoticePopupDao;
import com.kt.giga.home.openapi.cms.dao.PrivacyDao;
import com.kt.giga.home.openapi.cms.dao.TermsDao;
import com.kt.giga.home.openapi.cms.dao.VersionAppDao;
import com.kt.giga.home.openapi.cms.dao.VersionFrmwrDao;
import com.kt.giga.home.openapi.cms.domain.Code;
import com.kt.giga.home.openapi.cms.domain.FileInfo;
import com.kt.giga.home.openapi.cms.domain.GuideCamera;
import com.kt.giga.home.openapi.cms.domain.GuideCoach;
import com.kt.giga.home.openapi.cms.domain.GuideWelcome;
import com.kt.giga.home.openapi.cms.domain.HomesvcProps;
import com.kt.giga.home.openapi.cms.domain.NoticePm;
import com.kt.giga.home.openapi.cms.domain.NoticePopup;
import com.kt.giga.home.openapi.cms.domain.Terms;
import com.kt.giga.home.openapi.cms.domain.VersionApp;
import com.kt.giga.home.openapi.cms.domain.VersionFrmwr;
import com.kt.giga.home.openapi.service.APIException;

/**
 * 앱초기화 싱크 처리 서비스
 * 
 * @author 조상현
 *
 */
@Service
public class InitSyncService {
	private Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private HomesvcPropsDao homesvcPropsDao;
	
	@Autowired
	private CodeDao codeDao;
	
	@Autowired
	private PrivacyDao privacyDao;
	
	@Autowired
	private VersionAppDao versionAppDao;
	
	@Autowired
	private VersionFrmwrDao versionFrmwrDao;
	
	@Autowired
	private GuideCoachDao guideCoachDao;
	
	@Autowired
	private GuideWelcomeDao guideWelcomeDao;
	
	@Autowired
	private GuideCameraDao guideCameraDao;

	@Autowired
	private TermsDao termsDao;
	
	@Autowired
	private NoticePopupDao noticePopupDao;
	
	@Autowired
	private MonitorDao monitorDao;
	
	@Autowired
	private NoticePmDao noticePmDao;
	
	SimpleDateFormat fmt = new SimpleDateFormat("yyyyMMddHHmmss");

/*
	최신 앱 버전						001	init.newAppVer				버전 스트링, 예를 들면 1.0								1
	앱 업데이트 옵션					001	init.appUpOption			0:업데이트불필요, 1:강제업그레이드, 2:선택업그레이드	1
	카메라 최신 펌웨어 버전				001	dev.firm.version			버전 스트링, 예를 들면 1.0								1
	카메라 최신 펌웨어 업데이트 옵션	001	dev.firm.upOption			1:강제업그레이드, 2:선택업그레이드						1
	
	코치가이드 URL 리스트				001	init.coachGuideUrlList.h	단순 URL, 파이프 구분 N개, hdpi							1
	코치가이드 URL 리스트				001	init.coachGuideUrlList.xh	단순 URL, 파이프 구분 N개, xhdpi						1
	코치가이드 URL 리스트				001	init.coachGuideUrlList.xxh	단순 URL, 파이프 구분 N개, xxhdpi						1
	웰컴가이드 URL 리스트				001	init.welcomeGuideUrlList	단순 URL, 파이프 구분 N개								1
	카메라 팁 URL 리스트				001	init.camGuideUrlList		단순 URL, 파이프 구분 N개								1
	
	개인정보 취급방침 URL				001	init.privacyPolicyUrl		단순 URL												1
	상품 가입 안내 URL					001	init.joinGuideUrl			단순 URL												1
	Ucloud 가입안내 URL					001	init.ucloudJoinGuideUrl		단순 URL												1
	
	PM공지사항 URL	(스케쥴 처리함)		001	init.noticeUrl				단순 URL												1
	PM공지사항 옵션	(스케쥴 처리함)		001	init.noticeOption			0:공지없음, 1:공지있음(강제), 2:공지있음(선택)			1
	
	공지팝업 URL						001	init.noticePopUrl			단순 URL												1
	공지팝업 옵션						001	init.noticePopMoreYn		오늘하루 더이상 보지않기 기능 유무						1
*/
	
	/**
	 * 
	 * @param	cpCode
	 * @return 	void()
	 * @throws 	Exception
	 */
	public void syncInit(String cpCode ) throws Exception {

		Map<String, Object> map				= new HashMap<String, Object> ();
		map.put("cpCode"		, cpCode);	//서비스 구분코드 	- 001:홈모니터링, 002:홈헬스케어, 003:홈매니저
		map.put("osCode"		, "1701");	//OS 구분코드 		- 1701:안드로이드
		map.put("phoneTypeCode"	, "1801");	//단말형태 구분코드 - 1801:모바일폰
		
		String welcome_url="", camera_url="";
		Code code							= new Code();
		List<Code>	codeList			= new ArrayList<Code>();
		
		HomesvcProps homesvcProps 			= new HomesvcProps();
		homesvcProps.setSvccode( map.get("cpCode").toString() );
		
		String nowDateTime = fmt.format(new Date());
		
		//----------------------- 기본 항목 키값 생성 -------------------------
		try{
			
			initParams("init.newAppVer", 				homesvcProps); 	//최신 앱버전
			initParams("init.appUpOption", 			homesvcProps);	//앱 업데이트 옵션
			initParams("dev.firm.version", 				homesvcProps);	//카메라 최신 펌웨어 버전
			initParams("dev.firm.upOption", 			homesvcProps);	//카메라 최신 펌웨어 업데이트 옵션
			initParams("init.coachGuideUrlList.h", 	homesvcProps);	//코치가이드 hdpi URL 리스트
			initParams("init.coachGuideUrlList.xh", 	homesvcProps);	//코치가이드 xhdpi URL 리스트
			initParams("init.coachGuideUrlList.xxh", homesvcProps);	//코치가이드 xxhdpi URL 리스트
			initParams("init.welcomeGuideUrlList", 	homesvcProps);	//웰컴가이드 URL 리스트
			initParams("init.privacyPolicyUrl", 		homesvcProps);	//개인정보 취급방침 URL
			initParams("init.joinGuideUrl", 				homesvcProps);	//상품 가입 안내 URL
			initParams("init.noticeUrl", 					homesvcProps);	//PM공지사항 URL	(스케쥴 처리함)
			initParams("init.noticeOption", 			homesvcProps);	//PM공지사항 옵션	(스케쥴 처리함)
			initParams("init.noticePopUrl", 			homesvcProps);	//팝업공지 URL
			initParams("init.noticePopMoreYn", 		homesvcProps);	//팝업공지 옵션
			initParams("init.popupNoticeUrl", 			homesvcProps);	//팝업공지 URL
			initParams("init.popupNoticeMoreYn", 	homesvcProps);	//팝업공지 옵션
			
			switch (cpCode ){
				case "001" :
					initParams("init.ecPort", 						homesvcProps); 	//EC 서버 포트
					initParams("init.ecServerList", 				homesvcProps); 	//EC 서버 리스트
					
					initParams("init.camGuideUrlList", 			homesvcProps);	//카메라 팁 URL 리스트
					initParams("init.ucloudJoinGuideUrl", 			homesvcProps);	//Ucloud 가입안내 URL
					initParams("dev.relay.maxExpireTime", 		homesvcProps);	//모니터링 시간조절
					
					initParams("beta.firm.version", 			homesvcProps);	//테스트 카메라 최신 펌웨어 버전 (홈캠전용)
					initParams("beta.firm.upOption", 			homesvcProps);	//테스트 카메라 최신 펌웨어 업데이트 옵션 (홈캠전용)
					break;
				default :
			}
			
		} catch (Exception e){
			e.printStackTrace();
		}
		//----------------------- 기본 항목 키값 생성 -------------------------
		
		try {
			
			//최신 앱 버전
			VersionApp versionApp				= versionAppDao.getVerAppLast(map);

			if(versionApp != null){
				updateParams("init.newAppVer", 		versionApp.getFd_ver_num(), 										homesvcProps);	//최신 버전
				updateParams("init.appUpOption", 	versionApp.getFd_mandatory_yn().equals("Y") ? "1" : "2", 	homesvcProps);	//업데이트 필수여부 YN
			}
			
			//최신 카메라 펌웨어 버전
			VersionFrmwr versionFrmwr			= versionFrmwrDao.getVerFrmwrLast(map);
			if(versionFrmwr != null){
				updateParams("dev.firm.version", 		versionFrmwr.getFd_ver_num(), 											homesvcProps);	//최신 버전
				updateParams("dev.firm.upOption", 		versionFrmwr.getFd_mandatory_yn().equals("Y") ? "1" : "2", 	homesvcProps);	//업데이트 필수여부 YN
			}
			
			if(cpCode.equals("001")){
				//테스트 최신 카메라 펌웨어 버전 (홈캠전용)
				VersionFrmwr versionBetaFrmwr			= versionFrmwrDao.getVerBetaFrmwrLast(map);
				if(versionBetaFrmwr != null){
					updateParams("beta.firm.version", 			versionBetaFrmwr.getFd_ver_num(), 											homesvcProps);	//테스트 최신 버전 (홈캠전용)
					updateParams("beta.firm.upOption", 		versionBetaFrmwr.getFd_mandatory_yn().equals("Y") ? "1" : "2", 		homesvcProps);	//테스트 업데이트 필수여부 YN (홈캠전용)
				}
			}
			
			//앱버전 목록
			List<VersionApp> versionAppList		= versionAppDao.getVerAppList(map);
			for(VersionApp listAppVer : versionAppList){
				//DB에 파라메타값 미리 생성, 이후 로직에서 업데이트
				homesvcProps.setSvccode(cpCode );
				homesvcProps.setValue("N");//초기 더미값 생성

				initParams("init.coachGuideUrlList.h."	+	listAppVer.getFd_ver_num(), 		homesvcProps);
				initParams("init.coachGuideUrlList.xh."	+	listAppVer.getFd_ver_num(), 		homesvcProps);
				initParams("init.coachGuideUrlList.xxh."	+	listAppVer.getFd_ver_num(), 		homesvcProps);
				initParams("init.welcomeGuideUrlList."	+	listAppVer.getFd_ver_num(), 		homesvcProps);
				initParams("init.camGuideUrlList."			+	listAppVer.getFd_ver_num(), 		homesvcProps);
				
				map.put("fd_ver_num"	, listAppVer.getFd_ver_num()	);	//단말 버전
				
				//코치가이드
				code								= codeDao.getCode("1411");	//코치가이드 url 코드 : 1411
				List<GuideCoach> guideCoachList		= guideCoachDao.getGuideCoach(map);
				
				String coach_url_1="", coach_url_3="", coach_url_2=""; 	
				List<FileInfo> coachFileInfoList = null;
				
				for(GuideCoach listCol : guideCoachList){
					
					map.put("fk_unique", Integer.parseInt(listCol.getPk_guide_coach().toString()));
					map.put("fk_table", "tb_guide_coach");
					
					coachFileInfoList = guideCoachDao.getGuideCoachImg(map);
					for(FileInfo fileInfo : coachFileInfoList){
						if(fileInfo.getFd_description() != null && fileInfo.getFd_virutal_path() != null && fileInfo.getFd_save_name() != null){
							switch(fileInfo.getFd_description()){
								//1902:hdpi, 1903:xhdpi, 1904:xxhdpi
								case "1902" 	: 	coach_url_1 += coach_url_1.equals("") ? code.getFd_name() + fileInfo.getFd_virutal_path() + "/" + fileInfo.getFd_save_name() : "|" + code.getFd_name() + fileInfo.getFd_virutal_path() + "/" + fileInfo.getFd_save_name(); break;
								case "1903" 	: 	coach_url_2 += coach_url_2.equals("") ? code.getFd_name() + fileInfo.getFd_virutal_path() + "/" + fileInfo.getFd_save_name() : "|" + code.getFd_name() + fileInfo.getFd_virutal_path() + "/" + fileInfo.getFd_save_name(); break;
								case "1904" 	:	coach_url_3 += coach_url_3.equals("") ? code.getFd_name() + fileInfo.getFd_virutal_path() + "/" + fileInfo.getFd_save_name() : "|" + code.getFd_name() + fileInfo.getFd_virutal_path() + "/" + fileInfo.getFd_save_name(); break;
								default : 
							}
						}
					}
				}
				updateParams("init.coachGuideUrlList.h." 		+ 	listAppVer.getFd_ver_num(), coach_url_1, homesvcProps);
				updateParams("init.coachGuideUrlList.xh." 	+ 	listAppVer.getFd_ver_num(), coach_url_2, homesvcProps);
				updateParams("init.coachGuideUrlList.xxh." 	+ 	listAppVer.getFd_ver_num(), coach_url_3, homesvcProps);

				//웰컴가이드
				code								= codeDao.getCode("1412");	//웰컴가이드 url 코드 : 1412
				GuideWelcome guideWelcome			= guideWelcomeDao.getGuideWelcome(map);
				
				if(guideWelcome != null){
					map.put("fk_unique", Integer.parseInt(guideWelcome.getPk_guide_welcome().toString()));
					map.put("fk_table", "tb_guide_welcome");
					
					List<FileInfo> welcomeFileInfoList = guideWelcomeDao.getGuideWelcomeImg(map);
					welcome_url = "";
					
					for(FileInfo fileInfo : welcomeFileInfoList){
						if(fileInfo.getFd_virutal_path() != null && fileInfo.getFd_save_name() != null && fileInfo.getFd_sort_no() != null){
							if(fileInfo.getFd_sort_no().toString().equals("1")){
								welcome_url += code.getFd_name() + fileInfo.getFd_virutal_path() + "/" + fileInfo.getFd_save_name();
							}else{
								welcome_url += "|" + code.getFd_name() + fileInfo.getFd_virutal_path() + "/" + fileInfo.getFd_save_name();
							}
						}
					}
				}
				updateParams("init.welcomeGuideUrlList."	+ 	listAppVer.getFd_ver_num(), welcome_url, homesvcProps);
			
				//카메라 팁
				code								= codeDao.getCode("1413");	//카메라 팁 url 코드 : 1413
				GuideCamera guideCamera				= guideCameraDao.getGuideCamera(map);
				if(guideCamera != null){
					map.put("fk_unique", Integer.parseInt(guideCamera.getPk_guide_camera().toString()));
					map.put("fk_table", "tb_guide_camera");
					
					List<FileInfo> cameraFileInfoList = guideCameraDao.getGuideCameraImg(map);
					camera_url = "";
					
					for(FileInfo fileInfo : cameraFileInfoList){
						if(fileInfo.getFd_virutal_path() != null && fileInfo.getFd_save_name() != null && fileInfo.getFd_sort_no() != null){
							if(fileInfo.getFd_sort_no().toString().equals("1")){
								camera_url += code.getFd_name() + fileInfo.getFd_virutal_path() + "/" + fileInfo.getFd_save_name();
							}else{
								camera_url += "|" + code.getFd_name() + fileInfo.getFd_virutal_path() + "/" + fileInfo.getFd_save_name();
							}
						}
					}
				}
				updateParams("init.camGuideUrlList."	+ 	listAppVer.getFd_ver_num(), camera_url, homesvcProps);
				
				//------------- 디폴트 설정 -----------------
				if (versionApp.getFd_ver_num().equals( listAppVer.getFd_ver_num() )){ //최종버전과 리스트버전이 일치하면 값저장
					updateParams("init.coachGuideUrlList.h",		coach_url_1, 	homesvcProps);
					updateParams("init.coachGuideUrlList.xh", 	coach_url_2, 	homesvcProps);
					updateParams("init.coachGuideUrlList.xxh", 	coach_url_3, 	homesvcProps);
					updateParams("init.welcomeGuideUrlList", 	welcome_url, 	homesvcProps);
					updateParams("init.camGuideUrlList", 			camera_url, 	homesvcProps);
				}
				//------------- 디폴트 설정 -----------------
			}
			
			try {
				//개인정보 취급방침
				code								= codeDao.getCode("1414");	//개인정보 취급방침 url 코드 : 1414
				int privacySeq						= privacyDao.getLastSeq(map.get("cpCode").toString());
				List<Terms> termsList				= termsDao.getTermsList(map);
				for(Terms termsCol : termsList){
					if( termsCol.getFk_terms_code().equals( "1302") ){	//"1302" 개인정보 수집 및 이용동의
						updateParams("init.privacyPolicyUrl",	code.getFd_name() +"?uid="+ String.valueOf(privacySeq) + "&cpCode=" + cpCode , 	homesvcProps);
						break;
					}
				}
			
			}catch(Exception e){
				
			}
			
			//상품가입 안내 url
			codeList								= codeDao.getCodeList("2200");	//서비스 가입안내 url 업코드 : 2200
			for(Code codeCol : codeList){
				if(cpCode.equals(codeCol.getFd_memo())){
					updateParams("init.joinGuideUrl",	codeCol.getFd_name(), 	homesvcProps);
				}
			}
			
			//Ucloud 가입안내 url
			code								= codeDao.getCode("1409");	//ucloud 회원 가입 url 코드 : 1409
			updateParams("init.ucloudJoinGuideUrl", code.getFd_name(), homesvcProps);
			
			//팝업공지 url, 팝업공지 옵션
			map.put("nowDateTime", nowDateTime);
			
			code = codeDao.getCode("1405");	//팝업공지 url 코드 : 1405
			NoticePopup noticePopup = noticePopupDao.getNoticePopupLast(map);
			
			if(noticePopup != null) {
				updateParams("init.popupNoticeUrl",	 code.getFd_name() +"?uid="+ noticePopup.getPk_notice_popup() + "&cpCode=" + cpCode, homesvcProps);
				updateParams("init.popupNoticeMoreYn", noticePopup.getFd_nomore_yn(), homesvcProps);

				updateParams("init.noticePopUrl", code.getFd_name() +"?uid="+ noticePopup.getPk_notice_popup() + "&cpCode=" + cpCode, homesvcProps);
				updateParams("init.noticePopMoreYn", noticePopup.getFd_nomore_yn(), homesvcProps);
			} else {
				updateParams("init.popupNoticeUrl",	 "", homesvcProps);
				updateParams("init.popupNoticeMoreYn",	 "", homesvcProps);
	
				updateParams("init.noticePopUrl", "", homesvcProps);
				updateParams("init.noticePopMoreYn", "", homesvcProps);
			}
			
//			int viewCnt = noticePopupDao.getNoticePopupViewCnt(map);
//
//			String popupNoticeOption = viewCnt > 0 ? "1" : "0";
//			updateParams("init.popupNoticeOption", popupNoticeOption, homesvcProps);
			
			//모니터링 시간조절
			int interval_time			= monitorDao.getLast();
			updateParams("dev.relay.maxExpireTime", Integer.toString(interval_time), homesvcProps);
			
		} catch(DataAccessException e) {

			log.error(e.getMessage(), e);
			throw new APIException("Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		//Pm공지 등록(별도로 스케쥴도 동작함)
		try {
			code				= codeDao.getCode("1406");	//PM공지 url 코드 : 1406

			map.put("now", nowDateTime);

			NoticePm noticePm		= noticePmDao.getNoticePmNow(map);
			if(noticePm != null){
				
				updateParams("init.noticeOption", "1", homesvcProps);
				updateParams("init.noticeUrl", code.getFd_name() +"?uid="+ noticePm.getPk_notice_pm() + "&cpCode=" + cpCode, homesvcProps); //PM공지 url
				
			}else{

				updateParams("init.noticeOption", "0", homesvcProps);
				updateParams("init.noticeUrl", "", homesvcProps); //PM공지 url
				
			}
		} catch(DataAccessException e) {

			log.error(e.getMessage(), e);
		}
	}
	
	/**
	 * 파라미터 기본값 생성
	 * @param setName
	 * @param homesvcProps
	 * @throws Exception
	 */
	public void initParams(String setName, HomesvcProps homesvcProps) throws Exception {
		homesvcProps.setName(setName);
		if(homesvcPropsDao.getHomesvcPropsCnt(homesvcProps) == 0){	homesvcPropsDao.insertHomesvcProps(homesvcProps);	}
	}
	
	/**
	 * 파라미터 업데이트
	 * @param setName
	 * @param setValue
	 * @param homesvcProps
	 * @throws Exception
	 */
	public void updateParams(String setName, String setValue, HomesvcProps homesvcProps) throws Exception {
		homesvcProps.setName(setName);
		homesvcProps.setValue(setValue);
		homesvcPropsDao.updateHomesvcProps(homesvcProps);
	}

}

