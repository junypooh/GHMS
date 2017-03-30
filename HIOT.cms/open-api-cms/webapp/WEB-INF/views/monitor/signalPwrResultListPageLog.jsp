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
			
		});
	</script>
</head>

<body>

	<table class="board mt10">
			<caption>모니터링 조회 결과의 제목, 게시시간, 작성자, 노출여부를 확인 할수 있는 표</caption>
			<colgroup>
				<col style="width:10%" />
				<col style="width:13%" />
				<col style="width:14%" />
				<col style="width:10%" />
				<col style="width:10%" />
				<col style="width:10%" />
				<col style="width:10%" />
				<col style="width:10%" />
				<col style="width:13%" />
			</colgroup>
			<thead>
				<tr>
					<th scope="col" rowspan="2" style="BORDER-right: #B7B7B7 1px solid;">No.</th>
					<th scope="col" rowspan="2" style="BORDER-right: #B7B7B7 1px solid;">수신일시</th>
					<th scope="col" colspan="2" style="BORDER-right: #B7B7B7 1px solid;">연결 WiFi</th>
					<th scope="col" colspan="2" style="BORDER-right: #B7B7B7 1px solid;">인접채널</th>
					<th scope="col" colspan="2" style="BORDER-right: #B7B7B7 1px solid;">동일채널</th>
					<th scope="col" rowspan="2">발생일시</th>
				</tr>
				<tr>
					<th scope="col" style="BORDER-right: #B7B7B7 1px solid;">SSID</th>
					<th scope="col" style="BORDER-right: #B7B7B7 1px solid;">신호세기</th>
					<th scope="col" style="BORDER-right: #B7B7B7 1px solid;">채널수</th>
					<th scope="col" style="BORDER-right: #B7B7B7 1px solid;">신호세기</th>
					<th scope="col" style="BORDER-right: #B7B7B7 1px solid;">채널수</th>
					<th scope="col" style="BORDER-right: #B7B7B7 1px solid;">신호세기</th>
				</tr>
			</thead>
			
			<tbody id="noticeListContainer">
			
			<!-- DYNAMIC AREA 'list' -->
			<c:if test="${!empty signalResultList}">
				<c:forEach var="signalResult" items="${signalResultList}" varStatus="status" >
				<tr data-pk="${notice.pk}" onmouseover="this.style.backgroundColor='#f7f7f7'" onmouseout="this.style.backgroundColor=''" bgcolor="white">
					<td><fmt:formatNumber value="${status.index +1}" pattern="#,###"/></td>
					<td>${signalResult.rcvDt}</td>
					<td>${signalResult.CurrentSSID}</td>
					<td>${signalResult.CurrentRSSI}</td>
					<td>${signalResult.AdjacentNum}</td>
					<td>${signalResult.AdjacentRSSI}</td>
					<td>${signalResult.CoChannelNum}</td>
					<td>${signalResult.CoChannelRSSI}</td>
					<td>${signalResult.eventDt}</td>
				</tr>
				<tr onmouseover="this.style.backgroundColor='#f7f7f7'" onmouseout="this.style.backgroundColor=''" bgcolor="white">
					<td colspan="11" style="display:none; text-align:left" id="unitLog_${status.index +1}">${signalResult.attrs}</td>
				</tr>
				</c:forEach>
			</c:if>			
	
			<c:if test="${empty signalResultList}">
				<tr> 	
					<td align="center" colspan="11">검색된 데이타가 없습니다</td>
				</tr>
			</c:if>
			</tbody>
		</table>
	
	${pageNavi.navi}
</body>
</html>