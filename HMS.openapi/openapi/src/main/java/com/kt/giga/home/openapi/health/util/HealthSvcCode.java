package com.kt.giga.home.openapi.health.util;

import com.kt.giga.home.openapi.service.APIException;


public class HealthSvcCode {
	
	public String GetDstrCd(String svcCode) throws APIException, Exception {
		String dstr_cd, svc_theme_cd, unit_svc_cd = "";	// 지역코드, 서비스 테마코드, 단위 서비스 코드
		
		if(svcCode.length() != 9){
			dstr_cd		= svcCode;
		}else{
			dstr_cd			= svcCode.substring(0, 3);
			svc_theme_cd	= svcCode.substring(3, 6);
			unit_svc_cd		= svcCode.substring(6, 9);
		}
		return dstr_cd;
	}
	
	public String GetSvcThemeCd(String svcCode) throws APIException, Exception {
		String dstr_cd, svc_theme_cd, unit_svc_cd = "";	// 지역코드, 서비스 테마코드, 단위 서비스 코드
		
		if(svcCode.length() != 9){
			svc_theme_cd	= svcCode;
		}else{
			dstr_cd			= svcCode.substring(0, 3);
			svc_theme_cd	= svcCode.substring(3, 6);
			unit_svc_cd		= svcCode.substring(6, 9);
		}
		return svc_theme_cd;
	}
	
	public String GetUnitSvcCd(String svcCode) throws APIException, Exception {
		String dstr_cd, svc_theme_cd, unit_svc_cd = "";	// 지역코드, 서비스 테마코드, 단위 서비스 코드
		
		if(svcCode.length() != 9){
			unit_svc_cd		= svcCode;
		}else{
			dstr_cd			= svcCode.substring(0, 3);
			svc_theme_cd	= svcCode.substring(3, 6);
			unit_svc_cd		= svcCode.substring(6, 9);
		}
		return unit_svc_cd;
	}

	public String GetSvcCode(String dstr_cd, String svc_theme_cd, String unit_svc_cd) throws APIException, Exception {
		String svcCode		= "";
		
		if( ( dstr_cd.length() + svc_theme_cd.length() + unit_svc_cd.length() ) != 9){
			throw new Exception("sccCode length is not 9");
		}else{
			svcCode			= dstr_cd + svc_theme_cd + unit_svc_cd;
		}
		return svcCode;
	}

	
}
