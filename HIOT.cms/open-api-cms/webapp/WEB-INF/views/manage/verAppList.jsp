<%@ page contentType="text/html" trimDirectiveWhitespaces="true" pageEncoding="UTF-8"%>
<%@ taglib prefix="layout" 	tagdir="/WEB-INF/tags/layout" %>
<%@ taglib prefix="c"  		uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" 	uri="http://java.sun.com/jsp/jstl/fmt" %>

<layout:main>

	<jsp:attribute name="javascript">
	
		<script type="text/javascript">
			var cpCode = "${param.cpCode}";
			var osCode = "${param.osCode}"; 
			var page = "${param.page}"; 
			
			$(document).ready(function() {
				
				$("#cpCode").val(cpCode);
				$("#osCode").val(osCode);
				
				$("#osCode").change(function(){
					var form = document.forms["searchForm"];
					form.proc.value = '';
					form.submit();
				});
				
				$("#cpCode").change(function(){
					var form = document.forms["searchForm"];
					form.proc.value = '';
					form.submit();
				});
				
				$("#btnToggle").click(function(){
					var checked = $(this).prop("checked");
					$("#verAppListContainer input[type=checkbox]").each(function(index){
						$(this).prop("checked", checked);
					});
				});
				
				$("#btnSearch").click(function() {
					var form = document.forms["searchForm"];
					form.proc.value = '';
					form.submit();
				});
				
				$("#btnExcel").click(function() {
					//chkEmpty: "true" or "false"
					var chkEmpty = "${empty verAppList}";
					
					//verAppList가 존재할 경우
					if(chkEmpty == "false"){
						var form = document.forms["searchForm"];
						form.proc.value = 'excel';
						form.submit();
					}				
					else{ //verAppList가 존재 하지 않을 경우
						alert("데이타가 없습니다.");
					}
				});
				
				$("#btnWrite").click(function(){
					location.href = "/manage/verAppRegister?page=" + page;
				});
				
			});
			
		</script>	
		
	</jsp:attribute>
	<jsp:body>
		<form name="searchForm" method="get" action="/manage/verAppList">
			<input type="hidden" name="proc" value="" />
			<div class="borderTitle" style="margin-bottom: 5px;">
				<span class="pageTitle">APP 버전 관리</span>
				<select id="cpCode" name="cpCode" title="검색조건을 선택하세요" style="float:right;">
					<option value="">전체</option>
					<c:forEach var="svc" items="${loginInfo.svcList }">
					<option value="${svc.cpCode }">${svc.cpName }</option>
					</c:forEach>
				</select>
				</div>
				<div class="fll">
					<select id="osCode" name= "osCode">
						<option value="">전체</option>
						<c:forEach var="osCode" items="${osList}" varStatus="status" >
							<option value="${osCode.code}">${osCode.name}</option>
						</c:forEach>
					</select>
				</div>
			</form>
			<div class="flr" style="margin-bottom: 5px;">
				<a id="btnExcel" class="btn2 btn2_black30" style="cursor:pointer"><span>엑셀 다운로드</span></a>
			</div>

		<table class="board mt10">
			<caption>app 버전 조회 결과의 제목, 게시시간, 작성자, 노출여부를 확인 할수 있는 표</caption>
			<colgroup>
				<col style="width:5%" />
				<col style="width:15%" />
				<col style="width:15%" />
				<col style="width:*" />
				<col style="width:15%" />
				<col style="width:15%" />
				<col style="width:10%" />
			</colgroup>
			<thead>
				<tr>
					<th scope="col">NO</th>
					<th scope="col">상품명</th>
					<th scope="col">OS</th>
					<th scope="col">APP 버전</th>
					<th scope="col">단말</th>
					<th scope="col">업데이트 필수 여부</th>
					<th scope="col">등록일</th>
				</tr>
			</thead>
			
			<tbody id="verAppListContainer">
			
			<!-- DYNAMIC AREA 'list' -->
			<c:if test="${!empty verAppList}">
				<c:forEach var="verApp" items="${verAppList}" varStatus="status" >
				<tr data-pk="${verApp.pk}" onmouseover="this.style.backgroundColor='#f7f7f7'" onmouseout="this.style.backgroundColor=''" bgcolor="white">
					<!-- <td><input type="checkbox" name="" value=""></td> -->
					<td><fmt:formatNumber value="${pageNavi.totalCount - ((pageNavi.nowPage - 1) * pageNavi.pageSize + status.index) }" pattern="#,###"/></td>										
					<td>${verApp.cpName}</td>
					<td>
						<c:forEach var="osCode" items="${osList}" varStatus="status" >
							<c:if test="${verApp.osCode == osCode.code}">${osCode.name}</c:if>
						</c:forEach>
					</td>
					<td><a href="/manage/verAppModify?pk=${verApp.pk }&page=${param.page}&cpCode=${param.cpCode}&osCode=${param.osCode}&mandatoryYN=${verApp.mandatoryYN}" onfocus="this.blur();">${verApp.verNum}</a></td>
					<td>
						<c:forEach var="phCode" items="${phList}" varStatus="status" >
							<c:if test="${verApp.phCode == phCode.code}">${phCode.name}</c:if>
						</c:forEach>
					</td>
					<td>
						<c:choose>
							<c:when test="${verApp.mandatoryYN == 'Y' }">필수</c:when>
							<c:when test="${verApp.mandatoryYN == 'N' }">선택</c:when>
						</c:choose>
					</td>
					<td><fmt:formatDate value="${verApp.regDate}" pattern="yyyy-MM-dd"/></td>
				</c:forEach>
			</c:if>			
	
			<c:if test="${empty verAppList}">
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