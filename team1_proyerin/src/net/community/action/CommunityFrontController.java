package net.community.action;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.community.action.Action;
import net.community.action.ActionForward;

@WebServlet("*.co")
public class CommunityFrontController extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doBoard(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doBoard(request, response);
	}

	protected void doBoard(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		
		String RequestURI = request.getRequestURI();
		System.out.println(RequestURI);

		String contextPath = request.getContextPath();

		String command = RequestURI.substring(contextPath.length());
		System.out.println(command);
		
		

		// ������ �̵� ��� ���� ��,�̵������� ��� �� ���� �Ͽ� ���� ���ִ� ��ü�� ������ �������� ����
		ActionForward forward = null;

		// �ڽ� Action ��ü���� ���� �������̽� Ÿ���� �������� ����
		Action action = null;

		if (command.equals("/community/listcom.co")){
			action = new listcomAction();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else if(command.equals("/mainfromcommu.co")){
			forward = new ActionForward();
			forward.setPath("./watermelon.jsp?center=streamerhome.jsp&streamcenter=community/commumain.jsp");
			forward.setRedirect(true);
		}else if(command.equals("/modifycom.co")){
			action = new modifycomAction();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else if(command.equals("/deletecom.co")){
			action = new deletecomAction();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		
		
		
		}else if (command.equals("/commentComDel.co")) {

			action = new commentComDelAction();

			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}/*

		else if (command.equals("/commentComMod.co")) {

			action = new commentComModAction();

			try {

				forward = action.execute(request, response);

			} catch (Exception e) {
				e.printStackTrace();

			}
*/
		else if (command.equals("/commentComWrt.co")) {
			action = new commentComWrtAction();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
			/*
		} else if (command.equals("/deletecom.co")) {

			action = new deletecomAction();

			try {

				forward = action.execute(request, response);

			} catch (Exception e) {
				e.printStackTrace();

			}
		}else if(command.equals("/modifycom.co")){
			

			action=new modifycomAction();
			
			try {

				forward=action.execute(request, response);
				
			} catch (Exception e) {
				e.printStackTrace();
			

			}
	}*/
		}else if(command.equals("/writecom.co")){
				
			action = new writecomAction();
			
			try {
				forward=action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();			
			}	
		}else if(command.equals("/commentRepot.co")){
			action = new repotAction();
			try {
				forward=action.execute(request, response);
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