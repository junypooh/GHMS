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
		    var svcListStr = "${svcListStr}";

			$(document).ready(function(){
				
				if(statusCd != "") {
					$("input[name='statusCd'][value='" + statusCd +"']").prop("checked", true);
				}
				
				if(role != "") {
					$("#role").val(role);
				}
				
   				if(svcListStr != "") {
   					var cpCodeContainer = $('#cpCodeContainer');
   					if(svcListStr.indexOf(",") > 0) {	
	   					var svcList = svcListStr.split(",");
	   					
	   					for(var svc in svcList) {
	   						$(cpCodeContainer).find(":checkbox[value='"+ svcList[svc] + "']").prop("checked", true);
	   					}
   					} else {
   						$(cpCodeContainer).find(":checkbox[value='"+ svcListStr + "']").prop("checked", true);
   					}
   					
   				}
				
				$("#btnList").click(function() {
					location.href = "/manager/managerList?page=${param.page}&searchRole=${param.searchRole}&searchColumn=${param.searchString}&searchColumn=${param.searchString}";
				});
				
				$("#btnModify").click(function() {
 					var form = document.forms["modifyForm"];
					
					if(validate(form)) {
						
						if($("#cpCodeContainer input:checked").length < 1) {
							alert("관리 상품을 선택하여 주십시요.");
							return false;
						}						
						
						form.submit();	
					}
				});
				
				$("#btnRemove").click(function(){
					if(confirm("정발로 삭제 하시겠습니까?") == true) {
						var form = document.forms["modifyForm"];
						form.action = "/manager/managerRemove";
						form.submit();
					}
				});
				
			});
			
			
		</script>
	
	</jsp:attribute>
	
	<jsp:body>
		<h2 class="pageTitle">관리자 수정/삭제</h2>
		
		<table class="tbl_1">
			<caption></caption>
			<colgroup>
				<col style="width:15%" />
				<col style="width:*%" />
			</colgroup>
			<tbody>
				<form name="modifyForm" method="post" action="/manager/managerModify">		
				<input type="hidden" id="method" name="method" value="modify" />
				<input type="hidden" id="page" name="page" value="${param.page }" />
				<input type="hidden" id="searchRole" name="searchRole" value="${param.searchRole }" />
				<input type="hidden" id="searchColumn" name="searchColumn" value="${param.searchColumn }" />
				<input type="hidden" id="searchString" name="searchString" value="${param.searchString }" />
				<input type="hidden" id="id" name="id" value="${manager.id }" />
				<tr>
					<th scope="row">관리자 ID</th>
					<td colspan="3">${manager.id }</td>
				</tr>
				<tr>
					<th scope="row">비밀번호</th>
					<td>
						<input type="password" id="pw" name="pw" match="repw" hname="비밀번호" class= "w135"/>
					</td>
					<th scope="row">비밀번호 확인</th>
					<td>
						<input type="password" id="repw" name="repw" hname="비밀번호 확인" class= "w135"/>
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
						<input type="radio" name="statusCd" value="1102" checked="checked"  /> 허용함
						&nbsp;&nbsp;&nbsp;&nbsp;
						<input type="radio" name="statusCd" value="1105"/> 허용 안함
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
				<a id="btnModify" class="btn2 btn2_red30" style="cursor:pointer"><span>수정</span></a>
				<a id="btnRemove" class="btn2 btn2_red30" style="cursor:pointer"><span>삭제</span></a>
			</div>
		</div>			
		
	</jsp:body>
	
</layout:main>