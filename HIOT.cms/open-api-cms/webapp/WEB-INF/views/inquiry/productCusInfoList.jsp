<%@ page contentType="text/html" trimDirectiveWhitespaces="true" pageEncoding="UTF-8"%>
<%@ taglib prefix="layout" 	tagdir="/WEB-INF/tags/layout" %>
<%@ taglib prefix="c"  		uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" 	uri="http://java.sun.com/jsp/jstl/fmt" %>

<layout:main>

	<jsp:attribute name="javascript">
	
		<script type="text/javascript">
			
			var page = "${param.page}"; 
			var cpCode = "${param.cpCode}";
			var status = "${param.status}";
			var searchColumn = "${param.searchColumn}";

			$(document).ready(function() {
				
				$("#cpCode").val(cpCode);
				$("#status").val(status);
				$("#searchColumn").val(searchColumn);
				
				$("#cpCode").change(function(){
					
					//맥어드레스 검색일 경우 자동으로 포멧 변환
					if( $("#searchColumn").val() == "dev_cctc_mac"){
						str = $("#searchString").val();
						$("#searchString").val( str.replace( /:/gi ,"" ).replace( /(\w)(?=(?:\w{2})+(?!\w))/gi, '$1:') );
					}
					
					var form = document.forms["searchForm"];
					form.submit();
				});
				
				$("#status").change(function(){
					
					//맥어드레스 검색일 경우 자동으로 포멧 변환
					if( $("#searchColumn").val() == "dev_cctc_mac"){
						str = $("#searchString").val();
						$("#searchString").val( str.replace( /:/gi ,"" ).replace( /(\w)(?=(?:\w{2})+(?!\w))/gi, '$1:') );
					}
					
					var form = document.forms["searchForm"];
					form.submit();
				});
				
				$("#btnSearch").click(function() {
					
					//맥어드레스 검색일 경우 자동으로 포멧 변환
					if( $("#searchColumn").val() == "dev_cctc_mac"){
						str = $("#searchString").val();
						$("#searchString").val( str.replace( /:/gi ,"" ).replace( /(\w)(?=(?:\w{2})+(?!\w))/gi, '$1:') );
					}
					
					var form = document.forms["searchForm"];
					form.submit();
				});
			});
			
		</script>	
		
		
		
		
	</jsp:attribute>

	<jsp:body>
		<form name="searchForm" method="get" action="/inquiry/productCusInfoList">
		<div class="boardTitle">
			<div class="borderTitle">
				<span class="pageTitle">고객별 가입 상품 조회</span>
				<select id="cpCode" name="cpCode" title="검색조건을 선택하세요" style="float:right;">
					<option value="">전체</option>
					<c:forEach var="svc" items="${loginInfo.svcList }">
					<option value="${svc.cpCode }">${svc.cpName }</option>
					</c:forEach>
				</select>
			</div>
		</div>
		
		<div class="boardTop">
			<select id="status" name="status" style="float:left;">
				<option value="">전체</option>
				<option value="0001">사용중</option>
				<option value="0002">이용중지</option>
				<option value="0003">해지</option>
			</select>
			<div class="flr">
				<select id="searchColumn" name="searchColumn" title="검색조건을 선택하세요">
					<option value="mbr_id">고객ID</option>
					<option value="tel_no">휴대폰번호</option>
					<option value="dev_cctc_mac">카메라 mac</option>
				</select>
				<input type="text" id="searchString" name="searchString"  title="검색어를 입력하세요" value="${param.searchString }" />
				<button type="button" id="btnSearch" class="btn"><span>검색</span></button>
			</div>
		</div>		
		</form>
		<table class="board mt10">
			<caption>서비스 이용약관에 제목, 버전, 게시일을 확인 할수 있는 표</caption>
			<colgroup>
				<col style="width:5%" />
				<col style="width:20%" />
				<col style="width:*" />
				<col style="width:15%" />
				<col style="width:15%" />
				<col style="width:10%" />
				<col style="width:10%" />
			</colgroup>
			<thead>
				<tr>
					<th scope="col">NO</th>
					<th scope="col">고객ID</th>
					<th scope="col">휴대폰번호</th>
					<th scope="col">서비스명</th>
					<th scope="col">서비스일련번호</th>
					<th scope="col">상태</th>
					<th scope="col">가입일</th>
				</tr>
			</thead>
			
			<tbody id="serviceTermsListContainer">
			
			<!-- DYNAMIC AREA 'list' -->
			<c:if test="${!empty searchList}">
				<c:forEach var="search" items="${searchList}" varStatus="status" >
				<tr onmouseover="this.style.backgroundColor='#f7f7f7'" onmouseout="this.style.backgroundColor=''" bgcolor="white">
					<td><fmt:formatNumber value="${pageNavi.totalCount - ((pageNavi.nowPage - 1) * pageNavi.pageSize + status.index) }" pattern="#,###"/></td>
					<td>${search.mbrId}</td>
					<td>
						<c:choose>
							<c:when test="${search.svcTgtSeq==null || search.unitSvcCd==null || search.mbrSeq==null}">
								&nbsp;<a href="#" onclick="alert('고객정보가 없습니다.');" onfocus="this.blur();">${search.telNo}</a>
							</c:when>
							<c:otherwise>
								&nbsp;<a href="/inquiry/productCusInfoDetail?mbrSeq=${search.mbrSeq}&page=${param.page }&cpCode=${param.cpCode}&status=${param.status}&searchColumn=${param.searchColumn}&searchString=${param.searchString}" onfocus="this.blur();">${search.telNo}</a>
							</c:otherwise>
						</c:choose>
					</td>
					<td>${search.unitSvcNm}</td>
					<td>${search.svcTgtSeq}</td>
					<td>
						<c:choose>
							<c:when test="${search.oprtSttusCd=='0001'}">사용중</c:when>
							<c:when test="${search.oprtSttusCd=='0002'}">이용중지</c:when>
							<c:when test="${search.oprtSttusCd=='0003'}">해지</c:when>
							<c:otherwise></c:otherwise>
						</c:choose>
					</td>
					<td>${search.cretDt}</td>					
				</tr>
				</c:forEach>
			</c:if>			
	
			<c:if test="${empty searchList}">
				<tr> 	
					<td align="center" colspan="6">검색된 데이타가 없습니다</td>
				</tr>
			</c:if>
			</tbody>
		</table>
		
		${pageNavi.navi}
		
	</jsp:body>
</layout:main>
