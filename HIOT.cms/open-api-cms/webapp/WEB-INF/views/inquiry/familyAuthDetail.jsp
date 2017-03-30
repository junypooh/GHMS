<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"  		uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" 	uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" 		uri="http://java.sun.com/jsp/jstl/functions"%>

<!doctype html>
<html lang="ko">
	<head>
		<meta charset="UTF-8">
		<title>가족사용자 권한 정보 조회</title>
		<meta http-equiv="X-UA-Compatible" content="IE=edge" />
		<link rel="stylesheet" href="/resources/css/cms.css" />
		<script type="text/javascript" src="/resources/js/jquery-1.9.1.min.js"></script>
		<script type="text/javascript" src="/resources/js/jquery-ui-1.9.2.custom.js"></script>
		<script type="text/javascript" src="/resources/js/commonLibrary.js"></script>
		<script type="text/javascript" src="/resources/js/validate.js"></script>   
		<script type="text/javascript">
			$(document).ready(function(){
			});
		</script>	
			
	</head>
	<body>
		<div style="padding:20px;">
			<table class="tbl_1">
				<caption>가족사용자 권한 정보 조회</caption>
				<colgroup>
					<col style="width:20%" />
					<col style="width:25%" />
					<col style="width:18%" />
					<col style="width:12%" />
					<col style="width:25%" />
				</colgroup>
				<tbody>
				<!-- DYNAMIC AREA 'list' -->
				
					<tr>
						<th scope="col">가족사용자ID</th>
						<td>${mbrInfo.etsMbrId}</td>
						<th scope="col" colspan="2">가족사용자 휴대폰번호</th>
						<td>${mbrInfo.etsTelNo}</td>
					</tr>
					<tr>
						<th scope="col">마스터 ID</th>
						<td>${mbrInfo.mbrId}</td>
						<th scope="col" colspan="2">마스터 휴대폰</th>
						<td>${mbrInfo.telNo}</td>
					</tr>
					<tr>
						<th scope="col">승인일시</th>
						<td colspan="4">${mbrInfo.cretDt}</td>
					</tr>
					
					<tr>
						<th scope="col" rowspan="3">상품명</th>
						<td rowspan="3">홈매니저 허브</td>
						<th scope="col" rowspan="3">보유 IoT 기기</th>
						<td>도어락</td>
						<td>
							<table class="tbl_1">
							<caption></caption>
							<tbody>
							<c:forEach var="doorInfo" items="${doorInfo}" varStatus="status" >
							<tr><td>${doorInfo.devNm}</td></tr>
							</c:forEach>
							</tbody>
							</table>
						</td>
					</tr>
					<tr>
						<td>가스밸브</td>
						<td>
							<table class="tbl_1">
							<caption></caption>
							<tbody>
							<c:forEach var="gasInfo" items="${gasInfo}" varStatus="status" >
							<tr><td>${gasInfo.devNm}</td></tr>
							</c:forEach>
							</tbody>
							</table>
						</td>
					</tr>
					<tr>
						<td>침임감지</td>
						<td>
							<table class="tbl_1">
							<caption></caption>
							<tbody>
							<c:forEach var="trespsInfo" items="${trespsInfo}" varStatus="status" >
							<tr><td>${trespsInfo.devNm}</td></tr>
							</c:forEach>
							</tbody>
							</table>
						</td>
					</tr>
					<c:if test="${!empty apInfo}">
					<tr>
						<th scope="col">상품명</th>
						<td>GiGA WiFi Home</td>
						<th scope="col" colspan="2">사용</th>
						<td>O</td>
					</tr>
					</c:if>
					<c:if test="${empty apInfo}">
					<tr>
						<th scope="col">상품명</th>
						<td>GiGA WiFi Home</td>
						<th scope="col" colspan="2">사용</th>
						<td>X</td>
					</tr>
					</c:if>
				
				<!-- DYNAMIC AREA 'list' -->
				</tbody>
			</table>
			
			<div class="btns clearfix">
				<div class="fll" style=" padding-left:535px;">
					<a id="btnList" class="btn2 btn2_black30" style="cursor:pointer" onclick="javascript:window.close();"><span>닫기</span></a>
				</div>
			</div>	
		</div>
	</body>
</html>