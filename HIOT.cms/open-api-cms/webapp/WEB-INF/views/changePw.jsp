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
	<script type="text/javascript" src="/resources/js/plugins/security/passwordStrengthMeter.js"></script>
	<script type="text/javascript">
		${script}
		
		$(document).ready(function() {
			$("#beforePw").focus();
			
			$('#afterPw').keyup(function(){
				var id = "${loginId}";
				if(checkUID(this)){
					$('#passwordStrengthResult').html(passwordStrength($('#afterPw').val(), id));
				}else{
					$('#passwordStrengthResult').html("");
				}
			});
			
			$("#btnModify").click(function() {
				var frm = document.pwForm;
				if(validate(frm)){
					
					if($("#afterPw").val().ispattern("number")){
						alert("숫자로만 비밀번호를 구성 할수없습니다.");
						return false;
					}
					
					if($("#afterPw").val() != $("#afterChkPw").val()){
						alert("변경할 비밀번호와 재입력된 비밀번호가 일치하지 않습니다.");
						return false;
					}
					
					var score = $('#passwordStrengthResult').text();
					if(score == "높음" || score == "보통"){
						frm.action="/changePw";
						frm.submit();
					}
					else{
						alert('비밀번호 보안등급이 낮습니다.');
					}
				}
			});
			
			$("#btnCancel").click(function() {
				if(confirm("로그인 페이지로 이동합니다. 이동하시겠습니까?")){
					location.href = "/login";
				}
			});
		});
		
		function checkUID(objval){
			// 특수 문자 모음 
			// "{}[]()<>?|`~'!@#$%^&*-+=,.;:\"'\\/ "
			var objEv = $(objval);
			var num = "{}[]<>?|`~'-+=,.;:\"'\\/ ";
			var bFlag = true;
			for (var i = 0;i < objEv.val().length;i++)
			{
				if(num.indexOf(objEv.val().charAt(i)) != -1){
					bFlag = false;
				}
			}
			
			if(!bFlag || objEv.val().length==0)
			{
				objEv.val("");
			}
			return bFlag;
		}
	</script>
</head>

<body>

<div id="loginWrap">
	<div class="loginCont">
		<div class="loginContInner">
			<div><span class="pageTitle">비밀번호 변경</span></div>
			<form name="pwForm" method="POST" action="/changePw">
			<input type="hidden" name="method" value="change" />
			<input type="hidden" name="id" value="${loginId}" />
			<fieldset>
				<legend>비밀번호 변경</legend>
				<div class="loginBox">
					<p>
						- 비밀번호 사용기간이 만료가 되었습니다.
						<br/>
						- 계정의 보안을 위해 비밀번호를 변경해 주세요.
						<br/>
						<b style="color:red;">- 영문자(대/소), 숫자, 특수문자의 8자리 이상 조합</b>
						<br/>
						<b>- 사용기간 : 변경일로부터 3개월</b>
					</p>
					<p class="pwBox">
						<b>현재 비밀번호</b> 
						<input type="password" id="beforePw" name="beforePw"  require="true" msg="현재 패스워드를 입력하셔야 합니다."/>
					</p>
					<p class="pwBox">
						<b>변경할 비밀번호</b>
						<span class="pass-grade" style="text-align:right; padding-left:25px;">보안등급 </span><span id="passwordStrengthResult" style="color:red;"></span> 
						<input type="password" id="afterPw" name="afterPw"  require="true" msg="변경할 비밀번호를 입력하셔야 합니다."/>
					</p>
					<p class="pwBox">
						<b>변경할 비밀번호 재입력</b>
						<input type="password" id="afterChkPw" name="afterChkPw" require="true" msg="변경할 비밀번호를 입력하셔야 합니다."/>
					</p>
					<p class="btnBox">
						<a id="btnModify" class="btn2 btn2_black50" style="cursor:pointer"><span>변경</span></a>
						<a id="btnCancel" class="btn2 btn2_red30" style="cursor:pointer"><span>취소</span></a>
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