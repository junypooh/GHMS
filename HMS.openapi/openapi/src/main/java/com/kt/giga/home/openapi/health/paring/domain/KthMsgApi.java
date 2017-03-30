package com.kt.giga.home.openapi.health.paring.domain;


/**
 * kth messaging Api 클래스
 * 
 * @author 조상현
 *
 */

public class KthMsgApi {
	private String	send_time;		// 발송시간(없을 경우 즉시발송) “20130529171111”(2013-05-29-17:11:11)
	private String	send_phone;		// 발신자 전화번호 “01012345678”
	private String	dest_phone;		// 수신자 전화번호(동보 발송 시 콤마”,”구분자 사용) “01012345678”
	private String	send_name;		// 발신자 이름(32byte 미만)
	private String	dest_name;		// 수신자 이름(32byte 미만)
	private String	subject;		// 메시지의 제목(60byte 미만)
	private String	msg_body;		// 메시지의 내용(80byte 미만)
	private String	smsExcel;		// 대량 전송 할 경우 엑셀파일에 파라미터 데이터 입력하여 보냄(필드구성 : 발송시간|수신자번호|수신자이름|발신자번호|발신자이름|제목|내용)
	
	
	/**
	 * @return  발송시간
	 */
	public String getSend_time() {
		return send_time;
	}
	/**
	 * @param  발송시간
	 */
	public void setSend_time(String send_time) {
		this.send_time = send_time;
	}
	/**
	 * @return 발신자 전화번호
	 */
	public String getSend_phone() {
		return send_phone;
	}
	/**
	 * @param 발신자 전화번호
	 */
	public void setSend_phone(String send_phone) {
		this.send_phone = send_phone;
	}
	/**
	 * @return 수신자 전화번호
	 */
	public String getDest_phone() {
		return dest_phone;
	}
	/**
	 * @param 수신자 전화번호
	 */
	public void setDest_phone(String dest_phone) {
		this.dest_phone = dest_phone;
	}
	/**
	 * @return 발신자 이름
	 */
	public String getSend_name() {
		return send_name;
	}
	/**
	 * @param 발신자 이름
	 */
	public void setSend_name(String send_name) {
		this.send_name = send_name;
	}
	/**
	 * @return 수신자 이름
	 */
	public String getDest_name() {
		return dest_name;
	}
	/**
	 * @param 수신자 이름
	 */
	public void setDest_name(String dest_name) {
		this.dest_name = dest_name;
	}
	/**
	 * @return 메시지의 제목
	 */
	public String getSubject() {
		return subject;
	}
	/**
	 * @param 메시지의 제목
	 */
	public void setSubject(String subject) {
		this.subject = subject;
	}
	/**
	 * @return 메시지의 내용
	 */
	public String getMsg_body() {
		return msg_body;
	}
	/**
	 * @param 메시지의 내용
	 */
	public void setMsg_body(String msg_body) {
		this.msg_body = msg_body;
	}
	/**
	 * @return 대량 전송 할 경우 엑셀파일에 파라미터 데이터 입력하여 보냄
	 */
	public String getSmsExcel() {
		return smsExcel;
	}
	/**
	 * @param 대량 전송 할 경우 엑셀파일에 파라미터 데이터 입력하여 보냄
	 */
	public void setSmsExcel(String smsExcel) {
		this.smsExcel = smsExcel;
	}

}
