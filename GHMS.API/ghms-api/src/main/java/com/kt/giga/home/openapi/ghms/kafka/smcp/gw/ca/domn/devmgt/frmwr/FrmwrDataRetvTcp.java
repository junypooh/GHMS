package com.kt.giga.home.openapi.ghms.kafka.smcp.gw.ca.domn.devmgt.frmwr;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.kt.smcp.gw.ca.domn.CorePrcssData;

/**
 *
 * @since	: 2015. 3. 23.
 * @author	: CBJ
 * <PRE>
 * Revision History
 * ----------------------------------------------------
 * 2015. 3. 23. CBJ: 최초작성
 * ----------------------------------------------------
 * </PRE>
 */
public class FrmwrDataRetvTcp extends CorePrcssData implements Serializable, Cloneable
{
	/** 직렬화아이디 */
	private static final long serialVersionUID = 8934542775708167230L;
	/** 펌웨어일련번호 */
	private Long frmwrSeq;
	/** 파일조각사이즈 */
	private Integer fileSegSize;
	/** 요청조각번호 */
	private Integer rqtSeqNo;

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.DEFAULT_STYLE);
	}

	@Override
	public boolean isEmptyData()
	{
		return false;
	}

	public Long getFrmwrSeq() {
		return frmwrSeq;
	}
	public void setFrmwrSeq(Long frmwrSeq) {
		this.frmwrSeq = frmwrSeq;
	}
	public Integer getFileSegSize() {
		return fileSegSize;
	}
	public void setFileSegSize(Integer fileSegSize) {
		this.fileSegSize = fileSegSize;
	}
	public Integer getRqtSeqNo() {
		return rqtSeqNo;
	}
	public void setRqtSeqNo(Integer rqtSeqNo) {
		this.rqtSeqNo = rqtSeqNo;
	}



}
