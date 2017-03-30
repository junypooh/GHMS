<%@ page contentType="text/html" trimDirectiveWhitespaces="true" pageEncoding="UTF-8"%>
<%@ taglib prefix="layout" 	tagdir="/WEB-INF/tags/layout" %>
<%@ taglib prefix="c"  		uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" 	uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" 		uri="http://java.sun.com/jsp/jstl/functions"%>

<layout:main>

	<jsp:attribute name="javascript">
	
		<script type="text/javascript">
			
			$(document).ready(function() {
				
				$("#btnWrite").click(function(){
					location.href = "/manager/roleRegister";
				});
				
			});
			
			
		</script>	
		
	</jsp:attribute>

	<jsp:body>
	
		<h2 class="pageTitle"><b>관리자 역할 관리</b></h2>
	
		<div class="boardTop">
		</div>
		
		<table class="board mt10">
			<caption></caption>
			<colgroup>
				<col style="width:5%" />
				<col style="width:25%" />
				<col style="width:*%" />
			</colgroup>
			<thead>
				<tr>
					<th scope="col">NO</th>
					<th scope="col">역할</th>
					<th scope="col">역할 설명</th>
				</tr>
			</thead>
			
			<tbody>
			<c:if test="${!empty roleList}">
				<c:forEach var="role" items="${roleList}" varStatus="status" >
				<tr onmouseover="this.style.backgroundColor='#f7f7f7'" onmouseout="this.style.backgroundColor=''" bgcolor="white">
					<td><fmt:formatNumber value="${roleTotalCount - status.index }" pattern="#,###"/></td>
					<td><a href="/manager/roleModify?role=${role.role }">${role.name}</a></td>
					<td>${fn:substring(role.description, 0 , 70)}</td>
				</tr>
				</c:forEach>
			</c:if>
			
			<c:if test="${empty roleList}">
				<tr> 	
					<td align="center" colspan="4">등록된 역할이 없습니다</td>
				</tr>
			</c:if>
						
			</tbody>
		</table>
		
		
		<div class="btns clearfix">
			<div class="flr">
				<a id="btnWrite" class="btn2 btn2_red30" style="cursor:pointer"><span>등록</span></a>
			</div>
		</div>	
		
	</jsp:body>
</layout:main>