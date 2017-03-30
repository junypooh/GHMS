package com.kt.giga.home.infra.controller.ucems;

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

import com.google.gson.Gson;
import com.kt.giga.home.infra.domain.sdp.LoginInfoRequest;
import com.kt.giga.home.infra.domain.sdp.LoginInfoResponse;
import com.kt.giga.home.infra.domain.sdp.PartyAndSubInfoRequest;
import com.kt.giga.home.infra.domain.sdp.PartyAndSubInfoResponse;
import com.kt.giga.home.infra.domain.ucems.ApplyNewFirmInfoRequest;
import com.kt.giga.home.infra.domain.ucems.ApplyNewFirmInfoResponse;
import com.kt.giga.home.infra.domain.ucems.GetNewFirmInfoRequest;
import com.kt.giga.home.infra.domain.ucems.GetNewFirmInfoResponse;
import com.kt.giga.home.infra.domain.ucems.StatusNewFirmInfoRequest;
import com.kt.giga.home.infra.domain.ucems.StatusNewFirmInfoResponse;
import com.kt.giga.home.infra.service.sdp.SDPService;
import com.kt.giga.home.infra.service.ucems.UCEMSService;
import com.kt.giga.home.infra.util.JsonUtils;

@Controller
@RequestMapping("/ucems")
public class UCEMSController {

	@Autowired
	private UCEMSService ucemsService;

	@RequestMapping(value={"/get"}, method=RequestMethod.POST)
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody GetNewFirmInfoResponse get(@RequestBody GetNewFirmInfoRequest req) throws Exception {
		GetNewFirmInfoResponse res = (GetNewFirmInfoResponse)ucemsService.get(req);
		
		return res;
	}
	
	@RequestMapping(value={"/apply"}, method=RequestMethod.POST)
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody ApplyNewFirmInfoResponse  apply(@RequestBody ApplyNewFirmInfoRequest req) throws Exception {
		ApplyNewFirmInfoResponse res = (ApplyNewFirmInfoResponse)ucemsService.apply(req);
		
		return res;
	}
	
	@RequestMapping(value={"/status"}, method=RequestMethod.POST)
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody StatusNewFirmInfoResponse status(@RequestBody StatusNewFirmInfoRequest req) throws Exception {
		StatusNewFirmInfoResponse res = (StatusNewFirmInfoResponse)ucemsService.status(req);
		
		return res;
	}
}
