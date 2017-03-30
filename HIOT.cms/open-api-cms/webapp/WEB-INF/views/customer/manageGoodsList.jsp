<%@ page contentType="text/html" trimDirectiveWhitespaces="true" pageEncoding="UTF-8"%>
<%@ taglib prefix="layout" 	tagdir="/WEB-INF/tags/layout" %>
<%@ taglib prefix="c"  		uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" 	uri="http://java.sun.com/jsp/jstl/fmt" %>

<layout:main>

	<jsp:attribute name="javascript">
	
		<script type="text/javascript">
			
			var cpCode = "${param.cpCode}";
			var page = "${param.page}"; 
			var searchColumn = "${param.searchColumn}";
			var searchString = "${param.searchString}";
			
			$(document).ready(function() {
				
				$("#cpCode").val(cpCode);
				$("#searchColumn").val(searchColumn);
				
				$("#cpCode").change(function(){
					var form = document.forms["searchForm"];
					form.submit();
				});
				
				$("#btnSearch").click(function() {
					var form = document.forms["searchForm"];
					form.submit();
				});
				
				$("#btnToggle").click(function(){
					var checked = $(this).prop("checked");
					$("#manageGoodsListContainer input[type=checkbox]").each(function(index){
						$(this).prop("checked", checked);
					});
				});
				
				$("#btnRemove").click(function() {
					if (confirm(" 삭제하시겠습니까?") == true){
						var pkList = "";
						$("#manageGoodsListContainer tr input[type=checkbox]").each(function(index) {
							if($(this).prop("checked")) {
								pkList += $(this).parent().parent().attr("data-pk") + ",";
							}
						});	
						
						if(pkList == "") {
							alert("삭제할 공지사항을 선택하여 주십시오.");
							return false;
						} 
							
						pkList = pkList.substring(0, pkList.length -1);
						$.ajax({
							type : "POST",
						    cache: false,						
							url : "/customer/removeManageGoods",
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
					}else{   
					    return;
					}
				});
				
				$("#btnWrite").click(function(){
					location.href = "/customer/manageGoodsRegister?page=" + page + "&searchColumn=" + searchColumn + "&searchString=" + searchString;
				});				
			});
			
			
		</script>	
		
	</jsp:attribute>

	<jsp:body>
	
		<form name="searchForm" method="get" action="/customer/manageGoodsList">
			<div class="boardTitle">
				<div class="borderTitle">
					<span class="pageTitle">상품별 기능 관리</span>
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
						<option value="name">기능명</option>
						<option value="adminName">작성자</option>
					</select>
					<input type="text" id="searchString" name="searchString"  title="검색어를 입력하세요" value="${param.searchString }" />
					<button type="button" id="btnSearch" class="btn"><span>검색</span></button>
				</div>
			</div>
		</form>
		
		<table class="board mt10">
			<colgroup>
				<col style="width:5%" />
				<col style="width:5%" />
				<col style="width:25%" />
				<col style="width:*" />
				<col style="width:25%" />
				<col style="width:10%" />
			</colgroup>
			<thead>
				<tr>
				    <th scope="col"><input type="checkbox" id="btnToggle" /></th> 
					<th scope="col">NO</th>
					<th scope="col">상품명</th>
					<th scope="col">기능명</th>
					<th scope="col">작성자</th>
					<th scope="col">등록일</th>
				</tr>
			</thead>
			
			<tbody id="manageGoodsListContainer">
			
			<!-- DYNAMIC AREA 'list' -->
 			<c:if test="${!empty manageGoodsList}">
				<c:forEach var="manageGoods" items="${manageGoodsList}" varStatus="status" >
				<tr data-pk="${manageGoods.pk}" data-cp-code="${manageGoods.cpCode}" onmouseover="this.style.backgroundColor='#f7f7f7'" onmouseout="this.style.backgroundColor=''" bgcolor="white">
					<td><input type="checkbox" name="" value=""></td>
					<td><fmt:formatNumber value="${pageNavi.totalCount - ((pageNavi.nowPage - 1) * pageNavi.pageSize + status.index) }" pattern="#,###"/></td>
					<td>${manageGoods.cpName}</td>
					<td>
					&nbsp;<a href="/customer/manageGoodsModify?pk=${manageGoods.pk }&page=${param.page}&searchColumn=${param.searchColumn}&searchString=${param.searchString}&cpCode=${param.cpCode}" onfocus="this.blur();">${manageGoods.name}</a>
					</td>
					<td>${manageGoods.adminName}</td>
					<td><fmt:formatDate value="${manageGoods.regDate}" pattern="yyyy-MM-dd"/></td>
				</tr>
				</c:forEach>
			</c:if>		 		
			<c:if test="${empty manageGoodsList}">
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