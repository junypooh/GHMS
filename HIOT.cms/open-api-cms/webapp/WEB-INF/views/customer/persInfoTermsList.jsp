<%@ page contentType="text/html" trimDirectiveWhitespaces="true" pageEncoding="UTF-8"%>
<%@ taglib prefix="layout" 	tagdir="/WEB-INF/tags/layout" %>
<%@ taglib prefix="c"  		uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" 	uri="http://java.sun.com/jsp/jstl/fmt" %>

<layout:main>

	<jsp:attribute name="javascript">
	
		<script type="text/javascript">
			
			var page = "${param.page}"; // EL 의 param 은 Cotroller 에서 Model 에 추가하지 않아도 JSP 에서 바로 사용할수있다.
			var cpCode = "${param.cpCode}";
			// $(document).ready 는 문서가 로드 완료되는 시점으로 
			// 브라우저상에서 표시되어야 하는 모든 객체(image , css, movie 등등)가 로드되는 시점이 아닌 
			// html 문서가 모두 로드되는 시점이며, 이는 곧 javascript 에서 DOM 조작을 하는데 아무런
			// 문제가 없다는것을 말한다.
			$(document).ready(function() {
				
				$("#cpCode").val(cpCode);
				
				$("#cpCode").change(function(){
					var form = document.forms["searchForm"];
					form.submit();
				});
				
				$("#btnToggle").click(function(){
					var checked = $(this).prop("checked");
					$("#persInfoTermsListContainer input[type=checkbox]").each(function(index){
						$(this).prop("checked", checked);
					});
				});
				
				$("#btnRemove").click(function() {
					
					if(!confirm('삭제 하시겠습니까?')) return false;
					
					var pkList = "";
					$("#persInfoTermsListContainer tr input[type=checkbox]").each(function(index) {
						if($(this).prop("checked")) {
							pkList += $(this).parent().parent().attr("data-pk") + ",";
						}
					});	
					
					if(pkList == "") {
						alert("삭제할 약관을 선택하여 주십시오.");
						return false;
					} 
						
					pkList = pkList.substring(0, pkList.length -1);
					
					$.ajax({
						type : "POST",
					    cache: false,						
						url : "/customer/removePersInfoTerms",
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
					location.href = "/customer/persInfoTermsRegister?page=" + page;
				});
				
			});
			
		</script>	
		
	</jsp:attribute>

	<jsp:body>
		<form name="searchForm" method="get" action="/customer/persInfoTermsList">
		<div class="boardTitle">
			<div class="borderTitle">
				<span class="pageTitle">개인정보 취급방침</span>
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
			<caption>개인정보취급방침에 상품명, 제목, 공고일자, 시행일자, 버전정보 확인 할수 있는 표</caption>
			<colgroup>
				<col style="width:5%" />
				<col style="width:5%" />
				<col style="width:15%" />
				<col style="width:*" />
				<col style="width:15%" />
				<col style="width:15%" />
				<col style="width:10%" />
			</colgroup>
			<thead>
				<tr>
					<th scope="col"><input type="checkbox" id="btnToggle" /></th>
					<th scope="col">NO</th>
					<th scope="col">상품명</th>
					<th scope="col">제목</th>
					<th scope="col">공고일자</th>
					<th scope="col">시행일자</th>
					<th scope="col">버전정보</th>
				</tr>
			</thead>
			
			<tbody id="persInfoTermsListContainer">
			
			<!-- DYNAMIC AREA 'list' -->
			<c:if test="${!empty privacyList}">
				<c:forEach var="privacy" items="${privacyList}" varStatus="status" >
				<tr data-pk="${privacy.pk}" onmouseover="this.style.backgroundColor='#f7f7f7'" onmouseout="this.style.backgroundColor=''" bgcolor="white">
					<td><input type="checkbox" name="" value=""></td>
					<td><fmt:formatNumber value="${pageNavi.totalCount - ((pageNavi.nowPage - 1) * pageNavi.pageSize + status.index) }" pattern="#,###"/></td>
					<td>${privacy.cpName}</td>
					<td style="text-align:left;">
						&nbsp;<a href="/customer/persInfoTermsModify?pk=${privacy.pk }&page=${param.page}&cpCode=${param.cpCode}" onfocus="this.blur();">${privacy.title}</a>
					</td>
					<td>
						<fmt:parseDate value="${privacy.privacyAnnouncement}" var="dateFmt" pattern="yyyyMMddHHmmss"/>
						<fmt:formatDate value="${dateFmt}"  pattern="yyyy.MM.dd"/>
					</td>
					<td>
						<fmt:parseDate value="${privacy.privacyStart}" var="dateFmt" pattern="yyyyMMddHHmmss"/>
						<fmt:formatDate value="${dateFmt}"  pattern="yyyy.MM.dd"/>
					</td>
					<td>${privacy.privacyVer}</td>				
				</tr>
				</c:forEach>
			</c:if>			
	
			<c:if test="${empty privacyList}">
				<tr> 	
					<td align="center" colspan="7">검색된 데이타가 없습니다</td>
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