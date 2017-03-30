<%@ page contentType="text/html" trimDirectiveWhitespaces="true" pageEncoding="UTF-8"%>
<%@ taglib prefix="layout" 	tagdir="/WEB-INF/tags/layout" %>
<%@ taglib prefix="c"  		uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" 	uri="http://java.sun.com/jsp/jstl/fmt" %>

<layout:main>

	<jsp:attribute name="javascript">
	
		<script type="text/javascript">
		
			var cpCode	 = "${param.cpCode}";
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
				
				$("#cpCode").val(cpCode);
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
				
				$("#cpCode").change(function(){
					var form = document.forms["searchForm"];
					form.submit();					
				});
				
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
			
			function openPopup(unitSvcCd, etsMbrSeq, mbrSeq){
				
				var width = "700";
				var height = "600";
				var leftPos = (screen.availWidth - width)/2;
				var topPos = (screen.availHeight - height)/2;
				var position = "width="+ width + ", height=" + height + ", left=" + leftPos + ", top=" + topPos;
				var url = "/inquiry/familyAuthDetail?"+"cpCode=" + unitSvcCd 
													+ "&etsMbrSeq=" + etsMbrSeq 
													+ "&mbrSeq=" + mbrSeq;
				window.open(url, "winOpen", position, "menubar=1");
			}
			
		</script>	
		
	</jsp:attribute>

	<jsp:body>
	
		<form name="searchForm" method="get" action="/inquiry/familyAuthList">
		<div class="boardTitle">
			<div class="borderTitle">
				<span class="pageTitle">가족사용자 권한 정보 조회</span>
				<select id="cpCode" name="cpCode" title="검색조건을 선택하세요" style="float:right;">
					<option value="">전체</option>
					<c:forEach var="svc" items="${loginInfo.svcList }">
					<option value="${svc.cpCode }">${svc.cpName }</option>
					</c:forEach>
				</select>
			</div>
		</div>
		
		<div class="boardTop">
			<div class="">
				<div style="float:left;">
					<div style="padding:5px; padding-left:25px;">
						<b>검 색&nbsp;</b>
						<select id="searchColumn" name="searchColumn" style="width:110px;" title="검색조건을 선택하세요">
							<option value="etsMbrId">가족사용자ID</option> 
							<option value="etsTelNo">가족사용자 휴대폰번호</option>
							<option value="mbrId">마스터ID</option> 
							<option value="telNo">마스터 휴대폰번호</option>
						</select>
						<input type="text" id="searchString" name="searchString"  title="검색어를 입력하세요" value="${param.searchString }" style="width:115px;" />
					</div>
					<div >
						<input type="radio" id="rangeDate" name="searchDateType" value="range" checked="checked"/>
						<b>기간별&nbsp;</b>
						<input type="text" id="searchStartDate" name="searchStartDate" value="${param.searchStartDate }" readonly="readonly" style="width:100px" />
						<b>~</b> 
						<input type="text" id="searchEndDate" name="searchEndDate" value="${param.searchEndDate }" readonly="readonly" style="width:100px" />
						&nbsp;
						<input type="radio" id="monthDate" name="searchDateType" value="month">
						<b>월간별&nbsp;</b>
						<select id="searchMonth" name="searchMonth" style="width:110px;" disabled="disabled"></select>
						&nbsp;
						<button type="button" id="btnSearch" class="btn"><span>검색</span></button>
					</div>
				</div>
			</div>
		</div>		
		
		</form>
		
		<table class="board mt10">
			<caption>가족사용자 권한 정보 조회 가족사용자ID, 가족사용자 휴대폰번호, 마스터ID, 마스터 휴대폰번호, 승인일시를 확인 할수 있는 표</caption>
			<colgroup>
				<col style="width:5%" />
				<col style="width:15%" />
				<col style="width:15%" />
				<col style="width:15%" />
				<col style="width:15%" />
				<col style="width:15%" />
				<col style="width:20%" />
			</colgroup>
			<thead>
				<tr>
					<th scope="col">NO</th>
					<th scope="col">상품명</th>
					<th scope="col">가족사용자ID</th>
					<th scope="col">가족사용자<br/>휴대폰번호</th>
					<th scope="col">마스터ID</th>
					<th scope="col">마스터<br/>휴대폰번호</th>
					<th scope="col">승인일시</th>
				</tr>
			</thead>
			
			<tbody>
			<!-- DYNAMIC AREA 'list' -->
			<c:if test="${!empty familyAuthList}">
				<c:forEach var="familyAuthList" items="${familyAuthList}" varStatus="status" >
				<tr onmouseover="this.style.backgroundColor='#f7f7f7'" onmouseout="this.style.backgroundColor=''" bgcolor="white">
					<td><fmt:formatNumber value="${pageNavi.totalCount - ((pageNavi.nowPage - 1) * pageNavi.pageSize + status.index) }" pattern="#,###"/></td>
					<td>${familyAuthList.unitSvcNm}</td>
					<td><a href="#" onclick="openPopup('${familyAuthList.unitSvcCd}', '${familyAuthList.etsMbrSeq}', '${familyAuthList.mbrSeq}');" onfocus="this.blur();">${familyAuthList.etsMbrId}</a></td>
					<td>${familyAuthList.etsTelNo}</td>
					<td>${familyAuthList.mbrId}</td>
					<td>${familyAuthList.telNo}</td>
					<td>${familyAuthList.cretDt}</td>
				</tr>
				</c:forEach>
			</c:if>			
	
			<c:if test="${empty familyAuthList}">
				<tr> 	
					<td align="center" colspan="7">검색된 데이타가 없습니다</a></td>
				</tr>
			</c:if>
			<!-- DYNAMIC AREA 'list' -->
			</tbody>
		</table>
		
		${pageNavi.navi}
		
	</jsp:body>
</layout:main>