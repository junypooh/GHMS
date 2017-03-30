<%@ page contentType="text/html" trimDirectiveWhitespaces="true" pageEncoding="UTF-8"%>
<%@ taglib prefix="layout" 	tagdir="/WEB-INF/tags/layout" %>
<%@ taglib prefix="c"  		uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" 	uri="http://java.sun.com/jsp/jstl/fmt" %>

<layout:main>

	<jsp:attribute name="javascript">
	
		<script type="text/javascript">			
			var page   = "${param.page}";
			var searchColumn = "${param.searchColumn}"; 
	 	
			$(document).ready(function() {
				$("#searchColumn").val(searchColumn);
				
				$("#btnSearch").click(function() {
					var form = document.forms["searchForm"];
					form.submit();
				});					
				
				$("#logOnOffListContainer tr td[data-on-off]").each(function(index) {
					
					var pk = $(this).parent().attr("data-pk");
					var onoff = $(this).attr("data-on-off");
					var item = $(this).find("select");
					
 					item.val(onoff).change(function(){
 						if(confirm("On/Off를 변경 하시겠습니까?") == true) {

 							var svcTgtSeq = $(this).parent().parent().attr("data-svcTgtSeq");
 							var spotDevSeq = $(this).parent().parent().attr("data-spotDevSeq");
 							var devUUID = $(this).parent().parent().attr("data-devUUID"); 							
 							
 							updateLogOnOff(svcTgtSeq, spotDevSeq, devUUID, $(this).val());
 						} else {
 							var status = $(this).val() == "1" ? "0" : "1";
 							$(this).val(status);
 						}
 						
 						$(this).blur();
					});
				});				
			});
			
			function updateLogOnOff(svcTgtSeq, spotDevSeq, devUUID, logMode) {
				var logOnOffRequest = {};
				
				logOnOffRequest.svcTgtSeq = svcTgtSeq;
				logOnOffRequest.spotDevSeq = spotDevSeq;
				logOnOffRequest.devUUID = devUUID;
				logOnOffRequest.logMode = logMode;
				
 				$.ajax({
					type : "POST",
				    cache: false,						
					accept: "application/json",
					url : "/monitor/logOnOffRequest",
					contentType: "application/json; charset=UTF-8", 
					dataType : "json",
					data : JSON.stringify(logOnOffRequest)
				})	
				.done(function(result){
					if(result.code == 200) {
						alert("정상적으로 업데이트 처리 되었습니다.");
					} else {
						alert("업데이트 과정중 오류가 발생하였습니다.\n다시 시도하여 주십시오.");
					}
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
					<span class="pageTitle">카메라 로그전송 On/Off</span>
				</div>
			</div>
		<div class="boardTop">	
			<div class="flr">
				<form name="searchForm" method="get" action="/monitor/logOnOffList">
				<select id="searchColumn" name="searchColumn" title="검색조건을 선택하세요">
					<option value="mbr_id">고객ID</option>
					<option value="tel_no">휴대폰</option>
					<option value="spot_dev_id">홈카메라ID</option>
				</select>
				<input type="text" id="searchString" name="searchString"  title="검색어를 입력하세요" value="${param.searchString }" />
				<button type="button" id="btnSearch" class="btn"><span>검색</span></button>
				</form>
			</div>
		</div>
		
		<table class="board mt10">
			<caption>모니터링 조회 결과의 제목, 게시시간, 작성자, 노출여부를 확인 할수 있는 표</caption>
			<colgroup>
				<col style="width:5%" />
				<col style="width:5%" />
				<col style="width:20%" />
				<col style="width:*" />
				<col style="width:20%" />
				<col style="width:20%" />
				<col style="width:20%" />
			</colgroup>
			<thead>
				<tr>
					<th scope="col">NO</th>
					<th scope="col">ollehID</th>
					<th scope="col">휴대폰번호</th>
					<th scope="col">홈카메라ID</th>
					<th scope="col">카메라 닉네임</th>
					<th scope="col">On/Off 여부</th>
				</tr>
			</thead>
			
			<tbody id="logOnOffListContainer">
			
			<!-- DYNAMIC AREA 'list' -->
			<c:if test="${!empty logOnOffList}">
				<c:forEach var="logOnOff" items="${logOnOffList}" varStatus="status" >
				<tr data-svcTgtSeq="${logOnOff.svcTgtSeq}" data-spotDevSeq="${logOnOff.spotDevSeq}" data-devUUID="${logOnOff.devUUID}" onmouseover="this.style.backgroundColor='#f7f7f7'" onmouseout="this.style.backgroundColor=''" bgcolor="white">
					<td><fmt:formatNumber value="${pageNavi.totalCount - ((pageNavi.nowPage - 1) * pageNavi.pageSize + status.index) }" pattern="#,###"/></td>
					<td>${logOnOff.mbrId}</td>					
					<td>&nbsp;${logOnOff.telNo}</td>
					<td style="text-align:center;">${logOnOff.spotDevId}</td>
					<td style="text-align:center;">${logOnOff.devNm}</td>
					<td data-on-off="${logOnOff.logMode}" style="text-align:center;">
						<select name="onOff">
							<option value="1">On</option>
							<option value="0">Off</option>
						</select>
					</td>
				</tr>
				</c:forEach>
			</c:if>			
	
			<c:if test="${empty logOnOffList}">
				<tr> 	
					<td align="center" colspan="11">검색된 데이타가 없습니다</td>
				</tr>
			</c:if>
			</tbody>
		</table>
		
		${pageNavi.navi}
		
	</jsp:body>
</layout:main>