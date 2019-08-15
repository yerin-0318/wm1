<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>   
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>임시메인</title>
<style type="text/css">
	*{
		margin: 0;
		padding: 0;
	}

	div.inline{
		display: inline-block;
	}
	
	div.middle{
		width: 900px;
		position: relative;
	}
	
	div.left{
		border: 1px solid black;
	}
	
	div.left>div{
		position: relative;
		top:0;
		left:0;
	}
	
	div.right{
		border: 1px solid;
	}
</style>
</head>
<body>
<c:set var="center" value="${param.center}"/>
 <%String center = request.getParameter("center");
 System.out.println("center:"+center);%>
 <!-- 처음 CarMain.jsp페이지를 실행하면 당연히..  param.값을 받아올수 없기에  조건 주기-->
 <c:if test="${center == null}"> <!-- 넘겨받은 center값이 없으면.. 이동할  Center.jsp주소를 center변수에 저장 -->
 	<c:set var="center" value="main.jsp"/>
 </c:if>
 
 <center>
 	<div>
 		<jsp:include page="mainTop.jsp"/>
 	</div>
	<div class="middle">
	 	<jsp:include page="${center}"/>
	</div> 	
	<div>
	 <jsp:include page="playlist/footerPlayer.jsp"/>
	</div>
 </center>


</body>
</html>