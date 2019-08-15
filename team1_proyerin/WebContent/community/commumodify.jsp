<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<script src="http://code.jquery.com/jquery-latest.min.js"></script>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<%
	String num = request.getParameter("num");
%>
</head>
<!-- action="./modifycom.co" -->
<body>
	<form method="post" name="commumodifyForm" id="modifyform">
		사진<div id="photodiv"><input type="file" name="photo" onchange="checkextension()" id="fUpload"></div>
		<div id="prtdiv"></div>
		<button type="button" id="delbtn" onclick="delfile()">첨부파일 제거</button><br>
		내용 <textarea rows="30" cols="100" name="content" id="txt"></textarea>
		<button type="submit">임시 글쓰기 버튼</button>
		<button type="button" onclick="submitform()">전송하기</button>
		<input type="hidden" name="num" value="<%=num%>">
	</form>

</body>
<script type="text/javascript">
$(function(){
	//페이지 이동되면 동적으로 데이터 가져오기
	$.ajax({ 
		url:"<%=request.getContextPath()%>/communityajax",
		type:"post",
		async:false,
		data:{num:<%=num%>,from:"commumodify"},
		success:function(data,textStatus){
			data.replace(/\r/gi, '\\r').replace(/\n/gi, '\\n').replace(/\t/gi, '\\t').replace(/\f/gi, '\\f'); 
			console.log("data:"+data);
			var jsonInfo = JSON.parse(data);
			//var content = jsonInfo.content.replace(/\r\n/gi,"<br>"); // /*/gi <-정규 표현식 전체 *를 찾아줌
			$("#txt").html(jsonInfo.content);
			if(jsonInfo.photo!=null){
				$("#photodiv").append(jsonInfo.photo);
			}
		}
	});
});


var changefile = false; //file을 바꾼 내역이 있으면 true가 됨

function submitform(){
	var filename=$("#fUpload").val();
	alert(filename);
	if(changefile){/* file을 바꿨다면 enctype="multipart/form-data"로 전송*/
		$("#modifyform").attr("action","./modifycom.co?enc=multi");
		$("#modifyform").attr("enctype","multipart/form-data");
	}else{ /* 파일을 바꾸지 않았다면 일반전송 */
		alert("파일안보냄")
		$("#modifyform").attr("action","./modifycom.co?enc=normal");
	}
	document.commumodifyForm.submit();
}

function checkextension() { //첨부파일 확장자 유효성 체크
	 var ext = document.getElementById("fUpload").value;
	 changefile = true;
	 ext = ext.slice(ext.indexOf(".") + 1).toLowerCase();
	 
	 if(ext == "jpg" || ext == "jpeg" || ext == "png" || ext == "gif" || ext == "bmp" || ext == "dib"){
		 $("#prtdiv").html("");  
	 }else{
		 $("#prtdiv").html("첨부할 사진의 확장자는 .bmp .dib .jpg .jpeg .gif .png 만 가능합니다.");  
 	 }
}

function delfile(){
	changefile = true;
	$("#photodiv").html("<input type='file' name='photo' onchange='checkextension()' id='fUpload'>");
	$("#prtdiv").html(""); 
}
</script>
</html>