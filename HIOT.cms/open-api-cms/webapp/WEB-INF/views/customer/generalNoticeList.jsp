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
				
				$("#openYnSelect").val('${param.openYnSelect}');
				
				$("#openYnSelect").change(function(){
					var form = document.forms["searchForm"];
					form.submit();
				});
				
				$("#cpCode").change(function(){
					var form = document.forms["searchForm"];
					form.submit();
				});
				
				$("#btnToggle").click(function(){
					var checked = $(this).prop("checked");
					$("#noticeListContainer input[type=checkbox]").each(function(index){
						$(this).prop("checked", checked);
					});
				});
				
				$("#noticeListContainer tr td[data-open-yn]").each(function(index) {
					
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
				
				$("#noticeListContainer tr td img[data-preorder-yn]").each(function(index) {
					$(this).click(function() {
						var preorderYN = "";
						var pk = $(this).parent().parent().attr("data-pk");
						var targetCpCode = $(this).parent().parent().attr("data-cp-code");
							
						if($(this).attr("data-preorder-yn") == "Y") {
							preorderYN = "N";
							$(this).attr("src", "/resources/images/icon/icon_star_off.png").attr("data-preorder-yn", "N");
						} else {
							preorderYN = 'Y';
							$(this).attr("src", "/resources/images/icon/icon_star_on.png").attr("data-preorder-yn", "Y");
						}
						
						updatePreorderYN(pk, preorderYN, targetCpCode);
					});
				});
				
				$("#btnSearch").click(function() {
					var form = document.forms["searchForm"];
					form.submit();
				});
				
				$("#btnRemove").click(function() {
					if (confirm(" 삭제하시겠습니까?") == true){
						var pkList = "";
						$("#noticeListContainer tr input[type=checkbox]").each(function(index) {
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
							url : "/customer/removeGeneralNotice",
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
					location.href = "/customer/generalNoticeRegister?page=" + page + "&searchColumn=" + searchColumn + "&searchString=" + searchString;
				});
				
			});
			
			function updateOpenYN(pk, yn) {
				
				$.ajax({
					type : "GET",
				    cache: false,						
					url : "/customer/updateGeneralNoticeOpenYN",
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
			
			function updatePreorderYN(pk, yn, targetCpCode) {
				$.ajax({
					type : "GET",
				    cache: false,						
					url : "/customer/updateGeneralNoticePreorderYN",
					dataType : "json",
					data : { "pk" : pk, "preorderYN" : yn, "cpCode" : targetCpCode}
				})	
				.done(function(result){
					switch(result.code) {
						case 200:
							if(yn == "Y") {
								alert("중요표시가 체크되었습니다.\n선택된 공지는 공지사항 상단에 노출됩니다.");
							}
							location.reload();
							break;
						case 400:
							alert("중요 공지는 3개 이상 등록 할 수 없습니다.");
							location.reload();
							break;
						case 500:
							alert("서버에서 오류가 발생하였습니다.\n다시 시도하여 주십시오.");
							location.reload();
							break;
					}
				})
				.fail(function( jqXHR, textStatus, errorThrown){
					console.log(jqXHR);
				});					
			}			
			
		</script>	
		
	</jsp:attribute>

	<jsp:body>
	<form name="searchForm" method="get" action="/customer/generalNoticeList">
	
		<div class="boardTitle">
			<div class="borderTitle">
				<span class="pageTitle">일반공지사항</span>
				<select id="cpCode" name="cpCode" title="검색조건을 선택하세요" style="float:right;">
					<option value="">전체</option>
					<c:forEach var="svc" items="${loginInfo.svcList }">
					<option value="${svc.cpCode }">${svc.cpName }</option>
					</c:forEach>
				</select>
			</div>
		</div>
		
		<div class="boardTop">
			<div class="fll">
				<select id="openYnSelect" name="openYnSelect" title="노출 비노출 결정">
					<option value=""> 전체 </option>
					<option value="Y"> 노출 </option>
					<option value="N"> 비 노출 </option>					
				</select>
			</div>	
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
			<caption>PM공지 조회 결과의 제목, 게시시간, 작성자, 노출여부를 확인 할수 있는 표</caption>
			<colgroup>
				<col style="width:5%" />
				<col style="width:5%" />
				<col style="width:10%" />
				<col style="width:5%" />
				<col style="width:*%" />
				<col style="width:10%" />
				<col style="width:10%" />
				<col style="width:10%" />
			</colgroup>
			<thead>
				<tr>
					<th scope="col"><input type="checkbox" id="btnToggle" /></th>
					<th scope="col">번호</th>
					<th scope="col">상품명</th>
					<th scope="col">중요</th>
					<th scope="col">제목</th>
					<th scope="col">작성자</th>
					<th scope="col">등록일</th>
					<th scope="col">노출여부</th>
				</tr>
			</thead>
			
			<tbody id="noticeListContainer">
			
			<!-- DYNAMIC AREA 'list' -->
			<c:if test="${!empty noticeList}">
				<c:forEach var="notice" items="${noticeList}" varStatus="status" >
				<tr data-pk="${notice.pk}" data-cp-code="${notice.cpCode}" onmouseover="this.style.backgroundColor='#f7f7f7'" onmouseout="this.style.backgroundColor=''" bgcolor="white">
					<td><input type="checkbox" name="" value=""></td>
					<td><fmt:formatNumber value="${pageNavi.totalCount - ((pageNavi.nowPage - 1) * pageNavi.pageSize + status.index) }" pattern="#,###"/></td>
					<td>${notice.cpName}</td>
					<td>
						<c:if test="${notice.preorderYN == 'Y'}">
							<img src="/resources/images/icon/icon_star_on.png" style="width:20px;cursor:pointer" data-preorder-yn="Y" />	
						</c:if>
						<c:if test="${notice.preorderYN == 'N'}">
							<img src="/resources/images/icon/icon_star_off.png" style="width:20px;cursor:pointer" data-preorder-yn="N" />	
						</c:if>						
					</td>
					<td style="text-align:left;">
						&nbsp;<a href="/customer/generalNoticeModify?pk=${notice.pk }&page=${param.page}&searchColumn=${param.searchColumn}&searchString=${param.searchString}&cpCode=${param.cpCode}" onfocus="this.blur();">${notice.title}</a>
					</td>
					<td>${notice.adminName}</td>
					<td><fmt:formatDate value="${notice.regDate}" pattern="yyyy-MM-dd"/></td>
					<td data-open-yn="${notice.openYN}">
						<select name="openYN">
							<option value="Y">Y</option>
							<option value="N">N</option>
						</select>
					</td>
				</tr>
				</c:forEach>
			</c:if>			
	
			<c:if test="${empty noticeList}">
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