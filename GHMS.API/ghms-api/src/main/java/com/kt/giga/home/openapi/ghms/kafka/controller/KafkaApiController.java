/*******************************************************************************
 * Copyright(c) 2015 KT. All rights reserved.
 * This software is the proprietary information of KT.
 *******************************************************************************/
package com.kt.giga.home.openapi.ghms.kafka.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.kt.giga.home.openapi.ghms.common.GHmsConstants.CommonConstants;
import com.kt.giga.home.openapi.ghms.kafka.entity.HbaseControlHistory;
import com.kt.giga.home.openapi.ghms.kafka.service.HbaseControlHistoryService;
import com.kt.giga.home.openapi.ghms.kafka.service.KafkaMbrPwdService;
import com.kt.giga.home.openapi.ghms.kafka.service.KafkaSpotDevService;
import com.kt.giga.home.openapi.ghms.util.exception.APIException;

@Controller
public class KafkaApiController {
	
	@Autowired HbaseControlHistoryService hbaseControlHistoryService;
	@Autowired KafkaSpotDevService kafkaSpotDevService;
	@Autowired KafkaMbrPwdService kafkaMbrPwdService;
	
	@RequestMapping(value={"list/device/openandclosesensor/on"}, produces=CommonConstants.PRODUCES_JSON)
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public List<Map<String, Object>> openAndCloseSensorOnList(
			  @RequestParam(value="svcTgtSeq", required=false) Long svcTgtSeq 
			, @RequestParam(value="spotDevSeq", required=false) Long spotDevSeq) {
		return kafkaSpotDevService.selectOpenAndCloseSensorOnList(svcTgtSeq, spotDevSeq);
	}
	
	@RequestMapping(value={"one/device/devid/spotdevseq"}, produces=CommonConstants.PRODUCES_JSON)
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public Long spotDevSeq(
			  @RequestParam(value="svcTgtSeq") Long svcTgtSeq 
			, @RequestParam(value="spotDevId") String spotDevId) {
		return kafkaSpotDevService.selectSpotDevSeq(svcTgtSeq, spotDevId);
	}
	
	
	@RequestMapping(value={"test/1"}, method=RequestMethod.GET, produces=CommonConstants.PRODUCES_JSON)
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public Map<String, Object> test1(
			@RequestParam(value="serviceNo")	Long svcTgtSeq,
			@RequestParam(value="reqSeq", required=false)		String startRowKey,
			@RequestParam(value="devUUID", required=false)		Long devUuid,
			@RequestParam(value="limitCnt")		Integer limitCnt,
			HttpServletResponse response) throws APIException {
		
		return hbaseControlHistoryService.findList(limitCnt, svcTgtSeq, devUuid);
	}
		
}
