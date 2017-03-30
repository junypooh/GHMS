<%@ page contentType="text/html" trimDirectiveWhitespaces="true" pageEncoding="UTF-8"%>
<%@ taglib prefix="layout" 	tagdir="/WEB-INF/tags/layout" %>
<%@ taglib prefix="c"  		uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" 	uri="http://java.sun.com/jsp/jstl/fmt" %>

<layout:main>

	<jsp:attribute name="javascript">
		<script type="text/javascript">
			var checkFg = false;
			${script}
			$(document).ready(function(){
				
				$("#btnList").click(function() {
					var form = document.forms["transForm"];
					form.submit();
				});
				
				$("#btnRemove").click(function() {
					
					if(!confirm('삭제 하시겠습니까?')) return false;
					
					var pk = '${termsMap.pk}';
					$.ajax({
						type : "POST",
					    cache: false,						
						url : "/terms/removeServiceTerms",
						dataType : "json",
						data : { "pkList" : pk}
					})
					.done(function(result){
						if(result.code != 200) {
							alert("서버에서 오류가 발생하였습니다.\n다시 시도하여 주십시오.");
						} else {
							alert("정상적으로 삭제 처리 되었습니다.");
							var form = document.forms["transForm"];
							form.submit();
						}
					})
					.fail(function( jqXHR, textStatus, errorThrown){
						console.log(jqXHR);
					});
				});
				
				$("#btnModify").click(function() {
					var form = document.forms["modifyForm"];
					if(validate(form)) {
						form.submit();
					}
				});
			});
			
			function openPopup()
			{
				var width = "800";
				var height = "800";
				var leftPos = (screen.availWidth - width)/2;
				var topPos = (screen.availHeight - height)/2;
				var position = "width="+ width + ", height=" + height + ", left=" + leftPos + ", top=" + topPos;
				var url = "/preview?type=webview";
				url += "&pkCode=1401"
				url += "&pk=" + '${termsMap.pk}';
				url += "&cpCode=" + '${termsMap.cpCode}';
				window.open(url, "winOpen", position, "menubar=1");
			}			
		</script>
	
	</jsp:attribute>
	
	<jsp:body>
		<form name="transForm" method="get" action="/terms/serviceTermsList">
		<input type="hidden" name="page" value="${param.page }" />
		<input type="hidden" name="cpCode" value="${param.cpCode }" />
		</form>
	
		<div class="boardTitle">
			<div class="borderTitle">
				<span class="pageTitle">서비스 이용약관 수정</span>
			</div>
		</div>
		<table class="tbl_1">
			<caption></caption>
			<colgroup>
				<col style="width:15%" />
				<col style="width:35%" />
				<col style="width:17%" />
				<col style="width:*%" />
			</colgroup>
			<tbody>
				<form id="modifyForm" name="modifyForm" method="post" onsubmit="return false;" action="/terms/serviceTermsModify">
				<input type="hidden" name="method" value="modify" />
				<input type="hidden" name="pk" value="${termsMap.pk}" />
				<input type="hidden" name="page" value="${param.page }" />		
				<input type="hidden" name="cpCode" value="${param.cpCode }" />
				<tr>
					<th scope="row">상품(앱)명</th>
					<td colspan="3">${termsMap.cpName }</td>
				</tr>
				<tr>
					<th scope="col">작성자</th>
					<td>${termsMap.writerId}</td>
					<th scope="col">약관동의 필수 여부</th>
					<td>
						<select id= "mandatoryYN" name= "mandatoryYN">
							<c:choose>
								<c:when test="${termsMap.mandatoryYN == 'Y' }">
									<option value= "Y" selected="selected">필수</option>
									<option value= "N" >선택</option>
								</c:when>
								<c:when test="${termsMap.mandatoryYN == 'N' }">
									<option value= "Y" >필수</option>
									<option value= "N" selected="selected">선택</option>
								</c:when>
							</c:choose>
						</select>
					</td>
				</tr>
				<tr>
					<th scope="row"><label for="title">제목</label></th>
					<td colspan="3"><input type="text" id="title" name="title" value="${termsMap.title}" class="w330" maxlength="20" require="true" msg="제목을 입력하셔야 합니다." /></td>
				</tr>
				<tr>
					<th scope="row"><label for="title">버전</label></th>
					<td colspan="3">${termsMap.termsVer}</td>
				</tr>
				<tr>
					<th scope="row" colspan="4">
						<div><textarea id="termsContents" name="termsContents" rows="30" style="border:1px solid #ccc;width:98%" require="true" msg="내용을 입력하셔야 합니다." >${termsMap.termsContents}</textarea></div>
					</td>
				</tr>
				</form>
			</tbody>
		</table>
				
		<div class="btns clearfix">
			<div class="fll">
				<a id="btnList" class="btn2 btn2_black30" style="cursor:pointer"><span>목록</span></a>
				<a id="btnRemove" class="btn2 btn2_black30" style="cursor:pointer"><span>삭제</span></a>
			</div>
			<div class="flr">
				<a id="btnPreview" href="#none" onclick="openPopup();" onfocus="this.flr();" class="btn2 btn2_black30" style="cursor:pointer"><span>미리보기</span></a>
				<a id="btnModify" class="btn2 btn2_red30" style="cursor:pointer"><span>저장</span></a>
			</div>
		</div>			
		
	</jsp:body>
	
</layout:main>