<%@ page contentType="text/html" trimDirectiveWhitespaces="true" pageEncoding="UTF-8"%>
<%@ taglib prefix="layout" 	tagdir="/WEB-INF/tags/layout" %>
<%@ taglib prefix="c"  		uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" 	uri="http://java.sun.com/jsp/jstl/fmt" %>

<layout:main>

	<jsp:attribute name="javascript">
	
		<script type="text/javascript">
		
			${script}
			
			var openYN = "${notice.openYN}";
			var cpCode = "${notice.cpCode}";
			
			$(document).ready(function(){
				
				$("#open" + openYN).prop("checked", true);
				
				$("#btnList").click(function() {
					var form = document.forms["transForm"];
					form.submit();
				});
				
				$("#btnModify").click(function() {
					var form = document.forms["modifyForm"];
					
					if(validate(form)) {
						form.submit();
					}
				});
				
				$("#btnRemove").click(function() {
					
					if (confirm("삭제하시겠습니까?") == true){
						var pk = '${notice.pk}'; 
						$.ajax({
							type : "POST",
						    cache: false,						
							url : "/customer/removeGeneralNotice",
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
					}else{
						return;
					}
				});
			});
			
			function openPopup()
			{
				var width = "800";
				var height = "800";
				var leftPos = (screen.availWidth - width)/2;
				var topPos = (screen.availHeight - height)/2;
				var position = "width="+ width + ", height=" + height + ", left=" + leftPos + ", top=" + topPos;
				var url = "/preview?type=webview";
				url += "&pkCode=1404"
				url += "&pk=" + '${notice.pk}';
				url += "&cpCode=" + '${notice.cpCode}';
				
				window.open(url, "winOpen", position, "menubar=1");
			}			
		
		</script>
	
	</jsp:attribute>
	
	<jsp:body>
		<form name="transForm" method="get" action="/customer/generalNoticeList">
			<input type="hidden" name="page" value="${param.page }" />
			<input type="hidden" name="searchColumn" value="${param.searchColumn }" />
			<input type="hidden" name="searchString" value="${param.searchString }" />
		</form>
	
		<div class="boardTitle">
			<div class="borderTitle">
				<span class="pageTitle">일반공지</span>
			</div>
		</div>
		<table class="tbl_1">
			<caption></caption>
			<colgroup>
				<col style="width:15%" />
				<col style="width:*%" />
			</colgroup>
			<tbody>
				<form name="modifyForm" method="post" onsubmit="return false;" action="/customer/generalNoticeModify">
				<input type="hidden" name="method" value="modify" />
				<input type="hidden" name="pk" value="${notice.pk}" />
				<input type="hidden" name="modifierId" value="admin" />
				<input type="hidden" name="page" value="${param.page }" />
				<input type="hidden" name="searchColumn" value="${param.searchColumn }" />
				<input type="hidden" name="searchString" value="${param.searchString }" />				
				<tr>
					<th scope="row">앱명</th>
					<td>${notice.cpName }</td>
				</tr>
				<tr>
					<th scope="row"><label for="title">제목</label></th>
					<td><input type="text" id="title" name="title" class="w630" value="${notice.title }" require ="true" msg="제목을 입력하셔야 합니다." /></td>
				</tr>
				<tr>
					<th scope="row">작성자</th>
					<td>${notice.writerId }</td>
				</tr>
				<tr>
					<th scope="row">노출여부</th>
					<td>
						<input type="radio" id="openY" value="Y" name="openYN" /> 노출
						&nbsp;&nbsp;&nbsp;&nbsp;
						<input type="radio" id="openN" value="N" name="openYN" /> 비노출
					</td>
				</tr>
				<tr>
					<td colspan="2">
						<textarea name="contents" rows="30" style="border:1px solid #ccc;width:98%" require="true" msg="내용을 입력하셔야 합니다.">${notice.contents }
						</textarea>
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
				<a id="btnModify" class="btn2 btn2_red30" style="cursor:pointer"><span>수정</span></a>
			</div>
		</div>			
		
	</jsp:body>
	
</layout:main>