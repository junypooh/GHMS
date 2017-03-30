/*******************************************************************************
 * Copyright(c) 2015 KT. All rights reserved.
 * This software is the proprietary information of KT.
 *******************************************************************************/
package com.kt.giga.home.openapi.ghms.user.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.kt.giga.home.openapi.ghms.common.GHmsConstants.CommonConstants;
import com.kt.giga.home.openapi.ghms.common.domain.vo.BaseVo;
import com.kt.giga.home.openapi.ghms.common.token.AuthToken;
import com.kt.giga.home.openapi.ghms.user.domain.key.DeviceMasterKey;
import com.kt.giga.home.openapi.ghms.user.domain.key.MasterUserKey;
import com.kt.giga.home.openapi.ghms.user.domain.vo.DeviceMasterVo;
import com.kt.giga.home.openapi.ghms.user.domain.vo.MasterUserVo;
import com.kt.giga.home.openapi.ghms.user.domain.vo.UserInfo;
import com.kt.giga.home.openapi.ghms.user.service.MasterService;
import com.kt.giga.home.openapi.ghms.util.exception.APIException;

/**
 * 
 * @author junypooh ( kyungjun.park@ceinside.co.kr )
 * @since 2015. 1. 29.
 */
@Controller
@RequestMapping("user/master")
public class MasterController {

	/** Logger */
	private Logger log = LoggerFactory.getLogger(getClass());
	
	/**
	 * API 리턴 미디어 타입
	 */	
	
	@Autowired
	private MasterService masterService;

	/**
	 * 사용자(가족구성원) 등록 요청
	 * 
	 * @param authToken		인증 토큰
	 * @param MasterUserVo
	 * @return				BaseVo
	 * @throws APIException
	 */
	@RequestMapping(value={"request"}, method=RequestMethod.POST, produces=CommonConstants.PRODUCES_JSON)
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public BaseVo request(
			@RequestHeader(value="authToken")	String authToken,
			@RequestBody						MasterUserKey key,
			HttpServletResponse response) throws APIException {
		
		// 토큰 체크
		AuthToken token = AuthToken.checkLoginAuthToken(authToken);
		// 갱신된 토큰을 response header에 다시 넣어준다.
		response.setHeader("authToken", token.getToken());
		// slave 등록 요청(마스터에게 PNS 전송)
		return masterService.insertSlaveRequest(token, key);
	}

	/**
	 * 사용자(가족구성원) 승인
	 * 
	 * @param authToken		인증 토큰
	 * @param MasterUserVo
	 * @return				BaseVo
	 * @throws APIException
	 */
	@RequestMapping(value={"admission"}, method=RequestMethod.POST, produces=CommonConstants.PRODUCES_JSON)
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public BaseVo admission(
			@RequestHeader(value="authToken")	String authToken,
			@RequestBody						MasterUserKey key,
			HttpServletResponse response) throws APIException {
		
		// 토큰 체크
		AuthToken token = AuthToken.checkLoginAuthToken(authToken);
		// 갱신된 토큰을 response header에 다시 넣어준다.
		response.setHeader("authToken", token.getToken());

		return masterService.insertSlaveInfoByPushAdmission(token, key);
	}

	/**
	 * 사용자(가족구성원) 수정
	 * 
	 * @param authToken		인증 토큰
	 * @param MasterUserVo
	 * @return				BaseVo
	 * @throws APIException
	 */
	@RequestMapping(value={"useredit"}, method=RequestMethod.POST)
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public BaseVo useredit(
			@RequestHeader(value="authToken")	String authToken,
			MasterUserKey key,
			HttpServletResponse response) throws APIException {
		
		// 토큰 체크
		AuthToken token = AuthToken.checkLoginAuthToken(authToken);
		// 갱신된 토큰을 response header에 다시 넣어준다.
		response.setHeader("authToken", token.getToken());

		BaseVo vo = null;
		try {
			vo = masterService.updateUserInfo(token, key);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return vo;			

	}

	/**
	 * 사용자 리스트 조회
	 * 
	 * @param authToken		인증 토큰
	 * @param serviceNo		서비스일련번호
	 * @return				ArrayList<MasterUserVo>
	 * @throws APIException
	 */
	@RequestMapping(value={"userlist"}, method=RequestMethod.GET, produces=CommonConstants.PRODUCES_JSON)
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public List<MasterUserVo> userlist(
			@RequestHeader(value="authToken")	String authToken,
			@RequestParam(value="serviceNo")	long serviceNo,
			@RequestParam(value="isMaster")		String isMaster,
			HttpServletResponse response) throws APIException {
		
		// 토큰 체크
		AuthToken token = AuthToken.checkLoginAuthToken(authToken, isMaster);
		
		// 갱신된 토큰을 response header에 다시 넣어준다.
		response.setHeader("authToken", token.getToken());
		
		List<MasterUserVo> list = masterService.selectSlaveUserList(token, serviceNo);
		
		return list;
	}

	/**
	 * 사용자 삭제
	 * 
	 * @param authToken		인증 토큰
	 * @param key			MasterUserKey
	 * @return				BaseVo
	 * @throws APIException
	 */
	@RequestMapping(value={"userlist"}, method=RequestMethod.DELETE, produces=CommonConstants.PRODUCES_JSON)
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public BaseVo userlistDelete(
			@RequestHeader(value="authToken")	String authToken,
			@RequestParam(value="serviceNo")     long serviceNo,
			@RequestParam(value="userNo")        long userNo,
			HttpServletResponse response) throws APIException {
		
		// 토큰 체크
		AuthToken token = AuthToken.checkLoginAuthToken(authToken);
		// 갱신된 토큰을 response header에 다시 넣어준다.
		response.setHeader("authToken", token.getToken());

		return masterService.deleteSlaveUser(token, userNo, serviceNo);
	}

	/**
	 * 사용자 프로필 이미지 다운로드
	 * @param authToken
	 * @param userNo
	 * @param response
	 * @throws APIException
	 * @throws IOException
	 */
	@RequestMapping(value={"user/profile"}, method=RequestMethod.GET, produces=CommonConstants.PRODUCES_JSON)
	@ResponseStatus(HttpStatus.OK)
	public void userprofile(
			@RequestParam(value="userNo")	long userNo,
			HttpServletResponse response) throws APIException, IOException {
		
		Map<String, Object> userProfileImg = masterService.selectUserProfileImg(userNo);
		
		File file = new File(userProfileImg.get("store_file_nm").toString());
        response.setContentType("application/x-octetstream; charset=utf-8");
        response.setContentLength((int) file.length());

        response.setHeader("Content-Disposition", "attachment; filename=\"" + userProfileImg.get("origin_file_nm").toString() + "\";");
        response.setHeader("Content-Transfer-Encoding", "binary");

        OutputStream os = response.getOutputStream();
        FileInputStream fis = null;

        try {

            fis = new FileInputStream(file);
            FileCopyUtils.copy(fis, os);

        } finally {

            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                	e.printStackTrace();
                    log.error("exception : " + e.toString());
                }
            }
        }
        os.flush();
        os.close();
	}

	/**
	 * 사용자 디바이스 비밀번호 등록
	 * 
	 * @param authToken		인증 토큰
	 * @param key			MasterUserKey
	 * @return				BaseVo
	 * @throws APIException
	 */
	@RequestMapping(value={"passwd"}, method=RequestMethod.POST, produces=CommonConstants.PRODUCES_JSON)
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public BaseVo passwdRegist(
			@RequestHeader(value="authToken")	String authToken,
			@RequestBody						MasterUserKey key,
			HttpServletResponse response) throws APIException {
		
		// 토큰 체크
		AuthToken token = AuthToken.checkLoginAuthToken(authToken);
		// 갱신된 토큰을 response header에 다시 넣어준다.
		response.setHeader("authToken", token.getToken());
		
		return masterService.insertMbrPwdTxn(key);
	}

	/**
	 * 사용자 디바이스 비밀번호 수정
	 * 
	 * @param authToken		인증 토큰
	 * @param key			MasterUserKey
	 * @return				BaseVo
	 * @throws APIException
	 */
	@Deprecated
	@RequestMapping(value={"passwd"}, method=RequestMethod.PUT, produces=CommonConstants.PRODUCES_JSON)
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public BaseVo passwdUpdate(
			@RequestHeader(value="authToken")	String authToken,
			@RequestBody						MasterUserKey key,
			HttpServletResponse response) throws APIException {
		
		// 토큰 체크
		AuthToken token = AuthToken.checkLoginAuthToken(authToken);
		// 갱신된 토큰을 response header에 다시 넣어준다.
		response.setHeader("authToken", token.getToken());
		
		// Dummy Data Setting
		BaseVo vo = new BaseVo();
		vo.setResultCode("0");

		return vo;
	}

	/**
	 * 사용자 디바이스 비밀번호 조회
	 * 
	 * @param authToken		초기 인증 토큰
	 * @param key			UserListKey
	 * @return				ArrayList<MasterUserVo>
	 * @throws APIException
	 */
	@RequestMapping(value={"passwd"}, method=RequestMethod.GET, produces=CommonConstants.PRODUCES_JSON)
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public List<MasterUserVo> passwdList(
			@RequestHeader(value="authToken")	String authToken,
			@RequestParam(value="serviceNo")	long serviceNo,
			@RequestParam(value="devUUID")		long devUUID,
			HttpServletResponse response) throws APIException {
		
		// 토큰 체크
		AuthToken token = AuthToken.checkLoginAuthToken(authToken);
		// 갱신된 토큰을 response header에 다시 넣어준다.
		response.setHeader("authToken", token.getToken());
		
		List<MasterUserVo> list = masterService.selectMbrPwdTxn(serviceNo, devUUID);
		
		return list;
	}

	/**
	 * 사용자 디바이스 비밀번호 조회
	 * 
	 * @param authToken		초기 인증 토큰
	 * @param key			UserListKey
	 * @return				ArrayList<MasterUserVo>
	 * @throws APIException
	 */
	@RequestMapping(value={"passwd/kt"}, method=RequestMethod.GET, produces=CommonConstants.PRODUCES_JSON)
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public List<MasterUserVo> passwdListKT(
			@RequestParam(value="serviceNo")	long serviceNo,
			@RequestParam(value="devUUID")		long devUUID,
			HttpServletResponse response) throws APIException {
		
		List<MasterUserVo> list = masterService.selectMbrPwdTxn(serviceNo, devUUID);
		
		return list;
	}
	
	/**
	 * 사용자 디바이스 비밀번호 삭제
	 * @param authToken    인증토큰
	 * @param userNo       비밀번호 일련번호
	 * @param serviceNo    서비스기본일련번호
	 * @param devUUID      현장장치일련번호    
	 * @param passwdSeq    사용자 패스워드 일련번호
	 * @param response
	 * @return             BaseVo
	 * @throws APIException
	 */
	@RequestMapping(value={"passwd"}, method=RequestMethod.DELETE, produces=CommonConstants.PRODUCES_JSON)
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public BaseVo passwdDelete(
			@RequestHeader(value="authToken")	String authToken,
            @RequestParam(value="userNo")       long userNo,
            @RequestParam(value="serviceNo")    long serviceNo,
            @RequestParam(value="devUUID")      long devUUID,
            @RequestParam(value="passwdSeq")    long passwdSeq,
			HttpServletResponse response) throws APIException {
		
		// 토큰 체크
		AuthToken token = AuthToken.checkLoginAuthToken(authToken);
		// 갱신된 토큰을 response header에 다시 넣어준다.
		response.setHeader("authToken", token.getToken());
		
		return masterService.deleteMbrPwdTxn(userNo, serviceNo, devUUID, passwdSeq);
	}

	/**
	 * 사용자 디바이스 사용권한 등록
	 * 
	 * @param authToken		인증 토큰
	 * @param key			DeviceMasterKey
	 * @return				BaseVo
	 * @throws APIException
	 */
	@Deprecated
	@RequestMapping(value={"device"}, method=RequestMethod.POST, produces=CommonConstants.PRODUCES_JSON)
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public BaseVo deviceRegist(
			@RequestHeader(value="authToken")	String authToken,
			@RequestBody						DeviceMasterKey key,
			HttpServletResponse response) throws APIException {
		
		// 토큰 체크
		AuthToken token = AuthToken.checkLoginAuthToken(authToken);
		// 갱신된 토큰을 response header에 다시 넣어준다.
		response.setHeader("authToken", token.getToken());
		
		// Dummy Data Setting
		BaseVo vo = new BaseVo();
		vo.setResultCode("0");

		return vo;
	}

	/**
	 * 사용자 디바이스 사용권한 수정
	 * 
	 * @param authToken		인증 토큰
	 * @param key			DeviceMasterKey
	 * @return				BaseVo
	 * @throws APIException
	 */
	@RequestMapping(value={"device"}, method=RequestMethod.PUT, produces=CommonConstants.PRODUCES_JSON)
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public BaseVo deviceUpdate(
			@RequestHeader(value="authToken")	String authToken,
			@RequestBody						DeviceMasterKey key,
			HttpServletResponse response) throws APIException {
		
		// 토큰 체크
		AuthToken token = AuthToken.checkLoginAuthToken(authToken);
		// 갱신된 토큰을 response header에 다시 넣어준다.
		response.setHeader("authToken", token.getToken());
		
		return masterService.updateDeviceUserAuth(token, key);
	}

	/**
	 * 사용자 디바이스 사용권한 조회
	 * 
	 * @param authToken		인증 토큰
	 * @param serviceNo		서비스일련번호
	 * @return				DeviceMasterVo
	 * @throws APIException
	 */
	@RequestMapping(value={"device"}, method=RequestMethod.GET, produces=CommonConstants.PRODUCES_JSON)
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public DeviceMasterVo deviceList(
			@RequestHeader(value="authToken")	String authToken,
			@RequestParam(value="serviceNo")	long serviceNo,
			@RequestParam(value="userNo")       long userNo,
			HttpServletResponse response) throws APIException {
		
		// 토큰 체크
		AuthToken token = AuthToken.checkLoginAuthToken(authToken);
		// 갱신된 토큰을 response header에 다시 넣어준다.
		response.setHeader("authToken", token.getToken());
		
		DeviceMasterVo vo = masterService.selectDeviceUseAuth(token, serviceNo, userNo);

		return vo;
	}

	/**
	 * 사용자 디바이스 사용권한 조회
	 * 
	 * @param authToken		인증 토큰
	 * @param serviceNo		서비스일련번호
	 * @return				DeviceMasterVo
	 * @throws APIException
	 */
	@RequestMapping(value={"user/info/kt"}, method=RequestMethod.GET, produces=CommonConstants.PRODUCES_JSON)
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public List<UserInfo> userInfoKT(
			@RequestParam(value="serviceNo")	long serviceNo) throws APIException {
		
		List<UserInfo> list = masterService.selectUserInfoList(serviceNo);

		return list;
	}

}
