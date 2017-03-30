package com.kt.giga.home.cms.common.controller;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.servlet.http.*;

import org.springframework.ui.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.CookieGenerator;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.Controller;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.kt.giga.home.cms.util.*;
import com.kt.giga.home.cms.common.domain.*;
import com.kt.giga.home.cms.common.service.*;
import com.kt.giga.home.cms.manager.domain.*;
import com.kt.giga.home.cms.manager.service.*;

@Controller
public class LoginoutController {
	
	@Autowired
	private APIEnv apiEnv;	
	
	@Autowired
	private HttpServletRequest request;	
	
	@Autowired
	private ManagerService managerService;
	
	@Autowired
	private KthMsgApiService kthMsgApiService;
	
	@Autowired
	private CodeService codeService;	
	
	private String getEncryptByAES(String plainText) throws UnsupportedEncodingException, Exception {
		byte[] key 	= apiEnv.getProperty("cms.encrypt.key").getBytes("UTF-8");
		byte[] iv	= apiEnv.getProperty("cms.encrypt.iv").getBytes("UTF-8");
		return CipherUtils.encryptAESCTRNoPaddingHex(key, iv, plainText);
	}
	
	private String getDecryptByAES(String plainText) throws UnsupportedEncodingException, Exception {
		byte[] key 	= apiEnv.getProperty("cms.encrypt.key").getBytes("UTF-8");
		byte[] iv	= apiEnv.getProperty("cms.encrypt.iv").getBytes("UTF-8");
		return CipherUtils.decryptAESCTRNoPaddingHex(key, iv, plainText);
	}	
	
	@ResponseBody
	@RequestMapping("/authSMS")
	@SuppressWarnings("unchecked")
	public Map<String, Object> authSMS(@RequestParam Map<String, Object> login) {
		
		Map<String, Object> result = new HashMap<>();
		
		String id = login.get("id") != null ? login.get("id").toString().trim() : ""; 
		String pw = login.get("pw") != null ? login.get("pw").toString().trim() : ""; 		
		
		try {
			
			if(id.equals("") || pw.equals("")) {
				throw new Exception("잘못된 요청\n아이디 또는 비밀번호가 없습니다.");
			}
			
			Manager manager = managerService.get(id);
			
			HashEncrypt sha256 = new HashEncrypt(pw);
			String encyPw = sha256.getEncryptData(); 
			
			if(manager == null || !manager.getPw().equals(encyPw)) {
				throw new Exception("ID/PW를 확인하시고\n다시 시도하여 주십시오.");
			}
			
			Random generator 	= new Random();   
			int randomMsg		= generator.nextInt(8999) + 1000;	//1000~9999 4자리 난수발생
			String otpCode		= Integer.toString(randomMsg);
			
			KthMsgApi kthMsgApi = new KthMsgApi();
			
			String sendMsg = "[올레 기가 홈IoT] 관리자 본인확인을 위해 인증번호 ["+ otpCode +"]을 입력해 주세요.";
//			System.err.println(sendMsg);
			
			kthMsgApi.setSend_name("kt 홈IoT");				// 발신자 이름
			kthMsgApi.setSend_phone("100");					// 발신자 전화번호  // [필수]
			kthMsgApi.setSend_time("");						// 발신 예약일시 	// 없으면 즉시발송
			kthMsgApi.setDest_name("");						// 수진자 이름
			kthMsgApi.setDest_phone(manager.getMobile());	// 수신자 전화번호	// [필수]
			kthMsgApi.setSubject("인증번호");				// 제목
			kthMsgApi.setMsg_body(sendMsg);					// 메세지 내용

			Map<String, Object> sendResult = (Map<String, Object>)kthMsgApiService.sendSms(kthMsgApi);	//메세지 발송(테스트용으로 조건앞에서 확인함)
//			Map<String, Object> sendResult = new HashMap<String, Object>();
//			sendResult.put("code", 200);
			
			if((int)sendResult.get("code") == 200) {
				manager.setPwTmp(otpCode);
				manager.setPwTmpDate(new Date());
				managerService.modify(manager);
				
				result.put("code", 200);
				result.put("msg", manager.getMobile() + " 로\n인증번호가 발송되었습니다.");
			} else {
				throw new Exception("인증번호 발송이 실패했습니다.\n다시 시도하여 주시기 바랍니다.");
			}
		} catch(Exception e) {
			result.put("code", 500);
			result.put("msg", e.getMessage());
		}
		
		return result;
	}

	@RequestMapping("/login")
	public String loginForm(HttpServletResponse response, Model model) {
		
		String loginId = "";
		String chkLoginID = "";
		
		if(request.getCookies() != null) {
			for(Cookie cookie : request.getCookies()) {
				if(cookie.getName().equals("chkLoginID"))	{
					chkLoginID = cookie.getValue();
				}
				if(cookie.getName().equals("auth"))	{
					Cookie auth = new Cookie("auth", null);
					//auth.setDomain(".cms.home.giga.kt.com");
					auth.setPath("/");
					auth.setHttpOnly(true);
					auth.setMaxAge(0);
					response.addCookie(auth);
				}
				if(cookie.getName().equals("JSESSIONID"))	{
					Cookie JSESSIONID = new Cookie("JSESSIONID", null);
					//auth.setDomain(".cms.home.giga.kt.com");
					JSESSIONID.setPath("/");
					JSESSIONID.setHttpOnly(true);
					JSESSIONID.setMaxAge(0);
					response.addCookie(JSESSIONID);
				}
			}
			
			if(!chkLoginID.equals("")){
				try {
					loginId = getDecryptByAES(chkLoginID);
					Map<String, Object> login = new HashMap<String, Object>();
					login.put("id", loginId);
					model.addAttribute("login", login);
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
			}
		}
		
		return "/login";
	}
	
	@RequestMapping(value="/login", params="method=login")
	public String login(Model model, @RequestParam Map<String, Object> login, HttpServletResponse response) {
		
		String viewName = "";
		int loginFailCount = 10000;
		String securityInfoValue = "";
		String id = login.get("id") != null ? login.get("id").toString().trim() : ""; 
		String pw = login.get("pw") != null ? login.get("pw").toString().trim() : "";		
		String authNum = login.get("authNum") != null ? login.get("authNum").toString().trim() : "";		
		String chkLogin = login.get("chkLogin") != null ? login.get("chkLogin").toString().trim() : "";		
		
		try {
			if(request.getCookies() != null) {
				for(Cookie cookie : request.getCookies()) {
					if(cookie.getName().equals("securityInfo"))	{
						loginFailCount = Integer.parseInt(this.getDecryptByAES(cookie.getValue()));
					}
				}
			}
			
			if(id.equals("") || pw.equals("") || authNum.equals("")) {
				throw new Exception("잘못된 요청입니다.");
			}
			
			if(loginFailCount >= 10003) {
				throw new Exception("ID or PW or 인증번호 불일치 3번으로 인하여\\n3분동안 로그인을 하실수 없습니다.");
			}
			
			Manager manager = managerService.get(id);
			
			HashEncrypt sha256 = new HashEncrypt(pw);
			String encyPw = sha256.getEncryptData(); 
			CookieGenerator securityInfo = new CookieGenerator();
			if(manager == null || !manager.getPw().equals(encyPw)) {
				
				loginFailCount++;
				securityInfoValue = this.getEncryptByAES(Integer.toString(loginFailCount));
				
				securityInfo.setCookiePath("/");
				securityInfo.setCookieMaxAge(60 * 3); //3분
				securityInfo.setCookieName("securityInfo");
				securityInfo.setCookieHttpOnly(true);
				securityInfo.addCookie(response, securityInfoValue);	
				
				throw new Exception("ID/PW를 확인하시고 다시 시도하여 주십시오.\\n3번 불일치시, 3분동안 로그인을 하실수 없습니다.\\n(" + Integer.toString(3-(loginFailCount-10000)) + "번 가능)");
			}
			
			// TODO 인증번호 발급시간 체크
			// 테스트 편의를 위하여 주석처리
			if(!manager.getPwTmp().equals(authNum)) {
				loginFailCount++;
				securityInfoValue = this.getEncryptByAES(Integer.toString(loginFailCount));
				
				securityInfo.setCookiePath("/");
				securityInfo.setCookieMaxAge(60 * 3); //3분
				securityInfo.setCookieName("securityInfo");
				securityInfo.setCookieHttpOnly(true);
				securityInfo.addCookie(response, securityInfoValue);	
				throw new Exception("인증번호를 확인하시고\\n다시 시도하여 주십시오.\\n3번 불일치시, 3분동안 로그인을 하실수 없습니다.\\n(" + Integer.toString(3-(loginFailCount-10000)) + "번 가능)");
				//throw new Exception("인증번호를 확인하시고\\n다시 시도하여 주십시오.");
			}
			
			securityInfoValue = this.getEncryptByAES(Integer.toString(10000));
			
			securityInfo.setCookiePath("/");
			securityInfo.setCookieMaxAge(60 * 3); //3분
			securityInfo.setCookieName("securityInfo");
			securityInfo.setCookieHttpOnly(true);
			securityInfo.addCookie(response, securityInfoValue);	
			
			LoginInfo li = new LoginInfo();
			
			li.setId(manager.getId());
			li.setName(manager.getName());
			li.setMobile(manager.getMobile());
			li.setEmail(manager.getEmail());
			li.setRoleName(manager.getRoleName());
			li.setRemoteAddress(request.getRemoteAddr());
			
			int authExpire = Integer.parseInt(request.getServletContext().getInitParameter("authExpire")) * 60;
			String encryptStr = this.getEncryptByAES(ObjectConverter.toJSONString(li, Include.ALWAYS));
			
			CookieGenerator authGer = new CookieGenerator();
		    authGer.setCookiePath("/");
		    authGer.setCookieMaxAge(authExpire);
		    authGer.setCookieName("auth");
		    authGer.setCookieHttpOnly(true);
		    authGer.addCookie(response, encryptStr);	
		    
		    manager.setLoginDate(new Date());
		    manager.setPwTmp("");
		    
		    managerService.modify(manager);
		    
	    	CookieGenerator chkLoginID = new CookieGenerator();
	    	String loginId = this.getEncryptByAES(manager.getId());
	    	
	    	if(chkLogin.equals("true")) {
				chkLoginID.setCookiePath("/");
		    	chkLoginID.setCookieMaxAge(60 * 60 * 24 * 7); // 7일
		    	chkLoginID.setCookieName("chkLoginID");
		    	chkLoginID.setCookieHttpOnly(true);
		    	//chkLoginID.addCookie(response, manager.getId());
		    	chkLoginID.addCookie(response, loginId);
		    }else{
				chkLoginID.setCookiePath("/");
		    	chkLoginID.setCookieMaxAge(0);
		    	chkLoginID.setCookieName("chkLoginID");
		    	chkLoginID.setCookieHttpOnly(true);
		    	chkLoginID.addCookie(response, null);
		    }
	    	
	    	/*#########################################################*/
	    	
			Date pwChangeDate = new Date();
			
			if(manager.getChangePwDate()==null){
				pwChangeDate = manager.getRegDate();
			}else if(manager.getChangePwDate().toString().trim().equals("") ){
				pwChangeDate = manager.getRegDate();
			}else{
				pwChangeDate = manager.getChangePwDate();
			}
			/*pwChangeDate = manager.getChangePwDate()==null | manager.getChangePwDate().toString().trim().equals("") 
											? manager.getRegDate() 
											: manager.getChangePwDate();*/
											
			SimpleDateFormat ft = new SimpleDateFormat("yyyyMMdd");
	    	String nowDate = ft.format(new Date());
			
			Calendar c = Calendar.getInstance();
			c.setTime(pwChangeDate);
			c.add(Calendar.MONTH, 3);
			
			System.out.println("nowDate = " + nowDate);
			System.out.println("pwChangeDate = " + ft.format(pwChangeDate));
			System.out.println("c.getTime() = " + ft.format(c.getTime()));
			
			if(Integer.parseInt(nowDate) >= Integer.parseInt(ft.format(c.getTime()))){
				model.addAttribute("loginId", id);
				viewName = "redirect:/changePw";
			}else{
				viewName = "redirect:/inquiry/memberStatisticalTable";
			}
			
			//viewName = "redirect:/inquiry/memberStatisticalTable";
			/*#########################################################*/
		} catch (Exception e) {
			viewName = "/login";
			model.addAttribute("login", login);
			model.addAttribute("script", String.format("alert('%s');", e.getMessage()));
			e.printStackTrace();
		}
		
		return viewName;
	}
	
	@RequestMapping("/logout")
	public String logout(HttpServletResponse response)	{
		
		Cookie auth = new Cookie("auth", null);
		//auth.setDomain(".cms.home.giga.kt.com");
		auth.setPath("/");
		auth.setHttpOnly(true);
		auth.setMaxAge(0);
		response.addCookie(auth);		
		
		Cookie JSESSIONID = new Cookie("JSESSIONID", null);
		//auth.setDomain(".cms.home.giga.kt.com");
		JSESSIONID.setPath("/");
		JSESSIONID.setHttpOnly(true);
		JSESSIONID.setMaxAge(0);
		response.addCookie(JSESSIONID);
		
		return "redirect:/login";
	}
	
	@RequestMapping("/preview")
	public String preview(Model model, @RequestParam Map<String, Object> viewInfo, HttpServletResponse response)	{
		
		Map<String, Object> map = new HashMap<>();
		List<Map<String, Object>> imgList = new ArrayList<>();
		
		
		String type = viewInfo.get("type") != null ? viewInfo.get("type").toString().trim() : "";
		map.put("type", type);
		
		if( type.equals("image") )
		{
			
			String imgUrl = viewInfo.get("url") != null ? viewInfo.get("url").toString().trim() : "";
			
			for( int i=1; i<=10; i++  )
			{
				String imgName = viewInfo.get("imgName"+i) != null ? viewInfo.get("imgName"+i).toString().trim() : "";
				String imgPath = viewInfo.get("img"+i) != null ? viewInfo.get("img"+i).toString().trim() : "";
				
				imgName = ""+i;
				if( imgName.length() == 0 || imgPath.length() == 0 )
					break;
				
				Map<String, Object> imgMap = new HashMap<>();

				
				String imgFullUrl = imgUrl + imgPath;
				imgMap.put("name", imgName);
				imgMap.put("path", imgFullUrl);
				imgList.add(imgMap);
					
				if( i == 1 )
					map.put("url", imgFullUrl);

			}
			map.put("imgList", imgList);
		}
		else
		{
			String pkCode = viewInfo.get("pkCode") != null ? viewInfo.get("pkCode").toString().trim() : "";
			String pk = viewInfo.get("pk") != null ? viewInfo.get("pk").toString().trim() : "";
			String cpCode = viewInfo.get("cpCode") != null ? viewInfo.get("cpCode").toString().trim() : "";
			
			String fd_name = codeService.getUrl(pkCode);
			String url = fd_name + "?uid=" + pk + "&cpCode=" + cpCode;
			//url =  "http://www.ohcam.olleh.com/www/webview/faq?uid=10004";
			//url = "http://www.ohcam.olleh.com/www/webview/terms?uid=4";
			//url = "http://www.ohcam.olleh.com/www/webview/noticePopup?uid=2";	
			//url = url.replace("211.42.137.221:8090/www", "localhost:8090");
			
			//System.out.println("url:" + url);
			
			map.put("url", url);
			map.put("size", 1);
		}
		model.addAttribute("preview", map);
		
		return "/preview";
	}
	
	@RequestMapping("/changePw")
	public String changePwForm(Model model, @RequestParam Map<String, Object> login, HttpServletResponse response)	{
		
		String loginId = login.get("loginId") != null ? login.get("loginId").toString().trim() : ""; 
		
		Cookie auth = new Cookie("auth", null);
		//auth.setDomain(".cms.home.giga.kt.com");
		auth.setPath("/");
		auth.setHttpOnly(true);
		auth.setMaxAge(0);
		response.addCookie(auth);		
		
		Cookie JSESSIONID = new Cookie("JSESSIONID", null);
		//auth.setDomain(".cms.home.giga.kt.com");
		JSESSIONID.setPath("/");
		JSESSIONID.setHttpOnly(true);
		JSESSIONID.setMaxAge(0);
		response.addCookie(JSESSIONID);
		
		model.addAttribute("loginId", loginId);
		return "/changePw";
	}
	
	@RequestMapping(value="/changePw", params="method=change")
	public String changePw(Model model, @RequestParam Map<String, Object> login, HttpServletResponse response) {
		
		String id = login.get("id") != null ? login.get("id").toString().trim() : ""; 
		String beforePw = login.get("beforePw") != null ? login.get("beforePw").toString().trim() : "";
		String afterPw = login.get("afterPw") != null ? login.get("afterPw").toString().trim() : "";
		
		try {
			if(id.equals("") || beforePw.equals("") || afterPw.equals("")) {
				throw new Exception("잘못된 요청입니다.");
			}
			
			Manager manager = managerService.get(id);
			
			HashEncrypt sha256 = new HashEncrypt(beforePw);
			String encyPw = sha256.getEncryptData(); 
			
			HashEncrypt sha256After = new HashEncrypt(afterPw);
			String encyPwAfter = sha256After.getEncryptData(); 
			
			if(manager == null || !manager.getPw().equals(encyPw)) {
				throw new Exception("현재 비밀번호를 잘못 입력하였습니다.");
			}
			
			manager.setChangePwDate(new Date());
		    manager.setPw(encyPwAfter);
		    managerService.modify(manager);
		    
		    model.addAttribute("script", String.format("alert('%s'); location.href = '/login'; ", "변경된 비밀번호로 로그인 해주세요."));
			
		} catch (Exception e) {
			model.addAttribute("loginId", id);
			model.addAttribute("script", String.format("alert('%s');", e.getMessage()));
			e.printStackTrace();
		}
		
		return "/changePw";
	}
}
