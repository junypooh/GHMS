<%@ page contentType="text/html" trimDirectiveWhitespaces="true" pageEncoding="UTF-8"%>
<%@ taglib prefix="layout" 	tagdir="/WEB-INF/tags/layout" %>
<%@ taglib prefix="c"  		uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" 	uri="http://java.sun.com/jsp/jstl/fmt" %>

<layout:main>

	<jsp:attribute name="javascript">
	
		<script type="text/javascript">			
			var page   			= "${param.page}";
			var searchColumn 	= "${param.searchColumn}"; 
			var searchStartDate = "${param.searchStartDate}";
			var searchEndDate 	= "${param.searchEndDate}";
			
			$(document).ready(function() {
				$("#searchColumn").val(searchColumn);
				
				$("#btnSearchCam").click(function() {
					SearchCam();
				});	
				
				$("#searchString").keypress(function(e){
					if (e.keyCode == 13){
						SearchCam();
					}    
				});
			});	
			
			function SearchCam(){
				if( $("#searchString").val() == "" ){
					alert("검색어를 입력해 주세요.");
					$("#searchString").focus();
					
					return;
				}

				//맥어드레스 검색일 경우 자동으로 포멧 변환
				if( $("#searchColumn").val() == "sdeb.dev_gw_mac"){
					str = $("#searchString").val();
					$("#searchString").val( str.replace( /:/gi ,"" ).replace( /(\w)(?=(?:\w{2})+(?!\w))/gi, '$1:') );
				}
				
				var form = document.forms["searchCam"];
				form.action="/monitor/managerDeviceHbaseRlt";
				form.submit();
			}
		</script>	
		
	</jsp:attribute>

	<jsp:body>
	
		<div class="boardTitle">
			<div class="borderTitle">
				<span class="pageTitle" >홈매니저 IoT 기기 상태정보 결과 조회</span>
			</div>
		</div>
		
		<div class="boardTop">	
			<form id="searchCam" name="searchCam" method="get" >
				<div style="text-align:right">
					<select id="searchColumn" name="searchColumn" title="검색조건을 선택하세요">
						<option value="mb.mbr_id">고객 ID</option>
						<option value="stcb.tel_no">휴대폰 번호</option>
						<option value="sdb.spot_dev_id">GW ID</option>
						<option value="sdeb.dev_gw_mac">GW mac</option>
					</select>
					<input type="text" id="searchString" name="searchString" style="width:150px;" title="검색어를 입력하세요" value="${param.searchString }" />
					<button type="button" id="btnSearchCam" class="btn" style="width:100px;"><span>GW 검색</span></button>
				</div>				
			</form>
		</div>
		
		<table class="board mt10">
			<caption>모니터링 조회 대상 GW를 확인 할수 있는 표</caption>
			<colgroup>
				<col style="width:5%" />
				<col style="width:15%" />
				<col style="width:15%" />
				<col style="width:15%" />
				<col style="width:18%" />
				<col style="width:15%" />
				<col style="width:15%" />
			</colgroup>
			<thead>
				<tr>
					<th scope="col">선택</th>
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
			<c:if test="${empty signalCheckList}">
				<tr> 	
					<td align="center" colspan="7">검색된 데이타가 없습니다</td>
				</tr>
			</c:if>
			
			<c:if test="${!empty signalCheckList}">
			<script>
				$(document).ready(function() {
					//--------------------------- 날짜관련 스크립트 ---------------------------
					/*
					//var sHour	=  eHour	=  nowDate.getHours() +1;	//eHour 기준 24시간 검색하므로 +1 해줌 (분단위 고려) 
					var sHour	=  eHour	=  nowDate.getHours() ;	
					if( "${param.searchStartHour}" == "" ){
						$("#searchStartHour").val(sHour);
					}
					 if( "${param.searchEndDate}" == "" ){
						$("#searchEndHour").val(eHour);
					} 
					*/
		
					//현재시간
					var nowDate = new Date();

					//-------- 검색시간 종료 (오늘)
					var eMonth= nowDate.getMonth()+1; 
					eMonth = eMonth < 10 ? '0' + eMonth : eMonth;
		
					var eDay= nowDate.getDate(); 
					eDay = eDay < 10 ? '0' + eDay : eDay;
					
					var eDate= nowDate.getFullYear() + '-' + eMonth + '-' + eDay;

					//-------- 검색시간 시작 (7일:6일전 00시부터)
					nowDate.setDate(nowDate.getDate() - 6);
					
					var sMonth	= nowDate.getMonth()+1; 
					sMonth = sMonth < 10 ? '0' + sMonth : sMonth;
					
					var sDay	= nowDate.getDate(); 
					sDay = sDay < 10 ? '0' + sDay : sDay;
		
					var sDate	= nowDate.getFullYear() + '-' + sMonth + '-' + sDay; 
		
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
				                
				                else if (nowDate<iEndDate) {
				                    alert('종료일이 미래일 수 없습니다.');
				                    $("#searchStartDate").val(searchStartDate=='' ? sDate : searchStartDate);
				    				$("#searchEndDate").val(searchEndDate=='' ? eDate : searchEndDate);
				                }
				            }
				        }
				    });
					
					$("#searchStartDate").val(searchStartDate=='' ? sDate : searchStartDate);
					$("#searchEndDate").val(searchEndDate=='' ? eDate : searchEndDate);
					//--------------------------- 날짜관련 스크립트 ---------------------------
					
					$("#btnSearchLog").click(function() {
						$("#submitType").val("page");
						$("#searchRcvDtLast").val(		"");
						$("#searchEventDtLast").val( 	"");
						
						$("#searchRcvDtSeqLast").val(	"");
						$("#searchOccDtSeqLast").val( 	"");

						if($("#svcTgtSeq").val() == ""){
							alert( "조회하실 GW를 선택해 주세요." );
							return;
						}
						
						//검색범위 시간을 24시간으로 제한 -> 일주일로 수정.
						if( diffHour() > 24*7){
							alert( "검색기간을 일주일 이하로 선택해주세요." );
							return;
						}
						 
						//var form = document.forms["searchForm"];
						//form.submitType.value = "page";
						//form.target="screen";
						//form.action="/monitor/hbaseRltLog";
						//form.submit();
						
						
						$.ajax({
							type : "POST",
						    cache: false,						
							url : "/monitor/managerDeviceHbaseRltLog",
							data : $("#searchForm").serialize(),
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
					});		
					
					
					$("#btnDownload").click(function() {
						$("#submitType").val("download");

						if($("#svcTgtSeq").val() == ""){
							alert( "로그 조회하실 카메라를 선택해 주세요." );
							return;
						}
						
						//검색범위 시간을 24시간으로 제한 -> 일주일로 수정.
						if( diffHour() > 24*7){
							alert( "검색기간을 일주일 이하로 선택해주세요." );
							return;
						}
						
						var form = document.forms["searchForm"];
						form.submitType.value = "download";
						form.target="screen";
						form.action="/monitor/managerDeviceHbaseRltLog";
						form.submit();
						
					});	
				});
			
				function MoreLog(){
					$("#searchRcvDtLast").val(		$("#screen_log .rcvDtLast:last").text()	);
					$("#searchEventDtLast").val(	$("#screen_log .eventDtLast:last").text()	);
					
					$("#searchRcvDtSeqLast").val(	$("#screen_log .rcvDtLast:last").attr("rcvDtSeq")	);
					$("#searchOccDtSeqLast").val(	$("#screen_log .eventDtLast:last").attr("occDtSeq")	);
					
					
					$.ajax({
						type : "POST",
					    cache: false,						
						url : "/monitor/managerDeviceHbaseRltLog",
						data : $("#searchForm").serialize(),
						success:function(data){
							$("#screen_log").append(data);
			            }
					})
					.done(function(result){
						
					})
					.fail(function( jqXHR, textStatus, errorThrown){
						console.log(jqXHR);
					});
					
				} 

				
				//검색범위 시간
				function diffHour(){
					var sStartDate 	= jQuery.trim( $('#searchStartDate').val() );
					var dStartTime = new Date( 
										parseInt( sStartDate.substr(0, 4) )
										, parseInt( sStartDate.substr(5, 2) )
										, parseInt( sStartDate.substr(8, 2) )
										, parseInt( $('#searchStartHour').val() ) 
									);
	
					var sEndDate 	= jQuery.trim( $('#searchEndDate').val() );
					var dEndTime = new Date( 
										parseInt( sEndDate.substr(0, 4) )
										, parseInt( sEndDate.substr(5, 2) )
										, parseInt( sEndDate.substr(8, 2) )
										, parseInt( $('#searchEndHour').val() ) 
									);
					
					 var iDiffHour = (dEndTime - dStartTime) / (60 * 60 * 1000) + 1;	//59분59초 이므로 1시간 플러스
					 
					 return iDiffHour;
				}
				
				function selectCam(el){
					$("#svcTgtSeq").val(	$(el).parent().parent().attr("data-svcTgtSeq")	);
					$("#spotDevSeq").val(	$(el).parent().parent().attr("data-spotDevSeq")	);
					$("#devUUID ").val(		$(el).parent().parent().attr("data-devUUID")	);
				}
			</script>

			<c:forEach var="signalCheck" items="${signalCheckList}" varStatus="status" >
			<tr data-svcTgtSeq="${signalCheck.svcTgtSeq}" data-spotDevSeq="${signalCheck.spotDevSeq}" data-devUUID="${signalCheck.devUUID}" onmouseover="this.style.backgroundColor='#f7f7f7'" onmouseout="this.style.backgroundColor=''" bgcolor="white">
				<td><input type="radio" name="camChk" onClick="selectCam(this);"></td>
				<td style="text-align:center;"><fmt:formatNumber value="${signalCheckCount - status.index }" pattern="#,###"/></td>
				<td style="text-align:center;">${signalCheck.mbrId}</td>					
				<td>&nbsp;${signalCheck.telNo}</td>
				<td style="text-align:center;">${signalCheck.spotDevId}</td>
				<td style="text-align:center;">${signalCheck.devNm}</td>
				<td style="text-align:center;">${signalCheck.devGwMac}</td>
			</tr>
			</c:forEach>
			
			<tr>
				<td colspan="7">
					<form id="searchForm" name="searchForm" method="get" >

					<input type="hidden" id="svcTgtSeq" 	name="svcTgtSeq">
					<input type="hidden" id="spotDevSeq" 	name="spotDevSeq">
					<input type="hidden" id="devUUID" 		name="devUUID">
					<input type="hidden" id="submitType" 	name="submitType">
					<input type="hidden" id="searchRcvDtLast" 		name="searchRcvDtLast">
					<input type="hidden" id="searchEventDtLast" 	name="searchEventDtLast">
					<input type="hidden" id="searchRcvDtSeqLast" 	name="searchRcvDtSeqLast">
					<input type="hidden" id="searchOccDtSeqLast" 	name="searchOccDtSeqLast">
					
					<table style="width:100%; border:solid 1px; border-color:#E8E8E8;">
						<tr>
							<td style="font-weight:bold; text-align:center;">
								<button type="button" id="btnSearchLog" class="btn" style="width:100px;"><span><b>결과조회</b></span></button>
								<button type="button" id="btnDownload" class="btn" style="width:100px;"><span><b>다운로드</b></span></button>
							</td>
						</tr>
					</table>
					</form>
					
					<table style="width:100%;" class="board mt10" id="screen_log" style="display:none"></table>
					
					<table id="screen_log_title" style="display:none">
						<colgroup>
							<col style="width:20%" />
							<col style="width:20%" />
							<col style="width:10%" />
							<col style="width:20%" />
							<col style="width:10%" />
							<col style="width:20%" />
						</colgroup>
						<thead>
							<tr>
								<th scope="col">장치종류</th>
								<th scope="col">장치이름</th>
								<th scope="col">수신시간</th>
								<th scope="col">연결상태</th>
								<th scope="col">수신시간</th>
								<th scope="col">단말상태</th>
							</tr>
						</thead>
					</table>
				</td>
			</tr>
		</c:if>			
		</table>
		
	</jsp:body>
</layout:main>
