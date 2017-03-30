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

import com.kt.giga.home.openapi.ghms.cms.dao.CmsDao;
import com.kt.giga.home.openapi.ghms.cms.dao.CodeDao;
import com.kt.giga.home.openapi.ghms.cms.domain.vo.CmsVo;
import com.kt.giga.home.openapi.ghms.cms.domain.vo.CodeVo;
import com.kt.giga.home.openapi.ghms.util.exception.APIException;

/**
 * 
 * @author dahye.kim (dahye.kim@ceinside.co.kr)
 * @since 2015. 2. 23.
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
public class CmsService {
    private Logger log = LoggerFactory.getLogger(this.getClass());
    
    @Autowired
    private CodeDao codeDao;
    
    @Autowired
    private CmsDao cmsDao;
    
    
    /**
     * 코치가이드 조회
     * 
     * @param map
     * @return
     * @throws APIException
     */
    public List<CmsVo> selectGuideCoach(Map<String, Object> map) throws APIException{
        
        CodeVo code = codeDao.selectCode("1411");       // 코치가이드 url 코드 : 1411
        
        List<CmsVo> guideCoachList = new ArrayList<CmsVo>();
        
        try {
            
            guideCoachList = cmsDao.selectGuideCoach(map);
            
            for(CmsVo listCol : guideCoachList) {
                
                listCol.setTitle(listCol.getFd_title());                                     // 제목
                listCol.setVerNum(listCol.getFd_ver_num());                                  // 버전 정보
                listCol.setPosition(listCol.getFd_position_code());                          // 가이드 노출 위치
                listCol.setImgUrl_1(code.getFd_name() + listCol.getFd_file_path_1());        // 가이드 이미지 url 1
                listCol.setImgUrl_2(code.getFd_name() + listCol.getFd_file_path_2());        // 가이드 이미지 url 2
                listCol.setImgUrl_3(code.getFd_name() + listCol.getFd_file_path_3());        // 가이드 이미지 url 3
            }
            
        } catch(DataAccessException e) {
            e.printStackTrace();
            log.error(e.getMessage(), e);
            throw new APIException("Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        
        return guideCoachList;
    }
    
    /**
     * 웰컴가이드 조회
     * 
     * @param map
     * @return
     * @throws APIException
     */
    public CmsVo selectGuideWelcome(Map<String, Object> map) throws APIException{
        
        CodeVo code = codeDao.selectCode("1412");       // 웰컴가이드 url 코드 : 1412
        
        CmsVo vo = new CmsVo();
        
        try {
            vo = cmsDao.selectGuideWelcome(map);
            
            if(vo != null){
                vo.setVerNum(vo.getFd_ver_num());                               // 버전 정보
                vo.setImgUrl_1(code.getFd_name() + vo.getFd_file_path_1());     // 가이드 이미지 url 1
                vo.setImgUrl_2(code.getFd_name() + vo.getFd_file_path_2());     // 가이드 이미지 url 2
                vo.setImgUrl_3(code.getFd_name() + vo.getFd_file_path_3());     // 가이드 이미지 url 3
            }
            
        } catch(DataAccessException e) {
            e.printStackTrace();
            log.error(e.getMessage(), e);
            throw new APIException("Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        
        return vo;
    }
    
    /**
     * App 최신 버전 조회
     * 
     * @param map
     * @return
     * @throws APIException
     */
    public CmsVo selectVerAppLast(Map<String, Object> map) throws APIException{
        
        CmsVo vo = new CmsVo();
        
        try {
            
            vo = cmsDao.selectVerAppLast(map);
            
            if(vo != null) {
                vo.setVerNum(vo.getFd_ver_num());               // 최신 버전
                vo.setVerMemo(vo.getFd_ver_memo());             // 버전 설명
                vo.setVerMandatoryYn(vo.getFd_mandatory_yn());  // 업데이트 필수여부 YN
            }
            
            
        } catch(DataAccessException e) {
            e.printStackTrace();
            log.error(e.getMessage(), e);
            throw new APIException("Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        
        return vo;
    }
    
    /**
     * 펌웨어 최신버전 조회
     * 
     * @param map
     * @return
     * @throws APIException
     */
    public CmsVo selectVerFrmwrLast(Map<String, Object> map) throws APIException{
        
        CmsVo vo = new CmsVo();
        
        try {
            vo = cmsDao.selectVerFrmwrLast(map);
            
            if(vo != null) {
                vo.setVerNum(vo.getFd_ver_num());               // 최신 버전
                vo.setVerMemo(vo.getFd_ver_title());            // 버전 설명
                vo.setVerMandatoryYn(vo.getFd_mandatory_yn());  // 업데이트 필수여부 YN
                vo.setModelName(vo.getFd_model_name());         // 모델명
            }
            
        }catch(DataAccessException e) {
            e.printStackTrace();
            log.error(e.getMessage(), e);
            throw new APIException("Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        
        return vo;
    }
}
