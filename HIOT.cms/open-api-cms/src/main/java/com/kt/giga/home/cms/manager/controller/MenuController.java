package com.kt.giga.home.cms.manager.controller;

import java.util.*;

import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.*;

import com.kt.giga.home.cms.manager.domain.*;
import com.kt.giga.home.cms.manager.service.*;

@Controller
@RequestMapping("/manager")
public class MenuController {
	
	@Autowired
	private MenuService menuService;
	
	@RequestMapping("/mainMenu")
	public String mainMenu(Model model, @RequestParam(value="method", defaultValue="") String method, @RequestParam(value="menu", defaultValue="") String menuInfo) {
		
		Menu menu = null;
		List<Menu> mainMenuList = menuService.getList();			
		model.addAttribute("mainMenuList", mainMenuList);		
		
		if(method.equals("modifyForm")) { 
			menu = menuService.get(menuInfo); 
		}
		
		model.addAttribute("menu", menu);		
		
		return "/manager/mainMenu";
	}
	
	@RequestMapping(value="/mainMenu", params="method=register")
	public String mainMenuRegister(Model model, @RequestParam Map<String, String> menuInfo)	{
		
		int sortNo = (menuInfo.get("sortNo") != "") ? Integer.parseInt(menuInfo.get("sortNo")) : 1;
		
		Menu menu = new Menu();
		menu.setUpMenu("0000");
		menu.setMenu(menuInfo.get("menu").trim());
		menu.setName(menuInfo.get("name").trim());
		menu.setUrl(menuInfo.get("url").trim());
		menu.setUseYN(menuInfo.get("useYN"));
		menu.setSortNo(sortNo);

		try	{ 
			if(menu.getMenu().equals("0000")) 
				throw new Exception("deny");
			
			menuService.register(menu);
			model.addAttribute("script", String.format("alert('%s')", "정상적으로 등록 처리 되었습니다."));
			
		} catch(Exception ex) {
			String script = ex.getMessage().equals("deny") ?  "0000 코드는 사용하실수 없습니다." : "등록 과정중 오류가 발생하였습니다.\\n다시 시도하여 주십시오.";
			model.addAttribute("menu", menuInfo);
			model.addAttribute("script", String.format("alert('%s')", script));
		}				
		
		List<Menu> mainMenuList = menuService.getList();
		
		model.addAttribute("mainMenuList", mainMenuList);
		
		return "/manager/mainMenu";
	}	
	
	@RequestMapping(value="/mainMenu", params="method=modify")
	public String mainMenuModify(Model model, @RequestParam Map<String, String> menuInfo) {
		int sortNo = (menuInfo.get("sortNo") != "") ? Integer.parseInt(menuInfo.get("sortNo")) : 1;
		
		Menu menu = new Menu();
		menu.setUpMenu("0000");
		menu.setOrgMenu(menuInfo.get("orgMenu").trim());
		menu.setMenu(menuInfo.get("menu").trim());
		menu.setName(menuInfo.get("name").trim());
		menu.setUrl(menuInfo.get("url").trim());		
		menu.setUseYN(menuInfo.get("useYN"));
		menu.setSortNo(sortNo);
		
		try {
			
			if(menu.getMenu().equals("0000")) 
				throw new Exception("deny");
			
			menuService.modify(menu);			
			model.addAttribute("script", String.format("alert('%s')", "정상적으로 수정 처리 되었습니다."));
		} catch(Exception ex) {
			String script = ex.getMessage().equals("deny") ?  "0000 코드는 사용하실수 없습니다." : "수정 과정중 오류가 발생하였습니다.\\n다시 시도하여 주십시오.";
			model.addAttribute("menu", menuInfo);
			model.addAttribute("script", String.format("alert('%s')", script));
		}				
		
		List<Menu> mainMenuList = menuService.getList();
		
		model.addAttribute("mainMenuList", mainMenuList);
		
		return "/manager/mainMenu";
	}	
	
	@RequestMapping(value="/mainMenu", params="method=remove")
	public String mainMenuRemove(Model model, @RequestParam(value="menu", defaultValue="") String menuInfo) {
		try {
			menuService.remove(menuInfo);
			model.addAttribute("script", String.format("alert('%s')", "정상적으로 삭제 처리 되었습니다."));
		} catch(Exception ex){
			model.addAttribute("script", String.format("alert('%s')", "삭제 과정중 오류가 발생하였습니다.\\n다시 시도하여 주십시오."));			
		}
		
		List<Menu> mainMenuList = menuService.getList();
		
		model.addAttribute("mainMenuList", mainMenuList);
		
		return "/manager/mainMenu";				
	}	
	
	@RequestMapping("/subMenu")
	public String subMenu(Model model, @RequestParam Map<String, String> subMenuInfo) {
		String upMenu = subMenuInfo.get("upMenu");
		String menu = subMenuInfo.get("menu");
		String method = (subMenuInfo.get("method") == null) ? "" : subMenuInfo.get("method");
		
		Menu subMenu = null;
		Menu mainMenu = menuService.get(upMenu);
		List<Menu> subMenuList = menuService.getList(upMenu);	
		
		if(method.equals("modifyForm")) { 
			subMenu = menuService.get(menu); 
		}
		
		model.addAttribute("mainMenu", mainMenu);
		model.addAttribute("subMenu", subMenu);
		model.addAttribute("subMenuList", subMenuList);
		
		return "/manager/subMenu";	
	}	
	
	@RequestMapping(value="/subMenu", params="method=register")
	public String subMenuRegister(Model model, @RequestParam Map<String, String> subMenuInfo) {
		
		String upMenu = subMenuInfo.get("upMenu");
		int sortNo = subMenuInfo.get("sortNo") != "" ? Integer.parseInt(subMenuInfo.get("sortNo")) : 1;
		
		Menu subMenu = new Menu();
		subMenu.setUpMenu(upMenu);
		subMenu.setMenu(subMenuInfo.get("menu").trim());
		subMenu.setName(subMenuInfo.get("name").trim());
		subMenu.setUrl(subMenuInfo.get("url").trim());
		subMenu.setUseYN(subMenuInfo.get("useYN"));
		subMenu.setSortNo(sortNo);

		try	{ 
			
			if(subMenu.getMenu().equals("0000")) {
				throw new Exception("deny");
			}
			
			menuService.register(subMenu);			
			model.addAttribute("script", String.format("alert('%s')", "정상적으로 등록 처리 되었습니다."));
		} catch(Exception ex) {	
			String script = ex.getMessage().equals("deny") ?   "0000 코드는 사용하실수 없습니다." : "등록 과정중 오류가 발생하였습니다.\\n다시 시도하여 주십시오.";
			model.addAttribute("subMenu", subMenuInfo);
			model.addAttribute("script", String.format("alert('%s')", script));
		}				
		
		Menu mainMenu = menuService.get(upMenu);
		List<Menu> subMenuList = menuService.getList(upMenu);
		
		model.addAttribute("mainMenu", mainMenu);
		model.addAttribute("subMenuList", subMenuList);
		
		return "/manager/subMenu";
	}	
	
	@RequestMapping(value="/subMenu", params="method=modify")
	public String subMenuModify(Model model, @RequestParam Map<String, String> subMenuInfo)
	{
		String upMenu = subMenuInfo.get("upMenu");
		int sortNo = subMenuInfo.get("sortNo") != "" ? Integer.parseInt(subMenuInfo.get("sortNo")) : 1;
		
		Menu subMenu = new Menu();
		subMenu.setUpMenu(upMenu);
		subMenu.setOrgMenu(subMenuInfo.get("orgMenu"));
		subMenu.setMenu(subMenuInfo.get("menu").trim());
		subMenu.setName(subMenuInfo.get("name").trim());
		subMenu.setSortNo(sortNo);
		subMenu.setUseYN(subMenuInfo.get("useYN"));
		subMenu.setUrl(subMenuInfo.get("url").trim());

		try	{ 
			
			if(subMenu.getMenu().equals("0000")) {
				throw new Exception("deny");
			}
			
			menuService.modify(subMenu);			
			model.addAttribute("script", String.format("alert('%s')", "정상적으로 수정 처리 되었습니다."));
		} catch(Exception ex) {
			String script = ex.getMessage().equals("deny") ?  "0000 코드는 사용하실수 없습니다." : "수정 과정중 오류가 발생하였습니다.\\n다시 시도하여 주십시오.";
			model.addAttribute("subMenu", subMenuInfo);
			model.addAttribute("script", String.format("alert('%s')", script));
		}				
		
		Menu mainMenu = menuService.get(upMenu);
		List<Menu> subMenuList = menuService.getList(upMenu);
		
		model.addAttribute("mainMenu", mainMenu);
		model.addAttribute("subMenuList", subMenuList);
		
		return "/manager/subMenu";
	}	
	
	@RequestMapping(value="/subMenu", params="method=remove")
	public String subMenuRemove(Model model, @RequestParam(value="upMenu", defaultValue="") String upMenu, @RequestParam(value="menu", defaultValue="") String subMenuInfo) {
		try {
			menuService.remove(subMenuInfo);
			model.addAttribute("script", String.format("alert('%s')", "정상적으로 삭제 처리 되었습니다."));
		}
		catch(Exception ex) {
			model.addAttribute("script", String.format("alert('%s')", "삭제 과정중 오류가 발생하였습니다.\\n다시 시도하여 주십시오."));			
		}
		
		Menu mainMenu = menuService.get(upMenu);
		List<Menu> subMenuList = menuService.getList(upMenu);
		
		model.addAttribute("mainMenu", mainMenu);
		model.addAttribute("subMenuList", subMenuList);
		
		return "/manager/subMenu";
	}		

}
