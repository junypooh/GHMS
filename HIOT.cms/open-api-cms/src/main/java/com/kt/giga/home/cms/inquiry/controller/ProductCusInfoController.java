package com.kt.giga.home.cms.inquiry.controller;

import java.util.*;

import javax.servlet.http.*;

import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.*;

import com.kt.giga.home.cms.common.domain.Code;
import com.kt.giga.home.cms.common.domain.LoginInfo;
import com.kt.giga.home.cms.common.service.CodeService;
import com.kt.giga.home.cms.inquiry.service.*;
import com.kt.giga.home.cms.util.*;

@Controller
@RequestMapping("/inquiry")
public class ProductCusInfoController {
	
	@Autowired
	private HttpServletRequest request;
	
	@Autowired
	private ProductCusInfoService productCusInfoService;
	
	@Autowired
	private CodeService codeService;
	
	@RequestMapping("/productCusInfoList")
	public String getProductCusInfoList(Model model, @RequestParam Map<String, Object> searchInfo) {
		
		int telCnt, page, pageSize = 10;
		
		String maskStr, tempStr;
		String[] arrStr;
		String cpCode	= searchInfo.get("cpCode") == null || searchInfo.get("cpCode").toString().trim().equals("") ? "" : searchInfo.get("cpCode").toString().trim();
		String status	= searchInfo.get("status") == null || searchInfo.get("status").toString().trim().equals("") ? "" : searchInfo.get("status").toString().trim();
		page = searchInfo.get("page") == null || searchInfo.get("page").toString().trim().equals("") ? 1 : Integer.parseInt(searchInfo.get("page").toString().trim());
		String searchColumn	= searchInfo.get("searchColumn") == null || searchInfo.get("searchColumn").toString().trim().equals("") ? "" : searchInfo.get("searchColumn").toString().trim();
		String searchString	= searchInfo.get("searchString") == null || searchInfo.get("searchString").toString().trim().equals("") ? "" : searchInfo.get("searchString").toString().trim();
		
		LoginInfo loginInfo = (LoginInfo)request.getAttribute("loginInfo");
		
		searchInfo.put("id"				, loginInfo.getId());
		searchInfo.put("page"			, page);
		searchInfo.put("pageSize"		, pageSize);
		
		int count = productCusInfoService.getCount(searchInfo);
		List<Map<String, Object>> searchList = productCusInfoService.getList(searchInfo);
		List<Code> codeList = codeService.getList("2000");
		
		for(Map<String, Object> nMap : searchList){
			
			if(nMap.get("mbrId") != null){
				tempStr = nMap.get("mbrId").toString();
				if(tempStr.length() > 3) {
					maskStr = tempStr.substring(0, tempStr.length() - 3) + "***";
					nMap.put("mbrId", maskStr);
				}else{nMap.put("mbrId", "--");}
			}else{nMap.put("mbrId", "--");}
			
			if(nMap.get("telNo") != null){
				tempStr = nMap.get("telNo").toString();
				if(tempStr.length() > 3) {
					telCnt = Integer.parseInt(nMap.get("telCnt").toString()) - 1;
					if(telCnt > 0){
						maskStr = tempStr.substring(0, tempStr.length() - 4) + "**** (외 " + Integer.toString(telCnt) + ")";
					}else{
						maskStr = tempStr.substring(0, tempStr.length() - 4) + "****";
					}
					nMap.put("telNo", maskStr);
				}else{nMap.put("telNo", "--");}
			}else{nMap.put("telNo", "--");}
			
			if(nMap.get("cretDt") != null){
				arrStr = nMap.get("cretDt").toString().split(" ");
				nMap.put("cretDt", arrStr[0]);
			}
		}
		
		PageNavi pageNavi = new PageNavi();
		
		pageNavi.setAction("/inquiry/productCusInfoList");
		pageNavi.setTotalCount(count);
		pageNavi.setPageSize(pageSize);
		pageNavi.setNowPage(page);
		pageNavi.setParameters("cpCode", cpCode);
		pageNavi.setParameters("status", status);
		pageNavi.setParameters("searchColumn", searchColumn);
		pageNavi.setParameters("searchString", searchString);
		pageNavi.make();
		
		model.addAttribute("searchList"	, searchList);
		model.addAttribute("codeList"	, codeList);
		model.addAttribute("pageNavi"	, pageNavi);
		
		return "/inquiry/productCusInfoList";
	}
	
	@RequestMapping("/productCusInfoDetail")
	public String productCusInfoDetailForm(Model model, @RequestParam long mbrSeq) {
		String maskStr, tempStr;
		String[] arrStr;
		int telCnt;
		
		List<Map<String, Object>> termlInfoList = productCusInfoService.getTermlInfoList(mbrSeq);
		List<Map<String, Object>> spotDevInfoList = productCusInfoService.getSpotDevInfoList(mbrSeq);
		
		
		for(Map<String, Object> nMap : termlInfoList){
			
			if(nMap.get("mbrId") != null){
				tempStr = nMap.get("mbrId").toString();
				if(tempStr.length() > 3) {
					maskStr = tempStr.substring(0, tempStr.length() - 3) + "***";
					nMap.put("mbrId", maskStr);
				}else{nMap.put("mbrId", "--");}
			}else{nMap.put("mbrId", "--");}
			
			if(nMap.get("telNo") != null){
				tempStr = nMap.get("telNo").toString();
				if(tempStr.length() > 3) {
					maskStr = tempStr.substring(0, tempStr.length() - 4) + "****";
					nMap.put("telNo", maskStr);
				}else{nMap.put("telNo", "--");}
			}else{nMap.put("telNo", "--");}
		}
		
		model.addAttribute("termlInfoList", termlInfoList);
		model.addAttribute("spotDevInfoList", spotDevInfoList);
		
		return "/inquiry/productCusInfoDetail";
	}
	
	
	
	
}
