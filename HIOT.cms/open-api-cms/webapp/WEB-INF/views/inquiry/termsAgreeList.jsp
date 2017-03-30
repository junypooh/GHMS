<%@ page contentType="text/html" trimDirectiveWhitespaces="true" pageEncoding="UTF-8"%>
<%@ taglib prefix="layout" 	tagdir="/WEB-INF/tags/layout" %>
<%@ taglib prefix="c"  		uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" 	uri="http://java.sun.com/jsp/jstl/fmt" %>

<layout:main>

	<jsp:attribute name="javascript">
	
		<script type="text/javascript">
		
			var page = "${param.page}"; 
			var searchColumn = "${param.searchColumn}";
			var searchString = "${param.searchString}";	
			var cpCode = "${param.cpCode}";
			var termsCode = "${param.termsCode}";
		
			$(document).ready(function(){
				
				$("#cpCode").val(cpCode);
				$("#termsCode").val(termsCode);
				$("#searchColumn").val(searchColumn);
				
				$("#cpCode").change(function(){
					var form = document.forms["searchForm"];
					form.submit();
				});
				
				$("#termsCode").change(function(){
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
		<form name="searchForm" method="get" action="/inquiry/termsAgreeList">
		<div class="boardTitle">
			<div class="borderTitle">
				<span class="pageTitle">약관동의 조회</span>
				<select id="cpCode" name="cpCode" title="검색조건을 선택하세요" style="float:right;">
					<option value="">전체</option>
					<c:forEach var="svc" items="${loginInfo.svcList }">
					<option value="${svc.cpCode }">${svc.cpName }</option>
					</c:forEach>
				</select>
			</div>
		</div>
		
		<div class="boardTop">
			<select id="termsCode" name="termsCode" style="float:left;">
				<option value="">전체</option>
				<c:forEach var="code" items="${codeList}" varStatus="status" >
					<option value="${code.code}">${code.name}</option>
				</c:forEach>
			</select>
			<div class="flr">
				<select id="searchColumn" name="searchColumn" title="검색조건을 선택하세요">
					<option value="mbrId">고객 ID</option>
					<option value="telNo">휴대폰 번호</option>
				</select>
				<input type="text" id="searchString" name="searchString"  title="검색어를 입력하세요" value="${param.searchString }" />
				<button type="button" id="btnSearch" class="btn"><span>검색</span></button>
			</div>
		</div>
		</form>
		
		<table class="board mt10">
			<caption>약관동의 조회 결과의 고객ID, 서비스이용약관, 버전정보, 동의여부, 동의일자를 확인 할수 있는 표</caption>
			<colgroup>
				<col style="width:5%" />
				<col style="width:8%" />
				<col style="width:15%" />
				<col style="width:15%" />
				<col style="width:25%" />
				<col style="width:10%" />
				<col style="width:7%" />
				<col style="width:15%" />
			</colgroup>
			<thead>
				<tr>
					<th scope="col">번호</th>
					<th scope="col">고객 ID</th>
					<th scope="col">휴대폰번호</th>
					<th scope="col">상품명</th>
					<th scope="col">동의약관</th>
					<th scope="col">버전정보</th>
					<th scope="col">동의여부</th>
					<th scope="col">동의일시</th>
				</tr>
			</thead>
			
			<tbody>
			<!-- DYNAMIC AREA 'list' -->
			<c:if test="${!empty termsAgreeList}">
				<c:forEach var="termsAgree" items="${termsAgreeList}" varStatus="status" >
				<tr onmouseover="this.style.backgroundColor='#f7f7f7'" onmouseout="this.style.backgroundColor=''" bgcolor="white">
					<td><fmt:formatNumber value="${pageNavi.totalCount - ((pageNavi.nowPage - 1) * pageNavi.pageSize + status.index) }" pattern="#,###"/></td>
					<td>${termsAgree.mbrId }</td>
					<td>${termsAgree.telNo }</td>
					<td>${termsAgree.cpName }</td>
					<td>${termsAgree.termsTitle}</td>
					<td>${termsAgree.termsVer}</td>
					<td>
						<c:if test="${termsAgree.agreeYN == 'Y' }">O</c:if>
						<c:if test="${termsAgree.agreeYN == 'N' }">X</c:if>
					</td>
					<td><fmt:formatDate value="${termsAgree.agreeDate }" pattern="yyyy-MM-dd hh:mm"/></td>
				</tr>
				</c:forEach>
			</c:if>			
	
			<c:if test="${empty termsAgreeList}">
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