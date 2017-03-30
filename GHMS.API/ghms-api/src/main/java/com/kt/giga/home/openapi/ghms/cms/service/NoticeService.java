/*******************************************************************************
 * Copyright(c) 2015 KT. All rights reserved.
 * This software is the proprietary information of KT.
 *******************************************************************************/
package com.kt.giga.home.openapi.ghms.cms.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.kt.giga.home.openapi.ghms.cms.dao.CodeDao;
import com.kt.giga.home.openapi.ghms.cms.dao.NoticeDao;
import com.kt.giga.home.openapi.ghms.cms.domain.vo.CodeVo;
import com.kt.giga.home.openapi.ghms.cms.domain.vo.NoticeDtl;
import com.kt.giga.home.openapi.ghms.cms.domain.vo.NoticeVo;
import com.kt.giga.home.openapi.ghms.util.exception.APIException;

/**
 * 공지사항 Service
 * 
 * @author dahye.kim (dahye.kim@ceinside.co.kr)
 * @since 2015. 2. 10.
 */
@Service
@Transactional(
        propagation = Propagation.REQUIRED,
        rollbackFor = {
                Exception.class,
                RuntimeException.class,
                SQLException.class
        }
)
public class NoticeService {
    private Logger log = LoggerFactory.getLogger(this.getClass());
    
    @Autowired
    private CodeDao codeDao;
    
    @Autowired
    private NoticeDao noticeDao;

    
    /**
     * @param map   cpCode 서비스 코드, startSeq 검색 요청할 시작 인덱스, limitCnt start_seq 부터 불러올 개수
     * @return      일반공지 리스트 조회
     * @throws APIException
     */
    public NoticeVo selectNoticeList(Map<String, Object> map) throws APIException {
        
        NoticeVo vo = new NoticeVo();
        
        CodeVo code = codeDao.selectCode("1404");   //일반공지 url 코드 : 1404
        
        try {
            List<NoticeDtl> list = new ArrayList<NoticeDtl>();
            
            List<NoticeDtl> noticeList = noticeDao.selectNoticeList(map);
            
            vo.setTotCnt(noticeDao.selectNoticeAllCnt(map));
            
            for(NoticeDtl noticeCol : noticeList) {
                NoticeDtl dtl = new NoticeDtl();
                
                dtl.setNoticeListId(noticeCol.getPk_notice());                                 // 일반공지 pk
                dtl.setNoticeTitle(noticeCol.getFd_title());                                   // 일반공지 제목
                dtl.setNoticeUrl(code.getFd_name() + "?uid=" + noticeCol.getPk_notice() + "&cpCode=" + map.get("cpCode").toString());      // 일반공지 url
                
                String regDt    = "";
                regDt           += noticeCol.getFd_regdate().substring(0, 4);
                regDt           += noticeCol.getFd_regdate().substring(5, 7);
                regDt           += noticeCol.getFd_regdate().substring(8, 10);
                regDt           += noticeCol.getFd_regdate().substring(11, 13);
                regDt           += noticeCol.getFd_regdate().substring(14, 16);
                dtl.setNoticeRegDt(regDt);                                                      // 일반공지 등록일
                
                list.add(dtl);
            }

            vo.setNoticeDtlList(list);

        } catch(DataAccessException e) {
            log.error(e.getMessage(), e);
            throw new APIException("Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
            
        return vo;
    }
    
    /**
     * @param map cpCode 단위 서비스 코드
     * @return PM공지 리스트 조회
     * @throws APIException
     */
    public NoticeVo selectNoitcePmList(Map<String, Object> map) throws APIException{
        
        
        NoticeVo vo = new NoticeVo();
        
        List<NoticeDtl> list = new ArrayList<NoticeDtl>();
        
        CodeVo code = codeDao.selectCode("1406");     // PM공지 url 코드 : 1406
        
        try {
            List<NoticeDtl> noticePmList = noticeDao.selectNoticePmList(map);
            
            vo.setTotCnt(noticeDao.selectNoticePmAllCnt(map));
            
            for(NoticeDtl noticePmCol : noticePmList){
                
                NoticeDtl dtl = new NoticeDtl();
                
                dtl.setNoticeListId(noticePmCol.getPk_notice_pm());
                dtl.setNoticeTitle(noticePmCol.getFd_title());
                dtl.setNoticeUrl(code.getFd_name() + "?uid=" + noticePmCol.getPk_notice_pm() + "&cpCode=" + map.get("cpCode").toString());
                dtl.setNoticeStart(noticePmCol.getFd_start_time());
                dtl.setNoticeEnd(noticePmCol.getFd_end_time());
                
                list.add(dtl);
            }
            vo.setNoticeDtlList(list);
        } catch(DataAccessException e) {
            log.error(e.getMessage(), e);
            throw new APIException("Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        
        
        return vo;
    }
    
    /**
     * 팝업공지 리스트 조회
     * 
     * @param map
     * @return
     * @throws APIException
     */
    public NoticeVo selectNoticePopupList(Map<String, Object> map) throws APIException{
        
        NoticeVo vo = new NoticeVo();
        
        CodeVo code = codeDao.selectCode("1405");       // 팝업공지 url 코드 : 1405
        
        try {
            List<NoticeDtl> noticePopupList = noticeDao.selectNoticePopupList(map);
            
            vo.setTotCnt(noticeDao.selectNoticePopupAllCnt(map));
            
            for(NoticeDtl noticePopupCol : noticePopupList) {
                
                noticePopupCol.setNoticeListId(noticePopupCol.getPk_notice_popup());                               // 팝업공지 PK
                noticePopupCol.setNoticeTitle(noticePopupCol.getFd_title());                                       // 팝업공지 제목
                noticePopupCol.setNoticeUrl(code.getFd_name() + "?uid=" + noticePopupCol.getPk_notice_popup() + "&cpCode=" + map.get("cpCode").toString());    // 팝업공지 url
                noticePopupCol.setNoticeKind(noticePopupCol.getFd_popup_type());                                   // 메시지 창 종류(popup, alert, toast)
                noticePopupCol.setNoticeStopYn(noticePopupCol.getFd_nomore_yn());                                  // "오늘 하루 더 이상 보지 않음" 기능 유무 ('Y','N')
            }
            
            vo.setNoticeDtlList(noticePopupList);
            
        }catch(DataAccessException e) {
            e.printStackTrace();
            log.error(e.getMessage(), e);
            throw new APIException("Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        
        return vo;
    }
    
    /**
     * FAQ 리스트 조회
     * 
     * @param map
     * @return
     * @throws APIException
     */
    public NoticeVo selectFaqList(Map<String, Object> map) throws APIException{
        
        NoticeVo vo = new NoticeVo();
        
        CodeVo code = codeDao.selectCode("1407");     // FAQ url 코드 : 1407
        
        try {
            List<NoticeDtl> faqList = noticeDao.selectFaqList(map);
            
            vo.setTotCnt(noticeDao.selectFaqAllCnt(map));
            
            for(NoticeDtl faqCol : faqList) {
                
                faqCol.setFaqListId(faqCol.getPk_faq());                               // FAQ PK
                faqCol.setFaqTitle(faqCol.getFd_title());                              // FAQ 제목
                faqCol.setFaqCate(faqCol.getFd_cate_name());                           // FAQ 카테고리이름
                faqCol.setFaqUrl(code.getFd_name() + "?uid=" + faqCol.getPk_faq() + "&cpCode=" + map.get("cpCode").toString());    // FAQ url
                
                String regDt = "";
                regDt       += faqCol.getFd_regdate().substring(0, 4);
                regDt       += faqCol.getFd_regdate().substring(5, 7);
                regDt       += faqCol.getFd_regdate().substring(8, 10);
                regDt       += faqCol.getFd_regdate().substring(11, 13);
                regDt       += faqCol.getFd_regdate().substring(14, 16);
                faqCol.setFaqRegDt(regDt);                                             // FAQ 등록일
                
            }
            vo.setNoticeDtlList(faqList);
            
        }catch(DataAccessException e) {
            e.printStackTrace();
            log.error(e.getMessage(), e);
            throw new APIException("Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        
        return vo;
    }
}
