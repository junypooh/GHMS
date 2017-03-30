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
				
				/* // ios, android 
				var osCode = $('#osCode').val(); 
				
				if(osCode == "1701"){
					$("#hdpi_apkFile").attr("accept", ".apk");
				}
				else if(osCode == "1801"){
					$("#hdpi_apkFile").attr("accept", ".ipa");
				} */
				
				$("#btnRegister").click(function() {
					var verNum = $('#verNum').val();
					var cpCode = $('#cpCode option:selected').val();
					var osCode = $('#osCode option:selected').val();
					var phCode = $('#phCode option:selected').val();
					var submitFg= true; 
					var form = document.forms["registerForm"];

					// 폼 존재 유무 확인 
					 if(!validate(form)){
					 	return false; 
					}
					
					//파일이 하나도 선택이 안되어있는지 검증
					/* $("#fileContainer input:file").each(function(){
						if($(this).val().trim() == "") {
							alert('App(.apk, ipa)을 선택해주세요.');
							submitFg = false;
						}
					});
					if(!submitFg) return false; */
					 
					 // 버전 체크 확인 
					 if(!verCheck(verNum)){
						 alert("0.00.00.00 or 0.0.0.0 형태로 버전정보 입력이 가능합니다.");
						 return false;
					 }else{
						$('#verNum').attr("verNum", true);
					 }
					 
					//등록하려는 정보의 상품, OS, 단말의 동일한버전과 상위 버전이 있는지 검증
					$.ajax({
						async : false, 
						type : "POST",
					    cache: false,						
						url : "/manage/checkVerAppFormData",
						dataType : "json",
						data : { "cpCode" : cpCode, "verNum" : verNum, "osCode" : osCode , "phCode" : phCode}
					})
					.done(function(result){
						if(result.code == 200) { submitFg = true; }
						else if(result.code == 300) { alert('등록하려는 앱버전보다 높은거나 같은 앱버전이 있습니다. \n확인하시고 다시 등록해 주세요.'); submitFg = false; }
						else{ alert("서버에서 오류가 발생하였습니다.\n다시 시도하여 주십시오."); submitFg = false; }
					})
					.fail(function( jqXHR, textStatus, errorThrown){ 
						console.log(jqXHR); submitFg = false;
					});	
					
					// submit() 
					if(submitFg){form.submit();}
				});
			});
					
			function appendFileName(evt, id){
				var files = evt.target.files;
				var file = files[0].name;
				$("#"+id+"Name").text(file);
			}
			
			function verCheck(str) {
				var chk_str = str;
				//정규식 : 0.00.00.00 or 0.0.0.0
				var pattern = /^(\d{1,1})([.]\d{1,2})([.]\d{1,2})([.]\d{1,2})$/;
				if(pattern.test(chk_str)){ return true;}
				else{ return false; }
			}
			

		</script>
	</jsp:attribute>
	
	<jsp:body>
		<form name="transForm" method="get" action="/manage/verAppList">
		<input type="hidden" name="page" value="${param.page }" />
		<input type="hidden" name="searchColumn" value="${param.searchColumn }" />
		<input type="hidden" name="searchString" value="${param.searchString }" />
		</form>
	
		<div class="boardTitle">
			<div class="borderTitle">
				<span class="pageTitle">App버전 등록</span>
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
				<form name="registerForm" method="post" action="verAppRegister" onsubmit="return false;" enctype="multipart/form-data">
				<input type="hidden" name="method" 	value="register" />
				<tr>
					<th scope="row">앱명</th>
					<td colspan="3">						
						<select id="cpCode" name=cpCode>
							<c:forEach var="svc" items="${loginInfo.svcList }">
							<option value="${svc.cpCode }">${svc.cpName }</option>
							</c:forEach>
						</select>
					</td>
				</tr>
					
				<tr>
					<th scope="col">작성자</th>
					<td>${loginInfo.id}</td>
					

					<th scope="row">APP 버전</th>
					<td><input type="text" id="verNum" name="verNum" require="true" msg="버전을 입력하셔야 합니다." onkeyup="fc_chk2()" onkeypress="fc_chk2()"/></td>
				</tr>
					
				<tr>					
					<th scope="col">OS</th>
					<td>
						<select id="osCode" name= "osCode">
							<c:forEach var="osCode" items="${osList}" varStatus="status" >
								<option value="${osCode.code}">${osCode.name}</option>
							</c:forEach>
						</select>
						&nbsp;&nbsp;&nbsp;&nbsp;
					</td>
					<th scope="col">단말</th>
					<td>
						<select id= "phCode" name= "phCode">
							<c:forEach var="phCode" items="${phList}" varStatus="status" >
								<option value="${phCode.code}">${phCode.name}</option>
							</c:forEach>
						</select>
					</td>
				</tr>
				<tr>
				<th scope="col">APP 업로드(apk, ipa)</th>
					<td colspan= "3">
						<div id="fileContainer" style="padding:10px;">
							<input type="file" id="hdpi_apkFile" name="hdpi_apkFile" onchange="appendFileName(event, this.id);" accept=".apk, .ipa" style="width:450px;" />
						</div>
					</td>
				</tr>
				<tr>
					<th scope="col">APP 버전 설명</th>
					<td colspan="3">
						<textarea name="verMemo" rows="10" style="border:1px solid #ccc;width:98%" require="true" msg="내용을 입력하셔야 합니다.">${notice.verMemo }
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