package com.kt.giga.home.openapi.common;

import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 인증토큰 클래스.
 * 
 * @author 
 *
 */
public class AuthToken {
	
	/**
     * 타임 스탬프 시간 포맷
     */
    public static final SimpleDateFormat tsFormat = new SimpleDateFormat("yyyyMMddHHmmssSSS");
    /**
     * 기본 캐릭터셋. UTF-8
     */
    public static final String charset = "UTF-8";
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
	 * 유저 아이디
	 */
	private String userId;
	/**
	 * 인증 토큰 스트링
	 */
	private String token;
	/**
	 * 타임 스탬프. 발급 시간
	 */
	private String timeStamp;
	/**
	 * 서비스 코드
	 */
	private String svcCode;
	/**
	 * 요청 IP
	 */
	private String requestIp;
	
	/**
	 * 생성자. Private 이며 직접 생성하지 않고 별도 static 메쏘드로 생성
	 * 
	 * @param deviceId		앱 단말 식별자
	 * @param telNo			전화번호
	 * @param pnsRegId		PNS Registration ID
	 * @param userNo		유저 넘버
	 * @param userId		유저 아이디
	 * @param svcCode		서비스 코드
	 */
	private AuthToken(String deviceId, String telNo, String pnsRegId, String userNo, String userId, String svcCode, String requestIp) {
		this.deviceId = deviceId;
		this.telNo = telNo;
		this.pnsRegId = pnsRegId;
		this.userNo = userNo;
		this.userId = userId;
		this.svcCode = svcCode;
		this.requestIp = requestIp;
	}
	
	/**
	 * 생성자. Private 이며 직접 생성하지 않고 별도 static 메쏘드로 생성
	 * 
	 * @param deviceId		앱 단말 식별자
	 * @param telNo			전화번호
	 * @param pnsRegId		PNS Registration ID
	 * @param userNo		유저 넘버
	 * @param userId		유저 아이디
	 * @param svcCode		서비스 코드
	 */
	private AuthToken(String deviceId, String telNo, String pnsRegId, String userNo, String userId, String svcCode) {
		new AuthToken(deviceId, telNo, pnsRegId, userNo, userId, svcCode, null);
	}
	
	/**
	 * 생성자. Private.
	 */
	private AuthToken() {

    }

    /**
     * 인증 토큰 인코딩 메쏘드
     *
     * @param deviceId 앱 단말 식별자
     * @param telNo    전화번호
     * @param pnsRegId PNS Registration ID
     * @param userNo   유저 넘버 (String)
     * @param userId   유저 아이디
     * @param svcCode  서비스 코드
     * @return 인증 토큰 객체
     */
    public static AuthToken encodeAuthToken(String deviceId, String telNo, String pnsRegId, String userNo, String userId, String svcCode, String requestIp) {
        AuthToken authToken = new AuthToken(deviceId, telNo, pnsRegId, userNo, userId, svcCode, requestIp);
        authToken.encode();
        return authToken;
    }

    /**
     * 인증 토큰 인코딩 메쏘드
     *
     * @param deviceId 앱 단말 식별자
     * @param telNo    전화번호
     * @param pnsRegId PNS Registration ID
     * @param userNo   유저 넘버 (String)
     * @param userId   유저 아이디
     * @param svcCode  서비스 코드
     * @return 인증 토큰 객체
     */
    public static AuthToken encodeAuthToken(String deviceId, String telNo, String pnsRegId, String userNo, String userId, String svcCode) {
        return encodeAuthToken(deviceId, telNo, pnsRegId, userNo, userId, svcCode, null);
    }

    /**
     * 인증 토큰 인코딩 메쏘드
     *
     * @param deviceId 앱 단말 식별자
     * @param telNo    전화번호
     * @param pnsRegId PNS Registration ID
     * @param userNo   유저 넘버 (Long)
     * @param userId   유저 아이디
     * @param svcCode  서비스 코드
     * @return 인증 토큰 객체
     */
    public static AuthToken encodeAuthToken(String deviceId, String telNo, String pnsRegId, long userNo, String userId, String svcCode) {
        return encodeAuthToken(deviceId, telNo, pnsRegId, String.valueOf(userNo), userId, svcCode);
    }

    /**
     * 인증 토큰 인코딩 메쏘드
     *
     * @param authToken 인증토큰
     * @param requestIp 요청지 IP
     * @return 인증 토큰 객체
     */
    public static AuthToken encodeAuthTokenAddRequestIp(String authToken, String requestIp) {
        AuthToken token = decodeAuthToken(authToken);
        AuthToken newAutoToken = encodeAuthToken(token.getDeviceId(), token.getTelNo(), token.getPnsRegId(),
                token.getUserNo(), token.getUserId(), token.getSvcCode(), requestIp);
        newAutoToken.encode();
        return newAutoToken;
    }

    /**
     * 인증 토큰 디코딩 메쏘드
     *
     * @param token 인증 토큰
     * @return 인증 토큰 객체
     */
    public static AuthToken decodeAuthToken(String token) {
        AuthToken authToken = new AuthToken();
        authToken.decode(token);
        return authToken;
    }

    /**
     * 인증 토큰 갱신 메쏘드
     *
     * @param token 인증 토큰
     * @return 갱신된 인증 토큰
     */
    public static AuthToken refresh(String token) {
        AuthToken authToken = decodeAuthToken(token);
        authToken.encode();
        return authToken;
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
	
	public String getUserId() {
		return userId;
    }
	
	public String getToken() {
		return token;
	}

    public String getTimeStamp() {
		return timeStamp;
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
	
	public String getSvcCode(){
		return svcCode;
	}
	
	public String getRequestIp(){
		return requestIp;
    }

    /**
     * 토큰의 만료 여부 체크 메쏘드
     *
     * @param min            토큰 만료 시간 분
	 * @return				만료 여부
	 */
	public boolean expired(int min) {
        return (System.currentTimeMillis() - getTimeStampLong() > min * 60 * 1000);
	}
	
	/**
	 * AES 암호화 키 생성 메쏘드
     *
	 * @return				16 바이트 암호화 키
     */
	private byte[] getKey() {
		return new byte[]{100,104,108,125,98,8,1,35,29,10,15,112,13,114,45,96};
	}
	
	/**
	 * AES 암호화 IV 생성 메쏘드
     *
	 * @return				16 바이트 암호화 IV
     */
	private byte[] getIv() {
		return new byte[]{7,53,10,113,48,1,68,44,9,37,81,90,65,71,51,116};
	}
	
	/**
	 * 토큰 항목 값의 URLEncoding 처리 메쏘드
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
	 * 토큰 항목 값의 URLDecoding 처리 메쏘드
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
     * 인증 토큰 인코딩 메쏘드
     */
    private void encode() {

		this.timeStamp = tsFormat.format(new Date(System.currentTimeMillis()));

        String kv =
                "deviceId=" + toTokenValue(deviceId) +
                        "&telNo="		+	toTokenValue(telNo) +
				"&pnsRegId=" + toTokenValue(pnsRegId) +
                        "&userId=" + toTokenValue(userId) +
                        "&svcCode=" + toTokenValue(svcCode) +
				"&ts="			+	timeStamp +
                        "&userNo="		+	toTokenValue(userNo) +
				"&requestIp="	+	toTokenValue(requestIp);

        try {
			this.token = CipherUtils.encryptAESCTRNoPaddingHex(getKey(), getIv(), kv);
		} catch(Exception e) {
            // ignore
        }
	}
	
	/**
	 * 인증 토큰 디코딩 메쏘드.
     *
	 * @param token			인증 토큰
	 */
	private void decode(String token) {
		try {

			this.token = token;

			String plainText = CipherUtils.decryptAESCTRNoPaddingHex(getKey(), getIv(), token);
			String plainTextTokens[] = StringUtils.splitPreserveAllTokens(plainText, "&");

			this.deviceId	= fromTokenValue(StringUtils.splitPreserveAllTokens(plainTextTokens[0], "=")[1]);
			this.telNo		= fromTokenValue(StringUtils.splitPreserveAllTokens(plainTextTokens[1], "=")[1]);
			this.pnsRegId	= fromTokenValue(StringUtils.splitPreserveAllTokens(plainTextTokens[2], "=")[1]);
			this.userId		= fromTokenValue(StringUtils.splitPreserveAllTokens(plainTextTokens[3], "=")[1]);
			this.svcCode    = fromTokenValue(StringUtils.splitPreserveAllTokens(plainTextTokens[4], "=")[1]);
            this.timeStamp = fromTokenValue(StringUtils.splitPreserveAllTokens(plainTextTokens[5], "=")[1]);
            this.userNo		= fromTokenValue(StringUtils.splitPreserveAllTokens(plainTextTokens[6], "=")[1]);
			this.requestIp	= fromTokenValue(StringUtils.splitPreserveAllTokens(plainTextTokens[7], "=")[1]);

		} catch(Exception e) {
			// ignore
        }
	}
	
	/**
	 * 초기 인증 토큰 유효성 체크 메쏘드
     *
	 * @return				인증 토큰 유효성 체크 결과
	 */
	public boolean isValidInitToken() {
        return
				StringUtils.isNotBlank(this.deviceId) &&
                        StringUtils.isNotBlank(this.svcCode) &&
				StringUtils.isNotBlank(this.timeStamp);
    }
	
	/**
     * 로그인 인증 토큰 유효성 체크 메쏘드
     *
	 * @return				인증 토큰 유효성 체크 결과
	 */
	public boolean isValidLoginToken() {
		// userId 체크하지 않음
        return
				StringUtils.isNotBlank(this.deviceId) &&
				StringUtils.isNotBlank(this.svcCode) &&
				StringUtils.isNotBlank(this.timeStamp);
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}

}
