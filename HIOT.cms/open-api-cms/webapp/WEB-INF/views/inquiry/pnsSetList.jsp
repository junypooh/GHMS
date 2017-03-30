<%@ page contentType="text/html" trimDirectiveWhitespaces="true" pageEncoding="UTF-8"%>
<%@ taglib prefix="layout" 	tagdir="/WEB-INF/tags/layout" %>
<%@ taglib prefix="c"  		uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" 	uri="http://java.sun.com/jsp/jstl/fmt" %>

<layout:main>

	<jsp:attribute name="javascript">
	
		<script type="text/javascript">			
			var cpCode   = "${param.cpCode}";
 			var searchColumn = "${param.searchColumn}";
 			var pushColumn = "${param.pushColumn}"; 
	 	
			$(document).ready(function() {
				
 				$('#cpCode').val(cpCode); 
				$("#searchColumn").val(searchColumn);
				$("#pushColumn").val(pushColumn);
				
				$('#cpCode, #pushColumn').change(function() {
					var form = document.forms["searchForm"];
					form.submit();		
				});
				
				$("#btnSearch").click(function() {
					var form = document.forms["searchForm"];
					form.submit();
				});
			});
			
			function openPopup(unitSvcCd, telNo, mbrSeq, mbrId, connTermlId,statEvetCd,setupVal){
				
				var width = "750";
				var height = "580";
				var leftPos = (screen.availWidth - width)/2;
				var topPos = (screen.availHeight - height)/2;
				var position = "width="+ width + ", height=" + height + ", left=" + leftPos + ", top=" + topPos;
				var url = "/inquiry/pnsSetDetail?" 	+ "page=1" 
													+ "&unitSvcCd=" + unitSvcCd 
													+ "&telNo=" + telNo 
													+ "&mbrSeq=" + mbrSeq
													+ "&mbrId=" + mbrId
													+ "&connTermlId=" + connTermlId
													+ "&statEvetCd=" + statEvetCd
													+ "&setupVal=" + setupVal;
				window.open(url, "winOpen", position, "menubar=1");
			}

		</script>	
		
	</jsp:attribute>

	<jsp:body>
	
		<form name="searchForm" method="get" action="/inquiry/pnsSetList">
		<div class="boardTitle">
			<div class="borderTitle">
				<span class="pageTitle">PNS 발송 설정 조회</span>
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
				<!-- 
				<select id="pushColumn" name="pushColumn" >
					<option value="">푸시 전체</option>
					<c:forEach var="ent" items="${getEntList}" varStatus="status" >
						<option value="${ent.unitEntCd}">${ent.unitEntNm}</option>
					</c:forEach>
				</select>
				 -->
			</div>		
			<div class="flr">
				<select id="searchColumn" name="searchColumn" title="검색조건을 선택하세요">
					<!-- <option value="mbrId">고객ID</option>  -->
					<option value="telNo">휴대폰번호</option>
				</select>
				<input type="text" id="searchString" name="searchString"  title="검색어를 입력하세요" value="${param.searchString }" />
				<button type="button" id="btnSearch" class="btn"><span>검색</span></button>
			</div>
		</div>
		</form>
		
		<table class="board mt10">
			<caption>PNS발송 설정조회의 제목, 게시시간, 작성자, 노출여부를 확인 할수 있는 표</caption>
			<colgroup>
				<col style="width:5%" />
				<col style="width:15%" />
				<col style="width:15%" />
				<col style="width:15%" />
				<col style="width:*" />
				<col style="width:30%" />
			</colgroup>
			<thead>
				<tr>
					<th scope="col">NO</th>
					<th scope="col">고객ID</th>
					<th scope="col">휴대폰번호</th>
					<th scope="col">상품명</th>
					<th scope="col">푸시설정</th>
					<th scope="col">설정값</th>
				</tr>
			</thead>
			
			<tbody id="noticeListContainer">
			
			<!-- DYNAMIC AREA 'list' -->
 			<c:if test="${!empty pnsSetListEx}">
				<c:forEach var="pnsSet" items="${pnsSetListEx}" varStatus="status" >
					<tr onmouseover="this.style.backgroundColor='#f7f7f7'" onmouseout="this.style.backgroundColor=''" bgcolor="white">
						<td><fmt:formatNumber value="${pageNavi.totalCount - ((pageNavi.nowPage - 1) * pageNavi.pageSize + status.index) }" pattern="#,###"/></td>
						<td>${pnsSet.mbrId}</td>
						<td>${pnsSet.telNo}</td>
						<td>${pnsSet.unitSvcNm}</td>
						<td>
							<c:if test="${!empty pnsSet.setupVal}">
								<c:forEach var="setupVal" items="${pnsSet.setupVal}" varStatus="status" >
									<%-- <div style="border-bottom: 1px solid #b7b7b7;">${setupVal.statEvetNm}</div> --%>
									<div align="left">${setupVal.statEvetNm}</div>
								</c:forEach>
							</c:if>
						</td>
						<td>
							<c:if test="${!empty pnsSet.setupVal}">
								<c:forEach var="setupVal" items="${pnsSet.setupVal}" varStatus="status" >
									<%-- <div style="border-bottom: 1px solid #b7b7b7;">${setupVal.setupVal}</div> --%>
									<div>${setupVal.setupVal}</div>
								</c:forEach>
							</c:if>
						</td>
					</tr>
				</c:forEach>
			</c:if>			
	
			<c:if test="${empty pnsSetListEx}">
				<tr> 	
					<td align="center" colspan="7">검색된 데이타가 없습니다</td>
				</tr>
			</c:if>
			 
			 
			</tbody>
		</table>
		
		${pageNavi.navi}
		
	</jsp:body>
</layout:main>