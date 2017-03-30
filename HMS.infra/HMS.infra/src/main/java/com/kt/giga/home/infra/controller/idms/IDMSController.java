package com.kt.giga.home.infra.controller.idms;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.httpclient.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kt.giga.home.infra.domain.idms.SubscriptionInfoRequest;
import com.kt.giga.home.infra.domain.idms.SubscriptionInfoRequestList;
import com.kt.giga.home.infra.domain.idms.SubscriptionInfoResponse;
import com.kt.giga.home.infra.domain.idms.SubscriptionManagerInfoRequest;
import com.kt.giga.home.infra.service.idms.IDMSService;

@Controller
@RequestMapping("/idms")
public class IDMSController {

	@Autowired
	private IDMSService idmsService;

	@RequestMapping(value={"/ohb/subscriptions"}, method=RequestMethod.POST)
	public void subscribes(@RequestBody SubscriptionInfoRequestList list, HttpServletResponse httpResponse) throws Exception {
		idmsService.subscribes(list);
	}
	
	@RequestMapping(value={"/ohb/subscription"}, method=RequestMethod.POST)
	public @ResponseBody SubscriptionInfoResponse subscribe(@RequestBody SubscriptionInfoRequest req, HttpServletResponse httpResponse) throws Exception {
		SubscriptionInfoResponse res = (SubscriptionInfoResponse)idmsService.subscribe(req);
		
		if(res != null) {
			if(res.getStatusCode() != 0) {
				httpResponse.setStatus(res.getStatusCode());
			}
		}
		
		return res;
	}
	
	@RequestMapping(value={"/ohb/subscription"}, method=RequestMethod.GET)
	public @ResponseBody SubscriptionInfoRequest subscribe(@RequestParam(value="saId", required=false) String saId,
			@RequestParam(value="serviceNo", required=false) String serviceNo, 
			HttpServletResponse httpResponse) throws Exception {
		if((saId == null || "".equals(saId)) && (serviceNo == null || "".equals(serviceNo))) {
			httpResponse.setStatus(HttpStatus.SC_BAD_REQUEST);
			return null;
		}
		
		SubscriptionInfoRequest req = new SubscriptionInfoRequest();
		req.setSaId(saId);
		req.setServiceNo(serviceNo);
		
		SubscriptionInfoRequest res = (SubscriptionInfoRequest)idmsService.selectSubscription(req);
		
		if(res == null) {
			httpResponse.setStatus(HttpStatus.SC_NOT_FOUND);
		}
		
		return res;
	}
    
    @RequestMapping(value={"/ghms/subscription"}, method=RequestMethod.POST)
    public @ResponseBody SubscriptionInfoResponse ghmsSubscribe(@RequestBody SubscriptionManagerInfoRequest req, HttpServletResponse httpResponse) throws Exception {

        SubscriptionInfoResponse res = (SubscriptionInfoResponse)idmsService.ghmsSubscribe(req);
        
        if(res != null) {
            if(res.getStatusCode() != 0) {
                httpResponse.setStatus(res.getStatusCode());
            }
        }
        
        return res;
    }
    
    @RequestMapping(value={"/ghms/subscription"}, method=RequestMethod.GET)
    public @ResponseBody SubscriptionManagerInfoRequest ghmsSubscribe(@RequestParam(value="saId", required=false) String saId,
            @RequestParam(value="serviceNo", required=false) String serviceNo, 
            HttpServletResponse httpResponse) throws Exception {
        if((saId == null || "".equals(saId)) && (serviceNo == null || "".equals(serviceNo))) {
            httpResponse.setStatus(org.apache.commons.httpclient.HttpStatus.SC_BAD_REQUEST);
            return null;
        }
        
        SubscriptionManagerInfoRequest req = new SubscriptionManagerInfoRequest();
        req.setSaId(saId);
        req.setServiceNo(serviceNo);
        
        SubscriptionManagerInfoRequest res = (SubscriptionManagerInfoRequest)idmsService.selectSubscriptionManager(req);
        
        return res;
    }
}
