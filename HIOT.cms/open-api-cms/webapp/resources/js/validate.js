//유효성 체크모듈 집인점 함수로 폼객첵 멤버들의 속성들을 확인하여 그에 맞는 함수실행
function validate(form) {
    var i, element, items, item, result;

    items = new Array();

    for (i = 0; i < form.elements.length; i++) {
        element = form.elements[i];
        if (element.getAttribute("maxbytes") != null) items.push("maxbytes");
        if (element.getAttribute("minbytes") != null) items.push("minbytes");
        if (element.getAttribute("minlength") != null) items.push("minlength");
        if (element.getAttribute("ispattern") != null) items.push("ispattern");
        if (element.getAttribute("require") != null && element.getAttribute("ispattern") == null) items.push("require");
        if (element.getAttribute("match") != null) items.push("match");

        while (items.length > 0) {
            item = items.pop();
            eval("result = " + item + "_chk(element,form,i);");
            if (!result) return false;
        }
    }

    return true;
}

//필수적으로 체크해야하는 멤버들의 타입을 구분하여 해당 유효성 검증 함수실행
function require_chk(element, form) {
    var result;

    if (element.getAttribute("require").toString().toLowerCase() == "true") {
        switch (element.type) {
            case "radio":
            case "checkbox":
                result = check_check2radio(element, form);
                break;
            case "select-one":
            case "select-multiple":
                result = check_select(element)
                break;
            default:
                result = check_text(element);
                break;
        }
    }
    else {
        result = false;
    }

    return result;
}

//2개의 항목값을 비교
function match_chk(element, form) {
    var next_element = form.elements[element.getAttribute("match")];

    if (element.value.trim() != next_element.value.trim()) {
        alert(getmsg(element, "match") + " 항목과 " + getmsg(next_element, "match") + "이 일치하지 않습니다.");
        next_element.focus();
        return false;
    }

    return true;
}

//현재 멤버의 값이 최대 바이트수를 넘겼는지 체크
function maxbytes_chk(element) {
    var maxbytes, nowbytes;

    maxbytes = element.getAttribute("maxbytes");
    nowbytes = (element.value).bytes();

    if (maxbytes >= nowbytes) return true;

    alert(getmsg(element, "maxbytes"));
    element.focus();
    return false;
}

//현재 멤버의 값이 최소 바이트수 이상인지 체크
function minbytes_chk(element) {
    var minbytes, nowbytes;

    minbytes = element.getAttribute("minbytes");
    nowbytes = (element.value).bytes();

    if (minbytes <= nowbytes) return true;

    alert(getmsg(element, "minbytes"));
    element.focus();
    return false;
}

//현재 멤버의 값이 최소 길이 이상인지 체크
function minlength_chk(element) {
    var minlength = element.getAttribute("minlength");

    if (minlength <= element.value.length) return true;

    alert(getmsg(element, "minlength"));
    element.focus();
    return false;
}

//패턴유형별로 형식이 맞는지 체크
function ispattern_chk(element, form, i) {
    var result, regx, value, original_value;

    value = element.value.trim();

    if (element.getAttribute("span") != null) {
        var _value = new Array();
        for (span = 0; span < element.getAttribute("span"); span++) {
            _value[span] = form.elements[i + span].value;
        }

        value = _value.join(element.getAttribute("glue") == null ? "" : element.getAttribute("glue"));
    }

    if (element.getAttribute("require") == null && value.length == 0) return true;

    switch (element.getAttribute("ispattern")) {
        case "email":
            result = chk_mail(value);
            break;
        case "jumin":
            result = chk_rsno(value);
            break;
        case "business":
            result = chk_vend(value);
            break;
        case "image":
            result = chk_imgfile(value);
            break;
        default:
            result = value.ispattern(element.getAttribute("ispattern"));
            break
    }
    

    if (result) return true;

    alert(getmsg(element, "ispattern"));
    element.focus();
    return false;
}

//체크박스 또는 라디오버튼의 멤버중 하나가 선택되었는지 검증
function check_check2radio(element_member, form) {
    var element;

    eval("element = form." + element_member.name + ";");
    if (isNaN(element.length)) {
        if (element.checked) return true;
    }
    else {
        for (i = 0; i < element.length; i++) {
            if (element[i].checked) return true;
        }
    }

    alert(getmsg(element_member, "require"));
    element_member.focus();
    return false;
}

//셀렉트박스의 멤버중 값이 있는 멤버가 선택되었는지 검증
function check_select(element) {
    for (i = 0; i < element.length; i++) {
        if (element.options[i].selected && element.options[i].value != "") return true;
    }

    alert(getmsg(element, "require"));
    element.focus();
    return false;
}

//입력박스의 값이 입력되었는지 체크
function check_text(element) {
    if (element.value.length < 1) {
        alert(getmsg(element, "require"));
        element.focus();
        return false;
    }
    return true;
}

//유효성 검증 형태별 경고 메시지를 리턴
function getmsg(element, type) {
    var msg, typename = element.type;

    if (element.getAttribute("msg") != null) {
        msg = element.getAttribute("msg");
    }
    else {
        msg = element.getAttribute("hname") ? element.getAttribute("hname") : element.name;
        switch (type) {
            case "require":
                msg += (typename == "text" || typename == "textarea" || typename == "file" || typename == "password") ? " 항목을 입력해 주십시오." : " 항목을 선택해 주십시오.";
                break;
            case "maxbytes":
                msg += " 항목의 길이가 너무 초과되었습니다. (최대 " + element.getAttribute(type) + " 바이트)";
                break;
            case "minbytes":
                msg += " 항목의 길이가 최소 " + element.getAttribute(type) + " 바이트 이상 입력해야 합니다.";
                break;
            case "minlength":
                msg += " 항목의 길이가 너무 짧습니다.";
                break;
            case "ispattern":
                msg += " 항목의 형식이 잘못되었습니다.";
                break;
        }
    }

    return msg;
}

//'****************************************************************************************
//'* Description: 배열 랜덤 프로토타입 정의
//'****************************************************************************************            
Array.prototype.shuffle = function(){ 
	return this.concat().sort( 
		function(){return Math.random() - Math.random();} 
	); 
} 

//'****************************************************************************************
//'* Description: input 숫자만 허용
//'****************************************************************************************            
//function onlyNumber(el){
//	el.value = el.value.replace(/\D/g,'');
//	el.blur();
//	el.focus();
//}
function onlyNumber(el, msg){
	el.value = el.value.replace(/\D/g,'');
	el.blur();
	el.focus();

	arguNumb=arguments.length;
	if (arguNumb == 2){
		alert( msg );
	}
}

//'****************************************************************************************
//'* Description: 천단위 콤마 포멧
//'****************************************************************************************            
function number_format_value(get_number)
{
	get_number += '';
	x = get_number.split('.');
	x1 = x[0];
	x2 = x.length > 1 ? '.' + x[1] : '';
	var rgx = /(\d+)(\d{3})/;
	while (rgx.test(x1)) {
		x1 = x1.replace(rgx, '$1' + ',' + '$2');
	}
	return x1 + x2;
}
function number_format(el)
{
	var pattern = /[^.0-9-]/g;
		el.value = el.value.replace(pattern,'');
	
		el.value += '';
		x = el.value.split('.');
		x1 = x[0];
		x2 = x.length > 1 ? '.' + x[1] : '';
		var rgx = /(\d+)(\d{3})/;
		while (rgx.test(x1)) {
			x1 = x1.replace(rgx, '$1' + ',' + '$2');
		}
		el.value = x1 + x2;
}

function del_comma(number)
{
	return number.replace(/,/gi,'');
}


//'****************************************************************************************
//'* Description: 공백제거 프로토타입 정의
//'****************************************************************************************            
String.prototype.trim = function()
{
	return this.replace(/(^[ \f\n\r\t]*)|([ \f\n\r\t]*$)/g, "");
}
