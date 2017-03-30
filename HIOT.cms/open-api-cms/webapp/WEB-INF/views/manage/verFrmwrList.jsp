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
			$(document).ready(function() {
				
				$("#cpCode").val(cpCode);
				
				$("#cpCode").change(function(){
					var form = document.forms["searchForm"];
					form.submit();
				});
				
				$("#btnToggle").click(function(){
					var checked = $(this).prop("checked");
					$("#verFrmwrListContainer input[type=checkbox]").each(function(index){
						$(this).prop("checked", checked);
					});
				});
							

				$('#proc').click(function(){
					var form = document.forms["searchForm"];
					form.submit();
				});
				
				$("#btnRemove").click(function() {
					var pkList = "";
					$("#verFrmwrListContainer tr input[type=checkbox]").each(function(index) {
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
						url : "/manage/removeVerFrmwr",
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
					location.href = "/manage/verFrmwrRegister?page=" + page + "&searchColumn=" + searchColumn + "&searchString=" + searchString;
				});				
			});
			
			// 정렬 추가 가능 
 			/* function goSort(){							
				var frm = document.sortForm;
				if(frm.proc.value == ''){
					frm.proc.value = 'up';
				}else if(frm.proc.value == 'up'){
					frm.proc.value = 'down';
				}else if(frm.proc.value == 'down'){
					frm.proc.value = 'up';
				}
				frm.submit();
			}  */
			
		</script>	
		
	</jsp:attribute>

	<jsp:body>
	
		<form name="searchForm" method="get" action="/manage/verFrmwrList">
		<div class="boardTitle">
				<div class="borderTitle">
					<span class="pageTitle">단말펌웨어 버전 관리</span>
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
			<caption>단말 펌웨어 버전 조회 결과의 제목, 게시시간, 작성자, 노출여부를 확인 할수 있는 표</caption>
			<colgroup>
				<col style="width:5%" />
				<col style="width:15%" />
				<col style="width:*%" />
				<col style="width:15%" />
				<col style="width:15%" />
				<col style="width:15%" />
				<col style="width:10%" />
			</colgroup>
			<form id="sortForm" name="sortForm" method="post" action="/manage/verFrmwrList">
				<input type="hidden" name= "proc" value="${param.proc}"/>
			</form>
			<thead>
				<tr>
					<th scope="col">NO</th>
					<th scope="col">상품명</th>
					<th scope="col">모델명</th>
					<th scope="col">버전</th>
					<th scope="col">업데이트 필수 여부</th>
					<th scope="col">등록자</th>
					<th scope="col">등록일</th>
				</tr>
			</thead>
			
			<tbody id="verFrmwrListContainer">
			
			<!-- DYNAMIC AREA 'list' -->
			<c:if test="${!empty verFrmwrList}">
				<c:forEach var="verFrmwr" items="${verFrmwrList}" varStatus="status" >
				<tr data-pk="${verFrmwr.pk}" onmouseover="this.style.backgroundColor='#f7f7f7'" onmouseout="this.style.backgroundColor=''" bgcolor="white">
					<td><fmt:formatNumber value="${pageNavi.totalCount - ((pageNavi.nowPage - 1) * pageNavi.pageSize + status.index) }" pattern="#,###"/></td>
					<td>
							${verFrmwr.cpName}
					</td>
					<td>
					&nbsp;<a href="/manage/verFrmwrModify?pk=${verFrmwr.pk }&page=${param.page}&cpCode=${param.cpCode}" onfocus="this.blur();">${verFrmwr.modelName}</a>
					</td>
					<td>${verFrmwr.verNum}</td>
					<td>${verFrmwr.mandatoryYN}</td>
					<td>${verFrmwr.adminName}</td>
					<td><fmt:formatDate value="${verFrmwr.regDate}" pattern="yyyy-MM-dd"/></td>
				</tr>
				</c:forEach>
			</c:if>			
	
			<c:if test="${empty verFrmwrList}">
				<tr> 	
					<td align="center" colspan="7">검색된 데이타가 없습니다</td>
				</tr>
				
			</c:if>
			</tbody>
		</table>
		
		${pageNavi.navi}
		
		<div class="btns clearfix">
			<div class="flr">
				<a id="btnWrite" class="btn2 btn2_red30" style="cursor:pointer"><span>등록</span></a>
			</div>
		</div>	
		
	</jsp:body>
</layout:main>