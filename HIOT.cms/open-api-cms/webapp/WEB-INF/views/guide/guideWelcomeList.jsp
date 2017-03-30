<%@ page contentType="text/html" trimDirectiveWhitespaces="true" pageEncoding="UTF-8"%>
<%@ taglib prefix="layout" 	tagdir="/WEB-INF/tags/layout" %>
<%@ taglib prefix="c"  		uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" 	uri="http://java.sun.com/jsp/jstl/fmt" %>

<layout:main>

	<jsp:attribute name="javascript">
	
		<script type="text/javascript">
			
			var page = "${param.page}"; // EL 의 param 은 Cotroller 에서 Model 에 추가하지 않아도 JSP 에서 바로 사용할수있다.
			var searchColumn = "${param.searchColumn}";
			var searchString = "${param.searchString}";
			var cpCode = "${param.cpCode}";
			
			// $(document).ready 는 문서가 로드 완료되는 시점으로 
			// 브라우저상에서 표시되어야 하는 모든 객체(image , css, movie 등등)가 로드되는 시점이 아닌 
			// html 문서가 모두 로드되는 시점이며, 이는 곧 javascript 에서 DOM 조작을 하는데 아무런
			// 문제가 없다는것을 말한다.
			$(document).ready(function() {
				
				$("#cpCode").val(cpCode);
				$("#searchColumn").val(searchColumn);
				
				$("#cpCode").change(function(){
					var form = document.forms["searchForm"];
					form.submit();
				});
				
				$("#btnToggle").click(function(){
					var checked = $(this).prop("checked");
					$("#guideWelcomeListContainer input[type=checkbox]").each(function(index){
						$(this).prop("checked", checked);
					});
				});
				
				$("#guideWelcomeListContainer tr td[data-open-yn]").each(function(index) {
					
					var pk = $(this).parent().attr("data-pk");
					var cpCode = $(this).parent().attr("data-cp-code");
					var verNum = $(this).parent().attr("data-ver-num");
					var openYN = $(this).attr("data-open-yn");
					var item = $(this).find("select");
					var id = $(this).find("select").val();

 					item.val(openYN).change(function(){
 						if(confirm("노출여부를 변경 하시겠습니까?")) {
 							updateOpenYN(pk, $(this).val(), cpCode, verNum);	
	 						
	 						//노출여부를 Y로 했을때 같은 상품명은 전부 N으로 설정.
	 						if($(this).val() == 'Y'){
	 							$("#guideWelcomeListContainer tr td[data-open-yn]").each(function(index) {
	 	 							var setCpCode = $(this).parent().attr("data-cp-code");
	 	 							var setPk = $(this).parent().attr("data-pk");
	 	 							var setVerNum = $(this).parent().attr("data-ver-num");
	 	 							if(cpCode == setCpCode && pk != setPk && verNum == setVerNum){
	 	 								$(this).find("select").val('N')
	 	 							}
	 	 						});	
	 						}
 						} else {
 							var status = $(this).val() == "Y" ? "N" : "Y";
 							$(this).val(status);
 						}
 						
 						$(this).blur();
					});
				});
				
				$("#btnSearch").click(function() {
					// form 요소를 가져오는 다양한 방법이 존재하나 아래와 같은 경우는
					// 현재 까지 모든 브라우저상에서 이상이 없음을 확인한 형태이다.
					var form = document.forms["searchForm"];
					form.submit();
				});
				
				$("#btnRemove").click(function() {
					
					if(!confirm('삭제 하시겠습니까?')) return false;
					
					var pkList = "";
					$("#guideWelcomeListContainer tr input[type=checkbox]").each(function(index) {
						if($(this).prop("checked")) {
							pkList += $(this).parent().parent().attr("data-pk") + ",";
						}
					});	
					
					if(pkList == "") {
						alert("삭제할 웰컴가이드를 선택하여 주십시오.");
						return false;
					} 
						
					pkList = pkList.substring(0, pkList.length -1);
					
					$.ajax({
						type : "POST",
					    cache: false,						
						url : "/guide/removeWelcomeGuide",
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
					location.href = "/guide/guideWelcomeRegister";
				});
				
			});
			
			function updateOpenYN(pk, yn, cpCode, verNum) {
				
				// Jquery 의 Ajax 함수 호출의 경우 기본적으로 비동기 이며,
				// async 옵션을 통하여 동기식으로 변경 가능하다.
				$.ajax({
					type : "GET",
				    cache: false,						
					url : "/guide/updateWelcomeOpenYN",
					dataType : "json",
					data : { "pk" : pk, "openYN" : yn, "targetCpCode" : cpCode, "verNum" : verNum}
				})
				.done(function(result){
					if(result.code != 200) {
						alert("서버에서 오류가 발생하였습니다.\n다시 시도하여 주십시오.");
						location.reload();
					}
				})
				.fail(function( jqXHR, textStatus, errorThrown){
					console.log(jqXHR); // Debugging 시 브라우저 개발자 도구의 콘솔창에서 데이터를 확인할수있다. 
				});
			}
			
		</script>	
		
	</jsp:attribute>

	<jsp:body>
		<form name="searchForm" method="get" action="/guide/guideWelcomeList">
		<div class="boardTitle">
			<div class="borderTitle">
				<span class="pageTitle">웰컴 가이드</span>
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
					<option value="adminName">작성자</option>
				</select>
				<input type="text" id="searchString" name="searchString"  title="검색어를 입력하세요" value="${param.searchString }" />
				<button type="button" id="btnSearch" class="btn"><span>검색</span></button>
			</div>
		</div>
		</form>
		
		<table class="board mt10">
			<caption>웰컴가이드에 상품명, 앱버전, 작성자, 등록일자, 노출여부를 확인 할수 있는 표</caption>
			<colgroup>
				<col style="width:5%" />
				<col style="width:5%" />
				<col style="width:10%" />
				<col style="width:*%" />
				<col style="width:15%" />
				<col style="width:15%" />
				<col style="width:15%" />
			</colgroup>
			<thead>
				<tr>
					<th scope="col"><input type="checkbox" id="btnToggle" /></th>
					<th scope="col">NO</th>
					<th scope="col">상품명</th>
					<th scope="col">앱버전</th>
					<th scope="col">작성자</th>
					<th scope="col">등록일자</th>
					<th scope="col">노출여부</th>
				</tr>
			</thead>
			
			<tbody id="guideWelcomeListContainer">
			
			<!-- DYNAMIC AREA 'list' -->
			<c:if test="${!empty welcomeList}">
				<c:forEach var="welcome" items="${welcomeList}" varStatus="status" >
				<tr data-pk="${welcome.pk}" data-cp-code="${welcome.cpCode}" data-ver-num="${welcome.verNum}" onmouseover="this.style.backgroundColor='#f7f7f7'" onmouseout="this.style.backgroundColor=''" bgcolor="white">
					<td><input type="checkbox" name="" value=""></td>
					<td><fmt:formatNumber value="${pageNavi.totalCount - ((pageNavi.nowPage - 1) * pageNavi.pageSize + status.index) }" pattern="#,###"/></td>
					<td>${welcome.cpName }</td>
					<td>
						<a href="/guide/guideWelcomeModify?pk=${welcome.pk }&page=${param.page}&searchColumn=${param.searchColumn}&searchString=${param.searchString}&cpCode=${param.cpCode}" onfocus="this.blur();">
							${welcome.verNum}
						</a>
					</td>
					<td>${welcome.adminName}</td>
					<td>
						<fmt:parseDate value="${welcome.regDate}" var="dateFmt" pattern="yyyyMMdd"/>
						<fmt:formatDate value="${dateFmt}"  pattern="yyyy.MM.dd"/>
					</td>
					<td data-open-yn="${welcome.openYN}">
						<select name="openYN">
							<option value="Y">Y</option>
							<option value="N">N</option>
						</select>
					</td>
				</tr>
				</c:forEach>
			</c:if>			
	
			<c:if test="${empty welcomeList}">
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