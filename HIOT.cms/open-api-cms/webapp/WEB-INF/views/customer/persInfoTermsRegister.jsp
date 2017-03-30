<%@ page contentType="text/html" trimDirectiveWhitespaces="true" pageEncoding="UTF-8"%>
<%@ taglib prefix="layout" 	tagdir="/WEB-INF/tags/layout" %>
<%@ taglib prefix="c"  		uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" 	uri="http://java.sun.com/jsp/jstl/fmt" %>

<layout:main>

	<jsp:attribute name="javascript">
		<script type="text/javascript">
			var checkFg = false;
			${script}
			
			$(document).ready(function(){
				
				$('#privacyAnnouncement').datepicker();   
				$('#privacyStart').datepicker(); 
				
				$("#btnList").click(function() {
					var form = document.forms["transForm"];
					form.submit();
				});
				
				$("#btnRegister").click(function() {
					var form = document.forms["registerForm"];			
					if(validate(form)) {
						if(checkFg){
							form.submit();
						}else{
							alert('버전 체크를 눌러주세요.');
						}
					}
				});
			});
			
			function checkVersion() {
				var verNum = $('#privacyVer').val();
				if(!verCheck(verNum)){
					alert("0.00.00 or 0.0.0 형태로 버전정보 입력이 가능합니다.");
					return false;
				}
				
				var cpCode = $('#cpCode option:selected').val();
				
				if($('#privacyVer').val() != ''){
					$.ajax({
						type : "GET",
					    cache: false,						
						url : "/customer/checkVerInfoFormData",
						dataType : "json",
						data : { "cpCode" : cpCode, "privacyVer" : $('#privacyVer').val()}
					})
					.done(function(result){
						if(result.code == 200) { 
							alert("해당 버전은 등록 가능합니다.");
							$('#privacyVer').attr("readonly", true);
							checkFg = true;
						}
						else if(result.code == 300) { alert('등록하려는 버전보다 높거나 같은 버전이 있습니다. \n확인하시고 다시 등록해 주세요.'); checkFg = false; }
						else{ alert("서버에서 오류가 발생하였습니다.\n다시 시도하여 주십시오."); checkFg = false; }
					})
					.fail(function( jqXHR, textStatus, errorThrown){
						console.log(jqXHR); 
						checkFg = false;
					});
				}else{
					alert('버전을 입력해주세요.');
				}				
			}
			
			function verCheck(str) {
				var chk_str = str;
				//정규식 : 0.00.00 or 0.0.0
				var pattern = /^(\d{1,1})([.]\d{1,2})([.]\d{1,2})$/;
				if(pattern.test(chk_str)){ return true;}
				else{ return false; }
			}
		</script>
	
	</jsp:attribute>
	
	<jsp:body>
		<form name="transForm" method="get" action="/customer/persInfoTermsList">
		<input type="hidden" name="page" value="${param.page }" />
		</form>
	
		<h2 class="pageTitle">개인정보 취급방침 등록</h2>
		<table class="tbl_1">
			<caption></caption>
			<colgroup>
				<col style="width:15%" />
				<col style="width:35%" />
				<col style="width:15%" />
				<col style="width:35%" />
			</colgroup>
			<tbody>
				<form id="registerForm" name="registerForm" method="post" action="/customer/persInfoTermsRegister">
				<input type="hidden" name="method" value="register" />
				<input type="hidden" name="writerId" value="${loginInfo.id}" />
				<input type="hidden" name="page" value="${param.page }" />		
				<tr>
					<th scope="row">앱명</th>
					<td>
						<select id="cpCode" name="cpCode" title="검색조건을 선택하세요">
							<option value="001">홈 캠</option>
							<option value="002">홈 피트니스</option>
							<option value="003">홈 매니저</option>
						</select>
					</td>
					<th scope="row">작성자</th>
					<td>${loginInfo.id}</td>
				</tr>
				<tr>
					<th scope="row"><label for="title">제목</label></th>
					<td colspan="3"><input type="text" id="title" name="title" class="w630" maxlength="20" require="true" msg="제목을 입력하셔야 합니다." size="20" maxlength="20" /></td>
				</tr>
				<tr>
					<th scope="row"><label for="title">공고일자</label></th>
					<td>
						<input type="text" id="privacyAnnouncement" name="privacyAnnouncement" value="${privacyAnnouncement}" class="ipt_tx" require="true" msg="공고일자를 입력하셔야 합니다." readonly="readonly"/>
					</td>
					<th scope="row"><label for="title">시행일자</label></th>
					<td>
						<input type="text" id="privacyStart" name="privacyStart" class="ipt_tx" value="${privacyStart}" require="true" msg="시행일자를 입력하셔야 합니다." readonly="readonly"/>
					</td>
				</tr>
				<tr>
					<th scope="row"><label for="title">버전등록</label></th>
					<td colspan="3">
						<input type="text" id="privacyVer" name="privacyVer" class="w230" require="true" msg="버전을 입력하셔야 합니다." />
						<button type="button" id="btnCheckVer" class="btn" onclick="checkVersion();"><span>버전체크</span></button><span style="color:red;'">&nbsp;&nbsp;버전 입력 예시) 1.00.00</span>
					</td>
				</tr>
				<tr>
					<th scope="row" colspan="4">
						<div><textarea id="privacyContents" name="privacyContents" require="true" msg="내용을 입력하셔야 합니다." rows="20" style="border:1px solid #ccc;width:98%"></textarea></div>
					</td>
				</tr>
				</form>
			</tbody>
		</table>
				
		<div class="btns clearfix">
			<div class="fll">
				<a id="btnList" class="btn2 btn2_black30" style="cursor:pointer"><span>목록</span></a>
			</div>
			<div class="flr">
				<a id="btnRegister" class="btn2 btn2_red30" style="cursor:pointer"><span>저장</span></a>
			</div>
		</div>			
		
	</jsp:body>
	
</layout:main>