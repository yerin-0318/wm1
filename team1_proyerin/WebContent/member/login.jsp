<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<!--구글 -->
<meta name = "google-signin-client_id"content = "1047724328942-l97947f3icvgu44qa12frv834n0i8d87.apps.googleusercontent.com">
<meta name="google-signin-scope" content="profile email">
<script src="https://apis.google.com/js/platform.js" async defer></script>
<!--구글 -->
<!--카카오  -->
<meta http-equiv="X-UA-Compatible" content="IE=edge"/>
<meta name="viewport" content="user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0, width=device-width"/>
<script src="//developers.kakao.com/sdk/js/kakao.min.js"></script>
<!--카카오  -->
<!--네이버 -->
<script type="text/javascript" src="https://static.nid.naver.com/js/naveridlogin_js_sdk_2.0.0.js" charset="utf-8"></script>
<!--네이버-->
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">


<script src="http://code.jquery.com/jquery-latest.min.js"></script>

<title>Insert title here</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>

</head>
<body>
	<form class="form-signin" action="./login.me" method="post" id="f" name="f">
	<div class="container">
	 <h1 class="logintitle">Water Melon</h1>
		<label class="mw-only">이메일</label> <input type="text" id="email"
			name="email" placeholder="이메일" required autofocus><br>
	   <div id="idmessage"></div>
		<label for="inputpassword" class="mw-only">비밀번호</label> <input
			type="password" id="password" name="password" class="form-control"
			placeholder="비밀번호" required>
	   <div id="pwmessage"></div>
	   <input type="button" id="btn" value="로그인" onclick="fn_checking()"/>
	   <!--구글인증 -->
		<div class="g-signin2" data-onsuccess="onSignIn"></div>
		<!--구글인증 -->
		
		<!-- 카카오인증 -->
		<div>
			<a id="kakao-login-btn"></a>
			<a href="http://developers.kakao.com/logout"></a>
		</div>
		<!-- 카카오인증 -->
		
		<!-- 네이버 인증 -->
		<div id="naverIdLogin"></div>
		<!-- 네이버 인증 -->
	</div>
	</form>
</body>

 <script type="text/javascript">
	function fn_checking() {
		var _email = $("#email").val();
		var _password = $("#password").val();

		if (_email == '') {
			$("#idmessage").text("아이디를 입력해주세요.");
			setTimeout(function() {
				$("#idmessage").text("");
				
			}, 2500);

		} else if (_password == '') {
			$("#pwmessage").text("비밀번호를 입력해주세요.");
			setTimeout(function() {
				$("#pwmessage").text("");

			}, 2500);

		} else {
			$("#idmessage").text("");
			$.ajax({
						url:"<%=request.getContextPath()%>/memberAjax",
						type:"post",
						async: true,
						data:{email:_email,password:_password,from:"getlogin"},
						dataType: "text",
						success: function(data, textStatus) {
							//응답메세지
							if (data == 'not_pass') {
								$("#pwmessage").text("비밀번호가 틀렸어요.");
								$("#password").val("");
								setTimeout(function() {
									$("#idmessage").text("");

								}, 2500);
							} else if (data == 'not_id') {
								$("#pwmessage").text(
												"아이디 또는 비밀번호를 다시 확인하세요"
											  + " 사이트에 등록되지 않은 아이디이거나, 아이디 또는 비밀번호를 잘못 입력하셨습니다.");
								setTimeout(function() {
									$("#pwmessage").text("");
					
								}, 3000);

							} else {
								
								document.f.submit();							
							}
					}
			});
		}

	}
</script> 

<script type="text/javascript">
/*************DB확인 **********/
function fn_process(Email) {
	console.log("fn_process.."+Email);
	var checkEmail = 0;
	//아래의 아이디입력란에서 입력한 ID값을 얻어 저장
	var _email = Email
	
	if(_email == ''){//아이디를 입력 하지 않았을 경우
		alert("ID를 입력하세요.");
		return;
	}
	$.ajax(
			{
				url:"<%=request.getContextPath()%>/memberAjax", //요청할 가상주소 
				type:"post", //요청방식 지정
				async:false,  //비동기 방식의 요청 지정 
				data:{id:_email, from:"checkEmail"}, // 아래의 입력한 아이디를 서블릿으로 전송함(요청값).
				dataType:"text",// 요청후 서블릿으로 부터 응답받을 데이터 타입 
				success:function(data,textStatus){//요청전송과 응답이 모두 성공했을경우 응답 메세지를 받아 작업할 곳
					//응답메세지를 받아 구현할 곳
					if(data == 'usable'){
						//id속성값이 message인 <div>태그영역을 선택해  "사용할수 있는 ID입니다."메세지표시
						console.log("DB에 email이 없음")
						//사용할수 있는 ID이면  버튼을 비활성화 시킵니다.
						//location.href="join.jsp"
						console.log(data);
						checkEmail = 0;
					}else{
						console.log("DB에 email이 있음")
						//location.href="join.jsp"
						console.log(data);
						checkEmail = 1;
					}
				}
				
			}
		  );
	return checkEmail;
}      

/*gmail*/
	function onSignIn(googleUser) {
	    // Useful data for your client-side scripts:
	    var profile = googleUser.getBasicProfile();
	    console.log("ID: " + profile.getId()); // Don't send this directly to your server!
	    console.log('Full Name: ' + profile.getName());
	    console.log('Given Name: ' + profile.getGivenName());
	    console.log('Family Name: ' + profile.getFamilyName());
	    console.log("Image URL: " + profile.getImageUrl());
	    console.log("Email: " + profile.getEmail());
	    var fullname = profile.getName();

 	    var gmailEmail = profile.getEmail();
// 		document.getElementById("email").value = gmailEmail;
	    // The ID token you need to pass to your backend:
	    var id_token = googleUser.getAuthResponse().id_token;
	    console.log("ID Token: " + id_token);
	    var checkEmail = fn_process(gmailEmail);
	    console.log(checkEmail);
	    if(checkEmail == 0) {
	    	//데이터 베이스에 없을 경우(즉 회원가입하지 않은 이메일인 경우) join페이지로 get방식으로 이메일 전달
	    	location.href = "watermelon.jsp?center=member/join.jsp?email=" + gmailEmail;
	    } else {
	    	//위의 반대
	    	location.href = "./login.me?email="+gmailEmail;
	    }
	    var auth2 = gapi.auth2.getAuthInstance();
	    auth2.disconnect();
	  }
	
	 
</script>


<script type="text/javascript">
/* 카카오 로그인 */
//사용할 앱의 JavaScript 키를 설정해 주세요.
Kakao.init('ce9fc5c1056c61e18a038f0c1bdf6b00');
// 카카오 로그인 버튼을 생성합니다.
Kakao.Auth.createLoginButton({
	container: '#kakao-login-btn',
  	success: function(authObj) {
	  Kakao.API.request({

          url: '/v1/user/me',

          success: function(res) {
        	  	console.log("여기!");
                JSON.stringify(res);
                JSON.stringify(authObj);
                console.log("id:"+res.id);
                console.log("email:"+res.kaccount_email);
                console.log(authObj.access_token);
                var kakaoEmail = res.kaccount_email;
//                 document.getElementById("email").value = kakaoEmail;
				var checkEmail = fn_process(kakaoEmail)
			    console.log(checkEmail);
			    if(checkEmail == 0) {
			    	//데이터 베이스에 없을 경우(즉 회원가입하지 않은 이메일인 경우) join페이지로 get방식으로 이메일 전달
			    	location.href = "watermelon.jsp?center=member/join.jsp?email=" + kakaoEmail+"&type=";
			    } else {
			    	//위의 반대
			    	location.href = "./login.me?email="+ kakaoEmail;
			    }
				//Kakao.cleanup()
				//Kakao.isInitialized()
				//? 
				//Kakao.Auth.logout();
              }

            })/* 
    alert(JSON.stringify(authObj));
	console.log(res.id);//<---- 콘솔 로그에 id 정보 출력(id는 res안에 있기 때문에  res.id 로 불러온다)
    console.log(res.kaccount_email);//<---- 콘솔 로그에 email 정보 출력
    console.log(res.gender);
    console.log(res.birthday);
    console.log(authObj.access_token);  */
  },
  fail: function(err) {
     alert(JSON.stringify(err));
  }
});


/* 네이버로그인 */
var naverLogin = new naver.LoginWithNaverId({
  		clientId : "nivulnd70ui6sfblXXsK",
  		callbackUrl : "http://localhost:8090/watermelon/callback.jsp",
  		isPopup : false,
  		loginButton : {color : "green", type : 3, height : 60}
  		

  	})
  	naverLogin.init();
  	naverLogin.getLoginStatus(function (status) {
		if (status) {
			var gender = naverLogin.user.getGender();
			var naverEmail = naverLogin.user.getEmail();
			var nickname = naverLogin.user.getNickName();
			var name = naverLogin.user.getName();
			var profileImage = naverLogin.user.getProfileImage();
			var birthday = naverLogin.user.getBirthday();			
			var uniqId = naverLogin.user.getId();
			var age = naverLogin.user.getAge();
			console.log("email : ", naverEmail);
			console.log("gender :", gender)
			console.log("name : ", name);
			console.log("nickname : ", nickname);
			console.log("profileImage : ", profileImage);
			console.log("birthday : ", birthday);
			console.log("uniqId : ", uniqId);
			console.log("age : ", age);
			
// 			document.getElementById("email").value = naverEmail;
			var checkEmail = fn_process(naverEmail);
			
		    console.log(checkEmail);
		    if(checkEmail == 0) {
		    	//데이터 베이스에 없을 경우(즉 회원가입하지 않은 이메일인 경우) join페이지로 get방식으로 이메일 전달
		    	location.href = "watermelon.jsp?center=member/join.jsp?email=" + naverEmail;
		    } else {
		    	//위의 반대
		    	location.href = "./login.me?email="+naverEmail;
		    }
			naverLogin.logout();
			
			
		} else {
			console.log("AccessToken이 올바르지 않습니다.");
		}
	});
</script>

</html>