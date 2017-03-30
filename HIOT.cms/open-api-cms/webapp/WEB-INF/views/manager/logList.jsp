<%@ page contentType="text/html" trimDirectiveWhitespaces="true" pageEncoding="UTF-8"%>
<%@ taglib prefix="layout" 	tagdir="/WEB-INF/tags/layout" %>
<%@ taglib prefix="c"  		uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" 	uri="http://java.sun.com/jsp/jstl/fmt" %>

<layout:main>

	<jsp:attribute name="javascript">
	
		<script type="text/javascript">
			var searchColumn = '${param.searchColumn}';
			var search_cms_main_menu = '${param.search_cms_main_menu}';
			var search_cms_sub_menu = '${param.search_cms_sub_menu}';
			
			$(document).ready
			(
				function()
				{
					$('#search_cms_main_menu').val(search_cms_main_menu);
					$('#search_cms_sub_menu').val(search_cms_sub_menu);
					if(searchColumn != '') $('#searchColumn').val(searchColumn);
						$('#search_start, #search_finish').datepicker();
						
						$('#search_cms_main_menu').change(initSubMenu);
					$('#btnSearch').click(search); 
				}
			);
			
			function initSubMenu()
			{
				$('#search_cms_sub_menu option').each
				(
					function()
					{
						if($(this).val() != '')
						{
							$(this).remove();
						}
					}
				);
				
				var fd_up_cms_menu = $('#search_cms_main_menu').val();
				if(fd_up_cms_menu != '')
				{				
					$.ajax({
						type : "POST",
					    cache: false,						
						url : "/manager/initSubMenuList",
						dataType : "json",
						data : { 
								"fd_up_cms_menu" : fd_up_cms_menu
						}
					})
					.done(function(result){
						if(result.code != 200) {
							alert("서버에서 오류가 발생하였습니다.\n다시 시도하여 주십시오.");
						} else {
							var cnt = result.list.length;	
							for(var i=0; i<cnt; i++){
								var menu	= result.list[i].menu;
       							var name	= result.list[i].name;
       							$("#search_cms_sub_menu").append("<option value='"+menu+"'>"+name+"</option>");
       						}
						}
					})
					.fail(function( jqXHR, textStatus, errorThrown){
						console.log(jqXHR);
					});	
				}
			}
			
			function search()
			{
				var form = document.searchForm;
				form.action = location.pathname;
				form.submit();
			}
			
			function view(pk_cms_log)
			{
				var form = document.searchForm;
				form.pk_cms_log.value = pk_cms_log;
				form.action = '/manager/logView';
				form.submit();
			}		
		</script>	
		
	</jsp:attribute>

	<jsp:body>
	
		<div class="boardTitle">
			<div class="borderTitle">
				<span class="pageTitle">CMS 사용 로그 관리</span>
			</div>
		</div>
		<div class="boardTop">	
			<table>
			<form name="searchForm" method="get">
			<input type="hidden" id="pk_cms_log" name="pk_cms_log" />
			<input type="hidden" id="page" name="page" value="${param.page }" />
				<colgroup>
					<col style="width:150px" />
					<col style="width:270px" />
					<col style="width:100px" />
					<col style="width:*%" />
				</colgroup>
				
				<tr>
					<td style="font-weight: bold; text-align: right; padding-right:10px;">메인/서브 메뉴 </td>
					<td colspan="2" style="font-weight: bold; text-align: left; padding-bottom:5px;">
						<select id="search_cms_main_menu" name="search_cms_main_menu" style="width:120px">
							<option value="">전체</option>					
						<c:forEach var="mainMenu" items="${mainMenuList }"> 
							<option value="${mainMenu.menu }">${mainMenu.name }</option>
						</c:forEach>						
						</select>
						<select id="search_cms_sub_menu" name="search_cms_sub_menu" style="width:230px">
							<option value="">전체</option>				
						<c:forEach var="subMenu" items="${subMenuList }">
							<option value="${subMenu.menu }">${subMenu.name }</option>
						</c:forEach>				
						</select>	
					</td>
					<td rowspan="3" align="left">
						<button type="button" id="btnSearch" class="btn" style="height:55px; width:80px;"><span><b>검 색</b></span></button>
					</td>
				</tr>
				<tr>
					<td style="font-weight: bold; text-align: right; padding-right:10px;">검색 대상 </td>
					<td style="font-weight: bold; text-align: left; padding-bottom:5px;">
						<select id="searchColumn" name="searchColumn" style="width:120px;">
							<option value="fd_admin_id">아이디</option>
							<option value="fd_class_name">클래스 명</option>									
							<option value="fd_method_name">메서드 명</option>										
						</select>		
						<input type="text" id="searchString" name="searchString" style="width:125px" value="${param.searchString }">
					</td>
				</tr>
				<tr>
					<td style="font-weight: bold; text-align: right; padding-right:10px;">검색 날짜 </td>
					<td style="font-weight: bold; text-align: left; padding-bottom:5px;">
						<input type="text" id="search_start" name="search_start" value="${param.search_start }" readonly="readonly" style="width:110px" />
						<b>~</b> 
						<input type="text" id="search_finish" name="search_finish" value="${param.search_finish }" readonly="readonly" style="width:110px" />
					</td>
				</tr>
			</form>
			</table>
		</div>
		
		<table class="board mt10">
			<caption>CMS 사용 로그 관리</caption>
			<colgroup>
				<col style="width:10%" />
				<col style="width:20%" />
				<col style="width:*%" />
				<col style="width:10%" />
				<col style="width:10%" />
			</colgroup>
			<thead>
				<tr>
					<th scope="col">번호</th>
					<th scope="col">메인 메뉴<br>서브 메뉴</th>
					<th scope="col">클래스 명<br>메서드 명</th>
					<th scope="col">관리자 ID<br>접속 IP</th>
					<th scope="col">로그일시</th>
				</tr>
			</thead>
			
			<tbody>
			<c:if test="${!empty logList }">
				<c:forEach var="log" items="${logList }" varStatus="status">
				<tr onmouseover="this.style.backgroundColor='#f7f7f7'" onmouseout="this.style.backgroundColor=''" bgcolor="white">
					<td><fmt:formatNumber value="${pageNavi.totalCount - ((pageNavi.nowPage - 1) * pageNavi.pageSize + status.index) }" pattern="#,###"/></td>
					<td style="text-align:left;">
						<a href="#none" onfocus="this.blur()" onclick="view(${log.pk_cms_log });">${log.fd_cms_main_menu_name }</a><br>
						<a href="#none" onfocus="this.blur()" onclick="view(${log.pk_cms_log });">${log.fd_cms_sub_menu_name }</a>
					</td>	
					<td style="text-align:left;">
						<a href="#none" onfocus="this.blur()" onclick="view(${log.pk_cms_log });">${log.fd_class_name }</a><br>
						<a href="#none" onfocus="this.blur()" onclick="view(${log.pk_cms_log });">${log.fd_method_name }</a>
					</td>		
					<td>
						<a href="#none" onfocus="this.blur()" onclick="view(${log.pk_cms_log });">${log.fd_admin_id }</a><br>
						<a href="#none" onfocus="this.blur()" onclick="view(${log.pk_cms_log });">${log.fd_access_ip }</a>
					</td>		
					<td>
						<a href="#none" onfocus="this.blur()" onclick="view(${log.pk_cms_log });">
							<fmt:parseDate value="${log.fd_reg_date}" var="dateFmt" pattern="yyyyMMddHHmmss"/>
							<fmt:formatDate value="${dateFmt}" pattern="yyyy-MM-dd HH:mm:ss"/>
						</a>
					</td>		
				</tr>
				</c:forEach>
			</c:if>
			<c:if test="${empty logList }">
				<tr>
					<td colspan="8">검색된 데이타가 없습니다</td>
				</tr>
			</c:if>
			</tbody>
		</table>
		
		${pageNavi.navi}
		<!-- 
		<div class="btns clearfix">
			<div class="fll">
				<a id="btnRemove" class="btn2 btn2_black30" style="cursor:pointer" href= "#"><span>삭제</span></a>
			</div>
			<div class="flr">
				<a id="btnWrite" class="btn2 btn2_red30" style="cursor:pointer"><span>등록</span></a>
			</div>
		</div>	
		 -->
	</jsp:body>
</layout:main>