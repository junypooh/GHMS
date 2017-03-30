<%@ page contentType="text/html" trimDirectiveWhitespaces="true" pageEncoding="UTF-8"%>
<%@ taglib prefix="layout" 	tagdir="/WEB-INF/tags/layout" %>
<%@ taglib prefix="c"  		uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" 	uri="http://java.sun.com/jsp/jstl/fmt" %>

<layout:main>

	<jsp:attribute name="javascript">
	
		<script type="text/javascript">		
			${script}
		
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
						alert("홈캠 카메라 신호세기 확인 요청할 대상을\n하나 이상 선택 하셔야합니다.");
						return false;
					}
					
					if($("input[name=repeat]:checked").size() == 0) {
						alert("신호세기 확인 방법을 선택하십시요.");
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
					type 		: "POST",
				    cache		: false,						
					accept		: "application/json",
					url 		: "/monitor/signalPwrCheckRequest",
					contentType	: "application/json; charset=UTF-8", 
					dataType 	: "json",
					data 		: JSON.stringify(signalCheckList)
				})	
				.done(function(result){
					
					if(result.code == "200"){
						alert("카메라 신호세기 확인요청 되었습니다.\n잠시후 '카메라 신호세기 결과조회' 메뉴에서 확인 가능합니다.");
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
						alert("카메라 신호세기 확인요청이 실패하였습니다.\n잠시후 다시 이용해주세요..");
					}
				})
				.fail(function( jqXHR, textStatus, errorThrown){
					console.log(jqXHR);
				});				
			}
			

			// 화면기획 변경으로 체크박스를 숨김, 다중체크를 위해 기능은 유지
			function SignalCheck(chkbox_id){
				
				$("input:checkbox").prop("checked", false);
				$(chkbox_id).prop("checked", true);
				
				signalCheckRequest();
			}

			//엑셀 업로드 보이기
			function openUpload(){
				$("#excelUpload").xShowPopup();
			}

			//엑셀 업로드 숨기기
			function closeUpload(){
				$("#excelUpload").xHidePopup();
			}
			
			/**
			 * layer popup open 처리
			 */
			$.fn.xShowPopup = function() {
				$(this).each(function() {
					$("#mask").show();
					var tempm = $(this).height()/2 ;
					$(this).css('margin-top', '-'+tempm+'px');
					$(this).show();
				});
			};
			/**
			 * layer popup close 처리
			 */
			$.fn.xHidePopup = function(e) {
				if (typeof(e) != "undefined"){
					e.preventDefault();
				}
				$(this).each(function() {
					$("#mask").hide();
					$(this).hide();
				});
			};
			
			function uploadSignalRequest() {
				var form = document.forms["uploadForm"];
				
				if(form.file.value.trim()!=""){
					form.submit();	
				}else{
					alert("엑셀파일(csv)을 선택해주세요.");
				}
			}
			
			function checkExt(file) {
				var enableUploadFileExt=new Array("csv");
				var fileName = file.value;
				var startPoint = 0;
				var isFile = false;
				
				//File 체크확인
				var isFileChecked = true;

		        if(file.value == ""){
		            return;
		        }

		        //파일 확장자 체크
		        for(var i=fileName.length-1; i>=0; i--) {
		            if(fileName.charAt(i) == ".") {
		                startPoint = i;
		                break;
		            }
		        }
		        
		        var fileExt = fileName.substring(startPoint+1,fileName.length);

		        for(i=0; i<enableUploadFileExt.length; i++) {
		            if(fileExt.toLowerCase() == enableUploadFileExt[i]){
		                isFile = true;
		                break;
		            }
		        }
		        
		        if(!isFile){
		            alert("CSV 파일이 아닙니다.\n다시 선택해주세요.");
		            file.value="";
		            isFile = false;
		            return;
		        }


			}
			
		</script>	
		
	</jsp:attribute>

	<jsp:body>
		<div tabindex="0" class="layerPop" id="excelUpload" style="margin-top: -108px; display: none;">
		<form name="uploadForm" method="post" action="/monitor/signalPwrCheckUpload" onsubmit="return false;" enctype="multipart/form-data">
			<h4>신호세기 다중조회</h4>
			<div class="layerCnt">
				<p align="left">
					엑셀파일(csv) <input type="file" name="file" accept=".csv" onchange="checkExt(this);" style="width:80%;" />
				</p>
				<p align="left">다중 조회는 최대 100개까지 입력이 가능합니다.<br><br></p>
				
				<div class="fll">
					<a class="btn" style="padding-top: 8px;" href="/resources/sample.csv"><span>다중조회 양식 다운로드</span></a>
				</div>
				<div class="flr">
					<a onclick="javascript:uploadSignalRequest();" class="btn2 btn2_red30" style="cursor:pointer"><span>요청</span></a>
				</div>
			</div>
			<div class="layClose">
				<button class="btnClose" onclick="closeUpload();"><img alt="레이어 닫기" src="/resources/images/btn_x.png"></button>
			</div>
		</form>
		</div>
		
		<div class="boardTitle">
			<div class="borderTitle">
				<span class="pageTitle">올레 홈캠 카메라 신호세기 확인 요청</span>
			</div>
		</div>
		<div class="boardTop">	
			<div class="fll">
				<button type="button" class="btn" onClick="openUpload();"><span>신호세기 다중조회 요청</span></button>
			</div>
				
			<div class="flr">
				<form name="searchForm" method="get" action="/monitor/signalPwrCheckList">
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
				<col style="width:15%" />
				<col style="width:*" />
				<col style="width:15%" />
				<col style="width:15%" />
				<col style="width:15%" />
				<col style="width:15%" />
			</colgroup>
			<thead>
				<tr>
					<th scope="col"><input type="checkbox" id="btnToggle" /></th>
					<th scope="col">No.</th>
					<th scope="col">olleh ID</th>
					<th scope="col">휴대폰 번호</th>
					<th scope="col">카메라 ID</th>
					<th scope="col">카메라 닉네임</th>
					<th scope="col">카메라 접속상태</th>
					<th scope="col">신호세기 확인</th>
				</tr>
			</thead>
			
			<tbody id="signalCheckListContainer">
			
			<!-- DYNAMIC AREA 'list' -->
			<c:if test="${!empty signalCheckList}">
				<c:forEach var="signalCheck" items="${signalCheckList}" varStatus="status" >
				<tr data-svcTgtSeq="${signalCheck.svcTgtSeq}" data-spotDevSeq="${signalCheck.spotDevSeq}" data-devUUID="${signalCheck.devUUID}" onmouseover="this.style.backgroundColor='#f7f7f7'" onmouseout="this.style.backgroundColor=''" bgcolor="white">
					<td ><input id="chk_${signalCheck.svcTgtSeq}" type="checkbox" name="" value=""></td>
					<td style="text-align:center;"><fmt:formatNumber value="${signalCheckCount - status.index }" pattern="#,###"/></td>
					<td style="text-align:center;">${signalCheck.mbrId}</td>					
					<td>&nbsp;${signalCheck.telNo}</td>
					<td style="text-align:center;">${signalCheck.spotDevId}</td>
					<td style="text-align:center;">${signalCheck.devNm}</td>
					<td style="text-align:center;">${signalCheck.devConStat}</td>
					<td style="text-align:center;"><a onclick="javascript:SignalCheck(chk_${signalCheck.svcTgtSeq})" class="btn2 btn2_red30" style="cursor:pointer"><span>요청</span></a></td>
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
		
		<c:if test="${!empty signalCheckList}">
		<table>
			<tr height="50px">
				<td>
					<input type="button" id="btnSignalCheck" value="신호세기 확인 요청" class="btn" style="cursor:pointer">
				</td>
			</tr>
		</table>	
		</c:if>	
		
		<table style="border-width:1px;border-style:solid;width:100%;height:40px; display:none;">
			<tr>
				<td style="text-align:center">반복설정</td>
				<td>
					<label><input type="radio" name="repeat" value="1" checked/> 1회</label>
					&nbsp;
					<label><input type="radio" name="repeat" value="0" /> 반복</label>
				</td>
				<td style="text-align:left">
					간격
					<select id="collectPeriod">
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
					<select id="collectCount">
						<option value="1">1회</option>
						<option value="2">2회</option>
						<option value="3">3회</option>
						<option value="4">4회</option>
						<option value="5">5회</option>
						<option value="6">6회</option>
						<option value="7">7회</option>
						<option value="8">8회</option>
						<option value="9">9회</option>
						<option value="10">10회</option>
					</select>					
				</td>
				<td style="text-align:center;">
					&nbsp;
				</td>
			</tr>
		</table>		
		
		
	</jsp:body>
	
</layout:main>