//확장자 체크함수 
function chk_imgfile(sFile) 
{
    var chkstr = ".gif .jpg .jpeg .png";
    var sExt = sFile.match(/\.[^\.]*$/);
    sExt = sExt ? sExt[0].toLowerCase() : ".";
    if (chkstr.indexOf(sExt) < 0) 
    {
        return false;
    }
    else 
    {
        return true;
    }
}

//메일체크함수
function chk_mail(ObjMail) 
{
    ObjMail = ObjMail.trim();
    if (ObjMail.search(/^((\w|[\-\.])+)@((\w|[\-\.])+)\.([A-Za-z]+)$/) == -1) 
    {
        return false;
    }
    return true;
}

//이벤트 응모자 리스트 엑셀 다운로드
function getEventEntryList(pk_event) 
{
	if($("#eventEntryContainer").length == 0)
	{
		$("body").append("<iframe id='eventEntryContainer' name='eventEntryContainer' style='display:none;'></iframe>");
	}
	
	var iframe = document.eventEntryContainer;
	
	if($("#eventEntryContainer").length > 0)
	{
		iframe.document.write("");
		iframe.document.close();
	}
	
	var formStr  = "<form name='eventEntryForm' method='post' action='/event/eventEntryList.do'>";
	formStr		+= "<input type='hidden' name='pk_event' value='" + pk_event + "'>";
	formStr 	+= "</form>";
	
	iframe.document.write(formStr);
	iframe.document.close();
	
	var form = iframe.document.eventEntryForm;
	form.submit();
}


function onlyNumber() 
{
    if ((event.keyCode < 48) || (event.keyCode > 57)) 
    {
        if (!(event.keyCode == 45 || event.keyCode == 46)) 
        {
            event.returnValue = false;
        }
    }
}

function dateFormat(datebox) 
{
    var value = datebox.value.trim();

    if (value.length == 8) 
    {
        datebox.value = value.substring(0, 4) + "-" + value.substring(4, 6) + "-" + value.substring(6, 8);
    }
}

function cancelEnter() 
{
    if (event.keyCode == 13) 
    {
        return false;
    }
}



//문자열의 좌우공백 제거
function _string_trim() 
{
  return this.replace(/(^\s*)|(\s*$)/gi, "");
}

//문자열의 왼쪽부터 지정한 수만큼의 문자를 반환
function _string_left(num) 
{
  return this.substring(0, num);
}

//문자열의 오른쪽부터 정해진 수 만큼의 문자를 반환
function _string_right(num) 
{
  return this.substring((this.length - num), this.length);
}

//문자열의 바이트수를 반환
function _string_bytes() 
{
    var str = this;
    var l = 0;
    for (var i=0; i<str.length; i++)
    {
    	l += (str.charCodeAt(i) > 128) ? 2 : 1;
    }
    return l;
}

//바이트 수 만큼 문자열을 자름
function _string_cutbytes(len) 
{
    var str = this;
    var l = 0;
    for (var i = 0; i < str.length; i++) 
    {
        l += (str.charCodeAt(i) > 128) ? 2 : 1;
        if (l > len) return str.substring(0,i);
    }
    return str;
}

//거꾸로된 문자열을 반환
function _string_reverse() 
{
  var temp_str = "";

  for (i = 1; i <= this.length; i++) 
  {
      temp_str += this.substr(this.length - i, 1);
  }

  return temp_str;
}

//문자열을 주어진수만큼 반복하여 연결한후 반환
function _string_repeat(num) 
{
  var temp_str = "";

  for (i = 1; i <= num; i++) 
  {
      temp_str += this;
  }

  return temp_str;
}

//문자열을 어떠한 길이가 되도록 원본문자열의 왼쪽에 다른 문자열로 채우고 반환
function _string_lpad(num, str) 
{
  return (num > this.length) ? (str.repeat(num - this.length)).left(num - this.length) + this : this;
}

//문자열을 어떠한 길이가 되도록 원본문자열의 오른쪽에 다른 문자열로 채우고 반환
function _string_rpad(num, str) 
{
  return (num > this.length) ? this + (str.repeat(num - this.length)).left(num - this.length) : this;
}

/*
replace 메서드는 일치하는 하나의 문자열만 바꾼후 반환하지만
replaceAll의 경우 일치하는 모든 문자열을 치환후 반환
*/
function _string_replaceall(str1, str2)
{
  var str = "";
  eval("str = /" + str1 + "/gi;");
  return this.replace(str, str2);
}

//문자열중 줄바꿈문자를 HTML 줄바꿈 Tag로 변환하여 반환
function _string_nl2br() 
{
  return this.replaceall("\n", "<br>");
}

//숫자로된 문자열에 3자리마다 콤마를 추가하여 반환
function _string_formatnumber() 
{
/*  if (this < 0) { this *= -1; var minus = true; }
  else var minus = false;

  var dotPos = (this + "").split(".");
  var dotU = dotPos[0];
  var dotD = dotPos[1];
  var commaFlag = dotU.length % 3;

  if (commaFlag) 
  {
      var out = dotU.substring(0, commaFlag)
      if (dotU.length > 3) out += ",";
  }
  else var out = "";

  for (var i = commaFlag; i < dotU.length; i += 3) 
  {
      out += dotU.substring(i, i + 3)
      if (i < dotU.length - 3) out += ",";
  }

  if (minus) out = "-" + out;
  if (dotD) return out + "." + dotD;
  else return out;*/
}

//특수 문자를 HTML 엔티티로 변환
function _string_htmlspecialchars(quote_style) 
{
  var temp_str = "";

  temp_str = ((this.replaceall("&", "&amp;")).replaceall("<", "&lt;")).replaceall(">", "&gt;");

  switch (quote_style.toLowerCase()) {
      case "compat":
          temp_str = temp_str.replaceall('"', '&quot;');
          break;
      case "quotes":
          temp_str = temp_str.replaceall('"', '&quot;');
          temp_str = temp_str.replaceall("'", "&#39;");
          break;
  }
  return temp_str;
}

//HTML Tag를 제거하고 반환
function _string_striptags() 
{
  return this.replace(/[<][^>]*[>]/gi, "");
}

//대소문자를 가리지않고 부분 문자열이 처음 나오는 문자 위치를 반환
function _string_iindexOf(str) 
{
  return (this.toLowerCase()).indexOf(str.toLowerCase());
}

//대소문자를 가리지않고 부분 문자열이 마지막으로 나오는 문자 위치를 반환
function _string_ilastIndexOf(str) 
{
  return (this.toLowerCase()).lastIndexOf(str.toLowerCase());
}

//패턴체크후 Boolean 값 반환
function _string_isPattern(str) 
{
  var regx;
  if (this.length == 0 || this == null || this == "undefined") return false;

  switch (str.toLowerCase()) 
  {
      case "number":
          regx = /^[0-9]+$/;
          break;
      case "alpha":
          regx = /^[a-zA-Z]+$/;
          break;
      case "alphanum":
          regx = /^[a-zA-Z0-9]+$/;
          break;
      case "hangle":
          regx = /^[가-힣]+$/;
          break;
      case "hanglenum":
          regx = /^[가-힣0-9]+$/;
          break;
      case "zipcode":
          regx = /[0-9]{3}-[0-9]{3}/;
          break;
      case "phone":
          regx = /^[0-9]{2,3}-[0-9]{3,4}-[0-9]{4}$/;
          break;
      case "hp":
          regx = /^[0-9]{3}-[0-9]{3,4}-[0-9]{4}$/;
          break;
      case "date":
          regx = /^[0-9]{4}-[0-9]{2}-[0-9]{2}$/;
          break;
      case "float1":
          regx = /^[0-9.]+$/;
          break;
      case "url":
          regx = /^((http|https|ftp|telnet|news):\/\/[a-z0-9-]+\.[][a-zA-Z0-9:&#@=_~%;\?\/\.\+-]+)$/gi;
          break;
  }

  return regx.test(this) ? true : false;
}

Date.prototype.format = function(f) {
    if (!this.valueOf()) return " ";
 
    var weekName = ["일요일", "월요일", "화요일", "수요일", "목요일", "금요일", "토요일"];
    var d = this;
     
    return f.replace(/(yyyy|yy|MM|dd|E|hh|mm|ss|a\/p)/gi, function($1) {
        switch ($1) {
            case "yyyy": return d.getFullYear();
            case "yy": return (d.getFullYear() % 1000).zf(2);
            case "MM": return (d.getMonth() + 1).zf(2);
            case "dd": return d.getDate().zf(2);
            case "E": return weekName[d.getDay()];
            case "HH": return d.getHours().zf(2);
            case "hh": return ((h = d.getHours() % 12) ? h : 12).zf(2);
            case "mm": return d.getMinutes().zf(2);
            case "ss": return d.getSeconds().zf(2);
            case "a/p": return d.getHours() < 12 ? "오전" : "오후";
            default: return $1;
        }
    });
};
 
String.prototype.string = function(len){var s = '', i = 0; while (i++ < len) { s += this; } return s;};
String.prototype.zf = function(len){return "0".string(len - this.length) + this;};
Number.prototype.zf = function(len){return this.toString().zf(len);};

function dateAdd(interval, addVal, yyyymmdd, delimiter)
{
	if(delimiter != "") 
	{
		yyyymmdd = yyyymmdd.replace(eval("/\\" + delimiter + "/g"), "");
	}
	 
	var yyyy = yyyymmdd.substr(0, 4);
	var mm  = yyyymmdd.substr(4, 2);
	var dd  = yyyymmdd.substr(6, 2);
	 
	if(interval == "yyyy") 
	{
		yyyy = (yyyy * 1) + (addVal * 1); 
	} 
	else if(interval == "mm") 
	{
		mm  = (mm * 1) + (addVal * 1);
	} 
	else if(interval == "dd") 
	{
		dd  = (dd * 1) + (addVal * 1);
	}
	
	var cDate = new Date(yyyy, mm - 1, dd);
	var cYear = cDate.getFullYear();
	var cMonth = cDate.getMonth() + 1;
	var cDay = cDate.getDate();
	
	cMonth = cMonth < 10 ? "0" + cMonth : cMonth;
	cDay = cDay < 10 ? "0" + cDay : cDay;
	 
	if (delimiter != "") 
	{
		return cYear + delimiter + cMonth + delimiter + cDay;
	} 
	else 
	{
		return cYear + '' + cMonth + '' + cDay;
	}
}


/*
//2011년 09월 11일 오후 03시 45분 42초
console.log(new Date().format("yyyy년 MM월 dd일 a/p hh시 mm분 ss초"));
 
//2011-09-11
console.log(new Date().format("yyyy-MM-dd"));
 
//'11 09.11
console.log(new Date().format("'yy MM.dd"));
 
//2011-09-11 일요일
console.log(new Date().format("yyyy-MM-dd E"));
 
//현재년도 : 2011
console.log("현재년도 : " + new Date().format("yyyy"));
 */

String.prototype.trim = _string_trim;
String.prototype.left = _string_left;
String.prototype.right = _string_right;
String.prototype.bytes = _string_bytes;
String.prototype.cutbytes = _string_cutbytes;
String.prototype.reverse = _string_reverse;
String.prototype.repeat = _string_repeat;
String.prototype.lpad = _string_lpad;
String.prototype.rpad = _string_rpad;
String.prototype.replaceall = _string_replaceall;
String.prototype.nl2br = _string_nl2br;
String.prototype.formatnumber = _string_formatnumber;
String.prototype.htmlspecialchars = _string_htmlspecialchars;
String.prototype.striptags = _string_striptags;
String.prototype.iindexOf = _string_iindexOf;
String.prototype.ilastIndexOf = _string_ilastIndexOf;
String.prototype.ispattern = _string_isPattern;
