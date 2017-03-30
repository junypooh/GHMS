<%@ page contentType="text/html" trimDirectiveWhitespaces="true" pageEncoding="UTF-8"%>
<%@ taglib prefix="layout" 	tagdir="/WEB-INF/tags/layout" %>
<%@ taglib prefix="c"  		uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" 	uri="http://java.sun.com/jsp/jstl/fmt" %>

<layout:main>

	<jsp:attribute name="javascript">
	
		<script type="text/javascript">
			
			var page = "${param.page}"; 
			var cpCode = "${param.cpCode}";

			$(document).ready(function() {
				
				$("#cpCode").val(cpCode);
				
				$("#cpCode").change(function(){
					var form = document.forms["searchForm"];
					form.submit();
				});
				
				$("#btnToggle").click(function(){
					var checked = $(this).prop("checked");
					$("#thirdPersTermsListContainer input[type=checkbox]").each(function(index){
						$(this).prop("checked", checked);
					});
				});
				
				$("#btnRemove").click(function() {
					var pkList = "";
					$("#thirdPersTermsListContainer tr input[type=checkbox]").each(function(index) {
						if($(this).prop("checked")) {
							pkList += $(this).parent().parent().attr("data-pk") + ",";
						}
					});	
					
					if(pkList == "") {
						alert("삭제할 약관을 선택하여 주십시오.");
						return false;
					} 
					
					if(confirm("정말로 삭제하시겠습니까?") == true) {
						pkList = pkList.substring(0, pkList.length -1);
						
						$.ajax({
							type : "POST",
						    cache: false,						
							url : "/terms/removeThirdPersTerms",
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
					}
				});
				
				$("#btnWrite").click(function(){
					location.href = "/terms/thirdPersTermsRegister";
				});
				
			});
			
		</script>	
		
	</jsp:attribute>

	<jsp:body>
		<form name="searchForm" method="get" action="/terms/thirdPersTermsList">
		<div class="boardTitle">
			<div class="borderTitle">
				<span class="pageTitle">제 3자 취급위탁 약관</span>
				<select id="cpCode" name="cpCode" title="검색조건을 선택하세요" style="float:right;">
					<option value="">전체</option>
					<c:forEach var="svc" items="${loginInfo.svcList }">
					<option value="${svc.cpCode }">${svc.cpName }</option>
					</c:forEach>
				</select>
			</div>
		</div>
		</form>
		<table class="board mt10">
			<caption>제 3자 취급 위탁 약관에 상품명, 제목, 공고일자, 시행일자, 버전정보를 확인 할수 있는 표</caption>
			<colgroup>
				<col style="width:5%" />
				<col style="width:5%" />
				<col style="width:10%" />
				<col style="width:*" />
				<col style="width:10%" />
				<col style="width:15%" />
			</colgroup>
			<thead>
				<tr>
					<th scope="col"><input type="checkbox" id="btnToggle" /></th>
					<th scope="col">NO</th>
					<th scope="col">상품명</th>
					<th scope="col">제목</th>
					<th scope="col">약관버전</th>
					<th scope="col">게시일</th>
				</tr>
			</thead>
			
			<tbody id="thirdPersTermsListContainer">
			
			<!-- DYNAMIC AREA 'list' -->
			<c:if test="${!empty termsList}">
				<c:forEach var="terms" items="${termsList}" varStatus="status" >
				<tr data-pk="${terms.pk}" onmouseover="this.style.backgroundColor='#f7f7f7'" onmouseout="this.style.backgroundColor=''" bgcolor="white">
					<td><input type="checkbox" name="" value=""></td>
					<td><fmt:formatNumber value="${pageNavi.totalCount - ((pageNavi.nowPage - 1) * pageNavi.pageSize + status.index) }" pattern="#,###"/></td>
					<td>${terms.cpName }</td>
					<td>
						&nbsp;<a href="/terms/thirdPersTermsModify?pk=${terms.pk }&page=${param.page}&cpCode=${param.cpCode}" onfocus="this.blur();">${terms.title}</a>
					</td>
					<td>${terms.termsVer}</td>
					<td>${terms.regDate}</td>					
				</tr>
				</c:forEach>
			</c:if>						
	
			<c:if test="${empty termsList}">
				<tr> 	
					<td align="center" colspan="6">검색된 데이타가 없습니다</td>
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