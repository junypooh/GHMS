package com.kt.smcp.gw.ca.domn.devmgt.frmwr;

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
public class PkgDataRetvTcp extends CorePrcssData implements Serializable, Cloneable
{
	/** 직렬화아이디 */
	private static final long serialVersionUID = 8934542775708167230L;
	/** 패키지일련번호 */
	private Long pkgSeq;
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

	public Long getPkgSeq() {
		return pkgSeq;
	}
	public void setPkgSeq(Long pkgSeq) {
		this.pkgSeq = pkgSeq;
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
