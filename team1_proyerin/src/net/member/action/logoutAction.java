package net.member.action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class logoutAction implements Action{

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("MemberLogoutAction execute()");
		//세션값 초기화
		HttpSession session=request.getSession();
		session.invalidate();
		

		ActionForward forward=new ActionForward();
		forward.setRedirect(true);
		forward.setPath("./main.me");
		return forward;
	}

}
