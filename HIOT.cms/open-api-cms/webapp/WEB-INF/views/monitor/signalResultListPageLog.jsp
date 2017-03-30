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
		<caption>모니터링 조회 결과를 확인 할수 있는 표</caption>
		<colgroup>
			<col style="width:5%" />
			<col style="width:10%" />
			<col style="width:10%" />
			<col style="width:10%" />
			<col style="width:*%" />
		</colgroup>
		<thead>
			<tr>
				<th scope="col">No.</th>
				<th scope="col">수신일시</th>
				<th scope="col">로그타입</th>
				<th scope="col">코드값</th>
				<th scope="col">로그값</th>
			</tr>
		</thead>
		
		<tbody id="noticeListContainer">
		
		<!-- DYNAMIC AREA 'list' -->
		<c:if test="${!empty signalResultList}">
			<c:forEach var="signalResult" items="${signalResultList}" varStatus="status" >
			<tr onmouseover="this.style.backgroundColor='#f7f7f7'" onmouseout="this.style.backgroundColor=''" bgcolor="white">
				<td><fmt:formatNumber value="${pageNavi.totalCount - ((pageNavi.nowPage - 1) * pageNavi.pageSize + status.index) }" pattern="#,###"/>
				<td>${signalResult.rcvDt}</td>
				<td>${signalResult.logType}</td>					
				<td>${signalResult.logCode}</td>
				<td style="text-align:left; word-break:break-all;">${signalResult.attrs}</td>
			</tr>
			</c:forEach>
		</c:if>			
	
		<c:if test="${empty signalResultList}">
			<tr> 	
				<td align="center" colspan="5">검색된 데이타가 없습니다</td>
			</tr>
		</c:if>
		</tbody>
	</table>
	
	${pageNavi.navi}
</body>
</html>