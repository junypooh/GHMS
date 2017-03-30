package com.kt.giga.home.openapi.dao;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Param;
import org.json.simple.JSONArray;
import org.springframework.stereotype.Component;

import com.kt.giga.home.openapi.domain.AppUser;
import com.kt.giga.home.openapi.domain.User;

@Component("oldUserDao")
public interface UserDaoOld {
	
//	public int getSvcTgtSeq();
	public int getMbrsSeq();
	
	public AppUser getUserBase(String userId);
	public void insertUserBase(AppUser userBase);
	public void insertUserAuth(AppUser userAuth);
	
//	public int getOnlineAppUserCount(@Param("userId") String userId, @Param("expireTimeMinute") int expireTimeMinute);
	
	public AppUser getAppUser(@Param("userId") String userId, @Param("deviceId") String deviceId);
	public void insertAppUser(AppUser appUser);
	public void updateAppUser(AppUser appUser);
	
	public void insertAppUserToken(AppUser appUserToken);
	public void updateAppUserToken(AppUser appUserToken);
	
	// TODO 디바이스 관련 매퍼로 위치를 변경해야 합니다.
	public void updateDeviceName(@Param("spotDevId") String spotDevId, @Param("devNm") String devNm);
	
	
	// 아래는 CRUD 기본 샘플입니다. TODO 프로젝트 완료 후 제거해 주세요.
	public User getUser(String userId);
	public ArrayList<User> getUserList(@Param("limit") int limit);
	public void insertUser(User user);
	public void updateUser(User user);
	public void deleteUser(String userId);

	public int getUserCnt(String telNo);

	public void insertMbrsBas(AppUser appUser);

	public void insertSvcMbrsRel(AppUser appUser);

	public void insertSvcTgtAccsBas(AppUser appUser);

	public void insertSvcAccsHst(AppUser appUserBase);

	public void insertSvcTgtAccsAthnTxn(AppUser appUserBase);

	public int chkCustId(String custId);

	public void updateSvcTgtBas(int mbrsSeq);

	public void updateSvcTgtAccsAthnTxn(AppUser userBase);

	public void updateSvcTgtAccsBas(AppUser userBase);

	public void updateSvcAccsHst(AppUser userBase);

	public int getOnlineAppUserCount(AppUser userBase);

	public String getNoticeUrl(String svcCode);

	public int getParentsAgree(AppUser appUser);

	public JSONArray getTermsYn(AppUser appUser);

}
