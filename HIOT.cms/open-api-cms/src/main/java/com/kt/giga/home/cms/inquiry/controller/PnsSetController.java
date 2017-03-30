package com.kt.giga.home.cms.inquiry.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.kt.giga.home.cms.inquiry.service.PnsSetService;
import com.kt.giga.home.cms.util.PageNavi;

@Controller
@RequestMapping("/inquiry")
public class PnsSetController {
	
	@Autowired
	private HttpServletRequest request;

	@Autowired
	private PnsSetService pnsSetService; 
		
	@RequestMapping("/pnsSetList")
	public String getPnsSetList(Model model,@RequestParam Map<String, Object> searchInfo) {
		
		int page, pageSize = 5;
		
		page = searchInfo.get("page") == null || searchInfo.get("page").toString().trim().equals("") ? 1 : Integer.parseInt(searchInfo.get("page").toString().trim());
		String searchString	= searchInfo.get("searchString") != null ? searchInfo.get("searchString").toString().trim() : "***---***";	// 첫번째 화면에서 아무것도 보이지 않도록 하기 위해
		String searchColumn	= searchInfo.get("searchColumn") != null ? searchInfo.get("searchColumn").toString().trim() : "mbrId";		// 첫번째 화면에서 아무것도 보이지 않도록 함
		String pushColumn	= searchInfo.get("pushColumn") != null ? searchInfo.get("pushColumn").toString().trim() : "";
		String cpCode	= searchInfo.get("cpCode") == null || searchInfo.get("cpCode").toString().trim().equals("") ? "" : searchInfo.get("cpCode").toString().trim();
		
		searchInfo.put("page"			, page);
		searchInfo.put("pageSize"		, pageSize);
		
		// 첫번째페이지에서 검색결과 없이 나오기 위해서
		searchInfo.put("searchString"	, searchString);
		searchInfo.put("searchColumn"	, searchColumn);
		
		int pnsSetCount = pnsSetService.getCount(searchInfo);
		List<Map<String, Object>> pnsSetList = pnsSetService.getList(searchInfo);
		
		List<Map<String, Object>> getEntList = pnsSetService.getEntList();
		
		List<Object> pnsSetListEx = pnsSetService.getListEx(pnsSetList);
		
		PageNavi pageNavi = new PageNavi();
		
		pageNavi.setAction("/inquiry/pnsSetList");
		pageNavi.setTotalCount(pnsSetCount);
		pageNavi.setPageSize(pageSize);
		pageNavi.setNowPage(page);
		pageNavi.setParameters("cpCode", cpCode);
		pageNavi.setParameters("searchColumn", searchColumn);
		pageNavi.setParameters("searchString", searchString);
		pageNavi.setParameters("pushColumn", pushColumn);
		pageNavi.make();
		
		model.addAttribute("getEntList"		, getEntList);
		model.addAttribute("pnsSetListEx"	, pnsSetListEx);
		model.addAttribute("pageNavi"		, pageNavi);
		
		return "/inquiry/pnsSetList";
	}
	
	@RequestMapping("/pnsSetDetail")
	public String pnsSetDetail(Model model,@RequestParam Map<String, Object> searchInfo) {
		int page, pageSize = 5;
		
		page = searchInfo.get("page") == null || searchInfo.get("page").toString().trim().equals("") ? 1 : Integer.parseInt(searchInfo.get("page").toString().trim());
		String svcTgtSeq	= searchInfo.get("svcTgtSeq") != null ? searchInfo.get("svcTgtSeq").toString().trim() : "";
		String spotDevSeq	= searchInfo.get("spotDevSeq") != null ? searchInfo.get("spotDevSeq").toString().trim() : "";
		
		searchInfo.put("page"			, page);
		searchInfo.put("pageSize"		, pageSize);

		int pnsSetCount = pnsSetService.getDetailCount(searchInfo);
		List<Map<String, Object>> pnsSetDetailList = pnsSetService.getDetailList(searchInfo);		
		
		// 선택된 리스트 정보는 갖고있다.
		Map<String, Object> pnsSetList = new HashMap<String, Object>();
		String mbrId 		= searchInfo.get("mbrId") != null ? searchInfo.get("mbrId").toString().trim() : "";
		String mbrSeq 		= searchInfo.get("mbrSeq") != null ? searchInfo.get("mbrSeq").toString().trim() : "";
		String unitSvcCd 	= searchInfo.get("unitSvcCd") != null ? searchInfo.get("unitSvcCd").toString().trim() : ""; 
		String connTermlId 	= searchInfo.get("connTermlId") != null ? searchInfo.get("connTermlId").toString().trim() : "";
		String statEvetCd 	= searchInfo.get("statEvetCd") != null ? searchInfo.get("statEvetCd").toString().trim() : "";
		String setupVal 		= searchInfo.get("setupVal") != null ? searchInfo.get("setupVal").toString().trim() : "";
		String telNo 		= searchInfo.get("telNo") != null ? searchInfo.get("telNo").toString().trim() : "";
		
		pnsSetList.put("mbrId"			, mbrId);
		pnsSetList.put("connTermlId"	, connTermlId);
		pnsSetList.put("setupVal"		, setupVal);
		pnsSetList.put("telNo"			, telNo);
		
		String tmp;
		
		List<Map<String, Object>> getEntList = pnsSetService.getEntList();
		for (Map<String, Object> ent : getEntList)
		{
			tmp = ent.get("unitEntCd") != null ? ent.get("unitEntCd").toString().trim() : "";
			if( tmp.equals(statEvetCd) )
			{
				pnsSetList.put("statEvetNm"		, ent.get("unitEntNm"));
				break;
			}
		}

		List<Map<String, Object>> getSvcList = pnsSetService.getSvcList();
		for (Map<String, Object> svc : getSvcList)
		{
			tmp = svc.get("unitSvcCd") != null ? svc.get("unitSvcCd").toString().trim() : "";
			if( tmp.equals(unitSvcCd) )
			{
				pnsSetList.put("unitSvcNm"		, svc.get("unitSvcNm"));
				break;
			}
		}
		
		String maskStr;
		if(connTermlId.length() > 3) {
			maskStr = connTermlId.substring(0, connTermlId.length() - 3) + "***";
			pnsSetList.put("connTermlId", maskStr);
		}
			

		
		PageNavi pageNavi = new PageNavi();
		
		pageNavi.setAction("/inquiry/pnsSetDetail");
		pageNavi.setTotalCount(pnsSetCount);
		pageNavi.setPageSize(pageSize);
		pageNavi.setNowPage(page);
		pageNavi.setParameters("unitSvcCd", unitSvcCd);	
		pageNavi.setParameters("telNo", telNo);		
		pageNavi.setParameters("mbrSeq", mbrSeq);		
		pageNavi.setParameters("mbrId", mbrId);		
		pageNavi.setParameters("connTermlId", connTermlId);		
		pageNavi.setParameters("statEvetCd", statEvetCd);		
		pageNavi.setParameters("setupVal", setupVal);
		pageNavi.make();
		
		model.addAttribute("pnsSetList"			, pnsSetList);
		model.addAttribute("pnsSetDetailList"	, pnsSetDetailList);
		model.addAttribute("pageNavi"			, pageNavi);
		
		return "/inquiry/pnsSetDetail";
	}
}
