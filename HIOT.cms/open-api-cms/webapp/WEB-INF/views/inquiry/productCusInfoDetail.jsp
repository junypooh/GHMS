<%@ page contentType="text/html" trimDirectiveWhitespaces="true" pageEncoding="UTF-8"%>
<%@ taglib prefix="layout" 	tagdir="/WEB-INF/tags/layout" %>
<%@ taglib prefix="c"  		uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" 	uri="http://java.sun.com/jsp/jstl/fmt" %>

<layout:main>

	<jsp:attribute name="javascript">
		<script type="text/javascript">
			${script}
			$(document).ready(function(){
				
				$("#btnList").click(function() {
					var form = document.forms["transForm"];
					form.submit();
				});
				
			});
			   
			function display(id){
				if($("#" + id).css("display") == 'block'){
					$("#" + id).hide();
				}else{
					$("#" + id).show();
				}
			}
		</script>
	
	</jsp:attribute>
	
	<jsp:body>
		<form name="transForm" method="get" action="/inquiry/productCusInfoList">
		<input type="hidden" name="page" value="${param.page }" />
		<input type="hidden" name="cpCode" value="${param.cpCode }" />
		<input type="hidden" name="status" value="${param.status }" />
		<input type="hidden" name="searchColumn" value="${param.searchColumn }" />
		<input type="hidden" name="searchString" value="${param.searchString }" />
		</form>
	
		<div class="boardTitle">
			<div class="borderTitle">
				<span class="pageTitle">상품별 고객정보 상세보기</span>
			</div>
		</div>

		<div style="text-align:left; font-weight:bold; font-size:16px;"><span>가입자 정보</span></div>
		<div>
			<table class="tbl_1">
				<caption></caption>
				<colgroup>
					<col style="width:25%" />
					<col style="width:75%" />
				</colgroup>
				<tbody>
					<tr>
						<th scope="row" style="border-top:none;">아이디</th>
						<td>${termlInfoList[0].mbrId}</td>					
					</tr>
					<tr>
						<th scope="row" style="border-top:none;"><label for="title">휴대폰정보</label></th>
						<td>
							<c:forEach var="termlInfo" items="${termlInfoList }">
								${termlInfo.telNo }&nbsp;&nbsp;&nbsp;
							</c:forEach>
						</td>
					</tr>
				</tbody>
			</table>
		</div>
		<div style="text-align:left; font-weight:bold; font-size:16px; padding-top:25px;"><span>기기 정보</span></div>
		<div>
		<c:if test="${!empty spotDevInfoList}">
			<c:forEach var="spotDevInfo" items="${spotDevInfoList}" varStatus="status" >		
			<table class="tbl_1">
				<caption></caption>
				<colgroup>
					<col style="width:25%" />
					<col style="width:25%" />
					<col style="width:25%" />
					<col style="width:25%" />
				</colgroup>
				<tbody>
					<tr>
						<th scope="row" style="border-top:none;"><label for="title">청약상태</label></th>
						<td>
							<c:choose>
								<c:when test="${spotDevInfo.oprtSttusCd=='0001'}">사용중</c:when>
								<c:when test="${spotDevInfo.oprtSttusCd=='0002'}">이용중지</c:when>
								<c:when test="${spotDevInfo.oprtSttusCd=='0003'}">해지</c:when>
								<c:otherwise></c:otherwise>
							</c:choose>						
						</td>
						<th scope="row"><label for="title">가입일</label></th>
						<td>
							<fmt:formatDate value="${spotDevInfo.cretDt}"  pattern="yyyy-MM-dd"/>
						
						</td>
					</tr>
					<tr>
						<th scope="row"><label for="title">서비스일련번호</label></th>
						<td>${spotDevInfo.svcTgtSeq}</td>
						<th scope="row"><label for="title">기기 닉네임</label></th>
						<td>${spotDevInfo.devNm}</td>
					</tr>
					<tr>
						<th scope="row"><label for="title">모델명</label></th>
						<td>${spotDevInfo.devModelNm}</td>
						<th scope="row"><label for="title">펌웨어</label></th>
						<td>${spotDevInfo.frmwrVerNo}</td>
					</tr>							
				</tbody>
			</table>
			<br/>
			</c:forEach>
		</c:if>			
		</div>
				
		<div class="btns clearfix">
			<div class="fll">
				<a id="btnList" class="btn2 btn2_black30" style="cursor:pointer"><span>목록</span></a>
			</div>
		</div>			
		
	</jsp:body>
	
</layout:main>