package com.kt.giga.home.cms.util;

import java.util.*;

/**
 * <pre>
 * 오라클사용시 테이블명 및 필드가 모두 대문자로 변경됨
 * MyBatis 에서 HashMap으로 데이터를 가져올시 모든 키값이 대문자이므로 
 * 키값을 소문자로 변경 하기위하여 사용
 * </pre>
 */
public class MapKeyConvert 
{
	/**
	 * <p>Map의 키값을 소문자로 변경(value : Object)</p>
	 * @param map 타겟
	 * @return 키가 소문자로 변경된 Map
	 */
	public static Map<String, Object> toLowerVO(Map<String, Object> map)
	{
		Map<String, Object> nmap = new HashMap<String, Object>();

		for(Map.Entry<String, Object> e : map.entrySet())
		{
			nmap.put(e.getKey().toLowerCase(), e.getValue());
		}
		
		return nmap;
	}	
	
	/**
	 * <p>List에 담겨있는 Map의 키값을 소문자로 변경(value : Object)</p>
	 * @param list 타겟
	 * @return 키가 소문자로 변경된 List
	 */
	public static List<Map<String, Object>> toLowersVO(List<Map<String, Object>> list)
	{
		List<Map<String, Object>> nlist = new ArrayList<Map<String, Object>>();
		for(Map<String, Object> map : list)
		{
			nlist.add(toLowerVO(map));			
		}
		
		return nlist;
	}
	
	/**
	 * <p>Map의 키값을 소문자로 변경(value : String)</p>
	 * @param map 타겟
	 * @return 키가 소문자로 변경된 map
	 */	
	public static Map<String, String> toLowerVS(Map<String, String> map)
	{
		Map<String, String> nmap = new HashMap<String, String>();

		for(Map.Entry<String, String> e : map.entrySet())
		{
			nmap.put(e.getKey().toLowerCase(), e.getValue());
		}
		
		return nmap;
	}	
	
	/**
	 * <p>List에 담겨있는 Map의 키값을 소문자로 변경(value : String)</p>
	 * @param list 타겟
	 * @return 키가 소문자로 변경된 List
	 */	
	public static List<Map<String, String>> toLowersVS(List<Map<String, String>> list)
	{
		List<Map<String, String>> nlist = new ArrayList<Map<String, String>>();
		for(Map<String, String> map : list)
		{
			nlist.add(toLowerVS(map));			
		}
		
		return nlist;
	}	
}
