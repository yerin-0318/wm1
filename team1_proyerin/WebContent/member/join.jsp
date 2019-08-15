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
<title>Insert title here</title>
<!-- 제이쿼리 연동임 -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
</head>

<script type="text/javascript">
var result_pwd = false;


function authEmail(){
	var email = $("#email").val();
	
	if(email == ""){
		alert("이메일을 입력해 주세요.");
	}else{
		$.ajax({
			type: 'POST',
			url : '<%=request.getContextPath()%>/EmailCheck',
			data: {email:email,from:"getEmail"},
			success: function(result){
				var str;
				if(result == "false"){//사용할수 있는 이메일, 중복 ㄴ
					str = "<font color='green'>사용가능한 이메일입니다.</font>";
					$('#checkEmail').html(str);
					window.open('member/email_auth.jsp?email='+email, 'Email 인증요청',
    				'width=500, height=400, menubar=no, status=no, toolbar=no');
				}else{
					str = "<font color='red'> 이미 사용중인 메일주소입니다.</font>";
					$('#checkEmail').html(str);
				}}, error: function(){
				alert("ERROR!!");
				}
				
			}
			
			
		);
	}
}

 
/* 비밀번호 유효성 검사 메서드*/
function checkPwd(){
	var pwd1 = $("#password").val();
	var checkSpan = $("#checkPwd1");
	 var reg = /^.*(?=.{6,20})(?=.*[0-9])(?=.*[a-zA-Z]).*$/;
	
 	if(!reg.test(pwd1)){
 		checkSpan.html("<font color='red'>비밀번호는 숫자, 알파벳 조합으로 6자리 이상 입력해야 합니다! </font>");

 	}else{
 		checkSpan.html("<font color='green'>완료(숫자+알파벳 6자 이상)</font>");
 		result_pwd = true;
 	}
}

/* 비밀번호 재입력 일치 검사 메서드 */
function checkPwd2(){
	var pwd1 = document.getElementById("password").value;
	var pwd2 = document.getElementById("password2").value;
	var checkSpan = document.getElementById("checkPwd2");
	if(pwd2 != ""){
    	if(pwd2 == pwd1){
    		checkSpan.innerHTML = "<font color='green'>비밀번호가 일치합니다.</font>";
    	}else{
    		checkSpan.innerHTML = "<font color='red'> 비밀번호가 일치하지 않습니다.</font>";
    	}
	}
}


</script>

 <script type="text/javascript">
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
		var email = profile.getName();
	    // The ID token you need to pass to your backend:
	    var id_token = googleUser.getAuthResponse().id_token;
	    console.log("ID Token: " + id_token);
	  }
	
	function checkForm(){
		
		/*  if(document.getElementById("authBtn").disabled==false){
			alert("이메일 인증 버튼을 눌러주세요!");
			$("#email").focus();
			return false;
		}else */ if(!result_pwd){
			alert("비밀번호를 확인해 주세요!");
			$("#password1").focus();
			return false
		}
		
		else return;
		
	}
	        
</script>
<body>

<article>
	<form action="./joinmemberAction.me" method="post">
		<fieldset>
			<legend> 음악을 즐겨요! </legend>
			<div>
			<label>이메일</label>
			<input type="text" placeholder="watermelon@email.com" name="email" id="email" required >
			<span id="checkEmail">&nbsp;</span>
			<input type="button" id="button" onclick="authEmail()" value="인증">
			</div><!--구글인증 -->
				<div class="g-signin2" data-onsuccess="onSignIn"></div>
			<div>
			<!-- 카카오인증 -->
				<a id="kakao-login-btn"></a>
   				<a href="http://developers.kakao.com/logout"></a>
			</div>
			<div><!-- 네이버 인증 -->
				<div id="naverIdLogin"></div>
			</div>
			<div>
			<label>비밀번호</label>
			<input type="password" name="password" id="password" onblur="checkPwd()" placeholder="숫자+알파벳 6자 이상" required>
			<span id="checkPwd1">&nbsp;</span>
			<br>
			<label>비밀번호 확인</label>
			<input type="password" name="password2" id="password2" onblur="checkPwd2()" placeholder="한번더 입력해 주세요!" required>
			<span id="checkPwd2">&nbsp;</span>
			</div>
			
			<div>
			<label>이름</label>
			<input type="text" name="name" placeholder="닉네임" required>
			</div>
			
			<div>
			<label>성별</label>
			<input type="radio" name="gender" value="male"  required>
			<label>남자</label>
			<input type="radio" name="gender" value="female" >
			<label>여자</label>
			</div>
			
			<div>
			<label>생일</label>
			<input type="date" name="birth"  required>
			</div>
			
		
		</fieldset>
		
		<fieldset>
		<legend> 알려주세요! </legend>
		<input type="checkbox" name="like" value="ballad"> <label>발라드</label>
		<input type="checkbox" name="like" value="rap"> <label>랩/힙합</label>
		<input type="checkbox" name="like" value="rnb"> <label>R&B</label><br>
		<input type="checkbox" name="like" value="acous"> <label>어쿠스틱</label>
		<input type="checkbox" name="like" value="pop"> <label>팝</label>
		<input type="checkbox" name="like" value="trot"> <label>트로트</label><br>
		<input type="checkbox" name="like" value="dance"> <label>댄스</label>
		<input type="checkbox" name="like" value="etc"> <label>기타</label>
		</fieldset>
		<br>
		<input type="submit" value="가입">
		<input type="reset" value="취소">
	
	
	</form>

</article>
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
                JSON.stringify(res);
                JSON.stringify(authObj);
                console.log("id:"+res.id);
                console.log("email:"+res.kaccount_email);
                console.log("gender:"+res.gender);
                console.log("birthday:"+res.birthday);
                console.log(authObj.access_token);

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
			var email = naverLogin.user.getEmail();
			var nickname = naverLogin.user.getNickName();
			var name = naverLogin.user.getName();
			var profileImage = naverLogin.user.getProfileImage();
			var birthday = naverLogin.user.getBirthday();			
			var uniqId = naverLogin.user.getId();
			var age = naverLogin.user.getAge();
			console.log("email : ", email);
			console.log("gender :", gender)
			console.log("name : ", name);
			console.log("nickname : ", nickname);
			console.log("profileImage : ", profileImage);
			console.log("birthday : ", birthday);
			console.log("uniqId : ", uniqId);
			console.log("age : ", age);
			
		} else {
			console.log("AccessToken이 올바르지 않습니다.");
		}
	});
</script>
</body>
</html>