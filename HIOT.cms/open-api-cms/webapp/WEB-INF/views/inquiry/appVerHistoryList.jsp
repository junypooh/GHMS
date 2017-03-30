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
		<form name="searchForm" method="get" action="/inquiry/appVerHistoryList">
		<div class="boardTitle">
			<div class="borderTitle">
				<span class="pageTitle">APP버전 조회</span>
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
					<option value="mbr_id">고객ID</option>
					<option value="tel_no">휴대폰번호</option>
				</select>
				<input type="text" id="searchString" name="searchString"  title="검색어를 입력하세요" value="${param.searchString }" />
				<button type="button" id="btnSearch" class="btn"><span>검색</span></button>
			</div>
		</div>
		</form>
		</form>
		
		<table class="board mt10">
			<caption></caption>
			<colgroup>
				<col style="width:5%" />
				<col style="width:15%" />
				<col style="width:15%" />
				<col style="width:10%" />
				<col style="width:15%" />
				<col style="width:15%" />
				<col style="width:10%" />
				<col style="width:15%" />
			</colgroup>
			<thead>
				<tr>
					<th scope="col">번호</th>
					<th scope="col">고객ID</th>
					<th scope="col">휴대폰번호</th>
					<th scope="col">상품명</th>
					<th scope="col">App버전정보</th>
					<th scope="col">단말모델명</th>
					<th scope="col">OS버전</th>
					<th scope="col">등록일시</th>
				</tr>
			</thead>
			
			<tbody>
			<!-- DYNAMIC AREA 'list' -->
			<c:if test="${!empty appVerHistoryList}">
				<c:forEach var="appVerHistory" items="${appVerHistoryList}" varStatus="status" >
				<tr onmouseover="this.style.backgroundColor='#f7f7f7'" onmouseout="this.style.backgroundColor=''" bgcolor="white">
					<td><fmt:formatNumber value="${pageNavi.totalCount - ((pageNavi.nowPage - 1) * pageNavi.pageSize + status.index) }" pattern="#,###"/></td>
					<td>${appVerHistory.mbrId}</td>
					<td>${appVerHistory.telNo}</td>
					<td>${appVerHistory.cpName}</td>
					<td>${appVerHistory.applVer}</td>
					<td>${appVerHistory.devName}</td>
					<td>${appVerHistory.osVer}</td>
					<td>${appVerHistory.cretDt}</td>
				</tr>
				</c:forEach>
			</c:if>			
	
			<c:if test="${empty appVerHistoryList}">
				<tr> 	
					<td align="center" colspan="8">검색된 데이타가 없습니다</td>
				</tr>
			</c:if>
			<!-- DYNAMIC AREA 'list' -->
			</tbody>
		</table>
		
		${pageNavi.navi}
		
	</jsp:body>
</layout:main>