package com.kt.giga.home.openapi.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.xml.ws.http.HTTPException;

import org.apache.http.HttpException;
import org.apache.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.kt.giga.home.openapi.domain.HomeProvision;
import com.kt.giga.home.openapi.service.HomeProvisionService;
import com.kt.giga.home.openapi.util.JsonUtils;

@Controller
@RequestMapping("/")
public class HomeProvisionController {
	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	private static final String PRODUCES_JSON = "application/json; charset=UTF-8";

	@Autowired
	private HomeProvisionService homeProvisionService;

	@RequestMapping(value={"infra/subscription"}, method=RequestMethod.POST, produces=PRODUCES_JSON)
	@ResponseBody
	public Map<String, Object> subscription(@RequestBody HashMap<String, String> params,
			HttpServletResponse httpResponse) throws Exception {
		
		log.info("\n" + JsonUtils.toPrettyJson(params));
		
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			HomeProvision home = new HomeProvision();
			home.setSaId(params.get("saId"));
			home.setIntfCode(params.get("intfCode"));
			home.setProdCode(params.get("prodCode"));
			home.setProdDtlCode(params.get("prodDtlCode"));
			home.setWoNo(params.get("woNo"));
			home.setInsDate(params.get("insDate"));
			home.setCustid(params.get("custid"));
			home.setCustName(params.get("custName"));
			home.setCctvModelName(params.get("cctvModelName"));
			home.setCctvModelCd(params.get("cctvModelCd"));
			home.setCctvSaid(params.get("cctvSaid"));
			home.setCctvMac(params.get("cctvMac"));
			home.setCctvSerial(params.get("cctvSerial"));
			home.setCctvSecret(params.get("cctvSecret"));
			home.setCctvCode(params.get("cctvCode"));
			home.setCctvLoc(params.get("cctvLoc"));
			home.setIdmsInsDate(params.get("idmsInsDate"));
			home.setDataReset(params.get("dataReset"));
			home.setRollbackState(params.get("rollbackState"));
	
			result =  homeProvisionService.setProvision(home);			
			
		} catch(HTTPException e) {
			result.put("resultCode", HomeProvisionService.RESULT_FAIL);
		} catch(Exception e) {
			result.put("resultCode", HomeProvisionService.RESULT_FAIL);
		}
		
		return result;
	}
}
