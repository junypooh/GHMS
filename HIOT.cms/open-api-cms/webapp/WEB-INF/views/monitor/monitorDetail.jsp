<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"  		uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" 	uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" 		uri="http://java.sun.com/jsp/jstl/functions"%>

<!doctype html>
<html lang="ko">
	<head>
		<meta charset="UTF-8">
		<title>상세 내역</title>
		<meta http-equiv="X-UA-Compatible" content="IE=edge" />
		<link rel="stylesheet" href="/resources/css/cms.css" />
		<script type="text/javascript" src="/resources/js/jquery-1.9.1.min.js"></script>
		<script type="text/javascript" src="/resources/js/jquery-ui-1.9.2.custom.js"></script>
		<script type="text/javascript" src="/resources/js/commonLibrary.js"></script>
		<script type="text/javascript" src="/resources/js/validate.js"></script>   
		<script type="text/javascript">
			window.onload = function(){
				window.document.body.scroll = "auto";
			}
			$(document).ready(function(){
			});
		</script>	
			
	</head>
	<body>
		<div style="padding:20px;">
			<table class="board mt10">
				<caption>모니터링 상세 내역</caption>
				<colgroup>
					<col style="width:10%" />
					<col style="width:10%" />
					<col style="width:15%" />
					<col style="width:*%" />
					<col style="width:15%" />
					<col style="width:5%" />
					<col style="width:15%" />
				</colgroup>
				<tbody>
				<thead>
					<tr>
						<th scope="col">From</th>
						<th scope="col">To</th>
						<th scope="col">요청내용</th>
						<th scope="col">요청</th>
						<th scope="col">응답</th>
						<th scope="col">응답결과</th>
						<th scope="col">상태매세지</th>
					</tr>
				</thead>
				<!-- DYNAMIC AREA 'list' -->
				<tr>
					<td>더미데이터</td>
					<td>더미데이터</td>
					<td>더미데이터</td>
					<td>더미데이터</td>
					<td>더미데이터</td>
					<td>더미데이터</td>
					<td>더미데이터</td>
				</tr>
				<tr>
					<td>010-1111-****</td>
					<td>Open API</td>
					<td>사용자 인증</td>
					<td>2015.01.21 20:05:18:02</td>
					<td>2015.01.21 20:05:18:02</td>
					<td>성공</td>
					<td></td>
				</tr>
				<tr>
					<td>010-1111-****</td>
					<td>Open API</td>
					<td>사용자 인증</td>
					<td>2015.01.21 20:05:18:02</td>
					<td>2015.01.21 20:05:18:02</td>
					<td>성공</td>
					<td>유효하지 않은 비밀번호</td>
				</tr>
				<tr>
					<td>010-1111-****</td>
					<td>Open API</td>
					<td>사용자 인증</td>
					<td>2015.01.21 20:05:18:02</td>
					<td>2015.01.21 20:05:18:02</td>
					<td>성공</td>
					<td>유효하지 않은 비밀번호</td>
				</tr>
				<tr>
					<td>010-1111-****</td>
					<td>Open API</td>
					<td>사용자 인증</td>
					<td>2015.01.21 20:05:18:02</td>
					<td>2015.01.21 20:05:18:02</td>
					<td>성공</td>
					<td></td>
				</tr>
				<tr>
					<td>010-1111-****</td>
					<td>Open API</td>
					<td>사용자 인증</td>
					<td>2015.01.21 20:05:18:02</td>
					<td>2015.01.21 20:05:18:02</td>
					<td>성공</td>
					<td>유효하지 않은 비밀번호</td>
				</tr>
				<tr>
					<td>010-1111-****</td>
					<td>Open API</td>
					<td>사용자 인증</td>
					<td>2015.01.21 20:05:18:02</td>
					<td>2015.01.21 20:05:18:02</td>
					<td>성공</td>
					<td>유효하지 않은 비밀번호</td>
				</tr>
				<tr>
					<td>010-1111-****</td>
					<td>Open API</td>
					<td>사용자 인증</td>
					<td>2015.01.21 20:05:18:02</td>
					<td>2015.01.21 20:05:18:02</td>
					<td>성공</td>
					<td></td>
				</tr>
				<tr>
					<td>010-1111-****</td>
					<td>Open API</td>
					<td>사용자 인증</td>
					<td>2015.01.21 20:05:18:02</td>
					<td>2015.01.21 20:05:18:02</td>
					<td>성공</td>
					<td></td>
				</tr>
				<!-- DYNAMIC AREA 'list' -->
				</tbody>
			</table>
			
		<div class="btns clearfix">
			<div class="fll" style=" padding-left:635px;">
				<a id="btnList" class="btn2 btn2_black30" style="cursor:pointer;" onclick="javascript:window.close();"><span>닫기</span></a>
			</div>
		</div>	
		</div>
	</body>
</html>