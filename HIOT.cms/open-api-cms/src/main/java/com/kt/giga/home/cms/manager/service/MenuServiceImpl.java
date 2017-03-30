package com.kt.giga.home.cms.manager.service;

import java.util.*;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.*;
import org.springframework.beans.factory.annotation.*;

import com.kt.giga.home.cms.manager.dao.*;
import com.kt.giga.home.cms.manager.domain.*;

/**
 * 메뉴 서비스
 * @author 김용성
 *
 */
@Service
public class MenuServiceImpl implements MenuService {
	
	@Autowired
	private MenuDao menuDao;

	@Override
	public Menu get(String menu) {
		Menu Menu = new Menu();
		Menu.setMenu(menu);
		return this.get(Menu);
	}

	@Override
	public Menu get(Menu menu) {
		return menuDao.get(menu);
	}

	@Override
	public int getCount() {
		return this.getCount("0000");
	}

	@Override
	public int getCount(String upMenu) {
		Menu menu = new Menu();
		menu.setUpMenu(upMenu);
		return this.getCount(menu);
	}

	@Override
	public int getCount(Menu menu) {
		return menuDao.getCount(menu);
	}

	@Override
	public List<Menu> getList() {
		return this.getList("0000");
	}

	@Override
	public List<Menu> getList(String upMenu) {
		Menu menu = new Menu();
		menu.setUpMenu(upMenu);
		return this.getList(menu);
	}

	@Override
	public List<Menu> getList(Menu menu) {
		return menuDao.getList(menu);
	}
	
	@Override
	public List<Menu> getSortList() {
		return menuDao.getSortList();
	}	
	
	@Override
	public List<Menu> getListByID(String id) {
		return menuDao.getListByID(id);
	}	
	
	private void updateSortNo(Menu menu, boolean plus) throws Exception
	{
		boolean equalsSortNo = false;
		List<Menu> menuList = this.getList(menu);
		
		for(Menu ml : menuList) { 
			if(!menu.getMenu().equals(ml.getMenu()) && ml.getSortNo() == menu.getSortNo())
				equalsSortNo = true;
		}
		
		if((equalsSortNo && plus) || !plus)	{
			for(Menu ml : menuList)	{		
				if(!menu.getMenu().equals(ml.getMenu())) {
					if(menu.getSortNo() <= ml.getSortNo()) {
						int updateNum = (plus) ? ml.getSortNo() + 1 : ml.getSortNo() - 1;
						ml.setSortNo(updateNum);
						menuDao.modify(ml);
					}
				}
			}	
		}
	}	
	
	private void updateSortNo(Menu menu, Menu orgMenu) throws Exception
	{
		boolean equalsSortNo = false;
		List<Menu> menuList = this.getList(menu);
		
		for(Menu ml : menuList) { 
			if(!menu.getMenu().equals(ml.getMenu()) && ml.getSortNo() == menu.getSortNo())
				equalsSortNo = true;
		}		
		
		if(equalsSortNo) {
			if(menu.getSortNo() > orgMenu.getSortNo()) {
				for(Menu ml : menuList)	{
					if(!menu.getMenu().equals(ml.getMenu())) {
						if(menu.getSortNo() >= ml.getSortNo() && orgMenu.getSortNo() < ml.getSortNo()) {
							ml.setSortNo(menu.getSortNo() - 1);
							menuDao.modify(ml);
						}
					}
				}
			} else {
				for(Menu ml : menuList) {
					if(!menu.getMenu().equals(ml.getMenu())) {
						if(menu.getSortNo() <= ml.getSortNo() && orgMenu.getSortNo() > ml.getSortNo()) {
							ml.setSortNo(ml.getSortNo() + 1);
							menuDao.modify(ml);
						}
					}
				}				
			}			
		}
	}	

	@Override
	@Transactional
	public void register(Menu menu) throws Exception {
		if(this.get(menu) != null)
			throw new Exception("중복된 코드 입니다.");
		
		menuDao.register(menu);
		this.updateSortNo(menu, true);
	}

	@Override
	@Transactional
	public void modify(Menu menu) throws Exception {
		Menu orgMenu = this.get(menu.getOrgMenu());
		
		if(!orgMenu.getMenu().equals(menu.getMenu())) {		
			if(this.get(menu) != null)
				throw new Exception("중복된 코드 입니다.");
			
			if(menu.getUpMenu().equals("0000"))	{
				List<Menu> childMenuList = this.getList(menu);
				for(Menu cml : childMenuList) {
					cml.setUpMenu(menu.getMenu());
					menuDao.modify(cml);
				}											
			}
		}
		
		menuDao.modify(menu);
		this.updateSortNo(menu, orgMenu);	
	}

	@Override
	public void remove(String menuStr) throws Exception {
		Menu menu = this.get(menuStr);
		this.remove(menu);
	}

	@Override
	@Transactional
	public void remove(Menu menu) throws Exception {
		if(menu.getUpMenu().equals("0000"))	{
			List<Menu> childMenuList = this.getList(menu.getMenu());
			for(Menu cml : childMenuList) {
				menuDao.remove(cml);
			}
		}
		
		menuDao.remove(menu);
		this.updateSortNo(menu, false);	
	}
}
