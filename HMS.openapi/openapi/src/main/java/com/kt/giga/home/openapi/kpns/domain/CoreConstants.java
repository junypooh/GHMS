package com.kt.giga.home.openapi.kpns.domain;

public class CoreConstants {
	public static enum  StormFields{
		INPUT_EVENT,	//Esper에 들어오는 이벤트를 나타내는 필드 이름(값으로는 CollectEvent, ComplexEvent 두 타입이 있다.) 
		SVC_TGT_SEQ,	//서비스대상 일련번호 
		OUT_EVENT,		//Esper에서 나가는 이벤트 필드 이름(OutbreakComplexEvent)
		EVENT_ACTION;	//이벤트 액션스크립트 필드 이름
	}
	
	public static enum KafkaTopics{
		COLLECT_EVENT,		//계측이벤트용 토픽
		COMPLEX_EVNET,		//외부발생이벤트 또는 3MPCore 생성 이벤트
		CONTROL_ACTION,		//제어 액션처리
		PROPAGATION_ACTION	//전파 액션처리
	}
}
