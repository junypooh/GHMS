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
				if( $("#searchColumn").val() == "sdeb.dev_cctc_mac"){
					str = $("#searchString").val();
					$("#searchString").val( str.replace( /:/gi ,"" ).replace( /(\w)(?=(?:\w{2})+(?!\w))/gi, '$1:') );
				}
				
				var form = document.forms["searchCam"];
				form.action="/monitor/signalPwrResultListPage";
				form.submit();
			}
		</script>	
		
	</jsp:attribute>

	<jsp:body>
	
		<div class="boardTitle">
			<div class="borderTitle">
				<span class="pageTitle" >올레 홈캠 카메라 신호세기 결과조회</span>
			</div>
		</div>
		
		<div class="boardTop">	
			<form name="searchCam" method="get" >
				<div style="text-align:right">
					<select id="searchColumn" name="searchColumn" title="검색조건을 선택하세요">
						<option value="mb.mbr_id">고객 ID</option>
						<option value="stcb.tel_no">휴대폰 번호</option>
						<option value="sdb.spot_dev_id">카메라 ID</option>
						<option value="sdeb.dev_cctc_mac">카메라 mac</option>
					</select>
					<input type="text" id="searchString" name="searchString" style="width:150px;" title="검색어를 입력하세요" value="${param.searchString }" />
					<button type="button" id="btnSearchCam" class="btn" style="width:100px;"><span>카메라 검색</span></button>
				</div>				
			</form>
		</div>
		
		<table class="board mt10">
			<caption>모니터링 조회 대상 카메라를 확인 할수 있는 표</caption>
			<colgroup>
				<col style="width:5%" />
				<col style="width:12%" />
				<col style="width:12%" />
				<col style="width:12%" />
				<col style="width:12%" />
				<col style="width:10%" />
				<col style="width:12%" />
				<col style="width:10%" />
				<col style="width:10%" style="display:none"/>
			</colgroup>
			<thead>
				<tr>
					<th scope="col">선택</th>
					<th scope="col">No.</th>
					<th scope="col">olleh ID</th>
					<th scope="col">휴대폰 번호</th>
					<th scope="col">카메라 ID</th>
					<th scope="col">카메라 닉네임</th>
					<th scope="col">카메라 mac</th>
					<th scope="col">접속상태</th>
				</tr>
			</thead>
			
			<tbody id="signalCheckListContainer">
			
			<!-- DYNAMIC AREA 'list' -->
			<c:if test="${empty signalCheckList}">
				<tr> 	
					<td align="center" colspan="8">검색된 데이타가 없습니다</td>
				</tr>
			</c:if>
			
			<c:if test="${!empty signalCheckList}">
			<script>
				$(document).ready(function() {
					//--------------------------- 날짜관련 스크립트 ---------------------------
					//현재시간
					var nowDate = new Date();
					//var sHour	=  eHour	=  nowDate.getHours() +1;	//eHour 기준 24시간 검색하므로 +1 해줌 (분단위 고려) 
					var sHour	=  eHour	=  nowDate.getHours();	//eHour 기준 24시간 검색하므로 +1 해줌 (분단위 고려) 
					if( "${param.searchStartHour}" == "" ){
						$("#searchStartHour").val(sHour);
					}
					/* if( "${param.searchEndDate}" == "" ){
						$("#searchEndHour").val(eHour);
					} */
		
					//-------- 검색시간 시작
					// default 끝 날짜 (7일뒤 날짜)
					nowDate.setDate(nowDate.getDate() - 7);
					
					// default 시작 날짜 (오늘 날짜)
					var sMonth	= nowDate.getMonth()+1; 
					sMonth = sMonth < 10 ? '0' + sMonth : sMonth;
					
					var sDay	= nowDate.getDate(); 
					sDay = sDay < 10 ? '0' + sDay : sDay;
		
					var sDate	= nowDate.getFullYear() + '-' + sMonth + '-' + sDay; 
		
					//-------- 검색시간 종료
					nowDate.setDate(nowDate.getDate() + 7);
					
					var eMonth= nowDate.getMonth()+1; 
					eMonth = eMonth < 10 ? '0' + eMonth : eMonth;
		
					var eDay= nowDate.getDate(); 
					eDay = eDay < 10 ? '0' + eDay : eDay;
					
					var eDate= nowDate.getFullYear() + '-' + eMonth + '-' + eDay;
					
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
						if($("#svcTgtSeq").val() == ""){
							alert( "조회하실 카메라를 선택해 주세요." );
							return;
						}
						
						//검색범위 시간을 24시간으로 제한 -> 일주일로 수정.
						if( diffHour() > 24*8){
							//alert( "검색시간을 24시간 이하로 선택해주세요." );
							alert( "검색기간을 일주일 이하로 선택해주세요." );
							return;
						}
						
						var form = document.forms["searchForm"];
						form.submitType.value = "page";
						form.target="screen";
						form.action="/monitor/signalPwrResultListPageLog";
						form.submit();
					});		
					
					
					$("#btnDownload").click(function() {
						if($("#svcTgtSeq").val() == ""){
							alert( "로그 조회하실 카메라를 선택해 주세요." );
							return;
						}
						
						//검색범위 시간을 24시간으로 제한 -> 일주일로 수정.
						if( diffHour() > 24*8){
							//alert( "검색시간을 24시간 이하로 선택해주세요." );
							alert( "검색기간을 일주일 이하로 선택해주세요." );
							return;
						}
						
						var form = document.forms["searchForm"];
						form.submitType.value = "download";
						//form.target="screen";
						form.action="/monitor/signalPwrResultListPageLog.download";
						form.submit();
					});	
					
					$('#screen').load(function() {
						//$(this).height( $(this).contents().find('body')[0].scrollHeight );
						var iframeHeight = (this).contentWindow.document.body.scrollHeight;
						(this).height=iframeHeight+20;
					});
				});
				
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
					
					 var iDiffHour = (dEndTime - dStartTime) / (60 * 60 * 1000) ;
					
					 return iDiffHour;
				}
				
				function selectCam(el){
					$("#svcTgtSeq").val(	$(el).parent().parent().attr("data-svcTgtSeq")	);
					$("#spotDevSeq").val(	$(el).parent().parent().attr("data-spotDevSeq")	);
					$("#devUUID ").val(		$(el).parent().parent().attr("data-devUUID")	);
					$("#devCctvMac ").val(		$(el).parent().parent().attr("data-devCctvMac")	);
				}
			</script>

			<c:forEach var="signalCheck" items="${signalCheckList}" varStatus="status" >
			<tr data-svcTgtSeq="${signalCheck.svcTgtSeq}" data-spotDevSeq="${signalCheck.spotDevSeq}" data-devUUID="${signalCheck.devUUID}" data-devCctvMac="${signalCheck.devCctvMac}" onmouseover="this.style.backgroundColor='#f7f7f7'" onmouseout="this.style.backgroundColor=''" bgcolor="white">
				<td><input type="radio" name="camChk" onClick="selectCam(this);"></td>
				<td style="text-align:center;"><fmt:formatNumber value="${signalCheckCount - status.index }" pattern="#,###"/></td>
				<td style="text-align:center;">${signalCheck.mbrId}</td>					
				<td>&nbsp;${signalCheck.telNo}</td>
				<td style="text-align:center;">${signalCheck.spotDevId}</td>
				<td style="text-align:center;">${signalCheck.devNm}</td>
				<td style="text-align:center;">${signalCheck.devCctvMac}</td>
				<td style="text-align:center;">${signalCheck.devConStat}</td>
			</tr>
			</c:forEach>
			
			<tr>
				<td colspan="8">
					<form name="searchForm" method="get" >

					<input type="hidden" id="svcTgtSeq" 	name="svcTgtSeq">
					<input type="hidden" id="spotDevSeq" 	name="spotDevSeq">
					<input type="hidden" id="devUUID" 		name="devUUID">
					<input type="hidden" id="devCctvMac" 		name="devCctvMac">
					
					<input type="hidden" id="submitType" 	name="submitType" value="wifi">
					<input type="hidden" id="searchLogType" name="submitType" value="ALL">
					
					<table style="width:100%; border:solid 1px; border-color:#E8E8E8;">
						<tr>
							<td style="font-weight:bold; text-align:center;">
								<input type="text" id="searchStartDate" name="searchStartDate" value="${param.searchStartDate }" readonly="readonly" style="width:70px" />
								<select id="searchStartHour" name="searchStartHour" style="width:60px">
								<%-- <c:if test="${!empty hourList}">
									<c:forEach var="hour" items="${hourList}" varStatus="status" >
										<option <c:if test="${hour.hour == param.searchStartHour}">selected</c:if> value="${hour.hour}">${hour.hour} 시</option>
									</c:forEach>
								</c:if> --%>
								<c:forEach var="hour" items="${hourList}" varStatus="status" >
									<option value="${hour.hour}">${hour.hour} 시</option>
								</c:forEach>
								</select>
								00분 00초
								<b>~</b> 
								<input type="text" id="searchEndDate" name="searchEndDate" value="${param.searchEndDate }" readonly="readonly" style="width:70px" />
								<select id="searchEndHour" name="searchEndHour" style="width:60px">
								<%-- <c:if test="${!empty hourList && param.searchEndHour != null}">
									<c:forEach var="hour" items="${hourList}" varStatus="status" >
										<option <c:if test="${hour.hour == param.searchEndHour}">selected</c:if> value="${hour.hour}">${hour.hour} 시</option>
									</c:forEach>
								</c:if>
								<c:if test="${!empty hourList && param.searchEndHour == null}">
									<c:forEach var="hour" items="${hourList}" varStatus="status" >
										<option <c:if test="${hour.hour == 23}">selected</c:if> value="${hour.hour}">${hour.hour} 시</option>
									</c:forEach>
								</c:if> --%>
								<c:forEach var="hour" items="${hourList}" varStatus="status" >
									<option <c:if test="${hour.hour == 23}">selected="selected"</c:if> value="${hour.hour}">${hour.hour} 시</option>
								</c:forEach>
								</select>
								59분 59초
								
								<button type="button" id="btnSearchLog" class="btn" style="width:100px;"><span><b>결과조회</b></span></button>
								<button type="button" id="btnDownload" class="btn" style="width:100px;"><span><b>다운로드</b></span></button>
							</td>
						</tr>
					</table>
					</form>
					
					<table style="width:100%;">
						<tr>
							<td colspan="8">
								<iframe id="screen" name="screen" width="100%" height="0" src="" style="border:1px solid; border-color:#E8E8E8;" frameborder="0" marginheight="0" scrolling="auto"></iframe>
							</td>
						</tr>
					</table>
				</td>
			</tr>
		</c:if>			
		</table>
		
	</jsp:body>
</layout:main>
