<%@ page contentType="text/html" trimDirectiveWhitespaces="true" pageEncoding="UTF-8"%>
<%@ taglib prefix="layout" 	tagdir="/WEB-INF/tags/layout" %>
<%@ taglib prefix="c"  		uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" 	uri="http://java.sun.com/jsp/jstl/fmt" %>

<layout:main>

	<jsp:attribute name="javascript">
	
		<script type="text/javascript">			
			var page   = "${param.page}";
			var searchColumn = "${param.searchColumn}"; 
			var searchStartDate = "${param.searchStartDate}";
			var searchEndDate = "${param.searchEndDate}";
			$(document).ready(function() {
				$("#searchColumn").val(searchColumn);
				
				$("#btnSearch").click(function() {
					
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
					
					var form = document.forms["searchForm"];
					form.action="/monitor/signalResultList"
					form.submit();
				});		
				
				$("#btnDownload").click(function() {
					
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
					
					var form = document.forms["searchForm"];
					form.action="/monitor/signalResultList.download"
					form.submit();
				});	
				
				$("#searchString").keypress(function(e){
					if (e.keyCode == 13){
						if( $("#searchString").val() == "" ){
							alert("검색어를 입력해 주세요.");
							$("#searchString").focus();
							
							return;
						}

						var form = document.forms["searchForm"];
						form.submit();
					}    
				});
				
				$("#searchWeek").change(function() {
					passingDate();
				});	
				
				//--------------------------- 날짜관련 스크립트 ---------------------------
				//현재시간
				var nowDate = new Date();
				// default 끝 날짜 (7일뒤 날짜)
				nowDate.setDate(nowDate.getDate() - 7);
				
				// default 시작 날짜 (오늘 날짜)
				var sMonth= nowDate.getMonth()+1; 
				sMonth = sMonth < 10 ? '0' + sMonth : sMonth;
				var sDay= nowDate.getDate(); 
				sDay = sDay < 10 ? '0' + sDay : sDay;
				var sDate= nowDate.getFullYear() + '-' + sMonth + '-' + sDay; 
				
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
			});
			
			//팝업 부분 
			function openPopup( row ){
				var width = "750";
				var height = "350";
				var leftPos = (screen.availWidth - width)/2;
				var topPos = (screen.availHeight - height)/2;
				var position = "width = "+ width + ", height = " + height 
								+ ",left= " + leftPos + ", top= " + topPos;
				
				//window.open("winOpen", position, "menubar=1");
				var url = "/monitor/signalResultDetail";
				winOpen = window.open("", "winOpen", position, "menubar=1");
				winOpen.document.write( $("#"+row).text() );
				
				/* var frm = document.popForm;
				frm.action = "/inquiry/pnsHistoryDetail"
				frm.target = "winOpen";
				frm.method = "post";
				frm.cpCode.value = cpCode;
				frm.customerId.value = customerId;
				frm.phoneNum.value = phoneNum;
				frm.submit(); */
			}
			
			//로그 상세 
			function openLog( row ){

				$("#"+row).slideDown();
			}	
			
			function passingDate() {
				var week = jQuery("select[name=searchWeek]").val();
				
				//현재시간
				var nowDate = new Date();
				// default 끝 날짜 (n일뒤 날짜)
				nowDate.setDate(nowDate.getDate() - week*1);
				
				// default 시작 날짜 (오늘 날짜)
				var sMonth= nowDate.getMonth()+1; 
				sMonth = sMonth < 10 ? '0' + sMonth : sMonth;
				var sDay= nowDate.getDate(); 
				sDay = sDay < 10 ? '0' + sDay : sDay;
				var sDate= nowDate.getFullYear() + '-' + sMonth + '-' + sDay; 
				
				nowDate.setDate(nowDate.getDate() + week*1);
				var eMonth= nowDate.getMonth()+1; 
				eMonth = eMonth < 10 ? '0' + eMonth : eMonth;
				var eDay= nowDate.getDate(); 
				eDay = eDay < 10 ? '0' + eDay : eDay;
				var eDate= nowDate.getFullYear() + '-' + eMonth + '-' + eDay;
				
				$("#searchStartDate").val(sDate);
				$("#searchEndDate").val(eDate);
			}

		</script>	
		
	</jsp:attribute>

	<jsp:body>
	
		<div class="boardTitle">
			<div class="borderTitle">
				<span class="pageTitle">올레 홈캠 카메라 로그 결과조회</span>
			</div>
		</div>
		<div class="boardTop">	
			<table>
			<form name="searchForm" method="get" action="/monitor/signalResultList">
				<colgroup>
					<col style="width:60px" />
					<col style="width:110px" />
					<col style="width:310px" />
					<col style="width:*%" />
				</colgroup>
				
				<tr>
					<td style="font-weight: bold; text-align: center;">검 색</td>
					<td style="font-weight: bold; text-align: center;">
						<select id="searchColumn" name="searchColumn" style="width:100px" title="검색조건을 선택하세요">
							<option value="mb.mbr_id">고객 ID</option>
							<option value="stcb.tel_no">휴대폰 번호</option>
							<option value="sdb.spot_dev_id">카메라 ID</option>
							<option value="sdeb.dev_cctc_mac">카메라 mac</option>
						</select>
					</td>
					<td style="text-align: center;">
						<input type="text" id="searchString" name="searchString" style="width:296px" title="검색어를 입력하세요" value="${param.searchString }" />
					</td>
					<td rowspan="3" style="padding-left:5px;">
						<button type="button" id="btnSearch" class="btn" style="height:55px; width:80px;"><span><b>검 색</b></span></button>
						<c:if test="${!empty signalResultList}">
						<button type="button" id="btnDownload" class="btn" style="height:55px; width:80px;"><span><b>검색결과<br>다운로드</b></span></button>
						</c:if>	
					</td>
				</tr>
				<tr style="height:5px;"><td></td></tr>
				<tr>
					<td style="font-weight: bold; text-align: center;">
						기 간
					</td>
					<td style="font-weight: bold; text-align: center;">
						<select id="searchWeek" name="searchWeek" style="width:100px">
							<option value="7" <c:if test="${'7' == param.searchWeek}">selected</c:if>>최근 1주일</option>
							<option value="14" <c:if test="${'14' == param.searchWeek}">selected</c:if>>최근 2주일</option>
							<option value="21" <c:if test="${'21' == param.searchWeek}">selected</c:if>>최근 3주일</option>
							<option value="28" <c:if test="${'28' == param.searchWeek}">selected</c:if>>최근 4주일</option>
						</select>
					</td>
					<td style="text-align: center;">
						<input type="text" id="searchStartDate" name="searchStartDate" value="${param.searchStartDate }" readonly="readonly" style="width:70px" />
						<select id="searchStartHour" name="searchStartHour" style="width:60px">
						<c:if test="${!empty hourList}">
							<c:forEach var="hour" items="${hourList}" varStatus="status" >
								<option <c:if test="${hour.hour == param.searchStartHour}">selected</c:if> value="${hour.hour}">${hour.hour} 시</option>
							</c:forEach>
						</c:if>
						</select>
						<b>~</b> 
						<input type="text" id="searchEndDate" name="searchEndDate" value="${param.searchEndDate }" readonly="readonly" style="width:70px" />
						<select id="searchEndHour" name="searchEndHour" style="width:60px">
						<c:if test="${!empty hourList && param.searchEndHour != null}">
							<c:forEach var="hour" items="${hourList}" varStatus="status" >
								<option <c:if test="${hour.hour == param.searchEndHour}">selected</c:if> value="${hour.hour}">${hour.hour} 시</option>
							</c:forEach>
						</c:if>
						<c:if test="${!empty hourList && param.searchEndHour == null}">
							<c:forEach var="hour" items="${hourList}" varStatus="status" >
								<option <c:if test="${hour.hour == 23}">selected</c:if> value="${hour.hour}">${hour.hour} 시</option>
							</c:forEach>
						</c:if>
						</select>
					</td>
				</tr>
			</form>
			</table>
			
		</div>
		
		<table class="board mt10">
			<caption>모니터링 조회 결과의 제목, 게시시간, 작성자, 노출여부를 확인 할수 있는 표</caption>
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
					<th scope="col">No.</th>
					<th scope="col">olleh ID</th>
					<th scope="col">휴대폰 번호</th>
					<th scope="col">카메라 ID</th>
					<th scope="col">카메라 닉네임</th>
					<th scope="col">카메라 mac</th>
					<th scope="col">로그유형</th>
					<th scope="col">발생일시</th>
					<th scope="col" style="display:none">상세보기</th>
				</tr>
			</thead>
			
			<tbody id="noticeListContainer">
			
			<!-- DYNAMIC AREA 'list' -->
			<c:if test="${!empty signalResultList}">
				<c:forEach var="signalResult" items="${signalResultList}" varStatus="status" >
				<tr data-pk="${notice.pk}" onmouseover="this.style.backgroundColor='#f7f7f7'" onmouseout="this.style.backgroundColor=''" bgcolor="white">
					<td><fmt:formatNumber value="${status.index +1}" pattern="#,###"/></td>
					<td>${signalResult.mbrId}</td>
					<td>${signalResult.telNo}</td>					
					<td><b style="cursor:pointer" onclick="openLog('unitLog_${status.index +1}');">${signalResult.spotDevId}</b></td>
					<td>${signalResult.devNm}</td>
					<td>${signalResult.devCctvMac}</td>
					<td>${signalResult.logType}</td>
					<td>${signalResult.eventDt}</td>
					<td style="display:none"><a onclick="javascript:openPopup();" class="btn2 btn2_red30" style="cursor:pointer"><span>확인</span></a></td>
				</tr>
				<tr onmouseover="this.style.backgroundColor='#f7f7f7'" onmouseout="this.style.backgroundColor=''" bgcolor="white">
					<td colspan="11" style="display:none; text-align:left" id="unitLog_${status.index +1}">${signalResult.attrs}</td>
				</tr>
				</c:forEach>
			</c:if>			
	
			<c:if test="${empty signalResultList}">
				<tr> 	
					<td align="center" colspan="11">검색된 데이타가 없습니다</td>
				</tr>
			</c:if>
			</tbody>
		</table>
		
		
		
	</jsp:body>
</layout:main>
