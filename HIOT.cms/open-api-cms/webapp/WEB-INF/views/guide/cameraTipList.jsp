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
			var positionCode = "${param.positionCode}";
			
			$(document).ready(function() {
				
				$("#cpCode").val(cpCode);
				$("#positionCode").val(positionCode);
				$("#searchColumn").val(searchColumn);
				
				$("#cpCode").change(function(){
					var form = document.forms["searchForm"];
					form.submit();
				});
				
				$("#positionCode").change(function(){
					var form = document.forms["searchForm"];
					form.submit();
				});
				
				$("#btnToggle").click(function(){
					var checked = $(this).prop("checked");
					$("#cameraTipListContainer input[type=checkbox]").each(function(index){
						$(this).prop("checked", checked);
					});
				});
				
				$("#cameraTipListContainer tr td[data-open-yn]").each(function(index) {

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
	 							$("#cameraTipListContainer tr td[data-open-yn]").each(function(index) {
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
					var form = document.forms["searchForm"];
					form.submit();
				});
				
				$("#btnRemove").click(function() {
					
					if(!confirm('삭제 하시겠습니까?')) return false;
					
					var pkList = "";
					$("#cameraTipListContainer tr input[type=checkbox]").each(function(index) {
						if($(this).prop("checked")) {
							pkList += $(this).parent().parent().attr("data-pk") + ",";
						}
					});	
					
					if(pkList == "") {
						alert("삭제할 코치가이드를 선택하여 주십시오.");
						return false;
					} 
						
					pkList = pkList.substring(0, pkList.length -1);
					
					$.ajax({
						type : "POST",
					    cache: false,						
						url : "/guide/removeCameraTip",
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
					location.href = "/guide/cameraTipRegister?page=" + page + "&searchColumn=" + searchColumn + "&searchString=" + searchString + "&cpCode=" + cpCode + "&positionCode=" + positionCode;
				});
				
			});
			
			function updateOpenYN(pk, yn, cpCode, verNum) {
				
				$.ajax({
					type : "GET",
				    cache: false,						
					url : "/guide/updateCameraTipOpenYN",
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
					console.log(jqXHR);  
				});
			}
			
		</script>	
		
	</jsp:attribute>

	<jsp:body>
		<form name="searchForm" method="get" action="/guide/cameraTipList">
		<div class="boardTitle">
			<div class="borderTitle">
				<span class="pageTitle">카메라 설정 Tip</span>
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
			<caption>카메라설정팁에 제목, 가이드위치, 앱버전, 작성자, 노출여부를 확인 할수 있는 표</caption>
			<colgroup>
				<col style="width:5%" />
				<col style="width:5%" />
				<col style="width:10%" />
				<col style="width:*%" />
				<col style="width:15%" />
				<col style="width:15%" />
				<col style="width:15%" />
				<col style="width:10%" />
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
			
			<tbody id="cameraTipListContainer">
			
			<!-- DYNAMIC AREA 'list' -->
			<c:if test="${!empty cameraTipList}">
				<c:forEach var="cameraTip" items="${cameraTipList}" varStatus="status" >
				<tr data-pk="${cameraTip.pk}" data-cp-code="${cameraTip.cpCode}" data-ver-num="${cameraTip.verNum}" onmouseover="this.style.backgroundColor='#f7f7f7'" onmouseout="this.style.backgroundColor=''" bgcolor="white">
					<td><input type="checkbox" name="" value=""></td>
					<td><fmt:formatNumber value="${pageNavi.totalCount - ((pageNavi.nowPage - 1) * pageNavi.pageSize + status.index) }" pattern="#,###"/></td>
					<td>${cameraTip.cpName }</td>
					<td>
						<a href="/guide/cameraTipModify?pk=${cameraTip.pk }&page=${param.page}&searchColumn=${param.searchColumn}&searchString=${param.searchString}&cpCode=${param.cpCode}" onfocus="this.blur();">
							${cameraTip.verNum}
						</a>
					</td>
					<td>${cameraTip.adminName}</td>
					<td>
						<fmt:parseDate value="${cameraTip.regDate}" var="dateFmt" pattern="yyyyMMdd"/>
						<fmt:formatDate value="${dateFmt}"  pattern="yyyy.MM.dd"/>
					</td>
					<td data-open-yn="${cameraTip.openYN}">
						<select name="openYN">
							<option value="Y">Y</option>
							<option value="N">N</option>
						</select>
					</td>
				</tr>
				</c:forEach>
			</c:if>			
	
			<c:if test="${empty cameraTipList}">
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