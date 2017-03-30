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
					
					var verNum = jQuery("select[name=verNum]").val();
					var openYN = $('#openY').is(":checked") ? 'Y' : 'N';
					var cpCode = $('#cpCode option:selected').val();
					var form = document.forms["registerForm"];
					var submitFg = false;

					//유효성 검증
					if(!validate(form)) { return false; }
					
					//파일이 하나도 선택이 안되어있는지 검증
					$("#fileContainer input:file").each(function(){
						if($(this).val().trim() != "") {
							submitFg = true;
						}
					});
					if(!submitFg) {
						alert("이미지를 한개이상 등록 하셔야 됩니다.");
						return false;
					}
					
					//버전 검증
					if(verNum == null){ alert("해당 상품의 앱버전이 없습니다."); return false; }
					
					//노출여부가 Y일때 상품명, 앱버전, 위치가 같은정보가 있는지 체크
					if(openYN == 'Y'){
						$.ajax({
							async : false, 
							type : "POST",
						    cache: false,						
							url : "/guide/checkWelcomeFormData",
							dataType : "json",
							data : { "cpCode" : cpCode, "verNum" : verNum}
						})
						.done(function(result){
							if(result.code == 200) { submitFg = true; }
							else if(result.code == 300) { 
								if(confirm("상품/앱버전이 같은 정보가 있을경우\n비노출로 등록됩니다.\n진행하시겠습니까?")){
									$('#openN').prop("checked", true);
									submitFg = true;
								}else{
									submitFg = false;
								}
							}
							else{ alert("서버에서 오류가 발생하였습니다.\n다시 시도하여 주십시오."); submitFg = false; }
						})
						.fail(function( jqXHR, textStatus, errorThrown){ 
							console.log(jqXHR); submitFg = false;
						});						
					}
					//모든 검증이 true일때 submit();
					if(submitFg){ form.submit(); }
				});
				
				$("#cpCode").change(function(){
                	var cpCode = jQuery("select[name=cpCode]").val();
					$("#verNum").empty().data('options');
					
					$.ajax({
						type : "POST",
					    cache: false,						
						url : "/guide/getWelcomeVersionList",
						dataType : "json",
						data : { "cpCode" : cpCode }
					})
					.done(function(result){
						if(result.code != 200) {
							alert("서버에서 오류가 발생하였습니다.\n다시 시도하여 주십시오.");
						} else {
							var cnt = result.list.length;	
							for(var i=0; i<cnt; i++){
								var verNum	= result.list[i].fd_ver_num;
       							$("#verNum").append("<option value='"+verNum+"'>"+verNum+"</option>");
       						}
						}
					})
					.fail(function( jqXHR, textStatus, errorThrown){
						console.log(jqXHR);
					});	
				});
			});
			
		</script>
	
	</jsp:attribute>
	
	<jsp:body>
		<form name="transForm" method="get" action="/guide/guideWelcomeList">
		</form>
	
		<div class="boardTitle">
			<div class="borderTitle">
				<span class="pageTitle">웰컴 가이드</span>
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
				<form id="registerForm" name="registerForm" method="post" onsubmit="return false;" action="/guide/guideWelcomeRegister" enctype="multipart/form-data">
				<input type="hidden" name="method" value="register" />
				<tr>
					<th scope="row">앱명</th>
					<td>
						<select id="cpCode" name="cpCode">
							<c:forEach var="svc" items="${loginInfo.svcList }">
							<option value="${svc.cpCode }">${svc.cpName }</option>
							</c:forEach>
						</select>
					</td>
					<th scope="row">앱버전</th>
					<td>
						<!-- <input type="text" id="verNum" name="verNum" class="w135" require="true" msg="앱버전을 입력하셔야 합니다." /> -->
						<select id="verNum" name="verNum" style="width:110px;">
							<c:forEach var="ver" items="${verList}" varStatus="status" >
								<option value="${ver.fd_ver_num}">${ver.fd_ver_num}</option>
							</c:forEach>
						</select>
					</td>
				</tr>
				<tr>
					<th scope="row">작성자</th>
					<td colspan="3">${loginInfo.id}</td>
				</tr>
				<tr>
					<th scope="row">노출여부</th>
					<td colspan="3">
						<input type="radio" id="openY" name="openYN" value="Y" />&nbsp;노출&nbsp;&nbsp;&nbsp;
						<input type="radio" id="openN" name="openYN" value="N" checked="checked" />&nbsp;비노출
					</td>
				</tr>
				<tr>
					<th scope="row">이미지</th>
					<td colspan="3">
						<div id="fileContainer" style="width:100%">
		            		<div style="padding:3px;"><b>&nbsp;1.&nbsp;&nbsp;</b><input type="file" name="image1" accept=".png, .jpg, .jpeg, .bmp, .gif" style="width:70%;"/></div>	
		            		<div style="padding:3px;"><b>&nbsp;2.&nbsp;&nbsp;</b><input type="file" name="image2" accept=".png, .jpg, .jpeg, .bmp, .gif" style="width:70%;"/></div>
		            		<div style="padding:3px;"><b>&nbsp;3.&nbsp;&nbsp;</b><input type="file" name="image3" accept=".png, .jpg, .jpeg, .bmp, .gif" style="width:70%;"/></div>
		            		<div style="padding:3px;"><b>&nbsp;4.&nbsp;&nbsp;</b><input type="file" name="image4" accept=".png, .jpg, .jpeg, .bmp, .gif" style="width:70%;"/></div>
		            		<div style="padding:3px;"><b>&nbsp;5.&nbsp;&nbsp;</b><input type="file" name="image5" accept=".png, .jpg, .jpeg, .bmp, .gif" style="width:70%;"/></div>
		            		<div style="padding:3px;"><b>&nbsp;6.&nbsp;&nbsp;</b><input type="file" name="image6" accept=".png, .jpg, .jpeg, .bmp, .gif" style="width:70%;"/></div>
		            		<div style="padding:3px;"><b>&nbsp;7.&nbsp;&nbsp;</b><input type="file" name="image7" accept=".png, .jpg, .jpeg, .bmp, .gif" style="width:70%;"/></div>
		            		<div style="padding:3px;"><b>&nbsp;8.&nbsp;&nbsp;</b><input type="file" name="image8" accept=".png, .jpg, .jpeg, .bmp, .gif" style="width:70%;"/></div>
		            		<div style="padding:3px;"><b>&nbsp;9.&nbsp;&nbsp;</b><input type="file" name="image9" accept=".png, .jpg, .jpeg, .bmp, .gif" style="width:70%;"/></div>
		            		<div style="padding:3px;"><b>10.&nbsp;&nbsp;</b><input type="file" name="image10" accept=".png, .jpg, .jpeg, .bmp, .gif" style="width:70%;"/></div>		            						            		
		            	</div>
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