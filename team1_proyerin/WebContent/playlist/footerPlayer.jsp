<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" type="text/css"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
<link rel="stylesheet" type="text/css"
	href="css/watermelonplayer.css">
  <script src="js/id3-minimized.js" type="text/javascript"></script>
</head>
<style></style>
<body>

<br><br>

<div id="output"></div>

<br><br>

	<div class="border-top border-bottom fixed-bottom">
		<div class="container" style="min-width: 720px !important;">
		<div class="row">
			
			<div class="audio-btn audio-prev" style="background-repeat: no-repeat;"></div>
			<div id="audio_play" class="audio-btn audio-play" onclick="audio_play();"></div>
			<div id="audio_next" class="audio-btn audio-next"></div>
			<div id="audio_shuffle" class="audio-btn audio-shuffle-off"></div>
<!-- 			<div class="audio-btn audio-shuffle-on"></div> -->
			<div id="audio_repeat" class="audio-btn audio-repeat-off"></div>
<!-- 			<div class="audio-btn audio-repeat-on"></div> -->
<!-- 			<div class="audio-btn audio-repeat-one"></div> -->
				 
			<span id="audio_current" style="float:left;margin:12px;">0:00</span><!-- audio -->
			<div style="height:48px; float:left">
				<div class="progress" id="audio_frm"
				
				 style="height:12px;width:400px;margin-top:18px;">
					<div
						id="audio_pro" class="progress-bar progress-bar-striped progress-bar-animated"
						role="progressbar" aria-valuenow="0" aria-valuemin="0" aria-valuemax="100">
					</div>
				</div>
			</div>
			<span id="audio_duration" style="float:left;margin:12px;">0:00</span><!-- audio end -->
			
			<div class="audio-btn audio-volume-on" style="position: relative; display: inline-block;" id="volume"><!-- volume -->
			
			<div id="volume-pop"
				style="top:-116px;width: 24px;position: absolute;display: none">
				<div id="volume_frm" class="progress progress-bar-vertical">
					<div 
						id="volume_pro"class="progress-bar progress-bar-striped progress-bar-animated bg-warning"
						role="progressbar" aria-valuenow="100" aria-valuemin="0"
						aria-valuemax="100" style="height: 100%;">
					</div>
				</div>
			</div>
			
			
			
		</div> <!-- volume end -->
			
			<img id="audio_art" src="image/watermelon.png" height="38px" style="margin-left:24px;margin-top: 5px;margin-right:6px">
			<div style="height:48px;padding-left:6px;position: relative; display: inline-block;" >
			<div id="audio_title" style="width:180px;white-space:nowrap;overflow:hidden;text-overflow:ellipsis;"></div>
			<div id="audio_artist" class="text-muted" style="width:180px;white-space:nowrap;overflow:hidden;text-overflow:ellipsis;"></div>

					<div id="music_list_pop" class="border"
						style="position:absolute;width:300px;background:#32d736;height:510px;top:-516px;left:-70px;padding:12px
						;overflow: auto;display: none">
						
						
						
					</div>
				</div>
			
			<!-- 333 <> f50 -->
<!-- 			<svg id="music_list" width="24px" height="24px" style="margin-top:12px;margin-left: 12px"> -->
			<svg class="audio-btn" id="music_list">
        		<path fill="#333" d="M6 11h12v2H6zM6 7h8v2H6zM6 15h12v2H6zM16 3v6l4-3z"/>
      		</svg>
      		
      		<button id="ajax">AJAX</button>
      		
			</div><!-- row -->
			</div><!--container -->
		</div><!--border-->
	
	<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js"></script>
<script src="http://code.jquery.com/jquery-latest.min.js"></script>
<script>
	/* AJAX */
	$(function(){
	$("#ajax").on("click",function(){
		var _jsonInfo="jsonInfo입니다.";
		$.ajax({
			 type:"post",
			 async:false,
			 url:"<%=request.getContextPath()%>/addPlaylist.pl",
			 data:{jsonInfo:_jsonInfo},
			 success:function(data,textStatus){
				 var jsonInfo = JSON.parse(data);
				 
				 var str = "플레이리스트가져오기<br>";
				     
				     for(var i in jsonInfo.playlist){
				    	 str += "넘버 : " + jsonInfo.playlist[i].num + "<br>";
				    	 str += "이메일 : " + jsonInfo.playlist[i].email + "<br>";
				    	 str += "무넘버 : " + jsonInfo.playlist[i].mu_num + "<br><br>";
				     } 
				     $("#output").html(str);
				 
			 },
		error:function(request,status,error){
			alert(" code:"+request.status+"\n message:"+request.responseText+"\n error:"+error);
		},
		complete:function(){
		}
		   });	
	}); // end click
	}); // end function
	</script>

<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.bundle.min.js"></script>
<script src="js/watermelonplayer.js"></script>
</body>
</html>