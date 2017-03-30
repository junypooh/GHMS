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
				
				$("#btnModify").click(function() {
					
					var cpCode= '${manageGoods.cpCode}';
					var pk = '${manageGoods.pk}';
					var name= $('#name').val();
					var submitFg= false;
					
					var form = document.forms["modifyForm"];	
					if (confirm(" 수정하시겠습니까?") == true){
						
						$.ajax({
							async : false, 
							type : "POST",
						    cache: false,						
							url : "/customer/checkManageGoodsFormData",
							dataType : "json",
							data : { "cpCode" : cpCode, "name" : name, "pk" : pk}
						})
						.done(function(result){
							if(result.code == 200) {
								submitFg = true; 
							}
							else if(result.code == 201) {
								alert('동일 서비스명이 존재합니다. 확인하시고 다시 등록하여 주세요.'); 
								submitFg = false;
							}
							else{
								alert("서버에서 오류가 발생하였습니다.\n다시 시도하여 주십시오.");
								submitFg = false; 
							}
						})
						.fail(function( jqXHR, textStatus, errorThrown){ 
							console.log(jqXHR); submitFg = false;
						});	
						if(validate(form) & (submitFg)){
							form.submit();
						}	
					}
				});
				
				$("#btnRemove").click(function() {
					
					if (confirm("삭제하시겠습니까?") == true){
						var pk = ""; 
						pk= '${manageGoods.pk}'; 
						$.ajax({
							type : "POST",
						    cache: false,						
							url : "/customer/removeManageGoods",
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
					}else{
						return;
					}
				});
			});
			
		</script>
	
	</jsp:attribute>
	
	<jsp:body>
		<form name="transForm" method="get" action="/customer/manageGoodsList">
			<input type="hidden" name="page" value="${param.page }" />
			<input type="hidden" name="searchColumn" value="${param.searchColumn }" />
			<input type="hidden" name="searchString" value="${param.searchString }" />
		</form>	
		<div class="boardTitle">
			<div class="borderTitle">
				<span class="pageTitle">상품 별 기능관리</span>
			</div>
		</div>
		<table class="tbl_1">
			<caption></caption>
			<colgroup>
				<col style="width:15%" />
				<col style="width:*" />
			</colgroup>
			<tbody>
				<form name="modifyForm" method="post" onsubmit="return false;" action="/customer/manageGoodsModify">
					<input type="hidden" name="method" value="modify" />
					<input type="hidden" name="pk" value="${manageGoods.pk}" />
					<input type="hidden" name="modifyId" value="${modifyId}" />
					<input type="hidden" name="page" value="${param.page }" />
					<input type="hidden" name="searchColumn" value="${param.searchColumn }" />
					<input type="hidden" name="searchString" value="${param.searchString }" />
									
					<tr>
						<th scope="row">앱명</th>
						<td>${manageGoods.cpName }</td>
					</tr>						
					<tr>
						<th scope="col">작성자</th>
						<td >${manageGoods.writerId}</td>
					</tr>
					<tr>
						<th scope="row">기능명</th>
						<td><input type="text" id="name" name="name" class="w230" maxlength="20" require="true" msg="기능명을 입력하셔야 합니다." value="${manageGoods.name }"/></td>
					</tr>
					<tr>
						<th>기능 설명</th>
						<td>
						<textarea id= "description" name="description" rows="10" style="border:1px solid #ccc;width:98%" require ="true" msg="내용을 입력하셔야 합니다.">${manageGoods.description }</textarea>
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
				<a id="btnModify" class="btn2 btn2_red30" style="cursor:pointer"><span>수정</span></a>
			</div>
		</div>				
		
	</jsp:body>
	
</layout:main>