<%@ page contentType="text/html" trimDirectiveWhitespaces="true" pageEncoding="UTF-8"%>
<%@ taglib prefix="layout" 	tagdir="/WEB-INF/tags/layout" %>
<%@ taglib prefix="c"  		uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" 	uri="http://java.sun.com/jsp/jstl/fmt" %>

<layout:main>

	<jsp:attribute name="javascript">
	
		<script type="text/javascript">
			${script}
			var searchColumn = "${param.searchColumn}";
			var searchString = "${param.searchString}";
			$(document).ready(function() {
				
				$("#searchColumn").val(searchColumn);
				$("#searchString").val(searchString);
				
				$("#btnSearchCam").click(function(){
					
					if( $("#searchDevString").val() == "" ){
						alert("검색어를 입력해 주세요.");
						$("#searchDevString").focus();
						return;
					}
					
					//맥어드레스 검색일 경우 자동으로 포멧 변환
					if( $("#searchDevColumn").val() == "sdeb.dev_cctc_mac"){
						str = $("#searchDevString").val();
						$("#searchDevString").val( str.replace( /:/gi ,"" ).replace( /(\w)(?=(?:\w{2})+(?!\w))/gi, '$1:') );
					}
					
					var frm = document.searchForm;
					frm.method.value = "";
					frm.submit();
				});	
				
				$("#btnInsertCam").click(function(){
					
					//==================================================================
					var svcTgtSeqList = "";
					var spotDevSeqList = "";
					var svcTgtSeq = "";
					var spotDevSeq = "";
					var skipProcess = true;
					var msg = "";
					$("#searchCamListContainer tr input[type=checkbox]").each(function(index) {
						if($(this).prop("checked")) {
							
							svcTgtSeq = $(this).parent().parent().attr("data-svcTgtSeq");
							
							$("#targetCamListContainer tr").each(function(index) {
								//console.log($(this).attr("data-svcTgtSeq"));
								if(svcTgtSeq == $(this).attr("data-svcTgtSeq")){
									skipProcess = false;
									msg +=  "(mac = " + $(this).attr("data-devCctvMac") + ")" + "\n";
								}
							});
							
							if(skipProcess){
								svcTgtSeqList += $(this).parent().parent().attr("data-svcTgtSeq") + ",";
								spotDevSeqList += $(this).parent().parent().attr("data-spotDevSeq") + ",";	
							}
						}
					});
					
					if(skipProcess){
						if(svcTgtSeqList=='' || spotDevSeqList==''){
							alert("추가할 단말을 체크해주세요.");
							return false;
						}	
					}else{
						alert("단말은 중복 등록을 할 수 없습니다.\n" + msg);
						return false;
					}
					
					if(svcTgtSeqList.length>0){ 
						svcTgtSeqList = svcTgtSeqList.substring(0, svcTgtSeqList.length -1); //마지막 "," 제거
					}
					if(spotDevSeqList.length>0){
						spotDevSeqList = spotDevSeqList.substring(0, spotDevSeqList.length -1); //마지막 "," 제거
					}
					//=================================================================
						
					//alert("svcTgtSeqList : " + svcTgtSeqList + "\n" + "spotDevSeqList : " + spotDevSeqList);
					
					if(!confirm("체크된 단말을 등록하시겠습니까?")){
						return false;
					}
					
					$("#svcTgtSeqList").val(svcTgtSeqList);
					$("#spotDevSeqList").val(spotDevSeqList);
					
					var searchColumn = $('#searchColumn option:selected').val();
					var searchString = $('#searchString').val();
					
					var frm = document.registerForm;
					frm.searchColumn.value = searchColumn;
					frm.searchString.value = searchString;
					frm.submit();
				});
				
			});
			
			function goDelete(svcTgtSeq, spotDevSeq) {
				
				if(!confirm("등록된 단말을 삭제하시겠습니까?")){
					return false;
				}
				
				var searchColumn = $('#searchColumn option:selected').val();
				var searchString = $('#searchString').val();
				
				var frm = document.deleteForm;
				frm.svcTgtSeq.value = svcTgtSeq;
				frm.spotDevSeq.value = spotDevSeq;
				frm.searchColumn.value = searchColumn;
				frm.searchString.value = searchString;
				frm.submit();
			}
			
		</script>	
		
	</jsp:attribute>

	<jsp:body>
		<div class="boardTitle">
			<div class="borderTitle">
				<span class="pageTitle">테스트 단말 관리</span>
			</div>
		</div>
		
		<form name="deleteForm" method="post" action="/manage/betaCamList">
			<input type="hidden" id="method" name="method" value="delete" />
			<input type="hidden" id="svcTgtSeq" name="svcTgtSeq" />
			<input type="hidden" id="spotDevSeq" name="spotDevSeq" />
			<input type="hidden" name="searchColumn" />
			<input type="hidden" name="searchString" />
		</form>
		
		<table class="tbl_1">
			<caption></caption>
			<colgroup>
				<col style="width:15%" />
				<col style="width:*%" />
			</colgroup>
			<tbody>
				<tr>
					<th scope="row">단말 검색</th>
					<td colspan="3">
							<form name="searchForm" method="post" action="/manage/betaCamList">
								<div style="text-align:left">
									<select id="searchColumn" name="searchColumn" title="검색조건을 선택하세요">
										<option value="mb.mbr_id">고객 ID</option>
										<option value="stcb.tel_no">휴대폰 번호</option>
										<option value="sdb.spot_dev_id">카메라 ID</option>
										<option value="sdeb.dev_cctc_mac">카메라 mac</option>
									</select>
									<input type="text" id="searchString" name="searchString" style="width:150px;" title="검색어를 입력하세요" />
									<button type="button" id="btnSearchCam" class="btn" style="width:100px;"><span>단말 검색</span></button>
									<button type="button" id="btnInsertCam" class="btn" style="width:150px;"><span>체크된 단말 등록</span></button>
								</div>			
							</form>
							<table class="board mt10">
							<caption></caption>
							<colgroup>
								<col style="width:5%" />
								<col style="width:5%" />
								<col style="width:12%" />
								<col style="width:15%" />
								<col style="width:12%" />
								<col style="width:10%" />
								<col style="width:12%" />
								<col style="width:10%" />
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
							
							<tbody id="searchCamListContainer">
							<!-- DYNAMIC AREA 'list' -->
							<c:if test="${empty searchCamList}">
								<tr> 	
									<td style="border-left:none;" align="center" colspan="8">검색된 데이타가 없습니다</td>
								</tr>
							</c:if>
							<form name="registerForm" method="post" action="/manage/betaCamList">
								<input type="hidden" id="method" name="method" value="register" />
								<input type="hidden" id="svcTgtSeqList" 	name="svcTgtSeqList">
								<input type="hidden" id="spotDevSeqList" 	name="spotDevSeqList">
								<input type="hidden" name="searchColumn" />
								<input type="hidden" name="searchString" />
							<c:if test="${!empty searchCamList}">
								<c:forEach var="searchCam" items="${searchCamList}" varStatus="status" >
								<tr 	data-svcTgtSeq="${searchCam.svcTgtSeq}" 
										data-spotDevSeq="${searchCam.spotDevSeq}" 
										onmouseover="this.style.backgroundColor='#f7f7f7'" 
										onmouseout="this.style.backgroundColor=''" 
										bgcolor="white"
								>
									<td style="border-left:none;"><input type="checkbox" name="camChk"></td>
									<td style="text-align:center;"><fmt:formatNumber value="${searchCamCount - status.index }" pattern="#,###"/></td>
									<td style="text-align:center;">${searchCam.mbrId}</td>					
									<td style="text-align:center;">${searchCam.telNo}</td>
									<td style="text-align:center;">${searchCam.spotDevId}</td>
									<td style="text-align:center;">${searchCam.devNm}</td>
									<td style="text-align:center;">${searchCam.devCctvMac}</td>
									<td style="text-align:center;">${searchCam.devConStat}</td>
								</tr>
								</c:forEach>
							</c:if>
							</form>
							</tbody>
						</table>	
					</td>
				</tr>
				<tr>
					<th scope="row">등록된 단말</th>
					<td colspan="3">
						<table class="board mt10">
							<caption></caption>
							<colgroup>
								<col style="width:5%" />
								<col style="width:12%" />
								<col style="width:15%" />
								<col style="width:12%" />
								<col style="width:10%" />
								<col style="width:12%" />
								<col style="width:10%" />
								<col style="width:10%" />
							</colgroup>
							<thead>
								<tr>
									<th scope="col">No.</th>
									<th scope="col">olleh ID</th>
									<th scope="col">휴대폰 번호</th>
									<th scope="col">카메라 ID</th>
									<th scope="col">카메라 닉네임</th>
									<th scope="col">카메라 mac</th>
									<th scope="col">접속상태</th>
									<th scope="col">삭제</th>
								</tr>
							</thead>
							
							<tbody id="targetCamListContainer">
								<c:if test="${empty targetCamList}">
									<tr> 	
										<td style="border-left:none;" align="center" colspan="7">등록되는 단말이 없습니다.</td>
									</tr>
								</c:if>
								<c:if test="${!empty targetCamList}">
									<c:forEach var="targetCam" items="${targetCamList}" varStatus="status" >
										<tr 	data-svcTgtSeq="${targetCam.svcTgtSeq}" 
												data-spotDevSeq="${targetCam.spotDevSeq}" 
												data-devCctvMac="${targetCam.devCctvMac}" 
												onmouseover="this.style.backgroundColor='#f7f7f7'" 
												onmouseout="this.style.backgroundColor=''" 
												bgcolor="white"
										>
											<td style="border-left:none; text-align:center;"><fmt:formatNumber value="${targetCamCount - status.index }" pattern="#,###"/></td>
											<td style="text-align:center;">${targetCam.mbrId}</td>
											<td style="text-align:center;">${targetCam.telNo}</td>
											<td style="text-align:center;">${targetCam.spotDevId}</td>
											<td style="text-align:center;">${targetCam.devNm}</td>
											<td style="text-align:center;">${targetCam.devCctvMac}</td>
											<td style="text-align:center;">${targetCam.devConStat}</td>
											<td style="text-align:center;"><a class="btn2 btn2_red30" onclick="goDelete('${targetCam.svcTgtSeq}', '${targetCam.spotDevSeq}')" style="cursor:pointer"><span>삭제</span></a></td>
										</tr>
									</c:forEach>
								</c:if>
							</tbody>
						</table>
					</td>
				</tr>
		</table>		
		
	</jsp:body>
</layout:main>