<%@ page contentType="text/html" trimDirectiveWhitespaces="true" pageEncoding="UTF-8"%>
<%@ taglib prefix="layout" 	tagdir="/WEB-INF/tags/layout" %>
<%@ taglib prefix="c"  		uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" 	uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" 	uri="http://www.springframework.org/tags/form" %>

<layout:main>
	<jsp:attribute name="javascript">
		<script type="text/javascript">
		
			${script}
			
			var cpCode = "${serviceGuide.cpCode}";
			var svcFn = "${serviceGuide.svcFn}";
			var openYN = "${serviceGuide.openYN}";
			var contentsTypeStr = "${serviceGuide.contentsType}";
			
			$(document).ready(function(){
				
				if(openYN != "") {
					$("#open" + openYn).prop("checked", true);
				}
				
				if(cpCode != "") {
					$("#cpCode").val(cpCode);
				}
				
				changeCpCode($("#cpCode").val());
				
				if(svcFn != "") {
					$("#svcFn").val(svcFn);
				}	
				
				if(contentsTypeStr != "") {
					$("#contentsType").val(contentsTypeStr);
					contentsType(type);
				}
				
				$("#cpCode").change(function(){
					changeCpCode($(this).val());
				});
				
				$("#btnList").click(function() {
					location.href = "/customer/serviceGuideList?searchCpCode=${param.searchCpCode}"; 
				});
				
				$("#btnRegister").click(function() {
					var form = document.forms["registerForm"];
 					if(validate(form)) {
 						
 						if(form.contentsType.value == "text") {
 							var contents = form.faqContents.value.trim();
 							if(contents.length == 0) {
 								alert("서비스 안내 내용을 입력하십시요.");
 								form.contents.focus();
 								return false;
 							}
 						} else {
 							var result = false;
 							$("#fileContainer input:file").each(function(){
 								if($(this).val().trim() != "") {
 									result = true;
 								}
 							});
 							
 							if(!result) {
 								alert("이미지를 한개이상 등록 하셔야 됩니다.");
 								return false;
 							}
 						}

 						form.submit();
					}
				});
				
				
			});
			
			function contentsType(type) {
				
 				$("#contentsType").val(type);
				
				if(type == "text") {
					$("#textType").show();
					$("#imageType").hide();
				} else {
					$("#textType").hide();
					$("#imageType").show();					
				} 
			}
			
			function changeCpCode(cpCode) {
				$.ajax({
					type : "POST",
				    cache: false,						
					url : "/customer/getManageGoodsList",
					dataType : "json",
					data : { "cpCode" : cpCode}
				})
				.done(function(result){
					if(result.code == 200) {
						$("#svcFn").find("option").remove();
 						for(var i = 0; i < result.list.length; i++) {
							$("#svcFn").append($("<option></option>").attr("value", result.list[i].pk).text(result.list[i].name));
						}
					} else {
						alert("서버에서 오류가 발생하였습니다.\n다시 시도하여 주십시오.");
					}
				})
				.fail(function( jqXHR, textStatus, errorThrown){
					console.log(jqXHR);
				});
			}
			
		</script>
	
	</jsp:attribute>
	
	<jsp:body>
		<div class="boardTitle">
			<div class="borderTitle">
				<span class="pageTitle">서비스안내(FAQ)</span>
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
				<form id="registerForm" name="registerForm" method="post" onsubmit="return false;" action="/customer/serviceGuideRegister" enctype="multipart/form-data">
				<input type="hidden" name="method" value="register" />
				<input type="hidden" id="contentsType" name="contentsType" value="text" />
				<input type="hidden" name="searchCpCode" value="${param.searchCpCode }" />
				<tr>
					<th scope="row">앱명</th>
					<td>
						<select id="cpCode" name="cpCode">
							<c:forEach var="svc" items="${loginInfo.svcList}">
							<option value="${svc.cpCode }">${svc.cpName }</option>
							</c:forEach>
						</select>
					</td>
					<th scope="row">기능명</th>
					<td>
						<select id="svcFn" name="svcFn"></select>
					</td>
				</tr>
				<tr>
					<th scope="row">작성자</th>
					<td colspan="3">${loginInfo.id}</td>
				</tr>
				<tr>
					<th scope="row"><label for="title">제목</label></th>
					<td colspan="3"><input type="text" id="title" name="title" value="${serviceGuide.title }" style="width:430px" maxlength="20" require="true" msg="제목을 입력하셔야 합니다." /></td>
				</tr>
				<tr>
					<th scope="row">노출 여부</th>
					<td colspan="3">
						<label><input type="radio" id="openY" name="openYN" value="Y" />&nbsp;노출&nbsp;&nbsp;&nbsp;</label>
						<label><input type="radio" id="openN" name="openYN" value="N" checked="checked" />&nbsp;비노출&nbsp;&nbsp;&nbsp;</label>
					</td>
				</tr>
				<tr>
					<th scope="row">서비스 안내</th>
					<td colspan="3">
						<!-- 텍스트 형 활성화시 -->
				        <div id="textType" style="display:block;">
				        	<div class="tab_left1">텍스트 형</div>
				        	<div class="tab_right1"><a href="javascript:contentsType('image');">이미지 형</a></div>
				        	<div class="tab_con">
				            	<div style="padding:5px;"><textarea name="faqContents" rows="20" style="border:1px solid #ccc;width:98%">${serviceGuide.contents}</textarea></div>
				            </div>
				        </div>
				        <!-- 이미지 형 활성화시 -->
				        <div id="imageType" style="display:none;">
				        	<div class="tab_left2"><a href="javascript:contentsType('text');">텍스트 형</a></div>
				        	<div class="tab_right2">이미지 형</div>
				        	<div class="tab_con">
				            	<div id="fileContainer" style="width:100%">
				            		<div style="padding:3px;">
				            			<b>&nbsp;1.&nbsp;&nbsp;</b>
				            			<input type="file" name="image1" style="width:60%"/>
				            			<input type="text" name="description1" style="width:200px;"/> 
				            		</div>	
				            		<div style="padding:3px;">
				            			<b>&nbsp;2.&nbsp;&nbsp;</b>
				            			<input type="file" name="image2" style="width:60%;"/>
				            			<input type="text" name="description2" style="width:200px;"/>
				            		</div>
				            		<div style="padding:3px;">
				            			<b>&nbsp;3.&nbsp;&nbsp;</b>
				            			<input type="file" name="image3" style="width:60%;"/>
				            			<input type="text" name="description3" style="width:200px;"/>
				            		</div>
				            		<div style="padding:3px;">
				            			<b>&nbsp;4.&nbsp;&nbsp;</b>
				            			<input type="file" name="image4" style="width:60%;"/>
				            			<input type="text" name="description4" style="width:200px;"/>
				            		</div>
				            		<div style="padding:3px;">
				            			<b>&nbsp;5.&nbsp;&nbsp;</b>
				            			<input type="file" name="image5" style="width:60%;"/>
				            			<input type="text" name="description5" style="width:200px;"/>
				            		</div>
				            		<div style="padding:3px;">
				            			<b>&nbsp;6.&nbsp;&nbsp;</b>
				            			<input type="file" name="image6" style="width:60%;"/>
				            			<input type="text" name="description6" style="width:200px;"/>
				            		</div>
				            		<div style="padding:3px;">
				            			<b>&nbsp;7.&nbsp;&nbsp;</b>
				            			<input type="file" name="image7" style="width:60%;"/>
				            			<input type="text" name="description7" style="width:200px;"/>
				            		</div>
				            		<div style="padding:3px;">
				            			<b>&nbsp;8.&nbsp;&nbsp;</b>
				            			<input type="file" name="image8" style="width:60%;"/>
				            			<input type="text" name="description8" style="width:200px;"/>
				            		</div>
				            		<div style="padding:3px;">
				            			<b>&nbsp;9.&nbsp;&nbsp;</b>
				            			<input type="file" name="image9" style="width:60%;"/>
				            			<input type="text" name="description9" style="width:200px;"/>
				            		</div>
				            		<div style="padding:3px;">
				            			<b>10.&nbsp;&nbsp;</b>
				            			<input type="file" name="image10" style="width:60%;"/>
				            			<input type="text" name="description10" style="width:200px;"/>
				            		</div>				            						            		
				            	</div>
				            </div>
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