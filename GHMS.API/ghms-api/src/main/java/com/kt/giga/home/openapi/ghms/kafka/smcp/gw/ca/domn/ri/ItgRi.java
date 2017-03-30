package com.kt.giga.home.openapi.ghms.kafka.smcp.gw.ca.domn.ri;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.kt.giga.home.openapi.ghms.kafka.smcp.gw.ca.domn.CorePrcssData;

/**
 * 통합자원
 * @since	: 2015. 3. 1.
 * @author	: CBJ
 * <PRE>
 * Revision History
 * ----------------------------------------------------
 * 2015. 3. 1. CBJ: 최초작성
 * ----------------------------------------------------
 * </PRE>
 */
public class ItgRi extends CorePrcssData implements Serializable, Cloneable
{
	/** 직렬화아이디 */
	private static final long serialVersionUID = 7927619227937420065L;

	/** 게이트웨이어댑터자원리스트 */
	protected List<GwAdapRi> gwAdapRis = new ArrayList<GwAdapRi>();
	/** 상위어댑터자원리스트 */
	protected List<UpSysAdapRi> upSysAdapRis = new ArrayList<UpSysAdapRi>();
	/** 트랜잭션자원 */
	protected TransacRi transacRi;

	@Override
	public String toString()
	{
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SIMPLE_STYLE);
	}

	@Override
	public Object clone() throws CloneNotSupportedException
	{
		return super.clone();
	}

	@Override
	public boolean isEmptyData()
	{
		if(gwAdapRis.size() != 0)
		{
			return false;
		}

		if(upSysAdapRis.size() != 0)
		{
			return false;
		}

		if(transacRi != null)
		{
			return false;
		}
		return true;
	}

	public List<GwAdapRi> getGwAdapRis() {
		return gwAdapRis;
	}

	public void setGwAdapRis(List<GwAdapRi> gwAdapRis) {
		this.gwAdapRis = gwAdapRis;
	}

	public List<UpSysAdapRi> getUpSysAdapRis() {
		return upSysAdapRis;
	}

	public void setUpSysAdapRis(List<UpSysAdapRi> upSysAdapRis) {
		this.upSysAdapRis = upSysAdapRis;
	}

	public TransacRi getTransacRi() {
		return transacRi;
	}

	public void setTransacRi(TransacRi transacRi) {
		this.transacRi = transacRi;
	}


}
