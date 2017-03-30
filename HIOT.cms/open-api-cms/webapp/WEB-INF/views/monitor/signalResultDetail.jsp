<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"  		uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" 	uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" 		uri="http://java.sun.com/jsp/jstl/functions"%>

<!doctype html>
<html lang="ko">
	<head>
		<meta charset="UTF-8">
		<title>올레 홈캠 카메라 신호상태</title>
		<meta http-equiv="X-UA-Compatible" content="IE=edge" />
		<link rel="stylesheet" href="/resources/css/cms.css" />
		<script type="text/javascript" src="/resources/js/jquery-1.9.1.min.js"></script>
		<script type="text/javascript" src="/resources/js/jquery-ui-1.9.2.custom.js"></script>
		<script type="text/javascript" src="/resources/js/commonLibrary.js"></script>
		<script type="text/javascript" src="/resources/js/validate.js"></script>   
		<script type="text/javascript">
			$(document).ready(function(){
			});
		</script>	
			
	</head>
	<body>
		<div style="padding:20px;">
			<table class="tbl_1">
				<caption>올레 홈캠 카메라 로그 상세</caption>
				<colgroup>
					<col style="width:25%" />
					<col style="width:25%" />
					<col style="width:25%" />
					<col style="width:25%" />
				</colgroup>
				<tbody>
				<!-- DYNAMIC AREA 'list' -->
				
					<tr>
						<th scope="col">카메라ID</th>
						<td>더미데이터</td>
						<th scope="col">모델명</th>
						<td>더미데이터</td>
					</tr>
					<tr>
						<th scope="col">카메라 MAC</th>
						<td>12:34:56:78</td>
						<th scope="col">단말버전</th>
						<td>1.1.1</td>
					</tr>
					<tr>
						<th scope="col">조회시간</th>
						<td>20141124 14:20</td>
						<th scope="col">연결 SSID</th>
						<td>kt_WLAN_A12B</td>
					</tr>
					<tr>
						<th scope="col">신호세기</th>
						<td>강함</td>
						<th scope="col">RSSI</th>
						<td>12</td>
					</tr>
					<tr>
						<th scope="col">인접채널 수</th>
						<td>23</td>
						<th scope="col">연장채널 신호세기</th>
						<td>13</td>
					</tr>
					<tr>
						<th scope="col">동일채널 수</th>
						<td>21</td>
						<th scope="col">동일채널 신호세기</th>
						<td>31</td>
					</tr>
				
				<!-- DYNAMIC AREA 'list' -->
				</tbody>
			</table>
			
			<div class="btns clearfix">
				<div class="fll" style=" padding-left:535px;">
					<a id="btnList" class="btn2 btn2_black30" style="cursor:pointer" onclick="javascript:window.close();"><span>닫기</span></a>
				</div>
			</div>	
		</div>
	</body>
</html>