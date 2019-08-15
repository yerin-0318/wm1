<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="stylesheet" type="text/css"
   href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
<link rel="stylesheet" type="text/css"
   href="css/watermelonplayer.css">

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>

   <nav class="navbar navbar-expand-lg navbar-dark bg-dark" id="mode1"> <a
      class="navbar-brand" href="#" >Navbar</a>
   <button class="navbar-toggler" type="button" data-toggle="collapse"
      data-target="#navbarNavDropdown" aria-controls="navbarNavDropdown"
      aria-expanded="false" aria-label="Toggle navigation">
      <span class="navbar-toggler-icon"></span>
   </button>
   <div class="collapse navbar-collapse" id="navbarNavDropdown">
      <ul class="navbar-nav">
         <li class="nav-item active"><a class="nav-link" href="#">Home
               <span class="sr-only">(current)</span>
         </a></li>
         <li class="nav-item"><a class="nav-link" href="watermelon.jsp?center=streamerhome.jsp">Streamer</a></li>
         <li class="nav-item"><a class="nav-link" href="watermelon.jsp?center=member/join.jsp">JOIN</a></li>
         <%
         	String email = (String)session.getAttribute("email");
         	System.out.println(email);
         	if(email==null){ 
         %>
         <li class="nav-item"><a class="nav-link" href="watermelon.jsp?center=member/login.jsp">LOGIN</a></li>
         <%}else{%>
         <li class="nav-item"><a class="nav-link" href="./logout.me">LOGOUT</a></li>
         <li class="nav-item"><a class="nav-link" href="./watermelon.jsp?center=member/modify.jsp">회원정보 수정</a></li>
         <li class="nav-item"><a class="nav-link" href="./watermelon.jsp?center=member/drop.jsp">탈퇴</a></li> 	 	 
    <%     } %>
         <li class="nav-item dropdown"><a
            class="nav-link dropdown-toggle" href="#"
            id="navbarDropdownMenuLink" role="button" data-toggle="dropdown"
            aria-haspopup="true" aria-expanded="false"> Dropdown link </a>
            <div class="dropdown-menu" aria-labelledby="navbarDropdownMenuLink">
               <a class="dropdown-item" href="#">Action</a> <a
                  class="dropdown-item" href="#">Another action</a> <a
                  class="dropdown-item" href="#">Something else here</a>
            </div></li>
         <form class="form-inline">
            <input class="form-control mr-sm-2" type="search"
               placeholder="Search" aria-label="Search">
            <button class="btn btn-outline-success my-2 my-sm-0" type="submit">Search</button>
         </form>
         <li class="nav-item"><a class="nav-link" href="javascript:modeChange(2)">Mode Change</a></li>
      </ul>
   </div>
   </nav>
   
   <nav class="navbar navbar-expand-lg navbar-dark bg-dark" id="mode2" style="top:-56px;display:none"> <a
      class="navbar-brand" href="#" >Navbar2</a>
   <button class="navbar-toggler" type="button" data-toggle="collapse"
      data-target="#navbarNavDropdown" aria-controls="navbarNavDropdown"
      aria-expanded="false" aria-label="Toggle navigation">
      <span class="navbar-toggler-icon"></span>
   </button>
   <div class="collapse navbar-collapse" id="navbarNavDropdown">
      <ul class="navbar-nav">
         <li class="nav-item active"><a class="nav-link" href="#">Home
               <span class="sr-only">(current)</span>
         </a></li>
         <li class="nav-item"><a class="nav-link" href="watermelon.jsp?center=streamerhome.jsp">Streamer</a></li>
         <li class="nav-item"><a class="nav-link" href="#">Pricing</a></li>
         <li class="nav-item"><a class="nav-link" href="#">Action</a></li>
         <li class="nav-item"><a class="nav-link" href="#">Another</a></li>
         <li class="nav-item"><a class="nav-link" href="#">Something</a></li>
         <li class="nav-item"><a class="nav-link" href="javascript:modeChange(1)">Mode Change</a></li>
      </ul>
   </div>
   </nav>
   
   
      
   
   <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js"></script>
<script src="http://code.jquery.com/jquery-latest.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.bundle.min.js"></script>
<script>
// 헤드 모양 바꾸기
function modeChange(num){
      switch(num){
      case 1:
         $("#mode2").animate({top: "-"+$("#mode2").css("height")}, 100,'linear',function(){
            $("#mode2").css("display","none");
            $("#mode1").css("display","");
            $("#mode1").animate({top: "0px"},100);
         });
         break;
      case 2:
         $("#mode1").animate({top: "-"+$("#mode1").css("height")}, 100,'linear',function(){
            $("#mode1").css("display","none");
            $("#mode2").css("display","");
            $("#mode2").animate({top: "0px"},100);
         });
         break;
      }
   }


</script>

</body>
</html>