<%@ page contentType="text/html" trimDirectiveWhitespaces="true" pageEncoding="UTF-8"%>
<%@ taglib prefix="layout" 	tagdir="/WEB-INF/tags/layout" %>
<%@ taglib prefix="c"  		uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" 	uri="http://java.sun.com/jsp/jstl/fmt" %>

<layout:main>

	<jsp:attribute name="javascript">
	
		<script type="text/javascript">
			${script}
			
			var statusCd = "${manager.statusCd}";
		    var role = "${manager.role}";

			$(document).ready(function(){
				
				if(statusCd != "") {
					$("input[name='statusCd'][value='" + statusCd +"']").prop("checked", true);
				}
				
				if(role != "") {
					$("#role").val(role);
				}
				
				$("#btnOverlap").click(function() {
					var id = $("#id").val();
					if(id.trim().length > 3 && id.trim().length < 13) {
						isOverlapID(id);
					} else {
						alert($("#id").attr("msg"));
					}
				});
				
				$("#btnRegister").click(function() {
 					var form = document.forms["registerForm"];
					
					if(validate(form)) {
						
						if($("#cpCodeContainer input:checked").length < 1) {
							alert("관리 상품을 선택하여 주십시요.");
							return false;
						}
						
						if($("#isOverlapID").val().trim() == "false") {
							form.submit();	
						} else {
							var msg = $("#isOverlapID").val().trim() == "true" ? "중복된 아이디 입니다." : "아이디 중복 체크를 하셔야합니다."; 
							alert(msg);							
						}
					}
				});
				
				$("#btnList").click(function() {
					location.href = "/manager/managerList";
				});
				
			});
			
			function isOverlapID(id) {
				
				var msg = "";
				
				$.ajax({
					type : "POST",
				    cache: false,						
					url : "/manager/isOverlapID",
					dataType : "json",
					data : { "id" : id}
				})
				.done(function(result){
					if(result.code == 200) {
						$("#isOverlapID").val(result.data);
						if(result.data == "true") {
							msg = "중복된 아이디입니다.";
						} else {
							msg = "사용 가능한 아이디입니다.";
						}
					} else {
						msg = result.msg;
					}
					
					alert(msg);
				})
				.fail(function( jqXHR, textStatus, errorThrown){
					console.log(jqXHR);
				});					
			}
			
			
		</script>
	
	</jsp:attribute>
	
	<jsp:body>
		<h2 class="pageTitle">관리자 등록</h2>
		
		<table class="tbl_1">
			<caption></caption>
			<colgroup>
				<col style="width:15%" />
				<col style="width:*%" />
			</colgroup>
			<tbody>
				<form name="registerForm" method="post" action="/manager/managerRegister">		
				<input type="hidden" id="method" name="method" value="register" />
				<input type="hidden" id="isOverlapID" name="isOverlapID" value="" />
				<tr>
					<th scope="row">관리자 ID</th>
					<td colspan="3">
						<input type="text" id="id" name="id" value="${manager.id }" maxlength="12" require="true" ispattern="alphanum" minbytes="4" msg="아이디는 최소 4자 이상 12자 이하로 입력하셔야 합니다." class= "w135 hangulLimit"/>&nbsp;&nbsp;
						<button type="button" id="btnOverlap" class="btn"><span>중복 확인</span></button>
					</td>
				</tr>
				<tr>
					<th scope="row">비밀번호</th>
					<td>
						<input type="password" id="pw" name="pw" require="true" match="repw" hname="비밀번호" class= "w135"/>
					</td>
					<th scope="row">비밀번호 확인</th>
					<td>
						<input type="password" id="repw" name="repw" minlength="4" hname="비밀번호 확인" class= "w135"/>
					</td>	
				</tr>				
				<tr>
					<th scope="row">관리자 성명</th>
					<td>
						<input type="text" id="name" name="name" value="${manager.name }" require="true" minlength="2" msg="관리자 성명은 2자 이상 입력하셔야 합니다." class= "w135"/>
					</td>
					<th scope="row">부서</th>
					<td>
						<input type="text" id="team" name="team" value="${manager.team }" class= "w135"/>
					</td>	
				</tr>
				<tr>
					<th scope="row">핸드폰 번호</th>
					<td>
						<input type="text" id="mobile" name="mobile" value="${manager.mobile }" require="true" minlength="9" msg="핸드폰 번호를 입력 하셔야합니다." class= "w135 numOnly"/>
					</td>
					<th scope="row">이메일</th>
					<td>
						<input type="text" id="email" name="email" value="${manager.email }" require="true" ispattern="email" hname="이메일" class= "w135 hangulLimit"/>
					</td>	
				</tr>
				<tr>
					<th scope="row">사용 목적</th>
					<td colspan="3">
						<input type="text" id="memo" name="memo" value="${manager.memo }" class= "w630">
					</td>
				</tr>
				<tr>
					<th>허용 여부</th>
					<td colspan="3">
						<label><input type="radio" name="statusCd" value="1102" checked="checked"  /> 허용함</label>
						&nbsp;&nbsp;&nbsp;&nbsp;
						<label><input type="radio" name="statusCd" value="1105"/> 허용 안함</label>
					</td>
				</tr>
				<tr>
					<th scope="col">관리자 역할</th>
					<td colspan="3">
						<select id="role" name="role">
						<c:forEach var="role" items="${roleList }">
							<option value="${role.role }">${role.name }</option>
						</c:forEach>
						</select>
					</td>			
				</tr>				
				<tr>
					<th scope="col">관리 상품</th>
					<td id="cpCodeContainer" colspan="3">
						<c:forEach var="service" items="${serviceList }">
						<label>
							<input type="checkbox" name="cpCodes" value="${service.name }" />
							${service.memo }&nbsp;&nbsp;
						</label>
						</c:forEach>
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