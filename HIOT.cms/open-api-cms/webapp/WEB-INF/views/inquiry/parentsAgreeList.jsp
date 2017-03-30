<%@ page contentType="text/html" trimDirectiveWhitespaces="true" pageEncoding="UTF-8"%>
<%@ taglib prefix="layout" 	tagdir="/WEB-INF/tags/layout" %>
<%@ taglib prefix="c"  		uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" 	uri="http://java.sun.com/jsp/jstl/fmt" %>

<layout:main>

	<jsp:attribute name="javascript">
	
		<script type="text/javascript">
		
			var searchColumn = "${param.searchColumn}";
			var searchString = "${param.searchString}";		
			var cpCode = "${param.cpCode}";
		
			$(document).ready(function(){
				
				$("#cpCode").val(cpCode)
				$("#searchColumn").val(searchColumn)
				
				$("#cpCode").change(function(){
					var form = document.forms["searchForm"];
					form.submit();
				});
				
				$("#btnSearch").click(function(){
					var form = document.forms["searchForm"];
					form.submit();					
				});
			});
		
		</script>	
	
	</jsp:attribute>

	<jsp:body>
		<form name="searchForm" method="get" action="/inquiry/parentsAgreeList">
		<div class="boardTitle">
			<div class="borderTitle">
				<span class="pageTitle">법정대리인 동의 조회</span>
				<select id="cpCode" name="cpCode" title="검색조건을 선택하세요" style="float:right;">
					<option value="">전체</option>
					<c:forEach var="svc" items="${loginInfo.svcList }">
					<option value="${svc.cpCode }">${svc.cpName }</option>
					</c:forEach>
				</select>
			</div>
		</div>
		
		<div class="boardTop">
			<div class="flr">
				<select id="searchColumn" name="searchColumn" title="검색조건을 선택하세요">
					<option value="parentsName">보호자 이름</option>
					<option value="parentsMobile">보호자 휴대폰</option>
				</select>
				<input type="text" id="searchString" name="searchString"  title="검색어를 입력하세요" value="${param.searchString }" />
				<button type="button" id="btnSearch" class="btn"><span>검색</span></button>
			</div>
		</div>
		</form>
		</form>
		
		<table class="board mt10">
			<caption>약관동의 조회 결과의 고객ID, 서비스이용약관, 버전정보, 동의여부, 동의일자를 확인 할수 있는 표</caption>
			<colgroup>
				<col style="width:5%" />
				<col style="width:15%" />
				<col style="width:15%" />
				<col style="width:20%" />
				<col style="width:15%" />
				<col style="width:15%" />
				<col style="width:15%" />
			</colgroup>
			<thead>
				<tr>
					<th scope="col">번호</th>
					<th scope="col">상품명</th>
					<th scope="col">고객 ID</th>
					<th scope="col">보호자 이름</th>
					<th scope="col">보호자 휴대폰</th>
					<th scope="col">동의여부</th>
					<th scope="col">동의일자</th>
				</tr>
			</thead>
			
			<tbody>
			<!-- DYNAMIC AREA 'list' -->
			<c:if test="${!empty parentsAgreeList}">
				<c:forEach var="parentsAgree" items="${parentsAgreeList}" varStatus="status" >
				<tr onmouseover="this.style.backgroundColor='#f7f7f7'" onmouseout="this.style.backgroundColor=''" bgcolor="white">
					<td><fmt:formatNumber value="${pageNavi.totalCount - ((pageNavi.nowPage - 1) * pageNavi.pageSize + status.index) }" pattern="#,###"/></td>
					<td>${parentsAgree.cpName }</td>
					<td>${parentsAgree.mbrId }</td>
					<td>${parentsAgree.parentsName }</td>
					<td>${parentsAgree.parentsMobile }</td>
					<td>
						<c:if test="${parentsAgree.agreeYN == 'Y' }">O</c:if>
						<c:if test="${parentsAgree.agreeYN == 'N' }">X</c:if>
					</td>
					<td><fmt:formatDate value="${parentsAgree.agreeDate }" pattern="yyyy-MM-dd hh:mm"/></td>
				</tr>
				</c:forEach>
			</c:if>			
	
			<c:if test="${empty parentsAgreeList}">
				<tr> 	
					<td align="center" colspan="7">검색된 데이타가 없습니다</td>
				</tr>
			</c:if>
			<!-- DYNAMIC AREA 'list' -->
			</tbody>
		</table>
		
		${pageNavi.navi}
		
	</jsp:body>
</layout:main>