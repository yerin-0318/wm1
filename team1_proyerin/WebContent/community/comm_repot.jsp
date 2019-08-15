<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>  

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<script src="http://code.jquery.com/jquery-latest.min.js"></script>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<form name="" id="" action="">
		<input type="hidden" id="this_num" value="<%=request.getParameter("num") %>">
		<div>
			피신고인 : <span id="writer"></span>
		</div>
		<div>
			신고사유
			<input type="radio" name="reason" value="1">원치않는 상업성 콘텐츠 또는 스팸<br>
			<input type="radio" name="reason" value="2">포르노 또는 음란물<br>
			<input type="radio" name="reason" value="3">아동 학대<br>
			<input type="radio" name="reason" value="4">증오심 표현 또는 노골적인 폭력<br>
			<input type="radio" name="reason" value="5">권리침해 또는 괴롭힘<br>
			<input type="radio" name="reason" value="0">기타 <input type="text" name="another" disabled><br>
		</div>
		<div>
			<button type="button">신고하기</button>
		</div>
	</form>
</body>
<script type="text/javascript">
//작성자 정보 가져오기
	$(function(){
		var this_num = $("#this_num").val();
		$.ajax({
			url:"<%=request.getContextPath()%>/commentRepot.co",
			type:"post",
			async:false,
			data:{num:this_num},
			success:function(data,textStatus){
				var jsonInfo = JSON.parse(data);
				let html = jsonInfo.name+ jsonInfo.email;
			}			
		});
	});
</script>
</html>