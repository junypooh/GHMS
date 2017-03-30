<%@ page contentType="text/html" trimDirectiveWhitespaces="true" pageEncoding="UTF-8"%>
<%@ taglib prefix="layout" 	tagdir="/WEB-INF/tags/layout" %>
<%@ taglib prefix="c"  		uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" 	uri="http://java.sun.com/jsp/jstl/fmt" %>

<layout:main>

	<jsp:attribute name="javascript">
		<script type="text/javascript">
			${script}
			$(document).ready(function(){
				
				$('#privacyAnnouncement').datepicker();   
				$('#privacyStart').datepicker(); 
				
				$("#btnList").click(function() {
					var form = document.forms["transForm"];
					form.submit();
				});
				
				$("#btnRemove").click(function() {
					
					if(!confirm('삭제 하시겠습니까?')) return false;
					
					var pk = '${privacyMap.pk}';
					$.ajax({
						type : "POST",
					    cache: false,						
						url : "/customer/removePersInfoTerms",
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
						form.submit();
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
				url += "&pkCode=1414"
				url += "&pk=" + '${privacyMap.pk}';
				url += "&cpCode=" + '${privacyMap.cpCode}';
				
				window.open(url, "winOpen", position, "menubar=1");
			}
			
			
		</script>
	
	</jsp:attribute>
	
	<jsp:body>
		<form name="transForm" method="get" action="/customer/persInfoTermsList">
		<input type="hidden" name="page" value="${param.page }" />
		<input type="hidden" name="cpCode" value="${param.cpCode }" />
		</form>
	
		<h2 class="pageTitle">개인정보 취급방침 수정</h2>
		<table class="tbl_1">
			<caption></caption>
			<colgroup>
				<col style="width:15%" />
				<col style="width:35%" />
				<col style="width:15%" />
				<col style="width:35%" />
			</colgroup>
			<tbody>
				<form id="modifyForm" name="modifyForm" method="post" action="/customer/persInfoTermsModify">
				<input type="hidden" name="method" value="modify" />
				<input type="hidden" name="pk" value="${privacyMap.pk}" />
				<input type="hidden" name="page" value="${param.page }" />		
				<input type="hidden" name="cpCode" value="${param.cpCode }" />
				<input type= "hidden" name="modifyId" value="${loginfo.id}" />
				<tr>
					<th scope="row">상품(앱)명</th>
					<td>${privacyMap.cpName}</td>
					<th scope="row">작성자</th>
					<td>${privacyMap.writerId}</td>
				</tr>
				<tr>
					<th scope="row"><label for="title">제목</label></th>
					<td colspan="3"><input type="text" id="title" name="title" value="${privacyMap.title}" class="w630" maxlength="20" require="true" msg="제목을 입력하셔야 합니다." /></td>
				</tr>
				<tr>
					<th scope="row"><label for="title">공고일자</label></th>
					<td>
						<input type="text" id="privacyAnnouncement" name="privacyAnnouncement" value="${privacyMap.privacyAnnouncement}" class="ipt_tx" require="true" msg="공고일자를 입력하셔야 합니다." readonly="readonly" />
					</td>
					<th scope="row"><label for="title">시행일자</label></th>
					<td>
						<input type="text" id="privacyStart" name="privacyStart" class="ipt_tx" value="${privacyMap.privacyStart}" require="true" msg="시행일자를 입력하셔야 합니다." readonly="readonly" />
					</td>
				</tr>
				<tr>
					<th scope="row"><label for="title">버전정보</label></th>
					<td colspan="3">${privacyMap.privacyVer}</td>
				</tr>
				<tr>
					<th scope="row" colspan="4">
						<div><textarea id="privacyContents" name="privacyContents" require= "true" msg="내용을 입력하셔야 합니다." rows="20" style="border:1px solid #ccc;width:98%">${privacyMap.privacyContents}</textarea></div>
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