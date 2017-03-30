package com.kt.giga.home.openapi.ghms.kafka.type;

import java.util.HashMap;
import java.util.Map;

public enum KafkaMsgType {
	/** 장치정보 조회(내부) */
	INITA_DEV_RETV( ((short)3311) ),
	/** 장치정보 조회 응답(내부) */
	INITA_DEV_RETV_RESLT( ((short)3312) ),
	/** 장치정보 갱신보고(외부) */
	INITA_DEV_UDATERPRT_EXTR( ((short)3321) ),
	/** 장치정보 조회(외부) */
	INITA_DEV_RETV_EXTR( ((short)3331) ),
	/** 장치정보 조회 응답(외부) */
	INITA_DEV_RETV_RESLT_EXTR( ((short)3332) ),
	/** 장치정보 갱신보고(내부)*/
	INITA_DEV_UDATERPRT( ((short)3341) ),
	/** 통합데이터 전달 */
	CONTL_ITGCNVY_DATA( ((short)5251) ),
	/** 통합데이터 전달 */
	CONTL_ITGCNVY_DATA_RESLT( ((short)5252) ),
	/** 최종값쿼리 */
	QRY_LAST_VAL( ((short)7121) ),
	/** 최종값쿼리 응답*/
	QRY_LAST_VAL_RESLT( ((short)7122) ),
	/** 펌웨어정보조회 */
	FRMWR_INFO_RETV( ((short)8111) ),
	/** 펌웨어정보조회 응답*/
	FRMWR_INFO_RETV_RESLT( ((short)8112) ),
	/** 펌웨어업데이트 통지 */
	FRMWR_UDATE_NTFY( ((short)8121) ),
	/** 펌웨어업데이트 통지 응답*/
	FRMWR_UDATE_NTFY_RESLT( ((short)8122) )
	;

	
	private static final Map<Short, KafkaMsgType> map = new HashMap<Short, KafkaMsgType>();
	static {
		for (KafkaMsgType it : values()) {
			map.put(it.getValue(), it);
		}
	}

	// value에 해당되는 enum을 반환
	public static KafkaMsgType fromShort(Short value) {
		return map.get(value);
	}
	
	
	short value;
	
	KafkaMsgType(short value){
		this.value = value;
	}
	
	public short getValue(){
		return this.value;
	}
	
	public boolean isValue(short value){
		return this.value == value;
	}
	
}
