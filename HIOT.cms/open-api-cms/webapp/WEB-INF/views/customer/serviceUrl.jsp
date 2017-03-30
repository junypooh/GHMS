<%@ page contentType="text/html" trimDirectiveWhitespaces="true" pageEncoding="UTF-8"%>
<%@ taglib prefix="layout" 	tagdir="/WEB-INF/tags/layout" %>
<%@ taglib prefix="c"  		uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" 	uri="http://java.sun.com/jsp/jstl/fmt" %>

<layout:main>

	<jsp:attribute name="javascript">
	
	
		<script type="text/javascript">
			${script}
			var http= 'http://';	// http 없을 때 넣어주는 것
 			var cpCode = '${cpCode}';
			$(document).ready(function() {;
			
				$("#cpCode").val(cpCode);
				
				$("#cpCode").change(function() {
					var form = document.forms["searchForm"];
					form.submit();
				});
				
				$("#btnModify").click(function() {
					var cpCode = $('#cpCode option:selected').val();
					if($("#putUrl").val() == ''){
						alert("Url은 공백일 수 없습니다."); 
						return;
					}
					var form = document.forms["modifyForm"];
					if(validate(form)) {
						form.targetCpCode.value = cpCode;
						form.submit();
					}
				});
			});	
			
			/* function putWindowOpenPopup(){ 
 				// 도메인 체크 ㄱ					
 				var textUrl = $("#putUrl").val();
 				var pattern = new RegExp('^(https?:\\/\\/)?'+ // 프로토콜

 					  '((([a-z\d](([a-z\d-]*[a-z\d])|([ㄱ-힣]))*)\.)+[a-z]{2,}|'+ // 도메인명 <-이부분만 수정됨

 					  '((\\d{1,3}\\.){3}\\d{1,3}))'+ // 아이피

 					  '(\\:\\d+)?(\\/[-a-z\\d%_.~+]*)*'+ // 포트번호

 					  '(\\?[;&a-z\\d%_.~+=-]*)?'+ // 쿼리스트링

					  '(\\#[-a-z\\d_]*)?$','i'); // 해쉬테그들	  
			  if(!pattern.test(textUrl)) {
				    alert("올바른 URL이 아닙니다.");
				    
 			  } else {
    			  		if(textUrl.substring(0, 7) != http){
	 					textUrl = http + textUrl;
 					window.open(textUrl,'target_name','scrollbars=yes,toolbar=no,resizable=yes,left=0,top=0'); 
	 				} 
 				}	  
			} */ 			
			
 			// 현재 URL 링크 버튼 function ㄱ
 			function windowOpenPopup(){ 
 				var textUrl = "${serviceUrl}";
 				if(textUrl.substring(0, 7) != http){
 					textUrl = http + textUrl;

 				}
				window.open(textUrl,'target_name','scrollbars=yes,toolbar=no,resizable=yes,left=0,top=0'); 
				
			}	
		</script>	
		
	</jsp:attribute>

	<jsp:body>
		
		<form name="searchForm" method="get" action="/customer/serviceUrl">
		<div class="boardTitle">
			<div class="borderTitle">
				<span class="pageTitle">서비스 가입 안내</span>
				<select id="cpCode" name="cpCode" title="검색조건을 선택하세요" style="float:right;">
					<c:forEach var="svc" items="${loginInfo.svcList }">
					<option value="${svc.cpCode }">${svc.cpName }</option>
					</c:forEach>
				</select>
			</div>
		</div>
		</form>

		<h2 class="pageTitle">
			<b>
				<c:forEach var="svc" items="${loginInfo.svcList }">
					<c:if test="${cpCode == svc.cpCode }">상품 상세안내 URL 변경(${svc.cpName})</c:if>
				</c:forEach>
			</b>
		</h2>
		<form name="modifyForm" method="post" onsubmit="return false;" action="/customer/updateServiceUrl">
		<input type="hidden" id="targetCpCode" name="targetCpCode" value="" />
			<table class="tbl_1">			
					<caption> </caption>
					<colgroup>
						<col style="width:30%" />
						<col style="width:70%" />
					</colgroup>						
				<tbody id="ServiceUrlListContainer">
					<tr>
						<th style="text-align: center;">현재</th>
						<td style="text-align: left;">
							<span id="getUrl">${serviceUrl}</span>
							<button type="button" onclick="windowOpenPopup();" class="btn"><span>페이지 이동</span></button>
						</td>
					</tr>
					<tr>
						<th style="text-align: center;">변경</th>
						<td style="text-align: left;">
							<input type="text" id= "putUrl" name="putUrl" class="w630">
						</td>
					</tr>
				</tbody>
			</table>
		</form>			
		<div class="btns clearfix">
			<div class="flr">
				<a id="btnModify" class="btn2 btn2_red30" style="cursor:pointer"><span>저장</span></a>
			</div>
		</div>	
		
	</jsp:body>
</layout:main>