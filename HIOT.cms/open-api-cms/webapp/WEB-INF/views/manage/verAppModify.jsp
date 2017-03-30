<%@ page contentType="text/html" trimDirectiveWhitespaces="true" pageEncoding="UTF-8"%>
<%@ taglib prefix="layout" 	tagdir="/WEB-INF/tags/layout" %>
<%@ taglib prefix="c"  		uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" 	uri="http://java.sun.com/jsp/jstl/fmt" %>

<layout:main>

	<jsp:attribute name="javascript">
	
		<script type="text/javascript">
			${script}
			//
			$(document).ready(function(){
				
				$("#btnList").click(function() {
					var form = document.forms["transForm"];
					form.submit();
				});
				

				$("#btnRemove").click(function() {
					
					if(!confirm('삭제 하시겠습니까?')) return false;
					
					var pk = '${verApp.pk}';
					$.ajax({
						type : "POST",
					    cache: false,						
						url : "/manage/removeVerApp",
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
					
					if (confirm(" 수정하시겠습니까?") == true){
						if(validate(form)) {
							form.submit();
						}
					}
				});
			});
			
			function viewFile(filePath) {
				var scheme = "${pageContext.request.scheme}";
				var domain = '${verApp.domain}';
				var port = '${verApp.port}';
				window.open(scheme + "://" + domain + ":" + port + filePath, "");
			}
			
		</script>
	
	</jsp:attribute>
	
	<jsp:body>
		<form name="transForm" method="get" action="/manage/verAppList">
		<input type="hidden" name="page" value="${param.page }" />
		<input type="hidden" name="cpCode" value="${param.cpCode }" />
		<input type="hidden" name="osCode" value="${param.osCode }" />
		</form>
		
		<div class="boardTitle">
			<div class="borderTitle">
				<span class="pageTitle">APP 버전</span>
			</div>
		</div>
		
		<table class="tbl_1">
			<caption></caption>
			<colgroup>
				<col style="width:15%" />
				<col style="width:35%" />
				<col style="width:15%" />
				<col style="width:35%" />
			</colgroup>
			<tbody>
				<form name="modifyForm" method="post" action="/manage/verAppModify" onsubmit="return false;" enctype="multipart/form-data">
					<input type="hidden" name="method" value="modify" />
					<input type="hidden" name="pk" value="${verApp.pk }" />
					<input type="hidden" name="mandatoryYN" value= "${verApp.mandatoryYN}" />			
					<input type="hidden" name="page" value="${param.page }" />
					<input type="hidden" name="osCode" value="${param.osCode }" />
					<input type="hidden" name="cpCode" value="${param.cpCode }" />
					<tr>
						<th scope="row">앱명</th>
						<td colspan="3">${verApp.cpName }</td>
					</tr>				
					<tr>
						<th scope="row">작성자</th>
						<td >${verApp.writerId}</td>
						<th scope="row">App버전</th>
						<td>${verApp.verNum}
							<c:choose>
								<c:when test="${mandatoryYN == 'Y'}">
									(필수업데이트) 
								</c:when>
								<c:when test="${mandatoryYN == 'N' }">
									(선택업데이트)
								</c:when>
							</c:choose>
						</td>
					</tr>
					<tr>
						<th scope="row">OS</th>
						<td>
							<c:forEach var="osCode" items="${osList}" varStatus="status" >
								<c:if test="${verApp.osCode == osCode.code}">${osCode.name}</c:if>
							</c:forEach>
						</td>
						
						<th scope="row">단말</th>
						<td>
							<c:forEach var="phCode" items="${phList}" varStatus="status" >
								<c:if test="${verApp.phCode == phCode.code}">${phCode.name}</c:if>
							</c:forEach>
						</td>
					</tr>
					<tr>
						<th scope="col">APP apk업로드</th>
						<td colspan= "3">
							<div id="fileContainer" style="padding:10px;">
								<input type="file" id="hdpi_apkFile" name="hdpi_apkFile" onchange="appendFileName(event, this.id);" accept=".apk, .ipa" style="width:450px;" />
								<c:forEach var="file" items="${verApp.fileList }">
									<a href="#none" onclick="viewFile('${file.virtualPath}/${file.saveName}');">${file.orgName}</a>
								</c:forEach>
							</div>
						</td>
					</tr>			
					<tr>
					<th scope="col">APP 버전 설명</th>
						<td colspan="3">
						<div>
							<textarea name="verMemo"id="verMemo" require= "true" msg="내용을 입력하셔야 합니다." rows="10" style="border:1px solid #ccc;width:98%">${verApp.verMemo}</textarea>
						</div>
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
				<a id="btnModify" class="btn2 btn2_red30" style="cursor:pointer"><span>저장</span></a>
			</div>
		</div>			
		
	</jsp:body>
	
</layout:main>
