package com.kt.giga.home.cms.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* css 에 아래의 style 필요 */
/*
	.paging {margin-top: 30px;text-align: center;}
	.paging a, .paging span.current {display: inline-block;width:25px;height:21px;margin:0 -2px;padding-top:4px;font-weight: bold;color: #464646;}
	.paging span.current {width:23px;height:19px;color: #e60012;}
	.paging a.img {margin:0 0 ;border: 1px solid #ccc;background:#f2f2f2 url('/resources/images/bg_paging.gif') 0 0 no-repeat;text-indent: -9999px;}
	.paging a.prev {margin-right: 10px;background-position: -29px 0;}
	.paging a.next {margin-left: 10px;background-position: -61px 0;}
	.paging a.next2 {background-position: -90px 0;}
 */
public class PageNavi 
{
	private String firstImage 	= "<img src='/resources/images/pagenavi/bt_pre01.png' alt=''>";
	private String lastImage 	= "<img src='/resources/images/pagenavi/bt_nxt01.png' alt=''>";
	private String prevImage 	= "<img src='/resources/images/pagenavi/bt_pre02.png' alt=''>";
	private String nextImage 	= "<img src='/resources/images/pagenavi/bt_nxt02.png' alt=''>";
	
	private String method = "GET";
	
	/**
	 * <pre>
	 * &lt;form method='?'&gt;
	 * 기본값 : post
	 * </pre>
	 * @return 폼 메서드
	 */
	public String getMethod()
	{
		return this.method;
	}
	
	/**
	 * <pre>
	 * &lt;form method='?'&gt;
	 * 기본값 : post
	 * </pre>
	 * @param method 폼 메서드
	 */
	public void setMethod(String method)
	{
		this.method = method;
	}
	
	private String action = "";
	
	/**
	 * &lt;form action='?'&gt;
	 * @return 폼 엑션
	 */
	public String getAction()
	{
		return this.action;
	}
	
	/**
	 * &lt;form action='?'&gt;
	 * @param action 폼 엑션
	 */
	public void setAction(String action)
	{
		this.action = action;
	}
	
	private String pageVar = "page";
	
	/**
	 * <pre>
	 * &lt;input type='hidden' name='?' value='현재 페이지 번호'&gt;
	 * 기본값 : page
	 * </pre>
	 * @return 전달 페이지 변수명
	 */
	public String getPageVar()
	{
		return this.pageVar;
	}
	
	/**
	 * <pre>
	 * &lt;input type='hidden' name='?' value='현재 페이지 번호'&gt;
	 * 기본값 : page
	 * </pre>
	 * @param pageVar 전달 페이지 변수명
	 */
	public void setPageVar(String pageVar)
	{
		this.pageVar = pageVar;
	}
	
	private int totalCount = 0;
	
	/**
	 * @return 전체 레코드 카운트
	 */
	public int getTotalCount()
	{
		return this.totalCount;
	}
	
	/**
	 * @param totalCount 전체 레코드 카운트
	 */
	public void setTotalCount(int totalCount)
	{
		this.totalCount = totalCount; 
	}
	
	private int pageSize = 10;
	
	/**
	 * <pre>
	 * 화면상에 출력되는 레코드의 수
	 * 기본값 : 10
	 * </pre>
	 * @return 페이지 사이즈
	 */
	public int getPageSize()
	{
		return this.pageSize;
	}
	
	/**
	 * <pre>
	 * 화면상에 출력되는 레코드의 수
	 * 기본값 : 10
	 * </pre>
	 * @param pageSize 페이지 사이즈 
	 */
	public void setPageSize(int pageSize)
	{
		this.pageSize = pageSize;
	}	

	/**
	 * <pre>
	 * 페이지 총 갯수
	 * </pre>
	 */	
	public int getPageCount()
	{
		double count = ((this.getTotalCount() - 1) / this.getPageSize()) + 1;
		return (int)Math.round(count);
	}
	
	private int nowPage = 1;
	
	/**
	 * @return 현재 페이지 번호
	 */
	public int getNowPage()
	{
		return this.nowPage;
	}
	
	/**
	 * @param nowPage 현재 페이지 번호
	 */
	public void setNowPage(int nowPage)
	{
		this.nowPage = nowPage;
	}
	
	private int numberCount = 10;
	
	/**
	 * <p>페이지네비에 출력할 페이지 번호 갯수 </p>
	 * @return 페이지 번호 갯수
	 */
	public int getNumberCount()
	{
		return this.numberCount;
	}
	
	/**
	 * <p>페이지네비에 출력할 페이지 번호 갯수 </p>
	 * @param numberCount 페이지 번호 갯수
	 */
	public void setNumberCount(int numberCount)
	{
		this.numberCount = numberCount;
	}
	
	private HashMap<String, String> parameters = new HashMap<String, String>(); 
	
	public String getParameters(String key)
	{
		return parameters.containsKey(key) ? parameters.get(key) : "";
	}
	
	public void setParameters(String key, String value)
	{
		if(parameters.containsKey(key))
		{
			parameters.remove(key);	
		}

		parameters.put(key, value);			
	}
	
	//only for Hbase
	private Map<String, Object> searchInfo = new HashMap<String, Object>();
	
	public void setParametersMap(Map<String, Object> searchInfo)
	{
		String searchLogType 	= searchInfo.get("searchLogType") != null ? searchInfo.get("searchLogType").toString().trim() : "";
		String searchStartDate 	= searchInfo.get("searchStartDate") != null ? searchInfo.get("searchStartDate").toString().trim() : "";
		String searchEndDate 	= searchInfo.get("searchEndDate") != null ? searchInfo.get("searchEndDate").toString().trim() : "";
		String searchStartHour 	= searchInfo.get("searchStartHour") != null ? searchInfo.get("searchStartHour").toString().trim() : "";
		String searchEndHour 	= searchInfo.get("searchEndHour") != null ? searchInfo.get("searchEndHour").toString().trim() : "";
		
		String svcTgtSeq 		= searchInfo.get("svcTgtSeq") != null ? searchInfo.get("svcTgtSeq").toString().trim() : "";
		String spotDevSeq 		= searchInfo.get("spotDevSeq") != null ? searchInfo.get("spotDevSeq").toString().trim() : "";
		String devUUID 			= searchInfo.get("devUUID") != null ? searchInfo.get("devUUID").toString().trim() : "";

		parameters.put("searchLogType", searchLogType);
		parameters.put("searchStartDate", searchStartDate);
		parameters.put("searchEndDate", searchEndDate);
		parameters.put("searchStartHour", searchStartHour);
		parameters.put("searchEndHour", searchEndHour);
		
		parameters.put("svcTgtSeq", svcTgtSeq);
		parameters.put("spotDevSeq", spotDevSeq);
		parameters.put("devUUID", devUUID);
	}
	
	private String script = "";
	
	/**
	 * <p>페이지네비에 사용되는 JavaScript</p>
	 * @return PageNavi Script
	 */
	public String getScript()
	{
		return this.script;
	}
	
	private String navi = "";
	
	/**
	 * <p>화면상에 출력되는 페이지네비 HTML</p>
	 * @return
	 */
	public String getNavi()
	{
		return this.navi;
	}
	
	/**
	 * 페이지네비에 필요한 HTML 과 JavaScript 생성
	 */
	public void make()
	{
		if(navi.equals(""))	{
			String value 	= "";
			String iframe 	= "<iframe name='hidden_container' style='display:none;'></iframe>";
			String form 	= "		var formStr	= \"<form name='naviForm' method='" + this.getMethod() + "' action='" + this.getAction() + "' target='_parent'>\";";
			
			form += "\n		formStr		+= \"	<input type='hidden' name='" + this.getPageVar() + "' value=''>\";";
			
			for(Map.Entry<String, String> e : parameters.entrySet()){
				
				if(getAction().equals("get")){
					try{ 
						value = cleanXSS(URLEncoder.encode(e.getValue(), "utf-8")); 
					} catch(UnsupportedEncodingException ex){}
				}
				else{
					value = cleanXSS(e.getValue());
				}
									
				form += "\n		formStr		+= \"	<input type='hidden' name='" + e.getKey() + "' value='" + value + "'>\";";
			}
			
			form += "\n		formStr		+= \"</form>\";";
			
			script  = "<script language='javascript'>";
			script += "\n $(document).ready";
			script += "\n (";
			script += "\n 	function()";
			script += "\n 	{";
			script += "\n" + form + "\n";			
			script += "\n 		var naviFrame 	= document.hidden_container;";
			script += "\n 		naviFrame.document.write( formStr );";
			script += "\n 		naviFrame.document.close();";
			script += "\n	}";
			script += "\n );";
			script += "\n";
			script += "\n function goPage(num)";
			script += "\n {";
			script += "\n 	var naviForm = hidden_container.document.forms['naviForm'];";
			script += "\n 	if(num != null) naviForm." + this.getPageVar() + ".value = num;";
			script += "\n 	naviForm.submit();";
			script += "\n }";
			script += "\n </script>";
            
            navi += "\n" + script + "\n";
            navi += "\n \t<div id='paging' class='paging'>\n";
            navi += this.getPrev();
            
            int blockPage, x = 1;
            int padding = 0;
            double blockCnt = (this.getNowPage() - 1) / this.getNumberCount();
            blockPage = (int)Math.ceil(blockCnt) * this.getNumberCount() + 1;
            
            while(x <= this.getNumberCount() && blockPage <= this.getPageCount())
            {
            	padding = Integer.toString(blockPage).length()*5;
            	if(blockPage == this.getNowPage())
            	{
            		navi += String.format("\t\t<span class='current' style='padding-right:%dpx;'>%d</span>\n", padding, blockPage);
            	}
            	else
            	{
            		navi += String.format("\t\t<a href='javascript:void();' onclick='javascript:goPage(%d);' style='padding-right:%dpx;' >%d</a>\n", blockPage, padding, blockPage);
            	}
            	
            	blockPage++;
            	x++;
            }
            
            navi += this.getNext(blockPage);
            navi += "\t</div>\n";
            navi = iframe + navi;
		}		
	}
	
	private String getPrev()
	{
//		String prev = "<span style='width:44px;display:inline-block;text-align:right;margin-right:6px;'>";
		String prev = "";
		
//		prev += String.format("<a class='pre' style='cursor:pointer' onclick='javascript:goPage(1);'>%s</a>", firstImage);
		prev = "\t\t<a href='javascript:void();' class='img prev2' onclick='goPage(1);'>처음으로</a>\n";
		
		if(this.getNowPage() <= this.getNumberCount())
		{
//			prev += String.format("<a class='pre' style='cursor:pointer'>%s</a>", prevImage);
			prev += "\t\t<a href='javascript:void();' class='img prev'>이전페이지</a>\n";
		}
		else
		{
			double prevCnt = (this.getNowPage() - 1) / this.getNumberCount();
			int prevPage = ((int)Math.ceil(prevCnt) * this.getNumberCount() + 1) - this.getNumberCount();
			//prevPage = prevPage < 0 ? 1 : prevPage;
//			prev += String.format("<a class='pre' style='cursor:pointer' onclick='javascript:goPage(%s);'>%s</a>", prevPage, prevImage);					
			prev += "\t\t<a href='javascript:void();' class='img prev' onclick='goPage("+prevPage+");'>이전페이지</a>\n";
		}
		
//		prev += "</span>";
					
		return prev;
	}
	
	private String getNext(int blockPage)
	{
//		String next = "<span style='width:44px;display:inline-block;text-align:left;margin-left:6px;'>";
		String next = "";
		
		if(blockPage > this.getPageCount())
		{
//			next += String.format("<a class='next' style='cursor:pointer'>%s</a>", nextImage);
			next += "\t\t<a href='javascript:void();' class='img next'>다음페이지</a>\n";
		}
		else
		{
			double nextCnt = (this.getNowPage() - 1) / this.getNumberCount();
			int nextPage = ((int)Math.ceil(nextCnt) * this.getNumberCount() + 1) + this.getNumberCount();
//			next += String.format("<a class='next' style='cursor:pointer' onclick='javascript:goPage(%s);'>%s</a>", nextPage, nextImage);
			next += "\t\t<a href='javascript:void();' class='img next' onclick='goPage(" + nextPage + ");'>다음페이지</a>\n";
		}
		
//		next += String.format("<a class='next' style='cursor:pointer' onclick='javascript:goPage(%s);'>%s</a>", this.getPageCount(), lastImage);
		next += "\t\t<a href='javascript:void();' class='img next2' onclick='goPage(" + this.getPageCount() + ");'>마지막으로</a>\n";

//		next += "</span>";
		
		return next;
	}
	
	private String cleanXSS(String value) {
	    //You'll need to remove the spaces from the html entities below
		value = value.replaceAll("<", "").replaceAll(">", "");
    	value = value.replaceAll("\\(", "").replaceAll("\\)", "");
    	value = value.replaceAll("'", "");
    	value = value.replaceAll("eval\\((.*)\\)", "");
    	value = value.replaceAll("[\\\"\\\'][\\s]*javascript:(.*)[\\\"\\\']", "\"\"");
    	value = value.replaceAll("script", "");
    	value = value.replaceAll("\"", "");
    	
    	return value;
    }
}
