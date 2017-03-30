<%@ page contentType="text/html" trimDirectiveWhitespaces="true" pageEncoding="UTF-8"%>
<%@ taglib prefix="layout" 	tagdir="/WEB-INF/tags/layout" %>
<%@ taglib prefix="c"  		uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" 	uri="http://java.sun.com/jsp/jstl/fmt" %>

<layout:main>

	<jsp:attribute name="javascript">
		<script type="text/javascript">
			${script}
					
			
			$(document).ready(function(){
				
				//현재시간
				var nowDate = new Date();
				
				var tMonth= nowDate.getMonth()+1; 
				tMonth = tMonth < 10 ? '0' + tMonth : tMonth;
				
				var tDay= nowDate.getDate(); 
				tDay = tDay < 10 ? '0' + tDay : tDay;
				
				// 오늘 yyyy-MM-dd tYMD
				//var tYMD= nowDate.getFullYear() + '-' + tMonth + '-' + tDay; 
				
				var nMonth= nowDate.getMonth()+1; 
				nMonth = nMonth < 10 ? '0' + nMonth : nMonth;

				var nDay= nowDate.getDate()+1; 
				nDay = nDay < 10 ? '0' + nDay : nDay;
				
				// 내일 yyyy-MM-dd nYMD
				//var nYMD= nowDate.getFullYear() + '-' + nMonth + '-' + nDay;
				
				var tYMD = new Date().format("yyyy-MM-dd");
				var nYMD = dateAdd("dd", 1, tYMD.replace("-", ""), "-");
				
			    // 달력 - 시작일
			    var start_date= $('#start_date').val(); 
			    $('#start_date').datepicker({
			        dateFormat: 'yy-mm-dd',
			        onSelect: function (dateText, inst) {
			            var sEndDate = jQuery.trim($('#end_date').val());
			            if (sEndDate.length>0) {
			                var iEndDate   = parseInt(sEndDate.replace(/-/g, ''));
			                var iStartDate = parseInt(jQuery.trim(dateText).replace(/-/g, ''));
			                
			                if (iStartDate>iEndDate) {
			                    alert('시작일보다 종료일이 과거일 수 없습니다.');
			                    $('#start_date').val(tYMD);
			                }
			            }
			        }
			    });
			    
			    // 달력 - 종료일
			    $('#end_date').datepicker({
			        dateFormat: 'yy-mm-dd',
			        onSelect: function (dateText, inst) {
			            var sStartDate = jQuery.trim($('#start_date').val());
			            if (sStartDate.length>0) {
			                var iStartDate = parseInt(sStartDate.replace(/-/g, ''));
			                var iEndDate  = parseInt(jQuery.trim(dateText).replace(/-/g, ''));
			                
			                if (iStartDate>iEndDate) {
			                    alert('시작일보다 종료일이 과거일 수 없습니다.');
			                    $('#end_date').val(nYMD);
			                }
			            }
			        }
			    });
			    
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


				//예약 끝 시간 세팅
				for(var i = 0; i <= 23; i++){
					dateStr = (i < 10) ? '0' + i : i;
					$('#e_hour').append('<option value="' + i + '">' + dateStr + '시</option>');
				}
				$('#e_hour').val(nowDate.getHours());

				//예약 끝 분 세팅
				for(var i = 0; i <= 59; i++){
					dateStr = (i < 10) ? '0' + i : i;
					$('#e_minute').append('<option value="' + i + '">' + dateStr + '분</option>');
				}
				$('#e_minute').val(nowDate.getMinutes());

				$('#start_date').val(tYMD);
				$('#end_date').val(nYMD);
				
				$("#btnList").click(function() {
					var form = document.forms["transForm"];
					form.submit();
				});
				
				$('#endYN').click(function(){ endYNDisplay(); });
				
  				$('#reserveChk').click(function(){ reserveDisplay(); });

				$("#btnRegister").click(function() {
					var form = document.forms["registerForm"];
					if(validate(form)) {
						form.submit();
					}
				});
			});
			
			function reserveDisplay(){
				if($('#reserveChk').is(":checked")){
					$('#endYN').attr("disabled", false);
					$('#start_date').attr("style","background-color: #ffffff;width:80px")
					$('#start_date').removeAttr("disabled");
					$('#s_hour').removeAttr("disabled");
					$('#s_minute').removeAttr("disabled");
					
					$('#end_date').attr("style","background-color: #ffffff;width:80px")
					$('#end_date').removeAttr("disabled");
					$('#e_hour').removeAttr("disabled");
					$('#e_minute').removeAttr("disabled");
				}else{						
					$('#endYN').attr("checked", false);
					$('#endYN').attr("disabled", true);
					$('#s_hour').removeAttr("value");
					$('#s_minute').removeAttr("value");
					$('#e_hour').removeAttr("value");
					$('#e_minute').removeAttr("value");
					
					$('#start_date').attr("style","background-color: #e2e2e2;width:80px")
					$('#start_date').attr("disabled", "true"); 
					$('#s_hour').attr("disabled", "true");
					$('#s_minute').attr("disabled", "true");						

					$('#end_date').attr("style","background-color: #e2e2e2;width:80px")
					$('#end_date').attr("disabled", "true"); 
					$('#e_hour').attr("disabled", "true");
					$('#e_minute').attr("disabled", "true");
				}	
			}
			
			function endYNDisplay(){
				if($('#reserveChk').is(":checked")){
					if($('#endYN').is(":checked")){
						$('#end_date').attr("style","background-color: #e2e2e2;width:80px")
						$('#end_date').attr("disabled", "true"); 
						$('#e_hour').attr("disabled", "true");
						$('#e_minute').attr("disabled", "true");
					}else{
						$('#end_date').attr("style","background-color: #ffffff;width:80px")
						$('#end_date').removeAttr("disabled");
						$('#e_hour').removeAttr("disabled");
						$('#e_minute').removeAttr("disabled");
					}
				}
			}
		
			
		</script>
	
	</jsp:attribute>
	
	<jsp:body>
		<form name="transForm" method="get" action="/customer/popupNoticeList">
		</form>
	
		<div class="boardTitle">
			<div class="borderTitle">
				<span class="pageTitle">팝업공지 등록</span>
			</div>
		</div>
		<table class="tbl_1">
			<caption></caption>
			<colgroup>
				<col style="width:20" />
			</colgroup>
			<tbody>
				<form name="registerForm" method="post" action="/customer/popupNoticeRegister">
				<input type="hidden" name="method" value="register" />
				<input type="hidden" name="writerId" value="${loginInfo.id }" />
				<tr>
					<th scope="row">앱명</th>
					<td>
						<select id="cpCode" name=cpCode>
								<option value= "001">홈 캠</option>
								<option value= "002">홈 피트니스</option>
								<option value= "003">홈 매니저</option>
						</select>
					</td>
				</tr>
				<tr>
					<th scope="row"><label for="title">제목</label></th>
					<td><input type="text" id="title" name="title" class="w630" value="${notice.title }" require="true" msg="제목을 입력하셔야 합니다." /></td>
				</tr>
				<tr>
					<th scope="row">작성자</th>
					<td>${loginInfo.id }</td>
				</tr>
				<tr>
					<th scope="colgroup"><input type="checkbox" id="reserveChk" name="reserveChk" checked="checked"/>&nbsp;&nbsp;예약 설정</th>
					<th>
						<div id= "start_div">	
							<span>예약 시작일</span>
							&nbsp;&nbsp;&nbsp;&nbsp;
							<input type="text" 		id="start_date" 	name= "start_date" value=""  class="ipt_tx" style="width:80px" readonly="readonly"/>
							&nbsp;&nbsp;&nbsp;&nbsp;									
							<select id= "s_hour" name="s_hour"></select>	
							<select id= "s_minute" name="s_minute" ></select>
							&nbsp;&nbsp;&nbsp;&nbsp;						
						</div>
						<hr/>
						<div id= "end_div">
							<span>예약 종료일</span> 
							&nbsp;&nbsp;&nbsp;&nbsp;
							<input type="text" 		id="end_date" 	 name="end_date"	value=""  class="ipt_tx" style="width:80px" readonly="readonly"/>
							&nbsp;&nbsp;&nbsp;&nbsp;
							<select id= "e_hour" name="e_hour" ></select>	
							<select id= "e_minute" name="e_minute" ></select>	
							&nbsp;&nbsp;&nbsp;&nbsp;														
							<input type= "checkbox" id ="endYN" name= "endYN" value= "Y"> 종료일 지정 안함
						</div>
					</th>
				</tr>
				<tr>
					<th scope="row">노출여부</th>
					<td>
						<input type="radio" name="openYN" value="Y"  /> 노출
						&nbsp;&nbsp;&nbsp;&nbsp;
						<input type="radio" name="openYN" value="N" checked="checked"/> 비노출
					</td>
				</tr>					
				<tr>	
					<th scope="col">하루동안 보지 않기</th>
					<td><select name= "nomoreYN">
								<option value="Y">Y</option>
								<option value="N">N</option>
																</select>
					</td>
				<tr>
						<th scope="col">TYPE</th>
					<td><select name="popupType">
							<!-- 	<option value="2101">팝업</option>
								<option value="2102">경고창</option>
								<option value="2103">일반</option>
							 -->
							 <c:forEach var="popupType" items="${typeList}" varStatus="status">
								<option value="${popupType.code}">${popupType.memo}</option>
							 </c:forEach>								
						</select>
							 
					</td>
				</tr>
				<tr>
					<td colspan="2">
						<textarea name="contents" rows="30" style="border:1px solid #ccc;width:98%" require="true" msg="내용을 입력하셔야 합니다.">${notice.contents }
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