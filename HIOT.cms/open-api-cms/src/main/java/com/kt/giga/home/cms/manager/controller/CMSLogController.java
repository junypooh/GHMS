package com.kt.giga.home.cms.manager.controller;

import java.util.*;

import org.json.simple.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.kt.giga.home.cms.util.*;
import com.kt.giga.home.cms.manager.domain.*;
import com.kt.giga.home.cms.manager.service.*;

@Controller
@RequestMapping("/manager")
public class CMSLogController
{
	@Autowired
	private CMSLogService cmsLogService;
	
	@Autowired
	private CMSLogParamService cmsLogParamService;
	
	@Autowired
	private MenuService menuService;	
	
	@ResponseBody
	@RequestMapping("/initSubMenuList")
	public Map<String, Object> initSubMenuList(@RequestParam(value="fd_up_cms_menu", defaultValue="") String fd_up_cms_menu)
	{
		Map<String, Object> result = new HashMap<>(); 
		try {
			List<Menu> list = menuService.getList(fd_up_cms_menu);
			result.put("list", list);
			result.put("code", 200);
			result.put("msg", "success");
			/*for(Menu subMenu : subMenuList)
			{
				result.put("menu", subMenu.getMenu());
				result.put("name", subMenu.getName());
				result.put("code", 200);
				result.put("msg", "success");
			} */
		}catch(Exception e) {
			// TODO: handle exception
			result.put("code", 500);
			result.put("msg", "server error : " + e.getMessage());
		}
		
		
		
		
		return result;
	}
	
	@RequestMapping("/logList")
	public String logList(Model model, @RequestParam Map<String, Object> map)
	{
		int page, pageSize = 20;
		
		page = Integer.parseInt(map.get("page") == null || map.get("page").toString().trim().equals("") ? "1" : map.get("page").toString());
		
		String searchColumn 		= map.get("searchColumn") == null ? "" : map.get("searchColumn").toString();
		String searchString 		= map.get("searchString") == null ? "" : map.get("searchString").toString();
		String search_start 		= map.get("search_start") == null ? "" : map.get("search_start").toString();
		String search_finish 		= map.get("search_finish") == null ? "" : map.get("search_finish").toString();
		String search_cms_main_menu	= map.get("search_cms_main_menu") == null ? "" : map.get("search_cms_main_menu").toString();
		String search_cms_sub_menu	= map.get("search_cms_sub_menu") == null ? "" : map.get("search_cms_sub_menu").toString();
		
		String org_search_start = search_start;
		String org_search_finish = search_finish;
		
		search_start  = (!search_start.equals("")) ? search_start.replace("-", "") + "000000" : "";
		search_finish = (!search_finish.equals("")) ? search_finish.replace("-", "") + "235959" : "";
		
		map.remove("page");
		map.put("page", page);
		map.put("pageSize", pageSize);
		
		map.remove("search_start");		
		map.put("search_start", search_start);
		
		map.remove("search_finish");
		map.put("search_finish", search_finish);		
		
		map.put("org_search_start", org_search_start);
		map.put("org_search_finish", org_search_finish);
		int totalCount = cmsLogService.getCount(map);
		List<Map<String, Object>> logList = MapKeyConvert.toLowersVO(cmsLogService.getList(map));
		
		List<Menu> mainMenuList = menuService.getList();
		
		List<Menu> subMenuList = null;
		if(!search_cms_main_menu.equals(""))
		{
			subMenuList = menuService.getList(search_cms_main_menu);
		}
		
		PageNavi pageNavi = new PageNavi();
		pageNavi.setMethod("get");
		pageNavi.setPageSize(20);
		pageNavi.setNowPage(page);
		pageNavi.setTotalCount(totalCount);
		pageNavi.setAction("/manager/logList");
		pageNavi.setParameters("searchColumn", searchColumn);
		pageNavi.setParameters("searchString", searchString);
		pageNavi.setParameters("search_start", org_search_start);
		pageNavi.setParameters("search_finish", org_search_finish);
		pageNavi.setParameters("search_cms_main_menu", search_cms_main_menu);
		pageNavi.setParameters("search_cms_sub_menu", search_cms_sub_menu);		
		pageNavi.make();
		
		/*
		mav.addObject("pageNavi", pageNavi);		
		
		ModelAndView mav = new ModelAndView("manager/logList");
		
		mav.addObject("logList", logList);		
		mav.addObject("totalCount", totalCount);		
		mav.addObject("pageHelper", map);
		mav.addObject("mainMenuList", mainMenuList);		
		mav.addObject("subMenuList", subMenuList);		
		*/
		
		model.addAttribute("logList", logList);
		model.addAttribute("totalCount", totalCount);
		model.addAttribute("pageHelper", map);
		model.addAttribute("mainMenuList", mainMenuList);
		model.addAttribute("subMenuList", subMenuList);
		model.addAttribute("pageNavi", pageNavi);
		
		return "manager/logList";		
	}
	
	@RequestMapping("/logView")
	public String logView(Model model, @RequestParam Map<String, Object> map)
	{
		int pk_cms_log = Integer.parseInt(map.get("pk_cms_log") == null || map.get("pk_cms_log").toString().trim().equals("") ? "1" : map.get("pk_cms_log").toString());
		
		Map<String, Object> log =  MapKeyConvert.toLowerVO(cmsLogService.get(pk_cms_log));
		List<Map<String, Object>> logParamList = MapKeyConvert.toLowersVO(cmsLogParamService.getList(pk_cms_log));
			
		List<Map<String, Object>> logParamNewList = new ArrayList<Map<String, Object>>();
		Map<String, Object> logParamListByType1 = new HashMap<String, Object>();
		Map<String, Object> logParamListByType2 = new HashMap<String, Object>();
		
		for(Map<String, Object> logParam : logParamList)
		{
			int fd_param_type = Integer.parseInt(logParam.get("fd_param_type").toString());
			String fd_param_name = logParam.get("fd_param_name").toString();
			
			if(fd_param_type == 0)
			{
				logParamNewList.add(logParam);
			}
			else
			{
				if(!logParamListByType1.containsKey(fd_param_name))
				{
					List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
					
					for(Map<String, Object> lp : logParamList)
					{
						if(lp.get("fd_param_name").toString().equals(fd_param_name))
						{
							list.add(lp);
						}
					}
					
					if(fd_param_type == 1)
					{
						logParamListByType1.put(fd_param_name, list);
					}
					else
					{
						logParamListByType2.put(fd_param_name, list);
					}
				}				
			}
		}

		String temp = "";
		LogParameterCompare logParameterCompare = new LogParameterCompare();
		for(Map.Entry<String, Object> entry : logParamListByType1.entrySet())
		{
			List<Map<String, Object>> list = (List<Map<String, Object>>)entry.getValue();
			Collections.sort(list, logParameterCompare);
			
			String fd_param_value = "";
			for(Map<String, Object> param : list)
			{
				temp = param.get("fd_param_value") == null ? "" : param.get("fd_param_value").toString();
				fd_param_value += temp;
			}
			
			Map<String, Object> param = new HashMap<String, Object>();
			
			param.put("pk_cms_log_param", list.get(0).get("pk_cms_log_param").toString());
			param.put("fk_cms_log", list.get(0).get("fk_cms_log").toString());
			param.put("fd_param_name", entry.getKey());
			param.put("fd_param_value", fd_param_value);
			param.put("fd_param_index", 0);
			param.put("fd_param_type", 1);
			
			logParamNewList.add(param);
		}
		
		for(Map.Entry<String, Object> entry : logParamListByType2.entrySet())
		{
			List<Map<String, Object>> list = (List<Map<String, Object>>)entry.getValue();
			Collections.sort(list, logParameterCompare);
			
			String fd_param_value = "";
			for(Map<String, Object> param : list)
			{
				temp = param.get("fd_param_value") == null ? "" : param.get("fd_param_value").toString();
				fd_param_value += temp + "<br />";
			}
			
			Map<String, Object> param = new HashMap<String, Object>();
			
			param.put("pk_cms_log_param", list.get(0).get("pk_cms_log_param").toString());
			param.put("fk_cms_log", list.get(0).get("fk_cms_log").toString());
			param.put("fd_param_name", entry.getKey());
			param.put("fd_param_value", fd_param_value);
			param.put("fd_param_index", 0);
			param.put("fd_param_type", 2);
			
			logParamNewList.add(param);
		}		
		
		/*ModelAndView mav = new ModelAndView("manager/logView");
		
		mav.addObject("log", log);
		mav.addObject("logParamList", logParamNewList);*/
		
		model.addAttribute("log", log);
		model.addAttribute("logParamList", logParamList);
		
		return "manager/logView";
	}
	
	public class LogParameterCompare implements Comparator<Map<String, Object>>
	{
		@Override
		public int compare(Map<String, Object> obj1, Map<String, Object> obj2)
		{
			int result = 0;
			int fd_param_index1 = Integer.parseInt(obj1.get("fd_param_index").toString());
			int fd_param_index2 = Integer.parseInt(obj2.get("fd_param_index").toString());
			
			if(fd_param_index1 > fd_param_index2)
			{
				result = 1;
			}
			else if(fd_param_index1 < fd_param_index2)
			{
				result = -1;
			}
			
			return result;
		}
	}
}
