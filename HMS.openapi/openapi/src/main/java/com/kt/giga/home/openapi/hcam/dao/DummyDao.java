package com.kt.giga.home.openapi.hcam.dao;

import java.util.ArrayList;

import com.kt.giga.home.openapi.hcam.domain.User;
import com.kt.giga.home.openapi.vo.spotdev.SpotDev;

public interface DummyDao {
	
	public void deleteTermsAgree(String userId);
	
	public void deleteParentsAgree(String userId);	
	
	public void updateSvcTgtConnBas(String userId);

	public User selectUserInfo(String devUUID);
	
	public ArrayList<SpotDev> selectSpotDevList(String devUUID);
}
