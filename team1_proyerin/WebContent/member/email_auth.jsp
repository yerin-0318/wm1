<%@page import="net.member.db.membersDAO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta charset="UTF-8">
<title>이메일 인증</title>
</head>

<%
	request.setCharacterEncoding("UTF-8");
	String email = request.getParameter("email");
		
	membersDAO dao = new membersDAO();
	
	String authNum = dao.authNum();
	boolean result = dao.sendEmail(email, authNum);
	
	if(result==false){
%>		<script type="text/javascript">
			alert("메일 전송 실패!<br>메일주소를 확인 해 주세요.");
			window.close();
		</script>
<% 	}
%>

<script type="text/javascript">

	function checkAuthNum(){
		var checkNum = document.getElementById("authNum").value;
		var authNum = <%=authNum%>
		
		if(!checkNum){
			alert("인증번호를 입력하십시오.")
		}else{
			if(checkNum == authNum){
				alert("성공적으로 인증되었습니다.");
				opener.document.getElementById("email").readOnly = true;
				window.close();
			}else{
				alert("인증번호가 잘못되었습니다.");
				return false;
			}
		}
	}
	
</script>

<body>
	<form class="form-signin">
		<div class="text-center mt-5 mb-4">
			<h1 class="h3 mb-3">Email 인증을 해주십시오!</h1>
			<p><%=email %>로 인증메일이 발송되었습니다.</p>
		</div>
		
		<div class="form-label-group mt-4">
			<input type="text" id="authNum" name="authNum" class="form-control" placeholder="" autofocus="">
			<label for="inputEmail">인증번호를 입력하세요.</label>
			
		</div>
		<br><br>
		
		<div class="mt-3">
			<button class="btn btn-lg btn-primary btn-block" type="button" onclick="checkAuthNum()">인증하기</button>
		</div>
		<p class="mt-5 mb-3 text-muted text-center">© watermelon</p>
	</form>
</body>
</html>