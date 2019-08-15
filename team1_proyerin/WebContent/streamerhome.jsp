<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>   
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>STREAMER</title>
<style type="text/css">
	*{
		margin: 0;
		padding: 0;
	}

	div.top{
		width: 900px;
		height: 300px;
		background-color: #c3eef6; 
	}
	
	div.inline{
		display: inline-block;
	}
	
	div.bottom{
		height: 30px;
		width: 100%;
		background-color: white;
		border: 1px solid red;
		position: fixed;
		bottom: 0;
	}
	
	div.bottom>img{
		position: absolute; top:0;
		height: 100%;
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
<c:set var="streamcenter" value="${param.streamcenter}"/>
 
 <!-- 처음 CarMain.jsp페이지를 실행하면 당연히..  param.값을 받아올수 없기에  조건 주기-->
 <c:if test="${streamcenter == null}"> <!-- 넘겨받은 center값이 없으면.. 이동할  Center.jsp주소를 center변수에 저장 -->
 	<c:set var="streamcenter" value="musics/musicsmain.jsp"/>
 </c:if>
 
 <center>
 	<div class="top">
 	</div>
 	<div>
 	 	<div class="left inline">
	 		<div class="inline"><a href="watermelon.jsp?center=streamerhome.jsp&streamcenter=musics/musicsmain.jsp">music</a></div>
	 		<div class="inline"><a href="watermelon.jsp?center=streamerhome.jsp&streamcenter=community/commumain.jsp">community</a></div>
	 	</div>
 	</div>
	<div class="middle">
	 	<div class="center inline">
	 		<jsp:include page="${streamcenter}"/>
	 	</div>
	 </div> 	
 </center>


</body>
</html>