package com.kt.giga.home.openapi.cms.service;

import com.kt.giga.home.openapi.cms.dao.*;
import com.kt.giga.home.openapi.cms.domain.*;
import com.kt.giga.home.openapi.dao.SpotDevBasDao;
import com.kt.giga.home.openapi.dao.SvcTgtBasDao;
import com.kt.giga.home.openapi.service.APIException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 공지사항, FAQ 처리 서비스
 * 
 * @author 조상현
 *
 */
@Service
public class NoticeService {
	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private SpotDevBasDao spotDevBasDao;
	
	@Autowired
	private SvcTgtBasDao svcTgtBasDao;
	
	@Autowired
	private NoticeDao noticeDao;
	
	@Autowired
	private NoticePmDao noticePmDao;
	
	@Autowired
	private NoticePopupDao noticePopupDao;
	
	@Autowired
	private FaqDao faqDao;
	
	@Autowired
	private CodeDao codeDao;
	
	@Autowired
	private GuideCoachDao guideCoachDao;
	
	@Autowired
	private GuideWelcomeDao guideWelcomeDao;
	
	@Autowired
	private GuideCameraDao guideCameraDao;
	
//	@Autowired
//	private TransactionService transactionService;
	
	/**
	 * 
	 * @param svcTgtId	서비스대상아이디
	 * @return svcTgtSeq 서비스대상일련번호
	 * @throws Exception
	 */
	public int getSvcTgtSeq(String svcTgtId) throws Exception {
		
		int svcTgtSeq;
		
		try {
			svcTgtSeq		= svcTgtBasDao.getSvcTgtSeq(svcTgtId);
			
		} catch(DataAccessException e) {

			log.error(e.getMessage(), e);
			throw new APIException("Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return svcTgtSeq;
	}
	
	/**
	 * 
	 * @param cpCode 서비스 코드, startSeq 검색 요청할 시작 인덱스, limitCnt start_seq 부터 불러올 개수
	 * @return 일반공지 리스트 조회
	 * @throws Exception
	 */
	public JSONObject getNoticeList(Map<String, Object> map) throws Exception {

		JSONObject jsonObj 			= new JSONObject();
		JSONArray jsonArray 		= new JSONArray();

		Code code				= codeDao.getCode("1404");	//일반공지 url 코드 : 1404
		try {
			List<Notice> noticeList		= noticeDao.getNoticeList(map);
			
			jsonObj.put("totalCnt"		, noticeDao.getNoticeAllCnt(map)	);
			for(Notice noticeCol : noticeList){
					
					JSONObject listObj 			= new JSONObject();

					listObj.put("noticeListId"		, noticeCol.getPk_notice()		);	// 일반공지 PK
					listObj.put("noticeTitle"		, noticeCol.getFd_title()		);	// 일반공지 제목
					listObj.put("noticeUrl"			, code.getFd_name() +"?uid="+ noticeCol.getPk_notice() + "&cpCode=" + map.get("cpCode").toString() );	// 일반공지 url
					
					String regDt	= "";
					regDt			+= noticeCol.getFd_regdate().substring(0, 4);
					regDt			+= noticeCol.getFd_regdate().substring(5, 7);
					regDt			+= noticeCol.getFd_regdate().substring(8, 10);
					regDt			+= noticeCol.getFd_regdate().substring(11, 13);
					regDt			+= noticeCol.getFd_regdate().substring(14, 16);
					listObj.put("noticeRegDt"		, regDt);	// 일반공지 등록일
					
					jsonArray.add(listObj);
			}
			jsonObj.put("list"		, jsonArray		);
			
		} catch(DataAccessException e) {

			log.error(e.getMessage(), e);
			throw new APIException("Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return jsonObj;
	}

	/**
	 * 
	 * @param cpCode 서비스 코드, startSeq 검색 요청할 시작 인덱스, limitCnt start_seq 부터 불러올 개수
	 * @return FAQ 리스트 조회
	 * @throws Exception
	 */
	public JSONObject getFaqList(Map<String, Object> map) throws Exception {

		JSONObject jsonObj 			= new JSONObject();
		JSONArray jsonArray 			= new JSONArray();
		
		Code code				= codeDao.getCode("1407");	//FAQ url 코드 : 1407
		try {
			List<Faq> faqList		= faqDao.getFaqList(map);
			
			jsonObj.put("totalCnt"		, faqDao.getFaqAllCnt(map)	);
			for(Faq faqCol : faqList){
					
					JSONObject listObj 			= new JSONObject();

					listObj.put("faqListId"		, faqCol.getPk_faq()		);	// FAQ PK
					listObj.put("faqTitle"		, faqCol.getFd_title()		);	// FAQ 제목
					listObj.put("faqCate"		, faqCol.getFd_cate_name()	);	// FAQ 카테고리 이름
					listObj.put("faqUrl"		, code.getFd_name() +"?uid="+ faqCol.getPk_faq() + "&cpCode=" + map.get("cpCode").toString() );	// FAQ url
					
					String regDt	= "";
					regDt			+= faqCol.getFd_regdate().substring(0, 4);
					regDt			+= faqCol.getFd_regdate().substring(5, 7);
					regDt			+= faqCol.getFd_regdate().substring(8, 10);
					regDt			+= faqCol.getFd_regdate().substring(11, 13);
					regDt			+= faqCol.getFd_regdate().substring(14, 16);
					listObj.put("faqRegDt"		, regDt);	// FAQ 등록일
					
					jsonArray.add(listObj);
			}
			jsonObj.put("list"		, jsonArray		);
			
		} catch(DataAccessException e) {

			log.error(e.getMessage(), e);
			throw new APIException("Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return jsonObj;
	}
	
	/**
	 * 
	 * @param cpCode 서비스 코드
	 * @return PM공지 리스트 조회
	 * @throws Exception
	 */
	public JSONObject getNoticePmList(Map<String, Object> map) throws Exception {

		JSONObject jsonObj 			= new JSONObject();
		JSONArray jsonArray 		= new JSONArray();

		Code code				= codeDao.getCode("1406");	//PM공지 url 코드 : 1406
		try {
			List<NoticePm> noticePmList		= noticePmDao.getNoticePmList(map);
			
			jsonObj.put("totalCnt"		, noticePmDao.getNoticePmAllCnt(map)		);
			for(NoticePm noticePmCol : noticePmList){
					
					JSONObject listObj 			= new JSONObject();

					listObj.put("noticeListId"	, noticePmCol.getPk_notice_pm()		);	// PM공지 PK
					listObj.put("noticeTitle"	, noticePmCol.getFd_title()			);	// PM공지 제목
					listObj.put("noticeUrl"		, code.getFd_name() +"?uid="+ noticePmCol.getPk_notice_pm() + "&cpCode=" + map.get("cpCode").toString() );	// PM공지 url
					listObj.put("noticeStart"	, noticePmCol.getFd_start_time()	);	// PM공지 시작일시
					listObj.put("noticeEnd"		, noticePmCol.getFd_end_time()		);	// PM공지 종료일시
					
					jsonArray.add(listObj);
			}
			jsonObj.put("list"		, jsonArray		);
			
		} catch(DataAccessException e) {

			log.error(e.getMessage(), e);
			throw new APIException("Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return jsonObj;
	}
	
	/**
	 * 
	 * @param cpCode 서비스 코드
	 * @return 팝업공지 리스트 조회
	 * @throws Exception
	 */
	public JSONObject getNoticePopupList(Map<String, Object> map) throws Exception {

		JSONObject jsonObj 			= new JSONObject();
		JSONArray jsonArray 		= new JSONArray();

		Code code				= codeDao.getCode("1405");	//팝업공지 url 코드 : 1405
		try {
			List<NoticePopup> noticePopupList		= noticePopupDao.getNoticePopupList(map);
			
			jsonObj.put("totalCnt"		, noticePopupDao.getNoticePopupAllCnt(map)	);
			for(NoticePopup noticePopupCol : noticePopupList){
					
					JSONObject listObj 			= new JSONObject();

					listObj.put("noticeListId"	, noticePopupCol.getPk_notice_popup()	);	// 팝업공지 PK
					listObj.put("noticeTitle"	, noticePopupCol.getFd_title()			);	// 팝업공지 제목
					listObj.put("noticeUrl"		, code.getFd_name() +"?uid="+ noticePopupCol.getPk_notice_popup() + "&cpCode=" + map.get("cpCode").toString() );	// 팝업공지 url
					listObj.put("noticeKind"	, noticePopupCol.getFd_popup_type()		);	// 메시지 창 종류 (popup, alert, toast)
					listObj.put("noticeStopYn"	, noticePopupCol.getFd_nomore_yn()		);	// "오늘 하루 더 이상 보지 않음" 기능 유무 ('Y','N')
					
					jsonArray.add(listObj);
			}
			jsonObj.put("list"		, jsonArray		);
			
		} catch(DataAccessException e) {

			log.error(e.getMessage(), e);
			throw new APIException("Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return jsonObj;
	}
	
	/**
	 * 
	 * @param cpCode 서비스 코드
	 * @return 코치가이드 조회
	 * @throws Exception
	 */
    @SuppressWarnings("unchecked")
    public JSONObject getGuideCoach(Map<String, Object> map) throws Exception {

		JSONObject jsonObj 			= new JSONObject();
		JSONArray jsonArray 		= new JSONArray();
		
		Code code						= codeDao.getCode("1411");	//코치가이드 url 코드 : 1411
		try {
			List<GuideCoach> guideCoachList		= guideCoachDao.getGuideCoach(map);

            List<FileInfo> coachFileInfoList = new ArrayList<>();
            Map<String, Object> searchMap = new HashMap<>();

            for(GuideCoach listCol : guideCoachList){

                searchMap.put("fk_unique", listCol.getPk_guide_coach());
                searchMap.put("fk_table", "tb_guide_coach");
                coachFileInfoList = guideCoachDao.getGuideCoachImg(searchMap);

                JSONObject listObj 			= new JSONObject();
				
				listObj.put("title"			, listCol.getFd_title()				);		// 제목
				listObj.put("verNum"		, listCol.getFd_ver_num()			);		// 버전 정보
				listObj.put("position"		, listCol.getFd_position_code()		);		// 가이드 노출 위치

                for (FileInfo fileInfo : coachFileInfoList) {
                    if (fileInfo.getFd_description() != null && fileInfo.getFd_virutal_path() != null && fileInfo.getFd_save_name() != null) {
                        switch (fileInfo.getFd_description()) {
                            //1902:hdpi, 1903:xhdpi, 1904:xxhdpi
                            case "1902":
                                listObj.put("imgUrl_1", code.getFd_name() + fileInfo.getFd_virutal_path() + "/" + fileInfo.getFd_save_name());
                                break;
                            case "1903":
                                listObj.put("imgUrl_2", code.getFd_name() + fileInfo.getFd_virutal_path() + "/" + fileInfo.getFd_save_name());
                                break;
                            case "1904":
                                listObj.put("imgUrl_3", code.getFd_name() + fileInfo.getFd_virutal_path() + "/" + fileInfo.getFd_save_name());
                                break;
                            default:
                        }
                    }
                }

				/*
                listObj.put("imgUrl_1"		, code.getFd_name() + listCol.getFd_file_path_1()	);	// 가이드 이미지 url 1
				listObj.put("imgUrl_2"		, code.getFd_name() + listCol.getFd_file_path_2()	);	// 가이드 이미지 url 2
				listObj.put("imgUrl_3"		, code.getFd_name() + listCol.getFd_file_path_3()	);	// 가이드 이미지 url 3
				 */
                //				listObj.put("guideUrl"		, code.getFd_name() +"?uid="+ guideCoach.getPk_guide_coach()	);	// 가이드 url
				
				jsonArray.add(listObj);
			}
			jsonObj.put("list"		, jsonArray		);
	
		} catch(DataAccessException e) {

			log.error(e.getMessage(), e);
			throw new APIException("Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return jsonObj;
	}
	
	/**
	 * 
	 * @param cpCode 서비스 코드
	 * @return 웰컴가이드 조회
	 * @throws Exception
	 */
    @SuppressWarnings("unchecked")
    public JSONObject getGuideWelcome(Map<String, Object> map) throws Exception {

		JSONObject jsonObj 				= new JSONObject();
		
		Code code						= codeDao.getCode("1412");	//웰컴가이드 url 코드 : 1412

        List<FileInfo> welcomeFileInfoList = new ArrayList<>();
        Map<String, Object> searchMap = new HashMap<>();

        try {
			GuideWelcome guideWelcome	= guideWelcomeDao.getGuideWelcome(map);

            if(guideWelcome != null){

                searchMap.put("fk_unique", guideWelcome.getPk_guide_welcome());
                searchMap.put("fk_table", "tb_guide_welcome");
                welcomeFileInfoList = guideWelcomeDao.getGuideWelcomeImg(searchMap);

                jsonObj.put("verNum"		, guideWelcome.getFd_ver_num()			);		// 버전 정보

                for (FileInfo fileInfo : welcomeFileInfoList) {
                    if (fileInfo.getFd_virutal_path() != null && fileInfo.getFd_save_name() != null && fileInfo.getFd_sort_no() != null) {
                        jsonObj.put("imgUrl_" + fileInfo.getFd_sort_no(), code.getFd_name() + fileInfo.getFd_virutal_path() + "/" + fileInfo.getFd_save_name());
                    } else {
                        jsonObj.put("imgUrl_" + fileInfo.getFd_sort_no(), "");
                    }
                }

				/*
                jsonObj.put("imgUrl_1"		, code.getFd_name() + guideWelcome.getFd_file_path_1()	);	// 가이드 이미지 url 1
				jsonObj.put("imgUrl_2"		, code.getFd_name() + guideWelcome.getFd_file_path_2()	);	// 가이드 이미지 url 2
				jsonObj.put("imgUrl_3"		, code.getFd_name() + guideWelcome.getFd_file_path_3()	);	// 가이드 이미지 url 3
				*/
			}
			
		} catch(DataAccessException e) {

			log.error(e.getMessage(), e);
			throw new APIException("Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return jsonObj;
	}	
	
	/**
	 * 
	 * @param cpCode 서비스 코드
	 * @return 카메라 팁 조회
	 * @throws Exception
	 */
    @SuppressWarnings("unchecked")
    public JSONObject getGuideCamera(Map<String, Object> map) throws Exception {

		JSONObject jsonObj 				= new JSONObject();
		
		Code code						= codeDao.getCode("1413");	//카메라 팁 url 코드 : 1413

        List<FileInfo> cameraFileInfoList = new ArrayList<>();
        Map<String, Object> searchMap = new HashMap<>();

        try {
			GuideCamera guideCamera	= guideCameraDao.getGuideCamera(map);

			if(guideCamera != null){

                searchMap.put("fk_unique", guideCamera.getPk_guide_camera());
                searchMap.put("fk_table", "tb_guide_camera");
                cameraFileInfoList = guideCameraDao.getGuideCameraImg(searchMap);

                jsonObj.put("verNum"		, guideCamera.getFd_ver_num()			);		// 버전 정보

                for (FileInfo fileInfo : cameraFileInfoList) {
                    if (fileInfo.getFd_virutal_path() != null && fileInfo.getFd_save_name() != null && fileInfo.getFd_sort_no() != null) {
                        jsonObj.put("imgUrl_" + fileInfo.getFd_sort_no(), code.getFd_name() + fileInfo.getFd_virutal_path() + "/" + fileInfo.getFd_save_name());
                    } else {
                        jsonObj.put("imgUrl_" + fileInfo.getFd_sort_no(), "");
                    }
                }

				/*
                jsonObj.put("imgUrl_1"		, code.getFd_name() + guideCamera.getFd_file_path_1()	);	// 가이드 이미지 url 1
				jsonObj.put("imgUrl_2"		, code.getFd_name() + guideCamera.getFd_file_path_2()	);	// 가이드 이미지 url 2
				jsonObj.put("imgUrl_3"		, code.getFd_name() + guideCamera.getFd_file_path_3()	);	// 가이드 이미지 url 3
				*/
			}
			
		} catch(DataAccessException e) {

			log.error(e.getMessage(), e);
			throw new APIException("Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return jsonObj;
	}	
}

