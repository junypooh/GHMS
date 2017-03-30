<%@ page contentType="text/html" trimDirectiveWhitespaces="true" pageEncoding="UTF-8"%>
<%@ taglib prefix="layout" 	tagdir="/WEB-INF/tags/layout" %>
<%@ taglib prefix="c"  		uri="http://java.sun.com/jsp/jstl/core" %>

<layout:main>

	<jsp:attribute name="javascript">
	
		<script type="text/javascript">
		
			${script}
			
			var useYN = "${subMenu.useYN}";
			
			$(document).ready(function(){
				
				if(useYN != "")	{
					$("#useYN option").each(function(){
						if(this.value == useYN)
							$(this).attr("selected", true);
						}
					);
				}
				
				$("#btnRegister").click(function(){ btnAction("register"); });
				$("#btnModify").click(function(){ btnAction("modify"); });		
				
				$('#btnInit').click(function() {
					var form = document.forms["transForm"];
					form.method.value = "";
					form.menu.value = "";
					form.name.value = "";
					form.url.value = "";
					form.useYN.value = "Y";
					form.sortNo.value = "";
					
					$("#btnRegister").show();
					$("#btnModify").hide();
				});		
				
				initBtns();
				
			});
			
    		function initBtns() {
    			if(useYN != ""){
		   			$("#btnRegister").hide();
		   			$("#btnModify").show();
   				} else {
		   			$("#btnRegister").show();
		   			$("#btnModify").hide();    				
    			}
    		}	
    		
    		function btnAction(method) {
    			var form = document.forms["transForm"];
    			if(validate(form))
   				{
        			form.method.value = method;
        			form.action = "/manager/subMenu";
        			form.submit();   				
   				}    			
    		}   
    		
    		function listAction(menu, method){
    			var result = true;
    			
    			if(method == "remove") { 
    				result = confirm("정말로 삭제 하시겠습니까?"); 
    			}
    			
    			var form = document.forms["listForm"];
				
    			form.method.value = method;
				form.menu.value = menu;
				form.action = "/manager/subMenu";
				
				if(result) form.submit();
    		}    		
			
			
		</script>	
		
	</jsp:attribute>

	<jsp:body>
	
		<h2 class="pageTitle"><b>서브 메뉴관리</b></h2>
		
		<table  class="tbl_1">
			<caption></caption>
			<colgroup>
				<col style="width:8%" />
				<col style="width:8%" />
				<col style="width:8%" />
				<col style="width:15%" />
				<col style="width:36%" />
				<col style="width:10%" />
				<col style="width:15%" />
			</colgroup>		
			<tbody>
				<tr>
					<td></td>
					<th style="text-align:center;">순서</th>
					<th style="text-align:center;">코드</th>
					<th style="text-align:center;">이름</th>
					<th style="text-align:center;">경로</th>
					<th style="text-align:center;">노출여부</th>			
					<th style="text-align:center;">메인메뉴로 가기</th>			
				</tr>    
				<tr>
					<td style="height:30px"><b>메인메뉴</b></td>
					<td style="height:30px" align="center">${mainMenu.sortNo }</td>
					<td style="height:30px" align="center">${mainMenu.menu }</td>
					<td style="height:30px" align="center">${mainMenu.name }</td>
					<td style="height:30px">${mainMenu.url }</td>
					<td style="height:30px" align="center">
						<c:if test="${mainMenu.useYN == 'Y' }">노출</c:if>
						<c:if test="${mainMenu.useYN == 'N' }">비노출</c:if>
					</td>
					<td style="height:30px" align="center"><a href="/manager/mainMenu">Click</a></td>
				</tr> 
			</tbody> 
		</table>		
		<br/><br/>
		<table class="tbl_1">
			<caption></caption>
			<colgroup>
				<col style="width:15%" />
				<col style="width;35%" />
				<col style="width:15%" />
				<col style="width:35%" />
			</colgroup>
			<tbody>
				<form id="transForm" name="transForm" method="post">	
				<input type="hidden" name="method" />					
				<input type="hidden" name="orgMenu" value="${subMenu.menu }" />			
				<input type="hidden" name="upMenu" value="${mainMenu.menu }" />			
				<tr>			
					<th scope="row"><label for="menu">코드</label></th>
					<td><input type="text" id="menu" name="menu" style="width:70px" maxlength="4" minbytes="4" msg="코드는 4자여야 합니다." class="numOnly" value="${subMenu.menu }" /></td>
					<th scope="row"><label for="name">이름</label></th>
					<td><input type="text" id="name" name="name" style="width:200px" minbytes="2" msg="이름은 2자이상 입력하셔야 합니다." value="${subMenu.name }" /></td>
				</tr>
				<tr>
					<th scope="row"><label for="hpnum">경로</label></th>
					<td><input type="text" id="url" name="url" style="width:97%" value="${subMenu.url }" /></td>
					<th scope="row"><label for="email">노출여부</label></th>
					<td>
						<select id="useYN" name="useYN" style="width:100px">
							<option value="Y">노출</option>
							<option value="N">비노출</option>
						</select>
					</td>
				</tr>
				<tr>
					<th scope="row"><label for="sortNo">순서</label></th>
					<td colspan="3"><input type="text" id="sortNo" name="sortNo" style="width:50px" minbytes="1" msg="순서를 입력하셔야 합니다." class="numOnly" value="${subMenu.sortNo }" /></td>
				</tr>
				</form>
			</tbody>
		</table>	
		<br/>
		<div class="flr">
			<a id="btnRegister" class="btn2 btn2_red30" style="cursor:pointer"><span>등록</span></a>
			<a id="btnModify" class="btn2 btn2_red30" style="cursor:pointer"><span>수정</span></a>
			<a id="btnInit" class="btn2 btn2_black30" style="cursor:pointer"><span>초기화</span></a>
		</div>
		<br/><br/><br/>
		<table class="board mt10">
			<form id="listForm" name="listForm" method="post">
			<input type="hidden" name="method" />
			<input type="hidden" name="menu" />
			<input type="hidden" name="upMenu" value=${mainMenu.menu } />
			</form>		
			<caption></caption>
			<colgroup>
				<col style="width:5%" />
				<col style="width:5%" />
				<col style="width:20%" />
				<col style="width:*%" />
				<col style="width:8%" />
				<col style="width:8%" />
				<col style="width:8%" />
			</colgroup>
			<thead>
				<tr>
					<th scope="col">순서</th>
					<th scope="col">코드</th>
					<th scope="col">이름</th>
					<th scope="col">경로</th>
					<th scope="col">노출여부</th>
					<th scope="col">수정</th>
					<th scope="col">삭제</th>
				</tr>
			</thead>
			
			<tbody>
			
			<!-- DYNAMIC AREA 'list' -->
			<c:if test="${!empty subMenuList}">
				<c:forEach var="subMenu" items="${subMenuList}" varStatus="status" >
				<tr onmouseover="this.style.backgroundColor='#f7f7f7'" onmouseout="this.style.backgroundColor=''" bgcolor="white">
					<td>${subMenu.sortNo }</td>
					<td>${subMenu.menu }</td>
					<td>${subMenu.name }</td>
					<td style="text-align:left;">${subMenu.url }</td>
					<td>
						<c:if test="${subMenu.useYN == 'Y' }">노출</c:if>
						<c:if test="${subMenu.useYN == 'N' }">비노출</c:if>
					</td>
					<td><a onclick="listAction('${subMenu.menu }', 'modifyForm');" style="cursor:pointer;">Click</a></td>
					<td><a onclick="listAction('${subMenu.menu }', 'remove');" style="cursor:pointer;">Click</a></td>
				</tr>
				</c:forEach>
			</c:if>			
	
			<c:if test="${empty subMenuList}">
				<tr> 	
					<td align="center" colspan="7">등록된 메뉴가 없습니다</td>
				</tr>
			</c:if>
			
			</tbody>
		</table>

		
	</jsp:body>
</layout:main>