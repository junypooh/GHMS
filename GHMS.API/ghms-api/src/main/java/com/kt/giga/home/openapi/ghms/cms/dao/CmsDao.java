/*******************************************************************************
 * Copyright(c) 2015 KT. All rights reserved.
 * This software is the proprietary information of KT.
 *******************************************************************************/
package com.kt.giga.home.openapi.ghms.cms.dao;

import java.util.List;
import java.util.Map;

import com.kt.giga.home.openapi.ghms.cms.domain.vo.CmsVo;

/**
 * 
 * @author dahye.kim (dahye.kim@ceinside.co.kr)
 * @since 2015. 2. 23.
 */
public interface CmsDao {
    
    /**
     * 코치가이드 리스트 조회
     * 
     * @param map
     * @return
     */
    public List<CmsVo>     selectGuideCoach(Map<String, Object> map);
    
    /**
     * 웰컴가이드 리스트 조회
     * 
     * @param map
     * @return
     */
    public CmsVo           selectGuideWelcome(Map<String, Object> map);
    
    /**
     * App 최신버전 조회
     * 
     * @param map
     * @return
     */
    public CmsVo           selectVerAppLast(Map<String, Object> map);
    
    /**
     * 펌웨어 최신버전 조회
     * 
     * @param map
     * @return
     */
    public CmsVo           selectVerFrmwrLast(Map<String, Object> map);

}
