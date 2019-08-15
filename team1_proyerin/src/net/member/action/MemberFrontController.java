package net.member.action;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.member.action.Action;
import net.member.action.ActionForward;

@WebServlet("*.me")
public class MemberFrontController extends HttpServlet{

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("doGet");
		doProcess(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("doPost");
		doProcess(request, response);
	}
	
	protected void doProcess(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//가상요청 주소 가져오기
		String RequestURI=request.getRequestURI();
		System.out.println(RequestURI);
		
		String contextPath=request.getContextPath();
		
		System.out.println(contextPath.length());
		
		String command=RequestURI.substring(contextPath.length());
		System.out.println("응답받은 페이지 이름 : "+command);
		
		ActionForward forward=null;
		
		Action action=null;
		
		if(command.equals("/joinmemberAction.me")){
			
			action= new joinmemberAction();
			
			try{
				forward=action.execute(request, response);
			}catch(Exception e){
				e.printStackTrace();
			}
			
		}else if(command.equals("/login.me")){
			action= new loginAction();
			
			try{
				forward=action.execute(request, response);
			}catch(Exception e){
				e.printStackTrace();
			}
		}else if(command.equals("/main.me")){
			System.out.println("email2:"+request.getSession().getAttribute("email"));
			forward = new ActionForward();
			forward.setRedirect(true);
			forward.setPath("./watermelon.jsp");
			
		}else if(command.equals("/modifymemberAction.me")){
				action = new modifymemberAction();
				
				try{
					forward = action.execute(request, response);
				}catch(Exception e){
					e.printStackTrace();
				}
		}else if(command.equals("/dropAction.me")){
				action = new dropAction();
			
				try{
					forward = action.execute(request, response);
				}catch(Exception e){
					e.printStackTrace();
				}
		}else if(command.equals("/logout.me")){
			action = new logoutAction();
			try{
				forward = action.execute(request, response);
			}catch(Exception e){
				e.printStackTrace();
			}
		};
	
		if(forward!=null){
	         if(forward.isRedirect()){//true
	            response.sendRedirect(forward.getPath());
	         }else{//false
	            RequestDispatcher dispatcher=request.getRequestDispatcher(forward.getPath());
	            dispatcher.forward(request, response);
	         }
	     }
		
		
		
	}
	
}
