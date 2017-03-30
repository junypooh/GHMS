/*******************************************************************************
 * Copyright(c) 2015 KT. All rights reserved.
 * This software is the proprietary information of KT.
 *******************************************************************************/
package com.kt.giga.home.openapi.ghms.user.domain.key;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

/**
 * 
 * @author junypooh ( kyungjun.park@ceinside.co.kr )
 * @since 2015. 2. 4.
 */
public class MasterUserKey {
    
    /** 지역 코드 */
    private String dstrCd; 
    
    /** 서비스 테마 코드 */
    private String svcThemeCd;
    
    /** 단위 서비스 코드 */
    private String unitSvcCd;
    
    /** 그룹 설정 코드 */
    private String groupSetupCd; 
    
    /** 설정 코드 */
    private String setupCd;               
	
	/** 휴대폰 번호 */
	private String telNo;  
	
	/** 회원 일련 번호 */
	private long userNo; 
	
	/** 회원 닉네임 */
	private String userNm;
	
	/** 서비스 대상 일련 번호 */
	private long serviceNo;
	
	/** 디바이스 ID (현장장치일련번호) */
	private long devUUID; 
	
	/** 비상연락망 시퀀스 */
	private int seq; 
	
	/** 사용자 패스워드 */
	private String passwd;
	
	/** 설정 코드 value */
	private String setupVal;
    
    /** 사용자 프로필 이미지 */
    private CommonsMultipartFile userImg;
    
    /** 알림수신여부(Y, N) */
    private String pushYn;
    
    /** 도어락 비빌번호 저장 메모리 공간 */
    private int memorySeq;

    /** file 관리 테이블 seq */
    private int fileSeq;

    /** 원본파일명 */
    private String orgFileNm;

    /** 저장파일명 */
    private String storeFileNm;

    /** 파일사이즈값 */
    private int fileSize;

    /** 접근URL주소 */
    private String acesUrlAdr;

    /** 컨텐츠타입값 */
    private String contsType;
    
    /** 사용자 패스워드 일련번호 */
    private long passwdSeq;
    
    /** 가족사용자 신청 승인 여부 */
    private String approvalYn;

    /**
     * @return TODO
     */
    public String getSetupVal() {
        return setupVal;
    }

    /**
     * @param setupVal TODO
     */
    public void setSetupVal(String setupVal) {
        this.setupVal = setupVal;
    }

    /**
     * @return TODO
     */
    public long getDevUUID() {
        return devUUID;
    }

    /**
     * @param devUUID TODO
     */
    public void setDevUUID(long devUUID) {
        this.devUUID = devUUID;
    }

    /**
     * @return TODO
     */
    public String getPasswd() {
        return passwd;
    }

    /**
     * @param passwd TODO
     */
    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    /**
     * @return TODO
     */
    public String getDstrCd() {
        return dstrCd;
    }

    /**
     * @return TODO
     */
    public String getSvcThemeCd() {
        return svcThemeCd;
    }

    /**
     * @return TODO
     */
    public String getUnitSvcCd() {
        return unitSvcCd;
    }

    /**
     * @return TODO
     */
    public String getGroupSetupCd() {
        return groupSetupCd;
    }

    /**
     * @return TODO
     */
    public String getSetupCd() {
        return setupCd;
    }

    /**
     * @return TODO
     */
    public String getTelNo() {
        return telNo;
    }

    /**
     * @return TODO
     */
    public long getUserNo() {
        return userNo;
    }

    /**
     * @return TODO
     */
    public String getUserNm() {
        return userNm;
    }

    /**
     * @return TODO
     */
    public long getServiceNo() {
        return serviceNo;
    }

    /**
     * @param dstrCd TODO
     */
    public void setDstrCd(String dstrCd) {
        this.dstrCd = dstrCd;
    }

    /**
     * @param svcThemeCd TODO
     */
    public void setSvcThemeCd(String svcThemeCd) {
        this.svcThemeCd = svcThemeCd;
    }

    /**
     * @param unitSvcCd TODO
     */
    public void setUnitSvcCd(String unitSvcCd) {
        this.unitSvcCd = unitSvcCd;
    }

    /**
     * @param groupSetupCd TODO
     */
    public void setGroupSetupCd(String groupSetupCd) {
        this.groupSetupCd = groupSetupCd;
    }

    /**
     * @param setupCd TODO
     */
    public void setSetupCd(String setupCd) {
        this.setupCd = setupCd;
    }

    /**
     * @param telNo TODO
     */
    public void setTelNo(String telNo) {
        this.telNo = telNo;
    }

    /**
     * @param userNo TODO
     */
    public void setUserNo(long userNo) {
        this.userNo = userNo;
    }

    /**
     * @param userNm TODO
     */
    public void setUserNm(String userNm) {
        this.userNm = userNm;
    }

    /**
     * @param serviceNo TODO
     */
    public void setServiceNo(long serviceNo) {
        this.serviceNo = serviceNo;
    }

	/**
	 * @return TODO
	 */
	public int getSeq() {
		return seq;
	}

	/**
	 * @param seq TODO
	 */
	public void setSeq(int seq) {
		this.seq = seq;
	}

	/**
	 * @return TODO
	 */
	public CommonsMultipartFile getUserImg() {
		return userImg;
	}

	/**
	 * @param userImg TODO
	 */
	public void setUserImg(CommonsMultipartFile userImg) {
		this.userImg = userImg;
	}

	/**
	 * @return TODO
	 */
	public String getPushYn() {
		return pushYn;
	}

	/**
	 * @param pushYn TODO
	 */
	public void setPushYn(String pushYn) {
		this.pushYn = pushYn;
	}
	
    /**
     * @return TODO
     */
    public int getMemorySeq() {
        return memorySeq;
    }

    /**
     * @param memorySeq TODO
     */
    public void setMemorySeq(int memorySeq) {
        this.memorySeq = memorySeq;
    }

    /**
	 * @return TODO
	 */
	public int getFileSeq() {
		return fileSeq;
	}

	/**
	 * @param fileSeq TODO
	 */
	public void setFileSeq(int fileSeq) {
		this.fileSeq = fileSeq;
	}

	/**
	 * @return TODO
	 */
	public String getOrgFileNm() {
		return orgFileNm;
	}

	/**
	 * @param orgFileNm TODO
	 */
	public void setOrgFileNm(String orgFileNm) {
		this.orgFileNm = orgFileNm;
	}

	/**
	 * @return TODO
	 */
	public String getStoreFileNm() {
		return storeFileNm;
	}

	/**
	 * @param storeFileNm TODO
	 */
	public void setStoreFileNm(String storeFileNm) {
		this.storeFileNm = storeFileNm;
	}

	/**
	 * @return TODO
	 */
	public int getFileSize() {
		return fileSize;
	}

	/**
	 * @param fileSize TODO
	 */
	public void setFileSize(int fileSize) {
		this.fileSize = fileSize;
	}

	/**
	 * @return TODO
	 */
	public String getAcesUrlAdr() {
		return acesUrlAdr;
	}

	/**
	 * @param acesUrlAdr TODO
	 */
	public void setAcesUrlAdr(String acesUrlAdr) {
		this.acesUrlAdr = acesUrlAdr;
	}

	/**
	 * @return TODO
	 */
	public String getContsType() {
		return contsType;
	}

	/**
	 * @param contsType TODO
	 */
	public void setContsType(String contsType) {
		this.contsType = contsType;
	}
    
    /**
     * @return TODO
     */
    public long getPasswdSeq() {
        return passwdSeq;
    }

    /**
     * @param passwdSeq TODO
     */
    public void setPasswdSeq(long passwdSeq) {
        this.passwdSeq = passwdSeq;
    }

    /**
	 * @return TODO
	 */
	public String getApprovalYn() {
		return approvalYn;
	}

	/**
	 * @param approvalYn TODO
	 */
	public void setApprovalYn(String approvalYn) {
		this.approvalYn = approvalYn;
	}

	/**
     * 파라미터 로그
     * {@inheritDoc}
     */
    @Override
    public String toString(){
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
	
}
