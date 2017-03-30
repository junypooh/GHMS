/*******************************************************************************
 * Copyright(c) 2015 KT. All rights reserved.
 * This software is the proprietary information of KT.
 *******************************************************************************/
package com.kt.giga.home.openapi.ghms.cms.domain.vo;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.kt.giga.home.openapi.ghms.common.domain.vo.BaseVo;

/**
 * 약관 VO
 * 
 * @author dahye.kim (dahye.kim@ceinside.co.kr)
 * @since 2015. 2. 6.
 */
@JsonInclude(Include.NON_EMPTY)
public class TermsVo extends BaseVo{

    @JsonProperty("termsListId")
    private String  pk_terms;                       // 약관아이디
    
    @JsonProperty("termsCode")
    private String fk_cp_code;                      // 약관코드

    @JsonProperty("termsVer")
    private String fd_terms_ver;                    // 약관 버전
    
    @JsonProperty("termsTitle")
    private String fd_terms_title;                  // 약관 제목
    
    private String termsUrl;                        // 약관 URL
    
    @JsonProperty("termsStart")
    private String  fd_terms_start;                 // 약관 시행일
    
    @JsonProperty("termsAgreeYn")
    private String  fd_agree_yn;                    // 약관 동의 여부 yn
    
    @JsonProperty("termsMandatoryYn")
    private String  fd_terms_mandatory_yn;          // 약관동의 필수여부YN
    
    /* 14세 미만 법정대리인 본인인증 요청 */
    
    private String checksum;                        // 인증번호 검증을 위한 key
    
    private String check1;                         // 인증번호 검증을 위한 파라미터 1
    
    private String check2;                         // 인증번호 검증을 위한 파라미터 2
    
    private String check3;                         // 인증번호 검증을 위한 파라미터 3

    /**
     * @return TODO
     */
    public String getChecksum() {
        return checksum;
    }

    /**
	 * @return TODO
	 */
	public String getCheck1() {
		return check1;
	}

	/**
	 * @param check1 TODO
	 */
	public void setCheck1(String check1) {
		this.check1 = check1;
	}

	/**
	 * @return TODO
	 */
	public String getCheck2() {
		return check2;
	}

	/**
	 * @param check2 TODO
	 */
	public void setCheck2(String check2) {
		this.check2 = check2;
	}

	/**
	 * @return TODO
	 */
	public String getCheck3() {
		return check3;
	}

	/**
	 * @param check3 TODO
	 */
	public void setCheck3(String check3) {
		this.check3 = check3;
	}

	/**
     * @param checksum TODO
     */
    public void setChecksum(String checksum) {
        this.checksum = checksum;
    }

    /**
     * @return TODO
     */
    public String getPk_terms() {
        return pk_terms;
    }

    /**
     * @param pk_terms TODO
     */
    public void setPk_terms(String pk_terms) {
        this.pk_terms = pk_terms;
    }

    /**
     * @return TODO
     */
    public String getFk_cp_code() {
        return fk_cp_code;
    }

    /**
     * @param fk_cp_code TODO
     */
    public void setFk_cp_code(String fk_cp_code) {
        this.fk_cp_code = fk_cp_code;
    }

    /**
     * @return TODO
     */
    public String getFd_terms_ver() {
        return fd_terms_ver;
    }

    /**
     * @param fd_terms_ver TODO
     */
    public void setFd_terms_ver(String fd_terms_ver) {
        this.fd_terms_ver = fd_terms_ver;
    }

    /**
     * @return TODO
     */
    public String getFd_terms_title() {
        return fd_terms_title;
    }

    /**
     * @param fd_terms_title TODO
     */
    public void setFd_terms_title(String fd_terms_title) {
        this.fd_terms_title = fd_terms_title;
    }

    /**
     * @return TODO
     */
    public String getTermsUrl() {
        return termsUrl;
    }

    /**
     * @param termsUrl TODO
     */
    public void setTermsUrl(String termsUrl) {
        this.termsUrl = termsUrl;
    }

    /**
     * @return TODO
     */
    public String getFd_terms_start() {
        return fd_terms_start;
    }

    /**
     * @param fd_terms_start TODO
     */
    public void setFd_terms_start(String fd_terms_start) {
        this.fd_terms_start = fd_terms_start;
    }

    /**
     * @return TODO
     */
    public String getFd_agree_yn() {
        return fd_agree_yn;
    }

    /**
     * @param fd_agree_yn TODO
     */
    public void setFd_agree_yn(String fd_agree_yn) {
        this.fd_agree_yn = fd_agree_yn;
    }

    /**
     * @return TODO
     */
    public String getFd_terms_mandatory_yn() {
        return fd_terms_mandatory_yn;
    }

    /**
     * @param fd_terms_mandatory_yn TODO
     */
    public void setFd_terms_mandatory_yn(String fd_terms_mandatory_yn) {
        this.fd_terms_mandatory_yn = fd_terms_mandatory_yn;
    }
}
