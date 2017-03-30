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
			var searchSnsnTag = "${param.searchSnsnTag}";
			var searchContlVal = "${param.searchContlVal}";
			
		
			$(document).ready(function(){
				
				//현재시간
				var nowDate = new Date();
				// default 시작 날짜 (오늘 날짜)
				nowDate.setDate(nowDate.getDate() - 7);
				var sMonth= nowDate.getMonth()+1; 
				sMonth = sMonth < 10 ? '0' + sMonth : sMonth;
				var sDay= nowDate.getDate(); 
				sDay = sDay < 10 ? '0' + sDay : sDay;
				var sDate= nowDate.getFullYear() + '-' + sMonth + '-' + sDay; 
				
				// default 끝 날짜 (7일뒤 날짜)
				nowDate.setDate(nowDate.getDate() + 7);
				var eMonth= nowDate.getMonth()+1; 
				eMonth = eMonth < 10 ? '0' + eMonth : eMonth;
				var eDay= nowDate.getDate(); 
				eDay = eDay < 10 ? '0' + eDay : eDay;
				var eDate= nowDate.getFullYear() + '-' + eMonth + '-' + eDay;
				
				menuAction(searchDateType);
				
				$("#cpCode").val(cpCode);
				$("#searchColumn").val(searchColumn);
				$("#searchSnsnTag").val(searchSnsnTag);
				$("#searchContlVal").val(searchContlVal);
				
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
			            }
			        }
			    });
				
				$("#searchStartDate").val(searchStartDate=='' ? sDate : searchStartDate);
				$("#searchEndDate").val(searchEndDate=='' ? eDate : searchEndDate);
				
				$("#cpCode").change(function(){
					var form = document.forms["searchForm"];
					form.event.value = "";
					form.submit();					
				});
				
				$("#btnExcel").click(function(){
					var excelCount = '${excelCount}';
					var form = document.forms["searchForm"];
					form.event.value = "excel";
					
					if(confirm("엑셀파일은 최대 " + excelCount + "건까지 다운로드 하실수 있습니다.\n다운로드 하시겠습니까?")){
						form.submit();	
					}
				});
				
				$("#btnSearch").click(function(){
					var form = document.forms["searchForm"];
					form.event.value = "";
					form.submit();					
				});
				
				$("#rangeDate").click(function(){
					menuAction('range');
				});
				
				$("#searchString").keypress(function(e){
					if (e.keyCode == 13){
						var form = document.forms["searchForm"];
						form.event.value = "";
						form.submit();
				    }    
				});
				
				$("#monthDate").click(function(){
					menuAction('month');
				});
				
				var start = 0, end = 6;
				var dateStr = '', nowMonth = '', dateFormatStr = '';
				var nowDate = new Date().format('yyyyMMdd');

	
				if(Number(nowDate.substring(6, 8) < 10)){
					nowMonth = dateAdd('mm', 0, nowDate, '');
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
				
				$("#searchSnsnTag").change(function(){
                	var searchSnsnTag = jQuery("select[name=searchSnsnTag]").val();
					var selectColumn = "searchContlVal";
					
					$("#searchContlVal").empty().data('options');
					$("#searchContlVal").append("<option value=''>전체</option>");
					
					if(searchSnsnTag == '') return false;
					
					$.ajax({
						type : "POST",
					    cache: false,						
						url : "/inquiry/getSnsnTagContlValList",
						dataType : "json",
						data : { 
							"searchSnsnTag" : searchSnsnTag
						}
					})
					.done(function(result){
						console.log("result.code" + result.code);
						if(result.code != 200) {
							alert("서버에서 오류가 발생하였습니다.\n다시 시도하여 주십시오.");
						} else {
							var cnt = result.list.length;
							console.log("cnt" + cnt);
							for(var i=0; i<cnt; i++){
								var code	= result.list[i].code;
       							var name	= result.list[i].name;
       							console.log("code" + code + "/ name" + name);
       							$("#searchContlVal").append("<option value='"+code+"'>"+name+"</option>");
       						}
						}
					})
					.fail(function( jqXHR, textStatus, errorThrown){
						console.log(jqXHR);
					});	
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
			
		</script>	
		

	</jsp:attribute>
	
	<jsp:body>
		<form name="searchForm" method="get" action="/inquiry/deviceControlRecord">
		<input type="hidden" id="event" name="event" />
		<div class="boardTitle">
			<div class="borderTitle">
				<span class="pageTitle">단말 제어이력 조회</span>
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
							<option value="customerId">고객ID</option>
							<option value="spotDevId">단말ID</option>
							<option value="phoneNum">휴대폰번호</option>
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
						<button type="button" id="btnSearch" class="btn">검색</button>&nbsp; <button type="button" id="btnExcel" class="btn">엑셀 다운로드</button>
					</div>
					
					<div style="padding:5px; padding-left:25px;">
						<b>내 용&nbsp;</b>
						<select id="searchSnsnTag" name="searchSnsnTag" style="width:200px;" title="내용을 선택하세요">
							<option value="">전체</option>
							<c:if test="${!empty snsnTagList}">
								<c:forEach var="item" items="${snsnTagList}" varStatus="status" >
									<option  value="${item.snsn_tag_cd}">${item.snsn_tag_nm}</option>
								</c:forEach>
							</c:if>
						</select>
						
						<select id="searchContlVal" name="searchContlVal" style="width:110px;" title="내용을 선택하세요">
							<option value="">전체</option>
							<c:forEach var="item" items="${ContlValList}" varStatus="status" >
								<option  value="${item.code}">${item.name}</option>
							</c:forEach>
						</select>
					</div>
				</div>

			</div>
		</div>		
		</form>
		
		<table class="board mt10">
			<caption>약관동의 조회 결과의 고객ID, 서비스이용약관, 버전정보, 동의여부, 동의일자를 확인 할수 있는 표</caption>
			<colgroup>
				<col style="width:5%" />
				<col style="width:10%" />
				<col style="width:10%" />
				<col style="width:10%" />
				<col style="width:15%" />
				<col style="width:10%" />
				<col style="width:*%" />
				<col style="width:10%" />
				<col style="width:10%" />
			</colgroup>
			<thead>
				<tr>
					<th scope="col">번호</th>
					<th scope="col">고객ID</th>
					<th scope="col">단말ID</th>
					<th scope="col">단말닉네임</th>
					<th scope="col">휴대폰번호</th>
					<th scope="col">상품명</th>
					<th scope="col">내용</th>
					<th scope="col">적용일</th>
					<th scope="col">적용시간</th>
				</tr>
			</thead>
			
			<tbody>
			<!-- DYNAMIC AREA 'list' -->
			<c:if test="${!empty controlRecordList}">
				<c:forEach var="controlRecord" items="${controlRecordList}" varStatus="status" >
				<tr onmouseover="this.style.backgroundColor='#f7f7f7'" onmouseout="this.style.backgroundColor=''" bgcolor="white">
					<td><fmt:formatNumber value="${pageNavi.totalCount - ((pageNavi.nowPage - 1) * pageNavi.pageSize + status.index) }" pattern="#,###"/></td>
					<td>${controlRecord.customerId}</td>
					<td>${controlRecord.spotDevId}</td>
					<td>${controlRecord.spotDevNm}</td>
					<td>${controlRecord.phoneNum}</td>
					<td>${controlRecord.unitSvcNm}</td>
					<td>${controlRecord.snsnTagNm}<br/>${controlRecord.contlInfo}</td>
					<td>${controlRecord.regDate}</td>
					<td>
						<fmt:parseDate value="${controlRecord.regTime}" var="dateFmt" pattern="HH:mm:ss"/>
						<fmt:formatDate value="${dateFmt}" pattern="a hh:mm"/> 
					</td>
				</tr>
				</c:forEach>
			</c:if>			
	
			<c:if test="${empty controlRecordList}">
				<tr> 	
					<td align="center" colspan="9">검색된 데이타가 없습니다</td>
				</tr>
			</c:if>
			<!-- DYNAMIC AREA 'list' -->
			</tbody>
		</table>
		
		${pageNavi.navi}
		
	</jsp:body>
</layout:main>
