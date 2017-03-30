<%@ page contentType="text/html" trimDirectiveWhitespaces="true" pageEncoding="UTF-8"%>
<%@ taglib prefix="layout" 	tagdir="/WEB-INF/tags/layout" %>
<%@ taglib prefix="c"  		uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" 	uri="http://java.sun.com/jsp/jstl/fmt" %>

<layout:main>

	<jsp:attribute name="javascript">
	
		<script type="text/javascript">	
		
			var searchDateType	 = "${param.searchDateType}";
			var searchMonth	 = "${param.searchMonth}";
			var searchColumn = "${param.searchColumn}";
			var searchString = "${param.searchString}";		
			var searchStartDate = "${param.searchStartDate}";
			var searchEndDate = "${param.searchEndDate}";
		
			$(document).ready(function(){
				
				//현재시간
				var nowDate = new Date();
				// default 끝 날짜 (7일뒤 날짜)
				nowDate.setDate(nowDate.getDate() - 1);
				
				// default 시작 날짜 (오늘 날짜)
				var sMonth= nowDate.getMonth()+1; 
				sMonth = sMonth < 10 ? '0' + sMonth : sMonth;
				var sDay= nowDate.getDate(); 
				sDay = sDay < 10 ? '0' + sDay : sDay;
				var sDate= nowDate.getFullYear() + '-' + sMonth + '-' + sDay; 
				
				nowDate.setDate(nowDate.getDate() + 1);
				var eMonth= nowDate.getMonth()+1; 
				eMonth = eMonth < 10 ? '0' + eMonth : eMonth;
				var eDay= nowDate.getDate(); 
				eDay = eDay < 10 ? '0' + eDay : eDay;
				var eDate= nowDate.getFullYear() + '-' + eMonth + '-' + eDay;
				
				menuAction(searchDateType);
				
				$("#searchColumn").val(searchColumn);
				
				$('#searchStartDate').datepicker({
			        dateFormat: 'yy-mm-dd',
			        onSelect: function (dateText, inst) {
			            var sEndDate = jQuery.trim($('#searchEndDate').val());
			            if (sEndDate.length>0) {
			                var iEndDate   = parseInt(sEndDate.replace(/-/g, ''));
			                var iStartDate = parseInt(jQuery.trim(dateText).replace(/-/g, ''));
			                
			                if (iStartDate>iEndDate) {
			                    alert('시작일보다 종료일이 과거일 수 없습니다.');
			                    $("#searchStartDate").val(searchStartDate=='' ? sDate : searchStartDate);
			    				$("#searchEndDate").val(searchEndDate=='' ? eDate : searchEndDate);
			                }
			            }
			        }
			    });
	
			    $('#searchEndDate').datepicker({
			        dateFormat: 'yy-mm-dd',
			        onSelect: function (dateText, inst) {
			            var sStartDate = jQuery.trim($('#searchStartDate').val());
			            if (sStartDate.length>0) {
			                var iStartDate = parseInt(sStartDate.replace(/-/g, ''));
			                var iEndDate  = parseInt(jQuery.trim(dateText).replace(/-/g, ''));
			                
			                if (iStartDate>iEndDate) {
			                    alert('시작일보다 종료일이 과거일 수 없습니다.');
			                    $("#searchStartDate").val(searchStartDate=='' ? sDate : searchStartDate);
			    				$("#searchEndDate").val(searchEndDate=='' ? eDate : searchEndDate);
			                }
			                
			                /* else if (nowDate<iEndDate) {
			                    alert('종료일이 미래일 수 없습니다.');
			                    $("#searchStartDate").val(searchStartDate=='' ? sDate : searchStartDate);
			    				$("#searchEndDate").val(searchEndDate=='' ? eDate : searchEndDate);
			                } */
			            }
			        }
			    });
				
				$("#searchStartDate").val(searchStartDate=='' ? sDate : searchStartDate);
				$("#searchEndDate").val(searchEndDate=='' ? eDate : searchEndDate);
				
				$("#btnSearch").click(function(){
					var form = document.forms["searchForm"];
					form.submit();					
				});
				
				$("#rangeDate").click(function(){
					menuAction('range');
				});
				
				$("#monthDate").click(function(){
					menuAction('month');
				});
				
				var start = 0, end = 6;
				var dateStr = '', nowMonth = '', dateFormatStr = '';
				var nowDate = new Date().format('yyyyMMdd');
	
				if(Number(nowDate.substring(6, 8) < 10)){
					nowMonth = dateAdd('mm', -1, nowDate, '');
					nowMonth = nowMonth.substring(0, 6);
					
					start = 0;
					end = 6;
				}else{
					nowMonth = nowDate.substring(0, 6);
				}
									
				for(var i = start ; i <= end; i++){
					dateStr = dateAdd('mm', -i, nowDate, '');
					dateFormatStr = dateStr.substring(0, 4) + '년 ' + dateStr.substring(4, 6) + '월';
					dateStr = dateStr.substring(0, 6);
					 
					$('#searchMonth').append('<option value="' + dateStr + '">' + dateFormatStr + '</option>');
				}	
				
				if(searchDateType != "") {
					$("#" + searchDateType + "Date").prop("checked", "checked");
				}
				
				$("#searchMonth").val(searchMonth);
				
				$("#btnSignalCheck").click(function(){
					if($("#signalCheckListContainer input[type=radio]:checked").size() == 0) {
						alert("이력조회 할 대상을\n하나 이상 선택 하셔야합니다.");
						return false;
					}
				if($("#signalCheckListDetail input[type=checkbox]:checked").size() == 0) {
					alert("이력조회 할 사용정보 구분을\n하나 이상 선택 하셔야합니다.");
					return false;
				}
					signalCheckRequest();
				});	
			});
	
			function menuAction(action){
				if(action == 'month'){
					$('#searchStartDate').attr("style","background-color: #e2e2e2;width:100px;")
					$('#searchEndDate').attr("style","background-color: #e2e2e2;width:100px;")
					
					$('#searchStartDate').attr("disabled", true);
					$('#searchEndDate').attr("disabled", true);
					$('#searchMonth').attr("disabled", false);
					
				}else {
					$('#searchStartDate').attr("style","background-color: #ffffff;width:100px;")
					$('#searchEndDate').attr("style","background-color: #ffffff;width:100px;")
					
					$('#searchStartDate').attr("disabled", false);
					$('#searchEndDate').attr("disabled", false);
					$('#searchMonth').attr("disabled", true);
				}
			}
			
			function signalCheckRequest() {

				$("#signalCheckListContainer input[type=radio]:checked").each(function(){
				    $("#svcTgtSeq").val($(this).val());
					//svcTgtSeq = $(this).val();
					//signalCheckList.svcTgtSeq = svcTgtSeq;
				});
								
				$("#signalCheckListDetail input[type=checkbox]:checked").each(function(){
				    $(this).val("Y");
				});
				
				$.ajax({
					type : "POST",
				    cache: false,						
					url : "/inquiry/managerDeviceLog",
					//data : JSON.stringify(signalCheckList),
					data : $("#searchLogForm").serialize(),
					success:function(data){
						$("#screen_log").show();	
		              	$("#screen_log").html( $("#screen_log_title").html() + data);
		            }
				})
				.done(function(result){
					
				})
				.fail(function( jqXHR, textStatus, errorThrown){
					console.log(jqXHR);
				});
				
			}
			
		</script>	
		
	</jsp:attribute>

	<jsp:body>
		
		<div class="boardTitle">
			<div class="borderTitle">
				<span class="pageTitle">홈매니저 사용 이력 조회</span>
			</div>
		</div>
		<div class="boardTop">	
			<div class="flr">
				<form name="searchForm" method="get" action="/inquiry/managerDeviceList">
				<select id="searchColumn" name="searchColumn" title="검색조건을 선택하세요">
					<option value="mb.mbr_id">고객 ID</option>
					<option value="stcb.tel_no">휴대폰 번호</option>
					<option value="sdeb.dev_gw_mac">GW mac</option>
				</select> 
				<input type="text" id="searchString" name="searchString"  title="검색어를 입력하세요" value="${param.searchString }" />
				<button type="button" id="btnSearch" class="btn"><span>검색</span></button>
				</form>
			</div>
		</div>
		
		<table class="board mt10">
			<caption>올레 홈매니저 GW 신호상태 조회의 id, 휴대폰번호, 게이트웨이id, 닉네임을 확인 할수 있는 표</caption>
			<colgroup>
				<col style="width:5%" />
				<col style="width:5%" />
				<col style="width:13%" />
				<col style="width:*%" />
				<col style="width:14%" />
				<col style="width:18%" />
				<col style="width:18%" />
			</colgroup>
			<thead>
				<tr>
					<th scope="col">&nbsp;</th>
					<th scope="col">No.</th>
					<th scope="col">olleh ID</th>
					<th scope="col">휴대폰 번호</th>
					<th scope="col">GW ID</th>
					<th scope="col">GW 닉네임</th>
					<th scope="col">GW mac</th>
				</tr>
			</thead>
			
			<tbody id="signalCheckListContainer">
			<!-- DYNAMIC AREA 'list' -->
			<c:if test="${!empty signalCheckList}">
				<c:forEach var="signalCheck" items="${signalCheckList}" varStatus="status" >
				<tr data-svcTgtSeq="${signalCheck.svcTgtSeq}" onmouseover="this.style.backgroundColor='#f7f7f7'" onmouseout="this.style.backgroundColor=''" bgcolor="white">
					<td><input type="radio" name="svcTgtSeqRadio" value="${signalCheck.svcTgtSeq}"></td>
					<td style="text-align:center;"><fmt:formatNumber value="${signalCheckCount - status.index }" pattern="#,###"/></td>
					<td style="text-align:center;">${signalCheck.mbrId}</td>					
					<td>&nbsp;${signalCheck.telNo}</td>
					<td style="text-align:center;">${signalCheck.spotDevId}</td>
					<td style="text-align:center;">${signalCheck.devNm}</td>
					<td style="text-align:center;">${signalCheck.devGwMac}</td>
				</tr>
				</c:forEach>
			</c:if>			
	
			<c:if test="${empty signalCheckList}">
				<tr> 	
					<td align="center" colspan="7">검색된 데이타가 없습니다</td>
				</tr>
			</c:if>
			</tbody>
		</table>
		<form name="searchLogForm" id="searchLogForm" method="get">
		<input type="hidden" id="svcTgtSeq" name="svcTgtSeq" />
		<table style="border-width:1px;border-style:solid;width:100%;height:40px">
			<tr>
				<td style="text-align:center"><b>사용정보 구분</b></td>
				<td style="text-align:left" id="signalCheckListDetail">
					<label><input type="checkbox" name="logTypeContlOcc" checked="checked" /> 기기원격제어</label>
					&nbsp;
					<label><input type="checkbox" name="logTypeEventOut" checked="checked" /> 이상상태발생</label>
				</td>
				<td style="text-align:left">
					<input type="radio" id="rangeDate" name="searchDateType" value="range" checked="checked"/>
					<b>기간별&nbsp;</b>
					<input type="text" id="searchStartDate" name="searchStartDate" value="${param.searchStartDate }" readonly="readonly" style="width:100px" />
					<b>~</b> 
					<input type="text" id="searchEndDate" name="searchEndDate" value="${param.searchEndDate }" readonly="readonly" style="width:100px" />
					&nbsp;
					<input type="radio" id="monthDate" name="searchDateType" value="month">
					<b>월간별&nbsp;</b>
					<select id="searchMonth" name="searchMonth" style="width:100px;" disabled="disabled"></select>
				</td>
				<td style="text-align:center;">
					<input type="button" id="btnSignalCheck" name="btnSignalCheck" value="이력 조회" class="btn" style="cursor:pointer">
				</td>
			</tr>
		</table>
		</form>
					
		<table style="width:100%;" class="board mt10" id="screen_log" style="display:none"></table>
		
		<table id="screen_log_title" style="display:none">
			<colgroup>
				<col style="width:17%" />
				<col style="width:14%" />
				<col style="width:14%" />
				<col style="width:14%" />
				<col style="width:14%" />
				<col style="width:14%" />
				<col style="width:14%" />
			</colgroup>
			<thead>
				<tr>
					<th scope="col">제어시간</th>
					<th scope="col">사용자ID</th>
					<th scope="col">허브명</th>
					<th scope="col">장치종류</th>
					<th scope="col">장치명</th>
					<th scope="col">사용정보</th>
					<th scope="col">제어종류</th>
				</tr>
			</thead>
		</table>	
		
	</jsp:body>
	
</layout:main>