package net.community.action;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import net.community.db.communityDAO;
import net.community.db.goodlistBean;
import net.community.db.goodlistDAO;
import net.writelist.db.com_commuDAO;

@WebServlet("/communityajax")
public class CommunityAjaxServlet extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doBoard(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doBoard(request, response);
	}

	protected void doBoard(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html; charset=UTF-8"); 
		
		String streamer = request.getParameter("streamer");
		String from = request.getParameter("from");
		communityDAO cdao = new communityDAO();
				
		//만약 게시글 출력하는 경우
		if(from.equals("getcontext")){
			int pageSize = 3; //�븳 �솕硫댁뿉 �굹�삱 湲��쓽 �닔
			String no =request.getParameter("no");
			
			int currentNo=0;
			
			if(no==null||no.equals("-1")){
				currentNo = cdao.getLastnum(streamer);
			}else{
				currentNo=Integer.parseInt(no)-1;
			}
			//System.out.println("currentNo"+currentNo);
			
			JSONArray jsonArr = cdao.getList(streamer, currentNo, pageSize);
			JSONObject totalObj = new JSONObject();
			
			totalObj.put("community", jsonArr);
			PrintWriter writer = response.getWriter();
			writer.print(totalObj);
				
		}else if(from.equals("gettotalComments")){ //댓글 더보기
			int num = Integer.parseInt(request.getParameter("num")); //글번호
			int nowlast = Integer.parseInt(request.getParameter("nowlast")); //마지막으로 출력된 댓글 번호
			String total = request.getParameter("total"); 
			//total==null처음 구함 alse total이전에 구해둠
			
			System.out.println("getTotal:"+num+","+nowlast+","+total);
			com_commuDAO dao = new com_commuDAO();
			JSONObject obj = dao.getCommInf(num, nowlast, total);
			String s = obj.toString();
			System.out.println(s);
			PrintWriter writer = response.getWriter();
			writer.print(obj);		
			
		}else if(from.equals("getcomments")){ //해당 게시글의 댓글을 가져올 경우
			int num = Integer.parseInt(request.getParameter("num")); 
			int startCom = Integer.parseInt(request.getParameter("startCom"));
			String check = request.getParameter("check");
			
			//num:게시글 번호 startCom:마지막으로 출력된 댓글 번호
			if(startCom == -1){ //처음 출력하는 경우
				startCom = cdao.getLastcommentnum(num)+1;
			}
			JSONArray jsonArr = cdao.getComments(num, startCom,check);
			JSONObject totalObj = new JSONObject();
			
			totalObj.put("comments",jsonArr);
			
			
			String jsonInfo = jsonArr.toJSONString();
			System.out.println(jsonInfo);
			
			PrintWriter writer = response.getWriter();
			writer.print(totalObj);
			
		}else if(from.equals("setgood")){ //좋아요 버튼 누른 경우
			System.out.println("doPlus");
			int num = Integer.parseInt(request.getParameter("num"));
			
			String email = request.getSession().getAttribute("email").toString();
			goodlistDAO goodDao = new goodlistDAO();
			String check="";
			//좋아요 내역이 있는지 확인한다
			//내역있으면 true, 내역없으면 false
			if(streamer.equals(email)){
				check="equal";
			}else if(goodDao.searchgood(2, num, email)){ //내역있는 경우 -> 좋아요가 추가될 수 없게 한다.
				check="false";
			}else{ //내역없는 경우 -> 1.내역을 작성한 후(goodlist) 2. 좋아요를 추가한다.(community)
				goodlistBean goodBean = new goodlistBean();
				goodBean.setBoardtype(2);
				goodBean.setEmail(email);
				goodBean.setPost_num(num);
				goodDao.setlist(goodBean); //내역을 작성
				
				communityDAO comDao = new communityDAO();
				comDao.plusgood(num); //좋아요를 추가
				
				check="true";
			}
			
			PrintWriter writer = response.getWriter();
			writer.print(check);
		}else if(from.equals("commumodify")){ //수정하기 누른 경우
			int num = Integer.parseInt(request.getParameter("num"));			
			JSONObject jsonObj = cdao.getCommcontent(num);
			
			PrintWriter writer = response.getWriter();
			writer.print(jsonObj);
		}
		
	}
}
