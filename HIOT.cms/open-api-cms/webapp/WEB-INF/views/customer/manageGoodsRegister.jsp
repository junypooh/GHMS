<%@ page contentType="text/html" trimDirectiveWhitespaces="true" pageEncoding="UTF-8"%>
<%@ taglib prefix="layout" 	tagdir="/WEB-INF/tags/layout" %>
<%@ taglib prefix="c"  		uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" 	uri="http://java.sun.com/jsp/jstl/fmt" %>

<layout:main>

	<jsp:attribute name="javascript">
	
		<script type="text/javascript">
		
			${script}

			$(document).ready(function(){
				$('#start_date').datepicker();   
				//현재시간
				var nowDate = new Date();

				nowDate.setTime(nowDate.getTime());

				//예약 시작 시간 세팅
				for(var i = 0; i <= 23; i++){
					dateStr = (i < 10) ? '0' + i : i;

					$('#s_hour').append('<option value="' + i + '">' + dateStr + '시</option>');
				}
				$('#s_hour').val(nowDate.getHours());	

				//예약 시작 분 세팅
				for(var i = 0; i <= 59; i++){
					dateStr = (i < 10) ? '0' + i : i;
					$('#s_minute').append('<option value="' + i + '">' + dateStr + '분</option>');
				}
				$('#s_minute').val(nowDate.getMinutes());
				$("#btnList").click(function() {
					var form = document.forms["transForm"];
					form.submit();
				});
				
				$("#btnRegister").click(function() {
					var cpCode= $('#cpCode option:selected').val();
					var name= $('#name').val();
					var submitFg= false;
					var form = document.forms["registerForm"];
					
					if($("#name").val().trim() == ''){
						alert('기능명을 입력해주세요.');
						return false;
					}
					if($("#description").val().trim() == ''){
						alert('기능설명을 입력해주세요.');
						return false;
					}
					
					$.ajax({
						async : false, 
						type : "POST",
					    cache: false,						
						url : "/customer/checkManageGoodsFormData",
						dataType : "json",
						data : { "cpCode" : cpCode, "name" : name }
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
				<span class="pageTitle">상품 별 기능 관리 등록</span>
			</div>
		</div>
		<table class="tbl_1">
			<caption></caption>
			<colgroup>
				<col style="width:15%" />
				<col style="width:*%" />
			</colgroup>
			<tbody>
				<form name="registerForm" method="post" onsubmit="return false;" action="/customer/manageGoodsRegister">
				<input type="hidden" name="method" value="register" />
				<input type="hidden" name="writerId" value="${writerId}" />
				<input type="hidden" name="page" value="${param.page }" />
				<input type="hidden" name="searchColumn" value="${param.searchColumn }" />
				<input type="hidden" name="searchString" value="${param.searchString }" />				
				<input type="hidden" name="writer" value="${param.searchString }" />				
				<tr>
					<th scope="row">앱명</th>
					<td colspan="3">
						<select id="cpCode" name = cpCode>
								<option value= "001">홈 캠</option>
								<option value= "002">홈 피트니스</option>
								<option value= "003">홈 매니저</option>
						</select>
					</td>
				</tr>
					
				<tr>
					<th scope="col">작성자</th>
					<td >${writerId}</td>
				</tr>					
				<tr>
					<th scope="row">기능명</th>
					<td><input type="text" id="name" name="name" class="w230" maxlength="20" require="true" msg="기능명을 입력하셔야 합니다."/></td>
				</tr>
				<tr>
					<th>기능 설명</th>
					<td>
					<textarea id= "description" name="description" rows="10" style="border:1px solid #ccc;width:98%" require ="true" msg="내용을 입력하셔야 합니다.">${manageGoods.verMemo }
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