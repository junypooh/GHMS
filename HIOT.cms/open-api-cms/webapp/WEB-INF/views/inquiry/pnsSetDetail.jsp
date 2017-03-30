<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"  		uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" 	uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" 		uri="http://java.sun.com/jsp/jstl/functions"%>

<!doctype html>
<html lang="ko">
	<head>
		<meta charset="UTF-8">
		<title>PNS 발송 이력 조회 상세</title>
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
				<caption>PNS 발송 설정 조회 상세</caption>
				<colgroup>
					<col style="width:25%" />
					<col style="width:25%" />
					<col style="width:25%" />
					<col style="width:25%" />
				</colgroup>
				<tbody>
				<!-- DYNAMIC AREA 'list' -->
				
					<tr>
						<th scope="col">고객ID</th>
						<td>${pnsSetList.mbrId}</td>
						<th scope="col">상품명</th>
						<td>${pnsSetList.unitSvcNm}</td>
					</tr>
					<tr>
						<th scope="col">홈 카메라ID</th>
						<td>${pnsSetList.connTermlId}</td>
						<th scope="col">휴대폰</th>
						<td>${pnsSetList.telNo}</td>
					</tr>
					<tr>
						<th scope="col">설정값</th>
						<td colspan="3">${pnsSetList.setupVal}</td>
					</tr>
					<tr>
						<th scope="col">설정 구분</th>
						<td colspan="3">${pnsSetList.statEvetNm}</td>
					</tr>
				
				<!-- DYNAMIC AREA 'list' -->
				</tbody>
			</table>
			
			<div style="text-align:left; font-weight:bold; font-size:15px; padding-top:25px;"><span>▶ 변경 이력</span></div>
			
			<table class="board mt10">
			<caption>PNS 발송 이력 조회 고객ID, 휴대폰번호, 상품명, 푸시, 발소일시, 발송시간, 발송여부를 확인 할수 있는 표</caption>
			<colgroup>
				<col style="width:5%" />
				<col style="width:*%" />
				<col style="width:15%" />
			</colgroup>
			<thead>
				<tr>
					<th scope="col">NO</th>
					<th scope="col">푸시설정</th>
					<th scope="col">설정값</th>
				</tr>
			</thead>
			
			<tbody>
			<!-- DYNAMIC AREA 'list' -->
			<c:if test="${!empty pnsSetDetailList}">
				<c:forEach var="pnsSetDetailList" items="${pnsSetDetailList}" varStatus="status" >
				<tr onmouseover="this.style.backgroundColor='#f7f7f7'" onmouseout="this.style.backgroundColor=''" bgcolor="white">
					<td><fmt:formatNumber value="${pageNavi.totalCount - ((pageNavi.nowPage - 1) * pageNavi.pageSize + status.index) }" pattern="#,###"/></td>
					<td>${pnsSetDetailList.statEvetNm}</td>
					<td>${pnsSetDetailList.setupVal}</td>
				</tr>
				</c:forEach>
			</c:if>			
	
			<c:if test="${empty pnsSetDetailList}">
				<tr> 	
					<td align="center" colspan="3">검색된 데이타가 없습니다</td>
				</tr>
			</c:if>
			<!-- DYNAMIC AREA 'list' -->
			</tbody>
		</table>
		
		${pageNavi.navi}
			
		<div class="btns clearfix">
			<div class="fll" style=" padding-left:635px;">
				<a id="btnList" class="btn2 btn2_black30" style="cursor:pointer;" onclick="javascript:window.close();"><span>닫기</span></a>
			</div>
		</div>	
		</div>
	</body>
</html>