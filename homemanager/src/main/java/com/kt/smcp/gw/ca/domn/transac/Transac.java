package com.kt.smcp.gw.ca.domn.transac;

import java.util.HashMap;

import com.kt.smcp.gw.ca.comm.GwCode.UseYn;

public class Transac
{
	/** 상위시스템아이디 */
	private String upSysId;
	/** 트랜잭션아이디(상위) */
	private String upSysTransacId;
	/** 게이트웨이연결아이디 */
	private String gwCnctId;
	/** 게이트웨이트랜잭션아이디 */
	private String gwCnctTransacId;
	/**요청API서버아이디  */
	private String reqApiSrvrId;
	/**요청EC서버아이디  */
	private String reqEcSrvrId;
	/** 트랜잭션생성시간 */
	private Long transacCretTime = System.currentTimeMillis();
	/** 트랜잭션시작시간 */
	private Long transacStTime;
	/** 트랜잭션종료시간 */
	private Long transacEndTime;
	/** 타임아웃기준시간 */
	private Long toutBaseTime;
	/** 트랜잭션처리상태 */
	private TransacTrtSttus transacTrtSttus = TransacTrtSttus.START;
	/** 저장여부 */
	private UseYn storeYn = UseYn.NO;

	/** 트랜잭션정보 */
	private HashMap<String, Object> transacObject = new HashMap<String, Object>();

	public void setTransac(Transac transac)
	{
		this.upSysId = transac.getUpSysId();
		this.upSysTransacId = transac.getUpSysTransacId();
		this.gwCnctId = transac.getGwCnctId();
		this.gwCnctTransacId = transac.getGwCnctTransacId();
		this.transacCretTime = transac.getTransacCretTime();
		this.transacStTime = transac.getTransacStTime();
		this.transacEndTime = transac.getTransacEndTime();
		this.toutBaseTime = transac.getToutBaseTime();
		this.transacTrtSttus = transac.getTransacTrtSttus();
		this.transacObject = transac.getTransacObject();
		this.storeYn = transac.getStoreYn();
	}


	public String getUpSysId() {
		return upSysId;
	}


	public void setUpSysId(String upSysId) {
		this.upSysId = upSysId;
	}


	public String getUpSysTransacId() {
		return upSysTransacId;
	}


	public void setUpSysTransacId(String upSysTransacId) {
		this.upSysTransacId = upSysTransacId;
	}


	public String getGwCnctId() {
		return gwCnctId;
	}


	public void setGwCnctId(String gwCnctId) {
		this.gwCnctId = gwCnctId;
	}


	public String getGwCnctTransacId() {
		return gwCnctTransacId;
	}


	public void setGwCnctTransacId(String gwCnctTransacId) {
		this.gwCnctTransacId = gwCnctTransacId;
	}


	public String getReqApiSrvrId() {
		return reqApiSrvrId;
	}


	public void setReqApiSrvrId(String reqApiSrvrId) {
		this.reqApiSrvrId = reqApiSrvrId;
	}


	public String getReqEcSrvrId() {
		return reqEcSrvrId;
	}


	public void setReqEcSrvrId(String reqEcSrvrId) {
		this.reqEcSrvrId = reqEcSrvrId;
	}


	public Long getTransacCretTime() {
		return transacCretTime;
	}


	public void setTransacCretTime(Long transacCretTime) {
		this.transacCretTime = transacCretTime;
	}


	public Long getTransacStTime() {
		return transacStTime;
	}


	public void setTransacStTime(Long transacStTime) {
		this.transacStTime = transacStTime;
	}


	public Long getTransacEndTime() {
		return transacEndTime;
	}


	public void setTransacEndTime(Long transacEndTime) {
		this.transacEndTime = transacEndTime;
	}


	public Long getToutBaseTime() {
		return toutBaseTime;
	}


	public void setToutBaseTime(Long toutBaseTime) {
		this.toutBaseTime = toutBaseTime;
	}


	public TransacTrtSttus getTransacTrtSttus() {
		return transacTrtSttus;
	}


	public void setTransacTrtSttus(TransacTrtSttus transacTrtSttus) {
		this.transacTrtSttus = transacTrtSttus;
	}


	public UseYn getStoreYn() {
		return storeYn;
	}


	public void setStoreYn(UseYn storeYn) {
		this.storeYn = storeYn;
	}


	public HashMap<String, Object> getTransacObject() {
		return transacObject;
	}


	public void setTransacObject(HashMap<String, Object> transacObject) {
		this.transacObject = transacObject;
	}


	public static enum TransacTrtSttus
	{
		START("START"),
		TRT_PROGR("TRT_PROGR"),
		END("END"),
		TOUT("TOUT"),
		;

		private final String value;

		private TransacTrtSttus(String value) {
			this.value = value;
		}

		public boolean equals(String obj) {
			return value.equals(obj);
		}

		/**
		 * @return the value
		 */
		public String getValue() {
			return value;
		}

		/*
		 * (non-Javadoc)
		 *
		 * @see java.lang.Enum#toString()
		 */
		@Override
		public String toString() {
			return value;
		}

		// value에 해당되는 enum을 반환하기 위한 Map 생성 및 설정
		private static final HashMap<String, TransacTrtSttus> map = new HashMap<String, TransacTrtSttus>();
		static {
			for (TransacTrtSttus it : values()) {
				map.put(it.toString(), it);
			}
		}

		// value에 해당되는 enum을 반환
		public static TransacTrtSttus fromString(String value) {
			return map.get(value);
		}
	}
}


