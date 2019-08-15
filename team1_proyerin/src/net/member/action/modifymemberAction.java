package net.member.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.member.db.membersBean;
import net.member.db.membersDAO;

public class modifymemberAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		request.setCharacterEncoding("utf-8");
		System.out.println("수정액션 넘어옴");
		
		membersBean mb = new membersBean();
		mb.setPassword(request.getParameter("password"));
		mb.setName(request.getParameter("name"));
		mb.setGender(request.getParameter("gender"));
		mb.setBirth(request.getParameter("birth"));
		mb.setEmail(request.getParameter("email"));
		System.out.println("수정");
		//회원수정 성공 여부를 담을 변수 선언
		boolean result = false;
	
		membersDAO mdao = new membersDAO();
		
		mdao.modifypro(mb);
		
		//회원정보수정실패시 null반환
				
				
				ActionForward forward = new ActionForward();
				forward.setRedirect(true);
				forward.setPath("./modify.me");
				return forward;
				
		
		
		
		
		
	}

	
}
