package net.community.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.writelist.db.com_commuDAO;

public class commentComDelAction implements Action{

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		int num = Integer.parseInt(request.getParameter("num"));
		com_commuDAO dao = new com_commuDAO();
		dao.deleteComm(num);
				
		return null;
	}
	
}
