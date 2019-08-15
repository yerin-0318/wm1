<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>



	<%
		request.setCharacterEncoding("UTF-8");
	
		String email = (String) session.getAttribute("email");

	%>

	<article>
	
	<form action="./dropAction.me" method="post" >
	<fieldset>
		<div>
		
			<lagend>탈퇴 비밀번호 확인 창</lagend><br>
			<label>이메일</label>
			<input type="text" name="email" value="<%=email%>" readonly><br>
			
			<label>비밀번호</label> 
			<input type="password" name="passwd" > <br>
			<br>
			</fieldset>
			<input type="submit" value="탈퇴" >
			<input type="button" value="취소">
		</div>
	</fieldset>
	</form>
	</article>
	
</body>

<script type="text/javascript">
	function fn_checking() {
		var _email = $("#email").val();
		var _password = $("#password").val();

	if (_password == '') {
			$("#pwmessage").text("비밀번호를 입력해주세요.");
			setTimeout(function() {
				$("#pwmessage").text("");

			}, 2500);

		} else {
			$("#idmessage").text("");
			$.ajax({
						url:"<%=request.getContextPath()%>/logincheck",
						type:"post",
						async: true,
						data:{email:_email,password:_password},
						dataType: "text",
						success: function(data, textStatus) {
							//응답메세지
							if (data == 'not_pass') {
								$("#pwmessage").text("<font color='green'> 비밀번호가 틀렸어요.");
								$("#password").val("");
								setTimeout(function() {
									$("#idmessage").text("");

								}, 2500);
							
							} else {
								
								document.f.submit();							
							}
					}
			});
		}

	}
</script> 
</html>