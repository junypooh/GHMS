<%@ page contentType="text/html" trimDirectiveWhitespaces="true" pageEncoding="UTF-8"%>
<%@ taglib prefix="layout" 	tagdir="/WEB-INF/tags/layout" %>
<%@ taglib prefix="c"  		uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" 	uri="http://java.sun.com/jsp/jstl/fmt" %>

<layout:main>

	<jsp:attribute name="javascript">
	
		<script type="text/javascript">			
			var page   = "${param.page}";
 			var cpCode = "${param.cpCode}"; 
	 	
			//팝업 부분 
			function openPopup(){
				var width = "750";
				var height = "480";
				var leftPos = (screen.availWidth - width)/2;
				var topPos = (screen.availHeight - height)/2;
				var position = "width = "+ width + ", height = " + height 
								+ ",left= " + leftPos + ", top= " + topPos;
				
				//window.open("winOpen", position, "menubar=1");
				var url = "/monitor/monitorDetail";
				window.open(url, "winOpen", position, "menubar=1");
				
				/* var frm = document.popForm;
				frm.action = "/inquiry/pnsHistoryDetail"
				frm.target = "winOpen";
				frm.method = "post";
				frm.cpCode.value = cpCode;
				frm.customerId.value = customerId;
				frm.phoneNum.value = phoneNum;
				frm.submit(); */
			}
 			
			$(document).ready(function() {
 				$('#cpCode').val(cpCode); 
				$("#searchColumn").val(searchColumn);
				
			    // 달력 - 시작일
			    var start_date= $('#start_date').val(); 
			    $('#start_date').datepicker({
			        dateFormat: 'yy-mm-dd',
			        onSelect: function (dateText, inst) {
			            var sEndDate = jQuery.trim($('#end_date').val());
			            if (sEndDate.length>0) {
			                var iEndDate   = parseInt(sEndDate.replace(/-/g, ''));
			                var iStartDate = parseInt(jQuery.trim(dateText).replace(/-/g, ''));
			                
			                if (iStartDate>iEndDate) {
			                    alert('시작일보다 종료일이 과거일 수 없습니다.');
			                    $('#start_date').val('');
			                }
			            }
			        }
			    });
			    $('#img_start_date').click(function() {$('#start_date').focus();});
			    // 달력 - 종료일
			    $('#end_date').datepicker({
			        dateFormat: 'yy-mm-dd',
			        onSelect: function (dateText, inst) {
			            var sStartDate = jQuery.trim($('#start_date').val());
			            if (sStartDate.length>0) {
			                var iStartDate = parseInt(sStartDate.replace(/-/g, ''));
			                var iEndDate  = parseInt(jQuery.trim(dateText).replace(/-/g, ''));
			                
			                if (iStartDate>iEndDate) {
			                    alert('시작일보다 종료일이 과거일 수 없습니다.');
			                    $('#end_date').val('');
			                }
			            }
			        }
			    });
			    $('#img_end_date').click(function() {$('#end_date').focus();});
			});

		</script>	
		
	</jsp:attribute>

	<jsp:body>
	
			<div class="boardTitle">
				<div class="borderTitle">
					<span class="pageTitle">올레 홈캠 모니터링</span>
				</div>
			</div>
			<!-- APP 형 활성화시 -->
			<div id="textType" style="display:block;">
				<div class="boardTop">	
					<div class="tab_left1">APP</div>
				    <div class="tab_right1"><a href="#">카메라</a></div>
				    <div class="tab_con" style="height: auto;">
		            	<div style="padding:5px; padding-left: 10px;  margin: 10px;">
							<select id="searchColumn" name="searchColumn" title="검색조건을 선택하세요">
								<option value="camId">카메라ID</option>
								<option value="camNick">카메라닉네임</option>
								<option value="phNum">휴대폰번호</option>
								</select>
								<input type="text" id="searchString" name="searchString"  title="검색어를 입력하세요" value="${param.searchString }" />
								<button type="button" id="btnSearch" class="btn"><span>검색</span></button>				            	
				      	</div>
			          	<div style="padding: 5px;  margin: 10px;">
			           		<span style="margin-left: 15px; margin-right: 5px;">검색</span>
				       		<select id= "term" name= "term" title="기간을 입력하세요"> 
			        			<option value="lasWeek">최근 일주일</option>
			           			<option>최근 3개월</option>
			           			<option>최근 1년</option>
			           			<option>전체</option>
			           		</select>

			           		<span style="margin-left: 15px; margin-right: 5px;">기간</span>
           	
							<input type="text" id="start_date" name= "start_date" value="" require= "true" msg="예약 시작 일을 입력하세요." class="ipt_tx" style="width:80px" readonly="readonly"/>
							~
							<input type="text" 		id="end_date" 	 name="end_date"	value=""  class="ipt_tx" style="width:80px" readonly="readonly"/>							
						<button type="button" id="btnSearch" class="btn">검색</button>
			           	</div>
				   	 </div>
				</div>
			</div>		   
		     <!-- 카메라 형 활성화시 -->
			 <div id="imageType" style="display:none;"></div>
		<table class="board mt10">
			<caption>모니터링 조회 결과의 제목, 게시시간, 작성자, 노출여부를 확인 할수 있는 표</caption>
			<colgroup>
				<col style="width:5%" />
				<col style="width:15%" />
				<col style="width:10%" />
				<col style="width:15%" />
				<col style="width:*" />
				<col style="width:15%" />
				<col style="width:10%" />
			</colgroup>
			<thead>
				<tr>
					<th scope="col">NO</th>
					<th scope="col">카메라ID</th>
					<th scope="col">카메라 닉네임</th>
					<th scope="col">휴대폰 번호</th>
					<th scope="col">요청 내용</th>
					<th scope="col">요청 시간</th>
					<th scope="col">응답 결과</th>
				</tr>
			</thead>
			
			<tbody id="noticeListContainer">
			
			<!-- DYNAMIC AREA 'list' -->
			<c:if test="${!empty noticeList}">
				<c:forEach var="notice" items="${noticeList}" varStatus="status" >
				<tr data-pk="${notice.pk}" onmouseover="this.style.backgroundColor='#f7f7f7'" onmouseout="this.style.backgroundColor=''" bgcolor="white">
					<td><fmt:formatNumber value="${pageNavi.totalCount - ((pageNavi.nowPage - 1) * pageNavi.pageSize + status.index) }" pattern="#,###"/></td>
					<td>${notice.interval}분</td>
					<td>${notice.writerId}</td>					
					<td>${notice.writerId}</td>
					<td style="text-align:center;">
						&nbsp;<a onclick="openPopup();">${notice.writerId}</a>
					</td>
					<td>${notice.amPmDate}</td>
					<td>
						<fmt:parseDate value="${notice.regDate}" var="dateFmt" pattern="yyyy-MM-dd HH:mm:ss"/>
						<fmt:formatDate value="${dateFmt}" pattern="yyyyMMdd HH:mm"/>
					</td>
				</tr>
				</c:forEach>
			</c:if>			
	
			<c:if test="${empty noticeList}">
				<tr> 	
					<td align="center" colspan="8">검색된 데이타가 없습니다</td>
				</tr>
			</c:if>
			</tbody>
		</table>
		
		${pageNavi.navi}
		
	</jsp:body>
</layout:main>