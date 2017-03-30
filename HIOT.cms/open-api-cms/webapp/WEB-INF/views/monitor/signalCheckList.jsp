<%@ page contentType="text/html" trimDirectiveWhitespaces="true" pageEncoding="UTF-8"%>
<%@ taglib prefix="layout" 	tagdir="/WEB-INF/tags/layout" %>
<%@ taglib prefix="c"  		uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" 	uri="http://java.sun.com/jsp/jstl/fmt" %>

<layout:main>

	<jsp:attribute name="javascript">
	
		<script type="text/javascript">			
			var searchColumn = "${param.searchColumn}";		

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
					form.submit();
				});				
				
				$("#btnToggle").click(function(){
					var checked = $(this).prop("checked");
					$("#signalCheckListContainer input[type=checkbox]").each(function(index){
						$(this).prop("checked", checked);
					});
				});	
				
				$("#btnSignalCheck").click(function(){
					if($("#signalCheckListContainer input[type=checkbox]:checked").size() == 0){
						alert("카메라 로그 확인 요청할 대상을\n하나 이상 선택 하셔야합니다.");
						return false;
					}
					
					if($("input[name=repeat]:checked").size() == 0) {
						alert("카메라 로그 확인 방법을 선택하십시요.");
						return false;
					}
					
					if( $("#collectCount").val() >144 || $("#collectCount").val()<1 ) {
						alert("연속횟수 범위는 1~144 입니다.");
						return false;
					}
					signalCheckRequest();
				});
			});
			
			function signalCheckRequest() {
				var devUUID = "";
				var svcTgtSeq = 0, spotDevSeq = 0;
				var signalCheckList = {items : []};
				
				var collectPeriod = 1;
				var collectCount = 1;
				
				if($("input[name=repeat]:checked").val() == 0) {
					collectPeriod = $("#collectPeriod").val();
					collectCount = $("#collectCount").val();
				}
				
				signalCheckList.collectPeriod = collectPeriod;
				signalCheckList.collectCount = collectCount;
								
				$("#signalCheckListContainer input[type=checkbox]:checked").each(function(){
					svcTgtSeq = $(this).parent().parent().attr("data-svcTgtSeq");
					spotDevSeq = $(this).parent().parent().attr("data-spotDevSeq");
					devUUID = $(this).parent().parent().attr("data-devUUID");
					signalCheckList.items.push({"svcTgtSeq" : svcTgtSeq, "spotDevSeq" : spotDevSeq, "devUUID" : devUUID});
				});
				
 				$.ajax({
					type : "POST",
				    cache: false,						
					accept: "application/json",
					url : "/monitor/signalCheckRequest",
					contentType: "application/json; charset=UTF-8", 
					dataType : "json",
					data : JSON.stringify(signalCheckList)
				})	
				.done(function(result){
					
					if(result.code == "200"){
						alert("카메라 로그 확인 요청되었습니다.\n잠시후 '카메라 로그 조회' 메뉴에서 확인 가능합니다.");
					}else if(result.code == "201"){
						
						msg = "총 " + result.totalCnt + " 건 중 " + result.successCnt + " 건 성공, " + result.failCnt + " 건 실패하였습니다.\n\n";
						for(i=0; i < result.successArr.length; i++){
							
							devUUID		= result.successArr[i];
							chkNum 		= $("#signalCheckListContainer > tr[data-devUUID=" + devUUID + "] > td:eq(1)").text();
							nickName 	= $("#signalCheckListContainer > tr[data-devUUID=" + devUUID + "] > td:eq(5)").text();
							
							msg += "성공 : No." + chkNum + " 항목의 " + nickName + "\n";
						}
						
						msg += "\n";
						
						for(i=0; i< result.failArr.length; i++){

							devUUID		= result.failArr[i];
							chkNum 		= $("#signalCheckListContainer > tr[data-devUUID=" + devUUID + "] > td:eq(1)").text();
							nickName 	= $("#signalCheckListContainer > tr[data-devUUID=" + devUUID + "] > td:eq(5)").text();
							
							msg += "실패 : No." + chkNum + " 항목의 " + nickName + "\n";
						}

						alert(msg);
						
					}else{
						alert("카메라 로그 요청이 실패하였습니다.\n잠시후 다시 이용해주세요..");
					}
				})
				.fail(function( jqXHR, textStatus, errorThrown){
					console.log(jqXHR);
				});				
			}
			
			function repeatOnOff( val ){
				if(val == 1){
					$("#collectPeriod").attr("disabled", true);
					$("#collectCount").attr("disabled", true);
				}else{
					$("#collectPeriod").attr("disabled", false);
					$("#collectCount").attr("disabled", false);
				}
				
			}
			
		</script>	
		
	</jsp:attribute>

	<jsp:body>
		
		<div class="boardTitle">
			<div class="borderTitle">
				<span class="pageTitle">올레 홈캠 카메라 로그 확인 요청</span>
			</div>
		</div>
		<div class="boardTop">	
			<div class="flr">
				<form name="searchForm" method="get" action="/monitor/signalCheckList">
				<select id="searchColumn" name="searchColumn" title="검색조건을 선택하세요">
					<option value="mb.mbr_id">고객 ID</option>
					<option value="stcb.tel_no">휴대폰 번호</option>
					<option value="sdb.spot_dev_id">카메라 ID</option>
					<option value="sdeb.dev_cctc_mac">카메라 mac</option>
				</select>
				<input type="text" id="searchString" name="searchString"  title="검색어를 입력하세요" value="${param.searchString }" />
				<button type="button" id="btnSearch" class="btn"><span>검색</span></button>
				</form>
			</div>
		</div>
		
		<table class="board mt10">
			<caption>올레 홈캠 카메라 신호상태 조회의 id, 퓨대폰번호, 홈카메라id, 닉네임을 확인 할수 있는 표</caption>
			<colgroup>
				<col style="width:5%" />
				<col style="width:5%" />
				<col style="width:13%" />
				<col style="width:*%" />
				<col style="width:14%" />
				<col style="width:14%" />
				<col style="width:14%" />
				<col style="width:14%" />
			</colgroup>
			<thead>
				<tr>
					<th scope="col"><input type="checkbox" id="btnToggle" /></th>
					<th scope="col">No.</th>
					<th scope="col">olleh ID</th>
					<th scope="col">휴대폰 번호</th>
					<th scope="col">카메라 ID</th>
					<th scope="col">카메라 닉네임</th>
					<th scope="col">카메라 mac</th>
					<th scope="col">카메라 접속상태</th>
				</tr>
			</thead>
			
			<tbody id="signalCheckListContainer">
			
			<!-- DYNAMIC AREA 'list' -->
			<c:if test="${!empty signalCheckList}">
				<c:forEach var="signalCheck" items="${signalCheckList}" varStatus="status" >
				<tr data-svcTgtSeq="${signalCheck.svcTgtSeq}" data-spotDevSeq="${signalCheck.spotDevSeq}" data-devUUID="${signalCheck.devUUID}" onmouseover="this.style.backgroundColor='#f7f7f7'" onmouseout="this.style.backgroundColor=''" bgcolor="white">
					<td><input type="checkbox" name="" value=""></td>
					<td style="text-align:center;"><fmt:formatNumber value="${signalCheckCount - status.index }" pattern="#,###"/></td>
					<td style="text-align:center;">${signalCheck.mbrId}</td>					
					<td>&nbsp;${signalCheck.telNo}</td>
					<td style="text-align:center;">${signalCheck.spotDevId}</td>
					<td style="text-align:center;">${signalCheck.devNm}</td>
					<td style="text-align:center;">${signalCheck.devCctvMac}</td>
					<td style="text-align:center;">${signalCheck.devConStat}</td>
				</tr>
				</c:forEach>
			</c:if>			
	
			<c:if test="${empty signalCheckList}">
				<tr> 	
					<td align="center" colspan="8">검색된 데이타가 없습니다</td>
				</tr>
			</c:if>
			</tbody>
		</table>
		<table style="border-width:1px;border-style:solid;width:100%;height:40px">
			<tr>
				<td style="text-align:center">반복설정</td>
				<td>
					<label><input type="radio" name="repeat" value="1" checked onclick="repeatOnOff(this.value)"/> 1회</label>
					&nbsp;
					<label><input type="radio" name="repeat" value="0"  onclick="repeatOnOff(this.value)"/> 반복</label>
				</td>
				<td style="text-align:left">
					간격
					<select id="collectPeriod" disabled>
						<option value="60">1분</option>
						<option value="120">2분</option>
						<option value="180">3분</option>
						<option value="240">4분</option>
						<option value="300">5분</option>
						<option value="360">6분</option>
						<option value="420">7분</option>
						<option value="480">8분</option>
						<option value="540">9분</option>
						<option value="600">10분</option>
					</select>
				</td>
				<td style="text-align:left">							
					&nbsp;
					연속 횟수 
					<input type="text" id="collectCount" name="collectCount" style="width:40px" value="1"  onKeyUp="onlyNumber(this);" disabled/> 회
				</td>
				<td style="text-align:center;">
					<input type="button" id="btnSignalCheck" value="로그전송 요청" class="btn" style="cursor:pointer">
				</td>
			</tr>
		</table>		
		
	</jsp:body>
	
</layout:main>