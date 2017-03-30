/*******************************************************************************
 * Copyright(c) 2015 KT. All rights reserved.
 * This software is the proprietary information of KT.
 *******************************************************************************/
package com.kt.giga.home.openapi.ghms.util.date;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;
import java.util.SimpleTimeZone;

import com.kt.giga.home.openapi.ghms.util.string.StringUtils;

/**
 * 
 * @author junypooh ( kyungjun.park@ceinside.co.kr )
 * @since 2015. 2. 16.
 */
public class DateTimeUtil {

    /** 요일 상수 */
    private static final String[] DAY = {"일", "월", "화", "수", "목", "금", "토" };
    private static final String[] EDAY = {"SUN", "MON", "TUE", "WED", "THU", "FRI", "SAT" };

    /**
     * 입력받은 parttern의 형태로 DateTime 리턴
     *
     * @param pattern yyyyMMddHHmmssSSS형식의 패턴
     * @return String
     *
     * <p><pre>
     *  - 사용 예
     *        String date = DateTime.getDateByPattern("yyyyMMddHHmmssSSS");
     *  결과 : 20080719153048357
     * </pre>
     */
    public static String getDateByPattern(String pattern) {
        java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat (pattern, java.util.Locale.KOREA);
        String dateString = formatter.format(new java.util.Date());
        return dateString;
    }

    /**
     * 현재일자의 요일을 리턴
     *
     * @return String
     *
     * <p><pre>
     *  - 사용 예
     *        String date = DateTime.getDayOfWeek();
     *  결과 : 토
     * </pre>
     */
    public static String getDayOfWeek() {
        Calendar c = Calendar.getInstance();
        return DAY[c.get(java.util.Calendar.DAY_OF_WEEK)-1];
    }

    /**
     * 현재일자의 영어요일을 리턴
     *
     * @return String
     *
     * <p><pre>
     *  - 사용 예
     *        String date = DateTime.getEnglishDayOfWeek();
     *  결과 : SUN
     * </pre>
     */
    public static String getEnglishDayOfWeek() {
        Calendar c = Calendar.getInstance();
        return EDAY[c.get(java.util.Calendar.DAY_OF_WEEK)-1];
    }

    /**
     * 입력받은 일자의 요일을 리턴
     *
     * @param date yyyyMMdd형식의 날짜
     * @return String
     *
     * <p><pre>
     *  - 사용 예
     *        String date = DateTime.getDayOfWeek("20080719");
     *  결과 : 토
     * </pre>
     */
    public static String getDayOfWeek(String date) {

        String sYear = StringUtils.getLeft(date, 4);
        String sMonth = StringUtils.getMid(date, 5, 2);
        String sDay = StringUtils.getRight(date, 2);

        Calendar cal = Calendar.getInstance();

        cal.set(Integer.parseInt(sYear), Integer.parseInt(sMonth) - 1, Integer.parseInt(sDay) );

        return DAY[cal.get(java.util.Calendar.DAY_OF_WEEK)-1];
    }

    /**
     * 현재 년을 리런
     *
     * @return String
     *
     * <p><pre>
     *  - 사용 예
     *        String date = DateTime.getYear();
     *  결과 : 2008
     * </pre>

     */
    public static String getYear() {
        return getDateByPattern("yyyy");
    }

    /**
     * 현재 월을 리턴
     *
     * @return String
     *
     * <p><pre>
     *  - 사용 예
     *        String date = DateTime.getMonth();
     *  결과 : 07
     * </pre>
     */
    public static String getMonth() {
        return getDateByPattern("MM");
    }

    /**
     * 현재 일을 리턴
     *
     * @return String
     *
     * <p><pre>
     *  - 사용 예
     *        String date = DateTime.getDate();
     *  결과 : 19
     * </pre>
     */
    public static String getDate() {
        return getDateByPattern("dd");
    }

    /**
     * 현재 시를 리턴
     *
     * @return String
     *
     * <p><pre>
     *  - 사용 예
     *        String date = DateTime.getHour();
     *  결과 : 15
     * </pre>
     */
    public static String getHour() {
        return getDateByPattern("HH");
    }

    /**
     * 현재 분을 리턴
     *
     * @return String
     *
     * <p><pre>
     *  - 사용 예
     *        String date = DateTime.getMinute();
     *  결과 : 30
     * </pre>
     */
    public static String getMinute() {
        return getDateByPattern("mm");
    }

    /**
     * 현재 초를 리턴
     *
     * @return String
     *
     * <p><pre>
     *  - 사용 예
     *        String date = DateTime.getSecond();
     *  결과 : 48
     * </pre>
     */
    public static String getSecond() {
        return getDateByPattern("ss");
    }

    /**
     * 현재 밀리초를 리턴
     *
     * @return String
     *
     * <p><pre>
     *  - 사용 예
     *        String date = DateTime.getMilliSecond();
     *  결과 : 357
     * </pre>
     */
    public static String getMilliSecond() {
        return getDateByPattern("SSS");
    }

    /**
     * 현재 날짜를 리턴
     *
     * @return String
     *
     * <p><pre>
     *  - 사용 예
     *        String date = DateTime.getDateString();
     *  결과 : 20080719
     * </pre>
     */
    public static String getDateString() {
        return getDateString("");
    }

    /**
     * 현재 날짜를 delimiter로 구분하여 리턴
     *
     * @param delimiter yyyyMMdd를 구분 할 문자
     * @return String
     *
     * <p><pre>
     *  - 사용 예
     *        String date = DateTime.getDateString("-");
     *  결과 : 2008-07-19
     * </pre>
     */
    public static String getDateString(String delimiter) {
        return getDateByPattern("yyyy" + delimiter + "MM" + delimiter + "dd");
    }

    /**
     * 현재 시각을 리턴
     *
     * @return String
     *
     * <p><pre>
     *  - 사용 예
     *        String date = DateTime.getTimeString();
     *  결과 : 153048
     * </pre>
     */
    public static String getTimeString() {
        return getTimeString("");
    }

    /**
     * 현재 시각을 delimiter로 구분하여 리턴
     *
     * @param delimiter HHmmss를 구분 할 문자
     * @return String
     *
     * <p><pre>
     *  - 사용 예
     *        String date = DateTime.getTimeString("-");
     *  결과 : 15:30:48
     * </pre>
     */
    public static String getTimeString(String delimiter) {
        return getDateByPattern("HH" + delimiter + "mm" + delimiter + "ss");
    }

    /**
     * 현재 시각을 리턴
     *
     * @return String
     *
     * <p><pre>
     *  - 사용 예
     *        String date = DateTime.getTimeStampString();
     *  결과 : 153048357
     * </pre>
     */
    public static String getTimeStampString() {
        return getTimeStampString("", "");
    }

    /**
     * 현재 시각을 timedelimiter와 milliseconddelimiter로 구분하여 리턴
     *
     * @param timedelimiter HHmmss를 구분 할 문자
     * @param milliseconddelimiter ssSSS를 구분 할 문자
     * @return String
     *
     * <p><pre>
     *  - 사용 예
     *        String date = DateTime.getTimeStampString(":", ".");
     *  결과 : 15:30:48.357
     * </pre>
     */
    public static String getTimeStampString(String timedelimiter, String milliseconddelimiter) {
        return getDateByPattern("HH" + timedelimiter + "mm" + timedelimiter + "ss" + milliseconddelimiter + "SSS");
    }

    /**
     * 입력 날짜를 delimiter로 구분하여 리턴
     *
     * @param date yyyyMMdd형식의 날짜
     * @param delimiter yyyyMMdd를 구분 할 문자
     * @return String
     *
     * <p><pre>
     *  - 사용 예
     *        String date = DateTime.getParseDateString("20080719", "-");
     *  결과 : 2008-07-19
     * </pre>
     */
    public static String getParseDateString(String date, String delimiter) {
        if ( !StringUtils.isLengh(date, 8) ) { return ""; }

        return StringUtils.getLeft(date, 4) + delimiter + StringUtils.getMid(date, 5, 2) + delimiter + StringUtils.getRight(date, 2);
    }

    /**
     * 입력 시각을 delimiter로 구분하여 리턴
     *
     * @param time HHmmss형식의 시각
     * @param delimiter HHmmss를 구분 할 문자
     * @return String
     *
     * <p><pre>
     *  - 사용 예
     *        String date = DateTime.getParseTimeString("153048", ":");
     *  결과 : 15:30:48
     * </pre>
     */
    public static String getParseTimeString(String time, String delimiter) {
        if ( !StringUtils.isLengh(time, 6) ) { return ""; }
        return StringUtils.getLeft(time, 2) + delimiter + StringUtils.getMid(time, 3, 2) + delimiter + StringUtils.getRight(time, 2);
    }

    /**
     * 입력 시각을 timedelimiter와 milliseconddelimiter로 구분하여 리턴
     *
     * @param time HHmmssSSS형식의 시각
     * @param timedelimiter HHmmss를 구분 할 문자
     * @param milliseconddelimiter ssSSS를 구분 할 문자
     * @return String
     *
     * <p><pre>
     *  - 사용 예
     *        String date = DateTime.getParseTimeStampString("153048357", ":", ".");
     *  결과 : 15:30:48.357
     * </pre>
     */
    public static String getParseTimeStampString(String time, String timedelimiter, String milliseconddelimiter) {
        if ( !StringUtils.isLengh(time, 9) ) { return ""; }
        return StringUtils.getLeft(time, 2) + timedelimiter + StringUtils.getMid(time, 3, 2) + timedelimiter + StringUtils.getMid(time, 5, 2) + milliseconddelimiter + StringUtils.getRight(time, 3);
    }

    /**
     * 입력 년의 윤년 여부를 리턴
     *
     * @param year yyyy형식의 년
     * @return boolean
     *
     * <p><pre>
     *  - 사용 예
     *        boolean date = DateTime.isLeapYear("2008");
     *  결과 : true
     * </pre>
     */
    public static boolean isLeapYear(String year) {
        int nRemain = 0;
        int nRemain_1 = 0;
        int nRemain_2 = 0;
        int nYear = Integer.parseInt(year);

        nRemain = nYear % 4;
        nRemain_1 = nYear % 100;
        nRemain_2 = nYear % 400;

        // the ramain is 0 when year is divided by 4;
        if (nRemain == 0) {
            // the ramain is 0 when year is divided by 100;
            if (nRemain_1 == 0) {
                // the remain is 0 when year is divided by 400;
                if (nRemain_2 == 0) return true;
                else return false;
            } else {
                return true;
            }
        }
        return false;
    }

    /**
     * 입력 날짜의 유효성 여부를 리턴
     *
     * @param date yyyyMMdd형식의 날짜
     * @return boolean
     *
     * <p><pre>
     *  - 사용 예
     *        boolean date = DateTime.isLeapYear("20080719");
     *  결과 : true
     * </pre>
     */
    public static boolean isDate(String date) {

        boolean nRet = true;

        int nYear = Integer.parseInt(StringUtils.getLeft(date, 4));
        int nMonth = Integer.parseInt(StringUtils.getMid(date, 5, 2));
        int nDay = Integer.parseInt(StringUtils.getRight(date, 2));

        int [] nDateMonth = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};

        if (isLeapYear("" + nYear)) { nDateMonth[1] = 29; }
        if ( nDay > nDateMonth[nMonth-1] || nDay < 1) nRet = false;
        if ( nMonth < 1 || nMonth > 12) nRet = false;
        if ( nMonth % 1 != 0 || nYear % 1 != 0 || nDay % 1 != 0) nRet = false;

        return nRet;
    }

    /**
     * 입력 받은 년, 월의 마지막 일수를 리턴
     *
     * @param year yyyy형식의 년
     * @param month MM형식의 월
     * @return int
     *
     * <p><pre>
     *  - 사용 예
     *        int date = DateTime.getLastMonthDate("2008", "07");
     *  결과 : 31
     * </pre>
     */
    public static int getLastMonthDate(String year, String month) {

        int [] nDateMonth = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};

        if (isLeapYear(year)) { nDateMonth[1] = 29; }

        int day = nDateMonth[Integer.parseInt(month) - 1];

        return day;
    }

    /**
     * 초를 시분초로 리턴
     *
     * @param second 초
     * @return String
     *
     * <p><pre>
     *  - 사용 예
     *        String date = DateTime.getSecondToTimeString(30);
     *  결과 : 000030
     * </pre>
     */
    public static String getSecondToTimeString(int second) {
        return getSecondToTimeString(second, "");
    }

    /**
     * 초를 시분초로 리턴
     *
     * @param second 초
     * @param delimiter HHmmss를 구분 할 문자
     * @return String
     *
     * <p><pre>
     *  - 사용 예
     *        String date = DateTime.getSecondToTimeString(30);
     *  결과 : 00:00:30
     * </pre>
     */
    public static String getSecondToTimeString(int second, String delimiter) {

        String sHh = "";
        String sMm = "";
        String sSs = "";

        sHh = "" + (second / 3600);
        sHh = StringUtils.getLPad(sHh, "0", 2);

        sMm = "" + (second - (Integer.parseInt(sHh) * 3600)) / 60;
        sMm = StringUtils.getLPad(sMm, "0", 2);

        sSs = "" + (second - (Integer.parseInt(sHh) * 3600) - (Integer.parseInt(sMm) * 60));
        sSs = StringUtils.getLPad(sSs, "0", 2);

        return sHh + delimiter + sMm + delimiter + sSs;
    }

    /**
     * 밀리초를 시분초밀리초로 리턴
     *
     * @param millisecond 밀리초
     * @return String
     *
     * <p><pre>
     *  - 사용 예
     *        String date = DateTime.getMilliSecondToTimeStampString(30);
     *  결과 : 000000030
     * </pre>
     */
    public static String getMilliSecondToTimeStampString(int millisecond) {
        return getMilliSecondToTimeStampString(millisecond, "", "");
    }

    /**
     * 밀리초를 시분초밀리초로 리턴
     *
     * @param millisecond 밀리초
     * @param timedelimiter HHmmss를 구분 할 문자
     * @param milliseconddelimiter ssSSS를 구분 할 문자
     * @return String
     *
     * <p><pre>
     *  - 사용 예
     *        String date = DateTime.getMilliSecondToTimeStampString(30, ":", ".");
     *  결과 : 00:00:00.030
     * </pre>
     */
    public static String getMilliSecondToTimeStampString(int millisecond, String timedelimiter, String milliseconddelimiter) {
        String tmpStr = "" + millisecond;
        String retStr = "";

        if ( tmpStr.length() > 3 ) {
            retStr = getSecondToTimeString(Integer.parseInt(StringUtils.getLeft(tmpStr, tmpStr.length() - 3)), timedelimiter);
            retStr += milliseconddelimiter + StringUtils.getMid(tmpStr, tmpStr.length() - 2, tmpStr.length() - (tmpStr.length() - 3));
        } else {


            if ( StringUtils.isLengh(tmpStr, 1) ) {
                tmpStr = StringUtils.getLPad(tmpStr, "0", 3);
            } else if ( StringUtils.isLengh(tmpStr, 2) ) {
                tmpStr = StringUtils.getLPad(tmpStr, "0", 2);
            }

            retStr = "00" + timedelimiter + "00" + timedelimiter + "00" + milliseconddelimiter + tmpStr;
        }

        return retStr;
    }

    /**
     * 입력 시각을 초로 리턴
     *
     * @param time HHmmss형식의 시각
     * @return int
     *
     * <p><pre>
     *  - 사용 예
     *        String date = DateTime.getTimeStringToSecond("011230");
     *  결과 : 4350
     * </pre>
     */
    public static int getTimeStringToSecond(String time) {
        if ( !StringUtils.isLengh(time, 6) ) { return 0; }

        int nRet = 0;

        int nHour = Integer.parseInt(StringUtils.getLeft(time, 2));
        int nMinute = Integer.parseInt(StringUtils.getMid(time, 3, 2));
        int nSecond = Integer.parseInt(StringUtils.getRight(time, 2));

        nRet += nHour * 3600;
        nRet += nMinute * 60;
        nRet += nSecond;

        return nRet;
    }

    /**
     * 입력 시각을 밀리초로 리턴
     *
     * @param time HHmmssSSS형식의 시각
     * @return int
     *
     * <p><pre>
     *  - 사용 예
     *        String date = DateTime.getTimeStampStringToMilliSecond("011230357");
     *  결과 : 4350357
     * </pre>
     */
    public static int getTimeStampStringToMilliSecond(String timestamp) {
        if ( !StringUtils.isLengh(timestamp, 9) ) { return 0; }
        return (getTimeStringToSecond(StringUtils.getLeft(timestamp, 6)) * 1000) + Integer.parseInt(StringUtils.getRight(timestamp,3));
    }

    /**
     * 입력 날짜에 년을 더한다
     *
     * @param date yyyyMMdd형식의 날짜
     * @param amount 년
     * @return String
     *
     * <p><pre>
     *  - 사용 예
     *        String date = DateTime.getAddYear("20080719", 5);
     *  결과 : 20130719
     * </pre>
     */
    public static String getAddYear(String date, int amount) {

        if ( date.length() != 8 ) { return ""; }

        String sYear = StringUtils.getLeft(date, 4);
        String sMonth = StringUtils.getMid(date, 5, 2);
        String sDay = StringUtils.getRight(date, 2);

        Calendar cal = Calendar.getInstance();

        cal.set(Integer.parseInt(sYear), Integer.parseInt(sMonth) - 1, Integer.parseInt(sDay) );
        cal.add(Calendar.YEAR, amount);

        sYear = "" + (Integer.parseInt("" + cal.get(Calendar.YEAR)));
        sMonth = "" + (Integer.parseInt("" + cal.get(Calendar.MONTH)) + 1 );
        sDay = "" + (Integer.parseInt("" + cal.get(Calendar.DATE)));

        sMonth = StringUtils.getLPad(sMonth, "0", 2);
        sDay = StringUtils.getLPad(sDay, "0", 2);

        return sYear + sMonth + sDay;
    }

    /**
     * 입력 날짜에 월을 더한다
     *
     * @param date yyyyMMdd형식의 날짜
     * @param amount 월
     * @return String
     *
     * <p><pre>
     *  - 사용 예
     *        String date = DateTime.getAddMonth("20080719", 5);
     *  결과 : 20081219
     * </pre>
     */
    public static String getAddMonth(String date, int amount) {

        if ( !StringUtils.isLengh(date, 8) ) { return ""; }

        String sYear = StringUtils.getLeft(date, 4);
        String sMonth = StringUtils.getMid(date, 5, 2);
        String sDay = StringUtils.getRight(date, 2);

        Calendar cal = Calendar.getInstance();

        cal.set(Integer.parseInt(sYear), Integer.parseInt(sMonth) - 1, Integer.parseInt(sDay) );
        cal.add(Calendar.MONTH, amount);

        sYear = "" + (Integer.parseInt("" + cal.get(Calendar.YEAR)));
        sMonth = "" + (Integer.parseInt("" + cal.get(Calendar.MONTH)) + 1);
        sDay = "" + (Integer.parseInt("" + cal.get(Calendar.DATE)));

        sMonth = StringUtils.getLPad(sMonth, "0", 2);
        sDay = StringUtils.getLPad(sDay, "0", 2);

        return sYear + sMonth + sDay;
    }

    /**
     * 입력 날짜에 일을 더한다
     *
     * @param date yyyyMMdd형식의 날짜
     * @param amount 일
     * @return String
     *
     * <p><pre>
     *  - 사용 예
     *        String date = DateTime.getAddDate("20080719", 5);
     *  결과 : 20080724
     * </pre>
     */
    public static String getAddDate(String date, int amount) {

        if ( !StringUtils.isLengh(date, 8) ) { return ""; }

        String sYear = StringUtils.getLeft(date, 4);
        String sMonth = StringUtils.getMid(date, 5, 2);
        String sDay = StringUtils.getRight(date, 2);

        Calendar cal = Calendar.getInstance();

        cal.set(Integer.parseInt(sYear), Integer.parseInt(sMonth) - 1, Integer.parseInt(sDay) );
        cal.add(Calendar.DATE, amount);

        sYear = "" + (Integer.parseInt("" + cal.get(Calendar.YEAR)));
        sMonth = "" + (Integer.parseInt("" + cal.get(Calendar.MONTH)) + 1);
        sDay = "" + (Integer.parseInt("" + cal.get(Calendar.DATE)));

        sMonth = StringUtils.getLPad(sMonth, "0", 2);
        sDay = StringUtils.getLPad(sDay, "0", 2);

        return sYear + sMonth + sDay;
    }

    /**
     * 입력 시각에 시간을 더한다.
     *
     * @param time HHmmss형식의 시각
     * @param amount 시간
     * @return String
     *
     * <p><pre>
     *  - 사용 예
     *        String date = DateTime.getAddHour("153048", 5);
     *  결과 : 203048
     * </pre>
     */
    public static String getAddHour(String time, int amount) {

        if ( !StringUtils.isLengh(time, 6) ) { return ""; }

        int nRet = getTimeStringToSecond(time);

        nRet += amount * 3600;

        return getSecondToTimeString(nRet);
    }

    /**
     * 입력 시각에 분을 더한다.
     *
     * @param time HHmmss형식의 시각
     * @param amount 분
     * @return String
     *
     * <p><pre>
     *  - 사용 예
     *        String date = DateTime.getAddHour("153048", 5);
     *  결과 : 153548
     * </pre>
     */
    public static String getAddMinute(String time, int amount) {

        if ( !StringUtils.isLengh(time, 6) ) { return ""; }

        int nRet = getTimeStringToSecond(time);

        nRet += amount * 60;

        return getSecondToTimeString(nRet);
    }

    /**
     * 입력 시각에 초를 더한다.
     *
     * @param time HHmmss형식의 시각
     * @param amount 초
     * @return String
     *
     * <p><pre>
     *  - 사용 예
     *        String date = DateTime.getAddHour("153048", 5);
     *  결과 : 153053
     * </pre>
     */
    public static String getAddSecond(String time, int amount) {

        if ( !StringUtils.isLengh(time, 6) ) { return ""; }

        int nRet = getTimeStringToSecond(time);

        nRet += amount;

        return getSecondToTimeString(nRet);
    }

    /**
     * 두 날짜의 사이 일수를 구한다.
     *
     * @param date_1 yyyyMMdd형식의 날짜
     * @param date_2 yyyyMMdd형식의 날짜
     * @return int
     *
     * <p><pre>
     *  - 사용 예
     *        String date = DateTime.getDateDiff("20080724", "20080719");
     *  결과 : 5
     * </pre>
     */
    public static int getDateDiff(String date_1, String date_2) {

        int nYear, nMonth, nDay;

        nYear = Integer.parseInt(StringUtils.getLeft(date_1, 4));
        nMonth = Integer.parseInt(StringUtils.getMid(date_1, 5, 2));
        nDay = Integer.parseInt(StringUtils.getRight(date_1, 2));

        Calendar sCal = Calendar.getInstance();
        sCal.set(nYear, nMonth - 1, nDay );

        nYear = Integer.parseInt(StringUtils.getLeft(date_2, 4));
        nMonth = Integer.parseInt(StringUtils.getMid(date_2, 5, 2));
        nDay = Integer.parseInt(StringUtils.getRight(date_2, 2));

        Calendar eCal = Calendar.getInstance();
        eCal.set(nYear, nMonth - 1, nDay );

        long day = eCal.getTime().getTime() - sCal.getTime().getTime();

        return (int)(day/(1000*60*60*24));
    }

    /**
     * 두 시각의 사이 초를 구한다.
     *
     * @param starttime HHmmss형식의 시각
     * @param endtime HHmmss형식의 시각
     * @return int
     *
     * <p><pre>
     *  - 사용 예
     *        String date = DateTime.getTimeDiff("153053", "153048");
     *  결과 : 5
     * </pre>
     */
    public static int getTimeDiff(String starttime, String endtime) {
        int nRet = getTimeStringToSecond(endtime) - getTimeStringToSecond(starttime);
        return nRet;
    }

    /**
     * 두 yyyyMMddHHmmss의 사이 초를 구한다.
     *
     * @param starttime yyyyMMddHHmmss형식의 시각
     * @param endtime yyyyMMddHHmmss형식의 시각
     * @return int
     *
     * <p><pre>
     *  - 사용 예
     *        String date = DateTime.getDateTimeDiff("20080724153053", "20080719153048");
     *  결과 : 345605
     * </pre>
     */
    public static int getDateTimeDiff(String startdatetime, String enddatetime) {

        int nDay = getDateDiff(StringUtils.getLeft(startdatetime, 8), StringUtils.getLeft(enddatetime, 8));
        int nTime = getTimeDiff(StringUtils.getRight(startdatetime, 6), StringUtils.getRight(enddatetime, 6));

        nDay = nDay * (60 * 60 * 24);


        return nDay + nTime;
    }

    /**
     * 두 시각의 사이 밀리초를 구한다.
     *
     * @param starttime HHmmssSSS형식의 시각
     * @param endtime HHmmssSSS형식의 시각
     * @return int
     *
     * <p><pre>
     *  - 사용 예
     *        String date = DateTime.getTimeDiff("011230357", "011230352");
     *  결과 : 5
     * </pre>
     */
    public static int getTimeStampDiff(String starttimestamp, String endtimestamp) {
        int nRet = getTimeStampStringToMilliSecond(starttimestamp) - getTimeStampStringToMilliSecond(endtimestamp);
        return nRet;
    }

    /**
     * 두 yyyyMMddHHmmssSSS의 사이 초를 구한다.
     *
     * @param starttime yyyyMMddHHmmssSSS형식의 시각
     * @param endtime yyyyMMddHHmmssSSS형식의 시각
     * @return int
     *
     * <p><pre>
     *  - 사용 예
     *        String date = DateTime.getDateTimeStampDiff("20080724011230357", "20080719011230352");
     *  결과 : 431999995
     * </pre>
     */
    public static int getDateTimeStampDiff(String startdatetime, String enddatetime) {

        int nDay = getDateDiff(StringUtils.getLeft(startdatetime, 8), StringUtils.getLeft(enddatetime, 8));
        int nTime = getTimeStampDiff(StringUtils.getRight(startdatetime, 9), StringUtils.getRight(enddatetime, 9));

        nDay = nDay * (1000 * 60 * 60 * 24);


        return nDay + nTime;
    }

    /**
     * 연산된 날짜를  포맷 유형의 문자열로 반환한다.
     *
     * @param format    Return 날짜 유형의 포맷
     * @param addNum    가감 연산 수량
     * @param addType   유형
     *  <ul>
     *      <li>yyyy : 년도</li>
     *      <li>mm   : 월</li>
     *      <li>dd   : 일</li>
     *      <li>hh   : 시간</li>
     *      <li>mi   : 분</li>
     *      <li>ss   : 초</li>
     *  </ul>
     * @return
     */
    public static String getAddDateTime(String format, int addNum , String addType) {
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat(format);
        java.util.Calendar cal = java.util.Calendar.getInstance();

        if(addType.equals("yyyy")){
            cal.add(Calendar.YEAR, addNum);
        }else if(addType.equals("mm")){
            cal.add(Calendar.MONTH, addNum);
        }else if(addType.equals("dd")){
            cal.add(Calendar.DATE, addNum);
        }else if(addType.equals("hh")){
            cal.add(Calendar.HOUR, addNum);
        }else if(addType.equals("mi")){
            cal.add(Calendar.MINUTE, addNum);
        }else if(addType.equals("ss")){
            cal.add(Calendar.SECOND, addNum);
        }

        return sdf.format(cal.getTime());
    }

    /**
     * 기준 날짜와 대상 날짜를 시간 차이를 비교하여  결과를 반환한다.
     * <ul>
     *  <li>비교날짜가  기준 날짜보다 이후   : true </li>
     *  <li>비교날짜가  기준 날짜보다 이전   : false </li>
     * </ul>
     * @param srcDate   기준 날짜
     * @param srcFmt    기준 날짜 포맷
     * @param distDate  대상 날짜
     * @param distFmt   대상 날짜 포맷
     * @return
     */
    public static boolean getDateDiff(String srcDate, String srcFmt, String distDate, String distFmt){
        boolean result = false;

        if(srcDate == null || srcDate.equals("") || distDate == null || distDate.equals(""))
            return result;

        if(srcFmt == null || srcFmt.equals(""))
            srcFmt = "yyyyMMddHHmmss";

        if(distFmt == null || distFmt.equals(""))
            distFmt = "yyyyMMddHHmmss";

        java.text.SimpleDateFormat srcSdf   = new java.text.SimpleDateFormat(srcFmt);
        java.text.SimpleDateFormat distSdf  = new java.text.SimpleDateFormat(distFmt);

        try{
            Date srcDt  = srcSdf.parse(srcDate);
            Date distDt = distSdf.parse(distDate);

            if(srcDt.before(distDt))
                result = true;

        }catch(Exception e){
            throw new RuntimeException("Date format not valid");
        }

        return result;
    }

    /** jjunon 2014.03.06
     * 현재 날짜 기준 몇일(ago) 전 날짜를 yyyyMMdd 패턴 문자열로 포맷한다.
     *
     * @return the string
     */
    public static String getBeforeDay(int ago) {
    	Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -1 * ago);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        sdf.setTimeZone(new SimpleTimeZone(9 * 60 * 60 * 1000, "KST"));
        return sdf.format(cal.getTime());
    }

    /** jjunon 2014.03.06
     * 현재 날짜 기준 몇일(ago) 후 날짜를 yyyyMMdd 패턴 문자열로 포맷한다.
     *
     * @return the string
     */
    public static String getAfterDay(int ago) {
    	Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, 1 * ago);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        sdf.setTimeZone(new SimpleTimeZone(9 * 60 * 60 * 1000, "KST"));
        return sdf.format(cal.getTime());
    }

    public static String getCurrDate() {
        GregorianCalendar gcal = new GregorianCalendar();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        sdf.setTimeZone(new SimpleTimeZone(9 * 60 * 60 * 1000, "KST"));
        return sdf.format(gcal.getTime());
    }

    public static String getCurrTime() {
        java.util.GregorianCalendar gcal = new java.util.GregorianCalendar();
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("HHmmss");
        sdf.setTimeZone(new java.util.SimpleTimeZone(9 * 60 * 60 * 1000, "KST"));
        return sdf.format(gcal.getTime());
    }

    public static Map<String, Object> getBetweenDate(int standardHour) throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		Calendar cal = Calendar.getInstance();
		int currentHour = cal.get(Calendar.HOUR_OF_DAY);
		String currentDay = getCurrDate();
		String startDate = "";
		String endDate = "";

		String startHourText = StringUtils.getLPad(Integer.toString(standardHour), "0", 2) + "0000";

		int endHour = standardHour - 1;
		if(endHour <= 0) {
			endHour = 23;
		}
		String endHourText = StringUtils.getLPad(Integer.toString(endHour), "0", 2) + "5959";

		if(currentHour >= standardHour) {//현재 시간이 기준시간보다 크구나...그럼 담날꺼를 가져오자..
			startDate = currentDay + startHourText;
			endDate = getAfterDay(1) + endHourText;
		} else {
			startDate = getBeforeDay(1) + startHourText;
			endDate = currentDay + endHourText;
		}

		resultMap.put("startDate", startDate);
		resultMap.put("endDate", endDate);

		return resultMap;
	}

}
