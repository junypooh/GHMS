/*******************************************************************************
 * Copyright(c) 2015 KT. All rights reserved.
 * This software is the proprietary information of KT.
 *******************************************************************************/
package com.kt.giga.home.openapi.ghms.cms.domain.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * 
 * @author junypooh ( kyungjun.park@ceinside.co.kr )
 * @since 2015. 2. 6.
 */
@JsonInclude(Include.NON_EMPTY)
public class NoticeDtl {
	
	private String noticeUrl;
	
	private String noticeListId;
	
	/** 공지사항 pk */
	private String pk_notice;
	
	/** 제목 */
	private String fd_title;
	
	/** 등록일 */
	private String fd_regdate;
	
	private String noticeRegDt;
	
	private String noticeStart;
	
	private String noticeEnd;
	
	private String noticeKind;
	
	private String noticeStopYn;
	
	private String noticeTitle;
	
	/** PM공지 pk */
	private String pk_notice_pm;
	
	/** PM공지 시작일지 */
	private String fd_start_time;
	
	/** PM공지 종료일시 */
	private String fd_end_time;
    
    /* FAQ */
    /** FAQ 상세 url */
    private String faqUrl;
    
    /** FAQ 목록 id */
    private String faqListId; 
    
    /** FAQ 제목 */
    private String faqTitle; 
    
    /** FAQ 카테고리 */
    private String faqCate; 
    
    /** FAQ 등록일 */
    private String faqRegDt; 
    
    
    /* FAQ DB 칼럼 */
    /** FAQ pk */
    private String pk_faq; 
    
    /** 서비스 구분 코드 */
    private String fd_cp_code; 
    
    /** FAQ 카테고리 */
    private String fd_faq_code;
    
    /** 내용 */
    private String fd_contents; 
    
    /** 게시 여부 yn */
    private String fd_open_yn; 
    
    /** 등록자 id */
    private String fd_writer_id; 
    
    /** 등록자 ip */
    private String fd_writer_ip;
    
    /** 수정자 id */
    private String fd_modifier_id; 
    
    /** 수정자 ip */
    private String fd_modifier_ip;    
    
    /** 수정일 */
    private String fd_update_date;
    
    private String fd_cate_name;
    
    /* 팝업공지 DB 칼럼 */
    /** 팝업공지 pk */
    private String  pk_notice_popup; 
    
    /** 팝업공지 메시지창 타입 */
    private String  fd_popup_type;
    
    /** 하루동안 보지않기 기능 유무YN */
    private String  fd_nomore_yn; 
    

    /**
     * @return TODO
     */
    public String getNoticeUrl() {
        return noticeUrl;
    }

    /**
     * @return TODO
     */
    public String getPk_notice() {
        return pk_notice;
    }

    /**
     * @return TODO
     */
    public String getFd_title() {
        return fd_title;
    }

    /**
     * @return TODO
     */
    public String getFd_regdate() {
        return fd_regdate;
    }

    /**
     * @return TODO
     */
    public String getNoticeStart() {
        return noticeStart;
    }

    /**
     * @return TODO
     */
    public String getNoticeEnd() {
        return noticeEnd;
    }

    /**
     * @return TODO
     */
    public String getNoticeKind() {
        return noticeKind;
    }

    /**
     * @return TODO
     */
    public String getNoticeStopYn() {
        return noticeStopYn;
    }

    /**
     * @param noticeUrl TODO
     */
    public void setNoticeUrl(String noticeUrl) {
        this.noticeUrl = noticeUrl;
    }

    /**
     * @param pk_notice TODO
     */
    public void setPk_notice(String pk_notice) {
        this.pk_notice = pk_notice;
    }

    /**
     * @param fd_title TODO
     */
    public void setFd_title(String fd_title) {
        this.fd_title = fd_title;
    }

    /**
     * @param fd_regdate TODO
     */
    public void setFd_regdate(String fd_regdate) {
        this.fd_regdate = fd_regdate;
    }

    /**
     * @param noticeStart TODO
     */
    public void setNoticeStart(String noticeStart) {
        this.noticeStart = noticeStart;
    }

    /**
     * @param noticeEnd TODO
     */
    public void setNoticeEnd(String noticeEnd) {
        this.noticeEnd = noticeEnd;
    }

    /**
     * @param noticeKind TODO
     */
    public void setNoticeKind(String noticeKind) {
        this.noticeKind = noticeKind;
    }

    /**
     * @param noticeStopYn TODO
     */
    public void setNoticeStopYn(String noticeStopYn) {
        this.noticeStopYn = noticeStopYn;
    }

    /**
     * @return TODO
     */
    public String getPk_notice_pm() {
        return pk_notice_pm;
    }

    /**
     * @param pk_notice_pm TODO
     */
    public void setPk_notice_pm(String pk_notice_pm) {
        this.pk_notice_pm = pk_notice_pm;
    }

    /**
     * @return TODO
     */
    public String getFd_start_time() {
        return fd_start_time;
    }

    /**
     * @return TODO
     */
    public String getFd_end_time() {
        return fd_end_time;
    }

    /**
     * @param fd_start_time TODO
     */
    public void setFd_start_time(String fd_start_time) {
        this.fd_start_time = fd_start_time;
    }

    /**
     * @param fd_end_time TODO
     */
    public void setFd_end_time(String fd_end_time) {
        this.fd_end_time = fd_end_time;
    }

    /**
     * @return TODO
     */
    public String getNoticeListId() {
        return noticeListId;
    }

    /**
     * @param noticeListId TODO
     */
    public void setNoticeListId(String noticeListId) {
        this.noticeListId = noticeListId;
    }

    /**
     * @return TODO
     */
    public String getFaqUrl() {
        return faqUrl;
    }

    /**
     * @return TODO
     */
    public String getFaqListId() {
        return faqListId;
    }

    /**
     * @return TODO
     */
    public String getFaqTitle() {
        return faqTitle;
    }

    /**
     * @return TODO
     */
    public String getFaqCate() {
        return faqCate;
    }

    /**
     * @return TODO
     */
    public String getFaqRegDt() {
        return faqRegDt;
    }

    /**
     * @return TODO
     */
    public String getPk_faq() {
        return pk_faq;
    }

    /**
     * @return TODO
     */
    public String getFd_cp_code() {
        return fd_cp_code;
    }

    /**
     * @return TODO
     */
    public String getFd_faq_code() {
        return fd_faq_code;
    }

    /**
     * @return TODO
     */
    public String getFd_contents() {
        return fd_contents;
    }

    /**
     * @return TODO
     */
    public String getFd_open_yn() {
        return fd_open_yn;
    }

    /**
     * @return TODO
     */
    public String getFd_writer_id() {
        return fd_writer_id;
    }

    /**
     * @return TODO
     */
    public String getFd_writer_ip() {
        return fd_writer_ip;
    }

    /**
     * @return TODO
     */
    public String getFd_modifier_id() {
        return fd_modifier_id;
    }

    /**
     * @return TODO
     */
    public String getFd_modifier_ip() {
        return fd_modifier_ip;
    }

    /**
     * @return TODO
     */
    public String getFd_update_date() {
        return fd_update_date;
    }

    /**
     * @param faqUrl TODO
     */
    public void setFaqUrl(String faqUrl) {
        this.faqUrl = faqUrl;
    }

    /**
     * @param faqListId TODO
     */
    public void setFaqListId(String faqListId) {
        this.faqListId = faqListId;
    }

    /**
     * @param faqTitle TODO
     */
    public void setFaqTitle(String faqTitle) {
        this.faqTitle = faqTitle;
    }

    /**
     * @param faqCate TODO
     */
    public void setFaqCate(String faqCate) {
        this.faqCate = faqCate;
    }

    /**
     * @param faqRegDt TODO
     */
    public void setFaqRegDt(String faqRegDt) {
        this.faqRegDt = faqRegDt;
    }

    /**
     * @param pk_faq TODO
     */
    public void setPk_faq(String pk_faq) {
        this.pk_faq = pk_faq;
    }

    /**
     * @param fd_cp_code TODO
     */
    public void setFd_cp_code(String fd_cp_code) {
        this.fd_cp_code = fd_cp_code;
    }

    /**
     * @param fd_faq_code TODO
     */
    public void setFd_faq_code(String fd_faq_code) {
        this.fd_faq_code = fd_faq_code;
    }

    /**
     * @param fd_contents TODO
     */
    public void setFd_contents(String fd_contents) {
        this.fd_contents = fd_contents;
    }

    /**
     * @param fd_open_yn TODO
     */
    public void setFd_open_yn(String fd_open_yn) {
        this.fd_open_yn = fd_open_yn;
    }

    /**
     * @param fd_writer_id TODO
     */
    public void setFd_writer_id(String fd_writer_id) {
        this.fd_writer_id = fd_writer_id;
    }

    /**
     * @param fd_writer_ip TODO
     */
    public void setFd_writer_ip(String fd_writer_ip) {
        this.fd_writer_ip = fd_writer_ip;
    }

    /**
     * @param fd_modifier_id TODO
     */
    public void setFd_modifier_id(String fd_modifier_id) {
        this.fd_modifier_id = fd_modifier_id;
    }

    /**
     * @param fd_modifier_ip TODO
     */
    public void setFd_modifier_ip(String fd_modifier_ip) {
        this.fd_modifier_ip = fd_modifier_ip;
    }

    /**
     * @param fd_update_date TODO
     */
    public void setFd_update_date(String fd_update_date) {
        this.fd_update_date = fd_update_date;
    }

    /**
     * @return TODO
     */
    public String getPk_notice_popup() {
        return pk_notice_popup;
    }

    /**
     * @return TODO
     */
    public String getFd_popup_type() {
        return fd_popup_type;
    }

    /**
     * @return TODO
     */
    public String getFd_nomore_yn() {
        return fd_nomore_yn;
    }

    /**
     * @param pk_notice_popup TODO
     */
    public void setPk_notice_popup(String pk_notice_popup) {
        this.pk_notice_popup = pk_notice_popup;
    }

    /**
     * @param fd_popup_type TODO
     */
    public void setFd_popup_type(String fd_popup_type) {
        this.fd_popup_type = fd_popup_type;
    }

    /**
     * @param fd_nomore_yn TODO
     */
    public void setFd_nomore_yn(String fd_nomore_yn) {
        this.fd_nomore_yn = fd_nomore_yn;
    }

    /**
     * @return TODO
     */
    public String getNoticeTitle() {
        return noticeTitle;
    }

    /**
     * @param noticeTitle TODO
     */
    public void setNoticeTitle(String noticeTitle) {
        this.noticeTitle = noticeTitle;
    }

    /**
     * @return TODO
     */
    public String getNoticeRegDt() {
        return noticeRegDt;
    }

    /**
     * @param noticeRegDt TODO
     */
    public void setNoticeRegDt(String noticeRegDt) {
        this.noticeRegDt = noticeRegDt;
    }

    /**
     * @return TODO
     */
    public String getFd_cate_name() {
        return fd_cate_name;
    }

    /**
     * @param fd_cate_name TODO
     */
    public void setFd_cate_name(String fd_cate_name) {
        this.fd_cate_name = fd_cate_name;
    }
    
    

}
