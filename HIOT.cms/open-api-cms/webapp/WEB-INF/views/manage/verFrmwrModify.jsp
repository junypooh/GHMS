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
				$("#btnRemove").click(function() {
					//
					if(!confirm('삭제 하시겠습니까?')) return false;
					
					var pk = '${verFrmwr.pk}';
					$.ajax({
						type : "POST",
					    cache: false,						
						url : "/manage/removeVerFrmwr",
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
			
		</script>
	
	</jsp:attribute>
	
	<jsp:body>
		<form name="transForm" method="get" action="/manage/verFrmwrList">
			<input type="hidden" name="page" value="${param.page }" />
			<input type="hidden" name="cpCode" value="${param.cpCode }" />
		</form>
		<div class="boardTitle">
			<div class="borderTitle">
				<span class="pageTitle">단말 펌웨어 버전관리</span>
			</div>
		</div>
		<table class="tbl_1">
			<caption></caption>
			<colgroup>
				<col style="width:20%" />
				<col style="width:30%" />
				<col style="width:20%" />
				<col style="width:30%" />
			</colgroup>
			<tbody>
				<form name="modifyForm" method="post" action="/manage/verFrmwrModify"  enctype="multipart/form-data">
					<input type="hidden" name="method" value="modify" />
					<input type="hidden" name="pk" value="${verFrmwr.pk}" />
					<input type="hidden" name="page" value="${param.page }" />			
					<input type="hidden" name="cpCode" value="${param.cpCode }" />	
					<input type="hidden" name="modifierId" value="${verFrmwr.modifierId }" />			
					<tr>
						<th scope="row">앱명</th>
						<td colspan="3">${verFrmwr.cpName }</td>
					</tr>					
					<tr>
						<th scope="col">작성자</th>
						<td>${verFrmwr.writerId}</td>
						<th scope="col">업데이트 필수 여부</th>
						<td>
							<select id= "mandatoryYN" name= "mandatoryYN">
								<c:choose>
									<c:when test="${verFrmwr.mandatoryYN == 'Y' }">
										<option value= "Y" selected="selected">필수</option>
										<option value= "N" >선택</option>
									</c:when>
									<c:when test="${verFrmwr.mandatoryYN == 'N' }">
										<option value= "Y" >필수</option>
										<option value= "N" selected="selected">선택</option>
									</c:when>
								</c:choose>
							</select>
						</td>
					</tr>
					<tr>
						<th>배포 설정</th>
						<td colspan="3">
							${verFrmwr.startTime}
						</td>
					</tr>						
					<tr>
						<th scope="row">단말 버전</th>
						<td>${verFrmwr.verNum}</td>
					
						<th scope="row">모델 명</th>
						<td>${verFrmwr.modelName}</td>
					</tr>
					<tr>
						<th>단말 버전 설명</th>
						<td colspan="3">
						<textarea name="verTitle" rows="10" style="border:1px solid #ccc;width:98%" require="true" msg="내용을 입력하셔야 합니다.">${verFrmwr.verTitle }</textarea>
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