/* 전역변수 */
	var timeout;
	var mouseonvolume=false;
	var mouseonaudio=false;
	var audio;var audioUrl;
	var audioArr=new Array();
	var seqArr=new Array(); // 순차, 랜덤
	var idx=-1;
	var shuffle=false;
	var repeat=0; // 0:반복없음 1:한곡반복 2:전체반복
	
/* 전역변수 끝*/	

window.onload = function () {
	/*Test*/
	/* audio.src에 파일주소를 줘야함,다른 변수에 주면 파일정보를 불러오는데 로딩이 오래걸림, 이유 모름.*/
		audio=new Audio();
		audioUrl="";
	    audio.ontimeupdate=audio_timeupdate;
	    audio.onvolumechange=volume_change;
	    volume_change();
	    audio_timeupdate();
	    
	    
	    var aaa=["upload/born.mp3","upload/go.mp3","upload/whenever.mp3","upload/you.mp3"];
	    for(var i=0;i<aaa.length;i++){
	    	audioUrl=aaa[i];
	    	addMusic();
	    }
	    audio.src=audioUrl;
	    
	    /* ready 0.5second*/
	    setTimeout(function ()  {
		doSeq();
		}, 500);
	    /*Test End*/
}

$("body").keydown(function( event ){
	if(event.keyCode==32){
		audio_play();
	}
}); // keyboard input end
function readyAudio(){
	setTimeout(function()  {
		doSeq();
		if(seqArr.length==0){
			readyAudio();
		}
	}, 500);
}
function doSeq(){
	seqArr.length=0;
	for(var i=0;i<audioArr.length;i++){
		seqArr.push(i);
	}
}
function doShuffle(){
	var tmp;var j;
	var str="";
	for(var i=0;i<audioArr.length;i++){
		str+="["+i+"]"+audioArr[seqArr[i]].title+"\n";
	}
	for(var i=0;i<audioArr.length;i++){
		j=Math.floor(Math.random()*seqArr.length);
		tmp=seqArr[j];
		seqArr[j]=seqArr[i];
		seqArr[i]=tmp;
	}
	str+="----SHUFFLE----\n";
	for(var i=0;i<audioArr.length;i++){
		str+="["+i+"]"+audioArr[seqArr[i]].title+"\n";
	}
}
/* audio_shuffle*/
$("#audio_shuffle").on("click",function(){
	if(audioArr.length==0)
		return;
	if(shuffle){// 무작위 재생 상태 : 순차 재생으로 변환
		doSeq();
		shuffle=false;
		$("#audio_shuffle").removeClass("audio-shuffle-on");
	}else{ // 순차 재생 상태 : 무작위 재생으로 변환
		doShuffle();
		shuffle=true;
		$("#audio_shuffle").addClass("audio-shuffle-on");
	}
});
/* audio_repeat*/
$("#audio_repeat").on("click",function(){
	switch (repeat){
	case 0: // 반복 없음: 전체 반복으로 변환
		repeat=2;
		$("#audio_repeat").addClass("audio-repeat-on");
		break;
	case 1: // 한곡 반복
		repeat=0;
		$("#audio_repeat").removeClass("audio-repeat-on");
		$("#audio_repeat").removeClass("audio-repeat-one");
		break;
	case 2: // 전체 반복: 한곡 반복으로 변환
		repeat=1;
		$("#audio_repeat").addClass("audio-repeat-one");
		break;
	}
});
/* music list ↓ */
 $("#music_list").on("click",function(){
	 if($("#music_list_pop").css("display")=="none"){
		 $("#music_list>path").css("fill","#f50");
	 $("#music_list_pop").fadeIn();
	 loadMusicList();
 }else{
	 $("#music_list_pop").fadeOut();
	 $("#music_list>path").css("fill","#333");
 }
 });
	
	function addMusicList() { // 해야댐
		$("#music_list_pop")
				.html(
						$("#music_list_pop").html()
								+ "<img src='"+picture+"' height='38px' style='float: left;margin-top: 5px;margin-right:6px'>"
								+ " <div style='height:48px;padding-left:6px;float: left' >"
								+ "<div style='width: 180px;white-space:nowrap; overflow: hidden; text-overflow: ellipsis;'>"
								+ title
								+ "</div>"
								+ "<div class='text-muted' style='width: 180px;white-space:nowrap; overflow: hidden; text-overflow: ellipsis;'>"
								+ artist
								+ "</div>"
								+ "</div>"
								+ "<div class='my-auto text-center' style='float:right;height:48px'>del</div><div style='height:48px;'class='w-100 mb-1'></div>");
	} // addMusicList end
	function loadMusicList() {
		var str = "";
		for (var i = 0; i < audioArr.length; i++) {
			str += "<img src='"+audioArr[i].picture+"' height='38px' style='float: left;margin-top: 5px;margin-right:6px'>"
					+ " <div style='height:48px;padding-left:6px;float: left' >"
					+ "<div style='width: 180px;white-space:nowrap; overflow: hidden; text-overflow: ellipsis;'>"
					+ audioArr[i].title
					+ "</div>"
					+ "<div class='text-muted' style='width: 180px;white-space:nowrap; overflow: hidden; text-overflow: ellipsis;'>"
					+ audioArr[i].artist
					+ "</div>"
					+ "</div>"
					+ "<div class='my-auto text-center' onclick='delMusic("+i+")' style='cursor:pointer;float:right;height:48px'>del</div><div style='height:48px;'class='w-100 mb-1'></div>";
		}
		$("#music_list_pop").html(str);
	}// loadMusicList end
	function delMusic(num){
		audioArr.splice(num,1);
		loadMusicList();
	}
	function addMusic(){
		 // audio.src에 해당하는 파일이 audioArr에 추가됨
		 var url=audioUrl;
			ID3.loadTags(url, function() {
		   	 var tags = ID3.getAllTags(url);
		   	 var title,artist,picture,album;
			    if(tags.title==undefined){
			    	title=url.substring(url.lastIndexOf('/')+1).replace('.mp3','');
			    }else{
			    	title=tags.title;
			    }
			    if(tags.artist==undefined){
			    	artist="Unknown";
			    }else{
			    	artist=tags.artist;
			    }
			    if(tags.album==undefined){
			    	album="Unknown";
			    }else{
			    	album=tags.album;
			    }
			    var image=tags.picture;
			   if (image) {
			        var base64String = "";
			        for (var i = 0; i < image.data.length; i++) {
			            base64String += String.fromCharCode(image.data[i]);
			        }
			        var base64 = "data:" + image.format + ";base64," +
			                window.btoa(base64String);
			        	picture=base64;
			      } else {
			    	  picture="image/watermelon.png";
		   }
			   audioArr.push({"title":title,"artist":artist,"album":album,"picture":picture,"src":url});
			}, {
		       tags: ["title","artist","album","picture"]
		     });
		} // addMusic end
	function loadMusic() {
			idx%=audioArr.length;
			var num=seqArr[idx];
			console.log(audioArr[num]);
		audio.src=audioArr[num].src;
		$("#audio_title").text(audioArr[num].title);
		$("#audio_artist").text(audioArr[num].artist);
		$("#audio_art").attr('src', audioArr[num].picture);
	} // loadMusic end
	/* audio ↓ */
	$("#audio_frm").on("mousedown", function() {
		if (isDead()) {
			return;
		}
		mouseonaudio = true;
		audio_click(event, this);
	});
	$("#audio_frm").on("mousemove", function() {
		if (isDead()) {
			return;
		}
		audio_click(event, this);
	});
	$("#audio_frm").on("mouseup", function() {
		if (isDead()) {
			return;
		}
		audio_click(event, this);
		mouseonaudio = false;
	});
	$("#audio_frm").on("mouseleave", function() {
		if (isDead()) {
			return;
		}
		audio_click(event, this);
		mouseonaudio = false;
	});
	function audio_click(event, obj) {
		if (isDead()) {
			return;
		}
		if (mouseonaudio) {
			audio.currentTime = (event.clientX - $(obj).offset().left)
					/ $(obj).css("width").replace('px', '') * audio.duration;
			audio_timeupdate();
		}
	}
	function audio_timeupdate() {
		if (isDead()) {
			return;
		}
		var progress=100 * audio.currentTime / audio.duration;
		$("#audio_pro").css("width",
				progress + "%");
		$("#audio_pro").css("aria-valuenow",
				progress);
		var second = Math.floor(audio.currentTime % 60);
		$("#audio_current").text(
				Math.floor(audio.currentTime / 60) + ":"
						+ (second < 10 ? "0" + second : second));
		if (!isNaN(audio.duration)) {
			second = Math.floor(audio.duration % 60);
			$("#audio_duration").text(
					Math.floor(audio.duration / 60) + ":"
							+ (second < 10 ? "0" + second : second));
		}
		if(audio.ended){ // play end
			/* 반복모드 아니고, 재생목록 끝*/
			$("#audio_pro").css("width","0%");
			$("#audio_pro").css("aria-valuenow","0");
			$("#audio_current").text("0:00");
			$("#audio_play").removeClass("audio-stop");
		}
	}
	function audio_play() {
		if (isDead()) {
			return;
		}
		if (audio.paused) {
			if(idx==-1){
				idx=0;
				loadMusic();
			}
			audio.play();
			$("#audio_play").addClass("audio-stop");
			$("#audio_pro").addClass("progress-bar-animated");
		} else {
			audio.pause();
			$("#audio_play").removeClass("audio-stop");
			$("#audio_pro").removeClass("progress-bar-animated");
		}
	}

	$("#audio_next").on("click", function() {
		idx=idx+1;
		loadMusic();
		audio_play();
	});
	/* volume ↓ */
	$("#volume").on("click", function(event) {
		if (isDead()) {
			return;
		}
		if (event.target != event.currentTarget) {
			return;
		}
		if (audio.muted) {
			$("#volume").removeClass("audio-volume-off");
			$("#volume_pro").removeClass("bg-dark");
			audio.muted = false;
		} else {
			$("#volume").addClass("audio-volume-off");
			$("#volume_pro").addClass("bg-dark");
			audio.muted = true;
		}
	});
	$("#volume_frm").on(
			"click",
			function(event) {
				if (isDead()) {
					return;
				}
				audio.volume = (Number($("#volume_frm").css("height").replace(
						'px', ''))
						+ $("#volume_frm").offset().top - event.clientY)
						/ $("#volume_frm").css("height").replace('px', '');
				volume_change();
				if (audio.muted) {
					$("#volume").removeClass("audio-volume-off");
					$("#volume_pro").removeClass("bg-dark");
					audio.muted = false;
				}
			});
	// 볼륨에 마우스 대면 조절창 오픈, 1.5초후 꺼짐
	$("#volume").on("mouseenter", function() {
		if (isDead()) {
			return;
		}
		clearTimeout(timeout);
		$("#volume-pop").show();
		timeout = setTimeout(function() {
			if (!mouseonvolume)
				$("#volume-pop").hide();
		}, 1500);
	});
	// 조절창 꺼지기 전에 들어가면 안꺼짐
	$("#volume-pop").on("mouseenter", function() {
		if (isDead()) {
			return;
		}
		mouseonvolume = true;
	});
	// 조절창 나가면 0.5초후 꺼짐
	$("#volume-pop").on("mouseleave", function() {
		if (isDead()) {
			return;
		}
		clearTimeout(timeout);
		mouseonvolume = false;
		timeout = setTimeout(function() {
			$("#volume-pop").hide();
		}, 500);
	});
	function volume_change() {
		if (isDead()) {
			return;
		}
		$("#volume_pro").css("height", 100 * audio.volume + "%");
		$("#volume_pro").css("aria-valuenow", audio.volume);
	}
	function isDead(){
		if(audio.src==''){
			return true;
		}
		return false;
	}
