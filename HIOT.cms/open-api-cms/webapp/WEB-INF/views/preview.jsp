<%@ page contentType="text/html" trimDirectiveWhitespaces="true"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="layout" tagdir="/WEB-INF/tags/layout"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<head>
<meta charset="UTF-8">
<title>미리보기</title>
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<link rel="stylesheet" href="/resources/css/cms.css" />
<link rel="stylesheet" href="/resources/css/plugins/dark-hive/jquery-ui-1.9.2.custom.css" />
<link rel="stylesheet" href="/resources/css/plugins/datepicker.css" />
<script type="text/javascript" src="/resources/js/jquery-1.9.1.min.js"></script>
<script type="text/javascript" src="/resources/js/jquery-ui-1.9.2.custom.js"></script>
<script type="text/javascript" src="/resources/js/plugins/ui/jquery.ui.datepicker.js"></script>
<script type="text/javascript" src="/resources/js/plugins/ui/i18n/jquery.ui.datepicker-ko.js"></script>
<script type="text/javascript" src="/resources/js/commonLibrary.js"></script>
<script type="text/javascript" src="/resources/js/validate.js"></script>
<style>
	#box{ 
		width:100%; 
		height: 100%; 
		text-align: center;
    	vertical-align: middle;
    	margin-top:5px;
    	
		
		}
		
	#screen {
		
	/*
	    zoom: 60%;
	    -moz-transform: scale(0.6);
	    -moz-transform-origin: 0 0;
	    -o-transform: scale(0.6);
	    -o-transform-origin: 0 0;
	    */
	    /*
	    -ms-transform: scale(0.6);
	    -moz-transform: scale(0.6);
	    -o-transform: scale(0.6);
	    -webkit-transform: scale(0.6);
	    transform: scale(0.6);
	
	    -ms-transform-origin: 0 0;
	    -moz-transform-origin: 0 0;
	    -o-transform-origin: 0 0;
	    -webkit-transform-origin: 0 0;
	    transform-origin: 0 0;
	    */
	    /*
	    zoom: 0.6;
		-moz-transform: scale(0.6);
		-moz-transform-origin: 0 0;
		-o-transform: scale(0.6);
		-o-transform-origin: 0 0;
		-webkit-transform: scale(0.6);
		-webkit-transform-origin: 0 0
	    */
	    
	}

</style>
<script type="text/javascript">


	$(document).ready(function() {
		
		var isChrome = window.chrome;
		if(isChrome) {
			zoom_crom_ifram(1);
		} else { 
			$('#screen').css("zoom",0.6);
		}
		
		$("#fixSize").click(function() {
			menuAction('fixSize');
			
			var size = $('#sfixSize').val();
			var strArray = size.split('x');
			$('#screen').attr("width", strArray[0]);
			$('#screen').attr("height", strArray[1]);
			//$('#screen').width(strArray[0]);
			//$('#screen').height(strArray[1]);
			
		});

		$("#editSize").click(function() {
			menuAction('editSize');
		});

		$("#btnPreview").click(function() {
			var editWidth = $('#editWidth').val();
			var editHeight = $('#editHeight').val();

			$('#screen').attr("width", editWidth);
			$('#screen').attr("height", editHeight);
			//$('#screen').width(editWidth);
			//$('#screen').height(editHeight);
		});
		
		
		$("#sfixSize").change(function() {
			var size = $('#sfixSize').val();
			var strArray = size.split('x');
			$('#screen').attr("width", strArray[0]);
			$('#screen').attr("height", strArray[1]);
			//$('#screen').width(strArray[0]);
			//$('#screen').height(strArray[1]);
		});
		
		$('#imgList').change(function(){
			var url = $('#imgList').val();
			$('#screen').attr('src', url);
		});
		
		$('#editWidth, #editHeight').keydown(function(e) {
		    var k = e.keyCode;
		     console.log(k);
		    if ((90 >= k && k >= 65) // a ~ z
		            || (111 >= k && k >= 106) // keypad operator
		            || (192 >= k && k >= 186) // -,=./;
		            || (222 >= k && k >= 219) // ']\[
		            || k == 32 // space bar
		            || k == 59 // FF ;
		            || k == 61 // FF =
		            || k == 173 // FF -
		            ) { 
		        e.preventDefault();
		    }
		});
		
		$("#zoomScreen").change(function() {
			var level = $('#zoomScreen').val();
			
			var isChrome = window.chrome;
			if(isChrome) {
				zoom_crom_ifram(level);
			} else { 
				zoom_ifram(level);
			}
		
			
			
		});

	});
	
	function zoom_ifram(level)
	{
		var value = 0.4 + (level*0.2);
		$('#screen').css({
			zoom: value
        });
	}
	
	function zoom_crom_ifram(level)
	{
		var value = 0.4 + (level*0.2);
		$('#screen').css({
			'webkit-transform': 'scale('+value+')', 
	    	'-webkit-transform-origin': '0 0'
        });
	}

	
	
	function menuAction(action) {
		if (action == 'fixSize') {
			$('#editWidth').attr("style",
					"background-color: #e2e2e2;width:100px;")
			$('#editHeight').attr("style",
					"background-color: #e2e2e2;width:100px;")

			$('#editWidth').attr("disabled", true);
			$('#editHeight').attr("disabled", true);
			$('#sfixSize').attr("disabled", false);

			$('#btnPreview').attr("disabled", true);

		} else {
			$('#editWidth').attr("style",
					"background-color: #ffffff;width:100px;")
			$('#editHeight').attr("style",
					"background-color: #ffffff;width:100px;")

			$('#editWidth').attr("disabled", false);
			$('#editHeight').attr("disabled", false);
			$('#sfixSize').attr("disabled", true);

			$('#btnPreview').attr("disabled", false);
		}
	}
</script>
</head>

<body>
 
	<div>
		<input type="radio" id="fixSize" name="fixSize" value="1"
			checked="checked"> <b>사이즈&nbsp;</b> <select id="sfixSize"
			name="sfixSize" style="width: 140px;">
			<!-- <option value="0x0">== 선택하세요 ==</option> -->
			<option value="480x800">hdpi 480x800</option>
			<option value="720x1280">xhdpi 720x1280</option>
			<option value="1080x1920">Xxhdpi 1080x1920</option>

		</select> &nbsp; <input type="radio" id="editSize" name="fixSize" value="0" />
		<b>직접입력&nbsp;</b> <input type="text" id="editWidth" name="editWidth"
			style="width: 80px" disabled="true" /> <b>x</b>
		<input type="text" id="editHeight" name="editHeight"
			style="width: 80px" disabled="true"  />
		

		<button type="button" id="btnPreview" class="btn">
			<span>확인</span>
		</button>
		
		&nbsp; 확대:
		<select id="zoomScreen"
			name="zoomScreen" style="width: 55px;">
			<option value="0">40%</option>
			<option value="1" selected="selected">60%</option>
			<option value="2">80%</option>
			<option value="3">100%</option>
		</select>
		
		<c:if test="${preview.type == 'image'}">
		&nbsp; 이미지 선택:
			<select id="imgList" name="imgList"  title="이미지를 선택하세요">
				<c:forEach var="image" items="${preview.imgList}" varStatus="status" >
					<option value="${image.path}">
						${image.name}
					</option>
				</c:forEach>
			</select>
		</c:if>
	</div>


	<div id="box" name="box"  border: 1px width="480" height="800">
		<iframe id="screen" name="screen" width="480" height="800" src="${preview.url}" scrolling="no"> </iframe>
	</div>
</body>