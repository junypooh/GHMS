<%@ page contentType="text/html" trimDirectiveWhitespaces="true" pageEncoding="UTF-8"%>
<%@ taglib prefix="layout" 	tagdir="/WEB-INF/tags/layout" %>
<%@ taglib prefix="c"  		uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" 	uri="http://java.sun.com/jsp/jstl/fmt" %>

<layout:main>

	<jsp:attribute name="javascript">
		<script type="text/javascript">
			$(document).ready(function(){
				$('#btnList').click(function(){ 
					var form = document.transForm;
					form.action = '/manager/logList';
					form.submit();
				});
			});		
		</script>	
	</jsp:attribute>

	<jsp:body>
	
		<div class="boardTitle">
			<div class="borderTitle">
				<span class="pageTitle">CMS 사용 로그 관리 상세</span>
			</div>
		</div>
		<table class="tbl_1">
			<form id="transForm" name="transForm" method="post">
			<input type="hidden" id="page" name="page" value="${param.page }" />			
			<input type="hidden" id="search_cms_main_menu" name="search_cms_main_menu" value="${param.search_cms_main_menu }" />
			<input type="hidden" id="search_cms_sub_menu" name="search_cms_sub_menu" value="${param.search_cms_sub_menu }" />
			<input type="hidden" id="searchColumn" name="searchColumn" value="${param.searchColumn }" />
			<input type="hidden" id="searchString" name="searchString" value="${param.searchString }" />
			<input type="hidden" id="search_start" name="search_start" value="${param.search_start }" />
			<input type="hidden" id="search_finish" name="search_finish" value="${param.search_finish }" />					
			</form>	
			<caption>CMS 사용 로그 관리 상세</caption>
			<colgroup>
				<col style="width:130px" />
				<col style="width:*%" />
			</colgroup>
			
			<tbody>
				<tr>
					<th scope="row">아이디 / 접속 IP</th>
					<td>${log.fd_admin_id } / ${log.fd_access_ip }</td>
				</tr>
				<tr>
					<th scope="row">접속 시간</th>
					<td>
						<fmt:parseDate value="${log.fd_reg_date}" var="dateFmt" pattern="yyyyMMddHHmmss"/>
						<fmt:formatDate value="${dateFmt}" pattern="yyyy-MM-dd HH:mm:ss"/>	
					</td>
				</tr>
				<tr>
					<th scope="row">메뉴</th>
					<td>${log.fd_cms_main_menu_name } > ${log.fd_cms_sub_menu_name }</td>
				</tr>
				<tr>
					<th scope="row">클래스 명</th>
					<td>${log.fd_class_name }</td>
				</tr>
				<tr>
					<th scope="row">메서드 명</th>
					<td>${log.fd_method_name }</td>
				</tr>
				<tr>
					<th scope="row">파라메터</th>
					<td>
						<c:if test="${!empty logParamList}">
							<table class="tbl_1">
								<caption></caption>
								<colgroup>
									<col style="width:150px" />
									<col style="width:*%" />
								</colgroup>
								<thead>
									<tr>
										<th scope="col">파라메타 이름</th>
										<th scope="col">파라메타 값</th>
									</tr>
								</thead>
							<c:forEach var="logParam" items="${logParamList }">
								<tr>
									<td style="border-left:none;">${logParam.fd_param_name } <c:if test="${logParam.fd_param_type == 2 }"><br />[배열]</c:if></td>
									<td>${logParam.fd_param_value }</td>
								</tr>
							</c:forEach>
							</table>
						</c:if>	
					</td>
				</tr>
			</tbody>
		</table>
		
		 
		<div class="btns clearfix">
			<div class="fll">
				<a id="btnList" class="btn2 btn2_black30" style="cursor:pointer"><span>목록</span></a>
			</div>
		</div>	
		
	</jsp:body>
</layout:main>