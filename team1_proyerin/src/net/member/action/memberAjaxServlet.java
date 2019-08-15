package net.member.action;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;

import net.member.db.membersDAO;


@WebServlet("/memberAjax")
public class memberAjaxServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
					doHandle(request, response);	
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
					doHandle(request, response);
	}
	
	
	
	protected void doHandle(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		request.setCharacterEncoding("UTF-8");
		
		response.setContentType("text/html; charset=UTF-8");
		
		PrintWriter writer = response.getWriter();
		String from = request.getParameter("from");
		
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		membersDAO mdao = new membersDAO();
		
		if(from.equals("getlogin")){
			int checking = mdao.loginCheck(email, password);
			
			if(checking == 0){//비밀번호 틀렸을경우~
				writer.print("not_pass");			
			}else if(checking == -1){//아이디자체가 없을경우~ 
				writer.print("not_id");	
			}
		}else if(from.equals("getmodify")){
			
		
			System.out.println(email);
			JSONObject jsonObj = mdao.selectModify(email);
			System.out.println(jsonObj);
	//		PrintWriter writer = response.getWriter();
			writer.print(jsonObj);
		}else if(from.equals("getEmail")){ //join->이메일 확인
	/*
			request.setCharacterEncoding("UTF-8");
			response.setContentType("text/html;charset=UTF-8");
			
			String email = request.getParameter("email");*/
			
			membersDAO dao = new membersDAO();
			boolean result = dao.dupEmail(email);
	
			response.getWriter().write(result+"");
			System.out.println("EmailCheck:"+result);
		}else if(from.equals("checkEmail")){//다른 포털사이트 아이디로 로그인
			
			//요청값얻기(사용자가 입력한 아이디 얻기)
			String id = request.getParameter("id");
			System.out.println("id=" + id);
			
			//사용자가 입력한 id와  DB에 저장되어 있는 회원의 id를 비교 하기 위해 DB작업
			membersDAO memberDAO = new membersDAO();
			
			//아이디 중복 체크 여부값 얻기
			boolean overlappedID = memberDAO.overlappedID(id);
			
			//아이디 중복이냐 중복이 아니냐에 따라 메세지를 클라이언트의 웹브라우저로 출력(응답)
			if(overlappedID == true){
				writer.print("not_usable");//중복
			}else{
				writer.print("usable");//중복되지않음
			}
		}
	}
		
}
	


