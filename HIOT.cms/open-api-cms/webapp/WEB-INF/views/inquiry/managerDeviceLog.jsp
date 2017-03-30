<%@ page contentType="text/html" trimDirectiveWhitespaces="true" pageEncoding="UTF-8"%>
<%@ taglib prefix="layout" 	tagdir="/WEB-INF/tags/layout" %>
<%@ taglib prefix="c"  		uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" 	uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" 		uri="http://java.sun.com/jsp/jstl/functions"%>

<c:if test="${!empty list}">
	<c:forEach var="result" items="${list}" varStatus="status" >
	<tr onmouseover="this.style.backgroundColor='#f7f7f7'" onmouseout="this.style.backgroundColor=''" bgcolor="white">
		<td>${result.occDt}</td>
		<td>${result.mbrId}</td>
		<td>${result.spotDevGwNm}</td>
		<td>${result.deviceType}</td>
		<td>${result.spotDevNm}</td>
		<td>${result.logType}</td>
		<td>${result.contlDesc}</td>
	</tr>
	</c:forEach>
</c:if>

<c:if test="${empty list}">
	<tr> 	
		<td align="center" colspan="7">
			검색된 데이타가 없습니다.
		</td>
	</tr>
</c:if>