package net.member.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.member.db.membersBean;
import net.member.db.membersDAO;

public class dropAction implements Action{
	/* 탈퇴 완료 후 다음페이지로 넘어가는거 추가해주세용 ~~~~~~~~~~~~~~~~~*/
	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		request.setCharacterEncoding("utf-8");
		System.out.println("dropAction()들어옴");
		
		HttpSession session = request.getSession();
		
		String email = (String) session.getAttribute("email");
		String passwd = request.getParameter("passwd");
		session.invalidate();
		
		membersDAO mdao = new membersDAO();
		membersBean mb = new membersBean();
		
		boolean result = false;
		result = mdao.dropDB(email);
		
		ActionForward forward = new ActionForward();
		forward.setRedirect(true);			
		forward.setPath("./main.me");			
		return forward;
	}

	
	
}
