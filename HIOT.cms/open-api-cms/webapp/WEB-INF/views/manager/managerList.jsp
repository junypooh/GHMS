<%@ page contentType="text/html" trimDirectiveWhitespaces="true" pageEncoding="UTF-8"%>
<%@ taglib prefix="layout" 	tagdir="/WEB-INF/tags/layout" %>
<%@ taglib prefix="c"  		uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" 	uri="http://java.sun.com/jsp/jstl/fmt" %>

<layout:main>

	<jsp:attribute name="javascript">
	
		<script type="text/javascript">
		
			var page = "${param.page}"; 
			var searchRole = "${param.searchRole}";
			var searchColumn = "${param.searchColumn}";
			var searchString = "${param.searchString}";		
		
			$(document).ready(function() {
				

				$("#searchRole").val(searchRole);
				$("#searchColumn").val(searchColumn);
				
				$("#btnToggle").click(function(){
					var checked = $(this).prop("checked");
					$("#managerListContainer input[type=checkbox]").each(function(index){
						$(this).prop("checked", checked);
					});
				});	
				
				$("#searchRole").change(function(){
					var form = document.forms["searchForm"];
					form.submit();
				});
				
				$("#btnRemove").click(function() {
					if (confirm("정말로 삭제하시겠습니까?") == true){	
						remove();
					}
				});				
				
				$("#btnSearch").click(function() {
					var form = document.forms["searchForm"];
					form.submit();
				});				
				
				$("#btnWrite").click(function(){
					location.href = "/manager/managerRegister";
				});
				
			});
			
			function view(id) {
				var form = document.forms["searchForm"];
				form.action = "/manager/managerModify";
				form.page.value = page;
				form.id.value = id;
				form.submit();
			}
			
			function remove() {
				var managerList = "";
				$("#managerListContainer tr input[type=checkbox]").each(function(index) {
					if($(this).prop("checked")) {
						managerList += $(this).parent().parent().attr("data-pk") + ",";
					}
				});	
				
				if(managerList == "") {
					alert("삭제할 관리자를 선택하여 주십시오.");
					return false;
				} 
					
				managerList = managerList.substring(0, managerList.length -1);
				
				$.ajax({
					type : "POST",
				    cache: false,						
					url : "/manager/removeManager",
					dataType : "json",
					data : { "managerList" : managerList}
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
		
		</script>	
		
	</jsp:attribute>

	<jsp:body>
	
		<div class="boardTitle">
			<span class="pageTitle">관리자 관리</span>
		</div>
		
			<form name="searchForm" method="get" action="/manager/managerList">
			<div class="boardTop">
			<input type="hidden" name="page" value="" />
			<input type="hidden" name="id" value="" />
			<select id="searchRole" name="searchRole" style="float:left;">
				<option value="0">역할 전체</option>
				<c:forEach var="role" items="${roleList }">
				<option value="${role.role }">${role.name }</option>
				</c:forEach>
			</select>
			<div class="flr">
				<select id="searchColumn" name="searchColumn" title="검색조건을 선택하세요">
					<option value="name">이름</option>
					<option value="id">아이디</option>
					<option value="mobile">핸드폰번호</option>
					<option value="email">이메일</option>
				</select>
				<input type="text" id="searchString" name="searchString"  title="검색어를 입력하세요" value="${param.searchString }" />
				<button type="button" id="btnSearch" class="btn"><span>검색</span></button>
			</div>
			</div>
			</form>
		
		<table class="board mt10">
			<caption>관리자 관리</caption>
			<colgroup>
				<col style="width:5%" />
				<col style="width:5%" />
				<col style="width:8%" />
				<col style="width:8%" />
				<col style="width:12%" />
				<col style="width:14%" />
				<col style="width:*" />
				<col style="width:12%" />
			</colgroup>
			<thead>
				<tr>
					<th scope="col"><input type="checkbox" id="btnToggle" /></th>
					<th scope="col">NO</th>
					<th scope="col">관리자 명</th>
					<th scope="col">아이디</th>
					<th scope="col">핸드폰 번호</th>
					<th scope="col">이메일</th>
					<th scope="col">관리상품</th>
					<th scope="col">역할</th>
				</tr>
			</thead>
			
			<tbody id="managerListContainer">
			<c:if test="${!empty managerList }">
				<c:forEach var="manager" items="${managerList }" varStatus="status">
				<tr data-pk="${manager.id }" onmouseover="this.style.backgroundColor='#f7f7f7'" onmouseout="this.style.backgroundColor=''" bgcolor="white">
					<td><input type="checkbox" name="" value=""></td>
					<td><fmt:formatNumber value="${pageNavi.totalCount - ((pageNavi.nowPage - 1) * pageNavi.pageSize + status.index) }" pattern="#,###"/></td>	
					<td><a href="#none" onfocus="this.blur()" onclick="view('${manager.id}');">${manager.name }</a></td>	
					<td><a href="#none" onfocus="this.blur()" onclick="view('${manager.id}');">${manager.id }</a></td>	
					<td><a href="#none" onfocus="this.blur()" onclick="view('${manager.id}');">${manager.mobile }</a></td>	
					<td><a href="#none" onfocus="this.blur()" onclick="view('${manager.id}');">${manager.email }</a></td>	
					<td>| <c:forEach var="svc" items="${manager.svcList}">&nbsp;${svc.cpName } |</c:forEach>
					</td>	
					<td>${manager.roleName }</td>	
				</tr>
				</c:forEach>
			</c:if>
			<c:if test="${empty managerList }">
				<tr>
					<td colspan="8">검색된 데이타가 없습니다</td>
				</tr>
			</c:if>
			</tbody>
		</table>
		
		${pageNavi.navi}
		
		<div class="btns clearfix">
			<div class="fll">
				<a id="btnRemove" class="btn2 btn2_black30" style="cursor:pointer" href= "#"><span>삭제</span></a>
			</div>
			<div class="flr">
				<a id="btnWrite" class="btn2 btn2_red30" style="cursor:pointer"><span>등록</span></a>
			</div>
		</div>	
		
	</jsp:body>
</layout:main>