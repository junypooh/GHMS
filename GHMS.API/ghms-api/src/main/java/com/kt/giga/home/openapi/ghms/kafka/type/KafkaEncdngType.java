package com.kt.giga.home.openapi.ghms.kafka.type;

import java.util.HashMap;
import java.util.Map;

public enum KafkaEncdngType {
	/** userDefined */
	USER_DEFINED( (byte)0x1 ),
	/** xml */
	XML( (byte)0x2 ),
	/** json */
	JSON( (byte)0x3 ),
	/** gpb */
	GPB( (byte)0x10 ),
	/** thrift */
	THRIFT( (byte)0x11 ),
	/** avro */
	AVRO( (byte)0x12 ),
	/** pcre */
	PCRE( (byte)0x13 )
	;
	
	byte value;

	private static final Map<Byte, KafkaEncdngType> map = new HashMap<Byte, KafkaEncdngType>();
	static {
		for (KafkaEncdngType it : values()) {
			map.put(it.getValue(), it);
		}
	}	

	// value에 해당되는 enum을 반환
	public static KafkaEncdngType fromByte(byte value) {
		return map.get(value);
	}
	
    KafkaEncdngType(byte value) {
            this.value = value;
    }
    
	public byte getValue() {
		return this.value;
	}
    
}
