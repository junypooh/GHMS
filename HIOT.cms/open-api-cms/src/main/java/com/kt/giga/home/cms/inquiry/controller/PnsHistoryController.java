package com.kt.giga.home.cms.inquiry.controller;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.math.NumberUtils;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.kt.giga.home.cms.common.service.APIEnv;
import com.kt.giga.home.cms.inquiry.service.PnsHistoryService;
import com.kt.giga.home.cms.inquiry.view.JxlsExcelViewPnsHistoryList;
import com.kt.giga.home.cms.util.PageNavi;

@Controller
@RequestMapping("/inquiry")
public class PnsHistoryController {
	
	@Autowired
	private HttpServletRequest request;

	@Autowired
	private PnsHistoryService pnsHistroyService; 
	
	@Autowired
	private APIEnv apiEnv;
		
	@RequestMapping("/pnsHistoryList")
	public String getSignalStrengthList(Model model,@RequestParam Map<String, Object> searchInfo) {
		
		int page, pageSize = 10;
		
		String excelCount = apiEnv.getProperty("excelCount");
		page = searchInfo.get("page") == null || searchInfo.get("page").toString().trim().equals("") ? 1 : Integer.parseInt(searchInfo.get("page").toString().trim());
		String searchString	= searchInfo.get("searchString") != null ? searchInfo.get("searchString").toString().trim() : "";
		String searchColumn	= searchInfo.get("searchColumn") != null ? searchInfo.get("searchColumn").toString().trim() : "";
		String cpCode	= searchInfo.get("cpCode") == null || searchInfo.get("cpCode").toString().trim().equals("") ? "" : searchInfo.get("cpCode").toString().trim();
		String searchDateType	= searchInfo.get("searchDateType") != null ? searchInfo.get("searchDateType").toString().trim() : "";
		String searchMonth 	= searchInfo.get("searchMonth") != null ? searchInfo.get("searchMonth").toString().trim() : "";
		String searchStartDate 	= searchInfo.get("searchStartDate") != null ? searchInfo.get("searchStartDate").toString().trim() : "";
		String searchEndDate 	= searchInfo.get("searchEndDate") != null ? searchInfo.get("searchEndDate").toString().trim() : "";
		String pushColumn	= searchInfo.get("pushColumn") == null || searchInfo.get("pushColumn").toString().trim().equals("") ? "" : searchInfo.get("pushColumn").toString().trim();
		
		//System.out.println("searchStartDate = " + searchStartDate);
		//System.out.println("searchEndDate = " + searchEndDate);
		
		if( searchInfo.isEmpty() )
		{
			 Calendar cal = Calendar.getInstance();
		     cal.add(Calendar.DATE, -1);
			 Date startDate = cal.getTime();
			 Date endDate = new Date();
			 SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			 searchStartDate = sdf.format(startDate);
			 searchEndDate = sdf.format(endDate);
			 searchDateType = "range";
		}
		
		searchInfo.put("page"			, page);
		searchInfo.put("pageSize"		, pageSize);
		
		try {
			if( pushColumn.length() == 13 ){
				String dstrCd 				= pushColumn.substring(0, 3);
				String svcThemeCd 			= pushColumn.substring(3, 6);
				String unitSvcCd 			= pushColumn.substring(6, 9);
				String unitEntCd			= pushColumn.substring(9, 13);

				searchInfo.put("dstrCd"		, dstrCd);
				searchInfo.put("svcThemeCd"	, svcThemeCd);
				searchInfo.put("unitSvcCd"	, unitSvcCd);
				searchInfo.put("unitEntCd"	, unitEntCd);

			}
					
		} catch (Exception e) {
			System.out.println("*****************************");// TODO: handle exception
			System.out.println("pushColumn:" + pushColumn);
			System.out.println("e:" + e);
			System.out.println("*****************************");// TODO: handle exception
		}
		
		try {
			if(!searchDateType.equals("")) {
				Date startDate = null, endDate = null;
				SimpleDateFormat ft = new SimpleDateFormat("yyyyMMddHHmmssSSS");
				
				if(searchDateType.equals("range")) {
					if(!searchStartDate.equals("") && !searchEndDate.equals("")) {
						startDate 	= ft.parse(searchStartDate.replace("-", "") + "000000" + "000");
						endDate		= ft.parse(searchEndDate.replace("-", "") + "235959" + "999");
					} 
					//System.out.println("1 startDate = " + startDate);
					//System.out.println("1 endDate = " + endDate);
				} else {
					int year = Integer.parseInt(searchMonth.substring(0, 4));
					int month = Integer.parseInt(searchMonth.substring(4));
					
					Calendar calendar = Calendar.getInstance();
					calendar.set(year, month - 1, 1);
					
					int lastDate = calendar.getActualMaximum(Calendar.DATE);
					
					startDate 	= ft.parse(searchMonth + "01000000" + "000");
					endDate		= ft.parse(searchMonth + String.valueOf(lastDate) + "235959" + "999");
					
					//System.out.println("2 startDate = " + startDate);
					//System.out.println("2 endDate = " + endDate);
				}
				
				searchInfo.put("searchStartDate", startDate);
				searchInfo.put("searchEndDate", endDate);	
			}			
		} catch(Exception e) {
			searchInfo.put("searchStartDate", null);
			searchInfo.put("searchEndDate", null);			
		}
		
		int pnsHistoryCount = pnsHistroyService.getCount(searchInfo);
		List<Map<String, Object>> pnsHistoryList = pnsHistroyService.getList(searchInfo);
		
		List<Map<String, Object>> pnsHistoryEntList = pnsHistroyService.getEntList(searchInfo);
		
		for (Map<String, Object> ent : pnsHistoryEntList)
		{
			String dstrCd 				= ent.get("dstrCd") != null ? 		ent.get("dstrCd").toString().trim() : "";
			String svcThemeCd 			= ent.get("svcThemeCd") != null ? 	ent.get("svcThemeCd").toString().trim() : "";
			String unitSvcCd 			= ent.get("unitSvcCd") != null ? 	ent.get("unitSvcCd").toString().trim() : "";
			String unitEntCd			= ent.get("unitEntCd") != null ? 	ent.get("unitEntCd").toString().trim() : "";
			
			String eventValue			= "" + dstrCd + svcThemeCd + unitSvcCd + unitEntCd;
			ent.put("eventValue", eventValue);
		}

		PageNavi pageNavi = new PageNavi();
		
		pageNavi.setAction("/inquiry/pnsHistoryList");
		pageNavi.setTotalCount(pnsHistoryCount);
		pageNavi.setPageSize(pageSize);
		pageNavi.setNowPage(page);
		
		pageNavi.setParameters("cpCode", cpCode);
		pageNavi.setParameters("searchColumn", searchColumn);
		pageNavi.setParameters("searchString", searchString);
		pageNavi.setParameters("searchDateType", searchDateType);
		pageNavi.setParameters("searchStartDate", searchStartDate);
		pageNavi.setParameters("searchEndDate", searchEndDate);
		pageNavi.setParameters("searchMonth", searchMonth);
		pageNavi.setParameters("pushColumn", pushColumn);

		pageNavi.make();
		
		model.addAttribute("pnsHistoryEntList"	, pnsHistoryEntList);
		model.addAttribute("pnsHistoryList"	, pnsHistoryList);
		model.addAttribute("pageNavi"	, pageNavi);
		model.addAttribute("excelCount"	, excelCount);
		
		return "/inquiry/pnsHistoryList";
	}
	
	@RequestMapping(value="/pnsHistoryList", params="event=excel") //proc 가 excel 일때 excel 다운 
	public ModelAndView excelPnsHistoryList(Model model, @RequestParam Map<String, Object> searchInfo) {
		
		String excelCount = apiEnv.getProperty("excelCount");
		
		String searchDateType	= searchInfo.get("searchDateType") != null ? searchInfo.get("searchDateType").toString().trim() : "";
		String searchMonth 	= searchInfo.get("searchMonth") != null ? searchInfo.get("searchMonth").toString().trim() : "";
		String searchStartDate 	= searchInfo.get("searchStartDate") != null ? searchInfo.get("searchStartDate").toString().trim() : "";
		String searchEndDate 	= searchInfo.get("searchEndDate") != null ? searchInfo.get("searchEndDate").toString().trim() : "";
		String pushColumn	= searchInfo.get("pushColumn") == null || searchInfo.get("pushColumn").toString().trim().equals("") ? "" : searchInfo.get("pushColumn").toString().trim();
		
		//System.out.println("searchStartDate = " + searchStartDate);
		//System.out.println("searchEndDate = " + searchEndDate);
		
		if( searchInfo.isEmpty() )
		{
			 Calendar cal = Calendar.getInstance();
		     cal.add(Calendar.DATE, -1);
			 Date startDate = cal.getTime();
			 Date endDate = new Date();
			 SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			 searchStartDate = sdf.format(startDate);
			 searchEndDate = sdf.format(endDate);
			 searchDateType = "range";
		}
		
		searchInfo.put("page"			, "");
		searchInfo.put("pageSize"		, "");
		searchInfo.put("limitCount"		, excelCount);
		
		try {
			if( pushColumn.length() == 13 ){
				String dstrCd 				= pushColumn.substring(0, 3);
				String svcThemeCd 			= pushColumn.substring(3, 6);
				String unitSvcCd 			= pushColumn.substring(6, 9);
				String unitEntCd			= pushColumn.substring(9, 13);

				searchInfo.put("dstrCd"		, dstrCd);
				searchInfo.put("svcThemeCd"	, svcThemeCd);
				searchInfo.put("unitSvcCd"	, unitSvcCd);
				searchInfo.put("unitEntCd"	, unitEntCd);

			}
					
		} catch (Exception e) {
			System.out.println("*****************************");// TODO: handle exception
			System.out.println("pushColumn:" + pushColumn);
			System.out.println("e:" + e);
			System.out.println("*****************************");// TODO: handle exception
		}
		
		try {
			if(!searchDateType.equals("")) {
				Date startDate = null, endDate = null;
				SimpleDateFormat ft = new SimpleDateFormat("yyyyMMddHHmmssSSS");
				
				if(searchDateType.equals("range")) {
					if(!searchStartDate.equals("") && !searchEndDate.equals("")) {
						startDate 	= ft.parse(searchStartDate.replace("-", "") + "000000" + "000");
						endDate		= ft.parse(searchEndDate.replace("-", "") + "235959" + "999");
					} 
					//System.out.println("1 startDate = " + startDate);
					//System.out.println("1 endDate = " + endDate);
				} else {
					int year = Integer.parseInt(searchMonth.substring(0, 4));
					int month = Integer.parseInt(searchMonth.substring(4));
					
					Calendar calendar = Calendar.getInstance();
					calendar.set(year, month - 1, 1);
					
					int lastDate = calendar.getActualMaximum(Calendar.DATE);
					
					startDate 	= ft.parse(searchMonth + "01000000" + "000");
					endDate		= ft.parse(searchMonth + String.valueOf(lastDate) + "235959" + "999");
					
					//System.out.println("2 startDate = " + startDate);
					//System.out.println("2 endDate = " + endDate);
				}
				
				searchInfo.put("searchStartDate", startDate);
				searchInfo.put("searchEndDate", endDate);	
			}			
		} catch(Exception e) {
			searchInfo.put("searchStartDate", null);
			searchInfo.put("searchEndDate", null);			
		}
	
		List<Map<String, Object>> pnsHistoryList = pnsHistroyService.getList(searchInfo);
		
		
		
		ModelAndView mav = new ModelAndView(new JxlsExcelViewPnsHistoryList());
		mav.addObject("pnsHistoryList", pnsHistoryList);
		
		return mav;
	}
	
	@RequestMapping("/pnsHistoryDetail")
	public String pnsHistoryDetail(Model model,@RequestParam Map<String, Object> searchInfo) {
		
		// 선택된 리스트 정보는 갖고있다.
		String mbrSeq		= searchInfo.get("mbrSeq") != null ? searchInfo.get("mbrSeq").toString().trim() : "";
		String mbrId 		= searchInfo.get("mbrId") != null ? searchInfo.get("mbrId").toString().trim() : "";
		String unitSvcCd 	= searchInfo.get("unitSvcCd") != null ? searchInfo.get("unitSvcCd").toString().trim() : ""; 
		String statEvetCd 	= searchInfo.get("statEvetCd") != null ? searchInfo.get("statEvetCd").toString().trim() : "";
		String msgTrmDt1 	= searchInfo.get("msgTrmDt1") != null ? searchInfo.get("msgTrmDt1").toString().trim() : "";
		String msgTrmDt2 	= searchInfo.get("msgTrmDt2") != null ? searchInfo.get("msgTrmDt2").toString().trim() : "";
		String telNo 		= searchInfo.get("telNo") != null ? searchInfo.get("telNo").toString().trim() : "";
		
		Map<String, Object> pnsHistoryDetail = new HashMap<String, Object>();
		pnsHistoryDetail.put("mbrId"			, mbrId);
		pnsHistoryDetail.put("telNo"			, telNo);
		pnsHistoryDetail.put("msgTrmDt1"		, msgTrmDt1);
		pnsHistoryDetail.put("msgTrmDt2"		, msgTrmDt2);
		
		//searchInfo.put("page"			, 1);
		//searchInfo.put("pageSize"		, 1);
		searchInfo.put("mbrSeq", 		NumberUtils.toLong(mbrSeq));
		
		
		String devNm="";
		
		
		
		List<Map<String, Object>> pnsHistoryDetailList = pnsHistroyService.getListDetail(searchInfo);
		
		
		if(pnsHistoryDetailList != null && pnsHistoryDetailList.size() > 0){
			pnsHistoryDetail =  pnsHistoryDetailList.get(0);
			if(pnsHistoryDetail.get("msgTrmSbst") != null){
				String tmpMsg = (String) pnsHistoryDetail.get("msgTrmSbst");

				try {
					JSONObject obj = (JSONObject) JSONValue.parse(tmpMsg);
					
					String msg = (String) obj.get("msg");
					String eventId = (String) obj.get("eventId");
					String msgDevNm = (String) obj.get("devNm");

					if( eventId.equals("001HIT001E0008") )
					{
						msg = msg.replace("${devNm}", msgDevNm);
						pnsHistoryDetail.put("devNm", msgDevNm);
					}
					else
					{
						if(pnsHistoryDetail.get("devNm") != null){
							devNm = (String) pnsHistoryDetail.get("devNm");
							pnsHistoryDetail.put("devNm"			, devNm);
						}						
						msg = msg.replace("${devNm}", devNm);
					}
					msg = msg.replace("${telNo}", telNo);
					msg = msg.replace("format($detectTime, \u0027yyyy/MM/DD HH:mm\u0027)", "'" + msgTrmDt1 +" "+ msgTrmDt2 + "'");
					
					pnsHistoryDetail.put("afterMsgTrmSbst"	, msg);
				} catch (Exception e) {
					pnsHistoryDetail.put("afterMsgTrmSbst"	, tmpMsg);
				}
			}
		}
			
		List<Map<String, Object>> getEntList = pnsHistroyService.getEntList(searchInfo);
		String tmp;
		for (Map<String, Object> ent : getEntList)
		{
			tmp = ent.get("unitEntCd") != null ? ent.get("unitEntCd").toString().trim() : "";
			if( tmp.equals(statEvetCd) )
			{
				pnsHistoryDetail.put("statEvetNm"		, ent.get("unitEntNm"));
				break;
			}
		}

		List<Map<String, Object>> getSvcList = pnsHistroyService.getSvcList();
		for (Map<String, Object> svc : getSvcList)
		{
			tmp = svc.get("unitSvcCd") != null ? svc.get("unitSvcCd").toString().trim() : "";
			if( tmp.equals(unitSvcCd) )
			{
				pnsHistoryDetail.put("unitSvcNm"		, svc.get("unitSvcNm"));
				break;
			}
		}			
		
		model.addAttribute("pnsHistoryDetail"			, pnsHistoryDetail);
		
		return "/inquiry/pnsHistoryDetail";
	}
}
