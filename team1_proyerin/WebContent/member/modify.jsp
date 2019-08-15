
<%@page import="net.member.db.membersBean"%>
<%@page import="net.member.db.membersDAO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>

<!-- 제이쿼리 연동임 -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
</head>
<body>

   <%
      request.setCharacterEncoding("UTF-8");
      
      String email = (String) session.getAttribute("email");

   %>

   
   <article>
      <form action="../modifymemberAction.me" method="post">
         
         <div>
         <fieldset>
            <legend>내 정보 수정 페이지 입니다!</legend>
            
            <label>이메일</label>
            <input type="text" name="email" value="<%=email%>" readonly>
            <br>
            
            <label>비밀번호</label>
            <input type="password" name="password"  id="password">
            <br>
            
            <label>이름</label>
            <input type="text" name="name" id="name">
            <br>
            
            <label>성별</label>
            <input type="radio" name="gender"  id="male" value="male" >
                <label for="male">남자</label>
             
                 <input type="radio" name="gender"  id="female" value="female">
                <label for="female">여자</label>
                 <br>
                 
                 <label>생일</label>
                 <input type="date" name="birth"  id="birth">
                 <br>
         </fieldset>
         </div>
         
         <div>
         <fieldset>
            <legend>취향이 바뀌셨나요?</legend>
            <input type="checkbox" name="like" value="ballad" <%if(false){%>checked<%}%>> <label>발라드</label>
            <input type="checkbox" name="like" value="rap" <%if(false){%>checked<%}%>> <label>랩/힙합</label>
            <input type="checkbox" name="like" value="rnb" <%if(false){%>checked<%}%>> <label>R&B</label><br>
            <input type="checkbox" name="like" value="acous" <%if(false){%>checked<%}%>> <label>어쿠스틱</label>
            <input type="checkbox" name="like" value="pop" <%if(false){%>checked<%}%>> <label>팝</label>
            <input type="checkbox" name="like" value="trot" <%if(false){%>checked<%}%>> <label>트로트</label><br>
            <input type="checkbox" name="like" value="dance" <%if(false){%>checked<%}%>> <label>댄스</label>
            <input type="checkbox" name="like" value="etc" <%if(false){%>checked<%}%>> <label>기타</label>
            
         </fieldset>
         </div>
         
         <div >
            <br> <input type="submit" value="수정 완료" >
            <input type="button" value="취소" >
         
         </div>
      
      
      </form>
   </article>
   
</body>
<script type="text/javascript">
$(function(){
	var _email="<%=email%>";
	//페이지 이동되면 동적으로 데이터 가져오기
	$.ajax({
		url:"<%=request.getContextPath()%>/memberAjax",
		type:"post",
		async:false,
		data:{email:_email,from:"getmodify"},
		success:function(data,textStatus){
			console.log("data:"+data);
			var jsonInfo = JSON.parse(data);
			$("#password").attr("value",jsonInfo.password);
			$("#name").attr("value",jsonInfo.name);
			
			
			if(jsonInfo.gender=="male"){
				$("#male").attr("checked","checked");
			}else{
				$("#female").attr("checked","checked");
			}
			
			$("#birth").attr("value",jsonInfo.birth);
			
		
		}
	});
});
</script>


</html>