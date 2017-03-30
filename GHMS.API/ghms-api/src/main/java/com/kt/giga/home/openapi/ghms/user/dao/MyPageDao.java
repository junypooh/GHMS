/*******************************************************************************
 * Copyright(c) 2015 KT. All rights reserved.
 * This software is the proprietary information of KT.
 *******************************************************************************/
package com.kt.giga.home.openapi.ghms.user.dao;

import java.util.List;

import com.kt.giga.home.openapi.ghms.user.domain.key.MasterUserKey;
import com.kt.giga.home.openapi.ghms.user.domain.vo.MasterUserVo;

/**
 * 
 * @author junypooh ( kyungjun.park@ceinside.co.kr )
 * @since 2015. 2. 11.
 */
public interface MyPageDao {
	
	/**
	 * 비상연락망 조회
	 * @param key		마스터 사용자 Key
	 * @return List<MasterUserVo>
	 */
	public List<MasterUserVo> selectEmcontacts(MasterUserKey key);

	/**
	 * 비상연락망 등록
	 * @param key
	 */
	public void insertEmcontacts(MasterUserKey key);

	/**
	 * 비상연락망 삭제
	 * @param key
	 */
	public int deleteEmcontacts(MasterUserKey key);
	
	/**
	 * 비상연락망 수정 (sms발송여부 UPDATE)
	 * 
	 * @param key
	 * @return
	 */
	public int updateEmcontacts(MasterUserKey key);

}
