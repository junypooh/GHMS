<%@ page contentType="text/html" trimDirectiveWhitespaces="true" pageEncoding="UTF-8"%>
<%@ taglib prefix="layout"  tagdir="/WEB-INF/tags/layout" %>
<%@ taglib prefix="c"       uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt"     uri="http://java.sun.com/jsp/jstl/fmt" %>

<layout:main>

    <jsp:attribute name="javascript">
    
        <script type="text/javascript">         
            var page   = "${param.page}";
            var cpCode = "${param.cpCode}"; 
        
            var searchDateType   = "${param.searchDateType}";
            var searchMonth  = "${param.searchMonth}";
            var searchColumn = "${param.searchColumn}";
            var searchString = "${param.searchString}";     
            var searchStartDate = "${param.searchStartDate}";
            var searchEndDate = "${param.searchEndDate}";
            var pushColumn = "${param.pushColumn}";
            
            var fd_type = "${param.fd_type}";
            var fd_depth_1 = "${param.fd_depth_1}";
            var fd_depth_2 = "${param.fd_depth_2}";
            var fd_depth_3 = "${param.fd_depth_3}";
            
            $(document).ready(function() {
            	
            	$("#fd_type").val(fd_type);
            	$("#fd_depth_1").val(fd_depth_1);
            	$("#fd_depth_2").val(fd_depth_2);
            	$("#fd_depth_3").val(fd_depth_3);
            	
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
                
                $('#cpCode').val(cpCode); 
                $("#searchColumn").val(searchColumn);
                $("#pushColumn").val(pushColumn);
                
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
                
                $("#btnExcel").click(function(){
					var excelCount = '${excelCount}';
					var form = document.forms["searchForm"];
					form.event.value = "excel";
					
					if(confirm("엑셀파일은 최대 " + excelCount + "건까지 다운로드 하실수 있습니다.\n다운로드 하시겠습니까?")){
						form.submit();	
					}
				});
                
                $("#cpCode").change(function(){
                    var form = document.forms["searchForm"];
                    form.event.value = "";
                    form.submit();                  
                });
                
                $("#btnSearch").click(function(){
                	
                    var form = document.forms["searchForm"];
                    form.event.value = "";
                    form.submit();                  
                });
                
                $("#searchString").keypress(function(e){
    				if (e.keyCode == 13){
    					var form = document.forms["searchForm"];
    					form.event.value = "";
                        form.submit();
    			    }    
    			});
                
                $("#pushColumn").change(function(){
                    var form = document.forms["searchForm"];
                    form.event.value = "";
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
                
                $("#fd_type").change(function(){
                	var fd_type = jQuery("select[name=fd_type]").val();
					var selectColumn = "fd_depth_1";
					
					$("#fd_depth_1").empty().data('options');
					$("#fd_depth_1").append("<option value=''>전체</option>");
					
					$("#fd_depth_2").empty().data('options');
					$("#fd_depth_2").append("<option value=''>전체</option>");
					
					$("#fd_depth_3").empty().data('options');
					$("#fd_depth_3").append("<option value=''>전체</option>");
					
					if(fd_type == '') return false;
					
					$.ajax({
						type : "POST",
					    cache: false,						
						url : "/inquiry/getSelectMenuCodeList",
						dataType : "json",
						data : { 
								"selectColumn" : selectColumn
							,	"pk_code" : fd_type
						}
					})
					.done(function(result){
						if(result.code != 200) {
							alert("서버에서 오류가 발생하였습니다.\n다시 시도하여 주십시오.");
						} else {
							var cnt = result.list.length;	
							for(var i=0; i<cnt; i++){
								var pk_code_str	= result.list[i].pk_code_str;
       							var fd_depth_1	= result.list[i].fd_depth_1;
       							$("#fd_depth_1").append("<option value='"+pk_code_str+"'>"+fd_depth_1+"</option>");
       						}
						}
					})
					.fail(function( jqXHR, textStatus, errorThrown){
						console.log(jqXHR);
					});	
				});
                
                $("#fd_depth_1").change(function(){
                	var fd_type = jQuery("select[name=fd_type]").val();
                	var fd_depth_1 = jQuery("select[name=fd_depth_1]").val();
					var selectColumn = "fd_depth_2";
					
					$("#fd_depth_2").empty().data('options');
					$("#fd_depth_2").append("<option value=''>전체</option>");
					
					$("#fd_depth_3").empty().data('options');
					$("#fd_depth_3").append("<option value=''>전체</option>");
					
					if(fd_depth_1 == '') return false;
					
					$.ajax({
						type : "POST",
					    cache: false,						
						url : "/inquiry/getSelectMenuCodeList",
						dataType : "json",
						data : { 
								"selectColumn" : selectColumn
							,	"pk_code" : fd_type + fd_depth_1
						}
					})
					.done(function(result){
						if(result.code != 200) {
							alert("서버에서 오류가 발생하였습니다.\n다시 시도하여 주십시오.");
						} else {
							var cnt = result.list.length;	
							for(var i=0; i<cnt; i++){
								var pk_code_str	= result.list[i].pk_code_str;
       							var fd_depth_2	= result.list[i].fd_depth_2;
       							$("#fd_depth_2").append("<option value='"+pk_code_str+"'>"+fd_depth_2+"</option>");
       						}
						}
					})
					.fail(function( jqXHR, textStatus, errorThrown){
						console.log(jqXHR);
					});	
				});
                
                $("#fd_depth_2").change(function(){
                	var fd_type = jQuery("select[name=fd_type]").val();
                	var fd_depth_1 = jQuery("select[name=fd_depth_1]").val();
                	var fd_depth_2 = jQuery("select[name=fd_depth_2]").val();
					var selectColumn = "fd_depth_3";
					
					$("#fd_depth_3").empty().data('options');
					$("#fd_depth_3").append("<option value=''>전체</option>");
					
					if(fd_depth_2 == '') return false;
					
					$.ajax({
						type : "POST",
					    cache: false,						
						url : "/inquiry/getSelectMenuCodeList",
						dataType : "json",
						data : { 
								"selectColumn" : selectColumn
								,	"pk_code" : fd_type + fd_depth_1 + fd_depth_2
						}
					})
					.done(function(result){
						if(result.code != 200) {
							alert("서버에서 오류가 발생하였습니다.\n다시 시도하여 주십시오.");
						} else {
							var cnt = result.list.length;	
							for(var i=0; i<cnt; i++){
								var pk_code_str	= result.list[i].pk_code_str;
       							var fd_depth_3	= result.list[i].fd_depth_3;
       							$("#fd_depth_3").append("<option value='"+pk_code_str+"'>"+fd_depth_3+"</option>");
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
    	<form name="searchForm" method="get" action="/inquiry/monitorHistoryList">
    	<input type="hidden" id="event" name="event" />
		<div class="boardTitle">
			<div class="borderTitle">
				<span class="pageTitle">모니터링 사용 조회</span>
	   		</div>
   		</div>
    	<div class="boardTop">
            <div style="padding:5px; padding-left:25px;">
        		<b>검 색&nbsp;</b>
		      	<select id="searchColumn" name="searchColumn" style="width:110px;" title="검색조건을 선택하세요">
          			<option value="mbr_id">고객ID</option>
        			<option value="spot_dev_id">홈카메라ID</option>
       				<option value="tel_no">휴대폰번호</option>
     			</select>
				<input type="text" id="searchString" name="searchString"  title="검색어를 입력하세요" value="${param.searchString }"  style="width:115px;" />
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
   		</div>

        <!-- APP 형 활성화시 -->
        <div id="textType" style="display:block; padding-top:20px">
      		<div class="boardTop">
        		<div class="fll">
		            <select id="fd_type" name="fd_type" style="width:130px;" title="검색조건을 선택하세요">
		                <option value="">전체</option>
		                <c:forEach var="type" items="${typeList}" varStatus="status" >
		                    <option value="${type.pk_code_str}">${type.fd_type}</option>
		                </c:forEach>
		            </select>
		            <select id="fd_depth_1" name="fd_depth_1" style="width:130px;" title="검색조건을 선택하세요">
		                <option value="">전체</option>
		                <c:if test="${!empty depth1_list}">
		                 <c:forEach var="depth1" items="${depth1_list}" varStatus="status" >
		                     <option value="${depth1.pk_code_str}">${depth1.fd_depth_1}</option>
		                 </c:forEach>
		              </c:if>
		            </select>
		            <select id="fd_depth_2" name="fd_depth_2" style="width:130px;" title="검색조건을 선택하세요">
		                <option value="">전체</option>
		                <c:if test="${!empty depth2_list}">
		                 <c:forEach var="depth2" items="${depth2_list}" varStatus="status" >
		                     <option value="${depth2.pk_code_str}">${depth2.fd_depth_2}</option>
		                 </c:forEach>
		              </c:if>
		            </select>
		            <select id="fd_depth_3" name="fd_depth_3" style="width:130px;" title="검색조건을 선택하세요">
		                <option value="">전체</option>
		                <c:if test="${!empty depth3_list}">
		                 <c:forEach var="depth3" items="${depth3_list}" varStatus="status" >
		                     <option value="${depth3.pk_code_str}">${depth3.fd_depth_3}</option>
		                 </c:forEach>
		              </c:if>
		            </select>
                </div>      
            </div>
        </div>         
        </form>                
        <div id="imageType" style="display:none;"></div>
        <table class="board mt10">
            <caption></caption>
            <colgroup>
                <col style="width:5%" />
                <col style="width:10%" />
                <col style="width:10%" />
                <col style="width:12%" />
                <col style="width:10%" />
                <col style="width:*" />
                <col style="width:5%" />
            </colgroup>
            <thead>
                <tr>
                    <th scope="col" rowspan="2" style="BORDER-right: #B7B7B7 1px solid;">NO</th>
                    <th scope="col" rowspan="2" style="BORDER-right: #B7B7B7 1px solid;">고객ID</th>
                    <th scope="col" rowspan="2" style="BORDER-right: #B7B7B7 1px solid;">홈카메라ID</th>
                    <th scope="col" rowspan="2" style="BORDER-right: #B7B7B7 1px solid;">홈카메라닉네임</th>
                    <th scope="col" rowspan="2" style="BORDER-right: #B7B7B7 1px solid;">휴대폰번호</th>
                    <th scope="col" style="BORDER-right: #B7B7B7 1px solid;">발생위치</th>
                    <th scope="col" rowspan="2" >일시</th>
                </tr>
                <tr>
                	<th scope="col" style="BORDER-right: #B7B7B7 1px solid;">상세정보</th>
                </tr>
            </thead>
            
            <tbody>
            <!-- DYNAMIC AREA 'list' -->
            <c:if test="${!empty monitorHistoryList}">
                <c:forEach var="monitorHistory" items="${monitorHistoryList}" varStatus="status" >
                <tr onmouseover="this.style.backgroundColor='#f7f7f7'" onmouseout="this.style.backgroundColor=''" bgcolor="white">
                    <td><fmt:formatNumber value="${pageNavi.totalCount - ((pageNavi.nowPage - 1) * pageNavi.pageSize + status.index) }" pattern="#,###"/></td>
                    <td>${monitorHistory.mbr_id}</td>
                    <td>${monitorHistory.spot_dev_id}</td>                  
                    <td>${monitorHistory.dev_nm}</td>
                    <td>${monitorHistory.tel_no}</td>
                    <td>
                    
                    	<!-- 발생 위치 -->
                    	<div style="text-align:left; padding-bottom:10px;">${monitorHistory.description}</div>
						
						<!-- 상세정보 : 모니터링 종료 -->
                    	<c:if test="${monitorHistory.menu_code=='CL03'}">
                    		<c:if test="${monitorHistory.sessionId!=''}">
	                    		<div style="text-align:left; padding-bottom:10px;">
	                    			세션아이디 : ${monitorHistory.sessionId}
	                    		</div>
                    		</c:if>
                    		<div style="text-align:left; padding-bottom:10px;">
                    			대역폭 : ${monitorHistory.bandwidth}
                    			<c:if test="${monitorHistory.resolution!=''}">, 해상도 : ${monitorHistory.resolution}</c:if>
                    			<c:if test="${monitorHistory.monitorTime!=''}">, 모니터링시간 : ${monitorHistory.monitorTime}</c:if>
                    		</div>
                    		<c:if test="${monitorHistory.accNetwork!=''}">
	                    		<div style="text-align:left; padding-bottom:10px;">
	                    			회선망 : ${monitorHistory.accNetwork}
	                    			<c:if test="${monitorHistory.deviceName!=''}">, 단말기명 : ${monitorHistory.deviceName}</c:if>
	                    			<c:if test="${monitorHistory.osVer!=''}">, OS버전 : ${monitorHistory.osVer}</c:if>
	                    		</div>
                    		</c:if>
                    		<div style="text-align:left;">${monitorHistory.fd_memo}</div>
                    	</c:if>
                    	
                    	<!-- 상세정보 : 모니터링 시작 -->
                    	<c:if test="${monitorHistory.menu_code=='CJ11'}">
                    		<c:if test="${monitorHistory.sessionId!=''}">
	                    		<div style="text-align:left;">
	                    			세션아이디 : ${monitorHistory.sessionId}
	                    		</div>
                    		</c:if>
                    		<c:if test="${monitorHistory.accNetwork!=''}">
                    			<div style="text-align:left; padding-top:10px;">
	                    			회선망 : ${monitorHistory.accNetwork}
	                    			<c:if test="${monitorHistory.deviceName!=''}">, 단말기명 : ${monitorHistory.deviceName}</c:if>
	                    			<c:if test="${monitorHistory.osVer!=''}">, OS버전 : ${monitorHistory.osVer}</c:if>
	                    		</div>
                    		</c:if>
                    	</c:if>
	                    	
	                    <!-- 상세정보 : 그 외 -->
                    	<c:if test="${monitorHistory.menu_code!='CL03' && monitorHistory.menu_code!='CJ11'}">
                    		<div style="text-align:left;">${monitorHistory.fd_memo}</div>
                    	</c:if>
	                
	                </td>
                    <td>${monitorHistory.amPmDate}</td>
               	</tr>
               	</c:forEach>
            </c:if>         
            <!-- DYNAMIC AREA 'list' -->
    
            <c:if test="${empty monitorHistoryList}">
                <tr>    
                    <td align="center" colspan="9">검색된 데이타가 없습니다</td>
                </tr>
            </c:if>
            </tbody>
        </table>
        
        ${pageNavi.navi}

    </jsp:body>
</layout:main>