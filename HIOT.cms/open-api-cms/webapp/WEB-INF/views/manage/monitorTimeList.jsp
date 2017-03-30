<%@ page contentType="text/html" trimDirectiveWhitespaces="true" pageEncoding="UTF-8"%>
<%@ taglib prefix="layout" 	tagdir="/WEB-INF/tags/layout" %>
<%@ taglib prefix="c"  		uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" 	uri="http://java.sun.com/jsp/jstl/fmt" %>

<layout:main>

	<jsp:attribute name="javascript">
	
		<script type="text/javascript">			
			var page   = "${param.page}";
			var cpCode = "${param.cpCode}"; 
			
			$(document).ready(function() {
		
				$('#cpCode').val(cpCode);
				$("#btnWrite").click(function(){
					location.href = "/manage/monitorTimeRegister?page=" + page;
				});			
			});

		</script>	
		
	</jsp:attribute>

	<jsp:body>
	
		<div class="boardTitle">
			<div class="borderTitle">
				<span class="pageTitle">올레 홈캠 모니터링 시간 조절</span>
			</div>
		</div>
		
		<table class="board mt10">
			<caption>모니터링 조회 결과의 제목, 게시시간, 작성자, 노출여부를 확인 할수 있는 표</caption>
			<colgroup>
				<col style="width:10%" />
				<col style="width:20%" />
				<col style="width:20%" />
				<col style="width:*" />
			</colgroup>
			<thead>
				<tr>
					<th scope="col">NO</th>
					<th scope="col">변경된 모니터링 시간</th>
					<th scope="col">변경자</th>
					<th scope="col">적용일</th>
				</tr>
			</thead>
			
			<tbody id="noticeListContainer">
			
			<!-- DYNAMIC AREA 'list' -->
			<c:if test="${!empty noticeList}">
				<c:forEach var="notice" items="${noticeList}" varStatus="status" >
				<tr data-pk="${notice.pk}" onmouseover="this.style.backgroundColor='#f7f7f7'" onmouseout="this.style.backgroundColor=''" bgcolor="white">
					<td><fmt:formatNumber value="${pageNavi.totalCount - ((pageNavi.nowPage - 1) * pageNavi.pageSize + status.index) }" pattern="#,###"/></td>
					<td>${notice.interval}분</td>
					<td>${notice.adminName}</td>
					<td>${notice.amPmDate}</td>
				</tr>
				</c:forEach>
			</c:if>			
	
			<c:if test="${empty noticeList}">
				<tr> 	
					<td align="center" colspan="4">검색된 데이타가 없습니다</td>
				</tr>
			</c:if>
			</tbody>
		</table>
		
		${pageNavi.navi}
		
		<div class="btns clearfix">
			<div class="flr">
				<a id="btnWrite" class="btn2 btn2_red30" style="cursor:pointer"><span>시간 조절</span></a>
			</div>
		</div>	
		
	</jsp:body>
</layout:main>