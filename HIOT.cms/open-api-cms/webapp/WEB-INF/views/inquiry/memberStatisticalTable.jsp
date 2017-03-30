<%@ page contentType="text/html" trimDirectiveWhitespaces="true" pageEncoding="UTF-8"%>
<%@ taglib prefix="layout" 	tagdir="/WEB-INF/tags/layout" %>
<%@ taglib prefix="c"  		uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" 	uri="http://java.sun.com/jsp/jstl/fmt" %>

<layout:main>

	<jsp:attribute name="stylesheet">
    	<style type="text/css">
    		.tab_con4 {width:100%; height:auto; border-top: 1px solid #999;margin-top:30px; overflow:auto;}
    	
  			#statisticalTable ul {
  				width : 100%;
			    margin: 0 auto; 
			    margin: 0;
			    padding: 0;			    
			    display:flex;
			}
			    	
		    #statisticalTable li {
		        height: 100%;
		        display: -moz-inline-stack;
		        display: inline-block;
		        vertical-align: top;
		        position : absloute;
		    } 
		</style>	
		
	</jsp:attribute>

	<jsp:attribute name="javascript">
	
		<script type="text/javascript" src="/resources/js/plugins/chart/jquery.flot.min.js"></script>
		<script type="text/javascript" src="/resources/js/plugins/chart/jquery.flot.symbol.min.js"></script>
		<script type="text/javascript" src="/resources/js/plugins/chart/jquery.flot.time.min.js"></script>
	
		<script type="text/javascript">
		
			var term			= "${param.term}";
			var unitSvcCds		= "${param.unitSvcCds}";
			var searchStartDate	= "${param.searchStartDate}";
			var searchEndDate 	= "${param.searchEndDate}";
			var today = new Date().format("yyyy-MM-dd");

			var options = {
					series: {
						lines: { show: true },
						points: { show: true }
					},
					xaxes: [
					{
						mode: "time",
						timeformat: "%y-%m-%d",
						tickLength: 0, // 세로라인 숨김
						//tickSize: [5, "day"],
						color: "#666",
						font: {
							size: 10,
							style: "normal",
							weight: "normal",
							family: "tahoma",
							variant: "small-caps"
						},
					}],
					yaxis: {
						color: "#666",
				//		tickDecimals: 2,
						font: {
							size: 10,
							style: "normal",
							weight: "normal",
							family: "tahoma",
							variant: "small-caps"
						},
						min : 0
					},
					legend: {
						noColumns: 0,
						labelFormatter: function (label, series) {
							return "<font color=\"white\" style=\"font-size:11px\">" + label + "</font>";
						},
						backgroundColor: "#000",
						backgroundOpacity: 0.8,
						labelBoxBorderColor: "#000000",
						position: "ne",
						margin: [0, -24]
					},
					grid: {
						hoverable: true,
						color: "#666",
						borderWidth: 2,
						mouseActiveRadius: 50,
						backgroundColor: { colors: ["#ffffff", "#EDF5FF"] },
						axisMargin: 20
					}
				};		
		
			var tabStyleOff		= "height:22px;border: 1px solid #999;background-color:#fff;z-index:100;";
			var tabStyleOn		= "height:22px;border-top: 1px solid #999; border-left: 1px solid #999; border-bottom: 1px solid #999;background-color:#eaeaea;z-index:100;";
			var tabStyleOnLast 	= "height:22px;border-top: 1px solid #999; border-left: 1px solid #999; border-right: 1px solid #999;background-color:#eaeaea;;z-index:100;";
		
			$(document).ready(function() {
				if(unitSvcCds != "") {
					//console.log(unitSvcCds);
					var unitSvcCdItem = unitSvcCds.replaceall("'", "").replaceall("& #39;", "").split(",");
					
 					for(var i = 0;i < unitSvcCdItem.length;i++) {
						$("input:checkbox[name=unitSvcCd][value=" + unitSvcCdItem[i] + "]").prop("checked", true);
						//console.log(unitSvcCdItem[i]);
					}
				} else {
					$("input:checkbox[name=unitSvcCd]").prop("checked", true);
				}
				
				$("#btnSearch").click(function(){
					var form = document.forms["searchForm"];
					if(validate(form)) {
						var unitSvcCdStr = "";
						var unitSvcCd = $("input:checkbox[name=unitSvcCd]:checked");
						if(unitSvcCd.size() == 0){
							alert("최소 한개 이상의 상품을 선택하셔야합니다.");
							return false;
						} 
						
						unitSvcCd.each(function(){
							unitSvcCdStr += "'" + $(this).val() + "',";
						});
						
						$("#unitSvcCds").val(unitSvcCdStr.substring(0, unitSvcCdStr.length - 1));
						
						form.submit();
					}
				});
				
				if(searchStartDate == "" && searchEndDate == "") {
					changeTerm("10day");
				}
				$("#term").val(term).change(function(){
					changeTerm($(this).val());
				});				
				
				$(".tab_left1").each(function(index){
					if(index > 0) {
						$(this).attr("style", tabStyleOff + "margin-left:" + (100 * index) + "px;").attr("active", "off");
					} else {
						$(this).attr("style", tabStyleOn).attr("active", "on");
					}
					
					$(this).attr("index", index);
					
					$(this).click(function(){
						changeTab(this);
					});
				});
				
				$(".tab_con4").each(function(index) {
					if(index == 0) {
						$(this).attr("style", "display:block;");
					} else {
						$(this).attr("style", "display:none;");
					}
				});
				
				//현재시간
				var nowDate = new Date();
				
			    // 달력 - 시작일
 			    var startDate = $('#searchStartDate').val();
			    var endDate = $('#searchEndDate').val(); 
				
			    // term selectbox change 시 초기화 날짜 가져옴. 
			    $('#term').change(function(){
					termValue = this.value;
					startDate = $('#searchStartDate').val();
					endDate = $('#searchEndDate').val();
				});
				
			    $('#searchStartDate').datepicker({
			        dateFormat: 'yy-mm-dd',
			        onSelect: function (dateText, inst) {
			            var sEndDate = jQuery.trim($('#searchEndDate').val());
			            if (sEndDate.length>0) {
			                var iEndDate   = parseInt(sEndDate.replace(/-/g, ''));
			                var iStartDate = parseInt(jQuery.trim(dateText).replace(/-/g, ''));
			                
			                // 시작일을 끝일 보다 미래의 시점으로 선택 했을 경우, alert와 날짜 초기화. 
			                if (iStartDate>iEndDate) {
			                    alert('시작일보다 종료일이 과거일 수 없습니다.'); 
			                    $("#searchStartDate").val(startDate);
			    				$("#searchEndDate").val(endDate);			    				
			                }
			            }
			        }
			    });
			    
			    // 달력 - 종료일
			    $('#searchEndDate').datepicker({
			        dateFormat: 'yy-mm-dd',
			        onSelect: function (dateText, inst) {
			            var sStartDate = jQuery.trim($('#searchStartDate').val());
			            if (sStartDate.length>0) {
			                var iStartDate = parseInt(sStartDate.replace(/-/g, ''));
			                var iEndDate  = parseInt(jQuery.trim(dateText).replace(/-/g, ''));

			                // 시작일을 끝일 보다 미래의 시점으로 선택 했을 경우, alert와 날짜 초기화.
			                if (iStartDate>iEndDate) {
			                    alert('시작일보다 종료일이 과거일 수 없습니다.');
			                    $("#searchStartDate").val(startDate);
			    				$("#searchEndDate").val(endDate);		
			                }
			            }
			        }
			    });
				
				$("#chartCate").change(function() {
					$('#placeholder').empty();
					var dataurl = $('#chartCate option:selected').val();
					$.plot("#placeholder", $("body").data(dataurl) , options);
				});		
				
				createChart();
				
			});
			
			function changeTab(activeTab) {
				var targetTab = $(".tab_left1[active=on]"); 
				var targetMargin = targetTab.attr("index") * 100;
				var activeMargin = $(activeTab).attr("index") * 100;
				
				$(targetTab).attr("style", tabStyleOff + "margin-left:" + targetMargin + "px;").attr("active", "off");
				
				if(parseInt($(activeTab).attr("index")) + 1 == $(".tab_left1").size()) {
					$(activeTab).attr("style", tabStyleOnLast + "margin-left:" + activeMargin + "px;").attr("active", "on");;
				} else {
					$(activeTab).attr("style", tabStyleOn + "margin-left:" + activeMargin + "px;").attr("active", "on");	
				}
				
				$(".tab_con4").each(function(index) {
					if(index == parseInt($(activeTab).attr("index"))) {
						$(this).attr("style", "display:block");
					} else {
						$(this).attr("style", "display:none");
					}
				});
			}			
			
			function createChart() {
				
				var unitSvcCdStr = "";
				var unitSvcCd = $("input:checkbox[name=unitSvcCd]:checked");				
				
				unitSvcCd.each(function(){
					unitSvcCdStr += "'" + $(this).val() + "',";
				});
				
				unitSvcCdStr = unitSvcCdStr.substring(0, unitSvcCdStr.length - 1);				
				
 				$.ajax({
					type : "POST",
				    cache: false,						
					url : "/inquiry/memberStatisticalChart",
					dataType : "json",
					data : { "unitSvcCds" : unitSvcCdStr, "searchStartDate" : $("#searchStartDate").val(), "searchEndDate" : $("#searchEndDate").val()}
				})
				.done(function(result){
	 				if(result.code == 200) {
						$("body").data("accrueOperate", result.accrueOperate);
						$("body").data("operate", result.operate);
						$("body").data("stop", result.stop);
						$("body").data("cancel", result.cancel);
						$.plot("#placeholder", result.accrueOperate, options);
						$("#placeholder").UseTooltip();						
					} else {
						alert("서버에서 오류가 발생하였습니다.\n다시 시도하여 주십시오.");
					} 
				})
				.fail(function( jqXHR, textStatus, errorThrown){
					console.log(jqXHR);
				});			
 				
				var previousPoint = null, previousLabel = null;
				$.fn.UseTooltip = function () {
					$(this).bind("plothover", function (event, pos, item) {
						if (item) {
							if ((previousLabel != item.series.label) || (previousPoint != item.dataIndex)) {
								previousPoint = item.dataIndex;
								previousLabel = item.series.label;
								$("#tooltip").remove();
		
								var x = item.datapoint[0];
								var y = item.datapoint[1];
								var date = new Date(x);
								var color = item.series.color;
		
								showTooltip(item.pageX, item.pageY, color,
											"<span>" + item.series.label + "</span><br>"  +
											(date.getMonth() + 1) + "/" + date.getDate() +
											" : <strong>" + y + "</strong> 명");
							}
						} else {
							$("#tooltip").remove();
							previousPoint = null;
						}
					});
				}; 				
			}
			
			function showTooltip(x, y, color, contents) {
				$('<div id="tooltip">' + contents + '</div>').css({
					position: 'absolute',
					display: 'none',
					top: y - 40,
					left: x - 120,
					border: '2px solid ' + color,
					padding: '3px',
					'font-size': '11px',
					'border-radius': '5px',
					'background-color': '#fff',
					'font-family': '돋움, Verdana, Arial, Helvetica, Tahoma, sans-serif',
					opacity: 0.9
				}).appendTo("body").fadeIn(200);
			}			
			
			function changeTerm(range) {
				var startDate = "", endDate = "";
				switch(range) {
					case "10day":
						startDate = dateAdd("dd", -10, today.replace("-", ""), "-");
						endDate = today;
						break;
					case "3month":
						startDate = dateAdd("mm", -3, today.replace("-", ""), "-");
						endDate = today;
						break;
					case "1year":
						startDate = dateAdd("yyyy", -1, today.replace("-", ""), "-");
						endDate = today;
						break;
				}
				
				$("#searchStartDate").val(startDate);
				$("#searchEndDate").val(endDate);				
			}
			
		</script>	
		
	</jsp:attribute>

	<jsp:body>
	
		<form name="searchForm" method="post" action="/inquiry/memberStatisticalTable">
		<input type="hidden" id="unitSvcCds" name="unitSvcCds" value="" />
		<div class="boardTitle">
			<div class="borderTitle">
				<span class="pageTitle">상품 별 가입자 현황</span>
			</div>
		</div>
		
		<div class="boardTop">
			<div class="">
				<div style="float:left;">
					<div style="padding-left:13px; padding-bottom:15px;">
						<b>상품&nbsp;</b>
						<c:forEach var="svc" items="${loginInfo.svcList }">
	            		<label><input type="checkbox" name="unitSvcCd" value="${svc.cpCode }"> ${svc.cpName } &nbsp;&nbsp;</label>
	            		</c:forEach>
					</div>
					<div style="padding-bottom:20px;">
						<b>기간별&nbsp;</b>
						<select id="term" name="term" title="기간을 입력하세요"> 
		        			<option value="10day">최근 10일</option>
		           			<option value="3month">최근 3개월</option>
		           			<option value="1year">최근 1년</option>
		           		</select>
		           		&nbsp;&nbsp;
						<input type="text" id="searchStartDate" name="searchStartDate" value="${param.searchStartDate }" require="true" msg="예약 시작 일을 입력하세요." class="ipt_tx" style="width:80px;position: relative; z-index: 10000;"/>
						<b>~</b> 
						<input type="text" id="searchEndDate" name="searchEndDate" value="${param.searchEndDate }" class="ipt_tx" style="width:80px;position: relative; z-index: 10000;"/>
						<button type="button" id="btnSearch" class="btn"><span>검색</span></button>
					</div>
				</div>
			</div>
		</div>
		</form>
		<br/>
		<div id="statisticalTable" style="display:block;">
			<div class="boardTop">	
				<c:forEach var="statisticalList" items="${serviceStatisticalList }" varStatus="status">
					<div class="tab_left1"><a style="cursor:pointer">${statisticalList[0].unitSvcNm }</a></div>
				</c:forEach>
			    <c:forEach var="statisticalList" items="${serviceStatisticalList }" varStatus="test">
					<div class="tab_con4">
						<table style="width: ${statisticalList.size()*99}px">
							<tr>
								<td>
									<ul>
										<li>
											<table style="height:120px;width:101px;border-left:1px solid;border-right:1px solid;border-bottom:1px solid;">
												<tr align="center" bgcolor="#eaeaea">
													<td >&nbsp;</td>
												</tr>
												<tr height="1"><td bgcolor="#A6A6A6"></td></tr>												
												<tr align="center">
													<td bgcolor="#eaeaea">누적 가입자 수</td>
												</tr>												
												<tr height="1"><td bgcolor="#A6A6A6"></td></tr>												
												<tr align="center">
													<td bgcolor="#eaeaea">신규 가입자 수</td>
												</tr>
												<tr height="1"><td bgcolor="#A6A6A6"></td></tr>
												<tr align="center">
													<td bgcolor="#eaeaea">이용 중지자 수</td>
												</tr>
												<tr height="1"><td bgcolor="#A6A6A6"></td></tr>
												<tr align="center">
													<td bgcolor="#eaeaea">해지자 수</td>
												</tr>
											</table>
										</li>									
										<c:forEach var="statistical" items="${statisticalList }" varStatus="status">
										<li>
											<table style="height:120px;width:86px;border-right:1px solid;border-bottom:1px solid;">
												<tr align="center" bgcolor="#eaeaea">
													<td >${statistical.dateStrFmt }</td>
												</tr>
												<tr height="1"><td bgcolor="#A6A6A6"></td></tr>												
												<tr align="center">
													<td >${statistical.accrueOperate }</td>
												</tr>												
												<tr height="1"><td bgcolor="#A6A6A6"></td></tr>												
												<tr align="center">
													<td >${statistical.operate }</td>
												</tr>
												<tr height="1"><td bgcolor="#A6A6A6"></td></tr>
												<tr align="center">
													<td>${statistical.stop }</td>
												</tr>
												<tr height="1"><td bgcolor="#A6A6A6"></td></tr>
												<tr align="center">
													<td>${statistical.cancel }</td>
												</tr>
											</table>
										</li>
										</c:forEach>
									</ul>
								</td>
							</tr>
						</table>
					</div>
			   	</c:forEach>
			</div>
		</div>	
		<br/>
		<div class="chartBox">
			<div style="padding-left:20px;">
			<select name="chartCate" id="chartCate" title="검색조건을 선택하세요">
				<option value="accrueOperate">총 누적가입자수</option>
				<option value="operate">신규가입자수</option>
				<option value="stop">이용중지자수</option>
				<option value="cancel">해지자수</option>
			</select>
			</div>
			<div id="placeholder" style="width:880px;height:500px;" class="demo-placeholder"></div>
		</div>
		
	</jsp:body>
	
</layout:main>