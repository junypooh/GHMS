<%@ tag language="java" description="" trimDirectiveWhitespaces="true" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"		uri="http://java.sun.com/jsp/jstl/core" %>
<%@ attribute name="stylesheet" fragment="true" %>
<%@ attribute name="javascript" fragment="true" %>
<%
 	if(request.getCookies() != null) {
		for(Cookie c : request.getCookies()) {
			if(c.getName().equals("auth")) {
				String encryptAuth = c.getValue();
				int authExpire = Integer.parseInt(request.getServletContext().getInitParameter("authExpire")) * 60;
				
				Cookie cookie = new Cookie("auth", encryptAuth);
				//cookie.setDomain(".cms.home.giga.kt.com");
				cookie.setHttpOnly(true);
				cookie.setMaxAge(authExpire);
				cookie.setPath("/");
				response.addCookie(cookie);
			}
		}
	} 
%>
<!doctype html>
<html lang="ko">
<head>
	<meta charset="UTF-8">
	<title> 올레 GiGA 홈 IoT 관리자모드</title>
	<meta http-equiv="X-UA-Compatible" content="IE=edge" />
	<link rel="stylesheet" href="/resources/css/cms.css" />
	<link rel="stylesheet" href="/resources/css/plugins/dark-hive/jquery-ui-1.9.2.custom.css" />
	<link rel="stylesheet" href="/resources/css/plugins/datepicker.css" />
	<jsp:invoke fragment="stylesheet"/>
	<script type="text/javascript" src="/resources/js/jquery-1.9.1.min.js"></script>
	<script type="text/javascript" src="/resources/js/jquery-ui-1.9.2.custom.js"></script>
	<script type="text/javascript" src="/resources/js/plugins/ui/jquery.ui.datepicker.js"></script>
	<script type="text/javascript" src="/resources/js/plugins/ui/i18n/jquery.ui.datepicker-ko.js"></script>
	<script type="text/javascript" src="/resources/js/commonLibrary.js"></script>
	<script type="text/javascript" src="/resources/js/validate.js"></script>   
	<script type="text/javascript" src="/resources/js/jquery.form.min.js"></script>
	<script type="text/javascript">
	
		var aclMainMenu = "${aclMainMenu}";
		var aclSubMenu = "${aclSubMenu}";
	
		$(document).ready(function(){
			
			// (숫자만 가능)
			$('.numOnly').css('imeMode','disabled')
			.keypress(function(event) {
			    /* if(event.which && (event.which < 48 || event.which > 57) && event.which != 8) {
			        event.preventDefault();
	    		} */
	    		var charASCII = event.which || event.keyCode; 
	    		if(charASCII && (charASCII < 48 || charASCII > 57) && charASCII != 8) {
			    	event.preventDefault();
	    		}
			})
			.keyup(function() {
			    if( $(this).val() != null && $(this).val() != '' ) {
			        $(this).val( $(this).val().replace(/[^0-9]/g, '') );
			    }
			});	
			
			// (한글입력 제한)
			$('.hangulLimit').css('imeMode','disabled')
			.keyup(function() {
				console.log($(this).val());
			    if( $(this).val() != null && $(this).val() != '' ) {
			    	$(this).val( $(this).val().replace( /[ㄱ-ㅎㅏ-ㅣ가-힣]/g, '' ) );
			    }
			});
			
			$("#main_menu_" + aclMainMenu).addClass("active");
			$("#sub_menu_" + aclSubMenu).addClass("active");
			
			$("#gnb a, #snb a").each(function(index){
				if($(this).attr("href") == "") {
					$(this).attr("href", "javascript:alert('준비중 입니다.');");
				}
			});
		});
		
		function ktsam() {
			window.open("https://ktsam.kt.com/controller/login/login_vv.php");
		}
	</script>		
	<jsp:invoke fragment="javascript"/>
</head>
<body>

<div id="wrap">
	<div id="header">
		<div id="headerInner">

			<div class="headerTop clearfix">
				<div class="fll">
					<h1><a href="/inquiry/memberStatisticalTable"><img src="/resources/images/logo.gif" alt="올레 GiGA 홈 IoT 관리자모드" /></a></h1>
					<a href="https://ktsam.kt.com/controller/login/login_vv.php" target="_blank" class="btnGoSite"><strong>kt SAM</strong> 사이트 이동</a>
				</div>
				<div class="utils">
					<p class="userinfo">
						<strong>[${loginInfo.roleName}] ${loginInfo.name } 님</strong>
						<c:if test="${!empty loginInfo.hostName}">
						<br>접속서버 : ${loginInfo.hostName}
						</c:if>
					</p>
					<p>
						<a href="/logout" class="btnLogout">Logout</a>
					</p>
				</div>
			</div>

			<ul id="gnb" class="clearfix">
				<c:forEach var="menu" items="${aclMainMenuList }">
				<li id="main_menu_${menu.menu }"><a href="${menu.url }">${menu.name}</a></li>
				</c:forEach>
			</ul>
		</div>
	</div>

	<div id="container" class="clearfix">
		<div id="snb">
			<ul>
				<c:forEach var="menu" items="${aclSubMenuList }">
				<li id="sub_menu_${menu.menu }"><a href="${menu.url }">${menu.name}</a></li>
				</c:forEach>
			</ul>
		</div>

		<div id="contents" style="height:100%;">
			<jsp:doBody />
		</div>
	</div>	

	<div id="footer">
		<div id="footerInner">COPYRIGHT@2015 KT CORP. ALL RIGHTS RESERVED.</div>
	</div>
	<div id="mask">dimmed</div> <!-- masking -->
	
</div>

</body>
</html>
