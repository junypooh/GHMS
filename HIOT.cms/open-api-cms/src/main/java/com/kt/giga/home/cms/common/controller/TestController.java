package com.kt.giga.home.cms.common.controller;

import java.util.*;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;
import org.apache.hadoop.hbase.client.HConnection;

//import com.kt.iot.store.HBaseStore;
//import com.kt.iot.store.vo.CollectEvent;
//import com.kt.iot.store.vo.LogEvent;
import com.kt.giga.home.cms.util.*;

@Controller
public class TestController {
	
	@RequestMapping(value="/test", params="eventType=LogEvent")
	public String test1(Model model, @RequestParam Map<String, String> searchInfo) throws Exception {
		
/*		String svcTgtSeqStr = searchInfo.get("svcTgtSeq") != null ? searchInfo.get("svcTgtSeq") : "104001";
		String spotDevSeqStr = searchInfo.get("spotDevSeq") != null ? searchInfo.get("spotDevSeq") : "1";
		String remoteHost = searchInfo.get("remoteHost") != null ? searchInfo.get("remoteHost") : "172.16.1.220";
		String remotePort = searchInfo.get("remotePort") != null ? searchInfo.get("remotePort") : "2181";*/
		
		
//		long startDt = 0;//System.currentTimeMillis()- (100*1000);
//		long endDt = System.currentTimeMillis();		
//		
//		String svcTgtSeqStr = searchInfo.get("svcTgtSeq") != null ? searchInfo.get("svcTgtSeq") : "104001";
//		String spotDevSeqStr = searchInfo.get("spotDevSeq") != null ? searchInfo.get("spotDevSeq") : "1";
//		String remoteHost = searchInfo.get("remoteHost") != null ? searchInfo.get("remoteHost") : "121.156.46.136";
//		String remotePort = searchInfo.get("remotePort") != null ? searchInfo.get("remotePort") : "8087";		
//		
//		long svcTgtSeq = Long.parseLong(svcTgtSeqStr);
//		long spotDevSeq = Long.parseLong(spotDevSeqStr);
//		
//		List<String> eventList = new ArrayList<>();		
//		
//		HConnection conn=null;
//		try {
//			conn=HBaseStore.getConnection(remoteHost, remotePort);
//			List<LogEvent> rows = HBaseStore.scanLogEvent(conn, svcTgtSeq, spotDevSeq, startDt , endDt, 100);
//			for (Iterator it = rows.iterator(); it.hasNext();) {
//				LogEvent event = (LogEvent) it.next();
//				System.out.println(event); 
//				eventList.add(ObjectConverter.toJSONString(event));
//			}
//		} finally {
//			if(conn!=null) 
//				conn.close();
//		}
//		
//		model.addAttribute("eventList", eventList);
		
		return "/test";		
	}
	
	@RequestMapping(value="/test", params="eventType=CollectEvent")
	public String test2(Model model, @RequestParam Map<String, String> searchInfo) throws Exception {
		
/*		String svcTgtSeqStr = searchInfo.get("svcTgtSeq") != null ? searchInfo.get("svcTgtSeq") : "104001";
		String spotDevSeqStr = searchInfo.get("spotDevSeq") != null ? searchInfo.get("spotDevSeq") : "1";
		String remoteHost = searchInfo.get("remoteHost") != null ? searchInfo.get("remoteHost") : "172.16.1.220";
		String remotePort = searchInfo.get("remotePort") != null ? searchInfo.get("remotePort") : "2181";
		
		long startDt = 0;//System.currentTimeMillis()- (100*1000);
		long endDt = System.currentTimeMillis();			
		
		String svcTgtSeqStr = searchInfo.get("svcTgtSeq") != null ? searchInfo.get("svcTgtSeq") : "104001";
		String spotDevSeqStr = searchInfo.get("spotDevSeq") != null ? searchInfo.get("spotDevSeq") : "1";
		String remoteHost = searchInfo.get("remoteHost") != null ? searchInfo.get("remoteHost") : "121.156.46.136";
		String remotePort = searchInfo.get("remotePort") != null ? searchInfo.get("remotePort") : "8087";

		long svcTgtSeq = Long.parseLong(svcTgtSeqStr);
		long spotDevSeq = Long.parseLong(spotDevSeqStr);
		String streamId="";
		
		List<String> eventList = new ArrayList<>();		
		
		HConnection conn=null;
		try {
			conn=HBaseStore.getConnection(remoteHost, remotePort);
			List<CollectEvent> rows = HBaseStore.scanCollectEvent(conn, svcTgtSeq, spotDevSeq, "", streamId, startDt , endDt, 11);
			for (Iterator it = rows.iterator(); it.hasNext();) {
				CollectEvent event = (CollectEvent) it.next();
				System.out.println("event:"+event);
				eventList.add(ObjectConverter.toJSONString(event));
			}
		} finally {
			if(conn!=null) 
				conn.close();
		}
		
		model.addAttribute("eventList", eventList);
		*/
		
		return "/test";		
	}	

}
