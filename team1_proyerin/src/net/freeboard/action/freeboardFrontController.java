package net.freeboard.action;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.freeboard.action.Action;
import net.freeboard.action.ActionForward;


public class freeboardFrontController extends HttpServlet {


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doFree(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doFree(request, response);
	}
	
	protected void doFree(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String RequestURI = request.getRequestURI();
		System.out.println(RequestURI);

		String contextPath = request.getContextPath();

		String command = RequestURI.substring(contextPath.length());
		System.out.println(command);

		/* �ּ� �� */
		// ������ �̵� ��� ���� ��,�̵������� ��� �� ���� �Ͽ� ���� ���ִ� ��ü�� ������ �������� ����
		ActionForward forward = null;

		// �ڽ� Action ��ü���� ���� �������̽� Ÿ���� �������� ����
		Action action = null;
		
		if(command.equals("/deletefree.free")){
			
			action = new deletefreeAction();
			
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		else if(command.equals("modifyfree.free")){
			action = new modifyfreeAction();
					try {
						forward = action.execute(request, response);
					} catch (Exception e) {
						
					}
		}
		else if(command.equals("writefree.free")){
			action = new writefreeAction();
					try {
						forward = action.execute(request, response);
					} catch (Exception e) {
						
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
