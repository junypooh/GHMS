/*******************************************************************************
 * Copyright(c) 2015 KT. All rights reserved.
 * This software is the proprietary information of KT.
 *******************************************************************************/
package com.kt.giga.home.openapi.ghms.cms.dao;

import java.util.List;
import java.util.Map;

import com.kt.giga.home.openapi.ghms.cms.domain.vo.NoticeDtl;


/**
 * 
 * @author dahye.kim (dahye.kim@ceinside.co.kr)
 * @since 2015. 2. 10.
 */
public interface NoticeDao {
    
    /**
     * 일반공지 리스트 조회
     * 
     * @param map
     * @return
     */
    public List<NoticeDtl>  selectNoticeList(Map<String, Object> map);
    
    /**
     * 일반공지 리스트 총 갯수
     * 
     * @param map
     * @return
     */
    public int              selectNoticeAllCnt(Map<String, Object> map);
    
    /**
     * PM공지 리스트 조회
     * 
     * @param map
     * @return
     */
    public List<NoticeDtl>  selectNoticePmList(Map<String, Object> map);
    
    /**
     * PM공지 리스트 총 갯수
     * 
     * @param map
     * @return
     */
    public int              selectNoticePmAllCnt(Map<String, Object> map);
    
    /**
     * 팝업공지 리스트 조회
     * 
     * @param map
     * @return
     */
    public List<NoticeDtl>  selectNoticePopupList(Map<String, Object> map);
    
    /**
     * 팝업공지 리스트 총 갯수
     * 
     * @param map
     * @return
     */
    public int              selectNoticePopupAllCnt(Map<String, Object> map);
    
    /**
     * FAQ 리스트 조회
     * 
     * @param map
     * @return
     */
    public List<NoticeDtl>  selectFaqList(Map<String, Object> map);
    
    /**
     * FAQ 리스트 총 갯수
     * 
     * @param map
     * @return
     */
    public int              selectFaqAllCnt(Map<String, Object> map);

}
