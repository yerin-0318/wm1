package net.member.action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.member.db.membersDAO;
import net.member.action.ActionForward;



public class loginAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String email = request.getParameter("email");
		System.out.println("세션 저장.. 이메일:"+email);
			
		HttpSession session = request.getSession();				
		session.setAttribute("email", email); 
			
		
		ActionForward forward = new ActionForward();
		forward.setRedirect(true);			
		forward.setPath("./main.me");			
		return forward;
			
		}
	}
	
			


