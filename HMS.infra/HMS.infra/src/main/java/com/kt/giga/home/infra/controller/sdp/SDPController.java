package com.kt.giga.home.infra.controller.sdp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.kt.giga.home.infra.client.host.GhmsSDPServerInfo;
import com.kt.giga.home.infra.client.host.SDPServerInfo;
import com.kt.giga.home.infra.domain.sdp.LoginInfoRequest;
import com.kt.giga.home.infra.domain.sdp.LoginInfoResponse;
import com.kt.giga.home.infra.domain.sdp.PartyAndSubInfoRequest;
import com.kt.giga.home.infra.domain.sdp.PartyAndSubInfoResponse;
import com.kt.giga.home.infra.domain.sdp.SpecificSubsAndUserInfoRequest;
import com.kt.giga.home.infra.domain.sdp.SpecificSubsAndUserInfoResponse;
import com.kt.giga.home.infra.domain.sdp.SpecificSubscpnInfoRequest;
import com.kt.giga.home.infra.domain.sdp.SpecificSubscpnInfoResponse;
import com.kt.giga.home.infra.service.sdp.SDPService;
import com.kt.giga.home.infra.service.silk.OllehRtrmmbService;

@Controller
@RequestMapping("/sdp")
public class SDPController {

	@Autowired
	private SDPService sdpService;
	
	@Autowired
	private OllehRtrmmbService ollehRtrmmbService;
	
	@Autowired
	private SDPServerInfo serverInfo;
	
	@Autowired
	private GhmsSDPServerInfo ghmsSdpServerInfo;
	
	@RequestMapping(value={"/login"}, method=RequestMethod.POST)
	public @ResponseBody LoginInfoResponse login(@RequestBody LoginInfoRequest loginReq) throws Exception {	
		LoginInfoResponse res = (LoginInfoResponse)sdpService.login(loginReq, serverInfo);
		
		return res;
	}
	
	@RequestMapping(value={"/subscription"}, method=RequestMethod.POST)
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody PartyAndSubInfoResponse subscription(@RequestBody PartyAndSubInfoRequest req) throws Exception {
		PartyAndSubInfoResponse res = (PartyAndSubInfoResponse)sdpService.subscription(req, serverInfo);
		
		return res;
	}
	
	@RequestMapping(value={"ghms/login"}, method=RequestMethod.POST)
	public @ResponseBody LoginInfoResponse ghmsLogin(@RequestBody LoginInfoRequest loginReq) throws Exception {	
		LoginInfoResponse res = (LoginInfoResponse)sdpService.login(loginReq, ghmsSdpServerInfo);
		
		return res;
	}
	
	@RequestMapping(value={"ghms/subscription"}, method=RequestMethod.POST)
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody PartyAndSubInfoResponse ghmsSubscription(@RequestBody PartyAndSubInfoRequest req) throws Exception {
		PartyAndSubInfoResponse res = (PartyAndSubInfoResponse)sdpService.subscription(req, ghmsSdpServerInfo);
		
		return res;
	}
	
	@RequestMapping(value={"ghms/getSpecificSubscpnInfo"}, method=RequestMethod.POST)
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody SpecificSubscpnInfoResponse ghmsGetSpecificSubscpnInfo(@RequestBody SpecificSubscpnInfoRequest req) throws Exception {
		SpecificSubscpnInfoResponse res = (SpecificSubscpnInfoResponse)sdpService.getSpecificSubscpnInfo(req, ghmsSdpServerInfo);
		
		return res;
	}
	
	@RequestMapping(value={"ghms/getSpecificSubsAndUserInfo"}, method=RequestMethod.POST)
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody SpecificSubsAndUserInfoResponse ghmsGetSpecificSubsAndUserInfo(@RequestBody SpecificSubsAndUserInfoRequest req) throws Exception {
		SpecificSubsAndUserInfoResponse res = (SpecificSubsAndUserInfoResponse)sdpService.getSpecificSubsAndUserInfo(req, ghmsSdpServerInfo);
		
		return res;
	}
	
	@RequestMapping(value={"/olleh"}, method=RequestMethod.POST)
	public @ResponseBody String olleh() throws Exception {
		ollehRtrmmbService.setOllehRtrmmb();
		return "{}";
	}
}
