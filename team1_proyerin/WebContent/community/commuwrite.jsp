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
	String email = request.getParameter("useremail");
String streamer = "hi12zz3@naver.com";//임시
%>
</head>
<body>
	<form method="post" id="writeform" enctype="multipart/form-data" name="commuwriteForm" action="./writecom.co">
		사진<div id="photodiv"><input type="file" name="photo" onchange="checkextension()" id="fUpload">
		<button type="button" id="delbtn" onclick="delfile()">첨부파일 제거</button>
		</div>
		<div id="writeprtdiv"></div>
		내용<br><textarea rows="10" cols="68" name="content"></textarea>
		<div><button type="submit">글쓰기</button></div>		
		<input type="hidden" value="<%=streamer %>" name="email"><%--  
		<c:set var="email" value="${useremail}" scope="request"/>  --%>
	</form>

</body>
<script type="text/javascript">
function checkextension() { /*첨부파일 확장자 유효성 체크 */
	 var ext = document.getElementById("fUpload").value; 
	 ext = ext.slice(ext.indexOf(".") + 1).toLowerCase();
	 
	 if(ext == "jpg" || ext == "jpeg" || ext == "png" || ext == "gif" || ext == "bmp" || ext == "dib"){
		 $("#writeprtdiv").html("");  
	 }else{
		 $("#writeprtdiv").html("첨부할 사진의 확장자는 .bmp .dib .jpg .jpeg .gif .png 만 가능합니다.");  
 	 }
}

function delfile(){
	$("#photodiv").html("<input type='file' name='photo' onchange='checkextension()' id='fUpload'>");
	$("#writeprtdiv").html(""); 
}
</script>
</html>