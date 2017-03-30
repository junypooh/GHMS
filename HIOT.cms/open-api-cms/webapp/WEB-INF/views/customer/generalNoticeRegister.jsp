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
				
				$("#btnRegister").click(function() {
					var form = document.forms["registerForm"];
					
					if(validate(form)) {
						form.submit();
					}
				});
			});
		
		</script>
	
	</jsp:attribute>
	
	<jsp:body>
		<form name="transForm" method="get" action="/customer/generalNoticeList">
		<input type="hidden" name="page" value="${param.page }" />
		<input type="hidden" name="searchColumn" value="${param.searchColumn }" />
		<input type="hidden" name="searchString" value="${param.searchString }" />
		</form>
	
		<div class="boardTitle">
			<div class="borderTitle">
				<span class="pageTitle">일반공지 등록</span>
			</div>
		</div>
		<table class="tbl_1">
			<caption></caption>
			<colgroup>
				<col style="width:15%" />
				<col style="width:*%" />
			</colgroup>
			<tbody>
				<form name="registerForm" method="post" onsubmit="return false;" action="/customer/generalNoticeRegister" >
				<input type="hidden" name="method" value="register" />
				<input type="hidden" name="writerId" value="${loginInfo.id }" />
				<input type="hidden" name="page" value="${param.page }" />
				<input type="hidden" name="searchColumn" value="${param.searchColumn }" />
				<input type="hidden" name="searchString" value="${param.searchString }" />				
				<tr>
					<th scope="row">앱명</th>
					<td>
						<select id="cpCode" name=cpCode>
								<option value= "001">홈 캠</option>
								<option value= "002">홈 피트니스</option>
								<option value= "003">홈 매니저</option>
						</select>
					</td>
				</tr>
				<tr>
					<th scope="row"><label for="title">제목</label></th>
					<td><input type="text" id="title" name="title" class="w630" value="${notice.title }" require="true" msg="제목을 입력하셔야 합니다." /></td>
				</tr>
				<tr>
					<th scope="row">작성자</th>
					<td>${loginInfo.id }</td>
				</tr>
				<tr>
					<th scope="row">노출여부</th>
					<td>
						<input type="radio" name="openYN" value="Y" checked="checked" /> 노출
						&nbsp;&nbsp;&nbsp;&nbsp;
						<input type="radio" name="openYN" value="N" /> 비노출
					</td>
				</tr>
				<tr>
					<td colspan="2">
						<textarea name="contents" rows="30" style="border:1px solid #ccc;width:98%" require="true" msg="내용을 입력하셔야 합니다.">${notice.contents }
						</textarea>
					</td>
				</tr>
				</form>
			</tbody>
		</table>
				
		<div class="btns clearfix">
			<div class="fll">
				<a id="btnList" class="btn2 btn2_black30" style="cursor:pointer"><span>목록</span></a>
			</div>
			<div class="flr">
				<a id="btnRegister" class="btn2 btn2_red30" style="cursor:pointer"><span>저장</span></a>
			</div>
		</div>			
		
	</jsp:body>
	
</layout:main>