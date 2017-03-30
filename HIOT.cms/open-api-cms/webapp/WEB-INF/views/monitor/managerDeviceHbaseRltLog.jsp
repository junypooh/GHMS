<%@ page contentType="text/html" trimDirectiveWhitespaces="true" pageEncoding="UTF-8"%>
<%@ taglib prefix="layout" 	tagdir="/WEB-INF/tags/layout" %>
<%@ taglib prefix="c"  		uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" 	uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" 		uri="http://java.sun.com/jsp/jstl/functions"%>

<c:if test="${!empty signalResultList}">
	<c:forEach var="signalResult" items="${signalResultList}" varStatus="status" >
	<tr onmouseover="this.style.backgroundColor='#f7f7f7'" onmouseout="this.style.backgroundColor=''" bgcolor="white">
		<td>${signalResult.devType}</td>
		<td>${signalResult.devNm}</td>
		<td>${signalResult.cretDt}</td>
		<td>${signalResult.devConStat}</td>
		<td>${signalResult.lastRecvDate}</td>
		<td>${signalResult.deviceStatus}</td>
	</tr>
	</c:forEach>
</c:if>
<c:if test="${moreBtn == true}">
	<tr class="moreBtn" onmouseover="this.style.backgroundColor='#f7f7f7'" onmouseout="this.style.backgroundColor=''" bgcolor="white">
		<td colspan="6" align="center">
			<a onclick="javascript:MoreLog(); $('.moreBtn').css('display','none');" class="btn2 btn2_red30" style="cursor:pointer;"><span>더보기</span></a>
		</td>
	</tr>
</c:if>

<c:if test="${empty signalResultList}">
	<tr> 	
		<td align="center" colspan="6">
			검색된 데이타가 없습니다.
		</td>
	</tr>
</c:if>