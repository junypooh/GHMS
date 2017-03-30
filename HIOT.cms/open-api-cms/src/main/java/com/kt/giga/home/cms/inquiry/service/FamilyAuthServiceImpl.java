/*******************************************************************************
 * Copyright(c) 2015 KT. All rights reserved.
 * This software is the proprietary information of KT.
 *******************************************************************************/
package com.kt.giga.home.cms.inquiry.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kt.giga.home.cms.inquiry.dao.FamilyAuthDao;

/**
 * 
 * @author junypooh ( kyungjun.park@ceinside.co.kr )
 * @since 2015. 8. 4.
 */
@Service
public class FamilyAuthServiceImpl implements FamilyAuthService {
	
	@Autowired
	private FamilyAuthDao familyAuthDao;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getCount(Map<String, Object> searchInfo) {
		return familyAuthDao.getCount(searchInfo);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Map<String, Object>> getList(Map<String, Object> searchInfo) {
		String maskStr, tempStr = "";
		
		List<Map<String, Object>> list = familyAuthDao.getList(searchInfo);
		
		if(list != null) {
			for(Map<String, Object> nMap : list){
				
				if (nMap.get("mbrId") != null) {

					tempStr = nMap.get("mbrId").toString();
					if (tempStr.length() > 3) {
						maskStr = tempStr.substring(0, tempStr.length() - 3)
								+ "***";
						nMap.put("mbrId", maskStr);
						nMap.put("beforeMbrId", tempStr);
					} else {
						nMap.put("mbrId", "");
						nMap.put("beforeMbrId", "");
					}

				} else {
					nMap.put("mbrId", "");
					nMap.put("beforeMbrId", "");
				}

				
				if (nMap.get("telNo") != null) {

					tempStr = nMap.get("telNo").toString().trim();
					if (tempStr.length() > 4) {
						maskStr = tempStr.substring(0, tempStr.length() - 4)
								+ "****";
						nMap.put("telNo", maskStr);
						nMap.put("beforeTelNo", tempStr);
					} else {
						nMap.put("telNo", "");
						nMap.put("beforeTelNo", "");
					}

				} else {
					nMap.put("telNo", "");
					nMap.put("beforeTelNo", "");
				}
				
				if (nMap.get("etsMbrId") != null) {

					tempStr = nMap.get("etsMbrId").toString();
					if (tempStr.length() > 3) {
						maskStr = tempStr.substring(0, tempStr.length() - 3)
								+ "***";
						nMap.put("etsMbrId", maskStr);
						nMap.put("beforeEstMbrId", tempStr);
					} else {
						nMap.put("etsMbrId", "");
						nMap.put("beforeEstMbrId", "");
					}

				} else {
					nMap.put("etsMbrId", "");
					nMap.put("beforeEstMbrId", "");
				}

				
				if (nMap.get("etsTelNo") != null) {

					tempStr = nMap.get("etsTelNo").toString().trim();
					if (tempStr.length() > 4) {
						maskStr = tempStr.substring(0, tempStr.length() - 4)
								+ "****";
						nMap.put("etsTelNo", maskStr);
						nMap.put("beforeEstTelNo", tempStr);
					} else {
						nMap.put("etsTelNo", "");
						nMap.put("beforeEstTelNo", "");
					}

				} else {
					nMap.put("etsTelNo", "");
					nMap.put("beforeEstTelNo", "");
				}
			}
		}
		
		return list;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Map<String, Object> getDetail(Map<String, Object> searchInfo) {
		
		Map<String, Object> detail = new HashMap<String, Object>();
		
		// 사용자 정보
		Map<String, Object> mbrInfo = familyAuthDao.getDetail(searchInfo);

		String maskStr = "", tempStr = "";
		
		if (mbrInfo.get("mbrId") != null) {

			tempStr = mbrInfo.get("mbrId").toString();
			if (tempStr.length() > 3) {
				maskStr = tempStr.substring(0, tempStr.length() - 3)
						+ "***";
				mbrInfo.put("mbrId", maskStr);
			} else {
				mbrInfo.put("mbrId", "");
			}

		}

		if (mbrInfo.get("telNo") != null) {

			tempStr = mbrInfo.get("telNo").toString().trim();
			if (tempStr.length() > 4) {
				maskStr = tempStr.substring(0, tempStr.length() - 4)
						+ "****";
				mbrInfo.put("telNo", maskStr);
			} else {
				mbrInfo.put("telNo", "");
			}

		}
		
		if (mbrInfo.get("etsMbrId") != null) {

			tempStr = mbrInfo.get("etsMbrId").toString();
			if (tempStr.length() > 3) {
				maskStr = tempStr.substring(0, tempStr.length() - 3)
						+ "***";
				mbrInfo.put("etsMbrId", maskStr);
			} else {
				mbrInfo.put("etsMbrId", "");
			}

		}
		
		if (mbrInfo.get("etsTelNo") != null) {

			tempStr = mbrInfo.get("etsTelNo").toString().trim();
			if (tempStr.length() > 4) {
				maskStr = tempStr.substring(0, tempStr.length() - 4)
						+ "****";
				mbrInfo.put("etsTelNo", maskStr);
			} else {
				mbrInfo.put("etsTelNo", "");
			}

		}
		
		// 도어락 권한 정보
		searchInfo.put("svcTgtTypeCd", "0000");
		searchInfo.put("deviceTypeCd", "4003");
		List<Map<String, Object>> doorInfo = familyAuthDao.getAuthDetail(searchInfo);
		// 가스밸브 권한 정보
		searchInfo.put("deviceTypeCd", "1006");
		List<Map<String, Object>> gasInfo = familyAuthDao.getAuthDetail(searchInfo);
		// 침임감지 권한 정보
		searchInfo.put("deviceTypeCd", "0701");
		List<Map<String, Object>> trespsInfo = familyAuthDao.getAuthDetail(searchInfo);
		// AP 권한 정보
		searchInfo.put("svcTgtTypeCd", "0001");
		searchInfo.put("deviceTypeCd", "G000");
		List<Map<String, Object>> apInfo = familyAuthDao.getAuthDetail(searchInfo);
		
		detail.put("mbrInfo", mbrInfo);
		detail.put("doorInfo", doorInfo);
		detail.put("gasInfo", gasInfo);
		detail.put("trespsInfo", trespsInfo);
		detail.put("apInfo", apInfo);
		
		return detail;
	}

}
