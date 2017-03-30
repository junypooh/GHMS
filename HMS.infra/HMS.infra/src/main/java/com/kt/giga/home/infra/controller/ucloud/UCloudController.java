package com.kt.giga.home.infra.controller.ucloud;

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.kt.giga.home.infra.domain.ucloud.AccountRequest;
import com.kt.giga.home.infra.domain.ucloud.AccountResponse;
import com.kt.giga.home.infra.domain.ucloud.TokenRequest;
import com.kt.giga.home.infra.domain.ucloud.TokenResponse;
import com.kt.giga.home.infra.service.ucloud.UCloudService;
import com.kt.giga.home.infra.util.JsonUtils;

@Controller
@RequestMapping("/ucloud")
public class UCloudController {

	@Autowired
	private UCloudService uCloudService;
	
	@RequestMapping(value={"/token"}, method=RequestMethod.POST)
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public TokenResponse postToken(@RequestBody TokenRequest req) throws Exception {
		TokenResponse res = (TokenResponse)uCloudService.postToken(req);
		
		return res;
	}
	
	@RequestMapping(value={"/token"}, method=RequestMethod.DELETE)
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public TokenResponse deleteToken(@RequestParam(value="authToken", required=true) String authToken,
			@RequestParam(value="caSaid", required=true) String caSaid) throws Exception {
		TokenRequest req = new TokenRequest();
		req.setAuthToken(authToken);
		req.setCaSaid(caSaid);
		
		TokenResponse res = (TokenResponse)uCloudService.deleteToken(req);
		
		return res;
	}
	
	@RequestMapping(value={"/account"}, method=RequestMethod.POST)
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public AccountResponse postAccount(@RequestBody AccountRequest req) throws Exception {
		AccountResponse res = (AccountResponse)uCloudService.postAccount(req);
		
		return res;
	}
	
	@RequestMapping(value={"/account"}, method=RequestMethod.DELETE)
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public AccountResponse deleteAccount(@RequestParam(value="loginId", required=true) String loginId,
			@RequestParam(value="cid", required=true) String cid,
			@RequestParam(value="saId", required=true) String saId) throws Exception {
		AccountRequest req = new AccountRequest();
		req.setLoginId(loginId);
		req.setCid(cid);
		req.setSaId(saId);
		
		AccountResponse res = (AccountResponse)uCloudService.deleteAccount(req);
		
		return res;
	}
	
	@RequestMapping(value={"/account"}, method=RequestMethod.PUT)
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public AccountResponse modifyAccount(@RequestParam(value="loginId", required=true) String loginId,
			@RequestParam(value="cid", required=true) String cid,
			@RequestParam(value="saId", required=true) String saId,
			@RequestParam(value="opType", required=true) String opType) throws Exception {
		AccountRequest req = new AccountRequest();
		req.setLoginId(loginId);
		req.setCid(cid);
		req.setSaId(saId);
		req.setOpType(opType);
		
		AccountResponse res = (AccountResponse)uCloudService.modifyAccount(req);
		
		return res;
	}
}
