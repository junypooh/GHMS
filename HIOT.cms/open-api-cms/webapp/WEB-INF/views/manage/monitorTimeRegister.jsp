<%@ page contentType="text/html" trimDirectiveWhitespaces="true" pageEncoding="UTF-8"%>
<%@ taglib prefix="layout" 	tagdir="/WEB-INF/tags/layout" %>
<%@ taglib prefix="c"  		uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" 	uri="http://java.sun.com/jsp/jstl/fmt" %>

<layout:main>

	<jsp:attribute name="javascript">
		<script type="text/javascript">
		
			${script}

			var interval = "${monitor.interval}";
			var unlimitedYN = "${monitor.unlimitedYN}";
		    
			$(document).ready(function(){
				
				if(unlimitedYN != "") {
					$("#unlimitedYN").prop("checked", true);
				}
				
				var timeSpinner = $("#timeSpinner").spinner();
				
				if(interval != "") {
					timeSpinner.spinner('value', interval);
				} else {
					timeSpinner.spinner('value', 1);
				}
				
				timeSpinner.spinner('option', 'min', 1 );				
				
  				$('#unlimitedYN').click(function(){					
  					var obj = document.getElementById("unlimitedYN"); 
  					if(obj.checked){
  						obj.value = "Y";					
  					}
  					else{
  						obj.value ="N";
  					}
  				});
			
				$("#btnList").click(function() {
					var form = document.forms["transForm"];
					form.submit();
				});
				
				$("#btnRegister").click(function() {
					var timeSpinner = $("#timeSpinner").val();
					var form = document.forms["registerForm"];
					
					if($('#unlimitedYN').is(":checked")){
						timeSpinner = -1;
					}
					
					if(validate(form)) {
						form.interval.value = timeSpinner;
						form.submit();	
					}
				});
			});
			
		</script>
	
	</jsp:attribute>
	
	<jsp:body>
		<form name="transForm" method="get" action="/manage/monitorTimeList">
		<input type="hidden" name="page" value="${param.page }" />
		<input type="hidden" name="searchColumn" value="${param.searchColumn }" />
		<input type="hidden" name="searchString" value="${param.searchString }" />
		</form>
	
		<div class="boardTitle">
			<div class="borderTitle">
				<span class="pageTitle">올레 홈캠 모니터링 시간 조절</span>
			</div>
		</div>
		<table class="tbl_1">
			<caption></caption>
			<colgroup>
				<col style="width:20%" />
				<col style="width:*%" />

			</colgroup>
			<tbody>
				<form name="registerForm" method="post" action="/manage/monitorTimeRegister">
				<input type="hidden" name="method" value="register" />
				<input type="hidden" name="page" value="${param.page }" />
				<input type="hidden" name="searchColumn" value="${param.searchColumn }" />
				<input type="hidden" name="searchString" value="${param.searchString }" />
				<input type="hidden" name="interval" value="" />
				<tr>
					<th scope="row">앱명</th>
					<td colspan="3">올레 홈캠</td>
				</tr>				
				<tr>
					<th scope="col">변경자</th>
					<td>${loginInfo.id}</td>
				</tr>
				<tr>
					<th>모니터링 시간 변경</th>
					<td>
						<input type="text" id="timeSpinner" name="timeSpinner" value="${monitor.interval }" require="true" msg="시간을 입력하셔야 합니다." style="width:50px;" class="numOnly"/> 분
						&nbsp;&nbsp;&nbsp;&nbsp; 
						<input type="checkbox"  id="unlimitedYN" name="unlimitedYN"/> <span>제한 없음</span> 
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