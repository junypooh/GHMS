package com.kt.giga.home.cms.vo.row;


/**
 * 위치데이터 클래스
 * @since	: 2014. 11. 2.
 * @author	: CBJ
 * <PRE>
 * Revision History
 * ----------------------------------------------------
 * 2014. 11. 2. CBJ: 최초작성
 * 2014. 11.16. 조홍진: OpenAPI 커스토마이징 
 * ----------------------------------------------------
 * </PRE>
 */
public class LoData
{
	/** 센싱태그코드 */
	private String snsnTagCd;
	/** 위치정보생성지 */
	private String 	loInfoCre;
	/** 위도 */
	private Double latit;
	/** 경도 */
	private Double lngit;
	/** 고도 */
	private Double altt;
	/** GPS상태 */
	private Integer gpsSttus;
	/** 수신위성개수 */
	private Integer rcvSatelCnt;
	/** 수평오차 */
	private Double hriznDfrn;
	/** 수직오차 */
	private Double vrtclDfrn;
	/** 방향 */
	private Integer drect;
	/** 속도 */
	private Double	speed;

	public String getSnsnTagCd() {
		return snsnTagCd;
	}

	public void setSnsnTagCd(String snsnTagCd) {
		this.snsnTagCd = snsnTagCd;
	}

	public String getLoInfoCre() {
		return loInfoCre;
	}

	public void setLoInfoCre(String loInfoCre) {
		this.loInfoCre = loInfoCre;
	}

	public Double getLatit() {
		return latit;
	}

	public void setLatit(Double latit) {
		this.latit = latit;
	}

	public Double getLngit() {
		return lngit;
	}

	public void setLngit(Double lngit) {
		this.lngit = lngit;
	}

	public Double getAltt() {
		return altt;
	}

	public void setAltt(Double altt) {
		this.altt = altt;
	}

	public Integer getGpsSttus() {
		return gpsSttus;
	}

	public void setGpsSttus(Integer gpsSttus) {
		this.gpsSttus = gpsSttus;
	}

	public Integer getRcvSatelCnt() {
		return rcvSatelCnt;
	}

	public void setRcvSatelCnt(Integer rcvSatelCnt) {
		this.rcvSatelCnt = rcvSatelCnt;
	}

	public Double getHriznDfrn() {
		return hriznDfrn;
	}

	public void setHriznDfrn(Double hriznDfrn) {
		this.hriznDfrn = hriznDfrn;
	}

	public Double getVrtclDfrn() {
		return vrtclDfrn;
	}

	public void setVrtclDfrn(Double vrtclDfrn) {
		this.vrtclDfrn = vrtclDfrn;
	}

	public Integer getDrect() {
		return drect;
	}

	public void setDrect(Integer drect) {
		this.drect = drect;
	}

	public Double getSpeed() {
		return speed;
	}

	public void setSpeed(Double speed) {
		this.speed = speed;
	}

}
