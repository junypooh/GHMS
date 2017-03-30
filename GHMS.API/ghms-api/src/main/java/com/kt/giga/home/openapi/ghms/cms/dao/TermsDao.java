/*******************************************************************************
 * Copyright(c) 2015 KT. All rights reserved.
 * This software is the proprietary information of KT.
 *******************************************************************************/
package com.kt.giga.home.openapi.ghms.cms.dao;

import java.util.List;
import java.util.Map;

import com.kt.giga.home.openapi.ghms.cms.domain.key.TermsKey;
import com.kt.giga.home.openapi.ghms.cms.domain.vo.TermsVo;


/**
 * 
 * @author dahye.kim (dahye.kim@ceinside.co.kr)
 * @since 2015. 2. 10.
 */
public interface TermsDao {
    
    public List<TermsVo>  selectTermsList(Map<String, Object> map);
    
    public void           insertTermsAgree(TermsKey key);
    
    public void           insertParentsAgree(TermsKey key);
    
    public void 		updateTermsRetract(TermsKey key);

}
