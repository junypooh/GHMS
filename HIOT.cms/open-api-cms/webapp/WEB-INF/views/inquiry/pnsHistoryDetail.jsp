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
				<caption>PNS 발송 이력 조회 상세</caption>
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
						<td>${pnsHistoryDetail.mbrId}</td>
						<th scope="col">상품명</th>
						<td>${pnsHistoryDetail.unitSvcNm}</td>
					</tr>
					<tr>
						<c:choose>
						<c:when test="${pnsHistoryDetail.unitSvcCd == '003'}"><th scope="col">홈 IoT장치명</th></c:when>
						<c:otherwise><th scope="col">홈 카메라ID</th></c:otherwise>
						</c:choose>
						<td>${pnsHistoryDetail.devNm}</td>
						<th scope="col">휴대폰</th>
						<td>${pnsHistoryDetail.telNo}</td>
					</tr>
					<tr>
						<th scope="col">발송일</th>
						<td>${pnsHistoryDetail.msgTrmDt1}</td>
						<th scope="col">발송시간</th>
						<td>${pnsHistoryDetail.msgTrmDt2}</td>
					</tr>
					<tr>
						<th scope="col">발송여부</th>
						<td colspan="3">
						<c:if test="${pnsHistoryDetail.msgTtrmForwardYn == 'Y'}">성공</c:if>
						<c:if test="${pnsHistoryDetail.msgTtrmForwardYn == 'N'}">실패</c:if>
						</td>
					</tr>
					<tr>
						<th scope="col">발송내용</th>
						
						<td colspan="3">
						${pnsHistoryDetail.afterMsgTrmSbst}
						</td>
					</tr>
					
					<tr>
						<th scope="col">수신일</th>
						<td>${pnsHistoryDetail.msgRcvDt1}</td>
						<th scope="col">수신시간</th>
						<td>${pnsHistoryDetail.msgRcvDt2}</td>
					</tr>
					<tr>
						<th scope="col">수신여부</th>
						<td colspan="3">
						<c:if test="${pnsHistoryDetail.msgRcvYn == 'Y'}">성공</c:if>
						<c:if test="${pnsHistoryDetail.msgRcvYn == 'N'}">실패</c:if>
						</td>
					</tr>
					<tr>
						<th scope="col">수신내용</th>
						
						<td colspan="3">
						${pnsHistoryDetail.msgRcvFailTxn }
						</td>
					</tr>
				
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