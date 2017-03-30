<%@ page contentType="text/html" trimDirectiveWhitespaces="true" pageEncoding="UTF-8"%>
<%@ taglib prefix="layout" 	tagdir="/WEB-INF/tags/layout" %>
<%@ taglib prefix="c"  		uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" 	uri="http://java.sun.com/jsp/jstl/fmt" %>

<layout:main>

	<jsp:attribute name="javascript">
		<script type="text/javascript" src="/resources/css/plugins/uploadify/jquery.uploadify.min.js"></script>
		<script type="text/javascript" src="/resources/css/plugins/uploadify/uploadify.swf"></script>
		<script type="text/javascript">
		
			var openYN = "${welcome.openYN}";
		
			${script}
			$(document).ready(function(){
				
				if(openYN == 'Y'){
					$('#openY').attr('checked', 'checked');
				}else if('${coach.openYN}' == 'N'){
					$('#openN').attr('checked', 'checked');
				}
				
				$("#btnList").click(function() {
					var form = document.forms["transForm"];
					form.submit();
				});
				
				$("#btnRemove").click(function() {
					
					if(!confirm('삭제 하시겠습니까?')) return false;
					
					var pk = '${welcome.pk}';
					$.ajax({
						type : "POST",
					    cache: false,						
						url : "/guide/removeWelcomeGuide",
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
				var domain = '${welcome.domain}';
				var port = '${welcome.port}';
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
				var domain = '${welcome.domain}';
				var port = '${welcome.port}';
				
				var max = 10;
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
		<form name="transForm" method="get" action="/guide/guideWelcomeList">
		<input type="hidden" name="page" value="${param.page }" />
		<input type="hidden" name="searchColumn" value="${param.searchColumn }" />
		<input type="hidden" name="searchString" value="${param.searchString }" />
		<input type="hidden" name="cpCode" value="${param.cpCode }" />
		<c:forEach var="image" items="${welcome.imageList }" varStatus="status">
			<input type="hidden" id ="img${status.count}" name="img${status.count}" value="${image.virtualPath}/${image.saveName }" />
			<input type="hidden" id ="imgName${status.count}" name="imgName${status.count}" value="${image.orgName}" />
		</c:forEach>		
		</form>
	
		<div class="boardTitle">
			<div class="borderTitle">
				<span class="pageTitle">웰컴 가이드</span>
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
				<form id="modifyForm" name="modifyForm" method="post" onsubmit="return false;" action="/guide/guideWelcomeModify" enctype="multipart/form-data">
				<input type="hidden" name="method" value="modify" />
				<input type="hidden" name=pk value="${welcome.pk}" />
				<input type="hidden" name="page" value="${param.page }" />
				<input type="hidden" name="searchColumn" value="${param.searchColumn }" />
				<input type="hidden" name="searchString" value="${param.searchString }" />	
				<input type="hidden" name=cpCode value="${param.cpCode}" />
				<input type="hidden" name=targetCpCode value="${welcome.cpCode}" />
				<input type="hidden" name=verNum value="${welcome.verNum}" />							
				<tr>
					<th scope="row">앱명</th>
					<td>
						<c:choose>
							<c:when test="${welcome.cpCode=='001'}">올레기가 홈캠</c:when>
							<c:when test="${welcome.cpCode=='002'}">올레기가 홈피트니스</c:when>
							<c:otherwise></c:otherwise>
						</c:choose>
					</td>
					<th scope="row">앱버전</th>
					<td>${welcome.verNum}</td>
				</tr>
				<tr>
					<th scope="row">작성자</th>
					<td colspan="3">${welcome.writerId}</td>
				</tr>
				<tr>
					<th scope="row">노출여부</th>
					<td colspan="3">
						<input type="radio" id="openY" name="openYN" value="Y" />&nbsp;노출&nbsp;&nbsp;&nbsp;
						<input type="radio" id="openN" name="openYN" value="N" checked="checked" />&nbsp;비노출
					</td>
				</tr>
				<c:if test="${!empty welcome.imageList }">
				<tr>
					<th scope="row">등록된 이미지</th>
					<td colspan="3">
						<c:forEach var="image" items="${welcome.imageList }" varStatus="status">
							&nbsp;${status.count}.&nbsp;&nbsp;<a href="#none" onclick="viewImage('${image.virtualPath}/${image.saveName }');">${image.orgName }</a> 
							<br/>
						</c:forEach>
					</td>
				</tr>			
				</c:if>	
				<tr>
					<th scope="row">이미지</th>
					<td colspan="3">
						<div id="fileContainer" style="width:100%">
		            		<div style="padding:3px;"><b>&nbsp;1.&nbsp;&nbsp;</b><input type="file" name="image1" accept=".png, .jpg, .jpeg, .bmp, .gif" style="width:70%;"/></div>	
		            		<div style="padding:3px;"><b>&nbsp;2.&nbsp;&nbsp;</b><input type="file" name="image2" accept=".png, .jpg, .jpeg, .bmp, .gif" style="width:70%;"/></div>
		            		<div style="padding:3px;"><b>&nbsp;3.&nbsp;&nbsp;</b><input type="file" name="image3" accept=".png, .jpg, .jpeg, .bmp, .gif" style="width:70%;"/></div>
		            		<div style="padding:3px;"><b>&nbsp;4.&nbsp;&nbsp;</b><input type="file" name="image4" accept=".png, .jpg, .jpeg, .bmp, .gif" style="width:70%;"/></div>
		            		<div style="padding:3px;"><b>&nbsp;5.&nbsp;&nbsp;</b><input type="file" name="image5" accept=".png, .jpg, .jpeg, .bmp, .gif" style="width:70%;"/></div>
		            		<div style="padding:3px;"><b>&nbsp;6.&nbsp;&nbsp;</b><input type="file" name="image6" accept=".png, .jpg, .jpeg, .bmp, .gif" style="width:70%;"/></div>
		            		<div style="padding:3px;"><b>&nbsp;7.&nbsp;&nbsp;</b><input type="file" name="image7" accept=".png, .jpg, .jpeg, .bmp, .gif" style="width:70%;"/></div>
		            		<div style="padding:3px;"><b>&nbsp;8.&nbsp;&nbsp;</b><input type="file" name="image8" accept=".png, .jpg, .jpeg, .bmp, .gif" style="width:70%;"/></div>
		            		<div style="padding:3px;"><b>&nbsp;9.&nbsp;&nbsp;</b><input type="file" name="image9" accept=".png, .jpg, .jpeg, .bmp, .gif" style="width:70%;"/></div>
		            		<div style="padding:3px;"><b>10.&nbsp;&nbsp;</b><input type="file" name="image10" accept=".png, .jpg, .jpeg, .bmp, .gif" style="width:70%;"/></div>		            						            		
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