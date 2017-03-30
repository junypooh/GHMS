package com.kt.giga.home.infra.controller.kpns;

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

import com.kt.giga.home.infra.domain.kpns.PushInfoRequest;
import com.kt.giga.home.infra.domain.kpns.PushInfoResponse;
import com.kt.giga.home.infra.service.kpns.KPNSService;
import com.kt.giga.home.infra.util.JsonUtils;

@Controller
@RequestMapping("/kpns")
public class KPNSController {
	
	@Autowired
	private KPNSService kpnsService;
	
	@RequestMapping(value={"/ohb/push"}, method=RequestMethod.POST)
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public PushInfoResponse push(@RequestBody PushInfoRequest req) throws Exception {
		PushInfoResponse res = (PushInfoResponse)kpnsService.push(req);
		
		return res;
	}
}
