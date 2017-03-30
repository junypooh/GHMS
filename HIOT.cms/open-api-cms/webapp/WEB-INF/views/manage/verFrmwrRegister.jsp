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
					var verNum= $('#verNum').val(); 
					var cpCode= $('#cpCode option:selected').val();
					var mandatoryYN= $('#mandatoryYN option:selected').val();
					var form = document.forms["registerForm"];
					var submitFg= false; 
					
					if(!validate(form)){
						return false; 
					}					
					if(!verCheck(verNum)){
						alert("0.00.00 형태로 버전 정보 입력이 가능합니다.")
						return false;
					}else{
						$('#verNum').attr("verNum", true);
					}
					
					$.ajax({
						async : false, 
						type : "POST",
					    cache: false,						
						url : "/manage/checkVerFrmwrFormData",
						dataType : "json",
						data : { "cpCode" : cpCode, "verNum" : verNum}
					})
					.done(function(result){
						if(result.code == 200) { submitFg = true; }
						else if(result.code == 201) { alert('등록하려는 앱버전보다 높은 앱버전이 있습니다. 확인하시고 다시 등록해 주세요.'); submitFg = false; }
						else if(result.code == 202) { alert('등록하려는 앱버전과 동일한 앱버전이 있습니다. 확인하시고 다시 등록해 주세요.'); submitFg = false; }
						else{ alert("서버에서 오류가 발생하였습니다.\n다시 시도하여 주십시오."); submitFg = false; }
					})
					.fail(function( jqXHR, textStatus, errorThrown){ 
						console.log(jqXHR); submitFg = false;
					});									
					//모든 검증이 true일때 submit();
					if(submitFg){ form.submit(); }
				});				
					
			});
			
			// 버전 체크 0.00.00
			function verCheck(str) {
				var chk_str = str;
				//정규식 : 0.00.00
				var pattern = /^(\d{1,1})([.]\d{2,2}?)([.]\d{2,2}?)?$/;
				if(str.length == 7 && pattern.test(chk_str)){
					return true;		
				}else{
					return false; 
				}
			}
						
		</script>	
	</jsp:attribute>
	
	<jsp:body>
		<form name="transForm" method="get" action="/manage/verFrmwrList">
			<input type="hidden" name="page" value="${param.page }" />
			<input type="hidden" name="cpCode" value="${param.cpCode }" />
		</form>
		<div class="boardTitle">
			<div class="borderTitle">
				<span class="pageTitle">단말 펌웨어 버전관리 등록</span>
			</div>
		</div>
		<table class="tbl_1">
			<caption></caption>
			<colgroup>
				<col style="width:15%" />
				<col style="width:*%" />
			</colgroup>
			<tbody>
				<form name="registerForm" method="post" action="/manage/verFrmwrRegister">
					<input type="hidden" name="method" value="register" />
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
						<th scope="col">업데이트 필수 여부</th>
						<td>
							<select id= "mandatoryYN" name= "mandatoryYN">
								<option value= "N">업데이트 선택</option>
								<option value= "Y">업데이트 필수</option>
							</select>
						</td>
					</tr>
					<tr>
						<th>배포 설정</th>
						<td colspan="3">배포시작&nbsp;&nbsp; 
							<input type="text" id="start_date" 	name= "start_date" value= "${verFrmwr.startTime}"  class="ipt_tx" style="width:80px" readonly="readonly"><span></span>
							&nbsp;&nbsp;									
							<select id= "s_hour" name="s_hour"></select>	
							<select id= "s_minute" name="s_minute" ></select>
						</td>
					</tr>
						
					<tr>
						<th scope="row">단말 버전</th>
						<td><input type= "text" id="verNum" name="verNum" class="w230" require="true" msg="단말 버전을 입력하셔야 합니다."></td>
						<th scope="row">모델 명</th>
						<td><input type="text" id="modelName" name="modelName" class="w230" require="true" msg="모델 명을 입력하셔야 합니다." maxlength="15"/></td>
					</tr>
					<tr>
						<th>단말 버전 설명</th>
						<td colspan="3">
							<textarea name="verTitle" rows="10" style="border:1px solid #ccc;width:98%" require="true" msg="내용을 입력하셔야 합니다.">${verFrmwr.verMemo }</textarea>
						</td>					
					</tr>
				</form>
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