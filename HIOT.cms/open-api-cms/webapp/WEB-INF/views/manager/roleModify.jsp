<%@ page contentType="text/html" trimDirectiveWhitespaces="true" pageEncoding="UTF-8"%>
<%@ taglib prefix="layout" 	tagdir="/WEB-INF/tags/layout" %>
<%@ taglib prefix="c"  		uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" 	uri="http://java.sun.com/jsp/jstl/fmt" %>

<layout:main>

	<jsp:attribute name="javascript">
	
		<script type="text/javascript">
			${script}
			
			var useYN = "${role.useYN}";
			var aclListStr = "${aclListStr}";
		
			$(document).ready(function(){
				
				if(useYN != "") {
					$("#use" + useYN).prop("checked", "checked");
				}
				
				$('#btnList').click(function(){
					location.href = "/manager/roleList";
				});				
				
				$("#btnModify").click(function(){
					var form = document.forms["modifyForm"];
					
					if(validate(form)) {
						actionRole("modify");
					}
				});
				
   				$("#btnRemove").click(function(){ 
  					if(confirm("정말로 삭제 하시겠습니까?")) {
  						actionRole("remove");
  					}
   				});	
   				
   				if(aclListStr != "") {
   					var aclContainer = $('#aclContainer');
   					if(aclListStr.indexOf(",") > 0) {	
	   					var aclList = aclListStr.split(",");
	   					
	   					for(var acl in aclList) {
	   						$(aclContainer).find(":checkbox[value='"+ aclList[acl] + "']").prop("checked", true);
	   					}
   					} else {
   						$(aclContainer).find(":checkbox[value='"+ aclListStr + "']").prop("checked", true);
   					}
   					
   				}   				
				
   				$(".parentCheckBox").each(function(){
  					$(this).click(function(){
						var checked = $(this).prop("checked");
						$(this).parent().parent().next().find(":checkbox").prop("checked", checked);
  					});
   				});				
			});
			
			function actionRole(method)
			{
				form = document.forms["modifyForm"];
				form.method.value = method;
				form.submit();
			}			
			
		</script>
	
	</jsp:attribute>
	
	<jsp:body>
		<h2 class="pageTitle">관리자 역할 수정/삭제</h2>

		<form name="modifyForm" method="post" action="/manager/roleModify">
		<input type="hidden" id="method" name="method" value="modify"/>
		<input type="hidden" id="role" name="role" value="${role.role }"/>
		<table class="tbl_1">
			<caption></caption>
			<colgroup>
				<col style="width:15%" />
				<col style="width:*%" />

			</colgroup>
			<tbody>
				<tr>
					<th scope="row">역할 이름</th>
					<td colspan="3">
						<input type="text" id="name" name="name" style="width:200px" value="${role.name }" require="true" msg="역할 이름을 입력하셔야 합니다."/>
					</td>
				</tr>
					
				<tr>
					<th scope="col">역할 설명</th>
					<td colspan= "3">
					<textarea id="description" name="description" rows="5" style="border:1px solid #ccc;width:98%" msg="내용을 입력하셔야 합니다.">${role.description }</textarea>
					</td>
				</tr>
				<tr>
					<th>역할 사용여부</th>
					<td>
						<input type="radio" id="useY" name="useYN" value="Y" checked="checked"  /> 사용
						&nbsp;&nbsp;&nbsp;
						<input type="radio" id="useN" name="useYN" value="N"/> 사용 안함
					</td>
				</tr>
				
			</tbody>
		</table>
		
		<h2 class="pageTitle">메뉴별 권한</h2>
		<table id="aclContainer" class="tbl_1">
			<caption></caption>
			<colgroup>
				<col style="width:25%" />
				<col style="width:*%" />				
			</colgroup>
			<tbody>
				<tr>
					<th scope="col">메인 메뉴</th>			
					<th scope="col">서브 메뉴</th>			
				</tr>
				<c:forEach var="mainMenu" items="${menuList}" varStatus="status" >
					<c:if test="${mainMenu.upMenu == '0000' }"> 				
					<tr>
						<th>
							<label>
								<input type="checkbox" name="permissions" class="parentCheckBox" value="${mainMenu.menu }">
								&nbsp;${mainMenu.name }
							</label>
						</th>			
						<td>
						<c:forEach var="subMenu" items="${menuList}" varStatus="status">
							<c:if test="${mainMenu.menu == subMenu.upMenu }">
								<label>
									<input type="checkbox" name="permissions" value="${subMenu.menu }"}>
									${subMenu.name }&nbsp;
								</label>
							</c:if>										
						</c:forEach>					
						</td>			
					</tr>	
					</c:if>
				</c:forEach>			
			</tbody>
		</table>
		</form>
				
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