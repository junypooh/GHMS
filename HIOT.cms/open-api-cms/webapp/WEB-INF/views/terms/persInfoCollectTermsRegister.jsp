<%@ page contentType="text/html" trimDirectiveWhitespaces="true" pageEncoding="UTF-8"%>
<%@ taglib prefix="layout" 	tagdir="/WEB-INF/tags/layout" %>
<%@ taglib prefix="c"  		uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" 	uri="http://java.sun.com/jsp/jstl/fmt" %>

<layout:main>

	<jsp:attribute name="javascript">
		<script type="text/javascript">
			
			${script}
			var checkFg = false;
			$(document).ready(function(){
				
				$("#btnList").click(function() {
					var form = document.forms["transForm"];
					form.submit();
				});
				
				$("#btnRegister").click(function() {
					
					var form = document.forms["registerForm"];
					if(validate(form)) {
						if(checkFg){
							form.submit();
						}else{
							alert('버전 체크를 눌러주세요.');
						}
					}
				});
			});
			
			function checkVersion() {
				
				var termsVer = $('#termsVer').val();
				var cpCode = $('#cpCode option:selected').val();
				
				if(termsVer == ''){
					alert('버전을 입력해주세요.');
					return false;
				} 
				
				// 버전 체크 확인 
				 if(!verCheck(termsVer)){
					 alert("0.00.00 or 0.0.0 형태로 버전정보 입력이 가능합니다.");
					 return false;
				 }
				
				//앱버전이 낮거나 동일한지 체크
				$.ajax({
					type : "GET",
				    cache: false,						
					url : "/terms/checkInfoCollectFormData",
					dataType : "json",
					data : { "termsVer" : termsVer, "cpCode" : cpCode}
				})
				.done(function(result){
					if(result.code == 200) { 
						alert("해당 버전은 등록 가능합니다.");
						$('#termsVer').attr("readonly", true);
						checkFg = true;
					}
					else if(result.code == 300) { alert('등록하려는 버전보다 높거나 같은 버전이 있습니다. \n확인하시고 다시 등록해 주세요.'); checkFg = false; }
					else{ alert("서버에서 오류가 발생하였습니다.\n다시 시도하여 주십시오."); checkFg = false; }
				})
				.fail(function( jqXHR, textStatus, errorThrown){
					console.log(jqXHR);  
					checkFg = false;
				});
			}
			
			function verCheck(str) {
				var chk_str = str;
				//정규식 : 0.00.00 or 0.0.0
				var pattern = /^(\d{1,1})([.]\d{1,2})([.]\d{1,2})$/;
				if(pattern.test(chk_str)){ return true;}
				else{ return false; }
			}
			
		</script>
	
	</jsp:attribute>
	
	<jsp:body>
		<form name="transForm" method="get" action="/terms/persInfoCollectTermsList">
		</form>
	
		<h2 class="pageTitle">개인정보 수집 및 이용동의 등록</h2>
		<table class="tbl_1">
			<caption></caption>
			<colgroup>
				<col style="width:15%" />
				<col style="width:35%" />
				<col style="width:17%" />
				<col style="width:*%" />
			</colgroup>
			<tbody>
				<form id="registerForm" name="registerForm" method="post" action="/terms/persInfoCollectTermsRegister">
				<input type="hidden" name="method" value="register" />		
				<tr>
					<th scope="row">상품(앱)명</th>
					<td colspan="3">
						<select id="cpCode" name="cpCode">
							<c:forEach var="svc" items="${loginInfo.svcList }">
							<option value="${svc.cpCode }">${svc.cpName }</option>
							</c:forEach>
						</select>
					</td>
				</tr>
				<tr>
					<th scope="col">작성자</th>
					<td>${loginInfo.id}</td>
					<th scope="col">약관동의 필수 여부</th>
					<td>
						<select id= "mandatoryYN" name= "mandatoryYN">
							<option value= "N">선택</option>
							<option value= "Y" selected>필수</option>
						</select>
					</td>
				</tr>
				<tr>
					<th scope="row"><label for="title">제목</label></th>
					<td colspan="3"><input type="text" id="title" name="title" class="w330" maxlength="20" require="true" msg="제목을 입력하셔야 합니다." /></td>
				</tr>
				<tr>
					<th scope="row"><label for="title">버전등록</label></th>
					<td colspan="3">
						<input type="text" id="termsVer" name="termsVer" class="w230" require="true" msg="버전을 입력하셔야 합니다." />
						<button type="button" id="btnCheckVer" class="btn" onclick="checkVersion();"><span>버전체크</span></button><span style="color:red;'">&nbsp;&nbsp;버전 입력 예시) 1.00.00</span>
					</td>
				</tr>
				<tr>
					<th scope="row" colspan="4">
						<div><textarea id="termsContents" name="termsContents" rows="30" style="border:1px solid #ccc;width:98%" require="true" msg="내용을 입력하셔야 합니다." ></textarea></div>
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