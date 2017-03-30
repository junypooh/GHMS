<%@ page contentType="text/html" trimDirectiveWhitespaces="true" pageEncoding="UTF-8"%>
<%@ taglib prefix="layout" 	tagdir="/WEB-INF/tags/layout" %>
<%@ taglib prefix="c"  		uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" 	uri="http://java.sun.com/jsp/jstl/fmt" %>

<layout:main>

	<jsp:attribute name="javascript">
		<script type="text/javascript">
			var cpCode = "${serviceGuide.cpCode }";
			var svcFn = "${serviceGuide.svcFn}";
			var openYN = "${serviceGuide.openYN}";
			var contentsTypeStr = "${serviceGuide.contentsType}";
			
			${script}
			$(document).ready(function(){
				changeCpCode(cpCode);

				if(openYN != "") {
					$("#open" + openYN).prop("checked", true);
				}				
				
				if(contentsTypeStr != "") {
					contentsType(contentsTypeStr);
				}			
				
				$("#btnList").click(function() {
					var form = document.forms["transForm"];
					form.submit();
				});
				
				$("#btnRemove").click(function() {
					
					if(!confirm('삭제 하시겠습니까?')) return false;
					
					var pk = '${serviceGuide.pk}';
					$.ajax({
						type : "POST",
					    cache: false,						
						url : "/customer/removeServiceGuide",
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
 					if(validate(form)) {
 						
 						if(form.contentsType.value == "text") {
 							var contents = form.faqContents.value.trim();
 							if(contents.length == 0) {
 								alert("서비스 안내 내용을 입력하십시요.");
 								form.contents.focus();
 								return false;
 							}
 						} else {
 							var result = false;
 							$("#fileContainer input:file").each(function(){
 								if($(this).val().trim() != "") {
 									result = true;
 								}
 							});
 							
 							if(!result) {
 								alert("이미지를 한개이상 등록 하셔야 됩니다.");
 								return false;
 							}
 						}

 						form.submit();
					}
				});
				
			});
			
			function contentsType(type) {
				
				$("#contentsType").val(type);
				
				if(type == "text") {
					$("#textType").show();
					$("#imageType").hide();
				} else {
					$("#textType").hide();
					$("#imageType").show();					
				} 
			}
			
			function viewImage(imagePath) {
				var scheme = "${pageContext.request.scheme}";
				var domain = '${serviceGuide.domain}';
				var port = '${serviceGuide.port}';
				window.open(scheme + "://" + domain + ":" + port + imagePath, "");
			}

			function openPopup()
			{
				var type = '${serviceGuide.contentsType}';
				var width = "800";
				var height = "800";
				var leftPos = (screen.availWidth - width)/2;
				var topPos = (screen.availHeight - height)/2;
				var position = "width="+ width + ", height=" + height + ", left=" + leftPos + ", top=" + topPos;
				var url;
				
				if(type == 'text'){
					
					url = "/preview?type=" + type;
					url += "&pkCode=1407"
					url += "&pk=" + '${serviceGuide.pk}';
					url += "&cpCode=" + '${serviceGuide.cpCode}';
					
				}else if(type == 'image'){
					
					var scheme = "${pageContext.request.scheme}";
					var domain = '${serviceGuide.domain}';
					var port = '${serviceGuide.port}';
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
					url = "/preview?type=" + type + "&url=" + scheme + "://" + domain + ":" + port;
					for( i=0; i<10; i++ )
					{
						url += "&img"+(i+1)+"=" + img[i];
						url += "&imgName"+(i+1)+"=" + imgName[i];
					}		
					
				}else{
					alert('잘못된 접근입니다. 관리자에게 문의해주세요.');
				}
				
				window.open(url, "winOpen", position, "menubar=1");
			}
			
			function changeCpCode(cpCode) {
				$.ajax({
					type : "POST",
				    cache: false,						
					url : "/customer/getManageGoodsList",
					dataType : "json",
					data : { "cpCode" : cpCode}
				})
				.done(function(result){
					if(result.code == 200) {
						$("#svcFn").find("option").remove();
 						for(var i = 0; i < result.list.length; i++) {
							$("#svcFn").append($("<option></option>").attr("value", result.list[i].pk).text(result.list[i].name));
						}
 						if(svcFn != "") {
 							$("#svcFn").val(svcFn);
 						}
					} else {
						alert("서버에서 오류가 발생하였습니다.\n다시 시도하여 주십시오.");
					}
				})
				.fail(function( jqXHR, textStatus, errorThrown){
					console.log(jqXHR);
				});
			}
			
		</script>
	
	</jsp:attribute>
	
	<jsp:body>
		<form name="transForm" method="get" action="/customer/serviceGuideList">
		<input type="hidden" name="page" value="${param.page }" />
		<input type="hidden" name="searchColumn" value="${param.searchColumn }" />
		<input type="hidden" name="searchString" value="${param.searchString }" />
		<input type="hidden" name="searchCpCode" value="${param.searchCpCode }" />
		<input type="hidden" name="searchSvcFn" value="${param.searchSvcFn }" />
		</form>
	
		<h2 class="pageTitle">서비스안내(FAQ) 수정</h2>
		<table class="tbl_1">
			<caption></caption>
			<colgroup>
				<col style="width:15%" />
				<col style="width:35%" />
				<col style="width:15%" />
				<col style="width:35%" />
			</colgroup>
			<tbody>
				<form id="modifyForm" name="modifyForm" method="post" action="/customer/serviceGuideModify" enctype="multipart/form-data">
				<input type="hidden" name="method" value="modify" />
				<input type="hidden" name="page" value="${param.page }" />
				<input type="hidden" name="searchColumn" value="${param.searchColumn }" />
				<input type="hidden" name="searchString" value="${param.searchString }" />
				<input type="hidden" name="searchCpCode" value="${param.searchCpCode }" />
				<input type="hidden" name="searchSvcFn" value="${param.searchSvcFn }" />
				<input type="hidden" name="pk" value="${serviceGuide.pk}" />
				<input type="hidden" name="cpCode" value="${serviceGuide.cpCode }" />
				<input type="hidden" id="contentsType" name="contentsType" />
				<tr>
					<th scope="row">앱명</th>
					<td>${serviceGuide.cpName }</td>
					<th scope="row">기능명</th>
					<%-- <td>${serviceGuide.svcFnName }</td> --%>
					<td><select id="svcFn" name="svcFn"></select></td>
				</tr>
				<tr>
					<th scope="row">작성자</th>
					<td colspan="3">${serviceGuide.writerId}</td>
				</tr>
				<tr>
					<th scope="row"><label for="title">제목</label></th>
					<td colspan="3"><input type="text" id="title" name="title" value="${serviceGuide.title}" style="width:430px" require="true" msg="제목을 입력하셔야 합니다." /></td>
				</tr>
				<tr>
					<th scope="row">노출 여부</th>
					<td colspan="3">
						<input type="radio" id="openY" name="openYN" value="Y" />&nbsp;노출&nbsp;&nbsp;&nbsp;
						<input type="radio" id="openN" name="openYN" value="N" />&nbsp;비노출&nbsp;&nbsp;&nbsp;
					</td>
				</tr>
				<c:if test="${!empty serviceGuide.imageList }">
				<tr>
					<th scope="row">등록된 이미지</th>
					<td colspan="3">
						<c:forEach var="image" items="${serviceGuide.imageList }" varStatus="status">
							&nbsp;${status.count}.&nbsp;&nbsp;<a href="#none" onclick="viewImage('${image.virtualPath}/${image.saveName }');">${image.orgName }</a> / ${image.description } 
							<br/>
						</c:forEach>
					</td>
				</tr>			
				</c:if>					
				<tr>
					<th scope="row">서비스 안내</th>
					<td colspan="3">
						<!-- 텍스트 형 활성화시 -->
				        <div id="textType" style="display:block;">
				        	<div class="tab_left1">텍스트 형</div>
				        	<div class="tab_right1"><a href="javascript:contentsType('image');">이미지 형</a></div>
				        	<div class="tab_con">
				            	<div style="padding:5px;"><textarea name="faqContents" rows="20" style="border:1px solid #ccc;width:98%">${serviceGuide.contents}</textarea></div>
				            </div>
				        </div>
				        <!-- 이미지 형 활성화시 -->
				        <div id="imageType" style="display:none;">
				        	<div class="tab_left2"><a href="javascript:contentsType('text');">텍스트 형</a></div>
				        	<div class="tab_right2">이미지 형</div>
				        	<div class="tab_con">
				            	<div id="fileContainer" style="width:100%">
				            		<div style="padding:3px;">
				            			<b>&nbsp;1.&nbsp;&nbsp;</b>
				            			<input type="file" name="image1" style="width:60%"/>
				            			<input type="text" name="description1" style="width:200px;"/> 
				            		</div>	
				            		<div style="padding:3px;">
				            			<b>&nbsp;2.&nbsp;&nbsp;</b>
				            			<input type="file" name="image2" style="width:60%;"/>
				            			<input type="text" name="description2" style="width:200px;"/>
				            		</div>
				            		<div style="padding:3px;">
				            			<b>&nbsp;3.&nbsp;&nbsp;</b>
				            			<input type="file" name="image3" style="width:60%;"/>
				            			<input type="text" name="description3" style="width:200px;"/>
				            		</div>
				            		<div style="padding:3px;">
				            			<b>&nbsp;4.&nbsp;&nbsp;</b>
				            			<input type="file" name="image4" style="width:60%;"/>
				            			<input type="text" name="description4" style="width:200px;"/>
				            		</div>
				            		<div style="padding:3px;">
				            			<b>&nbsp;5.&nbsp;&nbsp;</b>
				            			<input type="file" name="image5" style="width:60%;"/>
				            			<input type="text" name="description5" style="width:200px;"/>
				            		</div>
				            		<div style="padding:3px;">
				            			<b>&nbsp;6.&nbsp;&nbsp;</b>
				            			<input type="file" name="image6" style="width:60%;"/>
				            			<input type="text" name="description6" style="width:200px;"/>
				            		</div>
				            		<div style="padding:3px;">
				            			<b>&nbsp;7.&nbsp;&nbsp;</b>
				            			<input type="file" name="image7" style="width:60%;"/>
				            			<input type="text" name="description7" style="width:200px;"/>
				            		</div>
				            		<div style="padding:3px;">
				            			<b>&nbsp;8.&nbsp;&nbsp;</b>
				            			<input type="file" name="image8" style="width:60%;"/>
				            			<input type="text" name="description8" style="width:200px;"/>
				            		</div>
				            		<div style="padding:3px;">
				            			<b>&nbsp;9.&nbsp;&nbsp;</b>
				            			<input type="file" name="image9" style="width:60%;"/>
				            			<input type="text" name="description9" style="width:200px;"/>
				            		</div>
				            		<div style="padding:3px;">
				            			<b>10.&nbsp;&nbsp;</b>
				            			<input type="file" name="image10" style="width:60%;"/>
				            			<input type="text" name="description10" style="width:200px;"/>
				            		</div>				            						            		
				            	</div>
				            </div>
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