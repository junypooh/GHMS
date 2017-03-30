<%@ page contentType="text/html" trimDirectiveWhitespaces="true" pageEncoding="UTF-8"%>
<%@ taglib prefix="layout" 	tagdir="/WEB-INF/tags/layout" %>
<%@ taglib prefix="c"  		uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" 	uri="http://java.sun.com/jsp/jstl/fmt" %>

<layout:main>

	<jsp:attribute name="javascript">
	
		<script type="text/javascript">
		
			${script}
			var mandatoryYN = '${verFrmwr.mandatoryYN}'=='' ? '' : '${verFrmwr.mandatoryYN}';
			
			$(document).ready(function(){
				
				$('#mandatoryYN').val(mandatoryYN);
				
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
						url : "/manage/removeBetaVerFrmwr",
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
					
					if(!validate(form)){
						return false; 
					}					

					form.submit(); 
				});		
			});
			
			// 버전 체크 0.00.00
			function verCheck(str) {
				var chk_str = str;
				//정규식 : 0.00.00
				var pattern = /^(\d{1,1})([.]\d{2,2}?)([.]\d{2,2}?)?$/;
				if(str.length == 7 && pattern.test(chk_str)){
					return true;		
				}else{
					return false; 
				}
			}
			
		</script>	
	</jsp:attribute>
	
	<jsp:body>
		<form name="transForm" method="get" action="/manage/betaVerFrmwrList">
			<input type="hidden" name="page" value="${param.page }" />
		</form>
		<div class="boardTitle">
			<div class="borderTitle">
				<span class="pageTitle">테스트 단말 펌웨어 버전관리 수정</span>
			</div>
		</div>
		<table class="tbl_1">
			<caption></caption>
			<colgroup>
				<col style="width:15%" />
				<col style="width:35%" />
				<col style="width:20%" />
				<col style="width:30%" />
			</colgroup>
			<tbody>
				<form name="modifyForm" method="post" action="/manage/betaVerFrmwrModify">
					<input type="hidden" name="method" value="modify" />
					<input type="hidden" name="pk" value="${verFrmwr.pk}" />
					<tr>
						<th scope="row">앱명</th>
						<td colspan="3">올레 홈캠</td>
					</tr>					
					<tr>
						<th scope="col">작성자</th>
						<td>${verFrmwr.writerId}</td>
						<th scope="col">업데이트 필수 여부</th>
						<td>
							<select id="mandatoryYN" name="mandatoryYN">
								<option value= "N">업데이트 선택</option>
								<option value= "Y">업데이트 필수</option>
							</select>
						</td>
					</tr>
					<tr>
						<th scope="row">배포 설정</th>
						<td colspan="3">${verFrmwr.startTime}</td>
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
							<textarea id="verTitle" name="verTitle" rows="10" style="border:1px solid #ccc;width:98%" require="true" msg="내용을 입력하셔야 합니다.">${verFrmwr.verTitle }</textarea>
						</td>					
					</tr>
				</form>
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