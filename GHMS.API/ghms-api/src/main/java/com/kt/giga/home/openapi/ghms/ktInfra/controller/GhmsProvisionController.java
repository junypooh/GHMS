/*******************************************************************************
 * Copyright(c) 2015 KT. All rights reserved.
 * This software is the proprietary information of KT.
 *******************************************************************************/
package com.kt.giga.home.openapi.ghms.ktInfra.controller;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.kt.giga.home.openapi.ghms.common.GHmsConstants.CommonConstants;
import com.kt.giga.home.openapi.ghms.ktInfra.domain.key.GhmsProvisionKey;
import com.kt.giga.home.openapi.ghms.ktInfra.service.GhmsProvisionService;
import com.kt.giga.home.openapi.ghms.util.exception.APIException;
import com.kt.giga.home.openapi.ghms.util.json.JsonUtils;

/**
 * 
 * @author dahye.kim (dahye.kim@ceinside.co.kr)
 * @since 2015. 3. 10.
 */
@Controller
@RequestMapping("infra")
public class GhmsProvisionController {
	
	private Logger log = LoggerFactory.getLogger(this.getClass());
    
    @Autowired
    private GhmsProvisionService ghmsProvisionService;
    

    @RequestMapping(value={"ghmsSubscription"}, method=RequestMethod.POST, produces=CommonConstants.PRODUCES_JSON)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Map<String, Object> ghmsSubscription(
            @RequestBody                        HashMap<String, String> params) throws APIException {

    	log.info("------------------- setProvision---------------------" );
		log.info("\n" + JsonUtils.toPrettyJson(params));
		
        GhmsProvisionKey key = new GhmsProvisionKey();
        key.setSaId(params.get("saId"));
        key.setIntfCode(params.get("intfCode"));
        key.setProdCode(params.get("prodCode"));
        key.setProdDtlCode(params.get("prodDtlCode"));
        key.setWoNo(params.get("woNo"));
        key.setInsDate(params.get("insDate"));
        key.setCustid(params.get("custid"));
        key.setCustName(params.get("custName"));
        key.setGwModelName(params.get("gwModelName"));
        key.setGwModelCd(params.get("gwModelCd"));
        key.setGwSaid(params.get("gwSaid"));
        key.setGwMac(params.get("gwMac"));
        key.setGwSerial(params.get("gwSerial"));
        key.setGwSecret(params.get("gwSecret"));
        key.setGwCode(params.get("gwCode"));
        key.setIdmsInsDate(params.get("idmsInsDate"));
        key.setDataReset(params.get("dataReset"));
        key.setRollbackState(params.get("rollbackState"));
        key.setServiceNo(params.get("serviceNo"));
        
        return ghmsProvisionService.setProvision(key);
    }

}
