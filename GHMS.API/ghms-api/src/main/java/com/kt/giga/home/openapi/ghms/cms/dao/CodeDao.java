/*******************************************************************************
 * Copyright(c) 2015 KT. All rights reserved.
 * This software is the proprietary information of KT.
 *******************************************************************************/
package com.kt.giga.home.openapi.ghms.cms.dao;

import com.kt.giga.home.openapi.ghms.cms.domain.vo.CodeVo;

/**
 * 
 * @author dahye.kim (dahye.kim@ceinside.co.kr)
 * @since 2015. 2. 10.
 */
public interface CodeDao {
    
    public CodeVo     selectCode(String code);

}
