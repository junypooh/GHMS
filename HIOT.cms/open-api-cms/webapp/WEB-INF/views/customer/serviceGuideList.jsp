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
			var searchCpCode = "${param.searchCpCode}";
			var searchSvcFn  = "${param.searchSvcFn}";
			
			$(document).ready(function() {
				
				$("#searchCpCode").val(searchCpCode);
				$("#searchSvcFn").val(searchSvcFn);
				$("#searchColumn").val(searchColumn);
				
				$("#searchCpCode, #searchSvcFn").change(function(){
					var form = document.forms["searchForm"];
					form.submit();
				});
				
				$("#btnSearch").click(function() {
					var form = document.forms["searchForm"];
					form.submit();
				});				
				
				$("#btnToggle").click(function(){
					var checked = $(this).prop("checked");
					$("#serviceGuideListContainer input[type=checkbox]").each(function(index){
						$(this).prop("checked", checked);
					});
				});
				
				$("#serviceGuideListContainer tr td[data-open-yn]").each(function(index) {
					
					var pk = $(this).parent().attr("data-pk");
					var openYN = $(this).attr("data-open-yn");
					var item = $(this).find("select");

 					item.val(openYN).change(function(){
 						if(confirm("노출여부를 변경 하시겠습니까?") == true) {
 							updateOpenYN(pk, $(this).val());
 						} else {
 							var status = $(this).val() == "Y" ? "N" : "Y";
 							$(this).val(status);
 						}
 						
 						$(this).blur();
					});
				});
				
				$("#btnRemove").click(function() {
					
					if(!confirm('삭제 하시겠습니까?')) return false;
					
					var pkList = "";
					$("#serviceGuideListContainer tr input[type=checkbox]").each(function(index) {
						if($(this).prop("checked")) {
							pkList += $(this).parent().parent().attr("data-pk") + ",";
						}
					});	
					
 					if(pkList == "") {
						alert("삭제할 서비스 안내를 선택하여 주십시오.");
						return false;
					} 
						
					pkList = pkList.substring(0, pkList.length -1);
					
					$.ajax({
						type : "POST",
					    cache: false,
						url : "/customer/removeServiceGuide",
						dataType : "json",
						data : { "pkList" : pkList}
					})
					.done(function(result){
						if(result.code != 200) {
							alert("서버에서 오류가 발생하였습니다.\n다시 시도하여 주십시오.");
						} else {
							alert("정상적으로 삭제 처리 되었습니다.");
							location.reload();
						}
					})
					.fail(function( jqXHR, textStatus, errorThrown){
						console.log(jqXHR);
					});
				});
				
				$("#btnWrite").click(function(){
					location.href = "/customer/serviceGuideRegister";
				});
				
			});
			
			function updateOpenYN(pk, yn) {
				
				$.ajax({
					type : "GET",
				    cache: false,						
					url : "/customer/updateServiceOpenYN",
					dataType : "json",
					data : { "pk" : pk, "openYN" : yn}
				})
				.done(function(result){
					if(result.code != 200) {
						alert("서버에서 오류가 발생하였습니다.\n다시 시도하여 주십시오.");
						location.reload();
					}
				})
				.fail(function( jqXHR, textStatus, errorThrown){
					console.log(jqXHR);  
				});
			}
			
		</script>	
		
	</jsp:attribute>

	<jsp:body>
		<form name="searchForm" method="get" action="/customer/serviceGuideList">
		<div class="boardTitle">
			<div class="borderTitle">
				<span class="pageTitle">서비스안내(FAQ)</span>
				<select id="searchCpCode" name="searchCpCode" title="검색조건을 선택하세요" style="float:right;">
					<option value="">전체</option>
					<c:forEach var="svc" items="${loginInfo.svcList }">
					<option value="${svc.cpCode }">${svc.cpName }</option>
					</c:forEach>
				</select>
			</div>
		</div>
		
		<div class="boardTop">
			<select id="searchSvcFn" name="searchSvcFn" style="float:left;">
				<option value="">전체</option>
				<c:forEach var="svcFn" items="${svcFnList}" varStatus="status" >
				<option value="${svcFn.pk}">${svcFn.name}</option>
				</c:forEach>
			</select>
			<div class="flr">
				<select id="searchColumn" name="searchColumn" title="검색조건을 선택하세요">
					<option value="title">제목</option>
					<option value="adminName">작성자</option>
				</select>
				<input type="text" id="searchString" name="searchString"  title="검색어를 입력하세요" value="${param.searchString }" />
				<button type="button" id="btnSearch" class="btn"><span>검색</span></button>
			</div>
		</div>
		</form>
		
		<table class="board mt10">
			<caption>서비스 안내에 구분, 제목, 작성자, 노출여부, 작성일을 확인 할수 있는 표</caption>
			<colgroup>
				<col style="width:5%" />
				<col style="width:5%" />
				<col style="width:10%" />
				<col style="width:10%" />
				<col style="width:*%" />
				<col style="width:5%" />
				<col style="width:10%" />
				<col style="width:10%" />
			</colgroup>
			<thead>
				<tr>
					<th scope="col"><input type="checkbox" id="btnToggle" /></th>
					<th scope="col">NO</th>
					<th scope="col">상품명</th>
					<th scope="col">기능명</th>
					<th scope="col">제목</th>
					<th scope="col">작성자</th>
					<th scope="col">작성일</th>
					<th scope="col">노출여부</th>
				</tr>
			</thead>
			
			<tbody id="serviceGuideListContainer">
			
			<!-- DYNAMIC AREA 'list' -->
			<c:if test="${!empty serviceGuideList}">
				<c:forEach var="serviceGuide" items="${serviceGuideList}" varStatus="status" >
				<tr data-pk="${serviceGuide.pk}" onmouseover="this.style.backgroundColor='#f7f7f7'" onmouseout="this.style.backgroundColor=''" bgcolor="white">
					<td><input type="checkbox" name="" value=""></td>
					<td><fmt:formatNumber value="${pageNavi.totalCount - ((pageNavi.nowPage - 1) * pageNavi.pageSize + status.index) }" pattern="#,###"/></td>
					<td>${serviceGuide.cpName}</td>
					<td>${serviceGuide.svcFnName }</td>
					<td style="text-align:left;">
						&nbsp;<a href="/customer/serviceGuideModify?pk=${serviceGuide.pk}&page=${param.page}&searchColumn=${param.searchColumn}&searchString=${param.searchString}&searchCpCode=${param.searchCpCode}&searchFaqCode=${param.searchFaqCode}" onfocus="this.blur();">${serviceGuide.title}</a>
					</td>
					<td>${serviceGuide.adminName}</td>
					<td>
						<fmt:parseDate value="${serviceGuide.regDate}" var="dateFmt" pattern="yyyyMMdd"/>
						<fmt:formatDate value="${dateFmt}"  pattern="yyyy.MM.dd"/>
					</td>
					<td data-open-yn="${serviceGuide.openYN}">
						<select name="openYN">
							<option value="Y">Y</option>
							<option value="N">N</option>
						</select>
					</td>
				</tr>
				</c:forEach>
			</c:if>			
	
			<c:if test="${empty serviceGuideList}">
				<tr> 	
					<td align="center" colspan="8">검색된 데이타가 없습니다</td>
				</tr>
			</c:if>
			</tbody>
		</table>
		
		${pageNavi.navi}
		
		<div class="btns clearfix">
			<div class="fll">
				<a id="btnRemove" class="btn2 btn2_black30" style="cursor:pointer"><span>삭제</span></a>
			</div>
			<div class="flr">
				<a id="btnWrite" class="btn2 btn2_red30" style="cursor:pointer"><span>등록</span></a>
			</div>
		</div>	
		
	</jsp:body>
</layout:main>