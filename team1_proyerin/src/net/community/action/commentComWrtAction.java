package net.community.action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.writelist.db.com_commuBean;
import net.writelist.db.com_commuDAO;

public class commentComWrtAction implements Action{

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		int com_num = Integer.parseInt(request.getParameter("num"));
		String comments = request.getParameter("comments");
		HttpSession session=request.getSession();
		String email = (String) session.getAttribute("email");
		System.out.println("num:"+com_num+", commentstxt:"+comments+", email:"+email);
		
		com_commuBean combean = new com_commuBean();
		combean.setContent(comments);
		combean.setCom_num(com_num);
		combean.setEmail(email);
		
		com_commuDAO dao = new com_commuDAO();
		dao.insertComm(combean);
		
		PrintWriter writer = response.getWriter();
		writer.print(1);
		
		return null;
	}
	
}
