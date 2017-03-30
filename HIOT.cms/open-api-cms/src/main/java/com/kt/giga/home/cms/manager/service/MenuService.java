package com.kt.giga.home.cms.manager.service;

import java.util.*;

import com.kt.giga.home.cms.manager.domain.*;

/**
 * 메뉴 서비스
 * @author 김용성
 *
 */
public interface MenuService {
	
	/**
	 *  메뉴를 가져온다.
	 * @param menu 메뉴코드
	 * @return  메뉴
	 */
	Menu get(String menu);	
	
	/**
	 *  메뉴를 가져온다.
	 * @param menu 메뉴코드
	 * @return  메뉴
	 */	
	Menu get(Menu menu);
	
	/**
	 * 최상위 Level 메뉴 카운트 수 를 가져온다. 
	 * @return 최상위 Level 메뉴 카운트 수
	 */
	int getCount();
	
	/**
	 * 주어진 메뉴코드의 서브 메뉴 카운트 수 를 가져온다.
	 * @param upMenu  메뉴코드
	 * @return 주어진  메뉴코드의 서브 메뉴 카운트 수
	 */
	int getCount(String upMenu);	
	
	/**
	 * 주어진  메뉴코드의 서브 메뉴 카운트 수 를 가져온다.
	 * @param menu  메뉴
	 * @return 주어진  메뉴코드의 서브 메뉴 카운트 수
	 */		
	int getCount(Menu menu);
	
	/**
	 * 최상위  메뉴 리스트를 가져온다.
	 * @return 최상위 메뉴리스트
	 */
	List<Menu> getList();
		
	/**
	 * 주어진  메뉴코드의 서브 메뉴 리스트를 가져온다.
	 * @param upMenu  메뉴코드
	 * @return 주어진  메뉴코드의 서브 메뉴 리스트
	 */	
	List<Menu> getList(String upMenu);	
	
	/**
	 * 주어진  메뉴의 서브 메뉴리스트를 가져온다.
	 * @param menu  메뉴
	 * @return 주어진  메뉴의 서브 메뉴리스트
	 */	
	List<Menu> getList(Menu menu);
	
	/**
	 * 정렬된  메뉴 전체 리스트를 가져온다.
	 * @return 정렬된  메뉴 전체 리스트
	 */
	List<Menu> getSortList();	
	
	/**
	 * 관리자 id 를 값으로  메뉴 리스트를 가져온다.
	 * @param id
	 * @return 관리자 id 를 값으로 하는  메뉴 리스트
	 */	
	List<Menu> getListByID(String id);		
	
	/**
	 *  메뉴 등록
	 * @param menu 메뉴
	 * @throws Exception 
	 */		
	void register(Menu menu) throws Exception;
	
	/**
	 *  메뉴를 수정한다.
	 * @param menu 메뉴
	 * @throws Exception 
	 */		
	void modify(Menu menu) throws Exception;
	
	/**
	 *  메뉴를 삭제한다.
	 * @param menu 메뉴코드
	 * @throws Exception 
	 */	
	void remove(String menu) throws Exception;		
	
	/**
	 *  메뉴를 삭제한다.
	 * @param menu  메뉴
	 * @throws Exception 
	 */		
	void remove(Menu menu) throws Exception;
	
}
