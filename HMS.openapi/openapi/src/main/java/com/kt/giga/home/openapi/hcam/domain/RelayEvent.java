package com.kt.giga.home.openapi.hcam.domain;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.joda.time.DateTime;

@XmlRootElement(name = "RelayEvent")
public class RelayEvent {

	// 이벤트 종류
	private String eventType;
	// 세션 아이디
	private String sessionID;
	// 채널 아이디
	private String channelID;
	// 입력 캠 아이디
	private String inputDeviceID;
	// 출력 단말 아이디
	private String outputDeviceID;
	// 해당 채널이 시작한 시간
	private DateTime channelStartTime;
	// 세션이 모두 종료된 후부터 캠 연결을 유지하는 시간
	private DateTime channelWaitingTime;
	// 해당 작업이 들어온 시간
	private DateTime sessionStartTime;
	//종료 시간
	private DateTime sessionTimeout;
	//세션이 전부 끊기고 캠 타임 아웃이 시작된 시간
	private DateTime channelEmptyTime;


	public String getEventType() {
		return eventType;
	}

	@XmlElement
	public void setEventType(String eventType) {
		this.eventType = eventType;
	}
	public String getSessionID() {
		return sessionID;
	}

	@XmlElement
	public void setSessionID(String sessionId) {
		this.sessionID = sessionId;
	}
	public String getChannelID() {
		return channelID;
	}

	@XmlElement
	public void setChannelID(String channelId) {
		this.channelID = channelId;
	}
	public String getInputDeviceID() {
		return inputDeviceID;
	}

	@XmlElement
	public void setInputDeviceID(String inputDeviceId) {
		this.inputDeviceID = inputDeviceId;
	}
	public String getOutputDeviceID() {
		return outputDeviceID;
	}

	@XmlElement
	public void setOutputDeviceID(String outputDeviceId) {
		this.outputDeviceID = outputDeviceId;
	}
	public DateTime getChannelStartTime() {
		return channelStartTime;
	}

	@XmlElement
	public void setChannelStartTime(DateTime channelStartTime) {
		this.channelStartTime = channelStartTime;
	}
	public DateTime getChannelWaitingTime() {
		return channelWaitingTime;
	}

	@XmlElement
	public void setChannelWaitingTime(DateTime channelWaitingTime) {
		this.channelWaitingTime = channelWaitingTime;
	}
	public DateTime getSessionStartTime() {
		return sessionStartTime;
	}

	@XmlElement
	public void setSessionStartTime(DateTime sessionStartTime) {
		this.sessionStartTime = sessionStartTime;
	}
	public DateTime getSessionTimeout() {
		return sessionTimeout;
	}

	@XmlElement
	public void setSessionTimeout(DateTime sessionTimeout) {
		this.sessionTimeout = sessionTimeout;
	}
	public DateTime getChannelEmptyTime() {
		return channelEmptyTime;
	}

	@XmlElement
	public void setChannelEmptyTime(DateTime channelEmptyTime) {
		this.channelEmptyTime = channelEmptyTime;
	}

}
