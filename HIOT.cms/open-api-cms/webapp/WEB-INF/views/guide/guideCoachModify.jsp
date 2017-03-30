<%@ page contentType="text/html" trimDirectiveWhitespaces="true" pageEncoding="UTF-8"%>
<%@ taglib prefix="layout" 	tagdir="/WEB-INF/tags/layout" %>
<%@ taglib prefix="c"  		uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" 	uri="http://java.sun.com/jsp/jstl/fmt" %>

<layout:main>

	<jsp:attribute name="javascript">
		<script type="text/javascript" src="/resources/css/plugins/uploadify/jquery.uploadify.min.js"></script>
		<script type="text/javascript" src="/resources/css/plugins/uploadify/uploadify.swf"></script>
		<script type="text/javascript">
		
			var openYN = "${coach.openYN}";
			var hdpi_imgFileName = "${coach.hdpiOrgName}";
			var xhdpi_imgFileName = "${coach.xhdpiOrgName}";
			var xxhdpi_imgFileName = "${coach.xxhdpiOrgName}";
			
			${script}
			$(document).ready(function(){
				
				if(openYN == 'Y'){
					$('#openY').attr('checked', 'checked');
				}else if(openYN == 'N'){
					$('#openN').attr('checked', 'checked');
				}
				
				$('#hdpi_imgFileName').text(hdpi_imgFileName);
				$('#xhdpi_imgFileName').text(xhdpi_imgFileName);
				$('#xxhdpi_imgFileName').text(xxhdpi_imgFileName);
				
				$("#btnList").click(function() {
					var form = document.forms["transForm"];
					form.submit();
				});
				
				$("#btnRemove").click(function() {
					
					if(!confirm('삭제 하시겠습니까?')) return false;
					
					var pk = '${coach.pk}';
					$.ajax({
						type : "POST",
					    cache: false,						
						url : "/guide/removeCoachGuide",
						dataType : "json",
						data : { "pkList" : pk}
					})
					.done(function(result){
						if(result.code != 200) {
							alert("서버에서 오류가 발생하였습니다.\n다시 시도하여 주십시오.");
						} else {
							alert("정상적으로 삭제 처리 되었습니다.");
							var form = document.forms["transForm"];
							form.submit();
						}
					})
					.fail(function( jqXHR, textStatus, errorThrown){
						console.log(jqXHR);
					});
				});
				
				$("#btnModify").click(function() {
					var form = document.forms["modifyForm"];

					//유효성 검증
					if(!validate(form)) { return false; }

					//모든 검증이 true일때 submit();
					form.submit();
				});
			});
			
			function viewImage(imagePath) {
				var scheme = "${pageContext.request.scheme}";
				var domain = '${coach.domain}';
				var port = '${coach.port}';
				window.open(scheme + "://" + domain + ":" + port + imagePath, "");
				/*
				var url  = scheme + "://" + domain + ":" + port + imagePath;
				
				var width = "600";
				var height = "800";
				var leftPos = (screen.availWidth - width)/2;
				var topPos = (screen.availHeight - height)/2;
				var position = "width="+ width + ", height=" + height + ", left=" + leftPos + ", top=" + topPos;
				var url = "/preview?url=" + url;
				window.open(url, "winOpen", position, "menubar=1");
				*/
			}
			
			function openPopup()
			{
				var scheme = "${pageContext.request.scheme}";
				var domain = '${coach.domain}';
				var port = '${coach.port}';
				
				var max = 3;
				var img = new Array(max);
				var imgName = new Array(max);
				
				for( i=1; i<=10; i++ )
				{
					img[i-1] = $('#img'+i).val();
					if(typeof img[i-1] == "undefined" ) img[i-1] = "";
					
					imgName[i-1] = $('#imgName'+i).val();
					if(typeof imgName[i-1] == "undefined" ) imgName[i-1] = "";
				}			
				
				var width = "800";
				var height = "800";
				var leftPos = (screen.availWidth - width)/2;
				var topPos = (screen.availHeight - height)/2;
				var position = "width="+ width + ", height=" + height + ", left=" + leftPos + ", top=" + topPos;
				var url = "/preview?type=image&url=" + scheme + "://" + domain + ":" + port;
				for( i=0; i<10; i++ )
				{
					url += "&img"+(i+1)+"=" + img[i];
					url += "&imgName"+(i+1)+"=" + imgName[i];
				}		
				
				window.open(url, "winOpen", position, "menubar=1");
				
			}
			
		</script>
	
	</jsp:attribute>
	
	<jsp:body>
		<form name="transForm" method="get" action="/guide/guideCoachList">
		<input type="hidden" name="page" value="${param.page }" />
		<input type="hidden" name="cpCode" value="${param.cpCode }" />
		<input type="hidden" name="positionCode" value="${param.positionCode }" />
		<input type="hidden" name="searchString" value="${param.searchString }" />
		<input type="hidden" name="searchColumn" value="${param.searchColumn }" />
		</form>
	
		<div class="boardTitle">
			<div class="borderTitle">
				<span class="pageTitle">코치가이드 수정</span>
			</div>
		</div>
		
		<table class="tbl_1">
			<caption></caption>
			<colgroup>
				<col style="width:15%" />
				<col style="width:35%" />
				<col style="width:15%" />
				<col style="width:35%" />
			</colgroup>
			<tbody>
				<form id="modifyForm" name="modifyForm" method="post" onsubmit="return false;" action="/guide/guideCoachModify" enctype="multipart/form-data">
				<input type="hidden" name="method" value="modify" />
				
				<input type="hidden" name="pk" value="${coach.pk}" />
				<input type="hidden" name="page" value="${param.page }" />
				<input type="hidden" name="targetCpCode" value="${coach.cpCode }" />
				<input type="hidden" name="verNum" value="${coach.verNum }" />
				<input type="hidden" name="targetPositionCode" value="${coach.positionCode }" />
				
				<input type="hidden" name="cpCode" value="${param.cpCode }" />
				<input type="hidden" name="positionCode" value="${param.positionCode }" />
				<input type="hidden" name="searchString" value="${param.searchString }" />
				<input type="hidden" name="searchColumn" value="${param.searchColumn }" />
				
				<input type="hidden" name="description1" value="1902" />
				<input type="hidden" name="description2" value="1903" />
				<input type="hidden" name="description3" value="1904" />
				
				<c:forEach var="image" items="${coach.imageList }" varStatus="status">
					<input type="hidden" id ="img${status.count}" name="img${status.count}" value="${image.virtualPath}/${image.saveName }" />
					<input type="hidden" id ="imgName${status.count}" name="imgName${status.count}" value="${image.orgName}" />
				</c:forEach>
				
				
				<tr>
					<th scope="row">상품(앱)명</th>
					<td>${coach.cpName }</td>
					<th scope="row">앱버전</th>
					<td>${coach.verNum}</td>
				</tr>
				<tr>
					<th scope="row">작성자</th>
					<td colspan="3">${coach.writerId}</td>
				</tr>
				<tr>
					<th scope="row">가이드 위치</th>
					<td colspan="3">
						<c:forEach var="code" items="${codeList}" varStatus="status" >
							<c:if test="${code.code == coach.positionCode}">${code.name}</c:if>
						</c:forEach>
					</td>
				</tr>
				<tr>
					<th scope="row">노출여부</th>
					<td colspan="3">
						<input type="radio" id="openY" name="openYN" value="Y" />&nbsp;노출&nbsp;&nbsp;&nbsp;
						<input type="radio" id="openN" name="openYN" value="N" />&nbsp;비노출
					</td>
				</tr>
				<c:if test="${!empty coach.imageList }">
				<tr>
					<th scope="row">등록된 이미지</th>
					<td colspan="3">
						<c:forEach var="image" items="${coach.imageList }" varStatus="status">
							&nbsp;${status.count}.&nbsp;&nbsp;<a href="#none" onclick="viewImage('${image.virtualPath}/${image.saveName }');">${image.orgName }</a> 
							<br/>
						</c:forEach>
					</td>
				</tr>			
				</c:if>	
				<tr>
					<th scope="row">이미지</th>
					<td colspan="3">
						<div id="fileContainer" style=" width:100%; ">
		            		<div style="padding:3px;"><b>&nbsp;1.&nbsp;&nbsp;</b><input type="file" name="image1" accept=".png, .jpg, .jpeg, .bmp, .gif" style="width:70%;" /><b>&nbsp;hdpi&nbsp;(해상도&nbsp;480x800)</b></div>	
		            		<div style="padding:3px;"><b>&nbsp;2.&nbsp;&nbsp;</b><input type="file" name="image2" accept=".png, .jpg, .jpeg, .bmp, .gif" style="width:70%;" /><b>&nbsp;xhdpi&nbsp;(해상도&nbsp;720x1280)</b></div>
		            		<div style="padding:3px;"><b>&nbsp;3.&nbsp;&nbsp;</b><input type="file" name="image3" accept=".png, .jpg, .jpeg, .bmp, .gif" style="width:70%;" /><b>&nbsp;Xxhdpi&nbsp;(해상도&nbsp;1080x1920)</b></div>
		            	</div>
					</td>
				</tr>
				</form>
			</tbody>
		</table>
				
		<div class="btns clearfix">
			<div class="fll">
				<a id="btnList" class="btn2 btn2_black30" style="cursor:pointer"><span>목록</span></a>
				<a id="btnRemove" class="btn2 btn2_black30" style="cursor:pointer"><span>삭제</span></a>
			</div>
			<div class="flr">
				<a id="btnPreview" href="#none" onclick="openPopup();" onfocus="this.flr();" class="btn2 btn2_black30" style="cursor:pointer"><span>미리보기</span></a>
				<a id="btnModify" class="btn2 btn2_red30" style="cursor:pointer"><span>저장</span></a>
			</div>
		</div>			
		
	</jsp:body>
	
</layout:main>