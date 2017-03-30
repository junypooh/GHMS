<%@ page contentType="text/html" trimDirectiveWhitespaces="true" pageEncoding="UTF-8"%>
<%@ taglib prefix="layout" 	tagdir="/WEB-INF/tags/layout" %>
<%@ taglib prefix="c"  		uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" 	uri="http://java.sun.com/jsp/jstl/fmt" %>

<layout:main>

	<jsp:attribute name="javascript">
		<script type="text/javascript">
			${script}
			
			var openYN = "${notice.openYN}";
			var reserveChk = "${notice.reserveChk}";
			var endYN = "${notice.endYN}";			
			var s_hour = '${notice.s_hour}';
			var s_minute = '${notice.s_minute}';
			var e_hour = '${notice.e_hour}';
			var e_minute = '${notice.e_minute}';
			
			$(document).ready(function(){
				//현재시간
				var nowDate = new Date();
				
				// 현재 년월일과 내일 년월일
				var tMonth= nowDate.getMonth()+1; 
				tMonth = tMonth < 10 ? '0' + tMonth : tMonth;
				
				var tDay= nowDate.getDate(); 
				tDay = tDay < 10 ? '0' + tDay : tDay;
				
				//var tYMD= nowDate.getFullYear() + '-' + tMonth + '-' + tDay; 
				
				var nMonth= nowDate.getMonth()+1; 
				nMonth = nMonth < 10 ? '0' + nMonth : nMonth;

				var nDay= nowDate.getDate()+1; 
				nDay = nDay < 10 ? '0' + nDay : nDay;
				
				//var nYMD= nowDate.getFullYear() + '-' + nMonth + '-' + nDay;
				
				var tYMD = new Date().format("yyyy-MM-dd");
				var nYMD = dateAdd("dd", 1, tYMD.replace("-", ""), "-");				

				//노출여부
				if(openYN == 'Y'){
					$("#openY").prop('checked', true);
				}else if(openYN == 'N'){ 
					$("#openN").prop('checked', true);
				}
				
				//예약설정
				if(reserveChk == 'Y'){
					$('#reserveChk').prop('checked', true);
				}else{
					$('#reserveChk').prop('checked', false); 
					$('#endYN').prop('checked', false); 
				}
				
				//종료일 지정안함
				if(reserveChk == 'Y' && endYN == 'Y'){
					$('#endYN').prop('checked', true); 
				}
				
				reserveDisplay();
				//endYNDisplay();
				
			    // 달력 - 시작일
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
			    
			    var year = nowDate.getFullYear();
			    var month = nowDate.getMonth()+1;
			    month = month > 10 ? month : "0" +month
			    var sDay = nowDate.getDate();
			    sDay = sDay > 10 ? sDay : "0"+sDay;
	    		var eDay = nowDate.getDate()+1;
			    eDay = eDay > 10 ? eDay : "0"+eDay;
			    
			    //시작일이 없을때 default 값 입력.
			    if(reserveChk == 'N'){
			    	$('#start_date').val(tYMD); 
			    	$('#end_date').val(nYMD);
			    }
			    
			    //종료일이 없을때 default 값 입력
			    if(reserveChk == 'Y' && endYN == 'Y'){ 
			    	$('#end_date').val(nYMD); 
			    }

				//예약 시작 시간 세팅
				for(var i = 0; i <= 23; i++){
					dateStr = (i < 10) ? '0' + i : i;
					$('#s_hour').append('<option value="' + i + '">' + dateStr + '시</option>');
				}
				if(reserveChk == 'Y'){ $('#s_hour').val(s_hour); 
				}else{ $('#s_hour').val(nowDate.getHours()); }
				
				//예약 시작 분 세팅
				for(var i = 0; i <= 59; i++){
					dateStr = (i < 10) ? '0' + i : i;
					$('#s_minute').append('<option value="' + i + '">' + dateStr + '분</option>');
				}
				if(reserveChk == 'Y'){ $('#s_minute').val(s_minute);
				}else{ $('#s_minute').val(nowDate.getMinutes()); }

				//예약 끝 시간 세팅
				for(var i = 0; i <= 23; i++){
					dateStr = (i < 10) ? '0' + i : i;
					$('#e_hour').append('<option value="' + i + '">' + dateStr + '시</option>');
				}
				if(reserveChk == 'Y' && endYN == 'N'){
					$('#e_hour').val(e_hour);
				}else{
					$('#e_hour').val(nowDate.getHours()); 
				}

				//예약 끝 분 세팅
				for(var i = 0; i <= 59; i++){
					dateStr = (i < 10) ? '0' + i : i;
					$('#e_minute').append('<option value="' + i + '">' + dateStr + '분</option>');
				}
				if(reserveChk == 'Y' && endYN == 'N'){
					$('#e_minute').val(e_minute);
				}else{ $('#e_minute').val(nowDate.getMinutes()); }
				
				$("#btnList").click(function() {
					var form = document.forms["transForm"];
					form.submit();
				});
				
  				$('#endYN').click(function(){ endYNDisplay(); });
				
  				$('#reserveChk').click(function(){ reserveDisplay(); });
  				
				$("#btnModify").click(function() {
					var form = document.forms["modifyForm"];
					
					if(validate(form)) {
						
						form.submit();
					}
				});
				
				$("#btnRemove").click(function() {
					
					if (confirm("삭제하시겠습니까?") == true){ 
						var pk= '${notice.pk}'; 
						$.ajax({
							type : "POST",
						    cache: false,						
							url : "/customer/removePmNotice",
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
			
			function reserveDisplay(){
				//현재시간
				var nowDate = new Date();
				
				// 현재 년월일과 내일 년월일
				var tMonth= nowDate.getMonth()+1; 
				tMonth = tMonth < 10 ? '0' + tMonth : tMonth;
				
				var tDay = nowDate.getDate(); 
				tDay = tDay < 10 ? '0' + tDay : tDay;
				
				var tYMD = nowDate.getFullYear() + '-' + tMonth + '-' + tDay; 
				
				var nMonth = nowDate.getMonth()+1; 
				nMonth = nMonth < 10 ? '0' + nMonth : nMonth;

				var nDay = nowDate.getDate()+1; 
				nDay = nDay < 10 ? '0' + nDay : nDay;
				
				var nYMD = nowDate.getFullYear() + '-' + nMonth + '-' + nDay;
				
				if($('#reserveChk').is(":checked")){
					$('#endYN').attr("disabled", false);
					$('#start_date').attr("style","background-color: #ffffff;width:80px");
					$('#start_date').removeAttr("disabled");
					$('#s_hour').removeAttr("disabled");
					$('#s_minute').removeAttr("disabled");
					
					$('#end_date').attr("style","background-color: #ffffff;width:80px");
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
					
					//$('#start_date').val(tYMD);
					$('#start_date').attr("style","background-color: #e2e2e2;width:80px");
					$('#start_date').attr("disabled", "true"); 
					$('#s_hour').attr("disabled", "true");
					$('#s_minute').attr("disabled", "true");						

					//$('#end_date').val(nYMD);
					$('#end_date').attr("style","background-color: #e2e2e2;width:80px");
					$('#end_date').attr("disabled", "true"); 
					$('#e_hour').attr("disabled", "true");
					$('#e_minute').attr("disabled", "true");
				}	
			}
			
			function endYNDisplay(){
				
				//현재시간
				var nowDate = new Date();
				
				// 현재 년월일과 내일 년월일
				var tMonth= nowDate.getMonth()+1; 
				tMonth = tMonth < 10 ? '0' + tMonth : tMonth;
				
				var tDay = nowDate.getDate(); 
				tDay = tDay < 10 ? '0' + tDay : tDay;
				
				//var tYMD = nowDate.getFullYear() + '-' + tMonth + '-' + tDay; 
				
				var nMonth = nowDate.getMonth()+1; 
				nMonth = nMonth < 10 ? '0' + nMonth : nMonth;

				var nDay = nowDate.getDate()+1; 
				nDay = nDay < 10 ? '0' + nDay : nDay;
				
				//var nYMD = nowDate.getFullYear() + '-' + nMonth + '-' + nDay;
				
				var tYMD = new Date().format("yyyy-MM-dd");
				var nYMD = dateAdd("dd", 1, tYMD.replace("-", ""), "-");				
				
				if($('#reserveChk').is(":checked")){
					if($('#endYN').is(":checked")){
						$('#end_date').attr("style","background-color: #e2e2e2;width:80px")
						$('#end_date').attr("disabled", "true"); 
						$('#e_hour').attr("disabled", "true");
						$('#e_minute').attr("disabled", "true");
					}else{
						//$('#end_date').val(nYMD);
						$('#end_date').attr("style","background-color: #ffffff;width:80px")
						$('#end_date').removeAttr("disabled");
						$('#e_hour').removeAttr("disabled");
						$('#e_minute').removeAttr("disabled");
					}
				}
			}
			
			function openPopup()
			{
				var width = "800";
				var height = "800";
				var leftPos = (screen.availWidth - width)/2;
				var topPos = (screen.availHeight - height)/2;
				var position = "width="+ width + ", height=" + height + ", left=" + leftPos + ", top=" + topPos;
				var url = "/preview?type=webview";
				url += "&pkCode=1406"
				url += "&pk=" + '${notice.pk}';
				url += "&cpCode=" + '${notice.cpCode}';
				
				window.open(url, "winOpen", position, "menubar=1");
			}
			
		</script>
	
	</jsp:attribute>
	
	<jsp:body>
		<form name="transForm" method="get" action="/customer/pmNoticeList">
			<input type="hidden" name="page" value="${param.page }" />
			<input type="hidden" name="cpCode" value="${param.cpCode }" />
			<input type="hidden" name="openYnSelect" value="${param.openYnSelect }" />
			<input type="hidden" name="searchColumn" value="${param.searchColumn }" />
			<input type="hidden" name="searchString" value="${param.searchString }" />	
		</form>
	
		<div class="boardTitle">
			<div class="borderTitle">
				<span class="pageTitle">PM공지</span>
			</div>
		</div>
		<table class="tbl_1">
			<caption></caption>
			<colgroup>
				<col style="width:15%" />
				<col style="width:*%" />
			</colgroup>
			<tbody>
				<form name="modifyForm" method="post" action="/customer/pmNoticeModify">
				<input type="hidden" name="method" value="modify" />
				<input type="hidden" name="modifyId" value="${loginfo.id}" />
				<input type="hidden" name="pk" value="${notice.pk }" />
				<input type="hidden" name="targetCpCode" value="${notice.cpCode }" />	
				<input type="hidden" name="page" value="${param.page }" />
				<input type="hidden" name="cpCode" value="${param.cpCode }" />
				<input type="hidden" name="openYnSelect" value="${param.openYnSelect }" />
				<input type="hidden" name="searchColumn" value="${param.searchColumn }" />
				<input type="hidden" name="searchString" value="${param.searchString }" />	
				<tr>
					<th scope="row">앱명</th>
					<td>${notice.cpName }</td>
				</tr>
				<tr>
					<th scope="row"><label for="title">제목</label></th>
					<td><input type="text" id="title" name="title" class="w630" value="${notice.title }" require="true" msg="제목을 입력하셔야 합니다." /></td>
				</tr>
				<tr>
					<th scope="row">작성자</th>
					<td>${notice.writerId }</td>
				</tr>
				<tr>
					<th scope="colgroup"><input type="checkbox" id="reserveChk" name="reserveChk" checked="checked" value= "Y"/>&nbsp;&nbsp;예약 설정</th>
					<th>
						<div id= "start_div">	
							<span>예약 시작일</span>
							&nbsp;&nbsp;&nbsp;&nbsp;	
							<input type="text" 		id="start_date" 	name= "start_date" value= "${notice.startTime}"  class="ipt_tx" style="width:80px" readonly="readonly"/>
							&nbsp;&nbsp;&nbsp;&nbsp;									
							<select id= "s_hour" name="s_hour"></select>	
							<select id= "s_minute" name="s_minute"></select>
							&nbsp;&nbsp;&nbsp;&nbsp;						
						</div>
						<hr/>
						<div id= "end_div">
							<span>예약 종료일</span>
							&nbsp;&nbsp;&nbsp;&nbsp;	
							<input type="text" 		id="end_date" 	 name="end_date"	value= "${notice.endTime}"  class="ipt_tx" style="width:80px" readonly="readonly"/>
							&nbsp;&nbsp;&nbsp;&nbsp;
							<select id= "e_hour" name="e_hour" ></select>	
							<select id= "e_minute" name="e_minute" ></select>	
							&nbsp;&nbsp;&nbsp;&nbsp;														
							<input type= "checkbox" id ="endYN" name= "endYN" value= "Y">&nbsp;&nbsp;종료일 지정 안함
						</div>
					</th>
				</tr>
				<tr>
					<th scope="row">노출여부</th>
						<td>
							<input type="radio" id= "openY" name="openYN" value="Y"/> 노출
							&nbsp;&nbsp;&nbsp;&nbsp;
							<input type="radio" id= "openN" name="openYN" value="N"/> 비노출
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
				<a id="btnRemove" class="btn2 btn2_black30" style="cursor:pointer"><span>삭제</span></a>
			</div>
			<div class="flr">
				<a id="btnPreview" href="#none" onclick="openPopup();" onfocus="this.flr();" class="btn2 btn2_black30" ><span>미리보기</span></a>
				<a id="btnModify" class="btn2 btn2_red30" style="cursor:pointer"><span>수정</span></a>
			</div>
		</div>			
		
	</jsp:body>
	
</layout:main>