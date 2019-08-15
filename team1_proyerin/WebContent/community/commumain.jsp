<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>  
<!--  
손예린 : 각 스트리머의 커뮤니티 게시판임

-->
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<!--무한 스크롤  -->
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<%-- <link rel="stylesheet" href="${pageContext.request.contextPath }/assets/css/guestbook-ajax.css" rel="stylesheet" type="text/css">
<link rel="stylesheet" href="https://code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css"> --%>
<%-- <script type="text/javascript" src="${pageContext.request.contextPath }/assets/js/jquery/jquery-1.9.0.js"/>
 --%>
<script src="http://code.jquery.com/jquery-latest.min.js"></script>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>

<style type="text/css">
div.testdiv{height: 200px; border:2px solid #000; margin-bottom: 100px;}
div.comm{
	min-width: 500px; max-width: 500px; min-height: 500px; margin: 10 0; border: 1px dotted #c3eef6;
}
div.comment{
	background-color: #e8e8e8;
}
div.comm_content{
	border-bottom: 1px solid #ffffff; 
}
</style>

</head>
<body>
<%
	response.setContentType("text/html; charset=UTF-8"); 
	String useremail = (String)session.getAttribute("email"); //hidf3@naver.com

	String streamer = "hi12zz3@naver.com";
	String streamerName = "임시 닉네임"; //<--구현하기
	int pageNum = 1; //임시값
	response.setContentType("text/html; charset=UTF-8"); 
%>
<%if(useremail!=null && useremail.equals(streamer)){ %>
<%-- <a href="./watermelon.jsp?center=streamerhome.jsp&streamcenter=community/commuwrite.jsp?useremail=<%=useremail%>">글쓰기</a>
 --%>
<div id="writediv"><!-- 글작성하는 곳 streamer와 useremail이 동일한 경우에만 뜬다! -->
	<jsp:include page="commuwrite.jsp"/>
</div>
<%} %>

<div id="prtdiv"></div>
</body>


<script type="text/javascript">
	var _streamer="hi12zz3@naver.com";
	var isEnd = false; //var와 같은 변수이나 재선언이 불가능하다. 변수에 재할당이 가능하다. (const는 재할당이 불가능하다)
	$(function(){
		$(window).scroll(function(){
			if(isEnd==true) {return;}
			let $window = $(this);
			let scrollTop = parseInt($window.scrollTop());
			let windowHeight = parseInt($window.height()); //화면에 보여지는 높이
			let documentHeight = parseInt($(document).height()); //페이지 전체 높이

			console.log("parseInt(scrollTop+windowHeight): "+parseInt(scrollTop+windowHeight));
			console.log("documentHeight: "+(documentHeight));
			console.log("windowHeight :"+windowHeight);
			console.log("scrollTop1 :"+scrollTop);
			var bottom = parseInt(scrollTop+windowHeight);
			if(parseInt(scrollTop+windowHeight)==documentHeight){//스크롤이 바닥에서 30 위에 있을 때
				console.log("-----------------------------------------------")
	 			var _scrollNo = $("div#prtdiv div.comm").last().data("num");
				console.log("scrollTop2 :"+scrollTop);
				$('html').animate({scrollTop : parseInt(scrollTop)}); 

				//아이디pridiv의 자식 태그div클래스comm 중 마지막 자식의 num가져옴
				fetchList(_scrollNo);
			}
			
		});
		var _startNo = "-1";
		fetchList(_startNo); //스크롤 안했을 때
	});
	
	let fetchList = function(_startNo){
		if(isEnd==true) {return;} //남은 데이터가 5개 이하면 종료됨
		//글 리스트를 가져올 때 시작 번호
		//renderList 함수에서 html코드를 보면 li태그에 data-no속성이 있는 것을 알 수 있다.
		//ajax에서는 data-속성의 값을 가져오기 위해 data()함수를 제공.
	/* 	let _startNo = $("#prtdiv p").last().data("num");
		if(_startNo=="undefined"){
			_startNo = "0";
		}
	 */	console.log("_startNo: "+_startNo);		
		$.ajax({ //data가지러 가기
			url:"<%=request.getContextPath()%>/communityajax",	
			type:"post",
			async:false,
			data:{streamer:_streamer, no:_startNo, from:"getcontext"},
			//dataType:"json", JSON타입으로 넘겨도 받을때는 String으로 넘어옴 -> parseerror발생함
			success:function(data,textStatus){
				//data.replace(/\r/gi, '\\r').replace(/\n/gi, '\\n').replace(/\t/gi, '\\t').replace(/\f/gi, '\\f'); 
				//빈공간 제어 -> 제거 안하면 신텍스 오류남
				var jsonInfo = JSON.parse(data);
				let length = jsonInfo.community.length;	
				if(length==0){
					isEnd=true;
				}
				let html;
				for(var i in jsonInfo.community){
					var num = jsonInfo.community[i].num;
					html = "";
					html += "<div class='comm' id='comm"+num+"' data-num="+num+">" //data-num => json에서 제공하는 객체. num호출해서 값 받을 수 있다.
					+"<div>"+jsonInfo.community[i].name+"</div>"+
					"<div>"+jsonInfo.community[i].date+"</div>";

					var photo = jsonInfo.community[i].photo;
					if(photo=="null" || photo==null){}
					else{ html+="<div><img src=./communityphoto/"+photo+" width='300px' height='300px'></div>";}
					
					var content = jsonInfo.community[i].content.replace(/\r\n/gi,"<br>"); // /*/gi <-정규 표현식 전체 *를 찾아줌
					
					html+="<div>"+content+"</div>"+
					"<div><img src='./image/like.png' onclick='goodplus("+num+")' width=17px><span id='good"+num+"'>"+jsonInfo.community[i].good+"</span></div>";
										
					var thisemail = jsonInfo.community[i].email
					/* var comdivId = "#comments"+num; //해당 게시글의 전체 댓글란 아이디
					var tmp = num+","+comdivId; */
					if(thisemail=='<%=useremail%>'){
						html+="<div><button type='button' onclick='modify("+num+")'>수정</button>"+
						"<button type='button' onclick='deletecon("+num+")'>삭제</button></div></div>"
					}else{
						html+="</div>";
					}
					html+="<input type='text' id='commentstxt"+num+"'>"
						+"<button type='button' id='"+num+"' class='comInsert'>댓글 달기</button>"
						+"<div id='prtalert"+num+"'></div>" //비회원일 시 댓글 달기 제한됨
						+"<div id='comments"+num+"'></div>"//comments <-댓글란
						+"<div id='morecomm"+num+"' data-total='null'></div>"; //더보기 버튼 눌리는 곳
					$("#prtdiv").append(html);
					prtcomments(num,false);
				}
				//$("#prtdiv").append(html);
				//prtcomments(num);
			},
			error:function(data,textStatus){ //작업중 오류가 발생했을 경우에 수행할 작업을 설정 합니다.
				 alert("에러가 발생했습니다."+textStatus+", data="+data);
			}
		});
	};	
	
	function modify(num) {
		location.href="streamerhome.jsp?center=community/commumodify.jsp?num="+num;
	}
	
	function deletecon(num) {
		location.href="deletecom.co?num="+num;
	}
	
	function prtcomments(_num, type) { 
		//불러온 댓글 출력하기 _num : 커뮤니티 글 번호, _id : 댓글란(=div태그) 아이디값
		//type이 true이면 댓글 새로 작성후 출력, false이면 일반출력
		var _startcom;
		var _id = "#comments"+_num;
		var len = $(_id).find("div").length;
		var _check = "first"; //처음 댓글 출력하는 경우
		if(type || len==0){ //type=true댓글 입력후 재출력 len==0아직 아무 댓글도 없는 경우
			_startcom = -1;
		}else{ //댓글이 있는 경우 마지막 댓글의 번호 가져온다
			_startcom = $(_id+" div.comment").last().data("num");
			_check = "next";
		} 
		console.log("출력된 댓글 수: "+len);
		//출력된 댓글 마지막 번호 가져오기
				
		$.ajax({			
			url:"<%=request.getContextPath()%>/communityajax",
			type:"post",
			async:false,
			data:{num:_num,from:"getcomments", startCom:_startcom, check:_check},
			success:function(data,textStatus){
				var jsonInfo = JSON.parse(data);
				let html="";
				for(var i in jsonInfo.comments){
					var content = jsonInfo.comments[i].content.replace(/\r\n/gi,"<br>"); // /*/gi <-정규 표현식 전체 *를 찾아줌
					var commentNum = jsonInfo.comments[i].num; //댓글 넘버
					
					html += "<div class='comment' data-num="+commentNum+">"; //data-num => json에서 제공하는 객체. num호출해서 값 받을 수 있다.
					
					//고정된 댓글이면 추가됨
					if(jsonInfo.comments[i].pin==1){
						html += "<div><img src='./image/push-pin.png' width=17px>이 댓글을 "+'<%=streamerName%>'+"님이 고정하셨습니다.</div>"; }
					
					html += "<div><span>"+jsonInfo.comments[i].name+"</span>";
					
					//본인이 올린 댓글이면 삭제 버튼 생기게 한다.
					if(jsonInfo.comments[i].email=='<%=useremail%>'){ 
						html += "<span><img class='comDel' data-num="+commentNum+" src='./image/delete.png' width='20'></span>" }
					
					html +=	"<span><img class='repot' data-num="+commentNum+" src='./image/siren.png' width='20'></span>"
							+"</div><div>"+jsonInfo.comments[i].date+"</div>"
							+"<div class=''>"+content+"</div>";
					//더보기로 추가된 댓글인 경우
					if(!type){
						$(_id).append(html);
						html="";
					}
					
				}
				if(type){ //새로 댓글달아서 생긴 경우
					$(_id).html(html);
				}
				console.log("_id:"+_id);
				let nowlast = $(_id+" div.comment").last().data("num");
				console.log("마지막 댓글 번호:"+nowlast);
				morecommCnt(_num, nowlast); //글번호, 마지막 댓글 번호 가져가기 =>더보기 버튼 구현
			}
		});
	}
	
	//댓글 신고
	$(function(){
		$(document).on("click","img.repot",function(){
			let _num = $(this).last().data("num"); // 댓글 번호
			alert(_num);
			window.open("community/comm_repot.jsp?num="+_num,"","width=400 height=200");
		})	
	});
	
	$(function(){ //댓글삭제
		$(document).on("click","img.comDel",function(){
			let _num = $(this).last().data("num"); // 댓글 번호
			var parents = $(this).parents('div.comment').parent();
			let com_num = parents.attr("id").split("s");
			console.log("com_num:"+com_num[1]);
			$.ajax({
				url:"<%=request.getContextPath()%>/commentComDel.co",
				type:"post",
				async:false,
				data:{num:_num},
				success:function(data,textStatus){
					prtcomments(com_num[1],true);
					$("#commentstxt"+com_num[1]).val("");
				}
			});
		})
	});
	
	$(function(){ //댓글달기 버튼 클릭시
		$(document).on("click","button.comInsert",function(){
			let com_num = $(this).attr("id");
			if('<%=useremail%>'=="null"){
				$("#prtalert"+com_num).html("<b>비회원은 댓글 작성이 불가합니다.</b>");
				return;
			}			
			let comments = $("#commentstxt"+com_num).val();
			
			$.ajax({
				url:"<%=request.getContextPath()%>/commentComWrt.co",
				type:"post",
				async:false,
				data:{num:com_num,comments:comments},
				success:function(data,textStatus){
					prtcomments(com_num,true);
					$("#commentstxt"+com_num).val("");
				}			
			});
			
		});
	});
	
	
	
	function morecommCnt(_num, _nowlast){
		//전체 글 수 가져오기
		let total = $("div#morecomm"+_num).last().data("total");
		console.log("total:"+total);
		let type = 1;
		//전체 댓글 수가 0인 경우!!
		let html="";
		$.ajax({
			url:"<%=request.getContextPath()%>/communityajax",
			type:"post",
			async:false,
			data:{num:_num,nowlast:_nowlast,from:"gettotalComments",total:total},
			success:function(data,textStatus){
				var JsonInfo = JSON.parse(data);
				//이전에도 total구한 경우(-1) -> total 그대로 사용
				//
				let prtCnt = JsonInfo.prtCnt; //지금 출력된 댓글 수
				let totalCnt = JsonInfo.total; //전체 댓글 수
				if(prtCnt==-1 || totalCnt==prtCnt){ //해당글에 댓글이 없을 경우 or 전체 댓글 수 == 출력된 댓글 수
					html=""; //빈칸으로 둔다
				}else{
					if(JsonInfo.total != 0){//구해둔 total값이 없는 경우
						$("div#morecomm"+_num).attr("data-total",totalCnt);
						total = totalCnt;
					}
					html+="<button type='button' id='morecomments' "+
					"onclick=prtcomments("+_num+")>댓글 더보기("+prtCnt+"/"+total+")</button>";
				}			
			}//success끝
		});
		$("div#morecomm"+_num).html(html);
	}
	
	function goodplus(_num){
		if('<%=useremail%>'=="null"){
			alert("좋아요!!는 로그인 후에 가능합니다. .. 세션(ip)저장하는 방식으로 할까????")
		}else{
			$.ajax({
				url:"<%=request.getContextPath()%>/communityajax",
				type:"post",
				async:false,
				data:{num:_num,streamer:'<%=streamer%>',from:"setgood"},
				success:function(data,textStatus){
					
					if(data=="equal"){
						alert("본인 글에는 좋아요!!하실 수 없습니다.");
					}else if(data=="true"){
						let good = parseInt($("#good"+_num).html())+1;
						console.log("좋아요:"+good);
						$("#good"+_num).html(good);
					}else{
						alert("이미 좋아요!!하셨습니다.");
					}
				}
			});
		}
	}
	
	
</script>

</html>