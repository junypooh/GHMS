package com.kt.giga.home.openapi.ghms.kafka.smcp.gw.ca.query;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.kt.giga.home.openapi.ghms.kafka.smcp.gw.ca.domn.spotdev.SpotDevBas;
import com.kt.giga.home.openapi.ghms.kafka.smcp.gw.ca.domn.spotdev.SpotDevCommChDtl;
import com.kt.giga.home.openapi.ghms.kafka.smcp.gw.ca.domn.spotdev.SpotDevDtl;

@JsonInclude(Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
public class SpotDevBasVO extends SpotDevBas {

	private CommnAgntBasVO commnAgntBasVO;

	/** 현장장치상세 정보 목록 */
	/** 현장장치통신채널상세목록*/
	private List<SpotDevDtlVO> spotDevDtlVOs = new ArrayList<SpotDevDtlVO>();
	private List<SpotDevCommChDtlVO> spotDevCommChDtlVOs = new ArrayList<SpotDevCommChDtlVO>();

	public CommnAgntBasVO getCommnAgntBasVO() {
		return commnAgntBasVO;
	}

	public void setCommnAgntBasVO(CommnAgntBasVO commnAgntBasVO) {
		this.commnAgntBasVO = commnAgntBasVO;
	}

	/**
	 * @return the spotDevDtlVOs
	 */
	public List<SpotDevDtlVO> getSpotDevDtlVOs() {
		return spotDevDtlVOs;
	}

	/**
	 * @param spotDevDtlVOs the spotDevDtlVOs to set
	 */
	public void setSpotDevDtlVOs(List<SpotDevDtlVO> spotDevDtlVOs) {
		this.spotDevDtlVOs = spotDevDtlVOs;
	}


	/**
	 * @return the spotDevCommChDtlVOs
	 */
	public List<SpotDevCommChDtlVO> getSpotDevCommChDtlVOs() {
		return spotDevCommChDtlVOs;
	}

	/**
	 * @param spotDevCommChDtlVOs the spotDevCommChDtlVOs to set
	 */
	public void setSpotDevCommChDtlVOs(List<SpotDevCommChDtlVO> spotDevCommChDtlVOs) {
		this.spotDevCommChDtlVOs = spotDevCommChDtlVOs;
	}


	public static class SpotDevDtlVO extends SpotDevDtl {
		private Long svcTgtSeq;		// 서비스대상일련번호
		private Long spotDevSeq;

		/**
		 * @return the svcTgtSeq
		 */
		public Long getSvcTgtSeq() {
			return svcTgtSeq;
		}
		/**
		 * @param svcTgtSeq the svcTgtSeq to set
		 */
		public void setSvcTgtSeq(Long svcTgtSeq) {
			this.svcTgtSeq = svcTgtSeq;
		}
		/**
		 * @return the spotDevSeq
		 */
		public Long getSpotDevSeq() {
			return spotDevSeq;
		}
		/**
		 * @param spotDevSeq the spotDevSeq to set
		 */
		public void setSpotDevSeq(Long spotDevSeq) {
			this.spotDevSeq = spotDevSeq;
		}
	}

	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}

	/**
	 * <PRE>
	 *  ClassName GwCommChDtlVO
	 * </PRE>
	 * @brief
	 * @version 1.0
	 * @date 2014. 1. 22. 오후 3:08:24
	 * @author byw
	 */

	public static class SpotDevCommChDtlVO extends SpotDevCommChDtl {
		/** 서비스대상일련번호 */
		private Long svcTgtSeq;
		/** 현장장치일련번호 */
		private Long spotDevSeq;

		/**
		 * @return the svcTgtSeq
		 */
		public Long getSvcTgtSeq() {
			return svcTgtSeq;
		}
		/**
		 * @param svcTgtSeq the svcTgtSeq to set
		 */
		public void setSvcTgtSeq(Long svcTgtSeq) {
			this.svcTgtSeq = svcTgtSeq;
		}
		/**
		 * @return the spotDevSeq
		 */
		public Long getSpotDevSeq() {
			return spotDevSeq;
		}
		/**
		 * @param spotDevSeq the spotDevSeq to set
		 */
		public void setSpotDevSeq(Long spotDevSeq) {
			this.spotDevSeq = spotDevSeq;
		}
	}

}
