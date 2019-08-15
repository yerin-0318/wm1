package net.manage.action;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.manage.action.Action;
import net.manage.action.ActionForward;
@WebServlet("*.manage")
public class ManageFrontController extends HttpServlet{

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doManage(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doManage(request, response);
	}
	
	protected void doManage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	
		String RequestURI = request.getRequestURI();
		System.out.println(RequestURI);

		String contextPath = request.getContextPath();

		String command = RequestURI.substring(contextPath.length());
		System.out.println(command);

		/* 占쌍쇽옙 占쏙옙 */
		// 占쏙옙占쏙옙占쏙옙 占싱듸옙 占쏙옙占� 占쏙옙占쏙옙 占쏙옙,占싱듸옙占쏙옙占쏙옙占쏙옙 占쏙옙占� 占쏙옙 占쏙옙占쏙옙 占싹울옙 占쏙옙占쏙옙 占쏙옙占쌍댐옙 占쏙옙체占쏙옙 占쏙옙占쏙옙占쏙옙 占쏙옙占쏙옙占쏙옙占쏙옙 占쏙옙占쏙옙
		ActionForward forward = null;

		// 占쌘쏙옙 Action 占쏙옙체占쏙옙占쏙옙 占쏙옙占쏙옙 占쏙옙占쏙옙占쏙옙占싱쏙옙 타占쏙옙占쏙옙 占쏙옙占쏙옙占쏙옙占쏙옙 占쏙옙占쏙옙
		Action action = null;
		
		if(command.equals("kickback.manage")){
			action = new kickbackAction();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	
		else if(command.equals("showmember.manage")){
			action = new showmemberAction();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		else if(command.equals("warningDel.manage")){
			action = new warningDelAction();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		else if(command.equals("warninglist.manage")){
			action = new warninglistAction();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
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
