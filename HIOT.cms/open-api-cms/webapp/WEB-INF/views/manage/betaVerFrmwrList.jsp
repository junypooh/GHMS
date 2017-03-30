<%@ page contentType="text/html" trimDirectiveWhitespaces="true" pageEncoding="UTF-8"%>
<%@ taglib prefix="layout" 	tagdir="/WEB-INF/tags/layout" %>
<%@ taglib prefix="c"  		uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" 	uri="http://java.sun.com/jsp/jstl/fmt" %>

<layout:main>

	<jsp:attribute name="javascript">
	
		<script type="text/javascript">
			
			var page = "${param.page}"; 
			$(document).ready(function() {
				$("#btnWrite").click(function(){
					location.href = "/manage/betaVerFrmwrRegister?page=" + page;
				});				
			});
			
		</script>	
		
	</jsp:attribute>

	<jsp:body>
	
		<form name="searchForm" method="get" action="/manage/betaVerFrmwrList">
		<div class="boardTitle">
			<div class="borderTitle">
				<span class="pageTitle">테스트 단말펌웨어 버전 관리</span>
				<%-- <select id="cpCode" name="cpCode" title="검색조건을 선택하세요" style="float:right;">
					<option value="">전체</option>
					<c:forEach var="svc" items="${loginInfo.svcList }">
					<option value="${svc.cpCode }">${svc.cpName }</option>
					</c:forEach>
				</select> --%>
			</div>
		</div>
		</form>
		
		<table class="board mt10">
			<caption></caption>
			<colgroup>
				<col style="width:5%" />
				<col style="width:15%" />
				<col style="width:*%" />
				<col style="width:15%" />
				<col style="width:15%" />
				<col style="width:15%" />
				<col style="width:10%" />
			</colgroup>
			<form id="sortForm" name="sortForm" method="post" action="/manage/verFrmwrList">
				<input type="hidden" name= "proc" value="${param.proc}"/>
			</form>
			<thead>
				<tr>
					<th scope="col">NO</th>
					<th scope="col">상품명</th>
					<th scope="col">모델명</th>
					<th scope="col">버전</th>
					<th scope="col">업데이트 필수 여부</th>
					<th scope="col">등록자</th>
					<th scope="col">등록일</th>
				</tr>
			</thead>
			
			<tbody id="verFrmwrListContainer">
			
			<!-- DYNAMIC AREA 'list' -->
			<c:if test="${!empty verFrmwrList}">
				<c:forEach var="verFrmwr" items="${verFrmwrList}" varStatus="status" >
				<tr data-pk="${verFrmwr.pk}" onmouseover="this.style.backgroundColor='#f7f7f7'" onmouseout="this.style.backgroundColor=''" bgcolor="white">
					<td><fmt:formatNumber value="${pageNavi.totalCount - ((pageNavi.nowPage - 1) * pageNavi.pageSize + status.index) }" pattern="#,###"/></td>
					<td>
							${verFrmwr.cpName}
					</td>
					<td>
					&nbsp;<a href="/manage/betaVerFrmwrModify?pk=${verFrmwr.pk }&page=${param.page}" onfocus="this.blur();">${verFrmwr.modelName}</a>
					</td>
					<td>${verFrmwr.verNum}</td>
					<td>${verFrmwr.mandatoryYN}</td>
					<td>${verFrmwr.adminName}</td>
					<td><fmt:formatDate value="${verFrmwr.regDate}" pattern="yyyy-MM-dd"/></td>
				</tr>
				</c:forEach>
			</c:if>			
	
			<c:if test="${empty verFrmwrList}">
				<tr> 	
					<td align="center" colspan="7">검색된 데이타가 없습니다</td>
				</tr>
				
			</c:if>
			</tbody>
		</table>
		
		${pageNavi.navi}
		
		<div class="btns clearfix">
			<div class="flr">
				<a id="btnWrite" class="btn2 btn2_red30" style="cursor:pointer"><span>등록</span></a>
			</div>
		</div>	
		
	</jsp:body>
</layout:main>