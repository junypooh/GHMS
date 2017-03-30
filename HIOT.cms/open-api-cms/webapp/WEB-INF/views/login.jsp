<%@ page contentType="text/html" trimDirectiveWhitespaces="true" pageEncoding="UTF-8"%>
<%@ taglib prefix="layout" 	tagdir="/WEB-INF/tags/layout" %>
<%@ taglib prefix="c"  		uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" 	uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" 		uri="http://java.sun.com/jsp/jstl/functions"%>
<!doctype html>
<html lang="ko">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title> 올레 GiGA 홈 IoT 관리자모드</title>
	<meta http-equiv="X-UA-Compatible" content="IE=edge" />
	<link rel="stylesheet" href="/resources/css/cms.css" />
	<script type="text/javascript" src="/resources/js/jquery-1.9.1.min.js"></script>
	<script type="text/javascript" src="/resources/js/commonLibrary.js"></script>
	<script type="text/javascript" src="/resources/js/validate.js"></script>   
	<script type="text/javascript">
		${script}
	
		$(document).ready(function() {
			
			$("#id").focus();
			
			// (한글입력 제한)
			$('.hangulLimit').css('imeMode','disabled')
			.keyup(function() {
				console.log($(this).val());
			    if( $(this).val() != null && $(this).val() != '' ) {
			    	$(this).val( $(this).val().replace( /[ㄱ-ㅎㅏ-ㅣ가-힣]/g, '' ) );
			    }
			});
			
			$("label + input[type=text], label + input[type=password]").keyup(function(){
				if($(this).val()!=""){
					$(this).prev().hide();
				}else{
					$(this).prev().show();
				}
			})
			.prev().click(function(){
				$(this).next().focus();
			});			
			
			$("#btnAuthSMS").click(function() {
				var id = $("#id").val().trim();
				var pw = $("#pw").val().trim();
				
				if(id.length == 0) {
					alert("아이디를 입력하셔야 합니다.");
					$("#id").focus();
					return false;
				}
				
				if(pw.length == 0) {
					alert("패스워드를 입력하셔야 합니다.");
					$("#pw").focus();
					return false;					
				}
				
				authSMS(id, pw);
			});
			
			$("#btnLogin").click(function() {
				var form = document.forms["loginForm"];
				
				if(validate(form)) {
					login();
				}
			});
            
            if($("#id").val()){
            	$("#chkLogin").prop( "checked", true ); //	chkLogin
            }
            
            
            $("#authNum").on(function() {
				var form = document.forms["loginForm"];
				
				if(validate(form)) {
					login();
				}
			});
            
		});
		
		function authSMS(id, pw) {
			$.ajax({
				type : "POST",
			    cache: false,						
				url : "/authSMS",
				dataType : "json",
				data : { "id" : id, "pw" : pw}
			})
			.done(function(result){
				if(result.code == 200) {
					alert(result.msg);
					$("#authSMSGuide").text("재발송");
				} else {
					alert(result.msg);
				}
			})
			.fail(function( jqXHR, textStatus, errorThrown){
				console.log(jqXHR);
			});				
		}

		function login() {
			var form = document.forms["loginForm"];
			if(validate(form)) {
				form.submit();
			}				
		}
		
	</script>
</head>

<body>

<div id="loginWrap">
	<div class="loginCont">
		<div class="loginContInner">
			<h1><img src="/resources/images/logo.gif" alt="올레 GiGA 홈 IoT 관리자모드" /></h1>
			<form name="loginForm" method="POST" action="/login" autocomplete="off">
			<input type="hidden" name="method" value="login" />
			<fieldset>
				<legend>로그인 정보 입력</legend>
				<div class="loginBox">
					<p class="idBox">
						<input type="text" id="id" name="id" value="${login.id }" placeholder="ID" class="hangulLimit" require="true" msg="아이디를 입력하셔야 합니다." />
					</p>
					<p class="pwBox">
						<input type="password" id="pw" name="pw" placeholder="PW" autocomplete="off" require="true" msg="패스워드를 입력하셔야 합니다."/>
					</p>
					<p class="certification">
						<input type="text" id="authNum" name="authNum" value="${login.authNum }" placeholder="인증번호" class="hangulLimit" require="true" msg="인증번호를 입력하셔야 합니다." onkeydown="javascript: if (event.keyCode == 13) {login();}"/>
						<button type="button" id="btnAuthSMS" class="btn"><span id="authSMSGuide">인증번호 받기</span></button>
					</p>
					<p class="chkLogin">
						<input type="checkbox" id="chkLogin" name="chkLogin" value="true" /><label for="chkLogin">ID 저장</label>
					</p>
					<p class="btnBox">
						<a id="btnLogin" class="btnLogin" style="cursor:pointer;">Login</a>
					</p>
				</div>
			</fieldset>
			</form>
		</div>
	</div>
	<div id="footer">
		<div id="footerInner">COPYRIGHT@2014 KT CORP. ALL RIGHTS RESERVED.</div>
	</div>
</div>

</body>
</html>