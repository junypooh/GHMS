/*******************************************************************************
 * Copyright(c) 2015 KT. All rights reserved.
 * This software is the proprietary information of KT.
 *******************************************************************************/
package com.kt.giga.home.openapi.ghms.util.string;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import org.apache.commons.lang.math.NumberUtils;

import com.kt.giga.home.openapi.ghms.kafka.type.MicroTimestampType;

/**
 * 
 * @author junypooh ( kyungjun.park@ceinside.co.kr )
 * @since 2015. 2. 16.
 */
public class StringUtils {

	/**
	 * @param svcUnitCd
	 * @param svcTgtSeq
	 * @param spotDevSeq
	 * @param snsnCd(not use)
	 * @param transacId
	 * @return salt.reverse(svcTgtSeq)-svcCode.hash(서비스코드)-svcTgtSeq.hash(서비스대상일련번호)-spotDevSeq.hash(서비스장치일련번호)-desc_contlDt(제어시간-역순) 
	 */
	public static String getHbaseRowKey(String svcUnitCd, String svcTgtSeq, String spotDevSeq, String transacId){
		String delimeter = "-";
		StringBuffer buffer = new StringBuffer();
		
		buffer.append(reverseString(svcTgtSeq));	/** salt */
		buffer.append(delimeter);
		buffer.append(svcUnitCd);	/** 서비스코드 */
		buffer.append(delimeter);
		buffer.append(svcTgtSeq);	/** 서비스대상일련번호 */
		buffer.append(delimeter);
		buffer.append(spotDevSeq);	/** 서비스장치일련번호 */
		buffer.append(delimeter);
		buffer.append(MicroTimestampType.TRANSAC_ID.getReverseTimestamp(transacId));	/** 제어시간-역순 */
		return buffer.toString();
	}
	
	/**
	 * @param input
	 * @return reverse string
	 */
	public static String reverseString(String input) {
		return ( new StringBuffer(input) ).reverse().toString();
	}	
	/**
	 * @param input
	 * @return hash 문자열(MD5 : 128bit, SHA-256 : 160bit)
	 */
	public static String strToHash(String input, String algorithm){
		String output = ""; 
		try{
			MessageDigest md = MessageDigest.getInstance(algorithm); 
			md.update(input.getBytes()); 
			byte byteData[] = md.digest();
			StringBuffer sb = new StringBuffer();
			for(int i = 0 ; i < byteData.length ; i++){
				sb.append(Integer.toString((byteData[i]&0xff) + 0x100, 16).substring(1));
			}
			output = sb.toString();
		}catch(NoSuchAlgorithmException e){
			e.printStackTrace(); 
			output = null; 
		}
		return output;
	}
	
	/**
	 * @param byte array 
	 * @return hex string
	 */
	public static String byteArrayToHex(byte[] a) {
		   StringBuilder sb = new StringBuilder(a.length * 2);
		   for(byte b: a){
		      sb.append(String.format("%02x", b & 0xff));
		   }
		   return sb.toString();
	}
/*	
	public static void main(String[] arg) {
		
		String time = "01:00";
		System.out.println(Integer.parseInt(time.replaceAll(":", "")));

		byte b = (byte) 0x04;
		byte[] binValTxn = new byte[1];
		binValTxn[0] = b;
		
		System.out.println(byteArrayToHex(binValTxn));

	}
*/	
	/**
	 * @param aBuf
	 * @param aLen
	 * @return 아스크코드 값의 여부를 리턴한다.
	 */
	public static boolean isAscii(byte[] aBuf, int aLen) {
		   
		for(int i=0; i<aLen; i++) {
		   if ((0x0080 & aBuf[i]) != 0) {
		      return false ;
		   }
		}
	   return true ;
	}	
	
	/**
     * 델리미터로 문자열 자른 후 배열로 리턴한다.
     *
     * @param sourceString 원본 문자열, token 델리미터
     * @return String[]
     */
	public static String[] getStringArrayBySplit( String sourceString, String token) {
        ArrayList<String> al = new ArrayList<String>();

        if(sourceString == null || sourceString.length() <= 0) {
        	return null;
        }

        int i = 0;
        int j = 0;
        while (j >= 0) {
            // a=0,b={1,c=2,d=3}
            i = j;
            j = sourceString.indexOf(token, j);
            if (j < 0) {
                if (i > sourceString.length()) {
                    i = sourceString.length();
                }
                al.add(sourceString.substring(i, sourceString.length()).trim());
                break;
            }
            al.add(sourceString.substring(i, j).trim());
            j += 1;
        }

        String[] ar = new String[al.size()];
        al.toArray(ar);

        return ar;
    }

    /**
     * NULL 문자열 체크.
     *
     * @param strOrg 원본 문자열
     * @return String
     */
    public static String nvl(String strOrg)
    {
        if(strOrg != null && strOrg.length() > 0)
        {
            return strOrg.trim();
        }
        else
        {
            return "";
        }
    }

    /**
     * NULL 문자열 체크.
     *
     * @param strOrg 원본문자열
     * @param strDef 기본값
     * @return String
     */
    public static String nvl(String strOrg, String strDef)
    {
        String strValue = nvl(strOrg);

        if(!"".equals(strValue))
        {
            return strValue;
        }
        else
        {
            return strDef;
        }
    }



    /**
     * NULL 문자열 체크 int형.
     *
     * @param strOrg 원본문자열
     * @param nDef 기본값
     * @return int
     */
    public static int nvl(String strOrg, int nDef)
    {
        String strValue = nvl(strOrg);

        if(!"".equals(strValue))
        {
            return Integer.parseInt(strValue);
        }
        else
        {
            return nDef;
        }
    }

    /**
     * NULL 문자열 체크 long형.
     *
     * @param strOrg 원본문자열
     * @param nDef 기본값
     * @return int
     */
    public static long nvl(String strOrg, long nDef)
    {
        String strValue = nvl(strOrg);

        if(!"".equals(strValue))
        {
            return Long.parseLong(strValue);
        }
        else
        {
            return nDef;
        }
    }


    /**
     * NULL 문자열 체크 float형.
     *
     * @param strOrg 원본문자열
     * @param nDef 기본값
     * @return float
     */
    public static float nvl(String strOrg, float nDef)
    {
        String strValue = nvl(strOrg);

        if(!"".equals(strValue))
        {
            return Float.parseFloat(strValue);
        }
        else
        {
            return nDef;
        }
    }

    /**
     * NULL 문자열 체크 double형.
     *
     * @param strOrg 원본문자열
     * @param nDef 기본값
     * @return double
     */
    public static double nvl(String strOrg, double nDef)
    {
        String strValue = nvl(strOrg);

        if(!"".equals(strValue))
        {
            return Double.parseDouble(strValue);
        }
        else
        {
            return nDef;
        }
    }

    //--------------------------------------------------------------------------
    // NULL 관련 UTIL
    //--------------------------------------------------------------------------
    /**
        null이거나 공백일때 true를 반환하다.
        Parameter Parsing이나 DB에서 데이터를 읽어올 때 유용하게 쓰일 것이다.

        @param str null인지 공백인지 확인하고 싶은 String 값
        @return null이거나 공백이면 true반환, 아니면 false 반환
    */
    public static boolean isNullOrSpace(String str) {

        if (str == null || str.trim().length() == 0) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean isNullOrBlank(String str) {

        return isNullOrSpace(str);
    }

    public static String null2Blank(String str) {

        if (str == null) {
            return "";
        }
        return str;
    }

    public static String null2String(Object obj, String str) {

        if (obj == null)
            return str;
        else if (obj instanceof String)
            return (String) obj;
        else
            return obj.toString();
    }

    public static String null2String(Object obj) {

        return null2String(obj, "");
    }

    public static String null2nbsp(String str) {

        if (isNullOrSpace(str)) {
            return "&nbsp;";
        }
        return str;
    }

    /**
    * 문자열이 null이면 "0" 반환
    * @param str
    * @return String NULL이면 "0"을 반환하고 NULL이 아니면 원 문자열 그대로 반환
    */
    public static String null2ZeroString(String str) {

        if (isNullOrSpace(str)) {
            return "0";
        }
        return str;
    }

    /**
     * 문자열의 처음부터 '0'을 제거
     * @param str
     * @return String 앞에서부터 '0'을 제거한 문자열 반환
     */
    public static String removeFrontZero(String str) {

        while (str.length() > 0 && str.charAt(0) == '0') {
            str = str.substring(1, str.length());
        }

        return str;
    }

    //--------------------------------------------------------------------------
    // STRING 관련 UTIL
    //--------------------------------------------------------------------------

    /**
        문자열이 길때 어떤 특정한 길이에서 짤라서 반환한다. 짜른 뒤에 "..."를 붙인다.
        단, 한글이나 영문이나 모두 1글자로 취급한다.

        @param s 짜르고 싶은 문장
        @param i 짜르고 싶은 길이
        @return 일정길이로 짜른 문자열에 "..."를 붙여 반환한다.
    */
    public static String cutString(String s, int i) {

        String s1 = s;
        if (s1.length() > i)
            s1 = s.substring(0, i) + "...";
        return s1;
    }

    /**
        문자열이 길때 어떤 특정한 길이에서 짤라서 반환한다. 짜른 뒤에 "..."를 붙인다.
        단, 한글은 2바이트, 영문은 1바이트로 취급하여 반올림해서 짜른다.

        @param s 짜르고 싶은 문장
        @param i 짜르고 싶은 길이
        @return 일정길이로 짜른 문자열에 "..."를 붙여 반환한다.
    */
    public static String cutString2Byte(String s, int i) {

        if (i < 4)
            return s;
        int j = 0;
        StringBuffer stringbuffer = new StringBuffer();
        for (int k = 0; k < s.length(); k++) {
            char c = s.charAt(k);
            if (c < '\uAC00' || '\uD7A3' < c)
                j++;
            else
                j += 2;
            stringbuffer.append(c);
            if (j <= i - 3)
                continue;
            stringbuffer.append("...");

            break;
        }
        return stringbuffer.toString();
    }

    public static String cutString2Byte(String s, int limitCnt, String charsetName) throws Exception {

        StringBuffer stringbuffer = new StringBuffer();
        int j = 0;

        for (int k = 0; k < s.length(); k++) {
            char c = s.charAt(k);

            if (c < '\uAC00' || '\uD7A3' < c)
                j++;
            else
                j += 3;

            if (j <= limitCnt - 3 && (stringbuffer.toString()).getBytes(charsetName).length < limitCnt) {
                stringbuffer.append(c);
                continue;
            } else {
                break;
            }
        }
        return stringbuffer.toString();
    }

    public static String tagTranslate(String s) {

        if (s == null || s.trim().equals(""))
            return "";
        StringBuffer stringbuffer = new StringBuffer(s.length());
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c == '<')
                stringbuffer.append("&lt;");
            else if (c == '>')
                stringbuffer.append("&gt;");
            else if (c == '"')
                stringbuffer.append("&quot;");
            else if (c == '&')
                stringbuffer.append("&amp;");
            else if (c == '\'')
                stringbuffer.append("&#039");
            else
                stringbuffer.append(c);
        }

        return stringbuffer.toString();
    }

    public static String enter2brTag(String s) {

        if (s == null || s.trim().equals(""))
            return "";
        StringTokenizer stringtokenizer = new StringTokenizer(s, "\n");
        StringBuffer stringbuffer = new StringBuffer();
        for (; stringtokenizer.hasMoreTokens(); stringbuffer.append(stringtokenizer.nextToken() + "<br>"))
            ;
        return stringbuffer.toString();
    }

    //--------------------------------------------------------------------------
    // NUMBER 관련 UTIL
    //--------------------------------------------------------------------------

    /**
     * request 받은 문자가 null 이면 target(int)값을 반환한다.
     * @param requestParameter
     * @param target
     * @return
     * @throws Exception
     */
    public static int convertNull2Int(String requestParameter, int target) throws Exception {

        if (requestParameter == null) {
            return target;
        } else {
            return parseInt(requestParameter);
        }
    }

    /**
        String형 숫자를 int형 숫자로 변환한다. 변환이 안되는 String은 0로 반환한다.

        @param s int로 바꿀 String
        @return int형으로 바뀐 String
    */
    public static int parseInt(String s) {

        try {
            if (s == null || s.equals(""))
                return 0;
            else
                return Integer.parseInt(s);
        } catch (Exception exception) {
            return 0;
        }
    }

    public static long parseLong(String s) {

        try {
            if (s == null || s.equals(""))
                return 0;
            else
                return Long.parseLong(s);
        } catch (Exception exception) {
            return 0;
        }
    }

    public static float parseFloat(String s) {

        try {
            if (s == null || s.equals(""))
                return 0;
            else
                return Float.parseFloat(s);
        } catch (Exception exception) {
            return 0;
        }
    }

    /**
        String형 숫자를 double형 숫자로 변환한다. 변환이 안되는 String은 0로 반환한다.

        @param s double로 바꿀 String
        @return double형으로 바뀐 String
    */
    public static double parseDouble(String s) {

        if (s.equals("")) {
            return 0.0D;
        } else {
            try {
                return Double.valueOf(s).doubleValue();
            } catch (Exception e) {
                return 0.0D;
            }
        }
    }

    /**
        통화형식처럼 숫자 3자리미다 ,(콤마)를 찍는다. double(int)형 인자를 받는다.

        @param money double(int)형 통화형식
        @return 3자리마다 ,(콤마)가 찍힌 형식
    */
    public static String moneyFormat(double d) {

        NumberFormat numberformat = NumberFormat.getNumberInstance();
        String s = numberformat.format(d);
        return s;
    }

    /**
        통화형식처럼 숫자 3자리미다 ,(콤마)를 찍는다. String형 인자를 받는다.

        @param money String형 통화형식
        @return 3자리마다 ,(콤마)가 찍힌 형식
    */
    public static String moneyFormat(String s) {

        NumberFormat numberformat = NumberFormat.getNumberInstance();
        String s1;
        try {
            Number number = numberformat.parse(s);
            s1 = numberformat.format(number);
        } catch (ParseException parseexception) {
            s1 = "0";
        }
        return s1;
    }

    /**
        대상문자열안의 논리적 값이 숫자를 표현하면 true, 아니면 false를 반환한다.

        @param str 대상문자열
        @return 대상문자열의 논리적 값이 숫자이면 true, 아니면 false
    */
    public static boolean isStringIsNumeric(String str) {

        try {
            NumberFormat nf = NumberFormat.getInstance();
            // Number number = nf.parse(str);
            nf.parse(str);
            return true;
        } catch (ParseException parseexception) {
            return false;
        }
    }

    /**
        대상문자열안의 논리적 숫자앞에 원하는 size의 자릿수에 맞게 '0'를 붙인다. 예) 000000000 (일억자리수)에 345를 넣었을 경우 '000000345'반환

        @param str 대상문자열
        @param size 원하는 자릿수
        @return 대상문자열에 자릿수 만큼의 '0'를 붙인 문자열
    */
    public static String zerofill(String str, int size) {

        try {
            NumberFormat nf = NumberFormat.getInstance();
            return zerofill(nf.parse(str), size);
        } catch (Exception e) {
            return "";
        }

    }

    /**
        int형 숫자앞에 원하는 size의 자릿수에 맞게 '0'를 붙인다. 예) 000000000 (일억자리수)에 345를 넣었을 경우 '000000345'반환

        @param str 대상 int형 숫자
        @param size 원하는 자릿수
        @return 대상문자열에 자릿수 만큼의 '0'를 붙인 문자열
    */
    public static String zerofill(int num, int size) {

        return zerofill(new Integer(num), size);
    }

    /**
        double형 숫자앞에 원하는 size의 자릿수에 맞게 '0'를 붙인다. 예) 000000000 (일억자리수)에 345를 넣었을 경우 '000000345'반환

        @param str 대상 double형 숫자
        @param size 원하는 자릿수
        @return 대상문자열에 자릿수 만큼의 '0'를 붙인 문자열
    */
    public static String zerofill(double num, int size) throws Exception {

        return zerofill(new Double(num), size);
    }

    /**
        Number형 숫자앞에 원하는 size의 자릿수에 맞게 '0'를 붙인다. 예) 000000000 (일억자리수)에 345를 넣었을 경우 '000000345'반환

        @param num 대상 Number형 숫자
        @param size 원하는 자릿수
        @return 대상문자열에 자릿수 만큼의 '0'를 붙인 문자열
    */
    public static String zerofill(Number num, int size) {

        String zero = "";
        for (int i = 0; i < size; i++) {
            zero += "0";
        }
        DecimalFormat df = new DecimalFormat(zero);
        return df.format(num);
    }

    /**
     * 입력 문자열을 입력 길이만큼 parttern문자를 왼쪽에 채워 리턴
     *
     * @param str 입력 문자열
     * @param parttern 채울 문자
     * @param length 총 길이
     * @return String
     *
     * <p><pre>
     *  - 사용 예
     *        String date = StringUtil.getLPad("123", "0", 5);
     *  결과 : 00123
     * </pre>
     *
     */
    public static String getLPad(String str, String parttern, int length) {

        String strTmp = "";
        int nStr = str.length();

        if ( parttern.length() != 1 ) { return ""; }
        if ( nStr == length ) { return str; }
        if ( nStr > length ) { return ""; }

        for ( int inx = 0 ; inx < (length - nStr) ; inx++ ) {
            strTmp += parttern;
        }

        return strTmp + str;
    }

    /**
     * 입력 문자열을 입력 길이만큼 parttern문자를 오른쪽에 채워 리턴
     *
     * @param str 입력 문자열
     * @param parttern 채울 문자
     * @param length 총 길이
     * @return String
     *
     * <p><pre>
     *  - 사용 예
     *        String date = StringUtil.getRPad("123", "0", 5);
     *  결과 : 12300
     * </pre>
     */
    public static String getRPad(String str, String parttern, int length) {

        String strTmp = "";
        int nStr = str.length();

        if ( parttern.length() != 1 ) { return ""; }
        if ( nStr == length ) { return str; }
        if ( nStr > length ) { return ""; }

        for ( int inx = 0 ; inx < (length - nStr) ; inx++ ) {
            strTmp += parttern;
        }

        return str + strTmp;
    }
    /**
     * 입력 문자열이 NULL인지 체크
     *
     * @param str NULL 체크 문자열
     * @return boolean
     *
     * <p><pre>
     *  - 사용 예
     *        boolean date = StringUtil.isNull(null);
     *  결과 : true
     * </pre>
     */
    public static boolean isNull(String str) {

        boolean nRet = false;

        if ( str == null ) { nRet = true; }

        return nRet;
    }

    /**
     * 입력 문자열이 NULL인지 체크하여 문자열 대체
     *
     * @param str NULL 체크 문자열
     * @param changeStr 대체 문자열
     * @return String
     *
     * <p><pre>
     *  - 사용 예
     *        String date = StringUtil.ifNull(null, "Empty");
     *  결과 : Empty
     * </pre>
     */
    public static String ifNull(String str, String changeStr) {

        String retStr = "";
        if ( isNull(str) && !isNull(changeStr) ) {
            retStr = changeStr;
        } else {
            retStr = null;
        }

        return retStr;
    }

    /**
     * 입력 문자열이 비어있는지 체크
     *
     * @param str 입력 문자열
     * @return boolean
     *
     * <p><pre>
     *  - 사용 예
     *        boolean date = StringUtil.isEmpty("");
     *  결과 : true
     * </pre>
     */
    public static boolean isEmpty(String str) {
        boolean nRet = false;
        if ( str.equals("") ) { nRet = true; }
        return nRet;
    }

    /**
     * 입력 문자열을 trim하여 비어있는지 체크
     *
     * @param str 입력 문자열
     * @return boolean
     *
     * <p><pre>
     *  - 사용 예
     *        boolean date = StringUtil.isEmptyTrim("    ");
     *  결과 : true
     * </pre>
     */
    public static boolean isEmptyTrim(String str) {
        return isEmpty(str.trim());
    }

    /**
     * 문자열을 왼쪽에서 부터 짤라온다
     *
     * @param str 문자열
     * @param index 인덱스
     * @return String
     *
     * <p><pre>
     *  - 사용 예
     *        boolean date = StringUtil.getLeft("ABCDEFGHI", 3);
     *  결과 : ABC
     * </pre>
     */
    public static String getLeft(String str, int index) {
        return str.substring(0, index);
    }

    /**
     * 문자열을 오른쪽에서 부터 짤라온다
     *
     * @param str 문자열
     * @param index 인덱스
     * @return String
     *
     * <p><pre>
     *  - 사용 예
     *        boolean date = StringUtil.getRight("ABCDEFGHI", 3);
     *  결과 : GHI
     * </pre>
     */
    public static String getRight(String str, int index) {
        return str.substring(str.length() - index, str.length());
    }

    /**
     * 문자열 가운데를 짤라온다
     *
     * @param str 문자열
     * @param startindex 시작인덱스
     * @param chrcount 문자개수
     * @return String
     *
     * <p><pre>
     *  - 사용 예
     *        boolean date = StringUtil.getMid("ABCDEFGHI", 4, 3);
     *  결과 : DEF
     * </pre>
     */
    public static String getMid(String str, int startindex, int chrcount) {
        return str.substring(startindex - 1, startindex - 1 + chrcount);
    }

    /**
     * 문자열의 길이가 length와 같은지 체크
     *
     * @param str 입력문자열
     * @param length 문자열길이
     * @return boolean
     *
     * <p><pre>
     *  - 사용 예
     *        boolean date = StringUtil.isLengh("ABCDEFGHI", 9);
     *  결과 : true
     * </pre>
     */
    public static boolean isLengh(String str, int length) {
        boolean nRet = false;
        if ( str.length() == length ) {
            nRet = true;
        }
        return nRet;
    }

    /**
    *
    * 카드번호 마스킹 처리
    *
    * @param 카드번호 아이디
    * @return 마스킹 처리 된 아이디
    * @throws
    * @version 1.0.0
    * @since 2014. 4. 21.
    */
    public static String maskingCardNo(String value, int sIdx, int eIdx) {
        String data = String.valueOf(value);
        if( sIdx < 0 ) {
            throw new ArrayIndexOutOfBoundsException("sIdx : " + sIdx);
        }

        if( (eIdx+1) > data.length() ) {
            throw new ArrayIndexOutOfBoundsException("eIdx : " + eIdx + ", data size : " + data.length());
        }

        if( sIdx > eIdx ) {
            throw new ArrayIndexOutOfBoundsException("sIdx : " + sIdx + ", eIdx : " + eIdx);
        }

        StringBuffer buff = new StringBuffer();
        buff.append(data.substring(0, sIdx));
        for( int i = sIdx; i <= eIdx; i++ ) {
            buff.append("*");
        }
        buff.append(data.substring(eIdx+1));

        return buff.toString();
    }

    /**
     *
     * 아이디 마스킹 처리
     *
     * @param id 아이디
     * @return 마스킹 처리 된 아이디
     * @throws
     * @version 1.0.0
     * @since 2013. 6. 18.
     */
    public static String maskingId(String id) {
        String maskId = "";
        if (id != null && !id.equals("") && id.length() > 2) {
        	if(id.indexOf("@") > -1){
        		String [] emailId = id.split("@");
        		if(emailId.length >= 2){
        			maskId += emailId[0].substring(0, emailId[0].length() - 2) + "**"+"@"+emailId[1];
        		}
        	}else{
        		maskId += id.substring(0, id.length() - 2) + "**";
        	}
        }
        return maskId;
    }

    /**
     *
     * 이름 첫글자와 마지막글자를 제외한 부분들 마스킹 처리
     *
     * @param name 이름
     * @return
     * @throws
     * @author <a href="mailto:nkw80@skmns.co.kr">nkw80</a>
     * @version 1.0.0
     * @since 2013. 6. 18.
     */
    public static String maskingName(String name) {
        String maskName = "";
        if (name != null && name.length() > 1) {
            if (name.length() == 2) {
                maskName = name.substring(0, 1) + "*";
            } else {
                maskName = name.substring(0, 1);

                for (int i = 1; i < name.length() - 1; i++) {
                    maskName += "*";
                }

                maskName += name.substring(name.length() - 1);

            }
        }
        return maskName;
    }

    /**
     *
     * 모든 문자를 mask 처리
     *
     * @param str
     * @return
     * @throws
     * @author <a href="mailto:nkw80@skmns.co.kr">nkw80</a>
     * @version 1.0.0
     * @since 2013. 6. 18.
     */
    public static String chgMaskAll(String str) {
        String maskStr = "";
        if (str != null) {
            for (int i = 0; i < str.length(); i++) {
                maskStr += "*";
            }
        }
        return maskStr;
    }

    /**
     * 휴대폰번호 - 및  뒷 4자리 마스킹
     * @Method 설명:
     * @author ms01525 : kilmmk (kilmmk@skmns.co.kr)
     * @param mdnNo
     * @return
     */
    public static String getConvertPhoneNumber(String mdnNo){
		String tempMdn = "";
		if(mdnNo.length() == 10){
			tempMdn = mdnNo.substring(0, 3)+"-"+"***"+"-"+mdnNo.substring(6, 10);
		}else if(mdnNo.length() == 11){
			tempMdn = mdnNo.substring(0, 3)+"-"+"****"+"-"+mdnNo.substring(7, 11);
		}
		return tempMdn;
	}

    /**
     * HTML 읽어오기
     * @return String
     */
    public static String getHttpUrlContents(String URL) throws Exception {

        String contents = null;

        String line = null;
        StringBuffer mailContent = new StringBuffer();
        //Html 을 받아올 데이터
        URL url = new URL(URL);
        HttpURLConnection httpCon = (HttpURLConnection) url.openConnection();
        httpCon.connect();
        //HTTP 응답코드가 성공인 경우

        try {
            if (httpCon.getResponseCode() == 200) {
                // URL커넥션 데이터 저장
                BufferedReader reader = new BufferedReader(new InputStreamReader(httpCon.getInputStream(), "euc-kr"));
                while ((line = reader.readLine()) != null) {
                    mailContent.append(line + "\n");
                }
                //HTML 출력
                contents = mailContent.toString();
            }
        } catch (Exception e) {
            System.err.println("[StringHandler.getHttpUrlContents ERROR] " + e.getMessage());
        }

        return contents;
    }

    /**
     * HTML 읽어오기
     * @return String
     */
    public static String getHttpUrlContents(String URL, String charset) throws Exception {

        String contents = null;

        String line = null;
        StringBuffer mailContent = new StringBuffer();
        //Html 을 받아올 데이터
        URL url = new URL(URL);
        HttpURLConnection httpCon = (HttpURLConnection) url.openConnection();
        httpCon.connect();
        //HTTP 응답코드가 성공인 경우

        try {
            if (httpCon.getResponseCode() == 200) {
                // URL커넥션 데이터 저장
                BufferedReader reader = new BufferedReader(new InputStreamReader(httpCon.getInputStream(), charset));
                while ((line = reader.readLine()) != null) {
                    mailContent.append(line + "\n");
                }
                //HTML 출력
                contents = mailContent.toString();
            }
        } catch (Exception e) {
            System.err.println("[StringHandler.getHttpUrlContents ERROR] " + e.getMessage());
        }

        return contents;
    }

    /**
     * 입력된 String 중 Number만 뽑아서 리턴 한다.
     * @param str
     * @return
     */
    public static String getStringToNumber(String str) {
        List<Byte> lst = new ArrayList<Byte>();
        if ( str == null || str.trim().equals(""))  return "";

        byte[] bt = str.getBytes();

        for ( int i = 0 ; i < bt.length ; i++ ) {
            if ( bt[i] >= 48 && bt[i] <= 57 ) {
                lst.add(bt[i]);
            }
        }

        bt = new byte[lst.size()];

        for ( int i = 0 ; i < lst.size() ; i++ ) {
            bt[i] = lst.get(i);
        }

        return new String(bt);
    }

    //문자열의 값이 숫자가 아니면 기본값으로 리턴한다.
    public static String getDefValueNotNumber(String data, String def) {
    	if(NumberUtils.isNumber(data)) {//숫자구나
    		return data;
    	}
    	return def;
    }


}
