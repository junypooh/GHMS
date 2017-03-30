/*******************************************************************************
 * Copyright(c) 2015 KT. All rights reserved.
 * This software is the proprietary information of KT.
 *******************************************************************************/
package com.kt.giga.home.openapi.ghms.common.token;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.context.ContextLoader;

import com.kt.giga.home.openapi.ghms.common.GHmsConstants.ErrorCode;
import com.kt.giga.home.openapi.ghms.user.service.UserService;
import com.kt.giga.home.openapi.ghms.util.date.DateTimeUtil;
import com.kt.giga.home.openapi.ghms.util.encrypt.CipherUtils;
import com.kt.giga.home.openapi.ghms.util.exception.APIException;

/**
 * 인증토큰 클래스.
 * @author junypooh ( kyungjun.park@ceinside.co.kr )
 * @since 2015. 1. 30.
 */
public class AuthToken {

    /**
     * 로거
     */
    private Logger log = LoggerFactory.getLogger(this.getClass());

	/**
	 * App 구분 (AND, IOS)
	 */
	private String userAgent;
	
	/**
	 * 앱 단말 식별자
	 */
	private String deviceId;
	
	/**
	 * 전화번호
	 */    
	private String telNo;
	
	/**
	 * PNS Registration ID
	 */
	private String pnsRegId;
	
	/**
	 * 유저 넘버. 식별자
	 */
	private String userNo;
	
	/**
	 * credential 아이디
	 */
	private String credentialId;
	
	/**
	 * 인증 토큰 스트링
	 */
	private String token;
	
	/**
	 * 타임 스탬프. 발급 시간
	 */
	private String timeStamp;
	
	/**
	 * 회원구분[T,M,S](1)+지역코드(3)+서비스테마코드(3)+단위서비스코드(3) (10자리)
	 */
	private String unionSvcCd;
    
    /**
     * 회원 구분 (T: 구분없음, M: 마스터 , S: 슬레이브)
     */
    private String userType;
    
    /**
     * 지역 코드
     */
    private String dstrCd;
    
    /**
     * 서비스 테마 코드
     */
    private String svcThemeCd;
    
    /**
     * 단위 서비스 코드
     */
    private String unitSvcCd;
    
    /**
     * 앱 버젼
     */
    private String appVer;
    
    private String mbrId;
	
	/**
	 * 타임 스탬프 시간 포맷
	 */
//	public static final SimpleDateFormat tsFormat = new SimpleDateFormat("yyyyMMddHHmmssSSS");
//	public static final SimpleDateFormat tsFormat = new SimpleDateFormat("yyyyMMddHHmmss");
	
	/**
	 * 기본 캐릭터셋. UTF-8
	 */
	public static final String charset = "UTF-8";
	
	/**
	 * 생성자. Private 이며 직접 생성하지 않고 별도 static 메서드로 생성
	 * 
     * @param user-agent    	App 구분 (AND, IOS)
	 * @param deviceId  		앱 단말 식별자
	 * @param telNo    			전화번호
	 * @param pnsRegId     		PNS Registration ID
	 * @param userNo       		유저 넘버
	 * @param credentialId  	credential 아이디
	 * @param unionSvcCd		회원구분[T,M,S](1)+지역코드(3)+서비스테마코드(3)+단위서비스코드(3) (10자리)
	 */
	private AuthToken(String userAgent, String deviceId, String telNo, String pnsRegId, String userNo, String credentialId, String unionSvcCd, String appVer, String mbrId) {
	    
	    if(unionSvcCd.length() == 10){
	        this.userType       = unionSvcCd.substring(0, 1);
	        this.dstrCd       = unionSvcCd.substring(1, 4);
	        this.svcThemeCd   = unionSvcCd.substring(4, 7);
	        this.unitSvcCd    = unionSvcCd.substring(7, 10);
	    }
	    
	    this.userAgent		  = userAgent;
		this.deviceId         = deviceId;
		this.telNo            = telNo;
		this.pnsRegId         = pnsRegId;
		this.userNo           = userNo;
		this.credentialId     = credentialId;
		this.unionSvcCd       = unionSvcCd;
		this.appVer           = appVer;
		this.mbrId            = mbrId;
	}
	
	/**
	 * 생성자. Private.
	 */
	private AuthToken() {
		
	}
	
	/**
	 * @return TODO
	 */
	public String getUserAgent() {
		return userAgent;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public String getTelNo() {
		return telNo;
	}
	
	public String getPnsRegId() {
		return pnsRegId;
	}

	public String getUserNo() {
		return userNo;
	}
	
	public long getUserNoLong() {
		return NumberUtils.toLong(getUserNo(), -1);
	}
	
	public String getCredentialId() {
		return credentialId;
	}	

	public String getToken() {
		return token;
	}

	public String getTimeStamp() {
		return timeStamp;
	}	

	public String getMbrId() {
		return mbrId;
	}
	
	/**
	 * 토큰의 만료 여부 체크 메서드
	 * 
	 * @param min			토큰 만료 시간 분
	 * @return				만료 여부
	public boolean expired(int min) {
		return (System.currentTimeMillis() - getTimeStampLong() > min * 60 * 1000); 
	}
	
	public long getTimeStampLong() {
		
		long tsLong = 0;
		
		try {
			tsLong = tsFormat.parse(getTimeStamp()).getTime();
		} catch(Exception e) {
			// ignore
		}
		
		return tsLong; 
	}
	 */
	public boolean expired(int min) {

		SimpleDateFormat tsFormat = new SimpleDateFormat("yyyyMMddHHmmss");
		String str = tsFormat.format(new Date());
		
		log.debug("expired > " + str);
		log.debug("expired > " + getTimeStamp());
		
		int dateTimeDiff = DateTimeUtil.getDateTimeDiff(getTimeStamp(), str);
		
		log.debug("expired > " + dateTimeDiff / 60);
		
		if(dateTimeDiff / 60 > min) {
			log.error("GHMS_EXPIRED_("+(dateTimeDiff / 60 > min)+")" + this.userNo + "_" + this.telNo + "_" + str + "_" + getTimeStamp() + "_" + this.timeStamp);
		}
		
		return (dateTimeDiff / 60 > min); 
	}
/*
	public static void main(String[] atg) throws Exception {
		
		SimpleDateFormat tsFormat = new SimpleDateFormat("yyyyMMddHHmmss");
		String str = tsFormat.format(new Date());
		System.out.println(str);
		
		int dateTimeDiff = DateTimeUtil.getDateTimeDiff("20150624170000", str);
		System.out.println(dateTimeDiff / 60);
	}
*/
	public String getUnionSvcCd(){
		return unionSvcCd;
	}
    
    public String getUserType(){
        return userType;
    }
    
    private void setUserType(String userType){
        this.userType = userType;
    }
    
    public String getDstrCd(){
        return dstrCd;
    }
    
    public String getSvcThemeCd(){
        return svcThemeCd;
    }
    
    public String getUnitSvcCd(){
        return unitSvcCd;
    }
    
    public String getAppVer(){
        return appVer;
    }
	
	/**
	 * AES 암호화 키 생성 메서드
	 * 
	 * @return				16 바이트 암호화 키
	 */
	private byte[] getKey() {
		return new byte[]{100,104,108,125,98,8,1,35,29,10,15,112,13,114,45,96};
	}
	
	/**
	 * AES 암호화 IV 생성 메서드
	 * 
	 * @return				16 바이트 암호화 IV
	 */
	private byte[] getIv() {
		return new byte[]{7,53,10,113,48,1,68,44,9,37,81,90,65,71,51,116};
	}
	
	/**
	 * 토큰 항목 값의 URLEncoding 처리 메서드
	 * 
	 * @param value		토큰 항목 값
	 * @return			URLEncoding 된 토큰 항목 값
	 */
	private String toTokenValue(String value) {
		
		String result = null;
		
		try {
			result = URLEncoder.encode(StringUtils.defaultString(value), "UTF-8");
		} catch(Exception e) {
			// ignore
		}
		
		return result;
	}
	
	/**
	 * 토큰 항목 값의 URLDecoding 처리 메서드
	 * 
	 * @param value			토큰 항목 값
	 * @return				URLDecoding 된 토큰 항목 값
	 */
	private String fromTokenValue(String value) {

		String result = null;
		
		try {
			result = URLDecoder.decode(value, "UTF-8");
		} catch(Exception e) {
			
		}
		
		return result;
	}
	
	/**
	 * 인증 토큰 인코딩 메서드
	 */
	private void encode() {

//		this.timeStamp = tsFormat.format(new Date(System.currentTimeMillis()));
		SimpleDateFormat tsFormat = new SimpleDateFormat("yyyyMMddHHmmss");
		this.timeStamp = tsFormat.format(new Date());

		String kv =
				"deviceId=" 		+	toTokenValue(deviceId) +
				"&telNo="   		+	toTokenValue(telNo) +
				"&pnsRegId="    	+	toTokenValue(pnsRegId) +
				"&credentialId="  	+	toTokenValue(credentialId) + 
				"&unionSvcCd=" 	    +   toTokenValue(userType + unionSvcCd.substring(1)) +
//				"&unionSvcCd=" 	    +   toTokenValue(unionSvcCd) +
				"&ts="  			+	timeStamp +
				"&userNo="  		+	toTokenValue(userNo) +
				"&userType="        +   toTokenValue(userType) +
				"&dstrCd="          +   toTokenValue(dstrCd) +
				"&svcThemeCd="      +   toTokenValue(svcThemeCd) +
				"&unitSvcCd="       +   toTokenValue(unitSvcCd) +
				"&appVer="          +   toTokenValue(appVer) +
				"&userAgent=" 		+	toTokenValue(userAgent) +
				"&mbrId=" 			+	toTokenValue(mbrId);		
		
		try {
			this.token = CipherUtils.encryptAESCTRNoPaddingHex(getKey(), getIv(), kv);
		} catch(Exception e) {
			// ignore
		}		
	}	
	
	/**
	 * 인증 토큰 디코딩 메서드.
	 * 
	 * @param token			인증 토큰
	 */
	private void decode(String token) {
		try {
			
			this.token = token;

			String plainText = CipherUtils.decryptAESCTRNoPaddingHex(getKey(), getIv(), token);
			String plainTextTokens[] = StringUtils.splitPreserveAllTokens(plainText, "&");
			
			this.deviceId	     = fromTokenValue(StringUtils.splitPreserveAllTokens(plainTextTokens[0], "=") [1]);
			this.telNo		     = fromTokenValue(StringUtils.splitPreserveAllTokens(plainTextTokens[1], "=") [1]);
			this.pnsRegId	     = fromTokenValue(StringUtils.splitPreserveAllTokens(plainTextTokens[2], "=") [1]);
			this.credentialId    = fromTokenValue(StringUtils.splitPreserveAllTokens(plainTextTokens[3], "=") [1]);
			this.unionSvcCd      = fromTokenValue(StringUtils.splitPreserveAllTokens(plainTextTokens[4], "=") [1]);
			this.timeStamp	     = fromTokenValue(StringUtils.splitPreserveAllTokens(plainTextTokens[5], "=") [1]);
			this.userNo		     = fromTokenValue(StringUtils.splitPreserveAllTokens(plainTextTokens[6], "=") [1]);	
			this.userType	     = fromTokenValue(StringUtils.splitPreserveAllTokens(plainTextTokens[7], "=") [1]);	
			this.dstrCd	         = fromTokenValue(StringUtils.splitPreserveAllTokens(plainTextTokens[8], "=") [1]);	
			this.svcThemeCd      = fromTokenValue(StringUtils.splitPreserveAllTokens(plainTextTokens[9], "=") [1]);	
			this.unitSvcCd       = fromTokenValue(StringUtils.splitPreserveAllTokens(plainTextTokens[10], "=")[1]);
			this.appVer          = fromTokenValue(StringUtils.splitPreserveAllTokens(plainTextTokens[11], "=")[1]);
			this.userAgent       = fromTokenValue(StringUtils.splitPreserveAllTokens(plainTextTokens[12], "=")[1]);
			this.mbrId       	 = fromTokenValue(StringUtils.splitPreserveAllTokens(plainTextTokens[13], "=")[1]);
/*			
			System.out.println("deviceId : " + this.deviceId	); 
			System.out.println("telNo : " + this.telNo		); 
			System.out.println("pnsRegId : " + this.pnsRegId	); 
			System.out.println("credentialId : " + this.credentialId);
			System.out.println("unionSvcCd : " + this.unionSvcCd  );
			System.out.println("timeStamp : " + this.timeStamp	); 
			System.out.println("userNo : " + this.userNo		); 
			System.out.println("userType : " + this.userType	); 
			System.out.println("dstrCd : " + this.dstrCd	    ); 
			System.out.println("svcThemeCd : " + this.svcThemeCd  );
			System.out.println("unitSvcCd : " + this.unitSvcCd   );
			System.out.println("appVer : " + this.appVer      );
			System.out.println("userAgent : " + this.userAgent   );
*/
		} catch(Exception e) {
			// ignore
		}
	}
/*
	public static void main(String[] args) throws Exception {
		
		String token = "87fbe11bc11525636834dff1c69f93cb83986f6503f1ee5ac0064cef3a304c3611354cb6f83109f47f843e7c1f5b6af555a282378e93326356f4437a41c33141a9666217d2e458bc0886e49d10a9d8ac2e5555ee1cb9f94469c01b36396279a8b667ceab07c22d5dc796e5277a1cfccaedaa5adccc06d0cf4084be68462109ca2caf2170bb15f5804bd108b7de5320383b39cfaedb3d3a49d1578df41b9072e7bbcd03eda552b283ebfcf20f2da84c901b2ef0d9086ac7a276491b5dfeab6e642781690d25fa9c55382ed8f6f73d12a04e1388e9cd959b816dfd5fa13cdbb835c655044478e4a32ce5a18d5b98e8";


		String plainText = CipherUtils.decryptAESCTRNoPaddingHex(new byte[]{100,104,108,125,98,8,1,35,29,10,15,112,13,114,45,96}, new byte[]{7,53,10,113,48,1,68,44,9,37,81,90,65,71,51,116}, token);
		String plainTextTokens[] = StringUtils.splitPreserveAllTokens(plainText, "&");
		
		for(String str : plainTextTokens) {
			System.out.println(str);
		}
	}
*/
	/**
	 * 초기 인증 토큰 유효성 체크 메서드
	 * 
	 * @return				인증 토큰 유효성 체크 결과
	 */
	public boolean isValidInitToken() {
		return 
				StringUtils.isNotBlank(this.deviceId) &&
				StringUtils.isNotBlank(this.telNo) &&
				StringUtils.isNotBlank(this.pnsRegId) &&
				StringUtils.isNotBlank(this.unionSvcCd) &&
				StringUtils.isNotBlank(this.timeStamp) &&
				StringUtils.isNotBlank(this.userType) &&
				StringUtils.isNotBlank(this.dstrCd) &&
				StringUtils.isNotBlank(this.svcThemeCd) &&
				StringUtils.isNotBlank(this.unitSvcCd) &&
				StringUtils.isNotBlank(this.appVer) &&
				StringUtils.isNotBlank(this.userAgent);
	}
	
	/**
	 * 로그인 인증 토큰 유효성 체크 메서드
	 * 
	 * @return				인증 토큰 유효성 체크 결과
	 */
	public boolean isValidLoginToken() {
		return 
				StringUtils.isNotBlank(this.deviceId) &&
				StringUtils.isNotBlank(this.telNo) &&
				StringUtils.isNotBlank(this.unionSvcCd) &&
				StringUtils.isNotBlank(this.timeStamp) &&
				StringUtils.isNotBlank(this.userType) &&
				StringUtils.isNotBlank(this.dstrCd) &&
				StringUtils.isNotBlank(this.svcThemeCd) &&
				StringUtils.isNotBlank(this.unitSvcCd) &&
				StringUtils.isNotBlank(this.credentialId) &&
				StringUtils.isNotBlank(this.appVer) &&
				StringUtils.isNotBlank(this.userAgent) &&
				StringUtils.isNotBlank(this.mbrId);
	}	
	
	/**
	 * 인증 토큰 인코딩 메서드
	 * 
     * @param user-agent    App 구분 (AND, IOS)
	 * @param deviceId		앱 단말 식별자
	 * @param telNo			전화번호
	 * @param pnsRegId		PNS Registration ID
	 * @param userNo		유저 넘버 (String)
	 * @param credentialId	credential 아이디
	 * @param unionSvcCd	회원구분[T,M,S](1)+지역코드(3)+서비스테마코드(3)+단위서비스코드(3) (10자리)
     * @param appVer        앱 버젼
	 * @return				인증 토큰 객체
	 */
	public static AuthToken encodeAuthToken(String userAgent, String deviceId, String telNo, String pnsRegId, String userNo, String credentialId, String unionSvcCd, String appVer, String mbrId) {
		AuthToken authToken = new AuthToken(userAgent, deviceId, telNo, pnsRegId, userNo, credentialId, unionSvcCd, appVer, mbrId);
		authToken.encode();
		return authToken;
	}
	
	/**
	 * 인증 토큰 인코딩 메서드
	 * 
     * @param user-agent    App 구분 (AND, IOS)
	 * @param deviceId		앱 단말 식별자
	 * @param telNo			전화번호
	 * @param pnsRegId		PNS Registration ID
	 * @param userNo		유저 넘버 (Long)
	 * @param credentialId	credential 아이디
	 * @param unionSvcCd	회원구분[T,M,S](1)+지역코드(3)+서비스테마코드(3)+단위서비스코드(3) (10자리)
     * @param appVer        앱 버젼
	 * @return				인증 토큰 객체
	 */
	public static AuthToken encodeAuthToken(String userAgent, String deviceId, String telNo, String pnsRegId, long userNo, String credentialId, String unionSvcCd, String appVer, String mbrId) {
		return encodeAuthToken(userAgent, deviceId, telNo, pnsRegId, String.valueOf(userNo), credentialId, unionSvcCd, appVer, mbrId);
	}
	
	/**
	 * 인증 토큰 디코딩 메서드
	 * 
	 * @param token			인증 토큰
	 * @return				인증 토큰 객체
	 */
	public static AuthToken decodeAuthToken(String token) {
		AuthToken authToken = new AuthToken();
		authToken.decode(token);
		return authToken;
	}
	
	/**
	 * 인증 토큰 갱신 메서드
	 * 
	 * @param token			인증 토큰
     * @param isMaster		마스터/슬레이브 구분
	 * @return				갱신된 인증 토큰
	 */
	public static AuthToken refresh(String token, String isMaster) {
		AuthToken authToken = decodeAuthToken(token);
		if(isMaster != null && isMaster.length() == 1) {
			authToken.setUserType(isMaster);	
		}
		authToken.encode();
		return authToken;
	}
    
    /**
     * 아이디/패스워드 인증 후 발급한 로그인 인증토큰 유효성을 체크하는 메서드 
     * 
     * @param authToken     로그인 인증 토큰
     * @return              로그인 인증 토큰 객체
     * @throws APIException
     */
    public static AuthToken checkLoginAuthToken(String token) throws APIException {
        
        return checkLoginAuthToken(token, null);
    }
    
    /**
     * 아이디/패스워드 인증 후 발급한 로그인 인증토큰 유효성을 체크하는 메서드 
     * 
     * @param authToken     로그인 인증 토큰
     * @param isMaster		마스터/슬레이브 구분
     * @return              로그인 인증 토큰 객체
     * @throws APIException
     */
    public static AuthToken checkLoginAuthToken(String token, String isMaster) throws APIException {
        AuthToken authToken = decodeAuthToken(token);

		// 중복로그인임시로직 시작
        UserService userService = (UserService)ContextLoader.getCurrentWebApplicationContext().getBean("userService");
        HashMap<String, Object> userSvcExist = null;
		try {
			userSvcExist = userService.selectUserSvcExistCnt(authToken.getUserNoLong(), authToken.getDstrCd(), authToken.getSvcThemeCd(), authToken.getUnitSvcCd(), authToken.getDeviceId());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        String stcbCnt          = ((Long)userSvcExist.get("stcbCnt")).toString();
        String stcbDeviceCnt    = ((Long)userSvcExist.get("stcbDeviceCnt")).toString();
        
        // 로그인 처리 되어있는데, 다른 장비에서 이미 로그인 되어 있다면 Exception 발생
        if(!"0".equals(stcbCnt) && "0".equals(stcbDeviceCnt)){
        	throw new APIException("Duplicate LoginDevice", HttpStatus.UNAUTHORIZED, ErrorCode.DUPLICATE_LOGIN_AUTH_TOKEN);
        }
        // 중복로그인임시로직 끝

        if(!authToken.isValidLoginToken()) {
            throw new APIException("Invalid LoginAuthToken", HttpStatus.UNAUTHORIZED, ErrorCode.INVALID_LOGIN_AUTH_TOKEN);
        }
        // timeStemp 가 120분전이라면 Exception던짐
        if(authToken.expired(60 * 2)){
            throw new APIException("Expired LoginAuthToken", HttpStatus.UNAUTHORIZED, ErrorCode.EXPIRED_AUTH_TOKEN);
        }
        
        AuthToken newAuthToken =  refresh(token, isMaster);
        
        return newAuthToken;
    }
    
    /**
     * 앱 초기 실행에서 발급한 인증토큰 유효성을 체크하는 메서드
     * 
     * @param token         인증 토큰
     * @return              갱신된 인증 토큰
     */
	public static AuthToken checkInitAuthToken(String token) throws APIException{
	    AuthToken authToken = decodeAuthToken(token);
        if(!authToken.isValidInitToken()) {
            throw new APIException("Invalid InitAuthToken", HttpStatus.UNAUTHORIZED, ErrorCode.INVALID_INIT_AUTH_TOKEN);
        }
	    return authToken;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}
}
